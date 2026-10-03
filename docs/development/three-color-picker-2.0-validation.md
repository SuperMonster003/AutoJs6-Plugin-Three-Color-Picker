# 3-Color Picker 2.0.0 validation

Validated on 2026-10-03, with versionCode 20 and the dotted application ID
`io.github.supermonster003.autojs6.plugin.three.color.picker`.

Release preparation uses versionCode 21. The first remote CI run found that Pillow's
Windows zlib-ng and Linux zlib encoders produced different PNG bytes for identical
pixels. The generator now uses a controlled RGBA PNG stream with the standard-library
zlib fixed-Huffman strategy. Windows and Ubuntu checks agree byte-for-byte; every
decoded pixel remains identical to the artwork used in the device checks below.

## Identity and host

- Product title: `3-Color Picker`; root project: `autojs6-plugin-three-color-picker`.
- Plugin ID and common INFO category: `three-color-picker`.
- Functional API package, Binder descriptor, four transaction positions, capture service
  action/category and engine remain the existing screen-color-picker contract v1.
- The synchronized host API requires AutoJs6 versionCode 5316 or later. The host's
  package query, official installation catalog and transparent icon allowlist use the new package.
- No host drawer strings or drawer implementation were changed. The Simplified Chinese
  label remains `屏幕取色`.
- The former package can remain installed. Settings are not migrated automatically.

## Automated checks

| Check | Result |
| --- | --- |
| Ten-language Markdown generation, read-only check and Windows wrapper | Passed; 36 outputs |
| Icon generation and read-only check | Passed; 14 deterministic outputs |
| Python repository/icon tests | 5 passed; 1 inapplicable legacy-generator test skipped |
| Plugin JVM tests | 52 passed |
| Plugin debug APK and instrumentation APK | Built with the default debug signer |
| Explicit official debug/test build | Built with the existing release signer and `.test.official` test package |
| Signed release collection | Passed, including signature and exact APK-set verification |
| Plugin lintDebug | 0 errors, 19 warnings; dependency updates, drawable/collection suggestions and unused resources |
| Shared API release AAR and unit tests | Built; 3 tests passed |
| AutoJs6 app debug assembly and targeted JVM tests | Built; 12 tests passed |
| Official index generator tests | 46 passed |

The official index was regenerated from GitHub after the repository rename: all 62
published entries passed validation. Only this repository's entry and links were
applied to the checked-in index. Its latest published release remains v1.1.3 with the
original package, APK name, version, signer and checksum; no 2.0.0 publication was invented.

The platform acceptance build used the checked-in Wrapper, two workers, the prescribed
Temurin vendor properties, and disabled automatic build-number/time changes. The
application contains no native libraries. The supplied icon originals are 387 x 387;
generated transparent artwork uses identical alpha for both themes. Final antialiased
adaptive pixels fit the 66 dp safe circle. Android density scaling can round rendered
edge channels by one level; tests check unscaled source pixels exactly and rendered
pixels with that bounded tolerance.

## Device checks

The same 20 selected instrumentation tests passed on an Android 7.0 / API 24 x86
emulator and a Sony XQ-DQ72 / API 33 physical device. They cover the home screen,
Arabic RTL, light/dark artwork, four launcher modes and actual alias resource IDs,
settings confirm/cancel behavior, theme colors, permission-flow cancellation and
recreation, service discovery, Binder calls, Wake/component protection, and INFO
metadata without starting capture.

Two additional home-screen tests passed on API 24 with 1.5 font scale and landscape
orientation. Temporary appearance choices and launcher states were restored. System
rotation settings were restored exactly. API 24 normalized its absent font-scale key
to the equivalent default `1.0` when the temporary scale was removed.

On API 24, a manual smoke check used the visible start button and Android's capture
consent dialog. The floating magnifier displayed a sampled color and coordinates;
reopening the home screen showed Active and Stop color picker. Stopping returned the
home screen to Ready to pick and released MediaProjection. Temporary overlay access
was restored to its default mode. No old application or launcher data was removed.

The API 33 build reported an available official host appearance snapshot; the ordinary
debug build on API 24 exercised the unavailable-host fallback. This does not claim an
end-to-end test of the newly built host drawer on a physical device.

The [light](../images/home-light.png) and [dark](../images/home-dark.png) home screenshots
were captured from the running API 33 app after its window transition completed.

## Limits

No physical ColorOS activation, Android 17 / API 37 capture-consent behavior,
TalkBack audio session, split-screen interaction or exhaustive vendor-launcher cache
test was run. The package rename is a new installation, so an old-package in-place
upgrade or settings migration is not claimed. No APK or tag was published by this change.
