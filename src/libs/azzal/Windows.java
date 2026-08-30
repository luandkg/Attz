package libs.azzal;

import libs.arquivos.audio.HZ;
import libs.arquivos.audio.HZControlador;
import libs.azzal.cenarios.Cena;
import libs.azzal.cenarios.Cenarios;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Windows extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private Tempo mTempo;
    private boolean mExecutando;

    private int mLargura;
    private int mAltura;

    private BufferedImage mImagem;

    private Renderizador mRenderizador;

    private int mCenaID;
    private Cena mCena;
    private Cenarios mCenarios;

    private Mouse mMouse;
    private Teclado mTeclado;

    private boolean mTemAudio = false;
    private HZ audio;

    public Windows(String eTitulo, int eLargura, int eAltura) {

        mLargura = eLargura;
        mAltura = eAltura;

        this.setSize(eLargura, eAltura);
        this.setTitle(eTitulo);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        if (mImagem == null) {
            mImagem = new BufferedImage(this.getLargura(), this.getAltura(), BufferedImage.TYPE_INT_ARGB);
        }

        mRenderizador = new Renderizador(mImagem);

        mTempo = new Tempo();
        mExecutando = false;

        mCenarios = new Cenarios();

        mMouse = new Mouse();

        this.addMouseMotionListener(mMouse);
        this.addMouseListener(mMouse);

        mTeclado = new Teclado();
        this.addKeyListener(mTeclado);

    }

    public Windows(String eTitulo, int eLargura, int eAltura,int px,int py) {

        mLargura = eLargura;
        mAltura = eAltura;

        this.setSize(eLargura, eAltura);
        this.setTitle(eTitulo);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLocation(px,py);

        if (mImagem == null) {
            mImagem = new BufferedImage(this.getLargura(), this.getAltura(), BufferedImage.TYPE_INT_ARGB);
        }

        mRenderizador = new Renderizador(mImagem);

        mTempo = new Tempo();
        mExecutando = false;

        mCenarios = new Cenarios();

        mMouse = new Mouse();

        this.addMouseMotionListener(mMouse);
        this.addMouseListener(mMouse);

        mTeclado = new Teclado();
        this.addKeyListener(mTeclado);

    }

    public Mouse getMouse() {
        return mMouse;
    }

    public Teclado getTeclado() {
        return mTeclado;
    }

    public void audio_emitir(HZ eAudio) {

        audio = eAudio;
        mTemAudio = true;

    }

    public void audio_retirar() {
        mTemAudio = false;
    }

    public void tocar() {
        if (audio.temMais()) {
            HZControlador.toque(audio);
        } else {
            // mTemAudio = false;
        }
    }

    public boolean temAudio() {
        return mTemAudio;
    }

    public HZ getAudio() {
        return audio;
    }

    @Override
    public void run() {

        mExecutando = true;

        // GAME LOOP FREITAS - 2020 07 16

        final int GAME_HERTIZ = 30;
        final double GAME_QUADRO = 1000000000 / (double) GAME_HERTIZ;

        int mAtualizador = 0;
        int mDesenhador = 0;
        int mDilatador = 0;
        int mVerificador = 0;
        int mAcumulador = 0;

        double mPassado = System.nanoTime();
        double mFuturo = mPassado + GAME_QUADRO;

        double mDilatacao = 0.0;
        double mDilatando = 0.0;
        double mDilatadorMin = mPassado;
        double mDilatadorMax = mPassado + ((double) (GAME_HERTIZ) * GAME_QUADRO);

        while (mExecutando) {

            double mPresente = System.nanoTime();
            boolean mDesenhar = false;

            if (mTemAudio) {
                tocar();
            }

            if (mPresente >= mFuturo) {
                mAtualizador += 1;

                // System.out.println("\t - Desenhando " + mDesenhador);
                mFuturo = (mPresente - (mPresente - mFuturo)) + GAME_QUADRO;

                mCena.update(mPresente - mPassado);

                mDesenhar = true;

            }

            if (mPresente >= mDilatadorMax) {

                mDilatacao = mPresente - mDilatadorMin;
                mDilatando = mDilatacao;
                mVerificador = 0;

                while (mDilatando > GAME_QUADRO) {
                    mDilatando -= GAME_QUADRO;
                    mVerificador += 1;
                }

                // System.out.println("GAME LOOP : " + (mDilatacao) + " << " + mAtualizador +
                // "," + mVerificador + "," + mDilatador + " >> :: " + mDesenhador);

                mDilatadorMin = mPresente;
                mDilatadorMax = mPresente + ((double) (GAME_HERTIZ) * GAME_QUADRO);

                mAcumulador += mDilatador;

                if (mAcumulador >= GAME_HERTIZ) {
                    // System.out.println("\t - ACUMULADO : " + (mAcumulador) + " - Desacumulando "
                    // + GAME_HERTIZ);
                    mAcumulador -= GAME_HERTIZ;

                    mCena.update(mPresente - mPassado);
                    mDesenhar = true;

                }

                mDilatador = 0;
                mAtualizador = 0;
                mDesenhador = 0;
            } else {
                if (mAtualizador >= GAME_HERTIZ) {
                    mDilatador += 1;
                }
            }

            if (mDesenhar) {
                mDesenhador += 1;
                mCena.draw(mRenderizador);
                getGraphics().drawImage(mImagem, 0, 0, getLargura(), getAltura(), null);
            }

            // try {
            // Thread.yield();
            // Thread.sleep(1);
            // } catch (Exception e) {
            // System.out.println("GAME LOOP - PROBLEMA !");
            // }

            mPassado = System.nanoTime();

        }

        mExecutando = false;
    }

    public int getLargura() {
        return mLargura;
    }

    public int getAltura() {
        return mAltura;
    }

    public BufferedImage getImagem() {
        return mImagem;
    }

    public int CriarCenario(Cena eCena) {
        return mCenarios.CriarCenario(eCena);
    }

    public void setCenario(int eCenaID) {
        mCena = mCenarios.getCenario(eCenaID).getCena();
        mCenaID = eCenaID;
        mCena.setWindows(this);
        mCena.iniciar(this);
    }

    public int CriarCenarioAplicavel(Cena eCena) {
        int eID = mCenarios.CriarCenario(eCena);
        setCenario(eID);
        return eID;
    }

}