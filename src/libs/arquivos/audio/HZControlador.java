package libs.arquivos.audio;

import libs.arquivos.binario.Arquivador;
import libs.arquivos.binario.Inteiro;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class HZControlador {

    private static final String HZ_MAGIC = "ATTZHZ1";
    private static final int HZ_VERSION_1 = 1; // formato legado
    private static final int HZ_VERSION_2 = 2; // novo formato com blocos compactados
    private static final int HZ_VERSION = HZ_VERSION_2;
    private static final int BLOCO_TAMANHO = 65536; // blocos de 64KB para melhor compressão
    public static final AudioFormat FORMATO_PADRAO = new AudioFormat(44100f, 16, 2, true, false);

    public static class Transferencia {

        public byte[] wavDados;
        public long tamanho;

        public Transferencia(byte[] dados, long tam) {
            wavDados = dados;
            tamanho = tam;
        }

    }

    public static class DadosAudioHz {
        private final byte[] dados;
        private final AudioFormat formato;
        private final long tamanho;

        public DadosAudioHz(byte[] dados, AudioFormat formato, long tamanho) {
            this.dados = dados;
            this.formato = formato;
            this.tamanho = tamanho;
        }

        public byte[] getDados() {
            return dados;
        }

        public AudioFormat getFormato() {
            return formato;
        }

        public long getTamanho() {
            return tamanho;
        }
    }

    public static void salvarAudio(String eArquivoHz, byte[] dados) {
        salvarAudio(eArquivoHz, dados, FORMATO_PADRAO);
    }

    public static void salvarAudio(String eArquivoHz, byte[] dados, AudioFormat formato) {
        if (dados == null) {
            dados = new byte[0];
        }
        if (formato == null) {
            formato = FORMATO_PADRAO;
        }

        Arquivador.remover(eArquivoHz);
        try (DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(eArquivoHz)))) {
            output.write(HZ_MAGIC.getBytes(StandardCharsets.US_ASCII));
            output.writeInt(HZ_VERSION);
            output.writeInt(Math.round(formato.getSampleRate()));
            output.writeInt(formato.getSampleSizeInBits());
            output.writeInt(formato.getChannels());
            output.writeBoolean(formato.getEncoding() == AudioFormat.Encoding.PCM_SIGNED);
            output.writeBoolean(formato.isBigEndian());

            int numBlocos = (dados.length + BLOCO_TAMANHO - 1) / BLOCO_TAMANHO;
            output.writeInt(numBlocos);

            for (int i = 0; i < numBlocos; i++) {
                int inicio = i * BLOCO_TAMANHO;
                int fim = Math.min(inicio + BLOCO_TAMANHO, dados.length);
                byte[] bloco = Arrays.copyOfRange(dados, inicio, fim);

                byte[] blocoCompactado = compactarBloco(bloco);
                output.writeInt(bloco.length); // tamanho descompactado
                output.writeInt(blocoCompactado.length); // tamanho compactado
                output.write(blocoCompactado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] compactarBloco(byte[] bloco) {
        if (bloco == null || bloco.length == 0) {
            return new byte[0];
        }

        // Tentar comprimir com gzip
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzip = new GZIPOutputStream(saida);
            gzip.write(bloco);
            gzip.close();

            byte[] comprimido = saida.toByteArray();
            // Se compressão economizou >= 20%, usar gzip; senão, usar sem compressão
            if (comprimido.length < bloco.length * 0.8) {
                ByteArrayOutputStream resultado = new ByteArrayOutputStream();
                try (DataOutputStream output = new DataOutputStream(resultado)) {
                    output.writeByte(0x01); // Marcador: comprimido com gzip
                    output.write(comprimido);
                }
                return resultado.toByteArray();
            } else {
                // Sem compressão significativa
                ByteArrayOutputStream resultado = new ByteArrayOutputStream();
                try (DataOutputStream output = new DataOutputStream(resultado)) {
                    output.writeByte(0xFF); // Marcador: sem compressão
                    output.write(bloco);
                }
                return resultado.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback: retornar sem compressão
            ByteArrayOutputStream resultado = new ByteArrayOutputStream();
            try (DataOutputStream output = new DataOutputStream(resultado)) {
                output.writeByte(0xFF);
                output.write(bloco);
            } catch (IOException ignored) {
            }
            return resultado.toByteArray();
        }
    }

    public static byte[] descompactarBloco(byte[] blocoCompactado, int tamanhoDescompactado) {
        if (blocoCompactado == null || blocoCompactado.length == 0) {
            return new byte[tamanhoDescompactado];
        }

        try {
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(blocoCompactado));
            byte marcador = input.readByte();

            if (marcador == (byte) 0xFF) {
                // Bloco sem compressão
                byte[] buffer = new byte[tamanhoDescompactado];
                int bytesLidos = input.read(buffer);
                input.close();
                return bytesLidos > 0 ? Arrays.copyOf(buffer, bytesLidos) : buffer;
            } else if (marcador == (byte) 0x01) {
                // Bloco comprimido com gzip - ler direto do input já posicionado
                byte[] buffer = new byte[tamanhoDescompactado];
                GZIPInputStream gzip = new GZIPInputStream(input);
                int bytesLidos = gzip.read(buffer);
                gzip.close();
                input.close();
                return bytesLidos > 0 ? Arrays.copyOf(buffer, bytesLidos) : buffer;
            } else {
                // Marcador desconhecido
                input.close();
                return new byte[tamanhoDescompactado];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[tamanhoDescompactado];
    }

    public static DadosAudioHz carregarAudio(String eArquivoHz) {
        byte[] bytes = Arquivador.GET_BYTES(eArquivoHz);
        return decodificar(bytes);
    }

    private static DadosAudioHz decodificar(byte[] arquivoHz) {
        if (arquivoHz == null || arquivoHz.length == 0) {
            return new DadosAudioHz(new byte[0], FORMATO_PADRAO, 0);
        }

        try (DataInputStream input = new DataInputStream(
                new BufferedInputStream(new ByteArrayInputStream(arquivoHz)))) {
            byte[] magic = new byte[HZ_MAGIC.length()];
            int lido = input.read(magic);
            if (lido == magic.length && Arrays.equals(magic, HZ_MAGIC.getBytes(StandardCharsets.US_ASCII))) {
                int version = input.readInt();

                if (version == HZ_VERSION_1) {
                    return decodificarV1(input);
                } else if (version == HZ_VERSION_2) {
                    return decodificarV2(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DadosAudioHz(Arrays.copyOf(arquivoHz, arquivoHz.length), FORMATO_PADRAO, arquivoHz.length);
    }

    private static DadosAudioHz decodificarV1(DataInputStream input) throws IOException {
        int sampleRate = input.readInt();
        int sampleSizeInBits = input.readInt();
        int channels = input.readInt();
        boolean signed = input.readBoolean();
        boolean bigEndian = input.readBoolean();
        int tamanho = input.readInt();
        byte[] dados = new byte[tamanho];
        int total = input.read(dados);
        if (total < dados.length) {
            dados = Arrays.copyOf(dados, total);
        }
        AudioFormat formato = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return new DadosAudioHz(dados, formato, dados.length);
    }

    private static DadosAudioHz decodificarV2(DataInputStream input) throws IOException {
        int sampleRate = input.readInt();
        int sampleSizeInBits = input.readInt();
        int channels = input.readInt();
        boolean signed = input.readBoolean();
        boolean bigEndian = input.readBoolean();
        int numBlocos = input.readInt();

        ByteArrayOutputStream saida = new ByteArrayOutputStream();

        for (int i = 0; i < numBlocos; i++) {
            int tamanhoDescompactado = input.readInt();
            int tamanhoCompactado = input.readInt();

            // Ler bloco compactado inteiramente
            byte[] blocoCompactado = new byte[tamanhoCompactado];
            int totalLido = 0;
            while (totalLido < tamanhoCompactado) {
                int bytesLidos = input.read(blocoCompactado, totalLido, tamanhoCompactado - totalLido);
                if (bytesLidos < 0)
                    break;
                totalLido += bytesLidos;
            }

            byte[] blocoDescompactado = descompactarBloco(blocoCompactado, tamanhoDescompactado);
            saida.write(blocoDescompactado);
        }

        byte[] dados = saida.toByteArray();
        AudioFormat formato = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return new DadosAudioHz(dados, formato, dados.length);
    }

    public static void converterToHZ(String eArquivoWAV, String eArquivoHZ) {
        Transferencia eTransferencia = obterDadosDoWAV(eArquivoWAV);

        Arquivador.remover(eArquivoHZ);

        toHZ(eTransferencia, eArquivoHZ);
    }

    public static void converterToHZ(String eArquivoWAV) {
        Transferencia eTransferencia = obterDadosDoWAV(eArquivoWAV);

        Arquivador.remover(eArquivoWAV.replace(".wav", ".hz"));

        toHZ(eTransferencia, eArquivoWAV.replace(".wav", ".hz"));
    }

    public static Transferencia obterDadosDoWAV(String eArquivoWAV) {

        ByteArrayOutputStream byteArrayOutputStream;
        AudioFormat audioFormat;
        TargetDataLine targetDataLine;
        AudioInputStream audioInputStream;
        SourceDataLine sourceDataLine;
        float frequency = 8000.0F; // 8000,11025,16000,22050,44100
        int samplesize = 16;
        String myPath;
        long myChunkSize;
        long mySubChunk1Size;
        int myFormat;
        long myChannels;
        long mySampleRate;
        long myByteRate;
        int myBlockAlign;
        int myBitsPerSample;
        long myDataSize;
        // I made this public so that you can toss whatever you want in here
        // maybe a recorded buffer, maybe just whatever you want
        byte[] myData;

        DataInputStream inFile = null;
        myData = null;
        byte[] tmpLong = new byte[4];
        byte[] tmpInt = new byte[2];

        try {
            inFile = new DataInputStream(new FileInputStream(eArquivoWAV));

            // System.out.println("Reading wav file...\n"); // for debugging only

            String chunkID = "" + (char) inFile.readByte() + (char) inFile.readByte() + (char) inFile.readByte()
                    + (char) inFile.readByte();

            myChunkSize = inFile.readLong(); // read the ChunkSize

            String format = "" + (char) inFile.readByte() + (char) inFile.readByte() + (char) inFile.readByte()
                    + (char) inFile.readByte();

            // print what we've read so far
            // System.out.println("chunkID:" + chunkID + " chunk1Size:" + myChunkSize + "
            // format:" + format); // for debugging only

            String subChunk1ID = "" + (char) inFile.readByte() + (char) inFile.readByte() + (char) inFile.readByte()
                    + (char) inFile.readByte();

            mySubChunk1Size = inFile.readLong(); // read the SubChunk1Size

            myFormat = inFile.readInt(); // read the audio format. This should be 1 for PCM

            myChannels = inFile.readInt(); // read the # of channels (1 or 2)

            mySampleRate = inFile.readLong(); // read the samplerate

            myByteRate = inFile.readLong(); // read the byterate

            myBlockAlign = inFile.readInt(); // read the blockalign

            myBitsPerSample = inFile.readInt(); // read the bitspersample

            // print what we've read so far
            // System.out.println("SubChunk1ID:" + subChunk1ID + " SubChunk1Size:" +
            // mySubChunk1Size + " AudioFormat:" + myFormat + " Channels:" + myChannels + "
            // SampleRate:" + mySampleRate);

            // read the data chunk header - reading this IS necessary, because not all wav
            // files will have the data chunk here - for now, we're just assuming that the
            // data chunk is here
            String dataChunkID = "" + (char) inFile.readByte() + (char) inFile.readByte() + (char) inFile.readByte()
                    + (char) inFile.readByte();

            myDataSize = inFile.readInt(); // read the size of the data

            // read the data chunk
            myData = new byte[(int) myDataSize];
            int leu = inFile.read(myData);

            System.out.println("TODO :: " + myData.length);
            System.out.println("LEU  :: " + leu);

            inFile.close();

            return new Transferencia(myData, leu);

        } catch (Exception e) {
            return new Transferencia(new byte[10], 9);
        }

    }

    private static void toHZ(Transferencia eTransferencia, String eArquivoAU) {
        byte[] dados = eTransferencia != null && eTransferencia.wavDados != null ? eTransferencia.wavDados
                : new byte[0];
        salvarAudio(eArquivoAU, dados);
    }

    public static HZ init(String eArquivo) {
        HZ eHZ = new HZ(eArquivo);
        return eHZ;
    }

    public static void proxima(HZ eHZ) {
        eHZ.mais = lerAmostragemBuffer(eHZ.ptr, eHZ.au_buffer, eHZ.buffer, eHZ.originais, eHZ.copiar);
        if (eHZ.mais > 0) {
            eHZ.au_lendo += eHZ.mais;
        } else {
            eHZ.continuar = false;
            eHZ.terminou = true;
        }
    }

    public static void toque_direto(HZ eHZ, byte[] buff, int etam) {
        eHZ.mLinhaDeAudio.write(buff, 0, etam);
    }

    public static void toque(HZ eHZ) {
        eHZ.mais = lerAmostragemBuffer(eHZ.ptr, eHZ.au_buffer, eHZ.buffer, eHZ.originais, eHZ.copiar);

        if (eHZ.getMais() > 0) {
            eHZ.avancar(eHZ.getMais());
            eHZ.onToque();
        } else {
            eHZ.continuar = false;
            eHZ.terminou = true;
        }
    }

    public static int lerAmostragemBuffer(RefInt ptr, byte[] arquivo_au, byte[] buffer, byte[] originais,
            byte[] copiar) {

        if (ptr.get() >= arquivo_au.length) {
            return -1;
        }

        byte primeiro = arquivo_au[ptr.get()];
        ptr.mais(1);

        int numBytesRead = -1;

        if (primeiro == (byte) 0) {

            int pt = 0;

            for (int indice = 0; indice < (256 / 2); indice++) {
                if (ptr.get() >= arquivo_au.length) {
                    return -1;
                }
                buffer[pt] = arquivo_au[ptr.get()];
                ptr.mais(1);
                if (ptr.get() >= arquivo_au.length) {
                    return -1;
                }
                buffer[pt + 1] = arquivo_au[ptr.get()];
                ptr.mais(1);

                pt += 2;
            }
            numBytesRead = 256;
            // System.out.println("BLOCO COMPLETO");
        } else {
            int repetidos = Inteiro.byteToInt(primeiro);
            int total_repetidos = 0;

            // System.out.println("BLOCO COMPACTADO -->> " + repetidos);

            ArrayList<RepeticaoBinaria> repeticoes = new ArrayList<RepeticaoBinaria>();

            for (int r = 0; r < repetidos; r++) {
                if (ptr.get() + 1 >= arquivo_au.length) {
                    return -1;
                }

                byte b1 = arquivo_au[ptr.get()];
                ptr.mais(1);

                byte b2 = arquivo_au[ptr.get()];
                ptr.mais(1);

                if (ptr.get() >= arquivo_au.length) {
                    return -1;
                }
                int qt = Inteiro.byteToInt(arquivo_au[ptr.get()]);
                ptr.mais(1);

                total_repetidos += qt;

                // System.out.println("\t - Repetir :: " + Inteiro.byteToInt(b1) + " e " +
                // Inteiro.byteToInt(b2) + " -->> " + qt);

                RepeticaoBinaria rb = new RepeticaoBinaria(b1, b2, "");
                repeticoes.add(rb);

                for (int q = 0; q < qt; q++) {
                    if (ptr.get() >= arquivo_au.length) {
                        return -1;
                    }
                    int pos = Inteiro.byteToInt(arquivo_au[ptr.get()]);
                    ptr.mais(1);

                    rb.guardar(pos);
                    // System.out.println("\t\t :: " + pos);

                }

            }

            int qt_originais = 256 - (total_repetidos * 2);
            // System.out.println("\t - Originais : " + qt_originais);
            // System.out.println("\t - Repetidos : " + total_repetidos);

            int pt = 0;
            for (int indice = 0; indice < (qt_originais / 2); indice++) {
                if (ptr.get() >= arquivo_au.length) {
                    return -1;
                }
                originais[pt] = arquivo_au[ptr.get()];
                ptr.mais(1);
                if (ptr.get() >= arquivo_au.length) {
                    return -1;
                }
                originais[pt + 1] = arquivo_au[ptr.get()];
                ptr.mais(1);
                pt += 2;
            }

            // ab.mostrarBuffer(originais);

            rearranjar(buffer, originais, qt_originais, repeticoes, copiar);

            numBytesRead = 256;

        }

        return numBytesRead;
    }

    public static void rearranjar(byte[] buffer, byte[] originais, int qt_originais,
            ArrayList<RepeticaoBinaria> repeticoes, byte[] copiar) {
        rearranjarBloco(buffer, originais, qt_originais, repeticoes, copiar, 256);
    }

    private static void rearranjarBloco(byte[] buffer, byte[] originais, int qt_originais,
            ArrayList<RepeticaoBinaria> repeticoes, byte[] copiar, int tamanhoBloco) {

        int bufferLen = Math.min(buffer.length, tamanhoBloco);

        // Copiar originais para o buffer
        for (int o = 0; o < Math.min(qt_originais, bufferLen); o++) {
            buffer[o] = originais[o];
        }

        for (RepeticaoBinaria rep : repeticoes) {
            int mais = 0;

            for (int pos : rep.getPosicoes()) {
                if (pos + 2 > bufferLen) {
                    continue;
                }

                int deslocar = Math.min(qt_originais - pos + mais, bufferLen - pos - 2);
                mais += 2;

                if (deslocar > 0) {
                    int c = 0;
                    int maxCopy = Math.min(pos + deslocar, bufferLen);
                    for (int des = pos; des < maxCopy; des++) {
                        if (c < copiar.length) {
                            copiar[c] = buffer[des];
                            c += 1;
                        }
                    }

                    c = 0;
                    for (int des = pos + 2; des < Math.min(pos + deslocar + 2, bufferLen); des++) {
                        if (c < copiar.length && c < deslocar) {
                            buffer[des] = copiar[c];
                            c += 1;
                        }
                    }
                }

                if (pos < bufferLen && pos + 1 < bufferLen) {
                    buffer[pos] = rep.getB1();
                    buffer[pos + 1] = rep.getB2();
                }
            }
        }
    }

}
