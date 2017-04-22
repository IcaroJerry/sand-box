package org.sdfs.client.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import org.sdfs.interfaces.GlobalConfig;
import org.sdfs.interfaces.model.AvailableServers;
import org.sdfs.interfaces.model.IDirectoryService;
import org.sdfs.interfaces.model.IServer;
import org.sdfs.interfaces.model.IUser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author icarojerry
 */
public class ClientController {

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }

        return instance;
    }

    public void initialize(String dsAddr, int dsPort, String dirWorkspace) {
        //availableServers = null;
        try {
            DIRECTORY_SERVICE = new IDirectoryService(dsAddr, dsPort);

            DIR_USER_WORSPACE = dirWorkspace;

            makeWorkspaceDir();
            UiController.getInstance().show();
        } catch (IOException ex) {
            LogController.printLog("ERROR", "Ocorreu um erro ao criar diretório do utilizador.");
        }
    }

    public void connectToServer(IServer s) {
        Socket socket;
        InetAddress addr;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            addr = InetAddress.getByName(s.getServerAddr());
            socket = new Socket(addr, s.getPort());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (UnknownHostException ex) {
            LogController.printLog("ERROR", "Endereço do Servidor é inválido!");
        } catch (IOException ex) {
            LogController.printLog("ERROR", "Erro ao criar conexão com o Servidor!");
        }
    }

    public void connectToDirectoryService() {
//        if (!USER_CONNECTED_2_DS) {
        DatagramSocket udpSocket;
        DatagramPacket packet;
        DatagramPacket packetReceiver;
        InetAddress dsAddr;
        ObjectInputStream ois;
        Object objReceived;
        ByteArrayOutputStream bout;
        ObjectOutputStream ous;

        try {

            udpSocket = new DatagramSocket();
            udpSocket.setSoTimeout(GlobalConfig.TIME_OUT);

            bout = new ByteArrayOutputStream();
            ous = new ObjectOutputStream(bout);
            ous.writeObject(GlobalConfig.MSG_CONNECT);
            ous.flush();
            ous.close();

            packet = new DatagramPacket(bout.toByteArray(), bout.size());
            dsAddr = InetAddress.getByName(DIRECTORY_SERVICE.getAddr());
            packet.setAddress(dsAddr);
            packet.setPort(DIRECTORY_SERVICE.getPort());

            //Enviando mensagem CONNECT
            udpSocket.send(packet);
            LogController.printLog("", "Solicitação para efetuar Conexão enviada ao Serviço de Diretória no endereço "
                    + DIRECTORY_SERVICE.getAddr() + ":" + DIRECTORY_SERVICE.getPort());

            //TODO: if(dsAddr != remote_addr);// Verificar Se o pacote recebido é da Origem
            //Recebendo tamanho da Lista de Servidores Disponíveis
            packetReceiver = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
            udpSocket.receive(packetReceiver);

            LogController.printLog("INFO", "Resposta recebida do Serviço de Diretória.");

            ois = new ObjectInputStream(new ByteArrayInputStream(
                    packetReceiver.getData(), 0, packetReceiver.getLength()));
            objReceived = ois.readObject();

            //Se a mensagem Recebida não for instancia a lista
            if (!(objReceived instanceof AvailableServers)) {
                throw new Exception("Mensagem recebida é inválida!");
            }

            AvailableServers aServers = (AvailableServers) objReceived;

            packet.setData(GlobalConfig.MSG_CONFIRM.getBytes());
            packet.setLength(GlobalConfig.MSG_CONFIRM.getBytes().length);
            udpSocket.send(packet);
            
            packetReceiver = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
            udpSocket.receive(packetReceiver);
            
            ois = new ObjectInputStream(new ByteArrayInputStream(
                    packetReceiver.getData(), 0, packetReceiver.getLength()));
            objReceived = ois.readObject();

            //Se a mensagem Recebida não for instancia a lista
            if (!(objReceived instanceof List)) {
                throw new Exception("Mensagem recebida é inválida!");
            }

            List<IUser> connectedUsers = (List<IUser>) objReceived;

            packet.setData(GlobalConfig.MSG_CONFIRM.getBytes());
            packet.setLength(GlobalConfig.MSG_CONFIRM.getBytes().length);
            udpSocket.send(packet);
            LogController.printLog("INFO", "Enviando confirmação ao Serviço de Diretória.");

            this.changeAvailableServers(aServers);
            this.setUsersConnected(connectedUsers);
            //Acionando Thread dedicada ao Serviço de Diretoria
            //Thread t = new Thread(new ThreadToDirectoryService(udpSocket));
            //t.start();//TEST

            USER_CONNECTED_2_DS = true;
            UiController.getInstance().refreshAll();
        } catch (UnknownHostException ex) {
            LogController.printLog("ERROR", "Endereço do Serviço de Diretória é inválido ou desconhecido.");
        } catch (SocketException ex) {
            LogController.printLog("ERROR", "Um erro ocorreu ao configurar conexão com o Serviço de Diretória..");
        } catch (IOException ex) {
            LogController.printLog("ERROR", "Um erro ocorreu ao tentar conexão com o Serviço de Diretória.");
        } catch (ClassNotFoundException ex) {
            LogController.printLog("ERROR", "Um erro ocorreu ao receber mensagem do Serviço de Diretoria.");
        } catch (Exception ex) {
            LogController.printLog("ERROR", ex.getMessage());
        }
//        }
//        else {
//            LogController.printLog("ALERT", "O Cliente já está conectado a um Serviço de Diretória");
//        }
    }

