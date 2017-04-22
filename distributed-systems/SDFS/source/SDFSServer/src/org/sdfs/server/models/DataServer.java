/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.server.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import org.sdfs.interfaces.GlobalConfig;
import org.sdfs.interfaces.model.IServer;
import org.sdfs.interfaces.model.IUser;
import org.sdfs.interfaces.sdfsexceptions.ServerActionException;
import org.sdfs.server.controller.LogController;
import org.sdfs.server.controller.ServerController;

/**
 *
 * @author icarojerry
 */
public class DataServer implements Serializable{
    
    public DataServer(IServer server, int servicePort, String serviceAddr) {
        this.server = server;
        this.serviceAddr = serviceAddr;
        this.servicePort = servicePort;
        this.registredUsers = new HashMap<>();
    }

    public void initServer() throws ServerActionException{
        try {
            connectToDirectoryService();
        } catch (Exception ex) {
            throw new ServerActionException("Ocorreu um erro ao inicializar o Servidor.");
        }
    }
    
    public void connectToDirectoryService() {
        if (!ServerController.getInstance().isConnected2DS()) {
            DatagramSocket udpSocketLocal;
            DatagramPacket packet;
            DatagramPacket packetReceiver;
            InetAddress dsAddr;
            ByteArrayOutputStream bout;
            ObjectOutputStream ous;
            ObjectInputStream ois;
            Object objReceived;

            try {
                udpSocketLocal = new DatagramSocket();
                udpSocketLocal.setSoTimeout(GlobalConfig.TIME_OUT);
                bout =  new ByteArrayOutputStream();
                ous = new ObjectOutputStream(bout);
                ous.writeObject(this.server);
                ous.flush();
                ous.close();

                dsAddr = InetAddress.getByName(this.serviceAddr);
                packet = new DatagramPacket(bout.toByteArray(), bout.size());
                packet.setAddress(dsAddr);
                packet.setPort(this.servicePort);

                //Enviando mensagem CONNECT
                udpSocketLocal.send(packet);
                LogController.printLog("", "Solicitação para efetuar Conexão enviada ao Serviço de Diretória...");
                
                byte[] buff = new byte[GlobalConfig.BUFFER_SIZE];
                packet.setData(buff);
                packet.setLength(buff.length);
                
                int tryCount = 0;
                while(tryCount != MAX_TRY) {
                    try {
                        udpSocketLocal.receive(packet);
                        tryCount = MAX_TRY;
                    } catch (IOException ex) {
                        tryCount++;
                        if(tryCount == MAX_TRY){
                            LogController.printLog("ALERT", "Nenhum Serviço de Diretória disponível no endereço "
                                    + getServiceAddr()+ "/" + getServicePort());
                            return;
                        }
                    }
                }

                String response = new String(packet.getData(),packet.getData().length);
                if(response.contains(GlobalConfig.MSG_CONFIRM)){
                    ServerController.getInstance().setServerConnected2DS(true);
                }
                else {
                    LogController.printLog("ALERT", response);
                }
                
            }catch (IOException ex) {
                LogController.printLog("ERROR", ex.getMessage());
            }
        }
        else {
            LogController.printLog("ALERT", "O Servidor já está conectado a um Serviço de Diretória!");
        }
    }

    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters"> 
    public String getServerAlias() {
        return server.getServerAlias();
    }

    public void setServerAlias(String serverAlias) {
        this.server.setServerAlias(serverAlias);
    }
    public String getServerAddr() {
        return server.getServerAddr();
    }
    public int getServerPort() {
        return this.server.getPort();
    }
    public void setServerPort(int port) {
        this.server.setPort(port);
    }
    public String getServerDataDir() {
        return server.getServerDataDir();
    }
    public void setServerDataDir(String serverDataDir) {
        this.server.setServerDataDir(serverDataDir);
    }
    public int getServicePort() {
        return servicePort;
    }
    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }
    public String getServiceAddr() {
        return serviceAddr;
    }
    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    //</editor-fold> 
    
    private int servicePort;
    private String serviceAddr;
    private IServer server;
    private DatagramSocket udpSocket;
    private HashMap<String, IUser> registredUsers;
    private int MAX_TRY = 3;

}
