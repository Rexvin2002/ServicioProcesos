package Unidad01.Arrays;

import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author kgv17
 */
public class ParImparSeparador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Tabla para almacenar los números introducidos por el usuario
        int[] numeros = new int[100]; // Un tamaño arbitrario para la tabla
        int contador = 0;
        String input;

        // Pedir al usuario que introduzca enteros hasta que ingrese un número negativo
        while (true) {
            input = JOptionPane.showInputDialog("Introduce un número entero (negativo para finalizar):");

            // Comprobar si la entrada es válida
            try {
                int numero = Integer.parseInt(input);

                // Romper el bucle si el número es negativo
                if (numero < 0) {
                    break;
                }

                // Verificar si el contador no excede el tamaño del array
                if (contador < numeros.length) {
                    // Guardar el número en la tabla
                    numeros[contador] = numero;
                    contador++;
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha alcanzado el límite de números permitidos.");
                }

            } catch (NumberFormatException e) {
                // Manejar la excepción si la entrada no es un número entero
                JOptionPane.showMessageDialog(null, "Por favor, introduce un número entero válido.");
            }
        }

        // Crear tablas para pares e impares
        int[] pares = new int[contador];
        int[] impares = new int[contador];
        int contadorPares = 0;
        int contadorImpares = 0;

        // Separar los números en pares e impares
        for (int i = 0; i < contador; i++) {
            if (numeros[i] % 2 == 0) {
                pares[contadorPares++] = numeros[i];
            } else {
                impares[contadorImpares++] = numeros[i];
            }
        }

        // Copiar los elementos a tablas finales usando copyOf
        pares = Arrays.copyOf(pares, contadorPares);
        impares = Arrays.copyOf(impares, contadorImpares);

        // Ordenar las tablas
        Arrays.sort(pares);
        Arrays.sort(impares);

        // Mostrar los resultados por consola
        System.out.println("Elementos pares: " + Arrays.toString(pares));
        System.out.println("Elementos impares: " + Arrays.toString(impares));
    }
}
