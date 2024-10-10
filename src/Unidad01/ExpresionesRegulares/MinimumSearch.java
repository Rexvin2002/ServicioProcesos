
package Unidad01.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class MinimumSearch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String str = "['Cadena1', 'Cadena2', 'Cadena3']";
        
        // ? significa busca lo mínimo que puedas.Así que busca algo entre ' '
        Pattern p = Pattern.compile("'(.+?)'");
        Matcher m = p.matcher(str);
        
        while (m.find()) {
            System.out.println("1 ==> "+m.group(1));
            System.out.println("0 ==> "+m.group(0));
        }
    }
    
}
