package ifba.ads.ssol.shell;

import ifba.ads.ssol.shell.windows.TelaPCB;
import ifba.ads.ssol.shell.windows.TelaGerenciaDeMemoria;
import ifba.ads.ssol.shell.windows.TelaProcesso;
import ifba.ads.ssol.shell.windows.TelaGerenciaDeDisco;
import ifba.ads.ssol.shell.windows.TelaEEV;
import ifba.ads.ssol.interfaces.IUIController;
import ifba.ads.ssol.virtualmachine.UIController;

public class Core {

    public static TelaPCB getWindowTelaPCB() {
        return windowTelaPCB;
    }

    public static TelaGerenciaDeMemoria getWindowGerenciaDeMemoria() {
        return windowGerenciaDeMemoria;
    }

    public static TelaGerenciaDeDisco getWindowGerenciaDeDisco() {
        return windowGerenciaDeDisco;
    }

    public static TelaEEV getWindowEEV() {
        return windowEEV;
    }

    public static TelaProcesso getWindowProcesso() {
        return windowProcesso;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        uiController = new UIController();

        windowTelaPCB = new TelaPCB();
        windowGerenciaDeMemoria = new TelaGerenciaDeMemoria();
        windowGerenciaDeDisco = new TelaGerenciaDeDisco();
        windowEEV = new TelaEEV();
        windowProcesso = new TelaProcesso();

        windowGerenciaDeMemoria.setLocation(0, 400);
        windowGerenciaDeDisco.setLocation(700, 0);
        windowTelaPCB.setLocation(900, 462);
        windowEEV.setLocation(500, 462);
        windowProcesso.setSize(600, 400);

        uiController.initializeCPU();
        windowProcesso.setVisible(true);
    }

    private static TelaProcesso windowProcesso;
    private static TelaPCB windowTelaPCB;
    private static TelaGerenciaDeMemoria windowGerenciaDeMemoria;
    private static TelaGerenciaDeDisco windowGerenciaDeDisco;
    private static TelaEEV windowEEV;
    private static IUIController uiController;
}
