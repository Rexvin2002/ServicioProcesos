package programamultiplataforma;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
// import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FileRenamer {

    public static void main(String[] args) {
        // Crear un objeto Scanner para leer desde la consola
        // Scanner scanner = new Scanner(System.in);

        // Preguntar al usuario por el directorio
        System.out.print("Introduce el directorio donde renombrar los archivos: ");
        String folderPath = "C:\\Users\\kgv17\\Desktop\\CarpetaEjemplo";
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

    /**
     * Renombra archivos en un directorio de forma recursiva.
     *
     * @param folder      El directorio base.
     * @param regex       La expresión regular para coincidir con los nombres de archivo.
     * @param replacement El prefijo del nuevo nombre de archivo.
     * @param counter     Contador para mantener nombres únicos.
     * @return true si todos los archivos se renombraron correctamente, false si hubo errores.
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

    /**
     * Genera un nombre único para evitar colisiones.
     *
     * @param folder      El directorio donde se guardará el archivo.
     * @param baseName    El nombre base del archivo.
     * @param counter     Contador para nombres únicos.
     * @param extension   La extensión del archivo.
     * @return Un archivo con un nombre único que no colisiona con los existentes.
     */
    public static File generateUniqueFileName(File folder, String baseName, AtomicInteger counter, String extension) {
        File newFile;
        do {
            String newName = baseName + "_" + counter.getAndIncrement() + extension;
            newFile = new File(folder, newName);
        } while (newFile.exists());
        return newFile;
    }

    /**
     * Obtiene la extensión de un archivo.
     *
     * @param fileName El nombre del archivo.
     * @return La extensión del archivo.
     */
    public static String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        return (lastIndex > 0) ? fileName.substring(lastIndex) : "";
    }

}
