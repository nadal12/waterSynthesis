public class Hydrogen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4; //Nombre de síntesis que farà cada molècula.
    private static int hydrogensWaiting = 0; //Variable per saber quin hidrogen es parell i quin senar.
    private final int id; //Identificador de l'hidrogen.

    public Hydrogen(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("    Aquest és l'Hidrogen " + id);
        try {
            for (int i = 0; i < SYNTHESIS_NUMBER; i++) {
                waterSynthesis.mutex.acquire();  //Per protegir la variable hydrogensWaiting.
                hydrogensWaiting++;
                if (hydrogensWaiting % 2 == 1) { //Hidrogen senar
                    waterSynthesis.mutex.release();
                    System.out.println("    L'Hidrogen senar " + id + " espera un altre hidrogen");
                    waterSynthesis.waitSecondHydrogen.acquire(); //Espera a que arribi el segon hidrogen.
                } else {
                    waterSynthesis.mutex.release(); //SIMULACÍO 1 -> INTERCALAT
                    System.out.println("    L'Hidrogen parell " + id + " allibera un oxigen per fer aigua");
                    waterSynthesis.waitForHydrogens.release(); //Amolla l'oxigen que estava esperant
                    waterSynthesis.waitSecondHydrogen.acquire(); // S'atura fins que l'oxigen acabi de fer l'aigua.
                    //waterSynthesis.mutex.release(); //SIMULACÍO 2 -> SENSE INTERCALAT
                }

                // Espera perque estiguin preparats per fer una altre síntesi.
                sleep(500);
            }
            System.out.println("    L'Hidrogen " + id + " acaba");
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
