package apps.app_tronarko;

import apps.app_letrum.Fonte;
import apps.app_letrum.Maker.FonteRunTime;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cor;
import libs.luan.Lista;
import libs.luan.Opcional;
import libs.tronarko.agenda.Alarme;
import libs.tronarko.Hiperarkos;
import libs.tronarko.Superarkos;
import libs.tronarko.Tozte;
import libs.tronarko.utils.EventoLegenda;


public class HiperarkoWidget {

    private Cores mCores;

    private Fonte TextoGrande;
    private Fonte TextoGrande_Sel;
    private Fonte TextoGrande_Hoje;

    private Fonte TextoPequeno;
    private Fonte TextoPequeno_Sel;


    private Fonte TextoPequeno_Hoje;
    private Fonte TextoPequeno_Hoje2;
    private Fonte TextoPequeno_HojePintado;

    private Opcional<Tozte> mSelecionado;

    private int HX1;
    private int HX2;
    private int VY1;
    private int VY2;

    private int CAIXA_X;
    private int CAIXA_Y;

    private Cor is_hoje;
    private Cor tocando;
    private Cor tem_eventos;

    private int mHiperarko;
    private int mTronarko;

    private boolean mPodeSelecionar;

    public HiperarkoWidget(int px, int py, int eHiperarko, int eTronarko) {

        CAIXA_X = px;
        CAIXA_Y = py;

        mHiperarko = eHiperarko;
        mTronarko = eTronarko;

        mCores = new Cores();

        TextoGrande = new FonteRunTime(mCores.getPreto(), 15);
        TextoGrande_Sel = new FonteRunTime(mCores.getVermelho(), 15);

        TextoGrande_Hoje = new FonteRunTime(mCores.getPreto(), 11);

        TextoPequeno = new FonteRunTime(mCores.getPreto(), 11);
        TextoPequeno_Sel = new FonteRunTime(mCores.getVermelho(), 11);

        TextoPequeno_Hoje = new FonteRunTime(mCores.getVermelho(), 11);
        TextoPequeno_Hoje2 = new FonteRunTime(mCores.getBranco(), 11);
        TextoPequeno_HojePintado = new FonteRunTime(mCores.getBranco(), 11);


        mSelecionado = new Opcional<Tozte>();


        HX1 = (CAIXA_X - 10) + (0 * 40) + 5;
        HX2 = (CAIXA_X - 10) + (10 * 40) + 5;

        VY1 = 30 + ((0 + 1) * 20) + CAIXA_Y + 10;
        VY2 = 30 + ((5 + 1) * 20) + CAIXA_Y + 10;

        tocando = new Cor(255, 20, 160);
        tem_eventos = new Cor(150, 200, 160);
        is_hoje = new Cor(180, 60, 160);

        mPodeSelecionar = false;
    }

    public void setPodeSelecionar(boolean e) {
        mPodeSelecionar = e;
    }

    public Cor getCorTocando() {
        return tocando;
    }

    public Cor getCorEventos() {
        return tem_eventos;
    }

    public void selecionar(Tozte eTozte) {
        mSelecionado.set(eTozte);
    }

    public Opcional<Tozte> getSelecionado() {
        return mSelecionado;
    }

    public void setHiperarko(int v) {
        mHiperarko = v;
    }

    public void setTronarko(int v) {
        mTronarko = v;
    }

    public void setTamanhoCaixaTitulo(int tamanho) {
        TextoGrande = new FonteRunTime(mCores.getPreto(), tamanho);
        TextoGrande_Sel = new FonteRunTime(mCores.getVermelho(), tamanho);
    }

    public int getPosY(){
        return CAIXA_Y;
    }

