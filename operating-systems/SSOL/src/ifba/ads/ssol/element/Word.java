package ifba.ads.ssol.element;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.virtualmachine.ProjectController;

public class Word {

    public Word(ByteSSOL[] argByte) {

        ioWord = new ByteSSOL[ProjectController.WORD_SIZE];

        for (int i = 0; i < ProjectController.WORD_SIZE; i++) {

            ioWord[i] = argByte[i];
        }

    }

    @Override
    public String toString() {
        String temp = "";

        for (int i = 0; i < ProjectController.WORD_SIZE; i++) {
            temp += ioWord[i].toString();
        }
        return temp;
    }

    private ByteSSOL[] ioWord;
}
