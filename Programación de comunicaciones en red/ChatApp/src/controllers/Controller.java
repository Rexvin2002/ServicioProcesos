package controllers;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Controller {

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public Controller() {
    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public static ImageIcon escalarImagen(String ruta) {

        ImageIcon originalIcon = new ImageIcon(ruta);
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);

    }

    public static ImageIcon escalarImagenTabla(ImageIcon icono) {

        Image scaledImage = icono.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);

    }

    public void escalarEstablecerImagenFromSrc(JLabel etiqueta, String ruta) {

        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource(ruta));
        Image scaledImage = originalIcon.getImage().getScaledInstance(
                etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_SMOOTH);
        etiqueta.setIcon(new ImageIcon(scaledImage));

    }

    public void escalarEstablecerImagenFromURL(JLabel etiqueta, URL imageUrl) {

        if (imageUrl != null) {

            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_SMOOTH);
            etiqueta.setIcon(new ImageIcon(scaledImage));

        } else {
            System.err.println("La URL de la imagen es nula.");
        }

    }

    public void escalarEstablecerImagenFromString(JLabel etiqueta, String ruta) {

        ImageIcon originalIcon = new ImageIcon(ruta);
        Image scaledImage = originalIcon.getImage().getScaledInstance(
                etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_SMOOTH);
        etiqueta.setIcon(new ImageIcon(scaledImage));

    }

    public static void escalarImagenBoton(JButton boton, String ruta) {

        SwingUtilities.invokeLater(() -> {

            ImageIcon originalIcon = new ImageIcon(ruta);
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    boton.getWidth() + 8, boton.getHeight(), Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(scaledImage));

        });

    }

    public static void funcionBoton(KeyEvent evt, JButton boton) {

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton.doClick();
        }

    }

    public static void applyHandCursorToAllButtons(Container container) {

        // Busca botones recursivamente en todos los componentes y contenedores
        for (Component component : container.getComponents()) {

            if (component instanceof JButton button) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            // Si el componente es un contenedor, búsca recursivamente dentro de él
            if (component instanceof Container container1) {
                applyHandCursorToAllButtons(container1);
            }

        }

    }

    public void renameFolder(String oldFolderPath, String newFolderPath) {

        try {
            Path oldFolder = Paths.get(oldFolderPath);
            Path newFolder = Paths.get(newFolderPath);

            if (Files.exists(oldFolder) && Files.isDirectory(oldFolder)) {

                // Renombrar la carpeta
                Files.move(oldFolder, newFolder, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Carpeta renombrada a: " + newFolder);

            } else {
                System.err.println("La carpeta no existe o no es un directorio.");
            }

        } catch (IOException e) {
            System.err.println("Error al renombrar la carpeta: " + e.getMessage());
        }

    }

    public static void configurarUTF8Encoding() {

        try {

            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setProperty("file.encoding", "UTF-8");

        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: " + e);

        }

    }

}
