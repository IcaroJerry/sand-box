/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces.model;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author icarojerry
 */
public class IUser implements Serializable {
    
//    abstract public void login(String username, String password); //throws Exceptions
//    abstract public void logout(); //throws Exceptions
//    abstract public void registryRequest(String username, String password); //throws Exceptions
//    abstract public void sendFile();
//    abstract public void downloadFile();
//    abstract public void listAll(); //File and Directories
//    abstract public void listFile(); //
    //public void actionFile(String action, String[] args); //
   //FileOperations
    ///List; Send; Download; Rename; Delete; Move; Copy; Open; Write; Read; See Details
    public IUser(String name, String userName, String password){
        this.name = name;
        this.userName = userName;
        this.password =  password;
    }

    public IUser(String userName, InetAddress addr, int port){
        this.userName = userName;
        this.addr = addr;
        this.port = port;
    }

    public IUser(String name, String userName, String password, InetAddress addr, int port){
        this.name = name;
        this.userName = userName;
        this.password =  password;
        this.addr = addr;
        this.port = port;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters"> 
    
    public InetAddress getAddr() {
        return addr;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String nickname) {
        this.userName = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //</editor-fold>
    
    private String userName;
    private String name;
    private String password;
    private InetAddress addr;
    private int port;
}
