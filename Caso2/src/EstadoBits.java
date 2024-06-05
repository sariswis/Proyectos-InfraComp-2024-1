public class EstadoBits {
    private int[] rBits;
    private int[] mBits;
    private int nPaginas;

    public EstadoBits(int nPaginas) {
        this.nPaginas = nPaginas;
        this.rBits = new int[nPaginas];
        this.mBits = new int[nPaginas];
    }

    public synchronized void actualizarBitR () {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < nPaginas; i++){
            rBits[i] = 0;
        }
    }

    public synchronized void solicitarActualizarBitR(){
        notify();
    }

    public synchronized void referenciarPagina(int nPagina, boolean modificado){
        rBits[nPagina] = 1;
        if (modificado){
            mBits[nPagina] = 1;
        }
    }

    public synchronized int seleccionarPaginaEliminar (Double[] tablaPaginas){
        int pagBorrar = -1;
        for (int i = 0; i < nPaginas && pagBorrar == -1; i++){
            if (tablaPaginas[i] != Double.POSITIVE_INFINITY && rBits[i] == 0 && mBits[i] == 0){ // Clase 1
                pagBorrar = i;
            }
        }

        for (int i = 0; i < nPaginas && pagBorrar == -1; i++){
            if (tablaPaginas[i] != Double.POSITIVE_INFINITY && rBits[i] == 0 && mBits[i] == 1){ // Clase 2
                pagBorrar = i;
            }
        }

        for (int i = 0; i < nPaginas && pagBorrar == -1; i++){
            if (tablaPaginas[i] != Double.POSITIVE_INFINITY && rBits[i] == 1 && mBits[i] == 0){ // Clase 3
                pagBorrar = i;
            }
        }

        for (int i = 0; i < nPaginas && pagBorrar == -1; i++){
            if (tablaPaginas[i] != Double.POSITIVE_INFINITY && rBits[i] == 1 && mBits[i] == 1){ // Clase 4
                pagBorrar = i;
            }
        }

        return pagBorrar;
    }






    
}
