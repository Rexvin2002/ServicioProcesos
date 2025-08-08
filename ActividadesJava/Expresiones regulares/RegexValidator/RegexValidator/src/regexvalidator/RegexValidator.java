package regexvalidator;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Pattern;

public class RegexValidator {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // True si el texto tiene solo las primeras tres letras del abecedario, repetidas tres veces
        Pattern pattern = Pattern.compile("[abc]{3}");
        String[] texts = {
            "abc", // válido
            "bca", // válido
            "cba", // válido
            "aaa", // válido
            "abcd", // no válido
            "abcabc", // no válido
            "def" // no válido
        };

        for (String text : texts) {

            if (pattern.matcher(text).matches()) {

                System.out.println("El texto '" + text + "' cumple con el patrón.");

            } else {
                System.err.println("El texto '" + text + "' no cumple con el patrón.");
            }

        }

    }

}
