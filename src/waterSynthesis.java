public class waterSynthesis {

    private static final int OXYGEN_MOLECULES = 2;
    private static final int HYDROGEN_MOLECULES = 4;

    public static void main(String[] args) {

        Thread[] molecules = new Thread[OXYGEN_MOLECULES + HYDROGEN_MOLECULES];

        for (int i = 0; i < OXYGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Oxygen());
            molecules[i].start();
        }

        for (int i = OXYGEN_MOLECULES; i < HYDROGEN_MOLECULES; i++) {
            molecules[i] = new Thread(new Hydrogen());
            molecules[i].start();
        }

        for (Thread molecule : molecules) {
            try {
                molecule.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
