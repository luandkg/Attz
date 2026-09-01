package libs.tronarko;

import libs.luan.Lista;
import libs.luan.Par;
import libs.luan.RefInt;
import libs.tempo.Calendario;
import libs.tempo.Data;
import libs.tempo.Horario;


// 		AUTOR : LUAN ALVES FREITAS
// 		DATA  : 21 09 2018
//
//       	ATUALIZACOES
//
//	CRIACAO		  : 21/09/2018 - Desenvolvimento do Sistema Temporal Tronarko
//
//	ORGANIZACAO 1 : 22/09/2018 
//  ORGANIZACAO 2 : 25/09/2018 
//  ORGANIZACAO 3 : 12/10/2018 
//  ORGANIZACAO 4 : 10/03/2019
//  ORGANIZACAO 5 : 25/04/2021
//  ORGANIZACAO 6 : 27/03/2022
//
//
//	ATUALIZACAO 1  : 29/10/2018 - Implementacao Modarko e Periarko
//	ATUALIZACAO 2  : 01/12/2019 - Alteracao de Nomenclatura dos Superarkos
//  ATUALIZACAO 3  : 29/12/2019 - Metodo de Sincronizacao
//	ATUALIZACAO 4  : 24/03/2020 - Organizacao dos Satelites
//	ATUALIZACAO 5  : 25/03/2020 - Aprimoramento do Metodo de Eventos e Criacao das Classes de Intervalo
// 	ATUALIZACAO 6  : 26/03/2020 - Metodo de Ordenacao , Fusos Horarios e Enumeradores : Hiperarkos, Superarkos, Signos, Periakos e Modarkos.
//	ATUALIZACAO 7  : 26/03/2020 - TozteCor e Interface UI
//	ATUALIZACAO 8  : 30/03/2020 - Avisos
//	ATUALIZACAO 9  : 25/04/2021 - ReOrganizacao do Pacote Tronarko
//	ATUALIZACAO 10 : 15/05/2021 - Hizarkos Com Cor
//  ATUALIZACAO 11 : 06/03/2022 - Fases GIBOSAS DOS SATELITES
//  ATUALIZACAO 12 : 11/03/2022 - AGENDA E FLUXO TEMPORAL
//  ATUALIZACAO 13 : 27/03/2022 - TRONARKO FALSUM e PSEUDO TRONARKO
//  ATUALIZACAO 14 : 26/01/2025 - INTEGRAÇÃO ENTRE TRONARKO E CALENDARIO PARA ESPECIALIZAÇÃO E SIMPLIFICAÇÃO
//  ATUALIZACAO 15 : 04/03/2025 - CONSTANTES , Enumerador MEGARKO e Classe Evento


public class Tronarko {

    private static final Data DATA_INICIO = Calendario.PARSER_DATA("21/09/2018");
    private static final int TRONARKO_INICIO = 7000;
    private static final int TRONARKO_SUPERARKOS_POR_TRONARKO = 500;
    private static final int TRONARKO_SUPERARKOS_POR_HIPERARKO = 50;

    public static final int SUPERARKOS_POR_HIPERARKO = 50;
    public static final int SUPERARKOS_POR_MEGARKO = 10;

    // TRON
    public static String getAgora() {
        return getTronAgora().getTextoZerado();
    }

    public static Tron getTronAgora() {

        Par<Data, Horario> para_data_horario = Calendario.GET_DATA_E_HORARIO();

        Hazde eHazde = getHora(para_data_horario.getValor());
        Tozte eTozte = getData(para_data_horario.getChave());

        return new Tron(eHazde, eTozte);
    }


    // TOZTE
    public static Tozte getTozte() {
        return getData(Calendario.getDataHoje());
    }

    public static Tozte getData(Data eData) {
        return getData(eData.getDia(), eData.getMes(), eData.getAno());
    }


    public static Tozte getData(String entrada) {
        return getData(Calendario.PARSER_DATA(entrada));
    }

    public static Tozte getData(int eDia, int eMes, int eAno) {

        // System.out.printf("\nHCC : %s",eData);

        RefInt diferenca_de_dias = new RefInt(Calendario.GET_DIFERENCA_DE_DIAS(DATA_INICIO, new Data(eAno, eMes, eDia)));


        RefInt iTronarko = new RefInt(TRONARKO_INICIO);
        RefInt iHiperarko = new RefInt(1);
        RefInt iSuperarko = new RefInt(1);

        if (diferenca_de_dias.get() >= 0) {

            diferenca_de_dias.reduzir_aumentando(iTronarko, TRONARKO_SUPERARKOS_POR_TRONARKO);
            diferenca_de_dias.reduzir_aumentando(iHiperarko, TRONARKO_SUPERARKOS_POR_HIPERARKO);

            iSuperarko.set(1 + diferenca_de_dias.get());

        } else if (diferenca_de_dias.get() < 0) {

            iTronarko.subtrair(1);
            iHiperarko.set(10);

            diferenca_de_dias.aumentar_reduzindo(iTronarko, TRONARKO_SUPERARKOS_POR_TRONARKO);
            diferenca_de_dias.aumentar_reduzindo(iHiperarko, TRONARKO_SUPERARKOS_POR_HIPERARKO);


            iSuperarko.set(TRONARKO_SUPERARKOS_POR_HIPERARKO + 1 + diferenca_de_dias.get());
        }

        return new Tozte(iSuperarko.get(), iHiperarko.get(), iTronarko.get());
    }


