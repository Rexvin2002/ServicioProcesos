package threads;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.text.DecimalFormat;

class Animal extends Thread {

    private final String name;
    private int timesEaten;
    private final int eatingTime;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static long globalStartTime = System.currentTimeMillis();  // Se mantiene el tiempo global
    private long totalTime;

    public Animal(String name, int eatingTime) {
        this.name = name;
        this.eatingTime = eatingTime;
        this.timesEaten = 0;
    }

    @Override
    public void run() {
        // Tiempo cuando empieza a comer
        long start = System.currentTimeMillis();
        System.out.println(name + " empieza a comer en: " + df.format((start - globalStartTime) / 1000.0) + " s");

        for (int i = 0; i < eatingTime; i++) {
            try {
                Thread.sleep(500);
                System.out.println(name + " dice: come come...");
                timesEaten++;
            } catch (InterruptedException e) {
                System.out.println(name + " fue interrumpido mientras comía.");
            }
        }

        // Tiempo cuando termina de comer
        long end = System.currentTimeMillis();
        totalTime = end - start;
        System.out.println(name + " termina de comer en: " + df.format((end - globalStartTime) / 1000.0) + " s");
        System.out.println("Tiempo transcurrido en run(): " + df.format(totalTime / 1000.0) + " s");
        System.out.println(name + " ha comido " + timesEaten + " veces\n");
    }

    public long mostrarTiempoTotal() {
        return totalTime;
    }

    public static void resetearGlobalStartTime() {
        globalStartTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        Animal gato = new Animal("Gato", 1);
        Animal perro = new Animal("Perro", 2);
        Animal rino = new Animal("Rino", 4);
        Animal loro = new Animal("Loro", 2);

        // Ejecutar con run(), secuencialmente
        System.out.println("\n\nRUN\n\n");
        gato.run();
        perro.run();
        rino.run();
        loro.run();

        // Resetear tiempo global antes de usar start()
        resetearGlobalStartTime();

        // Ejecutar con start(), de forma concurrente
        System.out.println("\n\nSTART\n\n");

        gato.start();
        perro.start();
        rino.start();
        loro.start();

        // Esperar que todos los hilos terminen
        gato.join();
        perro.join();
        rino.join();
        loro.join();

        // Calcular el tiempo total transcurrido global (al final de todos los hilos)
        long totalGlobalTime = System.currentTimeMillis() - globalStartTime;
        System.out.println("\nTiempo total transcurrido en START: " + df.format(totalGlobalTime / 1000.0) + " s");
    }
}
