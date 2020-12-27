public class waterSynthesis {

    private static final int OXYGEN_MOLECULES = 2;
    private static final int HYDROGEN_MOLECULES = 4;

    public static void main(String[] args) {

        Thread[] molecules = new Thread[OXYGEN_MOLECULES + HYDROGEN_MOLECULES];

        System.out.println("Simulació Sintetitzant Aigua");

        for (int i = 0; i < OXYGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Oxygen(i + 1, HYDROGEN_MOLECULES));
            molecules[i].start();
        }

        for (int i = OXYGEN_MOLECULES; i < OXYGEN_MOLECULES + HYDROGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Hydrogen(i - OXYGEN_MOLECULES + 1));
            molecules[i].start();
        }

        for (Thread molecule : molecules) {
            join(molecule);
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
