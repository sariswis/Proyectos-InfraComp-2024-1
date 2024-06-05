# Caso 3 - Comunicación segura

Se trata de un servidor con delegados que atiende a varios clientes, quienes desean realizar consultas seguras bajo estándares de confidencialidad e integridad

Su implementación se realizó con concurrencia y sincronización de threads

Igualmente, se hizo uso de las librerías nativas de Java (net, crypto y security) para garantizar una comunicación segura por sockets

## Funcionamiento

El delegado y el cliente se comunican bajo un protocolo de 21 pasos, donde se evidencia el uso de algoritmos de cifrado simétrico y asimétrico, firmas digitales y códigos de autenticación

Para la generación de la llave simétrica, se hace uso del algoritmo de Diffie-Hellman. Para la generación de la llave de cifrado y la del código de autenticación, se usa la mitad de un hash o digest como entrada

## ¿Cómo ejecutar?

Ejecute el main en la clase Servidor.java

Ingrese el número de clientes que desea simular.

En la consola, verá las operaciones realizadas por los clientes y delegados.

Finalmente, se añaden los tiempos de ejecución para los threads de clientes y delegados

## Archivos adicionales

En la carpeta datos, se encuentran los archivos de los números P y G generados (PG.in para los números estilizados y PyG.in para los números directos de la consola), junto a un diccionario de diez usuarios que son autenticados por el servidor (usuarios.in)

## Colaboradores

* Daniel Felipe Diaz Moreno - [ddi4z](https://github.com/ddi4z)
* Sara Sofía Cárdenas Rodríguez - [sariswis](https://github.com/sariswis)
* Sara Benavides Mora - [sarabemora](https://github.com/sarabemora)
