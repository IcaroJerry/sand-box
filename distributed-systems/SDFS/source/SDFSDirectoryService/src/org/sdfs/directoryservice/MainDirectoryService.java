/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.directoryservice;

import org.sdfs.directoryservice.controller.DirectoryServiceController;
import org.sdfs.directoryservice.controller.LogController;

/**
 *
 * @author icarojerry
 */
public class MainDirectoryService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                
        //<editor-fold defaultstate="collapsed" desc="Tratando Argumentos caso existam">
        LogController.showWindowLog = LogController.showConsoleLog = true;
        String DS_ADDR = "localhost";
        int DS_PORT = 5000;
        
        if (args.length == 2 || args.length == 3) {
            try{
                DS_ADDR = args[0];
                DS_PORT = Integer.parseInt(args[1]);

                if(args.length == 3)
                    LogController.showConsoleLog = LogController.showWindowLog = Boolean.parseBoolean(args[2]);
            }catch(Exception e) {
                System.err.println("Erro de Sintaxe: <DirectoryServiceAddr> <DirectoryServiceAddrPort> [debug]");
                return;
            }
        }
        else if(args.length != 0 ){ 
            System.err.println("Erro de Sintaxe: <DirectoryServiceAddr> <DirectoryServiceAddrPort> [debug]");
            return;
        }
        //</editor-fold>
        
        LogController.printLog("INFO", "Inicializando o Serviço de Diretória...");
        DirectoryServiceController.getInstance().initialize(DS_ADDR, DS_PORT);
        LogController.printLog("INFO", "Serviço de Diretória ativo!");   
    }
    
}
