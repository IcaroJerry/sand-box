/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces.sdfsexceptions;

/**
 *
 * @author icarojerry
 */
public class ServerActionException extends Exception {

    public ServerActionException(String msg) {
        super();
        this.codMessage = -1;
        this.msg = msg;
    }
    
    public ServerActionException(int codMessage) {
        super();
        this.codMessage = codMessage;
        msg = null;
    }

    public ServerActionException() {
        super();
        this.codMessage = 0;
        msg = null;
    }

    @Override
    public String getMessage() {
        if(this.codMessage == -1)
            return msg;
        
        switch (this.codMessage) {
            case 0: 
                return MSG_DATA_SERVER_NOT_EXIST;


            default:
                return MSG_DEFAULT;
        }
    }

    public static int DATA_SERVER_NOT_EXIST = 0;


    private final int codMessage;
    private final String msg;
    private final String MSG_DEFAULT = "Ocorreu um erro durante a operação de carregar configurações do Servidor.";
    private final String MSG_DATA_SERVER_NOT_EXIST = "Servidor não possui dados de configurações salvos.";

}
