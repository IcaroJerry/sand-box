package org.sdfs.directoryservice.thread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sdfs.interfaces.GlobalConfig;

public class ThreadToRequestsNew {

    public ThreadToRequestsNew() {
        try {
            udpSocket = new DatagramSocket();
            DatagramPacket dp;
            while (true) {
                dp = new DatagramPacket(new byte[GlobalConfig.BUFFER_SIZE], GlobalConfig.BUFFER_SIZE);
                udpSocket.receive(dp);

                new Thread(new ListenerSocket(socket, dp)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadToRequestsNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DatagramSocket udpSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> userConected = new HashMap<>();
    private Map<String, ObjectOutputStream> serverConected = new HashMap<>();

    private class ListenerSocket implements Runnable {

        public ListenerSocket(Socket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
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

        private DatagramPacket packet;
        private Socket socket;
    }
}
