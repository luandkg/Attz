package apps.app_atzum.servicos;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_atzum.Atzum;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumTerra;
import apps.app_atzum.utils.AtzumCriativoLog;
import libs.arquivos.QTT;
import libs.azzal.geometria.Ponto;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.*;

import java.util.ArrayList;
import java.util.Random;

public class AtzumCentralDados {

    public static void PROXIMIDADE_COM_OCENAO() {

        AtzumCriativoLog.iniciar("AtzumCentralDados.PROXIMIDADE_COM_OCENAO");


        Lista<Entidade> tronarko_cidades_distancia_oceanica = Atzum.GET_CIDADES_NOMES();


        Atzum atzum = new Atzum();
        AtzumTerra atzum_terra = new AtzumTerra();


        QTT dados_oceano = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("oceanos.qtt"));


        int largura = atzum_terra.getLargura();
        int altura = atzum_terra.getAltura();


        for (Entidade cidade : tronarko_cidades_distancia_oceanica) {

            int cidade_x = cidade.atInt("X");
            int cidade_y = cidade.atInt("Y");


            int distancia_proximidade = Integer.MAX_VALUE;
            int mais_proximo = 0;

            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    int valor = dados_oceano.getValor(x, y);
                    if (valor > 0) {
                        if (Espaco2D.distancia_entre_pontos(cidade_x, cidade_y, x, y) < distancia_proximidade) {
                            distancia_proximidade = Espaco2D.distancia_entre_pontos(cidade_x, cidade_y, x, y);
                            mais_proximo = valor;
                        }
                    }
                }
            }

            fmt.print("Cidade {} - {} -->> {} dist = {} :: {}", cidade_x, cidade_y, mais_proximo, distancia_proximidade, atzum.GET_OCEANO(mais_proximo));

            cidade.at("Oceano_Nome", atzum.GET_OCEANO(mais_proximo));
            cidade.at("Oceano_Distancia", distancia_proximidade);

        }

        ENTT.AT_ALTERAR_NOME(tronarko_cidades_distancia_oceanica,"Cidade","CidadePos");

        FS.organizar_pasta( AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo"));

        ENTT.GUARDAR(tronarko_cidades_distancia_oceanica, AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades_distancia_oceanica.entts"));

        ENTT.EXIBIR_TABELA(ENTT.SLICE_PRIMEIROS(tronarko_cidades_distancia_oceanica,10));

        AtzumCriativoLog.terminar("AtzumCentralDados.PROXIMIDADE_COM_OCENAO");

        fmt.print("OK !");
    }


    public static void VER_AMOSTRAS() {

        //Lista<Entidade> dados_publicados = ENTT.ABRIR_COM_LIMITE(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_cidades_publicado.entts"),30);

        // ENTT.EXIBIR_TABELA(dados_publicados);


        Lista<Entidade> mapa_sensores_v9 = ENTT.ABRIR_COM_LIMITE(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores.entts"), 10);
        ENTT.EXIBIR_TABELA(mapa_sensores_v9);

    }


    public static void NOMEAR_CIDADES() {

        fmt.print(">> Nomear cidades...");

        Lista<Entidade> cidades = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_cidades_publicado.entts"));

        Lista<Entidade> nomes_cidades= ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/CIDADES_NOMES.entts"));

        for(Entidade cidade : nomes_cidades){
            NOMEAR(cidades, cidade.atInt("X"), cidade.atInt("Y"), cidade.at("Nome"));
        }


        ENTT.GUARDAR(cidades, AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_cidades_publicado.entts"));

        fmt.print(">> Tudo Ok !");
    }

    public static void NOMEAR(Lista<Entidade> cidades, int px, int py, String nome) {

        Entidade e_cidade = ENTT.GET_SEMPRE(cidades, "Cidade", px + "::" + py);
        e_cidade.at("Nome", nome);

    }


    public static void INFORME(String nome, int valor) {

        Lista<Entidade> dados = ENTT.ABRIR(AtzumCreator.DADOS_GET_ARQUIVO("AtzumDadosInfos.entts"));

        Entidade e = ENTT.GET_SEMPRE(dados, "Info", nome);
        e.at("Valor", valor);

        ENTT.GUARDAR(dados, AtzumCreator.DADOS_GET_ARQUIVO("AtzumDadosInfos.entts"));
    }

    public static int OBTER_INTEIRO(String nome) {

        Lista<Entidade> dados = ENTT.ABRIR(AtzumCreator.DADOS_GET_ARQUIVO("AtzumDadosInfos.entts"));

        Entidade e = ENTT.GET_SEMPRE(dados, "Info", nome);
        return e.atInt("Valor");
    }


    public static void CRIAR_NOMES_DAS_CIDADES() {

        // Lista<Entidade> cidades = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_cidades_publicado.entts"));


        Lista<Entidade> cidades = ENTT.CRIAR_LISTA();

        for (Ponto pt : Atzum.GET_CIDADES()) {
            Entidade e_cidade = ENTT.CRIAR_EM_SEQUENCIALMENTE(cidades, "ID");

            e_cidade.at("Cidade", pt.getX() + "::" + pt.getY());

            e_cidade.at("X", pt.getX());
            e_cidade.at("Y", pt.getY());
            e_cidade.at("Nome", "");

        }


        // NOMEAR(cidades, 706, 955, "Ampz");
        //  NOMEAR(cidades, 692, 963, "Eczos");
        //  NOMEAR(cidades, 690, 949, "Talaque");
        //  NOMEAR(cidades, 1140, 755, "Aaz");


        Unico<String> cidades_nomes = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade cidade : cidades) {
            if (cidade.isVazio("Nome")) {

                String cidade_nome = CRIAR_CIDADE_NOME_VALIDO(cidades_nomes);

                cidade.at("Nome", cidade_nome);
                cidades_nomes.item(cidade_nome);
            }
        }

        ENTT.EXIBIR_TABELA_COM_NOME(cidades, "CIDADES");

        ENTT.GUARDAR(cidades,AtzumCreator.DADOS_GET_ARQUIVO("AtzumCidades.entts"));

    }

    public static String CRIAR_CIDADE_NOME_VALIDO(Unico<String> cidades_nomes) {

        boolean valido = false;
        String cidade_nome = "";

        while (!valido) {

            cidade_nome = Strings.CAPTALIZAR(getSimplesMenor());

            fmt.print("Criando :: {}",cidade_nome);

            boolean v = false;

            int nao = 0;

            for (String nome : cidades_nomes) {
                if (nome.length() == cidade_nome.length()) {
                    int diffs = Strings.QUANTIDADE_LETRAS_IGUAIS(nome, cidade_nome);
                    fmt.print("\t ++ Mesmo Tamanho :: {} - {} = {}/{} -->> {}", nome, cidade_nome, diffs, nome.length(), Portugues.VALIDAR(diffs < (nome.length() / 2), "SIM", "NAO"));

                    if (diffs < (nome.length() / 2)) {
                      //  v = true;
                    }else{
                        nao+=1;
                    }

                }else{
                 //   v=true;
                }
            }

            if(cidades_nomes.getQuantidade()==0){
                v=true;
            }

            if (nao<5) {
                fmt.print("\t ++ Aceita :: {}",cidade_nome);

                if (!cidades_nomes.existe(cidade_nome)) {
                    break;
                } else {
                    fmt.print("\t ++ Outra Tentativa :: {}", cidade_nome);
                }
            }else{
                fmt.print("\t ++ NAO :: {} - {}", cidade_nome,nao);
            }


        }

        fmt.print("CRIADO :: {}",cidade_nome);

        return cidade_nome;
    }

    public static String getSimplesMenor() {

        String primeiro_nome = "";

        ArrayList<String> iniciais = new ArrayList<String>();
        ArrayList<String> silabas = new ArrayList<String>();
        ArrayList<String> terminais = new ArrayList<String>();

        iniciais.add("am");
        iniciais.add("at");
        iniciais.add("oz");
        iniciais.add("er");
        iniciais.add("um");
        iniciais.add("aq");
        iniciais.add("iw");
        iniciais.add("ub");


        silabas.add("ga");
        silabas.add("ma");
        silabas.add("lo");
        silabas.add("te");
        silabas.add("va");
        silabas.add("la");
        silabas.add("ne");
        silabas.add("xi");
        silabas.add("pa");
        silabas.add("guo");
        silabas.add("gue");

        silabas.add("dou");
        silabas.add("faz");
        silabas.add("rez");
        silabas.add("doc");

        silabas.add("ra");
        silabas.add("gra");
        silabas.add("mox");
        silabas.add("rem");
        silabas.add("zal");
        silabas.add("col");

        terminais.add("maz");
        terminais.add("tom");
        terminais.add("sol");
        terminais.add("dim");
        terminais.add("mesh");
        terminais.add("paz");

        Random sorte = new Random();

        int quantidade_de_silabas = 2 + sorte.nextInt(2);

        for (int s = 0; s < quantidade_de_silabas; s++) {

            if (s==0){

                if(Aleatorio.aleatorio(100)>50){
                    primeiro_nome += iniciais.get(sorte.nextInt(iniciais.size()));
                }else{
                    primeiro_nome += silabas.get(sorte.nextInt(silabas.size()));
                }

            }else{
                if (s == (quantidade_de_silabas - 1)) {
                    primeiro_nome += silabas.get(sorte.nextInt(silabas.size()));
                } else {
                    primeiro_nome += terminais.get(sorte.nextInt(terminais.size()));
                }

            }


        }

        return primeiro_nome;
    }


}
