package apps.app_tronarko;

import apps.app_letrum.Fonte;
import apps.app_letrum.FonteDupla;
import apps.app_letrum.FonteDuplaRunTime;
import apps.app_letrum.Maker.FonteRunTime;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.Windows;
import libs.azzal.cenarios.Cena;
import libs.azzal.utilitarios.Cor;
import libs.documentar.AutoInt;
import libs.luan.Lista;
import libs.luan.Tempo;
import libs.mockui.Interface.Acao;
import libs.mockui.Interface.BotaoCor;
import libs.mockui.Interface.Clicavel;
import libs.mockui.Marcador;
import libs.rho_benchmark.RhoBenchmark;
import libs.tronarko.eventos.Comunicado;
import libs.tronarko.eventos.Comunicum;
import libs.tronarko.eventos.Eventum;
import libs.tronarko.eventos.Momentum;
import libs.tronarko.Hazde;
import libs.tronarko.satelites.Ceu;
import libs.tronarko.Tozte;
import libs.tronarko.Tron;
import libs.tronarko.Tronarko;
import libs.tronarko.utils.EventoLegenda;
import libs.tronarko.utils.TronarkoFalsum;


public class AppTronarko extends Cena {


    private FonteDupla mTextoGrande;

    private FonteDupla mTextoPequeno;

    private Fonte mTextoPequenoBranco;
    private Fonte TextoGrande_Sel;

    private Eventum mEventum;
    private Ceu mCeu;


    private Tozte mAtualmente;
    private Tozte mHoje;
    private Hazde mAgora;

    private int mQuantos;

    private Cores mCores;

    private Clicavel mClicavel;
    private BotaoCor BTN_MENOS;
    private BotaoCor BTN_MAIS;
    private BotaoCor BTN_HOJE;


    private Satelatizador mSatelatizadorAllux;
    private Satelatizador mSatelatizadorUnnos;
    private Satelatizador mSatelatizadorEttos;

    private HiperarkoWidget mHiperarkoWidget_01;
    private HiperarkoWidget mHiperarkoWidget_02;
    private HiperarkoWidget mHiperarkoWidget_03;
    private HiperarkoWidget mHiperarkoWidget_04;
    private HiperarkoWidget mHiperarkoWidget_05;

    private HiperarkoWidget mHiperarkoWidget_06;
    private HiperarkoWidget mHiperarkoWidget_07;
    private HiperarkoWidget mHiperarkoWidget_08;
    private HiperarkoWidget mHiperarkoWidget_09;
    private HiperarkoWidget mHiperarkoWidget_10;

    private HiperarkoWidget mHiperarkoWidgetSelecionado;
    private TronarkoImagemSignos mTronarkoImagemSignos;

    private Lista<EventoLegenda> mEventos;

    private RhoBenchmark mRhoBenchmark;

    private TronarkoFalsum mFalsum;

    private int mAnimacao = 0;

