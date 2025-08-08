package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.Arrays;
import javax.swing.JOptionPane;

public class CompareArrays {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Crear dos arrays para almacenar dos conjuntos de 5 números decimales cada uno
        double[] array1 = new double[5];
        double[] array2 = new double[5];

        // Pedir al usuario que introduzca 5 números decimales para el primer array
        for (int i = 0; i < 5; i++) {
            String input = JOptionPane.showInputDialog("Introduce un número decimal para el primer array (" + (i + 1) + "/5):");
            array1[i] = Double.parseDouble(input);
        }

        // Pedir al usuario que introduzca 5 números decimales para el segundo array
        for (int i = 0; i < 5; i++) {
            String input = JOptionPane.showInputDialog("Introduce un número decimal para el segundo array (" + (i + 1) + "/5):");
            array2[i] = Double.parseDouble(input);
        }

        // Buscar el primer índice donde los dos arrays son diferentes
        int mismatchIndex = Arrays.mismatch(array1, array2);

        // Mostrar el resultado
        if (mismatchIndex == -1) {

            JOptionPane.showMessageDialog(null, "Los dos arrays son iguales.");

        } else {
            JOptionPane.showMessageDialog(null, "Los dos arrays son diferentes en el índice: " + mismatchIndex);
        }

    }

}
