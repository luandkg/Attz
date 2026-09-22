package apps.app_atzum.servicos;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumTerra;
import apps.app_atzum.utils.AtzumCriativoLog;
import apps.app_atzum.utils.AtzumPontosInteiro;
import apps.app_atzum.utils.Rasterizador;
import apps.app_atzum.utils.RegiaoDefinida;
import libs.arquivos.QTT;
import libs.arquivos.QTTCache;
import libs.arquivos.binario.Inteiro;
import libs.arquivos.video.Empilhador;
import libs.arquivos.video.VideoCodecador;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.azzal.utilitarios.HSV;
import libs.imagem.Efeitos;
import libs.imagem.Imagem;
import libs.luan.*;
import libs.meta_functional.Acao;

import java.awt.image.BufferedImage;

public class ServicoRegioes {

    private static final String SERVICO_NOME = "ServicoRegioes";


    public static void INIT() {
        AtzumCriativoLog.iniciar(SERVICO_NOME + ".INIT");

        ORGANIZAR_REGIOES();
        EXPANDIR_REGIOES_ATE_A_MARGEM();
        EXTRAIR_REGIOES_CONTORNOS();

        ORGANIZAR_DADOS_REGIOES();

        EXTRAIR_CONTORNO_OCEANICO();

        EXTRAIR_DISTANCIA_OCEANICA();
        PROXIMIDADE_COM_OCEANO();
        PROXIMIDADE_COM_TERRA();

        ORGANIZAR_DADOS_PLANETA();
        ORGANIZAR_OCEANOS();
        RENDERIZAR_OCEANOS();

        AtzumCriativoLog.terminar(SERVICO_NOME + ".INIT");
        AtzumCriativoLog.exibir_item(SERVICO_NOME + ".INIT");
    }


    public static void ORGANIZAR_REGIOES() {

        AtzumCriativoLog.iniciar("ServicoRegioes.ORGANIZAR_REGIOES");

        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/planeta/atzum_planeta.png"));


        Cores mCores = new Cores();

        Renderizador render = new Renderizador(mapa);


        RefInt processante = new RefInt(0);

        Acao durante_mudanca = new Acao() {
            @Override
            public void fazer() {
                Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/processando.png"));
            }
        };

        VideoCodecador vic = new VideoCodecador();
        Empilhador vic_empilhador = vic.criar(AtzumCreator.VIDEO_GET_ARQUIVO("regionalizando.vi"), render.getLargura() / 2, render.getAltura() / 2);

        Acao a_cada_100 = new Acao() {
            @Override
            public void fazer() {

             //   Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.PROCESSANDO_GET_ARQUIVO("processando_" + fmt.zerado(processante.get(), 4) + ".png"));
                processante.set(processante.get() + 1);
                vic_empilhador.empurrarQuadro(Efeitos.reduzirMetade(render.toImagemSemAlfa()));

            }
        };


        Lista<RegiaoDefinida> regioes = AtzumCreator.GET_REGIOES();

        for (RegiaoDefinida regiao : regioes) {

            fmt.print(">> Rasterizar regiao :: {} - {}", regiao.getValor(), regiao.getSubValor());

            Rasterizador.RASTERIZAR_COM(render, regiao.getX(), regiao.getY(), mCores.getPreto(), regiao.getCor(), durante_mudanca, a_cada_100);
        }

        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes"));

        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes/regioes_v1.png"));

        vic_empilhador.fechar();

        AtzumCriativoLog.terminar("ServicoRegioes.ORGANIZAR_REGIOES");
        AtzumCriativoLog.exibir_item("ServicoRegioes.ORGANIZAR_REGIOES");

    }


    public static void EXPANDIR_REGIOES_ATE_A_MARGEM() {

        AtzumCriativoLog.iniciar("ServicoRegioes.EXPANDIR_REGIOES_ATE_A_MARGEM");


        Cores mCores = new Cores();
        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes/regioes_v1.png"));


        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {
                if (render.getPixel(x, y).igual(mCores.getVermelho())) {

                    Cor mais_proxima = Rasterizador.GET_COR_DO_QUADRANTE_DIFERENTE(render, x, y, 50, mCores.getVermelho(), mCores.getPreto());
                    render.setPixel(x, y, mais_proxima);

                }
            }
        }


        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes/regioes_v2.png"));
        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes.png"));

