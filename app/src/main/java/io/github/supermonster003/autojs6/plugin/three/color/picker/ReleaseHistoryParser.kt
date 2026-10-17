package io.github.supermonster003.autojs6.plugin.three.color.picker

internal data class ReleaseHistoryItem(val tag: String?, val text: String)

internal data class ReleaseHistoryVersion(val version: String, val date: String?, val items: List<ReleaseHistoryItem>)

/**
 * Reads the changelog assets written by `.python/generate_markdown.py`: a `******` / `### title` / `******`
 * banner, then per version a `# vX.Y.Z` heading, an `_date_` line and `` - `tag` text `` bullets.
 * Inline code markers inside the text are dropped; lines outside this shape are ignored.
 */
internal object ReleaseHistoryParser {
    private val VERSION = Regex("""^#\s+(v?\d\S*)$""")
    private val DATE = Regex("""^_(.+)_$""")
    private val BULLET = Regex("""^-\s+(?:`([^`]+)`\s*)?(.*)$""")
    private val INLINE_CODE = Regex("""`([^`]+)`""")

    fun parse(markdown: String): List<ReleaseHistoryVersion> {
        val versions = mutableListOf<ReleaseHistoryVersion>()
        var version: String? = null
        var date: String? = null
        var items = mutableListOf<ReleaseHistoryItem>()
        fun flush() {
            versions += ReleaseHistoryVersion(version ?: return, date, items.toList())
        }
        for (raw in markdown.lineSequence()) {
            val line = raw.trim()
            val heading = VERSION.matchEntire(line)
            when {
                heading != null -> {
                    flush()
                    version = heading.groupValues[1]
                    date = null
                    items = mutableListOf()
                }
                version == null -> Unit
                date == null && items.isEmpty() && DATE.matches(line) -> date = DATE.matchEntire(line)!!.groupValues[1].trim()
                line.startsWith("- ") -> {
                    val bullet = BULLET.matchEntire(line) ?: continue
                    val tag = bullet.groupValues[1].trim().takeIf { it.isNotEmpty() }
                    val text = INLINE_CODE.replace(bullet.groupValues[2].trim(), "$1")
                    if (tag != null || text.isNotEmpty()) items += ReleaseHistoryItem(tag, text)
                }
            }
        }
        flush()
        return versions
    }
}

/**
 * A long dotted identifier such as the application ID has no line break opportunity, so a narrow
 * dialog splits it mid-word. Zero width spaces before its dots let it wrap there instead (the dot
 * then starts the next line), except after a short leading segment such as `io` that would be left
 * alone at a line end; [remove] takes them out again for copying.
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

    fun remove(text: CharSequence): String = text.toString().replace(BREAK.toString(), "")

    fun contains(text: CharSequence): Boolean = BREAK in text
}
