package org.sdfs.interfaces.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author icarojerry
 */
public class AvailableServers implements Serializable {
    public AvailableServers() {
        servers = new HashMap<>();
    }
    
    public HashMap<String, IServer> getServers() {
        return servers;
    }
    
    public List<IServer> getAllServers() {
        List<IServer> tmpList = new ArrayList<>();
        for (Iterator iterator = servers.values().iterator(); iterator.hasNext();) {
            tmpList.add((IServer) iterator.next());
        }
        return tmpList;
    }
    
    public boolean add(IServer s) {
        this.servers.put(s.getServerAlias(), s);

        return true;
    }
    
    public String[] getServersAlias() {
        String[] serverAlias = new String[servers.values().size()];
        int index = 0;
        for (Iterator iterator = servers.values().iterator(); iterator.hasNext();) {
            IServer next = (IServer) iterator.next();
            serverAlias[index] = next.getServerAlias();
            ++index;
        }
        return serverAlias;
    }

    private HashMap<String, IServer> servers;
    static final long serialVersionUID = 101L;
}
