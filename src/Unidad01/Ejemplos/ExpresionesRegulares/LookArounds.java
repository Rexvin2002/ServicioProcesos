package Unidad01.Ejemplos.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class LookArounds {

    /**
     * @param args the command line arguments
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
        // Compilamos el patrón de la expresión regular
        Pattern pattern = Pattern.compile(patternString);
        // Creamos un Matcher para encontrar coincidencias en el texto
        Matcher matcher = pattern.matcher(text);
        
        // Buscamos coincidencias
        boolean found = false;
        while (matcher.find()) {
            // Imprimimos la coincidencia encontrada
            System.out.println("Coincidencia encontrada: " + matcher.group());
            found = true;
        }
        
        if (!found) {
            System.out.println("No se encontraron coincidencias para:"+text+" ==> "+patternString);
        }
    }
}
