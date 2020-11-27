public class Hydrogen implements Runnable {

    private static final int SYNTHESIS_NUMBER = 4;
    private int id;

    public Hydrogen() {
        this.id = (int) Thread.currentThread().getId();
    }

    @Override
    public void run() {
        System.out.println("    Aquest és l'Hidrogen " + id);
    }
}
