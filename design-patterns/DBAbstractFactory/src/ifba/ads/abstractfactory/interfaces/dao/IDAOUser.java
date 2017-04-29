package ifba.ads.abstractfactory.interfaces.dao;

import ifba.ads.abstractfactory.interfaces.model.IUser;
import java.util.List;


public interface IDAOUser {
    
    public Double refreshCredit(IUser user) throws Exception;
    public IUser loadUser(IUser user)throws Exception;
    public void saveUser(IUser user) throws Exception;
    public void debitCredit(IUser user, double debitCredit)throws Exception;
    public void updateCredit(IUser user, double addCredits)throws Exception;  
    public void updateUser(IUser user)throws Exception;
    public void deleteUser(IUser user)throws Exception;
            
    public  List<IUser> allUsers(String email)throws Exception;
}
