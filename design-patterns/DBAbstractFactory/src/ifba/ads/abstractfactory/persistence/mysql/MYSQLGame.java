package ifba.ads.abstractfactory.persistence.mysql;

import ifba.ads.abstractfactory.interfaces.dao.IDAOGame;
import ifba.ads.abstractfactory.interfaces.model.IGame;
import java.util.List;


public class MYSQLGame  implements IDAOGame{

    @Override
    public List<IGame> getAllGameGruop(char group) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IGame> getAllGame() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existGameGroup(char group) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
