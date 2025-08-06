package mostrarargumentos;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import javax.swing.JOptionPane;

public class MostrarArgumentos {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        JOptionPane.showConfirmDialog(null, "Vamo a mostrar argumentos");

        System.out.println("\nINICIO PROGRAMA");

        for (int i = 0; i < args.length; i++) {
            System.out.println(i + ".-" + args[i]);
        }

        System.out.println("\nFIN DEL PROGRAMA\n");

    }

}
