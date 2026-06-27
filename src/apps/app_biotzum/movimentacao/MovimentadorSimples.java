package apps.app_biotzum.movimentacao;

import apps.app_biotzum.Organismo;
import libs.arquivos.binario.Inteiro;
import libs.luan.Aleatorio;
import libs.luan.Lista;

public class MovimentadorSimples implements Movimentador {

    private Organismo mOrganismo;

    private MovimentacaoSentido mSentido;
    private int mMovimentacaoSentidoContador;
    private int mMovimentacaoSentidoMaximo;

    private int mMovimentacaoMudancaSentidoContador;
    private int mMovimentacaoMudancaSentidoMaximo;


    private boolean mSingalX;
    private int mSingalXContador;
    private int mSinalXMaximo;

    private boolean mSingalY;
    private int mSingalYContador;
    private int mSinalYMaximo;


    public MovimentadorSimples(Organismo eOrganismo) {
        mOrganismo = eOrganismo;
        mSentido = MovimentacaoSentido.Horizontal;
        mMovimentacaoSentidoContador = 0;
        mMovimentacaoSentidoMaximo = Aleatorio.aleatorio_entre(3, 10);

        mMovimentacaoMudancaSentidoContador = 0;
        mMovimentacaoMudancaSentidoMaximo = Aleatorio.aleatorio_entre(15, 30);

        int sentido = Aleatorio.aleatorio_entre(1, 3);

        if (sentido == 1) {
            mSentido = MovimentacaoSentido.Horizontal;
        } else if (sentido == 2) {
            mSentido = MovimentacaoSentido.Vertical;
        } else {
            mSentido = MovimentacaoSentido.Diagonal;
        }

        mSingalX = true;
        mSingalXContador = 0;
        mSinalXMaximo = Aleatorio.aleatorio_entre(20, 40);

        mSingalY = true;
        mSingalYContador = 0;
        mSinalYMaximo = Aleatorio.aleatorio_entre(20, 40);
    }

    public Movimento andar(Lista<Organismo> outros) {

        int ret_x=0;
        int ret_y=0;
        int ret_gasto=0;
        int ret_passos=0;

        int mover_x = 0;
        int mover_y = 0;

        if (mMovimentacaoMudancaSentidoContador < mMovimentacaoMudancaSentidoMaximo) {
            mMovimentacaoMudancaSentidoContador += 1;
        } else {
            mMovimentacaoMudancaSentidoContador = 0;
            mMovimentacaoMudancaSentidoMaximo = Aleatorio.aleatorio_entre(15, 30);

            int sentido = Aleatorio.aleatorio_entre(1, 3);

            if (sentido == 1) {
                mSentido = MovimentacaoSentido.Horizontal;
            } else if (sentido == 2) {
                mSentido = MovimentacaoSentido.Vertical;
            } else {
                mSentido = MovimentacaoSentido.Diagonal;
            }
        }

        if (mSingalXContador < mSinalXMaximo) {
            mSingalXContador += 1;
        } else {
            mSingalX = Aleatorio.aleatorio(100) > 50 ;
            mSingalXContador = 0;
            mSinalXMaximo = Aleatorio.aleatorio_entre(20, 40);
        }

        if (mSingalYContador < mSinalYMaximo) {
            mSingalYContador += 1;
        } else {
            mSingalY = Aleatorio.aleatorio(100) > 50 ;
            mSingalYContador = 0;
            mSinalYMaximo = Aleatorio.aleatorio_entre(20, 40);
        }


        if (mMovimentacaoSentidoContador < mMovimentacaoSentidoMaximo) {
            mMovimentacaoSentidoContador += 1;

            if (mSentido == MovimentacaoSentido.Horizontal) {
                if (mSingalX) {
                    mover_x = Aleatorio.aleatorio_entre(0, 3);
                } else {
                    mover_x = Aleatorio.aleatorio_entre(-3, 0);
                }
            } else if (mSentido == MovimentacaoSentido.Vertical) {

                if (mSingalY) {
                    mover_y = Aleatorio.aleatorio_entre(0, 3);
                } else {
                    mover_y = Aleatorio.aleatorio_entre(-3, 0);
                }

            } else {
                if (mSingalX) {
                    mover_x = Aleatorio.aleatorio_entre(0, 3);
                } else {
                    mover_x = Aleatorio.aleatorio_entre(-3, 0);
                }
                if (mSingalY) {
                    mover_y = Aleatorio.aleatorio_entre(0, 3);
                } else {
                    mover_y = Aleatorio.aleatorio_entre(-3, 0);
                }
            }

        } else {
            mMovimentacaoSentidoContador = 0;
            mMovimentacaoSentidoMaximo = Aleatorio.aleatorio_entre(3, 10);
        }

        int px = mOrganismo.getX() + mover_x;
        int py = mOrganismo.getY() + mover_y;

        int gasto_de_movimentacao = mOrganismo.calcularGastoDeMovimento(mover_x, mover_y);

        if (mOrganismo.getEnergia() >= gasto_de_movimentacao) {
            if (mOrganismo.isLocalValido(px, py, outros)) {
                mOrganismo.andarDireto(mover_x, mover_y, px, py);

                ret_x=mover_x;
                ret_y=mover_y;
                ret_gasto=gasto_de_movimentacao;
                ret_passos= Inteiro.MODULO(mover_x)+Inteiro.MODULO(mover_y);

            }
        }

        return new Movimento(ret_x,ret_y,ret_gasto,ret_passos);
    }
}
