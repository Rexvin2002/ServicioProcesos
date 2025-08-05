package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

public class FileRenamer {

    private static final Scanner SCANNER = Controller.getSCANNER();
    private static final String SEPARATOR = Controller.getSEPARATOR();

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public FileRenamer() {
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
                        System.out.println("Renombrado: " + originalName + " -> " + newName);

                    } else {
                        System.err.println("\nError al renombrar: " + originalName);
                        System.out.println("\n");
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

        try {

            Controller.configurarUTF8Encoding();

        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError: " + e.getMessage());
            System.out.println("\n" + SEPARATOR);
        }

        // Solicitar ruta del directorio con valor por defecto
        System.out.println("\nIntroduzca la ruta del directorio :");
        String directoryPathInput = SCANNER.nextLine().trim();
        String directoryPath = directoryPathInput.isEmpty()
                ? ProgramaMultiplataforma.getCARPETAEJEMPLO()
                : directoryPathInput;

        // Solicitar expresión regular con valor por defecto
        System.out.println("Introduzca el patrón regex a buscar:");
        String regexInput = SCANNER.nextLine().trim();
        String regex = regexInput.isEmpty() ? ".*\\.txt" : regexInput;

        // Solicitar texto de reemplazo con valor por defecto
        System.out.println("Introduzca el texto de reemplazo:");
        String replacementInput = SCANNER.nextLine().trim();
        String replacement = replacementInput.isEmpty() ? "renamed_file.txt" : replacementInput;

        // Verificar el directorio
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("\n¡La ruta del directorio no es válida!");
            System.out.println("\n" + SEPARATOR);
            return;
        }

        // Mostrar configuración que se usará
        System.out.println("Configuración a utilizar:");
        System.out.println("Directorio: " + directoryPath);
        System.out.println("Patrón regex: " + regex);
        System.out.println("Texto de reemplazo: " + replacement + "\n");

        // Crear un mapa para controlar los índices de nombres duplicados
        HashMap<String, Integer> nameCounter = new HashMap<>();

        // Reemplazar nombres de archivos recursivamente
        int renamedCount = replaceFileNames(directory, regex, replacement, nameCounter);
        System.out.println("\nArchivos renombrados: " + renamedCount);
        System.out.println("\n" + SEPARATOR);

    }

}
