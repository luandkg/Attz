package apps.app_tronarko;

import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cor;
import libs.luan.Lista;
import libs.luan.Par;
import libs.luan.Strings;
import libs.tronarko.Hazde;
import libs.tronarko.Periarkos;
import libs.tronarko.Tronarko;

public class PeriarkoProgresso {

    private Cores  mCores;

    private int mPX;
    private int mPY;

    private Hazde mAgora;
    private Lista<Par<String, Cor>> mPeriarkos;

    public PeriarkoProgresso(int ePX, int ePY, Lista<Par<String, Cor>> ePeriarkos){
        mCores = new Cores();
        mPX = ePX;
        mPY=ePY;
        mPeriarkos=ePeriarkos;
    }

    public void update(Hazde agora){
        mAgora=agora;
    }

    public void draw(Renderizador r){

        for (int arkoNumero = 0; arkoNumero <= 9; arkoNumero++) {

            Cor cor = mCores.getPreto();

            Periarkos p = Tronarko.GET_PERIARKO(arkoNumero);

            for (Par<String, Cor> m : mPeriarkos) {
                if (Strings.isIgual(m.getChave(), p.toString())) {
                    cor = m.getValor();
                    break;
                }
            }

            r.drawRect_Pintado(mPX + ((arkoNumero) * 40), mPY + 110, 40, 9, cor);
        }


        r.drawRect(mPX + ((mAgora.getArco()) * 40), mPY + 105, 40, 18, mCores.getPreto());

        double ti =  40.0/100.0;

        r.drawRect_Pintado((mPX + ((mAgora.getArco()) * 40)) + (int)(mAgora.getItta()*ti), mPY + 100, 3, 30, mCores.getPreto());


        r.drawRect_Pintado(mPX, mPY + 110 + 3, 400, 3, mCores.getPreto());


    }
}
