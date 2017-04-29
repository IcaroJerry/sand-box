package ifba.ads.abstractfactory.persistence;

import ifba.ads.abstractfactory.interfaces.IAbstractFactoryBD;
import ifba.ads.abstractfactory.interfaces.dao.IDAOGame;
import ifba.ads.abstractfactory.interfaces.dao.IDAOTeam;
import ifba.ads.abstractfactory.interfaces.dao.IDAOUser;
import ifba.ads.abstractfactory.persistence.postgres.PostgresGame;
import ifba.ads.abstractfactory.persistence.postgres.PostgresTeam;
import ifba.ads.abstractfactory.persistence.postgres.PostgresUser;


public class PostgresFactoryBD extends IAbstractFactoryBD {

     @Override
    public IDAOGame CreateBDGame() {
      return new PostgresGame();
    }

    @Override
    public IDAOTeam CreateBDTeam() {
       return new PostgresTeam();
    }

    @Override
    public IDAOUser CreateBDUser() {
       return new PostgresUser();
   }
}
