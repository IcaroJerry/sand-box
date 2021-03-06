package ifba.ads.ssol.shell.windows;

import ifba.ads.ssol.shell.Core;
import ifba.ads.ssol.filemenager.fat.Fat2;
import ifba.ads.ssol.interfaces.GUIFileSystem;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciaDeDisco extends javax.swing.JFrame {

    public class ThreadDrawFAT2 extends Thread {

        @Override
        public void run() {
            this.addLinhas();
        }

        private void addLinhas() {
            GUIFileSystem guiFat2 = new GUIFileSystem();
            ArrayList<String[]> datafat2 = guiFat2.showFat2();
            DefaultTableModel tempModel = (DefaultTableModel) tblFAT2.getModel();
            tempModel = cleanRows(tempModel);
            ArrayList lista = new ArrayList();
            int tamanho = Fat2.getSize();

            for (int i = 0; i < tamanho; i++) {
                String[] rowTemp = datafat2.get(i);
                lista.add("" + rowTemp[0]);
                lista.add("" + rowTemp[1]);
                tempModel.addRow(lista.toArray());
                lista.clear();
                rowTemp = null;
            }
            tblFAT2.setModel(tempModel);
            datafat2 = new ArrayList<String[]>();
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

    public class ThreadDrawFAT1 extends Thread {

        @Override
        public void run() {
            this.addLinhas();
        }

        private void addLinhas() {
            GUIFileSystem guiFat1 = new GUIFileSystem();
            ArrayList<String[]> datafat1 = guiFat1.showFat1();
            DefaultTableModel tempModel = (DefaultTableModel) tblFAT1.getModel();
            tempModel = cleanRows(tempModel);
            ArrayList lista = new ArrayList();
            int tamanho = datafat1.size();

            for (int i = 0; i < tamanho; i++) {
                String[] rowTemp = datafat1.get(i);
                lista.add(rowTemp[0]);
                lista.add(rowTemp[1]);
                lista.add(rowTemp[2]);
                tempModel.addRow(lista.toArray());
                lista.clear();
            }
            tblFAT1.setModel(tempModel);
        }

        private DefaultTableModel cleanRows(DefaultTableModel tempModel) {
            for (int i = 0; i < tempModel.getRowCount(); i++) {
                tempModel.removeRow(0);
            }
            if (tempModel.getRowCount() != 0) {
                tempModel.removeRow(0);
            }
            return tempModel;
        }

    }

    /**
     * Creates new form Janela2
     */
    public TelaGerenciaDeDisco() {
        initComponents();

        new ThreadDrawFAT1().start();
        new ThreadDrawFAT2().start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFAT1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblFAT2 = new javax.swing.JTable();

        jScrollPane2.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerência de Disco");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel2.setText("GERENCIAMENTO DE DISCO");

        tblFAT1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Processo", "Bloco", "Tamanho"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFAT1.setColumnSelectionAllowed(true);
        tblFAT1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblFAT1);
        tblFAT1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblFAT1.getColumnModel().getColumnCount() > 0) {
            tblFAT1.getColumnModel().getColumn(0).setResizable(false);
            tblFAT1.getColumnModel().getColumn(1).setResizable(false);
            tblFAT1.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel3.setText("FAT 1");

        jLabel4.setText("FAT2");

        tblFAT2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Posição", "Bloco"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFAT2.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(tblFAT2);
        tblFAT2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblFAT2.getColumnModel().getColumnCount() > 0) {
            tblFAT2.getColumnModel().getColumn(0).setResizable(false);
            tblFAT2.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel2)))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Core.getWindowProcesso().notifyWindowCheck();
    }//GEN-LAST:event_formWindowClosing

    public void initializeDataTable() {
        DefaultTableModel tempModel = (DefaultTableModel) this.tblFAT2.getModel();
        for (int i = 0; i < Fat2.getSize(); i++) {
            tempModel.addRow(new Object[]{"" + i}); // Icaro Deve fornecer;
        }
    }

    public void notifyFat1() {
        new ThreadDrawFAT1().start();
    }

    public void notifyFat2() {
        new ThreadDrawFAT2().start();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTree jTree1;
    private javax.swing.JTable tblFAT1;
    private javax.swing.JTable tblFAT2;
    // End of variables declaration//GEN-END:variables
}
