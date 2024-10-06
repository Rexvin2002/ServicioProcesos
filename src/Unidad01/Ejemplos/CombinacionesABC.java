
package Unidad01.Ejemplos;

/**
 *
 * @author kgv17
 */
public class CombinacionesABC {
    public static void main(String[] args) {
        char[] letras = {'a', 'b', 'c'};
        int longitud = 3;
        generarCombinaciones(letras, "", longitud);
    }

    public static void generarCombinaciones(char[] letras, String combinacion, int longitud) {
        // Si la longitud de la combinación actual es igual a la longitud deseada, imprimirla
        if (combinacion.length() == longitud) {
            System.out.println(combinacion);
            return;
        }

        // Recorrer cada letra y construir la combinación
        for (char letra : letras) {
            generarCombinaciones(letras, combinacion + letra, longitud);
        }
    }
}