    // HAZDE
    public static Hazde getHora(String entrada) {
        return getHora(Calendario.PARSER_HORARIO(entrada));
    }

    public static Hazde getHora(Horario eHorario) {
        return getHora(eHorario.getHora(), eHorario.getMinutos(), eHorario.getSegundos(), eHorario.getMilissegundos());
    }

    public static Hazde getHora(int eHora, int eMinuto, int eSegundo, int eMilissegundo) {


        double HAZDE_COMPLETO = (24 * 1000 * 60 * 60);
        double TOTAL_SEGUNDOS = (10 * 1000 * 100 * 100);
        double TAXADOR = HAZDE_COMPLETO / TOTAL_SEGUNDOS;

        double toSegundos = eMilissegundo + (eSegundo * 1000) + (eMinuto * 1000 * 60) + (eHora * 1000 * 60 * 60);


        RefInt iArco = new RefInt(0);
        RefInt iIttas = new RefInt(0);
        RefInt iUzzons = new RefInt((int) ((toSegundos / TAXADOR) / 1000));

        iUzzons.reduzir_aumentando(iIttas, 100);
        iIttas.reduzir_aumentando(iArco, 100);


        return new Hazde(iArco.get(), iIttas.get(), iUzzons.get());
    }

    public static Hazde getHora(int eHora, int eMinuto, int eSegundo) {

        int eMilissegundo = 0;

        double HAZDE_COMPLETO = (24 * 1000 * 60 * 60);
        double TOTAL_SEGUNDOS = (10 * 1000 * 100 * 100);
        double TAXADOR = HAZDE_COMPLETO / TOTAL_SEGUNDOS;

        double toSegundos = eMilissegundo + (eSegundo * 1000) + (eMinuto * 1000 * 60) + (eHora * 1000 * 60 * 60);


        RefInt iArco = new RefInt(0);
        RefInt iIttas = new RefInt(0);
        RefInt iUzzons = new RefInt((int) ((toSegundos / TAXADOR) / 1000));

        iUzzons.reduzir_aumentando(iIttas, 100);
        iIttas.reduzir_aumentando(iArco, 100);


        return new Hazde(iArco.get(), iIttas.get(), iUzzons.get());
    }

    public static Hazde getHazde() {
        return getHora(Calendario.getHorario());
    }

    // FACILITADORES

    public static Tozte make_tozte(int s, int h, int t) {
        return new Tozte(s, h, t);
    }

    public static Hazde make_hazde(int a, int i, int u) {
        return new Hazde(a, i, u);
    }

    public static Hazde CRIAR_HAZDE_ARKO_ITTAS(int total) {
        return new Hazde(total / 100, total - ((total / 100) * 100), 0);
    }

    public static Hazde getHazdeComecar() {
        return new Hazde(0, 0, 0);
    }

    public static Hazde getHazdeTerminar() {
        return new Hazde(9, 9, 99);
    }


    public static long SUPERARKOS_ENTRE_COM(Tozte inicio, Tozte fim) {
        return TOZTE_ENTRE(inicio, fim, false);
    }

    public static long SUPERARKOS_ENTRE_COM_FIM(Tozte inicio, Tozte fim) {
        return TOZTE_ENTRE(inicio, fim, true);
    }


    private static long TOZTE_ENTRE(Tozte eInicio, Tozte eFim, boolean com_fim) {

        long ret = 0;

        if (eInicio.isMenorIgualQue(eFim)) {
            ret = eFim.getSuperarkosTotal() - eInicio.getSuperarkosTotal();
        } else {
            ret = ((eInicio.getSuperarkosTotal() - eFim.getSuperarkosTotal()));
        }

        if (com_fim) {
            ret += 1;
        }


        return ret;
    }


    public static long HAZDE_DIFERENCA(Hazde a, Hazde b) {
        return a.getTotalEttons() - b.getTotalEttons();
    }

    public static long TOZTE_DIFERENCA(Tozte a, Tozte b) {
        return a.getSuperarkosTotal() - b.getSuperarkosTotal();
    }


