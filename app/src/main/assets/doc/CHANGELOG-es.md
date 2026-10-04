******

### Historial de versiones

******

# v2.0.1

_2026/10/04_

- `Mejorado` Los iconos del centro de plugins usan los tamaños, posiciones, imágenes claras y oscuras y fondos circulares ajustados en Icon Studio, conservando fuentes y parámetros reproducibles

# v2.0.0

_2026/10/03_

- `Aviso` La versión 2.0 usa el nuevo ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android la instala como una aplicación independiente; la anterior puede permanecer instalada y los ajustes no se migran automáticamente. Concede los permisos de nuevo y usa AutoJs6 versionCode 5316 o posterior para el modo plugin. La etiqueta del panel de AutoJs6 no cambia
- `Mejorado` Screen Color Picker pasa a llamarse 3-Color Picker, con una nueva pantalla de inicio, iconos claros/oscuros y cuatro modos de icono del lanzador
- `Mejorado` Referencia a MT Manager (bm.mt.plus) v2.26.9, agradecimientos y procedimiento para consultas de derechos en Ajustes y en la documentación
- `Mejorado` Tamaño visual uniforme de los iconos del lanzador y del Centro de complementos, con fondos transparentes y diseños en blanco, negro o grises neutros

# v1.2.0

_2026/09/30_

- `Añadido` Unificar los ajustes independientes: idioma, modo nocturno, color e icono. Seguir AutoJs6 de forma predeterminada con alternativa del sistema, superficies neutras y controles adaptados. Aplicar los cambios tras confirmar, ofrecer una vista previa HEX/RGB y usar el modo adaptable automático por defecto, conservando las elecciones explícitas al actualizar.

# v1.1.3

_2026/09/19_

- `Corregido` Advertencias de lectura de SDK XML v4 con AGP 9.1 y comprobaciones de alineación nativa de APK activadas por error al ensamblar pruebas unitarias JVM, mediante los plugins de compilación compartidos 1.8.3
- `Mejorado` Tras compileSdk, targetSdk sube a 37 (Android 17); el comportamiento del plugin no depende del nuevo objetivo

# v1.1.2

_2026/09/16_

- `Corregido` Cerrar el diálogo anterior de permisos de superposición al recrear la pantalla para evitar ventanas residuales y conflictos de foco

# v1.1.1

_2026/09/15_

- `Mejorado` compileSdk sube a 37 (Android 17); targetSdk se mantiene en 36 hasta verificar el comportamiento que depende del objetivo

# v1.1.0

_2026/09/13_

- `Añadido` Historial de versiones local desde la interfaz con traducciones y alternativa en inglés
- `Mejorado` Comprobación de la firma completa, los APK esperados y la documentación reproducible de cada versión
- `Mejorado` Conservar un icono PNG base derivado del icono existente y usarlo en la documentación

# v1.0.1

_2026/09/11_

- `Corregido` Evitar el bloqueo de ThemeEnforcement al aplicar un tema Material explícito a los botones Material creados desde el Context del servicio/aplicación
- `Corregido` Mostrar la herramienta del panel de AutoJs6 solo mientras el plugin esté activado en el Centro de plugins y restaurarla al volver a activarlo
- `Mejorado` La verificación de compilación rechaza dependencias nativas accidentales y genera un informe JSON

# v1.0.0

_2026/09/01_

- `Añadido` Ejecuta el mismo APK desde el iniciador o úsalo como la herramienta extendida Selector de color de pantalla de AutoJs6
- `Añadido` Usa un selector flotante táctil con muestra ampliada, coordenadas, HEX, RGB y HSL
- `Añadido` Usa la interfaz Binder contract v1 con getInfo, getState, getStartPendingIntent y stop
- `Añadido` Usa la interfaz local y la documentación en 10 idiomas con procesamiento de color totalmente sin conexión
- `Mejorado` Gestiona de forma explícita los permisos de superposición, captura de pantalla, servicio en primer plano y notificación con una acción de detención
- `Mejorado` Admite el protocolo Wake de AutoJs6 y la versión mínima 5278 del anfitrión con CI y comprobaciones de cambios en la documentación
