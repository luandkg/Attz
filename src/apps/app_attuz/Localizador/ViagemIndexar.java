package apps.app_attuz.Localizador;

import apps.app_attuz.Viagem.Viajante;
import libs.arquivos.BZ2;
import libs.arquivos.TX;
import libs.dkg.AutoDKG;
import libs.dkg.DKG;
import libs.dkg.DKGObjeto;
import libs.luan.Lista;
import libs.tronarko.Hazde;
import libs.tronarko.Tozte;
import libs.tronarko.utils.IntTronarko;
import libs.tronarko.utils.StringTronarko;

public class ViagemIndexar {


    public static void indexar(String eArquivoViagem, String eArquivoBZZ) {


        DKG t2 = new DKG();
        t2.abrir(eArquivoViagem);

        System.out.println("Itens :: " + t2.unicoObjeto("Viagem").getObjetos().getQuantidade());

        StringTronarko st = new StringTronarko();

        if (t2.unicoObjeto("Viagem").getObjetos().getQuantidade() > 0) {


            int primeiro = Integer.parseInt(st.getTronarko(t2.unicoObjeto("Viagem").getObjetos().get(0).identifique("Tozte").getValor()));

            int ultimo_indice = t2.unicoObjeto("Viagem").getObjetos().getQuantidade() - 1;

            int ultimo_tronarko = Integer.parseInt(st.getTronarko(t2.unicoObjeto("Viagem").getObjetos().get(ultimo_indice).identifique("Tozte").getValor()));
            int ultimo_hiperarko = Integer.parseInt(st.getHiperarko(t2.unicoObjeto("Viagem").getObjetos().get(ultimo_indice).identifique("Tozte").getValor()));
            int ultimo_superarko = Integer.parseInt(st.getSuperarko(t2.unicoObjeto("Viagem").getObjetos().get(ultimo_indice).identifique("Tozte").getValor()));


            Tozte eTozteCorrente = new Tozte(1, 1, primeiro);

            Tozte eTozteUltimo = new Tozte(ultimo_superarko, ultimo_hiperarko, ultimo_tronarko);


            int TOZTE_PRIMEIRO = 3501196;

            System.out.println("Comecar -->> " + (eTozteCorrente.getSuperarkosTotal() - TOZTE_PRIMEIRO));

            while (eTozteCorrente.getSuperarkosTotal() < eTozteUltimo.getSuperarkosTotal()) {
                DKG viagem_aqui = new DKG();
                DKGObjeto va = viagem_aqui.unicoObjeto("Viagem");


                for (DKGObjeto o : t2.unicoObjeto("Viagem").getObjetos()) {

                    String o_tozte = o.identifique("Tozte").getValor();
                    String f_tozte = st.getSuperarko(o_tozte) + "/" + st.getHiperarko(o_tozte) + "/" + st.getTronarko(o_tozte);


                    if (f_tozte.contentEquals(eTozteCorrente.getTexto())) {
                        va.getObjetos().adicionar(o);
                    }

                }


                Lista<Byte> bytes = TX.toListBytes(viagem_aqui.toString());

                int tt = bytes.getQuantidade() / 1024;

                int indice = (int) (eTozteCorrente.getSuperarkosTotal() - TOZTE_PRIMEIRO);

                if (indice >= 0 && tt <= 5) {
                    System.out.println("Pass -->> (" + indice + " ) " + eTozteCorrente.getTexto() + " :: " + va.getObjetos().getQuantidade() + " objs -->> " + tt + " kb com :: " + bytes.getQuantidade());
                    BZ2.atribuir(eArquivoBZZ, ((int) (eTozteCorrente.getSuperarkosTotal() - TOZTE_PRIMEIRO)), viagem_aqui.toString());
                }


                eTozteCorrente = eTozteCorrente.adicionar_Superarko(1);
            }

        }


    }

    public static String obterIndexadoString(String eArquivo, String eTozteProcurado) {

        StringTronarko st = new StringTronarko();

        int p_tronarko = Integer.parseInt(st.getTronarko(eTozteProcurado));
        int p_hiperarko = Integer.parseInt(st.getHiperarko(eTozteProcurado));
        int p_superarko = Integer.parseInt(st.getSuperarko(eTozteProcurado));

        Tozte eProcurado = new Tozte(p_superarko, p_hiperarko, p_tronarko);

        return obterIndexado(eArquivo, eProcurado);
    }

    public static String obterIndexado(String eArquivo, Tozte eTozteProcurado) {


        int ii = 3501196;

        int indice_chave = (int) (eTozteProcurado.getSuperarkosTotal() - ii);

        System.out.println(indice_chave);

        if (indice_chave >= 0) {
            String conteudo = BZ2.procurar(eArquivo, indice_chave);
            return conteudo;
        }

        return "";
    }

