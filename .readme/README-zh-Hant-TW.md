<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>使用適合觸控操作的浮動取色器擷取螢幕任意位置的顏色</p>
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
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- 繁體中文 (台灣) [zh-Hant-TW] # 目前
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### 簡介

Screen Color Picker 是一款為觸控螢幕設計的本機螢幕取色工具. 浮動取色器由可拖曳的目標環和即時放大鏡組成, 同步顯示放大像素網格, 目前顏色和精確座標, 讓手指操作保持直接清楚.

同一個 APK 可從桌面獨立啟動, 也可由 AutoJs6 自動探索為側拉式選單中的第三個擴充工具. 獨立模式不需要安裝 AutoJs6.

******

### 功能

- 雙入口: 從桌面獨立使用, 或作為 AutoJs6 擴充工具使用
- 觸控最佳化: 拖曳目標環粗略移動, 在放大鏡上滑動進行微調
- 即時資料: 放大鏡即時顯示取樣顏色及其座標
- 彈性複製: 以 HEX 或 RGB 複製顏色 (可僅保留數值), 也可複製座標
- 可控生命週期: 從首頁, 浮動介面, 通知或主程式工具開關停止
- 完全離線: 不上傳螢幕內容, 不保留螢幕截圖記錄

******

### 獨立使用

不需要 AutoJs6 的使用步驟

啟動器圖示可選擇自適應亮色, 自適應暗色 (預設), 自適應自動或透明背景. 自動模式嘗試跟隨系統主題, 但啟動器可能快取單一配色; 透明圖示可能被啟動器新增背景或遮罩. 切換保持應用程式執行, 顯示重新整理可能需要幾秒鐘.:

1. 從系統啟動器開啟 Screen Color Picker.
2. 點選啟動螢幕取色, 並依照說明允許顯示在其他應用程式上層.
3. 在系統提示中允許本次螢幕擷取.
4. 切換到要取色的應用程式, 拖曳目標環移動到目標像素, 然後在放大鏡圓盤上滑動微調.
5. 點選放大鏡上的顏色或座標文字即可複製, 長按顏色文字可切換格式, 也可隨時停止取色器.

******

### 作為 AutoJs6 工具使用

安裝後 AutoJs6 會透過正式外掛契約自動探索它, 並只會在外掛中心啟用此外掛時顯示選單工具:

1. 安裝外掛 APK. 不需要先開啟桌面入口.
2. 開啟 AutoJs6 外掛中心並確認螢幕取色已啟用. 如有提示, 請先授權外掛.
3. 開啟 AutoJs6 側拉式選單. 螢幕取色會作為擴充工具的第三個項目出現, 其執行開關預設關閉.
4. 首次使用時完成浮層和螢幕擷取授權.
5. 關閉選單開關, 使用浮動介面或通知操作即可停止.
6. 在外掛中心停用外掛會隱藏選單項目. 重新啟用外掛後該項目會恢復.

******

### 權限和隱私

所有螢幕處理都在裝置本機完成, 並且只在使用者明確啟動後執行.

- 螢幕擷取: Android 每次都會顯示系統授權介面
- 顯示在其他應用程式上層: 只用於可觸控的浮動取色介面
- 前景服務和通知: 讓進行中的擷取保持可見並可停止
- 剪貼簿: 只在使用者點選複製時寫入選定顏色
- 網路和儲存空間: 不申請網路或共用儲存空間權限

拒絕懸浮視窗或螢幕擷取權限會安全取消啟動. 拒絕通知權限時會顯示提示, 但啟動仍會繼續.

******

### 相容性

獨立模式與外掛模式有不同的最低要求.

| 模式 | 最低要求 |
|---|---|
| 獨立 App | Android 7.0 (API 24) or later |
| AutoJs6 擴充工具 | AutoJs6 versionCode 5278 or later |

******

### 外掛契約

以下資訊供主程式與外掛開發者核對. Binder 服務與 Wake Activity 受 org.autojs.permission.PLUGIN 保護, 桌面入口不受此權限限制.

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

#### v1.2.0

_2026/09/29_

- `新增` 啟動器圖示可選擇自適應亮色, 自適應暗色 (預設), 自適應自動或透明背景. 自動模式嘗試跟隨系統主題, 但啟動器可能快取單一配色; 透明圖示可能被啟動器新增背景或遮罩. 切換保持應用程式執行, 顯示重新整理可能需要幾秒鐘.

#### v1.1.3

_2026/09/19_

- `修正` AGP 9.1 建置時的 SDK XML v4 解析警告及 JVM 單元測試組裝工作誤觸發 APK 原生程式庫對齊檢查的問題 (共用建置外掛 1.8.3)
- `改善` 繼 compileSdk 之後將 targetSdk 提升到 37 (Android 17), 外掛程式行為不受新目標版本影響

#### v1.1.2

_2026/09/16_

- `修正` 懸浮視窗權限說明在介面重建時釋放舊對話框, 避免視窗殘留和焦點衝突

[檢視完整 CHANGELOG](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-TW.md)

******

### 建置

使用儲存庫隨附的 Gradle Wrapper 和 JDK 21 執行測試, 建置 debug APK 與 instrumentation APK, 然後執行 lint.

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
