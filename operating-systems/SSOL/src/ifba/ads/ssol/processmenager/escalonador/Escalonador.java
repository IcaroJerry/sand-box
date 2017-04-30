package ifba.ads.ssol.processmenager.escalonador;

import ifba.ads.ssol.processmenager.processo.Processo;
import java.util.ArrayList;

public class Escalonador {

    private static ArrayList<Processo> filaPronto = new ArrayList();
    private static ArrayList<String> processoCriado = new ArrayList();
    private static ArrayList<String> nomeProcessoFinalizar = new ArrayList();
    private static Escalonador escalonador;
    private static Processo currentProcess;

    private Escalonador() {
    }

    public static Escalonador getInstance() {
        if (escalonador == null) {
            escalonador = new Escalonador();
        }
        return escalonador;
    }

    public Processo getCurrentProcess() {
        return currentProcess;
    }

    public ArrayList<String> getProcessoCriado() {
        return processoCriado;
    }

    public ArrayList<Processo> getFilaPronto() {
        return filaPronto;
    }

    public boolean verificadorRemocao() throws Exception {
        if (this.filaPronto.isEmpty()) {
            throw new Exception("Não existem proessos para serem finalizados");
        } else {
            return false;
        }
    }

    public void solicitarRemocao(String nome) {
        this.nomeProcessoFinalizar.add(nome);
        removerProcessoCriado(nome);
    }

    public void pronto(Processo a) throws Exception {
        processoCriado.add(a.getPcb().getNomeProcesso());
        filaPronto.add(a);
    }

    public void prontoExectando() throws Exception {

        while (true) {
            if (filaPronto.isEmpty() == false) {
                Processo exe = filaPronto.get(0);

                executando(exe);

                filaPronto.remove(0);
            }
            Thread.sleep(1);
        }
    }

    private void executando(Processo x) throws Exception {

        if (finalizarProcesso(x.getPcb().getNomeProcesso()) == true) {
            x = null;
            return;
        }

        x.getPcb().setEstadoProcesso("Executando");
        x.getPcb().setTempoCpuProcesso(1);
        Thread.sleep(1000);//PASSA PARA A CPU EXECUTAR.

        x.getPcb().setEstadoProcesso("Pronto");
        filaPronto.add(x);
    }

    private boolean finalizarProcesso(String nome) {
        int tamanho = this.nomeProcessoFinalizar.size();
        for (int i = 0; i < tamanho; i++) {
            if (this.nomeProcessoFinalizar.get(i).equals(nome)) {
                this.nomeProcessoFinalizar.remove(i);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void removerProcessoCriado(String nome) {
        int tamanho = processoCriado.size();
        for (int i = 0; i < tamanho; i++) {
            if (processoCriado.get(i).equalsIgnoreCase(nome)) {
                processoCriado.remove(i);
                return;
            }
        }
    }
}
