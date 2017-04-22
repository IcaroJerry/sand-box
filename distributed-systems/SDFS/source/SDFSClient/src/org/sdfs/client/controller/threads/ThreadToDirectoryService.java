/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.client.controller.threads;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.sdfs.client.controller.ClientController;
import org.sdfs.client.controller.LogController;
import org.sdfs.interfaces.GlobalConfig;
import org.sdfs.interfaces.model.AvailableServers;


/**
 *
 * @author icarojerry
 */
public class ThreadToDirectoryService implements Runnable{

    public ThreadToDirectoryService(DatagramSocket socket) {
        this.udpSocket = socket;
    }
    
    @Override
    public void run() {
        while(true)
            listenToServiceDirectory();
    }
    
     public void listenToServiceDirectory() {
        DatagramPacket packetReceiver;
        ObjectInputStream ois;
        Object objReceived;

        try {
            //Thread.sleep(5000);//5s
            udpSocket.setSoTimeout(0);
            //Recebendo atualização da Lista de Servidores Disponíveis
            packetReceiver = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
            this.udpSocket.receive(packetReceiver);

            LogController.printLog("INFO", "Atualização da lista de Servidores recebida do Serviço de Diretória.");

            ois = new ObjectInputStream(new ByteArrayInputStream(
                                        packetReceiver.getData(), 0, packetReceiver.getLength()));
            objReceived = ois.readObject();
            ois.close();

            //Se a mensagem Recebida não for instancia a lista
            if(!(objReceived instanceof AvailableServers)) {
                throw new Exception("Mensagem recebida é inválida!");
            }

            AvailableServers aServers = (AvailableServers) objReceived;
            ClientController.getInstance().changeAvailableServers(aServers);
            LogController.printLog("INFO", "Lista de Servidores disponíveis foi atualizada.");
        } catch (UnknownHostException ex) {
           LogController.printLog("ERROR","Endereço do Serviço de Diretória é inválido ou desconhecido.");
        } catch (SocketException ex) {
           LogController.printLog("ERROR","Um erro ocorreu ao configurar conexão com o Serviço de Diretória..");
        } catch (IOException ex) {
           LogController.printLog("ERROR","Um erro ocorreu ao tentar conexão com o Serviço de Diretória."+ ex.getMessage());
        } catch (ClassNotFoundException ex) {
           LogController.printLog("ERROR", "Um erro ocorreu ao receber mensagem do Serviço de Diretoria.");
        } catch (Exception ex) {
           LogController.printLog("ERROR", ex.getMessage());
        }
    }

     private DatagramSocket udpSocket;
}
