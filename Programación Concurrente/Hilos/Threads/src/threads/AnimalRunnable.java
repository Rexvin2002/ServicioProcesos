package threads;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.text.DecimalFormat;

class AnimalRunnable implements Runnable {

    private final String name;
    private int timesEaten;
    private final int eatingTime;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static long globalStartTime = System.currentTimeMillis();  // Se mantiene el tiempo global
    private long totalTime;

    public AnimalRunnable(String name, int eatingTime) {
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
        // Crear los objetos Runnable
        AnimalRunnable gato = new AnimalRunnable("Gato", 1);
        AnimalRunnable perro = new AnimalRunnable("Perro", 2);
        AnimalRunnable rino = new AnimalRunnable("Rino", 4);
        AnimalRunnable loro = new AnimalRunnable("Loro", 2);

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

        // Crear hilos y pasarles los objetos Runnable
        Thread hiloGato = new Thread(gato);
        Thread hiloPerro = new Thread(perro);
        Thread hiloRino = new Thread(rino);
        Thread hiloLoro = new Thread(loro);

        // Iniciar los hilos
        hiloGato.start();
        hiloPerro.start();
        hiloRino.start();
        hiloLoro.start();

        // Esperar que todos los hilos terminen
        hiloGato.join();
        hiloPerro.join();
        hiloRino.join();
        hiloLoro.join();

        // Calcular el tiempo total transcurrido global (al final de todos los hilos)
        long totalGlobalTime = System.currentTimeMillis() - globalStartTime;
        System.out.println("\nTiempo total transcurrido en START: " + df.format(totalGlobalTime / 1000.0) + " s");
    }
}