    public static String TRON_DIFERENCA(Tron iniciado, Tron terminado) {

        String s_diferenca = "";

        if (iniciado.getTozte().isIgual(terminado.getTozte())) {

            long uzzons = Tronarko.HAZDE_DIFERENCA(terminado.getHazde(), iniciado.getHazde());
            s_diferenca = uzzons + " uz";

        } else if (iniciado.getTozte().adicionar_Superarko(1).isIgual(terminado.getTozte())) {

            long p1 = Tronarko.HAZDE_DIFERENCA(new Hazde(9, 99, 99), iniciado.getHazde());
            long p2 = Tronarko.HAZDE_DIFERENCA(terminado.getHazde(), new Hazde(0, 0, 0));

            s_diferenca = (p1 + p2) + " uz";

        } else if (terminado.getTozte().isMaiorQue(iniciado.getTozte().adicionar_Superarko(1))) {

            long t = Tronarko.SUPERARKOS_ENTRE_COM(iniciado.getTozte(), terminado.getTozte());

            long p1 = Tronarko.HAZDE_DIFERENCA(new Hazde(9, 99, 99), iniciado.getHazde());
            long p2 = Tronarko.HAZDE_DIFERENCA(terminado.getHazde(), new Hazde(0, 0, 0));

            s_diferenca = t + " tz " + (p1 + p2) + " uz";

        }

        return s_diferenca;
    }

    public static long TRON_DIFERENCA_VALOR(Tron iniciado, Tron terminado) {


        if (iniciado.getTozte().isIgual(terminado.getTozte())) {

            long uzzons = Tronarko.HAZDE_DIFERENCA(terminado.getHazde(), iniciado.getHazde());
            return uzzons;

        } else if (iniciado.getTozte().adicionar_Superarko(1).isIgual(terminado.getTozte())) {

            long p1 = Tronarko.HAZDE_DIFERENCA(new Hazde(9, 99, 99), iniciado.getHazde());
            long p2 = Tronarko.HAZDE_DIFERENCA(terminado.getHazde(), new Hazde(0, 0, 0));

            return (p1 + p2);

        } else if (terminado.getTozte().isMaiorQue(iniciado.getTozte().adicionar_Superarko(1))) {

            long t = Tronarko.SUPERARKOS_ENTRE_COM(iniciado.getTozte(), terminado.getTozte());

            long p1 = Tronarko.HAZDE_DIFERENCA(new Hazde(9, 99, 99), iniciado.getHazde());
            long p2 = Tronarko.HAZDE_DIFERENCA(terminado.getHazde(), new Hazde(0, 0, 0));

            return (t * 10 * 100 * 100) + (p1 + p2);

        }

        return 0;
    }

    public static Tron CRIAR_TRON_HAZDE_ZERADO(int eSuperarko, int eHiperarko, int eTronarko) {
        return new Tron(0, 0, 0, eSuperarko, eHiperarko, eTronarko);
    }


    public static Tozte TOZTE_PRIMEIRO(int eTronarko) {
        return new Tozte(1, 1, eTronarko);
    }

    public static Tozte TOZTE_ULTIMO(int eTronarko) {
        return new Tozte(50, 10, eTronarko);
    }

    public static Lista<Megarko> GET_MEGARKOS() {
        Lista<Megarko> megarkos = new Lista<Megarko>();

        megarkos.adicionar(Megarko.Megarko_1);
        megarkos.adicionar(Megarko.Megarko_2);
        megarkos.adicionar(Megarko.Megarko_3);
        megarkos.adicionar(Megarko.Megarko_4);
        megarkos.adicionar(Megarko.Megarko_5);

        return megarkos;
    }

    public static int HIZARKO_TAMANHO() {
        return 125;
    }

    public static Modarkos GET_MODARKO(int arko) {
        Modarkos ret = null;

        if (arko >= 0 && arko <= 1) {
            ret = Modarkos.OZZ;
        }
        if (arko >= 2 && arko <= 4) {
            ret = Modarkos.AZZ;
        }
        if (arko >= 5 && arko <= 7) {
            ret = Modarkos.AZZ;
        }
        if (arko >= 8 && arko <= 9) {
            ret = Modarkos.OZZ;
        }

        return ret;
    }

    public static Periarkos GET_PERIARKO(int arko) {
        Periarkos ret = null;

        if (arko >= 0 && arko <= 1) {
            ret = Periarkos.UD;
        }
        if (arko >= 2 && arko <= 4) {
            ret = Periarkos.AD;
        }
        if (arko >= 5 && arko <= 7) {
            ret = Periarkos.ED;
        }
        if (arko >= 8 && arko <= 9) {
            ret = Periarkos.OD;
        }

        return ret;

    }

}
