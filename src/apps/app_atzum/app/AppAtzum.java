package apps.app_atzum.app;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_atzum.Atzum;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.utils.ArquivoAtzumGeral;
import apps.app_atzum.utils.ArquivoAtzumTronarko;
import apps.app_atzum.utils.AtzumPlacasTectonicas;
import apps.app_atzum.utils.IntervaloDeValorColorido;
import apps.app_letrum.Fonte;
import apps.app_letrum.Maker.FonteRunTime;
import libs.arquivos.PreferenciasOrganizadas;
import libs.azzal.AzzalUnico;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.Windows;
import libs.azzal.cenarios.Cena;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.imagem.Efeitos;
import libs.imagem.Imagem;
import libs.luan.*;
import libs.mockui.Interface.Acao;
import libs.mockui.Interface.BotaoCor;
import libs.mockui.Interface.Clicavel;
import libs.tronarko.Tron;
import libs.tronarko.Tronarko;

import java.awt.event.KeyEvent;

public class AppAtzum extends Cena {


    public static void INICIAR() {

        PreferenciasOrganizadas po = new PreferenciasOrganizadas(AtzumCreator.LOGS_GET_ARQUIVO("atzum.dkg"));
        if (po.abrirSeExistir()) {

            int px = po.getInteiro("Janela", "PX");
            int py = po.getInteiro("Janela", "PY");

            AzzalUnico.unico_posicionado("Mapa Atzum", 2300, 950, new AppAtzum(), px, py);
        } else {
            AzzalUnico.unico("Mapa Atzum", 2300, 950, new AppAtzum());
        }

    }


    private Cores mCores;


    private Fonte ESCRITOR_NORMAL;
    private Fonte ESCRITOR_NORMAL_VERMELHO;
    private Fonte ESCRITOR_NORMAL_BRANCO;
    private Fonte ESCRITOR_NORMAL_BRANCO_GRANDE;


    private String mAlturaCorrente = "";

    private String mTerraOuAgua = "";
    private String mRegiaoCorrente = "";
    private String mOceanoCorrente = "";

    private String mPlacaCorrente = "";

    private Atzum mAtzum;
    public Lista<Ponto> mCidades;

    public boolean mCidadeSelecionada = false;
    public int mCidadeSelecionadaX = 0;
    public int mCidadeSelecionadaY = 0;
    private Lista<Par<String, String>> mCidadeDescritores;

    private Clicavel mClicavel;

    private Lista<Entidade> mInformacoesDasCidadesIndexadas;


    public ArquivoAtzumGeral mArquivoAtzumGeral;
    public ArquivoAtzumTronarko mArquivoAtzumTronarko;


    private GrupoDeBotoesGrandes mGrupoPrincipal;
    private GrupoDeBotoesGrandes mSubComandos;
    private GrupoDeBotoesGrandes mCamadasZoom;

    public MapaZoom mMapaZoom;
    private ClimaWidget mClima;
    private TectonicoWidget mTectonico;
    private AtmosfericoWidget mAtmosferico;

    public VideoEmExecucao mVideoEmExecucao;

    public WidgetMapaVisualizador mWidgetMapaVisualizador;

    private RefString mMapaDeAtividade = null;


    public Lista<Entidade> mFenomenosTectonicos;
    public Lista<Entidade> mFenomenosAtmosfericos;


