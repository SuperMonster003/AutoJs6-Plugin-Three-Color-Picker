package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.Choreographer
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.abs
import kotlin.math.roundToInt

/** Main-thread owner for the two mutually exclusive overlay windows. */
internal class PickerOverlayController(
    context: Context,
    private val session: ProjectionSession,
    private val onStopRequested: () -> Unit,
) : AutoCloseable {
    private val appContext = context.applicationContext
    private val windowManager = appContext.getSystemService(WindowManager::class.java)
    private val closed = AtomicBoolean(false)
    private val bubble = PickerBubbleView(appContext)
    private val bubbleSize = appContext.dp(64)
    private val bubbleMargin = appContext.dp(10)
    private val bubbleParams = overlayParams(bubbleSize, bubbleSize, focusable = false)
    private var bubbleAttached = false
    private var pickerView: PickerScreenLayout? = null
    private var frozenBitmap: Bitmap? = null
    private var refreshRetainedBitmap: Bitmap? = null

    init {
        val screen = screenSize()
        bubbleParams.x = (screen.first - bubbleSize - bubbleMargin).coerceAtLeast(bubbleMargin)
        bubbleParams.y = ((screen.second - bubbleSize) / 2).coerceAtLeast(bubbleMargin)
        bubble.onDrag = ::moveBubble
        bubble.onRelease = ::snapBubble
        bubble.onTap = ::openPicker
    }

    fun showBubble() {
        if (closed.get() || bubbleAttached || pickerView != null) return
        try {
            windowManager.addView(bubble, bubbleParams)
            bubbleAttached = true
        } catch (error: RuntimeException) {
            onStopRequested()
            throw error
        }
    }

    fun onConfigurationChanged(@Suppress("UNUSED_PARAMETER") configuration: Configuration) {
        if (closed.get()) return
        if (pickerView != null) {
            returnToBubble()
        } else if (bubbleAttached) {
            snapBubble()
        }
    }

    override fun close() {
        if (!closed.compareAndSet(false, true)) return
        removeBubble()
        removePicker(recycle = true)
        recycleRefreshRetainedBitmap()
    }

    private fun openPicker() {
        if (closed.get()) return
        removeBubble()
        afterHiddenFrames {
            session.capture { bitmap ->
                if (closed.get()) {
                    bitmap?.recycle()
                } else if (bitmap == null) {
                    Toast.makeText(appContext, R.string.text_capture_failed, Toast.LENGTH_LONG).show()
                    showBubble()
                } else {
                    showPicker(bitmap)
                }
            }
        }
    }

    private fun showPicker(bitmap: Bitmap) {
        removePicker(recycle = true)
        frozenBitmap = bitmap
        val view = PickerScreenLayout(
            context = appContext,
            bitmap = bitmap,
            onCopy = ::copyColor,
            onRefresh = ::refresh,
            onReturn = ::returnToBubble,
            onStop = onStopRequested,
        )
        try {
            windowManager.addView(
                view,
                overlayParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    focusable = true,
                ),
            )
            pickerView = view
        } catch (error: RuntimeException) {
            frozenBitmap = null
            bitmap.recycle()
            showBubble()
        }
    }

    private fun refresh() {
        if (closed.get()) return
        recycleRefreshRetainedBitmap()
        refreshRetainedBitmap = frozenBitmap
        removePicker(recycle = false)
        afterHiddenFrames {
            session.capture { replacement ->
                val retained = takeRefreshRetainedBitmap()
                if (closed.get()) {
                    replacement?.recycle()
                    retained?.recycle()
                    return@capture
                }
                if (replacement == null) {
                    Toast.makeText(appContext, R.string.text_capture_failed, Toast.LENGTH_LONG).show()
                    if (retained != null && !retained.isRecycled) showPicker(retained) else showBubble()
                } else {
                    retained?.takeUnless(Bitmap::isRecycled)?.recycle()
                    showPicker(replacement)
                }
            }
        }
    }

    private fun returnToBubble() {
        removePicker(recycle = true)
        showBubble()
    }

    private fun copyColor(value: String) {
        val clipboard = appContext.getSystemService(ClipboardManager::class.java)
        clipboard.setPrimaryClip(ClipData.newPlainText(appContext.getString(R.string.clipboard_label_color), value))
        Toast.makeText(appContext, appContext.getString(R.string.text_color_copied, value), Toast.LENGTH_SHORT).show()
    }

    private fun moveBubble(deltaX: Float, deltaY: Float) {
        if (!bubbleAttached || closed.get()) return
        val screen = screenSize()
        bubbleParams.x = (bubbleParams.x + deltaX.roundToInt()).coerceIn(
            bubbleMargin,
            (screen.first - bubbleSize - bubbleMargin).coerceAtLeast(bubbleMargin),
        )
        bubbleParams.y = (bubbleParams.y + deltaY.roundToInt()).coerceIn(
            bubbleMargin,
            (screen.second - bubbleSize - bubbleMargin).coerceAtLeast(bubbleMargin),
        )
        runCatching { windowManager.updateViewLayout(bubble, bubbleParams) }
    }

    private fun snapBubble() {
        if (!bubbleAttached || closed.get()) return
        val screen = screenSize()
        val left = bubbleMargin
        val right = (screen.first - bubbleSize - bubbleMargin).coerceAtLeast(left)
        bubbleParams.x = if (bubbleParams.x + bubbleSize / 2 < screen.first / 2) left else right
        bubbleParams.y = bubbleParams.y.coerceIn(
            bubbleMargin,
            (screen.second - bubbleSize - bubbleMargin).coerceAtLeast(bubbleMargin),
        )
        runCatching { windowManager.updateViewLayout(bubble, bubbleParams) }
    }

    private fun removeBubble() {
        if (!bubbleAttached) return
        runCatching { windowManager.removeViewImmediate(bubble) }
        bubbleAttached = false
    }

    private fun removePicker(recycle: Boolean) {
        pickerView?.let { view -> runCatching { windowManager.removeViewImmediate(view) } }
        pickerView = null
        if (recycle) frozenBitmap?.takeUnless(Bitmap::isRecycled)?.recycle()
        frozenBitmap = null
    }

    private fun takeRefreshRetainedBitmap(): Bitmap? = refreshRetainedBitmap.also {
        refreshRetainedBitmap = null
    }

    private fun recycleRefreshRetainedBitmap() {
        takeRefreshRetainedBitmap()?.takeUnless(Bitmap::isRecycled)?.recycle()
    }

    private fun afterHiddenFrames(block: () -> Unit) {
        Choreographer.getInstance().postFrameCallback {
            Choreographer.getInstance().postFrameCallback {
                if (!closed.get()) block()
            }
        }
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
    private fun overlayParams(width: Int, height: Int, focusable: Boolean): WindowManager.LayoutParams {
        val type = if (Build.VERSION.SDK_INT >= 26) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }
        val baseFlags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        return WindowManager.LayoutParams(
            width,
            height,
            type,
            if (focusable) baseFlags else baseFlags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT,
        ).apply {
            // WindowManager x is an absolute physical-screen coordinate, so START would mirror it in RTL.
            gravity = Gravity.TOP or Gravity.LEFT
            if (Build.VERSION.SDK_INT >= 28) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }
    }
}

