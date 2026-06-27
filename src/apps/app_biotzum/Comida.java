package apps.app_biotzum;

import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cronometro;
import libs.luan.Aleatorio;
import libs.luan.fmt;

public class Comida {

    private int mPx = 0;
    private int mPy = 0;

    private Cores mCores;

    private Cronometro mCron;
    private int mAnimacao = 0;

    public Comida(int x, int y) {
        mPx = x;
        mPy = y;
        mCores = new Cores();
        mCron = new Cronometro(100+ Aleatorio.aleatorio_entre(50,100));

    }

    public void atualizar() {
        mCron.esperar();
        if (mCron.foiEsperado()) {
            mCron.zerar();
            mAnimacao += 1;
            if (mAnimacao == 4) {
                mAnimacao = 0;
            }
        }
    }

    public int getX() {
        return mPx;
    }

    public int getY() {
        return mPy;
    }

    public void render(Renderizador g) {

        int tam = mAnimacao;

        g.drawRect_Pintado((mPx * 10) - (tam/2), (mPy * 10) -  (tam/2), tam, tam,mCores.getAmarelo());


    }

}