    @Override
    public void iniciar(Windows eWindows) {
        mCores = new Cores();

        ESCRITOR_NORMAL = new FonteRunTime(mCores.getVerde(), 10);
        ESCRITOR_NORMAL_VERMELHO = new FonteRunTime(mCores.getVermelho(), 10);
        ESCRITOR_NORMAL_BRANCO = new FonteRunTime(mCores.getBranco(), 10);
        ESCRITOR_NORMAL_BRANCO_GRANDE = new FonteRunTime(mCores.getBranco(), 30);

        String mTronarko = "7000";

        mArquivoAtzumGeral = new ArquivoAtzumGeral();
        mArquivoAtzumTronarko = new ArquivoAtzumTronarko(mTronarko);


        mWidgetMapaVisualizador = new WidgetMapaVisualizador(mArquivoAtzumGeral.GET_MAPA_DE_RELEVO(), Efeitos.reduzirMetade(Imagem.getCopia(mArquivoAtzumGeral.GET_MAPA_DE_RELEVO())));


        mAtzum = new Atzum();
        mCidades = Atzum.GET_CIDADES();


        mGrupoPrincipal = new GrupoDeBotoesGrandes(50, 30);
        mGrupoPrincipal.setSinalizador(BotaoSinalizador.LATERAL_DIREITA);
        mGrupoPrincipal.setAfastamentoX(120);

        mSubComandos = new GrupoDeBotoesGrandes(800, 850);
        mSubComandos.setSinalizador(BotaoSinalizador.ACIMA_DIRETA);
        mSubComandos.setAfastamentoX(100);

        mCamadasZoom = new GrupoDeBotoesGrandes(1900, 550);
        mCamadasZoom.setSinalizador(BotaoSinalizador.LATERAL_DIREITA);
        mCamadasZoom.setAfastamentoX(50);
        mCamadasZoom.setTamanho(20);
        mCamadasZoom.exibirTexto(false);

        mMapaZoom = new MapaZoom(this);

        mClicavel = new Clicavel();


        AtzumBotoesPrincipais.criar_camadas(mGrupoPrincipal, mSubComandos, this);
        AtzumBotoesPrincipais.criar_camadas_zoom(mCamadasZoom, this);

        mClima = new ClimaWidget();
        mTectonico = new TectonicoWidget();
        mAtmosferico = new AtmosfericoWidget();

        mVideoEmExecucao = new VideoEmExecucao();

        mGrupoPrincipal.aplicarCamada("Relevo");
        mCamadasZoom.aplicarCamada("Relevo");


        mCidadeDescritores = new Lista<Par<String, String>>();

        fmt.print(">> Carregando Tronarko : {}", mTronarko);

        Tron t1 = Tronarko.getTronAgora();

        //  mInformacoesDasCidades = mArquivoAtzumTronarko.getCidadesDadosPublicados();
        mInformacoesDasCidadesIndexadas = mArquivoAtzumTronarko.getCidadesDadosPublicadosIndicePorCidade();

        Tron t2 = Tronarko.getTronAgora();

        fmt.print("Gastou :: {}", Tronarko.TRON_DIFERENCA(t1, t2)); // 12 uz

        //ENTT.EXIBIR_TABELA(ENTT.SLICE_PRIMEIROS(mInformacoesDasCidadesIndexadas, 10));


        BotaoCor BTN_MAPA_CLIMATICO = mClicavel.criarBotaoCor(new BotaoCor(2150, 30, 30, 100, mCores.getBranco()));
        BotaoCor BTN_MAPA_TECTONICO = mClicavel.criarBotaoCor(new BotaoCor(2200, 30, 30, 100, mCores.getBranco()));
        BotaoCor BTN_MAPA_ATMOSFERICO = mClicavel.criarBotaoCor(new BotaoCor(2250, 30, 30, 100, mCores.getBranco()));

        BTN_MAPA_CLIMATICO.setAcao(new Acao() {
            @Override
            public void onClique() {

                mMapaDeAtividade.set("MAPA_CLIMATICO");
                PreferenciasOrganizadas.ALTERAR_DIRETO(AtzumCreator.LOGS_GET_ARQUIVO("atzum.dkg"), "Atzum", "MapaDeAtividade", mMapaDeAtividade.get());

            }
        });

        BTN_MAPA_TECTONICO.setAcao(new Acao() {
            @Override
            public void onClique() {

                mMapaDeAtividade.set("MAPA_TECTONICO");
                PreferenciasOrganizadas.ALTERAR_DIRETO(AtzumCreator.LOGS_GET_ARQUIVO("atzum.dkg"), "Atzum", "MapaDeAtividade", mMapaDeAtividade.get());

                mTectonico.setDados(mArquivoAtzumTronarko.getFenomenosTectonicos());
                ENTT.EXIBIR_TABELA(mArquivoAtzumTronarko.getFenomenosTectonicos());
            }
        });

        BTN_MAPA_ATMOSFERICO.setAcao(new Acao() {
            @Override
            public void onClique() {

                mMapaDeAtividade.set("MAPA_ATMOSFERICO");
                PreferenciasOrganizadas.ALTERAR_DIRETO(AtzumCreator.LOGS_GET_ARQUIVO("atzum.dkg"), "Atzum", "MapaDeAtividade", mMapaDeAtividade.get());

                mAtmosferico.setDados(mArquivoAtzumTronarko.getFenomenosAtmosfericos());
                ENTT.EXIBIR_TABELA(mArquivoAtzumTronarko.getFenomenosAtmosfericos());
            }
        });

        mMapaDeAtividade = new RefString();

        mMapaDeAtividade.set(PreferenciasOrganizadas.OBTER_DIRETO(AtzumCreator.LOGS_GET_ARQUIVO("atzum.dkg"), "Atzum", "MapaDeAtividade", "MAPA_CLIMATICO"));

        if (mMapaDeAtividade.isIgual("MAPA_CLIMATICO")) {
            BTN_MAPA_CLIMATICO.clicar();
        } else if (mMapaDeAtividade.isIgual("MAPA_TECTONICO")) {
            BTN_MAPA_TECTONICO.clicar();
        } else if (mMapaDeAtividade.isIgual("MAPA_ATMOSFERICO")) {
            BTN_MAPA_ATMOSFERICO.clicar();
        }

    }


