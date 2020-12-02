import java.util.concurrent.Semaphore;

public class Hydrogen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private int id;
    private static int hydrogensWaiting = 0;
    public static Semaphore waitSecondHydrogen = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);


    public Hydrogen() {
        this.id = (int) Thread.currentThread().getId();
    }

    @Override
    public void run() {
        System.out.println("    Aquest és l'Hidrogen " + id);
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (hydrogensWaiting < 1) {
            try {
                hydrogensWaiting++;
                System.out.println("Arriba el primer hidrogen...");
                mutex.release();
                waitSecondHydrogen.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Arriba segon hidrogen...");
            Oxygen.waitForHydrogens.release();
        }
        mutex.release();
        waitSecondHydrogen.release();
    }
}
