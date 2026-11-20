package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.content.Context
import android.content.Intent
import java.io.IOException
import java.util.Locale

/** Maps the UI locale to the bundled changelog asset shipped for it (AUTOJS6_PLUGIN_RELEASE_HISTORY_AGENTS.md, section 3). */
internal object ReleaseHistoryAssetPolicy {
    fun assetFor(locale: Locale): String = when (locale.language.lowercase(Locale.ROOT)) {
        "zh" -> when {
            locale.script.equals("Hans", ignoreCase = true) -> "CHANGELOG-zh-Hans.md"
            locale.country.equals("HK", ignoreCase = true) ||
                locale.country.equals("MO", ignoreCase = true) -> "CHANGELOG-zh-Hant-HK.md"
            locale.script.equals("Hant", ignoreCase = true) ||
                locale.country.equals("TW", ignoreCase = true) -> "CHANGELOG-zh-Hant-TW.md"
            else -> "CHANGELOG-zh-Hans.md"
        }
        "ar" -> "CHANGELOG-ar.md"
        "es" -> "CHANGELOG-es.md"
        "fr" -> "CHANGELOG-fr.md"
        "ja" -> "CHANGELOG-ja.md"
        "ko" -> "CHANGELOG-ko.md"
        "ru" -> "CHANGELOG-ru.md"
        else -> "CHANGELOG-en.md"
    }

    /** The asset paths to try in order: the one for [locale], then English. */
    fun candidatesFor(locale: Locale): List<String> =
        listOf(assetFor(locale), FALLBACK).distinct().map { "doc/$it" }

    private const val FALLBACK = "CHANGELOG-en.md"
}

internal data class ReleaseHistoryItem(val tag: String?, val text: String)

internal data class ReleaseHistoryEntry(
    val version: String,
    val date: String?,
    val items: List<ReleaseHistoryItem>,
) {
    /** The items by category, each category once, in the order the categories first appear (untagged: tag null). */
    val groups: List<ReleaseHistoryGroup>
        get() = items.groupBy { it.tag }.map { (tag, grouped) -> ReleaseHistoryGroup(tag, grouped.map { it.text }) }

    /** How many items each category has, in the same order; untagged items are not counted. */
    val categoryCounts: List<Pair<String, Int>>
        get() = groups.mapNotNull { group -> group.tag?.let { it to group.items.size } }
}

internal data class ReleaseHistoryGroup(val tag: String?, val items: List<String>)

/**
 * Parses the standard dialect of the bundled changelogs written by `.python/generate_markdown.py`: `# vX.Y.Z` or
 * `## vX.Y.Z` version headings, `###### date` lines, and `` * `tag` text `` bullets. Inline code markers inside the
 * text are dropped; anything else (the section title, the separator lines) is ignored.
 */
internal object ReleaseHistoryParser {
    private val BULLET_PATTERN = Regex("^\\*\\s+(?:`([^`]+)`\\s*)?(.*)$")
    private val VERSION_PATTERN = Regex("^#{1,2}\\s+(v\\S+)\\s*$")
    private val INLINE_CODE = Regex("`([^`]+)`")

    fun parse(markdown: String): List<ReleaseHistoryEntry> {
        val entries = mutableListOf<ReleaseHistoryEntry>()
        var version: String? = null
        var date: String? = null
        var items = mutableListOf<ReleaseHistoryItem>()

        fun flush() {
            val flushedVersion = version ?: return
            entries.add(ReleaseHistoryEntry(flushedVersion, date, items.toList()))
        }

        for (rawLine in markdown.lineSequence()) {
            val line = rawLine.trim()
            val versionMatch = VERSION_PATTERN.matchEntire(line)
            when {
                versionMatch != null -> {
                    flush()
                    version = versionMatch.groupValues[1]
                    date = null
                    items = mutableListOf()
                }
                line.startsWith("###### ") && version != null -> date = line.removePrefix("###### ").trim()
                line.startsWith("* ") && version != null -> {
                    val match = BULLET_PATTERN.matchEntire(line) ?: continue
                    val tag = match.groupValues[1].trim().takeIf { it.isNotEmpty() }
                    val text = INLINE_CODE.replace(match.groupValues[2].trim(), "$1")
                    if (tag != null || text.isNotEmpty()) {
                        items.add(ReleaseHistoryItem(tag, text))
                    }
                }
            }
        }
        flush()
        return entries
    }
}

/**
 * Returns the releases of the first candidate asset that holds at least one, so a missing, blank or unparsable
 * localized changelog falls back to the English one; empty when none of them does (the page shows its empty state).
 */
internal fun loadReleaseHistory(candidates: List<String>, loader: (String) -> String): List<ReleaseHistoryEntry> {
    for (path in candidates) {
        val markdown = try { loader(path) } catch (_: IOException) { continue }
        val entries = ReleaseHistoryParser.parse(markdown)
        if (entries.isNotEmpty()) return entries
    }
    return emptyList()
}

/**
 * A long dotted identifier such as the application ID has no line break opportunity, so a narrow screen splits it
 * mid-word. Zero width spaces before its dots let it wrap there instead (the dot then starts the next line), except
 * after a short leading segment such as `io` that would be left alone at a line end. The page text is not selectable,
 * so the spaces never reach the clipboard.
 */
internal object ReleaseHistoryLineBreaks {
    private val BREAK = Char(0x200B)
    private val DOTTED = Regex("""[A-Za-z][A-Za-z0-9_]*(?:\.[A-Za-z][A-Za-z0-9_]*){2,}""")
    private const val MIN_LENGTH = 24
    private const val MIN_LEADING = 4

    fun insert(text: String): String = DOTTED.replace(text) { match ->
        if (match.value.length < MIN_LENGTH) return@replace match.value
        val segments = match.value.split('.')
        buildString {
            segments.forEachIndexed { index, segment ->
                if (index == 1 && segments[0].length < MIN_LEADING) append('.') else if (index > 0) append("$BREAK.")
                append(segment)
            }
        }
    }
}

/** Opens the release history page; the settings row leads here. */
internal fun Context.openReleaseHistory() {
    startActivity(Intent(this, ReleaseHistoryActivity::class.java))
}
