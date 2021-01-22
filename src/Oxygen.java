/*
Nom i llintages: Nadal Llabrés Belmar
Enllaç al vídeo: https://youtu.be/skMSEgQluX4
 */

public class Oxygen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private static final int MAX_TIME_TO_PRODUCE_WATER = 3500; //Temps màxim que es tardarà a produïr aigua.
    private static final int CHARACTERS_NUMBER = 4; //Nombre de caràcters que s'imprimeixen quan es sintetitza aigua.
    private static final char CHARACTER_OXYGEN_1 = '*';
    private static final char CHARACTER_OXYGEN_2 = '+';
    private final int character;
    private final int id;

    public Oxygen(int id) {
        this.id = id;
        character = (id == 1) ? CHARACTER_OXYGEN_1 : CHARACTER_OXYGEN_2;
    }

    @Override
    public void run() {
        System.out.println("Aquest és l'Oxigen Ox" + id);

        // Bucle de síntesis.
        for (int i = 0; i < SYNTHESIS_NUMBER; i++) {
            waitForHydrogens();
            synthesizeWater();
            releaseHydrogens();
        }

        System.out.println("L'Oxigen 0x" + id + " acaba");
    }

    /**
     * Allibera els hidrogens que s'han utilitzat per a fer la síntesis.
     */
    private void releaseHydrogens() {
        for (int i = 0; i < 2; i++) {
            waterSynthesis.waitSecondHydrogen.release();
        }
    }

    /**
     * Mètode per imprimir caràcters corresponents a la sintetització d'aigua de cada molècula.
     */
    private void printCharacters() {
        for (int i = 0; i < CHARACTERS_NUMBER; i++) {
            System.out.print(Character.toString(character) + " ");
            sleep(2000);
        }
        System.out.println();
    }

    /**
     * Mètode que sintetitza aigua. Crida a la funció d'imprimir caràcters.
     */
    private void synthesizeWater() {
        sleep((long) (Math.random() * MAX_TIME_TO_PRODUCE_WATER));
        System.out.println("-----------> L'Oxigen Ox" + id + " sintetitza aigua");
        printCharacters();
    }

    /**
     * Mètode per esperar a que els hidrogens necessaris arribin.
     */
    private void waitForHydrogens() {
        try {
            waterSynthesis.waitForHydrogens.acquire(); //Espera a que arribin dos hidrogens.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que fa una espera al fil que el crida.
     * @param ms Temps d'espera amb ms.
     */
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
