
package Unidad01.ExpresionesRegulares;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class Group0 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            String texto = "Esto es un texto de 123 letras";
            String pattern = "(.*?)(\\d+)(.*)";
            
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(texto);
            
            if (m.find()) {
                System.out.println("Number Groups: "+m.groupCount());
                System.out.println("Full Group: "+m.group(0));
                System.out.println("1º (.*?) "+m.group(1));
                System.out.println("2º (\\d+) "+m.group(2));
                System.out.println("3º (.*) "+m.group(3));
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError: "+e.getMessage());
        }
        
    }

}
