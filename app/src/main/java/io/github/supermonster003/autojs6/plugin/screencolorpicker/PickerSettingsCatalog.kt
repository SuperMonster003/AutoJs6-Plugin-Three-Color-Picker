package io.github.supermonster003.autojs6.plugin.screencolorpicker

/**
 * Pure option catalog for the picker settings.
 *
 * Index semantics follow the on-screen order Large/Medium/Small (0/1/2) so a persisted
 * index maps directly to a spinner position. Values mirror MT Manager's tool so the
 * replicated behavior matches: capture radius 10/8/6 px, fine-tune divisor 10/20/30,
 * magnifier diameter 220/190/160 dp.
 */
internal object PickerSettingsCatalog {
    const val OPTION_COUNT = 3
    const val DEFAULT_INDEX = 1

    private val captureRadii = intArrayOf(10, 8, 6)
    private val fineTuneDivisors = intArrayOf(10, 20, 30)
    private val magnifierSizesDp = intArrayOf(220, 190, 160)

    fun sanitizeIndex(index: Int): Int = if (index in 0 until OPTION_COUNT) index else DEFAULT_INDEX

    fun captureRadiusFor(index: Int): Int = captureRadii[sanitizeIndex(index)]

    fun fineTuneDivisorFor(index: Int): Int = fineTuneDivisors[sanitizeIndex(index)]

    fun magnifierSizeDpFor(index: Int): Int = magnifierSizesDp[sanitizeIndex(index)]

    fun gridCellCountFor(captureRadius: Int): Int {
        require(captureRadius > 0)
        return captureRadius * 2 + 1
    }
}
