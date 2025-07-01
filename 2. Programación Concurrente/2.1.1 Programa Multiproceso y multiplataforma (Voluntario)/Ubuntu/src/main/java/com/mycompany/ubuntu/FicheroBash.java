
package com.mycompany.ubuntu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author kgv17
 */
public class FicheroBash {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("1. PROCESS BUILDER --> "+Arrays.toString(args));
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(args));
        commands.set(0, "./"+commands.getFirst());
        System.out.println("2. PROCESS BUILDER REDIRECT");
        
        try {
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("ERROR: "+ e.getMessage());
        }
    }
    
}
