3-Color Picker is a screen color picker: a draggable target ring and a live magnifier select any pixel on the screen, and its color and coordinates can then be copied as HEX or RGB. It works on its own from the launcher, or as the "Screen color picker" tool in the AutoJs6 side drawer; the screen content is processed only on the device.

### Getting started

1. Open 3-Color Picker from the launcher and tap "Start color picker".
2. When prompted, allow it to display over other apps, then allow this screen capture.
3. Switch to the app to sample, drag the target ring near the pixel, then slide on the magnifier disc to fine-tune.
4. Tap the color or coordinate text on the ring to copy it; tap the × at the bottom of the ring to stop.

### Main features

- **Floating picker**: The target ring covers long distances, while the magnifier shows the enlarged pixel grid, the current color and the coordinates; slide on the magnifier disc to move one pixel at a time.
- **Copying colors and coordinates**: Tap the color or the coordinates to copy them, and long-press the color to switch between HEX and RGB; with "Copy numeric value only" on, the # and the R, G, B letters are left out.
- **AutoJs6 side drawer**: Once the plugin is enabled in AutoJs6 "Plugin Center", the side drawer shows a "Screen color picker" switch that starts the picker when turned on and stops it when turned off.
- **Picker settings**: Under "Picker settings" in "Settings", adjust "Magnifier size", "Sampling range", "Fine-tune speed" and "Show grid lines".
- **Appearance**: "Language", "Dark mode" and "Theme color" default to "Follow AutoJs6" and can also be set separately; "Launcher icon" offers several styles.
- **Running notification**: While the picker runs, the notification "Screen color picker is active" is shown with a "Stop" action.
- **Release history and acknowledgements**: "Settings" also opens the "Release history" and the "Design reference and acknowledgements".

### Requirements

- Android 7.0 or later. Using it in the AutoJs6 side drawer requires AutoJs6 6.8.0 or later; using it on its own does not require AutoJs6.
- The permission to display over other apps is used for the floating picker; Android asks you to confirm the screen capture each time the picker starts.
- The notification permission is used for the running status and the "Stop" action; the picker still runs without it.
- It does not use the network or save screenshots, and writes to the clipboard only when you tap to copy.

### FAQ

**When displaying over other apps or screen capture is denied**

The start is cancelled safely and no overlay is left behind. Tap "Start color picker" again to request the permission again.

**Why "Screen color picker" is missing from the AutoJs6 side drawer**

Make sure the plugin is enabled in AutoJs6 "Plugin Center", and authorize it if prompted. When the plugin is disabled in Plugin Center, the entry is hidden.

The design references the floating screen color picker interactions of [MT Manager](https://mt.cc/) (bm.mt.plus) v2.26.9, and we thank its developers; this project is independently maintained, and the acknowledgement implies no affiliation, endorsement or permission. See the project documents [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) and [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md).

Full details on the features and permissions are on the [project homepage](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker).
