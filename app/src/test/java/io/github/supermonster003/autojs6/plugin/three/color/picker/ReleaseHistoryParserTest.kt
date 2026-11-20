package io.github.supermonster003.autojs6.plugin.three.color.picker

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.util.Locale

class ReleaseHistoryParserTest {
    @Test fun generatedDialectBecomesOneEntryPerVersion() {
        val markdown = """
            ******

            ### Release history

            ******

            # v2.0.1

            ###### 2026/10/17

            * `Fixed` Show the `Release history` as versions
            * `Improved` Icons

            # v2.0.0

            ###### 2026/10/03

            * `Hint` A hint
            * Untagged note
        """.trimIndent()

        val versions = ReleaseHistoryParser.parse(markdown)

        assertEquals(listOf("v2.0.1", "v2.0.0"), versions.map { it.version })
        assertEquals(listOf("2026/10/17", "2026/10/03"), versions.map { it.date })
        assertEquals(
            listOf(ReleaseHistoryItem("Fixed", "Show the Release history as versions"), ReleaseHistoryItem("Improved", "Icons")),
            versions[0].items,
        )
        assertEquals(listOf(ReleaseHistoryItem("Hint", "A hint"), ReleaseHistoryItem(null, "Untagged note")), versions[1].items)
        assertEquals(versions, ReleaseHistoryParser.parse(markdown.replace("\n", "\r\n")))
    }

    @Test fun acceptsTheDoubleHashHeadingAndIgnoresEverythingElse() {
        val markdown = """
            ### Release history
            # Release history
            * `Ignored` before any version
            ###### ignored date

            ## v1.0.0

            Free paragraph
            - `Ignored` dash bullet
            * Untagged item with a lone ` mark
            *
            # 0.8.0
        """.trimIndent()

        val versions = ReleaseHistoryParser.parse(markdown)

        assertEquals(listOf("v1.0.0"), versions.map { it.version })
        assertEquals(null, versions[0].date)
        assertEquals(listOf(ReleaseHistoryItem(null, "Untagged item with a lone ` mark")), versions[0].items)
        assertEquals(emptyList<ReleaseHistoryEntry>(), ReleaseHistoryParser.parse(""))
        assertEquals(emptyList<ReleaseHistoryEntry>(), ReleaseHistoryParser.parse("******\n\n### Release history\n\n******"))
    }

    /** The page names each category once and lists its items beneath; the collapsed summary counts them. */
    @Test fun groupsItemsByCategoryInTheOrderTheyFirstAppear() {
        val entry = ReleaseHistoryParser.parse(
            "# v1.2.0\n\n* `Hint` A\n* `Added` B\n* `Added` C\n* `Fixed` D\n* `Added` E\n* Untagged F\n",
        ).single()
        assertEquals(
            listOf("Hint" to listOf("A"), "Added" to listOf("B", "C", "E"), "Fixed" to listOf("D"), null to listOf("Untagged F")),
            entry.groups.map { it.tag to it.items },
        )
        assertEquals(listOf("Hint" to 1, "Added" to 3, "Fixed" to 1), entry.categoryCounts)
    }

    @Test fun bidiIsolatesAroundBrandNamesAreKept() {
        val isolated = "${Char(0x2068)}3-Color Picker${Char(0x2069)}"
        val versions = ReleaseHistoryParser.parse("# v2.0.1\n\n###### 2026/10/17\n\n* `Fixed` $isolated `code`")

        assertEquals(listOf(ReleaseHistoryItem("Fixed", "$isolated code")), versions.single().items)
    }

    @Test fun lineBreaksGoBeforeTheDotsOfLongIdentifiersOnly() {
        val id = "io.github.supermonster003.autojs6.plugin.three.color.picker"
        val text = "Version 2.0 uses the new application ID $id. MT Manager (bm.mt.plus) v2.26.9, AGP 9.1, HEX/RGB."
        val breakable = ReleaseHistoryLineBreaks.insert(text)
        val zwsp = Char(0x200B)

        assertEquals(text.replace(id, id.replace(".", "$zwsp.").replaceFirst("io$zwsp.", "io.")), breakable)
        assertEquals(6, breakable.count { it == zwsp })
        assertEquals("org.example.thing", ReleaseHistoryLineBreaks.insert("org.example.thing"))
        assertEquals("org.supermonster003$zwsp.autojs6$zwsp.picker", ReleaseHistoryLineBreaks.insert("org.supermonster003.autojs6.picker"))
        assertEquals("example$zwsp.supermonster003$zwsp.picker", ReleaseHistoryLineBreaks.insert("example.supermonster003.picker"))
        assertEquals(text, breakable.replace(zwsp.toString(), ""))
        assertEquals("", ReleaseHistoryLineBreaks.insert(""))
    }

    @Test fun everyBundledChangelogParsesCompletely() {
        val docs = File("src/main/assets/doc")
        val versionName = File("../version.properties").readLines()
            .first { it.startsWith("VERSION_NAME=") }.substringAfter('=').trim()
        val locales = listOf("ar", "en", "es", "fr", "ja", "ko", "ru", "zh-Hans-CN", "zh-HK", "zh-TW").map(Locale::forLanguageTag)
        val assets = locales.map { ReleaseHistoryAssetPolicy.candidatesFor(it).first() }
        assertEquals(10, assets.distinct().size)
        val zwsp = Char(0x200B)

        val shapes = assets.map { asset ->
            val markdown = docs.resolve(asset.removePrefix("doc/")).readText()
            val lines = markdown.lines().map(String::trim)
            val versions = ReleaseHistoryParser.parse(markdown)

            assertEquals(asset, lines.count { it.startsWith("# ") }, versions.size)
            assertEquals(asset, "v$versionName", versions.first().version)
            assertTrue(asset, versions.all { it.date != null && it.items.isNotEmpty() })
            assertTrue(asset, versions.flatMap { it.items }.all { it.tag != null && it.text.isNotEmpty() && '`' !in it.text })
            assertEquals(asset, lines.count { it.startsWith("* ") }, versions.sumOf { it.items.size })
            assertEquals(asset, 0, lines.count { it.startsWith("- ") || it.startsWith("_") })
            assertEquals(asset, markdown.count { it == Char(0x2068) }, versions.flatMap { it.items }.sumOf { item -> item.text.count { it == Char(0x2068) } })
            for (item in versions.flatMap { it.items }) {
                assertFalse(asset, zwsp in item.text)
            }
            assertTrue(asset, versions.flatMap { it.items }.any { item -> "picker" in item.text && zwsp in ReleaseHistoryLineBreaks.insert(item.text) })
            versions.map { Triple(it.version, it.date, it.items.size) }
        }
        // In right-to-left text, punctuation right after a long dotted identifier is drawn at its left edge and reads as part of it.
        val arabic = docs.resolve("CHANGELOG-ar.md").readText()
        assertEquals(emptyList<String>(), LONG_IDENTIFIER_BEFORE_PUNCTUATION.findAll(arabic).map { it.value }.filter { it.length > 24 }.toList())
        shapes.forEachIndexed { index, shape -> assertEquals(assets[index], shapes.first(), shape) }
    }

    private companion object {
        val LONG_IDENTIFIER_BEFORE_PUNCTUATION = Regex("""[A-Za-z][A-Za-z0-9_]*(?:\.[A-Za-z][A-Za-z0-9_]*){2,}[.,;:!?](?![A-Za-z0-9_])""")
    }
}
