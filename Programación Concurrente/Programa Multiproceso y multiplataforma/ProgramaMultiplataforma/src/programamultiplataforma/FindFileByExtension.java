package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindFileByExtension {

    private static final Scanner SCANNER = Controller.getSCANNER();
    private static final String SEPARATOR = Controller.getSEPARATOR();

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public FindFileByExtension() {
    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String args[]) {

        try {

            Controller.configurarUTF8Encoding();

        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError: " + e.getMessage());
            System.out.println("\n" + SEPARATOR);
        }

        // Solicitar carpeta con opción por defecto
        System.out.println("\nIntroduce la ruta del directorio:");
        String carpetaInput = SCANNER.nextLine().trim();
        String carpeta = carpetaInput.isEmpty()
                ? ProgramaMultiplataforma.getCARPETAEJEMPLO()
                : carpetaInput;

        // Solicitar extensión con opción por defecto
        System.out.println("Introduce la extensión a buscar:");
        String extensionInput = SCANNER.nextLine().trim();
        String extension = extensionInput.isEmpty() ? ".txt"
                : (extensionInput.startsWith(".") ? extensionInput : "." + extensionInput);

        // Mostrar configuración de búsqueda
        System.out.println("Configuración de búsqueda:");
        System.out.println("• Directorio: " + carpeta);
        System.out.println("• Extensión: " + extension);

        File folder = new File(carpeta);

        // Validar carpeta
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("\nError: La ruta no existe o no es un directorio válido");
            System.out.println(SEPARATOR);
            return;
        }

        // Realizar búsqueda
        System.out.println("\nBuscando archivos " + extension + "...");
        List<File> archivosEncontrados = buscarArchivosPorExtension(folder, extension);

        // Mostrar resultados
        if (archivosEncontrados.isEmpty()) {

            System.out.println("\nNo se encontraron archivos con la extensión " + extension);

        } else {

            System.out.println("\nArchivos encontrados (" + archivosEncontrados.size() + "):");
            archivosEncontrados.forEach(file
                    -> System.out.println("• " + file.getName() + " ("
                            + formatSize(file.length()) + ")"));

            long tamañoTotal = archivosEncontrados.stream()
                    .mapToLong(File::length)
                    .sum();
            System.out.println("\nTamaño total: " + formatSize(tamañoTotal));

        }

        System.out.println("\n" + SEPARATOR);

    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    private static String formatSize(long bytes) {

        if (bytes < 1024) {
            return bytes + " B";
        }

        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);

    }

    private static List<File> buscarArchivosPorExtension(File folder, String extension) {

        List<File> archivos = new ArrayList<>();

        // Validar parámetros
        if (folder == null || !folder.isDirectory() || extension == null) {
            return archivos;
        }

        // Buscar recursivamente
        File[] listaArchivos = folder.listFiles();

        if (listaArchivos != null) {

            for (File file : listaArchivos) {

                if (file.isDirectory()) {

                    archivos.addAll(buscarArchivosPorExtension(file, extension));

                } else if (file.getName().toLowerCase().endsWith(extension.toLowerCase())) {

                    archivos.add(file);

                }

            }

        }

        return archivos;

    }

}
