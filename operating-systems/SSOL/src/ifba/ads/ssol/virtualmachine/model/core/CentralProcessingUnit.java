package ifba.ads.ssol.virtualmachine.model.core;

import ifba.ads.ssol.interfaces.IMicroKernel;
import ifba.ads.ssol.virtualmachine.ProjectController;
import ifba.ads.ssol.virtualmachine.model.cpu.*;

public class CentralProcessingUnit implements Runnable {

    @Override
    public void run() {
        Start();
    }

    private void Start() {
        // while(true){ 
        int aux = 1;

        System.out.println("INICIANDO CICLO");
        while (aux <= ProjectController.NUM_QUATUM) {
            controlUnit.SeekInstruction();
            registers.Refresh();
            controlUnit.getInstructionDecoder().Decodify();
            arithmeticLogicUnit.Execute();
            System.out.println(new ProjectController().valuePC() + "PC");
            System.out.println(new ProjectController().valueRI() + "RI");
            aux++;
        }
        System.out.println("INICIANDO TROCA DE CONTEXTO");
        IMicroKernel.getInstance().contextSwitch();
        System.out.println("FIM DA TROCA DE CONTEXTO");
        //}

    }

    public CentralProcessingUnit() {
        registers = new Registers();
        arithmeticLogicUnit = new ArithmeticLogicUnit();
        controlUnit = new ControlUnit();
    }

    //<editor-fold defaultstate="collapsed" desc="Componentes da CPU ">
    private static Registers registers;
    private static ArithmeticLogicUnit arithmeticLogicUnit;
    private static ControlUnit controlUnit;
    //</editor-fold>

}
