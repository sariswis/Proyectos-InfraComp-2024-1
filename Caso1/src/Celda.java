import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Celda  extends Thread {
    private boolean viva;
    private Buzon buzon;
    private ArrayList<Buzon> buzonesVecinos;
    private int generaciones;
    private static CyclicBarrier barrera;
    private int vecinosVivos;

    public Celda(int i, boolean viva, int generaciones) {
        this.viva = viva;
        this.buzon = new Buzon(i+1);
        this.buzonesVecinos = new ArrayList<Buzon>();
        this.generaciones = generaciones;;
    }

    public boolean estaViva() {
        return viva;
    }

    public Buzon getBuzon() {
        return buzon;
    }

    public ArrayList<Buzon> getBuzonesVecinos() {
        return buzonesVecinos;
    }

    public int getCantidadVecinos() {
        return buzonesVecinos.size();
    }

    public void agregarBuzonVecino(Buzon buzonVecino) {
        this.buzonesVecinos.add(buzonVecino);
    }

    public static void setBarrera(CyclicBarrier barrera) {
        Celda.barrera = barrera;
    }

    public void aumentarVecinosVivos() {
        vecinosVivos++;
    }

    @Override
    public void run() {
        for (int g = 0; g < generaciones; g++) {
            this.vecinosVivos = 0;
            Consumidor consumidor = new Consumidor(this);
            consumidor.start();
            producirMensajes();

            try {
                consumidor.join();
                barrera.await();
                cambiarEstado();
                barrera.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            
        }
    }

    public void producirMensajes(){
        for (Buzon buzon: this.getBuzonesVecinos()) {
            buzon.enviarMensaje(this.estaViva());
        }
    }

    public void cambiarEstado() {    
        if (viva) {
            if (vecinosVivos == 0 || vecinosVivos > 3) {
                viva = false;
            }
        } else {
            if (vecinosVivos == 3) {
                viva = true;
            }
        }
    }

}
