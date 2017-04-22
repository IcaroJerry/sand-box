/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author icarojerry
 */
public interface IGetServersObserver extends Remote{
    
    public void notifyServerListChanged(String description) throws RemoteException; 
}
