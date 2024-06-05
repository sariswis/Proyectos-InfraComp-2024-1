import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Principal {
    private static Generador generador;
    private static Calculadora calculadora;

    public static void main(String[] args) {
        boolean continuar = true;
        while (continuar) {
            printMenu();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String opcion = br.readLine();
                switch (opcion) {
                    case "1":
                        System.out.println();
                        generarReferencias();
                        System.out.println();
                        break;
                    case "2":
                        System.out.println();
                        calcularDatos();
                        System.out.println();
                        break;
                    case "0":
                        continuar = false;
                        break;
                    default:
                        System.out.println();
                        System.out.println("Opción inválida");
                        System.out.println();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printMenu() {
        System.out.println("----------------------------------");
        System.out.println("¡Bienvenido!");
        System.out.println("Escriba el número de la opción:");
        System.out.println("1. Generación de las referencias");
        System.out.println("2. Cálculo de datos:");
        System.out.println("0. Salir");
        System.out.println("----------------------------------");
    }

    public static void generarReferencias() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese el tamaño de la página en Bytes (B):");
            int tamPagina = Integer.parseInt(br.readLine());
            System.out.println("Ingrese el número de filas de la matriz de datos:");
            int nf = Integer.parseInt(br.readLine());
            System.out.println("Ingrese el número de columnas de la matriz de datos:");
            int nc = Integer.parseInt(br.readLine());
            System.out.println("Ingrese el nombre del archivo a generar (sin extensión):");
            String nombreArchivo = br.readLine();

            System.out.println();
            System.out.println("Generando referencias...");
            generador = new Generador(tamPagina, nf, nc, nombreArchivo);
            generador.generarReferencias();
            System.out.println("Referencias generadas");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    public static void calcularDatos() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese el número de marcos de página:");
            int nMarcos = Integer.parseInt(br.readLine());
            System.out.println("Ingrese el nombre del archivo generado (sin extensión):");
            String nombreArchivo = br.readLine();

            System.out.println();
            System.out.println("Calculando datos...");
            calculadora = new Calculadora(nMarcos, nombreArchivo);
            calculadora.calcularDatos();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
