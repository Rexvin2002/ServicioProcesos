package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.Arrays;

public class InsertElementInArray {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Crear una tabla de 10 elementos
        int[] originalArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        // Crear una nueva tabla con un tamaño de 11 elementos
        int[] newArray = Arrays.copyOf(originalArray, originalArray.length + 1);

        // Insertar el nuevo elemento en la posición 5
        int newElement = 99; // El nuevo elemento que quieres insertar
        for (int i = newArray.length - 1; i > 5; i--) {
            newArray[i] = newArray[i - 1]; // Desplazar elementos a la derecha
        }
        newArray[5] = newElement; // Insertar el nuevo elemento en la posición 5

        // Mostrar el nuevo array
        System.out.println("Nuevo array: " + Arrays.toString(newArray));

    }

}
