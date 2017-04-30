package ifba.ads.ssol.microkernel;

import ifba.ads.ssol.interfaces.IFileManagement;
import ifba.ads.ssol.interfaces.IMemoryManagement;
import ifba.ads.ssol.interfaces.IProcessManagement;
import ifba.ads.ssol.interfaces.ICoreVirtualMachine;
import ifba.ads.ssol.interfaces.IFileMicroKernel;
import ifba.ads.ssol.interfaces.IMemoryMicroKernel;
import ifba.ads.ssol.interfaces.IMicroKernel;
import ifba.ads.ssol.interfaces.IProcessMicroKernel;
import javax.swing.JOptionPane;

/**
 * Conexão Kernel -> Gerências
 *
 *
 */
public class MicroKernel extends IMicroKernel implements IFileManagement, IProcessManagement, IMemoryManagement {

    //<editor-fold defaultstate="collapsed" desc="Gerência de Processos -> Kernel">
    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Gerência de Memória -> Kernel">
    @Override
    public void pageFault(int[] indexSector) {
        //ICoreVirtualMachine.getInstance().getProjectController().seekContentSector(indexSector);
        //int index = indexMemoryAlloc()   - Solicita da gerencia de memoria o local onde alocaremos o conteudo.         
        //ICoreVirtualMachine.getInstance().getProjectController().allocRAM(index);

        //<editor-fold defaultstate="collapsed" desc="DESCRIÇÃO PAGEFAULT">
        /*   
      *  @@ - GERENCIA DE PROCESSOS, AO RECEBER O COMANDO NEXTPROCESS() DO KERNEL, 
      *       FORNECE UM NOVO PROCESSO A SER EXECUTADO.
      *  @@ - ANTES DE PASSAR ESTE PROCESSO PARA O KERNEL, A G.P ENTRA EM CONTATO COM A GM
      *       PARA VERIFICAR SE A PAGINA ESTA EM MEMORIA. SE ESTIVER, OK, PROCESSO NORMAL.
      *       SE NÃO ESTIVER, A G.M RETORNA PARA A G.P QUE A PAGINA NÃO ESTA NA RAM.
      *  @@ - G.M INFORMA EM QUAL SETOR DO DISCO FISICO ESTA A PAGINA.
      *  @@ - G.P GERA PAGEFAULT PARA O KERNEL PASSANDO OS DADOS DO SETORES FISICOS DO DISCO
      *  @@ -  KERNEL USA UMA CONEXAO COM A G.A PASSANDO ESTES SETORES PARA A GERENCIA DE ARQUIVOS 
      *        QUE RETORNA O CONTEUDO DOS RESPECTIVOS SETORES.
      * 
      *  ATENÇÃO! PODEMOS TER DUAS SITUAÇÕES:
      * @@ -  SITUAÇÃO 1 - KERNEL CHAMA A G.M PASSANDO AS PAGINAS QUE FORAM
      *       RETORNADAS DA G.A.
      * @@ -  A G.M VERIFICA SE TEM ESPAÇO NA RAM. SE TIVER, CHAMA O NOSSO METODO
      *  allocPage() PASSANDO AS PAGINAS COMO PARAMETRO. **LEMBRANDO QUE ALLOCPAGE JA É UM METODO
      *  APLICADO DIRETAMENTE NA MAQUINA VIRTUAL.
      *  SE NÃO TIVER ESPAÇO, A GM CHAMA O METODO REMOVEPAGE() DO KERNEL.
      *  DEPOIS INVOCA O ALLOCPAGE() PARA ALOCAR A OVA PAGINA.
      *  UMA VEZ COM A PAGINA EM MEMORIA, O PROCESSO PODE SER EXECUTADO NORMALMENTE.
      *  
      *@@ -   SITUAÇÃO 2 - KERNEL CHAMA GERENCIA DE MEMORIA INFORMANDO O TAMANHO DO ARQUIVO A SER ALOCADO
      *  A GERENCIA DE MEMORIA VERIFICA SE POSSUI ESSE ESPAÇO DISPONIVEL NA RAM E RETORNA ALGUMA COISA(BOLLEANO SERIA BOM).
      *  SE TIVER, RETORNA PARA O KERNEL O ENDEREÇO ONDE ESSA PAGINA DEVE SER INSERIDA NA RAM.
      *  KERNEL ACIONA O METODO ALLOCPAGE() E REALIZA ALOCAÇÃO DIRETAMENTE NA MEMORIA RAM. 
      *  ESSE ME PARECE O MAIS CORRETO.)
      *
      * 
         */
        //</editor-fold>  
        //Object[] contentDisk =  fileComunication.getContentSector(indexSector);
        //memoryComunication.(contentDisk);
    }

    @Override
    public void allocPage(int init, Object[] page) {
        ICoreVirtualMachine.getInstance().getProjectController().allocRAM(init, page);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Gerência de Arquivos -> Kernel">
    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc=" Métodos Internos do Kernel">
    @Override
    public void contextSwitch() {
//        outProcess();
//        Object[] dataProcess = processComunication.next();
//        inputProcess(dataProcess);  
        JOptionPane.showMessageDialog(null, "Ocorreu uma troca de contexto");
    }

    /**
     * Dispachante de saida do processo. Salva o estado atual de execução. Os
     * valores dos registradores RI E PC possibilitam que o programa retorne sua
     * execução de onde parou.
     */
    private void outProcess() {

        Object[] dataProcess = new Object[2];

        dataProcess[0] = ICoreVirtualMachine.getInstance().getProjectController().valueRI();
        dataProcess[1] = ICoreVirtualMachine.getInstance().getProjectController().valuePC();

        processComunication.saveData(dataProcess);//QUEM CONVERTE O RI E PC REAL É A GERENCIA DE MEMORIA?
        //OU PASSAMOS O VALOR REAL?  
    }

    /**
     * Dispachante de entrada do processo. Prepara os procedimentos necessário
     * para execução do novo processo a ser executado.
     *
     * @param dataProcess Dados do processo a ser executado.
     */
    private void inputProcess(Object[] dataProcess) {
        ICoreVirtualMachine.getInstance().getProjectController().valueRI(dataProcess[0]);
        ICoreVirtualMachine.getInstance().getProjectController().valuePC(dataProcess[1]);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Atributos de Comunicação com as Gerências">
    private IFileMicroKernel fileComunication;
    private IMemoryMicroKernel memoryComunication;
    private IProcessMicroKernel processComunication;

    public MicroKernel() {
        ///instance = new MicroKernel();
        fileComunication = new FileMicroKernel();
        memoryComunication = new MemoryMicroKernel();
        processComunication = new ProcessMicroKernel();
    }
    //</editor-fold>
}
