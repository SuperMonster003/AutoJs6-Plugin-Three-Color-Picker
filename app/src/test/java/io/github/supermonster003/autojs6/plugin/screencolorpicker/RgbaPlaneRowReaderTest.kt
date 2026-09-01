package io.github.supermonster003.autojs6.plugin.screencolorpicker

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.nio.ByteBuffer

class RgbaPlaneRowReaderTest {
    @Test
    fun decodesArgbAndSkipsPaddingWithoutRequiringFinalPadding() {
        val layout = RgbaPlaneLayout(width = 2, height = 2, pixelStride = 4, rowStride = 12)
        val source = ByteBuffer.wrap(
            byteArrayOf(
                0x11, 0x22, 0x33, 0x44,
                0x55, 0x66, 0x77, 0x7f,
                0x7a, 0x7b, 0x7c, 0x7d,
                0x80.toByte(), 0x90.toByte(), 0xa0.toByte(), 0xb0.toByte(),
                0xc0.toByte(), 0xd0.toByte(), 0xe0.toByte(), 0xf0.toByte(),
            ),
        )
        val output = IntArray(layout.width)

        RgbaPlaneRowReader.decodeArgbRow(
            source = source,
            layout = layout,
            row = 1,
            byteScratch = ByteArray(layout.width * layout.pixelStride),
            argbOutput = output,
        )

        assertArrayEquals(intArrayOf(0xb08090a0.toInt(), 0xf0c0d0e0.toInt()), output)
    }

    @Test
    fun rejectsTruncatedRowsAndUndersizedScratchBuffers() {
        val layout = RgbaPlaneLayout(width = 2, height = 2, pixelStride = 4, rowStride = 12)
        val output = IntArray(layout.width)
        assertThrows(IllegalArgumentException::class.java) {
            RgbaPlaneRowReader.decodeArgbRow(
                ByteBuffer.allocate(layout.minimumBufferByteCount - 1),
                layout,
                1,
                ByteArray(layout.width * layout.pixelStride),
                output,
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            RgbaPlaneRowReader.decodeArgbRow(
                ByteBuffer.allocate(layout.minimumBufferByteCount),
                layout,
                0,
                ByteArray(layout.width * layout.pixelStride - 1),
                output,
            )
        }
    }
}
