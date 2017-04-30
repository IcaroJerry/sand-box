package ifba.ads.ssol.processmenager.facede;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.interfaces.ICoreVirtualMachine;
import ifba.ads.ssol.interfaces.IProjectController;
import ifba.ads.ssol.interfaces.MemoryFacade;
import ifba.ads.ssol.interfaces.SignalProcessManager;
import ifba.ads.ssol.processmenager.escalonador.Escalonador;
import ifba.ads.ssol.processmenager.processo.GerenciaProcesso;
import ifba.ads.ssol.processmenager.processo.Processo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ifba.ads.ssol.virtualmachine.CoreVirtualMachine;
import ifba.ads.ssol.virtualmachine.ProjectController;
import ifba.ads.ssol.virtualmachine.model.core.RandomAccessMemory;

public class Facede {

    private static Facede fachada;
    private int verificador = 0;
    private static Processo currentProcess;
    private static Escalonador escalonador = Escalonador.getInstance();

    private Facede() {
        new DisparadorThread().start();
    }

    public static Facede getInstance() {
        if (fachada == null) {
            fachada = new Facede();
        }
        return fachada;
    }

    public Processo criarProcesso(File arquivo) throws FileNotFoundException, Exception {
        Processo processo = GerenciaProcesso.getInstance().criarProcesso(arquivo);
        if (SignalProcessManager.getInstance().recieveProcessData(processo.getPcb().getPid(), processo.getPcb().getNomeProcesso(), processo.getPcb().getCaminhoProcesso(), processo.getPcb().getTamanhoProcessoByte())) {
            MemoryFacade.getInstance().getMemoryMenager().criarPaginas(processo.getPcb().getPid(), processo.getPcb().getTamanhoProcessoByte(), processo.getPcb().getNomeProcesso());

            executar(processo);
            pronto(processo);
            return processo;
        }

        throw new Exception("Volume do Disco não suporta este processo!");
    }

    public void executar(Processo p) throws Exception {
        Object[] obj = new Object[5];
        obj = MemoryFacade.getInstance().verificaEAlocaProcesso(p.getPcb().getPc(), p.getPcb().getRiProcesso(), p.getPcb().getPid());

        int pageFault = (int) obj[0];

        if (pageFault == 1) //PageFault
        {
            ICoreVirtualMachine.getInstance().getProjectController().valuePC((int) obj[2]);
            ICoreVirtualMachine.getInstance().getProjectController().allocRAM((int) obj[2], (Object[]) obj[4]);
            ICoreVirtualMachine.getInstance().getProjectController().valueRI(ICoreVirtualMachine.getInstance().getProjectController().ConvertToByteArray(String.valueOf(obj[3])));
        } else {
            ICoreVirtualMachine.getInstance().getProjectController().valuePC((int) obj[2]);
            ICoreVirtualMachine.getInstance().getProjectController().valueRI(ICoreVirtualMachine.getInstance().getProjectController().ConvertToByteArray(String.valueOf(obj[3])));
        }

    }

    public Escalonador getEscalonador() {
        return Escalonador.getInstance();
    }

    public void pronto(Processo processo) throws Exception {
        Escalonador.getInstance().pronto(processo);

    }

    public boolean verificarRemocaoProcesso() throws Exception {
        return Escalonador.getInstance().verificadorRemocao();
    }

    public void solcitarRemocaoProcesso(String id) {
        Escalonador.getInstance().solicitarRemocao(id);
        // Escalonador.getInstance().removerProcessoCriado(id);
    }

    public ByteSSOL[] teste() {
        ByteSSOL[] content = new ByteSSOL[ProjectController.MEMORY_SIZE * 1024];
        int aux = 2;
        int i;

        for (i = 0; i < content.length; i++) {
            content[i] = new ByteSSOL("01010100");//00000000
            if (i == 0 || i == 1 || i == 19 || i == 20) {
                content[i] = new ByteSSOL("00000000");
            }
        }

        return content;

    }

    public class DisparadorThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Escalonador.getInstance().prontoExectando();

                } catch (Exception ex) {
                    Logger.getLogger(Facede.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
