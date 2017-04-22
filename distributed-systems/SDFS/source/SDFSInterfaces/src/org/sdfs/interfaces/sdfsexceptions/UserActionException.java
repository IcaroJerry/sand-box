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
public class UserActionException extends Exception {

    public UserActionException(int codMessage) {
        super();
        this.codMessage = codMessage;
    }

    public UserActionException() {
        super();
        this.codMessage = 0;
    }

    @Override
    public String getMessage() {
        switch (this.codMessage) {
            case 0: 
                return MSG_FILE_NOT_CREATED;
            case 1:
                return MSG_FILE_NOT_UPDATED;
            case 2: 
                return MSG_FILE_NOT_FOUND;
            case 3: 
                return MSG_FILE_NOT_EXIST;
            case 4: 
                return MSG_FILE_EXIST;
            case 5: 
                return MSG_FILE_NOT_DELETED;
            case 6: 
                return MSG_FORBIDDEN_FILE;
            case 7: 
                return MSG_FORBIDDEN_DIRECTORY;
            case 8: 
                return MSG_DIRECTORY_NOT_CREATED;
            case 9: 
                return MSG_DIRECTORY_NOT_FOUND;
            case 10: 
                return MSG_DIRECTORY_NOT_EXIST;
            case 11: 
                return MSG_DIRECTORY_EXIST;

            default:
                return MSG_DEFAULT;
        }
    }

    public static int FILE_NOT_CREATED = 0;
    public static int FILE_NOT_UPDATED = 1;
    public static int FILE_NOT_FOUND = 2;
    public static int FILE_NOT_EXIST = 3;
    public static int FILE_EXIST = 4;
    public static int FILE_NOT_DELETED = 5;
    public static int FORBIDDEN_FILE = 6;
    public static int FORBIDDEN_DIRECTORY = 7;
    public static int DIRECTORY_NOT_CREATED = 8;
    public static int DIRECTORY_NOT_FOUND = 9;
    public static int DIRECTORY_NOT_EXIST = 9;
    public static int DIRECTORY_EXIST = 9;

    private final int codMessage;
    private final String MSG_DEFAULT = "Ocorreu um erro durante a operação";
    private final String MSG_FILE_NOT_CREATED = "";
    private final String MSG_FILE_NOT_UPDATED = "";
    private final String MSG_FILE_NOT_FOUND = "";
    private final String MSG_FILE_NOT_EXIST = "";
    private final String MSG_FILE_EXIST = "";
    private final String MSG_FILE_NOT_DELETED = "";
    private final String MSG_FORBIDDEN_FILE = "";
    private final String MSG_FORBIDDEN_DIRECTORY = "";
    private final String MSG_DIRECTORY_NOT_CREATED = "";
    private final String MSG_DIRECTORY_NOT_FOUND = "";
    private final String MSG_DIRECTORY_NOT_EXIST = "";
    private final String MSG_DIRECTORY_EXIST = "";
}
