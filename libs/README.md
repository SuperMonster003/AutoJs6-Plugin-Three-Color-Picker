# Bundled plugin API artifacts

This directory contains contract-only Android libraries required at compile time. Neither AAR
contains native code, a runtime engine, screenshots, or user data.

| Artifact | AutoJs6 source module | Size | SHA-256 |
| --- | --- | ---: | --- |
| `common-plugin-api.aar` | `plugin-api/common-plugin-api` official host settings contract | 13,212 bytes | `ee7eb7879a53506c4cca5e2d19d3058e28df2168fb33351a52302a3b9e532e15` |
| `screen-color-picker-api.aar` | `plugin-api/screen-color-picker-api` | 7,726 bytes | `5136dd3cceb67a878a1e7cf5158821c0970032ad4f0f6011972cf87b0fcef4e9` |

The source modules are maintained in the
[AutoJs6 repository](https://github.com/SuperMonster003/AutoJs6) under MPL-2.0. The screen color
picker API AAR is copied from the exact release output used by the matching host integration. The
common API now includes the versioned read-only `AutoJs6HostSettingsContract`, consumed by the standalone appearance layer. Exact source revision and checksum are pinned in `common-plugin-api.provenance.json`. The binary is copied from the audited official release API, not resolved from a sibling at build time.

To replace an artifact, rebuild the corresponding AutoJs6 module with `assembleRelease`, copy the
release AAR here, update the size and checksum above, and run the unit, lint, debug, release, and
instrumentation builds before committing it.
