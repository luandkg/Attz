package apps.app_biotzum.acontecimentos;

import apps.app_biotzum.*;
import libs.arquivos.binario.Inteiro;
import libs.luan.Lista;
import libs.luan.TabelaHash;
import libs.tronarko.Tronarko;

public class AcontecimentoMundano  extends  AcontecimentoTemporal {

    private Biotzum mBiotzum;
    private Lista<Organismo> mOrganismos;
    private Lista<Comida> mComidas;
    private TabelaHash<Integer, BiotzumTreinando> mTreinamentos;

    public AcontecimentoMundano(int eTempo,Biotzum eBiotzum, Lista<Organismo> eOrganismos,Lista<Comida> eComidas) {
        super(eTempo);
        mBiotzum=eBiotzum;
        mOrganismos=eOrganismos;
        mComidas=eComidas;

        mTreinamentos = new TabelaHash<>(Inteiro.HASH(),Inteiro.IGUALDADE());
    }

    @Override
    public void emAcontece() {

        mBiotzum.adicionarUzzon(10);
        for (Comida comida : mComidas) {
            comida.atualizar();
        }

        for (Organismo org : mOrganismos) {
            org.atualizar(mOrganismos, mComidas);

            mTreinamentos.getOuPadrao(org.getID(),new BiotzumTreinando(org.getID())).atualizar(org);

            if( mTreinamentos.get(org.getID()).comecou()){
                Loggum.ORGANISMO_TREINO_INICIAR(org, Tronarko.getTronAgora().getTextoZerado(),mTreinamentos.get(org.getID()).getTreinoID());
            }

            if( mTreinamentos.get(org.getID()).terminou()){
                Loggum.ORGANISMO_TREINO_TERMINAR(org, Tronarko.getTronAgora().getTextoZerado(),mTreinamentos.get(org.getID()).getTreinoID());
            }

        }

    }

}
