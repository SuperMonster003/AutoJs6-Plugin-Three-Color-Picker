<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap-night/ic_launcher.png?raw=true" media="(prefers-color-scheme: dark)" />
      <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-three-color-picker-ic-launcher" border="0" width="128" />
    </picture>
  </p>
  <h1>3-Color Picker</h1>
  <p>Toma colores de cualquier parte de la pantalla con un selector flotante táctil</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=License"/></a>
  </p>
</div>

******

### Idiomas

El README está disponible en los siguientes idiomas:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-fr.md)
- Español [es] # actual
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ar.md)

******

### Introducción

3-Color Picker es una herramienta local de muestreo de color diseñada para pantallas táctiles. Su selector flotante combina un anillo objetivo arrastrable con una lupa en tiempo real que muestra una cuadrícula de píxeles ampliada, el color actual y las coordenadas exactas, con una interacción directa y clara.

El mismo APK funciona de forma independiente desde el lanzador o es descubierto por AutoJs6 como la tercera herramienta ampliada de su panel. El modo independiente no requiere AutoJs6.

La versión 2.0 usa el nuevo ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android la instala como una aplicación independiente; la anterior puede permanecer instalada y los ajustes no se migran automáticamente. Concede los permisos de nuevo y usa AutoJs6 versionCode 5316 o posterior para el modo plugin. La etiqueta del panel de AutoJs6 no cambia.

******

### Funciones

- Dos entradas: uso independiente desde el lanzador o como herramienta ampliada de AutoJs6
- Optimizado para el tacto: arrastra el anillo objetivo para movimientos amplios y desliza sobre la lupa para el ajuste fino
- Datos inmediatos: la lupa muestra en tiempo real el color muestreado y sus coordenadas
- Copia flexible: copia el color como HEX o RGB (solo valores numéricos si lo prefieres) o copia las coordenadas
- Ciclo de vida controlado: detén desde la pantalla principal, la interfaz flotante, la notificación o el interruptor del host
- Totalmente sin conexión: el contenido de la pantalla no se sube y no se conserva un historial de capturas

******

### Uso independiente

<picture>
  <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-dark.png?raw=true" media="(prefers-color-scheme: dark)" />
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-light.png?raw=true" alt="3-Color Picker home" width="280" />
</picture>

Usa el selector sin AutoJs6

Unificar los ajustes independientes: idioma, modo nocturno, color e icono. Seguir AutoJs6 de forma predeterminada con alternativa del sistema, superficies neutras y controles adaptados. Aplicar los cambios tras confirmar, ofrecer una vista previa HEX/RGB y usar el modo adaptable automático por defecto, conservando las elecciones explícitas al actualizar.:

1. Abre 3-Color Picker desde el lanzador del sistema.
2. Pulsa Iniciar selector de color y sigue la explicación para permitir mostrar sobre otras aplicaciones.
3. Permite la captura de pantalla actual en el aviso del sistema Android.
4. Cambia a la aplicación que quieras muestrear, arrastra el anillo objetivo hasta el píxel deseado y desliza sobre el disco de la lupa para afinar.
5. Pulsa el texto de color o de coordenadas en la lupa para copiarlo, mantén pulsado el texto de color para cambiar de formato o detén el selector en cualquier momento.

******

### Uso como herramienta de AutoJs6

Tras la instalación AutoJs6 descubre la aplicación mediante el contrato formal del plugin y solo muestra su herramienta en el panel mientras el plugin está activado en el Centro de plugins:

1. Instala el APK del plugin. No necesitas abrir primero la entrada del lanzador.
2. Abre el Centro de plugins de AutoJs6 y comprueba que 3-Color Picker esté activado. Autoriza el plugin si se solicita.
3. Abre el panel de AutoJs6. Screen Color Picker aparece como la tercera entrada de Herramientas ampliadas, con su interruptor de ejecución desactivado de forma predeterminada.
4. Completa el consentimiento de superposición y captura de pantalla en el primer uso.
5. Desactiva el interruptor, usa la interfaz flotante o la acción de notificación para detener.
6. Desactivar el plugin en el Centro de plugins oculta la entrada del panel. Vuelve a activarlo para restaurarla.

