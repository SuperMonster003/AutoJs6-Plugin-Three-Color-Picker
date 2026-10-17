package io.github.supermonster003.autojs6.plugin.three.color.picker

import org.junit.Assert.*
import org.junit.Test
import java.io.File

class ReleaseHistoryParserTest {
    @Test fun generatedDialectBecomesOneEntryPerVersion() {
        val markdown = """
            ******

            ### Release history

            ******

            # v2.0.1

            _2026/10/17_

            - `Fixed` Show the `Release history` as versions
            - `Improved` Icons

            # v2.0.0

            _2026/10/03_

            - `Hint` A hint
        """.trimIndent()

        val versions = ReleaseHistoryParser.parse(markdown)

        assertEquals(listOf("v2.0.1", "v2.0.0"), versions.map { it.version })
        assertEquals(listOf("2026/10/17", "2026/10/03"), versions.map { it.date })
        assertEquals(
            listOf(ReleaseHistoryItem("Fixed", "Show the Release history as versions"), ReleaseHistoryItem("Improved", "Icons")),
            versions[0].items,
        )
        assertEquals(listOf(ReleaseHistoryItem("Hint", "A hint")), versions[1].items)
        assertEquals(versions, ReleaseHistoryParser.parse(markdown.replace("\n", "\r\n")))
    }

    @Test fun bannerStrayLinesAndUntaggedBulletsAreHandled() {
        val markdown = """
            ### Release history
            # Release history
            - `Ignored` before any version
            _ignored date_

            # v1.0.0

            Free paragraph
            - Untagged item with a lone ` mark
            -
            _not a date after items_
            ## v0.9.0
            # 0.8.0
        """.trimIndent()

        val versions = ReleaseHistoryParser.parse(markdown)

        assertEquals(listOf("v1.0.0", "0.8.0"), versions.map { it.version })
        assertNull(versions[0].date)
        assertEquals(listOf(ReleaseHistoryItem(null, "Untagged item with a lone ` mark")), versions[0].items)
        assertEquals(emptyList<ReleaseHistoryItem>(), versions[1].items)
        assertEquals(emptyList<ReleaseHistoryVersion>(), ReleaseHistoryParser.parse(""))
        assertEquals(emptyList<ReleaseHistoryVersion>(), ReleaseHistoryParser.parse("******\n\n### Release history\n\n******"))
    }

    @Test fun bidiIsolatesAroundBrandNamesAreKept() {
        val isolated = "${Char(0x2068)}3-Color Picker${Char(0x2069)}"
        val versions = ReleaseHistoryParser.parse("# v2.0.1\n\n_2026/10/17_\n\n- `Fixed` $isolated `code`")

        assertEquals(listOf(ReleaseHistoryItem("Fixed", "$isolated code")), versions.single().items)
    }

    @Test fun everyBundledChangelogParsesCompletely() {
        val docs = File("src/main/assets/doc")
        val versionName = File("../version.properties").readLines()
            .first { it.startsWith("VERSION_NAME=") }.substringAfter('=').trim()
        val locales = listOf(
            Triple("ar", "", ""), Triple("en", "", ""), Triple("es", "", ""), Triple("fr", "", ""), Triple("ja", "", ""),
            Triple("ko", "", ""), Triple("ru", "", ""), Triple("zh", "CN", "Hans"), Triple("zh", "HK", ""), Triple("zh", "TW", ""),
        )
        val assets = locales.map { (language, country, script) -> releaseHistoryCandidates(language, country, script).first() }
        assertEquals(10, assets.distinct().size)

        val shapes = assets.map { asset ->
            val markdown = docs.resolve(asset.removePrefix("doc/")).readText()
            val lines = markdown.lines().map(String::trim)
            val versions = ReleaseHistoryParser.parse(markdown)

            assertEquals(asset, lines.count { it.startsWith("# ") }, versions.size)
            assertEquals(asset, "v$versionName", versions.first().version)
            assertTrue(asset, versions.all { it.date != null && it.items.isNotEmpty() })
            assertTrue(asset, versions.flatMap { it.items }.all { it.tag != null && it.text.isNotEmpty() && '`' !in it.text })
            assertEquals(asset, lines.count { it.startsWith("- ") }, versions.sumOf { it.items.size })
            assertEquals(asset, markdown.count { it == Char(0x2068) }, versions.flatMap { it.items }.sumOf { item -> item.text.count { it == Char(0x2068) } })
            versions.map { Triple(it.version, it.date, it.items.size) }
        }
        shapes.forEachIndexed { index, shape -> assertEquals(assets[index], shapes.first(), shape) }
    }
}
