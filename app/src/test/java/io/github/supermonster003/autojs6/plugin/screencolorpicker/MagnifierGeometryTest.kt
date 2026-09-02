package io.github.supermonster003.autojs6.plugin.screencolorpicker

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.cos
import kotlin.math.sin

class MagnifierGeometryTest {
    @Test
    fun metricsUseWholePixelCellsAndRecomputeTheDiscFromThem() {
        val metrics = MagnifierGeometry.metrics(viewSize = 500, band = 37, gridCells = 17)
        assertEquals(249, metrics.viewRadius)
        assertEquals(37, metrics.band)
        // (249 - 37) * 2 = 424 -> 424 / 17 = 24 whole pixels per cell.
        assertEquals(24, metrics.cellSize)
        assertEquals(24 * 17 / 2, metrics.discRadius)
        assertEquals(metrics.discRadius + 37, metrics.outerRadius)
    }

    @Test
    fun metricsRejectViewsTooSmallForTheGrid() {
        assertThrows(IllegalArgumentException::class.java) {
            MagnifierGeometry.metrics(viewSize = 40, band = 18, gridCells = 21)
        }
    }

    @Test
    fun portraitMagnifierSitsAboveTheSampleAndFallsBelowNearTheTop() {
        val above = MagnifierGeometry.magnifierWindowPosition(540, 1200, 500, 1080, 2400)
        assertEquals(290 to 450, above)
        val below = MagnifierGeometry.magnifierWindowPosition(540, 700, 500, 1080, 2400)
        assertEquals(290 to 950, below)
        val clamped = MagnifierGeometry.magnifierWindowPosition(10, 1200, 500, 1080, 2400)
        assertEquals(0, clamped.first)
    }

    @Test
    fun landscapeMagnifierSitsBesideTheSampleAndFlipsNearTheLeftEdge() {
        val leftward = MagnifierGeometry.magnifierWindowPosition(1200, 540, 500, 2400, 1080)
        assertEquals(450 to 290, leftward)
        val rightward = MagnifierGeometry.magnifierWindowPosition(700, 540, 500, 2400, 1080)
        assertEquals(950 to 290, rightward)
    }

    @Test
    fun targetWindowReportsClampOverflowSoTheHoleStaysOnTheSamplePoint() {
        val centered = MagnifierGeometry.targetWindowPlacement(540, 1200, 126, 1080, 2400)
        assertEquals(ClampedWindowPlacement(477, 1137, 0, 0), centered)
        val topLeft = MagnifierGeometry.targetWindowPlacement(0, 0, 126, 1080, 2400)
        assertEquals(ClampedWindowPlacement(0, 0, -63, -63), topLeft)
        val bottomRight = MagnifierGeometry.targetWindowPlacement(1079, 2399, 126, 1080, 2400)
        assertEquals(ClampedWindowPlacement(954, 2274, 62, 62), bottomRight)
    }

    @Test
    fun anglesAreMeasuredClockwiseFromTwelveOClock() {
        assertEquals(0f, MagnifierGeometry.angleFromNoonDegrees(0f, -1f), 0.001f)
        assertEquals(90f, MagnifierGeometry.angleFromNoonDegrees(1f, 0f), 0.001f)
        assertEquals(180f, MagnifierGeometry.angleFromNoonDegrees(0f, 1f), 0.001f)
        assertEquals(270f, MagnifierGeometry.angleFromNoonDegrees(-1f, 0f), 0.001f)
    }

    @Test
    fun ringBandZonesMatchMtLayout() {
        val viewRadius = 249f
        val band = 37f
        val center = 250f
        fun zoneAt(angleFromNoonDegrees: Double, radius: Float = viewRadius): MagnifierHitZone {
            val radians = Math.toRadians(angleFromNoonDegrees)
            return MagnifierGeometry.hitZone(
                (center + radius * sin(radians)).toFloat(),
                (center - radius * cos(radians)).toFloat(),
                center,
                center,
                viewRadius,
                band,
            )
        }

        assertEquals(MagnifierHitZone.COLOR_TEXT, zoneAt(-40.0))
        assertEquals(MagnifierHitZone.COORDINATE_TEXT, zoneAt(40.0))
        assertEquals(MagnifierHitZone.CLOSE, zoneAt(180.0))
        assertEquals(MagnifierHitZone.NONE, zoneAt(0.0))
        assertEquals(MagnifierHitZone.NONE, zoneAt(100.0))
        assertEquals(MagnifierHitZone.NONE, zoneAt(-40.0, radius = viewRadius - band * 1.5f - 2f))
        assertEquals(MagnifierHitZone.NONE, zoneAt(-40.0, radius = viewRadius + band * 1.5f + 2f))
    }

    @Test
    fun luminanceBlendsValueAndInverseSaturation() {
        assertEquals(1.0f, MagnifierGeometry.luminanceFactor(0xFFFFFFFF.toInt()), 0.0001f)
        assertEquals(0.3f, MagnifierGeometry.luminanceFactor(0xFF000000.toInt()), 0.0001f)
        assertEquals(0.7f, MagnifierGeometry.luminanceFactor(0xFFFF0000.toInt()), 0.0001f)
        val gray = 0xC0 / 255f * 0.7f + 0.3f
        assertEquals(gray, MagnifierGeometry.luminanceFactor(0xFFC0C0C0.toInt()), 0.0001f)
    }

    @Test
    fun contrastColorsSwitchAtMtThresholds() {
        assertTrue(MagnifierGeometry.isDarkContent(0.5f))
        assertFalse(MagnifierGeometry.isDarkContent(0.51f))
        assertEquals(-0x1, MagnifierGeometry.contentColorFor(0.7f))
        assertEquals(-0x1000000, MagnifierGeometry.contentColorFor(0.71f))
        assertEquals(-0x1, MagnifierGeometry.markerColorFor(0.7f))
        assertEquals(0xFF444444.toInt(), MagnifierGeometry.markerColorFor(0.8f))
        assertEquals(0xFF999999.toInt(), MagnifierGeometry.markerColorFor(0.95f))
    }

    @Test
    fun fineTuneTruncatesTowardZeroLikeMt() {
        assertEquals(105 to 195, MagnifierGeometry.fineTuneTarget(100, 200, 55f, -55f, 10))
        assertEquals(100 to 200, MagnifierGeometry.fineTuneTarget(100, 200, 9f, -9f, 10))
        assertThrows(IllegalArgumentException::class.java) {
            MagnifierGeometry.fineTuneTarget(0, 0, 1f, 1f, 0)
        }
    }

    @Test
    fun samplePointsClampInsideTheScreen() {
        assertEquals(0 to 2399, MagnifierGeometry.clampSample(-5, 2500, 1080, 2400))
        assertEquals(1079 to 0, MagnifierGeometry.clampSample(5000, -1, 1080, 2400))
    }
}
