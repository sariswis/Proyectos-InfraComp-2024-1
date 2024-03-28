import java.util.ArrayList;

public class Buzon {
    private ArrayList<Boolean> mensajes;
    private int tamanio;

    public Buzon(int tamanio) {
        this.mensajes = new ArrayList<Boolean>();
        this.tamanio = tamanio;
    }

    public synchronized void enviarMensaje(boolean mensaje) {
        while(mensajes.size() == tamanio) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mensajes.add(mensaje);
        notifyAll();
    }

    public synchronized Boolean recibirMensaje() {
        if(mensajes.size() == 0) {
            return null;
        }
        Boolean b = mensajes.remove(0);
        notifyAll();
        return b;
    }
    
}
