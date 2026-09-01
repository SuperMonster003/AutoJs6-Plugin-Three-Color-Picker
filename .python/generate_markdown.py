#!/usr/bin/env python3
"""Generate and validate localized project documentation."""

from __future__ import annotations

import argparse
import json
import re
import sys
import unicodedata
from pathlib import Path
from typing import Any
from xml.etree import ElementTree


LANGUAGES = [
    "zh-Hans",
    "zh-Hant-HK",
    "zh-Hant-TW",
    "en",
    "fr",
    "es",
    "ja",
    "ko",
    "ru",
    "ar",
]
README_DEFAULT = "zh-Hans"
ANDROID_DEFAULT = "en"
STRING_DIRECTORIES = {
    "zh-Hans": "values-zh",
    "zh-Hant-HK": "values-zh-rHK",
    "zh-Hant-TW": "values-zh-rTW",
    "en": "values-en",
    "fr": "values-fr",
    "es": "values-es",
    "ja": "values-ja",
    "ko": "values-ko",
    "ru": "values-ru",
    "ar": "values-ar",
}
INSTRUCTION_DIRECTORIES = {
    code: directory.replace("values", "raw", 1)
    for code, directory in STRING_DIRECTORIES.items()
}
CHANGELOG_ALIASES = {
    "zh-Hans": ["zh", "zh-Hans"],
    "zh-Hant-HK": ["zh-rHK", "zh-Hant-HK"],
    "zh-Hant-TW": ["zh-rTW", "zh-Hant-TW"],
}
LIST_KEYS = ["features", "standalone_steps", "host_steps", "privacy_points"]
CHANGELOG_CATEGORIES = ["hint", "feature", "fix", "improvement", "dependency"]
CHANGELOG_LABELS = [f"changelog_label_{category}" for category in CHANGELOG_CATEGORIES]
EXPECTED_ARTIFACTS = 36
TEMPLATE_PATTERN = re.compile(r"\{\{\s*([A-Za-z0-9_$.-]+)\s*\}\}")
FORMAT_PATTERN = re.compile(r"%(?:\d+\$)?[A-Za-z]")
DATE_PATTERN = re.compile(r"^\d{4}/\d{2}/\d{2}$")
PLACEHOLDER_MARKERS = ("TODO_TRANSLATION", "TRANSLATION_PENDING", "MACHINE_TRANSLATION_PLACEHOLDER")


class GenerationError(RuntimeError):
    pass


def require(condition: bool, message: str) -> None:
    if not condition:
        raise GenerationError(message)


def reject_duplicates(pairs: list[tuple[str, Any]]) -> dict[str, Any]:
    result: dict[str, Any] = {}
    for key, value in pairs:
        require(key not in result, f"Duplicate JSON key: {key!r}")
        result[key] = value
    return result


def validate_text(path: Path, text: str) -> None:
    for marker in PLACEHOLDER_MARKERS:
        require(marker not in text, f"Translation marker {marker!r} remains in {path}")
    for line_number, line in enumerate(text.splitlines(), start=1):
        for character in line:
            if (
                unicodedata.east_asian_width(character) in ("F", "W")
                and unicodedata.category(character)[0] in ("P", "S", "Z")
            ):
                raise GenerationError(
                    f"Fullwidth symbol {character!r} (U+{ord(character):04X}) "
                    f"in {path} at line {line_number}"
                )


def load_text(path: Path) -> str:
    require(path.is_file(), f"Missing file: {path}")
    require(not path.is_symlink(), f"Refusing symlink: {path}")
    try:
        text = path.read_text(encoding="utf-8")
    except UnicodeDecodeError as error:
        raise GenerationError(f"Invalid UTF-8 in {path}: {error}") from None
    validate_text(path, text)
    return text


def load_json(path: Path) -> dict[str, Any]:
    try:
        value = json.loads(load_text(path), object_pairs_hook=reject_duplicates)
    except json.JSONDecodeError as error:
        raise GenerationError(f"Invalid JSON in {path}: {error}") from None
    require(isinstance(value, dict), f"JSON root must be an object: {path}")
    return value


def read_properties(path: Path) -> dict[str, str]:
    result: dict[str, str] = {}
    for raw_line in load_text(path).splitlines():
        line = raw_line.strip()
        if line and not line.startswith(("#", "!")) and "=" in line:
            key, value = line.split("=", 1)
            result[key.strip()] = value.strip()
    return result


def shape(value: Any) -> Any:
    if isinstance(value, dict):
        return ("dict", tuple((key, shape(item)) for key, item in value.items()))
    if isinstance(value, list):
        return ("list", len(value), tuple(shape(item) for item in value))
    return type(value).__name__


def render(template: str, values: dict[str, Any]) -> str:
    def replace(match: re.Match[str]) -> str:
        key = match.group(1)
        require(key in values, f"Missing template value: {key}")
        return str(values[key])

    output = TEMPLATE_PATTERN.sub(replace, template)
    require(TEMPLATE_PATTERN.search(output) is None, "Unresolved template placeholder")
    return output.rstrip() + "\n"


