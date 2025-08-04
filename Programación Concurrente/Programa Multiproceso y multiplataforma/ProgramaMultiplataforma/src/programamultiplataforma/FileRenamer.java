package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
// import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FileRenamer {

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public FileRenamer() {
    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Crear un objeto Scanner para leer desde la consola
        // Scanner scanner = new Scanner(System.in);
        // Preguntar al usuario por el directorio
        System.out.print("Introduce el directorio donde renombrar los archivos: ");
        String folderPath = ProgramaMultiplataforma.getCARPETAEJEMPLO();
        // String folderPath = scanner.nextLine();

        // Preguntar al usuario por la expresión regular
        System.out.print("Introduce la expresión regular para los nombres de los archivos: ");
        String regex = ".*\\.txt";
        // String regex = scanner.nextLine();

        // Preguntar al usuario por el reemplazo
        System.out.print("Introduce el reemplazo para los nombres de los archivos: ");
        String replacement = "renamed_file";
        // String replacement = scanner.nextLine();

        try {
            AtomicInteger counter = new AtomicInteger(1); // Contador para nombres únicos
            boolean success = renameFilesRecursively(new File(folderPath), regex, replacement, counter);

            if (success) {
                System.out.println("Archivos renombrados exitosamente.");
            } else {
                System.out.println("Ocurrieron algunos errores al renombrar los archivos.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Cerrar el scanner
        // scanner.close();
    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public static boolean renameFilesRecursively(File folder, String regex, String replacement, AtomicInteger counter) {

        boolean allRenamedSuccessfully = true;

        for (File file : folder.listFiles()) {

            if (file.isDirectory()) {

                // Procesar subdirectorios
                boolean subDirSuccess = renameFilesRecursively(file, regex, replacement, counter);

                if (!subDirSuccess) {
                    allRenamedSuccessfully = false;
                }

            } else if (file.isFile() && file.getName().matches(regex)) {

                // Generar el nuevo nombre y manejar colisiones
                File newFile = generateUniqueFileName(file.getParentFile(), replacement, counter, getFileExtension(file.getName()));

                // Renombrar archivo
                try {

                    Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Renombrado: " + file.getName() + " -> " + newFile.getName());

                } catch (IOException e) {
                    System.err.println("Error renombrando el archivo: " + file.getAbsolutePath());
                    allRenamedSuccessfully = false;
                }

            }

        }

        return allRenamedSuccessfully;

    }

    public static File generateUniqueFileName(File folder, String baseName, AtomicInteger counter, String extension) {

        File newFile;

        do {

            String newName = baseName + "_" + counter.getAndIncrement() + extension;
            newFile = new File(folder, newName);

        } while (newFile.exists());

        return newFile;

    }

    public static String getFileExtension(String fileName) {

        int lastIndex = fileName.lastIndexOf('.');
        return (lastIndex > 0) ? fileName.substring(lastIndex) : "";

    }

}
