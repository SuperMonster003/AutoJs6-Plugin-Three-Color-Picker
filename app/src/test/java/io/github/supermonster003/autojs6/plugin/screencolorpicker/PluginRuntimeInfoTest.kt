package io.github.supermonster003.autojs6.plugin.screencolorpicker

import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerContract
import org.junit.Assert.assertEquals
import org.junit.Test

class PluginRuntimeInfoTest {
    @Test
    fun pureMetadataAssemblyUsesInstalledValuesAndStableContract() {
        val fields = PluginRuntimeInfo.metadata(
            name = "Localized name",
            description = "Localized description",
            instruction = PLUGIN_INSTRUCTION_REFERENCE,
            author = "Author",
            versionName = "9.8.7",
            versionCode = 987L,
            versionDate = "Sep 1, 2026",
        )
        assertEquals("Localized name", fields.name)
        assertEquals("Localized description", fields.description)
        assertEquals("@raw/plugin_instruction", fields.instruction)
        assertEquals("9.8.7", fields.versionName)
        assertEquals(987L, fields.versionCode)
        assertEquals(ScreenColorPickerContract.PLUGIN_ID, fields.id)
        assertEquals(ScreenColorPickerContract.ENGINE, fields.engine)
        assertEquals(ScreenColorPickerContract.VARIANT_DEFAULT, fields.variant)
        assertEquals(ScreenColorPickerContract.VERSION, fields.contractVersion)
        assertEquals(5_278L, fields.requiredHostVersion)
    }
}
