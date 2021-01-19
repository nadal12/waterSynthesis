import java.util.concurrent.Semaphore;

public class Hydrogen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private static int hydrogensWaiting = 0;
    private final int id;

    public Hydrogen(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("    Aquest és l'Hidrogen " + id);

        for (int i = 0; i < SYNTHESIS_NUMBER; i++) {
            acquire(waterSynthesis.mutex);  //Per protegir la variable hydrogensWaiting.
            if (hydrogensWaiting < 1) {
                hydrogensWaiting++;
                System.out.println("    L'Hidrogen senar " + id + " espera un altre hidrogen");
            } else {
                System.out.println("    L'Hidrogen parell " + id + " allibera un oxigen per fer aigua");
                hydrogensWaiting = 0;
                waterSynthesis.waitForHydrogens.release(); //Amolla oxigen que estava esperant
            }
            waterSynthesis.mutex.release();
            acquire(waterSynthesis.waitSecondHydrogen);
            sleep(500);
        }

        System.out.println("    L'Hidrogen " + id + " acaba");
    }

    private void acquire(Semaphore semaphore) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
