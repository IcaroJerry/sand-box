package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.memorymenager.exception.PageFault;
import ifba.ads.ssol.memorymenager.exception.PageNotFoundException;
import ifba.ads.ssol.memorymenager.gerenciadememoria.MemoryMenager;
import ifba.ads.ssol.memorymenager.gerenciadememoria.Pagina;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MemoryFacade {

    private MemoryFacade() {
        memoryMenager = new MemoryMenager();
        obj = new Object[5];
    }

    public IMemoryMenager getMemoryMenager() {
        return this.memoryMenager;
    }

    public static MemoryFacade getInstance() {
        if (instance == null) {
            instance = new MemoryFacade();
        }

        return instance;
    }

    public Object[] verificaEAlocaProcesso(int pcVirtual, String ri, String pID) {
        Object[] obj = new Object[5];
        try {
            obj = this.memoryMenager.verificarPaginaNaRam(pcVirtual, ri, pID);
        } catch (PageFault ex) {
            obj = this.memoryMenager.pageFault(ex.infoPageFault());
            obj[3] = ex.riInfo();

        } catch (PageNotFoundException ex) {
            Logger.getLogger(MemoryFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tô verificando o processo");
        this.obj = obj;
        return obj;
    }

    public int[] getMolduraPagina() {
        return this.memoryMenager.getMemoryMenanger().getMolduraPagina();
    }

    public List<Pagina> getPageList() {
        return this.getMemoryMenager().getPageList();
    }

    public List<Pagina> getFilaFifo() {
        return this.getMemoryMenager().getFilaFifo();
    }

    public Object[] getObj() {
        return obj;
    }

    public void setObj(Object[] obj) {
        this.obj = obj;
    }

    private Object[] obj;
    private static MemoryFacade instance = null;
    private static IMemoryMenager memoryMenager = null;
}
