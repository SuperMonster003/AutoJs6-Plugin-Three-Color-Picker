# Bundled plugin API artifacts

This directory contains contract-only Android libraries required at compile time. Neither AAR
contains native code, a runtime engine, screenshots, or user data.

| Artifact | AutoJs6 source module | Size | SHA-256 |
| --- | --- | ---: | --- |
| `common-plugin-api.aar` | `plugin-api/common-plugin-api` compatibility baseline | 10,578 bytes | `f9ff9676543f45b2ff2b64bb832d377dbd5b7003acf3bb4239e98556c68b246f` |
| `screen-color-picker-api.aar` | `plugin-api/screen-color-picker-api` | 7,726 bytes | `5136dd3cceb67a878a1e7cf5158821c0970032ad4f0f6011972cf87b0fcef4e9` |

The source modules are maintained in the
[AutoJs6 repository](https://github.com/SuperMonster003/AutoJs6) under MPL-2.0. The screen color
picker API AAR is copied from the exact release output used by the matching host integration. The
common API uses the established Kotlin-compatible plugin baseline: every class used by this plugin
is byte-for-byte identical to the current host build; the newer host artifact only adds the unrelated
`AutoJs6HostSettingsContract` class. This avoids suppressing Kotlin metadata compatibility checks.

To replace an artifact, rebuild the corresponding AutoJs6 module with `assembleRelease`, copy the
release AAR here, update the size and checksum above, and run the unit, lint, debug, release, and
instrumentation builds before committing it.