    @Override
    public void update(double dt) {

        int px = getWindows().getMouse().getX();
        int py = getWindows().getMouse().getY();

        mAlturaCorrente = "";
        mTerraOuAgua = "";
        mRegiaoCorrente = "";
        mOceanoCorrente = "";

        if (mVideoEmExecucao.isExecutando()) {
            mVideoEmExecucao.update();
        }


        if (mWidgetMapaVisualizador.isDentro(px, py)) {


            mMapaZoom.update(false);

            int terra_ou_agua = mArquivoAtzumGeral.GET_PLANETA(mWidgetMapaVisualizador.getGPS_PX(), mWidgetMapaVisualizador.getGPS_PY());

            mTerraOuAgua = Portugues.VALIDAR(terra_ou_agua > 0, "TERRA", "ÁGUA");

            if (terra_ou_agua > 0) {

                int regiao_corrente = mArquivoAtzumGeral.GET_REGIAO(mWidgetMapaVisualizador.getGPS_PX(), mWidgetMapaVisualizador.getGPS_PY());
                int sub_regiao_corrente = mArquivoAtzumGeral.GET_SUBREGIAO(mWidgetMapaVisualizador.getGPS_PX(), mWidgetMapaVisualizador.getGPS_PY());

                mRegiaoCorrente = String.valueOf(regiao_corrente) + " :: " + String.valueOf(sub_regiao_corrente);

            } else {

                int oceano_corrente = mArquivoAtzumGeral.GET_OCEANO(mWidgetMapaVisualizador.getGPS_PX(), mWidgetMapaVisualizador.getGPS_PY());

                if (oceano_corrente > 0) {
                    mOceanoCorrente = oceano_corrente + " - " + mAtzum.GET_OCEANO(oceano_corrente);
                }

            }

            mAlturaCorrente = mArquivoAtzumGeral.GET_RELEVO_ALTITUDE(mWidgetMapaVisualizador.getGPS_PX(), mWidgetMapaVisualizador.getGPS_PY()) + "m";
            mPlacaCorrente = mAtzum.GET_PLACA_TECTONICA(mArquivoAtzumGeral.GET_PLACA_TECTONICA(mWidgetMapaVisualizador.getGPS_PX(), mWidgetMapaVisualizador.getGPS_PY()));

            if (getWindows().getMouse().isClicked()) {

                int lx = (int) getWindows().getLocationOnScreen().getX();
                int ly = (int) getWindows().getLocationOnScreen().getY();

                PreferenciasOrganizadas po = new PreferenciasOrganizadas(AtzumCreator.LOGS_GET_ARQUIVO("atzum.dkg"));
                po.abrirSeExistir();
                po.setInteiro("Janela", "PX", lx);
                po.setInteiro("Janela", "PY", ly);
                po.salvar();


                // AREA CLIMATICA


                if (Strings.isIgual(mTerraOuAgua, "TERRA")) {
                    procurar_cidade_proxima();
                }

                mMapaZoom.update(false);

            }


        }


        if (getWindows().getTeclado().foiPressionado(KeyEvent.VK_ESCAPE)) {
            mCidadeSelecionada = false;
            mClima.retirarCidade();
        }


        // if (getWindows().getMouse().isClicked()) {

        if (mCidadeSelecionada) {

            if (mMapaDeAtividade.get().contentEquals("MAPA_CLIMATICO")) {
                mClima.update(px, py, getWindows().getMouse().isClicked());
            } else if (mMapaDeAtividade.get().contentEquals("MAPA_TECTONICO")) {
            } else if (mMapaDeAtividade.get().contentEquals("MAPA_ATMOSFERICO")) {

            }

        }

        if (mMapaDeAtividade.get().contentEquals("MAPA_TECTONICO")) {
            mTectonico.update(px, py, getWindows().getMouse().isClicked());
        } else if (mMapaDeAtividade.get().contentEquals("MAPA_ATMOSFERICO")) {
            mAtmosferico.update(px, py, getWindows().getMouse().isClicked());
        }


        //   }

        mGrupoPrincipal.update(dt, px, py, getWindows().getMouse().isPressed());
        mCamadasZoom.update(dt, px, py, getWindows().getMouse().isPressed());
        mSubComandos.update(dt, px, py, getWindows().getMouse().isPressed());


        mClicavel.update(dt, px, py, getWindows().getMouse().isPressed());


        getWindows().getMouse().liberar();
        getWindows().getTeclado().limpar();

    }

