# Caso 2 - Memoria virtual

Se trata de una simulación de la memoria virtual de un proceso. Por ello, se simula un sistema de paginación usando el algoritmo de reemplazo de "Paginas no usadas recientemente”

Su implementación se realizó con concurrencia y sincronización de threads

## Funcionamiento

Se quiere calcular la cantidad de hits y de fallas de página de un proceso determinado, junto a sus tiempos de ejecución, asumiendo ciertos tiempos de lectura en memoria y SWAP

Dicho proceso aplica un filtro a una matriz de datos. Usa una matriz de datos, una matriz de filtro y una matriz de resultado

```java
for (int i = 1; i < nf - 1; i++) {
	for (int j = 1; j < nc - 1; j++) {
		// Recorrer los vecinos y aplicar el filtro
                // mat1: matriz de datos
                // mat2: matriz con el filtro (usaremos un filtro de 3x3 para resaltar bordes)
                // mat3: matriz resultante
                int acum = 0;
                for (int a = -1; a <= 1; a++) {
			for (int b = -1; b <= 1; b++) {
                        	int i2 = i + a;
                        	int j2 = j + b;
                        	int i3 = 1 + a;
                        	int j3 = 1 + b;
                        	acum += (mat1[i2][j2] * mat2[i3][j3]);
			}
                }

                if (acum >= 0 && acum <= 255) {
                    mat3[i][j] = acum;
                } else if (acum < 0) {
                    mat3[i][j] = 0;
                } else {
                    mat3[i][j] = 255;
                }
	}
}
// Se asigna un valor predefinido a los bordes
for (int i = 0; i < nc; i++) {
	mat3[0][i] = 0;
	mat3[nf - 1][i] = 255;
}
for (int i = 1; i < nf - 1; i++) {
	mat3[i][0] = 0;
	mat3[i][nc - 1] = 255;
}
```

## ¿Cómo ejecutar?

Diríjase a la clase Principal.java, ejecute el main y seleccione una de las dos opciones disponibles.

### Primera opción - Generación de referencias

#### Entradas

Digite los datos solicitados. Estos son el tamaño de la página, el número de filas y columnas de la matriz junto al nombre del archivo .in a generar.

#### **Salidas**

El archivo .in se generará y guardará en la carpeta del proyecto (Caso2) con el nombre que usted eligió.

Este archivo tendrá los datos de entrada (TP, NF y NC), la dimensión de la matriz de filtro (NF_NC_Filtro), el número de referencias (NR) y el número de páginas virtuales del proceso (NP).

Posteriormente, estarán las referencias indicando 4 campos. El primero de ellos es la matriz (M para matriz de datos, F para filtro y R para resultados), junto a su posición (fila y columna). Los tres campos restantes corresponden a la página virtual, el desplazamiento y el bit de acción (R para lectura y W para escritura)

### **Segunda opción - Cálculo de datos**

#### Entradas

Digite los datos solicitados. Estos son el número de marcos de página y el nombre del archivo generado previamente. Refiérase a algún archivo .in guardado en la carpeta del proyecto (Caso2)

#### **Salidas**

En consola, se imprimirán los hits, las fallas de página y los tiempos de ejecución de acuerdo a los tiempos asumidos

## Colaboradores

* Sara Sofía Cárdenas Rodríguez - [sariswis](https://github.com/sariswis)
* Leidy Johana Lozano Florez - [LeidyLozano](https://github.com/LeidyLozano)
