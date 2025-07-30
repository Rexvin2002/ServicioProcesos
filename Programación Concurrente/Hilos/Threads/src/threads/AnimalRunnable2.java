package threads;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AnimalRunnable2 implements Runnable {

    private final String nombre;
    private final int comidasObjetivo;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final AtomicInteger comidasRealizadas;
    private final CountDownLatch latch;
    private volatile boolean estaInterrumpido = false;

    public AnimalRunnable2(String nombre, int comidasObjetivo, CountDownLatch latch) {
        this.nombre = nombre;
        this.comidasObjetivo = comidasObjetivo;
        this.comidasRealizadas = new AtomicInteger(0);
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < comidasObjetivo && !estaInterrumpido; i++) {
                Thread.sleep(500);
                comidasRealizadas.incrementAndGet();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(nombre + " fue interrumpido mientras comía.");
        } finally {
            latch.countDown();
        }
    }

    // Interrumpir hilo
    public void interrumpir() {
        estaInterrumpido = true;
    }

    // Obtener total comidas realizadas por animal
    public int getComidasRealizadas() {
        return comidasRealizadas.get();
    }

    // Total comidas realizadas por todos los animales
    private static int calcularTotalComidas(AnimalRunnable2[] animales) {
        int totalComidas = 0;
        for (AnimalRunnable2 animal : animales) {
            totalComidas += animal.getComidasRealizadas();
        }
        return totalComidas;
    }

    // Creacion de ventana
    private static JFrame crearVentana() {
        JFrame ventana = new JFrame("Análisis Comparativo de Rendimiento de Hilos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(900, 400);
        ventana.setLocationRelativeTo(null);
        return ventana;
    }

    // Creacion de tabla
    private static DefaultTableModel crearModeloTabla() {
        String[] nombreColumnas = {
            "Número de Hilos",
            "Comidas Esperadas",
            "Comidas Reales",
            "Error Promedio %",
            "Error Mínimo %",
            "Error Máximo %"
        };
        return new DefaultTableModel(nombreColumnas, 0);
    }

    // Configuración de tabla
    private static void configurarTabla(JFrame ventana, JTable tabla) {
        tabla.setFillsViewportHeight(true);
        JScrollPane panelDesplazable = new JScrollPane(tabla);
        ventana.add(panelDesplazable);
        ventana.setVisible(true);
    }

    public static class ResultadosPrueba {

        private final int numeroHilos;
        private final int comidasEsperadas;
        private final int comidasReales;
        private final double errorPromedio;
        private final double errorMinimo;
        private final double errorMaximo;

        public ResultadosPrueba(int numeroHilos, int comidasEsperadas, int comidasReales,
                double errorPromedio, double errorMinimo, double errorMaximo) {
            this.numeroHilos = numeroHilos;
            this.comidasEsperadas = comidasEsperadas;
            this.comidasReales = comidasReales;
            this.errorPromedio = errorPromedio;
            this.errorMinimo = errorMinimo;
            this.errorMaximo = errorMaximo;
        }

        public Object[] aFilaTabla() {
            return new Object[]{
                numeroHilos,
                comidasEsperadas,
                comidasReales,
                String.format("%.2f", errorPromedio),
                String.format("%.2f", errorMinimo),
                String.format("%.2f", errorMaximo)
            };
        }
    }

    private static void ejecutarPruebas(DefaultTableModel modelo) {
        int[] cantidadHilos = {2, 10, 100, 1000};
        int ITERACIONES = 5;
        int COMIDAS_POR_ANIMAL = 10;

        for (int numHilos : cantidadHilos) {
            ResultadosPrueba resultados = ejecutarCasoPrueba(numHilos, ITERACIONES, COMIDAS_POR_ANIMAL);
            SwingUtilities.invokeLater(() -> modelo.addRow(resultados.aFilaTabla()));
        }
    }

    private static ResultadosPrueba ejecutarCasoPrueba(int numHilos, int iteraciones, int comidasPorAnimal) {
        double errorTotal = 0;
        double errorMinimo = Double.MAX_VALUE;
        double errorMaximo = Double.MIN_VALUE;
        int totalComidasReales = 0;

        for (int i = 0; i < iteraciones; i++) {
            int comidasReales = ejecutarIteracion(numHilos, comidasPorAnimal);
            int comidasEsperadas = numHilos * comidasPorAnimal;
            totalComidasReales += comidasReales;

            double error = Math.abs((comidasEsperadas - comidasReales) / (double) comidasEsperadas) * 100;
            errorTotal += error;
            errorMinimo = Math.min(errorMinimo, error);
            errorMaximo = Math.max(errorMaximo, error);
        }

        int promedioComidasReales = totalComidasReales / iteraciones;
        double errorPromedio = errorTotal / iteraciones;

        return new ResultadosPrueba(
                numHilos,
                numHilos * comidasPorAnimal,
                promedioComidasReales,
                errorPromedio,
                errorMinimo,
                errorMaximo
        );
    }

    private static int ejecutarIteracion(int numHilos, int comidasPorAnimal) {
        CountDownLatch latch = new CountDownLatch(numHilos);
        ExecutorService ejecutor = Executors.newFixedThreadPool(numHilos);
        AnimalRunnable2[] animales = new AnimalRunnable2[numHilos];

        // Crear y comenzar hilos
        for (int i = 0; i < numHilos; i++) {
            animales[i] = new AnimalRunnable2("Animal-" + i, comidasPorAnimal, latch);
            ejecutor.execute(animales[i]);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ejecutor.shutdown();

        // Calcular total de comidas realizadas
        return calcularTotalComidas(animales);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = crearVentana();
            DefaultTableModel modelo = crearModeloTabla();
            JTable tabla = new JTable(modelo);
            configurarTabla(ventana, tabla);

            // Ejecutar pruebas en segundo plano
            Thread hiloTest = new Thread(() -> ejecutarPruebas(modelo));
            hiloTest.start();
        });
    }

}
