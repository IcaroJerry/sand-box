package ifba.ads.abstractfactory.persistence.msql;

import ifba.ads.abstractfactory.interfaces.dao.IDAOTeam;
import ifba.ads.abstractfactory.interfaces.model.ITeam;
import java.util.List;


public class MSQLTeam implements IDAOTeam{

    @Override
    public List<ITeam> getAllTeam() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ITeam> getAllTeamGroup(char group) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ITeam> getAllTeamGroupRanking(char group) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
