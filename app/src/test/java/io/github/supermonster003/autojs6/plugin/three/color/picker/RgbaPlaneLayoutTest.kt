package io.github.supermonster003.autojs6.plugin.three.color.picker

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class RgbaPlaneLayoutTest {
    @Test
    fun rowPaddingProducesPaddedBitmapWidthAndOffsets() {
        val layout = RgbaPlaneLayout(width = 3, height = 2, pixelStride = 4, rowStride = 16)
        assertEquals(4, layout.paddedWidth)
        assertEquals(32, layout.bitmapByteCount)
        assertEquals(28, layout.minimumBufferByteCount)
        assertEquals(24, layout.byteOffset(2, 1))
    }

    @Test
    fun tightlyPackedPlaneHasNoPadding() {
        val layout = RgbaPlaneLayout(width = 10, height = 5, pixelStride = 4, rowStride = 40)
        assertEquals(10, layout.paddedWidth)
        assertEquals(200, layout.bitmapByteCount)
        assertEquals(200, layout.minimumBufferByteCount)
    }

    @Test
    fun invalidPlaneMetadataAndCoordinatesAreRejected() {
        assertThrows(IllegalArgumentException::class.java) { RgbaPlaneLayout(0, 1, 4, 4) }
        assertThrows(IllegalArgumentException::class.java) { RgbaPlaneLayout(2, 1, 4, 7) }
        assertThrows(IllegalArgumentException::class.java) { RgbaPlaneLayout(1, 1, 3, 4) }
        val layout = RgbaPlaneLayout(1, 1, 4, 4)
        assertThrows(IllegalArgumentException::class.java) { layout.byteOffset(1, 0) }
    }
}
