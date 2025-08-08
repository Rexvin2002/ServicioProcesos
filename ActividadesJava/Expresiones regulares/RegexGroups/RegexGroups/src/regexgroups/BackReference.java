package regexgroups;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackReference {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        String texto = "\nEl cielo está está despejado. Voy a correr correr hoy. La vida vida es bella.";
        Pattern pattern = Pattern.compile("\\b(\\w+)\\b\\s+\\1\\b");  // Busca palabras repetidas
        Matcher matcher = pattern.matcher(texto);

        System.out.println("=== TEXTO ANALIZADO ===\n" + texto + "\n");
        System.out.println("=== PALABRAS REPETIDAS ===");

        while (matcher.find()) {
            System.out.println("\nCoincidencia encontrada: '" + matcher.group() + "'");
            System.out.println("Grupo 0 (todo el match): '" + matcher.group(0) + "'");
            System.out.println("Grupo 1 (palabra repetida): '" + matcher.group(1) + "'");
            System.out.println("Posiciones: [" + matcher.start() + "-" + matcher.end() + "]");
        }

    }

}
