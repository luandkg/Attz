package libs.tronarko.eventos;

import libs.luan.Lista;
import libs.luan.Strings;
import libs.tronarko.Tozte;
import libs.tronarko.utils.EventoLegenda;

public class Comunicum {

    private Eventum mEventum;

    public Comunicum() {

        mEventum = new Eventum();

    }


    public Lista<String> comunicarTozte(Tozte TozteProcurar) {

        Lista<String> ret = new Lista<String>();

        ret.adicionar_varios(comunicarPequenosEventosNOVO(TozteProcurar));
        ret.adicionar_varios(comunicarGrandesEventosNOVO(TozteProcurar));

        return ret;
    }

    public Lista<String> comunicarPequenosEventosNOVO(Tozte TozteProcurar) {
        Lista<String> ret = new Lista<String>();

        for (AvisarPequenoEvento aviso_corrente : mEventum.getAvisosPequenosEventos()) {

            //int TronarkoInicio = TozteProcurar.getTronarko();

            int TronarkoInicio = aviso_corrente.getTronarkoProximoDe(TozteProcurar.getTronarko());


            if (TronarkoInicio == TozteProcurar.getTronarko()) {

                Tozte eCorrente = new Tozte(aviso_corrente.getSuperarko(), aviso_corrente.getHiperarko(), TronarkoInicio);


                Tozte eAvisoInicio = eCorrente.adicionar_Superarko((-1) * aviso_corrente.getAntes());
                Tozte eAvisoFim = eCorrente.adicionar_Superarko((+1) * aviso_corrente.getDepois());


                if (TozteProcurar.isMaiorIgualQue(eAvisoInicio) && TozteProcurar.isMenorIgualQue(eAvisoFim)) {

                    if (TozteProcurar.isIgual(eCorrente)) {

                        ret.adicionar("Estamos no " + aviso_corrente.getNome());

                    } else {

                        // ret.add(" - Deve Notificar !");

                        if (TozteProcurar.isMaiorIgualQue(eAvisoInicio) && TozteProcurar.isMenorQue(eCorrente)) {
                            // ret.add(" - Antes !");

                            long Falta = eCorrente.getSuperarkosTotal() - TozteProcurar.getSuperarkosTotal();

                            if (Falta == 1) {
                                ret.adicionar("Falta : " + (Falta) + " Superarko para " + aviso_corrente.getNome());
                            } else if (Falta > 1) {
                                ret.adicionar("Faltam : " + (Falta) + " Superarkos para " + aviso_corrente.getNome());
                            }

                        } else if (TozteProcurar.isMenorIgualQue(eAvisoFim) && TozteProcurar.isMaiorQue(eCorrente)) {
                            // ret.add(" - Depois !");

                            long Passou = TozteProcurar.getSuperarkosTotal() - eCorrente.getSuperarkosTotal();

                            if (Passou == 1) {
                                ret.adicionar("Passou : " + (Passou) + " Superarko após " + aviso_corrente.getNome());
                            } else if (Passou > 1) {
                                ret.adicionar("Passaram : " + (Passou) + " Superarkos após " + aviso_corrente.getNome());
                            }

                        }

                    }

                }

            }

        }

        return ret;
    }

    public Lista<String> comunicarGrandesEventosNOVO(Tozte tozte_procurar) {
        Lista<String> ret = new Lista<String>();

        for (AvisarGrandeEvento aviso_corrente : mEventum.getAvisosGrandesEventos()) {


            int TronarkoInicio = aviso_corrente.getTronarkoProximoDe(tozte_procurar.getTronarko());


            if (TronarkoInicio == tozte_procurar.getTronarko()) {

                Tozte eInicio = new Tozte(aviso_corrente.getSuperarkoInicio(), aviso_corrente.getHiperarkoInicio(), TronarkoInicio);
                Tozte eFim = new Tozte(aviso_corrente.getSuperarkoFim(), aviso_corrente.getHiperarkoFim(), TronarkoInicio);


                Tozte eAvisoInicio = eInicio.adicionar_Superarko((-1) * aviso_corrente.getAntes());
                Tozte eAvisoFim = eFim.adicionar_Superarko((+1) * aviso_corrente.getDepois());


                if (tozte_procurar.isMaiorIgualQue(eAvisoInicio) && tozte_procurar.isMenorIgualQue(eAvisoFim)) {

                    if (tozte_procurar.isMaiorIgualQue(eInicio) && tozte_procurar.isMenorIgualQue(eFim)) {

                        // ret.add(" - Grande Evento Ocorrendo !");

                        if (tozte_procurar.isIgual(eInicio)) {
                            ret.adicionar("Hoje é o Inicio de : " + aviso_corrente.getNome());

                        } else if (tozte_procurar.isIgual(eFim)) {
                            ret.adicionar("Hoje é o Fim de : " + aviso_corrente.getNome());

                        } else {
                            ret.adicionar("Estamos no " + aviso_corrente.getNome());

                        }

                    } else {

                        // ret.add(" - Deve Notificar !");

                        if (tozte_procurar.isMaiorIgualQue(eAvisoInicio) && tozte_procurar.isMenorQue(eInicio)) {
                            // ret.add(" - Antes !");

                            long Falta = eInicio.getSuperarkosTotal() - tozte_procurar.getSuperarkosTotal();

                            if (Falta == 1) {
                                ret.adicionar("Falta : " + (Falta) + " Superarko para " + aviso_corrente.getNome());
                            } else if (Falta > 1) {
                                ret.adicionar("Faltam : " + (Falta) + " Superarkos para " + aviso_corrente.getNome());
                            }

                        } else if (tozte_procurar.isMenorIgualQue(eAvisoFim) && tozte_procurar.isMaiorQue(eFim)) {
                            // ret.add(" - Depois !");

                            long Passou = tozte_procurar.getSuperarkosTotal() - eFim.getSuperarkosTotal();

                            if (Passou == 1) {
                                ret.adicionar("Passou : " + (Passou) + " Superarko após " + aviso_corrente.getNome());
                            } else if (Passou > 1) {
                                ret.adicionar("Passaram : " + (Passou) + " Superarkos após " + aviso_corrente.getNome());
                            }

                        }

                    }

                }

            }

        }

        return ret;
    }

