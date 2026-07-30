package testes;

import libs.arquivos.audio.HZ;
import libs.arquivos.audio.HZControlador;
import libs.arquivos.binario.Arquivador;

import java.io.File;

public class HZBlockPlaybackTest {
    public static void main(String[] args) {
        testBlockPlayback();
    }

    private static void testBlockPlayback() {
        String testFile = "/tmp/hz-playback-test.hz";
        Arquivador.remover(testFile);

        byte[] testData = createTestAudioData(2048);

        System.out.println("Saving audio with " + ((testData.length + 255) / 256) + " blocks...");
        HZControlador.salvarAudio(testFile, testData);

        File file = new File(testFile);
        long tamanhoArquivo = file.length();
        System.out.println("File size: " + tamanhoArquivo + " bytes");

        HZControlador.DadosAudioHz dados = HZControlador.carregarAudio(testFile);
        if (dados == null || dados.getDados() == null || dados.getDados().length != testData.length) {
            System.out.println("ERRO: Dados não foram recuperados corretamente");
            return;
        }

        byte[] recovered = dados.getDados();
        boolean match = true;
        for (int i = 0; i < testData.length; i++) {
            if (testData[i] != recovered[i]) {
                System.out.println("ERRO: Mismatch at position " + i);
                match = false;
                break;
            }
        }

        if (!match) {
            System.out.println("Playback test FAILED!");
            Arquivador.remover(testFile);
            return;
        }

        System.out.println("\nCreating player...");
        HZ player = new HZ(testFile);

        System.out.println("Player status:");
        System.out.println("  Has more data: " + player.temMais());
        System.out.println("  Progress: " + String.format("%.2f%%", player.getProgresso()));

        int blocksRead = 0;
        while (player.temMais() && blocksRead < 10) {
            HZControlador.proxima(player);
            if (player.getMais() > 0) {
                blocksRead++;
                System.out.println("  Block " + blocksRead + ": " + player.getMais() + " bytes");
            } else {
                break;
            }
        }

        player.fechar();
        System.out.println("\nPlayback test passed!");
        System.out.println("Total blocks read: " + blocksRead);

        Arquivador.remover(testFile);
    }

    private static byte[] createTestAudioData(int size) {
        byte[] data = new byte[size];

        for (int i = 0; i < size; i++) {
            if (i < size / 3) {
                data[i] = (byte) (i % 256);
            } else if (i < 2 * size / 3) {
                data[i] = (byte) ((i % 2 == 0) ? 0xFF : 0x00);
            } else {
                data[i] = (byte) ((i * 73 + 17) % 256);
            }
        }

        return data;
    }
}
