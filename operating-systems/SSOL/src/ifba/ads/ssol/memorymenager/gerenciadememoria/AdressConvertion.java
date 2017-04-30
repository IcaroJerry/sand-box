package ifba.ads.ssol.memorymenager.gerenciadememoria;

import ifba.ads.ssol.interfaces.IAdressInterface;
import ifba.ads.ssol.memorymenager.exception.InvalidPositionException;
import ifba.ads.ssol.memorymenager.exception.MemoryFullException;

public class AdressConvertion implements IAdressInterface {

    /**
     * @param args the command line arguments
     */
    private AdressConvertion() {
        molduraDePagina = new int[16];
        for (int i = 0; i < molduraDePagina.length; i++)//libera espaço na memória quando for criado
        {
            molduraDePagina[i] = 0;
        }
        setores = new int[2];
        pageSize = 1024;
        memorySize = 16384;
        memoryLength = 16;
    }

    /**
     * verifica se exite alguma posição de memória livre
     */
    @Override
    public boolean isFree() throws MemoryFullException {
        for (int i = 0; i <= molduraDePagina.length - 1; i++) {
            if (molduraDePagina[i] == 0) {
                return true;
            }
        }
        throw new MemoryFullException();
    }

    @Override
    public int getFreePosition() throws MemoryFullException {
        for (int i = 0; i < molduraDePagina.length; i++) {
            if (molduraDePagina[i] == 0) {
                return i;
            }
        }
        throw new MemoryFullException();//quando houver um memory full exception devemos chamar o algoritimo de substituição de página
    }

    @Override
    public void freePosition(int position) {//libera a posição de memoria
        molduraDePagina[position] = 0;
    }

    @Override
    public void setFreePosition(int position) {
        molduraDePagina[position] = 1;
    }

    @Override
    public void getValuePosition() {
        for (int i = 0; i < molduraDePagina.length; i++) {

            System.out.println("o valor de " + i + " é" + molduraDePagina[i]);

        }
    }

    /**
     * pegamos a posição correspondente no mapa de bits e retornamos a posição
     * de memória física a partir da qual deveremos alocar ou remover o processo
     */
    @Override
    public int posicaoRealDePaginaMapaDeBits(int position) throws InvalidPositionException {
        int i = position * pageSize;
        if (i <= memorySize) {
            return i;
        }
        throw new InvalidPositionException();

    }

    /*
     * dada uma página real retornamos a moldura da página
     */
    @Override
    public int molduraDePaginaMapaDeBits(int position) throws InvalidPositionException {
        int i = position / pageSize;
        if (i < memoryLength) {
            return i;
        }
        throw new InvalidPositionException();

    }

    public int[] getMolduraPagina() {
        return this.molduraDePagina;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public int getMolduraSize() {
        return molduraDePagina.length;
    }

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
    @Override
    public Object[] mapeamentoPC(int pcVirtual, String rI, String pId) {
        Object[] obj = new Object[5];
        obj[0] = -1;
        obj[1] = pId;
        obj[2] = pcVirtual % pageSize;
        obj[3] = rI;
        return obj;
    }

    /*
     * recebe os setores do disco e devolve para o kernel
     */
    @Override
    public int[] setorPage(int bl1, int bl2) {
        setores[0] = bl1;
        setores[1] = bl2;
        return setores;
    }

    public int getMemorySize() {
        return this.memorySize;
    }

    public static AdressConvertion getInstance() {
        if (adressConverter == null) {
            adressConverter = new AdressConvertion();
        }
        return adressConverter;
    }
    private static AdressConvertion adressConverter;
    private int[] molduraDePagina;
    private int[] setores;
    private int pageSize;
    private int memorySize;
    private int memoryLength;
    // private GerenciaDeMemoria memoryMenanger;
}