private data class ColorSample(val x: Int, val y: Int, val color: Int, val touchX: Float, val touchY: Float)

private class PickerBubbleView(context: Context) : View(context) {
    var onDrag: (Float, Float) -> Unit = { _, _ -> }
    var onRelease: () -> Unit = {}
    var onTap: () -> Unit = {}

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var lastRawX = 0f
    private var lastRawY = 0f
    private var downRawX = 0f
    private var downRawY = 0f
    private var dragged = false

    init {
        contentDescription = context.getString(R.string.content_desc_color_picker_bubble)
        isClickable = true
        elevation = context.dp(8).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        val radius = minOf(width, height) * 0.43f
        paint.style = Paint.Style.FILL
        paint.shader = LinearGradient(
            cx - radius,
            cy - radius,
            cx + radius,
            cy + radius,
            Color.rgb(40, 36, 48),
            Color.rgb(20, 20, 24),
            Shader.TileMode.CLAMP,
        )
        canvas.drawCircle(cx, cy, radius, paint)
        paint.shader = SweepGradient(
            cx,
            cy,
            intArrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.RED),
            null,
        )
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = context.dp(4).toFloat()
        canvas.drawCircle(cx, cy, radius - paint.strokeWidth / 2f, paint)
        paint.shader = null
        paint.color = Color.WHITE
        paint.strokeWidth = context.dp(2).toFloat()
        val arm = radius * 0.52f
        val gap = radius * 0.18f
        canvas.drawLine(cx - arm, cy, cx - gap, cy, paint)
        canvas.drawLine(cx + gap, cy, cx + arm, cy, paint)
        canvas.drawLine(cx, cy - arm, cx, cy - gap, paint)
        canvas.drawLine(cx, cy + gap, cx, cy + arm, paint)
        canvas.drawCircle(cx, cy, gap, paint)
        paint.style = Paint.Style.FILL
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                parent?.requestDisallowInterceptTouchEvent(true)
                downRawX = event.rawX
                downRawY = event.rawY
                lastRawX = downRawX
                lastRawY = downRawY
                dragged = false
                isPressed = true
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (!dragged && (abs(event.rawX - downRawX) > touchSlop || abs(event.rawY - downRawY) > touchSlop)) {
                    dragged = true
                }
                if (dragged) onDrag(event.rawX - lastRawX, event.rawY - lastRawY)
                lastRawX = event.rawX
                lastRawY = event.rawY
                return true
            }
            MotionEvent.ACTION_UP -> {
                isPressed = false
                if (dragged) onRelease() else performClick()
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                isPressed = false
                if (dragged) onRelease()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        onTap()
        return true
    }
}

