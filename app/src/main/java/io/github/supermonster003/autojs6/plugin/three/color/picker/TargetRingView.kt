package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.view.View
import kotlin.math.min

/**
 * Draggable donut marking the sample point. A hole is punched through the translucent
 * disc so the sampled pixel block is never covered by the overlay itself; when the
 * window is clamped at a screen edge the hole is offset by the clamp overflow so it
 * stays on the true sample point.
 */
internal class TargetRingView(context: Context) : View(context) {

    private val twoDp = resources.displayMetrics.density * 2f

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private var centerX = 0f
    private var centerY = 0f
    private var visualRadius = 0f
    private var holeRadius = twoDp
    private var holeOffsetX = 0f
    private var holeOffsetY = 0f

    fun setCaptureRadius(captureRadiusPx: Int) {
        holeRadius = captureRadiusPx + 1 + twoDp
        invalidate()
    }

    fun setHoleOffset(offsetX: Int, offsetY: Int) {
        val newX = offsetX.toFloat()
        val newY = offsetY.toFloat()
        if (newX == holeOffsetX && newY == holeOffsetY) return
        holeOffsetX = newX
        holeOffsetY = newY
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        visualRadius = min(min(w, h) / 2f, RING_DIAMETER_DP / 2f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        try {
            fillPaint.color = COLOR_OUTER_RING
            canvas.drawCircle(centerX, centerY, visualRadius, fillPaint)
            fillPaint.color = COLOR_BODY
            canvas.drawCircle(centerX, centerY, visualRadius - twoDp, fillPaint)

            val holeX = centerX + holeOffsetX
            val holeY = centerY + holeOffsetY
            canvas.drawCircle(holeX, holeY, holeRadius + twoDp, clearPaint)
            fillPaint.color = COLOR_HOLE_EDGE_OUTER
            canvas.drawCircle(holeX, holeY, holeRadius + twoDp, fillPaint)
            canvas.drawCircle(holeX, holeY, holeRadius + twoDp / 2f, clearPaint)
            fillPaint.color = COLOR_HOLE_EDGE_INNER
            canvas.drawCircle(holeX, holeY, holeRadius + twoDp / 2f, fillPaint)
            canvas.drawCircle(holeX, holeY, holeRadius, clearPaint)
        } finally {
            canvas.restoreToCount(layer)
        }
    }

    companion object {
        /** Visual donut diameter; the enclosing window stays at the 48dp touch-target minimum. */
        const val RING_DIAMETER_DP = 36
        private const val COLOR_OUTER_RING = -0x7f444445 // 0x80BBBBBB
        private const val COLOR_BODY = -0x80000000 // 0x80000000
        private const val COLOR_HOLE_EDGE_OUTER = -0x7f777778 // 0x80888888
        private const val COLOR_HOLE_EDGE_INNER = -0x7f111112 // 0x80EEEEEE
    }
}
