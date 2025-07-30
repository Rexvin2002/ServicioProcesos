package ui;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import manager.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import manager.FondoPanel;

public class VictoryDialog extends javax.swing.JDialog {

    private final JFrameFight frameFight;

    public VictoryDialog(JFrameFight parent, boolean modal) {

        super(parent, modal);
        this.frameFight = parent;
        initComponents();
        setLocationRelativeTo(null);

        // ESTABLECER FUENTE AL BOTÓN Y EL TITULO
        try {

            Font pixelFont1 = Font.createFont(Font.TRUETYPE_FONT, new File(GameManager.font));

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont1);

            this.jLabelImagen.setFont(pixelFont1.deriveFont(45f));
            this.jButtonExit.setFont(pixelFont1.deriveFont(25f));
            this.jButtonRestart.setFont(pixelFont1.deriveFont(25f));

        } catch (FontFormatException | IOException ex) {

            // EN CASO DE ERROR ESTABLECE ARIAL
            this.jLabelImagen.setFont(new Font("Arial", Font.PLAIN, 50));

        }

        // ESTABLECER ICONO AL FRAME
        try {

            Image imageIcon = ImageIO.read(new File(GameManager.icon));
            setIconImage(imageIcon);

        } catch (IOException e) {

            System.err.println("Error setting icon: " + e.getMessage());

        }

        // ESTABLECER ICONO AL FRAME
        // CARGAR Y ESTABLECER EL GIF EN jLabelImagen
        try {

            // Cargar el GIF en lugar de una imagen estática para jLabelImagen
            ImageIcon gifIcon = new ImageIcon(GameManager.victory);
            this.jLabelImagen.setIcon(gifIcon);

        } catch (Exception e) {

            System.err.println("Error loading GIF for jLabelImagen: " + e.getMessage());

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new FondoPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelImagen = new javax.swing.JLabel();
        jButtonRestart = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Game Over");
        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(450, 450));
        setUndecorated(true);
        setPreferredSize(GameManager.pantalla);

        jPanelMain.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMain.setMinimumSize(new java.awt.Dimension(450, 450));
        jPanelMain.setPreferredSize(GameManager.pantalla);

        jPanel1.setBackground(new Color(51,51,51, 150));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelImagen.setForeground(new java.awt.Color(255, 0, 0));
        jLabelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/victory.gif"))); // NOI18N
        jLabelImagen.setMaximumSize(new java.awt.Dimension(350, 290));
        jLabelImagen.setMinimumSize(new java.awt.Dimension(350, 290));
        jLabelImagen.setPreferredSize(new java.awt.Dimension(350, 290));

        jButtonRestart.setBackground(new Color(32,28,55,50));
        jButtonRestart.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRestart.setText("RESTART");
        jButtonRestart.setMaximumSize(new java.awt.Dimension(200, 40));
        jButtonRestart.setMinimumSize(new java.awt.Dimension(200, 40));
        jButtonRestart.setPreferredSize(new java.awt.Dimension(200, 40));
        jButtonRestart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonRestartMouseEntered(evt);
            }
        });
        jButtonRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRestartActionPerformed(evt);
            }
        });
        jButtonRestart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonRestartKeyPressed(evt);
            }
        });

        jButtonExit.setBackground(new Color(32,28,55,50));
        jButtonExit.setForeground(new java.awt.Color(255, 255, 255));
        jButtonExit.setText("EXIT");
        jButtonExit.setMaximumSize(new java.awt.Dimension(200, 40));
        jButtonExit.setMinimumSize(new java.awt.Dimension(200, 40));
        jButtonExit.setPreferredSize(new java.awt.Dimension(200, 40));
        jButtonExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonExitMouseEntered(evt);
            }
        });
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        jButtonExit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonExitKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(157, Short.MAX_VALUE)
                .addComponent(jLabelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 151, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonRestart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRestart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                .addContainerGap(132, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1248, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRestartActionPerformed

        GameManager.stopBackgroundMusic();
        this.frameFight.setVisible(true);
        this.frameFight.resetGame();
        this.frameFight.startGame();
        dispose();

    }//GEN-LAST:event_jButtonRestartActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed

        GameManager.stopBackgroundMusic();
        System.exit(0);

    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonRestartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRestartMouseEntered

        this.jButtonRestart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jButtonRestartMouseEntered

    private void jButtonExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonExitMouseEntered

        this.jButtonExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jButtonExitMouseEntered

    private void jButtonRestartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonRestartKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jButtonRestart.doClick();
        }

    }//GEN-LAST:event_jButtonRestartKeyPressed

    private void jButtonExitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonExitKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jButtonExit.doClick();
        }

    }//GEN-LAST:event_jButtonExitKeyPressed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VictoryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            VictoryDialog dialog = new VictoryDialog((JFrameFight) new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonRestart;
    private javax.swing.JLabel jLabelImagen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMain;
    // End of variables declaration//GEN-END:variables
}
