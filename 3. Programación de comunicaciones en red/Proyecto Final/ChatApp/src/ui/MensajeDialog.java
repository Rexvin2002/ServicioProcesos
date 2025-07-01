package ui;

import controllers.Controller;
import javax.swing.JFrame;

/**
 *
 * @author kgv17
 */
public class MensajeDialog extends javax.swing.JDialog {

    /**
     * Creates new form MessageDialog
     *
     * @param parent
     * @param modal
     */
    public MensajeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        Controller.applyHandCursorToAllButtons(rootPane);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDialogo = new javax.swing.JPanel();
        jPanelEtiqueta = new javax.swing.JPanel();
        jLabelMensaje = new javax.swing.JLabel();
        jPanelBoton = new javax.swing.JPanel();
        jButtonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dialogo");
        setMinimumSize(new java.awt.Dimension(700, 220));

        jPanelDialogo.setBackground(new java.awt.Color(51, 51, 51));
        jPanelDialogo.setMinimumSize(new java.awt.Dimension(700, 220));
        jPanelDialogo.setPreferredSize(new java.awt.Dimension(700, 220));
        jPanelDialogo.setLayout(new javax.swing.BoxLayout(jPanelDialogo, javax.swing.BoxLayout.Y_AXIS));

        jPanelEtiqueta.setOpaque(false);

        jLabelMensaje.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelMensaje.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMensaje.setText("jLabel1");
        jLabelMensaje.setMaximumSize(new java.awt.Dimension(700, 160));
        jLabelMensaje.setMinimumSize(new java.awt.Dimension(700, 160));
        jLabelMensaje.setPreferredSize(new java.awt.Dimension(700, 160));
        jPanelEtiqueta.add(jLabelMensaje);

        jPanelDialogo.add(jPanelEtiqueta);

        jPanelBoton.setOpaque(false);

        jButtonCerrar.setBackground(new java.awt.Color(0, 204, 51));
        jButtonCerrar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonCerrar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonCerrar.setText("Cerrar");
        jButtonCerrar.setBorder(null);
        jButtonCerrar.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonCerrar.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonCerrar.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarActionPerformed(evt);
            }
        });
        jPanelBoton.add(jButtonCerrar);

        jPanelDialogo.add(jPanelBoton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDialogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDialogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    public static void showMessageDialog(JFrame chatWindow, String mensaje, String titulo) {
        MensajeDialog mensajeDialog = new MensajeDialog(chatWindow, true);

        mensajeDialog.setTitle(titulo);
        
        // Establecer el texto con salto de línea automático usando HTML
        mensajeDialog.jLabelMensaje.setText(
                "<html><body style='width: fit-content;'>" + mensaje + "</body></html>"
        );

        // Ajustar el tamaño al contenido
        mensajeDialog.jLabelMensaje.setSize(
                mensajeDialog.jLabelMensaje.getPreferredSize()
        );

        // Redimensionar el diálogo según el contenido
        mensajeDialog.pack();
        mensajeDialog.setLocationRelativeTo(chatWindow);

        // Mostrar el diálogo
        mensajeDialog.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MensajeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MensajeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MensajeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MensajeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            MensajeDialog dialog = new MensajeDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JLabel jLabelMensaje;
    private javax.swing.JPanel jPanelBoton;
    private javax.swing.JPanel jPanelDialogo;
    private javax.swing.JPanel jPanelEtiqueta;
    // End of variables declaration//GEN-END:variables
}
