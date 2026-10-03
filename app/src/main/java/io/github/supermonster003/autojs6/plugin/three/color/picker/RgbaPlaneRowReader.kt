package io.github.supermonster003.autojs6.plugin.three.color.picker

import java.nio.ByteBuffer

/** Decodes one active RGBA row while skipping any producer row padding. */
internal object RgbaPlaneRowReader {
    fun decodeArgbRow(
        source: ByteBuffer,
        layout: RgbaPlaneLayout,
        row: Int,
        byteScratch: ByteArray,
        argbOutput: IntArray,
    ) {
        require(layout.pixelStride == BYTES_PER_RGBA_PIXEL)
        require(row in 0 until layout.height)
        val activeRowBytes = Math.multiplyExact(layout.width, layout.pixelStride)
        require(byteScratch.size >= activeRowBytes)
        require(argbOutput.size >= layout.width)
        val rowStart = layout.byteOffset(0, row)
        val rowEnd = Math.addExact(rowStart, activeRowBytes)
        require(rowEnd <= source.limit()) { "Captured pixel row is truncated" }

        source.duplicate().apply {
            position(rowStart)
            limit(rowEnd)
            get(byteScratch, 0, activeRowBytes)
        }
        repeat(layout.width) { x ->
            val offset = x * BYTES_PER_RGBA_PIXEL
            val red = byteScratch[offset].toInt() and 0xff
            val green = byteScratch[offset + 1].toInt() and 0xff
            val blue = byteScratch[offset + 2].toInt() and 0xff
            val alpha = byteScratch[offset + 3].toInt() and 0xff
            argbOutput[x] = (alpha shl 24) or (red shl 16) or (green shl 8) or blue
        }
    }

    private const val BYTES_PER_RGBA_PIXEL = 4
}
