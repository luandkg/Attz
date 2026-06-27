package apps.app_humanidade;

import apps.app_humanidade.idiomas.*;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.*;
import libs.tronarko.Tronarko;
import libs.zetta.ItemColecionavel;
import libs.zetta.ZettaColecao;
import libs.zetta.ZettaQuorum;
import libs.zetta.features.ZQC;
import servicos.ASSETS;

public class Traduttor {

    public static Lista<String> GET_PALAVRAS(String texto_simples) {

        texto_simples = Strings.CAIXA_BAIXA(texto_simples);
        texto_simples = Strings.RETIRAR_ACENTOS(texto_simples);

        String texto_traduzindo = texto_simples;
        Lista<String> palavras = new Lista<String>();


        while (!texto_traduzindo.isEmpty()) {
            String palavra = "";

            int i = 0;
            int o = texto_traduzindo.length();

            while (i < o) {
                String letra = String.valueOf(texto_traduzindo.charAt(i));
                if (!Strings.isLetra(letra)) {

                } else {
                    break;
                }
                i += 1;
            }

            while (i < o) {
                String letra = String.valueOf(texto_traduzindo.charAt(i));
                if (Strings.isLetra(letra)) {
                    palavra += letra;
                } else {
                    break;
                }
                i += 1;
            }

            if (!palavra.isEmpty()) {
                palavras.adicionar(palavra);
            }


            String sobrou = "";
            while (i < o) {
                sobrou += String.valueOf(texto_traduzindo.charAt(i));
                i += 1;
            }

            texto_traduzindo = sobrou;
        }

        return palavras;
    }


