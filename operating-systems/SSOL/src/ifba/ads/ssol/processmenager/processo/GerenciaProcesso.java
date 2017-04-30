package ifba.ads.ssol.processmenager.processo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Integer;

public class GerenciaProcesso {

    private static GerenciaProcesso gerencia;
    private ArrayList<Integer> listaPid = new ArrayList();

    private GerenciaProcesso() {
    }

    public static GerenciaProcesso getInstance() {
        if (gerencia == null) {
            gerencia = new GerenciaProcesso();
        }
        return gerencia;
    }

    private int gerarIdProcesso() {
        //String pid = ""+ ( (int) (Math.random()* 3000));
        int pid = 0;
        int tamanho = listaPid.size();

        if (listaPid.isEmpty() == true) {
            listaPid.add(1);
            return listaPid.get(0);
        } else {
            pid = listaPid.get((tamanho - 1));
            pid += 1;
            listaPid.add(pid);
            return listaPid.get(tamanho);
        }
    }

    private String criarNomeProcesso(File arquivo) {
        char letra;
        String nomeProcesso = arquivo.getName();
        int tamanho = nomeProcesso.length() - 4;
        String nome = "";
        for (int i = 0; i < tamanho; i++) {
            letra = nomeProcesso.charAt(i);
            nome += letra;
        }
        nomeProcesso = nome.concat(".exe");
        return nomeProcesso;
    }

    private int tamanhoProcessoBit(File arquivo) throws FileNotFoundException {
        BufferedReader arquivoB = new BufferedReader((new FileReader(arquivo)));
        int tamanho = 0;
        try {
            while (arquivoB.ready()) {
                tamanho += arquivoB.readLine().length();
            }
        } catch (IOException ex) {
            Logger.getLogger(GerenciaProcesso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tamanho;
    }

    private int quantidadeInstrucoes(File arquivo) throws FileNotFoundException {
        return (tamanhoProcessoBit(arquivo) / 16);
    }

    private int tamanhoProcessoByte(File arquivo) throws FileNotFoundException {
        return (tamanhoProcessoBit(arquivo) / 8);
    }

    public Processo criarProcesso(File arquivo) throws FileNotFoundException {
        String nomeProcesso = criarNomeProcesso(arquivo);
        String caminho = "" + arquivo;
        String pid = "" + gerarIdProcesso();
        int tamanhoBit = tamanhoProcessoBit(arquivo);
        int tamanhoByte = tamanhoProcessoByte(arquivo);
        int quantidadeInstrucoes = quantidadeInstrucoes(arquivo);
        Processo processo = new Processo();
        processo.getPcb().setPid(pid);
        processo.getPcb().setEstadoProcesso("Pronto");
        processo.getPcb().setPrioridadeProcesso("0");
        processo.getPcb().setTempoCpuProcesso(0);
        processo.getPcb().setTipoProcesso("CPU");
        processo.getPcb().setNomeProcesso("" + nomeProcesso);
        processo.getPcb().setCaminhoProcesso(caminho);
        processo.getPcb().setTamanhoProcessoBit(tamanhoBit);
        processo.getPcb().setTamanhoProcessoByte(tamanhoByte);
        processo.getPcb().setRiProcesso("0000000000000000");
        processo.getPcb().setQuantidadeInstrucoes(quantidadeInstrucoes);
        processo.getPcb().setPcProcesso(1);
        return processo;
    }

}
