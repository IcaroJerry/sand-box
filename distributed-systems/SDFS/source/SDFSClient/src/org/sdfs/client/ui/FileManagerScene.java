/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.client.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.sdfs.client.controller.ClientController;
import org.sdfs.client.controller.LogController;
import org.sdfs.interfaces.GlobalConfig;

/**
 *
 * @author icarojerry
 */
public class FileManagerScene extends javax.swing.JPanel {

    /**
     * Creates new form Simple
     */
    public FileManagerScene() throws IOException {
        super(new BorderLayout(3, 3));
        //initComponents();
        initCustomGui();
    }

    public void showRootFile() {
        tree.setSelectionInterval(0, 0);
    }

    //public static boolean copyFile(File from, File to) throws IOException {
    public boolean copyFile() throws IOException {
        boolean created;

        if (currentFile == null) {
            showErrorMessage("No file selected to rename.", "Select File");
            return false;
        }

        File from = currentFile;
        File to;
        
        String sufix = "_copy";
        int i = 0;

        while (true) {
            if (i != 0) {
                sufix = "_copy" + i;
            }
            File tmp = new File(currentFile.getCanonicalPath() + sufix);
            if (!tmp.exists()) {
                break;
            }
            ++i;
        }

        to = new File(currentFile.getCanonicalPath() + sufix);

        if (!from.isDirectory()) {
            created = to.createNewFile();
        } else {
            created = to.mkdir();
        }

        if (created) {
            FileChannel fromChannel = null;
            FileChannel toChannel = null;
            try {
                if (!to.isDirectory()) {
                    fromChannel = new FileInputStream(from).getChannel();
                    toChannel = new FileOutputStream(to).getChannel();

                    toChannel.transferFrom(fromChannel, 0, fromChannel.size());
                }

                to.setReadable(from.canRead());
                to.setWritable(from.canWrite());
                to.setExecutable(from.canExecute());

            } finally {
                if (fromChannel != null) {
                    fromChannel.close();
                }
                if (toChannel != null) {
                    toChannel.close();
                }
                TreePath parentPath = findTreePath(currentFile.getParentFile());
                DefaultMutableTreeNode parentNode
                        = (DefaultMutableTreeNode) parentPath.getLastPathComponent();

                if (to.isDirectory()) {
                    // add the new node..
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(to);

                    TreePath currentPath = findTreePath(currentFile);
                    DefaultMutableTreeNode currentNode
                            = (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                    treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
                }

                showChildren(parentNode);
            }

        }

        return created;
    }

    private TreePath findTreePath(File find) {
        for (int ii = 0; ii < tree.getRowCount(); ii++) {
            TreePath treePath = tree.getPathForRow(ii);
            Object object = treePath.getLastPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) object;
            File nodeFile = (File) node.getUserObject();

            try {
                if (nodeFile.getCanonicalPath() == find.getCanonicalPath()) {
                    return treePath;
                }
            } catch (IOException ex) {
                return null;
            }
        }
        // not found!
        return null;
    }

