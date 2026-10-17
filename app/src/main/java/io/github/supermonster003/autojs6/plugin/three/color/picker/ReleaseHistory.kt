package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.widget.ScrollView
import java.io.IOException

internal fun releaseHistoryCandidates(language: String, country: String, script: String): List<String> {
    val code = when {
        language == "zh" && country == "HK" -> "zh-Hant-HK"
        language == "zh" && (country == "TW" || script == "Hant") -> "zh-Hant-TW"
        language == "zh" -> "zh-Hans"
        language in setOf("en", "ar", "es", "fr", "ja", "ko", "ru") -> language
        else -> "en"
    }
    return listOf("doc/CHANGELOG-$code.md", "doc/CHANGELOG-en.md").distinct()
}

internal fun loadReleaseHistory(candidates: List<String>, loader: (String) -> String): String? {
    for (path in candidates) {
        try { loader(path).takeIf { it.isNotBlank() }?.let { return it } } catch (_: IOException) { }
    }
    return null
}

internal fun Activity.showReleaseHistory(): AlertDialog {
    val locale = resources.configuration.locales[0]
    val versions = loadReleaseHistory(releaseHistoryCandidates(locale.language, locale.country, locale.script)) { path ->
        assets.open(path).bufferedReader(Charsets.UTF_8).use { it.readText() }
    }?.let(ReleaseHistoryParser::parse).orEmpty()
    val palette=SettingsPalette(AppearanceSource.resolve(this))
    val ui=SettingsUi(this,palette)
    val content=LinearLayout(this).apply {
        orientation=LinearLayout.VERTICAL
        setPadding(ui.dp(24),ui.dp(8),ui.dp(24),ui.dp(16))
        if(versions.isEmpty()) addView(ui.text(getString(R.string.release_history_unavailable),14f).apply { tag="release-history-unavailable" })
        versions.forEachIndexed { index, version ->
            if(index>0) addView(View(context).apply { setBackgroundColor(palette.divider) },LinearLayout.LayoutParams(-1,ui.dp(1)).apply { topMargin=ui.dp(16); bottomMargin=ui.dp(16) })
            addView(releaseHistoryVersion(ui,version),LinearLayout.LayoutParams(-1,-2))
        }
    }
    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.release_history)
        .setView(ScrollView(this).apply { addView(content) })
        .setPositiveButton(android.R.string.ok, null)
         .show().also { dialog ->
            ui.styleDialog(dialog)
            val owner=this as? androidx.lifecycle.LifecycleOwner
            val observer=androidx.lifecycle.LifecycleEventObserver { _,event ->
                if(event==androidx.lifecycle.Lifecycle.Event.ON_DESTROY) dialog.dismiss()
            }
            owner?.lifecycle?.addObserver(observer)
            dialog.setOnDismissListener { owner?.lifecycle?.removeObserver(observer) }
        }
}

/** One version: the version and date on a header line, then one row per entry with its tag in the accent color. */
private fun Activity.releaseHistoryVersion(ui: SettingsUi, version: ReleaseHistoryVersion) = LinearLayout(this).apply {
    orientation=LinearLayout.VERTICAL; tag="release-history-${version.version}"
    val medium=Typeface.create("sans-serif-medium",Typeface.NORMAL)
    addView(LinearLayout(context).apply {
        orientation=LinearLayout.HORIZONTAL
        addView(ui.text(version.version,16f).apply {
            typeface=medium; androidx.core.view.ViewCompat.setAccessibilityHeading(this,true)
            // The Latin version would otherwise align by its own direction and run into the date in RTL.
            textAlignment=View.TEXT_ALIGNMENT_VIEW_START
        },LinearLayout.LayoutParams(0,-2,1f))
        version.date?.let { addView(ui.text(it,14f,true),LinearLayout.LayoutParams(-2,-2).apply { marginStart=ui.dp(8) }) }
    },LinearLayout.LayoutParams(-1,-2))
    version.items.forEach { item ->
        addView(LinearLayout(context).apply {
            orientation=LinearLayout.HORIZONTAL
            item.tag?.let {
                addView(ui.text(it,12f).apply {
                    typeface=medium; setTextColor(ui.palette.accent)
                    background=ui.fill(SettingsColorMath.alpha(ui.palette.accent,0x1f),6)
                    setPaddingRelative(ui.dp(8),ui.dp(2),ui.dp(8),ui.dp(2))
                },LinearLayout.LayoutParams(-2,-2).apply { marginEnd=ui.dp(8) })
            }
            addView(ReleaseHistoryEntryText(context).apply {
                text=ReleaseHistoryLineBreaks.insert(item.text); textSize=14f
                setTextColor(ui.palette.text); setLinkTextColor(ui.palette.accent); highlightColor=ui.palette.ripple
                textAlignment=View.TEXT_ALIGNMENT_VIEW_START
                setLineSpacing(ui.dp(2).toFloat(),1f); setTextIsSelectable(true)
            },LinearLayout.LayoutParams(0,-2,1f))
        },LinearLayout.LayoutParams(-1,-2).apply { topMargin=ui.dp(10) })
    }
}

/** Copies an entry without the zero width spaces [ReleaseHistoryLineBreaks] adds for wrapping. */
private class ReleaseHistoryEntryText(context: Context) : AppCompatTextView(context) {
    override fun onTextContextMenuItem(id: Int): Boolean {
        val selected=if(id==android.R.id.copy && selectionStart>=0 && selectionEnd>=0) text.subSequence(minOf(selectionStart,selectionEnd),maxOf(selectionStart,selectionEnd)) else null
        val handled=super.onTextContextMenuItem(id)
        if(handled && selected!=null && ReleaseHistoryLineBreaks.contains(selected)) {
            context.getSystemService(ClipboardManager::class.java)?.setPrimaryClip(ClipData.newPlainText(null,ReleaseHistoryLineBreaks.remove(selected)))
        }
        return handled
    }
}
