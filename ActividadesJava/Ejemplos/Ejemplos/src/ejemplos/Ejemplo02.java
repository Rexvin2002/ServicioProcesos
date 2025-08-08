package ejemplos;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ejemplo02 {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        Pattern pattern = Pattern.compile(".*\\bkevin\\b.*");

        String[] texts = {
            "Práctica Kevin Gomez",
            "Práctica kevin Gomez",
            "Práctica KEVIN Gomez"

        };

        for (String text : texts) {

            Matcher matcher = pattern.matcher(text);

            if (matcher.matches()) {

                System.out.println("En el texto estaba Kevin escrito: " + matcher.matches());

            } else {
                System.err.println("En el texto no estaba escrito: " + matcher.matches());
            }

        }

    }

}
