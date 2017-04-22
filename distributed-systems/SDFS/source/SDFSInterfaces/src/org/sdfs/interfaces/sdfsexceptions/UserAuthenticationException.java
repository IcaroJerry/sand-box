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
public class UserAuthenticationException extends Exception {

    public UserAuthenticationException(int codMessage) {
        super();
        this.codMessage = codMessage;
    }

    public UserAuthenticationException() {
        super();
        this.codMessage = 0;
    }

    @Override
    public String getMessage() {
        switch (this.codMessage) {
            case 0: 
                return MSG_USERNAME_EXIST;
            case 1:
                return MSG_USER_NOT_EXIST;
            case 2: 
                return MSG_PWD_WRONG;

            default:
                return MSG_DEFAULT;
        }
    }


    public static int USERNAME_EXIST = 0;
    public static int USER_NOT_EXIST = 1;
    public static int PWD_WRONG = 2;

    private int codMessage;
    private String MSG_DEFAULT = "Ocorreu um erro durante a operação";
    private String MSG_USERNAME_EXIST = "Username já está cadastrado!";
    private String MSG_USER_NOT_EXIST = "Usuário não existe!";
    private String MSG_PWD_WRONG = "Senha incorreta!";
}
