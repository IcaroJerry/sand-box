/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.client.ui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import javax.swing.JOptionPane;
import org.sdfs.client.controller.ClientController;
import org.sdfs.client.controller.LogController;
import org.sdfs.client.controller.UiController;

/**
 *
 * @author icarojerry
 */
public final class DSConfigScene extends javax.swing.JPanel {

    /**
     * Creates new form MainScene
     */
    public DSConfigScene() {
        initComponents();

        DEFAULT_MSG_txtAddr = this.txtServiceAddr.getText();
        DEFAULT_MSG_txtPort = this.txtServicePort.getText();

        addFocusLitener(this.txtServiceAddr);
        addFocusLitener(this.txtServicePort);

        this.bntLoadPreviousData.setVisible(false);
        this.bntLoadPreviousData.setEnabled(false);

        setTxtServiceAddr(ClientController.getInstance().getDirectoryService().getAddr());
        setTxtPort("" + ClientController.getInstance().getDirectoryService().getPort());
        refreshScene();
    }
    
    public void refreshScene() {
        if(!ClientController.getInstance().isConnected2DS()) {
            statusDS.setText("Disconnected :(");
            statusDS.setForeground(Color.red);
        }
        else {
            statusDS.setText("Connected :)");
            statusDS.setForeground(Color.green);
        }
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


    public void setTxtPort(String port) {
        if (port.isEmpty() || port.equals("0")) {
            port = DEFAULT_MSG_txtPort;
        }

        if (!port.equals(DEFAULT_MSG_txtPort)) {
            this.txtServicePort.setForeground(java.awt.Color.BLACK);
        } else {
            this.txtServicePort.setForeground(java.awt.Color.GRAY);
        }

        this.txtServicePort.setText(port);
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
        return this.bntConnectDS;
    }

    public javax.swing.JButton getBntLoadPreviousData() {
        return this.bntLoadPreviousData;
    }

    public void verifyTurnOnButton() {
        if (DEFAULT_MSG_txtAddr.equals(this.txtServiceAddr.getText())
                || this.txtServiceAddr.getText().isEmpty()
                || this.getTxtServicePort().equals("0")) {
            this.bntConnectDS.setEnabled(false);
        } else {
            this.bntConnectDS.setEnabled(true);
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

        bntConnectDS = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        bntLoadPreviousData = new javax.swing.JButton();
        ckSaveData = new javax.swing.JCheckBox();
        lblServiceAddr = new javax.swing.JLabel();
        txtServiceAddr = new javax.swing.JTextField();
        lblServicePort = new javax.swing.JLabel();
        txtServicePort = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        statusDS = new javax.swing.JLabel();

        bntConnectDS.setText("Connect");
        bntConnectDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntConnectDSActionPerformed(evt);
            }
        });

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

        jLabel8.setFont(new java.awt.Font("Dialog", 2, 10)); // NOI18N
        jLabel8.setText("* Required field");

        lblStatus.setText("Status:");

        statusDS.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        statusDS.setForeground(java.awt.Color.red);
        statusDS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusDS.setText("Disconnected");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntLoadPreviousData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bntConnectDS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblServiceAddr)
                                    .addComponent(lblServicePort))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtServicePort)
                                    .addComponent(txtServiceAddr, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ckSaveData)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(lblStatus)
                                .addGap(18, 18, 18)
                                .addComponent(statusDS, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 66, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(statusDS))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
                        .addGap(34, 34, 34)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckSaveData)
                .addGap(21, 21, 21)
                .addComponent(bntLoadPreviousData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntConnectDS)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bntLoadPreviousDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLoadPreviousDataActionPerformed
        
    }//GEN-LAST:event_bntLoadPreviousDataActionPerformed

    private void bntConnectDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntConnectDSActionPerformed
        String addr;
        int port;
        try {
            
            if(ClientController.getInstance().isConnected2DS()){
               int opt =  JOptionPane.showConfirmDialog(this, "Você já está conectado a um Serviço de Diretoria. Deseja continuar?",
                          "Directory Service Connected",
                          JOptionPane.CANCEL_OPTION);
               if(opt != JOptionPane.OK_OPTION) {
                   return;
               }
            }
            
            addr = this.txtServiceAddr.getText();
            if(ClientController.getInstance().getDirectoryService().getAddr() != addr) {
                ClientController.getInstance().getDirectoryService().setAddr(addr);
            }
            
            port = Integer.parseInt(this.txtServicePort.getText());
            if(ClientController.getInstance().getDirectoryService().getPort() != port) {
                ClientController.getInstance().getDirectoryService().setPort(port);
            }
            
            this.setEnabled(false);
            ClientController.getInstance().connectToDirectoryService();
            this.setEnabled(true);
            this.refreshScene();
            UiController.getInstance().refreshAll();
        }catch(NumberFormatException ex) {
            this.txtServicePort.setText(""+ClientController.getInstance().getDirectoryService().getPort());
            LogController.printLog("ALERT", "Por favor digite um número para o porto.");
            return;
        }
    }//GEN-LAST:event_bntConnectDSActionPerformed

    private final String DEFAULT_MSG_txtAddr;
    private final String DEFAULT_MSG_txtPort;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntConnectDS;
    private javax.swing.JButton bntLoadPreviousData;
    private javax.swing.JCheckBox ckSaveData;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblServiceAddr;
    private javax.swing.JLabel lblServicePort;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel statusDS;
    private javax.swing.JTextField txtServiceAddr;
    private javax.swing.JTextField txtServicePort;
    // End of variables declaration//GEN-END:variables
}
