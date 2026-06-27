package apps.app_humanidade;

import apps.app_humanidade.idiomas.*;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.*;

public class AppHumanidade {

    public static void INIT() {

        Lista<Idioma> idiomas = new Lista<Idioma>();

        idiomas.adicionar(new IdiomaTraddes());
        idiomas.adicionar(new IdiomaMokkom());
        idiomas.adicionar(new IdiomaRequiz());
        idiomas.adicionar(new IdiomaPlaque());
        idiomas.adicionar(new IdiomaDommus());
        idiomas.adicionar(new IdiomaAlkoz());
        idiomas.adicionar(new IdiomaInmeb());
        idiomas.adicionar(new IdiomaUppuma());

        Lista<Entidade> palavras = new Lista<Entidade>();

        for (Idioma idioma : idiomas) {
            idioma.exibir_amostra();

            for (String palavra : idioma.getAmostra(10)) {
                Entidade e = ENTT.CRIAR_EM(palavras);
                e.at("Palavra", palavra);
            }
        }

        for (Idioma idioma : idiomas) {
            Entidade e = ENTT.CRIAR_EM(palavras);
            e.at("Palavra", idioma.getNome());
        }


        for (Entidade palavra : palavras) {
            for (Idioma idioma : idiomas) {

                // boolean pertence = idioma.pertence(palavra.at("Palavra"));

                palavra.at(idioma.getNome(), "");

                //  if (pertence) {
                //     palavra.at(idioma.getNome(), "SIM");
                //  }

            }
        }

        ENTT.EXIBIR_TABELA_COM_TITULO(palavras, "Palavras");


        Lista<Entidade> sobrenomes = new Lista<Entidade>();

        int sobrenomes_quantidade = 500;

        for (Idioma idioma : idiomas) {
            int i = 0;
            for (String palavra : idioma.getAmostra(sobrenomes_quantidade)) {
                Entidade e = ENTT.CRIAR_EM(sobrenomes);
                e.at("Idioma", idioma.getNome());
                e.at("Palavra", palavra);
                e.at("Sobrenome", Portugues.VALIDAR(i < (sobrenomes_quantidade / 2), "SOBRENOME::1", "SOBRENOME::2"));
                i += 1;
            }
        }


        // ENTT.EXIBIR_TABELA_COM_TITULO(sobrenomes, "Sobrenomes");

        ENTT.EXIBIR_TABELA_COM_TITULO(ENTT.GET_AMOSTRA_PEQUENA(sobrenomes), "Sobrenomes");
        fmt.print("Sobrenomes Quantidade = {}", ENTT.CONTAGEM(sobrenomes));


        Lista<Entidade> nomes_completos = new Lista<Entidade>();
        for (Idioma idioma : idiomas) {

            Lista<String> sobrenomes_1 = ENTT.VALORES_DE(ENTT.COLETAR(ENTT.COLETAR(sobrenomes, "Sobrenome", "SOBRENOME::1"), "Idioma", idioma.getNome()), "Palavra");
            Lista<String> sobrenomes_2 = ENTT.VALORES_DE(ENTT.COLETAR(ENTT.COLETAR(sobrenomes, "Sobrenome", "SOBRENOME::2"), "Idioma", idioma.getNome()), "Palavra");

            Strings.LISTA_CAIXA_BAIXA(sobrenomes_1);
            Strings.LISTA_CAIXA_BAIXA(sobrenomes_2);

            Unico<String> sobrenomes_todos = new Unico<String>(Strings.IGUALAVEL());
            for (String sobrenome : sobrenomes_1) {
                sobrenomes_todos.item(sobrenome);
            }
            for (String sobrenome : sobrenomes_2) {
                sobrenomes_todos.item(sobrenome);
            }

            for (int i = 1; i <= 5; i++) {
                Entidade e = ENTT.CRIAR_EM(nomes_completos);
                e.at("Idioma", idioma.getNome());
                e.at("Palavra", Strings.CAPTALIZAR_FRASE(idioma.getUnico(sobrenomes_todos) + " " + Aleatorio.escolha_um(sobrenomes_1) + " " + Aleatorio.escolha_um(sobrenomes_2)));
            }

        }

        //ENTT.EMBARALHAR(nomes_completos);

        for (Entidade palavra : nomes_completos) {
            for (Idioma idioma_corrente : idiomas) {

                palavra.at(idioma_corrente.getNome(), "");

                if (idioma_corrente.pertence_frase(palavra.at("Palavra"))) {
                    palavra.at(idioma_corrente.getNome(), "SIM");
                }

            }

        }

        ENTT.EXIBIR_TABELA_COM_TITULO(nomes_completos, "Nomes Completos");


        for (Idioma idioma_corrente : idiomas) {

            //   fmt.print("{} -->> {}",idioma_corrente.getNome(),idioma_corrente.pertence_frase("Moannolla Boonna Koolorkaa"));
            //  fmt.print("{} -->> {}",idioma_corrente.getNome(),idioma_corrente.pertence("Pezzi"));

        }

        for (Idioma idioma_corrente : idiomas) {

            // Rozzempum Peggi Zeviz
            fmt.print("{} -->> {}", idioma_corrente.getNome(), idioma_corrente.pertence("Rozzempum"));
            //  fmt.print("{} -->> {}",idioma_corrente.getNome(),idioma_corrente.pertence("Pezzi"));

        }



        // ENTT.EXIBIR_TABELA_COM_TITULO(idiomas_dicionario, "Dicionario");

        Traduttor.PERIGOSO_CRIAR_DICIONARIO();

        String texto_simples = "O mundo é feito de escolhas individuais mundanas no mundo todo com varios mundos";

        fmt.print("Entrada : {}", texto_simples);

        for (Idioma idioma : idiomas) {
               fmt.print("{dir15} : {}", idioma.getNome(), Strings.CAPTALIZAR_FRASE(Traduttor.TRADUZIR(idioma.getNome(), texto_simples)));
        }


        Traduttor.EXIBIR_DICIONARIO();

        String traz_de_volta = "Droggepas Trogsezes Malopo";

        for (Idioma idioma_corrente : idiomas) {

            fmt.print("{} -->> {}", idioma_corrente.getNome(), idioma_corrente.pertence_palavras(Traduttor.GET_PALAVRAS(traz_de_volta)));

            if (idioma_corrente.pertence_palavras(Traduttor.GET_PALAVRAS(traz_de_volta))) {
                fmt.print("{dir15} : {}", idioma_corrente.getNome(), Strings.CAPTALIZAR_FRASE(Traduttor.TRADUZIR_DE_VOLTA(idioma_corrente.getNome(), traz_de_volta)));
            }

        }

    }

}
