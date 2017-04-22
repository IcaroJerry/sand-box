/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.server.ui;

import java.awt.event.FocusEvent;
import org.sdfs.interfaces.sdfsexceptions.ServerActionException;
import org.sdfs.server.controller.LogController;
import org.sdfs.server.controller.ServerController;
import org.sdfs.server.controller.UiController;

/**
 *
 * @author icarojerry
 */
public final class InitializeScene extends javax.swing.JPanel {

    /**
     * Creates new form MainScene
     */
    public InitializeScene() {
        initComponents();

        DEFAULT_MSG_txtAddr = this.txtServiceAddr.getText();
        DEFAULT_MSG_txtAlias = this.txtAlias.getText();
        DEFAULT_MSG_txtPort = this.txtPort.getText();
        DEFAULT_MSG_txtDataDir = this.txtDataDir.getText();

        addFocusLitener(this.txtServiceAddr);
        addFocusLitener(this.txtServicePort);
        addFocusLitener(this.txtAlias);
        addFocusLitener(this.txtDataDir);
        addFocusLitener(this.txtPort);

        this.bntLoadPreviousData.setVisible(false);
        this.bntLoadPreviousData.setEnabled(false);

        setTxtServiceAddr(ServerController.getInstance().getDataServer().getServiceAddr());
        setTxtAlias(ServerController.getInstance().getDataServer().getServerAlias());
        setTxtDataDir(ServerController.getInstance().getDataServer().getServerDataDir());
        setTxtServicePort("" + ServerController.getInstance().getDataServer().getServicePort());
        setTxtPort("" + ServerController.getInstance().getDataServer().getServerPort());
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getTxtServiceAddr() {
        if (this.txtServiceAddr.getText().isEmpty()
                || this.txtServiceAddr.getText().equals(DEFAULT_MSG_txtAddr)) {
            return "";
        } else {
            return this.txtServiceAddr.getText();
        }
    }

    public String getTxtServicePort() {
        if (this.txtServicePort.getText().isEmpty()
                || this.txtServicePort.getText().equals(DEFAULT_MSG_txtPort)) {
            return "0";
        } else {
            return this.txtServicePort.getText();
        }
    }

    public String getTxtAlias() {
        if (this.txtAlias.getText().isEmpty()
                || this.txtAlias.getText().equals(DEFAULT_MSG_txtAlias)) {
            return "";
        } else {
            return this.txtAlias.getText();
        }

    }

    public String getTxtDataDir() {
        if (this.txtDataDir.getText().isEmpty()
                || this.txtDataDir.getText().equals(DEFAULT_MSG_txtDataDir)) {
            return "";
        } else {
            return this.txtDataDir.getText();
        }
    }

    public String getTxtPort() {
        if (this.txtPort.getText().isEmpty()
                || this.txtPort.getText().equals(DEFAULT_MSG_txtPort)) {
            return "0";
        } else {
            return this.txtPort.getText();
        }
    }

    public void setTxtServiceAddr(String serviceAddr) {
        if (serviceAddr.isEmpty()) {
            serviceAddr = DEFAULT_MSG_txtAddr;
        }

        if (!serviceAddr.equals(DEFAULT_MSG_txtAddr)) {
            this.txtServiceAddr.setForeground(java.awt.Color.BLACK);
        } else {
            this.txtServiceAddr.setForeground(java.awt.Color.GRAY);
        }

        this.txtServiceAddr.setText(serviceAddr);
        verifyTurnOnButton();
    }

    public void setTxtAlias(String serverAlias) {
        if (serverAlias.isEmpty()) {
            serverAlias = DEFAULT_MSG_txtAlias;
        }

        if (!serverAlias.equals(DEFAULT_MSG_txtAlias)) {
            this.txtAlias.setForeground(java.awt.Color.BLACK);
        } else {
            this.txtAlias.setForeground(java.awt.Color.GRAY);
        }

        this.txtAlias.setText(serverAlias);
        verifyTurnOnButton();
    }

    public void setTxtDataDir(String dataDir) {
        if (dataDir.isEmpty()) {
            dataDir = DEFAULT_MSG_txtDataDir;
        }

        if (!dataDir.equals(DEFAULT_MSG_txtDataDir)) {
            this.txtDataDir.setForeground(java.awt.Color.BLACK);
        } else {
            this.txtDataDir.setForeground(java.awt.Color.GRAY);
        }

        this.txtDataDir.setText(dataDir);
        verifyTurnOnButton();
    }

    public void setTxtPort(String serverPort) {
        if (serverPort.isEmpty() || serverPort.equals("0")) {
            serverPort = DEFAULT_MSG_txtPort;
        }

        if (!serverPort.equals(DEFAULT_MSG_txtPort)) {
            this.txtPort.setForeground(java.awt.Color.BLACK);
        } else {
            this.txtPort.setForeground(java.awt.Color.GRAY);
        }

        this.txtPort.setText(serverPort);
        verifyTurnOnButton();
    }

    public void setTxtServicePort(String servicePort) {
        if (servicePort.isEmpty() || servicePort.equals("0")) {
            servicePort = DEFAULT_MSG_txtPort;
        }

        if (!servicePort.equals(DEFAULT_MSG_txtPort)) {
            this.txtServicePort.setForeground(java.awt.Color.BLACK);
        } else {
            this.txtServicePort.setForeground(java.awt.Color.GRAY);
        }

        this.txtServicePort.setText(servicePort);
        verifyTurnOnButton();
    }
    //</editor-fold>

    public javax.swing.JButton getBtnTurnOn() {
        return this.bntTurnOn;
    }

    public javax.swing.JButton getBntLoadPreviousData() {
        return this.bntLoadPreviousData;
    }

    public void verifyTurnOnButton() {
        if (DEFAULT_MSG_txtAddr.equals(this.txtServiceAddr.getText())
                || this.txtServiceAddr.getText().isEmpty()
                || //DEFAULT_MSG_txtAddr.equals(this.txtAddr.getText()) ||
                //this.txtAddr.getText().isEmpty() ||
                DEFAULT_MSG_txtAlias.equals(this.txtAlias.getText())
                || this.txtAlias.getText().isEmpty()
                || this.getTxtServicePort().equals("0")
                || DEFAULT_MSG_txtDataDir.equals(this.txtDataDir.getText())
                || this.txtDataDir.getText().isEmpty()) {
            this.bntTurnOn.setEnabled(false);
        } else {
            this.bntTurnOn.setEnabled(true);
        }
    }

    private void addFocusLitener(javax.swing.JTextField tx) {
        String defaultMsg = tx.getText();
        tx.setForeground(java.awt.Color.GRAY);

        tx.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                if (tx.getText().equals(defaultMsg)) {
                    tx.setText("");
                    tx.setForeground(java.awt.Color.BLACK);
                }
                verifyTurnOnButton();
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (tx.getText().isEmpty()) {
                    tx.setForeground(java.awt.Color.GRAY);
                    tx.setText(defaultMsg);
                }
                verifyTurnOnButton();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bntTurnOn = new javax.swing.JButton();
        txtAlias = new javax.swing.JTextField();
        txtDataDir = new javax.swing.JTextField();
        lblsAlias = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtPort = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bntLoadPreviousData = new javax.swing.JButton();
        ckSaveData = new javax.swing.JCheckBox();
        lblServiceAddr = new javax.swing.JLabel();
        txtServiceAddr = new javax.swing.JTextField();
        lblServicePort = new javax.swing.JLabel();
        txtServicePort = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();

        bntTurnOn.setText("Ligar Servidor");
        bntTurnOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntTurnOnActionPerformed(evt);
            }
        });

        txtAlias.setText("Digite um apelido");

        txtDataDir.setText("Digite um nome do Banco de Dados");

        lblsAlias.setText("Server Alias:");

        jLabel3.setText("Server Port:");

        txtPort.setText("Digite um porto");
        txtPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPortActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 2, 10)); // NOI18N
        jLabel4.setText("field empty for default port.");

        jLabel5.setText("Server DataBase:");

        bntLoadPreviousData.setText("Carregar Configurações");
        bntLoadPreviousData.setEnabled(false);
        bntLoadPreviousData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntLoadPreviousDataActionPerformed(evt);
            }
        });

        ckSaveData.setSelected(true);
        ckSaveData.setText("Salvar Configurações");

        lblServiceAddr.setText("Service Addr:");

        txtServiceAddr.setText("Digite um endereço");

        lblServicePort.setText("Service Port:");

        txtServicePort.setText("Digite um porto");

        jLabel1.setText("*");

        jLabel2.setText("*");

        jLabel6.setText("*");

        jLabel7.setText("*");

        jLabel8.setFont(new java.awt.Font("Dialog", 2, 10)); // NOI18N
        jLabel8.setText("* Required field");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntLoadPreviousData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bntTurnOn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator3))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(ckSaveData)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDataDir)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPort)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel4))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblsAlias)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAlias))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblServiceAddr)
                                    .addComponent(lblServicePort))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtServicePort)
                                    .addComponent(txtServiceAddr))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServiceAddr)
                    .addComponent(txtServiceAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServicePort)
                    .addComponent(txtServicePort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsAlias)
                    .addComponent(txtAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(ckSaveData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bntLoadPreviousData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntTurnOn)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPortActionPerformed

    private void bntLoadPreviousDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLoadPreviousDataActionPerformed
        try {
            ServerController.getInstance().loadPreviousData();
        } catch (ServerActionException ex) {
            LogController.printLog("ERROR", ex.getMessage());
        }
    }//GEN-LAST:event_bntLoadPreviousDataActionPerformed

    private void bntTurnOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntTurnOnActionPerformed
        try {
            ServerController.getInstance().setDataServer(
                    getTxtAlias(),
                    Integer.parseInt(getTxtPort()),
                    getTxtDataDir(),
                    getTxtServiceAddr(),
                    Integer.parseInt(getTxtServicePort()));
            if (ckSaveData.isSelected()) {
                ServerController.getInstance().dumpServerConfig();
            }

            ServerController.getInstance().turnOnServer();
            UiController.getInstance().refreshAllScreen();
        } catch (Exception ex) {
            LogController.printLog("ERROR", ex.getMessage());
        }
    }//GEN-LAST:event_bntTurnOnActionPerformed

    private final String DEFAULT_MSG_txtAddr;
    private final String DEFAULT_MSG_txtAlias;
    private final String DEFAULT_MSG_txtPort;
    private final String DEFAULT_MSG_txtDataDir;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntLoadPreviousData;
    private javax.swing.JButton bntTurnOn;
    private javax.swing.JCheckBox ckSaveData;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblServiceAddr;
    private javax.swing.JLabel lblServicePort;
    private javax.swing.JLabel lblsAlias;
    private javax.swing.JTextField txtAlias;
    private javax.swing.JTextField txtDataDir;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServiceAddr;
    private javax.swing.JTextField txtServicePort;
    // End of variables declaration//GEN-END:variables
}
