package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import java.util.concurrent.atomic.AtomicBoolean

internal data class CaptureDisplaySpec(val width: Int, val height: Int, val densityDpi: Int)

/** Owns exactly one MediaProjection and one VirtualDisplay for their whole lifetime. */
internal class ProjectionSession(
    context: Context,
    private val projection: MediaProjection,
    private val onProjectionStopped: () -> Unit,
) : AutoCloseable {
    private val appContext = context.applicationContext
    private val mainHandler = Handler(Looper.getMainLooper())
    private val displayManager = appContext.getSystemService(DisplayManager::class.java)
    private val closed = AtomicBoolean(false)
    private val closeCompleted = AtomicBoolean(false)
    private val resourcesReleased = AtomicBoolean(false)
    private val stopProjectionRequested = AtomicBoolean(false)
    private val pendingLock = Any()
    private val closeLock = Any()
    private val closeCallbacks = mutableListOf<() -> Unit>()
    private val captureThread = HandlerThread("ScreenColorPickerCapture").apply { start() }
    private val captureHandler = Handler(captureThread.looper)

    private lateinit var displaySpec: CaptureDisplaySpec
    private lateinit var imageReader: ImageReader
    private var pendingCapture: ((Bitmap?) -> Unit)? = null
    private lateinit var virtualDisplay: VirtualDisplay

    private val projectionCallback = object : MediaProjection.Callback() {
        override fun onStop() {
            onProjectionStopped()
        }

        override fun onCapturedContentResize(width: Int, height: Int) {
            if (width > 0 && height > 0) {
                resize(CaptureDisplaySpec(width, height, appContext.resources.configuration.densityDpi))
            }
        }
    }

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit

        override fun onDisplayRemoved(displayId: Int) {
            if (displayId == Display.DEFAULT_DISPLAY) onProjectionStopped()
        }

        override fun onDisplayChanged(displayId: Int) {
            if (displayId == Display.DEFAULT_DISPLAY) resize(currentDisplaySpec(appContext))
        }
    }

    init {
        try {
            displaySpec = currentDisplaySpec(appContext)
            imageReader = newReader(displaySpec)
            projection.registerCallback(projectionCallback, mainHandler)
            imageReader.setOnImageAvailableListener(::onImageAvailable, captureHandler)
            displayManager.registerDisplayListener(displayListener, mainHandler)
            virtualDisplay = checkNotNull(
                projection.createVirtualDisplay(
                    VIRTUAL_DISPLAY_NAME,
                    displaySpec.width,
                    displaySpec.height,
                    displaySpec.densityDpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    imageReader.surface,
                    null,
                    mainHandler,
                ),
            )
        } catch (error: Throwable) {
            runCatching { displayManager.unregisterDisplayListener(displayListener) }
            runCatching { projection.unregisterCallback(projectionCallback) }
            if (this::virtualDisplay.isInitialized) runCatching { virtualDisplay.release() }
            if (this::imageReader.isInitialized) {
                runCatching { imageReader.setOnImageAvailableListener(null, null) }
                runCatching { imageReader.close() }
            }
            runCatching { projection.stop() }
            captureThread.quitSafely()
            throw error
        }
    }

    fun capture(callback: (Bitmap?) -> Unit) {
        if (closed.get()) {
            mainHandler.post { callback(null) }
            return
        }
        val posted = captureHandler.post {
            if (closed.get()) {
                mainHandler.post { callback(null) }
                return@post
            }
            val accepted = synchronized(pendingLock) {
                if (pendingCapture != null) false else {
                    pendingCapture = callback
                    true
                }
            }
            if (!accepted) {
                mainHandler.post { callback(null) }
                return@post
            }
            mainHandler.postDelayed({ expireCapture(callback) }, CAPTURE_TIMEOUT_MILLIS)
        }
        if (!posted) mainHandler.post { callback(null) }
    }

    fun resize(spec: CaptureDisplaySpec) {
        if (closed.get() || spec.width <= 0 || spec.height <= 0) return
        val posted = captureHandler.post {
            if (closed.get() || spec == displaySpec) return@post
            var allocatedReader: ImageReader? = null
            val replacement = try {
                newReader(spec).also { reader ->
                    allocatedReader = reader
                    reader.setOnImageAvailableListener(::onImageAvailable, captureHandler)
                }
            } catch (_: Throwable) {
                allocatedReader?.let { reader -> runCatching { reader.close() } }
                onProjectionStopped()
                return@post
            }
            if (closed.get()) {
                runCatching { replacement.setOnImageAvailableListener(null, null) }
                runCatching { replacement.close() }
                return@post
            }
            try {
                virtualDisplay.resize(spec.width, spec.height, spec.densityDpi)
                virtualDisplay.setSurface(replacement.surface)
            } catch (_: Throwable) {
                runCatching { replacement.setOnImageAvailableListener(null, null) }
                runCatching { replacement.close() }
                onProjectionStopped()
                return@post
            }
            val previous = imageReader
            imageReader = replacement
            displaySpec = spec
            runCatching { previous.setOnImageAvailableListener(null, null) }
            runCatching { previous.close() }
        }
        if (!posted) onProjectionStopped()
    }

    override fun close() {
        close(stopProjection = true)
    }

    fun close(stopProjection: Boolean, onClosed: (() -> Unit)? = null) {
        if (stopProjection) stopProjectionRequested.set(true)
        val startsCleanup = closed.compareAndSet(false, true)
        onClosed?.let(::registerCloseCallback)
        if (!startsCleanup) return
        runCatching { displayManager.unregisterDisplayListener(displayListener) }
        runCatching { projection.unregisterCallback(projectionCallback) }
        val cleanup = Runnable(::releaseResources)
        val posted = runCatching { captureHandler.post(cleanup) }.getOrDefault(false)
        if (!posted) cleanup.run()
    }

    private fun onImageAvailable(reader: ImageReader) {
        val image = runCatching { reader.acquireLatestImage() }.getOrNull() ?: return
        val callback = synchronized(pendingLock) {
            pendingCapture.also { pendingCapture = null }
        }
        if (callback == null) {
            image.close()
            return
        }
        val bitmap = try {
            ImagePlaneBitmapConverter.convert(image)
        } catch (_: RuntimeException) {
            null
        } catch (_: OutOfMemoryError) {
            onProjectionStopped()
            null
        } finally {
            image.close()
        }
        if (!mainHandler.post { callback(bitmap) }) bitmap?.recycle()
    }

    private fun expireCapture(callback: (Bitmap?) -> Unit) {
        val expiration = Runnable {
            val expired = synchronized(pendingLock) {
                if (pendingCapture !== callback) false else {
                    pendingCapture = null
                    true
                }
            }
            if (expired) mainHandler.post { callback(null) }
        }
        if (!captureHandler.post(expiration)) expiration.run()
    }

    private fun registerCloseCallback(callback: () -> Unit) {
        val alreadyCompleted = synchronized(closeLock) {
            if (closeCompleted.get()) {
                true
            } else {
                closeCallbacks += callback
                false
            }
        }
        if (alreadyCompleted) dispatchCloseCallback(callback)
    }

    private fun releaseResources() {
        if (!resourcesReleased.compareAndSet(false, true)) return
        val abandoned = synchronized(pendingLock) {
            pendingCapture.also { pendingCapture = null }
        }
        runCatching { imageReader.setOnImageAvailableListener(null, null) }
        runCatching { virtualDisplay.release() }
        runCatching { imageReader.close() }
        if (stopProjectionRequested.get()) runCatching { projection.stop() }
        captureThread.quitSafely()

        abandoned?.let { callback -> mainHandler.post { callback(null) } }
        val callbacks = synchronized(closeLock) {
            closeCompleted.set(true)
            closeCallbacks.toList().also { closeCallbacks.clear() }
        }
        callbacks.forEach(::dispatchCloseCallback)
    }

    private fun dispatchCloseCallback(callback: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            callback()
        } else {
            mainHandler.post(callback)
        }
    }

    private fun newReader(spec: CaptureDisplaySpec): ImageReader = ImageReader.newInstance(
        spec.width,
        spec.height,
        PixelFormat.RGBA_8888,
        MAX_IMAGES,
    )

    private companion object {
        const val VIRTUAL_DISPLAY_NAME = "ScreenColorPicker"
        const val MAX_IMAGES = 3
        const val CAPTURE_TIMEOUT_MILLIS = 2_000L

        fun currentDisplaySpec(context: Context): CaptureDisplaySpec {
            val density = context.resources.configuration.densityDpi
            if (Build.VERSION.SDK_INT >= 30) {
                val bounds = context.getSystemService(WindowManager::class.java).maximumWindowMetrics.bounds
                return CaptureDisplaySpec(bounds.width(), bounds.height(), density)
            }
            @Suppress("DEPRECATION")
            val display = context.getSystemService(WindowManager::class.java).defaultDisplay
            val metrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            display.getRealMetrics(metrics)
            return CaptureDisplaySpec(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi)
        }
    }
}
