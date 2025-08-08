package wordchanger;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordChanger2 {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        try {
            // Configuración de la salida a UTF-8
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            String text = """
                          El lobo soplo y soplo pero la casa de madera no se
                          call\u00f3. Entonces pens\u00f3, \u00bfy si en vez de usar mis
                          pulmones uso un palo de madera?. Fue entonces
                          cuando busc\u00f3 por todo el bosque pero s\u00f3lo
                          encontraba maderas gordas que no pod\u00eda
                          transportar. A lo mejor haciendo una carretilla de
                          madera podr\u00eda transportar algo m\u00e1s grande y usarlo
                          para aporrear con fuerza la casita de madera...""";

            Pattern pattern = Pattern.compile("\\bmadera\\b");
            Matcher matcher = pattern.matcher(text);

            int count = 0;
            StringBuffer modifiedText = new StringBuffer();

            while (matcher.find()) {

                count++;

                if (count == 2 || count == 4) {

                    matcher.appendReplacement(modifiedText, "metal");

                } else {

                    matcher.appendReplacement(modifiedText, matcher.group());

                }

            }

            matcher.appendTail(modifiedText);

            System.out.println(modifiedText.toString());

        } catch (UnsupportedEncodingException e) {

            System.err.println("\nERROR: " + e.getMessage());

        }

    }

}
