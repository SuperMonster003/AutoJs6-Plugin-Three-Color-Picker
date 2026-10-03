<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap-night/ic_launcher.png?raw=true" media="(prefers-color-scheme: dark)" />
      <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-three-color-picker-ic-launcher" border="0" width="128" />
    </picture>
  </p>
  <h1>3-Color Picker</h1>
  <p>Samples colors anywhere on the screen with a touch-friendly floating picker</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=License"/></a>
  </p>
</div>

******

### Languages

The README is available in the following languages:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- English [en] # current
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ar.md)

******

### Introduction

3-Color Picker is a local color sampling tool designed for touchscreens. Its floating picker pairs a draggable target ring with a live magnifier that shows a magnified pixel grid, the current color, and exact coordinates while keeping finger interaction direct and clear.

The same APK can run independently from the launcher or be discovered by AutoJs6 as the third extended tool in its drawer. Standalone mode does not require AutoJs6.

Version 2.0 uses the new application ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android installs it as a separate app; the old app can remain installed and settings are not migrated automatically. Grant permissions again and use AutoJs6 versionCode 5316 or later for host integration. The AutoJs6 drawer label remains Screen color picker.

******

### Features

- Two entry points: use it independently from the launcher or as an AutoJs6 extended tool
- Touch optimized: drag the target ring for coarse moves and slide on the magnifier to fine-tune
- Live readout: the magnifier shows the sampled color and its coordinates in real time
- Flexible copy: copy the color as HEX or RGB, with an optional numeric-only form, or copy the coordinates
- Controlled lifecycle: stop from the home screen, floating UI, notification, or host tool switch
- Fully offline: screen content is never uploaded and screenshot history is not retained

******

### Standalone use

<picture>
  <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-dark.png?raw=true" media="(prefers-color-scheme: dark)" />
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-light.png?raw=true" alt="3-Color Picker home" width="280" />
</picture>

Use the picker without AutoJs6

Unify standalone settings with language, night mode, theme color and launcher icon in this order. Appearance follows AutoJs6 by default with system fallback, neutral surfaces and complete control tinting. Choices apply only after confirmation; add a shared HEX/RGB theme preview and make adaptive automatic the launcher default while preserving explicit choices during upgrades.:

1. Open 3-Color Picker from the system launcher.
2. Tap Start color picker and follow the explanation to allow display over other apps.
3. Allow the current screen capture in the Android system prompt.
4. Switch to the app you want to sample, drag the target ring to the desired pixel, then slide on the magnifier disc to fine-tune.
5. Tap the color or coordinate text on the magnifier to copy it, long-press the color text to switch formats, or stop the picker at any time.

******

### Use as an AutoJs6 tool

After installation AutoJs6 discovers the app through the formal plugin contract and shows its drawer tool only while the plugin is enabled in Plugin Center:

1. Install the plugin APK. You do not need to open its launcher entry first.
2. Open AutoJs6 Plugin Center and make sure 3-Color Picker is enabled. Authorize the plugin if prompted.
3. Open the AutoJs6 drawer. Screen Color Picker appears as the third Extended tools entry, with its runtime switch off by default.
4. Complete overlay and screen capture consent on first use.
5. Turn off the drawer switch, use the floating UI, or use the notification action to stop.
6. Disabling the plugin in Plugin Center hides the drawer entry. Re-enable the plugin to restore it.

******

### Permissions and privacy

All screen processing happens locally on the device and starts only after an explicit user action.

- Screen capture: Android presents a system consent screen for each capture session
- Display over other apps: used only for the touchable floating picker
- Foreground service and notification: keep an active capture visible and stoppable
- Clipboard: written only when the user taps a copy action
- Network and storage: neither network nor shared storage permission is requested

Denying the overlay or screen capture permission safely cancels startup. Denying notifications shows a warning and startup continues.

******

### Compatibility

Standalone and plugin modes have different minimum requirements.

| Mode | Minimum requirement |
|---|---|
| Standalone app | Android 7.0 (API 24) or later |
| AutoJs6 extended tool | AutoJs6 versionCode 5316 or later |

******

### Plugin contract

The following details are for host and plugin developers. The Binder service and Wake Activity are protected by org.autojs.permission.PLUGIN, while the launcher entry is not gated by that permission.

```text
application id: io.github.supermonster003.autojs6.plugin.three.color.picker
plugin id / INFO category: three-color-picker
engine / capture service category: screen-color-picker
variant: default
service action: org.autojs.plugin.SCREEN_COLOR_PICKER
wake action: org.autojs.plugin.action.WAKE
binder: org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin
contract version: 1
minimum host versionCode: 5316
native libraries: none
```

******

### Release history

#### v2.0.0

_2026/10/03_

- `Hint` Version 2.0 uses the new application ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android installs it as a separate app; the old app can remain installed and settings are not migrated automatically. Grant permissions again and use AutoJs6 versionCode 5316 or later for host integration. The AutoJs6 drawer label remains Screen color picker
- `Improved` Screen Color Picker is now 3-Color Picker, with a new home screen, light/dark artwork and four launcher icon modes
- `Improved` Design reference to MT Manager (bm.mt.plus) v2.26.9, acknowledgements and a rights-concern response policy are available in Settings and the project documentation

#### v1.2.0

_2026/09/30_

- `Added` Unify standalone settings with language, night mode, theme color and launcher icon in this order. Appearance follows AutoJs6 by default with system fallback, neutral surfaces and complete control tinting. Choices apply only after confirmation; add a shared HEX/RGB theme preview and make adaptive automatic the launcher default while preserving explicit choices during upgrades.

#### v1.1.3

_2026/09/19_

- `Fixed` SDK XML v4 parsing warnings with AGP 9.1 and APK native alignment checks incorrectly triggered by JVM unit-test assembly tasks, using shared build plugins 1.8.3
- `Improved` Raise targetSdk to 37 (Android 17) after compileSdk; the plugin's behavior does not depend on the new target

[View the complete CHANGELOG](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-en.md)

******

### Build

Use the checked-in Gradle Wrapper and JDK 21 to run tests, build the debug and instrumentation APKs, and then run lint.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

README files, plugin instructions, and changelogs are generated from JSON copy sources. After editing a source, run both commands below.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### Design reference and acknowledgements

The current design references MT Manager (bm.mt.plus) v2.26.9, especially its floating screen color picker interactions. We thank its developers for their work. This project is independently maintained; this acknowledgement does not imply affiliation, endorsement or permission. Rights holders can contact us through the project Issues. We will review concerns and cooperate with attribution corrections, replacement or removal as appropriate.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

Project documents: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)

******

### License

Project code is licensed under the Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/16kb.md)
