/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sdfs.interfaces;

import java.io.Serializable;

/**
 *
 * @author icarojerry
 */
public class IMetaMethodInvoker implements Serializable {
    
    public IMetaMethodInvoker(String methodName, Object[] args) {
        this.methodName = methodName;
        this.args = args;
    }
    public String getMethodName() {
        return this.methodName;
    }
    
    static final long serialVersionUID = 1010L;
    protected String methodName;
    protected Object[] args;
}