    public void comunicarTodos(int eTronarko) {


        Tozte tozte_corrente = new Tozte(1, 1, eTronarko);

        for (int i = 0; i < 500; i++) {

            if (comunicarTozte(tozte_corrente).getQuantidade() > 0) {

                System.out.printf("\n--------------------------------" + tozte_corrente.toString()
                        + "------------------------------------------\n");
                System.out.printf("\n");

                for (String ProximoEventoHoje : comunicarTozte(tozte_corrente)) {
                    System.out.println("\t - " + ProximoEventoHoje);
                }

            }

            tozte_corrente = tozte_corrente.adicionar_Superarko(1);
        }

    }

    public void comunicarTodosEFiltrarCom(String eFiltro, int eTronarko) {


        Tozte tozte_corrente = new Tozte(1, 1, eTronarko);

        for (int i = 0; i < 500; i++) {

            if (comunicarTozte(tozte_corrente).getQuantidade() > 0) {

                boolean listarEsse = false;

                for (String ProximoEventoHoje : comunicarTozte(tozte_corrente)) {
                    if (ProximoEventoHoje.contains(eFiltro)) {
                        listarEsse = true;
                    }
                }

                if (listarEsse) {

                    System.out.printf("\n--------------------------------" + tozte_corrente.toString()
                            + "------------------------------------------\n");
                    System.out.printf("\n");

                    for (String evento_corrente : comunicarTozte(tozte_corrente)) {
                        if (evento_corrente.contains(eFiltro)) {
                            System.out.println("\t - " + evento_corrente);

                        }
                    }

                }


            }

            tozte_corrente = tozte_corrente.adicionar_Superarko(1);
        }

    }

    public static Comunicado obterComunicado(Lista<EventoLegenda> eventos, Tozte de) {

        int menor = 0;
        boolean primeiro = true;
        boolean dentro = false;
        EventoLegenda dentro_de = null;

        boolean tera_proximo = false;
        EventoLegenda proximo = null;

        for (EventoLegenda tozte_evento : eventos) {

            if (primeiro) {

                int esse1 = Momentum.getDistancia(tozte_evento.getTozteMin(), de);
                int esse2 = Momentum.getDistancia(tozte_evento.getTozteMax(), de);

                int esse = esse1;

                if (tozte_evento.getNome().contains("Tron")) {
                    if (esse2 < esse1) {
                        esse = esse2;
                    }
                    // System.out.println("Esse 1 :: " + esse1);
                    // System.out.println("Esse 2 :: " + esse2);
                }


                if (esse <= 0) {
                    menor = esse;
                    primeiro = false;
                    tera_proximo = true;
                    proximo = tozte_evento;
                }

            } else {
                int esse = Momentum.getDistancia(tozte_evento.getTozteMin(), de);
                if (esse <= 0) {
                    if (esse > menor) {
                        menor = esse;
                        tera_proximo = true;
                        proximo = tozte_evento;
                    }
                }

            }

            if (Strings.isIgual( tozte_evento.getNome(), "Festival Reciclum Tron")) {
                if (de.isIgual(tozte_evento.getTozteMin()) || de.isIgual(tozte_evento.getTozteMax())) {
                    dentro = true;
                    dentro_de = tozte_evento;
                }
            } else if (Strings.isDiferente(tozte_evento.getNome(), "Festival Reciclum Tron")) {
                if (de.isMaiorIgualQue(tozte_evento.getTozteMin()) && de.isMenorIgualQue(tozte_evento.getTozteMax())) {
                    dentro = true;
                    dentro_de = tozte_evento;
                }
            }


        }

        Comunicado ret = new Comunicado(null, "");

        if (dentro) {
            ret = new Comunicado(dentro_de, "Curtindo " + dentro_de.getNome());
        } else {
            int pos = menor * (-1);
            if (tera_proximo) {

                String frase = "";

                if (pos == 0) {
                    frase = "Hoje começa " + proximo.getNome();
                } else if (pos == 1) {
                    frase = "Falta " + pos + " superarko para " + proximo.getNome();
                } else if (pos > 1) {
                    frase = "Falta " + pos + " superarkos para " + proximo.getNome();
                }

                ret = new Comunicado(proximo, frase);

            }
        }

        return ret;
    }

}
