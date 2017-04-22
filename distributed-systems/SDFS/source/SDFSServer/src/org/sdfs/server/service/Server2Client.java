package org.sdfs.server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.sdfs.server.threads.ClientThread;
import org.sdfs.server.controller.LogController;
import org.sdfs.server.controller.ServerController;

public class Server2Client implements Runnable {

    public Server2Client() {
        clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(0);
            serverPort = serverSocket.getLocalPort();
            
        } catch (IOException ex) {
            System.err.println("Ocorreu durante a inicialização do Socket para o Cliente.");
        }
    }

    @Override
    public void run() {
        if (!running) {
            try {
                running = true;
               LogController.printLog("INFO", "Servidor TCP ligado na porta:" + serverSocket.getLocalPort());
                while (true) {
                    addClient(new ClientThread(serverSocket.accept()));
                    LogController.printLog("INFO", "Requisição recebida");
                }
            } catch (UnknownHostException ex) {
                LogController.printLog("ERROR", ex.getMessage());
            } catch (IOException ex) {
                LogController.printLog("ERROR", ex.getMessage());
            }
        } else {
            LogController.printLog("INFO", "Servidor TCP já está executando na porta:" + serverSocket.getLocalPort());
        }
    }

    public synchronized void addClient(ClientThread cRef) {
        if (!clients.contains(cRef)) {
            clients.add(cRef);
            LogController.printLog("INFO", "Cliente: "+ cRef.getClientID()+" foi adicionado a lista.");
        }
        else
            LogController.printLog("INFO", "Cliente já está conectado!");
    }

    public synchronized void removeClient(ClientThread cRef) {
        if (clients.remove(cRef)) {
            LogController.printLog("", "Cliente: "+ cRef.getClientID() + " foi removido da lista.");
        }
        else
            LogController.printLog("INFO", "Cliente não pode ser removido!");
    }

    private ServerSocket serverSocket = null;
    private List<ClientThread> clients;
    private boolean running;
    public int serverPort;
}
