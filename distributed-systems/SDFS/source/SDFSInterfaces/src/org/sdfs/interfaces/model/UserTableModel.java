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
public class UserTableModel extends AbstractTableModel {

    private IUser[] users;
    private String[] columns = {"Users",};

    public UserTableModel() {
        this(new IUser[0]);
    }

    public UserTableModel(IUser[] users) {
        this.users = users;
    }

    public Object getValueAt(int row, int column) {
        IUser user = users[row];
        switch (column) {
            case 0:
                return user.getUserName();
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
        return users.length;
    }

    public IUser getServer(int row) {
        return users[row];
    }

    public void setUsers(IUser[] users) {
        this.users = users;
        fireTableDataChanged();
    }
}
