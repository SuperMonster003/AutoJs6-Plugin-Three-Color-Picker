<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/images/icon.svg?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>Выбирает цвета в любой точке экрана с помощью удобной плавающей пипетки</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### Языки

README доступен на следующих языках:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- Русский [ru] # текущий
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### Введение

Screen Color Picker является локальным инструментом выбора цвета для сенсорных экранов. Плавающая пипетка сочетает перетаскиваемое кольцо цели и лупу реального времени, показывая увеличенную пиксельную сетку, текущий цвет и точные координаты, сохраняя управление пальцем прямым и понятным.

Один APK запускается отдельно из панели приложений или обнаруживается AutoJs6 как третий расширенный инструмент в боковой панели. Для автономного режима AutoJs6 не требуется.

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

Используйте пипетку без AutoJs6:

1. Откройте Screen Color Picker из системной панели приложений.
2. Нажмите Запустить пипетку и следуйте пояснению для разрешения показа поверх других приложений.
3. Разрешите текущий захват экрана в системном запросе Android.
4. Перейдите в нужное приложение, перетащите кольцо цели к нужному пикселю, затем проведите по диску лупы для точной подстройки.
5. Коснитесь текста цвета или координат на лупе, чтобы скопировать его, удерживайте текст цвета для смены формата или остановите пипетку в любое время.

******

### Использование как инструмента AutoJs6

После установки AutoJs6 обнаруживает приложение через официальный контракт плагина и показывает его инструмент в боковой панели, только пока плагин включен в Центре плагинов:

1. Установите APK плагина. Сначала открывать его значок не требуется.
2. Откройте Центр плагинов AutoJs6 и убедитесь, что Screen Color Picker включен. При запросе авторизуйте плагин.
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
| Расширенный инструмент AutoJs6 | AutoJs6 versionCode 5278 or later |

******

### Контракт плагина

Следующие сведения предназначены для разработчиков хоста и плагина. Служба Binder и Wake Activity защищены org.autojs.permission.PLUGIN, а значок приложения не ограничен этим разрешением.

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

### История выпусков

#### v1.0.1

_2026/09/01_

- `Исправлено` Предотвращен сбой ThemeEnforcement за счет явного применения темы Material к кнопкам Material, создаваемым из Context службы/приложения
- `Исправлено` Инструмент в боковой панели AutoJs6 отображается только при включенном плагине в Центре плагинов и восстанавливается после повторного включения

#### v1.0.0

_2026/09/01_

- `Добавлено` Запускайте один APK отдельно из лаунчера или используйте его как расширенный инструмент выбора цвета экрана в AutoJs6
- `Добавлено` Используйте удобный для сенсорного экрана плавающий инструмент с увеличенным образцом, координатами, HEX, RGB и HSL
- `Добавлено` Используйте интерфейс Binder contract v1 с методами getInfo, getState, getStartPendingIntent и stop
- `Добавлено` Используйте локальный интерфейс и документацию на 10 языках с полностью автономной обработкой цвета
- `Улучшено` Явно обрабатывайте разрешения наложения, захвата экрана, фоновой службы и уведомлений с действием остановки
- `Улучшено` Поддерживайте протокол AutoJs6 Wake и минимальную версию хоста 5278 с CI и проверкой актуальности документации

[Открыть полный CHANGELOG](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-ru.md)

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

### Лицензия

Код проекта распространяется по Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)
