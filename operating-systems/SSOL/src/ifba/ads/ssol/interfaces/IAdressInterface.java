package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.memorymenager.exception.InvalidPositionException;
import ifba.ads.ssol.memorymenager.exception.MemoryFullException;

public interface IAdressInterface {

    void freePosition(int position);

    int getFreePosition() throws MemoryFullException;

    int getMolduraSize();

    int getPageSize();

    void getValuePosition();

    /**
     * verifica se exite alguma posição de memória livre
     */
    boolean isFree() throws MemoryFullException;

    /**
     * retorna um objeto do tipo PaginaOffset o qual contém o número do offset e
     * o número da página onde o processo se encontra o número da página indica
     * onde o processo se encontra e o offset deve ser somado ao endereço de
     * memória física para localizar onde exatamente a ins- trução se encontra
     * na memória física
     *
     * @param
     * @return
     */
    Object[] mapeamentoPC(int pcVirtual, String rI, String pId);

    /*
     * recebe os setores do disco e devolve para o kernel
     */
    int molduraDePaginaMapaDeBits(int position) throws InvalidPositionException;

    /**
     * pegamos a posição correspondente no mapa de bits e retornamos a posição
     * de memória física a partir da qual deveremos alocar ou remover o processo
     */
    int posicaoRealDePaginaMapaDeBits(int position) throws InvalidPositionException;

    /*
     * dada uma página real retornamos a moldura da página
     */
    void setFreePosition(int position);

    int[] setorPage(int bl1, int bl2);
}