    public static String TRADUZIR(String idioma_traducao, String texto_simples) {

        texto_simples = Strings.CAIXA_BAIXA(texto_simples);
        texto_simples = Strings.RETIRAR_ACENTOS(texto_simples);


        Lista<Idioma> idiomas = new Lista<Idioma>();

        idiomas.adicionar(new IdiomaTraddes());
        idiomas.adicionar(new IdiomaMokkom());
        idiomas.adicionar(new IdiomaRequiz());
        idiomas.adicionar(new IdiomaPlaque());
        idiomas.adicionar(new IdiomaDommus());
        idiomas.adicionar(new IdiomaAlkoz());
        idiomas.adicionar(new IdiomaInmeb());
        idiomas.adicionar(new IdiomaUppuma());


        if (!Lista.EXISTE_COM_ATRIBUTO(Idioma.PROCURAVEL_COM_NOME(), idiomas, idioma_traducao)) {
            throw new RuntimeException("Idioma desconhecido :: " + idioma_traducao);
        }

        Lista<Entidade> idiomas_dicionario = ZQC.COLECAO_ENTIDADES(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"), "IDIOMAS");

        String texto_traduzindo = texto_simples;
        String texto_traduzido = "";


        while (!texto_traduzindo.isEmpty()) {
            String palavra = "";

            int i = 0;
            int o = texto_traduzindo.length();

            while (i < o) {
                String letra = String.valueOf(texto_traduzindo.charAt(i));
                if (!Strings.isLetra(letra)) {
                    texto_traduzido += letra;
                } else {
                    break;
                }
                i += 1;
            }

            while (i < o) {
                String letra = String.valueOf(texto_traduzindo.charAt(i));
                if (Strings.isLetra(letra)) {
                    palavra += letra;
                } else {
                    break;
                }
                i += 1;
            }

           // fmt.print(">> Texto   :: {}",texto_traduzido);
          //  fmt.print(">> Palavra :: {}",palavra);

            if (!palavra.isEmpty()) {

                Entidade e_palavra = ENTT.GET_SEMPRE(idiomas_dicionario, "Palavra", palavra);
                String palavra_traduzida = e_palavra.at(idioma_traducao);

                if (palavra_traduzida.isEmpty()) {

                    Unico<String> palavras_existentes = Unico.CRIAR_DE_LISTA(Strings.IGUALAVEL(), ENTT.VALORES_DE(idiomas_dicionario, idioma_traducao));

                    for (Idioma idioma : idiomas) {

                        String palavra_nova = idioma.getUnico(palavras_existentes);
                        e_palavra.at(idioma.getNome(), palavra_nova);

                    }

                    e_palavra.at("TTS", Tronarko.getTronAgora().getTextoZerado());


                    ZQC.INSERIR(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"), "IDIOMAS", e_palavra);

                }


                e_palavra.at("Utilizada", Tronarko.getTozte().getTextoZerado());


                palavra_traduzida = e_palavra.at(idioma_traducao);
                texto_traduzido += palavra_traduzida;
            }


            String sobrou = "";
            while (i < o) {
                sobrou += String.valueOf(texto_traduzindo.charAt(i));
                i += 1;
            }

            texto_traduzindo = sobrou;
        }


        ZettaQuorum zq = new ZettaQuorum(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"));
        ZettaColecao dic = zq.getColecaoSempre("IDIOMAS");

        for (Entidade e_palavra : idiomas_dicionario) {

            if (e_palavra.atributo_existe("@ID") && e_palavra.is("Utilizada", Tronarko.getTozte().getTextoZerado())) {
                for (ItemColecionavel ic : dic.getItensEditaveis()) {
                    if (ic.get().is("@ID", e_palavra.at("@ID"))) {

                        if (ic.get().isDiferente("Utilizada", Tronarko.getTozte().getTextoZerado())) {
                            ic.get().at("Utilizada", Tronarko.getTozte().getTextoZerado());
                            ic.get().at("UtilizadaRanking", ic.get().atIntOuPadrao("UtilizadaRanking", 0) + 1);

                            ic.atualizar();

                        }

                        break;
                    }
                }
            }

        }


        zq.fechar();


        return texto_traduzido;
    }

    public static String TRADUZIR_DE_VOLTA(String idioma_traducao, String texto_simples) {

        texto_simples = Strings.CAIXA_BAIXA(texto_simples);
        texto_simples = Strings.RETIRAR_ACENTOS(texto_simples);


        Lista<Entidade> idiomas_dicionario = ZQC.COLECAO_ENTIDADES(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"), "IDIOMAS");

        String texto_traduzindo = texto_simples;
        String texto_traduzido = "";


        while (!texto_traduzindo.isEmpty()) {
            String palavra = "";

            int i = 0;
            int o = texto_traduzindo.length();

            while (i < o) {
                String letra = String.valueOf(texto_traduzindo.charAt(i));
                if (!Strings.isLetra(letra)) {
                    texto_traduzido += letra;
                } else {
                    break;
                }
                i += 1;
            }

            while (i < o) {
                String letra = String.valueOf(texto_traduzindo.charAt(i));
                if (Strings.isLetra(letra)) {
                    palavra += letra;
                } else {
                    break;
                }
                i += 1;
            }

            if (!palavra.isEmpty()) {

                // fmt.print("Proc :: {}",palavra);

                Entidade e_palavra = ENTT.GET_SEMPRE(idiomas_dicionario, idioma_traducao, palavra);
                String palavra_traduzida = e_palavra.at("Palavra");

                e_palavra.at("TTR", Tronarko.getTronAgora().getTextoZerado());
                e_palavra.at("Retorno", Tronarko.getTozte().getTextoZerado());


                texto_traduzido += palavra_traduzida;
            }


            String sobrou = "";
            while (i < o) {
                sobrou += String.valueOf(texto_traduzindo.charAt(i));
                i += 1;
            }

            texto_traduzindo = sobrou;
        }


        ZettaQuorum zq = new ZettaQuorum(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"));
        ZettaColecao dic = zq.getColecaoSempre("IDIOMAS");

        for (Entidade e_palavra : idiomas_dicionario) {

            if (e_palavra.atributo_existe("@ID") && e_palavra.is("Retorno", Tronarko.getTozte().getTextoZerado())) {
                for (ItemColecionavel ic : dic.getItensEditaveis()) {
                    if (ic.get().is("@ID", e_palavra.at("@ID"))) {

                        if (ic.get().isDiferente("Retorno", Tronarko.getTozte().getTextoZerado())) {
                            ic.get().at("Retorno", Tronarko.getTozte().getTextoZerado());
                            ic.get().at("RetornoRanking", ic.get().atIntOuPadrao("RetornoRanking", 0) + 1);

                            ic.atualizar();

                        }

                        break;
                    }
                }
            }

        }


        zq.fechar();


        return texto_traduzido;
    }


    public static void EXIBIR_DICIONARIO() {

        Lista<Entidade> idiomas_dicionario = ZQC.COLECAO_ENTIDADES(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"), "IDIOMAS");

        ENTT.EXIBIR_TABELA_COM_TITULO(idiomas_dicionario, "Dicionario");


    }


    public static void PERIGOSO_CRIAR_DICIONARIO() {

        Lista<Idioma> idiomas = new Lista<Idioma>();

        idiomas.adicionar(new IdiomaTraddes());
        idiomas.adicionar(new IdiomaMokkom());
        idiomas.adicionar(new IdiomaRequiz());
        idiomas.adicionar(new IdiomaPlaque());
        idiomas.adicionar(new IdiomaDommus());
        idiomas.adicionar(new IdiomaAlkoz());
        idiomas.adicionar(new IdiomaInmeb());
        idiomas.adicionar(new IdiomaUppuma());

        Lista<Entidade> idiomas_dicionario = ENTT.CRIAR_LISTA();

        Lista<String> palavras_reservadas = new Lista<String>();
        palavras_reservadas.adicionar_varios(Lista.CRIAR("a", "as", "o", "os"));
        palavras_reservadas.adicionar_varios(Lista.CRIAR("de", "da", "das", "do", "do"));
        palavras_reservadas.adicionar_varios(Lista.CRIAR("com", "sem", "para", "por", "pelo"));
        palavras_reservadas.adicionar_varios(Lista.CRIAR("se", "um", "uma", "uns", "umas", "e", "que", "ou", "mas"));

        for (Idioma idioma : idiomas) {

            Lista<String> amostra = idioma.getAmostraPequeno(100);

            for (String palavra_reservada : palavras_reservadas) {

                Lista<Entidade> e_amostra = ENTT.VALORES("Palavra", amostra);
                ENTT.EXIBIR_TABELA_COM_TITULO(e_amostra, idioma.getNome());

                Lista<Entidade> dessas = new Lista<Entidade>();
                for (Entidade e : e_amostra) {
                    if (e.at("Palavra").length() <= palavra_reservada.length()) {
                        dessas.adicionar(e);
                    }
                }

                fmt.print("Para :: {}", palavra_reservada);
                ENTT.EXIBIR_TABELA(dessas);

                if (!dessas.possuiObjetos()) {

                    for (Entidade e : e_amostra) {
                        if (e.at("Palavra").length() <= (palavra_reservada.length() + 1)) {
                            dessas.adicionar(e);
                        }
                    }

                    if (!dessas.possuiObjetos()) {
                        dessas = e_amostra;
                    }

                }

                Entidade e_palavra = ENTT.GET_SEMPRE(idiomas_dicionario, "Palavra", palavra_reservada);
                e_palavra.at(idioma.getNome(), Aleatorio.escolha_um(dessas).at("Palavra").toLowerCase());

                amostra.remover(Strings.IGUALDADE(), e_palavra.at("Palavra"));
            }
        }


        for (Entidade e_palavra : idiomas_dicionario) {
            e_palavra.at("TTS", Tronarko.getTronAgora().getTextoZerado());
        }

        ZQC.LIMPAR_TUDO(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"), "IDIOMAS");
        ZQC.INSERIR_VARIOS(ASSETS.GET_PASTA("idiomas").getArquivo("Idiomas.az"), "IDIOMAS", idiomas_dicionario);

    }
}
