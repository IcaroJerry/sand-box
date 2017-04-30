package ifba.ads.ssol.interfaces;

public abstract class ICoreVirtualMachine {

    /**
     * @return API de funcionabilidades gráficas.
     */
    public abstract IUIController getUIController();

    /**
     * @return API de funcionabilidades internas do projeto.
     */
    public abstract IProjectController getProjectController();

//<editor-fold defaultstate="collapsed" desc="Construção do Singleton">
    public static ICoreVirtualMachine getInstance() {
        return instance;
    }

    protected ICoreVirtualMachine() {
    }
    protected static ICoreVirtualMachine instance;
//</editor-fold>
}
