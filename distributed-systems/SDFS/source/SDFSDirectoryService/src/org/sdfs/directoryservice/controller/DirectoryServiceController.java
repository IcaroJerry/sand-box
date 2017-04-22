/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.directoryservice.controller;

import java.util.ArrayList;
import java.util.List;
import org.sdfs.directoryservice.thread.ThreadToRequests;

import org.sdfs.interfaces.model.AvailableServers;
import org.sdfs.interfaces.model.IDirectoryService;
import org.sdfs.interfaces.model.IServer;
import org.sdfs.interfaces.model.IUser;

/**
 *
 * @author icarojerry
 */
public class DirectoryServiceController extends IDirectoryService {

    public void initialize(String addr, int port) {
        this.setAddr(addr);
        this.setPort(port);

        Thread t = new Thread(ThreadToRequests.getInstance());
        t.start();

        UiController.getInstance().show();
    }

    public synchronized boolean addAvailableServer(IServer s) {

        availabeServers.getServers().put(s.getServerAlias(), s);
        ThreadToRequests.getInstance().noticeUsers();
        UiController.getInstance().refreshAll();
        
        return true;
    }

    public synchronized boolean removeServer(IServer s) {

        availabeServers.getServers().remove(s.getServerAlias());
        ThreadToRequests.getInstance().noticeUsers();
        UiController.getInstance().refreshAll();
        
        return true;
    }
    
    public synchronized void addConnectedUsers(IUser u) {
        connectedUsers.add(u);
        UiController.getInstance().refreshAll();
    }
    
    public synchronized List<IUser> getConnectedUsers() {
        return connectedUsers;
    }
    
    public synchronized AvailableServers getAvailableServers() {
        return  availabeServers;
    }
    
    public synchronized boolean existsServer(IServer s) {
        
        return  availabeServers.getServers().get(s.getServerAlias()) != null;
    }

    public static DirectoryServiceController getInstance() {
        if (instance == null) {
            instance = new DirectoryServiceController();
        }

        return instance;
    }

    private DirectoryServiceController() {
        availabeServers = new AvailableServers();
        connectedUsers = new ArrayList<>();
    }

    private AvailableServers availabeServers;
    private static DirectoryServiceController instance;

    
}
