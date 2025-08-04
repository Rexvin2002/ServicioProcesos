package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.File;

public class OpcionPersonalizada {

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public OpcionPersonalizada() {
    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public static void ejecutarBusquedaPorExtension() {

        // Scanner scanner = new Scanner(System.in);
        // Solicitar la carpeta donde buscar
        System.out.print("Introduce la ruta de la carpeta donde buscar archivos: ");
        String carpeta = ProgramaMultiplataforma.getCARPETAEJEMPLO();
        // String carpeta = scanner.nextLine();

        // Solicitar la extensión de los archivos a buscar
        System.out.print("Introduce la extensión de los archivos a buscar (ejemplo: .txt): ");
        String extension = ".txt";
        // String extension = scanner.nextLine();

        File folder = new File(carpeta);

        // Verificar si la carpeta existe
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("La carpeta proporcionada no es válida.");
            return;
        }

        // Realizar la búsqueda de archivos
        buscarArchivosPorExtension(folder, extension);

    }

    private static void buscarArchivosPorExtension(File carpeta, String extension) {

        // Listar archivos en la carpeta
        File[] archivos = carpeta.listFiles();

        // Verificar si hay archivos en la carpeta
        if (archivos == null || archivos.length == 0) {
            System.out.println("No se encontraron archivos en esta carpeta.");
            return;
        }

        boolean encontrado = false;

        for (File archivo : archivos) {

            if (archivo.isDirectory()) {

                // Si es un directorio, buscar recursivamente en él
                buscarArchivosPorExtension(archivo, extension);

            } else {

                // Si es un archivo, verificar si tiene la extensión solicitada
                if (archivo.getName().endsWith(extension)) {
                    System.out.println("Archivo encontrado: " + archivo.getAbsolutePath());
                    encontrado = true;
                }

            }

        }

        // Si no se encontró ningún archivo con la extensión especificada
        if (!encontrado) {
            System.out.println("No se encontraron archivos con la extensión " + extension + " en esta carpeta.");
        }

    }

}
