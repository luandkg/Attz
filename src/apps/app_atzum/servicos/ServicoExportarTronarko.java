package apps.app_atzum.servicos;

import apps.app_atzum.Atzum;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumProcessoCriativoEmTarefas;
import apps.app_atzum.animadores.AnimadoresDeSensores;
import apps.app_atzum.renderizadores.TronarkoRenderizadorSensores;
import libs.arquivos.DSInterno;
import libs.arquivos.IM;
import libs.arquivos.Zipper;
import libs.arquivos.ds.DS;
import libs.arquivos.ds.DSIndexador;
import libs.arquivos.ds.DSItem;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.imagem.Imagem;
import libs.luan.*;
import libs.tronarko.Tronarko;

public class ServicoExportarTronarko {


    public static void VER_ATZUM() {

        String arquivo_atzum = AtzumCreator.LOCAL_GET_ARQUIVO("tronarkos/atzum.ds");

        DS.DUMP_TABELA(arquivo_atzum);


        Opcional<DSItem> op_init = DS.buscar_item(arquivo_atzum, "@Atzum.index");

        if (Opcional.IS_OK(op_init)) {
            Lista<DSItem> indice = DSIndexador.GET_INDEX(arquivo_atzum, "@Atzum.index");
            // DSIndexador.VER_TAMANHO(indice);
            DS.DUMP_ITENS(indice);
            // ENTT.EXIBIR_TABELA_COM_NOME(indice, "@Atzum.index");
            fmt.print("Indice Tamanho >> {}", fmt.formatar_tamanho_precisao_dupla(op_init.get().getTamanhoUtilizadoPreAlocado()));
        }

    }

    public static void EXPORTAR_ATZUM() {

        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("tronarkos"));

        String arquivo_atzum = AtzumCreator.LOCAL_GET_ARQUIVO("tronarkos/atzum.ds");

        DS.limpar(arquivo_atzum);

        DS.adicionar_pre_alocado(arquivo_atzum, "@Atzum.index", Matematica.KB(500));

