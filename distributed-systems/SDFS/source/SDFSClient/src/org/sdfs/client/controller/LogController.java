/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.client.controller;

/**
 *
 * @author icarojerry
 */
public class LogController {
    
    public static void printLog(String typeMessage, String message) {
       
        if (showWindowLog)
            printOnWindow(typeMessage, message);
        if (showConsoleLog)
            printOnConsole(typeMessage, message);
    }
    
    private static void printOnWindow(String typeMessage, String message) { 
        UiController.getInstance().printLog(typeMessage, message);
    }

    private static void printOnConsole(String typeMessage, String message){ 
        String msgFinal;
        if (typeMessage.contains("ERROR"))
            msgFinal = "Um erro ocorreu: "+ message;
        else
            msgFinal = message;
        
        System.out.println(msgFinal);
    }

    public static boolean showWindowLog;
    public static boolean showConsoleLog;
}
