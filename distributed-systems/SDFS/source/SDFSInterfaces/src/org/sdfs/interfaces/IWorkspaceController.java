/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces;

import java.io.File;

/**
 *
 * @author icarojerry
 */
public interface IWorkspaceController {
    
    //Directory Actions
    public String[] listAll();
    public String[] listDir();
    public void renameDir(String dirName, String newDirName);
    public void makeDir(String dirName);
    public void openDir(String dirName);
    public void returnDir();
    
    
    //File Actions
    public void openFile(String filePath);
    public void createFile(String filePath);
    public void renameFile(File f, String newName);
    public void deleteFile(File f);
    public void sendFileToServer(File f);
    public File getFileOnServer(String filePath);
}
