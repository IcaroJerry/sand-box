package ifba.ads.ssol.memorymenager.gerenciadememoria;

import ifba.ads.ssol.interfaces.IMemoryMenager;
import ifba.ads.ssol.interfaces.SignalProcessManager;
import ifba.ads.ssol.memorymenager.exception.InvalidPositionException;
import ifba.ads.ssol.memorymenager.exception.MemoryFullException;
import ifba.ads.ssol.memorymenager.exception.PageFault;
import ifba.ads.ssol.memorymenager.exception.PageNotFoundException;
import ifba.ads.ssol.shell.Core;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryMenager implements IMemoryMenager {

    public MemoryMenager() {
        this.filaFifo = PageMaker.getInstance().criarPageList();
        this.pageList = PageMaker.getInstance().criarPageList();
        memoryMenanger = AdressConvertion.getInstance();
        tm = 0;
    }

    /**
     * método que verifica se a página do processo está na ram é para ser usado
     * para gerar o page fault vamos retorna a página caso esteja na excessão
     * caso a página não esteja
     *
     * @param processoID
     * @return
     */
    @Override
    public Object[] verificarPaginaNaRam(int pcVirtual, String ri, String processoID) throws PageFault, PageNotFoundException {
        Object[] obj = new Object[4];
        //efetua o calculo do off set para determinar a ppsição real da intrunção na memória              
        obj = this.memoryMenanger.mapeamentoPC(pcVirtual, ri, processoID);
        Pagina pg = this.localizarPagina(processoID, (pcVirtual / 1024));//localiza a página no page list       
        if (pg.getNpr() != -1) {//se o npv != -1 significa que a página está em memória
            obj[2] = (int) obj[2] + pg.getNpr();
            //soma o offset calculado antes com a posição de memória na qual a página foi alocada 
            return obj;//retorna este objeto o qual contém o pcreal, o processoID e o Ri que será utilizado pelo kernel para executar a instrução
        }
        throw new PageFault(pg, ri);  //caso a página não esteja na memória lançamos a exceção para a gerência de disco que irá localizar a mesma em disco    
    }

    /**
     * a posição livre é obtida do memory menanger getFreePosition pegamos a
     * posição de memória livre; setamos a posição na moldura de página para 1
     * (cheio); pegamos o valor desta posição da moldura e convertemos para o
     * valor real através do método do memory menanger
     * molduraDePaginaMapaDeBits, o qual será armazenado na página do processo
     * em questão
     */
    @Override
    public void alocaProcesso(Pagina pgAlocacao, int posicaoFree) throws PageNotFoundException, InvalidPositionException {

        int posicaoLivre = this.memoryMenanger.molduraDePaginaMapaDeBits(posicaoFree);
        //modifica para um no mapa de bits
        this.memoryMenanger.setFreePosition(posicaoLivre);
        pgAlocacao.setNpr(this.memoryMenanger.posicaoRealDePaginaMapaDeBits(posicaoLivre));
        //atualizamos no page list
        int posicaoDapg = this.localizarPaginaDeProcesso(pgAlocacao);
        this.pageList.get(posicaoDapg).setNpr(pgAlocacao.getNpr());//modifico o número da página real
        //preciso saber como o método do kernel faz para alocar isso
        memoryMenanger.getValuePosition();//só imprimo o valor da moldura de página para veririficar a alocação
    }

    @Override
    public void Fifo() throws PageNotFoundException, MemoryFullException, InvalidPositionException {//aloca a
        int posicao = this.filaFifo.get(0).getNpr();
        //libera a posição onde o primeiro elemento está alocado
        this.memoryMenanger.freePosition(this.memoryMenanger.molduraDePaginaMapaDeBits(posicao));
        //atualizamos o valor de npr da página que será removida
        int atualizarPagina = this.localizarPaginaDeProcesso(this.filaFifo.get(0));
        this.pageList.get(atualizarPagina).setNpr(-1);
        //retira da fila o primeiro processo
        System.out.println("removi o processo de npv " + this.filaFifo.get(0).getNpv() + " e id " + this.filaFifo.get(0).getProcessId());
        this.filaFifo.remove(0);
        System.out.println("a posição " + this.memoryMenanger.getFreePosition() + "tá livre");
        System.out.println(" pode alocar kernel");
        System.out.println("o tamanho da fila fifo " + this.filaFifo.size());
        Core.getWindowGerenciaDeMemoria().notifyWindow();
    }

    @Override
    public void addNaFilaFifo(Pagina p) {
        int chave = 0;
        for (int i = 0; i < filaFifo.size(); i++) {
            if (filaFifo.get(i).getProcessId().equalsIgnoreCase(p.getProcessId()) && (filaFifo.get(i).getNpv() == p.getNpv())) {
                chave = 1;
                System.out.println("já foi adicionado");
            }
        }
        if (chave != 1) {
            filaFifo.add(p);
        }
    }

    @Override
    public Pagina localizarPagina(String processID, int pgVirtual) throws PageNotFoundException {
        for (int i = 0; i < this.pageList.size(); i++) {
            if (this.pageList.get(i).getProcessId().equalsIgnoreCase(processID) && this.pageList.get(i).getNpv() == pgVirtual) {
                return this.pageList.get(i);
            }
        }
        throw new PageNotFoundException();
    }

    @Override
    public int localizarPaginaDeProcesso(Pagina pg) throws PageNotFoundException {

        for (int i = 0; i < this.pageList.size(); i++) {
            if (this.pageList.get(i).equals(pg)) {
                return i;
            }
        }
        throw new PageNotFoundException();
    }

    /**
     * quando o processo for criado a gerencia de processos nos manda o tamanho
     * para o qual eu deverei criar as páginas o tamanho será em páginas
     */
    @Override
    public void criarPaginas(String processoID, int tamanhoDoProcesso, String nomeProcesso) {
        /*
         * Mudança do método de criação de página pois carlos via me retornar 
         * o valor em bytes
         */
        Pagina pg = null;
        tm = (int) tamanhoDoProcesso / this.memoryMenanger.getPageSize();
        if (tm == 0) {
            tm = 1;
        }

        for (int i = 0; i < tm; i++) {
            pg = PageMaker.getInstance().criarPagina();
            pg.setProcessName(nomeProcesso);
            pg.setNpv(i);
            pg.setNpr(-1);//especifica que não está em memória
            pg.setProcessId(processoID);
            pageList.add(pg);
        }
    }

    @Override
    public int getPageListSize() {
        return this.pageList.size();
    }

    @Override
    public AdressConvertion getMemoryMenanger() {
        return this.memoryMenanger;
    }

    public List<Pagina> getFilaFifo() {
        return this.filaFifo;
    }

    /*
     * método para matar o processo 
     * remove do page list e libera as posições de memória que ele alocava
     * retira da fila fifo
     */
    public void kill(String processId) {
        for (int i = 0; i < pageList.size(); i++) {
            if (pageList.get(i).equals(processId)) {
                int posicao;
                try {
                    posicao = this.memoryMenanger.molduraDePaginaMapaDeBits(pageList.get(i).getNpr());
                    this.memoryMenanger.freePosition(posicao);
                } catch (InvalidPositionException ex) {
                    System.out.println("posição de memória inválida");
                }

                pageList.remove(i);
            }
        }
        for (int i = 0; i < filaFifo.size(); i++) {
            if (filaFifo.get(i).equals(processId)) {
                filaFifo.remove(i);
            }
        }
    }

    public int[] getMemVirtual() {
        return this.memoryMenanger.getMolduraPagina();
    }

    public Object[] pageFault(Pagina p) {

        Object[] obj = new Object[5];
        try {
            try {
                //pega a posição livre na memória virtual e efetua a conversão para posição real
                memoryPosition = memoryMenanger.posicaoRealDePaginaMapaDeBits(memoryMenanger.getFreePosition());
                obj[2] = memoryPosition;
            } catch (InvalidPositionException ex) {
                Logger.getLogger(MemoryMenager.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (MemoryFullException mfe) {
            try {
                try {
                    //se a memória está cheia chamamos o fifo para liberar memória
                    System.out.println("chamou o fifo");
                    this.Fifo();
                    memoryPosition = memoryMenanger.posicaoRealDePaginaMapaDeBits(memoryMenanger.getFreePosition());
                    obj[2] = memoryPosition;
                    System.out.println("a posição liberada é" + memoryMenanger.getFreePosition());

                } catch (PageNotFoundException ex) {
                    Logger.getLogger(MemoryMenager.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (MemoryFullException ex) {
                System.out.println("é impossível a memória está cheia e chegarmos até aqui");
            } catch (InvalidPositionException ex) {
                Logger.getLogger(MemoryMenager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        obj[1] = p.getProcessId();

        obj[4] = SignalProcessManager.getInstance().requestSectors(p.getProcessId(), p.getNpv());
        obj[0] = 1;//bit de controle que informa a ocorrência de um pagefault

        try {
            this.alocaProcesso(p, (int) obj[2]);
        } catch (PageNotFoundException ex) {
            Logger.getLogger(MemoryMenager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPositionException ex) {
            Logger.getLogger(MemoryMenager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.addNaFilaFifo(p);

        Core.getWindowGerenciaDeMemoria().notifyWindow();
        return obj;
    }

    @Override
    public List<Pagina> getPageList() {
        return this.pageList;
    }

    private List<Pagina> filaFifo;
    public List<Pagina> pageList;
    private AdressConvertion memoryMenanger;
    private int tm;
    private int memoryPosition = 0;

}
/**
 * o kernel vai me mandar um objeto contendo o pcb do processo, do qual eu farei
 * uso do processoID e do pc que contém a próxima instrução a ser executada
 *
 *
 * /**1º vemos se o processo está na ram;
 *
 * kernel deseja executar uma dada instrução de um processo contida no pc;
 * gerencia passa para o kernel a id do processo e a instrução(linha); o kernel
 * pergunta para a gerência de memória se o processo está na ram; Se estiver o
 * processo é executado; se não é gerado um page fault; de posse da informação
 * do page fault o kernel pergunta para o sistema de arquivos qual o tamanho da
 * página e qual é a página que contém a instrução a ser executada; o sistema de
 * arquivos devolve o tamanho da página, qual é a página e a id do processo; de
 * posse desses dados o kernel repassa-os para a gerencia de memória a gerencia
 * vê se tem espaço na ram; se tiver aloca, seta na tabela da pag o endereço da
 * ram onde a página foi alocada e informo que a página está alocada para o
 * kernel;
 *
 *
 * se estiver executa; se não estiver gera o page fault e ver se tem espaço na
 * ram;
 *
 * se tiver eu aloco o processo, "seto" a posição de memória para um; "seto" na
 * tabela de paginas o número da página real se não tiver chama o algoritmo de
 * substituição de páginas *
 *
 */
