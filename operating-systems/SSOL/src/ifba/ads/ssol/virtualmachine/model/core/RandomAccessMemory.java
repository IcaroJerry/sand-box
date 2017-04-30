package ifba.ads.ssol.virtualmachine.model.core;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.virtualmachine.ProjectController;
import ifba.ads.ssol.virtualmachine.model.elements.Word;

public class RandomAccessMemory {

    public RandomAccessMemory() {
        memory = new ByteSSOL[ProjectController.MEMORY_SIZE * 1024];
    }

    public Word getWord(int index) {
        ByteSSOL[] bytes = new ByteSSOL[ProjectController.WORD_SIZE];
        int aux = 0;

        for (int i = index; i < index + ProjectController.WORD_SIZE; i++) {
            bytes[aux] = memory[i];
            aux++;
        }

        return new Word(bytes);
    }

    public void alloc(int ini, Object[] content) {
        int aux = 0;

        for (int i = ini; i < ini + content.length; i++) {
            memory[i] = (ByteSSOL) content[aux];
            aux++;
        }

    }

    public Object getMemory(int index) {
        return memory[index];
    }

    public int length() {

        return memory.length;
    }

    private ByteSSOL[] memory;

}
