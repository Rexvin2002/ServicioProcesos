
package Unidad02.EjemploNotepad;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author kgv17
 */
public class EjemploNotepad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            String[] commands1 = {"Notepad.exe", "C:\\ruta\\completa\\notas.txt"};
            String[] commands2 = {"Notepad.exe", "src\\Unidad02\\EjemploNotepad\\notepad.txt"};
            Process process1 = Runtime.getRuntime().exec(commands1);
            Process process2 = Runtime.getRuntime().exec(commands2);

            int exitCode1 = process1.waitFor();
            int exitCode2 = process2.waitFor();
            
            System.out.println("\nEl código de retorno del proceso 1 es: " + exitCode1);
            System.out.println("\nEl código de retorno del proceso 2 es: " + exitCode2);
            
        } catch (IOException | InterruptedException e) {
            
            System.err.println("\nError: "+e.getMessage());
            
        }

        
        
        
        
        String filePath = "C:\\ruta\\inexistente\\archivo_no_existe.txt";
        
        File file = new File(filePath);
        
        if (!file.exists()) {
            
            System.err.println("\nEl archivo no existe: " + filePath);
            return;
            
        }
        
        try {
            
            String[] commands = {"Notepad.exe", filePath};
            Process process = Runtime.getRuntime().exec(commands);
            int exitCode = process.waitFor();
            System.out.println("Código de retorno del proceso: " + exitCode);
            
        } catch (IOException e) {
            
            System.err.println("\nError al intentar ejecutar el proceso: " + e.getMessage());
            
        } catch (InterruptedException e) {
            
            System.err.println("\nError: "+e.getMessage());
            
        }
        
        
    }
    
}
