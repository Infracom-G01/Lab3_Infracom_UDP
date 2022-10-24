# Lab3 - UDP

## Integrantes

* Luisa Fuentes
* Maria Paula Gonzalez
* Andres Lugo
* Jessica Robles

## Ejecución

Para la ejecución de la aplicación es necesario cambiar el parámetro del host y del puerto en donde se va a crear la conexión entre el cliente y el servidor. Para esto se debe ir a la clase cliente y cambiar estos dos atributos. Además, es posible modificar el tamaño de los chunks (segmentos de archivo) que se van a enviar. Para esto se debe modificar el atributo tamChunk de la clase cliente por un número menor o igual a 64000. Asimismo, es posible aumentar la probabilidad de la recepción completa de los paquetes por parte de los clientes aumentando el número de milisegundos de la línea "Thread.sleep(#) en el ciclo que realiza el Delegado. En caso que esté sea ejecutado sobre la máquina local se debe correr el main de la clase Servidor para que esté escuchando en el momento que ingresen los clientes. En caso de que sea ejecutado en una máquina virtual es necesario generar el .jar de la clase. Una vez que el servidor ya se esté ejecutando se debe hacer run a la clase main, en esta se va a preguntar la información necesaria al usuario para ejecutar la prueba deseada, seguido a esto se crearán los clientes indicados y se comenzará la transferencia de archivos.

Para revisar los logs de cada uno al final de la ejecución se debe ir a la carpeta logs que se encuentra en el directorio raíz, y para revisar los archivos transferidos a los clientes, se debe ir al equipo donde se ejecutó la clase cliente y aquí revisar la carpeta ArchivosRecibidos en el directorio raíz.
