public class Oxygen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;

    @Override
    public void run() {
        for (int i = 0; i < SYNTHESIS_NUMBER; i++) {
            waitForHydrogens();
            synthesizeWater();
        }
    }

    private void synthesizeWater() {

    }

    private void waitForHydrogens() {

    }
}
