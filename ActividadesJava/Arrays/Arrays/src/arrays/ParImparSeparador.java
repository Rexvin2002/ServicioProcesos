package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class ParImparSeparador {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Usamos ArrayList para manejar una cantidad dinámica de números
        ArrayList<Integer> numerosList = new ArrayList<>();
        String input;

        // Pedir al usuario que introduzca enteros hasta que ingrese un número negativo o cancele
        while (true) {

            input = JOptionPane.showInputDialog("Introduce un número entero (negativo para finalizar):");

            // Si el usuario pulsa Cancelar o cierra el diálogo
            if (input == null) {
                System.exit(0); // Cierra el programa
            }

            try {

                int numero = Integer.parseInt(input);

                if (numero < 0) {
                    break;
                }

                numerosList.add(numero);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Debes introducir un número entero válido.");
            }

        }

        // Convertir ArrayList a array primitivo
        int[] numeros = new int[numerosList.size()];

        for (int i = 0; i < numerosList.size(); i++) {
            numeros[i] = numerosList.get(i);
        }

        // Crear arrays para pares e impares
        int[] pares = new int[numeros.length];
        int[] impares = new int[numeros.length];
        int contadorPares = 0;
        int contadorImpares = 0;

        // Separar los números en pares e impares
        for (int num : numeros) {

            if (num % 2 == 0) {

                pares[contadorPares++] = num;

            } else {
                impares[contadorImpares++] = num;
            }

        }

        // Redimensionar los arrays al tamaño correcto
        pares = Arrays.copyOf(pares, contadorPares);
        impares = Arrays.copyOf(impares, contadorImpares);

        // Ordenar los arrays
        Arrays.sort(pares);
        Arrays.sort(impares);

        // Mostrar los resultados
        String mensaje = "Números pares: " + Arrays.toString(pares) + "\n"
                + "Números impares: " + Arrays.toString(impares);
        JOptionPane.showMessageDialog(null, mensaje, "Resultados", JOptionPane.INFORMATION_MESSAGE);

        // También mostrarlos por consola
        System.out.println("Números pares: " + Arrays.toString(pares));
        System.out.println("Números impares: " + Arrays.toString(impares));

    }

}
