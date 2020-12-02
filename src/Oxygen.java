import java.util.concurrent.Semaphore;

public class Oxygen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private int id;
    public static Semaphore waitForHydrogens = new Semaphore(0);

    public Oxygen(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Aquest és l'Oxigen Ox" + id);
        waitForHydrogens();
        synthesizeWater();
    }

    private void synthesizeWater() {
        System.out.println("WATER");
    }

    private void waitForHydrogens() {
        try {
            System.out.println("Oxygen " + id + " esperant dos hidrogens...");
            waitForHydrogens.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
