package ifba.ads.abstractfactory.persistence;

import ifba.ads.abstractfactory.interfaces.IAbstractFactoryBD;
import ifba.ads.abstractfactory.interfaces.dao.IDAOGame;
import ifba.ads.abstractfactory.interfaces.dao.IDAOTeam;
import ifba.ads.abstractfactory.interfaces.dao.IDAOUser;
import ifba.ads.abstractfactory.persistence.mysql.MYSQLGame;
import ifba.ads.abstractfactory.persistence.mysql.MYSQLTeam;
import ifba.ads.abstractfactory.persistence.mysql.MYSQLUser;

public class MYSQLFactoryBD extends IAbstractFactoryBD{

    @Override
    public IDAOGame CreateBDGame() {
      return new MYSQLGame();
    }

    @Override
    public IDAOTeam CreateBDTeam() {
       return new MYSQLTeam();
    }

    @Override
    public IDAOUser CreateBDUser() {
       return new MYSQLUser();
    }
}
