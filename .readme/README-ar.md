<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/images/icon.svg?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>يلتقط الألوان من أي مكان على الشاشة باستخدام منتقي عائم مناسب للمس</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### اللغات

يتوفر README باللغات التالية:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- العربية [ar] # الحالية

******

### مقدمة

Screen Color Picker أداة محلية لأخذ عينات الألوان ومصممة للشاشات اللمسية. يعرض المنتقي العائم عينة مكبرة وإحداثيات وصيغا متعددة للألوان مع إبقاء التفاعل بالإصبع مباشرا وواضحا.

يمكن تشغيل ملف APK نفسه بصورة مستقلة من المشغل أو يكتشفه AutoJs6 كثالث أداة موسعة في اللوحة الجانبية. لا يحتاج الوضع المستقل إلى AutoJs6.

******

### الميزات

- نقطتا دخول: استخدام مستقل من المشغل أو كأداة موسعة في AutoJs6
- محسن للمس: هدف عائم وعينة مكبرة لاختيار بكسل دقيق
- بيانات فورية: عرض إحداثيات الشاشة و HEX و RGB و HSL معا
- نسخ متعدد الصيغ: اختر HEX أو RGB أو HSL وانسخ قيمة اللون الحالية
- دورة حياة قابلة للتحكم: الإيقاف من الصفحة الرئيسية أو الواجهة العائمة أو الإشعار أو مفتاح المضيف
- دون اتصال بالكامل: لا يرفع محتوى الشاشة ولا يحتفظ بسجل لصور الشاشة

******

### الاستخدام المستقل

استخدم المنتقي دون AutoJs6:

1. افتح Screen Color Picker من مشغل النظام.
2. اضغط تشغيل منتقي الألوان واتبع الشرح للسماح بالظهور فوق التطبيقات الأخرى.
3. اسمح بالتقاط الشاشة الحالي في طلب نظام Android.
4. انتقل إلى التطبيق المطلوب واضغط الهدف العائم لتجميد الشاشة ثم اسحب مؤشر التقاطع فوق اللقطة.
5. انسخ الصيغة المطلوبة أو أوقف المنتقي في أي وقت.

******

### الاستخدام كأداة AutoJs6

بعد التثبيت يكتشف AutoJs6 التطبيق عبر عقد المكون الإضافي الرسمي ولا يعرض أداته في اللوحة الجانبية إلا عندما يكون المكون الإضافي مفعلا في مركز المكونات الإضافية:

1. ثبت ملف APK للمكون الإضافي. لا يلزم فتح مدخل المشغل أولا.
2. افتح مركز المكونات الإضافية في AutoJs6 وتأكد من تمكين Screen Color Picker. وافق على المكون الإضافي إذا طلب منك ذلك.
3. افتح اللوحة الجانبية في AutoJs6. يظهر Screen Color Picker كالعنصر الثالث ضمن الأدوات الموسعة, ويكون مفتاح تشغيله متوقفا افتراضيا.
4. أكمل موافقة الظهور والتقاط الشاشة عند أول استخدام.
5. أوقف مفتاح اللوحة أو استخدم الواجهة العائمة أو إجراء الإشعار للإيقاف.
6. يؤدي تعطيل المكون الإضافي في مركز المكونات الإضافية إلى إخفاء عنصر اللوحة الجانبية. أعد تمكينه لاستعادة العنصر.

******

### الأذونات والخصوصية

تتم كل معالجة الشاشة محليا على الجهاز ولا تبدأ إلا بعد إجراء صريح من المستخدم.

- التقاط الشاشة: يعرض Android شاشة موافقة النظام لكل جلسة
- الظهور فوق التطبيقات الأخرى: يستخدم فقط للمنتقي العائم القابل للمس
- خدمة المقدمة والإشعار: يبقيان الالتقاط النشط ظاهرا وقابلا للإيقاف
- الحافظة: لا تتم الكتابة إلا عندما يضغط المستخدم إجراء النسخ
- الشبكة والتخزين: لا يطلب إذن الشبكة أو التخزين المشترك

رفض إذن الظهور فوق التطبيقات الأخرى أو التقاط الشاشة يلغي التشغيل بأمان. أما رفض إذن الإشعارات فيعرض تنبيهًا فقط ويستمر التشغيل.

******

### التوافق

للوضع المستقل ووضع المكون الإضافي متطلبات دنيا مختلفة.

| الوضع | الحد الأدنى |
|---|---|
| تطبيق مستقل | Android 7.0 (API 24) or later |
| أداة AutoJs6 موسعة | AutoJs6 versionCode 5278 or later |

******

### عقد المكون الإضافي

التفاصيل التالية لمطوري المضيف والمكون الإضافي. خدمة Binder و Wake Activity محميتان بواسطة org.autojs.permission.PLUGIN بينما لا يتطلب مدخل المشغل هذا الإذن.

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

### سجل الإصدارات

#### v1.0.1

_2026/09/01_

- `إصلاح` منع تعطل ThemeEnforcement بتطبيق سمة Material صراحة على أزرار Material المنشأة من Context الخدمة/التطبيق
- `إصلاح` عرض أداة اللوحة الجانبية في AutoJs6 فقط عند تمكين المكون الإضافي في مركز المكونات الإضافية واستعادتها بعد إعادة تمكينه

#### v1.0.0

_2026/09/01_

- `إضافة` شغل ملف APK نفسه بصورة مستقلة من المشغل أو استخدمه كأداة موسعة لاختيار لون الشاشة في AutoJs6
- `إضافة` استخدم أداة اختيار عائمة مناسبة للمس مع عينة مكبرة وإحداثيات وصيغ HEX وRGB وHSL
- `إضافة` استخدم واجهة Binder من contract v1 مع getInfo وgetState وgetStartPendingIntent وstop
- `إضافة` استخدم الواجهة المحلية والوثائق بعشر لغات مع معالجة الألوان دون اتصال بالكامل
- `تحسين` تعامل بوضوح مع أذونات التراكب والتقاط الشاشة والخدمة الأمامية والإشعارات مع إجراء للإيقاف
- `تحسين` ادعم بروتوكول AutoJs6 Wake والحد الأدنى لإصدار المضيف 5278 مع CI وفحوصات انحراف الوثائق

[عرض CHANGELOG الكامل](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-ar.md)

******

### البناء

استخدم Gradle Wrapper المرفق و JDK 21 لتشغيل الاختبارات وبناء debug APK و instrumentation APK ثم شغل lint.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

يتم إنشاء README وتعليمات المكون الإضافي و changelog من مصادر JSON. بعد تعديل المصدر شغل الأمرين التاليين.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### الترخيص

كود المشروع مرخص بموجب Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)