    @Override
    public void iniciar(Windows eWindows) {
        eWindows.setTitle("Tronarko com Azzal");


        mCores = new Cores();

        mTextoGrande = new FonteDuplaRunTime(mCores.getPreto(), mCores.getVermelho(), 20);
        mTextoPequeno = new FonteDuplaRunTime(mCores.getPreto(), mCores.getVermelho(), 11);
        mTextoPequenoBranco = new FonteRunTime(mCores.getBranco(), 11);

        TextoGrande_Sel = new FonteRunTime(mCores.getVermelho(), 20);

        mEventum = new Eventum();
        mCeu = new Ceu();

        mAtualmente = null;
        mHoje = Tronarko.getTozte();
        mQuantos = 0;

        mClicavel = new Clicavel();

        int botaoPosY = 900;

        int maisX = 300;

        BTN_HOJE = mClicavel.criarBotaoCorDesenharAcima(new BotaoCor(1155 - 25 + maisX, botaoPosY, 50, 50, new Cor(200, 120, 0)));
        BTN_HOJE.setVariacao(new Cor(200, 120, 0), new Cor(255, 120, 0));

        BTN_HOJE.setAcao(new Acao() {
            @Override
            public void onClique() {
                mQuantos = 0;
            }
        });

        BTN_MENOS = mClicavel.criarBotaoCor(new BotaoCor(1100 + maisX, botaoPosY - 30, 50, 100, new Cor(50, 90, 156)));
        BTN_MENOS.setVariacao(new Cor(50, 90, 156), new Cor(100, 90, 156));

        BTN_MENOS.setAcao(new Acao() {
            @Override
            public void onClique() {
                mQuantos -= 1;
            }
        });

        BTN_MAIS = mClicavel.criarBotaoCor(new BotaoCor(1155 + maisX, botaoPosY - 30, 50, 100, new Cor(26, 188, 156)));
        BTN_MAIS.setVariacao(new Cor(26, 188, 156), new Cor(100, 188, 156));

        BTN_MAIS.setAcao(new Acao() {
            @Override
            public void onClique() {
                mQuantos += 1;
            }
        });


        mSatelatizadorAllux = new Satelatizador("comum");
        mSatelatizadorEttos = new Satelatizador("comum");
        mSatelatizadorUnnos = new Satelatizador("comum");

        mTronarkoImagemSignos = new TronarkoImagemSignos();

        //ExportarSequenciaLunar.exportar(mHoje, 100, "/home/luan/Imagens/tronarko_luas.png");
        // ExportarSequenciaLunar.exportar(new Tozte(1, 1, 7001), 500, "/home/luan/Imagens/tronarko_luas_iluminacao.png");
        //ExportarSequenciaLunar.exportar(new Tozte(1, 1, 7000), 500, "/home/luan/Imagens/tronarko_luas_escuridao.png");


        //MapaCelestial s = new MapaCelestial();

        //ArrayList<Tozte_Intervalo> illuminatti = s.getIluminacao().mostrar(new Tozte(1, 1, 7001), 2);
        //ArrayList<Tozte_Intervalo> onnozzatti = s.getEscuridao().mostrar(new Tozte(1, 1, 7000), 2);

        //s.mostrarOcorrencias(illuminatti);
        // s.mostrarOcorrencias(onnozzatti);

        // ObservarCeu.mostrar(new Tozte(1, 1, 7000),"ILUMINACAO", Fases.CHEIA, Fases.CHEIA, Fases.CHEIA);

        AutoInt px = new AutoInt(50);
        AutoInt py = new AutoInt(10);


        mHiperarkoWidget_01 = new HiperarkoWidget(px.mais_get(0), py.get(), 1, mHoje.getTronarko());
        mHiperarkoWidget_02 = new HiperarkoWidget(px.mais_get(450), py.get(), 2, mHoje.getTronarko());
        mHiperarkoWidget_03 = new HiperarkoWidget(px.re_init(50), py.mais_get(200), 3, mHoje.getTronarko());
        mHiperarkoWidget_04 = new HiperarkoWidget(px.mais_get(450), py.get(), 4, mHoje.getTronarko());
        mHiperarkoWidget_05 = new HiperarkoWidget(px.re_init(50), py.mais_get(200), 5, mHoje.getTronarko());

        mHiperarkoWidget_06 = new HiperarkoWidget(px.mais_get(450), py.get(), 6, mHoje.getTronarko());
        mHiperarkoWidget_07 = new HiperarkoWidget(px.re_init(50), py.mais_get(200), 7, mHoje.getTronarko());
        mHiperarkoWidget_08 = new HiperarkoWidget(px.mais_get(450), py.get(), 8, mHoje.getTronarko());
        mHiperarkoWidget_09 = new HiperarkoWidget(px.re_init(50), py.mais_get(200), 9, mHoje.getTronarko());
        mHiperarkoWidget_10 = new HiperarkoWidget(px.mais_get(450), py.get(), 10, mHoje.getTronarko());

        mHiperarkoWidgetSelecionado = new HiperarkoWidget(950, 140, 1, mHoje.getTronarko());
        mHiperarkoWidgetSelecionado.setTamanhoCaixaTitulo(20);

        mRhoBenchmark = new RhoBenchmark("res/libs.RhoBenchmark.dkg", 0, 400);

        Tron eComecar = new Tron(Tronarko.getHazdeComecar(), 1, 1, 7000);
        Tron eTerminar = new Tron(Tronarko.getHazdeTerminar(), 5, 1, 7000);

        mFalsum = new TronarkoFalsum(eComecar, eTerminar);


        mHoje = Tronarko.getTozte();
        mAgora = Tronarko.getHazde();


        // mHoje = mFalsum.getTozte();
        // mAgora = mFalsum.getHazde();

    }


