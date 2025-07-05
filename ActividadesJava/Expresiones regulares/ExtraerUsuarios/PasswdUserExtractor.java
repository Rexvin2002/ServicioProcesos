
package Unidad01.Ejemplos;

/**
 *
 * @author kgv17
 */
import java.util.regex.*;
import java.nio.file.*;
import java.io.IOException;

public class PasswdUserExtractor {
    public static void main(String[] args) {
        String regex = "^[^:]+:[^:]*:([1-9][0-9]{2,}|1000):[1-9][0-9]*:([^:]*)?:/home/[^:]+:/[^:]+$";
        Pattern pattern = Pattern.compile(regex);
        
        try {
            // Leer todas las líneas del archivo /etc/passwd
            for (String line : Files.readAllLines(Paths.get("/etc/passwd"))) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    System.out.println("Usuario encontrado: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
    }
}
