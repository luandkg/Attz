package apps.app_atzum;

import libs.entt.Entidade;
import libs.luan.Lista;

public class AtzumProcessoCriativoMarcador {

    public static Entidade MARQUE_INICIO(Lista<Entidade> comparativos, String nome) {
        return AtzumProcessoCriativoEmTarefas.MARQUE_INICIO(comparativos, nome);
    }

    public static void MARQUE_FIM(Lista<Entidade> comparativos, String nome) {
        AtzumProcessoCriativoEmTarefas.MARQUE_FIM(comparativos, nome);
    }

    public static void MARQUE_DURACAO(Entidade e_tronarko, String ATIVIDADE_CORRENTE) {
        AtzumProcessoCriativoEmTarefas.MARQUE_DURACAO(e_tronarko, ATIVIDADE_CORRENTE);
    }

    public static void ALFA_EXIBIR_PUBLICACAO(Lista<Entidade> alfa_tarefas) {
        AtzumProcessoCriativoEmTarefas.ALFA_EXIBIR_PUBLICACAO(alfa_tarefas);
    }

    public static void ALFA_SUB_EXIBIR_PUBLICACAO(Lista<Entidade> alfa_subtarefas) {
        AtzumProcessoCriativoEmTarefas.ALFA_SUB_EXIBIR_PUBLICACAO(alfa_subtarefas);
    }

    public static void BETA_EXIBIR_PUBLICACAO(Lista<Entidade> comparativos) {
        AtzumProcessoCriativoEmTarefas.BETA_EXIBIR_PUBLICACAO(comparativos);
    }
}
