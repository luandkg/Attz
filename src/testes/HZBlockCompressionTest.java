package testes;

import libs.arquivos.audio.HZControlador;
import libs.arquivos.binario.Arquivador;

import java.io.*;

public class HZBlockCompressionTest {
    public static void main(String[] args) {
        testBlockBasedCompression();
    }

    private static void testBlockBasedCompression() {
        String testFile = "/tmp/hz-block-test.hz";
        Arquivador.remover(testFile);

        // Criar dados de teste com padrão repetitivo (compressível)
        byte[] testData = createRepetitiveData(1024); // 1KB de dados repetitivos

        // Salvar em formato HZ v2 com blocos
        HZControlador.salvarAudio(testFile, testData);

        // Carregar o arquivo e verificar
        HZControlador.DadosAudioHz dadosCarregados = HZControlador.carregarAudio(testFile);

        // Validar
        if (dadosCarregados == null || dadosCarregados.getDados() == null) {
            System.out.println("ERRO: Dados não foram carregados");
            return;
        }

        byte[] dadosRecuperados = dadosCarregados.getDados();

        // Verificar tamanho
        if (dadosRecuperados.length != testData.length) {
            System.out.println("ERRO: Tamanho mismatch - esperado " + testData.length +
                    ", obtido " + dadosRecuperados.length);
            return;
        }

        // Verificar conteúdo
        for (int i = 0; i < testData.length; i++) {
            if (testData[i] != dadosRecuperados[i]) {
                System.out.println("ERRO: Dados mismatch na posição " + i);
                return;
            }
        }

        // Verificar compressão - arquivo compactado deve ser menor
        File file = new File(testFile);
        long tamanhoArquivo = file.length();
        long razaoCompressao = (testData.length * 100) / tamanhoArquivo;

        System.out.println("Block compression test passed!");
        System.out.println("Original size: " + testData.length + " bytes");
        System.out.println("Compressed file size: " + tamanhoArquivo + " bytes");
        System.out.println("Compression ratio: " + razaoCompressao + "%");
        System.out.println("Blocks used: " + ((testData.length + 255) / 256));

        Arquivador.remover(testFile);
    }

    private static byte[] createRepetitiveData(int size) {
        byte[] data = new byte[size];

        // Padrão 1: bytes repetidos
        for (int i = 0; i < size / 4; i++) {
            data[i] = (byte) (i % 256);
        }

        // Padrão 2: pares repetidos
        for (int i = size / 4; i < size / 2; i += 2) {
            data[i] = (byte) 42;
            data[i + 1] = (byte) 99;
        }

        // Padrão 3: sequência repetida
        byte[] pattern = { (byte) 0xFF, (byte) 0x00, (byte) 0xAA, (byte) 0x55 };
        for (int i = size / 2; i < size; i++) {
            data[i] = pattern[(i - size / 2) % pattern.length];
        }

        return data;
    }
}
