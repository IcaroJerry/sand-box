package ifba.ads.ssol.interfaces;

public interface IMemoryManagement {

    //<editor-fold defaultstate="collapsed" desc="Gerência de Memória -> Kernel">
    /**
     * Utilizado quando um processo é alocado para execução, mas existe alguma
     * pagina que não esta alocada na memoria RAM. Este metodo estabelece as
     * conexões necessarias com as gerencias perifericas para dar continuidade
     * ao processamento.
     *
     * @param indexSector Indice do Setor da Memória Física onde está contido a
     * página .
     */
    public void pageFault(int[] indexSector);

    // public int indexMemoryAlloc();
    // public void allocPage(int init);
    /**
     * Aloca uma página na Memoria RAM a partir do inicio informado e do
     * conteudo a ser alocado.
     *
     * @param init = inicio da pagina.
     * @param page = conteudo da pagina.
     */
    public void allocPage(int init, Object[] page);
    //</editor-fold>        

}