    @Override
    public void update(double dt) {

        long inicio = mRhoBenchmark.get();

        mHoje = Tronarko.getTozte();
        mAgora = Tronarko.getHazde();

        mFalsum.sincronizar(Tempo.getSegundos(), 12000);

        // mHoje = mFalsum.getTozte();
        // mAgora = mFalsum.getHazde();


        mClicavel.update(dt, getWindows().getMouse().getX(), getWindows().getMouse().getY(), getWindows().getMouse().isPressed());


        mHoje = mHoje.adicionar_Superarko(mQuantos);
        //mAgora = mAgora.adicionar_Arco(mQuantos);
        // mHoje = mHoje.adicionar_Tronarko(mQuantos);


        mHiperarkoWidgetSelecionado.setHiperarko(mHoje.getHiperarko());
        mHiperarkoWidgetSelecionado.setTronarko(mHoje.getTronarko());

        mHiperarkoWidget_01.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_02.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_03.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_04.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_05.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_06.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_07.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_08.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_09.setTronarko(mHoje.getTronarko());
        mHiperarkoWidget_10.setTronarko(mHoje.getTronarko());


        if (mAtualmente == null) {
            mAtualmente = mHoje;

            Momentum eMomentum = new Momentum();
            //  eMomentum.olharAoRedor(mAtualmente);

            mEventos = mEventum.getToztesComCor(mHoje.getTronarko());

        } else {
            if (mHoje.isDiferente(mAtualmente)) {
                mAtualmente = mHoje;

                Momentum eMomentum = new Momentum();
                //   eMomentum.olharAoRedor(mAtualmente);

                mEventos = mEventum.getToztesComCor(mHoje.getTronarko());

            }
        }

        getWindows().getMouse().liberar();

        long fim = mRhoBenchmark.get();

        //  mRhoBenchmark.set("libs.Tronarko.update()", inicio, fim);

        mAnimacao += 1;

        if (mAnimacao >= 50) {
            mAnimacao = 0;
        }

    }

