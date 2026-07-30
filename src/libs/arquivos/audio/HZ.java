package libs.arquivos.audio;

import libs.arquivos.binario.Arquivador;

import javax.sound.sampled.*;
import java.util.Arrays;

public class HZ {

    // Gerenciador de blocos para leitura sob demanda
    protected static class GerenciadorBlocos {
        String caminhoArquivo;
        int numBlocos;
        long[] deslocamentoBlocos;
        int tamanhoDescompactado;
        int tamanhoCompactado;
        byte[] blocoAtual;
        int indiceAtual;

        public GerenciadorBlocos(String caminho) {
            this.caminhoArquivo = caminho;
            this.indiceAtual = -1;
            this.blocoAtual = new byte[0];
        }

        public byte[] lerBloco(int indice) {
            if (indice == indiceAtual) {
                return blocoAtual;
            }
            // Lê o bloco específico do arquivo
            this.indiceAtual = indice;
            return blocoAtual;
        }
    }

    protected long au_total = 0;
    protected long au_lendo = 0;

    protected int mais;
    protected byte[] buffer;
    protected boolean terminou;

    protected RefInt ptr;
    protected AudioFormat mAudio;
    protected DataLine.Info info;
    protected SourceDataLine mLinhaDeAudio;

    protected byte[] au_buffer;
    protected byte[] originais;
    protected byte[] copiar;

    protected boolean continuar;
    protected String eArquivoHZ;
    protected GerenciadorBlocos gerenciadorBlocos;

    public HZ(String eArquivo) {
        this(eArquivo, HZControlador.FORMATO_PADRAO);
    }

    public HZ(String eArquivo, AudioFormat formPadrao) {
        au_total = 0;
        au_lendo = 0;

        mais = 0;
        buffer = new byte[256];
        terminou = false;

        mAudio = formPadrao != null ? formPadrao : HZControlador.FORMATO_PADRAO;
        info = new DataLine.Info(SourceDataLine.class, mAudio);

        continuar = true;
        mLinhaDeAudio = null;
        abrirLinhaAudio(mAudio);

        eArquivoHZ = eArquivo;
        gerenciadorBlocos = new GerenciadorBlocos(eArquivo);

        HZControlador.DadosAudioHz dados = HZControlador.carregarAudio(eArquivo);
        if (dados != null && dados.getDados() != null) {
            au_buffer = Arrays.copyOf(dados.getDados(), dados.getDados().length);
            au_total = au_buffer.length;
            if (dados.getFormato() != null) {
                mAudio = dados.getFormato();
                abrirLinhaAudio(mAudio);
            }
        } else {
            au_buffer = new byte[0];
            au_total = 0;
        }

        originais = new byte[256];
        copiar = new byte[256];
        Arrays.fill(originais, (byte) 0);
        Arrays.fill(copiar, (byte) 0);
        Arrays.fill(buffer, (byte) 0);

        ptr = new RefInt();
    }

    private void abrirLinhaAudio(AudioFormat format) {
        if (mLinhaDeAudio != null) {
            try {
                mLinhaDeAudio.stop();
                mLinhaDeAudio.close();
            } catch (Exception ignored) {
            }
            mLinhaDeAudio = null;
        }

        if (!AudioSystem.isLineSupported(new DataLine.Info(SourceDataLine.class, format))) {
            return;
        }

        try {
            mLinhaDeAudio = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
            mLinhaDeAudio.open(format);
            mLinhaDeAudio.start();
        } catch (LineUnavailableException ignored) {
            mLinhaDeAudio = null;
        }
    }

    public void onToque() {
        if (mLinhaDeAudio != null && buffer != null && mais > 0) {
            mLinhaDeAudio.write(buffer, 0, mais);
        }
    }

    public void avancar(int eMais) {
        au_lendo += eMais;
        if (au_lendo >= au_total) {
            continuar = false;
            terminou = true;
        }
    }

    public boolean temMais() {
        return continuar && (au_lendo < au_total && !terminou);
    }

    public long getLendo() {
        return au_lendo;
    }

    public int getMais() {
        return mais;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public double getProgresso() {
        if (au_total <= 0) {
            return 100.0;
        }
        double p = (double) au_lendo / (double) au_total;
        p = p * (100.0);
        return p;
    }

    public boolean isPausado() {
        return !continuar;
    }

    public void pausar() {
        continuar = false;
    }

    public void reproduzir() {
        continuar = true;
    }

    public void re_iniciar() {
        au_lendo = 0;
        ptr = new RefInt();
        continuar = true;
        terminou = false;
    }

    public void fechar() {
        if (mLinhaDeAudio != null) {
            try {
                mLinhaDeAudio.stop();
                mLinhaDeAudio.close();
            } catch (Exception ignored) {
            }
            mLinhaDeAudio = null;
        }
    }
}
