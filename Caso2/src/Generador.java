import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Generador {
    private static final int dimFiltro = 3;

    // Tamaños en Bytes (B)
    private static final int tamInt = 4;

    private int tamPagina;
    private int nf;
    private int nc;
    private String rutaArchivo;

    private double numInt;
    private double numIntPagina;
    private int numPaginas;
    private ArrayList<String> referencias;

    private int[][] mat1;
    private int[][] mat2;
    private int[][] mat3;

    private String[][] matDatosVirtual;
    private String[][] matFiltroVirtual;
    private String[][] matResultVirtual;

    public Generador(int tamPagina, int nf, int nc, String nombreArchivo) {
        this.tamPagina = tamPagina;
        this.nf = nf;
        this.nc = nc;
        this.rutaArchivo = "Caso2/" + nombreArchivo + ".in";

        this.numInt = 2 * nf * nc + Math.pow(dimFiltro , 2);
        this.numIntPagina = Math.floor(tamPagina / tamInt);
        this.numPaginas = (int) Math.ceil(this.numInt / this.numIntPagina);
        this.referencias = new ArrayList<String>();

        this.mat1 = new int[nf][nc];
        this.mat2 = new int[dimFiltro][dimFiltro];
        this.mat3 = new int[nf][nc];

        this.matDatosVirtual = new String[nf][nc];
        this.matFiltroVirtual = new String[dimFiltro][dimFiltro];
        this.matResultVirtual = new String[nf][nc];
    }

    public void generarReferencias() {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                archivo.delete();
            }
            archivo.createNewFile();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true));
            bw.write("TP=" + tamPagina + "\n");
            bw.write("NF=" + nf + "\n");
            bw.write("NC=" + nc + "\n");
            bw.write("NF_NC_Filtro=" + dimFiltro + "\n");
            generarPagsVirtualesDesplazamientos();
            filtrarMatrizDatos();
            bw.write("NR=" + referencias.size() + "\n");
            bw.write("NP=" + numPaginas);
            for (String ref : referencias) {
                bw.write("\n" + ref);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filtrarMatrizDatos() {
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
                        generarReferencia("M", i2, j2, "R");
                        generarReferencia("F", i3, j3, "R");
                    }
                }

                if (acum >= 0 && acum <= 255) {
                    mat3[i][j] = acum;
                } else if (acum < 0) {
                    mat3[i][j] = 0;
                } else {
                    mat3[i][j] = 255;
                }
                generarReferencia("R", i, j, "W");
            }
        }

        // Se asigna un valor predefinido a los bordes
        for (int i = 0; i < nc; i++) {
            mat3[0][i] = 0;
            generarReferencia("R", 0, i,  "W");
            mat3[nf - 1][i] = 255;
            generarReferencia("R", nf - 1, i,  "W");
        }
        for (int i = 1; i < nf - 1; i++) {
            mat3[i][0] = 0;
            generarReferencia("R", i, 0,  "W");
            mat3[i][nc - 1] = 255;
            generarReferencia("R", i, nc - 1,  "W");
        }
    }

    private void filtrarMatrizDatos2() {
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
    }

    private void generarReferencia(String matriz, int i, int j, String bitAccion) {
        String pagVirtualDesplazamiento = "";
        if (matriz.equals("F")) {
            pagVirtualDesplazamiento = matFiltroVirtual[i][j];
        } else if (matriz.equals("M")) {
            pagVirtualDesplazamiento = matDatosVirtual[i][j];
        } else if (matriz.equals("R")) {
            pagVirtualDesplazamiento = matResultVirtual[i][j];
        }
        referencias.add(matriz + "[" + i + "][" + j + "]," + pagVirtualDesplazamiento + "," + bitAccion);
    }

    private void generarPagsVirtualesDesplazamientos(){
        int tamRealPagina = tamPagina - (tamPagina % tamInt);
        int pagina = 0;
        int desplazamiento = 0;

        // Generar filtro
        for (int i = 0; i < dimFiltro; i++) {
            for (int j = 0; j < dimFiltro; j++) {
                matFiltroVirtual[i][j] = pagina + "," + desplazamiento;
                desplazamiento += tamInt;
                if (desplazamiento == tamRealPagina) {
                    pagina++;
                    desplazamiento = 0;
                }
            }
        }

        // Generar datos
        for (int i = 0; i < nf; i++) {
            for (int j = 0; j < nc; j++) {
                matDatosVirtual[i][j] = pagina + "," + desplazamiento;
                desplazamiento += tamInt;
                if (desplazamiento == tamRealPagina) {
                    pagina++;
                    desplazamiento = 0;
                }
            }
        }

        // Generar resultado
        for (int i = 0; i < nf; i++) {
            for (int j = 0; j < nc; j++) {
                matResultVirtual[i][j] = pagina + "," + desplazamiento;
                desplazamiento += tamInt;
                if (desplazamiento == tamRealPagina) {
                    pagina++;
                    desplazamiento = 0;
                }
            }
        }

    }

}
