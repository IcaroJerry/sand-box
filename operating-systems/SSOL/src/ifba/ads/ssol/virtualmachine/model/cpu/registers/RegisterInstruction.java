package ifba.ads.ssol.virtualmachine.model.cpu.registers;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.virtualmachine.model.elements.Word;

public class RegisterInstruction {

    public void refreshWord(Word argNewWord) {
        RegisterInstruction.CurrentWord = argNewWord;

    }

    public static Word RealValue() {
        return RegisterInstruction.CurrentWord;
    }

    public static void ModifyValue(Object newValue) {
        RegisterInstruction.CurrentWord = new Word((ByteSSOL[]) newValue);
    }

    private static Word CurrentWord;
}
