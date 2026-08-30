package libs.tronarko;

import libs.azzal.Cores;
import libs.azzal.utilitarios.Cor;
import libs.luan.Igualavel;
import libs.luan.Lista;
import libs.luan.Ordenavel;
import libs.tronarko.intervalos.Tozte_Intervalo;

public class Tozte {

    private int mSuperarkos;
    private int mHiperarkos;
    private int mTronarkos;

    public long getSuperarkosTotal() {

        long P1 = (long) (this.getSuperarko());
        long P2 = ((((long) (this.getHiperarko()) - 1) * 50));
        long P3 = ((long) (this.getTronarko()) * 500);

        return P1 + P2 + P3;

    }

    public long getSuperarkosDoTronarko() {

        long P1 = (long) (this.getSuperarko());
        long P2 = ((((long) (this.getHiperarko()) - 1) * 50));

        return P1 + P2 ;

    }

    public int getSuperarko() {
        return this.mSuperarkos;
    }

    public int getHiperarko() {
        return this.mHiperarkos;
    }

    public boolean isOk() {
        boolean ok = true;

        if (mSuperarkos <= 0 || mSuperarkos >= 51) {
            ok = false;
        }

        if (mHiperarkos <= 0 || mSuperarkos >= 11) {
            ok = false;
        }

        if (mTronarkos < 0) {
            ok = false;
        }

        return ok;
    }

    public int getTronarko() {
        return this.mTronarkos;
    }

    public Tozte getCopia() {
        return new Tozte(mSuperarkos, mHiperarkos, mTronarkos);
    }

    public Tozte(int _superarko, int _hiperarko, int _tronarko) {
        this.mSuperarkos = _superarko;
        this.mHiperarkos = _hiperarko;
        this.mTronarkos = _tronarko;
    }

    public String getTexto() {
        String texto = "";

        String p1 = String.valueOf(this.getSuperarko());
        String p2 = String.valueOf(this.getHiperarko());


        texto = p1 + "/" + p2 + "/" + this.getTronarko();

        return texto;
    }

    public String getTextoZerado() {

        String p1 = S(this.getSuperarko());
        String p2 = S(this.getHiperarko());
        String p3 = S(this.getTronarko());

        return p1 + "/" + p2 + "/" + p3;
    }


    public String getTextoInversoZerado() {

        String p1 = S(this.getSuperarko());
        String p2 = S(this.getHiperarko());
        String p3 = S(this.getTronarko());

        return p3 + "_" + p2 + "_" + p1;
    }

    public String getTextoInversoZerado_UnderLine() {

        String p1 = S(this.getSuperarko());
        String p2 = S(this.getHiperarko());
        String p3 = S(this.getTronarko());

        return p3 + "_" + p2 + "_" + p1;
    }


    private String S(int valor) {

        String sValor = String.valueOf(valor);
        if (sValor.length() == 1) {
            sValor = "0" + sValor;
        }
        return sValor;
    }

    public final String toString() {
        return getTexto();
    }

    public int SuperarkoDoTronarko() {
        return ((getHiperarko() - 1) * 50) + getSuperarko();
    }

    public int getMegarkoDoTronarko() {
        int mega = 1;
        int ns = this.SuperarkoDoTronarko();

        while (ns > 10) {
            mega += 1;
            ns -= 10;
        }

        return mega;
    }

    public int getMegarko() {
        int mega = 1;
        int ns = this.mSuperarkos;

        while (ns > 10) {
            mega += 1;
            ns -= 10;
        }

        return mega;
    }

    // SIZARKO

    public int getHizarko_Valor() {
        int ret = 1;
        int sdt = SuperarkoDoTronarko();

        while (sdt > Tronarko.HIZARKO_TAMANHO()) {
            ret += 1;
            sdt -= Tronarko.HIZARKO_TAMANHO();
        }

        return ret;
    }

    public int Hizarko_Duracao() {
        int sdt = SuperarkoDoTronarko();

        while (sdt > Tronarko.HIZARKO_TAMANHO()) {
            sdt -= Tronarko.HIZARKO_TAMANHO();
        }

        return sdt;
    }

