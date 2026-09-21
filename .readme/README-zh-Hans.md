<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>使用适合触摸操作的浮动取色器获取屏幕任意位置的颜色</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### 语言

README 目前提供以下语言:

- 简体中文 [zh-Hans] # 当前
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### 简介

Screen Color Picker 是一款面向触摸屏的本地屏幕取色工具. 浮动取色器由可拖动的目标环和实时放大镜组成, 同步显示放大像素网格, 当前颜色和精确坐标, 让手指操作保持直接而清晰.

同一个 APK 既能从桌面独立启动, 也能由 AutoJs6 作为侧拉抽屉中的第三个扩展工具自动发现. 独立模式不要求安装 AutoJs6.

******

### 功能

- 双入口: 从桌面独立使用, 或作为 AutoJs6 扩展工具使用
- 触摸优化: 拖动目标环粗略移动, 在放大镜上滑动进行微调
- 即时数据: 放大镜实时显示取样颜色及其坐标
- 灵活复制: 以 HEX 或 RGB 复制颜色 (可仅保留数值), 也可复制坐标
- 可控生命周期: 从主页, 浮动界面, 通知或宿主工具开关停止
- 完全离线: 不上传屏幕内容, 不保存截图历史

******

### 独立使用

无需 AutoJs6 的使用步骤:

1. 从系统启动器打开 Screen Color Picker.
2. 点按启动屏幕取色, 并按说明允许显示在其他应用上层.
3. 在系统提示中允许本次屏幕捕获.
4. 切换到要取色的应用, 拖动目标环移动到目标像素, 然后在放大镜圆盘上滑动微调.
5. 点按放大镜上的颜色或坐标文字即可复制, 长按颜色文字可切换格式, 也可随时停止取色器.

******

### 作为 AutoJs6 工具使用

安装后 AutoJs6 会通过正式插件契约自动发现它, 并仅在插件中心启用该插件时显示抽屉工具:

1. 安装插件 APK. 无需先打开桌面入口.
2. 打开 AutoJs6 插件中心并确认屏幕取色已启用. 如有提示, 请先授权插件.
3. 打开 AutoJs6 侧拉抽屉. 屏幕取色会作为扩展工具的第三个条目出现, 其运行开关默认关闭.
4. 首次运行时完成浮层和屏幕捕获授权.
5. 关闭抽屉开关, 使用浮动界面或通知操作即可停止.
6. 在插件中心禁用插件会隐藏抽屉条目. 重新启用插件后该条目会恢复.

******

### 权限和隐私

所有屏幕处理均在设备本地完成, 且只在用户明确启动后运行.

- 屏幕捕获: Android 每次会显示系统授权界面
- 显示在其他应用上层: 只用于可触摸的浮动取色界面
- 前台服务和通知: 让正在进行的捕获保持可见且可停止
- 剪贴板: 只在用户点按复制时写入选定颜色
- 网络和存储: 不申请网络或共享存储权限

拒绝悬浮窗或屏幕捕获权限会安全取消启动. 拒绝通知权限时会显示提示, 但启动仍会继续.

******

### 兼容性

独立模式与插件模式具有不同的最低要求.

| 模式 | 最低要求 |
|---|---|
| 独立 App | Android 7.0 (API 24) or later |
| AutoJs6 扩展工具 | AutoJs6 versionCode 5278 or later |

******

### 插件契约

以下信息供宿主与插件开发者核对. Binder 服务与 Wake Activity 受 org.autojs.permission.PLUGIN 保护, 桌面入口不受该权限限制.

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

### 发行历史

#### v1.1.3

_2026/09/19_

- `修复` AGP 9.1 构建时的 SDK XML v4 解析警告, 以及 JVM 单元测试误触发 APK 原生库对齐检查的问题 (共享构建插件 1.8.3)
- `优化` targetSdk 升级至 37 (Android 17)

#### v1.1.2

_2026/09/16_

- `修复` 界面重建时悬浮窗权限说明对话框残留及焦点冲突的问题

#### v1.1.1

_2026/09/15_

- `优化` compileSdk 升级至 37 (Android 17), targetSdk 保持 36

[查看完整 CHANGELOG](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hans.md)

******

### 构建

使用仓库自带的 Gradle Wrapper 和 JDK 21 运行测试, 构建 debug APK 与 instrumentation APK, 然后执行 lint.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

README, 插件说明和 changelog 由 JSON 文案源生成. 修改源文件后运行以下两个命令.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### 许可证

项目代码采用 Mozilla Public License 2.0 许可.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/16kb.md)
