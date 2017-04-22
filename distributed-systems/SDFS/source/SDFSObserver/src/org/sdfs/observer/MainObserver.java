/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.observer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.sdfs.interfaces.rmi.IGetServersService;

/**
 *
 * @author icarojerry
 */
public class MainObserver {

    public static void main(String[] args) {
        try {

            //Cria e lanca o servico 
            GetServersObserver observer = new GetServersObserver();
            System.out.println("Servico SDFSObserver criado e em execucao...");

            //Localiza o servico remoto nomeado "GetRemoteFile"
            String objectUrl = "rmi://127.0.0.1/GetRemoteFile"; //rmiregistry on localhost

            if (args.length > 0) {
                objectUrl = "rmi://" + args[0] + "/GetRemoteFile";
            }

            IGetServersService observerService = (IGetServersService) Naming.lookup(objectUrl);

            //adiciona observador no servico remoto
            observerService.addObserver(observer);

            System.out.println("<Enter> para terminar...");
            System.out.println();
            System.in.read();

            observerService.removeObserver(observer);
            UnicastRemoteObject.unexportObject(observer, true);

        } catch (RemoteException e) {
            System.out.println("Erro remoto - " + e);
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Erro - " + e);
            System.exit(1);
        }
    }
}
