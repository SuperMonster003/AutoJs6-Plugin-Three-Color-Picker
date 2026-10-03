# AutoJs6 3-Color Picker Plugin Repository Guide

## Scope and identity

This repository builds one Android APK that works in two modes:

- AutoJs6 discovers and controls it through a permission-protected Binder service.
- A user can launch the app and use the screen color picker without installing AutoJs6.

The following identities are stable contract values and must be changed together if the shared API ever changes:

| Item | Value |
|---|---|
| Project | `AutoJs6-Plugin-Three-Color-Picker` |
| Gradle root | `autojs6-plugin-three-color-picker` |
| App title | `3-Color Picker` |
| Application ID | `io.github.supermonster003.autojs6.plugin.three.color.picker` |
| API package | `org.autojs.plugin.screencolorpicker.api` |
| Plugin ID / INFO category | `three-color-picker` |
| Engine / capture service category | `screen-color-picker` |
| Variant | `default` |
| Service action | `org.autojs.plugin.SCREEN_COLOR_PICKER` |
| Binder interface | `IScreenColorPickerPlugin` |
| Contract version | `1` |
| Minimum host versionCode | `5316` |

The shared contract comes from `libs/screen-color-picker-api.aar`. Do not recreate its AIDL, constants, `PluginInfo`, or package names in this repository. The host and plugin must consume the same API build.

## Session safety

- Start with `git status --short`, the current branch, and recent commits.
- Treat existing uncommitted files as user work. Do not overwrite or roll them back.
- Search for deeper `AGENTS.md` files before editing a subtree.
- Do not use `git reset --hard` or `git checkout -- <path>`.
- Follow an explicit task instruction not to commit. Otherwise use Conventional Commits and keep each commit buildable.
- Before handoff, review `git diff --check`, `git diff`, and `git status --short`.

## Build rules

- Keep `io.github.supermonster003.autojs6-platform-versions` at the online Maven version declared in `settings.gradle.kts`; never use `mavenLocal()`.
- Build with the checked-in Gradle Wrapper and the JDK selected by the convention build.
- Read SDK and app versions from `version.properties`; do not duplicate them in modules.
- This is a JVM-only Android app. Do not add ABI splits, CMake, NDK, native ABI claims, or architecture-specific APKs without a real product requirement.
- `PluginInfo.supportedAbis` must remain empty while the APK has no native runtime.
- Keep Java and Kotlin source encoding at UTF-8.
- Add dependencies only when used. Prefer Google Maven and Maven Central.
- Never commit `local.properties`, `sign.properties`, keystores, passwords, tokens, build outputs, or generated APKs.
- A release collection task must fail when signing configuration is absent or incomplete. An unsigned APK is not a release artifact.

## Component and permission contract

The final manifest has seven real Activity/service components, four selectable launcher aliases and one internal update receiver:

- `MainActivity`: stable exported UI Activity, with no AutoJs6 plugin permission. It must work when AutoJs6 is absent. Four stable `launcher.*IconAlias` entries target it; only the selected alias has an enabled MAIN/LAUNCHER entry.
- `PickerRequestActivity`: non-exported, translucent permission flow launched by an explicit `PendingIntent`.
- `WakeActivity`: exported, `Theme.NoDisplay`, protected by `org.autojs.permission.PLUGIN`, responds to `org.autojs.plugin.action.WAKE`, and finishes immediately.
- `ThreeColorPickerPluginService`: exported Binder service protected by `org.autojs.permission.PLUGIN`, with the exact shared action and category.
- `ProjectionForegroundService`: non-exported foreground service with type `mediaProjection`.

Keep `org.autojs.plugin.WAKE_ACTIVITY` metadata resolvable to the real Wake Activity. Do not move work, permission prompts, network calls, or model initialization into `WakeActivity`.

Review `android:exported` and `android:permission` for every component. Do not add an exported provider, broadcast receiver, file path entry, implicit text receiver, or unprotected control service without a documented contract and threat review.

## Binder contract

Contract v1 transaction order is append-only:

1. `PluginInfo getInfo()`
2. `int getState()`
3. `PendingIntent getStartPendingIntent()`
4. `boolean stop()`

