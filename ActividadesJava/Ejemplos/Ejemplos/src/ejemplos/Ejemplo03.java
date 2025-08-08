package ejemplos;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ejemplo03 {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        System.out.println("\nLOOKAHEAD ----------------------");
        String text1 = "Había unos _123 Euros_ en la mesita";
        String pLookAheadAfterMatch = "\\d+(?= Euros)";
        System.out.println("TEXTO 1: ");
        matchersMine(text1, pLookAheadAfterMatch);

        System.out.println("\nNEGATIVE LOOKAHEAD ----------------------");
        String text2 = "Había unos 200 6 _123 Libras_ en la mesita";
        String pNegativeLookAheadAfterMatch = "\\d+(?! Libras)";
        System.out.println("TEXTO 1: ");
        matchersMine(text1, pNegativeLookAheadAfterMatch);
        System.out.println("\nTEXTO 2: ");
        matchersMine(text2, pNegativeLookAheadAfterMatch);

        System.out.println("\nLOOKBEHIND ----------------------");
        String pLookBehindAfterMatch = "(?<=unos _)\\d{3}";
        System.out.println("TEXTO 1: ");
        matchersMine(text1, pLookBehindAfterMatch);
        System.out.println("\nTEXTO 2: ");
        matchersMine(text2, pLookBehindAfterMatch);

        System.out.println("\nNEGATIVE LOOKBEHIND ----------------------");
        String pNegativeLookBehindAfterMatch = "(?<!unos _)\\d{3}";
        System.out.println("TEXTO 1: ");
        matchersMine(text1, pNegativeLookBehindAfterMatch);
        System.out.println("\nTEXTO 2: ");
        matchersMine(text2, pNegativeLookBehindAfterMatch);

    }

    private static void matchersMine(String text, String patternString) {

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        boolean found = false;

        while (matcher.find()) {

            System.out.println("Coincidencia encontrada: " + matcher.group());
            found = true;

        }

        if (!found) {
            System.err.println("No se encontraron coincidencias para:" + text + " ==> " + patternString);
        }

    }

}
