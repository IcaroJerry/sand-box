/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import org.sdfs.interfaces.model.IServer;

/**
 *
 * @author icarojerry
 */
public interface IGetServersService extends Remote {

    //example
    //public List<IServer> getFile(IGetServersClient cliRef) throws RemoteException;
    public List<IServer> getAvailableServers(IGetServersClient cliRef) throws RemoteException;

    public void addObserver(IGetServersObserver observer) throws RemoteException;

    public void removeObserver(IGetServersObserver observer) throws RemoteException;
}
