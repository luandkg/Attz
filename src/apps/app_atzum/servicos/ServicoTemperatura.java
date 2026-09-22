package apps.app_atzum.servicos;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_atzum.utils.AtzumCidades;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumTerra;
import apps.app_atzum.utils.AtzumCriativoLog;
import apps.app_atzum.utils.AtzumPontosInteiro;
import apps.app_atzum.utils.IntervaloDeValorColorido;
import apps.app_atzum.utils.Rasterizador;
import apps.app_letrum.Fonte;
import apps.app_letrum.Maker.FonteRunTime;
import libs.arquivos.QTT;
import libs.arquivos.binario.Inteiro;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.azzal.utilitarios.HSV;
import libs.imagem.Imagem;
import libs.luan.*;

import java.awt.image.BufferedImage;

public class ServicoTemperatura {

    private static final String SERVICO_NOME = "ServicoTemperatura";

    public static void INIT_T1() {

        BufferedImage mapa_global = AtzumCreator.GET_MAPA();
        Renderizador render = new Renderizador(mapa_global);

        Cores mCores = new Cores();

        Rasterizador.trocar_cores(render, mCores.getPreto(), mCores.getBranco());


        Fonte ESCRITOR_NORMAL = new FonteRunTime(mCores.getVerde(), 30);
        ESCRITOR_NORMAL.setRenderizador(render);

        Fonte ESCRITOR_NORMAL_AZUL = new FonteRunTime(mCores.getAzul(), 30);
        ESCRITOR_NORMAL_AZUL.setRenderizador(render);

        Fonte ESCRITOR_NORMAL_VERMELHO = new FonteRunTime(mCores.getVermelho(), 30);
        ESCRITOR_NORMAL_VERMELHO.setRenderizador(render);


        Lista<Ponto> pontos_verao = AtzumCidades.ABRIR_LOCAIS(AtzumCreator.LOCAL_GET_ARQUIVO("LINHA_TERMICA_1.dkg"));


        Lista.ORDENAR_CRESCENTE(pontos_verao, new Ordenavel<Ponto>() {
            @Override
            public int emOrdem(Ponto a, Ponto b) {
                return Integer.compare(a.getX(), b.getX());
            }
        });

        boolean tem_anterior = false;
        Ponto pt_anterior = null;

        for (Ponto ponto : pontos_verao) {
            render.drawCirculoCentralizado_Pintado(ponto, 5, mCores.getVermelho());

            if (tem_anterior) {
                render.drawLinha(ponto.getX(), ponto.getY(), pt_anterior.getX(), pt_anterior.getY(), mCores.getVermelho());
            }

            tem_anterior = true;
            pt_anterior = ponto;

        }


        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura_1/atzum_temperatura_verao.png"));


        // FASE 2
        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_1.qtt"), mapa_global.getWidth(), mapa_global.getHeight());
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_1.qtt"), mapa_global.getWidth(), mapa_global.getHeight(), -1);

        for (Ponto pt : pontos_verao) {
            QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_1.qtt"), pt.getX(), pt.getY(), 0);
        }

        fmt.print("Guardados : {}", pontos_verao.getQuantidade());


        fmt.print("Calculando proximidade com o faixa limite !");
        Renderizador terra = new Renderizador(AtzumCreator.GET_MAPA());

        for (int y = 0; y < terra.getAltura(); y++) {
            for (int x = 0; x < terra.getLargura(); x++) {
                if (terra.getPixel(x, y).igual(mCores.getAmarelo())) {

                    int proximidade = Espaco2D.GET_DISTANCIA_MAIS_PROXIMA(pontos_verao, x, y);

                    QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_1.qtt"), x, y, proximidade);

                }
            }
        }

        fmt.print("Tudo OK !");

    }