    private void renameFile() {
        if (currentFile == null) {
            showErrorMessage("No file selected to rename.", "Select File");
            return;
        }

        String renameTo = JOptionPane.showInputDialog(this, "New Name");
        if (renameTo != null) {
            try {
                boolean directory = currentFile.isDirectory();
                TreePath parentPath = findTreePath(currentFile.getParentFile());
                DefaultMutableTreeNode parentNode
                        = (DefaultMutableTreeNode) parentPath.getLastPathComponent();

                boolean renamed = currentFile.renameTo(new File(
                        currentFile.getParentFile(), renameTo));
                if (renamed) {
                    if (directory) {
                        // rename the node..

                        // delete the current node..
                        TreePath currentPath = findTreePath(currentFile);
                        System.out.println(currentPath);
                        DefaultMutableTreeNode currentNode
                                = (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                        treeModel.removeNodeFromParent(currentNode);

                        // add a new node..
                    }

                    showChildren(parentNode);
                } else {
                    String msg = "The file '"
                            + currentFile
                            + "' could not be renamed.";
                    showErrorMessage(msg, "Rename Failed");
                }
            } catch (Throwable t) {
                showThrowable(t);
            }
        }
        this.repaint();
    }

    private void deleteFile() {
        if (currentFile == null) {
            showErrorMessage("No file selected for deletion.", "Select File");
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this file?",
                "Delete File",
                JOptionPane.ERROR_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            try {
                System.out.println("currentFile: " + currentFile);
                TreePath parentPath = findTreePath(currentFile.getParentFile());
                System.out.println("parentPath: " + parentPath);
                DefaultMutableTreeNode parentNode
                        = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
                System.out.println("parentNode: " + parentNode);

                boolean directory = currentFile.isDirectory();
                boolean deleted = currentFile.delete();
                if (deleted) {
                    if (directory) {
                        // delete the node..
                        TreePath currentPath = findTreePath(currentFile);
                        System.out.println(currentPath);
                        DefaultMutableTreeNode currentNode
                                = (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                        treeModel.removeNodeFromParent(currentNode);
                    }

                    showChildren(parentNode);
                } else {
                    String msg = "The file '"
                            + currentFile
                            + "' could not be deleted.";
                    showErrorMessage(msg, "Delete Failed");
                }
            } catch (Throwable t) {
                showThrowable(t);
            }
        }
        this.repaint();
    }

    private void newFile() {
        if (currentFile == null) {
            showErrorMessage("Nenhum local está selecionado para o novo arquivo.", "Selecione um Local");
            return;
        }

        if (newFilePanel == null) {
            newFilePanel = new JPanel(new BorderLayout(3, 3));

            JPanel southRadio = new JPanel(new GridLayout(1, 0, 2, 2));
            newTypeFile = new JRadioButton("File", true);
            JRadioButton newTypeDirectory = new JRadioButton("Directory");
            ButtonGroup bg = new ButtonGroup();
            bg.add(newTypeFile);
            bg.add(newTypeDirectory);
            southRadio.add(newTypeFile);
            southRadio.add(newTypeDirectory);

            name = new JTextField(15);

            newFilePanel.add(new JLabel("Name"), BorderLayout.WEST);
            newFilePanel.add(name);
            newFilePanel.add(southRadio, BorderLayout.SOUTH);

        }

        int result = JOptionPane.showConfirmDialog(
                this,
                newFilePanel,
                "Create File",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean created;
                File parentFile = currentFile;
                if (!parentFile.isDirectory()) {
                    parentFile = parentFile.getParentFile();
                }
                File file = new File(parentFile, name.getText());
                if (newTypeFile.isSelected()) {
                    created = file.createNewFile();
                } else {
                    created = file.mkdir();
                }
                if (created) {

                    TreePath parentPath = findTreePath(parentFile);
                    DefaultMutableTreeNode parentNode
                            = (DefaultMutableTreeNode) parentPath.getLastPathComponent();

                    if (file.isDirectory()) {
                        // add the new node..
                        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file);

                        TreePath currentPath = findTreePath(currentFile);
                        DefaultMutableTreeNode currentNode
                                = (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                        treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
                    }

                    showChildren(parentNode);
                } else {
                    String msg = "O ficheiro '"
                            + file
                            + "' não foi criado.";
                    showErrorMessage(msg, "Operação para criar ficheiro falhou");
                }
            } catch (Throwable t) {
                showThrowable(t);
            }
        }
        this.repaint();
    }

    private void showErrorMessage(String errorMessage, String errorTitle) {
//        JOptionPane.showMessageDialog(
//                this,
//                errorMessage,
//                errorTitle,
//                JOptionPane.ERROR_MESSAGE
//        );
        LogController.printLog("ALERT", errorTitle + ": " + errorMessage);
    }

    private void showThrowable(Throwable t) {
//        JOptionPane.showMessageDialog(
//                this,
//                t.toString(),
//                t.getMessage(),
//                JOptionPane.ERROR_MESSAGE
//        );
        LogController.printLog("ALERT", t.getMessage() + ": " + t.toString());
        this.repaint();
    }

    /**
     * Update the table on the EDT
     */
    private void setTableData(final File[] files) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (fileTableModel == null) {
                    fileTableModel = new FileTableModel();
                    table.setModel(fileTableModel);
                }
                table.getSelectionModel().removeListSelectionListener(listSelectionListener);
                fileTableModel.setFiles(files);
                table.getSelectionModel().addListSelectionListener(listSelectionListener);
                if (!cellSizesSet) {
                    Icon icon = fileSystemView.getSystemIcon(files[0]);

                    // size adjustment to better account for icons
                    table.setRowHeight(icon.getIconHeight() + rowIconPadding);

                    setColumnWidth(0, -1);
                    setColumnWidth(3, 60);
                    table.getColumnModel().getColumn(3).setMaxWidth(120);
                    setColumnWidth(4, -1);
                    setColumnWidth(5, -1);
                    setColumnWidth(6, -1);
                    setColumnWidth(7, -1);
                    setColumnWidth(8, -1);
                    setColumnWidth(9, -1);

                    cellSizesSet = true;
                }
            }
        });
    }

    private void setColumnWidth(int column, int width) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (width < 0) {
            // use the preferred width of the header..
            JLabel label = new JLabel((String) tableColumn.getHeaderValue());
            Dimension preferred = label.getPreferredSize();
            // altered 10->14 as per camickr comment.
            width = (int) preferred.getWidth() + 14;
        }
        tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
        tableColumn.setMinWidth(width);
    }

    /**
     * Add the files that are contained within the directory of this node.
     * Thanks to Hovercraft Full Of Eels.
     */
    private void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground() {
                File file = (File) node.getUserObject();
                if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true); //!!
                    if (node.isLeaf()) {
                        for (File child : files) {
                            if (child.isDirectory()) {
                                publish(child);
                            }
                        }
                    }
                    setTableData(files);
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

    /**
     * Update the File details view with the details of this File.
     */
    private void setFileDetails(File file) {
        currentFile = file;
        Icon icon = fileSystemView.getSystemIcon(file);
        fileName.setIcon(icon);
        fileName.setText(fileSystemView.getSystemDisplayName(file));
        path.setText(file.getPath());
        date.setText(new Date(file.lastModified()).toString());
        size.setText(file.length() + " bytes");
        readable.setSelected(file.canRead());
        writable.setSelected(file.canWrite());
        executable.setSelected(file.canExecute());
        isDirectory.setSelected(file.isDirectory());

        isFile.setSelected(file.isFile());

        JFrame f = (JFrame) this.getTopLevelAncestor();
        if (f != null) {
            f.setTitle(
                    GlobalConfig.APP_TITLE
                    + " :: "
                    + fileSystemView.getSystemDisplayName(file));
        }

        this.repaint();
    }

    private void verifyButtonsControl() {
        int index = table.getSelectedRow();
        if (index == -1) {//|| (boolean) table.getValueAt(index, 8) == false) {
            openFile.setEnabled(false);
        } else {
            openFile.setEnabled(true);
        }
    }

    private void initCustomGui() throws IOException {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        fileSystemView = FileSystemView.getFileSystemView();
        desktop = Desktop.getDesktop();

        JPanel detailView = new JPanel(new BorderLayout(3, 3));
        //fileTableModel = new FileTableModel();

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.setShowVerticalLines(false);

        listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int row = table.getSelectionModel().getLeadSelectionIndex();
                setFileDetails(((FileTableModel) table.getModel()).getFile(row));
                verifyButtonsControl();
            }
        };
        table.getSelectionModel().addListSelectionListener(listSelectionListener);
        JScrollPane tableScroll = new JScrollPane(table);
        Dimension d = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), (int) d.getHeight() / 2));
        detailView.add(tableScroll, BorderLayout.CENTER);

        // the File tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        treeModel = new DefaultTreeModel(root);

        TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent tse) {
                DefaultMutableTreeNode node
                        = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
                showChildren(node);
                setFileDetails((File) node.getUserObject());
            }
        };

        // show the file system roots.
        File[] roots;// = fileSystemView.getRoots();
        File userWorspace = new File(new File(ClientController.getInstance().getUserDataDir()).getAbsolutePath());
        roots = userWorspace.listFiles();//fileSystemView.getFiles(userWorspace, true);
        for (File fileSystemRoot : roots) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
            root.add(node);
            //showChildren(node);
            //
            File[] files = fileSystemRoot.listFiles();//fileSystemView.getFiles(fileSystemRoot, true);
            for (File file : files) {
                if (file.isDirectory()) {
                    node.add(new DefaultMutableTreeNode(file));
                }
            }
            //
        }

        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        tree.addTreeSelectionListener(treeSelectionListener);
        tree.setCellRenderer(new FileTreeCellRenderer());
        tree.expandRow(0);
        JScrollPane treeScroll = new JScrollPane(tree);

        // as per trashgod tip
        tree.setVisibleRowCount(15);

        Dimension preferredSize = treeScroll.getPreferredSize();
        Dimension widePreferred = new Dimension(
                200,
                (int) preferredSize.getHeight());
        treeScroll.setPreferredSize(widePreferred);

        treeScroll.setSize(500, 500);

        // details for a File
        JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
        fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));

        JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
        fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

        JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
        fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

        fileDetailsLabels.add(new JLabel("File", JLabel.TRAILING));
        fileName = new JLabel();
        fileDetailsValues.add(fileName);
        fileDetailsLabels.add(new JLabel("Path/name", JLabel.TRAILING));
        path = new JTextField(5);
        path.setEditable(false);
        fileDetailsValues.add(path);
        fileDetailsLabels.add(new JLabel("Last Modified", JLabel.TRAILING));
        date = new JLabel();
        fileDetailsValues.add(date);
        fileDetailsLabels.add(new JLabel("File size", JLabel.TRAILING));
        size = new JLabel();
        fileDetailsValues.add(size);
        fileDetailsLabels.add(new JLabel("Type", JLabel.TRAILING));

        JPanel flags = new JPanel(new FlowLayout(FlowLayout.LEADING, 4, 0));
        isDirectory = new JRadioButton("Directory");
        isDirectory.setEnabled(false);
        flags.add(isDirectory);

        isFile = new JRadioButton("File");
        isFile.setEnabled(false);
        flags.add(isFile);
        fileDetailsValues.add(flags);

        int count = fileDetailsLabels.getComponentCount();
        for (int ii = 0; ii < count; ii++) {
            fileDetailsLabels.getComponent(ii).setEnabled(false);
        }

        JToolBar toolBar = new JToolBar();
        // mnemonics stop working in a floated toolbar
        toolBar.setFloatable(false);

        openFile = new JButton("Open");
        openFile.setMnemonic('o');

        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    if ((boolean) table.getValueAt(table.getSelectedRow(), 8) == false) {
                        desktop.open(currentFile);
                    } else {
                        String directoryPath = table.getValueAt(table.getSelectedRow(), 2).toString();
                        TreePath treePath = findTreePath(new File(directoryPath));
                        tree.setSelectionPath(treePath);

                        DefaultMutableTreeNode node
                                = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                        showChildren(node);
                        setFileDetails((File) node.getUserObject());

                        setFileDetails((File) node.getUserObject());
                    }

                } catch (Throwable t) {
                    showThrowable(t);
                }
                repaint();
            }
        });
        toolBar.add(openFile);

        editFile = new JButton("Edit");
        editFile.setMnemonic('e');
        editFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    desktop.edit(currentFile);
                } catch (Throwable t) {
                    showThrowable(t);
                }
            }
        });
        toolBar.add(editFile);

        printFile = new JButton("Print");
        printFile.setMnemonic('p');
        printFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    desktop.print(currentFile);
                } catch (Throwable t) {
                    showThrowable(t);
                }
            }
        });
        toolBar.add(printFile);

        // Check the actions are supported on this platform!
        openFile.setEnabled(desktop.isSupported(Desktop.Action.OPEN));
        editFile.setEnabled(desktop.isSupported(Desktop.Action.EDIT));
        printFile.setEnabled(desktop.isSupported(Desktop.Action.PRINT));

        toolBar.addSeparator();

        newFile = new JButton("New");
        newFile.setMnemonic('n');
        newFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                newFile();
            }
        });
        toolBar.add(newFile);

        copyFile = new JButton("Copy");
        copyFile.setMnemonic('c');
        copyFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    copyFile();
                } catch (IOException ex) {
                    showErrorMessage("Ocorreu um erro ao Copiar", "Erro ao tentar copiar item.");
                }
            }
        });
        toolBar.add(copyFile);

        JButton renameFile = new JButton("Rename");
        renameFile.setMnemonic('r');
        renameFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                renameFile();
            }
        });
        toolBar.add(renameFile);

        deleteFile = new JButton("Delete");
        deleteFile.setMnemonic('d');
        deleteFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteFile();
            }
        });
        toolBar.add(deleteFile);

        toolBar.addSeparator();

        verifyButtonsControl();

        readable = new JCheckBox("Read  ");
        readable.setMnemonic('a');
        //readable.setEnabled(false);
        toolBar.add(readable);

        writable = new JCheckBox("Write  ");
        writable.setMnemonic('w');
        //writable.setEnabled(false);
        toolBar.add(writable);

        executable = new JCheckBox("Execute");
        executable.setMnemonic('x');
        //executable.setEnabled(false);
        toolBar.add(executable);

        JPanel fileView = new JPanel(new BorderLayout(3, 3));

        fileView.add(toolBar, BorderLayout.NORTH);
        fileView.add(fileMainDetails, BorderLayout.CENTER);

        detailView.add(fileView, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                treeScroll,
                detailView);
        this.add(splitPane, BorderLayout.BEFORE_LINE_BEGINS);

        JPanel simpleOutput = new JPanel(new BorderLayout(3, 3));
        progressBar = new JProgressBar();
        simpleOutput.add(progressBar, BorderLayout.EAST);
        progressBar.setVisible(false);

        this.add(simpleOutput, BorderLayout.SOUTH);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Used to open/edit/print files.
     */
    private Desktop desktop;
    /**
     * Provides nice icons and names for files.
     */
    private FileSystemView fileSystemView;

    /**
     * currently selected File.
     */
    private File currentFile;

    /**
     * File-system tree. Built Lazily
     */
    private JTree tree;
    private DefaultTreeModel treeModel;

    /**
     * Directory listing
     */
    private JTable table;
    private JProgressBar progressBar;
    /**
     * Table model for File[].
     */
    private FileTableModel fileTableModel;
    private ListSelectionListener listSelectionListener;
    private boolean cellSizesSet = false;
    private int rowIconPadding = 6;

    /* File controls. */
    private JButton openFile;
    private JButton printFile;
    private JButton editFile;
    private JButton deleteFile;
    private JButton newFile;
    private JButton copyFile;
    /* File details. */
    private JLabel fileName;
    private JTextField path;
    private JLabel date;
    private JLabel size;
    private JCheckBox readable;
    private JCheckBox writable;
    private JCheckBox executable;
    private JRadioButton isDirectory;
    private JRadioButton isFile;

    /* GUI options/containers for new File/Directory creation.  Created lazily. */
    private JPanel newFilePanel;
    private JRadioButton newTypeFile;
    private JTextField name;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

