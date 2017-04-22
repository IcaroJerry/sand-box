/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author icarojerry
 */
public class ConnectedUsers implements Serializable{

    public ConnectedUsers() {
        users = new ArrayList<>();
    }
    
    public boolean add(IUser u) {
        return users.add(u);
    }
    
    public List<IUser> getUsers() {
        return  users;
    }
    
    private List<IUser> users;
    static final long serialVersionUID = 101L;
}
