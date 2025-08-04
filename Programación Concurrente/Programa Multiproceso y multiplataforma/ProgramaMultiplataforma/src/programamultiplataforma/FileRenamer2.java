package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.File;
import java.util.HashMap;
// import java.util.Scanner;

public class FileRenamer2 {

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public FileRenamer2() {
    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
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
                        System.err.println("Failed to rename: " + originalName);
                    }

                }

            }

        }

        return renamedCount;

    }

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

    private static String getFileNameWithoutExtension(String fileName) {

        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? fileName : fileName.substring(0, lastDotIndex);

    }

    private static String getFileExtension(String fileName) {

        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex);

    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Scanner scanner = new Scanner(System.in);
        // Solicitar ruta del directorio
        System.out.println("Enter the directory path:");
        String directoryPath = ProgramaMultiplataforma.getCARPETAEJEMPLO();
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

            System.err.println("Invalid directory path!");
            return;

        }

        // Crear un mapa para controlar los índices de nombres duplicados
        HashMap<String, Integer> nameCounter = new HashMap<>();

        // Reemplazar nombres de archivos recursivamente
        int renamedCount = replaceFileNames(directory, regex, replacement, nameCounter);
        System.out.println("Files renamed: " + renamedCount);

    }

}