    public Tozte Hizarko_Inicio() {
        switch (getHizarko_Valor()) {

            case 1:
                return this.Hizarko_InicioH1();
            case 2:
                return this.Hizarko_InicioH2();

            case 3:
                return this.Hizarko_InicioH3();

            case 4:
                return this.Hizarko_InicioH4();

        }
        return null;
    }

    public Tozte Hizarko_Fim() {
        switch (getHizarko_Valor()) {

            case 1:
                return this.Hizarko_FimH1();
            case 2:
                return this.Hizarko_FimH2();

            case 3:
                return this.Hizarko_FimH3();

            case 4:
                return this.Hizarko_FimH4();

        }
        return null;
    }

    public Tozte Hizarko_InicioH1() {

        Tozte ret = new Tozte(1, 1, this.mTronarkos);

        return ret;
    }

    public Tozte Hizarko_InicioH2() {
        return Hizarko_InicioH1().adicionar_Superarko(125);
    }

    public Tozte Hizarko_InicioH3() {
        return Hizarko_InicioH1().adicionar_Superarko(250);
    }

    public Tozte Hizarko_InicioH4() {
        return Hizarko_InicioH1().adicionar_Superarko(375);
    }

    public Tozte Hizarko_FimH1() {
        return Hizarko_InicioH1().adicionar_Superarko(124);
    }

    public Tozte Hizarko_FimH2() {
        return Hizarko_InicioH2().adicionar_Superarko(124);
    }

    public Tozte Hizarko_FimH3() {
        return Hizarko_InicioH3().adicionar_Superarko(124);
    }

    public Tozte Hizarko_FimH4() {
        return Hizarko_InicioH4().adicionar_Superarko(124);
    }

    public int Hizarko_Acabar() {

        return 126 - Hizarko_Duracao();

    }

    public Hizarkos getHizarko() {
        Hizarkos ret = null;

        switch (getHizarko_Valor()) {
            case 1:
                ret = Hizarkos.HITTARIUM;
                break;
            case 2:
                ret = Hizarkos.DEGGOVIUM;
                break;
            case 3:
                ret = Hizarkos.NUZTIUM;
                break;
            case 4:
                ret = Hizarkos.HARBARIUM;
                break;

        }

        return ret;
    }

    public Cor getHizarkoCor() {
        Cores mCores = new Cores();
        Cor ret = mCores.getPreto() ;

        switch (getHizarko_Valor()) {
            case 1:
                ret =mCores.getVermelho();
                break;
            case 2:
                ret =mCores.getAmarelo();
                break;
            case 3:
                ret =mCores.getCinza();
                break;
            case 4:
                ret =mCores.getAzul();
                break;

        }

        return ret;
    }

    public String Hizarko_nome() {
        return getHizarko().toString();
    }

    // SIGNOS
    public int getSigno_Valor() {
        int ret = 1;
        int sdt = SuperarkoDoTronarko();
        int faixa = 50;

        while (sdt > faixa) {
            ret += 1;
            sdt -= faixa;
        }

        return ret;
    }

    public String Signo_nome() {
        return getSigno().toString();
    }

    public Tozte_Intervalo getSignoIntervalo() {

        return new Tozte_Intervalo(Signo_nome(), new Tozte(1, this.getHiperarko(), this.getTronarko()),
                new Tozte(50, this.getHiperarko(), this.getTronarko()));

    }

    public Signos getSigno() {
        Signos ret = null;

        switch (getSigno_Valor()) {
            case 1:
                ret = Signos.TIGRE;
                break;
            case 2:
                ret = Signos.RAPOSA;
                break;
            case 3:
                ret = Signos.LEOPARDO;
                break;
            case 4:
                ret = Signos.GAVIAO;
                break;
            case 5:
                ret = Signos.TOURO;
                break;
            case 6:
                ret = Signos.LOBO;
                break;
            case 7:
                ret = Signos.GATO;
                break;
            case 8:
                ret = Signos.CARPA;
                break;
            case 9:
                ret = Signos.LEAO;
                break;
            case 10:
                ret = Signos.SERPENTE;
                break;

        }

        return ret;
    }