@SuppressLint("ViewConstructor")
private class PickerScreenLayout(
    context: Context,
    bitmap: Bitmap,
    onCopy: (String) -> Unit,
    onRefresh: () -> Unit,
    onReturn: () -> Unit,
    onStop: () -> Unit,
) : FrameLayout(context) {
    private val panel: LinearLayout
    private val swatch: View
    private val hexText: TextView
    private val rgbText: TextView
    private val hslText: TextView
    private val coordinateText: TextView
    private var currentSample = ColorSample(0, 0, Color.BLACK, 0f, 0f)
    private var topInset = 0
    private var bottomInset = 0

    init {
        val canvasView = PickerCanvasView(context, bitmap).apply {
            contentDescription = context.getString(R.string.text_picker_instruction)
        }
        addView(canvasView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        panel = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(context.dp(16), context.dp(12), context.dp(16), context.dp(12))
            elevation = context.dp(10).toFloat()
            background = GradientDrawable().apply {
                cornerRadius = context.dp(24).toFloat()
                setColor(Color.argb(238, 28, 27, 32))
                setStroke(context.dp(1), Color.argb(80, 255, 255, 255))
            }
        }
        val information = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        swatch = View(context).apply {
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
        }
        information.addView(swatch, LinearLayout.LayoutParams(context.dp(58), context.dp(58)).apply {
            marginEnd = context.dp(14)
        })
        val values = LinearLayout(context).apply { orientation = LinearLayout.VERTICAL }
        hexText = valueText(context, 17f, bold = true)
        rgbText = valueText(context, 14f)
        hslText = valueText(context, 14f)
        coordinateText = valueText(context, 14f)
        values.addView(hexText)
        values.addView(rgbText)
        values.addView(hslText)
        values.addView(coordinateText)
        information.addView(values, LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f))
        panel.addView(information, LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))

        val actions = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val copyButton = actionButton(context, R.string.text_copy) {}
        copyButton.setOnClickListener { anchor -> showCopyMenu(anchor, currentSample, onCopy) }
        actions.addView(copyButton)
        actions.addView(actionButton(context, R.string.text_refresh, onRefresh))
        actions.addView(actionButton(context, R.string.text_return_to_bubble, onReturn))
        actions.addView(actionButton(context, R.string.text_stop_color_picker, onStop))
        val scroll = HorizontalScrollView(context).apply {
            isHorizontalScrollBarEnabled = false
            addView(actions)
        }
        panel.addView(scroll, LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            topMargin = context.dp(8)
        })
        addView(panel, panelParams(Gravity.BOTTOM))

        canvasView.onSampleChanged = { sample ->
            currentSample = sample
            renderSample(sample)
            movePanelAwayFrom(sample.touchY, height)
        }
        canvasView.post { canvasView.sampleAt(width / 2f, height / 2f) }

        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            val system = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            topInset = system.top
            bottomInset = system.bottom
            updatePanelMargins()
            insets
        }
    }

    private fun renderSample(sample: ColorSample) {
        val hsl = ColorFormatter.hsl(sample.color)
        hexText.text = context.getString(R.string.text_hex_format, ColorFormatter.hex(sample.color))
        rgbText.text = context.getString(
            R.string.text_rgb_format,
            ColorFormatter.red(sample.color),
            ColorFormatter.green(sample.color),
            ColorFormatter.blue(sample.color),
        )
        hslText.text = context.getString(
            R.string.text_hsl_format,
            ColorFormatter.oneDecimal(hsl.hue),
            ColorFormatter.oneDecimal(hsl.saturation),
            ColorFormatter.oneDecimal(hsl.lightness),
        )
        coordinateText.text = context.getString(R.string.text_coordinates, sample.x, sample.y)
        swatch.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(sample.color or Color.BLACK)
            setStroke(context.dp(2), Color.WHITE)
        }
    }

    private fun movePanelAwayFrom(touchY: Float, viewHeight: Int) {
        if (viewHeight <= 0) return
        val desiredGravity = if (touchY > viewHeight / 2f) Gravity.TOP else Gravity.BOTTOM
        val params = panel.layoutParams as LayoutParams
        if (params.gravity != desiredGravity) {
            params.gravity = desiredGravity
            panel.layoutParams = params
            updatePanelMargins()
        }
    }

    private fun updatePanelMargins() {
        val params = panel.layoutParams as? LayoutParams ?: return
        val edge = context.dp(12)
        params.setMargins(edge, topInset + edge, edge, bottomInset + edge)
        panel.layoutParams = params
    }

    private fun panelParams(gravity: Int): LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
        this.gravity = gravity
        val edge = context.dp(12)
        setMargins(edge, edge, edge, edge)
    }

    private fun valueText(context: Context, size: Float, bold: Boolean = false): TextView = TextView(context).apply {
        textSize = size
        setTextColor(Color.WHITE)
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.MONOSPACE,
            if (bold) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL,
        )
        setLineSpacing(0f, 1.08f)
    }

    private fun showCopyMenu(anchor: View, sample: ColorSample, onCopy: (String) -> Unit) {
        val hsl = ColorFormatter.hsl(sample.color)
        val choices = mapOf(
            COPY_HEX to ColorFormatter.hex(sample.color),
            COPY_RGB to ColorFormatter.rgb(sample.color),
            COPY_HSL to ColorFormatter.hslCss(sample.color),
        )
        PopupMenu(context, anchor).apply {
            menu.add(
                COPY_GROUP,
                COPY_HEX,
                0,
                context.getString(R.string.text_hex_format, choices.getValue(COPY_HEX)),
            )
            menu.add(
                COPY_GROUP,
                COPY_RGB,
                1,
                context.getString(
                    R.string.text_rgb_format,
                    ColorFormatter.red(sample.color),
                    ColorFormatter.green(sample.color),
                    ColorFormatter.blue(sample.color),
                ),
            )
            menu.add(
                COPY_GROUP,
                COPY_HSL,
                2,
                context.getString(
                    R.string.text_hsl_format,
                    ColorFormatter.oneDecimal(hsl.hue),
                    ColorFormatter.oneDecimal(hsl.saturation),
                    ColorFormatter.oneDecimal(hsl.lightness),
                ),
            )
            setOnMenuItemClickListener { item ->
                choices[item.itemId]?.let(onCopy) != null
            }
            show()
        }
    }

    private fun actionButton(context: Context, text: Int, action: () -> Unit): MaterialButton = MaterialButton(context).apply {
        setText(text)
        isAllCaps = false
        minHeight = context.dp(48)
        minimumWidth = context.dp(72)
        setOnClickListener { action() }
    }

    private companion object {
        const val COPY_GROUP = 0x534350
        const val COPY_HEX = 1
        const val COPY_RGB = 2
        const val COPY_HSL = 3
    }
}