def bullets(items: list[str]) -> str:
    return "\n".join(f"- {item}" for item in items)


def numbered(items: list[str]) -> str:
    return "\n".join(f"{index}. {item}" for index, item in enumerate(items, start=1))


def read_android_strings(path: Path) -> tuple[list[str], dict[str, str]]:
    try:
        root = ElementTree.fromstring(load_text(path))
    except ElementTree.ParseError as error:
        raise GenerationError(f"Invalid XML in {path}: {error}") from None
    names: list[str] = []
    values: dict[str, str] = {}
    for element in root.findall("string"):
        name = element.get("name")
        require(bool(name), f"Nameless string in {path}")
        require(name not in values, f"Duplicate string {name!r} in {path}")
        names.append(name or "")
        values[name or ""] = (element.text or "").replace("\\'", "'").replace('\\"', '"')
    return names, values


def validate_languages(readmes: dict[str, dict[str, Any]]) -> None:
    reference = readmes[README_DEFAULT]
    for code, content in readmes.items():
        require(set(content) == set(reference), f"README key mismatch for {code}")
        for key in LIST_KEYS:
            require(isinstance(content.get(key), list) and content[key], f"{code}.{key} must be a non-empty list")
        require(shape(content) == shape(reference), f"README shape mismatch for {code}")


def validate_changelogs(changelogs: dict[str, dict[str, Any]], current_label: str) -> None:
    expected_top = set(CHANGELOG_LABELS) | {"$data"}
    reference = changelogs[README_DEFAULT]
    versions = list(reference.get("$data", {}))
    require(bool(versions), "Changelog must contain at least one version")
    require(versions[0] == current_label, f"Newest changelog {versions[0]!r} != {current_label!r}")
    for code, content in changelogs.items():
        require(set(content) == expected_top, f"Invalid changelog keys for {code}")
        data = content["$data"]
        require(list(data) == versions, f"Changelog version mismatch for {code}")
        for version, entry in data.items():
            reference_entry = reference["$data"][version]
            require(set(entry) == set(reference_entry), f"Changelog field mismatch for {code}/{version}")
            require(DATE_PATTERN.fullmatch(str(entry.get("released_date", ""))) is not None, f"Invalid date in {code}/{version}")
            require(entry["released_date"] == reference_entry["released_date"], f"Date mismatch in {code}/{version}")
            unknown = set(entry) - {"released_date", *CHANGELOG_CATEGORIES}
            require(not unknown, f"Unknown changelog categories in {code}/{version}: {sorted(unknown)}")
            for category in CHANGELOG_CATEGORIES:
                if category in reference_entry:
                    require(
                        isinstance(entry.get(category), list)
                        and len(entry[category]) == len(reference_entry[category]),
                        f"Changelog item count mismatch for {code}/{version}/{category}",
                    )


def validate_resources(root: Path, readmes: dict[str, dict[str, Any]]) -> None:
    resource_root = root / "app" / "src" / "main" / "res"
    default_names, default_values = read_android_strings(resource_root / "values" / "strings.xml")
    require(default_names == sorted(default_names), "values/strings.xml is not sorted by name")
    localized: dict[str, dict[str, str]] = {}
    for code, directory in STRING_DIRECTORIES.items():
        names, values = read_android_strings(resource_root / directory / "strings.xml")
        require(names == sorted(names), f"{directory}/strings.xml is not sorted by name")
        require(names == default_names, f"String key/order mismatch in {directory}")
        localized[code] = values
        require(
            values["plugin_description"] == readmes[code]["synopsis"],
            f"plugin_description does not match synopsis for {code}",
        )
        for name, value in values.items():
            require(
                FORMAT_PATTERN.findall(value) == FORMAT_PATTERN.findall(default_values[name]),
                f"Format placeholder mismatch for {code}/{name}",
            )
    require(default_values == localized[ANDROID_DEFAULT], "Default and explicit English strings differ")
    require(
        default_values["plugin_description"] == readmes[ANDROID_DEFAULT]["synopsis"],
        "Default plugin_description does not match English synopsis",
    )
    for code, values in localized.items():
        require(
            not values["plugin_description"].endswith((".", "!", "?")),
            f"plugin_description has terminal punctuation for {code}",
        )


def language_links(code: str, readmes: dict[str, dict[str, Any]], repo_url: str) -> str:
    lines: list[str] = []
    for target in LANGUAGES:
        label = f"{readmes[target]['$name']} [{target}]"
        if target == code:
            lines.append(f"- {label} # {readmes[target]['current']}")
        else:
            lines.append(f"- [{label}]({repo_url}/blob/master/.readme/README-{target}.md)")
    return "\n".join(lines).rstrip()


def format_changelog(content: dict[str, Any], heading: int = 1, limit: int | None = None) -> str:
    chunks: list[str] = []
    labels = {category: content[f"changelog_label_{category}"] for category in CHANGELOG_CATEGORIES}
    for index, (version, entry) in enumerate(content["$data"].items()):
        if limit is not None and index >= limit:
            break
        lines = [f"{'#' * heading} {version}", "", f"_{entry['released_date']}_", ""]
        for category in CHANGELOG_CATEGORIES:
            for item in entry.get(category, []):
                lines.append(f"- `{labels[category]}` {item}")
        chunks.append("\n".join(lines).rstrip())
    return "\n\n".join(chunks)


