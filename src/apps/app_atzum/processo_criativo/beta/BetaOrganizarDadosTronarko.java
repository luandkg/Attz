package apps.app_atzum.processo_criativo.beta;

import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.servicos.ServicoExportarTronarko;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;

public class BetaOrganizarDadosTronarko {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> beta_tarefas){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "ORGANIZAR_DADOS_TRONARKO";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);

                ServicoExportarTronarko.EXPORTAR_SENSORES_SUPERARKO();
                ServicoExportarTronarko.EXPORTAR_MODELOS();
                ServicoExportarTronarko.EXPORTAR_INFOGRAFICOS();
                ServicoExportarTronarko.EXPORTAR_DADOS_CIDADES();
                ServicoExportarTronarko.CONSOLIDAR_DADOS_CIDADES();

                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.BETA_EXIBIR_PUBLICACAO(beta_tarefas);

            }
        };
    }

}