******

### Permisos y privacidad

Todo el procesamiento de pantalla ocurre localmente en el dispositivo y comienza solo tras una acción explícita del usuario.

- Captura de pantalla: Android muestra un consentimiento del sistema para cada sesión
- Mostrar sobre otras aplicaciones: se usa solo para el selector flotante táctil
- Servicio en primer plano y notificación: mantienen una captura activa visible y fácil de detener
- Portapapeles: se escribe solo cuando el usuario pulsa una acción de copia
- Red y almacenamiento: no se solicita permiso de red ni de almacenamiento compartido

Rechazar el permiso para mostrar sobre otras apps o capturar la pantalla cancela el inicio de forma segura. Si rechazas las notificaciones, se muestra un aviso y el inicio continúa.

******

### Compatibilidad

Los modos independiente y plugin tienen requisitos mínimos diferentes.

| Modo | Requisito mínimo |
|---|---|
| Aplicación independiente | Android 7.0 (API 24) or later |
| Herramienta ampliada de AutoJs6 | AutoJs6 versionCode 5316 or later |

******

### Contrato del plugin

Los siguientes datos son para desarrolladores del host y del plugin. El servicio Binder y Wake Activity están protegidos por org.autojs.permission.PLUGIN, mientras que la entrada del lanzador no está limitada por ese permiso.

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

### Historial de versiones

#### v2.0.1

_2026/10/04_

- `Mejorado` Los iconos del centro de plugins usan los tamaños, posiciones, imágenes claras y oscuras y fondos circulares ajustados en Icon Studio, conservando fuentes y parámetros reproducibles

#### v2.0.0

_2026/10/03_

- `Aviso` La versión 2.0 usa el nuevo ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android la instala como una aplicación independiente; la anterior puede permanecer instalada y los ajustes no se migran automáticamente. Concede los permisos de nuevo y usa AutoJs6 versionCode 5316 o posterior para el modo plugin. La etiqueta del panel de AutoJs6 no cambia
- `Mejorado` Screen Color Picker pasa a llamarse 3-Color Picker, con una nueva pantalla de inicio, iconos claros/oscuros y cuatro modos de icono del lanzador
- `Mejorado` Referencia a MT Manager (bm.mt.plus) v2.26.9, agradecimientos y procedimiento para consultas de derechos en Ajustes y en la documentación
- `Mejorado` Tamaño visual uniforme de los iconos del lanzador y del Centro de complementos, con fondos transparentes y diseños en blanco, negro o grises neutros

#### v1.2.0

_2026/09/30_

- `Añadido` Unificar los ajustes independientes: idioma, modo nocturno, color e icono. Seguir AutoJs6 de forma predeterminada con alternativa del sistema, superficies neutras y controles adaptados. Aplicar los cambios tras confirmar, ofrecer una vista previa HEX/RGB y usar el modo adaptable automático por defecto, conservando las elecciones explícitas al actualizar.

[Ver el CHANGELOG completo](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-es.md)

******

### Compilación

Usa el Gradle Wrapper incluido y JDK 21 para ejecutar pruebas, compilar los APK de depuración e instrumentación y después ejecutar lint.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

Los README, las instrucciones del plugin y los changelog se generan desde fuentes JSON. Tras editar una fuente ejecuta los dos comandos siguientes.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### Referencia de diseño y agradecimientos

El diseño actual toma como referencia MT Manager (bm.mt.plus) v2.26.9, especialmente la interacción de su selector de color flotante. Agradecemos el trabajo de sus desarrolladores. Este proyecto es independiente; este reconocimiento no implica afiliación, respaldo ni autorización. Los titulares de derechos pueden contactarnos mediante las Issues del proyecto. Revisaremos las consultas y colaboraremos con correcciones de atribución, sustituciones o retiradas según corresponda.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

Documentación del proyecto: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)

******

### Licencia

El código del proyecto se distribuye bajo Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/16kb.md)
