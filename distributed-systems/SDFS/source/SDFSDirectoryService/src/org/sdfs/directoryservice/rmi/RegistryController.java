/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.directoryservice.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.sdfs.directoryservice.controller.LogController;

/**
 *
 * @author icarojerry
 */
public class RegistryController {

    public boolean registryService(Remote service, String serviceName) {
        try {
            /*
            * Regista o servico no rmiregistry local para que os clientes possam localiza'-lo, ou seja,
            * obter a sua referencia remota (endereco IP, porto de escuta, etc.).
             */

            registryService.bind(serviceName, service);
            LogController.printLog("INFO", "Servico " + serviceName + " registado no registry...");
            return true;
        } catch (RemoteException | AlreadyBoundException ex) {
            LogController.printLog("ERROR", "Ocorreu um erro ao tentar registrar o Serviço: " + serviceName);
        }
        return false;
    }

    public void finishService(Remote service) throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(service, true);
    }

    public static RegistryController getInstance() {
        if (instance == null) {
            instance = new RegistryController();
        }
        return instance;
    }

    private RegistryController() {
        /*
            * Lanca o rmiregistry localmente no porto TCP por omissao (1099) ou, caso este já se encontre
            * a correr, obtem uma referencia.
         */
        try {
            try {
                registryService = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                LogController.printLog("INFO", "Registry lancado no porto: " + Registry.REGISTRY_PORT + "...");
            } catch (RemoteException ex) {
                LogController.printLog("INFO", "Registry provavelmente já em execução...");
                registryService = LocateRegistry.getRegistry();
                LogController.printLog("INFO", "Referência para o Registry obtida com sucesso.");
            }
        } catch (RemoteException ex) {
            LogController.printLog("ERROR", "Ocorreu um erro tentar inicializar o Registry: " + ex.getMessage());
            System.exit(1);
        }
    }

    private Registry registryService;
    private static RegistryController instance;
}
