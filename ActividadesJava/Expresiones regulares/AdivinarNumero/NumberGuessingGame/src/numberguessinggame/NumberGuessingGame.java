package numberguessinggame;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class NumberGuessingGame extends JFrame {

    private final int[] randomNumbers = new int[4];  // Números aleatorios generados
    private final JTextField[] inputFields = new JTextField[4];  // Campos de texto para la entrada del usuario
    private final JLabel resultLabel = new JLabel("<");  // Etiqueta de resultado
    private final JButton okButton = new JButton("OK");  // Botón OK

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Espaciador para centrar verticalmente
        add(Box.createVerticalGlue());  // Espaciador flexible

        // Añadir espacio en la parte superior // Espaciador de 20 píxeles en la parte superior
        // Panel para los campos de números
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(1, 4, 20, 0));  // Usar GridLayout con 1 fila y 4 columnas
        numberPanel.setBackground(Color.BLACK);
        numberPanel.setMaximumSize(new Dimension(200, 70));  // Aumentar el tamaño del panel de números
        numberPanel.setPreferredSize(new Dimension(200, 70));
        numberPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Alinear el panel en el centro

        // Genera los números aleatorios y añade los campos de texto
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            randomNumbers[i] = rand.nextInt(10);  // Números entre 0 y 9
            inputFields[i] = new JTextField(1);  // Configurar el tamaño para un carácter
            ((AbstractDocument) inputFields[i].getDocument()).setDocumentFilter(new NumericDocumentFilter());
            inputFields[i].setHorizontalAlignment(JTextField.CENTER);
            inputFields[i].setFont(new Font("Arial", Font.PLAIN, 45));  // Aumentar el tamaño de la fuente
            inputFields[i].setBackground(Color.decode("#e6b937"));
            inputFields[i].setPreferredSize(new Dimension(25, 55));  // Tamaño del campo de texto
            inputFields[i].setMaximumSize(new Dimension(25, 55));  // Asegurar que el tamaño máximo sea 25x55
            numberPanel.add(inputFields[i]);
        }

        // Añadir el panel con los números
        add(numberPanel);

        // Añadir etiqueta de resultado (mayor, menor o igual)
        resultLabel.setFont(new Font("Verdana", Font.PLAIN, 46));  // Tamaño de la fuente
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Alinear la etiqueta en el centro

        // Establecer márgenes alrededor del resultLabel
        resultLabel.setBorder(new EmptyBorder(5, 10, 2, 10));  // Margen de 10 píxeles arriba y abajo, 20 píxeles a los lados

        add(resultLabel);

        // Botón OK
        okButton.setFont(new Font("Arial", Font.PLAIN, 24));  // Tamaño de la fuente
        okButton.setPreferredSize(new Dimension(200, 45));  // Tamaño del botón
        okButton.setMaximumSize(new Dimension(200, 45));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Alinear el botón en el centro
        okButton.setBackground(Color.DARK_GRAY);
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                checkGuess();  // Verifica la adivinanza cuando se hace clic
            }
        });

        add(okButton);

        // Espaciador para centrar verticalmente
        add(Box.createVerticalGlue());  // Espaciador flexible

        setPreferredSize(new Dimension(202, 255));  // Tamaño de la ventana
        setResizable(true);  // Permitir redimensionamiento
        pack();
        setLocationRelativeTo(null);  // Centrar la ventana
        setVisible(true);
    }

    // Método para verificar la adivinanza
    private void checkGuess() {
        boolean correct = true; // Asumimos que es correcto al inicio

        for (int i = 0; i < 4; i++) {
            int userGuess;
            try {
                userGuess = Integer.parseInt(inputFields[i].getText());  // Toma el valor ingresado por el usuario
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Ingresa un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;  // Salir si hay un error
            }

            // Comparar el número ingresado con el número aleatorio
            if (userGuess < randomNumbers[i]) {
                resultLabel.setText("<");  // El número es menor
                correct = false;
            } else if (userGuess > randomNumbers[i]) {
                resultLabel.setText(">");  // El número es mayor
                correct = false;
            } else {
                resultLabel.setText("=");  // El número es correcto
                correct = true;
            }
        }

        // Si el usuario adivina correctamente todos los números
        if (correct) {
            okButton.setBackground(Color.decode("#97e687"));  // Cambia el fondo del botón OK a verde
        } else {
            okButton.setBackground(Color.DARK_GRAY);  // Restablece el color del botón si no es correcto
        }
    }

    // Filtro para permitir solo números y limitar a 1 carácter
    private class NumericDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.length() > 0 && isNumeric(string) && (fb.getDocument().getLength() + string.length()) <= 1) {
                super.insertString(fb, offset, string.substring(0, 1), attr);  // Solo permite un carácter
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.length() > 0 && isNumeric(text) && (fb.getDocument().getLength() + text.length() - length) <= 1) {
                super.replace(fb, offset, length, text.substring(0, 1), attrs);  // Solo permite un carácter
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);  // Permitir eliminación
        }

        private boolean isNumeric(String str) {
            return str.matches("[0-9]");  // Verifica si el carácter es un número
        }
    }

    public static void main(String[] args) {
        new NumberGuessingGame();
    }
}
