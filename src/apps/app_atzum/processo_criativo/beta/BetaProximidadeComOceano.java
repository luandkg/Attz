package apps.app_atzum.processo_criativo.beta;

import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.AtzumCentralDados;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;

public class BetaProximidadeComOceano {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> beta_tarefas){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "PROXIMIDADE_COM_OCEANO";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumCentralDados.PROXIMIDADE_COM_OCENAO();
                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.BETA_EXIBIR_PUBLICACAO(beta_tarefas);


            }
        };
    }

}
