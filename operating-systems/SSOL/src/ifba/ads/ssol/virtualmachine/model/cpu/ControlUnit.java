package ifba.ads.ssol.virtualmachine.model.cpu;

import ifba.ads.ssol.virtualmachine.CoreVirtualMachine;
import ifba.ads.ssol.virtualmachine.ProjectController;
import ifba.ads.ssol.virtualmachine.model.cpu.controlunit.ControlSignalGenerator;
import ifba.ads.ssol.virtualmachine.model.cpu.controlunit.*;

public class ControlUnit {

    public void SeekInstruction() {
        while (CoreVirtualMachine.getInstance().getProjectController().valuePC() == -1) {
            System.out.println("Máquina Ociosa");
        }
        System.out.println("Buscando Instrução");
    }

    public ControlSignalGenerator getControlSignalGenerator() {
        return controlSignalGenerator;
    }

    public InstructionDecoder getInstructionDecoder() {
        return instructionDecoder;
    }

    public ControlUnit() {
        controlSignalGenerator = new ControlSignalGenerator();
        instructionDecoder = new InstructionDecoder();
    }
    private static ControlSignalGenerator controlSignalGenerator;
    private static InstructionDecoder instructionDecoder;

}
