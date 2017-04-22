package org.sdfs.interfaces.actions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author icarojerry
 */
public enum Action {
    CONNECT, DISCONNECT, HEART_BEATING;
    
    public enum User2ServiceDirectory {
        GET_AVAILABLE_SERVERS, SEND_ONE, SEND_ALL, GET_USERS_ON;
    }
    
    public enum User2Server {
        REGITRY, LOGIN, LOGOUT, GET_FILES, NEW_FILE;
    }
    
    public enum Server2DirectoryService {
        
    }
}
