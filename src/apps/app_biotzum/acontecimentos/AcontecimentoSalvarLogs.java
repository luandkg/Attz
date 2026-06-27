package apps.app_biotzum.acontecimentos;

import apps.app_biotzum.AcontecimentoTemporal;
import apps.app_biotzum.Biotzum;
import apps.app_biotzum.Organismo;
import apps.app_biotzum.Loggum;
import libs.arquivos.binario.Inteiro;
import libs.luan.*;
import libs.tronarko.Tronarko;

public class AcontecimentoSalvarLogs extends AcontecimentoTemporal {

    private Biotzum mBiotzum;
    private Lista<Organismo> mOrganismos;
    private String mBioCronTron = "";
    private int mArkoCorrente = -1;

    private TabelaHash<Integer, Integer> mResumoPassos;

    private TabelaHash<Integer, Integer> mResumoBatimentosMedia;
    private TabelaHash<Integer, Integer> mResumoBatimentosMinimo;
    private TabelaHash<Integer, Integer> mResumoBatimentosMaximo;

    private TabelaHash<Integer, String> mResumoEstagio;

    private int mBatimentos = 0;


    public AcontecimentoSalvarLogs(int eTempo, Biotzum eBiotzum, Lista<Organismo> eOrganismos) {
        super(eTempo);
        mBiotzum=eBiotzum;
        mOrganismos = eOrganismos;

        mResumoEstagio = new TabelaHash<Integer, String>(Inteiro.HASH(), Inteiro.IGUALDADE());

        mResumoPassos = new TabelaHash<Integer, Integer>(Inteiro.HASH(), Inteiro.IGUALDADE());
        mResumoBatimentosMedia = new TabelaHash<Integer, Integer>(Inteiro.HASH(), Inteiro.IGUALDADE());
        mResumoBatimentosMinimo = new TabelaHash<Integer, Integer>(Inteiro.HASH(), Inteiro.IGUALDADE());
        mResumoBatimentosMaximo = new TabelaHash<Integer, Integer>(Inteiro.HASH(), Inteiro.IGUALDADE());

        for (Organismo org : eOrganismos) {

            mResumoEstagio.set(org.getID(), org.getEstagioTexto());

            mResumoPassos.set(org.getID(), 0);

            mResumoBatimentosMedia.set(org.getID(), 0);
            mResumoBatimentosMinimo.set(org.getID(), 0);
            mResumoBatimentosMaximo.set(org.getID(), 0);
        }

    }

    @Override
    public void emAcontece() {

        String agora = mBiotzum.getTron().getTextoZerado();
        int arko_corrente = mBiotzum.getTron().getHazde().getItta();

        if (Strings.isDiferente(mBioCronTron, agora)) {

            for (Organismo org : mOrganismos) {
                if (Strings.isIgual(mResumoEstagio.get(org.getID()), "Normal") && Strings.isIgual(org.getEstagioTexto(), "Dormindo")) {
                    Loggum.ORGANISMO_DORME_INICIAR(org,agora);
                } else if (Strings.isIgual(mResumoEstagio.get(org.getID()), "Dormindo") && Strings.isIgual(org.getEstagioTexto(), "Normal")) {
                    Loggum.ORGANISMO_DORME_TERMINAR(org,agora);
                }
                mResumoEstagio.set(org.getID(), org.getEstagioTexto());
            }


            boolean adicionarResumo = false;
            boolean deveZerar = false;

            if (arko_corrente == mArkoCorrente) {

                for (Organismo org : mOrganismos) {
                    mResumoPassos.set(org.getID(), mResumoPassos.get(org.getID()) + org.getPassos());
                    mResumoBatimentosMedia.set(org.getID(), mResumoBatimentosMedia.get(org.getID()) + org.getBatimentos());

                    if (org.getBatimentos() < mResumoBatimentosMinimo.get(org.getID())) {
                        mResumoBatimentosMinimo.set(org.getID(), org.getBatimentos());
                    }

                    if (org.getBatimentos() > mResumoBatimentosMaximo.get(org.getID())) {
                        mResumoBatimentosMaximo.set(org.getID(), org.getBatimentos());
                    }

                }
                mBatimentos += 1;

            } else {

                if (arko_corrente >= 0) {
                    adicionarResumo = true;
                }

                mArkoCorrente = arko_corrente;
                deveZerar = true;

            }

            Loggum.ATUALIZAR(mOrganismos, agora);

            if (adicionarResumo) {
                Loggum.ATUALIZAR_RESUMO_PASSOS(mResumoPassos, agora);
                if (mBatimentos > 0) {
                    Loggum.ATUALIZAR_RESUMO_BATIMENTOS(mBatimentos, mResumoBatimentosMedia, mResumoBatimentosMinimo, mResumoBatimentosMaximo, agora);
                }
            }

            if (deveZerar) {
                for (Organismo org : mOrganismos) {
                    mResumoPassos.set(org.getID(), org.getPassos());

                    mResumoBatimentosMedia.set(org.getID(), org.getBatimentos());
                    mResumoBatimentosMinimo.set(org.getID(), org.getBatimentos());
                    mResumoBatimentosMaximo.set(org.getID(), org.getBatimentos());

                }
                mBatimentos = 1;
            }


        }

        mBioCronTron = agora;
    }

}
