package io.github.supermonster003.autojs6.plugin.three.color.picker

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class CoordinateMapperTest {
    @Test
    fun fitCenterAndMapAccountForLetterboxOffsets() {
        val bounds = CoordinateMapper.fitCenterBounds(1_000, 1_000, 2_000, 1_000)
        assertEquals(FloatBounds(0f, 250f, 1_000f, 750f), bounds)
        assertEquals(PixelCoordinate(0, 0), CoordinateMapper.mapToPixel(0f, 250f, bounds, 2_000, 1_000))
        assertEquals(PixelCoordinate(1_999, 999), CoordinateMapper.mapToPixel(1_000f, 750f, bounds, 2_000, 1_000))
        assertEquals(PixelCoordinate(1_000, 500), CoordinateMapper.mapToPixel(500f, 500f, bounds, 2_000, 1_000))
    }

    @Test
    fun mappingCanRejectOrClampOutsideCoordinates() {
        val bounds = FloatBounds(10f, 20f, 110f, 220f)
        assertNull(CoordinateMapper.mapToPixel(0f, 50f, bounds, 10, 20, clamp = false))
        assertEquals(PixelCoordinate(0, 19), CoordinateMapper.mapToPixel(-1f, 999f, bounds, 10, 20))
    }

    @Test
    fun rotationMappingCoversEveryDisplayRotation() {
        val pixel = PixelCoordinate(1, 2)
        assertEquals(PixelCoordinate(1, 2), CoordinateMapper.rotatePixel(pixel, 4, 3, 0))
        assertEquals(PixelCoordinate(0, 1), CoordinateMapper.rotatePixel(pixel, 4, 3, 90))
        assertEquals(PixelCoordinate(2, 0), CoordinateMapper.rotatePixel(pixel, 4, 3, 180))
        assertEquals(PixelCoordinate(2, 2), CoordinateMapper.rotatePixel(pixel, 4, 3, 270))
        assertEquals(PixelCoordinate(2, 2), CoordinateMapper.rotatePixel(pixel, 4, 3, -90))
        assertThrows(IllegalArgumentException::class.java) {
            CoordinateMapper.rotatePixel(pixel, 4, 3, 45)
        }
    }
}