        fmt.print(">> Adicionar objetos");


        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@parametros/cidades.entts", AtzumCreator.LOCAL_GET_ARQUIVO("parametros/CIDADES_NOMES.entts"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@parametros/vulcanismo.entts", AtzumCreator.LOCAL_GET_ARQUIVO("dados/vulcanismo.entts"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@parametros/tectonico_vulcoes.entts", AtzumCreator.LOCAL_GET_ARQUIVO("dados/tectonico_vulcoes.entts"));



        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_regioes.im", AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes.png"));
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_contorno_oceanico.im", AtzumCreator.LOCAL_GET_ARQUIVO("atzum_contorno_oceanico.png"));
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_regioes_contornos.im", AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes_contornos.png"));
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_terra.im", AtzumCreator.LOCAL_GET_ARQUIVO("atzum_terra.png"));
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_oceanos.im", AtzumCreator.LOCAL_GET_ARQUIVO("atzum_oceanos.png"));

        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_relevo.im", AtzumCreator.LOCAL_GET_ARQUIVO("build/planeta/atzum_relevo.png"));
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_relevo_terra.im", AtzumCreator.LOCAL_GET_ARQUIVO("build/planeta/atzum_relevo_terra.png"));
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_relevo_agua.im", AtzumCreator.LOCAL_GET_ARQUIVO("build/planeta/atzum_relevo_agua.png"));

        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_tectonismo_placas.im", AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas.png"));
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum, "@imagem/atzum_tectonismo_limites.im", AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_limites.png"));


        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/planeta.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/planeta.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/oceanos.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/oceanos.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/regioes.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/regioes.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/subregioes.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/subregioes.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/relevo.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/relevo.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/distancia_terra.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/distancia_terra.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/distancia_oceanica.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/distancia_oceanica.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/sensor_proximidade.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/sensor_proximidade.qtt"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum, "@dados/placas_tectonicas.qtt", AtzumCreator.LOCAL_GET_ARQUIVO("dados/placas_tectonicas.qtt"));




        Opcional<DSItem> op_init = DS.buscar_item(arquivo_atzum, "@Atzum.index");

        if (Opcional.IS_OK(op_init)) {

            fmt.print(">> Indexar");

            DSIndexador.INDEX(arquivo_atzum, "@Atzum.index");

            Lista<DSItem> indice = DSIndexador.GET_INDEX(arquivo_atzum, "@Atzum.index");

            DS.DUMP_ITENS(indice);
            fmt.print("Indice Tamanho >> {}", fmt.formatar_tamanho_precisao_dupla(op_init.get().getTamanhoUtilizadoPreAlocado()));

        }

        fmt.print(">> Tudo OK !");

    }

    public static void EXPORTAR_TRONARKO() {

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        String arquivo_atzum_tronarko = AtzumCreator.LOCAL_GET_ARQUIVO("tronarkos/atzum_tronarko_" + tronarko_corrente + ".ds");

        DS.limpar(arquivo_atzum_tronarko);

        DS.adicionar_pre_alocado(arquivo_atzum_tronarko, "@AtzumTronarko.index", Matematica.KB(500));

        fmt.print(">> Adicionar objetos");


        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);


        String arquivo_sensores_superarko_compactado = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_sensores_por_superarko.dz");
        String arquivo_cidades_consolidados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades_geral.entts");
        String arquivo_cidades_dados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades.entts");
        String arquivo_modelo_dados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_modelos.entts");

        String arquivo_info_modelo_climatico = AtzumCreator.LOCAL_GET_ARQUIVO("comparativos/tronarko_info_" + tronarko_corrente + "_modelo_climatico.png");
        String arquivo_info_modelo_vegetacao = AtzumCreator.LOCAL_GET_ARQUIVO("comparativos/tronarko_info_" + tronarko_corrente + "_modelo_vegetacao.png");

        String arquivo_modelo_climatico = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_modelo_climatico.png");
        String arquivo_modelo_vegetacao = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_modelo_vegetacao.png");

        String arquivo_fenomenos_atmosfericos = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/fenomenos_atmosfericos.entts");
        String arquivo_fenomenos_tectonicos = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/fenomenos_tectonicos.entts");



        Entidade e_tronarko = new Entidade();
        e_tronarko.at("Tronarko", tronarko_corrente);
        e_tronarko.at("Iniciado", Tronarko.getTronAgora().getTextoZerado());




        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/modelo_climatico.im", arquivo_modelo_climatico);
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/modelo_vegetacao.im", arquivo_modelo_vegetacao);

        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/modelo_info_climatico.im", arquivo_info_modelo_climatico);
        EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/modelo_info_vegetacao.im", arquivo_info_modelo_vegetacao);


        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@informacoes/fenomenos_atmosfericos.entts", arquivo_fenomenos_atmosfericos);
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@informacoes/fenomenos_tectonicos.entts", arquivo_fenomenos_tectonicos);



        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@dados/tronarko_sensores_por_superarko.dz", arquivo_sensores_superarko_compactado);
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@dados/tronarko_cidades_dados_publicados.entts", arquivo_cidades_consolidados);
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@dados/tronarko_cidades.entts", arquivo_cidades_dados);
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@dados/tronarko_modelos.entts", arquivo_modelo_dados);



        Lista<Entidade> indice_ultra_rapido = DSInterno.CRIAR_INDICE_ULTRA_RAPIDO(arquivo_cidades_consolidados,"CidadePos");

        EXPORTACAO_ADICIONAR_ENTIDADES(arquivo_atzum_tronarko, "@dados/tronarko_cidades_dados_publicados_indice_por_cidade.entts", indice_ultra_rapido);




        fmt.print(">> Exportando sensores : Umidade !");
        TronarkoRenderizadorSensores.TRONARKO_VER_SENSORES_UMIDADE();

        for (int modelo = 1; modelo <= 10; modelo++) {
            EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/umidade/modelo_" + modelo + ".im", TronarkoRenderizadorSensores.GET_ARQUIVO(modelo));
        }

        fmt.print(">> Exportando sensores : Temperatura !");
        TronarkoRenderizadorSensores.TRONARKO_VER_SENSORES_TEMPERATURA();

        for (int modelo = 1; modelo <= 10; modelo++) {
            EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/temperatura/modelo_" + modelo + ".im", TronarkoRenderizadorSensores.GET_ARQUIVO(modelo));
        }

        fmt.print(">> Exportando sensores : Massa de Ar !");
        TronarkoRenderizadorSensores.TRONARKO_VER_SENSORES_MASSAS_DE_AR();

        for (int modelo = 1; modelo <= 10; modelo++) {
            EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/massa_de_ar/modelo_" + modelo + ".im", TronarkoRenderizadorSensores.GET_ARQUIVO(modelo));
        }

        fmt.print(">> Exportando sensores : Fator Climático!");
        TronarkoRenderizadorSensores.TRONARKO_VER_SENSORES_FATORES_CLIMATICOS();

        for (int modelo = 1; modelo <= 10; modelo++) {
            EXPORTACAO_ADICIONAR_IMAGEM(arquivo_atzum_tronarko, "@imagem/fator_climatico/modelo_" + modelo + ".im", TronarkoRenderizadorSensores.GET_ARQUIVO(modelo));
        }




        // PROCESSAR ANIMAÇÕES

        AnimadoresDeSensores.TRONARKO_VER_SENSORES_TEMPERATURA();
        AnimadoresDeSensores.TRONARKO_VER_SENSORES_UMIDADE();
        AnimadoresDeSensores.TRONARKO_VER_SENSORES_MASSA_DE_AR();
        AnimadoresDeSensores.TRONARKO_VER_SENSORES_FATORES_CLIMATICOS();

        // ADICIONAR ANIMAÇÕES
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@animacao/temperatura.vi", AtzumCreator.LOCAL_GET_ARQUIVO("videos_sensores/temperatura.vi"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@animacao/umidade.vi", AtzumCreator.LOCAL_GET_ARQUIVO("videos_sensores/umidade.vi"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@animacao/massa_de_ar.vi", AtzumCreator.LOCAL_GET_ARQUIVO("videos_sensores/massa_de_ar.vi"));
        EXPORTACAO_ADICIONAR_ARQUIVO(arquivo_atzum_tronarko, "@animacao/fatores_climaticos.vi", AtzumCreator.LOCAL_GET_ARQUIVO("videos_sensores/fatores_climaticos.vi"));



        e_tronarko.at("Finalizado", Tronarko.getTronAgora().getTextoZerado());

        EXPORTACAO_ADICIONAR_ENTIDADE(arquivo_atzum_tronarko, "@parametros/tronarko.entt", e_tronarko);



        Opcional<DSItem> op_init = DS.buscar_item(arquivo_atzum_tronarko, "@AtzumTronarko.index");

        if (Opcional.IS_OK(op_init)) {

            fmt.print(">> Indexar");

            DSIndexador.INDEX(arquivo_atzum_tronarko, "@AtzumTronarko.index");

            Lista<DSItem> indice = DSIndexador.GET_INDEX(arquivo_atzum_tronarko, "@AtzumTronarko.index");
            DS.DUMP_ITENS(indice);

            //  ENTT.EXIBIR_TABELA_COM_NOME(indice, "@AtzumTronarko.index");
            fmt.print("Indice Tamanho >> {}", fmt.formatar_tamanho_precisao_dupla(op_init.get().getTamanhoUtilizadoPreAlocado()));

        }

        fmt.print(">> Tudo OK !");

    }

    public static void EXPORTACAO_ADICIONAR_ENTIDADE(String arquivo_atzum, String item_nome, Entidade entidade) {
        fmt.print("\t ++ {}", item_nome);
        DS.adicionar(arquivo_atzum, item_nome, ENTT.TO_DOCUMENTO(entidade));
    }

    public static void EXPORTACAO_ADICIONAR_ENTIDADES(String arquivo_atzum, String item_nome, Lista<Entidade> entidades) {
        fmt.print("\t ++ {}", item_nome);
        DS.adicionar(arquivo_atzum, item_nome, ENTT.TO_DOCUMENTO(entidades));
    }


    public static void EXPORTACAO_ADICIONAR_ARQUIVO(String arquivo_atzum, String item_nome, String arquivo) {
        fmt.print("\t ++ {}", item_nome);
        DS.adicionar_arquivo(arquivo_atzum, item_nome, arquivo);
    }

    public static void EXPORTACAO_ADICIONAR_IMAGEM(String arquivo_atzum, String item_nome, String arquivo_imagem) {
        fmt.print("\t ++ {}", item_nome);
        DS.adicionar(arquivo_atzum, item_nome, IM.salvar_to_chunks(Imagem.getImagem(arquivo_imagem)));
    }


    public static void EXPORTAR_SENSORES_SUPERARKO() {

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);

        String arquivo_sensores_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");
        String arquivo_sensores_superarko_compactado = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_sensores_por_superarko.dz");

        DS.limpar(arquivo_sensores_superarko_compactado);

        Lista<Entidade> comparativo = ENTT.CRIAR_LISTA();

        long tamanho_total = 0;

        for (DSItem superarko_item : DS.ler_todos(arquivo_sensores_superarko)) {

            fmt.print("Superarko : {} -->> {}", superarko_item.getNome(), fmt.formatar_tamanho_precisao_dupla(superarko_item.getTamanho()));

            DS.adicionar(arquivo_sensores_superarko_compactado, superarko_item.getNome(), Zipper.COMPACTAR(superarko_item.getBytes()));

            Entidade e_item = ENTT.GET_SEMPRE(comparativo, "Superarko", superarko_item.getNome());
            e_item.at("Normal", fmt.formatar_tamanho_precisao_dupla(superarko_item.getTamanho()));

            tamanho_total += superarko_item.getTamanho();
        }

        fmt.print("Modelo Completo : {} -->> {}", DS.contar(arquivo_sensores_superarko), fmt.formatar_tamanho_precisao_dupla(tamanho_total));


        long tamanho_compactado_total = 0;

        for (DSItem superarko_item : DS.ler_todos(arquivo_sensores_superarko_compactado)) {

            // fmt.print("Superarko : {} -->> {}", superarko_item.getNome(),fmt.formatar_tamanho_precisao_dupla(superarko_item.getTamanho()));

            Entidade e_item = ENTT.GET_SEMPRE(comparativo, "Superarko", superarko_item.getNome());
            e_item.at("Compactado", fmt.formatar_tamanho_precisao_dupla(superarko_item.getTamanho()));

            tamanho_compactado_total += superarko_item.getTamanho();
        }

        fmt.print("Modelo Completo Compactado : {} -->> {}", DS.contar(arquivo_sensores_superarko_compactado), fmt.formatar_tamanho_precisao_dupla(tamanho_compactado_total));

        ENTT.EXIBIR_TABELA(comparativo);


        //  Lista<Entidade> dados_bora_ver = ENTT.PARSER( Zipper.DESCOMPACTAR(DS.buscar_item(arquivo_sensores_superarko_compactado,"1.entts").get().getBytes()) );
        // ENTT.EXIBIR_TABELA(dados_bora_ver);

    }

    public static void EXPORTAR_MODELOS() {

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);

        Lista<Entidade> dados = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_v9.entts"));

        for (Entidade e : dados) {
            e.at("Sensor", e.at("X") + "::" + e.at("Y"));
        }

        ENTT.EXIBIR_TABELA(dados);

        ENTT.GUARDAR(dados, AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_modelos.entts"));

    }

    public static void EXPORTAR_INFOGRAFICOS() {

        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko"));
        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("comparativos"));

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);

        FS.COPIAR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_info_modelo_climatico.png"), AtzumCreator.LOCAL_GET_ARQUIVO("comparativos/tronarko_info_" + tronarko_corrente + "_modelo_climatico.png"));
        FS.COPIAR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_info_modelo_vegetacao.png"), AtzumCreator.LOCAL_GET_ARQUIVO("comparativos/tronarko_info_" + tronarko_corrente + "_modelo_vegetacao.png"));

    }


    public static void EXPORTAR_DADOS_CIDADES() {

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);

        String arquivo_sensores_por_sensor = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.ds");
        String arquivo_cidades_dados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades.entts");

        DS.limpar(arquivo_cidades_dados);

        Lista<Entidade> cidades = Atzum.GET_CIDADES_NOMES();
        ENTT.EXIBIR_TABELA(cidades);

        long tamanho_total = 0;

        for (DSItem sensor_item : DS.ler_todos(arquivo_sensores_por_sensor)) {

            fmt.print("Sensor : {}", sensor_item.getNome());

            Entidade sensor = ENTT.PARSER_ENTIDADE(sensor_item.getTexto());
            sensor.at("Sensor", sensor.at("X") + "::" + sensor.at("Y"));

            boolean existe_cidade_nesse_sensor = false;
            String cidade_id = "";
            String cidade_nome = "";

            for (Entidade cidade : cidades) {
                if (Strings.isIgual(cidade.at("Cidade"), sensor.at("Sensor"))) {
                    existe_cidade_nesse_sensor = true;

                    cidade_id = cidade.at("CidadeID");
                    cidade_nome = cidade.at("Nome");

                    break;
                }
            }

            if (existe_cidade_nesse_sensor) {
                sensor.at("CidadeID", cidade_id);
                sensor.at("CidadeNome", cidade_nome);

                ENTT.EXIBIR_TABELA(ENTT.CRIAR_LISTA_COM(sensor));

                DS.adicionar(arquivo_cidades_dados, sensor.at("CidadeNome"), ENTT.TO_DOCUMENTO(sensor));

            }


        }


    }


    public static void CONSOLIDAR_DADOS_CIDADES() {

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);


        String arquivo_cidades_dados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades.entts");
        String arquivo_cidades_modelos = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_modelos.entts");
        String arquivo_cidades_distancia_oceanica = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades_distancia_oceanica.entts");


        Lista<Entidade> cidades = Atzum.GET_CIDADES_NOMES();
        Lista<Entidade> dados = ENTT.ABRIR(arquivo_cidades_dados);
        Lista<Entidade> modelos = ENTT.ABRIR(arquivo_cidades_modelos);
        Lista<Entidade> distancia_oceanica = ENTT.ABRIR(arquivo_cidades_distancia_oceanica);

        fmt.print("Cidades   : {}", ENTT.CONTAGEM(cidades));
        fmt.print("Cidades   : {}", ENTT.CONTAGEM(dados));
        fmt.print("Modelos   : {}", ENTT.CONTAGEM(modelos));
        fmt.print("Distancia : {}", ENTT.CONTAGEM(distancia_oceanica));

        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(dados));
        // ENTT.EXIBIR_TABELA(ENTT.DISPERSAO(dados,"CidadeNome"));
        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(modelos));


        Lista<Entidade> dados_consolidados = ENTT.CRIAR_LISTA();

        for (Entidade cidade : cidades) {

            Entidade e_cidade = ENTT.CRIAR_EM(dados_consolidados);
            e_cidade.at("CidadeID", cidade.at("CidadeID"));
            e_cidade.at("CidadeNome", cidade.at("Nome"));
            e_cidade.at("SensorID", "");
            e_cidade.at("CidadePos", cidade.at("X") + "::" + cidade.at("Y"));
            e_cidade.at("X", cidade.at("X"));
            e_cidade.at("Y", cidade.at("Y"));


            Entidade e_sensor = ENTT.GET_SEMPRE(dados, "Sensor", e_cidade.at("CidadePos"));
            e_cidade.at("SensorID", e_sensor.at("SensorID"));

            Entidade e_modelo = ENTT.GET_SEMPRE(modelos, "Sensor", e_cidade.at("CidadePos"));
            e_cidade.at("Hiperestacao", e_modelo.at("Hiperestacao"));
            e_cidade.at("Vegetacao", e_modelo.at("Vegetacao"));
            e_cidade.at("Nevasca", e_modelo.at("Nevasca"));
            e_cidade.at("Chuva", e_modelo.at("Chuva"));
            e_cidade.at("Secura", e_modelo.at("Secura"));

            Entidade e_distancia_oceanica = ENTT.GET_SEMPRE(distancia_oceanica, "CidadePos", e_cidade.at("CidadePos"));
            e_cidade.at("Oceano_Nome", e_distancia_oceanica.at("Oceano_Nome"));
            e_cidade.at("Oceano_Distancia", e_distancia_oceanica.at("Oceano_Distancia"));


            double tMenor = e_sensor.atDouble("T1");
            double tMaior = e_sensor.atDouble("T1");

            double uMenor = e_sensor.atDouble("U1");
            double uMaior = e_sensor.atDouble("U1");

            e_cidade.at("tMin", tMenor);
            e_cidade.at("tMax", tMaior);

            e_cidade.at("uMin", uMenor);
            e_cidade.at("uMaxuM", uMaior);

            for (int superarko = 1; superarko <= 500; superarko++) {

                e_cidade.at("T" + superarko, e_sensor.at("T" + superarko));
                e_cidade.at("U" + superarko, e_sensor.at("U" + superarko));
                e_cidade.at("M" + superarko, e_sensor.at("M" + superarko));
                e_cidade.at("FC" + superarko, e_sensor.at("FC" + superarko));
                e_cidade.at("IC" + superarko, e_sensor.at("IC" + superarko));

                if (e_sensor.atDouble("T" + superarko) < tMenor) {
                    tMenor = e_sensor.atDouble("T" + superarko);
                }
                if (e_sensor.atDouble("T" + superarko) > tMaior) {
                    tMaior = e_sensor.atDouble("T" + superarko);
                }

                if (e_sensor.atDouble("U" + superarko) < uMenor) {
                    uMenor = e_sensor.atDouble("U" + superarko);
                }
                if (e_sensor.atDouble("U" + superarko) > uMaior) {
                    uMaior = e_sensor.atDouble("U" + superarko);
                }

            }

            e_cidade.at("tMin", tMenor);
            e_cidade.at("tMax", tMaior);

            e_cidade.at("uMin", uMenor);
            e_cidade.at("uMax", uMaior);

        }

        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(dados_consolidados));

        ENTT.GUARDAR(dados_consolidados, AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades_geral.entts"));

    }


    public static void VER_DADOS_VEGETACAO() {

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);

        String arquivo_cidades_modelos = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_modelos.entts");
        Lista<Entidade> modelos = ENTT.ABRIR(arquivo_cidades_modelos);

        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(modelos));

        Lista<Entidade> modelos_vegetacao = ENTT.CRIAR_LISTA();

        for (Entidade vegetacao : ENTT.AGRUPAR(modelos, "Vegetacao")) {

            Entidade e_vegetacao = ENTT.CRIAR_EM(modelos_vegetacao, "Vegetacao", vegetacao.at("Vegetacao"));

            e_vegetacao.at("Sensores", ENTT.CONTAGEM(vegetacao.getEntidades()));

            e_vegetacao.at("Nevasca.Menor", ENTT.GET_INTEIRO_MENOR(vegetacao.getEntidades(), "Nevasca"));
            e_vegetacao.at("Nevasca.Maior", ENTT.GET_INTEIRO_MAIOR(vegetacao.getEntidades(), "Nevasca"));

            e_vegetacao.at("Chuva.Menor", ENTT.GET_INTEIRO_MENOR(vegetacao.getEntidades(), "Chuva"));
            e_vegetacao.at("Chuva.Maior", ENTT.GET_INTEIRO_MAIOR(vegetacao.getEntidades(), "Chuva"));

            e_vegetacao.at("Secura.Menor", ENTT.GET_INTEIRO_MENOR(vegetacao.getEntidades(), "Secura"));
            e_vegetacao.at("Secura.Maior", ENTT.GET_INTEIRO_MAIOR(vegetacao.getEntidades(), "Secura"));

        }

        ENTT.EXIBIR_TABELA(modelos_vegetacao);

    }

    public static void VER_DADOS_CIDADES() {

        String tronarko_corrente = AtzumProcessoCriativoEmTarefas.GET_BETA_TRONARKO();

        fmt.print(">> Tronarko Corrente : {}", tronarko_corrente);

        String arquivo_cidades_consolidados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tempo/tronarko_cidades_geral.entts");
        Lista<Entidade> cidades = ENTT.ABRIR(arquivo_cidades_consolidados);

        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(cidades));

    }
}
