/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.directoryservice.thread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.sdfs.directoryservice.controller.DirectoryServiceController;
import org.sdfs.directoryservice.controller.LogController;
import org.sdfs.interfaces.GlobalConfig;
import org.sdfs.interfaces.model.IServer;
import org.sdfs.interfaces.model.IUser;

/**
 *
 * @author icarojerry
 */
public class ThreadToRequests implements Runnable {

    @Override
    public void run() {
        while (true) {
            listenRequests();
        }
    }

    public void listenRequests() {
        DatagramPacket packet;
        InetAddress requesterAddr;
        String messageReceived;
        ObjectInputStream ois;
        Object objReceived;

        try {
            LogController.printLog("INFO", "Aguardando requisições...");
            //Recebendo mensagem CONNECT
            packet = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
            this.udpSocket.receive(packet);
            requesterAddr = packet.getAddress();
            LogController.printLog("INFO", "Requisição recebida de :" + requesterAddr.toString());

            ois = new ObjectInputStream(new ByteArrayInputStream(
                    packet.getData(), 0, packet.getData().length));
            objReceived = ois.readObject();
            ois.close();

            if (objReceived instanceof IServer) {
                IServer s = (IServer) objReceived;
                if (!DirectoryServiceController.getInstance().existsServer(s)) {
                    DirectoryServiceController.getInstance().addAvailableServer(s);
                    packet.setData(GlobalConfig.MSG_CONFIRM.getBytes());
                    packet.setLength(GlobalConfig.MSG_CONFIRM.getBytes().length);
                } else {
                    String response = "O Serviço de Diretoria de ja possui um Servidor registrado com o nome: "
                            + s.getServerAlias() + "!";
                    packet.setData(response.getBytes());
                    packet.setLength(response.getBytes().length);
                }
                this.udpSocket.send(packet);
            }
            
            else if (objReceived instanceof String) {
                messageReceived = (String) objReceived;

                if (messageReceived.contains(GlobalConfig.MSG_CONNECT)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(DirectoryServiceController.getInstance().getAvailableServers());
                    oos.flush(); // garantir que os bytes são escritos no buff; ("esvaziar")
                    oos.close();
                    
                    //enviando lista de Servidores
                    packet.setData(baos.toByteArray());
                    packet.setLength(baos.toByteArray().length);
                    //packetSender.setAddress(requesterAddr);
                     this.udpSocket.send(packet);
                     
                    packet = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
                    this.udpSocket.receive(packet);
                    messageReceived = new String(packet.getData());
                    if (messageReceived.contains(GlobalConfig.MSG_CONFIRM)) {
                        baos = new ByteArrayOutputStream();
                        oos = new ObjectOutputStream(baos);
                        oos.writeObject(DirectoryServiceController.getInstance().getConnectedUsers());
                        oos.flush(); // garantir que os bytes são escritos no buff; ("esvaziar")
                        oos.close();

                        //enviando lista de Usuários Conectados
                        packet.setData(baos.toByteArray());
                        packet.setLength(baos.toByteArray().length);
                        //packetSender.setAddress(requesterAddr);
                        this.udpSocket.send(packet);

                        packet = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
                        this.udpSocket.receive(packet);
                        messageReceived = new String(packet.getData());

                        if (messageReceived.contains(GlobalConfig.MSG_CONFIRM)) {
                            LogController.printLog("INFO", "Menssagem de Confirmação do Usuário Recebida. Adicionando novo Usuário Conectado.");
                            String userID = "" + (DirectoryServiceController.getInstance().getConnectedUsers().size() + 1);
                            DirectoryServiceController.getInstance().addConnectedUsers(new IUser(userID, packet.getAddress(), packet.getPort()));

                        }
                        else
                            LogController.printLog("ERROR", "Requisição inválida!");
                    }
                    else
                        LogController.printLog("ERROR", "Requisição inválida!");
                }
            }
            else
                LogController.printLog("ERROR", "Requisição inválida!");
        } catch (UnknownHostException ex) {
            LogController.printLog("ERROR", ex.getMessage());
        } catch (SocketException | ClassNotFoundException ex) {
            LogController.printLog("ERROR", ex.getMessage());
        } catch (IOException ex) {
            LogController.printLog("ERROR", ex.getMessage());
        }
    }

//
//    public void listenRequests() {
//        DatagramPacket packet;
//        InetAddress requesterAddr;
//        String messageReceived;
//
//        try {
//            LogController.printLog("INFO", "Aguardando requisições de usuários");
//            //Recebendo mensagem CONNECT
//            packet = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
//            this.udpSocket.receive(packet);
//            requesterAddr = packet.getAddress();
//            LogController.printLog("INFO", "Requisição recebida de :" + requesterAddr.toString());
//
//            messageReceived = new String(packet.getData());
//
//            if (messageReceived.contains(GlobalConfig.MSG_CONNECT)) {
//                ByteArrayOutputStream baosListServers = new ByteArrayOutputStream();
//                ObjectOutputStream oos = new ObjectOutputStream(baosListServers);
//                oos.writeObject(DirectoryServiceController.getInstance().avilabeServers);
//                oos.flush(); // garantir que os bytes são escritos no buff; ("esvaziar")
//                oos.close();
//
//                //enviando lista de Servidores
//                packet.setData(baosListServers.toByteArray());
//                packet.setLength(baosListServers.size());
//                //packetSender.setAddress(requesterAddr);
//                this.udpSocket.send(packet);
//
//                packet = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
//                this.udpSocket.receive(packet);
//                messageReceived = new String(packet.getData());
//
//                if(messageReceived.contains(GlobalConfig.MSG_CONFIRM)) {
//                    LogController.printLog("INFO", "Menssagem de Confirmação do Usuário Recebida. Adicionando novo Usuário Conectado.");
//                    String userID = ""+ DirectoryServiceController.getInstance().usersConnected.size() + 1;
//                    DirectoryServiceController.getInstance().usersConnected.put(userID, new IUser(userID, packet.getAddress(), packet.getPort()));
//                }
//            
//            }
//
//        } catch (UnknownHostException ex) {
//            LogController.printLog("ERROR", ex.getMessage());
//        } catch (SocketException ex) {
//            LogController.printLog("ERROR", ex.getMessage());
//        } catch (IOException ex) {
//            LogController.printLog("ERROR", ex.getMessage());
//        } catch (Exception ex) {
//            LogController.printLog("ERROR", ex.getMessage());
//        }
//    }
    public void noticeUsers() {
        for (IUser u : DirectoryServiceController.getInstance().getConnectedUsers()) {
            sendAvailableServersToUser(u);
        }
    }
//
//    public void createAndListenSocket() {
//        try {
//            DatagramSocket socket = new DatagramSocket(DirectoryServiceController.DIRECTORY_SERVICE_PORT);
//            byte[] buffer = new byte[GlobalConfig.BUFFER_SIZE];
//            DatagramPacket packetReceiver;
//            ByteArrayInputStream bais;
//            ObjectInputStream ois;
//
//            while (true) {
//                packetReceiver = new DatagramPacket(buffer, buffer.length);
//                socket.receive(packetReceiver);
//                byte[] dataReceived = packetReceiver.getData();
//                bais = new ByteArrayInputStream(dataReceived);
//                ois = new ObjectInputStream(bais);
//
//                try {
//                    AvailableServers aServers = (AvailableServers) ois.readObject();
//                    System.out.println("Student object received = " + aServers);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                InetAddress IPAddress = packetReceiver.getAddress();
//                int port = packetReceiver.getPort();
//                String reply = "Thank you for the message";
//                byte[] replyBytea = reply.getBytes();
//                DatagramPacket replyPacket
//                        = new DatagramPacket(replyBytea, replyBytea.length, IPAddress, port);
//                socket.send(replyPacket);
//                Thread.sleep(2000);
//                System.exit(0);
//            }
//
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (IOException i) {
//            i.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//

    public void sendAvailableServersToUser(IUser u) {
        DatagramPacket packet;
        boolean error = false;
        try {
            LogController.printLog("INFO", "Enviando nova lista de Servidores para os Usuários...");
            ByteArrayOutputStream baosListServers = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baosListServers);
            oos.writeObject(DirectoryServiceController.getInstance().getAvailableServers());
            oos.flush(); // garantir que os bytes são escritos no buff; ("esvaziar")
            oos.close();

            //enviando lista de Servidores
            packet = new DatagramPacket(baosListServers.toByteArray(), baosListServers.size());
            packet.setAddress(u.getAddr());
            packet.setPort(u.getPort());

            this.udpSocket.send(packet);
            LogController.printLog("INFO", "Lista dos Servidores enviada com sucesso!");

        } catch (Exception ex) {
            LogController.printLog("ERROR", ex.getMessage());
            error = true;
        } finally {
            DirectoryServiceController.getInstance().getConnectedUsers().remove(u.getUserName());
        }
    }

    public static ThreadToRequests getInstance() {
        if (instance == null) {
            instance = new ThreadToRequests();
        }
        return instance;
    }

    private ThreadToRequests() {
        try {
            this.udpSocket = new DatagramSocket(DirectoryServiceController.getInstance().getPort());
        } catch (SocketException ex) {
            LogController.printLog("ERROR", ex.getMessage());
        }
    }

    private DatagramSocket udpSocket;
    private static ThreadToRequests instance;
}
