package bash;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;

public class ExecuteBashOnWindows {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Ruta al script existente (ajusta esto a tu ubicación real)
        String scriptPath = "src/scripts/script.bash";

        try {

            // --- Verificar si el script existe ---
            File scriptFile = new File(scriptPath);

            if (!scriptFile.exists()) {
                System.err.println("ERROR: El archivo script no existe en: " + scriptPath);
                return;
            }

            // --- Ejecución con Git Bash (recomendado para Windows) ---
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Program Files\\Git\\bin\\bash.exe", // Ruta a bash.exe
                    "-c", // Indicar que viene un comando
                    "bash " + scriptPath // Ejecutar el script
            );

            // --- Configurar el proceso ---
            pb.redirectErrorStream(true);  // Combinar salida y errores
            Process process = pb.start();

            // --- Leer la salida del script ---
            System.out.println("Salida del script:");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            }

            // --- Esperar a que termine y mostrar resultado ---
            int exitCode = process.waitFor();
            System.out.println("\nScript finalizado con código: " + exitCode);

        } catch (IOException | InterruptedException e) {
            System.err.println("\nError al ejecutar el script: " + e.getMessage());
        }

    }

}
