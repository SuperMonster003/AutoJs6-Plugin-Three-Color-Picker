<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/images/icon.svg?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>タッチ操作しやすいフローティングピッカーで画面上の色を取得します</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### 言語

README は次の言語で利用できます:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- 日本語 [ja] # 現在
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### 概要

Screen Color Picker はタッチ画面向けのローカル色取得ツールです. フローティングピッカーはドラッグできるターゲットリングとリアルタイムの拡大鏡で構成され, 拡大ピクセルグリッド, 現在の色, 正確な座標を表示し, 指で直接わかりやすく操作できます.

同じ APK をランチャーから単独で使用でき, AutoJs6 のドロワーでは 3 番目の拡張ツールとして自動検出できます. 単独モードでは AutoJs6 は不要です.

******

### 機能

- 2 つの入口: ランチャーから単独使用, または AutoJs6 拡張ツールとして使用
- タッチ最適化: ターゲットリングをドラッグして大まかに移動し, 拡大鏡の上をスライドして微調整
- 即時データ: 拡大鏡に取得中の色とその座標をリアルタイム表示
- 柔軟なコピー: HEX または RGB で色をコピー (数値のみも可能), 座標のコピーにも対応
- 制御可能なライフサイクル: ホーム, フローティング UI, 通知, ホストのスイッチから停止
- 完全オフライン: 画面内容を送信せず, スクリーンショット履歴を保存しない

******

### 単独で使用

AutoJs6 なしでピッカーを使用する手順:

1. システムランチャーから Screen Color Picker を開きます.
2. カラーピッカーを開始を押し, 説明に従って他のアプリの上への表示を許可します.
3. Android のシステム画面で今回の画面キャプチャを許可します.
4. 採色するアプリへ切り替え, ターゲットリングを目的のピクセルまでドラッグし, 拡大鏡の円盤上をスライドして微調整します.
5. 拡大鏡上の色または座標の文字をタップしてコピーし, 色の文字を長押しして形式を切り替えます. ピッカーはいつでも停止できます.

******

### AutoJs6 ツールとして使用

インストール後 AutoJs6 は正式なプラグイン契約を通じてアプリを検出し, プラグインセンターで有効な間だけドロワーツールを表示します:

1. プラグイン APK をインストールします. ランチャーを先に開く必要はありません.
2. AutoJs6 のプラグインセンターを開き, Screen Color Picker が有効であることを確認します. 求められた場合はプラグインを承認します.
3. AutoJs6 のドロワーを開きます. Screen Color Picker は拡張ツールの 3 番目に表示され, 実行スイッチは初期状態でオフです.
4. 初回にオーバーレイと画面キャプチャの同意を完了します.
5. ドロワーのスイッチ, フローティング UI, 通知アクションのいずれかで停止します.
6. プラグインセンターで無効にするとドロワー項目は非表示になります. 再び有効にすると項目が復元されます.

******

### 権限とプライバシー

画面処理はすべて端末内で行われ, ユーザーが明示的に開始した場合だけ動作します.

- 画面キャプチャ: セッションごとに Android のシステム同意画面を表示
- 他のアプリの上への表示: タッチ可能なフローティングピッカーだけに使用
- フォアグラウンドサービスと通知: 実行中のキャプチャを表示し停止可能にする
- クリップボード: ユーザーがコピー操作を押した場合だけ書き込み
- ネットワークとストレージ: ネットワーク権限と共有ストレージ権限を要求しない

他のアプリの上に表示する権限または画面キャプチャを拒否すると開始を安全に取り消します. 通知を拒否した場合は警告を表示し, 開始を続行します.

******

### 互換性

単独モードとプラグインモードでは最低要件が異なります.

| モード | 最低要件 |
|---|---|
| 単独アプリ | Android 7.0 (API 24) or later |
| AutoJs6 拡張ツール | AutoJs6 versionCode 5278 or later |

******

### プラグイン契約

以下はホストとプラグインの開発者向け情報です. Binder サービスと Wake Activity は org.autojs.permission.PLUGIN で保護され, ランチャー入口にはこの権限を要求しません.

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

### リリース履歴

#### v1.1.0

_2026/09/13_

- `追加` 画面からローカルのリリース履歴を表示し, 各言語と英語へのフォールバックに対応
- `改善` リリース署名の設定, APK の構成, ドキュメントの再生成結果を検証

#### v1.0.1

_2026/09/11_

- `修正` Service/application Context から Material ボタンを作成する際に Material テーマを明示的に適用し, ThemeEnforcement のクラッシュを防止
- `修正` プラグインセンターでプラグインが有効な間だけ AutoJs6 のドロワーツールを表示し, 再有効化後に自動復元
- `改善` 意図しないネイティブ依存関係をビルド時に拒否し, JSON レポートを生成

#### v1.0.0

_2026/09/01_

- `追加` 同じ APK をランチャーから単独で実行するか, AutoJs6 の画面カラーピッカー拡張ツールとして使用できます
- `追加` 拡大サンプル, 座標, HEX, RGB, HSL を表示するタッチ向けフローティングピッカーを使用できます
- `追加` getInfo, getState, getStartPendingIntent, stop を持つ contract v1 Binder インターフェースを使用できます
- `追加` 10 言語のローカル UI とドキュメントを利用でき, 色処理は完全にオフラインで行われます
- `改善` オーバーレイ, 画面キャプチャ, フォアグラウンドサービス, 通知の権限フローを停止操作付きで明示的に処理します
- `改善` AutoJs6 Wake プロトコルと最小ホストバージョン 5278 に対応し, CI とドキュメント差分検査を追加しました

[完全な CHANGELOG を表示](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-ja.md)

******

### ビルド

同梱の Gradle Wrapper と JDK 21 でテストを実行し, debug APK と instrumentation APK をビルドしてから lint を実行します.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

README, プラグイン説明, changelog は JSON 文書ソースから生成されます. ソースを変更したら次の 2 つのコマンドを実行します.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### ライセンス

プロジェクトコードは Mozilla Public License 2.0 で提供されます.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/16kb.md)
