package io.github.supermonster003.autojs6.plugin.screencolorpicker

import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

/** Disc measurements derived from the view size so the pixel grid uses whole-pixel cells. */
internal data class MagnifierMetrics(
    val viewRadius: Int,
    val band: Int,
    val cellSize: Int,
    val discRadius: Int,
    val outerRadius: Int,
)

/** Window origin plus how far the window had to be pushed back on screen. */
internal data class ClampedWindowPlacement(
    val x: Int,
    val y: Int,
    val overflowX: Int,
    val overflowY: Int,
)

internal enum class MagnifierHitZone { COLOR_TEXT, COORDINATE_TEXT, CLOSE, NONE }

/**
 * Pure geometry for the live magnifier replicated from MT Manager's tool: integer cell
 * sizing, ring-band hit zones measured clockwise from 12 o'clock, magnifier flip
 * placement, target-window edge clamping, fine-tune scaling, and luminance adaption.
 */
internal object MagnifierGeometry {
    const val COLOR_TEXT_ARC_START_HEX = 165f
    const val COLOR_TEXT_ARC_START_RGB = 180f
    const val COORDINATE_ARC_START = 285f
    const val TEXT_ARC_SWEEP = 90f

    fun metrics(viewSize: Int, band: Int, gridCells: Int): MagnifierMetrics {
        require(viewSize > 0 && band > 0 && gridCells > 0)
        val viewRadius = viewSize / 2 - 1
        val innerDiameter = (viewRadius - band) * 2
        require(innerDiameter >= gridCells) { "Magnifier view is too small for the grid" }
        val cellSize = innerDiameter / gridCells
        val discRadius = cellSize * gridCells / 2
        return MagnifierMetrics(
            viewRadius = viewRadius,
            band = band,
            cellSize = cellSize,
            discRadius = discRadius,
            outerRadius = discRadius + band,
        )
    }

    /**
     * Positions the magnifier window relative to the sample point: above it in portrait
     * (below when there is no room), beside it in landscape, then clamped on screen.
     */
    fun magnifierWindowPosition(
        sampleX: Int,
        sampleY: Int,
        windowSize: Int,
        screenWidth: Int,
        screenHeight: Int,
    ): Pair<Int, Int> {
        require(windowSize > 0 && screenWidth > 0 && screenHeight > 0)
        val x: Int
        val y: Int
        if (screenWidth < screenHeight) {
            x = sampleX - windowSize / 2
            val above = (sampleY - 1.5f * windowSize).toInt()
            y = if (above <= 0) (sampleY + 0.5f * windowSize).toInt() else above
        } else {
            y = sampleY - windowSize / 2
            val leftward = (sampleX - 1.5f * windowSize).toInt()
            x = if (leftward <= 0) (sampleX + 0.5f * windowSize).toInt() else leftward
        }
        return x.coerceIn(0, max(0, screenWidth - windowSize)) to
            y.coerceIn(0, max(0, screenHeight - windowSize))
    }

    /** Clamps the target window on screen and reports the overflow used to keep the hole on the sample point. */
    fun targetWindowPlacement(
        sampleX: Int,
        sampleY: Int,
        windowSize: Int,
        screenWidth: Int,
        screenHeight: Int,
    ): ClampedWindowPlacement {
        require(windowSize > 0 && screenWidth > 0 && screenHeight > 0)
        val rawX = sampleX - windowSize / 2
        val rawY = sampleY - windowSize / 2
        val clampedX = rawX.coerceIn(0, max(0, screenWidth - windowSize))
        val clampedY = rawY.coerceIn(0, max(0, screenHeight - windowSize))
        return ClampedWindowPlacement(
            x = clampedX,
            y = clampedY,
            overflowX = rawX - clampedX,
            overflowY = rawY - clampedY,
        )
    }

    /**
     * Degrees measured clockwise from 12 o'clock in the range (-90, 270], matching the
     * arc-text layout: color text [-70, -5], coordinates [5, 70], close icon [160, 200].
     */
    fun angleFromNoonDegrees(deltaX: Float, deltaY: Float): Float =
        Math.toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble())).toFloat() + 90f

    fun hitZone(x: Float, y: Float, centerX: Float, centerY: Float, viewRadius: Float, band: Float): MagnifierHitZone {
        val deltaX = x - centerX
        val deltaY = y - centerY
        val distance = hypot(deltaX, deltaY)
        val tolerance = band * 1.5f
        if (distance < viewRadius - tolerance || distance > viewRadius + tolerance) return MagnifierHitZone.NONE
        val angle = angleFromNoonDegrees(deltaX, deltaY)
        return when {
            angle in -70f..-5f -> MagnifierHitZone.COLOR_TEXT
            angle in 5f..70f -> MagnifierHitZone.COORDINATE_TEXT
            angle in 160f..200f -> MagnifierHitZone.CLOSE
            else -> MagnifierHitZone.NONE
        }
    }

    /** Perceived-lightness factor `V * 0.7 + (1 - S) * 0.3` used for adaptive contrast. */
    fun luminanceFactor(argb: Int): Float {
        val red = argb ushr 16 and 0xFF
        val green = argb ushr 8 and 0xFF
        val blue = argb and 0xFF
        val maximum = max(red, max(green, blue))
        val minimum = min(red, min(green, blue))
        val value = maximum / 255f
        val saturation = if (maximum == 0) 0f else (maximum - minimum).toFloat() / maximum
        return value * 0.7f + (1f - saturation) * 0.3f
    }

    fun isDarkContent(luminanceFactor: Float): Boolean = luminanceFactor <= 0.5f

    /** White on dark content, black otherwise; used for arc text and the close icon. */
    fun contentColorFor(luminanceFactor: Float): Int =
        if (luminanceFactor <= 0.7f) COLOR_WHITE else COLOR_BLACK

    /** Center-cell marker color with a mid-gray fallback on very light content. */
    fun markerColorFor(luminanceFactor: Float): Int = when {
        luminanceFactor <= 0.7f -> COLOR_WHITE
        luminanceFactor < 0.9f -> COLOR_DARK_GRAY
        else -> COLOR_MID_GRAY
    }

    fun fineTuneTarget(startX: Int, startY: Int, totalDeltaX: Float, totalDeltaY: Float, divisor: Int): Pair<Int, Int> {
        require(divisor > 0)
        return startX + (totalDeltaX / divisor).toInt() to startY + (totalDeltaY / divisor).toInt()
    }

    fun clampSample(x: Int, y: Int, screenWidth: Int, screenHeight: Int): Pair<Int, Int> {
        require(screenWidth > 0 && screenHeight > 0)
        return x.coerceIn(0, screenWidth - 1) to y.coerceIn(0, screenHeight - 1)
    }

    private const val COLOR_WHITE = -0x1
    private const val COLOR_BLACK = -0x1000000
    private const val COLOR_DARK_GRAY = -0xbbbbbc
    private const val COLOR_MID_GRAY = -0x666667
    const val COLOR_GRID_LIGHT = -0xf0f10
    const val COLOR_GRID_DARK = -0x5f5f60
    const val COLOR_OUT_OF_BOUNDS = -0x3f3f40
}
