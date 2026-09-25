package apps.app_atzum.processo_criativo.alfa_sub_tectonico;


import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.ServicoTectonico;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;

public class AlfaSubExtrairPlacasTectonicasContorno {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> alfa_tarefas, Entidade e_sub_atividade, Lista<Entidade> alfa_subtarefas){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "ServicoTectonico";
                String SUB_ATIVIDADE_CORRENTE = "ServicoTectonico::EXTRAIR_PLACAS_TECTONICAS_CONTORNOS";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_sub_atividade.getEntidades(), SUB_ATIVIDADE_CORRENTE);

                ServicoTectonico.EXTRAIR_PLACAS_TECTONICAS_CONTORNOS();

                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_sub_atividade.getEntidades(), SUB_ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_sub_atividade, SUB_ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.ALFA_EXIBIR_PUBLICACAO(alfa_tarefas);
                AtzumProcessoCriativoMarcador.ALFA_SUB_EXIBIR_PUBLICACAO(alfa_subtarefas);

            }
        };
    }

}
