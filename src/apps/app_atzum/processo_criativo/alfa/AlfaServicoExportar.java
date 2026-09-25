package apps.app_atzum.processo_criativo.alfa;

import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.ServicoExportarTronarko;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;
import libs.tronarko.Tronarko;

public class AlfaServicoExportar {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> alfa_tarefas){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "EXPORTAR_ATZUM";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                ServicoExportarTronarko.EXPORTAR_ATZUM();
                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.ALFA_EXIBIR_PUBLICACAO(alfa_tarefas);

                e_tronarko.at("Fim", Tronarko.getTronAgora().getTextoZerado());

            }
        };
    }

}

