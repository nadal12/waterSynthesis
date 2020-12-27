import java.util.concurrent.Semaphore;

public class Oxygen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private static final int MAX_TIME_TO_PRODUCE_WATER = 5000;
    private static final int CHARACTERS_NUMBER = 4;
    private static final char CHARACTER_OXYGEN_1 = '*';
    private static final char CHARACTER_OXYGEN_2 = '+';
    private final int character;
    private final int id;
    private final int hydrogenMolecules;
    public static Semaphore waitForHydrogens = new Semaphore(0);

    public Oxygen(int id, int hydrogenMolecules) {
        this.id = id;
        this.hydrogenMolecules = hydrogenMolecules;
        character = (id == 1) ? CHARACTER_OXYGEN_1 : CHARACTER_OXYGEN_2;
    }

    @Override
    public void run() {
        System.out.println("Aquest és l'Oxigen Ox" + id);

        for (int i = 0; i < SYNTHESIS_NUMBER; i++) {
            waitForHydrogens();
            synthesizeWater();
            releaseHydrogens();
        }

        System.out.println("L'Oxigen 0x" + id + " acaba");
    }

    private void releaseHydrogens() {
        for (int i = 0; i < hydrogenMolecules; i++) {
            Hydrogen.waitSecondHydrogen.release();
        }
    }

    private void printCharacters() {
        for (int i = 0; i < CHARACTERS_NUMBER; i++) {
            System.out.print(Character.toString(character) + " ");
        }
        System.out.println();
    }

    private void synthesizeWater() {
        sleep((long) (Math.random() * MAX_TIME_TO_PRODUCE_WATER));
        System.out.println("-----------> L'Oxigen Ox" + id + " sintetitza aigua");
        printCharacters();
    }

    private void waitForHydrogens() {
        try {
            waitForHydrogens.acquire();
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
