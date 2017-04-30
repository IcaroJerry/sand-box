package ifba.ads.ssol.virtualmachine.model.cpu;

import ifba.ads.ssol.virtualmachine.ProjectController;
import ifba.ads.ssol.virtualmachine.model.core.CentralProcessingUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArithmeticLogicUnit {

    public void Execute() {
        System.out.println("Executando");
        Thread delay = new Thread();
        try {
            delay.sleep(ProjectController.VALUE_DELAY);

        } catch (InterruptedException ex) {
            Logger.getLogger(CentralProcessingUnit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
