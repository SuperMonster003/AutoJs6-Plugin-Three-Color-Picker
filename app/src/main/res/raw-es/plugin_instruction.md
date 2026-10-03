# AutoJs6 3-Color Picker

3-Color Picker es una herramienta local de muestreo de color diseñada para pantallas táctiles. Su selector flotante combina un anillo objetivo arrastrable con una lupa en tiempo real que muestra una cuadrícula de píxeles ampliada, el color actual y las coordenadas exactas, con una interacción directa y clara.

La versión 2.0 usa el nuevo ID io.github.supermonster003.autojs6.plugin.three.color.picker. Android la instala como una aplicación independiente; la anterior puede permanecer instalada y los ajustes no se migran automáticamente. Concede los permisos de nuevo y usa AutoJs6 versionCode 5316 o posterior para el modo plugin. La etiqueta del panel de AutoJs6 no cambia.

### Uso independiente

1. Abre 3-Color Picker desde el lanzador del sistema.
2. Pulsa Iniciar selector de color y sigue la explicación para permitir mostrar sobre otras aplicaciones.
3. Permite la captura de pantalla actual en el aviso del sistema Android.
4. Cambia a la aplicación que quieras muestrear, arrastra el anillo objetivo hasta el píxel deseado y desliza sobre el disco de la lupa para afinar.
5. Pulsa el texto de color o de coordenadas en la lupa para copiarlo, mantén pulsado el texto de color para cambiar de formato o detén el selector en cualquier momento.

### Uso como herramienta de AutoJs6

1. Instala el APK del plugin. No necesitas abrir primero la entrada del lanzador.
2. Abre el Centro de plugins de AutoJs6 y comprueba que 3-Color Picker esté activado. Autoriza el plugin si se solicita.
3. Abre el panel de AutoJs6. Screen Color Picker aparece como la tercera entrada de Herramientas ampliadas, con su interruptor de ejecución desactivado de forma predeterminada.
4. Completa el consentimiento de superposición y captura de pantalla en el primer uso.
5. Desactiva el interruptor, usa la interfaz flotante o la acción de notificación para detener.
6. Desactivar el plugin en el Centro de plugins oculta la entrada del panel. Vuelve a activarlo para restaurarla.

### Permisos y privacidad

Todo el procesamiento de pantalla ocurre localmente en el dispositivo y comienza solo tras una acción explícita del usuario.

- Captura de pantalla: Android muestra un consentimiento del sistema para cada sesión
- Mostrar sobre otras aplicaciones: se usa solo para el selector flotante táctil
- Servicio en primer plano y notificación: mantienen una captura activa visible y fácil de detener
- Portapapeles: se escribe solo cuando el usuario pulsa una acción de copia
- Red y almacenamiento: no se solicita permiso de red ni de almacenamiento compartido

Rechazar el permiso para mostrar sobre otras apps o capturar la pantalla cancela el inicio de forma segura. Si rechazas las notificaciones, se muestra un aviso y el inicio continúa.

### Referencia de diseño y agradecimientos

El diseño actual toma como referencia MT Manager (bm.mt.plus) v2.26.9, especialmente la interacción de su selector de color flotante. Agradecemos el trabajo de sus desarrolladores. Este proyecto es independiente; este reconocimiento no implica afiliación, respaldo ni autorización. Los titulares de derechos pueden contactarnos mediante las Issues del proyecto. Revisaremos las consultas y colaboraremos con correcciones de atribución, sustituciones o retiradas según corresponda.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

Documentación del proyecto: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)
