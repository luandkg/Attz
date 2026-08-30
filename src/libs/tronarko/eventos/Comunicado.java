package libs.tronarko.eventos;

import libs.azzal.utilitarios.Cor;
import libs.tronarko.Tozte;
import libs.tronarko.utils.EventoLegenda;

public class Comunicado {

    private EventoLegenda mTozte;
    private String mValor;

    public Comunicado(EventoLegenda eEventoLegenda, String eValor) {
        mTozte = eEventoLegenda;
        mValor = eValor;
    }

    public EventoLegenda getTozte() {
        return mTozte;
    }

    public String getValor() {
        return mValor;
    }

    public boolean isOK() {
        return getValor().length() > 0;
    }

    public Cor getCor() {
        return getTozte().getCor();
    }

    public boolean temVariosSuperarkos() {

        boolean mais = false;

        if (!getValor().contains("Tron") && getTozte().getTozteMax().isMaiorQue(getTozte().getTozteMin())) {
            mais = true;
        }

        return mais;
    }

    public boolean estaDentro(Tozte dentro) {
        boolean ret = false;

        if (dentro.isMaiorIgualQue(getTozte().getTozteMin()) && dentro.isMenorIgualQue(getTozte().getTozteMax())) {
            ret = true;
        }

        return ret;
    }

    public int getDuracao() {
        int ret = 0;

        if (getTozte().getTozteMin().isIgual(getTozte().getTozteMax())) {
            ret = 1;
        } else if (getTozte().getTozteMin().isDiferente(getTozte().getTozteMax())) {
            ret = Momentum.getDistancia(getTozte().getTozteMin(), getTozte().getTozteMax());
        }

        return ret;
    }

    public int getDistanciaDe(Tozte dentro) {
        int ret = Momentum.getDistancia(getTozte().getTozteMin(), dentro);
        return ret;

    }


}
