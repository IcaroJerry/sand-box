/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.server.controller;

import org.sdfs.server.ui.MainWindow;

/**
 *
 * @author icarojerry
 */
public class UiController {
    
    public static UiController getInstance() {
        if (instance == null)
            instance = new UiController();

        return instance;
    }

    private UiController() {
        window = new MainWindow();
    }

    public void show() {
        window.setVisible(true);
    }
    
    public void setEnableLoadConfigOpt(boolean opt) {
        window.setEnableLoadConfigOpt(opt);
    }
    
    public void dataServerChanged() {
       window.dataServerChanged();
    }
    public void refreshAllScreen() {
        window.refreshAllScreen();
    }
    
    public void printLog(String typeMessage, String message) {
        window.printLog(typeMessage, message);
    }

    private MainWindow window;
    private static UiController instance;
}
