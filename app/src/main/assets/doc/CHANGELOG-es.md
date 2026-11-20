******

### Historial de versiones

******

# v2.0.2

###### 2026/11/20

* `Mejora` El historial de versiones pasa de un cuadro de diálogo a una página propia que se expande y contrae por versión: una versión contraída muestra su fecha y el número de entradas por categoría, una expandida nombra cada categoría una sola vez; todas las versiones se pueden expandir o contraer a la vez, la más reciente se abre expandida y el nombre del plugin aparece como subtítulo
* `Mejora` Las instrucciones del Centro de plugins se reescribieron en cuatro partes: primeros pasos, funciones principales, requisitos y preguntas frecuentes
* `Dependencia` Añadir Jetpack Compose BOM 2026.09.00 (página Historial de versiones)
* `Dependencia` Actualizar los plugins de compilación compartidos versión 1.9.0 -> 1.9.7

# v2.0.1

###### 2026/10/17

* `Corrección` Completar las traducciones que faltaban en los ajustes de actualización
* `Corrección` En la interfaz en árabe, 3-Color Picker ya no aparece invertido como Color Picker-3
* `Corrección` El historial de versiones ahora agrupa los cambios por versión con fechas y tipos de cambio, en lugar de mostrar símbolos de formato sin procesar
* `Mejora` Los iconos del centro de plugins usan los tamaños, posiciones, imágenes claras y oscuras y fondos circulares ajustados en Icon Studio, conservando fuentes y parámetros reproducibles
* `Mejora` Los iconos de información de la aplicación de Android comparten las imágenes y los fondos claros y oscuros de Icon Studio, conservando las imágenes transparentes del centro de plugins y las opciones del lanzador

# v2.0.0

###### 2026/10/03

* `Sugerencia` La versión 2.0 usa el nuevo ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android la instala como una aplicación independiente; la anterior puede permanecer instalada y los ajustes no se migran automáticamente. Concede los permisos de nuevo y usa AutoJs6 versionCode 5316 o posterior para el modo plugin. La etiqueta del panel de AutoJs6 no cambia
* `Mejora` Screen Color Picker pasa a llamarse 3-Color Picker, con una nueva pantalla de inicio, iconos claros/oscuros y cuatro modos de icono del lanzador
* `Mejora` Referencia a MT Manager (bm.mt.plus) v2.26.9, agradecimientos y procedimiento para consultas de derechos en Ajustes y en la documentación
* `Mejora` Tamaño visual uniforme de los iconos del lanzador y del Centro de complementos, con fondos transparentes y diseños en blanco, negro o grises neutros

# v1.2.0

###### 2026/09/30

* `Nuevo` Unificar los ajustes independientes: idioma, modo nocturno, color e icono. Seguir AutoJs6 de forma predeterminada con alternativa del sistema, superficies neutras y controles adaptados. Aplicar los cambios tras confirmar, ofrecer una vista previa HEX/RGB y usar el modo adaptable automático por defecto, conservando las elecciones explícitas al actualizar.

# v1.1.3

###### 2026/09/19

* `Corrección` Advertencias de lectura de SDK XML v4 con AGP 9.1 y comprobaciones de alineación nativa de APK activadas por error al ensamblar pruebas unitarias JVM, mediante los plugins de compilación compartidos 1.8.3
* `Mejora` Tras compileSdk, targetSdk sube a 37 (Android 17); el comportamiento del plugin no depende del nuevo objetivo

# v1.1.2

###### 2026/09/16

* `Corrección` Cerrar el diálogo anterior de permisos de superposición al recrear la pantalla para evitar ventanas residuales y conflictos de foco

# v1.1.1

###### 2026/09/15

* `Mejora` compileSdk sube a 37 (Android 17); targetSdk se mantiene en 36 hasta verificar el comportamiento que depende del objetivo

# v1.1.0

###### 2026/09/13

* `Nuevo` Historial de versiones local desde la interfaz con traducciones y alternativa en inglés
* `Mejora` Comprobación de la firma completa, los APK esperados y la documentación reproducible de cada versión
* `Mejora` Conservar un icono PNG base derivado del icono existente y usarlo en la documentación

# v1.0.1

###### 2026/09/11

* `Corrección` Evitar el bloqueo de ThemeEnforcement al aplicar un tema Material explícito a los botones Material creados desde el Context del servicio/aplicación
* `Corrección` Mostrar la herramienta del panel de AutoJs6 solo mientras el plugin esté activado en el Centro de plugins y restaurarla al volver a activarlo
* `Mejora` La verificación de compilación rechaza dependencias nativas accidentales y genera un informe JSON

# v1.0.0

###### 2026/09/01

* `Nuevo` Ejecuta el mismo APK desde el iniciador o úsalo como la herramienta extendida Selector de color de pantalla de AutoJs6
* `Nuevo` Usa un selector flotante táctil con muestra ampliada, coordenadas, HEX, RGB y HSL
* `Nuevo` Usa la interfaz Binder contract v1 con getInfo, getState, getStartPendingIntent y stop
* `Nuevo` Usa la interfaz local y la documentación en 10 idiomas con procesamiento de color totalmente sin conexión
* `Mejora` Gestiona de forma explícita los permisos de superposición, captura de pantalla, servicio en primer plano y notificación con una acción de detención
* `Mejora` Admite el protocolo Wake de AutoJs6 y la versión mínima 5278 del anfitrión con CI y comprobaciones de cambios en la documentación
