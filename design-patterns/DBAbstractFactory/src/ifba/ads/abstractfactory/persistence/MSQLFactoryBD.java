package ifba.ads.abstractfactory.persistence;

import ifba.ads.abstractfactory.interfaces.IAbstractFactoryBD;
import ifba.ads.abstractfactory.interfaces.dao.IDAOGame;
import ifba.ads.abstractfactory.interfaces.dao.IDAOTeam;
import ifba.ads.abstractfactory.interfaces.dao.IDAOUser;
import ifba.ads.abstractfactory.persistence.msql.MSQLGame;
import ifba.ads.abstractfactory.persistence.msql.MSQLTeam;
import ifba.ads.abstractfactory.persistence.msql.MSQLUser;


public class MSQLFactoryBD extends IAbstractFactoryBD{

    @Override
    public IDAOGame CreateBDGame() {
      return new MSQLGame();
    }

    @Override
    public IDAOTeam CreateBDTeam() {
       return new MSQLTeam();
    }

    @Override
    public IDAOUser CreateBDUser() {
       return new MSQLUser();
    }  
}
