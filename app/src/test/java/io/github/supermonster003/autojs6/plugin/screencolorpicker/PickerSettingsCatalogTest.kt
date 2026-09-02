package io.github.supermonster003.autojs6.plugin.screencolorpicker

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PickerSettingsCatalogTest {
    @Test
    fun optionValuesFollowLargeMediumSmallOrder() {
        assertEquals(10, PickerSettingsCatalog.captureRadiusFor(0))
        assertEquals(8, PickerSettingsCatalog.captureRadiusFor(1))
        assertEquals(6, PickerSettingsCatalog.captureRadiusFor(2))
        assertEquals(10, PickerSettingsCatalog.fineTuneDivisorFor(0))
        assertEquals(20, PickerSettingsCatalog.fineTuneDivisorFor(1))
        assertEquals(30, PickerSettingsCatalog.fineTuneDivisorFor(2))
        assertEquals(220, PickerSettingsCatalog.magnifierSizeDpFor(0))
        assertEquals(190, PickerSettingsCatalog.magnifierSizeDpFor(1))
        assertEquals(160, PickerSettingsCatalog.magnifierSizeDpFor(2))
    }

    @Test
    fun outOfRangeIndexesFallBackToTheMediumDefault() {
        assertEquals(PickerSettingsCatalog.DEFAULT_INDEX, PickerSettingsCatalog.sanitizeIndex(-1))
        assertEquals(PickerSettingsCatalog.DEFAULT_INDEX, PickerSettingsCatalog.sanitizeIndex(3))
        assertEquals(0, PickerSettingsCatalog.sanitizeIndex(0))
        assertEquals(2, PickerSettingsCatalog.sanitizeIndex(2))
        assertEquals(
            PickerSettingsCatalog.captureRadiusFor(PickerSettingsCatalog.DEFAULT_INDEX),
            PickerSettingsCatalog.captureRadiusFor(99),
        )
    }

    @Test
    fun gridAlwaysKeepsTheSampledPixelInItsCenterCell() {
        assertEquals(21, PickerSettingsCatalog.gridCellCountFor(10))
        assertEquals(17, PickerSettingsCatalog.gridCellCountFor(8))
        assertEquals(13, PickerSettingsCatalog.gridCellCountFor(6))
        assertThrows(IllegalArgumentException::class.java) {
            PickerSettingsCatalog.gridCellCountFor(0)
        }
    }
}
