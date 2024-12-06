
package Unidad02.BashScript;

import java.io.*;

public class ExecuteBashOnWindows {

    public static void main(String[] args) {
        // Ruta del directorio y el archivo del script
        String directoryPath = "C:/scripts";
        String scriptFileName = "script.bash";
        String scriptFilePath = directoryPath + "/" + scriptFileName;

        try {
            // Crear el directorio si no existe
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Directory created: " + directoryPath);
                } else {
                    System.out.println("Failed to create directory.");
                    return;
                }
            } else {
                System.out.println("Directory already exists: " + directoryPath);
            }

            // Crear y escribir el script.bash
            File scriptFile = new File(scriptFilePath);
            if (!scriptFile.exists()) {
                if (scriptFile.createNewFile()) {
                    System.out.println("Script file created: " + scriptFilePath);
                } else {
                    System.out.println("Failed to create script file.");
                    return;
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptFile))) {
                writer.write("#!/bin/bash\n");
                writer.write("ls -la\n");
                writer.write("echo \"The process has finished.\"\n");
            }
            System.out.println("Script written successfully.");

            // Hacer ejecutable el script (opcional para Linux o WSL)
            new ProcessBuilder("chmod", "+x", scriptFilePath).start().waitFor();

            // Ejecutar el script usando Bash en Windows
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptFilePath);
            processBuilder.redirectErrorStream(true); // Combinar stdout y stderr
            Process process = processBuilder.start();

            // Capturar y mostrar la salida del script
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                System.out.println("Output of the script:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Esperar a que termine el proceso y obtener el código de salida
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
}