    @Override
    public void draw(Renderizador g) {

        g.limpar(mCores.getPreto());

        ESCRITOR_NORMAL.setRenderizador(g);
        ESCRITOR_NORMAL_VERMELHO.setRenderizador(g);
        ESCRITOR_NORMAL_BRANCO.setRenderizador(g);
        ESCRITOR_NORMAL_BRANCO_GRANDE.setRenderizador(g);

        mWidgetMapaVisualizador.render(g);


        if (mVideoEmExecucao.isExibindo()) {

            g.drawImagem(mWidgetMapaVisualizador.getPosX(), mWidgetMapaVisualizador.getPosY(), mVideoEmExecucao.getImagemCorrente());

            int info_px = mWidgetMapaVisualizador.getPosX();
            int info_py = mWidgetMapaVisualizador.getPosY();

            ESCRITOR_NORMAL_BRANCO_GRANDE.escreva(info_px - 130, info_py + 50, mVideoEmExecucao.getLargura() + " vs " + mVideoEmExecucao.getAltura());
            ESCRITOR_NORMAL_BRANCO_GRANDE.escreva(info_px - 130, info_py + 100, mVideoEmExecucao.getFrameCorrente() + " frames de " + mVideoEmExecucao.getVideoQuadrosTotal());
            ESCRITOR_NORMAL_BRANCO_GRANDE.escreva(info_px - 130, info_py + 150, mVideoEmExecucao.getVideoDuracao());

            if (mVideoEmExecucao.getFrameCorrente() > 0 && mVideoEmExecucao.getFrameCorrente() <= 500) {
                if (mClima.temCidade()) {
                    mClima.marcarSuperarko(mVideoEmExecucao.getFrameCorrente());
                }
            }

            mVideoEmExecucao.verificarFim();


        }


        mClicavel.onDraw(g);

        if (mWidgetMapaVisualizador.isGPS_ON()) {
            ESCRITOR_NORMAL.escreveLinha(140, 50, 100, "GPS ON", "");

            ESCRITOR_NORMAL.escreveLinha(160, 50, 100, "X", " = " + mWidgetMapaVisualizador.getGPS_PX());
            ESCRITOR_NORMAL.escreveLinha(180, 50, 100, "Y", " = " + mWidgetMapaVisualizador.getGPS_PY());


            ESCRITOR_NORMAL.escreveLinha(220, 50, 150, "Local", " = " + mTerraOuAgua);
            g.drawRect_Pintado(30, 220 + 3, 10, 10, mCores.getBranco());

            if (mTerraOuAgua.contentEquals("TERRA")) {
                ESCRITOR_NORMAL.escreveLinha(240, 50, 150, "Altitude", " = " + mAlturaCorrente);
                g.drawRect_Pintado(30, 240 + 3, 10, 10, mCores.getBranco());
            } else if (mTerraOuAgua.contentEquals("ÁGUA")) {
                ESCRITOR_NORMAL.escreveLinha(240, 50, 150, "Profundidade", " = " + mAlturaCorrente);
                g.drawRect_Pintado(30, 240 + 3, 10, 10, mCores.getBranco());
            }

            if (!mRegiaoCorrente.isEmpty()) {
                ESCRITOR_NORMAL.escreveLinha(260, 50, 150, "Região", " = " + mRegiaoCorrente);
                g.drawRect_Pintado(30, 260 + 3, 10, 10, mCores.getBranco());
            }

            if (!mOceanoCorrente.isEmpty()) {
                ESCRITOR_NORMAL.escreveLinha(260, 50, 150, "Oceano", " = " + mOceanoCorrente);
                g.drawRect_Pintado(30, 260 + 3, 10, 10, mCores.getBranco());
            }

            if (!mPlacaCorrente.isEmpty()) {
                g.drawRect_Pintado(300, 260 + 3, 10, 10, mCores.getBranco());
                ESCRITOR_NORMAL.escreveLinha(260, 320, 350, "Placa", " = " + mPlacaCorrente);
            }


        } else {
            ESCRITOR_NORMAL_VERMELHO.escreva(50, 140, "GPS FAILED");
        }


        int X0 = mWidgetMapaVisualizador.getPosX();
        int Y0 = mWidgetMapaVisualizador.getPosY();


        for (Ponto cidade : mCidades) {

            boolean cidade_selecionada = false;

            if (mCidadeSelecionada) {
                if (mCidadeSelecionadaX == cidade.getX() && mCidadeSelecionadaY == cidade.getY()) {
                    cidade_selecionada = true;
                }
            }

            if (cidade_selecionada) {
                g.drawCirculoCentralizado_Pintado((cidade.getX() / 2) + X0, (cidade.getY() / 2) + Y0, 5, mCores.getVerde());
                g.drawCirculoCentralizado_Pintado((cidade.getX() / 2) + X0, (cidade.getY() / 2) + Y0, 2, mCores.getAzul());
            } else {

                Cor cidade_essa_cor = mCores.getBranco();

                if (mSubComandos.getQuantidade() > 0) {
                    cidade_essa_cor = mCores.getPreto();
                }

                // fmt.print("{} ->> {} :: {}",fc,cidade.toString(),cidade_essa_cor.toString());

                g.drawCirculoCentralizado_Pintado((cidade.getX() / 2) + X0, (cidade.getY() / 2) + Y0, 2, cidade_essa_cor);

            }


        }


        g.drawCirculoCentralizado_Pintado(20, 915, 10, mCores.getVermelho());
        ESCRITOR_NORMAL.escreva(35, 915 - 8, mGrupoPrincipal.getSelecionado());

        mGrupoPrincipal.render(g, ESCRITOR_NORMAL_BRANCO);
        mCamadasZoom.render(g, ESCRITOR_NORMAL_BRANCO);
        mSubComandos.render(g, ESCRITOR_NORMAL_BRANCO);


        if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "Regiões")) {

            int py = 100;
            for (String modelo_corrente : Atzum.GET_REGIOES()) {

                g.drawRect_Pintado(1700, py, 25, 25, Atzum.GET_REGIAO_COR(modelo_corrente));
                ESCRITOR_NORMAL_BRANCO.escreva(1700 + 40, py + 5, modelo_corrente);

                py += 30;
            }

        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "Modelo Climático")) {

            int py = 100;
            for (String modelo_corrente : Atzum.GET_MODELO_CLIMATICO()) {

                g.drawRect_Pintado(1700, py, 25, 25, Atzum.GET_MODELO_CLIMATICO_COR(modelo_corrente));
                ESCRITOR_NORMAL_BRANCO.escreva(1700 + 40, py + 5, modelo_corrente);

                py += 30;
            }

        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "Modelo Vegetação")) {

            int py = 100;
            for (String modelo_corrente : Atzum.GET_MODELO_VEGETACAO()) {

                g.drawRect_Pintado(1700, py, 25, 25, Atzum.GET_MODELO_VEGETACAO_COR(modelo_corrente));
                ESCRITOR_NORMAL_BRANCO.escreva(1700 + 40, py + 5, modelo_corrente);

                py += 30;
            }

        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "Umidade")) {

            int py = 100;
            for (IntervaloDeValorColorido modelo_corrente : Atzum.GET_UMIDADE_INTERVALOS_COLORIDOS()) {

                g.drawRect_Pintado(1700, py, 25, 25, modelo_corrente.getCor());
                ESCRITOR_NORMAL_BRANCO.escreva(1700 + 40, py + 5, modelo_corrente.getMinimo() + " a " + modelo_corrente.getMaximo());

                py += 30;
            }
        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "Temperatura")) {

            int py = 100;
            for (IntervaloDeValorColorido modelo_corrente : Atzum.GET_TEMPERATURA_INTERVALOS_COLORIDOS()) {

                g.drawRect_Pintado(1700, py, 25, 25, modelo_corrente.getCor());
                ESCRITOR_NORMAL_BRANCO.escreva(1700 + 40, py + 5, modelo_corrente.getMinimo() + " a " + modelo_corrente.getMaximo());

                py += 30;
            }
        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "MassaDeAr")) {

            int py = 100;
            for (String modelo_corrente : mAtzum.GET_MASSA_DE_AR_TIPOS()) {

                g.drawRect_Pintado(1700, py, 25, 25, mAtzum.GET_MASSA_DE_AR_COR(modelo_corrente));
                ESCRITOR_NORMAL_BRANCO.escreva(1700 + 40, py + 5, modelo_corrente);

                py += 30;
            }
        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "FatorClimatico")) {

            int py = 100;
            for (String modelo_corrente : mAtzum.GET_FATORES_CLIMATICOS()) {

                g.drawRect_Pintado(1700, py, 25, 25, Atzum.GET_FATOR_CLIMATICO_COR(modelo_corrente));
                ESCRITOR_NORMAL_BRANCO.escreva(1700 + 40, py + 5, modelo_corrente);

                py += 30;
            }
        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "MapaTectonico")) {

            int py = 200;
            for (Entidade fenomeno : mFenomenosTectonicos) {

                if (fenomeno.is("Evento", "TERREMOTO")) {
                    g.drawRect_Pintado(1800, py, 25, 25, mCores.getLaranja());
                } else {
                    g.drawRect_Pintado(1800, py, 25, 25, mCores.getVermelho());
                }

                ESCRITOR_NORMAL_BRANCO.escreva(1800 + 40, py + 5, fenomeno.at("Evento") + " :: " + fenomeno.at("Tozte"));

                py += 30;
            }

        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "MapaAtmosferico")) {

            int py = 200;
            for (Entidade fenomeno : mFenomenosAtmosfericos) {

                if (fenomeno.is("Evento", "FURACAO")) {
                    g.drawRect_Pintado(1800, py, 25, 25, mCores.getAzul());
                } else {
                    g.drawRect_Pintado(1800, py, 25, 25, mCores.getLaranja());
                }

                ESCRITOR_NORMAL_BRANCO.escreva(1800 + 40, py + 5, fenomeno.at("Evento") + " :: " + fenomeno.at("Tozte"));

                py += 30;
            }

        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "PlacasTectonicas")) {

            int py = 200;
            int placa_id = 0;
            for (String placa_tectonica : mAtzum.GET_PLACAS_TECTONICAS()) {

                g.drawRect_Pintado(1900, py, 25, 25, AtzumPlacasTectonicas.GET_PLACAS_TECTONICAS_CORES().get(placa_id));

                ESCRITOR_NORMAL_BRANCO.escreva(1900 + 40, py + 5, placa_tectonica);

                placa_id += 1;
                py += 30;
            }

        } else if (Strings.isIgual(mGrupoPrincipal.getSelecionado(), "Sociedades")) {


            Lista<Entidade> sociedades = Atzum.GET_SOCIEDADES();

            int py = 200;
            int placa_id = 0;
            for (Entidade so : sociedades) {

                g.drawRect_Pintado(1900, py, 25, 25, Cor.getHexCor(so.at("Cor")));

                ESCRITOR_NORMAL_BRANCO.escreva(1900 + 40, py + 5, so.at("Nome"));

                placa_id += 1;
                py += 30;
            }

        }

        int descritor_py = 300;

        if (mCidadeSelecionada) {

            for (Par<String, String> descritor : mCidadeDescritores) {
                if (!descritor.getChave().isEmpty()) {
                    ESCRITOR_NORMAL.escreveLinha(descritor_py, 40, 150, descritor.getChave(), " = " + descritor.getValor());
                }
                descritor_py += 20;
            }

        }


        if (mMapaDeAtividade.get().contentEquals("MAPA_CLIMATICO")) {
            mClima.render(g, descritor_py);
        } else if (mMapaDeAtividade.get().contentEquals("MAPA_TECTONICO")) {
            mTectonico.render(g, descritor_py);
        } else if (mMapaDeAtividade.get().contentEquals("MAPA_ATMOSFERICO")) {
            mAtmosferico.render(g, descritor_py);
        }

        mMapaZoom.render(g);


        g.drawRect(1900, 600, 300, 300, mCores.getVermelho());


        if (mMapaDeAtividade.get().contentEquals("MAPA_CLIMATICO")) {
            g.drawRect_Pintado(2150 + 5, 50, 20, 50, mCores.getLaranja());
        } else if (mMapaDeAtividade.get().contentEquals("MAPA_TECTONICO")) {
            g.drawRect_Pintado(2200 + 5, 50, 20, 50, mCores.getVermelho());
        } else if (mMapaDeAtividade.get().contentEquals("MAPA_ATMOSFERICO")) {
            g.drawRect_Pintado(2250 + 5, 50, 20, 50, mCores.getAzul());
        }

    }


    public void procurar_cidade_proxima() {


        mCidadeSelecionada = false;
        mCidadeDescritores.limpar();

        int mais_proxima = Integer.MAX_VALUE;

        for (Ponto cidade : mCidades) {
            int distancia = Espaco2D.distancia_entre_pontos(mWidgetMapaVisualizador.getGPS_PX(), mWidgetMapaVisualizador.getGPS_PY(), cidade.getX(), cidade.getY());
            if (distancia < mais_proxima) {
                mais_proxima = distancia;

                mCidadeSelecionada = true;
                mCidadeSelecionadaX = cidade.getX();
                mCidadeSelecionadaY = cidade.getY();

            }
        }


        if (mCidadeSelecionada) {


            Entidade mCidade = mArquivoAtzumTronarko.GET_CIDADE_DADOS(mCidadeSelecionadaX + "::" + mCidadeSelecionadaY);

            // mCidade = ENTT.GET_SEMPRE(mInformacoesDasCidades, "Cidade", cidade.getX() + "::" + cidade.getY());
            // mCidade = ENTT.GET_SEMPRE(mInformacoesDasCidades, "CidadePos", mCidadeSelecionadaX + "::" + mCidadeSelecionadaY);

            mCidadeDescritores.adicionar(new Par<String, String>("Nome", mCidade.at("CidadeNome")));
            mCidadeDescritores.adicionar(new Par<String, String>("Posição", mCidadeSelecionadaX + " : " + mCidadeSelecionadaY));

            int regiao_corrente = mArquivoAtzumGeral.GET_REGIAO(mCidadeSelecionadaX, mCidadeSelecionadaY);
            int sub_regiao_corrente = mArquivoAtzumGeral.GET_SUBREGIAO(mCidadeSelecionadaX, mCidadeSelecionadaY);

            String cidade_regiao = String.valueOf(regiao_corrente) + " :: " + String.valueOf(sub_regiao_corrente) + " - " + mAtzum.GET_REGIAO_NOME(regiao_corrente);
            String cidade_altitude = mArquivoAtzumGeral.GET_RELEVO_ALTITUDE(mCidadeSelecionadaX, mCidadeSelecionadaY) + "m";


            mCidadeDescritores.adicionar(new Par<String, String>("Região", cidade_regiao));
            mCidadeDescritores.adicionar(new Par<String, String>("Altitude", cidade_altitude));


            mCidadeDescritores.adicionar(new Par<String, String>("Tipo", Portugues.VALIDAR(mCidade.atInt("Oceano_Distancia") <= 15, "Litoranea", "Continental")));
            mCidadeDescritores.adicionar(new Par<String, String>("Oceano", mCidade.at("Oceano_Nome") + " - " + mCidade.at("Oceano_Distancia")));


            mTectonico.marcarCidade(mCidade);
            mAtmosferico.marcarCidade(mCidade);
            mClima.marcarCidade(mCidade);
        } else {
            mTectonico.retirarCidade();
            mAtmosferico.retirarCidade();
            mClima.retirarCidade();
        }

    }


}
