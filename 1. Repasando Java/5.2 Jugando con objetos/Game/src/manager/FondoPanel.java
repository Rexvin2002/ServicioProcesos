
package manager;

import javax.swing.*;
import java.awt.*;

public class FondoPanel extends JPanel{
        
        private final Image fondo;

        public FondoPanel() {
            
            this.fondo = new ImageIcon(GameManager.background).getImage();
        
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            
            super.paintComponent(g);
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this); // Dibuja la imagen escalada
            
        }
        
    }
