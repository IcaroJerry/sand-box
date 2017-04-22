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
public class IServer implements Serializable {
    public IServer(String serverAlias, String serverAddr) {
        this.serverAlias = serverAlias;
        this.serverAddr = serverAddr;
        this.port = 0;
    }

    public IServer(String serverAlias, String serverAddr, String serverDataDir) {
        this.serverAlias = serverAlias;
        this.serverAddr = serverAddr;
        this.port = 0;
        this.serverDataDir = serverDataDir;
    }
    
    public IServer(String serverAlias, String serverAddr, int serverPort, String serverDataDir) {
        this.serverAlias = serverAlias;
        this.serverAddr = serverAddr;
        this.port = serverPort;
        this.serverDataDir = serverDataDir;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getServerDataDir() {
        return serverDataDir;
    }

    public void setServerDataDir(String serverDataDir) {
        this.serverDataDir = serverDataDir;
    }

    public String getServerAlias() {
        return serverAlias;
    }

    public void setServerAlias(String serverAlias) {
        this.serverAlias = serverAlias;
    }
    
    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public List<IUser> getUsers() {
        if(connectedUsers == null)
            connectedUsers =  new ArrayList<>();

        return connectedUsers;
    }

    public void addConnectedUsers(IUser u) {
        if(connectedUsers == null)
            connectedUsers =  new ArrayList<>();
        
        connectedUsers.add(u);
    }
    //</editor-fold> 

    private List<IUser> connectedUsers;
    private String serverDataDir;
    private String serverAlias;
    private String serverAddr;
    private int port;
}
