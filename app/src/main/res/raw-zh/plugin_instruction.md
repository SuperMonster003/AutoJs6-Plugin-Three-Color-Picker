# AutoJs6 3-Color Picker

3-Color Picker 是一款面向触摸屏的本地屏幕取色工具. 浮动取色器由可拖动的目标环和实时放大镜组成, 同步显示放大像素网格, 当前颜色和精确坐标, 让手指操作保持直接而清晰.

2.0 版本采用新包名 io.github.supermonster003.autojs6.plugin.three.color.picker. Android 会将其视为独立应用, 旧版可保留并存, 设置不会自动迁移. 新版需重新授权, 插件模式要求 AutoJs6 versionCode 5316 或更高. 宿主抽屉中的 "屏幕取色" 文案保持不变.

### 独立使用

1. 从系统启动器打开 3-Color Picker.
2. 点按启动屏幕取色, 并按说明允许显示在其他应用上层.
3. 在系统提示中允许本次屏幕捕获.
4. 切换到要取色的应用, 拖动目标环移动到目标像素, 然后在放大镜圆盘上滑动微调.
5. 点按放大镜上的颜色或坐标文字即可复制, 长按颜色文字可切换格式, 也可随时停止取色器.

### 作为 AutoJs6 工具使用

1. 安装插件 APK. 无需先打开桌面入口.
2. 打开 AutoJs6 插件中心并确认3-Color Picker已启用. 如有提示, 请先授权插件.
3. 打开 AutoJs6 侧拉抽屉. 屏幕取色会作为扩展工具的第三个条目出现, 其运行开关默认关闭.
4. 首次运行时完成浮层和屏幕捕获授权.
5. 关闭抽屉开关, 使用浮动界面或通知操作即可停止.
6. 在插件中心禁用插件会隐藏抽屉条目. 重新启用插件后该条目会恢复.

### 权限和隐私

所有屏幕处理均在设备本地完成, 且只在用户明确启动后运行.

- 屏幕捕获: Android 每次会显示系统授权界面
- 显示在其他应用上层: 只用于可触摸的浮动取色界面
- 前台服务和通知: 让正在进行的捕获保持可见且可停止
- 剪贴板: 只在用户点按复制时写入选定颜色
- 网络和存储: 不申请网络或共享存储权限

拒绝悬浮窗或屏幕捕获权限会安全取消启动. 拒绝通知权限时会显示提示, 但启动仍会继续.

### 设计参考与致谢

当前设计方案参考 MT 管理器 (bm.mt.plus) v2.26.9, 尤其是其浮动屏幕取色交互. 感谢其开发者的设计与投入. 本项目独立维护, 此致谢不代表关联, 背书或已获授权. 如权利人对相关内容有异议, 可通过项目 Issues 联系我们. 我们将积极核实沟通, 并视情况配合补充署名, 替换或移除相关内容.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

项目说明: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)
