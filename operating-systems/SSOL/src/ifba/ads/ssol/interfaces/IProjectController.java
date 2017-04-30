package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.filemenager.fat.Sector;

public interface IProjectController {

    /**
     * @return Conteúdo atual do Registrador de Instrução.
     */
    public Object valueRI();

    /**
     *
     * @return Conteúdo atual do Registrador Contador de Programa.
     */
    public int valuePC();

    /**
     * Atualiza o Registrador de Instrução.
     *
     * @param newValue Conteúdo atual do Registrador de Instrução.
     */
    public void valueRI(Object newValue);

    /**
     * Atualiza o Registrador Contador de Programa.
     *
     * @param newValue Conteúdo atual do Registrador Contador de Programa.
     */
    public void valuePC(Object newValue);

    //public Object[] seekContentSector(int[] indexSector);
    /**
     * Converte uma String em um array de ByteSSOL do tamanho de um RI(Word)
     *
     * @param value
     * @return
     */
    public ByteSSOL[] ConvertToByteArray(String value);

    public ByteSSOL[] ConvertToByteArray(Sector[] value);

    public void allocRAM(int initiation, Object[] content);

    public void allocHD(int initiation, Object[] content);

    public static int VALUE_DELAY = 100; // tempo de execução de instrução.
    public static int NUM_QUATUM = 256; // quatum é medido por palavras (neste caso). 
    public static int MEMORY_SIZE = 16; // quantidade total da memória.
    public static int BYTE_PER_PAGE = 1024; //quantidade de bytes por páginas
    public static int WORD_SIZE = 2; // tamanho da palavra por Bytes.
    public static int BYTE_SIZE = 8; // tamanho do byte.
    public static final int SECTOR_SIZE = 512; // tamanho do setor.
    public static final int DISK_SIZE = 32; //tamanho do disco.

}