    public String Hiperarko_nome() {
        return getHiperarko_Status().toString();
    }

    public Hiperarkos getHiperarko_Status() {
        Hiperarkos ret = null;

        switch (getHiperarko()) {
            case 1:
                ret = Hiperarkos.DAZTO;
                break;
            case 2:
                ret = Hiperarkos.HERTO;
                break;
            case 3:
                ret = Hiperarkos.PURGO;
                break;
            case 4:
                ret = Hiperarkos.NOPTO;
                break;
            case 5:
                ret = Hiperarkos.FENCO;
                break;
            case 6:
                ret = Hiperarkos.MOZTO;
                break;
            case 7:
                ret = Hiperarkos.CARGO;
                break;
            case 8:
                ret = Hiperarkos.RIZNO;
                break;
            case 9:
                ret = Hiperarkos.SACNO;
                break;
            case 10:
                ret = Hiperarkos.TORNO;
                break;
        }

        return ret;
    }

    public String Hiperarko_Cor() {
        String ret = "";

        switch (getHiperarko()) {
            case 1:
                ret = "Branco";
                break;
            case 2:
                ret = "Amarelo";
                break;
            case 3:
                ret = "Vermelho";
                break;
            case 4:
                ret = "Azul";
                break;
            case 5:
                ret = "Laraja";
                break;
            case 6:
                ret = "Rosa";
                break;
            case 7:
                ret = "Verde";
                break;
            case 8:
                ret = "Violeta";
                break;
            case 9:
                ret = "Preto";
                break;
            case 10:
                ret = "Cinza";
                break;

        }

        return ret;
    }

    public int SuperarkoEmMegarko() {
        int ret = this.getSuperarko();

        while (ret > 10) {
            ret -= 10;
        }

        return ret;
    }

    public String Superarko_info() {
        return Superarko_nome() + ", " + mSuperarkos + " de " + Hiperarko_nome();
    }

    public String Superarko_nome() {
        return getSuperarko_Status().toString();
    }

    public String Superarko_capital() {
        return getSuperarko_Status().getCapital();
    }

    public Superarkos getSuperarko_Status() {
        Superarkos ret = null;

        switch (SuperarkoEmMegarko()) {
            case 1:
                ret = Superarkos.ALFA;
                break;
            case 2:
                ret = Superarkos.BETA;
                break;
            case 3:
                ret = Superarkos.GAMA;
                break;
            case 4:
                ret = Superarkos.DELTA;
                break;
            case 5:
                ret = Superarkos.EPSILON;
                break;
            case 6:
                ret = Superarkos.IOTA;
                break;
            case 7:
                ret = Superarkos.KAPA;
                break;
            case 8:
                ret = Superarkos.ZETA;
                break;
            case 9:
                ret = Superarkos.SIGMA;
                break;
            case 10:
                ret = Superarkos.OMEGA;
                break;

        }

        return ret;
    }

    public String Superarko_Simbolo() {
        String ret = "";

        switch (SuperarkoEmMegarko()) {
            case 1:
                ret = "Circulo";
                break;
            case 2:
                ret = "Linha";
                break;
            case 3:
                ret = "Triangulo";
                break;
            case 4:
                ret = "Quadrado";
                break;
            case 5:
                ret = "Pentagono";
                break;
            case 6:
                ret = "Hexagono";
                break;
            case 7:
                ret = "Pentagono e Linha";
                break;
            case 8:
                ret = "Retangulo";
                break;
            case 9:
                ret = "3 Triangulos";
                break;
            case 10:
                ret = "Circulo e Linha Central";
                break;

        }

        return ret;
    }

    // DIVISAO DO TRONARKO

    public int getBimestre() {
        int b = getHiperarko();
        int r = 0;

        if (b == 1 || b == 2) {
            r = 1;
        }
        if (b == 3 || b == 4) {
            r = 2;
        }
        if (b == 5 || b == 6) {
            r = 3;
        }
        if (b == 7 || b == 8) {
            r = 4;
        }
        if (b == 9 || b == 10) {
            r = 5;
        }

        return r;

    }

