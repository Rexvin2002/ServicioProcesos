package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.Arrays;
import javax.swing.JOptionPane;

public class ArrayNumberSearch {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Crear una tabla con tres enteros menores que 11
        int[] table = {3, 7, 10};

        // Ordenar la tabla para poder usar Arrays.binarySearch
        Arrays.sort(table);

        int userNumber = -1; // Inicializar userNumber a un valor que no esté en la tabla

        // Bucle para pedir un número hasta que sea válido
        while (true) {

            // Pedir al usuario que introduzca un número entre 1 y 10
            String input = JOptionPane.showInputDialog("Introduce un número entre 1 y 10:");
            userNumber = Integer.parseInt(input);

            // Comprobar si el número está en la tabla usando binarySearch
            int index = Arrays.binarySearch(table, userNumber);

            // Mostrar el resultado al usuario
            if (index >= 0) {

                JOptionPane.showMessageDialog(null, "El número " + userNumber + " está en la tabla.");
                break; // Salir del bucle si el número está en la tabla

            } else {
                JOptionPane.showMessageDialog(null, "El número " + userNumber + " NO está en la tabla.");
            }

        }

    }

}