    public static String procurando(String eArquivo, Viajante EU, Tozte eTozte, Hazde eHazde) {

        Tozte eProcurado = new Tozte(eTozte.getSuperarko(), eTozte.getHiperarko(), eTozte.getTronarko());

        String dados = obterIndexado(eArquivo, eProcurado);


        if (dados.length() > 0) {

            DKG eDKG = new DKG();
            eDKG.parser(dados);

            DKGObjeto eViagem = eDKG.unicoObjeto("Viagem");

            int valor = (eTozte.getHiperarko() * 50 * 10 * 100) + (eTozte.getSuperarko() * 10 * 100) + (eHazde.getArco() * 100) + eHazde.getItta();

            //  System.out.println("V :: " + valor);

            String estou = "---";

            String primeiro = "";
            String ultimo = "";

            int i = 0;

            boolean isVisitando = false;
            String visita_comecar = "";

            boolean isViajando = false;
            String viagem_comecar = "";
            String trilha_comecar = "";
            String cidade_chegou = "";

            String momento = "";

            for (DKGObjeto ePonto : eViagem.getObjetos()) {

                int eValor = ePonto.identifique("Valor").getInteiro(0);

                //  System.out.println("T :: " + ePonto.identifique("Tozte").getValor());
                //  System.out.println("V :: " + ePonto.identifique("Valor").getValor());


                if (eValor < valor) {
                    estou = ePonto.identifique("x").getValor() + ":" + ePonto.identifique("y").getValor();


                    if (ePonto.existeIdentificador("Cidade")) {
                        estou += " :: Visitando " + ePonto.identifique("Cidade").getValor();

                        if (!isVisitando) {
                            visita_comecar = ePonto.identifique("Tozte").getValor();
                        }

                        isVisitando = true;
                        isViajando = false;

                        momento = ePonto.identifique("Momento").getValor();
                        cidade_chegou = ePonto.identifique("ChegouCidade").getValor();

                    } else {
                        estou += " :: Viajando...";

                        if (!isViajando) {
                            viagem_comecar = ePonto.identifique("Tozte").getValor();
                        }

                        isVisitando = false;
                        isViajando = true;
                        trilha_comecar = ePonto.identifique("TrilhaComecou").getValor();

                    }

                    if (i == 0) {
                        primeiro = ePonto.identifique("Tozte").getValor() + " :: " + estou;
                    }

                    ultimo = ePonto.identifique("Tozte").getValor() + " :: " + estou;

                    EU.setPos(ePonto.identifique("x").getInteiro(), ePonto.identifique("y").getInteiro());

                    i += 1;

                } else {
                    break;
                }


            }

            System.out.println("Inicio do Superarko :: " + primeiro);
            System.out.println("Agora nesse momento :: " + ultimo);

            if (isVisitando) {
                ViajemAcao.onVisita(cidade_chegou, visita_comecar);
            }

            if (isViajando) {
                ViajemAcao.onEscurcao(trilha_comecar, viagem_comecar);
            }

            return estou;

        }

        return "";
    }

    public static void passeiBZZ(String eArquivo, Tozte eTozte, Hazde eHazde, Tozte eHoje) {

        int indiceBZZ = 0;

        boolean encontradoBZZ = false;

        int procurando_ittas = IntTronarko.getIttas(eHazde.getArco(), eHazde.getItta());

        int blocosBZZ = BZ2.getQuantidadeMaxima(eArquivo);

        boolean enc_fim = false;
        DKGObjeto fim = null;

        FluxoDeViagem viajando = new FluxoDeViagem();

        while (!encontradoBZZ && indiceBZZ < blocosBZZ) {

            //System.out.println("BLOCO BZZ -->> " + indiceBZZ);

            String conteudo_bloco = BZ2.procurar(eArquivo, indiceBZZ);


            DKG eDKC = AutoDKG.auto(conteudo_bloco);

            long eTozteRef_min = eTozte.getSuperarkosTotal();

            StringTronarko st = new StringTronarko();

            for (DKGObjeto ePonto : eDKC.unicoObjeto("Viagem").getObjetos()) {

                String visita_comecar = ePonto.identifique("Tozte").getValor();

                Tozte viagem_tozte = st.TOZTE_PARSER(visita_comecar);
                Hazde viagem_hazde = st.getHazdeDeComplexoSemUzzon(visita_comecar);


                long superarkos_valor = viagem_tozte.getSuperarkosTotal();

                int ittas_total = viagem_hazde.getIttasTotal();

                if (superarkos_valor < eTozteRef_min) {

                    ePonto.identifique("TozteValor", superarkos_valor);
                    viajando.passar(ePonto, eHoje);

                } else if (superarkos_valor == eTozteRef_min && ittas_total <= procurando_ittas) {

                    ePonto.identifique("TozteValor", superarkos_valor);
                    viajando.passar(ePonto, eHoje);

                    viajando.viagem_comecou = ePonto.identifique("TrilhaComecou").getValor();
                    viajando.viagem_terminou = ePonto.identifique("Tozte").getValor();

                    enc_fim = true;
                    fim = ePonto;
                    encontradoBZZ = true;


                }

            }


            indiceBZZ += 1;
        }

        if (enc_fim) {

            if (fim.existeIdentificador("Cidade")) {

                System.out.println("");
                System.out.println("\t -->> Hoje :: " + fim.identifique("Tozte").getValor() + " :: " + fim.identifique("Cidade").getValor());
                System.out.println("");

                System.out.println("\t      Passeio Inicio :: " + fim.identifique("ChegouCidade").getValor());
                System.out.println("\t      Passeio Duracao -->> " + IntTronarko.intervalo(fim.identifique("ChegouCidade").getValor(), fim.identifique("Tozte").getValor()));

            } else {

                System.out.println("");
                System.out.println("\t -->> Hoje   :: " + fim.identifique("Tozte").getValor());
                System.out.println("");
                System.out.println("\t -->> Viagem :: " + fim.identifique("TrilhaComecou").getValor() + " :: saiu de " + fim.identifique("Saiu").getValor());

                viajando.mostrarViagemAtual();
            }

        }
    }


}
