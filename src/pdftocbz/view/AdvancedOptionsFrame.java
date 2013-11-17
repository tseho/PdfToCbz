/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.view;


/**
 *
 * @author Tseho
 */
public final class AdvancedOptionsFrame extends javax.swing.JDialog {
    
    private WindowFrame window;

    /**
     * Creates new form AdvancedOptionsFrame
     */
    public AdvancedOptionsFrame(WindowFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.window = parent;
        
        this.renameArchivesPanel.setVisible(false);
        
        this.applySettings();
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void applySettings(){
        this.renameArchivesCheckBox.setSelected(this.window.getController().getAppSettings().isRenameArchives());
        
        this.inputRenameArchives.setText(this.window.getController().getAppSettings().getRenameArchivesSyntax());
        
        this.renameImagesCheckBox.setSelected(this.window.getController().getAppSettings().isRenameImages());
        
        this.inputRenameImages.setText(this.window.getController().getAppSettings().getRenameImagesSyntax());
    }
    
    public boolean isFormValid(){
        if(this.inputRenameArchives.getText().trim().length() == 0){
            return false;
        }
        if(this.inputRenameImages.getText().trim().length() == 0){
            return false;
        }
        return true;
    }
    
    public void updateSettings(){
        
        this.window.getController().getAppSettings().setRenameArchives(this.renameArchivesCheckBox.isSelected());
        
        this.window.getController().getAppSettings().setRenameImages(this.renameImagesCheckBox.isSelected());
        
        this.window.getController().getAppSettings().setRenameArchivesSyntax(this.inputRenameArchives.getText());
        
        this.window.getController().getAppSettings().setRenameImagesSyntax(this.inputRenameImages.getText());
        
        this.window.getController().getAppSettings().saveProperties();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        renameArchivesPanel = new javax.swing.JPanel();
        renameArchivesCheckBox = new javax.swing.JCheckBox();
        helpRenameArchivesLabel = new javax.swing.JLabel();
        helpRenameArchivesIcon = new javax.swing.JLabel();
        inputRenameArchives = new javax.swing.JTextField();
        renameImagesPanel = new javax.swing.JPanel();
        renameImagesCheckBox = new javax.swing.JCheckBox();
        helpRenameImagesIcon = new javax.swing.JLabel();
        helpRenameImagesLabel = new javax.swing.JLabel();
        inputRenameImages = new javax.swing.JTextField();
        validateButton = new javax.swing.JToggleButton();
        cancelButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        renameArchivesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        renameArchivesCheckBox.setText("Renommer les archives/dossiers exportés");

        helpRenameArchivesLabel.setText("Variables disponibles : $m = numéro du tome, $t = titre, $c = collection");

        helpRenameArchivesIcon.setText("Help");

        inputRenameArchives.setText("$m - $t");
        inputRenameArchives.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputRenameArchivesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout renameArchivesPanelLayout = new javax.swing.GroupLayout(renameArchivesPanel);
        renameArchivesPanel.setLayout(renameArchivesPanelLayout);
        renameArchivesPanelLayout.setHorizontalGroup(
            renameArchivesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(renameArchivesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(renameArchivesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputRenameArchives)
                    .addGroup(renameArchivesPanelLayout.createSequentialGroup()
                        .addGroup(renameArchivesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(renameArchivesCheckBox)
                            .addGroup(renameArchivesPanelLayout.createSequentialGroup()
                                .addComponent(helpRenameArchivesIcon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(helpRenameArchivesLabel)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        renameArchivesPanelLayout.setVerticalGroup(
            renameArchivesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(renameArchivesPanelLayout.createSequentialGroup()
                .addComponent(renameArchivesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(renameArchivesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(helpRenameArchivesLabel)
                    .addComponent(helpRenameArchivesIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputRenameArchives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        renameImagesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        renameImagesCheckBox.setText("Renommer les images");

        helpRenameImagesIcon.setText("Help");

        helpRenameImagesLabel.setText("Variables disponibles : $n = numéro de l'image ($n=1, $n2 = 01, $n3 = 001, etc), $d = nom du pdf");

        inputRenameImages.setText("$n3");
        inputRenameImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputRenameImagesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout renameImagesPanelLayout = new javax.swing.GroupLayout(renameImagesPanel);
        renameImagesPanel.setLayout(renameImagesPanelLayout);
        renameImagesPanelLayout.setHorizontalGroup(
            renameImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(renameImagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(renameImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputRenameImages)
                    .addGroup(renameImagesPanelLayout.createSequentialGroup()
                        .addGroup(renameImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(renameImagesPanelLayout.createSequentialGroup()
                                .addComponent(helpRenameImagesIcon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(helpRenameImagesLabel))
                            .addComponent(renameImagesCheckBox))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        renameImagesPanelLayout.setVerticalGroup(
            renameImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(renameImagesPanelLayout.createSequentialGroup()
                .addComponent(renameImagesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(renameImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(helpRenameImagesIcon)
                    .addComponent(helpRenameImagesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputRenameImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        validateButton.setText("Valider");
        validateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validateButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Annuler");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(renameArchivesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(renameImagesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(validateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(renameArchivesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(renameImagesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(validateButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputRenameArchivesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputRenameArchivesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputRenameArchivesActionPerformed

    private void validateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validateButtonActionPerformed
        // TODO add your handling code here:
        if(this.isFormValid()){
            this.updateSettings();
            this.setVisible(false);
        }
    }//GEN-LAST:event_validateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void inputRenameImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputRenameImagesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputRenameImagesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JLabel helpRenameArchivesIcon;
    private javax.swing.JLabel helpRenameArchivesLabel;
    private javax.swing.JLabel helpRenameImagesIcon;
    private javax.swing.JLabel helpRenameImagesLabel;
    private javax.swing.JTextField inputRenameArchives;
    private javax.swing.JTextField inputRenameImages;
    private javax.swing.JCheckBox renameArchivesCheckBox;
    private javax.swing.JPanel renameArchivesPanel;
    private javax.swing.JCheckBox renameImagesCheckBox;
    private javax.swing.JPanel renameImagesPanel;
    private javax.swing.JToggleButton validateButton;
    // End of variables declaration//GEN-END:variables
}
