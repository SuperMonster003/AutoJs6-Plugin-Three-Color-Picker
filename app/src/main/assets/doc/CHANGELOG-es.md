******

### Historial de versiones

******

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
