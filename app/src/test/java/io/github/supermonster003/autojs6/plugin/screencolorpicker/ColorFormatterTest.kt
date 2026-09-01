package io.github.supermonster003.autojs6.plugin.screencolorpicker

import org.junit.Assert.assertEquals
import org.junit.Test

class ColorFormatterTest {
    @Test
    fun hexIgnoresAlphaWhileArgbPreservesIt() {
        val color = 0x7F12ABEF
        assertEquals("#12ABEF", ColorFormatter.hex(color))
        assertEquals("#7F12ABEF", ColorFormatter.argbHex(color))
        assertEquals(0x7F, ColorFormatter.alpha(color))
    }

    @Test
    fun primaryColorsHaveCanonicalHslValues() {
        assertHsl(0.0, 100.0, 50.0, ColorFormatter.hsl(0xFFFF0000.toInt()))
        assertHsl(120.0, 100.0, 50.0, ColorFormatter.hsl(0xFF00FF00.toInt()))
        assertHsl(240.0, 100.0, 50.0, ColorFormatter.hsl(0xFF0000FF.toInt()))
        assertHsl(0.0, 0.0, 0.0, ColorFormatter.hsl(0xFF000000.toInt()))
        assertHsl(0.0, 0.0, 100.0, ColorFormatter.hsl(0xFFFFFFFF.toInt()))
    }

    @Test
    fun decimalFormattingIsLocaleStable() {
        assertEquals("12.3", ColorFormatter.oneDecimal(12.34))
    }

    @Test
    fun copyFormatsAreStableAndReadyForTheClipboard() {
        val color = 0xFF12ABEF.toInt()
        assertEquals("rgb(18, 171, 239)", ColorFormatter.rgb(color))
        assertEquals("hsl(198.5, 87.4%, 50.4%)", ColorFormatter.hslCss(color))
    }

    private fun assertHsl(h: Double, s: Double, l: Double, actual: HslColor) {
        assertEquals(h, actual.hue, 0.0001)
        assertEquals(s, actual.saturation, 0.0001)
        assertEquals(l, actual.lightness, 0.0001)
    }
}
