package validateip;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Pattern;

public class ValidateIP {

    public static void main(String[] args) {
        String ip = "192.168.0.1"; // Cambia esto por la IP que quieras validar

        if (esDireccionIPValida(ip)) {
            System.out.println(ip + " es una dirección IP válida.");
        } else {
            System.out.println(ip + " no es una dirección IP válida.");
        }
    }

    public static boolean esDireccionIPValida(String ip) {
        // Expresión regular para validar IPv4
        String patronIP = "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]?[0-9])$";
        return Pattern.matches(patronIP, ip);
    }
}
