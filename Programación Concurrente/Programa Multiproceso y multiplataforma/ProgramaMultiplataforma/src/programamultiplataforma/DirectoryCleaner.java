package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class DirectoryCleaner {

    private static final Scanner SCANNER = Controller.getSCANNER();
    private static final String SEPARATOR = Controller.getSEPARATOR();

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public DirectoryCleaner() {
    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public static void listEmptyDirectories(String rootDirectory) throws IOException {

        Path rootDirPath = Paths.get(rootDirectory).toAbsolutePath();

        if (!Files.isDirectory(rootDirPath)) {
            throw new IllegalArgumentException("La ruta proporcionada no es un directorio válido: " + rootDirectory);
        }

        System.out.println("Iniciando búsqueda de directorios vacíos en:");
        System.out.println(rootDirPath.toString());

        long emptyDirCount = Files.walk(rootDirPath).filter(Files::isDirectory).filter(path -> {

            try {

                return Files.list(path).count() == 0;

            } catch (IOException e) {
                System.err.println("Error al verificar el directorio: " + path + " - " + e.getMessage());
                return false;
            }

        }).peek(path -> System.out.println("• " + path.toString())).count();

        System.out.println("\nBúsqueda completada. Directorios vacíos encontrados: " + emptyDirCount);

    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        try {

            Controller.configurarUTF8Encoding();

            System.out.println("\nIntroduce la ruta del directorio raíz:");
            String directoryPathInput = SCANNER.nextLine().trim();
            String directoryPath = directoryPathInput.isEmpty()
                    ? ProgramaMultiplataforma.getCARPETAEJEMPLO()
                    : directoryPathInput;

            try {

                listEmptyDirectories(directoryPath);

            } catch (IllegalArgumentException e) {
                System.err.println("\nError: " + e.getMessage());
                System.out.println("\n" + SEPARATOR);

            } catch (IOException e) {
                System.err.println("\nError durante la búsqueda: " + e.getMessage());
                System.out.println("\n" + SEPARATOR);
            }

        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError de configuración: " + e.getMessage());
            System.out.println("\n" + SEPARATOR);

        } finally {
            System.out.println("\nProceso finalizado");
            System.out.println("\n" + SEPARATOR);
        }

    }
}
