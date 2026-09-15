<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>Samples colors anywhere on the screen with a touch-friendly floating picker</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### Languages

The README is available in the following languages:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- English [en] # current
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### Introduction

Screen Color Picker is a local color sampling tool designed for touchscreens. Its floating picker pairs a draggable target ring with a live magnifier that shows a magnified pixel grid, the current color, and exact coordinates while keeping finger interaction direct and clear.

The same APK can run independently from the launcher or be discovered by AutoJs6 as the third extended tool in its drawer. Standalone mode does not require AutoJs6.

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

Use the picker without AutoJs6:

1. Open Screen Color Picker from the system launcher.
2. Tap Start color picker and follow the explanation to allow display over other apps.
3. Allow the current screen capture in the Android system prompt.
4. Switch to the app you want to sample, drag the target ring to the desired pixel, then slide on the magnifier disc to fine-tune.
5. Tap the color or coordinate text on the magnifier to copy it, long-press the color text to switch formats, or stop the picker at any time.

******

### Use as an AutoJs6 tool

After installation AutoJs6 discovers the app through the formal plugin contract and shows its drawer tool only while the plugin is enabled in Plugin Center:

1. Install the plugin APK. You do not need to open its launcher entry first.
2. Open AutoJs6 Plugin Center and make sure Screen Color Picker is enabled. Authorize the plugin if prompted.
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
| AutoJs6 extended tool | AutoJs6 versionCode 5278 or later |

******

### Plugin contract

The following details are for host and plugin developers. The Binder service and Wake Activity are protected by org.autojs.permission.PLUGIN, while the launcher entry is not gated by that permission.

```text
application id: io.github.supermonster003.autojs6.plugin.screencolorpicker
plugin id / engine / category: screen-color-picker
variant: default
service action: org.autojs.plugin.SCREEN_COLOR_PICKER
wake action: org.autojs.plugin.action.WAKE
binder: org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin
contract version: 1
minimum host versionCode: 5278
native libraries: none
```

******

### Release history

#### v1.1.2

_2026/09/16_

- `Fixed` Release the old overlay permission dialog when the screen is recreated to prevent stale windows and focus conflicts

#### v1.1.1

_2026/09/15_

- `Improved` Raise compileSdk to 37 (Android 17); targetSdk stays at 36 until the behavior that depends on the target is verified

#### v1.1.0

_2026/09/13_

- `Added` Local release history is available from the interface, with localized text and an English fallback
- `Improved` Release packages are checked for a complete signing configuration, exact APK contents and reproducible documentation
- `Improved` Keep a base PNG launcher asset derived from the existing icon and reference it from the documentation

[View the complete CHANGELOG](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-en.md)

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

### License

Project code is licensed under the Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/16kb.md)
