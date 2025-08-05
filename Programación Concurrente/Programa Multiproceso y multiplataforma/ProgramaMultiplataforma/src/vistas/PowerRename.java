package vistas;

import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;
import programamultiplataforma.Controller;

/**
 *
 * @author kgv17
 */
public class PowerRename extends javax.swing.JFrame {

    private String filePath;
    private String content = "";
    private static final String SEPARATOR = Controller.getSEPARATOR();

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public PowerRename() {

        initComponents();
        setLocationRelativeTo(null);

        try {

            Controller.configurarUTF8Encoding();

        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError: " + e.getMessage());
            System.out.println("\n" + SEPARATOR);
        }

        Controller.escalarEstablecerImagen(this.jLabelOptions3, "/imgs/OptionsIcon.png");
        Controller.escalarEstablecerImagenBoton(this.jButtonDocument, "/imgs/DocumentIcon.png", 5);
        Controller.escalarEstablecerImagenBoton(this.jButtonFolder, "/imgs/FolderIcon.png", 5);
        Controller.escalarEstablecerImagenBoton(this.jButtonText, "/imgs/TextIcon.png", 5);
        Controller.escalarEstablecerImagenBoton(this.jButtonTextFormat, "/imgs/TextIcon.png", 5);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldSearch = new javax.swing.JTextField();
        jCheckBoxRegularExpression = new javax.swing.JCheckBox();
        jCheckBoxMatchOcurrences = new javax.swing.JCheckBox();
        jCheckBoxCaseSensitive2 = new javax.swing.JCheckBox();
        jSeparator9 = new javax.swing.JSeparator();
        jTextFieldTipo2 = new javax.swing.JTextField();
        jLabelApplyTo2 = new javax.swing.JLabel();
        jComboBoxApplyTo2 = new javax.swing.JComboBox<>();
        jSeparator10 = new javax.swing.JSeparator();
        jButtonDocument = new javax.swing.JButton();
        jButtonFolder = new javax.swing.JButton();
        jButtonText = new javax.swing.JButton();
        jLabelTextFormatting2 = new javax.swing.JLabel();
        jButtonaa2 = new javax.swing.JButton();
        jButtonAA2 = new javax.swing.JButton();
        jButtonAa2 = new javax.swing.JButton();
        jButtonAaAa2 = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JSeparator();
        jButtonTextFormat = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JSeparator();
        jButtonApply2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabelOptions3 = new javax.swing.JLabel();
        jLabelOptions2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1700, 1000));
        setPreferredSize(new java.awt.Dimension(1700, 1000));
        setSize(new java.awt.Dimension(1700, 1000));

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextFieldSearch.setText("jTextField1");
        jTextFieldSearch.setMinimumSize(new java.awt.Dimension(282, 40));
        jTextFieldSearch.setPreferredSize(new java.awt.Dimension(282, 40));

        jCheckBoxRegularExpression.setText("Use regular expressions");
        jCheckBoxRegularExpression.setMaximumSize(new java.awt.Dimension(282, 20));
        jCheckBoxRegularExpression.setMinimumSize(new java.awt.Dimension(282, 20));
        jCheckBoxRegularExpression.setPreferredSize(new java.awt.Dimension(282, 20));
        jCheckBoxRegularExpression.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRegularExpressionActionPerformed(evt);
            }
        });

        jCheckBoxMatchOcurrences.setText("Match all occurences");
        jCheckBoxMatchOcurrences.setMaximumSize(new java.awt.Dimension(282, 20));
        jCheckBoxMatchOcurrences.setMinimumSize(new java.awt.Dimension(282, 20));
        jCheckBoxMatchOcurrences.setPreferredSize(new java.awt.Dimension(282, 20));
        jCheckBoxMatchOcurrences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMatchOcurrencesActionPerformed(evt);
            }
        });

        jCheckBoxCaseSensitive2.setText("Case sensitive");
        jCheckBoxCaseSensitive2.setMaximumSize(new java.awt.Dimension(282, 20));
        jCheckBoxCaseSensitive2.setMinimumSize(new java.awt.Dimension(282, 20));
        jCheckBoxCaseSensitive2.setPreferredSize(new java.awt.Dimension(282, 20));
        jCheckBoxCaseSensitive2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCaseSensitive2ActionPerformed(evt);
            }
        });

        jSeparator9.setMinimumSize(new java.awt.Dimension(282, 10));
        jSeparator9.setPreferredSize(new java.awt.Dimension(282, 10));

        jTextFieldTipo2.setText("foobar");
        jTextFieldTipo2.setMinimumSize(new java.awt.Dimension(282, 40));
        jTextFieldTipo2.setPreferredSize(new java.awt.Dimension(282, 40));

        jLabelApplyTo2.setText("Apply to");

        jComboBoxApplyTo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filename + extension", "Item 2", "Item 3", "Item 4" }));
        jComboBoxApplyTo2.setMinimumSize(new java.awt.Dimension(146, 30));
        jComboBoxApplyTo2.setPreferredSize(new java.awt.Dimension(146, 30));

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButtonDocument.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonDocument.setMaximumSize(new java.awt.Dimension(30, 30));
        jButtonDocument.setMinimumSize(new java.awt.Dimension(30, 30));
        jButtonDocument.setPreferredSize(new java.awt.Dimension(30, 30));

        jButtonFolder.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonFolder.setMaximumSize(new java.awt.Dimension(50, 35));
        jButtonFolder.setMinimumSize(new java.awt.Dimension(30, 30));
        jButtonFolder.setPreferredSize(new java.awt.Dimension(30, 30));
        jButtonFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFolderActionPerformed(evt);
            }
        });

        jButtonText.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonText.setMaximumSize(new java.awt.Dimension(50, 35));
        jButtonText.setMinimumSize(new java.awt.Dimension(30, 30));
        jButtonText.setPreferredSize(new java.awt.Dimension(30, 30));

        jLabelTextFormatting2.setText("Text formatting");

        jButtonaa2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButtonaa2.setText("aa");
        jButtonaa2.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonaa2.setMaximumSize(new java.awt.Dimension(50, 35));
        jButtonaa2.setMinimumSize(new java.awt.Dimension(50, 35));
        jButtonaa2.setPreferredSize(new java.awt.Dimension(50, 35));
        jButtonaa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonaa2ActionPerformed(evt);
            }
        });

        jButtonAA2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButtonAA2.setText("AA");
        jButtonAA2.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonAA2.setMaximumSize(new java.awt.Dimension(60, 35));
        jButtonAA2.setMinimumSize(new java.awt.Dimension(60, 35));
        jButtonAA2.setPreferredSize(new java.awt.Dimension(60, 35));
        jButtonAA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAA2ActionPerformed(evt);
            }
        });

        jButtonAa2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButtonAa2.setText("Aa");
        jButtonAa2.setMaximumSize(new java.awt.Dimension(50, 35));
        jButtonAa2.setMinimumSize(new java.awt.Dimension(50, 35));
        jButtonAa2.setPreferredSize(new java.awt.Dimension(50, 35));

        jButtonAaAa2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButtonAaAa2.setText("Aa Aa");
        jButtonAaAa2.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonAaAa2.setMaximumSize(new java.awt.Dimension(80, 35));
        jButtonAaAa2.setMinimumSize(new java.awt.Dimension(80, 35));
        jButtonAaAa2.setPreferredSize(new java.awt.Dimension(80, 35));

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButtonTextFormat.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonTextFormat.setMaximumSize(new java.awt.Dimension(50, 35));
        jButtonTextFormat.setMinimumSize(new java.awt.Dimension(50, 35));
        jButtonTextFormat.setPreferredSize(new java.awt.Dimension(50, 35));

        jSeparator12.setMinimumSize(new java.awt.Dimension(282, 20));
        jSeparator12.setPreferredSize(new java.awt.Dimension(282, 20));

        jButtonApply2.setBackground(new java.awt.Color(0, 102, 153));
        jButtonApply2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButtonApply2.setForeground(new java.awt.Color(255, 255, 255));
        jButtonApply2.setText("Apply");
        jButtonApply2.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jButtonApply2.setMaximumSize(new java.awt.Dimension(50, 35));
        jButtonApply2.setMinimumSize(new java.awt.Dimension(30, 30));
        jButtonApply2.setPreferredSize(new java.awt.Dimension(30, 30));

        jLabelOptions3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelOptions3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOptions3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/OptionsIcon.png"))); // NOI18N
        jLabelOptions3.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelOptions3.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelOptions3.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelOptions2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelOptions2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOptions2.setText("?");
        jLabelOptions2.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelOptions2.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelOptions2.setPreferredSize(new java.awt.Dimension(25, 25));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabelOptions3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 42, Short.MAX_VALUE)
                .addComponent(jLabelOptions2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jLabelOptions3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelOptions2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                        .addComponent(jLabelTextFormatting2)
                        .addComponent(jLabelApplyTo2)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jButtonaa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonAA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonAa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonAaAa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(jButtonTextFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonApply2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldTipo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxRegularExpression, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxMatchOcurrences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxCaseSensitive2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jComboBoxApplyTo2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxRegularExpression, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxMatchOcurrences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxCaseSensitive2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldTipo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelApplyTo2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDocument, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxApplyTo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator10))
                .addGap(23, 23, 23)
                .addComponent(jLabelTextFormatting2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonaa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAaAa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator11)
                    .addComponent(jButtonTextFormat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 526, Short.MAX_VALUE)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonApply2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1101, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    private void jButtonFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFolderActionPerformed

        // Crear un JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Establecer el título del diálogo
        fileChooser.setDialogTitle("Select a file");

        // Abrir el diálogo para seleccionar un archivo
        int result = fileChooser.showOpenDialog(null);

        // Comprobar si se seleccionó un archivo
        if (result == JFileChooser.APPROVE_OPTION) {

            // Obtener el archivo seleccionado
            File selectedFile = fileChooser.getSelectedFile();

            // Almacenar la ruta en una variable
            filePath = selectedFile.getAbsolutePath();

            // Mostrar la ruta del archivo
            System.out.println("\nSelected file path: " + filePath);

        } else {
            System.out.println("\nNo file was selected.");
        }

    }//GEN-LAST:event_jButtonFolderActionPerformed

    /*
     * -----------------------------------------------------------------------
     * EVENTOS
     * -----------------------------------------------------------------------
     */
    private void jCheckBoxCaseSensitive2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCaseSensitive2ActionPerformed
        this.jTextFieldSearch.getText();
    }//GEN-LAST:event_jCheckBoxCaseSensitive2ActionPerformed

    private void jCheckBoxMatchOcurrencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMatchOcurrencesActionPerformed
        this.jTextFieldSearch.getText();
    }//GEN-LAST:event_jCheckBoxMatchOcurrencesActionPerformed

    private void jCheckBoxRegularExpressionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRegularExpressionActionPerformed
        this.jTextFieldSearch.getText();
    }//GEN-LAST:event_jCheckBoxRegularExpressionActionPerformed

    private void jButtonaa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonaa2ActionPerformed
        this.content = this.content.toLowerCase();
    }//GEN-LAST:event_jButtonaa2ActionPerformed

    private void jButtonAA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAA2ActionPerformed
        this.content = this.content.toUpperCase();
    }//GEN-LAST:event_jButtonAA2ActionPerformed

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String args[]) {

        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {

                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }

            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PowerRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new PowerRename().setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAA2;
    private javax.swing.JButton jButtonAa2;
    private javax.swing.JButton jButtonAaAa2;
    private javax.swing.JButton jButtonApply2;
    private javax.swing.JButton jButtonDocument;
    private javax.swing.JButton jButtonFolder;
    private javax.swing.JButton jButtonText;
    private javax.swing.JButton jButtonTextFormat;
    private javax.swing.JButton jButtonaa2;
    private javax.swing.JCheckBox jCheckBoxCaseSensitive2;
    private javax.swing.JCheckBox jCheckBoxMatchOcurrences;
    private javax.swing.JCheckBox jCheckBoxRegularExpression;
    private javax.swing.JComboBox<String> jComboBoxApplyTo2;
    private javax.swing.JLabel jLabelApplyTo2;
    private javax.swing.JLabel jLabelOptions2;
    private javax.swing.JLabel jLabelOptions3;
    private javax.swing.JLabel jLabelTextFormatting2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldTipo2;
    // End of variables declaration//GEN-END:variables
}
