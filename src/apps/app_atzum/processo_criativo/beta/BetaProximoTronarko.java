package apps.app_atzum.processo_criativo.beta;

import libs.entt.Entidade;
import libs.luan.Lista;
import libs.luan.RefInt;
import libs.meta_functional.Acao;
import libs.tronarko.Tronarko;

public class BetaProximoTronarko {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> beta_tarefas, Entidade e_atividade, RefInt eTronarko){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "PROXIMO_TRONARKO";

                e_tronarko.at("Fim", Tronarko.getTronAgora().getTextoZerado());

                e_atividade.at("Tronarko", eTronarko.get() + 1);

            }
        };
    }

}