//    public void invokerRemoteMethod(String methodName, Object[] args) {
//        IMetaMethodInvoker mmi = new IMetaMethodInvoker(methodName, args);
//    }
//
//    public void findServer() {
//
//    }
//    public void createAndListenSocket() {
//        try {
//            DatagramSocket socket = new DatagramSocket(DIRECTORY_SERVICE_PORT);
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
//
//    public void connectToServer() {
//        try {
//            InetAddress serverAddr = InetAddress.getByName("localhost");
//            int serverPort = 5000;
//            Socket socket = new Socket(serverAddr, serverPort);
//            ous = new ObjectOutputStream(clientSocket.getOutputStream());
//            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
//        } catch (UnknownHostException ex) {
//            LogController.printLog("ERROR", "O endereço do Servidor está incorreto ou inválido.");
//        } catch (IOException ex) {
//            LogController.printLog("ERROR", "Configuração do Socket inválida.");
//        }
//    }
//
//    public void sendMessageToServer() {
//        IUser user = new IUser("Icaro", "icaro", "1234");
//        Object[] objArg = new Object[1];
//        objArg[0] = (IUser) user;
//        IMetaMethodInvoker mmi = new IMetaMethodInvoker("LOGIN", objArg);
//
//        try {
//            ous.writeObject(mmi);
//            ous.flush();
//            ous.close();
//        } catch (IOException ex) {
//            LogController.printLog("ERROR", "Erro ao enviar mensagem para o Servidor.");
//        }
//
//    }
    public synchronized IDirectoryService getDirectoryService() {
        return DIRECTORY_SERVICE;
    }

    public synchronized void setDirectoryService(IDirectoryService DIRECTORY_SERVICE) {
        this.DIRECTORY_SERVICE = DIRECTORY_SERVICE;
    }

    public synchronized AvailableServers getAvailableServers() {
        return availableServers;
    }

    public synchronized void changeAvailableServers(AvailableServers s) {
        if (s.getServers().isEmpty()) {
            s = null;
            LogController.printLog("ALERT", "Nenhum Servidor está disponível no momento!");
        }

        this.availableServers = s;
        UiController.getInstance().refreshAll();
    }

    public String getUserWorkspaceDir() {
        return DIR_USER_WORSPACE;
    }

    public String getUserDataDir() {
        return DIR_USER_DATA;
    }

    public synchronized IServer getConnectedServer() {
        return connectedServer;
    }

    private void makeWorkspaceDir() throws IOException {
        File f = new File(DIR_USER_WORSPACE);

        if (f.exists()) {
            return;
        }

        f.mkdir();
    }

    private ClientController() {
        //availableServers = new AvailableServers();
        DIR_USER_DATA = "client-data";
    }

    public synchronized List<IUser> getUsersConnected() {
        return this.USERS_CONNECTED;
    }

    public synchronized void setUsersConnected(List<IUser> u) {
        this.USERS_CONNECTED = u;
        UiController.getInstance().refreshAll();
    }

    public boolean isConnected2DS() {
        return USER_CONNECTED_2_DS;
    }

    public boolean isConnected2SRV() {
        return USER_CONNECTED_2_SRV;
    }

    private boolean USER_CONNECTED_2_DS;
    private boolean USER_CONNECTED_2_SRV;
    private static String DIR_USER_WORSPACE;
    private static String DIR_USER_DATA;

    private static ClientController instance;
    private AvailableServers availableServers;
    private IServer connectedServer;
    private IDirectoryService DIRECTORY_SERVICE;
    private List<IUser> USERS_CONNECTED;
}
