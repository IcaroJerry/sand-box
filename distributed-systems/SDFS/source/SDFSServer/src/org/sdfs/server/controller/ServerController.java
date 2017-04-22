/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.sdfs.interfaces.model.IServer;
import org.sdfs.interfaces.model.IUser;
import org.sdfs.interfaces.sdfsexceptions.ServerActionException;
import org.sdfs.server.models.DataServer;
import org.sdfs.server.service.Server2Client;

/**
 *
 * @author icarojerry
 */
public class ServerController {

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }

        return instance;
    }

    public void initialize(String serverAlias, String serviceAddr, int servicePort, String dirDataServer) {
        try {
            server2Client = new Server2Client();
            int serverPort = server2Client.serverPort;
            new Thread(server2Client).start();
            
            SERVER_CONNECTED_TO_DS = false;
            DATA_SERVER = new DataServer(new IServer(serverAlias, "localhost", serverPort, dirDataServer),
                    servicePort, serviceAddr);
            makeDataDir();
            makeSharedWorkspaceDir();

            if (existsPreviousData()) {
                UiController.getInstance().setEnableLoadConfigOpt(true);
            } else {
                makeDataServerFile();
            }

            UiController.getInstance().show();
        } catch (IOException ex) {
            LogController.printLog("ERROR", "Erro ao inicializar configurações do Servidor - " + ex.getMessage());
        }
    }

    public void turnOnServer() {
        try {
            DATA_SERVER.initServer();
            if(isConnected2DS())
                LogController.printLog("INFO", "O Servidor está cadastrado no Serviço de Diretória!");
            
        } catch (ServerActionException ex) {
            LogController.printLog("ERROR", ex.getMessage());
        }
    }

    private boolean existsPreviousData() {
        File f = new File(DATA_SERVER.getServerDataDir() + File.separator
                + FILE_DATA_SERVER + DATA_SERVER.getServerAlias().replace(" ", "-").toLowerCase());

        return !(!f.exists() || f.length() == 0);
    }

    public synchronized boolean dumpServerConfig() throws Exception {
        File f = new File(DATA_SERVER.getServerDataDir() + File.separator
                + FILE_DATA_SERVER + DATA_SERVER.getServerAlias().replace(" ", "-").toLowerCase());

        ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(f));

        objOut.writeObject(DATA_SERVER);
        objOut.flush();
        objOut.close();

        return true;
    }

    public synchronized void loadPreviousData() throws ServerActionException {
        File f = new File(DATA_SERVER.getServerDataDir() + File.separator
                + FILE_DATA_SERVER + DATA_SERVER.getServerAlias().replace(" ", "-").toLowerCase());

        try {
            if (!f.exists()) {
                return;
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            setDataServer((DataServer) ois.readObject());

        } catch (IOException ex) {
            throw new ServerActionException(ServerActionException.DATA_SERVER_NOT_EXIST);
        } catch (ClassNotFoundException ex) {
            throw new ServerActionException();
        }
    }

    public DataServer getDataServer() {
        return DATA_SERVER;
    }

    public void setDataServer(String alias, int port, String dataDir, String serviceAddr, int servicePort) {
        DATA_SERVER.setServerDataDir(dataDir);
        DATA_SERVER.setServerAlias(alias);
        DATA_SERVER.setServerPort(port);
        DATA_SERVER.setServiceAddr(serviceAddr);
        DATA_SERVER.setServicePort(servicePort);

        UiController.getInstance().dataServerChanged();
    }

    public void setDataServer(DataServer s) {
        DATA_SERVER = s;

        UiController.getInstance().dataServerChanged();
    }

    private void makeSharedWorkspaceDir() throws IOException {
        File f = new File(DIR_SHARED_WORSPACE + File.separator
                + DATA_SERVER.getServerAlias().replace(" ", "-").toLowerCase());

        if (f.exists()) {
            return;
        }

        f.mkdirs();
    }

    private void makeDataDir() throws IOException {
        File f = new File(DATA_SERVER.getServerDataDir());

        if (f.exists()) {
            return;
        }

        f.mkdir();
    }

    private void makeDataServerFile() throws IOException {
        File f = new File(DATA_SERVER.getServerDataDir() + File.separator
                + FILE_DATA_SERVER + DATA_SERVER.getServerAlias().replace(" ", "-").toLowerCase());

        if (f.exists()) {
            return;
        }
        f.createNewFile();
    }
    
    public boolean isConnected2DS() {
        return SERVER_CONNECTED_TO_DS;
    }
    
    public void setServerConnected2DS(boolean opt) {
        SERVER_CONNECTED_TO_DS = opt;
        UiController.getInstance().refreshAllScreen();
    }
    
    public List<IUser> getUsersConnected() {
        return usersConnected;
    }
    
    private ServerController() {
        usersConnected = new ArrayList<>();
    }
    
    private List<IUser> usersConnected;
    private Server2Client server2Client;
    private boolean SERVER_CONNECTED_TO_DS;
    private static final String DIR_SHARED_WORSPACE = "shared-workspace";
    private static final String FILE_DATA_SERVER = "config";
    private static DataServer DATA_SERVER;
    private static ServerController instance;
}
