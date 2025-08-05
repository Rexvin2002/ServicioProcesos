package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileConcatenator {

    private static final Scanner SCANNER = Controller.getSCANNER();
    private static final String SEPARATOR = Controller.getSEPARATOR();

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        try {

            Controller.configurarUTF8Encoding();

            // 1. Configuración con valores por defecto
            String directoryPath = ProgramaMultiplataforma.getCARPETAEJEMPLO();

            System.out.println("\nIntroduce el patrón para los nombres de archivo:");
            String fileRegexInput = SCANNER.nextLine().trim();
            String fileRegex = fileRegexInput.isEmpty() ? ".*\\d*\\.txt" : fileRegexInput;

            System.out.println("Introduce el patrón para buscar errores:");
            String contentRegexInput = SCANNER.nextLine().trim();
            String contentRegex = contentRegexInput.isEmpty() ? ".*Error.*" : contentRegexInput;

            System.out.println("Introduce el nombre para el archivo de errores:");
            String errorsFileNameInput = SCANNER.nextLine().trim();
            String errorsFileName = errorsFileNameInput.isEmpty() ? "errores_encontrados.txt" : errorsFileNameInput;

            System.out.println("Introduce el nombre para el archivo combinado:");
            String combinedFileNameInput = SCANNER.nextLine().trim();
            String combinedFileName = combinedFileNameInput.isEmpty() ? "combined_output.txt" : combinedFileNameInput;

            // 2. Preparar archivos de salida
            Path errorsFile = Paths.get(directoryPath, errorsFileName);
            Path combinedFile = Paths.get(directoryPath, combinedFileName);

            // Eliminar archivos si ya existen
            if (Files.exists(errorsFile)) {
                Files.delete(errorsFile);
            }
            if (Files.exists(combinedFile)) {
                Files.delete(combinedFile);
            }

            Files.createFile(errorsFile);
            Files.createFile(combinedFile);

            // 3. Procesamiento
            System.out.println("Iniciando búsqueda...");
            System.out.println("\nConfiguración usada:");
            System.out.println("- Patrón archivos: " + fileRegex);
            System.out.println("- Patrón errores: " + contentRegex);
            System.out.println("- Archivo errores: " + errorsFileName);
            System.out.println("- Archivo combinado: " + combinedFileName + "\n");

            try (BufferedWriter errorsWriter = Files.newBufferedWriter(errorsFile); BufferedWriter combinedWriter = Files.newBufferedWriter(combinedFile)) {

                int totalFiles = 0;
                int filesWithErrors = 0;
                int totalErrors = 0;

                for (File file : new File(directoryPath).listFiles()) {

                    if (!file.isFile()) {
                        continue;
                    }

                    String fileName = file.getName();
                    totalFiles++;

                    // Saltar archivos de salida
                    if (fileName.equals(errorsFileName) || fileName.equals(combinedFileName)) {
                        continue;
                    }

                    // Verificar patrón del nombre (solo si se especificó un patrón)
                    if (!fileRegex.isEmpty() && !fileName.matches(fileRegex)) {
                        System.out.printf("Archivo %s no coincide con el patrón%n", fileName);
                        continue;
                    }

                    System.out.println("\nProcesando " + fileName + "...");
                    List<String> lines = Files.readAllLines(file.toPath());
                    boolean hasErrors = false;

                    // Escribir todo el contenido en combined_output
                    combinedWriter.write("=== Contenido de " + fileName + " ===\n");

                    for (String line : lines) {

                        combinedWriter.write(line + "\n");

                        // Buscar errores
                        if (line.matches(contentRegex)) {

                            errorsWriter.write(String.format("[%s] %s%n", fileName, line));
                            totalErrors++;
                            hasErrors = true;
                            System.out.println("! Error encontrado: " + line);

                        }

                    }

                    combinedWriter.write("\n"); // Separador entre archivos

                    if (hasErrors) {
                        filesWithErrors++;
                    }

                }

                // 4. Resultados
                System.out.println("\nRESULTADOS:");
                System.out.println("Total archivos: " + totalFiles);
                System.out.println("Archivos con errores: " + filesWithErrors);
                System.out.println("Total errores encontrados: " + totalErrors);
                System.out.println("Errores guardados en: " + errorsFile.toAbsolutePath());
                System.out.println("Contenido combinado guardado en: " + combinedFile.toAbsolutePath());
                System.out.println("\n" + SEPARATOR);

            }

        } catch (IOException e) {
            System.err.println("\nError: " + e.getMessage());
        }

    }

}
