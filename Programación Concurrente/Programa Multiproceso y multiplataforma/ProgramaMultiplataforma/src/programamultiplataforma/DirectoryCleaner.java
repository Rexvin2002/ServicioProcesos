package programamultiplataforma;

import java.io.*;
import java.nio.file.*;
// import java.nio.charset.StandardCharsets;
// import java.util.*;

public class DirectoryCleaner {

    /**
     * Lista todos los directorios vacíos dentro de una ruta de directorio dada.
     * Un directorio vacío se define como un directorio que no contiene
     * archivos ni subdirectorios.
     *
     * @param rootDirectory El directorio raíz para comenzar la búsqueda de directorios vacíos.
     * @throws IOException Si ocurre un error de entrada/salida al acceder a archivos o directorios.
     */
    public static void listEmptyDirectories(String rootDirectory) throws IOException {
        Path rootDirPath = Paths.get(rootDirectory).toAbsolutePath();

        // Verificar si la ruta es válida
        if (!Files.isDirectory(rootDirPath)) {
            throw new IllegalArgumentException("La ruta proporcionada no es un directorio válido: " + rootDirectory);
        }

        // Listar los directorios vacíos
        System.out.println("Buscando directorios vacíos en: " + rootDirPath.toString());

        Files.walk(rootDirPath)
            .filter(Files::isDirectory) // Filtrar solo directorios
            .filter(path -> {
                try {
                    // Comprobar si el directorio está vacío
                    return Files.list(path).count() == 0;
                } catch (IOException e) {
                    System.out.println("Error al verificar el directorio: " + path + " - " + e.getMessage());
                    return false;
                }
            })
            .forEach(path -> System.out.println("Directorio vacío encontrado: " + path.toString()));
    }

    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

        // Solicitar la ruta del directorio
        System.out.println("Introduce la ruta del directorio raíz para buscar directorios vacíos:");
        String directoryPath = "C:\\Users\\kgv17\\Desktop\\CarpetaEjemplo";
        // String directoryPath = scanner.nextLine();

        try {
            listEmptyDirectories(directoryPath);
        } catch (IOException e) {
            System.out.println("Error durante la búsqueda de directorios: " + e.getMessage());
        }
    }
}
