package apps.app_atzum.processo_criativo.alfa;

import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.ServicoUmidade;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;

public class AlfaServicoUmidade {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> alfa_tarefas){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "ServicoUmidade";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                ServicoUmidade.INIT();
                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.ALFA_EXIBIR_PUBLICACAO(alfa_tarefas);

            }
        };
    }

}

