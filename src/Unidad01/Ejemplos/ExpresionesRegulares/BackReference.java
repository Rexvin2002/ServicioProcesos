
package Unidad01.Ejemplos.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kgv17
 */
public class BackReference {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(a)\\1*(b)as\\1*");
        Matcher matcher = pattern.matcher("aaabasbbb");
        matcher.find();
        System.out.println("\nNºGrupos: "+matcher.groupCount());
        System.out.println("\nGrupo 0: "+matcher.group(0));
        System.out.println("Grupo start: "+matcher.start(0));
        System.out.println("Grupo end: "+matcher.end(0));
        
        System.out.println("\nGrupo 1: "+matcher.group(1));
        System.out.println("Grupo start: "+matcher.start(1));
        System.out.println("Grupo end: "+matcher.end(1));
        
        System.out.println("\nGrupo 2: "+matcher.group(2));
        System.out.println("Grupo start: "+matcher.start(2));
        System.out.println("Grupo end: "+matcher.end(2));
    }
    
}
