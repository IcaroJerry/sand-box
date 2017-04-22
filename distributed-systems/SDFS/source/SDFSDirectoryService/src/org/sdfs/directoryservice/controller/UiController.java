/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.directoryservice.controller;

import org.sdfs.directoryservice.ui.MainWindow;


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
    
    public void printLog(String typeMessage, String message) {
        window.printLog(typeMessage, message);
    }
    public void refreshAll() {
        window.refreshAllScenes();
    }

    private MainWindow window;
    private static UiController instance;
}