/**
 * A TableModel to hold File[].
 */
class FileTableModel extends AbstractTableModel {

    private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private String[] columns = {
        "Icon",
        "File",
        "Path/name",
        "Size",
        "Last Modified",
        "R",
        "W",
        "E",
        "D",
        "F",};

    FileTableModel() {
        this(new File[0]);
    }

    FileTableModel(File[] files) {
        this.files = files;
    }

    public Object getValueAt(int row, int column) {
        File file = files[row];
        switch (column) {
            case 0:
                return fileSystemView.getSystemIcon(file);
            case 1:
                return fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.getPath();
            case 3:
                return file.length();
            case 4:
                return file.lastModified();
            case 5:
                return file.canRead();
            case 6:
                return file.canWrite();
            case 7:
                return file.canExecute();
            case 8:
                return file.isDirectory();
            case 9:
                return file.isFile();
            default:
                System.err.println("Logic Error");
        }
        return "";
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 3:
                return Long.class;
            case 4:
                return Date.class;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return Boolean.class;
        }
        return String.class;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return files.length;
    }

    public File getFile(int row) {
        return files[row];
    }

    public void setFiles(File[] files) {
        this.files = files;
        fireTableDataChanged();
    }
}

/**
 * A TreeCellRenderer for a File.
 */
class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    private FileSystemView fileSystemView;

    private JLabel label;

    FileTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        File file = (File) node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}
