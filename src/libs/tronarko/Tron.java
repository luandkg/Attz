package libs.tronarko;

import libs.luan.Igualavel;
import libs.luan.Ordenavel;

public class Tron {

    private Hazde mHazdeC;
    private Tozte mTozteC;

    public Tron(int _arco, int _itta, int _uzzon, int _superarko, int _hiperarko, int _tronarko) {

        mHazdeC = new Hazde(_arco, _itta, _uzzon);
        mTozteC = new Tozte(_superarko, _hiperarko, _tronarko);

    }

    public Tron(Hazde eHazde, Tozte eTozte) {

        mHazdeC = new Hazde(eHazde.getArco(), eHazde.getItta(), eHazde.getUzzon());
        mTozteC = new Tozte(eTozte.getSuperarko(), eTozte.getHiperarko(), eTozte.getTronarko());

    }

    public Tron(Hazde eHazde, int _superarko, int _hiperarko, int _tronarko) {

        mHazdeC = new Hazde(eHazde.getArco(), eHazde.getItta(), eHazde.getUzzon());
        mTozteC = new Tozte(_superarko, _hiperarko, _tronarko);

    }

    public Tron getCopia() {
        return new Tron(mHazdeC.getArco(), mHazdeC.getItta(), mHazdeC.getUzzon(), mTozteC.getSuperarko(), mTozteC.getHiperarko(), mTozteC.getTronarko());
    }

    public Hazde getHazde() {
        return mHazdeC;
    }

    public Tozte getTozte() {
        return mTozteC;
    }

    public String getTexto() {
        return mHazdeC.getTexto() + " " + mTozteC.getTexto();
    }


    public String getTextoZerado() {
      return  getTozte().getTextoZerado() + " " + getHazde().getTextoZerado();
    }

    public String getTextoComInfos(){
        return getTextoZerado() + " ( "+ getTozte().getSuperarkosDoTronarko() + " ) :: " + getTozte().getHiperarko_Status().toString() + " - "+getTozte().getSuperarko_Status().toString();
    }

    public String getTextoInversoZerado() {
        String texto = "";

        String p1 = String.valueOf(getTozte().getSuperarko());
        String p2 = String.valueOf(getTozte().getHiperarko());
        String p3 = String.valueOf(getTozte().getTronarko());

        String p4 = String.valueOf(getHazde().getArco());
        String p5 = String.valueOf(getHazde().getItta());
        String p6 = String.valueOf(getHazde().getUzzon());

        if (p1.length() == 1) {
            p1 = "0" + p1;
        }

        if (p2.length() == 1) {
            p2 = "0" + p2;
        }

        if (p4.length() == 1) {
            p4 = "0" + p4;
        }
        if (p5.length() == 1) {
            p5 = "0" + p5;
        }
        if (p6.length() == 1) {
            p6 = "0" + p6;
        }

        texto = p3 + "_" + p2 + "_" + p1 + " " + p4 + ":" + p5 + ":" + p6;

        return texto;
    }

    public String getTextoSemUzzonZerado() {
        String texto = "";

        String p1 = String.valueOf(getTozte().getSuperarko());
        String p2 = String.valueOf(getTozte().getHiperarko());
        String p3 = String.valueOf(getTozte().getTronarko());

        String p4 = String.valueOf(getHazde().getArco());
        String p5 = String.valueOf(getHazde().getItta());

        if (p1.length() == 1) {
            p1 = "0" + p1;
        }

        if (p2.length() == 1) {
            p2 = "0" + p2;
        }

        if (p4.length() == 1) {
            p4 = "0" + p4;
        }
        if (p5.length() == 1) {
            p5 = "0" + p5;
        }


        texto = p1 + "/" + p2 + "/" + p3 + " " + p4 + ":" + p5;

        return texto;
    }


    public String toString() {
        return getTexto();
    }

    public long getSuperarkosTotal() {
        return (long) (mTozteC.getSuperarkosTotal());
    }

    public long getTotal() {

        long Total = 0;

        Total = (long) (mTozteC.getSuperarkosTotal()) * (10 * 100 * 100);

        Total += (long) mHazdeC.getTotalEttons();

        return Total;

    }


    public boolean isOk() {
        boolean ok = true;

        if (!mHazdeC.isOk() || !mTozteC.isOk()) {
            ok = false;
        }

        return ok;
    }


    // INTERNALIZAR METODOS

    public void internalizar_Superarko(int s) {
        mTozteC.internalizar_Superarko(s);
    }

    public void internalizar_Hiperarko(int h) {
        mTozteC.internalizar_Hiperarko(h);
    }

    public void internalizar_Tronarko(int t) {
        mTozteC.internalizar_Hiperarko(t);
    }

    public void internalizar_Arco(int a) {

        int narco = mHazdeC.getArco() + a;
        int nsuperarko = 0;

        while (narco > 9) {
            narco -= 10;
            nsuperarko += 1;
        }

        while (narco < 0) {
            narco += 10;
            nsuperarko -= 1;
        }

        mHazdeC = new Hazde(narco, mHazdeC.getItta(), mHazdeC.getUzzon());

        if (nsuperarko != 0) {
            mTozteC.internalizar_Superarko(nsuperarko);
        }

    }

    public void internalizar_Itta(int i) {

        int nitta = this.getHazde().getItta() + i;
        int narco = 0;

        while (nitta >= 100) {
            nitta -= 100;
            narco += 1;
        }

        while (nitta < 0) {
            nitta += 100;
            narco -= 1;
        }

        mHazdeC = new Hazde(mHazdeC.getArco(), nitta, mHazdeC.getUzzon());

        if (narco != 0) {
            internalizar_Arco(narco);
        }

    }

