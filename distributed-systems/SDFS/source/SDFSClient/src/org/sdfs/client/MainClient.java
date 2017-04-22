/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.client;

import org.sdfs.client.controller.ClientController;
import org.sdfs.client.controller.LogController;

/**
 *
 * @author icarojerry
 */
public class MainClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Valores default
        String serviceAddress = "localhost";
        int servicePort = 5000;
        String dirWorkspace = "workspace";
        LogController.showWindowLog = LogController.showConsoleLog = true;
        
        //<editor-fold defaultstate="collapsed" desc="Tratando Argumentos caso existam">
        if (args.length >= 3 && args.length <= 4) {
            try {
                serviceAddress = args[0];
                servicePort = Integer.parseInt(args[1]);
                dirWorkspace = args[2];
                
                if (args.length == 4) {
                    LogController.showWindowLog = LogController.showConsoleLog = Boolean.parseBoolean(args[3]);
                }
            } catch (Exception ex) {
                System.err.println("Sintaxe: MainClient <ServiceAddress> <ServicePort> <DirWorkspace> [debug]");
            }
        } else if (args.length != 0) {
            System.err.println("Sintaxe: MainClient <ServiceAddress> <ServicePort> <DirWorkspace> [debug]");
            return;
        } else {

        }
        //</editor-fold>

        ClientController.getInstance().initialize(serviceAddress, servicePort, dirWorkspace);
    }
}
