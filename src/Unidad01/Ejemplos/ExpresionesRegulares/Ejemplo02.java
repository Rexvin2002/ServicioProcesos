
package Unidad01.Ejemplos.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class Ejemplo02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // True si alguna de las partes de la frase tiene la palabra kevin sin distinguir mayúsculas y minúsculas
        Pattern pattern = Pattern.compile(".*\\bkevin\\b.*");
        String[] texts = {
            "Práctica Kevin Gomez",
            "Práctica kevin Gomez",
            "Práctica KEVIN Gomez"
        };

        for (String text : texts) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                System.out.println("En el texto estaba Kevin escrito: "+matcher.matches());
            } else {
                System.out.println("En el texto no estaba escrito: "+matcher.matches());
            }
        }
    }
    
}
