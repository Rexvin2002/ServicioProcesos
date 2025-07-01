
package programamultiplataforma;

import java.io.File;
import java.util.HashMap;
// import java.util.Scanner;

public class FileRenamer2 {

    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);

        // Solicitar ruta del directorio
        System.out.println("Enter the directory path:");
        String directoryPath = "C:\\Users\\kgv17\\Desktop\\CarpetaEjemplo";
        // String directoryPath = scanner.nextLine();

        // Solicitar expresión regular
        System.out.println("Enter the regex pattern to replace:");
        String regex = ".*\\.txt";
        // String regex = scanner.nextLine();

        // Solicitar nuevo texto
        System.out.println("Enter the replacement text:");
        String replacement = "hola";
        // String replacement = scanner.nextLine();

        // Verificar el directorio
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path!");
            return;
        }

        // Crear un mapa para controlar los índices de nombres duplicados
        HashMap<String, Integer> nameCounter = new HashMap<>();

        // Reemplazar nombres de archivos recursivamente
        int renamedCount = replaceFileNames(directory, regex, replacement, nameCounter);
        System.out.println("Files renamed: " + renamedCount);
    }

    /**
     * Reemplaza nombres de archivos recursivamente en un directorio.
     *
     * @param dir          El directorio base.
     * @param regex        La expresión regular para buscar.
     * @param replacement  El texto de reemplazo.
     * @param nameCounter  Mapa para gestionar índices de nombres duplicados.
     * @return La cantidad de archivos renombrados.
     */
    private static int replaceFileNames(File dir, String regex, String replacement, HashMap<String, Integer> nameCounter) {
        int renamedCount = 0;

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // Procesar subdirectorios recursivamente
                renamedCount += replaceFileNames(file, regex, replacement, nameCounter);
            } else {
                // Renombrar archivo
                String originalName = file.getName();
                String newName = originalName.replaceAll(regex, replacement);

                // Asegurar que no haya nombres duplicados
                newName = resolveDuplicateName(dir, newName, nameCounter);

                if (!originalName.equals(newName)) {
                    File renamedFile = new File(dir, newName);
                    if (file.renameTo(renamedFile)) {
                        renamedCount++;
                        System.out.println("Renamed: " + originalName + " -> " + newName);
                    } else {
                        System.out.println("Failed to rename: " + originalName);
                    }
                }
            }
        }

        return renamedCount;
    }

    /**
     * Resuelve conflictos de nombres añadiendo un índice incremental.
     *
     * @param dir         El directorio donde se está renombrando.
     * @param baseName    El nombre base que podría causar conflicto.
     * @param nameCounter Mapa que gestiona índices de nombres.
     * @return Un nombre único sin conflicto.
     */
    private static String resolveDuplicateName(File dir, String baseName, HashMap<String, Integer> nameCounter) {
        String name = baseName;
        String fileNameWithoutExt = getFileNameWithoutExtension(baseName);
        String fileExtension = getFileExtension(baseName);

        while (new File(dir, name).exists()) {
            int count = nameCounter.getOrDefault(fileNameWithoutExt, 0) + 1;
            nameCounter.put(fileNameWithoutExt, count);
            name = fileNameWithoutExt + count + fileExtension;
        }

        return name;
    }

    /**
     * Obtiene el nombre del archivo sin la extensión.
     *
     * @param fileName El nombre completo del archivo.
     * @return El nombre del archivo sin extensión.
     */
    private static String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? fileName : fileName.substring(0, lastDotIndex);
    }

    /**
     * Obtiene la extensión del archivo.
     *
     * @param fileName El nombre completo del archivo.
     * @return La extensión del archivo, incluyendo el punto.
     */
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex);
    }

}

