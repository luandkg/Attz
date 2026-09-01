package apps.app_tronarko;


import apps.app_letrum.Fonte;
import apps.app_letrum.Maker.FonteRunTime;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.Windows;
import libs.azzal.cenarios.Cena;
import libs.azzal.utilitarios.Cor;
import libs.mockui.Interface.Acao;
import libs.mockui.Interface.BotaoCor;
import libs.mockui.Interface.Clicavel;
import libs.mockui.Marcador;
import libs.tronarko.agenda.Alarme;
import libs.tronarko.agenda.Lembrete;
import libs.tronarko.Hazde;
import libs.tronarko.Superarkos;
import libs.tronarko.Tozte;
import libs.tronarko.Tronarko;
import libs.tronarko.utils.FluxoTemporal;
import libs.tronarko.utils.Ordenador;

public class AppAlarme extends Cena {


    private Windows mWindows;

    private Fonte mTextoGrande;
    private Fonte mTextoGrande_Hoje;

    private Fonte mTextoPequeno;
    private Fonte mTextoPequeno_Sel;

    private Fonte mTextoPequeno_Hoje;
    private Fonte mTextoPequeno_Hoje2;
    private Fonte mTextoPequeno_MuitoGrande;

    private Tronarko TronarkoC;

    private BotaoCor BTN_DISPENSADOR;


    private Tozte mHoje;
    private Hazde mAgora;

    private int mQuantos;

    private int mTocar;

    private Alarme mAlarme;
    private Clicavel mClicavel;
    private Cores mCores;


    private int HIPERARKO_POS_X = 50;
    private int HIPERARKO_POS_Y = 280;


    private HiperarkoWidget mHiperarkoWidget;

    @Override
    public void iniciar(Windows eWindows) {

        mWindows = eWindows;
        mWindows.setTitle("Alarme");

        mCores = new Cores();

        mTextoGrande = new FonteRunTime(mCores.getPreto(), 15);
        mTextoGrande_Hoje = new FonteRunTime(mCores.getPreto(), 11);

        mTextoPequeno = new FonteRunTime(mCores.getPreto(), 11);
        mTextoPequeno_Sel = new FonteRunTime(mCores.getVermelho(), 11);

        mTextoPequeno_Hoje = new FonteRunTime(mCores.getVermelho(), 11);
        mTextoPequeno_Hoje2 = new FonteRunTime(mCores.getBranco(), 11);
        mTextoPequeno_MuitoGrande = new FonteRunTime(mCores.getBranco(), 18);

        TronarkoC = new Tronarko();

        mClicavel = new Clicavel();

        BTN_DISPENSADOR = mClicavel.criarBotaoCorDesenharAcima(new BotaoCor(600, 480, 200, 50, new Cor(50, 90, 156)));
        BTN_DISPENSADOR.setAcao(new Acao() {
            @Override
            public void onClique() {
                mAlarme.dispensar(mHoje, mAgora);
                System.out.println("Dispensar : " + mAlarme.quandoFoiDispensado());
            }
        });


        mHoje = TronarkoC.getTozte();
        mQuantos = 0;

        mAlarme = new Alarme();

        mAlarme.marcarSimples(TronarkoC.getTozte(), TronarkoC.getHazde().adicionar_Itta(-2).getComEttonZerado());
        mAlarme.marcarSimples(TronarkoC.getTozte(), TronarkoC.getHazde().getComEttonZerado());
        mAlarme.marcarSimples(TronarkoC.getTozte(), TronarkoC.getHazde().adicionar_Itta(+1).getComEttonZerado());
        mAlarme.marcarSimples(TronarkoC.getTozte(), TronarkoC.getHazde().adicionar_Itta(+2).getComEttonZerado());
        mAlarme.marcarSimples(TronarkoC.getTozte(), TronarkoC.getHazde().adicionar_Itta(+3).getComEttonZerado());
        mAlarme.marcarSimples(TronarkoC.getTozte(), TronarkoC.getHazde().adicionar_Itta(+4).getComEttonZerado());

        mAlarme.marcarSuperarko(Superarkos.ALFA, new Hazde(2, 0, 0));
        mAlarme.marcarSuperarko(Superarkos.ALFA, new Hazde(6, 30, 0));
        mAlarme.marcarSuperarko(Superarkos.ALFA, new Hazde(8, 50, 0));

        mAlarme.marcarSuperarko(Superarkos.GAMA, new Hazde(5, 0, 0));

        mAlarme.marcarSuperarko(Superarkos.IOTA, new Hazde(6, 30, 0));
        mAlarme.marcarSuperarko(Superarkos.IOTA, new Hazde(8, 30, 0));

        // mAlarme.limparTudo();

        mAlarme.marcarSimples(FluxoTemporal.ultimo_tozte_hiperarko(mHoje), TronarkoC.getHazde().getComEttonZerado());
        mAlarme.marcarSimples(FluxoTemporal.amanha(mHoje), TronarkoC.getHazde().adicionar_Arco(+2).getComEttonZerado());

        mAlarme.marcarSimples(FluxoTemporal.futuro(mHoje, 12), TronarkoC.getHazde().adicionar_Arco(+2).getComEttonZerado());
        mAlarme.marcarSimples(FluxoTemporal.passado(mHoje, 12), TronarkoC.getHazde().adicionar_Arco(+2).getComEttonZerado());

        mTocar = 1;

        // mLigarCron = false;
        // mCronLigar = new Hazde(6, 61, 0);


        mHiperarkoWidget = new HiperarkoWidget(HIPERARKO_POS_X, HIPERARKO_POS_Y, mHoje.getHiperarko(), mHoje.getTronarko());
        mHiperarkoWidget.selecionar(TronarkoC.getTozte());
        mHiperarkoWidget.setPodeSelecionar(true);

        mTextoPequeno_Sel = new FonteRunTime(mHiperarkoWidget.getCorTocando(), 11);

    }


