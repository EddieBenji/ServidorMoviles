Dentro del paquete "MyTest", hay dos clases que pueden correr para probar la transferencia de archivos. Dado que no puedo simular la transferencia desde la app, creé un server (que transfiere el archivo) y un cliente: FileSender y Client respectivamente. La transferencia sucede por socket, sin embargo, se utiliza el inputStream para recorrer el archivo.

Luego corrar el FileSender.java, después el Client.java y verán que se crea un copy.txt con la info de Document.txt

Lo que hice fue tomar el código del Client.java que utiliza para leer el archivo, y pegarlo en el bluetoothServer. Si todo llega a funcionar, solo me faltará mostrar la info del archivo.

AGREGAR LAS LIBRERÍAS, QUE ESTÁN EN LA CARPETA "LIB" AL PROYECTO.
