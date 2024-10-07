
package Unidad01.Ejemplos.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class ChangeWord2 {
    public static void main(String[] args) {
        String text = "El lobo soplo y soplo pero la casa de madera no se " +
                      "calló. Entonces pensó, ¿y si en vez de usar mis " +
                      "pulmones uso un palo de madera?. Fue entonces " +
                      "cuando buscó por todo el bosque pero sólo " +
                      "encontraba maderas gordas que no podía " +
                      "transportar. A lo mejor haciendo una carretilla de " +
                      "madera podría transportar algo más grande y usarlo " +
                      "para aporrear con fuerza la casita de madera...";

        // Patrón para buscar "madera"
        Pattern pattern = Pattern.compile("\\bmadera\\b");
        Matcher matcher = pattern.matcher(text);

        // Contador para las apariciones
        int count = 0;
        StringBuffer modifiedText = new StringBuffer();

        // Recorrer todas las apariciones
        while (matcher.find()) {
            count++;
            // Sustituir en la segunda y cuarta aparición
            if (count == 2 || count == 4) {
                matcher.appendReplacement(modifiedText, "metal");
            } else {
                matcher.appendReplacement(modifiedText, matcher.group());
            }
        }
        matcher.appendTail(modifiedText);

        // Imprimir el texto modificado
        System.out.println(modifiedText.toString());
    }
}

