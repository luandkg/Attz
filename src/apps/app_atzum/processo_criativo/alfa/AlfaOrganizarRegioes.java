package apps.app_atzum.processo_criativo.alfa;

import apps.app_atzum.AtzumProcessoCriativoEmTarefas;
import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.ServicoInicial;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;
import libs.tronarko.Tronarko;

public class AlfaOrganizarRegioes {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> alfa_tarefas){
        return new Acao() {
            @Override
            public void fazer() {

                e_tronarko.at("Inicio", Tronarko.getTronAgora().getTextoZerado());
                e_tronarko.at("Fim", "");

                AtzumProcessoCriativoEmTarefas.ALFA_ZERAR();
                AtzumProcessoCriativoEmTarefas.BETA_ZERAR();


                String ATIVIDADE_CORRENTE = "ServicoInicial";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                ServicoInicial.INIT();
                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);


                AtzumProcessoCriativoMarcador.ALFA_EXIBIR_PUBLICACAO(alfa_tarefas);

            }
        };
    }


}
