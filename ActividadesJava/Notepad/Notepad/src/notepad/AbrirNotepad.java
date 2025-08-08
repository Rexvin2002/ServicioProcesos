package notepad;

import java.io.File;
import java.io.IOException;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
public class AbrirNotepad {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Nombre del archivo a abrir (puedes cambiarlo)
        String nombreArchivo = "notepad.txt";

        // Ruta completa del archivo (ajusta según tu estructura de directorios)
        String rutaArchivo = "src\\notepad\\" + nombreArchivo;

        // Verificar si el archivo existe
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {

            System.err.println("Error: El archivo '" + rutaArchivo + "' no existe.");
            System.out.println("¿Deseas crearlo? (S/N)");
            // Aquí podrías añadir lógica para crear el archivo si el usuario lo desea
            return;

        }

        try {

            // Comando para abrir Notepad con el archivo
            String[] comando = {"notepad.exe", rutaArchivo};

            // Ejecutar el proceso
            Process proceso = Runtime.getRuntime().exec(comando);

            // Esperar a que el proceso termine (cuando el usuario cierre Notepad)
            int codigoSalida = proceso.waitFor();

            // Mostrar resultado
            if (codigoSalida == 0) {

                System.out.println("Notepad se cerró correctamente.");

            } else {
                System.err.println("Notepad se cerró con código de error: " + codigoSalida);
            }

        } catch (IOException e) {
            System.err.println("Error al ejecutar Notepad: " + e.getMessage());

        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
        }

    }

}
