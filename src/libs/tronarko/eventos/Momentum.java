package libs.tronarko.eventos;

import libs.luan.Lista;
import libs.tronarko.Tozte;
import libs.tronarko.utils.EventoLegenda;

public class Momentum {

    public void olharAoRedor(Tozte mAtualmente) {

        System.out.println();
        System.out.println("Hoje : " + mAtualmente.getTexto());

        Tozte mAntes = mAtualmente.adicionar_Superarko(-100);
        Tozte mDepois = mAtualmente.adicionar_Superarko(+100);

        Eventum mEventum = new Eventum();

        Lista<EventoLegenda> mInfos = mEventum.getToztesComCorEmIntervalo(mAntes, mDepois);

        for (EventoLegenda eEventoLegenda : mInfos) {

            String nome = eEventoLegenda.getNome() + " :: " + eEventoLegenda.getTozte().getTexto();
            int distancia = getDistancia(mAtualmente, eEventoLegenda.getTozte());

            System.out.println(" -->> " + nome + " -->> " + distancia);
        }


    }

    public static int getDistancia(Tozte eReferencia, Tozte eAlgumTozte) {

        int valor = 0;

        Tozte eOutro_Ref = eReferencia.getCopia();
        Tozte eOutro_AlgumTozte = eAlgumTozte.getCopia();

        if (eOutro_Ref.isMaiorQue(eOutro_AlgumTozte)) {

            while (eOutro_Ref.isMaiorQue(eOutro_AlgumTozte)) {
                eOutro_Ref = eOutro_Ref.adicionar_Superarko(-1);
                valor -= 1;
            }

        } else if (eOutro_Ref.isMenorQue(eOutro_AlgumTozte)) {

            while (eOutro_Ref.isMenorQue(eOutro_AlgumTozte)) {
                eOutro_Ref = eOutro_Ref.adicionar_Superarko(+1);
                valor += 1;
            }

        }

        return valor;

    }


}
