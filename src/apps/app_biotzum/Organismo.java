package apps.app_biotzum;

import apps.app_biotzum.movimentacao.Movimentador;
import apps.app_biotzum.movimentacao.MovimentadorSimples;
import apps.app_biotzum.movimentacao.Movimento;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cronometro;
import libs.luan.Aleatorio;
import libs.luan.Lista;
import libs.luan.Matematica;
import libs.luan.fmt;

public class Organismo {

    public static final int ESTAGIO_NORMAL = 0;
    public static final int ESTAGIO_DESCANSANDO = 1;
    public static final int ESTAGIO_DORMINDO = 2;

    private static int ID_ORGANIZADOR = 0;

    private int mID;

    private int mX;
    private int mY;

    private Cores mCores = new Cores();

    private int mEnergia = 5000;
    private int mEstagio = 0;
    private int mDescansando = 0;
    private int mDeveDescansar = 0;

    private int mCansaco = 0;
    private int mCansacoMaximo = 1000;

    private int mEnergiaBasal = 150;

    private int mTempo = 0;
    private int mTempoAcordado = 0;
    private int mTempoAcordadoMaximo = 0;

    private int mTempoDormindo = 0;
    private int mTempoDormindoMaximo = 0;

    private int mPassos = 0;

    private int mBatimentos = 0;

    private Cronometro mCron;
    private Movimentador mMovimentador;

    public Organismo(int x, int y) {

        mID = ID_ORGANIZADOR;
        ID_ORGANIZADOR += 1;


        mX = x;
        mY = y;
        mEstagio = ESTAGIO_NORMAL;
        mCron = new Cronometro(500);
        mMovimentador = new MovimentadorSimples(this);

        mBatimentos = Aleatorio.aleatorio_entre(50, 80);
        mTempoAcordado = 0;
        mTempoAcordadoMaximo = Aleatorio.aleatorio_entre(1500, 2000);
    }

    public int getID() {
        return mID;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getEnergia() {
        return mEnergia;
    }

    public int getCansaco() {
        return mCansaco;
    }

    public int getDescansando() {
        return mDescansando;
    }

    public int getDescansandoLimite() {
        return mDeveDescansar;
    }

    public int getEstagio() {
        return mEstagio;
    }

    public int getPassos() {
        return mPassos;
    }

    public int getTempo() {
        return mTempo;
    }

    public int getBatimentos() {
        return mBatimentos;
    }

    public String getEstagioTexto() {
        String ret = "";
        if (mEstagio == ESTAGIO_NORMAL) {
            ret = "Normal";
        } else if (mEstagio == ESTAGIO_DESCANSANDO) {
            ret = "Descansando";
        } else if (mEstagio == ESTAGIO_DORMINDO) {
            ret = "Dormindo";
        }
        return ret;
    }

    public int calcularGastoDeMovimento(int mover_x, int mover_y) {
        int gasto_de_movimentacao = (Matematica.MODULO(mover_x) * 3) + (Matematica.MODULO(mover_y) * 5);
        return gasto_de_movimentacao;
    }

    public void andarDireto(int mover_x, int mover_y, int px, int py) {
        int gasto_de_movimentacao = calcularGastoDeMovimento(mover_x, mover_y);

        if (getEnergia() >= gasto_de_movimentacao) {
            mX = px;
            mY = py;
            mEnergia -= gasto_de_movimentacao;
        }
    }

    public Movimento andar(Lista<Organismo> outros) {
        return mMovimentador.andar(outros);
    }

    public boolean isLocalValido(int px, int py, Lista<Organismo> outros) {
        boolean ret = true;
        if (px >= 100) {
            ret = false;
        }
        if (py >= 100) {
            ret = false;
        }
        if (px <= 0) {
            ret = false;
        }
        if (py <= 1) {
            ret = false;
        }
        if (ret) {
            for (Organismo outro : outros) {
                if (outro.mX == px && outro.mY == py) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    public void zerarPassos() {
        mPassos = 0;
    }

    public void atualizar(Lista<Organismo> outros, Lista<Comida> comidas) {

        boolean aguardou = false;

        mCron.esperar();
        if (mCron.foiEsperado()) {
            mCron.zerar();
            aguardou = true;
        }

        if (!aguardou) {
            //   return;
        }

        mTempo += 1;
        mTempoAcordado += 1;

        if (mTempoAcordado > mTempoAcordadoMaximo) {
            mEstagio = ESTAGIO_DORMINDO;
            mTempoDormindoMaximo = Aleatorio.aleatorio_entre(300, 500);
            mTempoDormindo = 0;
            mTempoAcordado = 0;
        }


        if (mEstagio == ESTAGIO_NORMAL) {
            Movimento movimento = andar(outros);

            int passos = movimento.passos();

            if (passos > 0) {
                mBatimentos += Aleatorio.aleatorio_entre(0, 3);
            } else {
                if (mBatimentos > 50) {
                    mBatimentos -= Aleatorio.aleatorio_entre(0, 3);
                }
            }

            mPassos += passos;

            for (Comida comida : comidas) {
                if (comida.getX() == mX && comida.getY() == mY) {
                    comidas.remover(comida);
                    mEnergia += 5000;
                    fmt.print("\t -->> Comeuuuuuuuu");
                    break;
                }
            }

            if (mEnergia < mEnergiaBasal) {
                mEstagio = ESTAGIO_DESCANSANDO;
                mCron.zerar();
                mDescansando = 0;
                mDeveDescansar = Aleatorio.aleatorio_entre(15, 30);
                return;
            }

            if (mCansaco > mCansacoMaximo) {
                mEstagio = ESTAGIO_DESCANSANDO;
                mCron.zerar();
                mDescansando = 0;
                mDeveDescansar = Aleatorio.aleatorio_entre(15, 30);
            }
        } else if (mEstagio == ESTAGIO_DESCANSANDO) {
            mPassos = 0;
            if (aguardou) {
                mDescansando += 1;
                if (mCansaco > 0) {
                    mCansaco -= 1;
                }
                if (mDescansando >= mDeveDescansar) {
                    mEstagio = ESTAGIO_NORMAL;
                    mEnergia += 5000;
                    mDeveDescansar = 0;
                }
            }

            if (mBatimentos > 50) {
                mBatimentos -= Aleatorio.aleatorio_entre(2, 8);
            }

        } else if (mEstagio == ESTAGIO_DORMINDO) {
            mTempoDormindo += 1;

            if (mBatimentos > 30) {
                mBatimentos -= Aleatorio.aleatorio_entre(0, 3);
            }

            if (mTempoDormindo > mTempoDormindoMaximo) {
                mEstagio = ESTAGIO_NORMAL;
                mTempoDormindo = 0;
                mTempoAcordadoMaximo = Aleatorio.aleatorio_entre(1500, 2000);
            }
        }
    }


    public void render(Renderizador g) {

        if (mEstagio == ESTAGIO_NORMAL) {
            g.drawRect_Pintado(mX * 10, mY * 10, 10, 10, mCores.getVerde());
        } else {
            g.drawRect_Pintado(mX * 10, mY * 10, 10, 10, mCores.getVermelho());
        }
    }
}
