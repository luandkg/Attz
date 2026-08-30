package libs.tronarko;

import libs.luan.Igualavel;
import libs.luan.Ordenavel;
import libs.luan.fmt;

public class Hazde {

    private int mArkos;
    private int mIttas;
    private int mUzzons;

    public int getArco() {
        return this.mArkos;
    }

    public int getItta() {
        return this.mIttas;
    }

    public int getUzzon() {
        return this.mUzzons;
    }

    public Hazde(int _arco, int _itta, int _uzzon) {
        this.mArkos = _arco;
        this.mIttas = _itta;
        this.mUzzons = _uzzon;
    }

    public Hazde getCopia() {
        return new Hazde(mArkos, mIttas, mUzzons);
    }

    public int getTotalEttons() {

        int ret = (getArco() * 100 * 100) + (getItta() * 100) + getUzzon();

        return ret;
    }

    public int getTotalEttonsMaximo() {

        int ret = (10 * 100 * 100) ;

        return ret;
    }

    public String getTexto() {
        String texto = "";

        texto = this.getArco() + ":" + this.getItta() + ":" + this.getUzzon();

        return texto;
    }

    public String getTextoZerado() {

        String p1 = fmt.numero_zerado_c2(this.getArco());
        String p2 = fmt.numero_zerado_c2(this.getItta());
        String p3 = fmt.numero_zerado_c2(this.getUzzon());

        return p1 + ":" + p2 + ":" + p3;
    }

    public String getTextoSemUzzonZerado() {

        String p1 = fmt.numero_zerado_c2(this.getArco());
        String p2 = fmt.numero_zerado_c2(this.getItta());

        return p1 + ":" + p2;
    }

    public String getTextoComInfos() {

        String p1 = fmt.numero_zerado_c2(this.getArco());
        String p2 = fmt.numero_zerado_c2(this.getItta());
        String p3 = fmt.numero_zerado_c2(this.getUzzon());

        return p1 + ":" + p2 + ":" + p3 + " ( " + getPorcentagem() + " ) :: " + getPeriarko_Valor() + " - " + getModarko_Valor();
    }

    public int getProgresso(){
        return (getArco() * 100) + getItta();
    }

    public int getMaximo(){
        return 10 * 100;
    }

    public String getPorcentagem() {
        return fmt.f2Porcentagem(getProgresso(),getMaximo() );
    }

    public String getTextoSemUzzons() {
        String texto = "";

        texto = this.getArco() + ":" + this.getItta();

        return texto;
    }

    public String getTextoZeradoSemUzzons() {
        String texto = "";

        texto = fmt.numero_zerado_c2(this.getArco()) + ":" + fmt.numero_zerado_c2(this.getItta());

        return texto;
    }


    public String getTextoSemEttos() {
        String texto = "";

        texto = this.getArco() + ":" + this.getItta() + ":" + this.getUzzon();

        return texto;
    }


    public final String toString() {
        return getTexto();
    }

    public long getTotalEttonsParaAcabar() {

        int eFalta = (10 * 100 * 100) - getTotalEttons();

        return eFalta;
    }

    public String getTotalEttonsParaAcabarFormatado() {

        int eValor = getTotalEttons();

        int eTudo = (10 * 100 * 100);
        int eFalta = eTudo - eValor;

        int eEttons = eFalta;
        int eIttas = 0;
        int eArcos = 0;

        while (eEttons >= 100) {
            eEttons -= 100;
            eIttas += 1;
        }

        while (eIttas >= 100) {
            eIttas -= 100;
            eArcos += 1;
        }

        String sIttas = fmt.numero_zerado_c2(eIttas);
        String sEttons = fmt.numero_zerado_c2(eEttons);

        return "" + eArcos + ":" + sIttas + ":" + sEttons;
    }

    public Periarkos getPeriarko() {
        Periarkos ret = null;

        if (getArco() >= 0 && getArco() <= 1) {
            ret = Periarkos.UD;
        }
        if (getArco() >= 2 && getArco() <= 4) {
            ret = Periarkos.AD;
        }
        if (getArco() >= 5 && getArco() <= 7) {
            ret = Periarkos.ED;
        }
        if (getArco() >= 8 && getArco() <= 9) {
            ret = Periarkos.OD;
        }

        return ret;
    }

    public String getPeriarko_Valor() {
        return getPeriarko().toString();
    }

    public String PeriarkoCompleto() {
        return String.valueOf(this.getArco()) + "º " + String.valueOf(this.getItta()) + " " + getPeriarko_Valor();
    }

    public Modarkos getModarko() {
        Modarkos ret = null;

        if (getArco() >= 0 && getArco() <= 1) {
            ret = Modarkos.OZZ;
        }
        if (getArco() >= 2 && getArco() <= 4) {
            ret = Modarkos.AZZ;
        }
        if (getArco() >= 5 && getArco() <= 7) {
            ret = Modarkos.AZZ;
        }
        if (getArco() >= 8 && getArco() <= 9) {
            ret = Modarkos.OZZ;
        }

        return ret;
    }

    public String getModarko_Valor() {
        return getModarko().toString();
    }

    public String ModarkoCompleto() {
        return String.valueOf(this.getArco()) + "º " + getModarko_Valor();
    }


