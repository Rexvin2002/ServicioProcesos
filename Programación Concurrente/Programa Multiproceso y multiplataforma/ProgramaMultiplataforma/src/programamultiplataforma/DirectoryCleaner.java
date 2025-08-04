package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;
import java.nio.file.*;
// import java.nio.charset.StandardCharsets;
// import java.util.*;

public class DirectoryCleaner {

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

        // Verificar si la ruta es válida
        if (!Files.isDirectory(rootDirPath)) {
            throw new IllegalArgumentException("La ruta proporcionada no es un directorio válido: " + rootDirectory);
        }

        // Listar los directorios vacíos
        System.out.println("Buscando directorios vacíos en: " + rootDirPath.toString());

        Files.walk(rootDirPath).filter(Files::isDirectory).filter(path -> { // Filtrar solo directorios

            try {

                // Comprobar si el directorio está vacío
                return Files.list(path).count() == 0;

            } catch (IOException e) {
                System.err.println("Error al verificar el directorio: " + path + " - " + e.getMessage());
                return false;
            }

        }).forEach(path -> System.out.println("Directorio vacío encontrado: " + path.toString()));
    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        // Solicitar la ruta del directorio
        System.out.println("Introduce la ruta del directorio raíz para buscar directorios vacíos:");
        String directoryPath = ProgramaMultiplataforma.getCARPETAEJEMPLO();
        // String directoryPath = scanner.nextLine();

        try {

            listEmptyDirectories(directoryPath);

        } catch (IOException e) {
            System.err.println("Error durante la búsqueda de directorios: " + e.getMessage());
        }

    }

}
