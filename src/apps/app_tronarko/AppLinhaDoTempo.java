package apps.app_tronarko;


import apps.app_letrum.Fonte;
import apps.app_letrum.Maker.FonteRunTime;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.Windows;
import libs.azzal.cenarios.Cena;
import libs.azzal.utilitarios.Cor;
import libs.luan.Lista;
import libs.luan.Opcional;
import libs.mockui.Interface.Acao;
import libs.mockui.Interface.BotaoCor;
import libs.mockui.Interface.Clicavel;
import libs.tronarko.agenda.Alarme;
import libs.tronarko.eventos.Eventum;
import libs.tronarko.Hazde;
import libs.tronarko.Tozte;
import libs.tronarko.Tronarko;
import libs.tronarko.utils.EventoLegenda;

public class AppLinhaDoTempo extends Cena {


    private Windows mWindows;

    private Fonte TextoGrande;
    private Fonte TextoGrande_Hoje;

    private Fonte TextoPequeno;
    private Fonte TextoPequeno_Sel;

    private Fonte TextoPequeno_Hoje;
    private Fonte TextoPequeno_Hoje2;
    private Fonte TextoPequeno_HojePintado;

    private Tronarko TronarkoC;
    private Eventum EventumC;

    private BotaoCor BTN_DISPENSADOR;


    private Tozte mHoje;
    private Hazde mAgora;

    private int mQuantos;

    private int mTocar;


    private Alarme mAlarme;
    private Clicavel mClicavel;
    private Cores mCores;

    private Opcional<Tozte> mSelecionado;

    private int HIPERARKO_POS_X = 50;
    private int HIPERARKO_POS_Y = 280;

    private int HX1;
    private int HX2;
    private int VY1;
    private int VY2;

    private Cor cor_timeline;
    private Cor cor_atividade;

    private TimeLine mTimeLine;

    @Override
    public void iniciar(Windows eWindows) {

        mWindows = eWindows;
        mWindows.setTitle("Linha Do Tempo");

        mCores = new Cores();

        TextoGrande = new FonteRunTime(mCores.getPreto(), 15);
        TextoGrande_Hoje = new FonteRunTime(mCores.getPreto(), 11);

        TextoPequeno = new FonteRunTime(mCores.getPreto(), 11);
        TextoPequeno_Sel = new FonteRunTime(mCores.getVermelho(), 11);

        TextoPequeno_Hoje = new FonteRunTime(mCores.getVermelho(), 11);
        TextoPequeno_Hoje2 = new FonteRunTime(mCores.getBranco(), 11);
        TextoPequeno_HojePintado = new FonteRunTime(mCores.getBranco(), 11);

        TronarkoC = new Tronarko();
        EventumC = new Eventum();

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


        // mLigarCron = false;
        // mCronLigar = new Hazde(6, 61, 0);

        mSelecionado = new Opcional<Tozte>();

        HX1 = (HIPERARKO_POS_X - 10) + (0 * 40) + 5;
        HX2 = (HIPERARKO_POS_X - 10) + (10 * 40) + 5;

        VY1 = 30 + ((0 + 1) * 20) + HIPERARKO_POS_Y + 10;
        VY2 = 30 + ((5 + 1) * 20) + HIPERARKO_POS_Y + 10;

        mSelecionado.set(TronarkoC.getTozte());

        cor_timeline = Cor.getHexCor("#388e3c");
        cor_atividade = Cor.getHexCor("#43a047");


        TextoPequeno_Sel = new FonteRunTime(cor_timeline, 11);


        mTimeLine = new TimeLine(new Tozte(1, mHoje.getHiperarko(), mHoje.getTronarko()), new Tozte(50, mHoje.getHiperarko(), mHoje.getTronarko()));


        mTimeLine.adicionar(new Tozte(5, mHoje.getHiperarko(), mHoje.getTronarko()), "Passeio Amarelo");
        mTimeLine.adicionar(new Tozte(20, mHoje.getHiperarko(), mHoje.getTronarko()), "Passeio Vermelho");
        mTimeLine.adicionar(new Tozte(43, mHoje.getHiperarko(), mHoje.getTronarko()), "Passeio Verde");

        mTimeLine.adicionar(new Tozte(6, mHoje.getHiperarko(), mHoje.getTronarko()), "Passeio Cinza");
        mTimeLine.adicionar(new Tozte(7, mHoje.getHiperarko(), mHoje.getTronarko()), "Passeio Laranjado");
        mTimeLine.adicionar(new Tozte(35, mHoje.getHiperarko(), mHoje.getTronarko()), "Passeio Azul");

    }


