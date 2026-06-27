package apps.app_letrum.Maker;

import apps.app_letrum.Fonte;
import apps.app_letrum.Letra;
import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FonteRunTime implements Fonte {

    private BufferedImage mImagem;
    private ArrayList<Letra> mLetras;

    public int LARGURA = 0;
    public int ALTURA = 0;
    public int FONTE = 15;
    private int ESCREVA_COR = new Color(0, 0, 0).getRGB();
    private Renderizador mRenderizador;
    private Cor mCor;


    public FonteRunTime(Cor eCor, int eTamanho) {

        mCor = eCor;
        ESCREVA_COR = mCor.getValor();

        mLetras = new ArrayList<Letra>();


        String sequencia = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZçÇ_0123456789-–<>.,:;/\\+-*=()[]{}!@#$%ºª";
        sequencia += "áàâãäéèêẽëíìîĩïóòôõöúùûũü";
        sequencia += "ÁÀÂÃÄÉÈÊẼËÍÌÎĨÏÓÒÔÕÖÚÙÛŨÜ";
        sequencia += "\"\t";

        int quantidade = sequencia.length();
        for (int v = 0; v < quantidade; v++) {
            mLetras.add(new Letra(String.valueOf(sequencia.charAt(v)), 0, 0, 0, 0));
        }

        int eLinhas = 1;

        int tam = sequencia.length();
        while (tam > 10) {
            eLinhas += 1;
            tam -= 10;
        }

        int mLargura = (12 * (eTamanho + (eTamanho / 2)));
        int mAltura = (eTamanho + 20) * eLinhas;

        LARGURA = mLargura;
        ALTURA = mAltura;
        FONTE = eTamanho;

        mImagem = new BufferedImage(mLargura, mAltura, BufferedImage.TYPE_INT_ARGB);

        Graphics g = mImagem.getGraphics();

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, mLargura, mAltura);

        int i = 0;
        int e = 0;
        int o = sequencia.length();

        int x = eTamanho;
        int y = eTamanho + 5;

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, eTamanho));

        Color QuadroCor = new Color(0, 0, 0);

        while (i < o) {
            String l = String.valueOf(sequencia.charAt(i));

            String iString = String.valueOf(i);
            while (iString.length() < 2) {
                iString = "0" + iString;
            }

            g.setColor(QuadroCor);
            g.drawString(l, x, y);

            int w = g.getFontMetrics().stringWidth(l);

            g.setColor(Color.RED);

            int x1 = x - 2;
            int x2 = (x) + w;
            int y1 = y - eTamanho - 2;
            int y2 = y + (eTamanho / 2) - (eTamanho / 4);

            mLetras.get(i).redefinir(l, x1, y1, x2, y2);

            x += eTamanho + (eTamanho / 2);

            i += 1;
            e += 1;
            if (e > 10) {
                e = 0;
                y += (eTamanho + 20);
                x = eTamanho;
            }


        }

    }

    public FonteRunTime(Cor eCor, String eNome, int eTamanho) {

        mCor = eCor;
        ESCREVA_COR = mCor.getValor();

        mLetras = new ArrayList<Letra>();


        String sequencia = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZçÇ_0123456789-<>.,:;/\\+-*=()[]{}?!@#$%ºª";
        sequencia += "áàâãäéèêẽëíìîĩïóòôõöúùûũü";
        sequencia += "ÁÀÂÃÄÉÈÊẼËÍÌÎĨÏÓÒÔÕÖÚÙÛŨÜ";
        sequencia += "'\"\t";

        int quantidade = sequencia.length();
        for (int v = 0; v < quantidade; v++) {
            mLetras.add(new Letra(String.valueOf(sequencia.charAt(v)), 0, 0, 0, 0));
        }

        int eLinhas = 1;

        int tam = sequencia.length();
        while (tam > 10) {
            eLinhas += 1;
            tam -= 10;
        }

        int mLargura = (12 * (eTamanho + (eTamanho / 2)));
        int mAltura = (eTamanho + 20) * eLinhas;

        LARGURA = mLargura;
        ALTURA = mAltura;
        FONTE = eTamanho;

        mImagem = new BufferedImage(mLargura, mAltura, BufferedImage.TYPE_INT_ARGB);

        Graphics g = mImagem.getGraphics();

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, mLargura, mAltura);

        int i = 0;
        int e = 0;
        int o = sequencia.length();

        int x = eTamanho;
        int y = eTamanho + 5;

        g.setColor(Color.BLACK);
        g.setFont(new Font(eNome, Font.BOLD, eTamanho));

        Color QuadroCor = new Color(0, 0, 0);

        while (i < o) {
            String l = String.valueOf(sequencia.charAt(i));

            String iString = String.valueOf(i);
            while (iString.length() < 2) {
                iString = "0" + iString;
            }

            g.setColor(QuadroCor);
            g.drawString(l, x, y);

            int w = g.getFontMetrics().stringWidth(l);

            g.setColor(Color.RED);

            int x1 = x - 2;
            int x2 = (x) + w;
            int y1 = y - eTamanho - 2;
            int y2 = y + (eTamanho / 2) - (eTamanho / 4);

            mLetras.get(i).redefinir(l, x1, y1, x2, y2);

            x += eTamanho + (eTamanho / 2);

            i += 1;
            e += 1;
            if (e > 10) {
                e = 0;
                y += (eTamanho + 20);
                x = eTamanho;
            }


        }

    }

    public FonteRunTime(Cor eCor, String eNome, int eTamanho, boolean isBold) {

        mCor = eCor;
        ESCREVA_COR = mCor.getValor();

        mLetras = new ArrayList<Letra>();


        String sequencia = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZçÇ_0123456789-<>.,:;/\\+-*=()[]{}?!@#$%ºª";
        sequencia += "áàâãäéèêẽëíìîĩïóòôõöúùûũü";
        sequencia += "ÁÀÂÃÄÉÈÊẼËÍÌÎĨÏÓÒÔÕÖÚÙÛŨÜ";
        sequencia += "'\"\t";

        int quantidade = sequencia.length();
        for (int v = 0; v < quantidade; v++) {
            mLetras.add(new Letra(String.valueOf(sequencia.charAt(v)), 0, 0, 0, 0));
        }

        int eLinhas = 1;

        int tam = sequencia.length();
        while (tam > 10) {
            eLinhas += 1;
            tam -= 10;
        }

        int mLargura = (12 * (eTamanho + (eTamanho / 2)));
        int mAltura = (eTamanho + 20) * eLinhas;

        LARGURA = mLargura;
        ALTURA = mAltura;
        FONTE = eTamanho;

        mImagem = new BufferedImage(mLargura, mAltura, BufferedImage.TYPE_INT_ARGB);

        Graphics g = mImagem.getGraphics();

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, mLargura, mAltura);

        int i = 0;
        int e = 0;
        int o = sequencia.length();

        int x = eTamanho;
        int y = eTamanho + 5;

        g.setColor(Color.BLACK);

        if (isBold) {
            g.setFont(new Font(eNome, Font.BOLD, eTamanho));
        } else {
            g.setFont(new Font(eNome, Font.PLAIN, eTamanho));
        }


        Color QuadroCor = new Color(0, 0, 0);

        while (i < o) {
            String l = String.valueOf(sequencia.charAt(i));

            String iString = String.valueOf(i);
            while (iString.length() < 2) {
                iString = "0" + iString;
            }

            g.setColor(QuadroCor);
            g.drawString(l, x, y);

            int w = g.getFontMetrics().stringWidth(l);

            g.setColor(Color.RED);

            int x1 = x - 2;
            int x2 = (x) + w;
            int y1 = y - eTamanho - 2;
            int y2 = y + (eTamanho / 2) - (eTamanho / 4);

            mLetras.get(i).redefinir(l, x1, y1, x2, y2);

            x += eTamanho + (eTamanho / 2);

            i += 1;
            e += 1;
            if (e > 10) {
                e = 0;
                y += (eTamanho + 20);
                x = eTamanho;
            }


        }

    }

    public void setRenderizador(Renderizador eRenderizador) {
        mRenderizador = eRenderizador;
    }


    public int getAltura() {
        return FONTE;
    }

    public static int getTamanhoPequeno() {
        return 11;
    }

    public static int getTamanhoMedio() {
        return 15;
    }

    public static int getTamanhoMicro() {
        return 10;
    }


    public void escreveLinha(int y, int x1, int x2, String eTexto1, String eTexto2) {
        escreva(x1, y, eTexto1);
        escreva(x2, y, eTexto2);
    }

    public void escreveLinha(int y, int x1, int x2, String eTexto1, int eTexto2) {
        escreva(x1, y, eTexto1);
        escreva(x2, y, eTexto2);
    }


    public void escreva(int x, int y, int frase) {
        escreva(x,y,String.valueOf(frase));
    }


        public void escreva(int x, int y, String frase) {


        int i = 0;
        int o = frase.length();
        while (i < o) {
            String l = String.valueOf(frase.charAt(i));
            if (l.contentEquals(" ")) {
                x += 10;
            } else if (l.contentEquals("\t")) {
                x += 15;
            } else {
                boolean enc = false;
                Letra letraSelecionada = null;

                for (Letra e : mLetras) {
                    if (e.igual(l)) {
                        enc = true;
                        letraSelecionada = e;
                        break;
                    }
                }


                if (enc) {
                    int ox = x;
                    int yc = y;

                    for (int y1 = letraSelecionada.getY1(); y1 < letraSelecionada.getY2(); y1++) {
                        int xc = ox;
                        for (int xi = letraSelecionada.getX1(); xi < letraSelecionada.getX2(); xi++) {
                            int v = mImagem.getRGB(xi, y1);
                            if (v != 0) {
                                mRenderizador.drawPixelBruto(xc, yc, ESCREVA_COR);
                            }
                            xc += 1;
                        }
                        yc += 1;
                    }

                    x += (letraSelecionada.getX2() - letraSelecionada.getX1());
                }
            }
            i += 1;
        }
    }

    public void escrevaComOutraCor(int x, int y, String frase, Cor eOutraCor) {


        int i = 0;
        int o = frase.length();
        while (i < o) {
            String l = String.valueOf(frase.charAt(i));
            if (l.contentEquals(" ")) {
                x += 10;
            } else if (l.contentEquals("\t")) {
                x += 15;
            } else {
                boolean enc = false;
                Letra letraSelecionada = null;

                for (Letra e : mLetras) {
                    if (e.igual(l)) {
                        enc = true;
                        letraSelecionada = e;
                        break;
                    }
                }


                if (enc) {
                    int ox = x;
                    int yc = y;

                    for (int y1 = letraSelecionada.getY1(); y1 < letraSelecionada.getY2(); y1++) {
                        int xc = ox;
                        for (int xi = letraSelecionada.getX1(); xi < letraSelecionada.getX2(); xi++) {
                            int v = mImagem.getRGB(xi, y1);
                            if (v != 0) {
                                mRenderizador.drawPixelBruto(xc, yc, eOutraCor.getValor());
                            }
                            xc += 1;
                        }
                        yc += 1;
                    }

                    x += (letraSelecionada.getX2() - letraSelecionada.getX1());
                }
            }
            i += 1;
        }
    }


    public void escrevaCentralizado(int x, int y, String frase) {

        int largura = getLarguraDe(frase);

        escreva(x - (largura / 2), y, frase);

    }

    public int getLarguraDe(String frase) {

        int x = 0;
        int i = 0;
        int o = frase.length();

        while (i < o) {
            String l = String.valueOf(frase.charAt(i));
            if (l.contentEquals(" ")) {
                x += 10;
            } else if (l.contentEquals("\t")) {
                x += 15;
            } else {
                boolean enc = false;
                Letra letraSelecionada = null;

                for (Letra e : mLetras) {
                    if (e.igual(l)) {
                        enc = true;
                        letraSelecionada = e;
                        break;
                    }
                }


                if (enc) {
                    x += (letraSelecionada.getX2() - letraSelecionada.getX1());
                }
            }
            i += 1;
        }
        return x;
    }

    public Renderizador getRenderizador() {
        return mRenderizador;
    }

    public Cor getCor() {
        return mCor;
    }
}
