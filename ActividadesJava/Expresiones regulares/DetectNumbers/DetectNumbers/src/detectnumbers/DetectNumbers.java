package detectnumbers;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.*;
import java.util.ArrayList;

public class DetectNumbers {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        String frase = "En el centro tenemos 10 ordenadores portátiles, también tenemos 5 ordenadores fijos, tenemos 4 móviles pero sólo tenemos 3 cardboards.";

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(frase);

        ArrayList<Integer> numeros = new ArrayList<>();

        while (matcher.find()) {
            numeros.add(Integer.valueOf(matcher.group()));
        }

        System.out.println("Números encontrados: " + numeros);

    }

}
