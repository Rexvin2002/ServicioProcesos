package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

public class RandomTable {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Crear un array de 10 elementos
        int[] table = new int[10];
        Random random = new Random();

        // Llenar el array con valores aleatorios entre 10 y 100 usando Arrays.setAll
        Arrays.setAll(table, i -> random.nextInt(91) + 10); // Genera valores entre 10 y 100
        Arrays.sort(table);
        // Sumar los valores del array
        int sum = Arrays.stream(table).sum();

        // Mostrar los valores y la suma en un cuadro de diálogo
        JOptionPane.showMessageDialog(null, "Valores aleatorios en la tabla: " + Arrays.toString(table)
                + "\nSuma de los valores: " + sum);

    }
}
