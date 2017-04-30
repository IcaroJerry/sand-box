package ifba.ads.ssol.shell.windows;

import ifba.ads.ssol.interfaces.MemoryFacade;
import ifba.ads.ssol.shell.Core;
import ifba.ads.ssol.memorymenager.gerenciadememoria.Pagina;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciaDeMemoria extends javax.swing.JFrame {

    public class ThreadDrawElements extends Thread {

        @Override
        public void run() {
            this.addLinhas();
        }

        private void addLinhas() {
            DefaultTableModel tempTabelMemory = (DefaultTableModel) tblMemory.getModel();
            DefaultTableModel tempTableMold = (DefaultTableModel) tblMoldPage.getModel();
            int[] moldMemory = MemoryFacade.getInstance().getMolduraPagina();
            List<Pagina> listFifo = MemoryFacade.getInstance().getFilaFifo();
            tempTabelMemory = cleanRows(tempTabelMemory);
            tempTableMold = cleanRows(tempTableMold);

            ArrayList lista = new ArrayList();
            int moldSize = moldMemory.length;

            for (int i = 0; i < moldSize; i++) {

                if (moldMemory[i] == 0) {
                    lista.add("VAZIO");
                } else {
                    lista.add("PREENCHIDA");
                }

                tempTableMold.addRow(lista.toArray());
                lista.clear();
            }

            if (listFifo != null) {
                int fifoSize = listFifo.size();
                for (int i = 0; i < fifoSize; i++) {
                    lista.add("" + listFifo.get(i).getProcessId());
                    lista.add("" + listFifo.get(i).getNpr());
                    lista.add("" + listFifo.get(i).getNpv());
                    tempTabelMemory.addRow(lista.toArray());
                    lista.clear();
                }
            }
            tblMoldPage.setModel(tempTableMold);
            tblMemory.setModel(tempTabelMemory);
        }

        private DefaultTableModel cleanRows(DefaultTableModel tempModel) {
            int size = tempModel.getRowCount();
            for (int i = 0; i < size; i++) {
                tempModel.removeRow(0);
            }
            if (tempModel.getRowCount() != 0) {
                tempModel.removeRow(0);
            }
            return tempModel;
        }

    }

    public void notifyWindow() {
        new ThreadDrawElements().start();
    }

    /**
     * Creates new form Janela2
     */
    public TelaGerenciaDeMemoria() {
        initComponents();
        new ThreadDrawElements().start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane7 = new javax.swing.JScrollPane();
        jTextPane7 = new javax.swing.JTextPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane4 = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextPane8 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane5 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPane6 = new javax.swing.JTextPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblMemory = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblMoldPage = new javax.swing.JTable();
        lblTitle1 = new javax.swing.JLabel();

        jScrollPane7.setViewportView(jTextPane7);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane9.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane10.setViewportView(jTextArea2);

        jScrollPane4.setViewportView(jTextPane4);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane11.setViewportView(jTextArea3);

        jScrollPane1.setViewportView(jTextPane1);

        jScrollPane8.setViewportView(jTextPane8);

        jScrollPane5.setViewportView(jTextPane5);

        jScrollPane2.setViewportView(jTextPane2);

        jScrollPane3.setViewportView(jTextPane3);

        jScrollPane6.setViewportView(jTextPane6);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerência de Memória");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tblMemory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PID", "Nº Pag. Real", "Nº Pag Virtual"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMemory.getTableHeader().setReorderingAllowed(false);
        jScrollPane12.setViewportView(tblMemory);
        if (tblMemory.getColumnModel().getColumnCount() > 0) {
            tblMemory.getColumnModel().getColumn(0).setResizable(false);
            tblMemory.getColumnModel().getColumn(1).setResizable(false);
            tblMemory.getColumnModel().getColumn(2).setResizable(false);
        }

        tblMoldPage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Modulra de Página"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMoldPage.setColumnSelectionAllowed(true);
        jScrollPane13.setViewportView(tblMoldPage);
        tblMoldPage.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblMoldPage.getColumnModel().getColumnCount() > 0) {
            tblMoldPage.getColumnModel().getColumn(0).setResizable(false);
        }

        lblTitle1.setText("Fila FIFO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Core.getWindowProcesso().notifyWindowCheck();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane4;
    private javax.swing.JTextPane jTextPane5;
    private javax.swing.JTextPane jTextPane6;
    private javax.swing.JTextPane jTextPane7;
    private javax.swing.JTextPane jTextPane8;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JTable tblMemory;
    private javax.swing.JTable tblMoldPage;
    // End of variables declaration//GEN-END:variables
}