Do not reorder or change existing methods. Append future methods only after capability negotiation and a synchronized host/API/plugin update.

States are `INACTIVE = 0`, `STARTING = 1`, `ACTIVE = 2`, and `STOPPING = 3`. Repeated start/stop calls, process recreation, service destruction, and concurrent UI/Binder control must have deterministic behavior.

The Binder service and Launcher UI must share the same application-layer picker controller. The UI must not bind to its own Binder service merely to call the same process.

`PluginInfo` must report the installed package version, localized description, local instruction resource, author/collaborators, stable identity fields, empty ABI array, minimum host version, and contract version. Keep Android resource lookup separate from pure metadata assembly so the mapping can be unit tested.

## Screen capture, overlays, and privacy

- Request MediaProjection consent through the visible internal Activity and only after an explicit user action.
- Request overlay access only when needed and explain why before opening system settings.
- Start the media-projection foreground service within the platform deadline and keep its persistent notification accurate.
- Do not retain a MediaProjection token, captured frame, sampled pixel, or clipboard content longer than required.
- Never log screen contents, frames, private UI text, Binder tokens, or private URIs.
- The app is offline. Do not add `INTERNET`, analytics, accounts, remote configuration, or hidden downloads.
- Do not read the clipboard automatically. Copy only after a user action.
- Do not maintain a permanent color or screenshot history by default.
- Stop projection, release virtual displays/images, remove overlay windows, and close resources on stop, revocation, failure, and process teardown.

## UI and accessibility

- Follow system language, day/night mode, font scale, layout direction, and safe insets.
- Keep the touch target usable with one hand and at least 48 dp. The magnifier/target must not hide the sampled point.
- Every icon-only control and floating element needs an accurate content description.
- Support TalkBack focus order, RTL, large text, rotation, split screen, and process restoration.
- Avoid fixed screen coordinates. Normalize display rotation, density, cutouts, and captured-buffer coordinates before sampling.
- Use the Material theme already defined under `res/values`; do not add a second unrelated design system.

## Strings and resources

All user-visible strings must exist in:

- default English `values/`
- explicit English `values-en/`
- Arabic, Spanish, French, Japanese, Korean, Russian
- Simplified Chinese, Hong Kong Traditional Chinese, Taiwan Traditional Chinese

Default and explicit English shared entries must be identical. Keep `<string>` entries sorted by `name`, placeholder types/counts equal in every locale, and `plugin_description` free of terminal punctuation. Use ASCII punctuation and three ASCII dots for ellipses.

`app_name`, author, and build date are non-translatable build resources. Plugin IDs, variant, contract version, and minimum host version come from the shared API contract and must not be recreated as locale resources.

The adaptive launcher icon and monochrome icon must remain valid at their resource API levels. Notification icons must be opaque white silhouettes with a transparent background.

## Generated documentation

Sources of truth are:

- `.readme/common.json`
- `.readme/lang_*.json`
- `.readme/template_readme.md`
- `.readme/template_plugin_instruction.md`
- `.changelog/lang_*.json`
- `.changelog/template_changelog.md`

Generated files are README translations, root `README.md`, localized `raw*/plugin_instruction.md`, and localized changelog assets. Never edit those generated files by hand.

After changing documentation sources, resources, or `VERSION_NAME`, run:

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

The current version must be the first changelog key in every language, and all languages must use the same version/category/item shape.

## Test baseline

Local unit tests must cover:

- PluginInfo mapping and capabilities
- state transitions and idempotent start/stop
- coordinate and rotation mapping
- color formatting and alpha handling
- null, empty, invalid, boundary, and concurrent paths
- resource cleanup and projection revocation

Android instrumentation must cover:

- exact service discovery by shared action/category
- explicit bind and exact Binder descriptor
- installed package metadata and localized PluginInfo
- Wake metadata, action/category, exported state, and permission
- Launcher entry without plugin permission
- non-exported request Activity and projection service
- one real Binder happy path plus invalid and repeated control
- Activity launch independent of AutoJs6

Run the smallest sufficient verification, normally:

```powershell
py .python/generate_markdown.py --check
./gradlew.bat :app:testDebugUnitTest
./gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest
./gradlew.bat :app:lintDebug
```

