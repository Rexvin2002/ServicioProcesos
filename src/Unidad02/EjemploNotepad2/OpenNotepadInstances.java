
package Unidad02.EjemploNotepad2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kgv17
 */
public class OpenNotepadInstances {

    public static void main(String[] args) {
        // Define el directorio donde se crearán los archivos .txt
        String directoryPath = "C:/directorio/";
        File directory = new File(directoryPath);

        // Verifica si el directorio existe, y si no, lo crea
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directoryPath);
            } else {
                System.out.println("Failed to create directory: " + directoryPath);
                return;
            }
        }

        // Nombres de los archivos a crear y abrir
        String[] fileNames = {"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt"};

        // Lista para almacenar los procesos
        List<Process> processes = new ArrayList<>();

        // Intenta crear cada archivo y abrirlo en una instancia del Bloc de Notas
        for (String fileName : fileNames) {
            try {
                File file = new File(directory, fileName);

                // Verifica si el archivo existe, si no lo crea
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        System.out.println("Created new file: " + file.getAbsolutePath());
                    } else {
                        System.out.println("Failed to create file: " + file.getAbsolutePath());
                        continue;
                    }
                }

                // Configura el ProcessBuilder para abrir el Bloc de Notas con el archivo
                ProcessBuilder pBuilder = new ProcessBuilder("notepad.exe", file.getAbsolutePath());
                pBuilder.directory(directory); // Establece el directorio de trabajo

                // Inicia el proceso y lo almacena
                processes.add(pBuilder.start());
                System.out.println("Opened Notepad with file: " + file.getAbsolutePath());

            } catch (IOException e) {
                System.out.println("An error occurred while creating or opening file: " + fileName);
                System.out.println("Error: "+e.getMessage());
            }
        }

        // Opcional: Espera a que todos los procesos terminen
        for (Process process : processes) {
            try {
                process.waitFor(); // Espera a que el proceso termine
            } catch (InterruptedException e) {
                System.out.println("Process interrupted.");
                System.out.println("Error: "+e.getMessage());
            }
        }

        System.out.println("All processes finished.");
    }
    
}
