package io.github.supermonster003.autojs6.plugin.three.color.picker

import kotlin.math.floor
import kotlin.math.min

internal data class FloatBounds(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
}

internal data class PixelCoordinate(val x: Int, val y: Int)

internal object CoordinateMapper {
    fun fitCenterBounds(
        viewportWidth: Int,
        viewportHeight: Int,
        imageWidth: Int,
        imageHeight: Int,
    ): FloatBounds {
        require(viewportWidth > 0 && viewportHeight > 0)
        require(imageWidth > 0 && imageHeight > 0)
        val scale = min(viewportWidth.toFloat() / imageWidth, viewportHeight.toFloat() / imageHeight)
        val width = imageWidth * scale
        val height = imageHeight * scale
        val left = (viewportWidth - width) / 2f
        val top = (viewportHeight - height) / 2f
        return FloatBounds(left, top, left + width, top + height)
    }

    fun mapToPixel(
        x: Float,
        y: Float,
        content: FloatBounds,
        imageWidth: Int,
        imageHeight: Int,
        clamp: Boolean = true,
    ): PixelCoordinate? {
        require(imageWidth > 0 && imageHeight > 0)
        require(content.width > 0f && content.height > 0f)
        if (!clamp && (x < content.left || x >= content.right || y < content.top || y >= content.bottom)) {
            return null
        }
        val boundedX = x.coerceIn(content.left, Math.nextDown(content.right.toDouble()).toFloat())
        val boundedY = y.coerceIn(content.top, Math.nextDown(content.bottom.toDouble()).toFloat())
        val pixelX = floor((boundedX - content.left) * imageWidth / content.width).toInt()
            .coerceIn(0, imageWidth - 1)
        val pixelY = floor((boundedY - content.top) * imageHeight / content.height).toInt()
            .coerceIn(0, imageHeight - 1)
        return PixelCoordinate(pixelX, pixelY)
    }

    /** Maps a source pixel into a buffer rotated clockwise by a right angle. */
    fun rotatePixel(
        pixel: PixelCoordinate,
        sourceWidth: Int,
        sourceHeight: Int,
        clockwiseDegrees: Int,
    ): PixelCoordinate {
        require(sourceWidth > 0 && sourceHeight > 0)
        require(pixel.x in 0 until sourceWidth && pixel.y in 0 until sourceHeight)
        return when (((clockwiseDegrees % 360) + 360) % 360) {
            0 -> pixel
            90 -> PixelCoordinate(sourceHeight - 1 - pixel.y, pixel.x)
            180 -> PixelCoordinate(sourceWidth - 1 - pixel.x, sourceHeight - 1 - pixel.y)
            270 -> PixelCoordinate(pixel.y, sourceWidth - 1 - pixel.x)
            else -> throw IllegalArgumentException("Rotation must be a multiple of 90 degrees")
        }
    }
}
