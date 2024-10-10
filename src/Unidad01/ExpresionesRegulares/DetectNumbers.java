
package Unidad01.ExpresionesRegulares;

/**
 *
 * @author kgv17
 */
import java.util.regex.*;
import java.util.ArrayList;

public class DetectNumbers {

    public static void main(String[] args) {
        String frase = "En el centro tenemos 10 ordenadores portátiles, también tenemos 5 ordenadores fijos, tenemos 4 móviles pero sólo tenemos 3 cardboards.";
        
        // Patrón para encontrar números en la frase
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(frase);
        
        ArrayList<Integer> numeros = new ArrayList<>();
        
        // Buscar todos los números
        while (matcher.find()) {
            numeros.add(Integer.valueOf(matcher.group()));
        }
        
        // Imprimir los números encontrados
        System.out.println("Números encontrados: " + numeros);
    }
}