    public void internalizar_Uzzon(int u) {

        int nuzzon = mHazdeC.getUzzon() + u;
        int nitta = 0;

        while (nuzzon >= 100) {
            nuzzon -= 100;
            nitta += 1;
        }

        while (nuzzon < 0) {
            nuzzon += 100;
            nitta -= 1;
        }

        mHazdeC = new Hazde(mHazdeC.getArco(), mHazdeC.getItta(), nuzzon);

        if (nitta != 0) {
            internalizar_Itta(nitta);
        }

    }

    // MODIFICADORES

    public Tron modificar_Superarko(Tron sTron, int s) {

        int nsuperarko = sTron.getTozte().getSuperarko() + s;
        int nhiperarko = 0;

        while (nsuperarko > 50) {
            nsuperarko -= 50;
            nhiperarko += 1;
        }

        while (nsuperarko <= 0) {
            nsuperarko += 50;
            nhiperarko -= 1;
        }

        Tron ret = new Tron(sTron.getHazde().getArco(), sTron.getHazde().getItta(), sTron.getHazde().getUzzon(),
                nsuperarko, sTron.getTozte().getHiperarko(), sTron.getTozte().getTronarko());

        if (nhiperarko != 0) {
            return modificar_Hiperarko(ret, nhiperarko);
        } else {
            return ret;
        }

    }

    public Tron modificar_Hiperarko(Tron sTron, int h) {

        int nhiperarko = sTron.getTozte().getHiperarko() + h;
        int ntronarko = 0;

        while (nhiperarko > 10) {
            nhiperarko -= 10;
            ntronarko += 1;
        }

        while (nhiperarko <= 0) {
            nhiperarko += 10;
            ntronarko -= 1;
        }

        Tron ret = new Tron(sTron.getHazde().getArco(), sTron.getHazde().getItta(), sTron.getHazde().getUzzon(),
                sTron.getTozte().getSuperarko(), nhiperarko, sTron.getTozte().getTronarko());

        if (ntronarko != 0) {
            return modificar_Tronarko(ret, ntronarko);
        } else {
            return ret;
        }

    }

    public Tron modificar_Tronarko(Tron sTron, int t) {

        int ntronarko = sTron.getTozte().getTronarko() + t;

        Tron ret = new Tron(sTron.getHazde().getArco(), sTron.getHazde().getItta(), sTron.getHazde().getUzzon(),
                sTron.getTozte().getSuperarko(), sTron.getTozte().getHiperarko(), ntronarko);

        return ret;

    }

    public Tron modificar_Arco(int a) {

        Tron ret = new Tron(this.getHazde().getArco(), this.getHazde().getItta(), this.getHazde().getUzzon(),
                this.getTozte().getSuperarko(), this.getTozte().getHiperarko(), this.getTozte().getTronarko());

        ret.internalizar_Arco(a);

        return ret;

    }

    public Tron modificar_Itta(int i) {

        Tron ret = new Tron(this.getHazde().getArco(), this.getHazde().getItta(), this.getHazde().getUzzon(),
                this.getTozte().getSuperarko(), this.getTozte().getHiperarko(), this.getTozte().getTronarko());

        ret.internalizar_Itta(i);

        return ret;

    }

    public Tron modificar_Uzzon(int u) {

        Tron ret = new Tron(this.getHazde().getArco(), this.getHazde().getItta(), this.getHazde().getUzzon(),
                this.getTozte().getSuperarko(), this.getTozte().getHiperarko(), this.getTozte().getTronarko());

        ret.internalizar_Uzzon(u);

        return ret;

    }

    // ESPECIAIS

    public Tron proximo_Superarko() {
        return modificar_Superarko(this, 1);
    }

    public Tron anterior_Superarko() {
        return modificar_Superarko(this, -1);
    }

    // COMPARADORES

    public int Compare(Tron Outro) {
        int resposta = 0;

        if (this.getTotal() == Outro.getTotal()) {
            resposta = 0;
        }
        if (this.getTotal() < Outro.getTotal()) {
            resposta = -1;
        }
        if (this.getTotal() > Outro.getTotal()) {
            resposta = +1;
        }

        return resposta;

    }


    public boolean isMaiorQue(Tron Outro) {
        boolean resposta = false;
        if (this.getTotal() > Outro.getTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMenorQue(Tron Outro) {
        boolean resposta = false;
        if (this.getTotal() < Outro.getTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isIgual(Tron Outro) {
        boolean resposta = false;
        if (this.getTotal() == Outro.getTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isDiferente(Tron Outro) {
        boolean resposta = false;
        if (this.getTotal() != Outro.getTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMaiorIgualQue(Tron Outro) {
        boolean resposta = false;
        if (this.getTotal() >= Outro.getTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMenorIgualQue(Tron Outro) {
        boolean resposta = false;
        if (this.getTotal() <= Outro.getTotal()) {
            resposta = true;
        }

        return resposta;
    }


    public static Igualavel<Tron> IGUALDADE() {
        return new Igualavel<Tron>() {
            @Override
            public boolean is(Tron a, Tron b) {
                return a.isIgual(b);
            }
        };
    }

    public static Ordenavel<Tron> ORDENADOR() {
        return new Ordenavel<Tron>() {
            @Override
            public int emOrdem(Tron a, Tron b) {


                int resp = Ordenavel.IGUAL;

                if (a.isMaiorQue(b)) {
                    resp = Ordenavel.MAIOR;
                } else if (a.isMenorQue(b)) {
                    resp = Ordenavel.MENOR;
                }


                return resp;
            }
        };

    }
}
