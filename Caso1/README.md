# Caso 1 - Juego de la Vida

Se trata de una simulación del Juego de la Vida (Conway's Game of Life), el cual es un autómata celular determinado por su estado inicial, por lo que no requiere jugadores

Su implementación se realizó con concurrencia y sincronización de threads

## Reglas del juego

Si una celda muerta tiene exactamente 3 celdas vecinas vivas, esta celda nace, es decir, al siguiente turno estará viva.

Si una celda viva tiene más de tres celdas vivas alrededor (sobrepoblación) o si no tiene celdas vivas alrededor (aislamiento), esta celda muere.

Por lo tanto, una celda se mantiene viva si tiene entre 1 y 3 celdas vecinas vivas adyacentes

## Ejemplo

Los tableros indican cuáles celdas o células están vivas (con un asterisco) o muertas (vacías)

Si tenemos el siguiente archivo .in de entrada

```
3
false,true,false
true,true,true
false,false,false
```

Este es el tablero o población inicial (turno 0)

```
Turno 0
+---+---+---+
|   | * |   |
+---+---+---+
| * | * | * |
+---+---+---+
|   |   |   |
+---+---+---+
```

Por lo que esta es la evolución en 4 turnos de la población inicial en el archivo .out generado

```
Turno 1         Turno 2         Turno 3         Turno 4
+---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
| * | * | * |   | * |   | * |   |   |   |   |   |   |   |   |
+---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
| * | * | * |   |   |   |   |   | * |   | * |   | * |   | * |
+---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
|   | * |   |   | * | * | * |   | * | * | * |   | * |   | * |
+---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
```

## ¿Cómo ejecutar?

Sitúese en el archivo src/Tablero.java donde se encuentra el main

Ejecute con Visual Studio Code

Ingrese la ruta (completa o relativa) del archivo y las generaciones o turnos a simular.

## Colaboradores

* Sara Sofía Cárdenas Rodríguez - [sariswis](https://github.com/sariswis)
* Sara Benavides Mora - [sarabemora](https://github.com/sarabemora)
