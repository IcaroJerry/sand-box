/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces.model;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author icarojerry
 */
public class ServerTableModel extends AbstractTableModel {

    private IServer[] servers;
    private String[] columns = {"Servers",};

    public ServerTableModel() {
        this(new IServer[0]);
    }

    public ServerTableModel(IServer[] servers) {
        this.servers = servers;
    }
    
    public IServer getServerByRow(int row) {
        return  this.servers[row];
    }
    
    public int getRowByServer(IServer s) {
        for(int i = 0; i < this.servers.length; ++i)
            if(this.servers[i].getServerAlias() == s.getServerAlias())
                return i;
        return  -1;
    }
    
    public Object getValueAt(int row, int column) {
        IServer server = servers[row];
        switch (column) {
            case 0:
                return server.getServerAlias();
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
                return String.class;
            default:
                return Object.class;
        }
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return servers.length;
    }

    public IServer getServer(int row) {
        return servers[row];
    }

    public void setServers(IServer[] servers) {
        this.servers = servers;
        fireTableDataChanged();
    }
}
