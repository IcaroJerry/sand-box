package ifba.ads.ssol.virtualmachine.model.cpu;

import ifba.ads.ssol.virtualmachine.CoreVirtualMachine;
import ifba.ads.ssol.virtualmachine.model.cpu.registers.*;
import ifba.ads.ssol.virtualmachine.model.elements.Word;

public class Registers {

    public void Refresh() {
        System.out.println("Atualizando RI");
        RegisterInstruction.refreshWord(CoreVirtualMachine.getRandomAccessMemory().
                getWord(ProgramCounter.RealValue()));
        System.out.println("Atualizando PC");
        ProgramCounter.nextInstruction();
    }

    private RegisterInstruction RegisterInstruction;
    private ProgramCounter ProgramCounter;

    public Registers() {
        RegisterInstruction = new RegisterInstruction();
        ProgramCounter = new ProgramCounter();
    }

}
