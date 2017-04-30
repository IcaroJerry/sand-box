package ifba.ads.ssol.processmenager.processo;

public class Pcb {

    private String pid;
    private String nome;
    private String prioridade;
    private String estado;
    private int tempoCpc;
    private String tipoProcesso;
    private int pc;
    private String ri;
    private String caminhoProcesso;
    private int tamanhoProcessoByte;
    private int tamanhoProcessoBit;
    private int quantidadeInstrucoes;

    public Pcb() {

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNomeProcesso() {
        return nome;
    }

    public void setNomeProcesso(String nome) {
        this.nome = nome;
    }

    public String getPrioridadeProcesso() {
        return prioridade;
    }

    public void setPrioridadeProcesso(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getEstadoProcesso() {
        return estado;
    }

    public void setEstadoProcesso(String estado) {
        this.estado = estado;
    }

    public int getTempoCpuProcesso() {
        return this.tempoCpc;
    }

    public void setTempoCpuProcesso(int tempo) {
        this.tempoCpc += tempo;
    }

    public String getTipoProcesso() {
        return this.tipoProcesso;
    }

    public void setTipoProcesso(String tipo) {
        this.tipoProcesso = tipo;
    }

    public int getPc() {
        return pc;
    }

    public void setPcProcesso(int pc) {
        this.pc = pc;
    }

    public String getRiProcesso() {
        return ri;
    }

    public void setRiProcesso(String ri) {
        this.ri = ri;
    }

    public String getCaminhoProcesso() {
        return caminhoProcesso;
    }

    public void setCaminhoProcesso(String caminhoProcesso) {
        this.caminhoProcesso = caminhoProcesso;
    }

    public int getTamanhoProcessoByte() {
        return tamanhoProcessoByte;
    }

    public void setTamanhoProcessoByte(int tamanhoProcessoByte) {
        this.tamanhoProcessoByte = tamanhoProcessoByte;
    }

    public int getTamanhoProcessoBit() {
        return tamanhoProcessoBit;
    }

    public void setTamanhoProcessoBit(int tamanhoProcessoBit) {
        this.tamanhoProcessoBit = tamanhoProcessoBit;
    }

    public int getQuantidadeInstrucoes() {
        return this.quantidadeInstrucoes;
    }

    public void setQuantidadeInstrucoes(int quantidade) {
        this.quantidadeInstrucoes = quantidade;
    }
}
