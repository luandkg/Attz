package apps.app_biotzum;

public class BiotzumTreinando {

    private int mID;
    private int mContagemParaIniciar = 0;
    private int mContagemTerminar = 0;

    private final int TREINAMENTO_INICIAR = 100;
    private final int TREINAMENTO_TERMINAR = 100;

    private int mTreinoID=0;
    private boolean mComecou = false;
    private boolean mTerminou=false;

    public BiotzumTreinando(int eID){
        mID=eID;
        mContagemParaIniciar=0;
        mContagemTerminar=0;
        mComecou=false;
        mTerminou=false;
        mTreinoID=0;
    }

    public int getID(){
        return mID;
    }

    public void atualizar(Organismo org){

        mComecou=false;
        mTerminou=false;

        if(mContagemParaIniciar<TREINAMENTO_INICIAR){
            if(org.getBatimentos()>=60){
                mContagemParaIniciar+=1;
            }else{
                mContagemParaIniciar=0;
            }
        }else{

            if(mContagemParaIniciar==(TREINAMENTO_INICIAR+1)) {
                if(org.getBatimentos()<40 || org.getEstagioTexto().contains("Descansando") || org.getEstagioTexto().contains("Dormindo")){
                    mContagemTerminar+=1;
                }else{
                    mContagemTerminar=0;
                }
            }

            if(mContagemParaIniciar==TREINAMENTO_INICIAR){
                mContagemParaIniciar+=1;
                System.out.println("++ Org "+mID +" comecou a treinar !!!");
                mComecou=true;
                mTreinoID+=1;
            }

            if(mContagemTerminar>=TREINAMENTO_TERMINAR){
                System.out.println("-- Org "+mID +" terminou de treinar !!!");

                mContagemParaIniciar=0;
                mContagemTerminar=0;
                mTerminou=true;
            }

        }

    }

    public boolean comecou(){
        return mComecou;
    }

    public boolean terminou(){
        return mTerminou;
    }

    public int getTreinoID(){
        return mTreinoID;
    }
}
