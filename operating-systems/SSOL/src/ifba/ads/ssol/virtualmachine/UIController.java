package ifba.ads.ssol.virtualmachine;

import ifba.ads.ssol.interfaces.IUIController;

public class UIController implements IUIController {

    @Override
    public void initializeCPU() {
        CoreVirtualMachine.initialize();
        Thread cpu = new Thread(CoreVirtualMachine.getCentralProcessingUnit());
        cpu.start();
    }

}
