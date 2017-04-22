/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.observer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.sdfs.interfaces.rmi.IGetServersObserver;

/**
 *
 * @author icarojerry
 */
public class GetServersObserver  extends UnicastRemoteObject implements IGetServersObserver{
    
    public GetServersObserver() throws RemoteException {}
     
    @Override
    public void notifyServerListChanged(String description) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
