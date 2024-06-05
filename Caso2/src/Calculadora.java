import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Calculadora {
    // Tiempos en nanosegundos (ns)
    private static final long tiempoLecturaRAM = 30;
    private static final long tiempoLecturaSWAP = 10000000;

    // Tiempos en milisegundos (ms)
    private static final long tiempoThreadRAM = 1;
    private static final long tiempoThreadBitR = 4;

    private int nMarcos;
    private String rutaArchivo;

    private EstadoBits estadoBits;
    private ActualizadorBits actualizador;

    private HashMap<Integer, List<String>> referencias;
    private Double[] tablaPaginas;
    private int[] tablaAuxiliar;
    private boolean[] memoriaReal;

    private long nReferencias;
    private long nPaginas;
    private long hits;
    private long misses;

    /*
     * Constructor, podría necesitar más cosas más tarde.
     */
    public Calculadora (int nMarcos, String nombreArchivo){
        this.nMarcos = nMarcos;
        this.rutaArchivo = "Caso2/" + nombreArchivo + ".in";
        this.referencias = new HashMap<Integer, List<String>>();
        this.memoriaReal = new boolean[nMarcos];
    }

    /*
     * Este método se encarga de leer el archivo de referencias creado por la parte 1, de momento, almacena unicamente
     * el número de páginas y las referencias. Las referencias son guardadas en un mapa.
     */
    public void calcularDatos (){
        leerArchivo();
        rellenarEstructuras();
        realizarCalculos();
        imprimirDatos();
    }

    private void leerArchivo(){
        try {
            File referenciasArchivo = new File(rutaArchivo);
            Scanner scan = new Scanner(referenciasArchivo);
            for (int i = 0; i < 4; i++){
                scan.nextLine();
            }
            String[] nr = scan.nextLine().split("=");
            String[] np = scan.nextLine().split("=");
            nReferencias = Integer.parseInt(nr[1]);
            nPaginas = Integer.parseInt(np[1]);

            List<String> informacion;
            String [] regPartes;
            for (int i = 0; i < nReferencias; i++){
                regPartes = scan.nextLine().split(",");
                informacion = new ArrayList<String>();
                for (int j = 1; j < regPartes.length; j++){
                    informacion.add(regPartes[j]);
                }
                referencias.put(i, informacion);
            }
            scan.close();
        } catch (FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
            
        }
    }

    private void rellenarEstructuras(){
        estadoBits = new EstadoBits((int) nPaginas);
        tablaPaginas = new Double[(int) nReferencias];
        tablaAuxiliar = new int[(int) nReferencias];
        for (int i = 0; i < nPaginas; i++) {
            tablaPaginas[i] = Double.POSITIVE_INFINITY;
            tablaAuxiliar[i] = i;
        }
    }

    private void realizarCalculos(){
        int i = 0;
        List<String> ref;
        int pagina = 0;
        boolean write = false;

        actualizador = new ActualizadorBits(estadoBits);
        actualizador.start();
        
        int m = 0;
        int pagBorrar = 0;
        while (i < nReferencias){
            try {
                Thread.sleep(tiempoThreadRAM);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ref = referencias.get(i);
            pagina = Integer.parseInt(ref.get(0));
            write = ref.get(2).equals("W");

            if ((i+1) % tiempoThreadBitR == 0){
                estadoBits.solicitarActualizarBitR();
            } 

            if (tablaPaginas[pagina].isInfinite()){
                misses++;
                m = 0;
                // Mientras haya marcos y estén ocupados
                while (m < nMarcos && memoriaReal[m]){
                    m++;
                }
                // Si hay un marco libre
                if(m < nMarcos){
                    tablaPaginas[pagina] = (double) m; // Se manda a apuntar al marco libre
                    memoriaReal[m] = true; // Se marca como ocupado
                } else {
                    pagBorrar = estadoBits.seleccionarPaginaEliminar(tablaPaginas);
                    tablaPaginas[pagina] = tablaPaginas[pagBorrar];
                    tablaPaginas[pagBorrar] = Double.POSITIVE_INFINITY;
                }
            } else {
                hits++;
            }
            estadoBits.referenciarPagina(pagina, write);
            i++;
        }

        if (i % tiempoThreadBitR != 0){
            try {
                Thread.sleep((int) tiempoThreadBitR - (i % tiempoThreadBitR));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        actualizador.detener();
        estadoBits.solicitarActualizarBitR();
    }

    private void imprimirDatos(){
        System.out.println("Datos calculados:");
        System.out.println("Hits: " + hits + ", " + String.format("%.2f", hits * 100.0 / nReferencias) + "%");
        System.out.println("Fallas: " + misses + ", " + String.format("%.2f", misses * 100.0 / nReferencias) + "%");
        System.out.println("Tiempo de ejecución (ns): " + (hits * tiempoLecturaRAM + misses * tiempoLecturaSWAP));
        System.out.println("Tiempo si solamente fuesen hits (ns): " + (nReferencias * tiempoLecturaRAM));
        System.out.println("Tiempo si solamente fuesen fallos (ns): " + (nReferencias * tiempoLecturaSWAP));
    }

}
