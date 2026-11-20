3-Color Picker es un selector de color de pantalla: un anillo objetivo que se puede arrastrar y una lupa en vivo seleccionan cualquier píxel de la pantalla, cuyo color y coordenadas se pueden copiar después en HEX o RGB. Funciona por sí solo desde el lanzador o como la herramienta "Selector de color de pantalla" del panel lateral de AutoJs6; el contenido de la pantalla solo se procesa en el dispositivo.

### Primeros pasos

1. Abre 3-Color Picker desde el lanzador y toca "Iniciar selector de color".
2. Cuando se solicite, permite que se muestre sobre otras aplicaciones y después permite esta captura de pantalla.
3. Cambia a la aplicación que quieres muestrear, arrastra el anillo objetivo cerca del píxel y desliza sobre el disco de la lupa para el ajuste fino.
4. Toca el color o las coordenadas del anillo para copiarlos; toca la × de la parte inferior del anillo para detenerlo.

### Funciones principales

- **Selector flotante**: El anillo objetivo sirve para los desplazamientos largos, mientras la lupa muestra la cuadrícula de píxeles ampliada, el color actual y las coordenadas; desliza sobre el disco de la lupa para avanzar píxel a píxel.
- **Copiar colores y coordenadas**: Toca el color o las coordenadas para copiarlos y mantén pulsado el color para alternar entre HEX y RGB; con "Copiar solo el valor numérico" activado se omiten el # y las letras R, G, B.
- **Panel lateral de AutoJs6**: Al habilitar el plugin en el "Centro de plugins" de AutoJs6, el panel lateral muestra un interruptor "Selector de color de pantalla" que inicia el selector al activarlo y lo detiene al desactivarlo.
- **Ajustes del selector**: En "Ajustes del selector" dentro de "Ajustes", ajusta "Tamaño de la lupa", "Rango de muestreo", "Velocidad de ajuste fino" y "Mostrar líneas de cuadrícula".
- **Apariencia**: "Idioma", "Modo oscuro" y "Color del tema" usan por defecto "Seguir AutoJs6" y también se pueden ajustar por separado; "Icono del lanzador" ofrece varios estilos.
- **Notificación de actividad**: Mientras el selector funciona, se muestra la notificación "El selector de color de pantalla está activo" con la acción "Detener".
- **Historial y agradecimientos**: "Ajustes" también abre el "Historial de versiones" y la "Referencia de diseño y agradecimientos".

### Requisitos

- Android 7.0 o posterior. Usarlo en el panel lateral de AutoJs6 requiere AutoJs6 6.8.0 o posterior; usarlo por sí solo no requiere AutoJs6.
- El permiso para mostrarse sobre otras aplicaciones se usa para el selector flotante; Android pide confirmar la captura de pantalla cada vez que se inicia el selector.
- El permiso de notificaciones se usa para el estado de actividad y la acción "Detener"; sin él, el selector sigue funcionando.
- No usa la red ni guarda capturas de pantalla, y solo escribe en el portapapeles cuando tocas para copiar.

### Preguntas frecuentes

**Casos en que se deniega mostrarse sobre otras aplicaciones o la captura de pantalla**

El inicio se cancela de forma segura y no queda ninguna superposición. Toca de nuevo "Iniciar selector de color" para volver a solicitar el permiso.

**Motivos por los que "Selector de color de pantalla" no aparece en el panel lateral de AutoJs6**

Comprueba que el plugin esté habilitado en el "Centro de plugins" de AutoJs6 y autorízalo si se solicita. Cuando el plugin se deshabilita allí, la entrada se oculta.

El diseño toma como referencia la interacción del selector de color flotante de [MT Manager](https://mt.cc/) (bm.mt.plus) v2.26.9, y agradecemos a sus desarrolladores; este proyecto es independiente y este reconocimiento no implica afiliación, respaldo ni autorización. Consulta la documentación del proyecto: [Referencia de diseño](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) y [Derechos y retirada](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md).

Los detalles completos de las funciones y los permisos están en la [página del proyecto](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker).
