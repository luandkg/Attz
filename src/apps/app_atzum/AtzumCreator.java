package apps.app_atzum;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_atzum.utils.AtzumCidades;
import apps.app_atzum.utils.Rasterizador;
import apps.app_atzum.utils.RegiaoDefinida;
import libs.arquivos.IM;
import libs.arquivos.binario.Arquivador;
import libs.arquivos.ds.DS;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.azzal.utilitarios.HSV;
import libs.fs.PastaFS;
import libs.imagem.Imagem;
import libs.luan.FS;
import libs.luan.Lista;

import java.awt.image.BufferedImage;

public class AtzumCreator {


    public static final String LOCAL_PROCESSANDO = Atzum.GET_LOCAL() + "build/processando/";
    public static final String LOCAL_DADOS = Atzum.GET_LOCAL() + "dados/";
    public static final String LOCAL_VIDEOS = Atzum.GET_LOCAL() + "videos/";
    public static final String LOCAL_LOGS = Atzum.GET_LOCAL() + "logs/";

    public static void ORGANIZAR(){
        FS.organizar_pasta(LOCAL_PROCESSANDO);
        FS.organizar_pasta(LOCAL_DADOS);
        FS.organizar_pasta(LOCAL_VIDEOS);
        FS.organizar_pasta(LOCAL_LOGS);
    }

    public static String LOCAL_GET_ARQUIVO(String nome) {
        return new PastaFS(Atzum.GET_LOCAL()).getArquivo(nome);
    }

    public static String PROCESSANDO_GET_ARQUIVO(String nome) {
        return new PastaFS(LOCAL_PROCESSANDO).getArquivo(nome);
    }

    public static String VIDEO_GET_ARQUIVO(String nome) {
        return new PastaFS(LOCAL_VIDEOS).getArquivo(nome);
    }

    public static String DADOS_GET_ARQUIVO(String nome) {
        return new PastaFS(LOCAL_DADOS).getArquivo(nome);
    }

    public static String LOGS_GET_ARQUIVO(String nome) {
        return new PastaFS(LOCAL_LOGS).getArquivo(nome);
    }