    public int getSemestre() {
        int s = getHiperarko();
        int r = 0;

        if (s >= 1 && s <= 5) {
            r = 1;
        }
        if (s >= 6 && s <= 10) {
            r = 2;
        }

        return r;

    }

    public int getDecada() {

        return this.mTronarkos / 10;

    }

    public int getSeculo() {

        return this.mTronarkos / 100;

    }

    public int getMilenio() {

        return this.mTronarkos / 1000;

    }

    // ADICIONAR METODOS

    public Tozte adicionar_Superarko(int s) {
        return modificar_Superarko(this, s);
    }

    public Tozte adicionar_Hiperarko(int h) {
        return modificar_Hiperarko(this, h);
    }

    public Tozte adicionar_Tronarko(int t) {
        return modificar_Tronarko(this, t);
    }

    // RETIRAR METODOS

    public Tozte retirar_Superarko(int s) {
        return modificar_Superarko(this, (-1) * s);
    }

    public Tozte retirar_Hiperarko(int h) {
        return modificar_Hiperarko(this, (-1) * h);
    }

    public Tozte retirar_Tronarko(int t) {
        return modificar_Tronarko(this, (-1) * t);
    }


    // ESPECIAIS

    public Tozte proximo_Superarko() {
        return modificar_Superarko(this, 1);
    }

    public Tozte anterior_Superarko() {
        return modificar_Superarko(this, -1);
    }

    // MODIFICAR METODOS

    public Tozte modificar_Superarko(Tozte sTron, int s) {

        int nsuperarko = sTron.getSuperarko() + s;
        int nhiperarko = 0;

        while (nsuperarko > 50) {
            nsuperarko -= 50;
            nhiperarko += 1;
        }

        while (nsuperarko <= 0) {
            nsuperarko += 50;
            nhiperarko -= 1;
        }

        Tozte ret = new Tozte(nsuperarko, sTron.getHiperarko(), sTron.getTronarko());

        if ((nhiperarko == 0) == false) {
            return modificar_Hiperarko(ret, nhiperarko);
        } else {
            return ret;
        }

    }

    public Tozte modificar_Hiperarko(Tozte sTron, int h) {

        int nhiperarko = sTron.getHiperarko() + h;
        int ntronarko = 0;

        while (nhiperarko > 10) {
            nhiperarko -= 10;
            ntronarko += 1;
        }

        while (nhiperarko <= 0) {
            nhiperarko += 10;
            ntronarko -= 1;
        }

        Tozte ret = new Tozte(sTron.getSuperarko(), nhiperarko, sTron.getTronarko());

        if ((ntronarko == 0) == false) {
            return modificar_Tronarko(ret, ntronarko);
        } else {
            return ret;
        }

    }

    public Tozte modificar_Tronarko(Tozte sTron, int t) {

        int ntronarko = sTron.getTronarko() + t;

        Tozte ret = new Tozte(sTron.getSuperarko(), sTron.getHiperarko(), ntronarko);

        return ret;

    }

    // INTERNALIZAR METODOS

    public void internalizar_Superarko(int s) {

        int nsuperarko = this.getSuperarko() + s;
        int nhiperarko = 0;

        while (nsuperarko > 50) {
            nsuperarko -= 50;
            nhiperarko += 1;
        }

        while (nsuperarko <= 0) {
            nsuperarko += 50;
            nhiperarko -= 1;
        }

        if ((nhiperarko == 0)) {
            this.mSuperarkos = nsuperarko;
        } else {
            Tozte r = modificar_Hiperarko(this, nhiperarko);

            this.mSuperarkos = nsuperarko;
            this.mHiperarkos = r.getHiperarko();
            this.mTronarkos = r.getTronarko();
        }

    }

    public void internalizar_Hiperarko(int h) {

        int nhiperarko = this.getHiperarko() + h;
        int ntronarko = 0;

        while (nhiperarko > 10) {
            nhiperarko -= 10;
            ntronarko += 1;
        }

        while (nhiperarko <= 0) {
            nhiperarko += 10;
            ntronarko -= 1;
        }

        if ((ntronarko == 0)) {
            this.mHiperarkos = nhiperarko;
        } else {
            this.mHiperarkos = nhiperarko;
            this.mTronarkos = modificar_Tronarko(this, ntronarko).getTronarko();
        }

    }

