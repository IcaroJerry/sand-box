package ifba.ads.ssol.interfaces;

/**
 * Conexão Kernel -> Gerência de Processos
 *
 */
public interface IProcessMicroKernel {

    /**
     * Próximo processo a ser executado.
     *
     * @return Dados do novo processo a ser executado.
     */
    public Object[] next();

    /**
     * Salva os dados atuais do processo para saber de onde voltar na próxima
     * vez em que ele será executado.
     *
     * @param dataProcess Dados necessário para salvar o processo.
     */
    public void saveData(Object[] dataProcess);

}
