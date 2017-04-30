package ifba.ads.ssol.shell.windows;

import ifba.ads.ssol.processmenager.escalonador.Escalonador;
import ifba.ads.ssol.processmenager.facede.Facede;

public final class TelaFilinalizarProcesso extends javax.swing.JDialog {

    public TelaFilinalizarProcesso(javax.swing.JFrame frame, Boolean modal) {
        super(frame, modal);
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbListProcess = new javax.swing.JComboBox();
        btnFinalizar = new javax.swing.JButton();
        txtTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        txtTitle.setText("Selecione um processo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTitle)
                    .addComponent(cmbListProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(txtTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbListProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        if (evt.getSource() == btnFinalizar) {
            String cod = cmbListProcess.getSelectedItem().toString();
            ifba.ads.ssol.processmenager.facede.Facede.getInstance().solcitarRemocaoProcesso(cod);
            this.dispose();
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed

    public javax.swing.DefaultComboBoxModel preencher() {

        Escalonador tempElements = Facede.getInstance().getEscalonador();
        javax.swing.DefaultComboBoxModel tempModel = new javax.swing.DefaultComboBoxModel<>();
        int size = tempElements.getProcessoCriado().size();

        for (int i = 0; i < size; i++) {
            tempModel.addElement(tempElements.getProcessoCriado().get(i));
        }

        return tempModel;
    }

    @Override
    public void pack() {
        super.pack();
        cmbListProcess.setModel(this.preencher());
        this.setLayout(null);
        cmbListProcess.repaint();
        this.repaint();
        this.setVisible(true);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JComboBox cmbListProcess;
    private javax.swing.JLabel txtTitle;
    // End of variables declaration//GEN-END:variables
}
