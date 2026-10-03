package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Persisted picker preferences shared by the settings UI and the live overlay.
 *
 * Layout-affecting changes (magnifier size, capture range, grid lines) notify rebuild
 * listeners so an active overlay can recreate itself immediately; speed, display format,
 * and copy preferences are read on demand and need no rebuild.
 */
internal object PickerSettings {
    private const val PREFS_NAME = "picker_settings"
    private const val KEY_MAGNIFIER_SIZE = "magnifier_size"
    private const val KEY_CAPTURE_RANGE = "capture_range"
    private const val KEY_FINE_TUNE_SPEED = "fine_tune_speed"
    private const val KEY_SHOW_GRID = "show_grid"
    private const val KEY_COPY_NUMERIC_ONLY = "copy_numeric_only"
    private const val KEY_HEX_FORMAT = "hex_format"

    private val rebuildListeners = CopyOnWriteArraySet<() -> Unit>()

    fun magnifierSizeIndex(context: Context): Int =
        PickerSettingsCatalog.sanitizeIndex(prefs(context).getInt(KEY_MAGNIFIER_SIZE, PickerSettingsCatalog.DEFAULT_INDEX))

    fun setMagnifierSizeIndex(context: Context, index: Int) {
        val sanitized = PickerSettingsCatalog.sanitizeIndex(index)
        if (sanitized == magnifierSizeIndex(context)) return
        prefs(context).edit { putInt(KEY_MAGNIFIER_SIZE, sanitized) }
        notifyRebuild()
    }

    fun captureRangeIndex(context: Context): Int =
        PickerSettingsCatalog.sanitizeIndex(prefs(context).getInt(KEY_CAPTURE_RANGE, PickerSettingsCatalog.DEFAULT_INDEX))

    fun setCaptureRangeIndex(context: Context, index: Int) {
        val sanitized = PickerSettingsCatalog.sanitizeIndex(index)
        if (sanitized == captureRangeIndex(context)) return
        prefs(context).edit { putInt(KEY_CAPTURE_RANGE, sanitized) }
        notifyRebuild()
    }

    fun fineTuneSpeedIndex(context: Context): Int =
        PickerSettingsCatalog.sanitizeIndex(prefs(context).getInt(KEY_FINE_TUNE_SPEED, PickerSettingsCatalog.DEFAULT_INDEX))

    fun setFineTuneSpeedIndex(context: Context, index: Int) {
        prefs(context).edit { putInt(KEY_FINE_TUNE_SPEED, PickerSettingsCatalog.sanitizeIndex(index)) }
    }

    fun showGrid(context: Context): Boolean = prefs(context).getBoolean(KEY_SHOW_GRID, true)

    fun setShowGrid(context: Context, value: Boolean) {
        if (value == showGrid(context)) return
        prefs(context).edit { putBoolean(KEY_SHOW_GRID, value) }
        notifyRebuild()
    }

    fun copyNumericOnly(context: Context): Boolean = prefs(context).getBoolean(KEY_COPY_NUMERIC_ONLY, false)

    fun setCopyNumericOnly(context: Context, value: Boolean) {
        prefs(context).edit { putBoolean(KEY_COPY_NUMERIC_ONLY, value) }
    }

    fun hexFormat(context: Context): Boolean = prefs(context).getBoolean(KEY_HEX_FORMAT, true)

    fun setHexFormat(context: Context, value: Boolean) {
        prefs(context).edit { putBoolean(KEY_HEX_FORMAT, value) }
    }

    fun addRebuildListener(listener: () -> Unit) {
        rebuildListeners += listener
    }

    fun removeRebuildListener(listener: () -> Unit) {
        rebuildListeners -= listener
    }

    private fun notifyRebuild() {
        rebuildListeners.forEach { listener -> runCatching { listener() } }
    }

    private fun prefs(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
