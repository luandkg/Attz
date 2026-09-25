package apps.app_atzum.processo_criativo;

import apps.app.AgendadorDeTarefas;
import apps.app_atzum.processo_criativo.alfa.*;
import apps.app_atzum.processo_criativo.alfa_sub_regioes.*;
import apps.app_atzum.processo_criativo.alfa_sub_tectonico.*;
import apps.app_atzum.processo_criativo.beta.*;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.luan.RefInt;

public class AtzumTarefas {

    public static void ALFA_CRIAR_TAREFAS(AgendadorDeTarefas tarefas, Lista<Entidade> alfa_tarefas, Lista<Entidade> alfa_subtarefas, Entidade e_tronarko, Entidade e_sub_atividade) {


        tarefas.criarSequenciaDupla("", "ServicoInicial", "ServicoRegioes::ORGANIZAR_REGIOES", AlfaOrganizarRegioes.fazer(e_tronarko, alfa_tarefas));


        // SUBTAREFAS - REGIOES

        ALFA_CRIAR_SUB_TAREFAS_REGIOES(tarefas, alfa_tarefas, alfa_subtarefas, e_tronarko, e_sub_atividade);

        tarefas.criarSequencia("ServicoRegioes::CONCLUIDO", "ServicoTectonico::INICIAR_PLACAS", AlfaServicoRegioes.fazer(e_tronarko, alfa_tarefas, e_sub_atividade, alfa_subtarefas));


        ALFA_CRIAR_SUB_TAREFAS_TECTONICO(tarefas, alfa_tarefas, alfa_subtarefas, e_tronarko, e_sub_atividade);


        // FIM SUBTAREFAS

        tarefas.criarSequencia("ServicoRelevo", "ServicoUmidade", AlfaServicoRelevo.fazer(e_tronarko, alfa_tarefas));

        tarefas.criarSequencia("ServicoUmidade", "ServicoTemperatura", AlfaServicoUmidade.fazer(e_tronarko, alfa_tarefas));

        tarefas.criarSequencia("ServicoTemperatura", "ServicoCorrelacionar", AlfaServicoTemperatura.fazer(e_tronarko, alfa_tarefas));

        tarefas.criarSequencia("ServicoCorrelacionar", "ServicoMassasDeAr", AlfaServicoCorrelacionar.fazer(e_tronarko, alfa_tarefas));

        tarefas.criarSequencia("ServicoMassasDeAr", "ServicoSensores", AlfaServicoMassaDeAr.fazer(e_tronarko, alfa_tarefas));

        tarefas.criarSequencia("ServicoSensores", "EXPORTAR_ATZUM", AlfaServicoSensores.fazer(e_tronarko, alfa_tarefas));

        tarefas.criarSequencia("EXPORTAR_ATZUM", "TudoOK", AlfaServicoExportar.fazer(e_tronarko, alfa_tarefas));

    }