    public static void INIT_T2() {

        BufferedImage mapa_global = AtzumCreator.GET_MAPA();
        Renderizador render = new Renderizador(mapa_global);

        Cores mCores = new Cores();

        Rasterizador.trocar_cores(render, mCores.getPreto(), mCores.getBranco());


        Fonte ESCRITOR_NORMAL = new FonteRunTime(mCores.getVerde(), 30);
        ESCRITOR_NORMAL.setRenderizador(render);

        Fonte ESCRITOR_NORMAL_AZUL = new FonteRunTime(mCores.getAzul(), 30);
        ESCRITOR_NORMAL_AZUL.setRenderizador(render);

        Fonte ESCRITOR_NORMAL_VERMELHO = new FonteRunTime(mCores.getVermelho(), 30);
        ESCRITOR_NORMAL_VERMELHO.setRenderizador(render);


        Lista<Ponto> pontos_verao = AtzumCidades.ABRIR_LOCAIS(AtzumCreator.LOCAL_GET_ARQUIVO("LINHA_TERMICA_2.dkg"));


        Lista.ORDENAR_CRESCENTE(pontos_verao, new Ordenavel<Ponto>() {
            @Override
            public int emOrdem(Ponto a, Ponto b) {
                return Integer.compare(a.getX(), b.getX());
            }
        });

        boolean tem_anterior = false;
        Ponto pt_anterior = null;

        for (Ponto ponto : pontos_verao) {
            render.drawCirculoCentralizado_Pintado(ponto, 5, mCores.getVermelho());

            if (tem_anterior) {
                render.drawLinha(ponto.getX(), ponto.getY(), pt_anterior.getX(), pt_anterior.getY(), mCores.getVermelho());
            }

            tem_anterior = true;
            pt_anterior = ponto;

        }


        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura_2/atzum_temperatura_verao.png"));


        // FASE 2
        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_2.qtt"), mapa_global.getWidth(), mapa_global.getHeight());
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_2.qtt"), mapa_global.getWidth(), mapa_global.getHeight(), -1);

        for (Ponto pt : pontos_verao) {
            QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_2.qtt"), pt.getX(), pt.getY(), 0);
        }

        fmt.print("Guardados : {}", pontos_verao.getQuantidade());


        fmt.print("Calculando proximidade com o faixa limite !");
        Renderizador terra = new Renderizador(AtzumCreator.GET_MAPA());

        for (int y = 0; y < terra.getAltura(); y++) {
            for (int x = 0; x < terra.getLargura(); x++) {
                if (terra.getPixel(x, y).igual(mCores.getAmarelo())) {

                    int proximidade = Espaco2D.GET_DISTANCIA_MAIS_PROXIMA(pontos_verao, x, y);

                    QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_2.qtt"), x, y, proximidade);

                }
            }
        }

        fmt.print("Tudo OK !");

    }

    public static void RENDERIZAR_FAIXAS_DE_TEMPERATURA_T1() {

        fmt.print("Lendo distancias...");
        Renderizador terra = new Renderizador(AtzumCreator.GET_MAPA());

        BufferedImage mapa_global = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_margem_oceanica.png"));
        Cores mCores = new Cores();


        int menor = Integer.MAX_VALUE;
        int maior = Integer.MIN_VALUE;

        for (int y = 0; y < terra.getAltura(); y++) {
            for (int x = 0; x < terra.getLargura(); x++) {
                if (terra.getPixel(x, y).igual(mCores.getAmarelo())) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_1.qtt"), x, y);
                    if (valor > maior) {
                        maior = valor;
                    }

                    if (valor < menor) {
                        menor = valor;
                    }

                }
            }
        }


        fmt.print("Maior Distancia : {}", maior);
        fmt.print("Menor Distancia : {}", menor);

        Lista<Cor> cores_distancia = new Lista<Cor>();

        cores_distancia.adicionar(Cor.getHexCor("#FFEBEE"));
        cores_distancia.adicionar(Cor.getHexCor("#FFCDD2"));
        cores_distancia.adicionar(Cor.getHexCor("#EF9A9A"));
        cores_distancia.adicionar(Cor.getHexCor("#FF8A80"));
        cores_distancia.adicionar(Cor.getHexCor("#E57373"));
        cores_distancia.adicionar(Cor.getHexCor("#FF5252"));

        cores_distancia.adicionar(Cor.getHexCor("#EF5350"));
        cores_distancia.adicionar(Cor.getHexCor("#F44336"));

        cores_distancia.adicionar(Cor.getHexCor("#E53935"));
        cores_distancia.adicionar(Cor.getHexCor("#D32F2F"));
        cores_distancia.adicionar(Cor.getHexCor("#C62828"));
        cores_distancia.adicionar(Cor.getHexCor("#B71C1C"));
        cores_distancia.adicionar(Cor.getHexCor("#D50000"));


        Lista<Cor> cores_distancia_inversa = new Lista<Cor>();

