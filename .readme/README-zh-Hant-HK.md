<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>使用適合觸控操作的浮動取色器擷取螢幕任何位置的顏色</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### 語言

README 目前提供以下語言:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- 繁體中文 (香港) [zh-Hant-HK] # 目前
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### 簡介

Screen Color Picker 是一款為觸控螢幕設計的本機螢幕取色工具. 浮動取色器由可拖動的目標環和即時放大鏡組成, 同步顯示放大像素網格, 目前顏色和精確座標, 讓手指操作保持直接清晰.

同一個 APK 可從桌面獨立啟動, 亦可由 AutoJs6 自動探索為側拉抽屜中的第三個擴充工具. 獨立模式毋須安裝 AutoJs6.

******

### 功能

- 雙入口: 從桌面獨立使用, 或作為 AutoJs6 擴充工具使用
- 觸控最佳化: 拖動目標環粗略移動, 在放大鏡上滑動進行微調
- 即時資料: 放大鏡即時顯示取樣顏色及其座標
- 彈性複製: 以 HEX 或 RGB 複製顏色 (可僅保留數值), 亦可複製座標
- 可控生命週期: 從主頁, 浮動介面, 通知或主程式工具開關停止
- 完全離線: 不上載螢幕內容, 不保留螢幕截圖記錄

******

### 獨立使用

毋須 AutoJs6 的使用步驟:

1. 從系統啟動器開啟 Screen Color Picker.
2. 按下啟動螢幕取色, 並按照說明允許顯示在其他應用程式上層.
3. 在系統提示中允許本次螢幕擷取.
4. 切換到要取色的應用程式, 拖動目標環移動到目標像素, 然後在放大鏡圓盤上滑動微調.
5. 點按放大鏡上的顏色或座標文字即可複製, 長按顏色文字可切換格式, 亦可隨時停止取色器.

******

### 作為 AutoJs6 工具使用

安裝後 AutoJs6 會透過正式外掛契約自動探索它, 並只會在外掛中心啟用此外掛時顯示抽屜工具:

1. 安裝外掛 APK. 毋須先開啟桌面入口.
2. 開啟 AutoJs6 外掛中心並確認螢幕取色已啟用. 如有提示, 請先授權外掛.
3. 開啟 AutoJs6 側拉抽屜. 螢幕取色會作為擴充工具的第三個項目出現, 其執行開關預設關閉.
4. 首次使用時完成浮層和螢幕擷取授權.
5. 關閉抽屜開關, 使用浮動介面或通知操作即可停止.
6. 在外掛中心停用外掛會隱藏抽屜項目. 重新啟用外掛後該項目會恢復.

******

### 權限和私隱

所有螢幕處理均在裝置本機完成, 並只會在使用者明確啟動後運行.

- 螢幕擷取: Android 每次均會顯示系統授權介面
- 顯示在其他應用程式上層: 只用於可觸控的浮動取色介面
- 前景服務和通知: 讓進行中的擷取保持可見並可停止
- 剪貼簿: 只在使用者按下複製時寫入所選顏色
- 網絡和儲存空間: 不申請網絡或共用儲存空間權限

拒絕懸浮視窗或螢幕擷取權限會安全取消啟動. 拒絕通知權限時會顯示提示, 但啟動仍會繼續.

******

### 兼容性

獨立模式與外掛模式有不同的最低要求.

| 模式 | 最低要求 |
|---|---|
| 獨立 App | Android 7.0 (API 24) or later |
| AutoJs6 擴充工具 | AutoJs6 versionCode 5278 or later |

******

### 外掛契約

以下資料供主程式與外掛開發者核對. Binder 服務與 Wake Activity 受 org.autojs.permission.PLUGIN 保護, 桌面入口不受此權限限制.

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

### 發行歷史

#### v1.1.1

_2026/09/15_

- `改善` 將 compileSdk 提升到 37 (Android 17), targetSdk 保持 36, 待依賴目標版本的行為驗證後再提升

#### v1.1.0

_2026/09/13_

- `新增` 介面提供本地發行歷史, 支援多語言及英語回退
- `改善` 校驗發行簽署設定, 預期 APK 集合與可重現文件
- `改善` 從現有圖示保留基礎 PNG 啟動器資源, 並在文件中引用

#### v1.0.1

_2026/09/11_

- `修正` 為使用 Service/application Context 建立的 Material 按鈕明確套用 Material 主題, 避免 ThemeEnforcement 崩潰
- `修正` 只在外掛中心啟用外掛時顯示 AutoJs6 抽屜工具, 重新啟用後自動恢復
- `改善` 建置階段阻止意外引入原生相依套件, 並輸出 JSON 校驗報告

[查看完整 CHANGELOG](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-HK.md)

******

### 建構

使用倉庫隨附的 Gradle Wrapper 和 JDK 21 執行測試, 建構 debug APK 與 instrumentation APK, 然後執行 lint.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

README, 外掛說明和 changelog 由 JSON 文案來源產生. 修改來源後執行以下兩個指令.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### 授權條款

專案程式碼採用 Mozilla Public License 2.0 授權.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/16kb.md)
