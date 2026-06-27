package apps.app_biotzum;

import libs.azzal.utilitarios.Cronometro;

public class AcontecimentoTemporal {

    private Cronometro mCron;
    private int mTempo;

    public AcontecimentoTemporal(int eTempo){
        mTempo=eTempo;
        mCron = new Cronometro(100);
    }

    public void emAcontece(){

    }

    public void atualiza(){
        mCron.esperar();
        if (mCron.foiEsperado()) {
            mCron.zerar();
            emAcontece();
        }
    }
}
