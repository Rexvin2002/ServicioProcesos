
package Unidad01.Ejemplos.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class groupsReplacement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String phoneNumber = "601479874"; // Número de teléfono original

        // Patrón para validar el número de teléfono
        Pattern pattern = Pattern.compile("^(\\d{9})$");
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            // Formatear el número de teléfono
            String formattedNumber = formatPhoneNumber(phoneNumber);
            System.out.println(formattedNumber);
        } else {
            System.err.println("Error: El número de teléfono no es válido.");
        }
    }

    // Método para formatear el número de teléfono
    private static String formatPhoneNumber(String phoneNumber) {
        return String.format("(+34) %s", phoneNumber);
    }
}
