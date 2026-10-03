# AutoJs6 3-Color Picker

3-Color Picker 是一款為觸控螢幕設計的本機螢幕取色工具. 浮動取色器由可拖動的目標環和即時放大鏡組成, 同步顯示放大像素網格, 目前顏色和精確座標, 讓手指操作保持直接清晰.

2.0 版本採用新套件名稱 io.github.supermonster003.autojs6.plugin.three.color.picker. Android 會將其視為獨立應用程式, 舊版可保留並存, 設定不會自動遷移. 新版需重新授權, 插件模式要求 AutoJs6 versionCode 5316 或更高. 宿主抽屜的螢幕取色文案保持不變.

### 獨立使用

1. 從系統啟動器開啟 3-Color Picker.
2. 按下啟動螢幕取色, 並按照說明允許顯示在其他應用程式上層.
3. 在系統提示中允許本次螢幕擷取.
4. 切換到要取色的應用程式, 拖動目標環移動到目標像素, 然後在放大鏡圓盤上滑動微調.
5. 點按放大鏡上的顏色或座標文字即可複製, 長按顏色文字可切換格式, 亦可隨時停止取色器.

### 作為 AutoJs6 工具使用

1. 安裝外掛 APK. 毋須先開啟桌面入口.
2. 開啟 AutoJs6 外掛中心並確認3-Color Picker已啟用. 如有提示, 請先授權外掛.
3. 開啟 AutoJs6 側拉抽屜. 螢幕取色會作為擴充工具的第三個項目出現, 其執行開關預設關閉.
4. 首次使用時完成浮層和螢幕擷取授權.
5. 關閉抽屜開關, 使用浮動介面或通知操作即可停止.
6. 在外掛中心停用外掛會隱藏抽屜項目. 重新啟用外掛後該項目會恢復.

### 權限和私隱

所有螢幕處理均在裝置本機完成, 並只會在使用者明確啟動後運行.

- 螢幕擷取: Android 每次均會顯示系統授權介面
- 顯示在其他應用程式上層: 只用於可觸控的浮動取色介面
- 前景服務和通知: 讓進行中的擷取保持可見並可停止
- 剪貼簿: 只在使用者按下複製時寫入所選顏色
- 網絡和儲存空間: 不申請網絡或共用儲存空間權限

拒絕懸浮視窗或螢幕擷取權限會安全取消啟動. 拒絕通知權限時會顯示提示, 但啟動仍會繼續.

### 設計參考與致謝

目前設計方案參考 MT 管理器 (bm.mt.plus) v2.26.9, 尤其是其浮動螢幕取色互動. 感謝其開發者的設計與投入. 本項目獨立維護, 此致謝不代表關聯, 背書或已獲授權. 如權利人對相關內容有異議, 可透過項目 Issues 聯絡我們. 我們將積極核實溝通, 並視情況配合補充署名, 替換或移除相關內容.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

項目說明: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)
