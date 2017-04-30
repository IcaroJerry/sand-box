package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.microkernel.MicroKernel;

/**
 * Conexão VirtualMachine -> Kernel
 *
 *
 */
public abstract class IMicroKernel {

    /**
     * Utilizado para modificar o processo executado pela CPU. Executando todos
     * procedimentos necessários para troca de contexto.
     */
    public abstract void contextSwitch();

    //<editor-fold defaultstate="collapsed" desc="Construção do Singleton">
    public static IMicroKernel getInstance() {
        if (instance == null) {
            instance = new MicroKernel();
        }
        return instance;
    }

    protected IMicroKernel() {
    }

    protected static IMicroKernel instance = null;
//</editor-fold>  
}
