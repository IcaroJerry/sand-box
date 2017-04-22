package org.sdfs.server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server2ClientNEW {

    public Server2ClientNEW() {
        try {
            tcpSocket = new ServerSocket(0);

            while (true) {
                socket = tcpSocket.accept();

                new Thread(new ListenerSocket(socket)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server2ClientNEW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ServerSocket tcpSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> onlines = new HashMap<>();

    private class ListenerSocket implements Runnable {

        public ListenerSocket(Socket socket) {
            try {
                this.objOut = new ObjectOutputStream(socket.getOutputStream());
                this.objIn = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server2ClientNEW.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void run() {
//            ClientAction message = null;
//
//            try {
//            while ((message = (ClientAction) objIn.readObject()) != null) {
//               //message
//            }
//            } catch (IOException ex) {
//                Logger.getLogger(Server2ClientNEW.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(Server2ClientNEW.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        private ObjectOutputStream objOut;
        private ObjectInputStream objIn;
    }
}
