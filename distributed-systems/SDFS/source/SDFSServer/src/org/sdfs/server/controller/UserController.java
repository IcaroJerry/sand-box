/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.server.controller;

import org.sdfs.interfaces.model.IUser;
import org.sdfs.interfaces.IUserController;

/**
 *
 * @author icarojerry
 */
public class UserController implements IUserController {
    
    @Override
    public synchronized void registryUser(IUser user) throws Exception {
//        registredUsers.add(user);
//        dumpUsersData();
//        Method[] m = UserController.class.getDeclaredMethods();
    }
    
//    public synchronized boolean loadRegistredUser() throws IOException {     
//        FileInputStream fIn = new FileInputStream(userDataFile);
//        ObjectInputStream objIn = new ObjectInputStream(fIn);
//
//        try {
//            registredUsers = (AbstractList<IUser>) objIn.readObject();
//        } catch (ClassNotFoundException ex) {
//            System.err.println("Erro ao converter dados do arquivo de Usuários Cadastrados - " + ex.getMessage());
//        }
//
//        objIn.close();
//        fIn.close();
//        
//        return true;
//    }
//    
//    public synchronized boolean dumpUsersData() throws FileNotFoundException, IOException{
//        File f = new File(userDataFile);
//        FileOutputStream fOut = new FileOutputStream(f);
//        ObjectOutputStream objOut = new ObjectOutputStream(fOut);
//                    
//        objOut.writeObject(registredUsers);
//        objOut.flush();
//
//        return true;
//    }
//
//    public static UserController getInstance() {
//        if(instance == null)
//            instance = new UserController();
//        
//        return instance;
//    }

    private UserController() {
//        userDataFile = ServerController.getInstance().getDataServer().getServerDataDir()
//                       +File.separator+ServerController.FILE_USER_FILE;
//        File f = new File(userDataFile);
//        
//        if (!f.exists()) {
//            try {
//                f.createNewFile();
//                registredUsers = new ArrayList<>();
//                dumpUsersData();
//            } catch (IOException ex) {
//                System.err.println("Erro ao criar base de dados dos Usuários Cadastrados - " + ex.getMessage());
//            }
//        }
    }
    
//    private List<IUser> registredUsers;
//    private static UserController instance;
//    private final String userDataFile;
}
