
package com.mycompany.ubuntu;

import javax.swing.JOptionPane;

/**
 *
 * @author kgv17
 */
public class Ubuntu {

    public static void main(String[] args) {
        
        JOptionPane.showConfirmDialog(null, "Vamo a mostrar argumentos");
        
        System.out.println("INICIO PROGRAMA");
        
        for (int i = 0; i < args.length; i++) {
            System.out.println(i+".-"+args[i]);
        }
        
        System.out.println("FIN DEL PROGRAMA");
        
    }
}
