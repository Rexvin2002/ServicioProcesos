package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import vistas.PowerRename;

public class ProgramaMultiplataforma {

    private static final String CARPETAEJEMPLO = "src\\CarpetaEjemplo\\";

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        try {

            Controller.configurarUTF8Encoding();

        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try (Scanner scanner = new Scanner(System.in)) {

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
                                   7. Salir.""");

                System.out.print("\nIntroduce el número de la opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1 -> {

                        System.out.println("\n---------------------------------------------------");
                        System.out.println("Has seleccionado: Contador de archivos.");
                        System.out.println("---------------------------------------------------");

                        // Ejecutar el script FileCounter
                        try {

                            FileCounter.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar el contador de archivos: " + e.getMessage());
                        }

                    }
                    case 2 -> {

                        System.out.println("\n---------------------------------------------------");
                        System.out.println("Has seleccionado: Renombrar.");
                        System.out.println("---------------------------------------------------");
                        // Ejecutar el script FileRenamer

                        try {

                            FileRenamer.main(new String[]{CARPETAEJEMPLO, ".*.txt", "renamed_file"});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar el renombrado de archivos: " + e.getMessage());
                        }

                    }
                    case 3 -> {

                        System.out.println("\n---------------------------------------------------");
                        System.out.println("Has seleccionado: Reemplazar.");
                        System.out.println("---------------------------------------------------");

                        // Ejecutar el script FileRenamer2
                        try {

                            FileRenamer2.main(new String[]{CARPETAEJEMPLO, ".*\\.txt", "renamed_file"});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar el remplazo de archivos: " + e.getMessage());
                        }

                    }
                    case 4 -> {

                        System.out.println("\n---------------------------------------------------");
                        System.out.println("Has seleccionado: PowerRename.");
                        System.out.println("---------------------------------------------------");

                        // Ejecutar el script PowerRename
                        try {

                            PowerRename.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar PowerRename: " + e.getMessage());
                        }

                    }
                    case 5 -> {

                        System.out.println("\n---------------------------------------------------");
                        System.out.println("\nHas seleccionado: Concatenador.");
                        System.out.println("---------------------------------------------------");

                        // Ejecutar el script FileConcatenator
                        try {

                            FileConcatenator.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar concatenador de archivos: " + e.getMessage());
                        }

                    }
                    case 6 -> {

                        System.out.println("\nHas seleccionado: Buscador de directorios vacíos.");

                        try {

                            DirectoryCleaner.main(new String[]{});

                        } catch (Exception e) {
                            System.err.println("\nError al ejecutar concatenador de archivos: " + e.getMessage());
                        }

                    }
                    case 7 -> {
                        System.out.println("\n¡Hasta luego!");
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
