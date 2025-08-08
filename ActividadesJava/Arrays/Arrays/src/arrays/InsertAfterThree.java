package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.Arrays;

public class InsertAfterThree {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Crear una tabla de 5 elementos, solo uno de ellos vale 3
        int[] originalArray = {1, 3, 5, 7, 9};

        // Encontrar la posición del número 3
        int index = Arrays.binarySearch(originalArray, 3);

        // Verificar si se encontró el número
        if (index >= 0) {

            // Crear un nuevo array con un tamaño de 6 (1 elemento más)
            int[] newArray = Arrays.copyOf(originalArray, originalArray.length + 1);

            // Desplazar los elementos a la derecha para hacer espacio para el nuevo elemento
            for (int i = newArray.length - 1; i > index + 1; i--) {
                newArray[i] = newArray[i - 1];
            }

            // Insertar el nuevo elemento (por ejemplo, 4) en la posición adecuada
            newArray[index + 1] = 4; // Inserta el 4 tras el 3

            // Mostrar el nuevo array
            System.out.println("Nuevo array: " + Arrays.toString(newArray));

        } else {
            System.err.println("\nEl número 3 no se encontró en el array.");
        }

    }

}
