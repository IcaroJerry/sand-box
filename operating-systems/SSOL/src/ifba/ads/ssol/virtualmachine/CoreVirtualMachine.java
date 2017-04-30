package ifba.ads.ssol.virtualmachine;

import ifba.ads.ssol.interfaces.ICoreVirtualMachine;
import ifba.ads.ssol.interfaces.IProjectController;
import ifba.ads.ssol.interfaces.IUIController;
import ifba.ads.ssol.virtualmachine.model.core.*;

public class CoreVirtualMachine extends ICoreVirtualMachine {

    public static void initialize() {
        if (instance == null) {
            instance = new CoreVirtualMachine();
        }
    }

    protected CoreVirtualMachine() {
        randomAccessMemory = new RandomAccessMemory();
        centralProcessingUnit = new CentralProcessingUnit();
        uiController = new UIController();
        projectController = new ProjectController();
    }

//<editor-fold defaultstate="collapsed" desc="Métodos de Acesso">
    public static RandomAccessMemory getRandomAccessMemory() {
        return randomAccessMemory;
    }

    public static CentralProcessingUnit getCentralProcessingUnit() {
        return centralProcessingUnit;
    }

    @Override
    public IUIController getUIController() {
        return uiController;
    }

    @Override
    public IProjectController getProjectController() {
        return projectController;
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Atributos Privados">
    private static RandomAccessMemory randomAccessMemory;
    private static CentralProcessingUnit centralProcessingUnit;

    private ProjectController projectController;
    private UIController uiController;
    //</editor-fold>

}
