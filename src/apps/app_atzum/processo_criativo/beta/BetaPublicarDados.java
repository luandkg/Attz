package apps.app_atzum.processo_criativo.beta;

import apps.app_atzum.AtzumProcessoCriativoMarcador;
import apps.app_atzum.analisadores.Publicador;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.meta_functional.Acao;

public class BetaPublicarDados {

    public static Acao fazer(Entidade e_tronarko, Lista<Entidade> beta_tarefas){
        return new Acao() {
            @Override
            public void fazer() {

                String ATIVIDADE_CORRENTE = "PUBLICAR_DADOS";

                AtzumProcessoCriativoMarcador.MARQUE_INICIO(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);

                Publicador.PUBLICAR_INFO_CLIMATICO();
                Publicador.PUBLICAR_INFO_VEGETACAO();

                Publicador.PUBLICAR_DADOS();


                AtzumProcessoCriativoMarcador.MARQUE_FIM(e_tronarko.getEntidades(), ATIVIDADE_CORRENTE);
                AtzumProcessoCriativoMarcador.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);

                AtzumProcessoCriativoMarcador.BETA_EXIBIR_PUBLICACAO(beta_tarefas);

            }
        };
    }

}
