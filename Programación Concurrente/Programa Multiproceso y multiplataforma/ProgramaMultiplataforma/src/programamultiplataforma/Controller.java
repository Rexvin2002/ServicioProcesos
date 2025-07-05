package programamultiplataforma;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Controller {

    // Método para escalar imagen a un tamaño fijo de 200x200
    public static ImageIcon escalarImagen(String ruta) {
        ImageIcon originalIcon = new ImageIcon(ruta);
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // Método para escalar imagen a un tamaño fijo de 70x70
    public static ImageIcon escalarImagenTabla(ImageIcon icono) {
        Image scaledImage = icono.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // Método para escalar y establecer imagen en un JLabel
    public static void escalarEstablecerImagen(JLabel etiqueta, String ruta) {
        try {
            // Cargar la imagen desde el classpath
            InputStream inputStream = Controller.class.getResourceAsStream(ruta);
            if (inputStream == null) {
                throw new IllegalArgumentException("Image not found: " + ruta);
            }
            BufferedImage originalImage = ImageIO.read(inputStream);

            // Escalar la imagen al tamaño del JLabel
            Image scaledImage = originalImage.getScaledInstance(
                    etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_SMOOTH);

            // Establecer la imagen escalada como ícono del JLabel
            etiqueta.setIcon(new ImageIcon(scaledImage));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al cargar o escalar la imagen: " + ruta);
        }
    }

    // Método para escalar y establecer una imagen con padding en un JButton
    public static void escalarEstablecerImagenBoton(JButton boton, String ruta, int padding) {
        try {
            // Cargar la imagen desde el classpath
            InputStream inputStream = Controller.class.getResourceAsStream(ruta);
            if (inputStream == null) {
                throw new IllegalArgumentException("Image not found: " + ruta);
            }
            BufferedImage originalImage = ImageIO.read(inputStream);

            // Calcular las dimensiones del área de la imagen (restando el padding)
            int imageWidth = boton.getWidth() - padding * 2;
            int imageHeight = boton.getHeight() - padding * 2;

            // Escalar la imagen considerando el padding
            Image scaledImage = originalImage.getScaledInstance(
                    Math.max(imageWidth, 1), Math.max(imageHeight, 1), Image.SCALE_SMOOTH);

            // Crear un ImageIcon a partir de la imagen escalada
            ImageIcon icon = new ImageIcon(scaledImage);

            // Establecer la imagen escalada como ícono del botón
            boton.setIcon(icon);

            // Ajustar alineación del contenido
            boton.setHorizontalAlignment(SwingConstants.CENTER);
            boton.setVerticalAlignment(SwingConstants.CENTER);

            // Configurar bordes del botón para simular el padding
            boton.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

            // Quitar texto del botón (opcional)
            boton.setText("");
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al cargar o escalar la imagen: " + ruta);
        }
    }

    // Método para simular clic de botón al presionar Enter
    public static void funcionBoton(KeyEvent evt, JButton boton) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton.doClick();
        }
    }

}
