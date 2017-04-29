package ifba.ads.abstractfactory.interfaces;

import ifba.ads.abstractfactory.config.LoadConfigBD;
import ifba.ads.abstractfactory.interfaces.dao.IDAOGame;
import ifba.ads.abstractfactory.interfaces.dao.IDAOTeam;
import ifba.ads.abstractfactory.interfaces.dao.IDAOUser;


public abstract class IAbstractFactoryBD {
 
    public IAbstractFactoryBD getInstance()
    {
        if(instance == null){
            try {
                instance = (IAbstractFactoryBD) Class.forName(CURRENT_BD_FACTORY).newInstance();
                }catch (Exception ex) {
                    ex.printStackTrace();
            }
        }
        return instance;
    }
    
    public String getCurrentDBFactory() {
        return CURRENT_BD_FACTORY;
    }
    
    public void setCurrentDBFactory(String key) {
        CURRENT_BD_FACTORY = "ifba.ads.abstractfactory.persistence" + LoadConfigBD.getValue(key);
    }
    
    public abstract  IDAOGame  CreateBDGame();
    public abstract IDAOTeam CreateBDTeam();
    public abstract IDAOUser CreateBDUser();
     
    protected IAbstractFactoryBD() {}
    protected static IAbstractFactoryBD instance = null; 
    private String CURRENT_BD_FACTORY = "ifba.ads.abstractfactory.persistence" + LoadConfigBD.getValue("urlMYSQL");
}
