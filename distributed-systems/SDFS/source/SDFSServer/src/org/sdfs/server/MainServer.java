/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.server;

import org.sdfs.server.controller.ServerController;
import org.sdfs.server.controller.LogController;

/**
 *
 * @author icarojerry
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //<editor-fold defaultstate="collapsed" desc="Tratando Argumentos caso existam">
        LogController.showWindowLog = LogController.showConsoleLog = true;
        String serverAlias = "Server-Default";
        String serviceAddr = "localhost";
        int servicePort = 5000;
        String dirDataServer = "data-server";

        if (args.length == 4 || args.length == 5) {
            try {
                serverAlias = args[0];
                serviceAddr = args[1];
                servicePort = Integer.parseInt(args[2]);
                dirDataServer = args[3];

                if (args.length == 5) {
                    LogController.showConsoleLog = LogController.showWindowLog = Boolean.parseBoolean(args[4]);
                }
            } catch (Exception e) {
                System.err.println("Erro de Sintaxe: MainServer <ServerAlias> <ServiceAddr> <ServicePort> <DirDataServer> [debug]");
                return;
            }
        } else if (args.length != 0) {
            System.err.println("Erro de Sintaxe: MainServer <ServerAlias> <ServiceAddr> <ServicePort> <DirDataServer> [debug]");
            return;
        }
        //</editor-fold>

        ServerController.getInstance().initialize(serverAlias, serviceAddr, servicePort, dirDataServer);



//            try {
//                UserController.getInstance().loadRegistredUser();
//            } catch (IOException ex) {
//                System.err.println("Erro ao carregar Usuários Cadastrados.");
//            }
        //        HashMap<String, Method> userMethods = new HashMap<>();
        //        for(Method m: IUserController.class.getDeclaredMethods()){
        //            userMethods.put(m.getName().toUpperCase(), m);
        //        }
//            try {
//                ServerSocket socket = new ServerSocket(ServerController.SERVER_PORT);
//                Socket toClientSocket = socket.accept();
//
//                ObjectInputStream in = new ObjectInputStream(toClientSocket.getInputStream());
//                IMetaMethodInvoker mmi = (IMetaMethodInvoker) in.readObject();
//                System.out.println(mmi.getMethodName());
//
//            } catch (Exception ex) {
//                System.err.println(ex.getMessage());
//            }
    }
}
