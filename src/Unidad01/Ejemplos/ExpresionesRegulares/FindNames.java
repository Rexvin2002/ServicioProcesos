
package Unidad01.Ejemplos.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class FindNames {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Expresión regular para verificar nombres que empiezan con una letra mayúscula
        // y que contienen solo letras minúsculas (y vocales acentuadas).
        String regex = "\\b[A-Z][a-záéíóú]*\\b";

        // Cadena de texto con los nombres
        String nombres = "Manuel, Samuel, Martín";

        // Crear el patrón y el matcher
        Pattern patron = Pattern.compile(regex);
        Matcher matcher = patron.matcher(nombres);

        // Verificar cada coincidencia en la cadena
        while (matcher.find()) {
            System.out.println("El texto " + matcher.group() + " cumple con el patrón.");
        }
        
    }
    
}
