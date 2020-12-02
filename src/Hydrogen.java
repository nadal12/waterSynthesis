import java.util.concurrent.Semaphore;

public class Hydrogen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private static final int MAX_TIME_TO_ARRIVE = 3000;
    private static int hydrogensWaiting = 0;
    private final int id;
    private static Semaphore waitSecondHydrogen = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);

    public Hydrogen(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("    Aquest és l'Hidrogen " + id);

        for (int i = 0; i < SYNTHESIS_NUMBER; i++) {
            sleep((long) (Math.random() * MAX_TIME_TO_ARRIVE));
            acquire(mutex);
            if (hydrogensWaiting < 1) {
                hydrogensWaiting++;
                System.out.println("    L'Hidrogen senar " + id + " espera un altre hidrogen");
                mutex.release();
                acquire(waitSecondHydrogen);
            } else {
                System.out.println("    L'Hidrogen parell " + id + " allibera un oxigen per fer aigua");
                hydrogensWaiting = 0;
                waitSecondHydrogen.release();
                Oxygen.waitForHydrogens.release();
            }
            mutex.release();
        }

        System.out.println("    L'Hidrogen " + id + " acaba");
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void acquire(Semaphore semaphore) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
