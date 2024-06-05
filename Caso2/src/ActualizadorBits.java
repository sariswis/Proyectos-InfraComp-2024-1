public class ActualizadorBits extends Thread {
    private EstadoBits estadoBits;
    private boolean ejecutar = true;

    public ActualizadorBits(EstadoBits estadoBits) {
        this.estadoBits = estadoBits;
    }

    public void run(){
        while (ejecutar){
            estadoBits.actualizarBitR();
        }
    }

    public void detener(){
        ejecutar = false;
    }
    
}
