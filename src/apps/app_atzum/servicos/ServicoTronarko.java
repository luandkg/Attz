package apps.app_atzum.servicos;

import apps.app_arquivos.AppVideo;
import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_atzum.Atzum;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumProcessoCriativoEmTarefas;
import apps.app_atzum.AtzumTerra;
import apps.app_atzum.analisadores.AnalisadorTemperatura;
import apps.app_atzum.apps.AtzumSnapShots;
import apps.app_atzum.renderizadores.TronarkoRenderizadores;
import apps.app_atzum.utils.AtzumCriativoLog;
import apps.app_atzum.utils.MassaDeAr;
import apps.app_atzum.utils.Rasterizador;
import apps.app_atzum.utils.SnapShotter;
import libs.arquivos.QTT;
import libs.arquivos.QTTDouble;
import libs.arquivos.Quadrum;
import libs.arquivos.binario.Inteiro;
import libs.arquivos.ds.DS;
import libs.arquivos.ds.DSItem;
import libs.arquivos.indexados.CacheIndexado;
import libs.arquivos.indexados.CentumIndexado;
import libs.arquivos.video.Empilhador;
import libs.arquivos.video.VideoCodecador;
import libs.azzal.AzzalUnico;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.azzal.utilitarios.HSV;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.entt.Tag;
import libs.imagem.Efeitos;
import libs.imagem.Imagem;
import libs.luan.*;


public class ServicoTronarko {

    public static void CONSTRUIR_TRONARKO() {
        AtzumCriativoLog.iniciar("ServicoTronarko.CONSTRUIR_TRONARKO");

        CONSTRUIR_TRONARKO_MODELO_V2(1);
        CONSTRUIR_TRONARKO_MODELO_V2(2);

        AtzumCriativoLog.terminar("ServicoTronarko.CONSTRUIR_TRONARKO");
        AtzumCriativoLog.exibir_item("ServicoTronarko.CONSTRUIR_TRONARKO");

    }

