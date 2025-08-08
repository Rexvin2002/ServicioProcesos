package findnames;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindNames {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        String regex = "\\b[A-Z][a-záéíóú]*\\b";

        String nombres = "Manuel, Samuel, Martín";

        Pattern patron = Pattern.compile(regex);
        Matcher matcher = patron.matcher(nombres);

        while (matcher.find()) {
            System.out.println("El texto " + matcher.group() + " cumple con el patrón.");
        }

    }

}
