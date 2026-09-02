package apps.app_tronarko;

import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cor;
import libs.luan.Lista;
import libs.luan.Par;
import libs.luan.Strings;
import libs.tronarko.Hizarkos;
import libs.tronarko.Tozte;

public class HizarkoProgressoWidget {

    private Cores mCores;

    private int mPX;
    private int mPY;

    private Tozte mTozte;

    private int mHiperarko;
    private int mTronarko;
    private Lista<Par<String, Cor>> mHizarkos;
    private boolean mSuperarkoVisivel;

    public HizarkoProgressoWidget(int ePX, int ePY, Lista<Par<String, Cor>> eHizarkos){
        mCores = new Cores();
        mPX = ePX;
        mPY=ePY;
        mHizarkos=eHizarkos;
        mSuperarkoVisivel=true;
    }

    public void setSuperarkoVisivel(boolean eSuperarkoVisivel){
        mSuperarkoVisivel=eSuperarkoVisivel;
    }


    public void update(int eHiperarko,int eTronarko,Tozte eTozte){
        mHiperarko=eHiperarko;
        mTronarko=eTronarko;
        mTozte=eTozte;
    }

    public void draw(Renderizador r) {

        for (int valor = 0; valor < 500; valor++) {

            Cor cor = mCores.getPreto();

            Tozte tozte = new Tozte(1,1,mTronarko).adicionar_Superarko(valor);


            Hizarkos p = tozte.getHizarko();

            for (Par<String, Cor> m : mHizarkos) {
                if (Strings.isIgual(m.getChave(), p.toString())) {
                    cor = m.getValor();
                    break;
                }
            }

            r.drawRect_Pintado(mPX +(int)tozte.getSuperarkosDoTronarko(),mPY-3 , 1, 9, cor);
        }

        r.drawRect_Pintado(mPX, mPY , 500, 3, mCores.getPreto());

        if(mSuperarkoVisivel){
            r.drawRect_Pintado((mPX + ((int)(mTozte.getSuperarkosDoTronarko()) )), mPY-12 , 3, 30, mCores.getPreto());
        }

    }

}
