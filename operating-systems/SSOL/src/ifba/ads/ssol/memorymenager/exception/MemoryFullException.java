package ifba.ads.ssol.memorymenager.exception;

public class MemoryFullException extends Exception {

    public String infoMemoryFull() {
        return "Memoria cheia iniciar algoritmo de substituição de página";
    }
}
