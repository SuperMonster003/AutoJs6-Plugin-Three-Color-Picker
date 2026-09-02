package io.github.supermonster003.autojs6.plugin.screencolorpicker

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.nio.ByteBuffer

class RgbaBlockReaderTest {
    private val layout = RgbaPlaneLayout(width = 4, height = 4, pixelStride = 4, rowStride = 20)

    /** Pixel (x, y) has red = 0x10 * x + y, fixed green/blue, and a NON-opaque source alpha. */
    private fun buffer(): ByteBuffer {
        val bytes = ByteArray((layout.height - 1) * layout.rowStride + layout.width * layout.pixelStride)
        for (y in 0 until layout.height) {
            for (x in 0 until layout.width) {
                val offset = layout.byteOffset(x, y)
                bytes[offset] = (0x10 * x + y).toByte()
                bytes[offset + 1] = 0xA0.toByte()
                bytes[offset + 2] = 0xB0.toByte()
                bytes[offset + 3] = 0x00
            }
        }
        return ByteBuffer.wrap(bytes)
    }

    private fun pixel(x: Int, y: Int): Int = 0xFF000000.toInt() or (0x10 * x + y shl 16) or 0xA0B0

    @Test
    fun readsTheBlockRowMajorAndForcesOpaquePixels() {
        val output = IntArray(9)
        RgbaBlockReader.readArgbBlock(buffer(), layout, centerX = 1, centerY = 2, radius = 1, argbOutput = output)
        assertArrayEquals(
            intArrayOf(
                pixel(0, 1), pixel(1, 1), pixel(2, 1),
                pixel(0, 2), pixel(1, 2), pixel(2, 2),
                pixel(0, 3), pixel(1, 3), pixel(2, 3),
            ),
            output,
        )
    }

    @Test
    fun pixelsOutsideTheFrameBecomeTransparentZero() {
        val output = IntArray(9)
        RgbaBlockReader.readArgbBlock(buffer(), layout, centerX = 0, centerY = 0, radius = 1, argbOutput = output)
        assertArrayEquals(
            intArrayOf(
                0, 0, 0,
                0, pixel(0, 0), pixel(1, 0),
                0, pixel(0, 1), pixel(1, 1),
            ),
            output,
        )
    }

    @Test
    fun rejectsTruncatedBuffersAndOffScreenCenters() {
        assertThrows(IllegalArgumentException::class.java) {
            RgbaBlockReader.readArgbBlock(
                ByteBuffer.allocate(layout.minimumBufferByteCount - 1),
                layout,
                centerX = 3,
                centerY = 3,
                radius = 0,
                argbOutput = IntArray(1),
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            RgbaBlockReader.readArgbBlock(buffer(), layout, centerX = 4, centerY = 0, radius = 0, argbOutput = IntArray(1))
        }
        assertThrows(IllegalArgumentException::class.java) {
            RgbaBlockReader.readArgbBlock(buffer(), layout, centerX = 0, centerY = 0, radius = 1, argbOutput = IntArray(8))
        }
    }
}