    public void internalizar_Tronarko(int t) {

        int ntronarko = this.getTronarko() + t;

        this.mTronarkos = ntronarko;
    }

    // COMPARADORES

    public int Compare(Tozte Outro) {
        int resposta = 0;

        if (this.getSuperarkosTotal() == Outro.getSuperarkosTotal()) {
            resposta = 0;
        }
        if (this.getSuperarkosTotal() < Outro.getSuperarkosTotal()) {
            resposta = -1;
        }
        if (this.getSuperarkosTotal() > Outro.getSuperarkosTotal()) {
            resposta = +1;
        }

        return resposta;

    }

    public boolean isMaiorQue(Tozte Outro) {
        boolean resposta = false;
        if (this.getSuperarkosTotal() > Outro.getSuperarkosTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMenorQue(Tozte Outro) {
        boolean resposta = false;
        if (this.getSuperarkosTotal() < Outro.getSuperarkosTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isIgual(Tozte Outro) {

        boolean resposta = false;

        if (this.getSuperarkosTotal() == Outro.getSuperarkosTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isDiferente(Tozte Outro) {
        boolean resposta = false;
        if (this.getSuperarkosTotal() != Outro.getSuperarkosTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMaiorIgualQue(Tozte Outro) {
        boolean resposta = false;
        if (this.getSuperarkosTotal() >= Outro.getSuperarkosTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public boolean isMenorIgualQue(Tozte Outro) {
        boolean resposta = false;
        if (this.getSuperarkosTotal() <= Outro.getSuperarkosTotal()) {
            resposta = true;
        }

        return resposta;
    }

    public Lista<Tozte_Intervalo> getEpocas() {
        Lista<Tozte_Intervalo> Epocas = new Lista<Tozte_Intervalo>();

        Epocas.adicionar((new Tozte_Intervalo("CRIACAO", new Tozte(1, 1, 1), new Tozte(50, 10, 325))));
        Epocas.adicionar((new Tozte_Intervalo("DEUSES", new Tozte(1, 1, 326), new Tozte(50, 10, 5299))));
        Epocas.adicionar((new Tozte_Intervalo("REINOS", new Tozte(1, 1, 5300), new Tozte(50, 10, 6199))));
        Epocas.adicionar((new Tozte_Intervalo("IMPERIOS", new Tozte(1, 1, 6200), new Tozte(50, 10, 10000))));

        return Epocas;
    }

    public Epocas getEpoca() {
        Epocas ret = null;

        if (getTronarko() >= 1 && getTronarko() <= 325) {
            ret = Epocas.CRIACAO;
        }
        if (getTronarko() >= 326 && getTronarko() <= 5299) {
            ret = Epocas.DEUSES;
        }
        if (getTronarko() >= 5300 && getTronarko() <= 6199) {
            ret = Epocas.REINOS;
        }
        if (getTronarko() >= 6200) {
            ret = Epocas.IMPERIOS;
        }

        return ret;
    }

    public int getTronarkosDaEpoca() {
        int ret = 0;

        switch (this.getEpoca()) {

            case CRIACAO:
                ret = this.getTronarko() - 0;
                break;
            case DEUSES:
                ret = this.getTronarko() - 326;
                break;
            case REINOS:
                ret = this.getTronarko() - 5300;
                break;
            case IMPERIOS:
                ret = this.getTronarko() - 6200;
                break;
            default:

                break;
        }

        return ret;
    }


    public static Igualavel<Tozte> IGUALDADE() {
        return new Igualavel<Tozte>() {
            @Override
            public boolean is(Tozte a, Tozte b) {
                return a.isIgual(b);
            }
        };
    }

    public static Ordenavel<Tozte> ORDENADOR() {
        return new Ordenavel<Tozte>() {
            @Override
            public int emOrdem(Tozte a, Tozte b) {


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

