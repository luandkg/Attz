package apps.app_atzum;

import apps.app.AgendadorDeTarefas;
import apps.app_atzum.processo_criativo.AtzumAlfaCriativo;
import apps.app_atzum.processo_criativo.AtzumBetaCriativo;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.*;
import libs.tronarko.Tron;
import libs.tronarko.Tronarko;
import libs.tronarko.utils.Ordenador;
import libs.tronarko.utils.StringTronarko;
import libs.zetta.features.ZQC;

public class AtzumProcessoCriativoEmTarefas {

    public static final String ARQUIVO_LOCAL_ALFA = "logs/alfa_processo_criativo.entts";
    public static final String ARQUIVO_LOCAL_BETA = "logs/beta_processo_criativo.entts";

    public static final String ARQUIVO_PROCESSO_CRIATIVO = "logs/atzum_processo_criativo.az";

    public static final int TRONARKO_INICIAR = 7000;


    public static void INIT(int quantidade_de_passos) {


        AtzumCreator.ORGANIZAR();

        String execucao_corrente = Tronarko.getTozte().getTextoInversoZerado_UnderLine().replace("_", "") + Tronarko.getHazde().getTextoZerado().replace(":", "") + ":::" + fmt.zerado(Aleatorio.aleatorio(1000), 6);

        int passo = 1;

        while (passo <= quantidade_de_passos) {

            Tron t1 = Tronarko.getTronAgora();

            if(FS.arquivo_existe(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_ALFA))){
                AtzumProcessoCriativoEmTarefas.EXIBIR_PROCESSO();
            }


            String tronarko_corrente = "";
            String tarefa_corrente = "";

            if (Strings.isDiferente(AtzumProcessoCriativoEmTarefas.GET_ALFA_TAREFA(), "TudoOK")) {
                tarefa_corrente = GET_ALFA_TAREFA();
                AtzumProcessoCriativoEmTarefas.INIT_ALFA_SEQUENCIAL();
            } else {
                tronarko_corrente = GET_BETA_TRONARKO();
                tarefa_corrente = GET_BETA_TAREFA();
                AtzumProcessoCriativoEmTarefas.INIT_BETA_SEQUENCIAL();
            }

            AtzumProcessoCriativoEmTarefas.EXIBIR_PROCESSO();

            Tron t2 = Tronarko.getTronAgora();


            Entidade log = new Entidade();
            log.at("Tron", Tronarko.getTronAgora().getTextoZerado());
            log.at("Execucao", execucao_corrente);
            log.at("Passo", passo);
            log.at("Iniciado", t1.getTextoZerado());
            log.at("Terminado", t2.getTextoZerado());
            log.at("Tempo", Tronarko.TRON_DIFERENCA(t1, t2));
            log.at("Tronarko", tronarko_corrente);
            log.at("Tarefa", tarefa_corrente);

            ZQC.INSERIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_PROCESSO_CRIATIVO), "AtzumProcessoCriativo", log);


            passo += 1;
        }

    }


    public static void INIT_ALFA_SEQUENCIAL() {

        Lista<Entidade> alfa_dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_ALFA));

        Entidade e_atividade = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "TarefaCorrente");
        Entidade e_alfa_tarefas = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "Tarefas");
        Entidade e_alfa_subtarefa = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "SubTarefas");


        Lista<Entidade> alfa_tarefas = e_alfa_tarefas.getEntidades();
        Lista<Entidade> alfa_subtarefas = e_alfa_subtarefa.getEntidades();


        Entidade e_tronarko = MARQUE_INICIO(alfa_tarefas, "TRONARKO");
        Entidade e_sub_atividade = MARQUE_INICIO(alfa_subtarefas, "TRONARKO");

        String tarefa = e_atividade.at("Tarefa");

        AgendadorDeTarefas tarefas = new AgendadorDeTarefas();

        AtzumAlfaCriativo.CRIAR_TAREFAS(tarefas, alfa_tarefas, alfa_subtarefas, e_tronarko, e_sub_atividade);


        tarefas.setTarefaCorrente(tarefa);
        tarefas.executeUma();

        e_atividade.at("Tarefa", tarefas.getTarefaCorrente());

        ENTT.GUARDAR(alfa_dados, AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_ALFA));

    }


    public static void INIT_BETA_SEQUENCIAL() {

        Lista<Entidade> beta_dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_BETA));

        Entidade e_atividade = ENTT.GET_SEMPRE(beta_dados, "Conjunto", "TarefaCorrente");
        Entidade e_beta_comparativos = ENTT.GET_SEMPRE(beta_dados, "Conjunto", "Tarefas");

        Lista<Entidade> beta_tarefas = e_beta_comparativos.getEntidades();


        int tronarko = e_atividade.atIntOuPadrao("Tronarko", TRONARKO_INICIAR);
        String tarefa = e_atividade.at("Tarefa");


        fmt.print(">> Tarefa :: {} - {}", tronarko, tarefa);


        Entidade e_tronarko = MARQUE_INICIO(beta_tarefas, "TRONARKO_" + tronarko);


        BETA_EXIBIR_PUBLICACAO(beta_tarefas);


        AgendadorDeTarefas tarefas = new AgendadorDeTarefas();

        AtzumBetaCriativo.CRIAR_TAREFAS(tarefas, beta_tarefas, e_atividade, e_tronarko, tronarko);


        tarefas.setTarefaCorrente(tarefa);
        tarefas.executeUma();

        e_atividade.at("Tarefa", tarefas.getTarefaCorrente());


        ENTT.GUARDAR(beta_dados, AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_BETA));
    }


    public static Entidade MARQUE_INICIO(Lista<Entidade> comparativos, String nome) {
        ENTT.GET_SEMPRE(comparativos, "Acao", nome).at("Inicio", Tronarko.getTronAgora().getTextoZerado());

        return ENTT.GET_SEMPRE(comparativos, "Acao", nome);
    }

    public static void MARQUE_FIM(Lista<Entidade> comparativos, String nome) {

        ENTT.GET_SEMPRE(comparativos, "Acao", nome).at("Fim", Tronarko.getTronAgora().getTextoZerado());

        Entidade item = ENTT.GET_SEMPRE(comparativos, "Acao", nome);

        Tron iniciado = StringTronarko.PARSER_TRON(item.at("Inicio"));
        Tron terminado = StringTronarko.PARSER_TRON(item.at("Fim"));

        item.at("Tempo", "+ " + Tronarko.TRON_DIFERENCA(iniciado, terminado));

    }

    public static void MARQUE_DURACAO(Entidade e_tronarko, String ATIVIDADE_CORRENTE) {
        e_tronarko.at(ATIVIDADE_CORRENTE, OBTER_TEMPO_MARCADO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE));
    }

    public static String OBTER_TEMPO_MARCADO(Lista<Entidade> comparativos, String nome) {
        Entidade item = ENTT.GET_SEMPRE(comparativos, "Acao", nome);
        return item.at("Tempo");
    }


    public static void BETA_EXIBIR_PUBLICACAO(Lista<Entidade> beta_tarefas) {
        ENTT.EXIBIR_TABELA_COM_NOME(beta_tarefas, "PROCESSO CRIATIVO ATZUM - BETA");
    }


    public static void ALFA_EXIBIR_PUBLICACAO(Lista<Entidade> alfa_tarefas) {
        ENTT.EXIBIR_TABELA_COM_NOME(alfa_tarefas, "PROCESSO CRIATIVO ATZUM - ALFA");
    }

    public static void ALFA_SUB_EXIBIR_PUBLICACAO(Lista<Entidade> alfa_subtarefas) {

        for(Entidade e : alfa_subtarefas){
            if(!e.at("Inicio").isEmpty() && !e.at("Fim").isEmpty()){
                e.at("Tempo","+ "+Tronarko.TRON_DIFERENCA_ITTAS(StringTronarko.PARSER_TRON(e.at("Inicio")),StringTronarko.PARSER_TRON(e.at("Fim"))));
            }
        }

        ENTT.EXIBIR_TABELA_COM_NOME(alfa_subtarefas, "PROCESSO CRIATIVO ATZUM - ALFA :: SUB");

    }


    public static void EXIBIR_PROCESSO() {

        Lista<Entidade> alfa_dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_ALFA));

        Entidade e_atividade = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "TarefaCorrente");
        Entidade e_alfa_tarefas = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "Tarefas");
        Entidade e_alfa_subtarefa = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "SubTarefas");


        ENTT.EXIBIR_TABELA_COM_NOME(alfa_dados, "ALFA - GERAL");

        ALFA_EXIBIR_PUBLICACAO(e_alfa_tarefas.getEntidades());

        Lista<Entidade> alfa_sub_dados = e_alfa_subtarefa.getEntidades();
        if (alfa_sub_dados.getQuantidade() > 0) {
            ALFA_SUB_EXIBIR_PUBLICACAO(alfa_sub_dados.get(0).getEntidades());
        }

        Lista<Entidade> beta_dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_BETA));
        BETA_EXIBIR_PUBLICACAO(beta_dados);

    }


    public static String GET_ALFA_TAREFA() {
        Lista<Entidade> alfa_dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_ALFA));

        Entidade e_atividade = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "TarefaCorrente");
        Entidade e_alfa_tarefas = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "Tarefas");
        Entidade e_alfa_subtarefa = ENTT.GET_SEMPRE(alfa_dados, "Conjunto", "SubTarefas");


        String tarefa = e_atividade.at("Tarefa");
        return tarefa;
    }

    public static String GET_BETA_TAREFA() {
        Lista<Entidade> beta_dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_BETA));
        Entidade e_atividade = ENTT.GET_SEMPRE(beta_dados, "Conjunto", "TarefaCorrente");
        String beta_tronarko = e_atividade.at("Tarefa");

        return beta_tronarko;
    }

    public static String GET_BETA_TRONARKO() {
        Lista<Entidade> beta_dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_BETA));
        Entidade e_atividade = ENTT.GET_SEMPRE(beta_dados, "Conjunto", "TarefaCorrente");
        String beta_tronarko = e_atividade.at("Tronarko");

        return beta_tronarko;
    }


    public static void ALFA_ZERAR() {
        ENTT.GUARDAR(new Lista<Entidade>(), AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_ALFA));
    }

    public static void BETA_ZERAR() {
        ENTT.GUARDAR(new Lista<Entidade>(), AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_LOCAL_BETA));
    }


    public static void EXIBIR_EXECUTANDO() {
        fmt.print(">> Alfa -->> {} ", AtzumProcessoCriativoEmTarefas.GET_ALFA_TAREFA());
        fmt.print(">> Beta -->> {} :: {}", AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO(), AtzumProcessoCriativoEmTarefas.GET_BETA_TAREFA());
    }

    public static void VER_BANCO(){

        ZQC.EXIBIR_COLECAO(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_PROCESSO_CRIATIVO), "AtzumProcessoCriativo");

        Lista<Entidade> dadosBrutos = ZQC.COLECAO_ENTIDADES(AtzumCreator.LOCAL_GET_ARQUIVO(ARQUIVO_PROCESSO_CRIATIVO), "AtzumProcessoCriativo");

        Lista<Entidade> grupo = ENTT.AGRUPAR_E_ORDENAR(dadosBrutos, "Tronarko");

        int max = 0;
        Lista<String> tarefasOrdem = Lista.CRIAR();

        for(Entidade g : grupo){

            ENTT.ORDENAR_COM_ORDENADOR(g.getEntidades(),"Iniciado", Ordenador.TRON_ORDENADOR_STRING());


            g.at("Iniciado",ENTT.GET_PRIMEIRO(g.getEntidades()).at("Iniciado"));
            g.at("Terminado",ENTT.GET_ULTIMO(g.getEntidades()).at("Terminado"));

            if(!g.at("Tronarko").isEmpty() && ENTT.EXISTE(g.getEntidades(),"Tarefa","PROXIMO_TRONARKO")){
                g.at("Status","CONCLUIDO");
            }else if(!g.at("Tronarko").isEmpty()) {
                g.at("Status", "EXECUTANDO");
            }else  if(g.at("Tronarko").isEmpty() && ENTT.EXISTE(g.getEntidades(),"Tarefa","EXPORTAR_ATZUM")){
                g.at("Status","CONCLUIDO");
            }

            if(ENTT.GET_ULTIMO(g.getEntidades()).is("Tarefa","PROXIMO_TRONARKO")){
                max = ENTT.CONTAGEM(g.getEntidades());
                tarefasOrdem=ENTT.FILTRAR_UNICOS(g.getEntidades(),"Tarefa");
            }

            g.at("Tarefa",ENTT.GET_ULTIMO(g.getEntidades()).at("Tarefa"));
            g.at("Etapa",  Strings.GET_POSICAO(tarefasOrdem,g.at("Tarefa"))+1 + " de " + max);

            g.at("Tempo",Tronarko.TRON_DIFERENCA_ARKOS_E_ITTAS(StringTronarko.PARSER_TRON(g.at("Iniciado")),StringTronarko.PARSER_TRON(g.at("Terminado"))));

            ENTT.EXIBIR_TABELA(g.getEntidades());
        }


        ENTT.EXIBIR_TABELA_COM_NOME(grupo,"Por Tronarko");

    }
}
