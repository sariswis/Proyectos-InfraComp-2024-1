import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CyclicBarrier;

public class Tablero {
    private static String ruta;
    private static int generaciones;
    private static int dimension;
    private static Celda[][] celdas;
    
    public static void main(String[] args) {
        obtenerInput();
        leerArchivo();
        asignarVecinos();
        iniciarJuego();
    }

    public static void obtenerInput(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese la ruta del archivo: ");
            Tablero.ruta = br.readLine();
            System.out.println("Ingrese las generaciones: ");
            Tablero.generaciones = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void leerArchivo(){
        try {
            FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            Tablero.dimension = Integer.parseInt(br.readLine());
            Tablero.celdas = new Celda[dimension][dimension];

            String linea = br.readLine();
            String[] bools;
            int i = 0;
            int j;
            while(linea != null) {
                j = 0;
                bools = linea.split(",");
                for (String bool : bools) {
                    celdas[i][j] = new Celda(i, Boolean.parseBoolean(bool), generaciones);
                    j++;
                }
                linea = br.readLine();
                i++;
            }

            br.close();
            CyclicBarrier barrera = new CyclicBarrier(dimension*dimension);
            Celda.setBarrera(barrera);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void asignarVecinos(){
        int nuevo_i, nuevo_j; 
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int d_i = -1; d_i < 2; d_i++) {
                    for (int d_j = -1; d_j < 2; d_j++) {
                        nuevo_i = i + d_i;
                        nuevo_j = j + d_j;
                        if (0 <= nuevo_i  && nuevo_i < dimension && 0 <= nuevo_j  && nuevo_j < dimension && (d_i != 0 || d_j != 0)) {
                            celdas[i][j].agregarBuzonVecino(celdas[nuevo_i][nuevo_j].getBuzon());
                        }
                    }
                }
            }
        }
    }

    public static void iniciarJuego(){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                celdas[i][j].start();
            }
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                try {
                    celdas[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        imprimirTablero();
    }
                
    public static void imprimirTablero() {
        imprimirLinea(dimension);
        for (int i = 0; i < dimension; i++) {
            System.out.print("|");
            for (int j = 0; j < dimension; j++) {
                if(celdas[i][j].estaViva()){
                    System.out.print(" * " + "|");
                } else {
                    System.out.print("   " + "|");
                }
            }
            imprimirLinea(dimension);
        }
    }

    public static void imprimirLinea(int dimension) {
        System.out.println();
        System.out.print("+");
        for (int i = 0; i < dimension; i++) {
            System.out.print("---+");
        }
        System.out.println();
    }


}
