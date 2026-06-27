package apps.app_dajin;

public class Token {

    private int mLinha;
    private int mColuna;
    private TokenTipo mTipo;
    private String mValor;

    public Token(int eLinha,int eColuna,TokenTipo eTipo,String eValor){
        mLinha = eLinha;
        mColuna = eColuna;
        mTipo = eTipo;
        mValor = eValor;
    }

    public int getLinha(){
        return mLinha;
    }

    public int getColuna(){
        return mColuna;
    }

    public TokenTipo getTipo(){
        return mTipo;
    }

    public String getValor(){
        return mValor;
    }

    public boolean isValor(String com){
        return mValor.contentEquals(com);
    }

    public boolean isValorDiferente(String com){
        return !mValor.contentEquals(com);
    }
}