        AtzumCriativoLog.terminar("ServicoRegioes.EXPANDIR_REGIOES_ATE_A_MARGEM");
        AtzumCriativoLog.exibir_item("ServicoRegioes.EXPANDIR_REGIOES_ATE_A_MARGEM");

    }


    public static void ORGANIZAR_DADOS_REGIOES() {

        AtzumCriativoLog.iniciar("ServicoRegioes.ORGANIZAR_DADOS_REGIOES");

        Renderizador mapa_global = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes.png"));

        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("regioes.qtt"), mapa_global.getLargura(), mapa_global.getAltura());
        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("subregioes.qtt"), mapa_global.getLargura(), mapa_global.getAltura());

        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("regioes.qtt"), mapa_global.getLargura(), mapa_global.getAltura(), -1);
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("subregioes.qtt"), mapa_global.getLargura(), mapa_global.getAltura(), -1);


        Lista<RegiaoDefinida> regioes = AtzumCreator.GET_REGIOES();

        Cor cor_mudanca = new Cor(100, 100, 100);


        for (RegiaoDefinida regiao : regioes) {

            Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes.png"));

            RefInt processante = new RefInt(0);

            Acao durante_mudanca = new Acao() {
                @Override
                public void fazer() {
                    Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/processando.png"));
                }
            };

            Acao a_cada_100 = new Acao() {
                @Override
                public void fazer() {

                   // Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.PROCESSANDO_GET_ARQUIVO("processando_" + fmt.zerado(processante.get(), 4) + ".png"));
                    processante.set(processante.get() + 1);

                }
            };

            fmt.print("Regiao {}::{} ->> Cor :: {}", regiao.getValor(), regiao.getSubValor(), regiao.getCor().toString());
            fmt.print("\t ++ {} : {} == {}", regiao.getX(), regiao.getY(), render.getPixel(regiao.getX(), regiao.getY()).toString());

            Rasterizador.RASTERIZAR_COM(render, regiao.getX(), regiao.getY(), regiao.getCor(), cor_mudanca, durante_mudanca, a_cada_100);

            fmt.print("\t ++ {} : {} == {}", regiao.getX(), regiao.getY(), render.getPixel(regiao.getX(), regiao.getY()).toString());

            Cores mCores = new Cores();

            Renderizador render_salvar = Renderizador.CONSTRUIR(render.getLargura(), render.getAltura(), mCores.getPreto());


            Rasterizador.trocar_cores(render_salvar, mCores.getAmarelo(), mCores.getBranco());

            fmt.print("\t ++ Salvar Dados QTT");

            QTTCache cache_regioes = new QTTCache((AtzumCreator.DADOS_GET_ARQUIVO("regioes.qtt")), 1000);
            QTTCache cache_subregioes = new QTTCache((AtzumCreator.DADOS_GET_ARQUIVO("subregioes.qtt")), 1000);


            for (int y = 0; y < render.getAltura(); y++) {
                for (int x = 0; x < render.getLargura(); x++) {
                    if (render.getPixel(x, y).igual(cor_mudanca)) {
                        render_salvar.setPixel(x, y, regiao.getCor());

                        cache_regioes.cache(x, y, regiao.getValor());
                        cache_subregioes.cache(x, y, regiao.getSubValor());

                    }
                }
            }

            cache_regioes.guardar();
            cache_subregioes.guardar();

            fmt.print("\t ++ Dados QTT OK !");

            Imagem.exportar(render_salvar.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes/" + regiao.getValor() + "_" + regiao.getSubValor() + ".png"));

        }

        AtzumCriativoLog.terminar("ServicoRegioes.ORGANIZAR_DADOS_REGIOES");
        AtzumCriativoLog.exibir_item("ServicoRegioes.ORGANIZAR_DADOS_REGIOES");


    }


    public static void EXTRAIR_REGIOES_CONTORNOS() {

        AtzumCriativoLog.iniciar("ServicoRegioes.EXTRAIR_CONTORNOS");

        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes/regioes_v2.png"));

        Cores mCores = new Cores();


        Renderizador render_salvar = Renderizador.CONSTRUIR(render.getLargura(), render.getAltura(), mCores.getPreto());


        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {

                Cor ponto_a = render.getPixel(x, y);

                if (render.isPontoValido(x + 1, y)) {

                    Cor ponto_b = render.getPixel(x + 1, y);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x - 1, y)) {

                    Cor ponto_b = render.getPixel(x - 1, y);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x, y + 1)) {

                    Cor ponto_b = render.getPixel(x, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x, y - 1)) {

                    Cor ponto_b = render.getPixel(x, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                // DIAGONAL
                if (render.isPontoValido(x + 1, y + 1)) {

                    Cor ponto_b = render.getPixel(x + 1, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x + 1, y - 1)) {

                    Cor ponto_b = render.getPixel(x + 1, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x - 1, y + 1)) {

                    Cor ponto_b = render.getPixel(x - 1, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x - 1, -+1)) {

                    Cor ponto_b = render.getPixel(x - 1, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

            }
        }

        Imagem.exportar(render_salvar.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes/regioes_v3.png"));
        Imagem.exportar(render_salvar.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes_contornos.png"));


        AtzumCriativoLog.terminar("ServicoRegioes.EXTRAIR_CONTORNOS");
        AtzumCriativoLog.exibir_item("ServicoRegioes.EXTRAIR_CONTORNOS");

    }

    public static void EXTRAIR_CONTORNO_OCEANICO() {

        AtzumCriativoLog.iniciar("ServicoRegioes.EXTRAIR_CONTORNO_OCEANICO");

        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes.png"));

        Cores mCores = new Cores();
        Renderizador render = new Renderizador(mapa);


        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {
                if (render.getPixel(x, y).igual(mCores.getPreto())) {

                } else {
                    render.setPixel(x, y, mCores.getAmarelo());
                }
            }
        }

        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_terra.png"));

        Renderizador render_preto_e_branco = render.getCopia();
        Rasterizador.trocar_cores(render_preto_e_branco, mCores.getBranco(), mCores.getPreto());
        Rasterizador.trocar_cores(render_preto_e_branco, mCores.getAmarelo(), mCores.getBranco());

        Imagem.exportar(render_preto_e_branco.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_terra_preto_e_branco.png"));



        Renderizador render_salvar = Renderizador.CONSTRUIR(render.getLargura(), render.getAltura(), mCores.getPreto());


        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {

                Cor ponto_a = render.getPixel(x, y);

                if (render.isPontoValido(x + 1, y)) {

                    Cor ponto_b = render.getPixel(x + 1, y);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x - 1, y)) {

                    Cor ponto_b = render.getPixel(x - 1, y);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x, y + 1)) {

                    Cor ponto_b = render.getPixel(x, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x, y - 1)) {

                    Cor ponto_b = render.getPixel(x, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                // DIAGONAL
                if (render.isPontoValido(x + 1, y + 1)) {

                    Cor ponto_b = render.getPixel(x + 1, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x + 1, y - 1)) {

                    Cor ponto_b = render.getPixel(x + 1, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x - 1, y + 1)) {

                    Cor ponto_b = render.getPixel(x - 1, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x - 1, -+1)) {

                    Cor ponto_b = render.getPixel(x - 1, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

            }
        }

        Imagem.exportar(render_salvar.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_contorno_oceanico.png"));


        AtzumCriativoLog.terminar("ServicoRegioes.EXTRAIR_CONTORNO_OCEANICO");
        AtzumCriativoLog.exibir_item("ServicoRegioes.EXTRAIR_CONTORNO_OCEANICO");

    }


    public static void EXTRAIR_DISTANCIA_OCEANICA() {

        AtzumCriativoLog.iniciar("ServicoRegioes.EXTRAIR_DISTANCIA_OCEANICA");

        AtzumTerra atzum_terra = new AtzumTerra();


        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("distancia_oceanica.qtt"), atzum_terra.getLargura(), atzum_terra.getAltura());
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("distancia_oceanica.qtt"), atzum_terra.getLargura(), atzum_terra.getAltura(), -1);

        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("distancia_terra.qtt"), atzum_terra.getLargura(), atzum_terra.getAltura());
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("distancia_terra.qtt"), atzum_terra.getLargura(), atzum_terra.getAltura(), -1);


        Cores mCores = new Cores();


        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_contorno_oceanico.png"));

        Lista<Ponto> limite_oceanico = Rasterizador.GET_PONTOS_DE_COR(render, mCores.getVermelho());


        fmt.print("Pontos de limite : {}", limite_oceanico.getQuantidade());

        QTTCache dist_oceano_limite = new QTTCache(AtzumCreator.DADOS_GET_ARQUIVO("distancia_oceanica.qtt"), 1000);

        for (Ponto pt : limite_oceanico) {
            dist_oceano_limite.cache(pt.getX(), pt.getY(), 0);
        }

        dist_oceano_limite.guardar();

        fmt.print("Guardados : {}", limite_oceanico.getQuantidade());


        fmt.print("Calculando proximidade com o mar !");

        QTTCache dist_oceano = new QTTCache(AtzumCreator.DADOS_GET_ARQUIVO("distancia_oceanica.qtt"), 1000);
        QTTCache dist_terra = new QTTCache(AtzumCreator.DADOS_GET_ARQUIVO("distancia_terra.qtt"), 1000);

        int progresso_total = atzum_terra.getLargura()*atzum_terra.getAltura();
        int progresso_contagem = 0;
        int progresso_parcela = progresso_total/100;
        int progresso_parcela_contagem = 0;
        int progresso_exibir = 0;


        for (int y = 0; y < atzum_terra.getAltura(); y++) {
            for (int x = 0; x < atzum_terra.getLargura(); x++) {

                int proximidade = Integer.MAX_VALUE;

                for (Ponto pt : limite_oceanico) {
                    int distancia = Espaco2D.distancia_entre_pontos(x, y, pt.getX(), pt.getY());
                    if (distancia < proximidade) {
                        proximidade = distancia;
                    }
                }


                if (atzum_terra.isTerra(x, y)) {
                    dist_oceano.cache(x, y, proximidade);
                } else {
                    dist_terra.cache(x, y, proximidade);
                }

                progresso_contagem+=1;
                progresso_parcela_contagem+=1;
                if(progresso_parcela_contagem==progresso_parcela){
                    progresso_parcela_contagem=0;
                    progresso_exibir+=1;
                    fmt.print("\t >> Progresso : {}",progresso_exibir);
                }
            }
        }

        dist_oceano.guardar();
        dist_terra.guardar();

        fmt.print("Tudo OK !");
        AtzumCriativoLog.terminar("ServicoRegioes.EXTRAIR_DISTANCIA_OCEANICA");
        AtzumCriativoLog.exibir_item("ServicoRegioes.EXTRAIR_DISTANCIA_OCEANICA");


    }


    public static void PROXIMIDADE_COM_OCEANO() {

        AtzumCriativoLog.iniciar("ServicoRegioes.PROXIMIDADE_COM_OCEANO");

        AtzumTerra atzum_terra = new AtzumTerra();

        fmt.print("Lendo distancias...");

        Cores mCores = new Cores();

        QTT oceano_distancias = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("distancia_oceanica.qtt"));

        Extremos<Integer> distancia_oceanica = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());


        for (int y = 0; y < atzum_terra.getAltura(); y++) {
            for (int x = 0; x < atzum_terra.getLargura(); x++) {
                if (atzum_terra.isTerra(x, y)) {

                    int valor = oceano_distancias.getValor(x, y);
                    distancia_oceanica.set(valor);

                }
            }
        }


        fmt.print("Maior Distancia : {}", distancia_oceanica.getMaior());
        fmt.print("Menor Distancia : {}", distancia_oceanica.getMenor());


        Renderizador render_distancia = Renderizador.construir(atzum_terra.getLargura(), atzum_terra.getAltura(), mCores.getPreto());

        double escala_distancia = ((double) distancia_oceanica.getMaior() - (double) distancia_oceanica.getMenor()) / 100.0;


        for (int y = 0; y < atzum_terra.getAltura(); y++) {
            for (int x = 0; x < atzum_terra.getLargura(); x++) {
                if (atzum_terra.isTerra(x, y)) {

                    int valor = oceano_distancias.getValor(x, y);

                    render_distancia.setPixel(x, y, new HSV(240, 50, HSV.NORMAL_ESCALADO(valor, escala_distancia)));

                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_oceano_distancia.png"));

        fmt.print("Tudo OK !");
        AtzumCriativoLog.terminar("ServicoRegioes.PROXIMIDADE_COM_OCEANO");
        AtzumCriativoLog.exibir_item("ServicoRegioes.PROXIMIDADE_COM_OCEANO");

    }

    public static void PROXIMIDADE_COM_TERRA() {

        AtzumCriativoLog.iniciar("ServicoRegioes.PROXIMIDADE_COM_TERRA");

        AtzumTerra atzum_terra = new AtzumTerra();

        fmt.print("Lendo distancias...");

        Cores mCores = new Cores();

        QTT oceano_distancias = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("distancia_terra.qtt"));

        Extremos<Integer> distancia_oceanica = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());


        for (int y = 0; y < atzum_terra.getAltura(); y++) {
            for (int x = 0; x < atzum_terra.getLargura(); x++) {
                if (atzum_terra.isOceano(x, y)) {

                    int valor = oceano_distancias.getValor(x, y);
                    distancia_oceanica.set(valor);

                }
            }
        }


        fmt.print("Maior Distancia : {}", distancia_oceanica.getMaior());
        fmt.print("Menor Distancia : {}", distancia_oceanica.getMenor());


        Renderizador render_distancia = Renderizador.construir(atzum_terra.getLargura(), atzum_terra.getAltura(), mCores.getPreto());

        double escala_distancia = ((double) distancia_oceanica.getMaior() - (double) distancia_oceanica.getMenor()) / 100.0;


        for (int y = 0; y < atzum_terra.getAltura(); y++) {
            for (int x = 0; x < atzum_terra.getLargura(); x++) {
                if (atzum_terra.isOceano(x, y)) {

                    int valor = oceano_distancias.getValor(x, y);

                    int escalado = (int) ((double) valor / escala_distancia);
                    render_distancia.setPixel(x, y, new HSV(240, 50, HSV.NORMAL(escalado)));


                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_terra_distancia.png"));

        fmt.print("Tudo OK !");
        AtzumCriativoLog.terminar("ServicoRegioes.PROXIMIDADE_COM_TERRA");
        AtzumCriativoLog.exibir_item("ServicoRegioes.PROXIMIDADE_COM_TERRA");

    }

    public static void ORGANIZAR_DADOS_PLANETA() {

        AtzumCriativoLog.iniciar("ServicoRegioes.ORGANIZAR_DADOS_PLANETA");

        AtzumTerra atzum_terra = new AtzumTerra();

        int largura = atzum_terra.getLargura();
        int altura = atzum_terra.getAltura();

        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("planeta.qtt"), atzum_terra.getLargura(), atzum_terra.getAltura());

        QTTCache dados_planeta = new QTTCache(AtzumCreator.DADOS_GET_ARQUIVO("planeta.qtt"), 1000);

        for (int y = 0; y < altura; y++) {

            for (int x = 0; x < largura; x++) {

                if (atzum_terra.isTerra(x, y)) {
                    dados_planeta.cache(x, y, 1);
                } else {
                    dados_planeta.cache(x, y, -1);
                }


            }
        }

        dados_planeta.guardar();

        AtzumCriativoLog.terminar("ServicoRegioes.ORGANIZAR_DADOS_PLANETA");

        fmt.print("OK !");
    }

    public static void ORGANIZAR_OCEANOS() {
        fmt.print("Feature :: Oceanos");

        AtzumCriativoLog.iniciar("ServicoRegioes.ORGANIZAR_OCEANOS");

        String ARQUIVO_OCEANO = AtzumCreator.LOCAL_GET_ARQUIVO("parametros/OCEANOS.dkg");
        Unico<Par<Ponto, Integer>> pontos_de_relevo = AtzumPontosInteiro.UNICOS(AtzumPontosInteiro.ABRIR_ZERADO(ARQUIVO_OCEANO));

        int valor = 100;

        for (Par<Ponto, Integer> ponto : pontos_de_relevo) {
            ponto.setValor(valor);
            valor += 100;
        }

        AtzumTerra atzum_terra = new AtzumTerra();


        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("oceanos.qtt"), atzum_terra.getLargura(), atzum_terra.getAltura());


        int largura = atzum_terra.getLargura();
        int altura = atzum_terra.getAltura();

        QTTCache dados_oceano = new QTTCache(AtzumCreator.DADOS_GET_ARQUIVO("oceanos.qtt"), 1000);

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                if (atzum_terra.isOceano(x, y)) {
                    int valor_proximo = ServicoRelevo.ALTITUDE_MAIS_PROXIMA(pontos_de_relevo, x, y);
                    dados_oceano.cache(x, y, (valor_proximo / 100));
                } else {
                    dados_oceano.cache(x, y, -1);
                }
            }
        }

        dados_oceano.guardar();
        AtzumCriativoLog.terminar("ServicoRegioes.ORGANIZAR_OCEANOS");

        fmt.print("OK !");
    }

    public static void RENDERIZAR_OCEANOS() {

        AtzumCriativoLog.iniciar("ServicoRegioes.RENDERIZAR_OCEANOS");

        AtzumTerra atzum_terra = new AtzumTerra();


        QTT dados_oceano = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("oceanos.qtt"));


        int largura = atzum_terra.getLargura();
        int altura = atzum_terra.getAltura();

        Cores mCores = new Cores();
        Renderizador render = Renderizador.CONSTRUIR(largura, altura, mCores.getPreto());


        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int valor = dados_oceano.getValor(x, y);

                if (valor > 0) {

                    if (valor % 2 == 0) {
                        valor += 1;
                        render.setPixel(x, y, new HSV(200, valor * 15, 80));
                    } else {
                        valor -= 1;
                        render.setPixel(x, y, new HSV(200, 100 - (valor * 20), 80));
                    }

                }

            }
        }

        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("atzum_oceanos.png"));

        AtzumCriativoLog.terminar("ServicoRegioes.RENDERIZAR_OCEANOS");

        fmt.print("OK !");

    }

}
