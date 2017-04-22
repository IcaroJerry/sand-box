/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces;

import org.sdfs.interfaces.model.IUser;

/**
 *
 * @author icarojerry
 */
public interface IUserController {
    
    public void registryUser (IUser user) throws Exception;
}
