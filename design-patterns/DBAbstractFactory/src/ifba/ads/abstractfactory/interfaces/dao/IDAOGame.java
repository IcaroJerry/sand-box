package ifba.ads.abstractfactory.interfaces.dao;

import ifba.ads.abstractfactory.interfaces.model.IGame;
import java.util.List;


public interface IDAOGame {
    
   public List<IGame> getAllGameGruop(char group) throws Exception;
   public List<IGame> getAllGame() throws Exception;
   public boolean existGameGroup(char group) throws Exception ;
}