def build_artifacts(root: Path) -> dict[Path, str]:
    readme_dir = root / ".readme"
    changelog_dir = root / ".changelog"
    resource_dir = root / "app" / "src" / "main" / "res"
    changelog_asset_dir = root / "app" / "src" / "main" / "assets" / "doc"
    common = load_json(readme_dir / "common.json")
    readmes = {code: load_json(readme_dir / f"lang_{code}.json") for code in LANGUAGES}
    changelogs = {code: load_json(changelog_dir / f"lang_{code}.json") for code in LANGUAGES}
    version = read_properties(root / "version.properties").get("VERSION_NAME", "")
    require(bool(version), "VERSION_NAME is missing")
    current_label = version if version.startswith("v") else f"v{version}"
    validate_languages(readmes)
    validate_changelogs(changelogs, current_label)
    validate_resources(root, readmes)

    readme_template = load_text(readme_dir / "template_readme.md")
    instruction_template = load_text(readme_dir / "template_plugin_instruction.md")
    changelog_template = load_text(changelog_dir / "template_changelog.md")
    artifacts: dict[Path, str] = {}

    for code in LANGUAGES:
        values = {**common, **readmes[code], "$code": code, "version_name": version}
        values.update(
            language_links=language_links(code, readmes, common["repo_url"]),
            feature_list=bullets(readmes[code]["features"]),
            standalone_steps_list=numbered(readmes[code]["standalone_steps"]),
            host_steps_list=numbered(readmes[code]["host_steps"]),
            privacy_list=bullets(readmes[code]["privacy_points"]),
            latest_release=format_changelog(changelogs[code], heading=4, limit=3),
        )
        readme = render(readme_template, values)
        instruction = render(instruction_template, values)
        artifacts[readme_dir / f"README-{code}.md"] = readme
        if code == README_DEFAULT:
            artifacts[root / "README.md"] = readme
        instruction_directory = INSTRUCTION_DIRECTORIES[code]
        artifacts[resource_dir / instruction_directory / "plugin_instruction.md"] = instruction
        if code == ANDROID_DEFAULT:
            artifacts[resource_dir / "raw" / "plugin_instruction.md"] = instruction

        changelog_values = {**common, **readmes[code], "release_history": format_changelog(changelogs[code])}
        changelog = render(changelog_template, changelog_values)
        for alias in CHANGELOG_ALIASES.get(code, [code]):
            artifacts[changelog_asset_dir / f"CHANGELOG-{alias}.md"] = changelog
        if code == README_DEFAULT:
            artifacts[changelog_asset_dir / "CHANGELOG.md"] = changelog

    require(len(artifacts) == EXPECTED_ARTIFACTS, f"Expected {EXPECTED_ARTIFACTS} artifacts, got {len(artifacts)}")
    return artifacts


def generated_inventory(root: Path) -> set[Path]:
    inventory: set[Path] = set()
    if (root / "README.md").is_file():
        inventory.add(root / "README.md")
    inventory.update((root / ".readme").glob("README-*.md"))
    inventory.update((root / "app" / "src" / "main" / "assets" / "doc").glob("CHANGELOG*.md"))
    inventory.update((root / "app" / "src" / "main" / "res").glob("raw*/plugin_instruction.md"))
    return inventory


def write_artifacts(root: Path, artifacts: dict[Path, str]) -> None:
    orphans = generated_inventory(root) - set(artifacts)
    require(not orphans, "Orphan generated files: " + ", ".join(str(path.relative_to(root)) for path in sorted(orphans)))
    for path, content in artifacts.items():
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding="utf-8", newline="\n")
        print(f"Generated {path.relative_to(root)}")


def check_artifacts(root: Path, artifacts: dict[Path, str]) -> None:
    drift: list[str] = []
    for path, expected in sorted(artifacts.items()):
        if not path.is_file():
            drift.append(f"missing: {path.relative_to(root)}")
        elif path.read_text(encoding="utf-8") != expected:
            drift.append(f"stale: {path.relative_to(root)}")
    for path in sorted(generated_inventory(root) - set(artifacts)):
        drift.append(f"orphan: {path.relative_to(root)}")
    require(not drift, "Artifact drift -> " + "; ".join(drift))


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Generate localized README, instructions, and changelogs")
    parser.add_argument("--check", action="store_true")
    arguments = parser.parse_args(argv)
    root = Path(__file__).resolve().parents[1]
    try:
        artifacts = build_artifacts(root)
        if arguments.check:
            check_artifacts(root, artifacts)
        else:
            write_artifacts(root, artifacts)
    except GenerationError as error:
        print(f"MARKDOWN_ERROR {error}", file=sys.stderr)
        return 1
    print(f"MARKDOWN_OK languages={len(LANGUAGES)} artifacts={len(artifacts)} mode={'check' if arguments.check else 'write'}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
