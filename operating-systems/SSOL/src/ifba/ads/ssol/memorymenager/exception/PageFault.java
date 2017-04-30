package ifba.ads.ssol.memorymenager.exception;

import ifba.ads.ssol.memorymenager.gerenciadememoria.Pagina;

public class PageFault extends Exception {

    /**
     * retorna os dados para tratamento quando ocorre o pageFault
     */
    public PageFault(Pagina pg, String ri) {
        this.pg = pg;
        this.ri = ri;
    }

    public Pagina infoPageFault() {
        return pg;
    }

    public String riInfo() {
        return this.ri;
    }
    private String ri;
    private Pagina pg;
}
