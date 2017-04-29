package ifba.ads.abstractfactory.interfaces.dao;

import ifba.ads.abstractfactory.interfaces.model.ITeam;
import java.util.List;

public interface IDAOTeam {
    public List<ITeam> getAllTeamGroupRanking(char group) throws Exception;
    public List<ITeam> getAllTeam() throws Exception;
    public List<ITeam> getAllTeamGroup(char group) throws Exception;
    
}
