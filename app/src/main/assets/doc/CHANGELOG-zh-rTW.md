******

### 發行歷史

******

# v1.0.1

_2026/09/11_

- `修正` 為使用 Service/application Context 建立的 Material 按鈕明確套用 Material 主題, 避免 ThemeEnforcement 崩潰
- `修正` 只在外掛中心啟用外掛時顯示 AutoJs6 選單工具, 重新啟用後自動恢復
- `改善` 建置階段阻止意外引入原生相依套件, 並輸出 JSON 校驗報告

# v1.0.0

_2026/09/01_

- `新增` 同一個 APK 可從系統啟動器獨立執行, 也可作為 AutoJs6 的螢幕取色擴充工具使用
- `新增` 提供適合觸控螢幕的浮動取色器, 並同時顯示放大取樣, 座標, HEX, RGB 和 HSL
- `新增` 提供 contract v1 Binder 介面, 包含 getInfo, getState, getStartPendingIntent 和 stop
- `新增` 提供 10 種語言的本機介面和文件, 所有取色處理完全離線完成
- `改善` 明確處理懸浮視窗, 螢幕擷取, 前景服務和通知權限流程, 並可隨時停止
- `改善` 支援 AutoJs6 Wake 協定和最低宿主版本 5278, 並加入 CI 與文件漂移檢查