        while (cores_distancia.getQuantidade() > 0) {
            cores_distancia_inversa.adicionar(cores_distancia.get(cores_distancia.getQuantidade() - 1));
            cores_distancia.remover(cores_distancia.get(cores_distancia.getQuantidade() - 1));
        }

        int faixa = (maior - menor) / cores_distancia_inversa.getQuantidade();

        Renderizador render_distancia = Renderizador.construir(mapa_global.getWidth(), mapa_global.getHeight());
        render_distancia.limpar(mCores.getBranco());


        for (int y = 0; y < terra.getAltura(); y++) {
            for (int x = 0; x < terra.getLargura(); x++) {
                if (terra.getPixel(x, y).igual(mCores.getAmarelo())) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_1.qtt"), x, y);
                    int valor_faixa = valor / faixa;
                    if (valor_faixa >= cores_distancia_inversa.getQuantidade()) {
                        valor_faixa = cores_distancia_inversa.getQuantidade() - 1;
                    }
                    render_distancia.setPixel(x, y, cores_distancia_inversa.get(valor_faixa));


                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura_1/atzum_temperatura_verao_v2.png"));

        fmt.print("Tudo OK !");

    }

    public static void RENDERIZAR_FAIXAS_DE_TEMPERATURA_T2() {

        fmt.print("Lendo distancias...");
        Renderizador terra = new Renderizador(AtzumCreator.GET_MAPA());

        BufferedImage mapa_global = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_margem_oceanica.png"));
        Cores mCores = new Cores();


        int menor = Integer.MAX_VALUE;
        int maior = Integer.MIN_VALUE;

        for (int y = 0; y < terra.getAltura(); y++) {
            for (int x = 0; x < terra.getLargura(); x++) {
                if (terra.getPixel(x, y).igual(mCores.getAmarelo())) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_2.qtt"), x, y);
                    if (valor > maior) {
                        maior = valor;
                    }

                    if (valor < menor) {
                        menor = valor;
                    }

                }
            }
        }


        fmt.print("Maior Distancia : {}", maior);
        fmt.print("Menor Distancia : {}", menor);

        Lista<Cor> cores_distancia = new Lista<Cor>();

        cores_distancia.adicionar(Cor.getHexCor("#FFEBEE"));
        cores_distancia.adicionar(Cor.getHexCor("#FFCDD2"));
        cores_distancia.adicionar(Cor.getHexCor("#EF9A9A"));
        cores_distancia.adicionar(Cor.getHexCor("#FF8A80"));
        cores_distancia.adicionar(Cor.getHexCor("#E57373"));
        cores_distancia.adicionar(Cor.getHexCor("#FF5252"));

        cores_distancia.adicionar(Cor.getHexCor("#EF5350"));
        cores_distancia.adicionar(Cor.getHexCor("#F44336"));

        cores_distancia.adicionar(Cor.getHexCor("#E53935"));
        cores_distancia.adicionar(Cor.getHexCor("#D32F2F"));
        cores_distancia.adicionar(Cor.getHexCor("#C62828"));
        cores_distancia.adicionar(Cor.getHexCor("#B71C1C"));
        cores_distancia.adicionar(Cor.getHexCor("#D50000"));


        Lista<Cor> cores_distancia_inversa = new Lista<Cor>();

        while (cores_distancia.getQuantidade() > 0) {
            cores_distancia_inversa.adicionar(cores_distancia.get(cores_distancia.getQuantidade() - 1));
            cores_distancia.remover(cores_distancia.get(cores_distancia.getQuantidade() - 1));
        }

        int faixa = (maior - menor) / cores_distancia_inversa.getQuantidade();

        Renderizador render_distancia = Renderizador.construir(mapa_global.getWidth(), mapa_global.getHeight());
        render_distancia.limpar(mCores.getBranco());


        for (int y = 0; y < terra.getAltura(); y++) {
            for (int x = 0; x < terra.getLargura(); x++) {
                if (terra.getPixel(x, y).igual(mCores.getAmarelo())) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_2.qtt"), x, y);
                    int valor_faixa = valor / faixa;
                    if (valor_faixa >= cores_distancia_inversa.getQuantidade()) {
                        valor_faixa = cores_distancia_inversa.getQuantidade() - 1;
                    }
                    render_distancia.setPixel(x, y, cores_distancia_inversa.get(valor_faixa));


                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura_2/atzum_temperatura_verao_v2.png"));

        fmt.print("Tudo OK !");

    }


    public static void INIT() {

        AtzumCriativoLog.iniciar(SERVICO_NOME + ".INIT");


        PRE(1);
        PRE(2);

        PUBLICAR_TEMPERATURAS(1);
        PUBLICAR_TEMPERATURAS(2);

        RENDERIZAR_PUBLICADO(1);
        RENDERIZAR_PUBLICADO(2);

        //  INIT_V2("1");
        //   INIT_V2("2");

        //  RENDERIZAR("1");
        //  RENDERIZAR("2");

        AtzumCriativoLog.terminar(SERVICO_NOME + ".INIT");
        AtzumCriativoLog.exibir_item(SERVICO_NOME + ".INIT");

    }

    public static void PRE(int valor_pre) {

        String modelagem = String.valueOf(valor_pre);


        Cor COR_MUITO_QUENTE = Cor.getHexCor("#BF360C");
        Cor COR_QUENTE = Cor.getHexCor("#FB8C00");
        Cor COR_FRIO = Cor.getHexCor("#1976D2");
        Cor COR_MUITO_FRIO = Cor.getHexCor("#0D47A1");
        Cor COR_NORMAL = Cor.getHexCor("#8BC34A");


        AtzumTerra mapa_planeta = new AtzumTerra();

        Cores mCores = new Cores();
        Renderizador render_zonas = new Renderizador(Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_contorno_oceanico.png")));

        Rasterizador.trocar_cores(render_zonas, mCores.getVermelho(), mCores.getBranco());

        Lista<Par<Ponto, Integer>> zonas = new Lista<Par<Ponto, Integer>>();

        if (valor_pre == 1) {
            Lista<Par<Ponto, Integer>> zona_t1_q1 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q1.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q2 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q2.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q3 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q3.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q4 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q4.dkg"));
            //  Lista<Par<Ponto, Integer>> zona_t1_q5 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q5.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q6 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q6.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q7 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q7.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q8 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q7.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q9 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q9.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q10 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q10.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q11 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T1_Q11.dkg"));


            ServicoUmidade.MARCAR_ZONA(zona_t1_q1, COR_FRIO, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q2, COR_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q3, COR_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q4, COR_FRIO, render_zonas);
            //   ServicoUmidade.MARCAR_ZONA(zona_t1_q5,COR_MUITO_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q6, COR_MUITO_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q7, COR_MUITO_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q8, COR_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q9, COR_MUITO_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q10, COR_MUITO_FRIO, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q11, COR_NORMAL, render_zonas);


            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q1, -1);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q2, 2);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q3, 2);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q4, -1);
            // AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q5, 3);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q6, 3);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q7, 3);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q8, 2);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q9, 3);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q10, -2);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q11, 0);

            zonas.adicionar_varios(zona_t1_q1);
            zonas.adicionar_varios(zona_t1_q2);
            zonas.adicionar_varios(zona_t1_q3);
            zonas.adicionar_varios(zona_t1_q4);
            //  zonas.adicionar_varios(zona_t1_q5);
            zonas.adicionar_varios(zona_t1_q6);
            zonas.adicionar_varios(zona_t1_q7);
            zonas.adicionar_varios(zona_t1_q8);
            zonas.adicionar_varios(zona_t1_q9);
            zonas.adicionar_varios(zona_t1_q10);
            zonas.adicionar_varios(zona_t1_q11);


        } else if (valor_pre == 2) {

            Lista<Par<Ponto, Integer>> zona_t1_q1 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T2_Q1.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q2 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T2_Q2.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q3 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T2_Q3.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q4 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T2_Q4.dkg"));
            Lista<Par<Ponto, Integer>> zona_t1_q6 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T2_Q6.dkg"));
            Lista<Par<Ponto, Integer>> zona_t2_q7 = AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/T2_Q7.dkg"));


            ServicoUmidade.MARCAR_ZONA(zona_t1_q1, COR_FRIO, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q2, COR_FRIO, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q3, COR_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q4, COR_MUITO_QUENTE, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t1_q6, COR_MUITO_FRIO, render_zonas);
            ServicoUmidade.MARCAR_ZONA(zona_t2_q7, COR_MUITO_FRIO, render_zonas);


            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q1, -1);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q2, -1);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q3, 2);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q4, 3);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t1_q6, -2);
            AtzumPontosInteiro.TRANSFORMAR_VALOR(zona_t2_q7, -2);

            zonas.adicionar_varios(zona_t1_q1);
            zonas.adicionar_varios(zona_t1_q2);
            zonas.adicionar_varios(zona_t1_q3);
            zonas.adicionar_varios(zona_t1_q4);
            zonas.adicionar_varios(zona_t1_q6);
            zonas.adicionar_varios(zona_t2_q7);

        }

        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura"));

        Imagem.exportar(render_zonas.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_" + modelagem + "_v1.png"));


        Renderizador render_temperatura = Renderizador.CONSTRUIR(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());

        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor_proximo = Espaco2D.GET_VALOR_DA_DISTANCIA_MAIS_PROXIMA(zonas, x, y);

                    if (valor_proximo == 2) {
                        render_temperatura.setPixel(x, y, COR_QUENTE);
                    } else if (valor_proximo == 3) {
                        render_temperatura.setPixel(x, y, COR_MUITO_QUENTE);
                    } else if (valor_proximo == -1) {
                        render_temperatura.setPixel(x, y, COR_FRIO);
                    } else if (valor_proximo == -2) {
                        render_temperatura.setPixel(x, y, COR_MUITO_FRIO);
                    } else if (valor_proximo == 0) {
                        render_temperatura.setPixel(x, y, COR_NORMAL);
                    }

                }
            }
        }

        Imagem.exportar(render_temperatura.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_" + modelagem + "_v2.png"));

    }

    public static void PUBLICAR_TEMPERATURAS(int valor_pre) {


        QTT dados_relevo = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("relevo.qtt"));

        AtzumTerra mapa_planeta = new AtzumTerra();

        String modelagem = String.valueOf(valor_pre);

        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"), mapa_planeta.getLargura(), mapa_planeta.getAltura());
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"), mapa_planeta.getLargura(), mapa_planeta.getAltura(), -1);

        Cor COR_MUITO_QUENTE = Cor.getHexCor("#BF360C");
        Cor COR_QUENTE = Cor.getHexCor("#FB8C00");
        Cor COR_FRIO = Cor.getHexCor("#1976D2");
        Cor COR_MUITO_FRIO = Cor.getHexCor("#0D47A1");
        Cor COR_NORMAL = Cor.getHexCor("#8BC34A");

        Lista<IntervaloDeValorColorido> FAIXAS_DE_TEMPERATURA = new Lista<IntervaloDeValorColorido>();

        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(36, 45, COR_MUITO_QUENTE));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(30, 35, COR_QUENTE));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(15, 25, COR_NORMAL));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(-15, 10, COR_FRIO));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(-30, -15, COR_MUITO_FRIO));

        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura"));

        Renderizador temperatura_zonas = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_" + modelagem + "_v2.png"));


        Cores mCores = new Cores();
        Renderizador render_publicar = Renderizador.CONSTRUIR(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());

        for (IntervaloDeValorColorido temperatura : FAIXAS_DE_TEMPERATURA) {

            Lista<Ponto> pontos_de_temperatura = new Lista<Ponto>();

            for (int y = 0; y < mapa_planeta.getAltura(); y++) {
                for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                    if (mapa_planeta.isTerra(x, y)) {

                        if (temperatura_zonas.getPixel(x, y).igual(temperatura.getCor())) {
                            pontos_de_temperatura.adicionar(new Ponto(x, y));
                        }


                    }
                }
            }

            fmt.print("\t ++ Temperatura {} -->> {}", temperatura.getCor().toString(), pontos_de_temperatura.getQuantidade());


            if (pontos_de_temperatura.getQuantidade() > 0) {

                Lista<Par<Ponto, Integer>> eixos = new Lista<Par<Ponto, Integer>>();

                for (int escolher = 1; escolher <= 100; escolher++) {
                    Ponto escolhido = Aleatorio.escolha_um(pontos_de_temperatura);

                    render_publicar.drawCirculoCentralizado_Pintado(escolhido, 10, temperatura.getCor());

                    eixos.adicionar(new Par<Ponto, Integer>(escolhido, temperatura.getTemperaturaAleatoria()));
                }

                for (Ponto ponto : pontos_de_temperatura) {
                    int valor_proximo = Espaco2D.GET_VALOR_DA_DISTANCIA_MAIS_PROXIMA(eixos, ponto.getX(), ponto.getY());

                    int altitude = dados_relevo.getValor(ponto.getX(), ponto.getY());

                    if (altitude > 0) {

                        while (altitude > 100) {
                            altitude -= 100;
                            valor_proximo -= 1;
                        }

                    } else if (altitude < 0) {

                        while (altitude < -100) {
                            altitude += 100;
                            valor_proximo += 1;
                        }

                    }


                    QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"), ponto.getX(), ponto.getY(), valor_proximo);
                }

            }


            Imagem.exportar(render_publicar.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_" + modelagem + "_v3.png"));


        }


    }


    public static void INIT_V2(String modelagem) {

        BufferedImage mapa_global = AtzumCreator.GET_MAPA();
        Renderizador render = new Renderizador(mapa_global);

        Cores mCores = new Cores();

        Rasterizador.trocar_cores(render, mCores.getPreto(), mCores.getBranco());


        Fonte ESCRITOR_NORMAL = new FonteRunTime(mCores.getVerde(), 30);
        ESCRITOR_NORMAL.setRenderizador(render);

        Fonte ESCRITOR_NORMAL_AZUL = new FonteRunTime(mCores.getAzul(), 30);
        ESCRITOR_NORMAL_AZUL.setRenderizador(render);

        Fonte ESCRITOR_NORMAL_VERMELHO = new FonteRunTime(mCores.getVermelho(), 30);
        ESCRITOR_NORMAL_VERMELHO.setRenderizador(render);


        Lista<Par<Ponto, Integer>> ponto_frio_superior = new Lista<Par<Ponto, Integer>>();
        Lista<Par<Ponto, Integer>> ponto_quente = new Lista<Par<Ponto, Integer>>();
        Lista<Par<Ponto, Integer>> ponto_frio_inferior = new Lista<Par<Ponto, Integer>>();

        for (Par<Ponto, Integer> p : AtzumPontosInteiro.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/temperatura/LINHA_TERMICA_" + modelagem + ".dkg"))) {
            if (p.getValor() == 0) {
                ponto_frio_superior.adicionar(p);
            } else if (p.getValor() == 50) {
                ponto_frio_inferior.adicionar(p);
            } else if (p.getValor() == 100) {
                ponto_quente.adicionar(p);
            }
        }

        for (Par<Ponto, Integer> pt : ponto_frio_superior) {
            pt.setValor(0);
        }
        for (Par<Ponto, Integer> pt : ponto_frio_inferior) {
            pt.setValor(0);
        }


        Lista.ORDENAR_CRESCENTE(ponto_frio_superior, new Ordenavel<Par<Ponto, Integer>>() {
            @Override
            public int emOrdem(Par<Ponto, Integer> a, Par<Ponto, Integer> b) {
                return Integer.compare(a.getChave().getX(), b.getChave().getX());
            }
        });

        Lista.ORDENAR_CRESCENTE(ponto_quente, new Ordenavel<Par<Ponto, Integer>>() {
            @Override
            public int emOrdem(Par<Ponto, Integer> a, Par<Ponto, Integer> b) {
                return Integer.compare(a.getChave().getX(), b.getChave().getX());
            }
        });


        Lista.ORDENAR_CRESCENTE(ponto_frio_inferior, new Ordenavel<Par<Ponto, Integer>>() {
            @Override
            public int emOrdem(Par<Ponto, Integer> a, Par<Ponto, Integer> b) {
                return Integer.compare(a.getChave().getX(), b.getChave().getX());
            }
        });


        Lista<Par<Ponto, Integer>> medios_acima = new Lista<Par<Ponto, Integer>>();
        for (Par<Ponto, Integer> pt : ponto_quente) {

            Opcional<Ponto> frio_proximo = Espaco2D.GET_MAIS_PROXIMO(pt.getChave(), Espaco2D.SO_PONTOS(ponto_frio_superior));

            if (frio_proximo.isOK()) {

                int diff_y = (pt.getChave().getY() - frio_proximo.get().getY()) / 2;

                Ponto quente_proximo = new Ponto(pt.getChave().getX(), pt.getChave().getY() - diff_y);


                medios_acima.adicionar(new Par<>(quente_proximo, 50));
            }
        }

        Lista<Par<Ponto, Integer>> medios_abaixo = new Lista<Par<Ponto, Integer>>();
        for (Par<Ponto, Integer> pt : ponto_quente) {

            Opcional<Ponto> frio_proximo = Espaco2D.GET_MAIS_PROXIMO(pt.getChave(), Espaco2D.SO_PONTOS(ponto_frio_inferior));

            if (frio_proximo.isOK()) {

                int diff_y = (frio_proximo.get().getY() - pt.getChave().getY()) / 2;

                Ponto quente_proximo = new Ponto(pt.getChave().getX(), pt.getChave().getY() + diff_y);


                medios_abaixo.adicionar(new Par<>(quente_proximo, 50));
            }

        }

        MAPEAR_CURVA_TERMICA(render, ponto_frio_superior, mCores.getAzul());
        MAPEAR_CURVA_TERMICA(render, ponto_quente, mCores.getVermelho());
        MAPEAR_CURVA_TERMICA(render, ponto_frio_inferior, mCores.getAzul());

        MAPEAR_CURVA_TERMICA(render, medios_acima, mCores.getLaranja());
        MAPEAR_CURVA_TERMICA(render, medios_abaixo, mCores.getLaranja());


        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_" + modelagem + ".png"));


        String arquivo_dados = "temperatura_" + modelagem + ".qtt";


        // FASE 2
        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO(arquivo_dados), mapa_global.getWidth(), mapa_global.getHeight());
        QTT.alterar_todos(AtzumCreator.DADOS_GET_ARQUIVO(arquivo_dados), mapa_global.getWidth(), mapa_global.getHeight(), -1);


        fmt.print("Guardados : {}", ponto_frio_superior.getQuantidade());

        Lista<Par<Ponto, Integer>> todos_pontos = new Lista<Par<Ponto, Integer>>();
        todos_pontos.adicionar_varios(ponto_frio_superior);
        todos_pontos.adicionar_varios(ponto_quente);
        todos_pontos.adicionar_varios(ponto_frio_inferior);
        todos_pontos.adicionar_varios(medios_abaixo);
        todos_pontos.adicionar_varios(medios_acima);


        fmt.print("Calculando proximidade com o faixa limite !");
        Renderizador terra = new Renderizador(AtzumCreator.GET_MAPA());

        for (int y = 0; y < terra.getAltura(); y++) {
            for (int x = 0; x < terra.getLargura(); x++) {
                if (terra.getPixel(x, y).igual(mCores.getAmarelo())) {

                    int valor = Espaco2D.GET_VALOR_DA_DISTANCIA_MAIS_PROXIMA(todos_pontos, x, y);
                    int r_valor = -1;

                    if (valor == 0) {
                        r_valor = 0;
                    } else if (valor == 50) {
                        r_valor = 5;
                    } else if (valor == 100) {
                        r_valor = 10;
                    }

                    QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO(arquivo_dados), x, y, r_valor);

                }
            }
        }

        fmt.print("Tudo OK !");

    }

    public static void MAPEAR_CURVA_TERMICA(Renderizador render, Lista<Par<Ponto, Integer>> pontos, Cor eCor) {

        boolean tem_anterior = false;
        Ponto pt_anterior = null;

        for (Par<Ponto, Integer> ponto : pontos) {
            render.drawCirculoCentralizado_Pintado(ponto.getChave(), 5, eCor);

            if (tem_anterior) {
                render.drawLinha(ponto.getChave().getX(), ponto.getChave().getY(), pt_anterior.getX(), pt_anterior.getY(), eCor);
            }

            tem_anterior = true;
            pt_anterior = ponto.getChave();

        }

    }

    public static void RENDERIZAR(String modelagem) {

        fmt.print("Lendo distancias...");
        AtzumTerra mapa_planeta = new AtzumTerra();

        BufferedImage mapa_global = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_margem_oceanica.png"));
        Cores mCores = new Cores();

        String arquivo_dados = "temperatura_" + modelagem + ".qtt";
        Extremos<Integer> ex_temperatura = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());


        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO(arquivo_dados), x, y);
                    ex_temperatura.set(valor);

                }
            }
        }


        fmt.print("Maior Temperatura : {}", ex_temperatura.getMaior());
        fmt.print("Menor Temperatura : {}", ex_temperatura.getMenor());

        Lista<Cor> cores_distancia = new Lista<Cor>();

        cores_distancia.adicionar(Cor.getHexCor("#FFEBEE"));
        cores_distancia.adicionar(Cor.getHexCor("#FFCDD2"));
        cores_distancia.adicionar(Cor.getHexCor("#EF9A9A"));
        cores_distancia.adicionar(Cor.getHexCor("#FF8A80"));
        cores_distancia.adicionar(Cor.getHexCor("#E57373"));
        cores_distancia.adicionar(Cor.getHexCor("#FF5252"));

        cores_distancia.adicionar(Cor.getHexCor("#EF5350"));
        cores_distancia.adicionar(Cor.getHexCor("#F44336"));

        cores_distancia.adicionar(Cor.getHexCor("#E53935"));
        cores_distancia.adicionar(Cor.getHexCor("#D32F2F"));
        cores_distancia.adicionar(Cor.getHexCor("#C62828"));
        cores_distancia.adicionar(Cor.getHexCor("#B71C1C"));
        cores_distancia.adicionar(Cor.getHexCor("#D50000"));


        int faixa = (ex_temperatura.getMaior() - ex_temperatura.getMenor()) / cores_distancia.getQuantidade();

        Renderizador render_distancia = Renderizador.construir(mapa_global.getWidth(), mapa_global.getHeight());
        render_distancia.limpar(mCores.getBranco());


        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO(arquivo_dados), x, y);

                    render_distancia.setPixel(x, y, cores_distancia.get(valor));


                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_processada_" + modelagem + ".png"));

        fmt.print("Tudo OK !");

    }


    public static void RENDERIZAR_PUBLICADO(int valor_pre) {

        String modelagem = String.valueOf(valor_pre);

        fmt.print("Lendo Temperatura...");
        AtzumTerra mapa_planeta = new AtzumTerra();

        Cores mCores = new Cores();

        Extremos<Integer> ex_temperatura = new Extremos<Integer>(Inteiro.GET_ORDENAVEL());


        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"), x, y);
                    ex_temperatura.set(valor);

                }
            }
        }


        fmt.print("Maior Temperatura : {}", ex_temperatura.getMaior());
        fmt.print("Menor Temperatura : {}", ex_temperatura.getMenor());


        double faixa = 100.0 / (ex_temperatura.getMaior() - ex_temperatura.getMenor());

        Renderizador render_distancia = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());
        Renderizador render_temperatura_zonas = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());

        Cor COR_MUITO_QUENTE = Cor.getHexCor("#BF360C");
        Cor COR_QUENTE = Cor.getHexCor("#FB8C00");
        Cor COR_FRIO = Cor.getHexCor("#1976D2");
        Cor COR_MUITO_FRIO = Cor.getHexCor("#0D47A1");
        Cor COR_NORMAL = Cor.getHexCor("#8BC34A");

        Lista<IntervaloDeValorColorido> FAIXAS_DE_TEMPERATURA = new Lista<IntervaloDeValorColorido>();

        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(36, 45, COR_MUITO_QUENTE));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(30, 35, COR_QUENTE));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(10, 30, COR_NORMAL));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(-15, 10, COR_FRIO));
        FAIXAS_DE_TEMPERATURA.adicionar(new IntervaloDeValorColorido(-30, -15, COR_MUITO_FRIO));


        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (mapa_planeta.isTerra(x, y)) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("temperatura_t" + modelagem + ".qtt"), x, y);

                    render_distancia.setPixel(x, y, new HSV(350, 100, HSV.INVERSO((int) (valor * faixa))));

                    Cor cor_temp = mCores.getBranco();

                    for (IntervaloDeValorColorido temp_zona : FAIXAS_DE_TEMPERATURA) {
                        if (valor >= temp_zona.getMinimo() && valor <= temp_zona.getMaximo()) {
                            cor_temp = temp_zona.getCor();
                            break;
                        }
                    }

                    if (valor > FAIXAS_DE_TEMPERATURA.get(0).getMaximo()) {
                        cor_temp = FAIXAS_DE_TEMPERATURA.get(0).getCor();
                    }

                    if (valor < FAIXAS_DE_TEMPERATURA.get(FAIXAS_DE_TEMPERATURA.getQuantidade() - 1).getMinimo()) {
                        cor_temp = FAIXAS_DE_TEMPERATURA.get(FAIXAS_DE_TEMPERATURA.getQuantidade() - 1).getCor();
                    }

                    render_temperatura_zonas.setPixel(x, y, cor_temp);


                }
            }
        }


        Imagem.exportar(render_distancia.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_" + modelagem + "_v4.png"));
        Imagem.exportar(render_temperatura_zonas.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_" + modelagem + "_v5.png"));

        fmt.print("Tudo OK !");

    }


}
