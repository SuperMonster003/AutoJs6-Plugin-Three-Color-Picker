# Permission dialog recreation regression

## Cause and change

The failed API 37 run selected the translucent Activity's unfocused base window in Espresso.
The associated Android log also reported `WindowLeaked` from `explainOverlayPermission` after
Activity destruction. The Activity created an AlertDialog without retaining or dismissing it.

The Activity now owns the dialog reference, dismisses its window during destruction, and removes
queued callbacks belonging to that Activity. Dismissal during recreation does not cancel the
ongoing permission request. Espresso explicitly selects the dialog window and excludes the old
decor after recreation. The regression also checks that the old decor is detached, before
confirming that Cancel returns the picker to INACTIVE.

## Evidence, 2026-09-16

- API 37 x86_64, 16 KB emulator: complete device suite 10/10 passed, with no skipped tests.
- The permission-dialog recreation regression passed 20 consecutive fresh instrumentation runs.
  No retry-until-pass loop was used; the runner stopped on any failure or skip.
- Android logcat contained no Screen Color Picker WindowLeaked or root-focus exception during
  these runs.
- JVM tests: 42/42 passed. Debug/instrumentation assembly and lintDebug passed.
- Markdown generation/check passed for all 10 languages. Repository Python checks: 2 passed and
  1 existing check skipped because the strict generator supplies its own validation.
- IDE debugger launch was unavailable for this sibling repository: the active IDE project only
  contained AutoJs6. Diagnosis used the original Android lifecycle/window logs and direct device
  regression tests.
- These tests exercise the overlay explanation and cancellation path. They do not grant actual
  overlay access or MediaProjection capture consent.

Run after building and installing both debug APKs:

```powershell
adb -s <serial> shell am instrument -w -r `
  -e class 'io.github.supermonster003.autojs6.plugin.screencolorpicker.ScreenColorPickerPluginInstrumentedTest#overlayExplanationSurvivesActivityRecreationAndCancelRestoresInactiveState' `
  io.github.supermonster003.autojs6.plugin.screencolorpicker.test/androidx.test.runner.AndroidJUnitRunner
```

Use an unlocked test device without overlay access already granted to the plugin. An explicitly
pre-authorized device skips this path to avoid changing the developer's settings. Local logs
are under `build/verification/plugin-test-repair/` and remain ignored.