Use a device or emulator for instrumentation whenever available. Do not claim MediaProjection or special-device behavior was validated when only static tests ran.

## CI and handoff

- Build CI runs unit tests, lint, debug APK assembly, instrumentation APK assembly, and a real emulator test.
- Markdown CI runs the Windows batch check.
- Keep workflow permissions at `contents: read` unless an explicitly reviewed publishing workflow needs more.
- At handoff, state exactly what passed, what was not run, and whether device permission, overlay, and projection flows were exercised.

## Shared repository standard (2026-09-13)

Read [the complete repository standard](docs/development/repository-standard.md) before changing this repository. It is part of this repository guidance. Existing product-specific constraints above remain in force.

This APK contains ABI-independent managed code; native alignment verification rejects native dependencies. No ABI splits are appropriate. Release collection is `:app:appendDigestToReleasedFiles` and verifies the exact signed APK set. Do not claim physical ColorOS activation, projection consent or host output publication was tested unless it was actually exercised.

Run `.python/check_markdown.bat`, `py -3 -m unittest discover -s .python/tests`, and the Gradle Wrapper with `--max-workers=2`. Platform acceptance: `--no-daemon -Djava.vendor="Eclipse Adoptium" -Djava.vendor.version=Temurin-21.0.12.1+1 :app:assembleDebug :app:testDebugUnitTest`. Disable version auto-increment while checking a prepared commit. Before every commit set VERSION_BUILD to `git rev-list --count HEAD` plus one.

## Selectable launcher icons

- Expose adaptive light, adaptive dark, adaptive automatic (default), and transparent modes in one settings row. Explain automatic/transparent launcher caching and background limitations.
- Keep all four `launcher.*IconAlias` component names stable. Keep MainActivity enabled for existing explicit intents. Enable the new alias before disabling the old one with DONT_KILL_APP, migrate mutable shortcut ownership, and restore previous states if switching fails.
- Regenerate the separate launcher resources with `py .python/generate_launcher_icons.py` (Pillow 12.3.0); run its read-only `--check`. The maintainer-provided 387 x 387 light/dark PNG sources are preserved unchanged in `.python/icons/three-color-picker-ic-launcher-{light,dark}.png`. Both share the same alpha. Generate 432 px monochrome artwork from the light alpha; UI/adaptive sizes follow Optical geometry v1, optical offsets are zero. Validate final antialiased pixels against the safe circle. `ic_launcher` is always a transparent BitmapDrawable, with no adaptive XML override.
- Fixed dark uses glyph #D8D8D8/background #212121; fixed light uses glyph #272727/background #FAFAFA. Auto must have an independent resource ID: PackageManager eagerly resolves values aliases when parsing activity icons. Supply default dark and notnight light legacy bitmap wrappers plus matching default-v26 and notnight-v26 adaptive XML. Never put a legacy night PNG ahead of an adaptive v26 resource with the same name.
- Run LauncherIconResourceTest and LauncherIconSelectionTest on API 24 and a modern API. Tests restore exact component states and remove only their own temporary shortcuts; do not clear user or launcher data.
- PNG encoding uses an explicit RGBA row filter and the Python standard-library zlib fixed-Huffman strategy. Do not use Pillow's bundled PNG compressor for generated outputs: Windows zlib-ng and Linux zlib wheels produce different bytes for identical pixels. Verify `--check` on both systems when changing the encoder.

## Standalone settings standard (2026-09-29)

Read `../AUTOJS6_PLUGIN_STANDALONE_SETTINGS_AGENTS.md` for the maintainer-approved common style. All four appearance settings are implemented here: language, night mode, theme color, launcher icon. Use neutral surfaces, 16/14 sp text, 72 dp minimum two-line rows, 24 dp horizontal padding and matching outline icons. Choices and HEX/RGB preview remain drafts until OK; Cancel must have no persistence side effect. Default appearance follows AutoJs6 with system/default fallback, and the launcher default is Auto. The update-only receiver normalizes component state without changing explicit choices or starting business work.