@SuppressLint("ViewConstructor")
private class PickerCanvasView(context: Context, private val bitmap: Bitmap) : View(context) {
    var onSampleChanged: (ColorSample) -> Unit = {}

    private val imagePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private val pixelPaint = Paint()
    private val bitmapDestination = RectF()
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = context.dp(2).toFloat()
    }
    private var content = FloatBounds(0f, 0f, 1f, 1f)
    private var sample: ColorSample? = null
    private val gridRadius = 5
    private val cellSize = context.dp(14).toFloat()

    init {
        isClickable = true
        isFocusable = true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
        content = CoordinateMapper.fitCenterBounds(width, height, bitmap.width, bitmap.height)
        bitmapDestination.set(content.left, content.top, content.right, content.bottom)
        canvas.drawBitmap(bitmap, null, bitmapDestination, imagePaint)
        sample?.let { drawSelection(canvas, it) }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                sampleAtRaw(event.rawX, event.rawY)
                return true
            }
            MotionEvent.ACTION_UP -> {
                sampleAtRaw(event.rawX, event.rawY)
                performClick()
                return true
            }
            MotionEvent.ACTION_CANCEL -> return true
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun sampleAt(x: Float, y: Float) {
        if (width <= 0 || height <= 0 || bitmap.isRecycled) return
        content = CoordinateMapper.fitCenterBounds(width, height, bitmap.width, bitmap.height)
        val pixel = CoordinateMapper.mapToPixel(x, y, content, bitmap.width, bitmap.height, clamp = true) ?: return
        val selected = ColorSample(pixel.x, pixel.y, bitmap.getPixel(pixel.x, pixel.y), x, y)
        sample = selected
        onSampleChanged(selected)
        invalidate()
    }

    private fun sampleAtRaw(rawX: Float, rawY: Float) {
        val location = IntArray(2)
        getLocationOnScreen(location)
        sampleAt(rawX - location[0], rawY - location[1])
    }

    private fun drawSelection(canvas: Canvas, selected: ColorSample) {
        val markerX = selected.touchX.coerceIn(content.left, content.right)
        val markerY = selected.touchY.coerceIn(content.top, content.bottom)
        linePaint.color = Color.BLACK
        linePaint.strokeWidth = context.dp(4).toFloat()
        canvas.drawCircle(markerX, markerY, context.dp(10).toFloat(), linePaint)
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = context.dp(2).toFloat()
        canvas.drawCircle(markerX, markerY, context.dp(10).toFloat(), linePaint)

        val cells = gridRadius * 2 + 1
        val size = cells * cellSize
        val left = (markerX - size / 2f).coerceIn(context.dp(8).toFloat(), width - size - context.dp(8))
        val preferredTop = if (markerY > height / 2f) markerY - size - context.dp(34) else markerY + context.dp(34)
        val top = preferredTop.coerceIn(context.dp(8).toFloat(), height - size - context.dp(8))
        pixelPaint.style = Paint.Style.FILL
        for (row in -gridRadius..gridRadius) {
            for (column in -gridRadius..gridRadius) {
                val x = (selected.x + column).coerceIn(0, bitmap.width - 1)
                val y = (selected.y + row).coerceIn(0, bitmap.height - 1)
                pixelPaint.color = bitmap.getPixel(x, y)
                val cellLeft = left + (column + gridRadius) * cellSize
                val cellTop = top + (row + gridRadius) * cellSize
                canvas.drawRect(cellLeft, cellTop, cellLeft + cellSize + 0.5f, cellTop + cellSize + 0.5f, pixelPaint)
            }
        }
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = context.dp(3).toFloat()
        canvas.drawRect(left, top, left + size, top + size, linePaint)
        linePaint.color = if (ColorFormatter.hsl(selected.color).lightness > 55.0) Color.BLACK else Color.WHITE
        linePaint.strokeWidth = context.dp(2).toFloat()
        val centerLeft = left + gridRadius * cellSize
        val centerTop = top + gridRadius * cellSize
        canvas.drawRect(centerLeft, centerTop, centerLeft + cellSize, centerTop + cellSize, linePaint)
        canvas.drawLine(centerLeft + cellSize / 2f, centerTop, centerLeft + cellSize / 2f, centerTop + cellSize, linePaint)
        canvas.drawLine(centerLeft, centerTop + cellSize / 2f, centerLeft + cellSize, centerTop + cellSize / 2f, linePaint)
    }
}

private fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()