    public static BufferedImage GET_MAPA() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_terra.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_TERRA() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_terra.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_DE_CONTORNO() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes_contornos.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_DE_CONTORNO_REGIOES() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/regioes/regioes_v3.png"));
        return mapa;
    }


    public static BufferedImage GET_MAPA_RELEVO_TERRA() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("relevo_terra.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_DE_RELEVO() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_relevo.png"));
        return mapa;
    }


    public static BufferedImage GET_MAPA_DE_TEMPERATURA_T1() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_1_v5.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_DE_TEMPERATURA_T2() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/temperatura/atzum_temperatura_2_v5.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_DE_REGIOES() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_DE_OCEANOS() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_oceanos.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_CLIMATICO() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_modelo_climatico.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_VEGETACAO() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_modelo_vegetacao.png"));
        return mapa;
    }

    public static BufferedImage GET_MAPA_MARGEM_OCEANICA() {
        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_organizado.png"));
        return mapa;
    }


    public static BufferedImage GET_MAPA_PRETO_E_BRANCO() {

        Cores mCores = new Cores();

        BufferedImage mapa = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_terra.png"));
        Renderizador render_mapa_pronto = new Renderizador(mapa);
        Rasterizador.trocar_cores(render_mapa_pronto, mCores.getAmarelo(), mCores.getBranco());

        return render_mapa_pronto.toImagemSemAlfa();
    }

    public static void NORMALIZAR_2_CORES_ABAIXO_DE(Renderizador render, int valor_referencia, Cor cor_especial, Cor cor_fundo) {

        int largura = render.getLargura();
        int altura = render.getAltura();

        for (int y = 0; y < altura; y++) {

            for (int x = 0; x < largura; x++) {

                Cor cor = render.getPixel(x, y);

                if (cor.getRed() < valor_referencia && cor.getGreen() < valor_referencia && cor.getBlue() < valor_referencia) {
                    render.setPixel(x, y, cor_especial);
                } else {
                    render.setPixel(x, y, cor_fundo);
                }


            }
        }

    }




    public static Lista<RegiaoDefinida> GET_REGIOES() {


        Lista<RegiaoDefinida> regioes = new Lista<RegiaoDefinida>();


        Cor COR_ALFA = Cor.getHexCor("#FFC107"); // AMARELO
        Cor COR_BETA = Cor.getHexCor("#1E88E5"); // AZUL
        Cor COR_GAMA = Cor.getHexCor("#E65100"); // LARANJA
        Cor COR_DELTA = Cor.getHexCor("#D81B60"); // ROSA
        Cor COR_EPSILON = Cor.getHexCor("#283593"); // AZUL ESCURO
        Cor COR_OMEGA = Cor.getHexCor("#00796B"); // AZUL ESCURO
        Cor COR_LAMBDA = Cor.getHexCor("#78909C"); // CINZA
        Cor COR_OMICRON = Cor.getHexCor("#8D6E63"); // MARROM
        Cor COR_ZETA = Cor.getHexCor("#D32F2F"); // VERMELHO
        Cor COR_PI = Cor.getHexCor("#689F38"); // VERDE


        // REGIAO 1
        // Rasterizador.RASTERIZAR_COM(render, 548, 1063, mCores.getPreto(), mCores.getAmarelo(), durante_mudanca, a_cada_100);
        //   Rasterizador.RASTERIZAR_COM(render, 402, 1095, mCores.getPreto(), mCores.getAmarelo(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(548, 1063, COR_ALFA, 1, 10));
        regioes.adicionar(new RegiaoDefinida(402, 1095, COR_ALFA, 1, 11));


        // REGIAO 2
        // Rasterizador.RASTERIZAR_COM(render, 578, 767, mCores.getPreto(), mCores.getAzul(), durante_mudanca, a_cada_100);
        // Rasterizador.RASTERIZAR_COM(render, 507, 507, mCores.getPreto(), mCores.getAzul(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(578, 767, COR_BETA, 2, 20));
        regioes.adicionar(new RegiaoDefinida(507, 507, COR_BETA, 2, 21));


        // REGIAO 3
        //  Rasterizador.RASTERIZAR_COM(render, 864, 486, mCores.getPreto(), mCores.getLaranja(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(864, 486, COR_GAMA, 3, 30));

        // REGIAO 4
        // Rasterizador.RASTERIZAR_COM(render, 1410, 1209, mCores.getPreto(), mCores.getRosa(), durante_mudanca, a_cada_100);
        // Rasterizador.RASTERIZAR_COM(render, 1173, 1176, mCores.getPreto(), mCores.getRosa(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(1410, 1209, COR_DELTA, 4, 40));
        regioes.adicionar(new RegiaoDefinida(1173, 1176, COR_DELTA, 4, 41));


        // REGIAO 5
        //   Rasterizador.RASTERIZAR_COM(render, 1801, 1230, mCores.getPreto(), mCores.getBranco(), durante_mudanca, a_cada_100);
        // Rasterizador.RASTERIZAR_COM(render, 1906, 1276, mCores.getPreto(), mCores.getBranco(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(1801, 1230, COR_EPSILON, 5, 50));
        regioes.adicionar(new RegiaoDefinida(1906, 1276, COR_EPSILON, 5, 51));

        // REGIAO 6
        // Rasterizador.RASTERIZAR_COM(render, 1984, 1191, mCores.getPreto(), mCores.getTurquesa(), durante_mudanca, a_cada_100);
        // Rasterizador.RASTERIZAR_COM(render, 1895, 1125, mCores.getPreto(), mCores.getTurquesa(), durante_mudanca, a_cada_100);
        //   Rasterizador.RASTERIZAR_COM(render, 1929, 1048, mCores.getPreto(), mCores.getTurquesa(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(1984, 1191, COR_OMEGA, 6, 60));
        regioes.adicionar(new RegiaoDefinida(1895, 1125, COR_OMEGA, 6, 61));
        regioes.adicionar(new RegiaoDefinida(1929, 1048, COR_OMEGA, 6, 62));

        // REGIAO 7
        //  Rasterizador.RASTERIZAR_COM(render, 1841, 1097, mCores.getPreto(), mCores.getCinza(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(1841, 1097, COR_LAMBDA, 7, 70));


        // REGIAO 8
        //  Rasterizador.RASTERIZAR_COM(render, 1733, 1066, mCores.getPreto(), mCores.getMarrom(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(1733, 1066, COR_OMICRON, 8, 80));

        // REGIAO 9
        // Rasterizador.RASTERIZAR_COM(render, 1798, 1128, mCores.getPreto(), mCores.getCianeto(), durante_mudanca, a_cada_100);
        regioes.adicionar(new RegiaoDefinida(1798, 1128, COR_ZETA, 9, 90));

        // REGIAO 10
        // Rasterizador.RASTERIZAR_COM(render, 900, 900, mCores.getPreto(), mCores.getVerde(), durante_mudanca, a_cada_100);
        // Rasterizador.RASTERIZAR_COM(render, 1003, 1198, mCores.getPreto(), mCores.getVerde(), durante_mudanca, a_cada_100);
        // Rasterizador.RASTERIZAR_COM(render, 1014, 1124, mCores.getPreto(), mCores.getVerde(), durante_mudanca, a_cada_100);
        //  Rasterizador.RASTERIZAR_COM(render, 1060, 1070, mCores.getPreto(), mCores.getVerde(), durante_mudanca, a_cada_100);

        regioes.adicionar(new RegiaoDefinida(900, 900, COR_PI, 10, 100));
        regioes.adicionar(new RegiaoDefinida(1003, 1198, COR_PI, 10, 101));
        regioes.adicionar(new RegiaoDefinida(1014, 1124, COR_PI, 10, 102));
        regioes.adicionar(new RegiaoDefinida(1060, 1070, COR_PI, 10, 103));

        return regioes;
    }


    public static Renderizador GET_RENDER_PRETO_E_BRANCO() {
        Cores mCores = new Cores();
        Renderizador renderizador = new Renderizador(AtzumCreator.GET_MAPA());
        Rasterizador.trocar_cores(renderizador, mCores.getPreto(), mCores.getBranco());
        Rasterizador.trocar_cores(renderizador, mCores.getAmarelo(), mCores.getPreto());

        return renderizador;
    }

    public static Renderizador GET_RENDER_FUNDO_PRETO() {
        Cores mCores = new Cores();
        Renderizador renderizador = new Renderizador(AtzumCreator.GET_MAPA());
        Rasterizador.trocar_cores(renderizador, mCores.getBranco(), mCores.getPreto());
        Rasterizador.trocar_cores(renderizador, mCores.getAmarelo(), mCores.getBranco());

        return renderizador;
    }

    public static Renderizador GET_RENDER_FUNDO_PRETO_MARGEM_OCEANICA() {
        Cores mCores = new Cores();
        Renderizador renderizador = new Renderizador(AtzumCreator.GET_MAPA_MARGEM_OCEANICA());
        Rasterizador.trocar_cores(renderizador, mCores.getBranco(), mCores.getPreto());
        Rasterizador.trocar_cores(renderizador, mCores.getVermelho(), mCores.getBranco());

        return renderizador;
    }

    public static void TERRA_DRAW_PONTOS(Renderizador render, Lista<Ponto> pontos, HSV eCor) {

        AtzumTerra planeta = new AtzumTerra();

        for (Ponto pt : pontos) {
            if (planeta.isTerra(pt.getX(), pt.getY())) {
                render.setPixel(pt.getX(), pt.getY(), eCor);
            }
        }

    }

    public static void TERRA_DRAW_QUANDO_DISTANCIA_PROXIMA_MENOR(Renderizador render, Lista<Ponto> pontos, int distancia_maxima, HSV eCor) {

        AtzumTerra planeta = new AtzumTerra();

        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {
                if (planeta.isTerra(x, y)) {

                    int proximidade = Espaco2D.GET_DISTANCIA_MAIS_PROXIMA(pontos, x, y);

                    if (proximidade < distancia_maxima) {
                        render.setPixel(x, y, eCor);
                    }

                }
            }
        }

    }


    public static Lista<Ponto> TERRA_GET_PONTOS_DA_AREA_QUANDO_AO_REDOR(Renderizador render, Cor eCorDaArea, Cor eCorProxima) {

        AtzumTerra planeta = new AtzumTerra();

        Lista<Ponto> transicao_1 = new Lista<Ponto>();

        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {
                if (render.getPixel(x, y).igual(eCorDaArea)) {

                    boolean marcar = Rasterizador.TEM_AO_REDOR(render, x, y, eCorProxima);

                    if (marcar) {
                        transicao_1.adicionar(new Ponto(x, y));
                    }

                }
            }
        }

        return transicao_1;
    }


    public static void MARCAR_ZONA(String nome, Cor zona_cor, Renderizador render) {

        Lista<Ponto> pontos_da_zona = AtzumCidades.ABRIR_LOCAIS(AtzumCreator.LOCAL_GET_ARQUIVO(nome));


        boolean tem_anterior = false;
        Ponto pt_anterior = null;

        for (Ponto ponto : pontos_da_zona) {
            render.drawCirculoCentralizado_Pintado(ponto, 5, zona_cor);

            if (tem_anterior) {
                render.drawLinha(ponto.getX(), ponto.getY(), pt_anterior.getX(), pt_anterior.getY(), zona_cor);
            }

            tem_anterior = true;
            pt_anterior = ponto;

        }

    }

    public static void MARCAR_ZONA_DELIMITADA(Lista<Ponto> zona_delimitada, Cor zona_cor, Renderizador render) {


        boolean tem_anterior = false;
        Ponto pt_anterior = null;

        for (Ponto ponto : zona_delimitada) {
            render.drawCirculoCentralizado_Pintado(ponto, 5, zona_cor);

            if (tem_anterior) {
                render.drawLinha(ponto.getX(), ponto.getY(), pt_anterior.getX(), pt_anterior.getY(), zona_cor);
            }

            tem_anterior = true;
            pt_anterior = ponto;

        }

    }

    public static void MARCAR_ZONA_DELIMITADA_SEM_LINHA(Lista<Ponto> zona_delimitada, Cor zona_cor, Renderizador render) {


        boolean tem_anterior = false;
        Ponto pt_anterior = null;

        for (Ponto ponto : zona_delimitada) {
            render.drawCirculoCentralizado_Pintado(ponto, 5, zona_cor);

            if (tem_anterior) {
                // render.drawLinha(ponto.getX(),ponto.getY(),pt_anterior.getX(),pt_anterior.getY(),zona_cor);
            }

            tem_anterior = true;
            pt_anterior = ponto;

        }

    }


    public static void PREENCHER_TERRA(AtzumTerra planeta, Renderizador render, int px, int py, int largura, int altura, Cor eCor) {


        for (int y = py; y < (py + altura); y++) {
            for (int x = (px); x < (px + largura); x++) {
                if (planeta.isTerra(x, y)) {
                    render.setPixel(x, y, eCor);
                }
            }
        }


    }

}
