package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.memorymenager.gerenciadememoria.AdressConvertion;
import ifba.ads.ssol.memorymenager.gerenciadememoria.Pagina;
import ifba.ads.ssol.memorymenager.exception.InvalidPositionException;
import ifba.ads.ssol.memorymenager.exception.MemoryFullException;
import ifba.ads.ssol.memorymenager.exception.PageFault;
import ifba.ads.ssol.memorymenager.exception.PageNotFoundException;
import java.util.List;

public interface IMemoryMenager {

    public Object[] pageFault(Pagina p);

    void Fifo() throws PageNotFoundException, MemoryFullException, InvalidPositionException;

    public List<Pagina> getPageList();

    void addNaFilaFifo(Pagina p);

    /**
     * a posição livre é obtida do memory menanger getFreePosition pegamos a
     * posição de memória livre; setamos a posição na moldura de página para 1
     * (cheio); pegamos o valor desta posição da moldura e convertemos para o
     * valor real através do método do memory menanger
     * molduraDePaginaMapaDeBits, o qual será armazenado na página do processo
     * em questão
     */
    void alocaProcesso(Pagina pgAlocacao, int posicaoFree) throws PageNotFoundException, InvalidPositionException;

    public List<Pagina> getFilaFifo();

    /**
     * quando o processo for criado a gerencia de processos nos manda o tamanho
     * para o qual eu deverei criar as páginas o tamanho será em páginas
     */
    void criarPaginas(String processoID, int tamanhoDoProcesso, String nomeProcesso);

    AdressConvertion getMemoryMenanger();

    int getPageListSize();

    Pagina localizarPagina(String processID, int pgVirtual) throws PageNotFoundException;

    int localizarPaginaDeProcesso(Pagina pg) throws PageNotFoundException;

    /**
     * método que verifica se a página do processo está na ram é para ser usado
     * para gerar o page fault vamos retorna a página caso esteja na excessão
     * caso a página não esteja
     *
     * @param processoID
     * @return
     */
    Object[] verificarPaginaNaRam(int pcVirtual, String ri, String processoID) throws PageFault, PageNotFoundException;
}
