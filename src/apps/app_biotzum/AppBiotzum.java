package apps.app_biotzum;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_letrum.Fonte;
import apps.app_letrum.Maker.FonteRunTime;
import libs.azzal.*;
import libs.azzal.cenarios.Cena;
import libs.azzal.utilitarios.Cor;
import libs.luan.Opcional;

import java.awt.event.KeyEvent;

public class AppBiotzum extends Cena {

    private Biotzum mBiotzum;

    private Cores mCores;

    private Fonte mTextoPequeno;
    private Opcional<Organismo> mOrganismoProximo = Opcional.CANCEL();

    private Teclado mTeclado;
    private Mouse mMouse;

    public static void INICIAR() {
        AzzalUnico.unico("AppBiotzum", 1000, 1008, new AppBiotzum());
    }

    @Override
    public void iniciar(Windows eWindows) {

        eWindows.setTitle("AppBiotzum");
        mMouse = eWindows.getMouse();
        mTeclado = eWindows.getTeclado();

        mCores = new Cores();

        mBiotzum = new Biotzum();


        mTextoPequeno = new FonteRunTime(new Cor(255, 0, 0), 10);

        Loggum.INICIAR(mBiotzum.getOrganismos());

    }

    @Override
    public void update(double dt) {

        mBiotzum.update(dt);

        if (mMouse.isPressed()) {

            int px = mMouse.getX();
            int py = mMouse.getY();

            int mais_proximo = Integer.MAX_VALUE;

            mOrganismoProximo.esvaziar();

            for (Organismo org : mBiotzum.getOrganismos()) {
                int proximidade = Espaco2D.distancia_entre_pontos(px, py, org.getX() * 10, org.getY() * 10);
                if (proximidade < mais_proximo) {
                    mais_proximo = proximidade;
                    mOrganismoProximo.set(org);
                }
            }

        }

        if (mTeclado.foiPressionado(KeyEvent.VK_ESCAPE)) {
            mOrganismoProximo.esvaziar();
        } else if (mTeclado.foiPressionado(KeyEvent.VK_P)) {
            if (mOrganismoProximo.isOK()) {

                int px = mOrganismoProximo.get().getX() * 10;
                int py = mOrganismoProximo.get().getY() * 10;

                Organismo odif = mOrganismoProximo.get();

                int mais_proximo = Integer.MAX_VALUE;

                mOrganismoProximo.esvaziar();

                for (Organismo org : mBiotzum.getOrganismos()) {
                    if (odif != org) {
                        int proximidade = Espaco2D.distancia_entre_pontos(px, py, org.getX() * 10, org.getY() * 10);
                        if (proximidade < mais_proximo) {
                            mais_proximo = proximidade;
                            mOrganismoProximo.set(org);
                        }
                    }
                }

            }
        }

        mTeclado.limpar();
    }

    @Override
    public void draw(Renderizador g) {

        g.limpar(mCores.getPreto());


        for (int x = 0; x < 100; x++) {
            //   g.drawLinha(x * 10, 0, x * 10, g.getAltura(), mCores.getBranco());
        }

        for (int y = 0; y < 100; y++) {
            //   g.drawLinha(0, y * 10, g.getLargura(), y * 10, mCores.getBranco());
        }

        mBiotzum.render(g);

        if (mOrganismoProximo.isOK()) {
            g.drawRect((mOrganismoProximo.get().getX() * 10) - 10, (mOrganismoProximo.get().getY() * 10) - 10, 30, 30, mCores.getVermelho());

            mTextoPequeno.setRenderizador(g);
            mTextoPequeno.escreveLinha(100, 50, 150, "Posicao", mOrganismoProximo.get().getX() + " - " + mOrganismoProximo.get().getY());
            mTextoPequeno.escreveLinha(125, 50, 150, "Estagio", mOrganismoProximo.get().getEstagioTexto());

            mTextoPequeno.escreveLinha(150, 50, 150, "Energia", mOrganismoProximo.get().getEnergia());
            mTextoPequeno.escreveLinha(175, 50, 150, "Cansaço", mOrganismoProximo.get().getCansaco());

            if (mOrganismoProximo.get().getEstagio() == Organismo.ESTAGIO_DESCANSANDO) {
                mTextoPequeno.escreveLinha(200, 50, 150, "Descansando", mOrganismoProximo.get().getDescansando() + " de " + mOrganismoProximo.get().getDescansandoLimite());
            } else {
                mTextoPequeno.escreveLinha(200, 50, 150, "Descansando", mOrganismoProximo.get().getDescansando());
            }

            mTextoPequeno.escreveLinha(225, 50, 150, "Tempo", mOrganismoProximo.get().getTempo());
            mTextoPequeno.escreveLinha(250, 50, 150, "Passos", mOrganismoProximo.get().getPassos());
            mTextoPequeno.escreveLinha(275, 50, 150, "Batimentos", mOrganismoProximo.get().getBatimentos());

        }
    }
}
