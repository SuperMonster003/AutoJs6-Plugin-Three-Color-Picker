package io.github.supermonster003.autojs6.plugin.screencolorpicker

internal data class RgbaPlaneLayout(
    val width: Int,
    val height: Int,
    val pixelStride: Int,
    val rowStride: Int,
) {
    init {
        require(width > 0 && height > 0)
        require(pixelStride > 0)
        require(rowStride >= width * pixelStride)
        require(rowStride % pixelStride == 0)
    }

    val paddedWidth: Int = rowStride / pixelStride
    val bitmapByteCount: Int = Math.multiplyExact(Math.multiplyExact(paddedWidth, height), pixelStride)
    val minimumBufferByteCount: Int = Math.addExact(
        Math.multiplyExact(height - 1, rowStride),
        Math.multiplyExact(width, pixelStride),
    )

    fun byteOffset(x: Int, y: Int): Int {
        require(x in 0 until width && y in 0 until height)
        return Math.addExact(Math.multiplyExact(y, rowStride), Math.multiplyExact(x, pixelStride))
    }
}
