/*
Nom i llintages: Nadal Llabrés Belmar
Enllaç al vídeo:
 */

import java.util.concurrent.Semaphore;

public class waterSynthesis {

    private static final int OXYGEN_MOLECULES = 2; //Quantitat d'oxigens
    private static final int HYDROGEN_MOLECULES = 4; //Quantitat de hidrogens.

    public static Semaphore waitForHydrogens = new Semaphore(0); //Semàfor amb 0 permisos perque l'oxigen es quedi esperant els hidrogens.
    public static Semaphore waitSecondHydrogen = new Semaphore(0); //Semàfor amb 0 permisos perque el primer hidrogen es quedi esperant el segon.
    public static final Semaphore mutex = new Semaphore(1); //Semàfor mutex per a l'exclusió mutua dels hidrogens.

    public static void main(String[] args) {

        //Array de fils d'execució.
        Thread[] molecules = new Thread[OXYGEN_MOLECULES + HYDROGEN_MOLECULES];

        System.out.println("Simulació Sintetitzant Aigua");

        //Crear oxigens.
        for (int i = 0; i < OXYGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Oxygen(i + 1));
            molecules[i].start();
        }

        //Crear hidrogens
        for (int i = OXYGEN_MOLECULES; i < OXYGEN_MOLECULES + HYDROGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Hydrogen(i - OXYGEN_MOLECULES + 1));
            molecules[i].start();
        }

        //Fer el join perque arribin tots abans d'acabar el programa.
        for (Thread molecule : molecules) {
            join(molecule);
        }
    }

    /**
     * Mètode que fa el join d'un fil passat per paràmetre.
     * @param thread Fil que es vol fer el join.
     */
    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