    @Override
    public void update(double dt) {


        mHoje = TronarkoC.getTozte();
        mAgora = TronarkoC.getHazde();


        mClicavel.update(dt, getWindows().getMouse().getX(), getWindows().getMouse().getY(), getWindows().getMouse().isPressed());

        //System.out.println("Clicavel : " + mClicavel.getClicado());

        if (getWindows().getMouse().isClicked()) {

            mSelecionado.esvaziar();

            int px = getWindows().getMouse().getX();
            int py = getWindows().getMouse().getY();

            if (px >= HX1 && px <= HX2) {
                if (py >= VY1 && py <= VY2) {


                    int box_l = (HX2 - HX1) / 10;
                    int box_a = (VY2 - VY1) / 5;

                    for (int a = 0; a < 5; a++) {
                        for (int c = 0; c < 10; c++) {

                            int caixa_x1 = HX1 + (c * box_l) - 5;
                            int caixa_x2 = caixa_x1 + box_l - 10;

                            int caixa_y1 = VY1 + (a * box_a) - 2;
                            int caixa_y2 = caixa_y1 + box_a - 2;

                            //   g.drawRect(HX1 + (c*box_l)-5, VY1 + (a*box_a)-2, box_l-10, box_a-2, new Cor(255, 0, 0));

                            if (px >= caixa_x1 && px < caixa_x2 && py >= caixa_y1 && py < caixa_y2) {
                                int v = (a * 10) + (c) + 1;

                                mSelecionado.set(new Tozte(v, mHoje.getHiperarko(), mHoje.getTronarko()));
                            }

                        }
                    }


                }
            }

        }


        mHoje = mHoje.adicionar_Superarko(mQuantos);

        // System.out.println("Cron Ligar : " + mCronLigar.getTexto());


        getWindows().getMouse().liberar();

    }


    @Override
    public void draw(Renderizador r) {

        r.limpar(mCores.getBranco());
        //mClicavel.onDraw(r);

        //Hoje = Hoje.adicionar_Tronarko(5);

        Lista<EventoLegenda> mInfos = EventumC.getToztesComCorHiperarko(mHoje.getHiperarko(), mHoje.getTronarko());


        int LX = 50;
        int LY = 200;

        TextoPequeno.setRenderizador(r);
        TextoPequeno_Sel.setRenderizador(r);
        TextoGrande_Hoje.setRenderizador(r);
        TextoGrande.setRenderizador(r);
        TextoPequeno_Hoje.setRenderizador(r);
        TextoPequeno_Hoje2.setRenderizador(r);
        TextoPequeno_HojePintado.setRenderizador(r);

        TextoPequeno.escreva(LX, LY - 100, " -->> Hoje : " + mHoje.toString());
        TextoPequeno.escreva(LX, LY - 50, " -->> Agora : " + mAgora.toString());

        int caixa_x = 70;
        int caixa_y = 450;

        draw_time_line(r, caixa_x, caixa_y);


    }

    public void draw_time_line(Renderizador r, int caixa_x, int caixa_y) {

        int escala = 15;
        int caixa_altura = 30;

        int superarkos = mTimeLine.getSuperarkos();

        r.drawRect_Pintado(caixa_x, caixa_y, superarkos * escala, caixa_altura, cor_timeline);

        boolean acima = true;

        int vs = 0;
        int vd = 0;

        for (TozteAtividade atividade : mTimeLine.getAtividades()) {

            int pos = mTimeLine.getPosicao(atividade.getTozte());

            int pos_y = 0;
            int texto_y = 0;
            int texto_y2 = 0;

            if (acima) {

                vs += 1;
                if (vs == 3) {
                    vs = 0;
                }

                int variavel = vs * 120;

                pos_y = caixa_y - caixa_altura - 20 - variavel;
                texto_y = caixa_y - caixa_altura - 40 - variavel;
                texto_y2 = caixa_y - caixa_altura - 60 - variavel;

                r.drawRect_Pintado(caixa_x + (pos * escala), pos_y, 1, caixa_y - pos_y, mCores.getPreto());

            } else {

                vd += 1;
                if (vd == 3) {
                    vd = 0;
                }

                int variavel = vd * 120;

                pos_y = caixa_y + caixa_altura + 40 + variavel;
                texto_y = caixa_y + caixa_altura + 60 + variavel;
                texto_y2 = caixa_y + caixa_altura + 80 + variavel;

                r.drawRect_Pintado(caixa_x + (pos * escala), caixa_y + caixa_altura, 1, pos_y - caixa_y - caixa_altura, mCores.getPreto());

            }

            r.drawRect_Pintado(caixa_x + (pos * escala) - 5, pos_y, 10, 10, cor_atividade);

            TextoPequeno.escrevaCentralizado(caixa_x + (pos * escala), texto_y, atividade.getAtividade());
            TextoPequeno.escrevaCentralizado(caixa_x + (pos * escala), texto_y2, atividade.getTozte().getTexto());

            acima = !acima;
        }

        if (mTimeLine.isDentro(mHoje)) {

            int pos = mTimeLine.getPosicao(mHoje);
            r.drawRect_Pintado(caixa_x + (pos * escala) - 5, caixa_y + 10, 10, 10, mCores.getBranco());

        }


    }


}
