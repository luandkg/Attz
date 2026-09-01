package apps.app_tronarko;

import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cor;
import libs.luan.Lista;
import libs.luan.Par;
import libs.luan.Strings;
import libs.tronarko.Hazde;
import libs.tronarko.Modarkos;
import libs.tronarko.Tronarko;

public class ModarkoProgresso {

    private Cores  mCores;

    private int mPX;
    private int mPY;

    private boolean mIttaVisivel;

    private Hazde mAgora;
    private Lista<Par<String, Cor>> mModarkos;

    public ModarkoProgresso(int ePX, int ePY, Lista<Par<String, Cor>> eModarkos){
        mCores = new Cores();
        mPX = ePX;
        mPY=ePY;
        mModarkos=eModarkos;
        mIttaVisivel=true;
    }

    public void update(Hazde agora){
        mAgora=agora;
    }

    public void setIttaVisivel(boolean eIttaVisivel){
        mIttaVisivel=eIttaVisivel;
    }

    public void draw(Renderizador r){

        for (int arkoNumero = 0; arkoNumero <= 9; arkoNumero++) {

            Cor cor = mCores.getPreto();

            Modarkos p = Tronarko.GET_MODARKO(arkoNumero);

            for (Par<String, Cor> m : mModarkos) {
                if (Strings.isIgual(m.getChave(), p.toString())) {
                    cor = m.getValor();
                    break;
                }
            }

            r.drawRect_Pintado(mPX + ((arkoNumero) * 40), mPY + 110, 40, 9, cor);
        }


        r.drawRect(mPX + ((mAgora.getArco()) * 40), mPY + 105, 40, 18, mCores.getPreto());


        if(mIttaVisivel){
            r.drawRect_Pintado((mPX + ((mAgora.getArco()) * 40)) + (int)(mAgora.getItta()*(40.0/100.0)), mPY + 100, 3, 30, mCores.getPreto());
        }


        r.drawRect_Pintado(mPX, mPY + 110 + 3, 400, 3, mCores.getPreto());


    }
}
