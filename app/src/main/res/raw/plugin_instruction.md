Screen Color Picker is a local color sampling tool designed for touchscreens. Its floating picker shows a magnified sample, coordinates, and several color formats while keeping finger interaction direct and clear.

### Standalone use

1. Open Screen Color Picker from the system launcher.
2. Tap Start color picker and follow the explanation to allow display over other apps.
3. Allow the current screen capture in the Android system prompt.
4. Switch to the app you want to sample, tap the floating target to freeze the screen, then drag the crosshair over the snapshot.
5. Copy the desired format or stop the picker at any time.

### Use as an AutoJs6 tool

1. Install the plugin APK. You do not need to open its launcher entry first.
2. Open AutoJs6 Plugin Center and make sure Screen Color Picker is enabled. Authorize the plugin if prompted.
3. Open the AutoJs6 drawer. Screen Color Picker appears as the third Extended tools entry, with its runtime switch off by default.
4. Complete overlay and screen capture consent on first use.
5. Turn off the drawer switch, use the floating UI, or use the notification action to stop.
6. Disabling the plugin in Plugin Center hides the drawer entry. Re-enable the plugin to restore it.

### Permissions and privacy

All screen processing happens locally on the device and starts only after an explicit user action.

- Screen capture: Android presents a system consent screen for each capture session
- Display over other apps: used only for the touchable floating picker
- Foreground service and notification: keep an active capture visible and stoppable
- Clipboard: written only when the user taps a copy action
- Network and storage: neither network nor shared storage permission is requested

Denying the overlay or screen capture permission safely cancels startup. Denying notifications shows a warning and startup continues.
