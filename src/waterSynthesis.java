/*
Nom i llintages: Nadal Llabrés Belmar
Enllaç al vídeo:
 */

import java.util.concurrent.Semaphore;

public class waterSynthesis {

    private static final int OXYGEN_MOLECULES = 2; //Quantitat de molècules d'oxigen.
    private static final int HYDROGEN_MOLECULES = 4; //Quantitat de molècules d'hidrogen.

    /* DECLARACIÓ DELS SEMÀFORS
    * waitForHydrogens -> Semàfor que s'utilitza per bloquejar a l'oxigen perque esperi als dos hidrogens.
    *                     S'inicialitza sense permisos perque no ha de entrar cap oxigen fins que no hagin
    *                     arribat els dos hidrogens.
    *
    * waitSecondHydrogen -> Semàfor per bloquejar el primer hidrogen perque esperi al segon i despres per també
    *                       bloquejar el segon mentres espera que es faci la sintetització d'aigua.
    *
    * mutex -> Semàfor comptador per protegir la variable de comptador d'hidrogens, per diferenciar entre senar i
    *          parell. S'inicialitza amb un permís perque ens interessa que entri una única molècula dins la secció
    *          crítica.
    */
    public static Semaphore waitForHydrogens = new Semaphore(0);
    public static Semaphore waitSecondHydrogen = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {

        Thread[] molecules = new Thread[OXYGEN_MOLECULES + HYDROGEN_MOLECULES];

        System.out.println("Simulació Sintetitzant Aigua");

        //Creació de les molècules d'oxigen.
        for (int i = 0; i < OXYGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Oxygen(i + 1));
            molecules[i].start();
        }

        //Petita espera entre creació de molècules.
        sleep(100);

        //Creació de les molècules d'hidrogen.
        for (int i = OXYGEN_MOLECULES; i < OXYGEN_MOLECULES + HYDROGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Hydrogen(i - OXYGEN_MOLECULES + 1));
            molecules[i].start();
        }

        //Esperar a que tots els fils arribin a aquest punt per continuar.
        for (Thread molecule : molecules) {
            join(molecule);
        }
    }

    /**
     * Mètode que realitza el join del fil que es passa per paràmetre.
     * @param thread Fil que es vol fer el join.
     */
    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que fa una espera al fil que el crida.
     * @param ms Temps d'espera amb ms.
     */
    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
