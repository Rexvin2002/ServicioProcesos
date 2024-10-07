
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
        String regex = "\\b(\\d{2})(\\d{4})(\\d{4})\\b";
    
        String replacementText = "($1) $2$3";
        String source = "1234567890, 12345, and 9876543210";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);

        String formattedSource = m.replaceAll(replacementText);

        System.out.println("Text: "+source);
        System.out.println("Formatted Text: " + formattedSource);

    }
    
}
