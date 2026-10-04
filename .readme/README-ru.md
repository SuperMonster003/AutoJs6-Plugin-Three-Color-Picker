<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap-night/ic_launcher.png?raw=true" media="(prefers-color-scheme: dark)" />
      <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-three-color-picker-ic-launcher" border="0" width="128" />
    </picture>
  </p>
  <h1>3-Color Picker</h1>
  <p>Выбирает цвета в любой точке экрана с помощью удобной плавающей пипетки</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=License"/></a>
  </p>
</div>

******

### Языки

README доступен на следующих языках:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ko.md)
- Русский [ru] # текущий
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ar.md)

******

### Введение

3-Color Picker является локальным инструментом выбора цвета для сенсорных экранов. Плавающая пипетка сочетает перетаскиваемое кольцо цели и лупу реального времени, показывая увеличенную пиксельную сетку, текущий цвет и точные координаты, сохраняя управление пальцем прямым и понятным.

Один APK запускается отдельно из панели приложений или обнаруживается AutoJs6 как третий расширенный инструмент в боковой панели. Для автономного режима AutoJs6 не требуется.

Версия 2.0 использует новый ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android устанавливает ее как отдельное приложение; старую версию можно сохранить, настройки автоматически не переносятся. Предоставьте разрешения заново и используйте AutoJs6 versionCode 5316 или новее для режима плагина. Название инструмента в боковой панели AutoJs6 не меняется.

******

### Возможности

- Два способа запуска: отдельно из панели приложений или как расширенный инструмент AutoJs6
- Оптимизация для касаний: перетаскивайте кольцо цели для грубого перемещения и проводите по лупе для точной подстройки
- Мгновенные данные: лупа показывает выбранный цвет и его координаты в реальном времени
- Гибкое копирование: копируйте цвет как HEX или RGB (можно только числа) или копируйте координаты
- Управляемый жизненный цикл: остановка с главного экрана, плавающего интерфейса, уведомления или переключателя хоста
- Полностью автономно: содержимое экрана не отправляется и история снимков не сохраняется

******

### Автономное использование

<picture>
  <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-dark.png?raw=true" media="(prefers-color-scheme: dark)" />
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-light.png?raw=true" alt="3-Color Picker home" width="280" />
</picture>

Используйте пипетку без AutoJs6

Унифицированы отдельные настройки: язык, ночной режим, цвет и значок. По умолчанию используются настройки AutoJs6 с системным резервом, нейтральные поверхности и согласованные цвета элементов. Изменения сохраняются после подтверждения; добавлен предпросмотр HEX/RGB. Значок по умолчанию адаптивный автоматический, явный выбор сохраняется при обновлении.:

1. Откройте 3-Color Picker из системной панели приложений.
2. Нажмите Запустить пипетку и следуйте пояснению для разрешения показа поверх других приложений.
3. Разрешите текущий захват экрана в системном запросе Android.
4. Перейдите в нужное приложение, перетащите кольцо цели к нужному пикселю, затем проведите по диску лупы для точной подстройки.
5. Коснитесь текста цвета или координат на лупе, чтобы скопировать его, удерживайте текст цвета для смены формата или остановите пипетку в любое время.

******

### Использование как инструмента AutoJs6

После установки AutoJs6 обнаруживает приложение через официальный контракт плагина и показывает его инструмент в боковой панели, только пока плагин включен в Центре плагинов:

1. Установите APK плагина. Сначала открывать его значок не требуется.
2. Откройте Центр плагинов AutoJs6 и убедитесь, что 3-Color Picker включен. При запросе авторизуйте плагин.
3. Откройте боковую панель AutoJs6. Screen Color Picker появится третьим среди расширенных инструментов, а его рабочий переключатель по умолчанию будет выключен.
4. При первом использовании подтвердите наложение и захват экрана.
5. Выключите переключатель, используйте плавающий интерфейс или действие уведомления для остановки.
6. Отключение плагина в Центре плагинов скрывает пункт боковой панели. Повторное включение восстанавливает его.

******

### Разрешения и конфиденциальность

Вся обработка экрана выполняется локально на устройстве и начинается только после явного действия пользователя.

- Захват экрана: Android показывает системное согласие для каждого сеанса
- Показ поверх других приложений: используется только для сенсорной плавающей пипетки
- Фоновая служба и уведомление: делают активный захват видимым и доступным для остановки
- Буфер обмена: запись выполняется только после нажатия действия копирования
- Сеть и хранилище: разрешения сети и общего хранилища не запрашиваются

Отказ в разрешении на наложение или захват экрана безопасно отменяет запуск. При отказе в уведомлениях появится предупреждение, а запуск продолжится.

******

### Совместимость

Автономный режим и режим плагина имеют разные минимальные требования.

| Режим | Минимальное требование |
|---|---|
| Автономное приложение | Android 7.0 (API 24) or later |
| Расширенный инструмент AutoJs6 | AutoJs6 versionCode 5316 or later |

******

### Контракт плагина

Следующие сведения предназначены для разработчиков хоста и плагина. Служба Binder и Wake Activity защищены org.autojs.permission.PLUGIN, а значок приложения не ограничен этим разрешением.

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

### История выпусков

#### v2.0.1

_2026/10/04_

- `Улучшено` Значки центра плагинов используют размеры, положение, светлые и тёмные изображения и круглые фоны, настроенные в Icon Studio, сохраняя исходники и параметры для воспроизведения

#### v2.0.0

_2026/10/03_

- `Примечание` Версия 2.0 использует новый ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android устанавливает ее как отдельное приложение; старую версию можно сохранить, настройки автоматически не переносятся. Предоставьте разрешения заново и используйте AutoJs6 versionCode 5316 или новее для режима плагина. Название инструмента в боковой панели AutoJs6 не меняется
- `Улучшено` Screen Color Picker переименован в 3-Color Picker; обновлены главный экран и светлые/темные значки, сохранены четыре режима значка лаунчера
- `Улучшено` В настройках и документации указаны MT Manager (bm.mt.plus) v2.26.9 как источник дизайна, благодарности и порядок рассмотрения обращений правообладателей
- `Улучшено` Единый визуальный размер значков лаунчера и Центра плагинов, прозрачный фон и черно-белые изображения или нейтральные оттенки серого

#### v1.2.0

_2026/09/30_

- `Добавлено` Унифицированы отдельные настройки: язык, ночной режим, цвет и значок. По умолчанию используются настройки AutoJs6 с системным резервом, нейтральные поверхности и согласованные цвета элементов. Изменения сохраняются после подтверждения; добавлен предпросмотр HEX/RGB. Значок по умолчанию адаптивный автоматический, явный выбор сохраняется при обновлении.

[Открыть полный CHANGELOG](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-ru.md)

******

### Сборка

Используйте Gradle Wrapper из репозитория и JDK 21 для тестов, сборки debug APK и instrumentation APK, а затем запустите lint.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

README, инструкции плагина и changelog создаются из исходных JSON. После изменения источника выполните обе команды ниже.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### Источник дизайна и благодарности

Текущий дизайн опирается на MT Manager (bm.mt.plus) v2.26.9, особенно на взаимодействие с плавающей экранной пипеткой. Благодарим разработчиков за их работу. Проект поддерживается независимо; благодарность не означает сотрудничества, одобрения или разрешения. Правообладатели могут связаться с нами через Issues проекта. Мы рассмотрим обращения и при необходимости исправим указание авторства, заменим или удалим соответствующие материалы.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

Документация проекта: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)

******

### Лицензия

Код проекта распространяется по Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/16kb.md)
