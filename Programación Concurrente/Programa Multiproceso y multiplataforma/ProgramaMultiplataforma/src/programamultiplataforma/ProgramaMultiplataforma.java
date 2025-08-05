package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import vistas.PowerRename;

public class ProgramaMultiplataforma {

    private static final Scanner SCANNER = Controller.getSCANNER();
    private static final String CARPETAEJEMPLO = "src\\CarpetaEjemplo\\";
    private static final String SEPARATOR = Controller.getSEPARATOR();

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        try {

            Controller.configurarUTF8Encoding();

        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError: " + e.getMessage());
            System.out.println("\n" + SEPARATOR);
        }

        try (SCANNER) {

            int opcion;

            // Bucle principal para que el programa siga ejecutándose hasta que el usuario elija salir
            do {
                System.out.println("""
                                   
                                   Selecciona una opci\u00f3n:
                                   1. Contador de archivos.
                                   2. Renombrar.
                                   3. Reemplazar.
                                   4. PowerRename. 
                                   5. Concatenador. 
                                   6. Buscador de directorios vacíos.
                                   7. Buscador de archivos por extensión.
                                   8. Salir.""");

                System.out.println("\nIntroduce el número de la opción: ");
                opcion = SCANNER.nextInt();

                switch (opcion) {
                    case 1 -> {

                        System.out.println("\n" + SEPARATOR);
                        System.out.println("Has seleccionado: Contador de archivos.");
                        System.out.println(SEPARATOR);

                        // Ejecutar el script FileCounter
                        try {

                            FileCounter.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar el contador de archivos: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);
                        }

                    }
                    case 2 -> {

                        System.out.println("\n" + SEPARATOR);
                        System.out.println("Has seleccionado: Renombrar.");
                        System.out.println(SEPARATOR);
                        // Ejecutar el script FileRenamer2

                        try {

                            FileRenamer.main(new String[]{CARPETAEJEMPLO, ".*.txt", "renamed_file"});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar el renombrado de archivos: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);
                        }

                    }
                    case 3 -> {

                        System.out.println("\n" + SEPARATOR);
                        System.out.println("Has seleccionado: Reemplazar.");
                        System.out.println(SEPARATOR);

                        // Ejecutar el script FileRenamer
                        try {

                            FileRenamer.main(new String[]{CARPETAEJEMPLO, ".*\\.txt", "renamed_file"});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar el remplazo de archivos: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);
                        }

                    }
                    case 4 -> {
                        System.out.println("\n" + SEPARATOR);
                        System.out.println("Has seleccionado: PowerRename.");
                        System.out.println(SEPARATOR);

                        // Ejecutar el script PowerRename
                        try {

                            PowerRename powerRename = new PowerRename();

                            // Agregar WindowListener para saber cuando se cierra
                            final Thread waitingThread = Thread.currentThread();

                            powerRename.addWindowListener(new WindowAdapter() {

                                @Override
                                public void windowClosed(WindowEvent e) {
                                    waitingThread.interrupt(); // Interrumpir el hilo de espera
                                }

                            });

                            powerRename.setVisible(true);

                            // Esperar hasta que se cierre (sin sleep fijo)
                            try {

                                synchronized (waitingThread) {
                                    waitingThread.wait(); // Espera hasta ser notificado/interrumpido
                                }

                            } catch (InterruptedException e) {
                                System.out.println("\n" + SEPARATOR);
                            }

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar PowerRename: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);
                        }

                    }
                    case 5 -> {

                        System.out.println("\n" + SEPARATOR);
                        System.out.println("Has seleccionado: Concatenador.");
                        System.out.println(SEPARATOR);

                        // Ejecutar el script FileConcatenator
                        try {

                            FileConcatenator.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar concatenador de archivos: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);
                        }

                    }
                    case 6 -> {

                        System.out.println("\n" + SEPARATOR);
                        System.out.println("Has seleccionado: Buscador de directorios vacíos.");
                        System.out.println(SEPARATOR);

                        try {

                            DirectoryCleaner.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar concatenador de archivos: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);
                        }

                    }
                    case 7 -> {

                        System.out.println("\n" + SEPARATOR);
                        System.out.println("Has seleccionado: Buscador de archivos por extensión.");
                        System.out.println(SEPARATOR);

                        try {

                            FindFileByExtension.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar concatenador de archivos: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);
                        }

                    }
                    case 8 -> {

                        try {

                            System.out.println("\n" + SEPARATOR);
                            System.out.println("¡Hasta luego!");
                            System.out.println(SEPARATOR);

                        } catch (Exception e) {

                            System.err.println("\nError: " + e.getMessage());
                            System.out.println("\n" + SEPARATOR);

                        } finally {

                            if (SCANNER != null) {

                                SCANNER.close();
                                System.out.println("Scanner cerrado correctamente");
                                System.out.println("\n" + SEPARATOR);

                            }

                        }

                    }
                    default ->
                        System.err.println("\nOpción no válida. Por favor, selecciona un número entre 1 y 7.");

                }

            } while (opcion != 7);  // El bucle continuará hasta que el usuario elija salir (opción 7)

        }

    }

    /*
     * -----------------------------------------------------------------------
     * GETTERS Y SETTERS
     * -----------------------------------------------------------------------
     */
    public static String getCARPETAEJEMPLO() {
        return CARPETAEJEMPLO;
    }

}