    public void update(int px, int py, boolean isClicado) {

        if (!mPodeSelecionar) {
            return;
        }

        if (isClicado) {


            if (px >= HX1 && px <= HX2) {
                if (py >= VY1 && py <= VY2) {

                    mSelecionado.esvaziar();

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

                                mSelecionado.set(new Tozte(v, mHiperarko, mTronarko));
                            }

                        }
                    }


                }
            }

        }

    }

    public void draw_hiperarko(Renderizador r, Tozte Hoje, Alarme mAlarme, int mHiperarko) {

        TextoPequeno.setRenderizador(r);
        TextoPequeno_Sel.setRenderizador(r);
        TextoGrande_Hoje.setRenderizador(r);
        TextoGrande.setRenderizador(r);
        TextoGrande_Sel.setRenderizador(r);
        TextoPequeno_Hoje.setRenderizador(r);
        TextoPequeno_Hoje2.setRenderizador(r);
        TextoPequeno_HojePintado.setRenderizador(r);


        int eTronarko = Hoje.getTronarko();


        if (Hoje.getHiperarko() == mHiperarko) {
            TextoGrande_Sel.escreva(CAIXA_X, CAIXA_Y, Hiperarkos.getNumerado(mHiperarko));
        } else {
            TextoGrande.escreva(CAIXA_X, CAIXA_Y, Hiperarkos.getNumerado(mHiperarko));
        }

        // CAIXA DE NOMES DOS SUPERARKOS
        for (int s = 0; s < 10; s++) {

            String eSuperarkoCapital = Superarkos.get(s + 1).getCapital();
            boolean is_destacar = false;

            if ((Hoje.getTronarko() == eTronarko) && (Hoje.getHiperarko() == mHiperarko)) {
                if (eSuperarkoCapital.contentEquals(Hoje.Superarko_capital())) {
                    is_destacar = true;
                }
            }

            if (is_destacar) {
                TextoPequeno_Sel.escrevaComOutraCor((CAIXA_X) + (s * 40), 30 + CAIXA_Y, eSuperarkoCapital, is_hoje);
            } else {
                TextoPequeno.escreva((CAIXA_X) + (s * 40), 30 + CAIXA_Y, eSuperarkoCapital);
            }

        }

        // CAIXAS DE TOZTES

        for (int m = 0; m < 5; m++) {

            for (int s = 0; s < 10; s++) {

                int QX = CAIXA_X + (s * 40) + 5;
                int QY = CAIXA_Y + 10 + 30 + ((m + 1) * 20);

                Tozte mTozte = new Tozte((m * 10) + (s + 1), mHiperarko, eTronarko);

                Cor mCor = mCores.getBranco();

                boolean comFundo = false;
                if (mAlarme.temLembretes(mTozte)) {
                    mCor = tem_eventos;
                    comFundo = true;
                }

                if (mSelecionado.temValor()) {

                    if (mTozte.getSuperarko() == mSelecionado.get().getSuperarko()) {
                        mCor = is_hoje;
                        comFundo = true;
                    }

                }


                if (comFundo) {
                    r.drawRect_Pintado(QX - 1, QY, 25, 20, mCor);
                }


                String mSuperNum = String.valueOf(mTozte.getSuperarko());
                if (mSuperNum.length() == 1) {
                    mSuperNum = "0" + mSuperNum;
                }

                if (Hoje.isIgual(mTozte)) {

                    if (comFundo) {
                        TextoPequeno_Hoje2.escreva(QX, QY, mSuperNum);
                    } else {
                        TextoPequeno_Hoje.escreva(QX, QY, mSuperNum);
                    }


                } else {

                    if (comFundo) {
                        TextoPequeno_HojePintado.escreva(QX, QY, mSuperNum);
                    } else {
                        TextoPequeno.escreva(QX, QY, mSuperNum);
                    }

                }

            }

        }


    }


    public void draw_hiperarko_com_infos(Renderizador r, Tozte mHoje, Lista<EventoLegenda> mInfos) {

        TextoPequeno.setRenderizador(r);
        TextoPequeno_Sel.setRenderizador(r);
        TextoGrande_Hoje.setRenderizador(r);
        TextoGrande.setRenderizador(r);
        TextoGrande_Sel.setRenderizador(r);
        TextoPequeno_Hoje.setRenderizador(r);
        TextoPequeno_Hoje2.setRenderizador(r);
        TextoPequeno_HojePintado.setRenderizador(r);


        if (mHoje.getHiperarko() == (mHiperarko)) {
            TextoGrande_Sel.escreva(CAIXA_X - 10, CAIXA_Y, Hiperarkos.getNumerado(mHiperarko));
        } else {
            TextoGrande.escreva(CAIXA_X - 10, CAIXA_Y, Hiperarkos.getNumerado(mHiperarko));
        }

        for (int s = 0; s < 10; s++) {

            String eMega = Superarkos.get(s + 1).getCapital();

            if ((mHoje.getTronarko() == mTronarko) && (mHoje.getHiperarko() == mHiperarko)) {

                if (eMega.contentEquals(mHoje.Superarko_capital())) {

                    TextoPequeno_Sel.escreva((CAIXA_X - 10) + (s * 40),
                            (30) + CAIXA_Y, eMega);

                    int larg = TextoPequeno.getLarguraDe(eMega);

                    r.drawRect_Pintado((CAIXA_X - 10) + (s * 40), (30) + CAIXA_Y + 15, larg, 3, mCores.getPreto());


                } else {
                    TextoPequeno.escreva((CAIXA_X - 10) + (s * 40),
                            (30) + CAIXA_Y, eMega);

                }

            } else {

                TextoPequeno.escreva((CAIXA_X - 10) + (s * 40),
                        (30) + CAIXA_Y, eMega);

            }

        }

        int mSuperarko = 1;

        String mAtualInfoNome = "";
        String mPassadoInfoNome = "";

        for (int m = 0; m < 5; m++) {

            boolean anteriorComFundo = false;

            for (int s = 0; s < 10; s++) {


                int QX = (CAIXA_X - 10) + (s * 40) + 5;
                int QY = ((30) + ((m + 1) * 20)) + CAIXA_Y + 10;

                Tozte mTozte = new Tozte(mSuperarko, mHiperarko, mTronarko);

                Cor mCor = new Cor(0,0,0);

                boolean comFundo = false;

                for (EventoLegenda InfoC : mInfos) {

                    if (mTozte.isIgual(InfoC.getTozte())) {
                        mCor = InfoC.getCor();
                        comFundo = true;
                        mAtualInfoNome = InfoC.getNome();
                        break;
                    }

                }

                if (comFundo) {
                    r.drawRect_Pintado(QX - 3, QY - 2, 25, 20, mCor);
                }

                if (comFundo & anteriorComFundo) {


                    if (mPassadoInfoNome.contentEquals(mAtualInfoNome)) {
                        r.drawRect_Pintado(QX - 3 - 18, QY + 5, 20, 5, mCor);
                    }
                }

                anteriorComFundo = comFundo;
                mPassadoInfoNome = mAtualInfoNome;

                String mSuperNum = String.valueOf(mSuperarko);
                if (mSuperNum.length() == 1) {
                    mSuperNum = "0" + mSuperNum;
                }

                if (mHoje.isIgual(mTozte)) {
                    if (comFundo) {
                        TextoPequeno_HojePintado.escreva(QX, QY, mSuperNum);
                    } else {
                        TextoPequeno_Sel.escreva(QX, QY, mSuperNum);
                    }
                } else {
                    TextoPequeno.escreva(QX, QY, mSuperNum);
                }


                mSuperarko += 1;

            }

        }

    }


}
