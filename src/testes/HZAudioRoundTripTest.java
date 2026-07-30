package testes;

import libs.arquivos.audio.HZControlador;

import java.io.File;

public class HZAudioRoundTripTest {

    public static void main(String[] args) throws Exception {
        File temp = File.createTempFile("hz-roundtrip", ".hz");
        byte[] audio = new byte[] { 0, 0, -1, -1, 0, 0, 1, 1, -2, 2, 3, 4 };

        HZControlador.salvarAudio(temp.getAbsolutePath(), audio);
        HZControlador.DadosAudioHz dados = HZControlador.carregarAudio(temp.getAbsolutePath());

        if (dados == null) {
            throw new AssertionError("dados nao carregados");
        }

        if (dados.getDados().length != audio.length) {
            throw new AssertionError("tamanho inesperado: " + dados.getDados().length);
        }

        for (int i = 0; i < audio.length; i++) {
            if (audio[i] != dados.getDados()[i]) {
                throw new AssertionError(
                        "byte diferente na posicao " + i + ": " + audio[i] + " != " + dados.getDados()[i]);
            }
        }

        System.out.println("round-trip ok");
    }
}
