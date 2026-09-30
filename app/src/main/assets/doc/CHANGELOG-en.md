******

### Release history

******

# v1.2.0

_2026/09/30_

- `Added` Unify standalone settings with language, night mode, theme color and launcher icon in this order. Appearance follows AutoJs6 by default with system fallback, neutral surfaces and complete control tinting. Choices apply only after confirmation; add a shared HEX/RGB theme preview and make adaptive automatic the launcher default while preserving explicit choices during upgrades.

# v1.1.3

_2026/09/19_

- `Fixed` SDK XML v4 parsing warnings with AGP 9.1 and APK native alignment checks incorrectly triggered by JVM unit-test assembly tasks, using shared build plugins 1.8.3
- `Improved` Raise targetSdk to 37 (Android 17) after compileSdk; the plugin's behavior does not depend on the new target

# v1.1.2

_2026/09/16_

- `Fixed` Release the old overlay permission dialog when the screen is recreated to prevent stale windows and focus conflicts

# v1.1.1

_2026/09/15_

- `Improved` Raise compileSdk to 37 (Android 17); targetSdk stays at 36 until the behavior that depends on the target is verified

# v1.1.0

_2026/09/13_

- `Added` Local release history is available from the interface, with localized text and an English fallback
- `Improved` Release packages are checked for a complete signing configuration, exact APK contents and reproducible documentation
- `Improved` Keep a base PNG launcher asset derived from the existing icon and reference it from the documentation

# v1.0.1

_2026/09/11_

- `Fixed` Prevent a ThemeEnforcement crash by giving Material buttons created from the service/application Context an explicit Material theme
- `Fixed` Show the AutoJs6 drawer tool only while the plugin is enabled in Plugin Center, and restore it after re-enabling
- `Improved` Build verification rejects accidental native dependencies and produces a JSON report

# v1.0.0

_2026/09/01_

- `Added` Run the same APK independently from the launcher or as the AutoJs6 Screen Color Picker extended tool
- `Added` Use a touch-friendly floating picker with a magnified sample, coordinates, HEX, RGB, and HSL
- `Added` Use the contract v1 Binder interface with getInfo, getState, getStartPendingIntent, and stop
- `Added` Use the local interface and documentation in 10 languages with fully offline color processing
- `Improved` Handle overlay, screen capture, foreground service, and notification permission flows explicitly with a stop action
- `Improved` Support the AutoJs6 Wake protocol and minimum host version 5278 with CI and documentation drift checks
