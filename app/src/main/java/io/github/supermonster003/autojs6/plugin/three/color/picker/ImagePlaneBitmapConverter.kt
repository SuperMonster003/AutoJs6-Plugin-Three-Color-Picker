package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.graphics.Bitmap
import android.media.Image
import java.nio.ByteBuffer

internal object ImagePlaneBitmapConverter {
    fun convert(image: Image): Bitmap {
        val plane = image.planes.firstOrNull() ?: error("Captured image has no pixel plane")
        val layout = RgbaPlaneLayout(
            width = image.width,
            height = image.height,
            pixelStride = plane.pixelStride,
            rowStride = plane.rowStride,
        )
        return convert(plane.buffer, layout)
    }

    internal fun convert(buffer: ByteBuffer, layout: RgbaPlaneLayout): Bitmap {
        require(layout.pixelStride == BYTES_PER_RGBA_PIXEL) {
            "Unexpected RGBA pixel stride ${layout.pixelStride}"
        }
        val source = buffer.duplicate().apply { position(0) }
        require(source.remaining() >= layout.minimumBufferByteCount) { "Captured pixel buffer is truncated" }
        val bitmap = Bitmap.createBitmap(layout.width, layout.height, Bitmap.Config.ARGB_8888)
        val byteScratch = ByteArray(Math.multiplyExact(layout.width, layout.pixelStride))
        val argbRow = IntArray(layout.width)
        try {
            repeat(layout.height) { row ->
                RgbaPlaneRowReader.decodeArgbRow(source, layout, row, byteScratch, argbRow)
                bitmap.setPixels(argbRow, 0, layout.width, 0, row, layout.width, 1)
            }
            return bitmap
        } catch (error: Throwable) {
            if (!bitmap.isRecycled) bitmap.recycle()
            throw error
        }
    }

    private const val BYTES_PER_RGBA_PIXEL = 4
}
