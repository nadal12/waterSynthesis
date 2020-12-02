import java.util.concurrent.Semaphore;

public class Hydrogen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private static final int MAX_TIME_TO_ARRIVE = 3000;
    private int id;
    private static int hydrogensWaiting = 0;
    private static Semaphore waitSecondHydrogen = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);


    public Hydrogen(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("    Aquest és l'Hidrogen " + id);

        for (int i = 0; i < SYNTHESIS_NUMBER; i++) {
            try {
                Thread.sleep((long) (Math.random() * MAX_TIME_TO_ARRIVE));
                mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (hydrogensWaiting < 1) {
                try {
                    hydrogensWaiting++;
                    System.out.println("    L'Hidrogen senar " + id + " espera un altre hidrogen");
                    mutex.release();
                    waitSecondHydrogen.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("    L'Hidrogen parell " + id + " allibera un oxigen per fer aigua");
                hydrogensWaiting = 0;
                Oxygen.waitForHydrogens.release();
            }
            mutex.release();
            waitSecondHydrogen.release();
        }

        System.out.println("    L'Hidrogen " + id + " acaba");
    }
}
