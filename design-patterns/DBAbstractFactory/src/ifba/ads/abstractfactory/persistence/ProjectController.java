package ifba.ads.abstractfactory.persistence;

import ifba.ads.abstractfactory.interfaces.IProjectController;

public class ProjectController extends IProjectController {
    
    public static IProjectController getInstance() {
        if(instance == null)
            return new ProjectController();

        return instance;
    }
        
    private ProjectController(){}
    private static IProjectController instance;
}