    public boolean isOk() {
        boolean ok = true;

        if (mArkos <= 0 || mArkos >= 11) {
            ok = false;
        }

        if (mIttas <= 0 || mIttas >= 101) {
            ok = false;
        }

        if (mUzzons <= 0 || mUzzons >= 101) {
            ok = false;
        }

        return ok;
    }

    public Hazde adicionar_Arco(int a) {
        return modificar_Arco(this, a);
    }

    public Hazde adicionar_Itta(int i) {
        return modificar_Itta(this, i);
    }

    public Hazde adicionar_Uzzon(int u) {
        return modificar_Uzzon(this, u);
    }


    public Hazde getComEttonZerado() {
        Hazde ret = new Hazde(this.getArco(), this.getItta(), 0);

        return ret;
    }


    public Hazde modificar_Arco(Hazde sTron, int a) {

        int narco = sTron.getArco() + a;

        while (narco > 9) {
            narco -= 10;
        }

        while (narco < 0) {
            narco += 10;
        }

        Hazde ret = new Hazde(narco, sTron.getItta(), sTron.getUzzon());

        return ret;

    }

    public Hazde modificar_Itta(Hazde sTron, int i) {

        int nitta = sTron.getItta() + i;
        int narco = 0;

        while (nitta >= 100) {
            nitta -= 100;
            narco += 1;
        }

        while (nitta < 0) {
            nitta += 100;
            narco -= 1;
        }

        Hazde ret = new Hazde(sTron.getArco(), nitta, sTron.getUzzon());

        return modificar_Arco(ret, narco);

    }

    public Hazde modificar_Uzzon(Hazde sTron, int u) {

        int nuzzon = sTron.getUzzon() + u;
        int nitta = 0;

        while (nuzzon >= 100) {
            nuzzon -= 100;
            nitta += 1;
        }

        while (nuzzon < 0) {
            nuzzon += 100;
            nitta -= 1;
        }

        Hazde ret = new Hazde(sTron.getArco(), sTron.getItta(), nuzzon);

        return modificar_Itta(ret, nitta);

    }

    // INTERNALIZAR METODOS

    public void internalizar_Arco(int a) {

        int narco = this.getArco() + a;

        while (narco > 9) {
            narco -= 10;
        }

        while (narco <= 0) {
            narco += 10;
        }

        this.mArkos = narco;

    }

    public void internalizar_Itta(int i) {

        int nitta = this.getItta() + i;
        int narco = 0;

        while (nitta >= 100) {
            nitta -= 100;
            narco += 1;
        }

        while (nitta < 0) {
            nitta += 100;
            narco -= 1;
        }

        this.mIttas = nitta;

        Hazde ret = modificar_Arco(this, narco);

        this.mArkos = ret.getArco();

        this.mUzzons = ret.getUzzon();
        this.mIttas = ret.getItta();

    }

    public void internalizar_Uzzon(int u) {

        int nuzzon = this.getUzzon() + u;
        int nitta = 0;

        while (nuzzon >= 100) {
            nuzzon -= 100;
            nitta += 1;
        }

        while (nuzzon < 0) {
            nuzzon += 100;
            nitta -= 1;
        }

        this.mUzzons = nuzzon;

        Hazde ret = modificar_Itta(this, nitta);

        this.mArkos = ret.getArco();

        this.mIttas = ret.getItta();

    }

    // COMPARADORES

    public int compare(Hazde Outro) {
        int resposta = 0;

        if (this.getTotalEttons() == Outro.getTotalEttons()) {
            resposta = 0;
        }
        if (this.getTotalEttons() < Outro.getTotalEttons()) {
            resposta = -1;
        }
        if (this.getTotalEttons() > Outro.getTotalEttons()) {
            resposta = +1;
        }

        return resposta;

    }

    public boolean isMaior(Hazde Outro) {
        boolean resposta = false;
        if (this.getTotalEttons() > Outro.getTotalEttons()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMenor(Hazde Outro) {
        boolean resposta = false;
        if (this.getTotalEttons() < Outro.getTotalEttons()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isIgual(Hazde Outro) {
        boolean resposta = false;
        if (this.getTotalEttons() == Outro.getTotalEttons()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isDiferente(Hazde Outro) {
        boolean resposta = false;
        if (this.getTotalEttons() != Outro.getTotalEttons()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMaiorIgual(Hazde Outro) {
        boolean resposta = false;
        if (this.getTotalEttons() >= Outro.getTotalEttons()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMenorIgual(Hazde Outro) {
        boolean resposta = false;
        if (this.getTotalEttons() <= Outro.getTotalEttons()) {
            resposta = true;
        }

        return resposta;
    }

    public int getIttasTotal() {
        return (mArkos * 100) + mIttas;
    }


    public static Igualavel<Hazde> IGUALDADE() {
        return new Igualavel<Hazde>() {
            @Override
            public boolean is(Hazde a, Hazde b) {
                return a.isIgual(b);
            }
        };
    }

    public static Ordenavel<Hazde> ORDENADOR() {
        return new Ordenavel<Hazde>() {
            @Override
            public int emOrdem(Hazde a, Hazde b) {


                int resp = Ordenavel.IGUAL;

                if (a.isMaior(b)) {
                    resp = Ordenavel.MAIOR;
                } else if (a.isMenor(b)) {
                    resp = Ordenavel.MENOR;
                }


                return resp;
            }
        };

    }
}

