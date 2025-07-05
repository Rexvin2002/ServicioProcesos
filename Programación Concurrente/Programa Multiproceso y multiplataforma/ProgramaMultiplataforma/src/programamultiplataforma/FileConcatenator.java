package programamultiplataforma;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class FileConcatenator {

    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);

        // Solicitar la ruta del directorio
        System.out.println("Enter the directory path:");
        String directoryPath = "C:\\Users\\kgv17\\Desktop\\CarpetaEjemplo";
        // String directoryPath = scanner.nextLine();

        // Asegurarse de que la ruta de directorio esté bien formada
        if (directoryPath.endsWith(File.separator)) {
            directoryPath = directoryPath.substring(0, directoryPath.length() - 1); // Eliminar el separador final si lo tiene
        }

        // Solicitar la expresión regular para filtrar los archivos
        System.out.println("Enter the regex pattern for file names:");
        String fileRegex = ".*\\.txt$";
        // String fileRegex = scanner.nextLine();

        // Solicitar la expresión regular para filtrar el contenido
        System.out.println("Enter the regex pattern for content:");
        String contentRegex = ".*";
        // String contentRegex = scanner.nextLine();

        // Solicitar el nombre del archivo de salida
        System.out.println("Enter the name of the output file (with .txt extension):");
        String outputFileName = "output.txt";
        // String outputFileName = scanner.nextLine();

        // Validar que el nombre del archivo de salida tenga la extensión .txt
        if (!outputFileName.endsWith(".txt")) {
            System.out.println("The output file must have a .txt extension.");
            return;
        }

        // Crear un archivo de salida con ruta absoluta
        Path outputFilePath = Paths.get(outputFileName).toAbsolutePath();
        try {
            if (Files.exists(outputFilePath)) {
                Files.delete(outputFilePath); // Eliminar si ya existe
            }
            Files.createFile(outputFilePath);
            System.out.println("Created new output file: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error creating the output file: " + e.getMessage());
            return;
        }

        // Ejecutar la concatenación
        try {
            int processedFiles = concatenateFiles(directoryPath, fileRegex, contentRegex, outputFilePath);
            System.out.println("File concatenation completed. Processed " + processedFiles + " files.");
        } catch (IOException e) {
            System.out.println("Error during concatenation: " + e.getMessage());
            return;
        }

        // Verificar el contenido del archivo de salida
        try {
            long lineCount = verifyOutputFile(outputFilePath);
            if (lineCount > 0) {
                System.out.println("The output file was created successfully with " + lineCount + " lines.");
                // Mostrar el contenido del archivo
                System.out.println("\nOutput file contents:");
                Files.lines(outputFilePath).forEach(System.out::println);
            } else {
                System.out.println("Warning: The output file is empty. No matching content found.");
            }
        } catch (IOException e) {
            System.out.println("Error verifying output file: " + e.getMessage());
        }
    }

    private static int concatenateFiles(String directoryPath, String fileRegex, String contentRegex, Path outputFile) throws IOException {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }

        System.out.println("\nSearching in directory: " + dir.getAbsolutePath());
        System.out.println("File pattern: " + fileRegex);
        System.out.println("Content pattern: " + contentRegex);

        Pattern filePattern = Pattern.compile(fileRegex);
        Pattern contentPattern = Pattern.compile(contentRegex);
        
        int[] processedFiles = {0};
        int[] matchingFiles = {0};

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            // Listar todos los archivos en el directorio primero
            System.out.println("\nFiles in directory:");
            Files.walk(dir.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> System.out.println("Found file: " + path.getFileName()));

            // Procesar los archivos que coinciden con el patrón
            Files.walk(dir.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    processedFiles[0]++;
                    String fileName = path.getFileName().toString();
                    System.out.println("\nChecking file: " + fileName);
                    
                    if (filePattern.matcher(fileName).matches()) {
                        System.out.println("File matches pattern: " + fileName);
                        matchingFiles[0]++;
                        
                        try {
                            List<String> lines = Files.readAllLines(path);
                            System.out.println("Reading " + lines.size() + " lines from " + fileName);
                            
                            boolean foundMatch = false;
                            for (String line : lines) {
                                if (contentPattern.matcher(line).find()) {
                                    writer.write(line);
                                    writer.newLine();
                                    System.out.println("Added matching line: " + line);
                                    foundMatch = true;
                                }
                            }
                            
                            if (!foundMatch) {
                                System.out.println("No matching content found in: " + fileName);
                            }
                            
                        } catch (IOException e) {
                            System.out.println("Error processing file " + fileName + ": " + e.getMessage());
                        }
                    } else {
                        System.out.println("File does not match pattern: " + fileName);
                    }
                });
        }
        
        System.out.println("\nSummary:");
        System.out.println("Total files found: " + processedFiles[0]);
        System.out.println("Files matching name pattern: " + matchingFiles[0]);
        
        return processedFiles[0];
    }

    private static long verifyOutputFile(Path outputFile) throws IOException {
        return Files.lines(outputFile).count();
    }

}
