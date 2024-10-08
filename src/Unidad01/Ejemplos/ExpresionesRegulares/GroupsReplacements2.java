
package Unidad01.Ejemplos.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class GroupsReplacements2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Se le pueden poner nombres a los grupos
        String regex = "\\b(?<areaCode>\\d{3})(?<prefix>\\d{3})(?<postPhoneNumber>\\d{4})\\b";
        
        String replacementText = "(${areaCode}) ${prefix}-${postPhoneNumber}";
        String source = "1234567890 and 9876543210";
        
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        
        String formattedSource = m.replaceAll(replacementText);
        
        System.out.println("Text: "+source);
        System.out.println("Formatted Text: "+formattedSource);
    }
    
}
