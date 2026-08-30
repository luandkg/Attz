package libs.tronarko.utils;

import libs.azzal.utilitarios.Cor;
import libs.luan.Lista;
import libs.tronarko.Tozte;


public class TozteCor {

    private String mNome;
    private Tozte mTozte;
    private Cor mCor;

    private Lista<Tozte> mToztes;

    public TozteCor(String eNome, Tozte eTozte, Cor eCor) {
        mNome = eNome;
        mTozte = eTozte;
        mCor = eCor;

        mToztes = new Lista<Tozte>();

    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public Tozte getTozte() {
        return mTozte;
    }

    public Cor getCor() {
        return mCor;
    }

    public int getOrdem() {
        return mTozte.getSuperarko() + ((mTozte.getHiperarko() - 1) * 50) + (mTozte.getTronarko() * 500);
    }


    public Lista<Tozte> getToztes() {
        return mToztes;
    }


    public void adicionar_Tozte(Tozte eTozte) {
        mToztes.adicionar(eTozte);
    }


    public Tozte getTozteMin() {

        Tozte ret = null;

        if (mToztes.getQuantidade() > 0) {

            ret = mToztes.get(0);

            for (Tozte eTozte : mToztes) {

                if (eTozte.isMenorQue(ret)) {
                    ret = eTozte;
                }

            }

        }


        return ret;

    }


    public Tozte getTozteMax() {

        Tozte ret = null;

        if (mToztes.getQuantidade() > 0) {

            ret = mToztes.get(0);

            for (Tozte eTozte : mToztes) {

                if (eTozte.isMaiorQue(ret)) {
                    ret = eTozte;
                }

            }

        }


        return ret;

    }


    public String getComplemento() {

        String ret = "";


        if (mToztes.getQuantidade() == 1) {

            ret = "[ " + getTozteMin().getTextoZerado() + " ]";

        } else {

            if (getNome().contains("Reciclum")) {
                ret = "[ " + getTozteMin().getTextoZerado() + " e " + getTozteMax().getTextoZerado() + " ]";
            } else {
                ret = "[ " + getTozteMin().getTextoZerado() + " a " + getTozteMax().getTextoZerado() + " ]";
            }

        }


        return ret;


    }


    public boolean isDentro(Tozte referencia){
        boolean ret = false;

        for(Tozte t : mToztes){
            if(t.isIgual(referencia)){
                ret=true;
                break;
            }
        }

        return ret;
    }

}
