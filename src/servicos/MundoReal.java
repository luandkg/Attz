package servicos;

import libs.luan.Aleatorio;
import libs.luan.Lista;
import libs.luan.Strings;
import libs.luan.Vetor;

public class MundoReal {


    public static String GET_NOME_PESSOA_COMPLETO() {

        String nome_completo = "";

        Vetor<String> primeiro_nome = Vetor.CRIAR("Luan", "Alice", "Bruno", "Flavio", "Pedro", "Matheus", "Thiado", "Samara", "Alana", "Aglicia", "Marta", "Maria", "Madalena", "João", "Alberto", "Gustado", "Rodolfo", "Sara", "Carla", "Jéssica");
        Lista<String> sobrenome_lista_alfa = Lista.CRIAR("Bucar", "Alencar", "Freitas", "Diniz", "Coimbra", "Ferraz", "Almeida", "Oliveira", "Santos", "Silva", "Souza", "Pereira", "Ferreira");

        nome_completo = Aleatorio.escolha_um(primeiro_nome) + " " + Aleatorio.escolha_um_sem_repetir(sobrenome_lista_alfa) + " " + Aleatorio.escolha_um_sem_repetir(sobrenome_lista_alfa);

        return Strings.CAPTALIZAR_FRASE(nome_completo);
    }

    public static String GET_DOCUMENTO_NUMERICO(int tam) {
        String documento = "";

        for (int i = 0; i < tam; i++) {

            if (i == 3 || i == 6 || i == 9) {
                documento += ".";
            } else if (i == 12) {
                documento += "-";
            }

            documento += Aleatorio.aleatorio(10);

        }

        return documento;

    }

}
