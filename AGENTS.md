# AutoJs6 Screen Color Picker Plugin Repository Guide

## Scope and identity

This repository builds one Android APK that works in two modes:

- AutoJs6 discovers and controls it through a permission-protected Binder service.
- A user can launch the app and use the screen color picker without installing AutoJs6.

The following identities are stable contract values and must be changed together if the shared API ever changes:

| Item | Value |
|---|---|
| Project | `AutoJs6-Plugin-Screen-Color-Picker` |
| Gradle root | `autojs6-plugin-screen-color-picker` |
| App title | `Screen Color Picker` |
| Application ID | `io.github.supermonster003.autojs6.plugin.screencolorpicker` |
| API package | `org.autojs.plugin.screencolorpicker.api` |
| Plugin ID / engine / category | `screen-color-picker` |
| Variant | `default` |
| Service action | `org.autojs.plugin.SCREEN_COLOR_PICKER` |
| Binder interface | `IScreenColorPickerPlugin` |
| Contract version | `1` |
| Minimum host versionCode | `5278` |

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

The final manifest has five intentional components:

- `MainActivity`: exported Launcher entry, with no AutoJs6 plugin permission. It must work when AutoJs6 is absent.
- `PickerRequestActivity`: non-exported, translucent permission flow launched by an explicit `PendingIntent`.
- `WakeActivity`: exported, `Theme.NoDisplay`, protected by `org.autojs.permission.PLUGIN`, responds to `org.autojs.plugin.action.WAKE`, and finishes immediately.
- `ScreenColorPickerPluginService`: exported Binder service protected by `org.autojs.permission.PLUGIN`, with the exact shared action and category.
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
