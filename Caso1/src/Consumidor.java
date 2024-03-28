public class Consumidor extends Thread {
    private Celda celda;

    public Consumidor(Celda celda) {
        this.celda = celda;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < celda.getCantidadVecinos()) {
            Boolean b = celda.getBuzon().recibirMensaje();
            if (b == null) {
                Thread.yield();
            } else {
                if (b) {
                    celda.aumentarVecinosVivos();
                }
                i++;
            }
        } 
    }
}
