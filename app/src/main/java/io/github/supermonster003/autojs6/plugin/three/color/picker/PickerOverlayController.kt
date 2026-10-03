package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Main-thread owner of the live picker overlays: a draggable target ring on the sample
 * point and a magnifier disc placed beside it. Dragging the ring moves the sample point
 * 1:1; sliding on the magnifier fine-tunes it at a reduced rate; magnifier ring-band
 * zones copy the color/coordinates or stop the picker. Every captured frame refreshes
 * the magnifier through [ProjectionSession]'s sample stream.
 */
internal class PickerOverlayController(
    context: Context,
    private val session: ProjectionSession,
    private val onStopRequested: () -> Unit,
) : AutoCloseable {
    private val appContext = context.applicationContext
    private val windowManager = appContext.getSystemService(WindowManager::class.java)
    private val mainHandler = Handler(Looper.getMainLooper())
    private val closed = AtomicBoolean(false)

    private var sampleX = 0
    private var sampleY = 0
    private var captureRadius = PickerSettingsCatalog.captureRadiusFor(PickerSettingsCatalog.DEFAULT_INDEX)
    private var magnifierSizePx = 0
    private val targetSizePx = appContext.dp(TARGET_WINDOW_DP)

    private var targetRoot: TargetGestureLayout? = null
    private var targetRing: TargetRingView? = null
    private var targetParams: WindowManager.LayoutParams? = null
    private var magnifierRoot: MagnifierGestureLayout? = null
    private var magnifierView: MagnifierView? = null
    private var magnifierParams: WindowManager.LayoutParams? = null
    private var feedbackText: TextView? = null

    private var started = false
    private var firstSampleReceived = false
    private val watchdog = Runnable(::onCaptureTimedOut)
    private val rebuildRunnable = Runnable { rebuildOverlays(resetToCenter = false) }
    private val configRebuildRunnable = Runnable { rebuildOverlays(resetToCenter = true) }
    private val hideFeedbackRunnable = Runnable { feedbackText?.visibility = View.GONE }
    private val settingsListener: () -> Unit = {
        mainHandler.post {
            if (!closed.get() && started) {
                mainHandler.removeCallbacks(rebuildRunnable)
                mainHandler.postDelayed(rebuildRunnable, REBUILD_DELAY_MILLIS)
            }
        }
    }

    fun start() {
        if (closed.get() || started) return
        started = true
        val (screenWidth, screenHeight) = screenSize()
        sampleX = screenWidth / 2
        sampleY = screenHeight / 2
        try {
            buildOverlays()
        } catch (error: RuntimeException) {
            onStopRequested()
            throw error
        }
        session.setSampleListener(::onSample)
        session.setSampleRequest(sampleX, sampleY, captureRadius)
        PickerSettings.addRebuildListener(settingsListener)
        mainHandler.postDelayed(watchdog, FIRST_FRAME_TIMEOUT_MILLIS)
    }

    fun onConfigurationChanged(@Suppress("UNUSED_PARAMETER") configuration: Configuration) {
        if (closed.get() || !started) return
        teardownOverlays()
        mainHandler.removeCallbacks(configRebuildRunnable)
        mainHandler.postDelayed(configRebuildRunnable, REBUILD_DELAY_MILLIS)
    }

    override fun close() {
        if (!closed.compareAndSet(false, true)) return
        PickerSettings.removeRebuildListener(settingsListener)
        session.clearSampleRequest()
        session.setSampleListener(null)
        mainHandler.removeCallbacks(watchdog)
        mainHandler.removeCallbacks(rebuildRunnable)
        mainHandler.removeCallbacks(configRebuildRunnable)
        mainHandler.removeCallbacks(hideFeedbackRunnable)
        teardownOverlays()
    }

    private fun buildOverlays() {
        val sizeIndex = PickerSettings.magnifierSizeIndex(appContext)
        val rangeIndex = PickerSettings.captureRangeIndex(appContext)
        captureRadius = PickerSettingsCatalog.captureRadiusFor(rangeIndex)
        magnifierSizePx = appContext.dp(PickerSettingsCatalog.magnifierSizeDpFor(sizeIndex))
        val gridCells = PickerSettingsCatalog.gridCellCountFor(captureRadius)

        val (screenWidth, screenHeight) = screenSize()
        val clamped = MagnifierGeometry.clampSample(sampleX, sampleY, screenWidth, screenHeight)
        sampleX = clamped.first
        sampleY = clamped.second

        val ring = TargetRingView(appContext).apply { setCaptureRadius(captureRadius) }
        val target = TargetGestureLayout(appContext, ::onTargetDragTo).apply {
            addView(ring, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        }
        val magnifier = MagnifierView(
            appContext,
            gridCells = gridCells,
            showGrid = PickerSettings.showGrid(appContext),
            hexFormat = PickerSettings.hexFormat(appContext),
        )
        val feedback = buildFeedbackText()
        val magnifierLayout = MagnifierGestureLayout(
            appContext,
            onFineTuneTo = ::moveSampleTo,
            onTap = ::onMagnifierTap,
            onLongPressColorZone = ::toggleColorFormat,
            hitZoneAt = { x, y -> magnifier.hitZone(x, y) },
        ).apply {
            addView(magnifier, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
            addView(
                feedback,
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                },
            )
        }

        val targetLayoutParams = overlayParams(targetSizePx, targetSizePx)
        val magnifierLayoutParams = overlayParams(magnifierSizePx, magnifierSizePx)
        windowManager.addView(target, targetLayoutParams)
        try {
            windowManager.addView(magnifierLayout, magnifierLayoutParams)
        } catch (error: RuntimeException) {
            runCatching { windowManager.removeViewImmediate(target) }
            throw error
        }

        targetRoot = target
        targetRing = ring
        targetParams = targetLayoutParams
        magnifierRoot = magnifierLayout
        magnifierView = magnifier
        magnifierParams = magnifierLayoutParams
        feedbackText = feedback
        applyWindowPositions()
    }

    private fun teardownOverlays() {
        targetRoot?.let { view -> runCatching { windowManager.removeViewImmediate(view) } }
        magnifierRoot?.let { view -> runCatching { windowManager.removeViewImmediate(view) } }
        targetRoot = null
        targetRing = null
        targetParams = null
        magnifierRoot = null
        magnifierView = null
        magnifierParams = null
        feedbackText = null
    }

    private fun rebuildOverlays(resetToCenter: Boolean) {
        if (closed.get() || !started) return
        teardownOverlays()
        val (screenWidth, screenHeight) = screenSize()
        if (resetToCenter) {
            sampleX = screenWidth / 2
            sampleY = screenHeight / 2
        }
        try {
            buildOverlays()
        } catch (_: RuntimeException) {
            onStopRequested()
            return
        }
        session.setSampleRequest(sampleX, sampleY, captureRadius)
    }

    private fun applyWindowPositions() {
        val (screenWidth, screenHeight) = screenSize()
        val targetLayoutParams = targetParams
        val targetLayout = targetRoot
        if (targetLayoutParams != null && targetLayout != null) {
            val placement = MagnifierGeometry.targetWindowPlacement(sampleX, sampleY, targetSizePx, screenWidth, screenHeight)
            if (targetLayoutParams.x != placement.x || targetLayoutParams.y != placement.y) {
                targetLayoutParams.x = placement.x
                targetLayoutParams.y = placement.y
                runCatching { windowManager.updateViewLayout(targetLayout, targetLayoutParams) }
            }
            targetRing?.setHoleOffset(placement.overflowX, placement.overflowY)
        }
        val magnifierLayoutParams = magnifierParams
        val magnifierLayout = magnifierRoot
        if (magnifierLayoutParams != null && magnifierLayout != null) {
            val (x, y) = MagnifierGeometry.magnifierWindowPosition(sampleX, sampleY, magnifierSizePx, screenWidth, screenHeight)
            if (magnifierLayoutParams.x != x || magnifierLayoutParams.y != y) {
                magnifierLayoutParams.x = x
                magnifierLayoutParams.y = y
                runCatching { windowManager.updateViewLayout(magnifierLayout, magnifierLayoutParams) }
            }
        }
    }

    private fun onSample(result: SampleResult) {
        if (closed.get()) return
        if (result.radius != captureRadius) return
        if (!firstSampleReceived) {
            firstSampleReceived = true
            mainHandler.removeCallbacks(watchdog)
        }
        magnifierView?.updateSample(result.pixels, result.x, result.y)
    }

    private fun onCaptureTimedOut() {
        if (closed.get() || firstSampleReceived) return
        Toast.makeText(appContext, R.string.text_capture_timed_out, Toast.LENGTH_LONG).show()
        onStopRequested()
    }

    private fun onTargetDragTo(startX: Int, startY: Int, totalDeltaX: Float, totalDeltaY: Float) {
        moveSampleTo(startX + totalDeltaX.toInt(), startY + totalDeltaY.toInt())
    }

    private fun moveSampleTo(x: Int, y: Int) {
        if (closed.get()) return
        val (screenWidth, screenHeight) = screenSize()
        val clamped = MagnifierGeometry.clampSample(x, y, screenWidth, screenHeight)
        if (clamped.first == sampleX && clamped.second == sampleY) return
        sampleX = clamped.first
        sampleY = clamped.second
        session.setSampleRequest(sampleX, sampleY, captureRadius)
        applyWindowPositions()
    }

    private fun onMagnifierTap(zone: MagnifierHitZone) {
        val magnifier = magnifierView ?: return
        if (!magnifier.hasSample) return
        when (zone) {
            MagnifierHitZone.COLOR_TEXT ->
                copyToClipboard(magnifier.colorCopyText(PickerSettings.copyNumericOnly(appContext)))
            MagnifierHitZone.COORDINATE_TEXT -> copyToClipboard(magnifier.coordinateText())
            MagnifierHitZone.CLOSE -> onStopRequested()
            MagnifierHitZone.NONE -> Unit
        }
    }

    private fun toggleColorFormat() {
        val magnifier = magnifierView ?: return
        val newValue = !magnifier.hexFormat
        magnifier.hexFormat = newValue
        PickerSettings.setHexFormat(appContext, newValue)
    }

    private fun copyToClipboard(text: String) {
        val message = try {
            val clipboard = appContext.getSystemService(ClipboardManager::class.java)
            clipboard.setPrimaryClip(ClipData.newPlainText(appContext.getString(R.string.clipboard_label_color), text))
            appContext.getString(R.string.text_copied_value, text)
        } catch (error: RuntimeException) {
            error.toString()
        }
        showFeedback(message)
    }

    private fun showFeedback(message: String) {
        val feedback = feedbackText ?: return
        feedback.text = message
        feedback.visibility = View.VISIBLE
        feedback.alpha = 0f
        feedback.animate().cancel()
        feedback.animate().alpha(1f).setDuration(FEEDBACK_FADE_IN_MILLIS).start()
        mainHandler.removeCallbacks(hideFeedbackRunnable)
        mainHandler.postDelayed(hideFeedbackRunnable, FEEDBACK_VISIBLE_MILLIS)
    }

    private fun buildFeedbackText(): TextView = TextView(appContext).apply {
        setTextColor(Color.WHITE)
        textSize = 13f
        typeface = Typeface.MONOSPACE
        maxLines = 2
        setPadding(appContext.dp(12), appContext.dp(8), appContext.dp(12), appContext.dp(8))
        background = GradientDrawable().apply {
            cornerRadius = appContext.dp(8).toFloat()
            setColor(Color.argb(230, 40, 40, 40))
        }
        visibility = View.GONE
        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
    }

    private fun screenSize(): Pair<Int, Int> {
        if (Build.VERSION.SDK_INT >= 30) {
            val bounds = windowManager.maximumWindowMetrics.bounds
            return bounds.width() to bounds.height()
        }
        @Suppress("DEPRECATION")
        return android.graphics.Point().also(windowManager.defaultDisplay::getRealSize).let { it.x to it.y }
    }

    @SuppressLint("RtlHardcoded")
    private fun overlayParams(width: Int, height: Int): WindowManager.LayoutParams {
        val type = if (Build.VERSION.SDK_INT >= 26) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }
        return WindowManager.LayoutParams(
            width,
            height,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT,
        ).apply {
            // WindowManager x is an absolute physical-screen coordinate, so START would mirror it in RTL.
            gravity = Gravity.TOP or Gravity.LEFT
            if (Build.VERSION.SDK_INT >= 28) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }
    }

    private inner class TargetGestureLayout(
        context: Context,
        private val onDragTo: (startX: Int, startY: Int, totalDeltaX: Float, totalDeltaY: Float) -> Unit,
    ) : FrameLayout(context) {
        private var downRawX = 0f
        private var downRawY = 0f
        private var startSampleX = 0
        private var startSampleY = 0

        init {
            contentDescription = context.getString(R.string.content_desc_target_ring)
            if (Build.VERSION.SDK_INT >= 29) isForceDarkAllowed = false
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downRawX = event.rawX
                    downRawY = event.rawY
                    startSampleX = sampleX
                    startSampleY = sampleY
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    onDragTo(startSampleX, startSampleY, event.rawX - downRawX, event.rawY - downRawY)
                    return true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> return true
            }
            return super.onTouchEvent(event)
        }
    }

    private inner class MagnifierGestureLayout(
        context: Context,
        private val onFineTuneTo: (x: Int, y: Int) -> Unit,
        private val onTap: (MagnifierHitZone) -> Unit,
        private val onLongPressColorZone: () -> Unit,
        private val hitZoneAt: (x: Float, y: Float) -> MagnifierHitZone,
    ) : FrameLayout(context) {
        private val touchSlopSquared = ViewConfiguration.get(context).scaledTouchSlop.let { it * it }.toFloat()
        private var downRawX = 0f
        private var downRawY = 0f
        private var downLocalX = 0f
        private var downLocalY = 0f
        private var startSampleX = 0
        private var startSampleY = 0
        private var fineTuneDivisor = PickerSettingsCatalog.fineTuneDivisorFor(PickerSettingsCatalog.DEFAULT_INDEX)
        private var dragging = false
        private var longPressFired = false
        private val longPressRunnable = Runnable {
            if (!dragging && hitZoneAt(downLocalX, downLocalY) == MagnifierHitZone.COLOR_TEXT) {
                longPressFired = true
                onLongPressColorZone()
            }
        }

        init {
            contentDescription = context.getString(R.string.content_desc_magnifier)
            if (Build.VERSION.SDK_INT >= 29) isForceDarkAllowed = false
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downRawX = event.rawX
                    downRawY = event.rawY
                    downLocalX = event.x
                    downLocalY = event.y
                    startSampleX = sampleX
                    startSampleY = sampleY
                    fineTuneDivisor = PickerSettingsCatalog.fineTuneDivisorFor(PickerSettings.fineTuneSpeedIndex(context))
                    dragging = false
                    longPressFired = false
                    mainHandler.postDelayed(longPressRunnable, ViewConfiguration.getLongPressTimeout().toLong())
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    val totalDeltaX = event.rawX - downRawX
                    val totalDeltaY = event.rawY - downRawY
                    if (!dragging && totalDeltaX * totalDeltaX + totalDeltaY * totalDeltaY > touchSlopSquared) {
                        dragging = true
                        mainHandler.removeCallbacks(longPressRunnable)
                    }
                    if (dragging && !longPressFired) {
                        val target = MagnifierGeometry.fineTuneTarget(
                            startSampleX,
                            startSampleY,
                            totalDeltaX,
                            totalDeltaY,
                            fineTuneDivisor,
                        )
                        onFineTuneTo(target.first, target.second)
                    }
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    mainHandler.removeCallbacks(longPressRunnable)
                    if (!dragging && !longPressFired) onTap(hitZoneAt(event.x, event.y))
                    return true
                }
                MotionEvent.ACTION_CANCEL -> {
                    mainHandler.removeCallbacks(longPressRunnable)
                    return true
                }
            }
            return super.onTouchEvent(event)
        }

        override fun onDetachedFromWindow() {
            mainHandler.removeCallbacks(longPressRunnable)
            super.onDetachedFromWindow()
        }
    }

    private companion object {
        const val TARGET_WINDOW_DP = 48
        const val FIRST_FRAME_TIMEOUT_MILLIS = 500L
        const val REBUILD_DELAY_MILLIS = 50L
        const val FEEDBACK_FADE_IN_MILLIS = 150L
        const val FEEDBACK_VISIBLE_MILLIS = 1_500L
    }
}

internal fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()