    private static void ALFA_CRIAR_SUB_TAREFAS_REGIOES(AgendadorDeTarefas tarefas, Lista<Entidade> alfa_tarefas, Lista<Entidade> alfa_subtarefas, Entidade e_tronarko, Entidade e_sub_atividade) {

        tarefas.criarSequencia("ServicoRegioes::ORGANIZAR_REGIOES", "ServicoRegioes::EXPANDIR_REGIOES_ATE_A_MARGEM", AlfaSubOrganizarRegioes.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::EXPANDIR_REGIOES_ATE_A_MARGEM", "ServicoRegioes::EXTRAIR_REGIOES_CONTORNOS", AlfaSubExpandirRegioesAteMargem.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::EXTRAIR_REGIOES_CONTORNOS", "ServicoRegioes::ORGANIZAR_DADOS_REGIOES", AlfaSubExtrairRegioesContorno.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::ORGANIZAR_DADOS_REGIOES", "ServicoRegioes::EXTRAIR_CONTORNO_OCEANICO", AlfaSubOrganizarDadosRegioes.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::EXTRAIR_CONTORNO_OCEANICO", "ServicoRegioes::EXTRAIR_DISTANCIA_OCEANICA", AlfaSubExtrairContornoOceanico.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::EXTRAIR_DISTANCIA_OCEANICA", "ServicoRegioes::PROXIMIDADE_COM_OCEANO", AlfaSubExtrairDistanciaOceanica.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::PROXIMIDADE_COM_OCEANO", "ServicoRegioes::PROXIMIDADE_COM_TERRA", AlfaSubExtrairProximidadeComOceano.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::PROXIMIDADE_COM_TERRA", "ServicoRegioes::ORGANIZAR_DADOS_PLANETA", AlfaSubExtrairProximidadeComTerra.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::ORGANIZAR_DADOS_PLANETA", "ServicoRegioes::ORGANIZAR_OCEANOS", AlfaSubOrganizarDadosPlaneta.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::ORGANIZAR_OCEANOS", "ServicoRegioes::RENDERIZAR_OCEANOS", AlfaSubOrganizarOceano.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoRegioes::RENDERIZAR_OCEANOS", "ServicoRegioes::CONCLUIDO", AlfaSubRenderizarOceano.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

    }


    private static void ALFA_CRIAR_SUB_TAREFAS_TECTONICO(AgendadorDeTarefas tarefas, Lista<Entidade> alfa_tarefas, Lista<Entidade> alfa_subtarefas, Entidade e_tronarko, Entidade e_sub_atividade) {

        tarefas.criarSequencia("ServicoTectonico::INICIAR_PLACAS", "ServicoTectonico::EXTRAIR_PLACAS_TECTONICAS_CONTORNOS", AlfaSubIniciarPlacas.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoTectonico::EXTRAIR_PLACAS_TECTONICAS_CONTORNOS", "ServicoTectonico::CRIAR_PLACAS_COM_LIMITES", AlfaSubExtrairPlacasTectonicasContorno.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoTectonico::CRIAR_PLACAS_COM_LIMITES", "ServicoTectonico::GUARDAR_DADOS_PLACAS_TECTONICAS", AlfaSubCriarPlacasComLimite.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoTectonico::GUARDAR_DADOS_PLACAS_TECTONICAS", "ServicoTectonico::DEFINIR_AREAS_DE_ATIVIDADE_SISMICA", AlfaSubGuardarDadosPlacasTectonicas.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoTectonico::DEFINIR_AREAS_DE_ATIVIDADE_SISMICA", "ServicoTectonico::VULCANIZAR", AlfaSubDefinirAreasAtividadeSismica.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));

        tarefas.criarSequencia("ServicoTectonico::VULCANIZAR", "ServicoRelevo", AlfaSubVulcanizar.fazer(e_tronarko,alfa_tarefas,e_sub_atividade,alfa_subtarefas));
    }

    public static void BETA_CRIAR_TAREFAS(AgendadorDeTarefas tarefas, Lista<Entidade> beta_tarefas, Entidade e_atividade, Entidade e_tronarko, int tronarko) {

        tarefas.criarSequenciaDupla("", "CONSTRUIR_TRONARKO", "TRONARKO_PROCESSAR_SUPERARKOS", BetaConstruirTronarko.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("TRONARKO_PROCESSAR_SUPERARKOS", "FENOMENOS_TECTONICOS", BetaProcessarTronarko.fazer(e_tronarko, beta_tarefas, new RefInt(tronarko)));

        tarefas.criarSequencia("FENOMENOS_TECTONICOS", "SENSORES_ORGANIZAR_POR_SENSORES_COM_QUADRUM", BetaFenomenoTectonico.fazer(e_tronarko, beta_tarefas,new RefInt(tronarko)));

        tarefas.criarSequencia("SENSORES_ORGANIZAR_POR_SENSORES_COM_QUADRUM", "SENSORES_ORGANIZAR_POR_SENSORES_A_PARTIR_DE_QUADRUM", BetaOrganizarComQuadrum.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("SENSORES_ORGANIZAR_POR_SENSORES_A_PARTIR_DE_QUADRUM", "OBSERAR_VARIADORES", BetaOrganizarAPartirDeQuadrum.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("OBSERAR_VARIADORES", "CLIMA", BetaOrganizarVariadores.fazer(e_tronarko, beta_tarefas,new RefInt(tronarko)));

        tarefas.criarSequencia("CLIMA", "VEGETACAO",BetaTarefaClima.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("VEGETACAO", "PUBLICAR_DADOS", BetaTarefaVegetacao.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("PUBLICAR_DADOS", "PROXIMIDADE_COM_OCEANO", BetaPublicarDados.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("PROXIMIDADE_COM_OCEANO", "NOMEAR_CIDADES", BetaProximidadeComOceano.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("NOMEAR_CIDADES", "ORGANIZAR_DADOS_TRONARKO", BetaNomearCidades.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("ORGANIZAR_DADOS_TRONARKO", "EXPORTAR_TRONARKO", BetaOrganizarDadosTronarko.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("EXPORTAR_TRONARKO", "PROXIMO_TRONARKO", BetaExportarTronarko.fazer(e_tronarko, beta_tarefas));

        tarefas.criarSequencia("PROXIMO_TRONARKO", "CONSTRUIR_TRONARKO", BetaProximoTronarko.fazer(e_tronarko, beta_tarefas,e_atividade,new RefInt(tronarko)));

    }
}
