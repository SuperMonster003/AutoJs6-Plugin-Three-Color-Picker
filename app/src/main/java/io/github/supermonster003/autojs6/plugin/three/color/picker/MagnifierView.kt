package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import androidx.core.graphics.withSave
import kotlin.math.min

/**
 * Circular magnifier disc showing the sampled pixel block: pixel grid with etched grid
 * lines, ring band tinted with the live center color, curved color/coordinate texts on
 * the band, a close cross at the bottom, and a center-cell marker. Colors and layout
 * mirror MT Manager's tool; text and marker contrast adapt to the sampled color.
 */
@SuppressLint("ViewConstructor")
internal class MagnifierView(
    context: Context,
    private val gridCells: Int,
    private val showGrid: Boolean,
    hexFormat: Boolean,
) : View(context) {

    var hexFormat: Boolean = hexFormat
        set(value) {
            if (field == value) return
            field = value
            invalidate()
        }

    var sampleX: Int = 0
        private set
    var sampleY: Int = 0
        private set

    val centerColor: Int
        get() = pixels?.let { it[gridCells / 2 * gridCells + gridCells / 2] } ?: 0

    private var pixels: IntArray? = null

    private val basePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val cellPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 1f
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    }
    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 1f
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        typeface = Typeface.MONOSPACE
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics)
    }
    private val textArc = Path()

    /** Ring band thickness: one line height of the 12sp monospace text. */
    private val band: Int
    private val negativeDescent: Int

    private var centerX = 0f
    private var centerY = 0f
    private var viewRadius = 0
    private var metrics: MagnifierMetrics? = null

    init {
        val fontMetrics = textPaint.fontMetrics
        band = (fontMetrics.bottom - fontMetrics.top + 0.5f).toInt()
        negativeDescent = (-fontMetrics.descent).toInt()
    }

    fun updateSample(pixels: IntArray, sampleX: Int, sampleY: Int) {
        require(pixels.size == gridCells * gridCells)
        this.pixels = pixels
        this.sampleX = sampleX
        this.sampleY = sampleY
        invalidate()
    }

    val hasSample: Boolean get() = pixels != null

    fun hitZone(x: Float, y: Float): MagnifierHitZone =
        MagnifierGeometry.hitZone(x, y, centerX, centerY, viewRadius.toFloat(), band.toFloat())

    fun colorDisplayText(): String = ColorFormatter.colorText(centerColor, hexFormat)

    fun colorCopyText(numericOnly: Boolean): String =
        ColorFormatter.colorCopyText(centerColor, hexFormat, numericOnly)

    fun coordinateText(): String = ColorFormatter.coordinateText(sampleX, sampleY)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        viewRadius = min(w, h) / 2 - 1
        metrics = if (w > 0 && h > 0) MagnifierGeometry.metrics(min(w, h), band, gridCells) else null
    }

    override fun onDraw(canvas: Canvas) {
        val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        try {
            drawContent(canvas)
        } finally {
            canvas.restoreToCount(layer)
        }
    }

    private fun drawContent(canvas: Canvas) {
        basePaint.style = Paint.Style.FILL
        basePaint.strokeWidth = 1f
        val pixels = this.pixels
        val metrics = this.metrics
        if (pixels == null || metrics == null) {
            // Waiting for the first frame: a plain white ring of band thickness.
            basePaint.color = -0x1
            canvas.drawCircle(centerX, centerY, viewRadius.toFloat(), basePaint)
            canvas.drawCircle(centerX, centerY, (viewRadius - band).toFloat(), clearPaint)
            return
        }

        val cell = metrics.cellSize
        val discRadius = metrics.discRadius
        val outerRadius = metrics.outerRadius
        val gridSpan = ((viewRadius - band) * 2).toFloat()

        basePaint.color = -0x1000000
        canvas.drawCircle(centerX, centerY, discRadius.toFloat(), basePaint)

        val centerColor = pixels[gridCells / 2 * gridCells + gridCells / 2]
        val luminance = MagnifierGeometry.luminanceFactor(centerColor)
        val darkContent = MagnifierGeometry.isDarkContent(luminance)

        canvas.withSave {
            translate(centerX - discRadius, centerY - discRadius)
            for (row in 0 until gridCells) {
                for (col in 0 until gridCells) {
                    val pixel = pixels[row * gridCells + col]
                    cellPaint.color =
                        if (pixel and -0x1000000 != -0x1000000) MagnifierGeometry.COLOR_OUT_OF_BOUNDS else pixel
                    // Cells overlap by 1px to avoid hairline seams between them.
                    drawRect(
                        (col * cell).toFloat(),
                        (row * cell).toFloat(),
                        (col * cell + cell + 1).toFloat(),
                        (row * cell + cell + 1).toFloat(),
                        cellPaint,
                    )
                }
            }
            if (showGrid) {
                cellPaint.color = MagnifierGeometry.COLOR_GRID_LIGHT
                for (i in 1 until gridCells) {
                    val position = (i * cell).toFloat()
                    drawLine(0f, position, gridSpan, position, cellPaint)
                    drawLine(position, 0f, position, gridSpan, cellPaint)
                }
                cellPaint.color = MagnifierGeometry.COLOR_GRID_DARK
                for (i in 1 until gridCells) {
                    val position = (i * cell + 1).toFloat()
                    drawLine(0f, position, gridSpan, position, cellPaint)
                    drawLine(position, 0f, position, gridSpan, cellPaint)
                }
            }
            basePaint.color = MagnifierGeometry.markerColorFor(luminance)
            basePaint.style = Paint.Style.STROKE
            basePaint.strokeWidth = 3f
            val markerStart = (gridCells / 2 * cell).toFloat()
            drawRect(markerStart, markerStart, markerStart + cell, markerStart + cell, basePaint)
        }

        basePaint.strokeWidth = band.toFloat()
        basePaint.color = centerColor
        canvas.drawCircle(centerX, centerY, outerRadius - band / 2f - 1f, basePaint)

        basePaint.strokeWidth = 1f
        basePaint.color = if (darkContent) MagnifierGeometry.COLOR_GRID_LIGHT else MagnifierGeometry.COLOR_GRID_DARK
        canvas.drawCircle(centerX, centerY, discRadius.toFloat(), basePaint)
        canvas.drawCircle(centerX, centerY, outerRadius.toFloat(), basePaint)
        basePaint.color = if (darkContent) MagnifierGeometry.COLOR_GRID_DARK else MagnifierGeometry.COLOR_GRID_LIGHT
        canvas.drawCircle(centerX, centerY, (discRadius + 1).toFloat(), basePaint)
        canvas.drawCircle(centerX, centerY, (outerRadius - 1).toFloat(), basePaint)

        textPaint.color = MagnifierGeometry.contentColorFor(luminance)
        val arcRadius = (discRadius - negativeDescent).toFloat()
        textArc.reset()
        textArc.addArc(
            centerX - arcRadius,
            centerY - arcRadius,
            centerX + arcRadius,
            centerY + arcRadius,
            if (hexFormat) MagnifierGeometry.COLOR_TEXT_ARC_START_HEX else MagnifierGeometry.COLOR_TEXT_ARC_START_RGB,
            MagnifierGeometry.TEXT_ARC_SWEEP,
        )
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawTextOnPath(colorDisplayText(), textArc, 0f, 0f, textPaint)
        textArc.reset()
        textArc.addArc(
            centerX - arcRadius,
            centerY - arcRadius,
            centerX + arcRadius,
            centerY + arcRadius,
            MagnifierGeometry.COORDINATE_ARC_START,
            MagnifierGeometry.TEXT_ARC_SWEEP,
        )
        textPaint.textAlign = Paint.Align.LEFT
        canvas.drawTextOnPath(coordinateText(), textArc, 0f, 0f, textPaint)

        canvas.withSave {
            translate(centerX, centerY + outerRadius - band / 2f)
            rotate(45f)
            val crossHalfLength = band / 2f * 0.7f
            val crossHalfThickness = crossHalfLength * 0.11f
            drawRect(-crossHalfLength, -crossHalfThickness, crossHalfLength, crossHalfThickness, textPaint)
            drawRect(-crossHalfThickness, -crossHalfLength, crossHalfThickness, crossHalfLength, textPaint)
        }
    }
}
