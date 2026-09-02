package io.github.supermonster003.autojs6.plugin.screencolorpicker

import java.nio.ByteBuffer
import java.util.Arrays

/**
 * Reads the square pixel block around a sample point straight from a captured RGBA
 * plane, touching only the needed bytes. Pixels outside the frame are written as 0
 * (fully transparent), which the magnifier renders as its out-of-bounds gray.
 */
internal object RgbaBlockReader {
    fun readArgbBlock(
        source: ByteBuffer,
        layout: RgbaPlaneLayout,
        centerX: Int,
        centerY: Int,
        radius: Int,
        argbOutput: IntArray,
    ) {
        require(layout.pixelStride == BYTES_PER_RGBA_PIXEL)
        require(radius >= 0)
        val side = radius * 2 + 1
        require(argbOutput.size >= side * side)
        require(centerX in 0 until layout.width && centerY in 0 until layout.height)

        for (row in 0 until side) {
            val sourceY = centerY - radius + row
            val outputBase = row * side
            if (sourceY < 0 || sourceY >= layout.height) {
                Arrays.fill(argbOutput, outputBase, outputBase + side, 0)
                continue
            }
            val firstX = (centerX - radius).coerceAtLeast(0)
            val lastX = (centerX + radius).coerceAtMost(layout.width - 1)
            require(layout.byteOffset(lastX, sourceY) + BYTES_PER_RGBA_PIXEL <= source.limit()) {
                "Captured pixel row is truncated"
            }
            for (col in 0 until side) {
                val sourceX = centerX - radius + col
                argbOutput[outputBase + col] = if (sourceX < firstX || sourceX > lastX) 0 else {
                    val offset = layout.byteOffset(sourceX, sourceY)
                    val red = source.get(offset).toInt() and 0xff
                    val green = source.get(offset + 1).toInt() and 0xff
                    val blue = source.get(offset + 2).toInt() and 0xff
                    OPAQUE_ALPHA or (red shl 16) or (green shl 8) or blue
                }
            }
        }
    }

    private const val BYTES_PER_RGBA_PIXEL = 4
    private const val OPAQUE_ALPHA = 0xFF shl 24
}
