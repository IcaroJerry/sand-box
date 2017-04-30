package ifba.ads.ssol.processmenager.processo;

public class Processo {

    private Pcb pcb;

    public Processo() {
        pcb = new Pcb();
    }

    public Pcb getPcb() {
        return this.pcb;
    }
}