    @Override
    public void draw(Renderizador r) {

        long inicio = mRhoBenchmark.get();

        r.limpar(mCores.getBranco());

        mClicavel.onDraw(r);


        mTextoPequeno.setRenderizador(r);
        mTextoGrande.setRenderizador(r);
        mTextoPequenoBranco.setRenderizador(r);
        TextoGrande_Sel.setRenderizador(r);

        mTextoPequenoBranco.escreva(BTN_MENOS.getX() + 5, BTN_MENOS.getY() + 40, "-1");
        mTextoPequenoBranco.escreva(BTN_MAIS.getX() + 25, BTN_MAIS.getY() + 40, "+1");
        mTextoPequenoBranco.escreva(BTN_HOJE.getX() + 2, BTN_HOJE.getY() + 15, "HOJE");


        mEventum.alinharEventos(mEventos);


        mHiperarkoWidget_01.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_02.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_03.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_04.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_05.draw_hiperarko_com_infos(r, mHoje, mEventos);


        mHiperarkoWidget_06.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_07.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_08.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_09.draw_hiperarko_com_infos(r, mHoje, mEventos);
        mHiperarkoWidget_10.draw_hiperarko_com_infos(r, mHoje, mEventos);

        int infoPosY = 35;

        mTextoPequeno.escreva(950, infoPosY, " -->> Hoje : " + mHoje.getTextoZerado());
        mTextoPequeno.escreva(950, infoPosY + 25, " -->> Agora : " + mAgora.getTextoZerado());
        mTextoPequeno.escreva(950, infoPosY + 50, " -->> Falta : " + mAgora.getTotalEttonsParaAcabarFormatado());


        int POS_Y_SATELITES = infoPosY;
        int POS_X_SATELITES = 950 + 300;


        r.drawImagemComAlfa(POS_X_SATELITES, POS_Y_SATELITES, mSatelatizadorAllux.get(mCeu.getAllux().getFaseIntTozte(mHoje)));
        r.drawImagemComAlfa(POS_X_SATELITES + 60, POS_Y_SATELITES, mSatelatizadorEttos.get(mCeu.getEttos().getFaseIntTozte(mHoje)));
        r.drawImagemComAlfa(POS_X_SATELITES + 120, POS_Y_SATELITES, mSatelatizadorUnnos.get(mCeu.getUnnos().getFaseIntTozte(mHoje)));

        mTextoPequeno.escreva(POS_X_SATELITES - 10, POS_Y_SATELITES + 40, mCeu.getAllux().getNomeCapitalizado());
        mTextoPequeno.escreva(POS_X_SATELITES + 60 - 10, POS_Y_SATELITES + 40, mCeu.getEttos().getNomeCapitalizado());
        mTextoPequeno.escreva(POS_X_SATELITES + 120 - 10, POS_Y_SATELITES + 40, mCeu.getUnnos().getNomeCapitalizado());


        // mTextoPequeno.escreva(pAllus - 10, ePosY + 80, mCeu.getAllux().getFaseIntTozte(mHoje) + " :: " + mCeu.getAllux().getFase(mHoje).toString());


        mHiperarkoWidgetSelecionado.draw_hiperarko_com_infos(r, mHoje, mEventos);


        BarraDeProgresso.tri_progresso(r, 950, 320, 380, 50, mHoje.getSuperarko(), 25, 40);


        int AVISO_X = 950;
        int AVISO_Y = 370;

        Lista<EventoLegenda> eventos = mEventum.getLegenda(mEventos);

        for (EventoLegenda evento : eventos) {

            Marcador.marcar(r, AVISO_X, AVISO_Y, 20, 5, evento.getCor(), mCores.getBranco());

            mTextoPequeno.escreva(AVISO_X + 30, AVISO_Y, evento.getNome());
            mTextoPequeno.escreva(AVISO_X + 280, AVISO_Y, " -->> " + evento.getComplemento());

            if (evento.isDentro(mHoje) && mAnimacao >= 20 && mAnimacao <= 50) {
                r.drawRect_Pintado(AVISO_X + 7, AVISO_Y + 7, 6, 6, evento.getCor());
            }

            AVISO_Y += 30;

        }


        Comunicado eComunicado = Comunicum.obterComunicado(eventos, mHoje);

        if (eComunicado.isOK()) {

            Marcador.marcar_barra_dupla(r, AVISO_X, AVISO_Y + 30, 5, 25, eComunicado.getCor());
            mTextoPequeno.escreva(AVISO_X + 30, AVISO_Y + 35, eComunicado.getValor());

            if (eComunicado.temVariosSuperarkos()) {

                if (eComunicado.estaDentro(mHoje)) {

                    int duracao = eComunicado.getDuracao();
                    int ate = eComunicado.getDistanciaDe(mHoje);

                    BarraDeProgresso.progresso(r, AVISO_X, AVISO_Y + 70, 380, duracao, ate, eComunicado.getCor());

                }

            }

        }


        r.drawImagemComAlfa(1380, mHiperarkoWidgetSelecionado.getPosY() + 70, mTronarkoImagemSignos.getSigno(mHoje.getSigno()));
        mTextoPequeno.escrevaCentralizado(1380 + 32 - 2, mHiperarkoWidgetSelecionado.getPosY() + 150, mHoje.getSigno().toString());

        long fim = mRhoBenchmark.get();

        //  mRhoBenchmark.set("libs.Tronarko.render()", inicio, fim);

        TextoGrande_Sel.escreva(950, 750, mHoje.getHizarko().toString());

        BarraDeProgresso.progresso(r, 950, 800, 380, Tronarko.HIZARKO_TAMANHO(), mHoje.Hizarko_Duracao(), mHoje.getHizarkoCor());


        mTextoPequeno.escreva(950, 800 + 50, "Início : " + mHoje.Hizarko_Inicio().getTextoZerado());
        mTextoPequeno.escreva(1150, 800 + 50, "Fim : " + mHoje.Hizarko_Fim().getTextoZerado());

        mTextoPequeno.escreva(950, 800 + 80, "Duração : " + mHoje.Hizarko_Duracao());
        mTextoPequeno.escreva(1150, 800 + 80, "Tamanho : " + Tronarko.HIZARKO_TAMANHO());

        mTextoPequeno.escreva(950, 800 + 110, "Fluxo : " + hizarko_fluxo(mHoje.Hizarko_Duracao()));

    }


    public String hizarko_fluxo(int v) {
        String ret = "";

        if (v <= 10) {
            ret = "Começando...";
        } else if (v >= 115) {
            ret = "Terminando...";
        } else {
            ret = "Aproveitando a estação...";
        }

        return ret;
    }

}

