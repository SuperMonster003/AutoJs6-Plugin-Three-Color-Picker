<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap-night/ic_launcher.png?raw=true" media="(prefers-color-scheme: dark)" />
      <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-three-color-picker-ic-launcher" border="0" width="128" />
    </picture>
  </p>
  <h1>3-Color Picker</h1>
  <p>يلتقط الألوان من أي مكان على الشاشة باستخدام منتقي عائم مناسب للمس</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=License"/></a>
  </p>
</div>

******

### اللغات

يتوفر README باللغات التالية:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ru.md)
- العربية [ar] # الحالية

******

### مقدمة

3-Color Picker أداة محلية لأخذ عينات الألوان ومصممة للشاشات اللمسية. يجمع المنتقي العائم بين حلقة هدف قابلة للسحب ومكبر فوري يعرض شبكة بكسلات مكبرة واللون الحالي والإحداثيات الدقيقة مع إبقاء التفاعل بالإصبع مباشرا وواضحا.

يمكن تشغيل ملف APK نفسه بصورة مستقلة من المشغل أو يكتشفه AutoJs6 كثالث أداة موسعة في اللوحة الجانبية. لا يحتاج الوضع المستقل إلى AutoJs6.

يستخدم الإصدار 2.0 معرف التطبيق الجديد io.github.supermonster003.autojs6.plugin.three.color.picker. يثبته Android كتطبيق مستقل ويمكن إبقاء التطبيق القديم. لا تنتقل الإعدادات تلقائيا. امنح الأذونات مجددا واستخدم AutoJs6 versionCode 5316 أو أحدث لوضع المكون الإضافي. يبقى اسم أداة التقاط ألوان الشاشة في لوحة AutoJs6 كما هو.

******

### الميزات

- نقطتا دخول: استخدام مستقل من المشغل أو كأداة موسعة في AutoJs6
- محسن للمس: اسحب حلقة الهدف للتحرك الواسع ومرر على المكبر للضبط الدقيق
- بيانات فورية: يعرض المكبر اللون المأخوذ وإحداثياته في الوقت الفعلي
- نسخ مرن: انسخ اللون بصيغة HEX أو RGB (مع خيار الأرقام فقط) أو انسخ الإحداثيات
- دورة حياة قابلة للتحكم: الإيقاف من الصفحة الرئيسية أو الواجهة العائمة أو الإشعار أو مفتاح المضيف
- دون اتصال بالكامل: لا يرفع محتوى الشاشة ولا يحتفظ بسجل لصور الشاشة

******

### الاستخدام المستقل

<picture>
  <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-dark.png?raw=true" media="(prefers-color-scheme: dark)" />
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-light.png?raw=true" alt="3-Color Picker home" width="280" />
</picture>

استخدم المنتقي دون AutoJs6

توحيد الإعدادات المستقلة بترتيب اللغة والوضع الليلي ولون السمة ورمز التشغيل. اتباع AutoJs6 افتراضيا مع الرجوع إلى إعدادات النظام وأسطح محايدة وألوان متناسقة للعناصر. تحفظ الخيارات بعد التأكيد فقط مع معاينة HEX/RGB. يصبح الرمز المتكيف التلقائي هو الافتراضي مع الحفاظ على الخيارات الصريحة عند التحديث.:

1. افتح 3-Color Picker من مشغل النظام.
2. اضغط تشغيل منتقي الألوان واتبع الشرح للسماح بالظهور فوق التطبيقات الأخرى.
3. اسمح بالتقاط الشاشة الحالي في طلب نظام Android.
4. انتقل إلى التطبيق المطلوب واسحب حلقة الهدف إلى البكسل المطلوب ثم مرر على قرص المكبر للضبط الدقيق.
5. اضغط نص اللون أو الإحداثيات على المكبر لنسخه واضغط مطولا على نص اللون لتبديل الصيغة أو أوقف المنتقي في أي وقت.

******

### الاستخدام كأداة AutoJs6

بعد التثبيت يكتشف AutoJs6 التطبيق عبر عقد المكون الإضافي الرسمي ولا يعرض أداته في اللوحة الجانبية إلا عندما يكون المكون الإضافي مفعلا في مركز المكونات الإضافية:

1. ثبت ملف APK للمكون الإضافي. لا يلزم فتح مدخل المشغل أولا.
2. افتح مركز المكونات الإضافية في AutoJs6 وتأكد من تمكين 3-Color Picker. وافق على المكون الإضافي إذا طلب منك ذلك.
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
| أداة AutoJs6 موسعة | AutoJs6 versionCode 5316 or later |

******

### عقد المكون الإضافي

التفاصيل التالية لمطوري المضيف والمكون الإضافي. خدمة Binder و Wake Activity محميتان بواسطة org.autojs.permission.PLUGIN بينما لا يتطلب مدخل المشغل هذا الإذن.

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

### سجل الإصدارات

#### v2.0.1

_2026/10/04_

- `تحسين` تستخدم أيقونات مركز الملحقات الأحجام والمواضع والصور الفاتحة والداكنة والخلفيات الدائرية المعدلة في Icon Studio مع الاحتفاظ بالمصادر والمعلمات لإعادة إنتاجها

#### v2.0.0

_2026/10/03_

- `تلميح` يستخدم الإصدار 2.0 معرف التطبيق الجديد io.github.supermonster003.autojs6.plugin.three.color.picker. يثبته Android كتطبيق مستقل ويمكن إبقاء التطبيق القديم. لا تنتقل الإعدادات تلقائيا. امنح الأذونات مجددا واستخدم AutoJs6 versionCode 5316 أو أحدث لوضع المكون الإضافي. يبقى اسم أداة التقاط ألوان الشاشة في لوحة AutoJs6 كما هو
- `تحسين` إعادة تسمية Screen Color Picker إلى 3-Color Picker مع شاشة رئيسية جديدة وأيقونات فاتحة وداكنة والإبقاء على أوضاع أيقونة المشغل الأربعة
- `تحسين` إضافة مرجع التصميم MT Manager (bm.mt.plus) v2.26.9 والشكر وسياسة التعامل مع اعتراضات أصحاب الحقوق إلى الإعدادات ووثائق المشروع
- `تحسين` توحيد الحجم البصري لأيقونات المشغل ومركز الإضافات مع خلفيات شفافة ورسومات بالأبيض والأسود أو بتدرجات رمادية محايدة

#### v1.2.0

_2026/09/30_

- `إضافة` توحيد الإعدادات المستقلة بترتيب اللغة والوضع الليلي ولون السمة ورمز التشغيل. اتباع AutoJs6 افتراضيا مع الرجوع إلى إعدادات النظام وأسطح محايدة وألوان متناسقة للعناصر. تحفظ الخيارات بعد التأكيد فقط مع معاينة HEX/RGB. يصبح الرمز المتكيف التلقائي هو الافتراضي مع الحفاظ على الخيارات الصريحة عند التحديث.

[عرض CHANGELOG الكامل](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-ar.md)

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

### مرجع التصميم والشكر

يستند التصميم الحالي إلى MT Manager (bm.mt.plus) v2.26.9, خاصة تفاعلات منتقي ألوان الشاشة العائم. نشكر مطوريه على عملهم. تتم صيانة هذا المشروع بشكل مستقل ولا يعني هذا الشكر وجود ارتباط أو تأييد أو إذن. يمكن لأصحاب الحقوق التواصل عبر Issues المشروع. سنراجع الملاحظات ونتعاون في تصحيح النسبة أو الاستبدال أو الإزالة حسب الحالة.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

وثائق المشروع: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)

******

### الترخيص

كود المشروع مرخص بموجب Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/16kb.md)
