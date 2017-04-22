/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author icarojerry
 */
public class IDirectoryService implements Serializable {
    
    public IDirectoryService() {

    }
    
    public IDirectoryService(String serviceAddr, int servicePort) {
        this.addr = serviceAddr;
        this.port = servicePort;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public String getAddr() {
        return addr;
    }

    public void setAddr(String serverAddr) {
        this.addr = serverAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    //</editor-fold> 
    protected List<IUser> connectedUsers;
    protected String addr;
    protected int port;
}
