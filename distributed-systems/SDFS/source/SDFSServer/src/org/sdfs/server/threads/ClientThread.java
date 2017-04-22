package org.sdfs.server.threads;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import org.sdfs.interfaces.model.IUser;

public class ClientThread extends Thread {

    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
        //calculator = new Calculator();
        this.clientId = ClientThread.idCount;
        ClientThread.idCount += 1;
        System.out.println("Thread " + clientId + "- dedicada para cliente foi criada.");
    }

    public int getClientID() {
        return this.clientId;
    }

    @Override
    public void run() {
        DataInputStream is = null;
        PrintStream os = null;
        String operation;
        String op[];
        String result = "";
        String c = "";

        try {
            is = new DataInputStream(socket.getInputStream());
            os = new PrintStream(socket.getOutputStream());

            os.println("...connected!\n\n");
            os.println("Welcome to online calculator!");

            while (!c.equalsIgnoreCase("q")) {
                os.println("Type operation to the server: ");
                operation = is.readLine();

                /*if((op = operation.split("\\+")).length == 2){
               result = ""+user.plus(Double.valueOf(op[0]), Double.valueOf(op[1]));
             }
             else if((op = operation.split("-")).length == 2){
               result = ""+user.minus(Double.valueOf(op[0]), Double.valueOf(op[1]));
             }
             else if((op = operation.split("/")).length == 2){
               result = ""+user.division(Double.valueOf(op[0]), Double.valueOf(op[1]));
             }
             else if((op = operation.split("\\*")).length == 2){
               result = ""+user.times(Double.valueOf(op[0]), Double.valueOf(op[1]));
             }
             else{
                 result = "Error: Invalid expression! Try again...\n";
             }*/
                os.println("The result is:" + String.valueOf(result));
                os.println("To exit type \"q\" or any key to continue...");
                c = is.readLine();
            }
            os.println("\nBye bye...");
        } catch (IOException io) {
        }
    }

    private Socket socket;
    private int clientId;
    private static int idCount;
    //private ClientThread t[]; 
    private IUser user;
}
