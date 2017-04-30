package ifba.ads.ssol.interfaces;

/**
 * Conexão Kernel -> Gerência de Arquivos
 *
 */
public interface IFileMicroKernel {

    //<editor-fold defaultstate="collapsed" desc="Kernel -> Gerência de Arquivos">
    public Object[] getContentSector(int indexSector);

    //</editor-fold>   
}
