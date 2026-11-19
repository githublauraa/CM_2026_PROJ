# Corrección de ubicación al publicar reseñas

## Causas encontradas

El emulador tenía permiso aproximado, sin permiso preciso, GPS encendido y el proveedor de red deshabilitado. Android no entregaba coordenadas a la app. El código anterior solicitaba GPS durante 3,5 segundos y luego ubicación fusionada durante 3 segundos, rechazando cualquier punto anterior al instante de tocar Publicar.

## Cambio

- Solicitar juntos los permisos aproximado y preciso, dejando que Android presente la elección al usuario. Si ya hay permiso preciso, continuar directamente.
- Consultar Play services y los proveedores Android disponibles en paralelo. Un fallo en uno no impide probar los demás.
- Aceptar coordenadas válidas de hasta 60 segundos de antigüedad y esperar hasta 22 segundos por un punto reciente. Cancelar solicitudes pendientes al terminar o salir de la pantalla.
- Respetar la precisión concedida por el usuario. Antes de compartir en el mapa se redondean las coordenadas a dos decimales para conservar la ubicación aproximada anunciada por el formulario.
- Mostrar un error visible si no se obtiene ubicación, con orientación sobre permisos. Nunca inventar coordenadas ni publicar sin ubicación automáticamente.

## Verificación

Pruebas unitarias para coordenadas recientes, antiguas, futuras o inválidas y para conservar coordenadas y la marca de spoilers al enviar la reseña al repositorio.

La prueba instrumentada `ReviewLocationProviderTest` no escribe reseñas: consulta el mismo proveedor usado al publicar. Requiere `-e locationCheck true` y un GPS de emulador alimentado durante la prueba. En este emulador falló con permiso exclusivamente aproximado y proveedor de red deshabilitado; pasó con permiso preciso y coordenadas simuladas de Bogotá. El permiso preciso concedido temporalmente para la prueba se revocó al terminar.

La app no puede obtener una ubicación si Android no dispone de señal/proveedor o si el usuario deniega los permisos. En un emulador hay que suministrar una ubicación desde los controles de Location; encender el GPS no genera coordenadas por sí solo. Las coordenadas de la prueba fueron simuladas, no una lectura de la ubicación física del equipo.
