package io.github.supermonster003.autojs6.plugin.three.color.picker

import java.util.Locale
import kotlin.math.max
import kotlin.math.min

internal data class HslColor(val hue: Double, val saturation: Double, val lightness: Double)

internal object ColorFormatter {
    fun hex(argb: Int): String = String.format(
        Locale.US,
        "#%02X%02X%02X",
        red(argb),
        green(argb),
        blue(argb),
    )

    fun argbHex(argb: Int): String = String.format(
        Locale.US,
        "#%02X%02X%02X%02X",
        alpha(argb),
        red(argb),
        green(argb),
        blue(argb),
    )

    fun rgb(argb: Int): String = String.format(
        Locale.US,
        "rgb(%d, %d, %d)",
        red(argb),
        green(argb),
        blue(argb),
    )

    fun hslCss(argb: Int): String {
        val hsl = hsl(argb)
        return String.format(
            Locale.US,
            "hsl(%.1f, %.1f%%, %.1f%%)",
            hsl.hue,
            hsl.saturation,
            hsl.lightness,
        )
    }

    fun hsl(argb: Int): HslColor {
        val r = red(argb) / 255.0
        val g = green(argb) / 255.0
        val b = blue(argb) / 255.0
        val maximum = max(r, max(g, b))
        val minimum = min(r, min(g, b))
        val delta = maximum - minimum
        val lightness = (maximum + minimum) / 2.0
        if (delta == 0.0) return HslColor(0.0, 0.0, lightness * 100.0)

        val saturation = delta / (1.0 - kotlin.math.abs(2.0 * lightness - 1.0))
        val hueSection = when (maximum) {
            r -> ((g - b) / delta) % 6.0
            g -> (b - r) / delta + 2.0
            else -> (r - g) / delta + 4.0
        }
        val hue = (hueSection * 60.0 + 360.0) % 360.0
        return HslColor(hue, saturation * 100.0, lightness * 100.0)
    }

    fun oneDecimal(value: Double): String = String.format(Locale.US, "%.1f", value)

    fun compactRgb(argb: Int): String = String.format(
        Locale.US,
        "R%d,G%d,B%d",
        red(argb),
        green(argb),
        blue(argb),
    )

    fun colorText(argb: Int, hexFormat: Boolean): String =
        if (hexFormat) hex(argb) else compactRgb(argb)

    /** Text placed on the clipboard; numeric-only strips the '#' prefix or the R/G/B letters. */
    fun colorCopyText(argb: Int, hexFormat: Boolean, numericOnly: Boolean): String {
        val text = colorText(argb, hexFormat)
        if (!numericOnly) return text
        return if (text.startsWith("#")) text.substring(1) else text.replace(CHANNEL_LETTERS, "")
    }

    /** Coordinates are displayed and copied 1-based, comma-separated without spaces. */
    fun coordinateText(x: Int, y: Int): String = "${x + 1},${y + 1}"

    private val CHANNEL_LETTERS = Regex("[RGB]")

    fun alpha(argb: Int): Int = argb ushr 24 and 0xFF
    fun red(argb: Int): Int = argb ushr 16 and 0xFF
    fun green(argb: Int): Int = argb ushr 8 and 0xFF
    fun blue(argb: Int): Int = argb and 0xFF
}
