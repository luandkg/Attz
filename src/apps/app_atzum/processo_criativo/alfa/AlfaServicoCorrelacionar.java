package apps.app_atzum.processo_criativo.alfa;

import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.ServicoCorrelacionar;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;

public class AlfaServicoCorrelacionar {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> alfa_tarefas){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "ServicoCorrelacionar";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                ServicoCorrelacionar.INIT();
                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.ALFA_EXIBIR_PUBLICACAO(alfa_tarefas);

            }
        };
    }

}

