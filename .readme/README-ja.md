<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap-night/ic_launcher.png?raw=true" media="(prefers-color-scheme: dark)" />
      <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-three-color-picker-ic-launcher" border="0" width="128" />
    </picture>
  </p>
  <h1>3-Color Picker</h1>
  <p>タッチ操作しやすいフローティングピッカーで画面上の色を取得します</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=License"/></a>
  </p>
</div>

******

### 言語

README は次の言語で利用できます:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-es.md)
- 日本語 [ja] # 現在
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ar.md)

******

### 概要

3-Color Picker はタッチ画面向けのローカル色取得ツールです. フローティングピッカーはドラッグできるターゲットリングとリアルタイムの拡大鏡で構成され, 拡大ピクセルグリッド, 現在の色, 正確な座標を表示し, 指で直接わかりやすく操作できます.

同じ APK をランチャーから単独で使用でき, AutoJs6 のドロワーでは 3 番目の拡張ツールとして自動検出できます. 単独モードでは AutoJs6 は不要です.

2.0 は新しいアプリ ID io.github.supermonster003.autojs6.plugin.three.color.picker を使用します. Android では別のアプリとしてインストールされ, 旧版と共存できます. 設定は自動移行されません. 権限を再度許可し, プラグインモードでは AutoJs6 versionCode 5316 以降を使用してください. AutoJs6 ドロワーの画面色取得の表示名は変更しません.

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

<picture>
  <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-dark.png?raw=true" media="(prefers-color-scheme: dark)" />
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-light.png?raw=true" alt="3-Color Picker home" width="280" />
</picture>

AutoJs6 なしでピッカーを使用する手順

独立した設定画面を言語, 夜間モード, テーマ色, ランチャーアイコンの順に統一. 既定で AutoJs6 に従い, 利用できない場合はシステム設定に戻ります. 中立色の背景と各コントロールの配色を統一し, 確認後のみ保存します. HEX/RGB プレビューを追加し, 既定アイコンを自動に変更. 更新時は明示的な選択を保持します.:

1. システムランチャーから 3-Color Picker を開きます.
2. カラーピッカーを開始を押し, 説明に従って他のアプリの上への表示を許可します.
3. Android のシステム画面で今回の画面キャプチャを許可します.
4. 採色するアプリへ切り替え, ターゲットリングを目的のピクセルまでドラッグし, 拡大鏡の円盤上をスライドして微調整します.
5. 拡大鏡上の色または座標の文字をタップしてコピーし, 色の文字を長押しして形式を切り替えます. ピッカーはいつでも停止できます.

******

### AutoJs6 ツールとして使用

インストール後 AutoJs6 は正式なプラグイン契約を通じてアプリを検出し, プラグインセンターで有効な間だけドロワーツールを表示します:

1. プラグイン APK をインストールします. ランチャーを先に開く必要はありません.
2. AutoJs6 のプラグインセンターを開き, 3-Color Picker が有効であることを確認します. 求められた場合はプラグインを承認します.
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
| AutoJs6 拡張ツール | AutoJs6 versionCode 5316 or later |

******

### プラグイン契約

以下はホストとプラグインの開発者向け情報です. Binder サービスと Wake Activity は org.autojs.permission.PLUGIN で保護され, ランチャー入口にはこの権限を要求しません.

```text
application id: io.github.supermonster003.autojs6.plugin.three.color.picker
plugin id / INFO category: three-color-picker
engine / capture service category: screen-color-picker
variant: default
service action: org.autojs.plugin.SCREEN_COLOR_PICKER
wake action: org.autojs.plugin.action.WAKE
binder: org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin
contract version: 1
minimum host versionCode: 5316
native libraries: none
```

******

### リリース履歴

#### v2.0.0

_2026/10/03_

- `ヒント` 2.0 は新しいアプリ ID io.github.supermonster003.autojs6.plugin.three.color.picker を使用します. Android では別のアプリとしてインストールされ, 旧版と共存できます. 設定は自動移行されません. 権限を再度許可し, プラグインモードでは AutoJs6 versionCode 5316 以降を使用してください. AutoJs6 ドロワーの画面色取得の表示名は変更しません
- `改善` Screen Color Picker を 3-Color Picker に改名し, ホーム画面と明暗のアイコンを刷新. 4 種類のランチャーアイコンモードを維持
- `改善` 設定とプロジェクト文書に MT Manager (bm.mt.plus) v2.26.9 のデザイン参考, 謝辞と権利に関する申し立てへの対応方針を追加

#### v1.2.0

_2026/09/30_

- `追加` 独立した設定画面を言語, 夜間モード, テーマ色, ランチャーアイコンの順に統一. 既定で AutoJs6 に従い, 利用できない場合はシステム設定に戻ります. 中立色の背景と各コントロールの配色を統一し, 確認後のみ保存します. HEX/RGB プレビューを追加し, 既定アイコンを自動に変更. 更新時は明示的な選択を保持します.

#### v1.1.3

_2026/09/19_

- `修正` 共有ビルドプラグイン 1.8.3 により, AGP 9.1 での SDK XML v4 解析警告と, JVM 単体テストの組み立て時に APK ネイティブライブラリのアラインメント検証が誤って実行される問題
- `改善` compileSdk に続き targetSdk を 37 (Android 17) に引き上げ, プラグインの動作は新しいターゲットの影響を受けない

[完全な CHANGELOG を表示](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-ja.md)

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

### デザインの参考と謝辞

現在のデザインは MT Manager (bm.mt.plus) v2.26.9, 特にフローティング画面カラーピッカーの操作を参考にしています. 開発者の取り組みに感謝します. 本プロジェクトは独立して保守されており, この謝辞は提携, 推奨や許諾を意味しません. 権利者の方はプロジェクトの Issues からご連絡ください. 内容を確認し, 必要に応じて帰属表示の修正, 差し替えや削除に協力します.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

プロジェクトの説明: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)

******

### ライセンス

プロジェクトコードは Mozilla Public License 2.0 で提供されます.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/16kb.md)