Appearance snapshots are read asynchronously through the local pinned official common API and validated before use. Never bypass host signing/enable checks; unavailable snapshots use an honest fallback. Theme coverage includes disabled states, radio/check/switch, input cursor/selection, buttons, sliders, menus and dynamically created rows. Preserve content-specific colors such as sampled pixels. Run AppearancePolicyTest, ThemeColorValueTest, SettingsAppearanceTest and LauncherIconSelectionTest, including cancel/confirm, contrast and old-default migration.

The default debug signer is intentionally retained for existing debug installations. `-PofficialUiValidation=true` signs the debug/probe artifacts with the existing release configuration and uses a distinct `.test.official` test package. It is for local host-appearance verification only; never use it to replace an installed package with a different signer. Preserve exact artifact paths/hashes for both signing modes.

`ThreeColorPickerInfoService` is the permission-protected common INFO endpoint needed for official host appearance discovery. It reuses PluginRuntimeInfo and never starts capture. The original SCREEN_COLOR_PICKER Binder contract remains unchanged; verify both discovery paths and keep the new service protected by org.autojs.permission.PLUGIN.

## 3-Color Picker 2.0 identity and design (2026-10-03)

Read `../AUTOJS6_PLUGIN_THREE_SERIES_RENAME_AGENTS.md` and `../AUTOJS6_PLUGIN_BLACK_N_WHITE_ADAPTIVE_ICON_AGENTS.md` alongside the standalone settings standard. The maintainer chose the dotted application ID and version 2.0.0. Android treats this as a new app; the old `io.github.supermonster003.autojs6.plugin.screencolorpicker` may remain installed, with no automatic settings migration or uninstall.

Product classes use ThreeColorPicker; capture mechanics retain ScreenColorPicker API naming. The shared AAR and host have synchronized package/ID/minimum-host constants. The AIDL descriptor, transaction order, service action/category, engine and protocol version 1 remain functional contract names. The AutoJs6 drawer text, including Simplified Chinese `屏幕取色`, must not change.

The home screen uses the transparent themed mipmap, a readable session status/action, and three usage steps. Settings preserves the common appearance workflow and adds localized design acknowledgements. `docs/design-reference.md` records MT Manager (`bm.mt.plus`) v2.26.9 as a design reference; `RIGHTS_AND_TAKEDOWN.md` records rights-concern contact and cooperation. Neither attribution nor MPL-2.0 grants third-party permission. Keep these links in every generated README and plugin instruction.

## Optical icon standard (2026-10-03)

- Follow `../AUTOJS6_PLUGIN_BLACK_N_WHITE_ADAPTIVE_ICON_AGENTS.md` for every standalone plugin, including the Plugin Center. `.python/icon_geometry.py` v1 is a self-contained copy of the common geometry algorithm; keep its implementation identical across the standalone plugins. Never read sibling checkouts during a build.
- Derive size from the equal-weight combination of visible bounding-box area (alpha >= 16) and alpha-weighted ink area. Target visible size is 0.52 of the canvas, with only documented optical corrections in 0.94-1.06. The adaptive ratio is always the UI ratio multiplied by 72/108. This supersedes older hardcoded UI/adaptive widths in historical notes. Preserve aspect ratio, optical placement and final nonzero-alpha safety checks.
- Current derived widths: UI 0.6215, adaptive 0.4143 (rounded documentation values, not generation constants). Optical scale=1.00 and zero offsets.
- Generate `mipmap/ic_plugin_center.png` and its night counterpart from the same geometry as the transparent UI/launcher mode. They are transparent neutral artwork for installed and catalog entries, independent of the active launcher alias. Keep them through `raw/keep_plugin_center_icon.xml`. Existing separate brand assets retain their original purpose.
- Black, white and neutral grayscale are allowed for every plugin without per-plugin approval. Pure silhouettes default to #272727 / #D8D8D8; shaded artwork may preserve meaningful tonal details with R=G=B and matching day/night alpha. Stamp Mail is one example, not an exception. Keep light-theme artwork dark enough and dark-theme artwork light enough to remain legible. Do not introduce a filled background into the Plugin Center assets.
- Run the icon generator and its read-only `--check`, `.python/tests/test_icon_geometry.py`, existing icon regressions, and review the full set at 36/48/64 px in both themes and in launcher masks. `.github/workflows/icons.yml` verifies Windows/Linux reproducibility. Synthetic previews do not replace actual launcher verification.