    public static void CONSTRUIR_TRONARKO_MODELO_V2(int modelo_valor) {


        String modelagem = String.valueOf(modelo_valor);

        fmt.print("Lendo temperaturas :: Modelo - T" + modelo_valor);

        AtzumTerra mapa_planeta = new AtzumTerra();

        Cores mCores = new Cores();

        Extremos<Integer> ex_temperatura = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());
        Extremos<Integer> ex_temperatura_validados = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());

        QTT temperatura_dados = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"));

        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor = temperatura_dados.getValor(x, y);
                    ex_temperatura.set(valor);

                }
            }
        }


        fmt.print("\t++ Maior Temperatura : {}", ex_temperatura.getMaior());
        fmt.print("\t++ Menor Temperatura : {}", ex_temperatura.getMenor());


        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko"));

        String arquivo_processando_temperatura = "build/tronarko/temperatura_t" + modelagem + ".qtt";
        QTT.alocar(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura), mapa_planeta.getLargura(), mapa_planeta.getAltura());
        QTT.alterar_todos(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura), mapa_planeta.getLargura(), mapa_planeta.getAltura(), -1);


        fmt.print(">> Processando");
        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int temperatura_corrente = temperatura_dados.getValor(x, y);

                    if (Aleatorio.aleatorio(100) > 50) {
                        temperatura_corrente += 1;
                    } else {
                        temperatura_corrente -= 1;
                    }

                    QTT.alterar(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura), x, y, temperatura_corrente);

                    ex_temperatura_validados.set(temperatura_corrente);
                }
            }
        }


        fmt.print("\t++ Maior Temperatura : {}", ex_temperatura_validados.getMaior());
        fmt.print("\t++ Menor Temperatura : {}", ex_temperatura_validados.getMenor());


        fmt.print(">> Renderizando");

        temperatura_dados = QTT.getTudo(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura));

        Renderizador render_distancia = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());

        double temp_escopo = (double) (ex_temperatura_validados.getMaior() - ex_temperatura_validados.getMenor());

        double temp_taxa = 100.0 / temp_escopo;

        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int temperatura = temperatura_dados.getValor(x, y);

                    render_distancia.setPixel(x, y, new HSV(350, HSV.MAXIMO, HSV.INVERSO((int) (temperatura * temp_taxa))));

                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/temperatura_t" + modelagem + ".png"));

        fmt.print(">> Tudo OK !");

    }

    public static void CONSTRUIR_TRONARKO_MODELO(int modelo_valor) {


        String modelagem = String.valueOf(modelo_valor);

        fmt.print("Lendo temperaturas...");
        AtzumTerra mapa_planeta = new AtzumTerra();

        Cores mCores = new Cores();

        Extremos<Integer> ex_temperatura = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());
        Extremos<Integer> ex_temperatura_validados = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());


        Unico<Integer> tipos = new Unico<Integer>(Inteiro.IGUALAVEL());

        Lista<Ponto> pontos_terra = new Lista<Ponto>();

        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"), x, y);
                    ex_temperatura.set(valor);
                    tipos.item(valor);

                    pontos_terra.adicionar(new Ponto(x, y));
                }
            }
        }


        fmt.print("Maior Temperatura : {}", ex_temperatura.getMaior());
        fmt.print("Menor Temperatura : {}", ex_temperatura.getMenor());

        fmt.print("------------- TEMPERATURA MATRIZ ------------------");

        Vetor<Extremos<Integer>> zonas_de_temperatura = new Atzum().GET_ZONAS_DE_TEMPERATURAS();


        for (Extremos<Integer> tipo : zonas_de_temperatura) {
            fmt.print("\t + {esq5}      -- {esq5}", tipo.getMenor(), tipo.getMaior());
        }

        fmt.print("------------- TEMPERATURA TIPOS ------------------");
        for (int tipo : tipos) {
            fmt.print("\t + {}", tipo);
        }

        // Embaralhar.emabaralhe(pontos_terra);

        int criar_pontos = (mapa_planeta.getLargura() / 100) * (mapa_planeta.getAltura() / 100);

        Lista<Par<Ponto, Integer>> pontos_escolhidos = new Lista<Par<Ponto, Integer>>();

        int processando = 0;

        while (processando < criar_pontos) {

            int escolhido = Aleatorio.aleatorio(pontos_terra.getQuantidade());
            Ponto ponto_escolhido = pontos_terra.get(escolhido);

            int tipo = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"), ponto_escolhido.getX(), ponto_escolhido.getY());

            int temperatura_corrente = Aleatorio.aleatorio_entre(zonas_de_temperatura.get(tipo).getMenor(), zonas_de_temperatura.get(tipo).getMaior());
            ex_temperatura_validados.set(temperatura_corrente);

            pontos_escolhidos.adicionar(new Par<Ponto, Integer>(ponto_escolhido, temperatura_corrente));

            processando += 1;
        }

        fmt.print("Terra pontos    : {}", pontos_terra.getQuantidade());
        fmt.print("Criar pontos    : {}", criar_pontos);
        fmt.print("Pontos criados  : {}", pontos_escolhidos.getQuantidade());

        String arquivo_processando_temperatura = "build/tronarko/temperatura_t" + modelagem + ".qtt";

        QTT.alocar(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura), mapa_planeta.getLargura(), mapa_planeta.getAltura());
        QTT.alterar_todos(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura), mapa_planeta.getLargura(), mapa_planeta.getAltura(), -1);


        fmt.print(">> Processando");
        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor_proximo = Espaco2D.GET_VALOR_DA_DISTANCIA_MAIS_PROXIMA(pontos_escolhidos, x, y);

                    QTT.alterar(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura), x, y, valor_proximo);

                }
            }
        }


        fmt.print(">> Renderizando");

        Renderizador render_distancia = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getBranco());

        double temp_escopo = (double) (ex_temperatura_validados.getMaior() - ex_temperatura_validados.getMenor());

        double temp_taxa = 100.0 / temp_escopo;

        int SUBIR = 60;
        int DESCER = 80;

        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor = QTT.pegar(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura), x, y);
                    int temperatura = valor;

                    valor = valor + 100;

                    HSV hsv = new HSV(20, HSV.MEDIANO, HSV.INVERSO((int) (temperatura * temp_taxa)));

                    // render_distancia.setPixel(x, y, new Cor(valor, valor, valor));

                    if (temperatura <= 15) {
                        render_distancia.setPixel(x, y, new HSV(240, HSV.MEDIANO, HSV.INVERSO((int) ((temperatura) * temp_taxa))));
                    } else if (temperatura > 25) {
                        render_distancia.setPixel(x, y, new HSV(10, HSV.MEDIANO, HSV.INVERSO((int) ((temperatura) * temp_taxa))));
                    } else {
                        render_distancia.setPixel(x, y, new HSV(150, HSV.MEDIANO, HSV.INVERSO((int) (temperatura * temp_taxa))));
                    }


                    render_distancia.setPixel(x, y, new HSV(350, HSV.MAXIMO, HSV.INVERSO((int) (temperatura * temp_taxa))));

                    //render_distancia.setPixel(x, y, hsv);


                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/temperatura_t" + modelagem + ".png"));

        fmt.print("Tudo OK !");

    }


    public static void TRONARKO_PROCESSAR_SUPERARKOS(boolean usar_transicao,int tronarko_corrente) {

        AtzumCriativoLog.iniciar("ServicoTronarko.TRONARKO_PROCESSAR_SUPERARKOS");

        boolean ANALISAR_VARIACAO = true;


        String arquivo_processando_temperatura_v1 = "build/tronarko/temperatura_t1.qtt";
        String arquivo_processando_temperatura_v2 = "build/tronarko/temperatura_t2.qtt";

        Cores mCores = new Cores();

        AtzumTerra mapa_planeta = new AtzumTerra();

        Extremos<Integer> ex_temperatura_validados = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());

        AreaDouble tronarko_temperatura = new AreaDouble(mapa_planeta.getLargura(), mapa_planeta.getAltura());
        AreaDouble tronarko_temperatura_transicao = new AreaDouble(mapa_planeta.getLargura(), mapa_planeta.getAltura());
        AreaDouble tronarko_umidade_variacao = new AreaDouble(mapa_planeta.getLargura(), mapa_planeta.getAltura());


        boolean RENDERIZAR_AREAS_CLIMATICAS = false;


        AnalisadorTemperatura mAnalisadorTemperatura = null;

        if (RENDERIZAR_AREAS_CLIMATICAS) {
            mAnalisadorTemperatura = new AnalisadorTemperatura();
        }


        Atzum atzum = new Atzum();
        AtzumTerra mPlaneta = new AtzumTerra();

        for (int y = 0; y < mPlaneta.getAltura(); y++) {
            for (int x = 0; x < mPlaneta.getLargura(); x++) {
                if (mPlaneta.isTerra(x, y)) {
                    tronarko_umidade_variacao.set(x, y, 0);
                }
            }
        }

        fmt.print(">> Carregando massas de ar...");

        Cor AR_FRIO = atzum.getMassaDeArFria();
        Cor AR_QUENTE = atzum.getMassaDeArQuente();

        Lista<MassaDeAr> mMassasDeAr = atzum.getMassasDeAr();


        fmt.print(">> Massas de Ar : Inicializando...");
        for (MassaDeAr massa : mMassasDeAr) {
            ServicoMassasDeAr.MASSA_DE_AR_INICIAR(massa.getMassa());
        }

        fmt.print(">> Processando Temperatura...");

        QTT temp_valor_t1 = QTT.getTudo(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura_v1));
        QTT temp_valor_t2 = QTT.getTudo(AtzumCreator.LOCAL_GET_ARQUIVO(arquivo_processando_temperatura_v2));


        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor1 = temp_valor_t1.getValor(x, y);
                    int valor2 = temp_valor_t2.getValor(x, y);

                    tronarko_temperatura_transicao.set(x, y, ((double) valor2 - (double) valor1) / 250.0);

                    tronarko_temperatura.set(x, y, valor1);
                    ex_temperatura_validados.set(valor1, valor2);
                }
            }
        }


        double temp_escopo = (double) (ex_temperatura_validados.getMaior() - ex_temperatura_validados.getMenor());

        double temp_taxa = 100.0 / temp_escopo;


        fmt.print(">> Organizando arquivos de saida !");


        Lista<Entidade> sensores = Atzum.GET_SENSORES();


        boolean RENDERIZAR_VIDEOS = false;

        Opcional<Empilhador> video_temperatura = new Opcional<Empilhador>();
        Opcional<Empilhador> video_temperatura_umidade = new Opcional<Empilhador>();
        Opcional<Empilhador> video_temperatura_e_massas_de_ar = new Opcional<Empilhador>();
        Opcional<Empilhador> video_preciptacao_valor = new Opcional<Empilhador>();
        Opcional<Empilhador> video_preciptacao_tronarko = new Opcional<Empilhador>();

        Opcional<Empilhador> video_fatores_climaticos = new Opcional<Empilhador>();

        Opcional<Empilhador> video_temperatura_zonas = new Opcional<Empilhador>();


        if (RENDERIZAR_VIDEOS) {

            int video_largura = mapa_planeta.getLargura() / 2;
            int video_altura = mapa_planeta.getAltura() / 2;

            video_temperatura = new Opcional<Empilhador>(new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/temperatura.vi"), video_largura, video_altura));
            video_temperatura_umidade = new Opcional<Empilhador>(new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/tu.vi"), video_largura, video_altura));
            video_temperatura_e_massas_de_ar = new Opcional<Empilhador>(new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/tronarko_temperatura_e_massas_de_ar.vi"), video_largura, video_altura));
            video_preciptacao_valor = new Opcional<Empilhador>(new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/preciptacao_valor.vi"), video_largura, video_altura));
            video_preciptacao_tronarko = new Opcional<Empilhador>(new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/preciptacao_tronarko.vi"), video_largura, video_altura));
            video_fatores_climaticos = new Opcional<Empilhador>(new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/fatores_climaticos.vi"), video_largura, video_altura));
            video_temperatura_zonas = new Opcional<Empilhador>(new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/temperatura_zonas.vi"), video_largura, video_altura));

        }


        Extremos<Double> d_indo = new Extremos<Double>(Matematica.DOUBLE_COMPARADOR());
        Extremos<Double> d_voltando = new Extremos<Double>(Matematica.DOUBLE_COMPARADOR());


        QTT umidade_dados = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("umidade.qtt"));

        Renderizador render_tronarko_preciptacao = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());

        Renderizador render_mapa_pronto = new Renderizador(AtzumCreator.GET_MAPA_PRETO_E_BRANCO());

        Opcional<QTT> variacao_inferior = Opcional.CANCEL();
        Opcional<QTT> variacao_superior = Opcional.CANCEL();

        FS.organizar_pasta(AtzumCreator.LOCAL_DADOS);

        if (usar_transicao) {
            fmt.print("Usar Transicao :: {} ",usar_transicao);

            variacao_inferior = Opcional.OK(QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("variacao_inferior.qtt")));
            variacao_superior = Opcional.OK(QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("variacao_superior.qtt")));
        }

        Renderizador fenomeno_atsmofestico_furacao = ServicoFenomenoAtmosferico.MAPA_TEMPESTADE_INICIAR();
        Renderizador fenomeno_atsmofestico_tornado = ServicoFenomenoAtmosferico.MAPA_TEMPESTADE_INICIAR();

        String arquivo_sensores_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");

        DS.limpar(arquivo_sensores_por_superarko);



        Cor cor_furacao = Atzum.FENOMENO_COR_FURACAO;
        Cor cor_tornado = Atzum.FENOMENO_COR_TORNADO;


        if(tronarko_corrente== AtzumProcessoCriativoEmTarefas.TRONARKO_INICIAR){
            ServicoFenomenoAtmosferico.ZERAR();
        }

        Lista<Entidade> tronarko_atividade_atmosfericos = new Lista<Entidade>();

        Lista<Entidade> dados_furacoes = ENTT.ABRIR(ServicoFenomenoAtmosferico.ARQUIVO_FENOMENOS_FURACOES());
        Lista<Entidade> dados_tornados = ENTT.ABRIR(ServicoFenomenoAtmosferico.ARQUIVO_FENOMENOS_TORNADOS());



        fmt.print(">> Processando Tronarko...");
        for (int superarko = 1; superarko <= 500; superarko++) {


            PROCESSAR_TRANSICAO_DE_TEMPERATURA(mapa_planeta, superarko, tronarko_temperatura, d_indo, d_voltando, tronarko_temperatura_transicao);


            // RENDERIZAR PARTES

            fmt.print("\t ++ Superarko :: {}", superarko);

            Renderizador render_massas_de_ar = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());
            Renderizador render_massas_de_ar_pura = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());
            Renderizador render_preciptacao_valor = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());

            Renderizador render_tp = TronarkoRenderizadores.renderizar_variacao_de_temperatura(mapa_planeta, tronarko_temperatura, temp_taxa);

            for (MassaDeAr massa : mMassasDeAr) {

                if (massa.isFrio()) {
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_FIXO(render_massas_de_ar_pura, AR_FRIO, massa);
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_JUNTAS_FIXO(render_massas_de_ar, AR_FRIO, massa);
                } else if (massa.isQuente()) {
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_FIXO(render_massas_de_ar_pura, AR_QUENTE, massa);
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_JUNTAS_FIXO(render_massas_de_ar, AR_QUENTE, massa);
                }


                massa.proximo();
            }


            Renderizador movimentacao_temperatura_umidade = TronarkoRenderizadores.renderizar_temperatura_umidade(mapa_planeta, render_mapa_pronto, tronarko_temperatura, umidade_dados);

            ServicoMassasDeAr.ACUMULAR_MASSA_DE_AR(render_massas_de_ar, render_tp);
            ServicoMassasDeAr.TRANSPOR_MASSA_DE_AR(mapa_planeta, render_massas_de_ar, movimentacao_temperatura_umidade, render_preciptacao_valor, render_tronarko_preciptacao);
            Renderizador render_fatores_climaticos = ServicoMassasDeAr.PROCESSAR_FATORES_CLIMATICOS(mapa_planeta, tronarko_temperatura, umidade_dados, render_massas_de_ar);

            ServicoMassasDeAr.CALCULAR_UMIDADE(ANALISAR_VARIACAO, mapa_planeta, tronarko_umidade_variacao, render_fatores_climaticos, variacao_inferior, variacao_superior);


            Renderizador fenomeno_atsmofestico_furacao_do_superarko = ServicoFenomenoAtmosferico.MAPA_TEMPESTADE_INICIAR();
            Renderizador fenomeno_atsmofestico_tornado_do_superarko = ServicoFenomenoAtmosferico.MAPA_TEMPESTADE_INICIAR();

            ServicoFenomenoAtmosferico.PROCESSAR_FURACAO(mapa_planeta,atzum,fenomeno_atsmofestico_furacao_do_superarko,render_massas_de_ar);
            ServicoFenomenoAtmosferico.PROCESSAR_TORNADO(mapa_planeta,atzum,fenomeno_atsmofestico_tornado_do_superarko,render_massas_de_ar);

            ServicoFenomenoAtmosferico.PROCESSAR_FURACAO(mapa_planeta,atzum,fenomeno_atsmofestico_furacao,render_massas_de_ar);
            ServicoFenomenoAtmosferico.PROCESSAR_TORNADO(mapa_planeta,atzum,fenomeno_atsmofestico_tornado,render_massas_de_ar);

            tronarko_atividade_atmosfericos.adicionar_varios(ServicoFenomenoAtmosferico.processar_furacao(dados_furacoes, cor_furacao, fenomeno_atsmofestico_furacao_do_superarko.toImagemSemAlfa(), tronarko_corrente, superarko));
            tronarko_atividade_atmosfericos.adicionar_varios(ServicoFenomenoAtmosferico.processar_tornado(dados_tornados, cor_tornado, fenomeno_atsmofestico_tornado_do_superarko.toImagemSemAlfa(), tronarko_corrente, superarko));


            if (RENDERIZAR_VIDEOS) {

                video_temperatura.get().empurrarQuadro(Efeitos.reduzirMetade(TronarkoRenderizadores.renderizar_variacao_de_temperatura(mapa_planeta, tronarko_temperatura, temp_taxa).toImagemSemAlfa()));
                video_temperatura_zonas.get().empurrarQuadro(Efeitos.reduzirMetade(TronarkoRenderizadores.renderizar_variacao_de_temperatura_zona(mapa_planeta, tronarko_temperatura, temp_taxa).toImagemSemAlfa()));

                video_temperatura_umidade.get().empurrarQuadro(Efeitos.reduzirMetade(movimentacao_temperatura_umidade.toImagemSemAlfa()));

                video_temperatura_e_massas_de_ar.get().empurrarQuadro(Efeitos.reduzirMetade(render_tp.toImagemSemAlfa()));
                video_preciptacao_valor.get().empurrarQuadro(Efeitos.reduzirMetade(render_preciptacao_valor.toImagemSemAlfa()));

                video_preciptacao_tronarko.get().empurrarQuadro(Efeitos.reduzirMetade(render_tronarko_preciptacao.toImagemSemAlfa()));
                video_fatores_climaticos.get().empurrarQuadro(Efeitos.reduzirMetade(render_fatores_climaticos.toImagemSemAlfa()));

            }

            Lista<Entidade> sensores_dados = ORGANIZAR_DADOS_SENSORES(atzum, superarko, tronarko_temperatura, umidade_dados, tronarko_umidade_variacao, render_fatores_climaticos, render_massas_de_ar, sensores);


            DS.adicionar(arquivo_sensores_por_superarko, superarko + ".entts", ENTT.TO_DOCUMENTO(sensores_dados));


            if (RENDERIZAR_AREAS_CLIMATICAS) {
                mAnalisadorTemperatura.processarSuperarko(render_fatores_climaticos);
            }

            Imagem.exportar(fenomeno_atsmofestico_furacao.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/tronarko_zona_de_furacoes.png"));
            Imagem.exportar(fenomeno_atsmofestico_tornado.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/tronarko_zona_de_tornados.png"));

        }


        if (RENDERIZAR_VIDEOS) {
            video_temperatura.get().fechar();
            video_temperatura_zonas.get().fechar();
            video_temperatura_umidade.get().fechar();
            video_temperatura_e_massas_de_ar.get().fechar();
            video_preciptacao_valor.get().fechar();
            video_preciptacao_tronarko.get().fechar();
            video_fatores_climaticos.get().fechar();
        }


        Imagem.exportar(render_tronarko_preciptacao.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_preciptacao_temporal.png"));


        Imagem.exportar(fenomeno_atsmofestico_furacao.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/tronarko_zona_de_furacoes.png"));
        Imagem.exportar(fenomeno_atsmofestico_tornado.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/tronarko_zona_de_tornados.png"));

        ENTT.GUARDAR(dados_furacoes, ServicoFenomenoAtmosferico.ARQUIVO_FENOMENOS_FURACOES());
        ENTT.GUARDAR(dados_tornados, ServicoFenomenoAtmosferico.ARQUIVO_FENOMENOS_TORNADOS());

        ENTT.GUARDAR(tronarko_atividade_atmosfericos, ServicoFenomenoAtmosferico.ARQUIVO_FENOMENOS_ATMOSFERICOS());

        ServicoFenomenoAtmosferico.RENDER(tronarko_corrente);


        fmt.print("\t + {esq5}      -- {esq5}", fmt.f2(d_indo.getMenor()), fmt.f2(d_indo.getMaior()));
        fmt.print("\t + {esq5}      -- {esq5}", fmt.f2(d_voltando.getMenor()), fmt.f2(d_voltando.getMaior()));

        if (RENDERIZAR_AREAS_CLIMATICAS) {
            mAnalisadorTemperatura.renderizar();
        }


        fmt.print(">> GUARDAR DADOS DOS SENSORES");


        QTTDouble.guardar(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_temperatura_transicao.qttd"), tronarko_temperatura_transicao.getLargura(), tronarko_temperatura_transicao.getAltura(), tronarko_temperatura_transicao.getTudo());

        SENSORES_DADOS_ORGANIZAR();

        AtzumCriativoLog.terminar("ServicoTronarko.TRONARKO_PROCESSAR_SUPERARKOS");

        fmt.print(">> Tudo OK !!!");

    }


    public static void SENSORES_DADOS_ORGANIZAR() {

        String arquivo_sensores_captando = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_captando.ds");

        Lista<Entidade> sensores_dados = ENTT.CRIAR_LISTA();

        fmt.print(">> Organizando sensores...");

        for (DSItem sensor : DS.ler_todos(arquivo_sensores_captando)) {

            fmt.print("\t + Organizando Superarko : {}", Strings.GET_ATE(sensor.getNome(), "."));

            for (Entidade sensor_corrente : ENTT.PARSER(sensor.getTexto())) {

                Entidade sensor_organizado = ENTT.GET_SEMPRE(sensores_dados, "Sensor", sensor_corrente.at("Sensor"));

                for (Tag tag : sensor_corrente.tags()) {
                    sensor_organizado.at(tag.getNome(), tag.getValor());
                }

            }


        }

        fmt.print(">> Guardando dados dos sensores organizados !");

        ENTT.GUARDAR(sensores_dados, AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores.entts"));

        fmt.print(">> Sensores organizados !");

    }


    public static void EXIBIR_TRONARKO() {

        Lista<Entidade> dados_brutos = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko.entts"));

        // ENTT.EXIBIR_TABELA(dados_temp);
        // ENTT.EXIBIR_TABELA(ENTT.GET_FATIA_INICIO_PEQUENO(dados_brutos));

        Lista<Entidade> dados_resumidos = new Lista<Entidade>();

        Lista<Entidade> fatores_climaticos = new Lista<Entidade>();
        Lista<Entidade> estacoes_termicas = new Lista<Entidade>();


        int TEMPERATURA_QUENTE = 30;
        int TEMPERATURA_FRIA = 15;


        Unico<String> tipos_climaticos = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade cidade : dados_brutos) {

            Entidade e_cidade = ENTT.GET_SEMPRE(dados_resumidos, "Cidade", cidade.at("Cidade"));
            e_cidade.at("X", cidade.at("X"));
            e_cidade.at("Y", cidade.at("Y"));

            e_cidade.at("TMenor", cidade.atDouble("T1"));
            e_cidade.at("TMaior", cidade.atDouble("T1"));

            for (int superarko = 1; superarko <= 500; superarko++) {
                e_cidade.atSeMenor("TMenor", cidade.atDouble("T" + superarko));
                e_cidade.atSeMaior("TMaior", cidade.atDouble("T" + superarko));
            }

            e_cidade.at("UMenor", cidade.atDouble("U1"));
            e_cidade.at("UMaior", cidade.atDouble("U1"));

            for (int superarko = 1; superarko <= 500; superarko++) {
                e_cidade.atSeMenor("UMenor", cidade.atDouble("U" + superarko));
                e_cidade.atSeMaior("UMaior", cidade.atDouble("U" + superarko));
            }

            e_cidade.at("EstacoesTermicas", "");
            e_cidade.at("FatoresClimaticos", "");

            String massa_anterior = cidade.at("FC1");
            int massa_duracao = 0;
            String massa_inicio = "1";
            String massa_fim = "1";

            for (int s = 1; s <= 500; s++) {
                String massa_corrente = cidade.at("FC" + s);
                // fmt.print("Atual : {} - {}",cidade.at("Cidade"),massa_atual);

                if (!massa_corrente.isEmpty()) {
                    e_cidade.at(massa_corrente, "SIM");
                    tipos_climaticos.item(massa_corrente);
                }


                if (massa_anterior.contentEquals(massa_corrente)) {
                    massa_duracao += 1;
                    massa_fim = String.valueOf(s);
                } else {
                    Entidade fator_climatico = new Entidade();
                    fator_climatico.at("Cidade", cidade.at("Cidade"));
                    fator_climatico.at("Fator", massa_anterior);
                    fator_climatico.at("Inicio", massa_inicio);
                    fator_climatico.at("Fim", massa_fim);
                    fator_climatico.at("Tempo", massa_duracao);

                    fatores_climaticos.adicionar(fator_climatico);


                    massa_anterior = massa_corrente;
                    massa_duracao = 1;
                    massa_inicio = String.valueOf(s);
                    massa_fim = String.valueOf(s);

                }

            }

            Entidade fator_climatico = new Entidade();
            fator_climatico.at("Cidade", cidade.at("Cidade"));
            fator_climatico.at("Fator", massa_anterior);
            fator_climatico.at("Inicio", massa_inicio);
            fator_climatico.at("Fim", massa_fim);
            fator_climatico.at("Tempo", massa_duracao);

            fatores_climaticos.adicionar(fator_climatico);

            String s_fatores = "{ ";
            for (String fator : tipos_climaticos) {
                if (e_cidade.at(fator).contentEquals("SIM")) {
                    s_fatores += fator + " ";
                }
            }
            s_fatores = s_fatores + "}";

            e_cidade.at("FatoresClimaticos", s_fatores);


            String temperatura_anterior = TEMPERATURA_CLASSIFICAR(cidade.atDouble("T1"));
            int estacao_duracao = 0;
            String estacao_inicio = "1";
            String estacao_fim = "1";

            for (int s = 1; s <= 500; s++) {
                String temperatura_corrente = TEMPERATURA_CLASSIFICAR(cidade.atDouble("T" + s));

                if (temperatura_anterior.contentEquals(temperatura_corrente)) {
                    estacao_duracao += 1;
                    estacao_fim = String.valueOf(s);
                } else {
                    Entidade estacao_termica = new Entidade();
                    estacao_termica.at("Cidade", cidade.at("Cidade"));
                    estacao_termica.at("Estacao", temperatura_anterior);
                    estacao_termica.at("Inicio", estacao_inicio);
                    estacao_termica.at("Fim", estacao_fim);
                    estacao_termica.at("Tempo", estacao_duracao);
                    estacoes_termicas.adicionar(estacao_termica);

                    estacao_duracao = 1;
                    estacao_inicio = String.valueOf(s);
                    estacao_fim = String.valueOf(s);
                }

                if (s == 500) {
                    Entidade estacao_termica = new Entidade();
                    estacao_termica.at("Cidade", cidade.at("Cidade"));
                    estacao_termica.at("Estacao", temperatura_anterior);
                    estacao_termica.at("Inicio", estacao_inicio);
                    estacao_termica.at("Fim", estacao_fim);
                    estacao_termica.at("Tempo", estacao_duracao);
                    estacoes_termicas.adicionar(estacao_termica);
                }

                temperatura_anterior = temperatura_corrente;
            }

            String s_estacoes = "{ ";
            for (String estacao : ENTT.FILTRAR_UNICOS(ENTT.COLETAR(estacoes_termicas, "Cidade", cidade.at("Cidade")), "Estacao")) {
                s_estacoes += estacao + " ";
            }
            s_estacoes = s_estacoes + "}";
            e_cidade.at("EstacoesTermicas", s_estacoes);

        }

        ENTT.EXIBIR_TABELA(dados_brutos);

        //ENTT.EXIBIR_TABELA(dados_resumidos);

        /// ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(dados_brutos));
        //ENTT.EXIBIR_TABELA(fatores_climaticos);

        //  ENTT.EXIBIR_TABELA(ENTT.GET_FATIA_INICIO_PEQUENO(fatores_climaticos));
        // ENTT.EXIBIR_TABELA(estacoes_termicas);

        ENTT.GUARDAR(dados_brutos, AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_7000/tronarko_7000.entts"));
        ENTT.GUARDAR(fatores_climaticos, AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_7000/tronarko_fatores_climaticos.entts"));
        ENTT.GUARDAR(dados_resumidos, AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_7000/tronarko_resumo.entts"));
        ENTT.GUARDAR(estacoes_termicas, AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_7000/tronarko_estacoes.entts"));

    }


    public static void EXIBIR_TRONARKO_DADOS() {


        String arquivo_sensores_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");

        for (DSItem item_superarko : DS.ler_alguns(arquivo_sensores_por_superarko, 0, 5)) {

            Lista<Entidade> dados = ENTT.PARSER(item_superarko.getTexto());

            //ENTT.ORDENAR_INTEIRO(dados,"SensorID");

            // ENTT.EXIBIR_TABELA(ENTT.CRIAR_LISTA_COM(ENTT.GET_SEMPRE(dados,"SensorID","5250")));
            // ENTT.EXIBIR_TABELA(ENTT.CRIAR_LISTA_COM(ENTT.GET_ULTIMO(dados)));

            // Comum             |1148      |1343      |          |          |5250
            // Cidade            |1148      |1343      |1141      |1336      |5662

            int sid = 0;

            for (Entidade e : dados) {

                if (e.atInt("SensorID") == sid) {
                } else {
                    e.at("Status", "PROBLEMA");
                }
                e.at("Esperado", sid);

                sid += 1;
            }


            ENTT.EXIBIR_TABELA(dados);
            //  ENTT.EXIBIR_TABELA(ENTT.COLETAR(dados,"Status","PROBLEMA"));

            fmt.print(">> {}", ENTT.CONTAGEM(dados));
            fmt.print(">> {}", ENTT.CONTAGEM_UNICOS(dados, "SensorID"));

            //   break;
        }


        ENTT.EXIBIR_TABELA(Atzum.GET_SENSORES());

    }


    public static String TEMPERATURA_CLASSIFICAR(double temperatura) {
        if (temperatura <= 15) {
            return "FRIO";
        } else if (temperatura >= 30) {
            return "QUENTE";
        } else {
            return "AMBIENTE";
        }
    }


    public static void OBSERVAR_SENSORES() {

        //  ServicoTronarko.OBSERVAR_SENSORES >>> + 29 uz

        AtzumCriativoLog.iniciar("ServicoTronarko.OBSERVAR_SENSORES");

        Lista<Entidade> dados_brutos = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores.entts"));


        Cores mCores = new Cores();


        AtzumTerra planeta = new AtzumTerra();

        Vetor<Ponto> sensores = new Vetor<Ponto>(dados_brutos.getQuantidade());

        int sensor_id = 0;
        for (Entidade sensor : dados_brutos) {

            int px = sensor.atInt("X");
            int py = sensor.atInt("Y");

            sensores.set(sensor_id, new Ponto(px, py));
            sensor_id += 1;
        }


        fmt.print(">> Obtendo proximidade dos sensores");

        QTT proximidade = QTT.getTudo(AtzumCreator.LOCAL_GET_ARQUIVO("dados/sensor_proximidade.qtt"));


        fmt.print(">> Iniciando processo...");

        Empilhador video_sensores_observando = new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando.vi"), planeta.getLargura() / 2, planeta.getAltura() / 2);

        Renderizador render_tronarko_sensores_original = new Renderizador(AtzumCreator.GET_MAPA());
        Rasterizador.trocar_cores(render_tronarko_sensores_original, mCores.getAmarelo(), mCores.getBranco());

        for (int superarko = 1; superarko <= 500; superarko++) {

            fmt.print("Superarko : {}", superarko);

            Renderizador render_tronarko_sensores = new Renderizador(render_tronarko_sensores_original.toImagemSemAlfa());


            for (int y = 0; y < planeta.getAltura(); y++) {
                for (int x = 0; x < planeta.getLargura(); x++) {

                    if (planeta.isTerra(x, y)) {

                        int sensor_proximo = proximidade.getValor(x, y);

                        Ponto proximo = sensores.get(sensor_proximo);

                        Entidade sensor = ENTT.GET_SEMPRE(dados_brutos, "Cidade", proximo.getX() + "::" + proximo.getY());

                        double temperatura = sensor.atDoubleOuPadrao("T" + superarko, 0.0);

                        if (temperatura >= 30) {
                            render_tronarko_sensores.setPixel(x, y, mCores.getVermelho());
                        } else if (temperatura <= 15) {
                            render_tronarko_sensores.setPixel(x, y, mCores.getAzul());
                        } else {
                            render_tronarko_sensores.setPixel(x, y, mCores.getVerde());
                        }


                    }
                }
            }


            video_sensores_observando.empurrarQuadro(Efeitos.reduzirMetade(render_tronarko_sensores.toImagemSemAlfa()));

        }


        video_sensores_observando.fechar();

        SnapShotter.CRIAR(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando.vi"), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_observados.png"));


        AtzumCriativoLog.terminar("ServicoTronarko.OBSERVAR_SENSORES");
        AtzumCriativoLog.exibir_item("ServicoTronarko.OBSERVAR_SENSORES");

        //  ENTT.EXIBIR_TABELA(dados_brutos);


    }

    public static void OBSERVAR_SENSORES_v2() {

        //  ServicoTronarko.OBSERVAR_SENSORES >>> + 29 uz

        AtzumCriativoLog.iniciar("ServicoTronarko.OBSERVAR_SENSORES");

        fmt.print(">> Obtendo dados dos sensores...");

        //Lista<Entidade> dados_brutos = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/SENSORES.entts"));
        Lista<Entidade> dados_brutos = ENTT.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores.entts"));


        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(dados_brutos));

        Cores mCores = new Cores();


        AtzumTerra planeta = new AtzumTerra();

        Vetor<Ponto> sensores = new Vetor<Ponto>(dados_brutos.getQuantidade());

        int sensor_id = 0;
        for (Entidade sensor : dados_brutos) {

            int px = sensor.atInt("X");
            int py = sensor.atInt("Y");

            sensores.set(sensor_id, new Ponto(px, py));
            sensor_id += 1;
        }


        fmt.print(">> Iniciando processo...");


        Renderizador render_tronarko_sensores_original = new Renderizador(AtzumCreator.GET_MAPA());
        Rasterizador.trocar_cores(render_tronarko_sensores_original, mCores.getAmarelo(), mCores.getBranco());


        Empilhador video_sensores_observando = new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando.vi"), planeta.getLargura() / 2, planeta.getAltura() / 2);
        Empilhador video_sensores_observando_chuva = new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando_chuva.vi"), planeta.getLargura() / 2, planeta.getAltura() / 2);
        Empilhador video_sensores_observando_seca = new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando_seca.vi"), planeta.getLargura() / 2, planeta.getAltura() / 2);


        int sensor_tamanho = 30;
        int sensor_tamanho_metade = sensor_tamanho / 2;

        for (int superarko = 1; superarko <= 500; superarko++) {

            fmt.print("\t ++ Superarko : {}", superarko);

            Renderizador render_tronarko_sensores = new Renderizador(render_tronarko_sensores_original.toImagemSemAlfa());
            Renderizador render_tronarko_sensores_chuva = new Renderizador(render_tronarko_sensores_original.toImagemSemAlfa());
            Renderizador render_tronarko_sensores_seca = new Renderizador(render_tronarko_sensores_original.toImagemSemAlfa());

            for (Ponto sensor : sensores) {

                Entidade e_sensor = ENTT.GET_SEMPRE(dados_brutos, "Sensor", sensor.getX() + "::" + sensor.getY());
                double temperatura = e_sensor.atDoubleOuPadrao("T" + superarko, 0.0);

                String fator_climatico = e_sensor.at("FC" + superarko);

                boolean isChuva = false;
                boolean isNeve = false;

                boolean isSeca = false;
                boolean isSecaExtrema = false;

                if (fator_climatico.contentEquals("CHUVA")) {
                    isChuva = true;
                } else if (fator_climatico.contentEquals("NEVE")) {
                    isNeve = true;
                } else if (fator_climatico.contentEquals("SECA")) {
                    isSeca = true;
                } else if (fator_climatico.contentEquals("SECA_EXTREMA")) {
                    isSecaExtrema = true;
                }


                if (temperatura >= 30) {
                    AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores, sensor.getX() - sensor_tamanho_metade, sensor.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, mCores.getVermelho());
                } else if (temperatura <= 15) {
                    AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores, sensor.getX() - sensor_tamanho_metade, sensor.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, mCores.getAzul());
                } else {
                    AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores, sensor.getX() - sensor_tamanho_metade, sensor.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, mCores.getVerde());
                }


                if (isChuva) {
                    AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores_chuva, sensor.getX() - sensor_tamanho_metade, sensor.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, mCores.getAzul());
                } else if (isNeve) {
                    AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores_chuva, sensor.getX() - sensor_tamanho_metade, sensor.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, mCores.getCinza());
                }

                if (isSeca) {
                    AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores_seca, sensor.getX() - sensor_tamanho_metade, sensor.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, mCores.getAmarelo());
                } else if (isSecaExtrema) {
                    AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores_seca, sensor.getX() - sensor_tamanho_metade, sensor.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, mCores.getVermelho());
                }


            }


            video_sensores_observando.empurrarQuadro(Efeitos.reduzirMetade(render_tronarko_sensores.toImagemSemAlfa()));
            video_sensores_observando_chuva.empurrarQuadro(Efeitos.reduzirMetade(render_tronarko_sensores_chuva.toImagemSemAlfa()));
            video_sensores_observando_seca.empurrarQuadro(Efeitos.reduzirMetade(render_tronarko_sensores_seca.toImagemSemAlfa()));

        }


        // Imagem.exportar(render_tronarko_sensores.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_observados.png"));

        video_sensores_observando.fechar();
        video_sensores_observando_chuva.fechar();
        video_sensores_observando_seca.fechar();

        SnapShotter.CRIAR(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando.vi"), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_observados.png"));
        SnapShotter.CRIAR(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando_chuva.vi"), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_observados_chuva.png"));
        SnapShotter.CRIAR(AtzumCreator.LOCAL_GET_ARQUIVO("videos/sensores_observando_seca.vi"), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_observados_seca.png"));


        AtzumCriativoLog.terminar("ServicoTronarko.OBSERVAR_SENSORES");
        AtzumCriativoLog.exibir_item("ServicoTronarko.OBSERVAR_SENSORES");

        //  ENTT.EXIBIR_TABELA(dados_brutos);


    }

    public static void VIDEO_PLAYER() {
        AzzalUnico.unico("AppVideo", 2000, 1100, new AppVideo());
    }

    public static void SNAP_SHOTS() {
        AzzalUnico.unico("AtzumSnapShots", 2000, 1100, new AtzumSnapShots());
    }


    public static void MINIATURAS() {

        SnapShotter.CRIAR("/home/luan/Imagens/atzum/videos/fatores_climaticos.vi", "/home/luan/Imagens/atzum/build/tronarko/tronarko_fatores_climaticos.png");
        SnapShotter.CRIAR("/home/luan/Imagens/atzum/videos/temperatura_zonas.vi", "/home/luan/Imagens/atzum/build/tronarko/tronarko_temperatura_zonas.png");


    }


    public static void OBSERAR_VARIADORES() {


        fmt.print(">> Obtendo dados dos sensores...");

        String arquivo_dados_brutos = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores.entts");
        // Lista<Entidade> dados_brutos = ENTT.ABRIR(arquivo_dados_brutos);


        // ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(dados_brutos));


        Lista<Entidade> dados_resumo = ENTT.CRIAR_LISTA();


        for (DSItem item_sensor : DS.ler_todos(arquivo_dados_brutos)) {

            Entidade sensor = ENTT.PARSER_ENTIDADE(item_sensor.getTexto());

            fmt.print("\t >> Processando Sensor :: {}", sensor.at("Sensor"));

            Entidade resumo = ENTT.GET_SEMPRE(dados_resumo, "Sensor", sensor.at("Sensor"));

            resumo.at("X", sensor.at("X"));
            resumo.at("Y", sensor.at("Y"));

            for (int s = 1; s <= 500; s++) {
                String fator_climatico = sensor.at("FC" + s);
                if (Strings.isValida(fator_climatico)) {
                    resumo.at(fator_climatico, resumo.atIntOuPadrao(fator_climatico, 0) + 1);
                }
            }

            int fc_t = resumo.atIntOuPadrao("TEMPESTADE", 0);


            int fc_seca = resumo.atIntOuPadrao("SECA", 0);
            int fc_se = resumo.atIntOuPadrao("SECA_EXTREMA", 0);
            int fc_oc = resumo.atIntOuPadrao("ONDA_DE_CALOR", 0);
            int fc_ventania = resumo.atIntOuPadrao("VENTANIA", 0);


            int fc_chuva = resumo.atIntOuPadrao("CHUVA", 0);
            int fc_neve = resumo.atIntOuPadrao("NEVE", 0);
            int fc_tc = resumo.atIntOuPadrao("TEMPESTADE_CHUVA", 0);
            int fc_tn = resumo.atIntOuPadrao("TEMPESTADE_NEVE", 0);

            int inferior = fc_chuva + fc_neve + fc_tc + fc_tn + fc_t;


            int superior = fc_seca + fc_se + fc_oc + fc_ventania + fc_t;

            resumo.at("INFERIOR", inferior);
            resumo.at("SUPERIOR", superior);

        }

        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(dados_resumo));


        int maximo_superior = ENTT.GET_INTEIRO_MAIOR(dados_resumo, "SUPERIOR");
        int maximo_inferior = ENTT.GET_INTEIRO_MAIOR(dados_resumo, "INFERIOR");

        fmt.print("\t + SUPERIOR {}", maximo_superior);
        fmt.print("\t + INFERIOR {}", maximo_inferior);


        AtzumCentralDados.INFORME("Variacao.Superior", maximo_superior);
        AtzumCentralDados.INFORME("Variacao.Inferior", maximo_inferior);

        Cores mCores = new Cores();


        AtzumTerra planeta = new AtzumTerra();

        int sensor_tamanho = 30;
        int sensor_tamanho_metade = sensor_tamanho / 2;

        Renderizador render_tronarko_sensores_original = new Renderizador(AtzumCreator.GET_MAPA());
        Rasterizador.trocar_cores(render_tronarko_sensores_original, mCores.getAmarelo(), mCores.getBranco());
        Renderizador render_tronarko_sensores_inferior = new Renderizador(render_tronarko_sensores_original.toImagemSemAlfa());
        Renderizador render_tronarko_sensores_superior = new Renderizador(render_tronarko_sensores_original.toImagemSemAlfa());


        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("variacao_inferior.qtt"), planeta.getLargura(), planeta.getAltura());
        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("variacao_superior.qtt"), planeta.getLargura(), planeta.getAltura());

        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("variacao_inferior.qtt"), planeta.getLargura(), planeta.getAltura(), -1);
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("variacao_superior.qtt"), planeta.getLargura(), planeta.getAltura(), -1);


        for (Entidade sensor : dados_resumo) {

            int x = sensor.atInt("X");
            int y = sensor.atInt("Y");


            int i_inferior = Matematica.ESCALA_NORMAL(sensor.atInt("INFERIOR"), maximo_inferior);
            int i_superior = Matematica.ESCALA_NORMAL(sensor.atInt("SUPERIOR"), maximo_superior);

            QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("variacao_inferior.qtt"), x, y, i_inferior);
            QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("variacao_superior.qtt"), x, y, i_superior);


            AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores_inferior, x - sensor_tamanho_metade, y - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, new HSV(40, 100, HSV.NORMAL(i_inferior)).toRGB());
            AtzumCreator.PREENCHER_TERRA(planeta, render_tronarko_sensores_superior, x - sensor_tamanho_metade, y - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, new HSV(40, 100, HSV.NORMAL(i_superior)).toRGB());


        }


        render_tronarko_sensores_inferior.exportarSemAlfa(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_limites_inferior.png"));
        render_tronarko_sensores_superior.exportarSemAlfa(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_limites_superior.png"));

    }


    public static void DADOS_TRONARKO_ORGANIZADOS_VER() {

        String arquivo_sensores_organizados_indexados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.ci");

        CacheIndexado sensores_organizando = new CacheIndexado(CacheIndexado.CENTUM, arquivo_sensores_organizados_indexados);

        sensores_organizando.abrir();

        int quantidade = sensores_organizando.getQuantidade();

        fmt.print("Itens : {}", quantidade);

        for (int i = 0; i < 30; i++) {

            long pos = sensores_organizando.getPosicao(i);
            long tam = sensores_organizando.getTamanho(i);

            String conteudo = sensores_organizando.get(i);

            fmt.print("| Item | {esq6} | {esq13} || {esq13}  | >> {}", i, pos, fmt.formatar_tamanho(tam), conteudo);
        }


        fmt.print("Menor Bloco >> {}", fmt.formatar_tamanho_precisao_dupla(sensores_organizando.getTamanhoMenorBloco()));
        fmt.print("Maior Bloco >> {}", fmt.formatar_tamanho_precisao_dupla(sensores_organizando.getTamanhoMaiorBloco()));


        String arquivo_sensores_organizados_por_sensor = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.ds");
        DS.limpar(arquivo_sensores_organizados_por_sensor);

        int sensor_id = 0;
        int sensores = sensores_organizando.getQuantidade();

        while (sensor_id < sensores) {
            fmt.print("\t >> Organizando sensor : {} de {}", sensor_id, sensores);
            DS.adicionar(arquivo_sensores_organizados_por_sensor, String.valueOf(sensor_id), sensores_organizando.get(sensor_id));
            sensor_id += 1;
        }


        sensores_organizando.fechar();

    }


    public static void SENSORES_ORGANIZAR_POR_SENSORES() {


        String arquivo_sensores_organizados_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");
        String arquivo_sensores_organizados_indexados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.ci");


        Lista<Entidade> sensores = Atzum.GET_SENSORES();


        int sensores_quantidade = sensores.getQuantidade() + 1;

        int sensores_faltam = sensores_quantidade - CentumIndexado.getQuantidade(arquivo_sensores_organizados_indexados);

        CacheIndexado sensores_organizando = new CacheIndexado(CacheIndexado.CENTUM, arquivo_sensores_organizados_indexados);

        sensores_organizando.abrir();

        while (sensores_organizando.getQuantidade() < sensores_quantidade) {
            fmt.print("\t Sensores Item :: Alocando :: Faltam {}", sensores_faltam);
            sensores_organizando.criar_slot();
            sensores_faltam -= 1;
        }


        fmt.print("\t Sensores Zerando....");

        int slot_id = 0;
        while (slot_id < sensores_quantidade) {
            fmt.print("\t Sensores Item :: Zerando :: Faltam {}", (sensores_quantidade - slot_id));
            sensores_organizando.set(slot_id, "");
            slot_id += 1;
        }


        int info_q1 = sensores_quantidade / 4;
        int info_q2 = (info_q1 * 2) - 5;
        int info_q3 = (info_q1 * 3) - 5;
        int info_q4 = (info_q1 * 4) - 5;


        for (DSItem superarko_dados : DS.ler_todos(arquivo_sensores_organizados_por_superarko)) {

            String superarko = Strings.GET_ATE(superarko_dados.getNome(), ".");

            fmt.print(">> Superarko : {}", superarko);

            Lista<Entidade> sensores_dados = ENTT.PARSER(superarko_dados.getTexto());

            for (Entidade sensor_item : sensores_dados) {

                int sensor_id = sensor_item.atInt("SensorID");

                Entidade sensor_final = ENTT.PARSER_ENTIDADE(sensores_organizando.get(sensor_id));


                sensor_final.at("T" + superarko, sensor_item.at("T" + superarko));
                sensor_final.at("U" + superarko, sensor_item.at("U" + superarko));
                sensor_final.at("M" + superarko, sensor_item.at("M" + superarko));
                sensor_final.at("FC" + superarko, sensor_item.at("FC" + superarko));
                sensor_final.at("IC" + superarko, sensor_item.at("IC" + superarko));
                sensor_final.at("UV" + superarko, sensor_item.at("UV" + superarko));


                sensores_organizando.set(sensor_id, ENTT.TO_DOCUMENTO(sensor_final));

                if (sensor_id == info_q1 || sensor_id == info_q2 || sensor_id == info_q3 || sensor_id == info_q4) {
                    //    fmt.print("\t -->> Sensor {} do Superarko {}", sensor_id, superarko);
                    fmt.print("\t ++ Sensor {} do Superarko {}", sensor_id, superarko);
                }

            }

        }


        sensores_organizando.fechar();


    }

    public static void SENSORES_ORGANIZAR_POR_SENSORES_COM_QUADRUM() {


        String arquivo_sensores_organizados_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");
        String arquivo_sensores_organizados_indexados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.qa");


        Lista<Entidade> sensores = Atzum.GET_SENSORES();


        int sensores_quantidade = sensores.getQuantidade() + 1;

        fmt.print("\t Sensores Alocando....");


        if (!FS.arquivo_existe(arquivo_sensores_organizados_indexados)) {
            Quadrum.init(arquivo_sensores_organizados_indexados, sensores_quantidade, 505);
        }

        fmt.print("\t Sensores Iniciando....");

        Quadrum sensores_organizando = new Quadrum(arquivo_sensores_organizados_indexados);

        sensores_organizando.abrir();

        int sensores_faltam = sensores_quantidade - (int) sensores_organizando.getQuantidadeDeItens();


        while (sensores_organizando.getQuantidadeDeItens() < sensores_quantidade) {
            fmt.print("\t Sensores Item :: Alocando :: Faltam {}", sensores_faltam);
            sensores_organizando.novo_item();
            sensores_faltam -= 1;
        }


        fmt.print("\t Sensores Zerando....");

        int slot_id = 0;
        while (slot_id < sensores_quantidade) {
            fmt.print("\t Sensores Item :: Zerando :: Faltam {}", (sensores_quantidade - slot_id));
            sensores_organizando.limpar(slot_id);
            slot_id += 1;
        }


        int info_q1 = sensores_quantidade / 4;
        int info_q2 = (info_q1 * 2) - 5;
        int info_q3 = (info_q1 * 3) - 5;
        int info_q4 = (info_q1 * 4) - 5;


        for (DSItem superarko_dados : DS.ler_todos(arquivo_sensores_organizados_por_superarko)) {

            String superarko = Strings.GET_ATE(superarko_dados.getNome(), ".");

            fmt.print(">> Superarko : {}", superarko);

            Lista<Entidade> sensores_dados = ENTT.PARSER(superarko_dados.getTexto());

            for (Entidade sensor_item : sensores_dados) {

                int sensor_id = sensor_item.atInt("SensorID");

                if (sensor_id == 0) {
                    //  fmt.print("{}", Strings.LINEARIZAR(sensor_item.toTexto()));
                }

                // Entidade sensor_final = ENTT.PARSER_ENTIDADE(sensores_organizando.get(sensor_id));

                sensores_organizando.set(sensor_id, Integer.parseInt(superarko), ENTT.TO_DOCUMENTO(sensor_item));

                if (sensor_id == info_q1 || sensor_id == info_q2 || sensor_id == info_q3 || sensor_id == info_q4) {
                    //    fmt.print("\t -->> Sensor {} do Superarko {}", sensor_id, superarko);
                    fmt.print("\t ++ Sensor {} do Superarko {}", sensor_id, superarko);
                }

            }

        }


        sensores_organizando.fechar();


    }

    public static void VER_SENSORES_COM_QUADRUM() {


        String arquivo_sensores_organizados_indexados = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.qa");

        Lista<Entidade> sensores = Atzum.GET_SENSORES();

        int sensores_quantidade = sensores.getQuantidade();

        Quadrum sensores_organizando = new Quadrum(arquivo_sensores_organizados_indexados);

        sensores_organizando.abrir();

        for (int i = 1; i <= 5; i++) {
            fmt.print("{} ->> {}", Strings.LINEARIZAR(sensores_organizando.get(0, i)), fmt.formatar_tamanho_precisao_dupla(sensores_organizando.getTamanho(0, i)));
        }

        for (int i = 495; i <= 500; i++) {
            fmt.print("{} ->> {}", Strings.LINEARIZAR(sensores_organizando.get(sensores_quantidade - 1, i)), fmt.formatar_tamanho_precisao_dupla(sensores_organizando.getTamanho(sensores_quantidade - 1, i)));
        }


        fmt.print("Item menor : {}", fmt.formatar_tamanho_precisao_dupla(sensores_organizando.getTamanhoMenor()));
        fmt.print("Item maior : {}", fmt.formatar_tamanho_precisao_dupla(sensores_organizando.getTamanhoMaior()));

        int maior = sensores_organizando.getTamanhoMaior();

        sensores_organizando.exibirItensComTamanho(maior);


        sensores_organizando.fechar();

        String arquivo_sensores_organizados_indexados_compactado = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.dz");

        //  Zipper.ZIPAR_ARQUIVO(arquivo_sensores_organizados_indexados,arquivo_sensores_organizados_indexados_compactado);

        // Zipper.UNZIPAR_ARQUIVO(arquivo_sensores_organizados_indexados_compactado,arquivo_sensores_organizados_indexados);
    }


    public static void SENSORES_ORGANIZAR_POR_SENSORES_A_PARTIR_DE_QUADRUM() {


        String arquivo_sensores_por_sensor_quadrum = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.qa");
        String arquivo_sensores_por_sensor = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.ds");


        DS.limpar(arquivo_sensores_por_sensor);

        Lista<Entidade> sensores = Atzum.GET_SENSORES();

        int sensores_quantidade = sensores.getQuantidade();

        Quadrum sensores_organizando = new Quadrum(arquivo_sensores_por_sensor_quadrum);

        sensores_organizando.abrir();

        for (Entidade e_sensor : sensores) {

            Entidade sensor_dados = new Entidade();
            sensor_dados.at("SensorID", e_sensor.atInt("SensorID"));
            sensor_dados.at("Sensor", e_sensor.at("Sensor"));
            sensor_dados.at("X", e_sensor.at("X"));
            sensor_dados.at("Y", e_sensor.at("Y"));

            for (int superarko = 1; superarko <= 500; superarko++) {

                Entidade item = ENTT.PARSER_ENTIDADE(sensores_organizando.get(e_sensor.atInt("SensorID"), superarko));

                sensor_dados.at("T" + superarko, item.at("T" + superarko));
                sensor_dados.at("U" + superarko, item.at("U" + superarko));
                sensor_dados.at("M" + superarko, item.at("M" + superarko));
                sensor_dados.at("FC" + superarko, item.at("FC" + superarko));
                sensor_dados.at("IC" + superarko, item.at("IC" + superarko));
                sensor_dados.at("UV" + superarko, item.at("UV" + superarko));


            }

            DS.adicionar(arquivo_sensores_por_sensor, e_sensor.at("SensorID"), ENTT.TO_DOCUMENTO(sensor_dados));

            fmt.print("\t >> Sensor {esq6} de {esq6}", e_sensor.at("SensorID"), sensores_quantidade);
        }


        sensores_organizando.fechar();


    }


    public static void SENSORES_POR_SENSOR_VISUALIZAR() {

        String arquivo_sensores_por_sensor = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_sensor.ds");


        //  Lista<Entidade> sensores = new Lista<Entidade>();
        Lista<Entidade> analise_sensores = new Lista<Entidade>();

        int i = 0;
        for (DSItem item : DS.ler_todos(arquivo_sensores_por_sensor)) {

            fmt.print("\t >> Sensor : {}", item.getNome());

            //  Entidade e_sensor = ENTT.ADICIONAR_EM(sensores,ENTT.PARSER_ENTIDADE(item.getTexto()));
            Entidade e_sensor = ENTT.PARSER_ENTIDADE(item.getTexto());

            Entidade a_sensor = ENTT.CRIAR_EM(analise_sensores, "SensorID", e_sensor.at("SensorID"));
            a_sensor.at("Sensor", e_sensor.at("Sensor"));
            a_sensor.at("X", e_sensor.at("X"));
            a_sensor.at("Y", e_sensor.at("Y"));

            int t = 0;
            int u = 0;

            for (int s = 1; s <= 500; s++) {
                if (e_sensor.isValido("T" + s)) {
                    t += 1;
                }
                if (e_sensor.isValido("U" + s)) {
                    u += 1;
                }
            }

            a_sensor.at("Temperatura", t);
            a_sensor.at("Umidade", u);


            //  if (i > 10) {
            //    break;
            //  }
            i += 1;
        }

        // ENTT.EXIBIR_TABELA_COM_NOME(ENTT.GET_AMOSTRA_PEQUENA(sensores), "Dados Sensores - Por Sensor");
        ENTT.EXIBIR_TABELA_COM_NOME(ENTT.GET_AMOSTRA_PEQUENA(analise_sensores), "Analise - Dados Sensores - Por Sensor");


        fmt.print("Sensores com temperatura valida : {}", ENTT.CONTAGEM(analise_sensores, "Temperatura", 500));
        fmt.print("Sensores com umidade valida     : {}", ENTT.CONTAGEM(analise_sensores, "Umidade", 500));


    }

    public static void PROCESSAR_TRANSICAO_DE_TEMPERATURA(AtzumTerra mapa_planeta, int superarko, AreaDouble tronarko_temperatura, Extremos<Double> d_indo, Extremos<Double> d_voltando, AreaDouble tronarko_temperatura_transicao) {

        boolean temperatura_aumetando = true;

        if (superarko > 250) {
            temperatura_aumetando = false;
        }


        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    if (temperatura_aumetando) {
                        tronarko_temperatura.set(x, y, tronarko_temperatura.get(x, y) + tronarko_temperatura_transicao.get(x, y));
                        d_indo.set(tronarko_temperatura.get(x, y));
                    } else {
                        tronarko_temperatura.set(x, y, tronarko_temperatura.get(x, y) - tronarko_temperatura_transicao.get(x, y));
                        d_voltando.set(tronarko_temperatura.get(x, y));
                    }


                }
            }
        }

    }


    public static Lista<Entidade> ORGANIZAR_DADOS_SENSORES(Atzum atzum, int superarko, AreaDouble tronarko_temperatura, QTT umidade_dados, AreaDouble tronarko_umidade_variacao, Renderizador render_fatores_climaticos, Renderizador render_massas_de_ar, Lista<Entidade> sensores) {

        Lista<Entidade> sensores_dados = new Lista<Entidade>();

        for (Entidade sensor : sensores) {


            int sensor_px = 0;
            int sensor_py = 0;


            int padrao_sensor_px = sensor.atInt("X");
            int padrao_sensor_py = sensor.atInt("Y");

            if (sensor.is("Tipo", "Comum")) {
                sensor_px = sensor.atInt("X");
                sensor_py = sensor.atInt("Y");
            } else if (sensor.is("Tipo", "Cidade")) {
                sensor_px = sensor.atInt("X");
                sensor_py = sensor.atInt("Y");
            } else if (sensor.is("Tipo", "Referenciado")) {
                sensor_px = sensor.atInt("RefX");
                sensor_py = sensor.atInt("RefY");
            }


            libs.entt.Entidade sensor_param = ENTT.CRIAR_EM(sensores_dados, "Sensor", padrao_sensor_px + "::" + padrao_sensor_py);

            String fator_climatico_corrente = atzum.getFatorClimatico(render_fatores_climaticos.getPixel(sensor_px, sensor_py));
            double temperatura_corrente = tronarko_temperatura.get(sensor_px, sensor_py);
            double umidade_corrente = umidade_dados.getValor(sensor_px, sensor_py);

            double umidade_variacao = tronarko_umidade_variacao.get(sensor_px, sensor_py);

            umidade_corrente += umidade_variacao;

            umidade_corrente = Matematica.NORMALIZAR(umidade_corrente, 0, 100);

            String tem_interferencia = "NAO";

            if (Strings.isIgual(fator_climatico_corrente, "ONDA_DE_CALOR")) {
                temperatura_corrente += 7;
                tem_interferencia = "SIM";
            } else if (Strings.isIgual(fator_climatico_corrente, "SECA")) {
                temperatura_corrente += 5;
                tem_interferencia = "SIM";
            } else if (Strings.isIgual(fator_climatico_corrente, "SECA_EXTREMA")) {
                temperatura_corrente += 10;
                tem_interferencia = "SIM";
            } else if (Strings.isIgual(fator_climatico_corrente, "CHUVA")) {
                temperatura_corrente -= 10;
                tem_interferencia = "SIM";
            } else if (Strings.isIgual(fator_climatico_corrente, "TEMPESTADE_CHUVA")) {
                temperatura_corrente -= 12;
                tem_interferencia = "SIM";
            } else if (Strings.isIgual(fator_climatico_corrente, "NEVE")) {
                temperatura_corrente -= 15;
                tem_interferencia = "SIM";
            } else if (Strings.isIgual(fator_climatico_corrente, "TEMPESTADE")) {
                temperatura_corrente -= 10;
                tem_interferencia = "SIM";
            }

            umidade_corrente = Matematica.NORMALIZAR(umidade_corrente, 0, 100);

            sensor_param.at("SensorID", sensor.atInt("SensorID"));
            sensor_param.at("X", padrao_sensor_px);
            sensor_param.at("Y", padrao_sensor_py);
            sensor_param.at("T" + superarko, fmt.f2(temperatura_corrente));
            sensor_param.at("U" + superarko, fmt.f2(umidade_corrente));
            sensor_param.at("M" + superarko, atzum.getMassaDeArTipo(render_massas_de_ar.getPixel(sensor_px, sensor_py)));
            sensor_param.at("FC" + superarko, fator_climatico_corrente);
            sensor_param.at("IC" + superarko, tem_interferencia);
            sensor_param.at("UV" + superarko, fmt.f2(umidade_variacao));

        }

        return sensores_dados;
    }

}
