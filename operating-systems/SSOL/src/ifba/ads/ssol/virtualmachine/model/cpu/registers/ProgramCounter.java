package ifba.ads.ssol.virtualmachine.model.cpu.registers;

import ifba.ads.ssol.virtualmachine.ProjectController;

public class ProgramCounter {

    public void nextInstruction() {

        ProgramCounter.CurrentValue += ProjectController.WORD_SIZE;

    }

    public static int RealValue() {
        return ProgramCounter.CurrentValue;
    }

    public static void ModifyRealValue(Object argiNewValue) {
        ProgramCounter.CurrentValue = (int) argiNewValue;
    }

    private static int CurrentValue = -1;

}
