package libs.luan;

import java.util.Iterator;

public class TabelaHash<T1, T2> implements Iterable<LinhaHash<T1, T2>> {

    private Hasher<T1> mHasher;
    private Igualdade<T1> mIgualdade;

    private final int LIMITE = 1000;
    private Vetor<LinhaHash<T1, T2>> mDados;

    public TabelaHash(Hasher<T1> eHasher, Igualdade<T1> eIgualdade) {

        mHasher = eHasher;
        mIgualdade = eIgualdade;
        mDados = new Vetor<LinhaHash<T1, T2>>(LIMITE);
        for (int v = 0; v < LIMITE; v++) {
            mDados.set(v, new LinhaHash<T1, T2>(mIgualdade, v));
        }
    }

    public void set(T1 eChave, T2 eValor) {
        if (existe(eChave)) {

            LinhaHash<T1, T2> item = getItem(eChave);
            item.set(eChave, eValor);

        } else {
            mDados.get(mHasher.hash(eChave)).set(eChave, eValor);
        }
    }

    public boolean existe(T1 eChave) {
        return mDados.get(mHasher.hash(eChave)).existe(eChave);
    }

    public T2 get(T1 eChave) {
        return mDados.get(mHasher.hash(eChave)).get(eChave);
    }

    public T2 getOuPadrao(T1 eChave,T2 padrao) {
        if(!existe(eChave)){
            set(eChave,padrao);
        }
        return mDados.get(mHasher.hash(eChave)).get(eChave);
    }

    public boolean remover(T1 eChave) {
        return mDados.get(mHasher.hash(eChave)).remover(eChave);
    }

    public LinhaHash<T1, T2> getItem(T1 eChave) {
        return mDados.get(mHasher.hash(eChave));
    }

    public LinhaHash<T1, T2> index(int i) {
        return mDados.get(i);
    }

    public int getQuantidade() {
        int quantidade = 0;
        for (LinhaHash<T1, T2> linha : this) {
            quantidade += linha.getQuantidade();
        }
        return quantidade;
    }

    public Iterator<LinhaHash<T1, T2>> iterator() {
        return new IteradorDaTabelaHash(mDados);
    }

    private class IteradorDaTabelaHash implements Iterator<LinhaHash<T1, T2>> {

        private int index = 0;
        private Vetor<LinhaHash<T1, T2>> mDados;

        public IteradorDaTabelaHash(Vetor<LinhaHash<T1, T2>> eDados) {
            mDados = eDados;
            index = 0;
        }

        public boolean hasNext() {
            return index < mDados.getCapacidade();
        }

        public LinhaHash<T1, T2> next() {
            LinhaHash<T1, T2> eValor = mDados.get(index);

            index += 1;

            return eValor;
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");

        }
    }


    public Lista<T1> getChaves() {

        Lista<T1> chaves = new Lista<T1>();

        for (LinhaHash<T1, T2> linha : this) {

            if (linha.estaOcupada()) {
                chaves.adicionar(linha.getChave());

                for (LinhaHashColisao<T1, T2> colisao : linha.getColisoes()) {
                    chaves.adicionar(colisao.getChave());
                }

            }
        }

        return chaves;
    }

    public Lista<T2> getValores() {

        Lista<T2> valores = new Lista<T2>();

        for (LinhaHash<T1, T2> linha : this) {

            if (linha.estaOcupada()) {
                valores.adicionar(linha.getValor());

                for (LinhaHashColisao<T1, T2> colisao : linha.getColisoes()) {
                    valores.adicionar(colisao.getValor());
                }

            }
        }

        return valores;
    }


    public Lista<Par<T1, T2>> toPares() {
        Lista<Par<T1, T2>> pares = new Lista<Par<T1, T2>>();

        for (LinhaHash<T1, T2> linha : this) {

            if (linha.estaOcupada()) {
                pares.adicionar(new Par<T1, T2>(linha.getChave(), linha.getValor()));
                for (LinhaHashColisao<T1, T2> colisao : linha.getColisoes()) {
                    pares.adicionar(new Par<T1, T2>(colisao.getChave(), colisao.getValor()));
                }
            }
        }


        return pares;
    }

}
