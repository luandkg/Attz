package apps.app_atzum.processo_criativo.beta;

import apps.app_atzum.AtzumProcessoCriativoEmTarefas;
import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.ServicoFenomenoTectonico;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.luan.RefInt;
import libs.meta_functional.Acao;

public class BetaFenomenoTectonico {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> beta_tarefas, RefInt eTronarko){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "FENOMENOS_TECTONICOS";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);

                if (eTronarko.get() == AtzumProcessoCriativoEmTarefas.TRONARKO_INICIAR) {
                    ServicoFenomenoTectonico.ZERAR();
                }

                ServicoFenomenoTectonico.INIT(eTronarko.get());

                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.BETA_EXIBIR_PUBLICACAO(beta_tarefas);

            }
        };
    }

}
