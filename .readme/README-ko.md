<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>터치하기 쉬운 플로팅 선택기로 화면 어디서나 색상을 추출합니다</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### 언어

README는 다음 언어로 제공됩니다:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- 한국어 [ko] # 현재
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### 소개

Screen Color Picker는 터치 화면을 위해 설계된 로컬 색상 추출 도구입니다. 플로팅 선택기는 드래그할 수 있는 대상 링과 실시간 확대경으로 구성되어 확대된 픽셀 격자, 현재 색상, 정확한 좌표를 표시하며 손가락으로 직접 명확하게 조작할 수 있습니다.

동일한 APK를 런처에서 독립적으로 실행하거나 AutoJs6 서랍의 세 번째 확장 도구로 자동 검색할 수 있습니다. 독립 모드에는 AutoJs6가 필요하지 않습니다.

******

### 기능

- 두 가지 진입점: 런처에서 독립 사용 또는 AutoJs6 확장 도구로 사용
- 터치 최적화: 대상 링을 드래그해 크게 이동하고 확대경 위에서 밀어 미세 조정
- 즉시 데이터: 확대경이 추출 중인 색상과 좌표를 실시간으로 표시
- 유연한 복사: 색상을 HEX 또는 RGB로 복사하고 (숫자만 복사 가능) 좌표도 복사
- 제어 가능한 수명 주기: 홈, 플로팅 UI, 알림 또는 호스트 도구 스위치에서 중지
- 완전한 오프라인: 화면 내용을 업로드하지 않고 스크린샷 기록을 보관하지 않음

******

### 독립 사용

AutoJs6 없이 선택기 사용

런처 아이콘은 적응형 밝게, 적응형 어둡게 (기본값), 적응형 자동 또는 투명 배경으로 설정할 수 있습니다. 자동 모드는 시스템 테마를 따르려고 시도하지만 런처가 하나의 색상을 캐시할 수 있습니다. 투명 아이콘에도 배경이나 마스크가 추가될 수 있습니다. 전환해도 앱은 계속 실행되며 반영까지 몇 초가 걸릴 수 있습니다.:

1. 시스템 런처에서 Screen Color Picker를 엽니다.
2. 색상 선택기 시작을 누르고 안내에 따라 다른 앱 위에 표시를 허용합니다.
3. Android 시스템 알림에서 현재 화면 캡처를 허용합니다.
4. 색상을 가져올 앱으로 전환하고 대상 링을 원하는 픽셀로 드래그한 다음 확대경 원판 위에서 밀어 미세 조정합니다.
5. 확대경의 색상 또는 좌표 텍스트를 눌러 복사하고 색상 텍스트를 길게 눌러 형식을 전환하며 언제든지 선택기를 중지할 수 있습니다.

******

### AutoJs6 도구로 사용

설치 후 AutoJs6는 공식 플러그인 계약을 통해 앱을 검색하며 플러그인 센터에서 플러그인이 활성화된 동안에만 서랍 도구를 표시합니다:

1. 플러그인 APK를 설치합니다. 런처 항목을 먼저 열 필요가 없습니다.
2. AutoJs6 플러그인 센터를 열고 Screen Color Picker가 활성화되어 있는지 확인합니다. 메시지가 표시되면 플러그인을 승인합니다.
3. AutoJs6 서랍을 엽니다. Screen Color Picker는 확장 도구의 세 번째 항목으로 표시되며 실행 스위치는 기본적으로 꺼져 있습니다.
4. 처음 사용할 때 오버레이 및 화면 캡처 동의를 완료합니다.
5. 서랍 스위치를 끄거나 플로팅 UI 또는 알림 작업을 사용하여 중지합니다.
6. 플러그인 센터에서 플러그인을 비활성화하면 서랍 항목이 숨겨집니다. 다시 활성화하면 항목이 복원됩니다.

******

### 권한 및 개인정보 보호

모든 화면 처리는 기기에서 로컬로 수행되며 사용자의 명시적 작업 후에만 시작됩니다.

- 화면 캡처: Android가 각 캡처 세션마다 시스템 동의 화면 표시
- 다른 앱 위에 표시: 터치 가능한 플로팅 선택기에만 사용
- 포그라운드 서비스 및 알림: 활성 캡처를 표시하고 중지 가능하게 유지
- 클립보드: 사용자가 복사 작업을 누를 때만 기록
- 네트워크 및 저장소: 네트워크 또는 공유 저장소 권한을 요청하지 않음

다른 앱 위에 표시 또는 화면 캡처 권한을 거부하면 시작이 안전하게 취소됩니다. 알림 권한을 거부하면 경고를 표시하고 시작은 계속됩니다.

******

### 호환성

독립 모드와 플러그인 모드의 최소 요구 사항은 서로 다릅니다.

| 모드 | 최소 요구 사항 |
|---|---|
| 독립 앱 | Android 7.0 (API 24) or later |
| AutoJs6 확장 도구 | AutoJs6 versionCode 5278 or later |

******

### 플러그인 계약

다음 정보는 호스트 및 플러그인 개발자를 위한 것입니다. Binder 서비스와 Wake Activity는 org.autojs.permission.PLUGIN으로 보호되지만 런처 항목에는 해당 권한이 필요하지 않습니다.

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

### 릴리스 기록

#### v1.2.0

_2026/09/29_

- `추가` 런처 아이콘은 적응형 밝게, 적응형 어둡게 (기본값), 적응형 자동 또는 투명 배경으로 설정할 수 있습니다. 자동 모드는 시스템 테마를 따르려고 시도하지만 런처가 하나의 색상을 캐시할 수 있습니다. 투명 아이콘에도 배경이나 마스크가 추가될 수 있습니다. 전환해도 앱은 계속 실행되며 반영까지 몇 초가 걸릴 수 있습니다.

#### v1.1.3

_2026/09/19_

- `수정` 공유 빌드 플러그인 1.8.3을 통해 AGP 9.1의 SDK XML v4 파싱 경고 및 JVM 단위 테스트 조립 작업에서 APK 네이티브 라이브러리 정렬 검사가 잘못 실행되는 문제 해결
- `개선` compileSdk 에 이어 targetSdk 를 37 (Android 17) 로 올리며, 플러그인 동작은 새 대상 버전의 영향을 받지 않음

#### v1.1.2

_2026/09/16_

- `수정` 화면을 다시 생성할 때 이전 오버레이 권한 설명 대화상자를 해제하여 창 잔류와 포커스 충돌 방지

[전체 CHANGELOG 보기](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-ko.md)

******

### 빌드

저장소의 Gradle Wrapper와 JDK 21로 테스트를 실행하고 debug APK 및 instrumentation APK를 빌드한 다음 lint를 실행합니다.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

README, 플러그인 안내 및 changelog는 JSON 문서 원본에서 생성됩니다. 원본을 수정한 후 다음 두 명령을 실행하세요.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### 라이선스

프로젝트 코드는 Mozilla Public License 2.0으로 제공됩니다.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/16kb.md)
