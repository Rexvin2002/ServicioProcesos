package regexgroups;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleQuoteExtractor {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        String str = "['Cadena1', 'Cadena2', 'Cadena3']";

        // Delimita el texto con comillas dobles
        // . (cualquier carácter) + (una o más ocurrencias del elemento anterior) ? (cuantificador + no codicioso (lazy) )
        Pattern p = Pattern.compile("'(.+?)'");
        Matcher m = p.matcher(str);

        while (m.find()) {
            System.out.println("0 ==> " + m.group(0));
            System.out.println("1 ==> " + m.group(1));
        }

    }

}