    @Override
    public void update(double dt) {


        mHoje = TronarkoC.getTozte();
        mAgora = TronarkoC.getHazde();


        mClicavel.update(dt, getWindows().getMouse().getX(), getWindows().getMouse().getY(), getWindows().getMouse().isPressed());

        //System.out.println("Clicavel : " + mClicavel.getClicado()); 

        int px = getWindows().getMouse().getX();
        int py = getWindows().getMouse().getY();


        mHiperarkoWidget.update(px, py, getWindows().getMouse().isClicked());


        mHoje = mHoje.adicionar_Superarko(mQuantos);

        // System.out.println("Cron Ligar : " + mCronLigar.getTexto());


        getWindows().getMouse().liberar();

    }


    @Override
    public void draw(Renderizador r) {

        r.limpar(mCores.getBranco());
        mClicavel.onDraw(r);

        //Hoje = Hoje.adicionar_Tronarko(5);

        mTextoPequeno.setRenderizador(r);
        mTextoPequeno_Sel.setRenderizador(r);
        mTextoGrande_Hoje.setRenderizador(r);
        mTextoGrande.setRenderizador(r);
        mTextoPequeno_Hoje.setRenderizador(r);
        mTextoPequeno_Hoje2.setRenderizador(r);
        mTextoPequeno_MuitoGrande.setRenderizador(r);


        mTextoPequeno_MuitoGrande.escreva(BTN_DISPENSADOR.getX() + 30, BTN_DISPENSADOR.getY() + 10, "DISPENSAR");


        mTextoPequeno.escreva(50, 100, " -->> Hoje : " + mHoje.toString());
        mTextoPequeno.escreva(50, 150, " -->> Agora : " + mAgora.toString());


        mTextoGrande.escreva(600, 100, " AGENDA : " + mHoje.getSuperarko_Status().toString());


        draw_agenda(r, 610, 150);


        mHiperarkoWidget.drawHiperarko(r, mHoje, mAlarme, mHoje.getHiperarko());


        draw_agenda_selecionado(r, 50, 500, mHiperarkoWidget);

    }


    public void draw_agenda(Renderizador r, int px, int py) {

        for (Lembrete eLembrete : Ordenador.OrdenarLembretes(mAlarme.getLembretes(mHoje))) {

            boolean estaTocando = mAlarme.estaTocando(eLembrete, mTocar, mHoje, mAgora);

            if (estaTocando) {

                Marcador.marcar(r, px, py, 20, 5, mHiperarkoWidget.getCorTocando(), mCores.getBranco());

                mTextoPequeno_Sel.escreva(px + 30, py, eLembrete.getTozte().getTexto() + " " + eLembrete.getHazde().getTextoSemUzzons());

            } else {
                mTextoPequeno.escreva(px + 30, py, eLembrete.getTozte().getTexto() + " " + eLembrete.getHazde().getTextoSemUzzons());
            }

            py += 30;

        }

    }

    public void draw_agenda_selecionado(Renderizador r, int px, int py, HiperarkoWidget eHiperarkoWidget) {


        if (eHiperarkoWidget.getSelecionado().temValor()) {

            Tozte mTozteSelecionado = eHiperarkoWidget.getSelecionado().get();

            for (Lembrete lembrete : Ordenador.OrdenarLembretes(mAlarme.getLembretes(mTozteSelecionado))) {

                Cor eCor = eHiperarkoWidget.getCorEventos();

                int evento_modo = mAlarme.getModo(lembrete, mTocar, mTozteSelecionado, mHoje, mAgora);

                switch (evento_modo) {
                    case Alarme.EVENTO_PASSADO: {
                        Marcador.marcar(r, px, py - 3, 20, 5, mCores.getAzul(), mCores.getBranco());
                        break;
                    }
                    case Alarme.EVENTO_TOCANDO: {
                        Marcador.marcar(r, px, py - 3, 20, 5, mHiperarkoWidget.getCorTocando(), mCores.getBranco());
                        break;
                    }
                    case Alarme.EVENTO_FUTURO: {
                        r.drawRect_Pintado(px, py - 3, 20, 20, eCor);
                        break;
                    }
                }

                mTextoPequeno.escreva(px + 30, py, lembrete.getTozte().getTexto() + " -->> " + lembrete.getHazde().getTextoSemUzzons());

                py += 30;

            }

        }

    }
}
