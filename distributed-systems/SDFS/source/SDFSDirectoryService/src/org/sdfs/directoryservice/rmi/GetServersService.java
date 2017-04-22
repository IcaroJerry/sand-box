/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.directoryservice.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.sdfs.directoryservice.controller.DirectoryServiceController;
import org.sdfs.interfaces.model.IServer;
import org.sdfs.interfaces.rmi.IGetServersClient;
import org.sdfs.interfaces.rmi.IGetServersObserver;
import org.sdfs.interfaces.rmi.IGetServersService;

/**
 *
 * @author icarojerry
 */
public class GetServersService  extends UnicastRemoteObject implements IGetServersService{
    
    @Override
    public List<IServer> getAvailableServers(IGetServersClient cliRef) throws RemoteException {
        return DirectoryServiceController.getInstance().getAvailableServers().getAllServers();
    }

    @Override
    public synchronized void addObserver(IGetServersObserver observer) throws RemoteException {
        if(!observers.contains(observer)){
            observers.add(observer);
            System.out.println("+ um observador.");
        }
    }

    @Override
    public synchronized void removeObserver(IGetServersObserver observer) throws RemoteException {
         if(observers.remove(observer))
            System.out.println("- um observador.");
    }
    
    public synchronized void notifyObservers(String msg)
    {
        int i;
        
        for(i = 0; i < observers.size(); i++){
            try{       
                observers.get(i).notifyServerListChanged(msg);
            }catch(RemoteException e){
                observers.remove(i--);
                System.out.println("- um observador (observador inacessivel).");
            }
        }
    }
    
    public static GetServersService getInstance() throws RemoteException {
        if (instance == null) {
            instance = new GetServersService();
        }
        return instance;
    }
    
    private GetServersService() throws RemoteException {}
    private List<IGetServersObserver> observers;
    private static GetServersService instance;
}
