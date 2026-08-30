package libs.tronarko.eventos;

import libs.azzal.utilitarios.Cor;
import libs.luan.Lista;
import libs.luan.Ordenador;
import libs.tronarko.Hizarkos;
import libs.tronarko.utils.EventoLegenda;
import libs.tronarko.eventos.Ocorrencia.Modos;
import libs.tronarko.Tozte;

public class Eventum {

    private Lista<AvisarPequenoEvento> mPequenosEventos;
    private Lista<AvisarGrandeEvento> mGrandesEventos;

    public Eventum() {

        mPequenosEventos = new Lista<AvisarPequenoEvento>();
        mGrandesEventos = new Lista<AvisarGrandeEvento>();

        Lista<Ocorrencia> mOcorrencias = new Lista<Ocorrencia>();

        mOcorrencias.adicionar(new Ocorrencia("Festival Reciclum Tron", 1, 1, 1, 1, 1));
        mOcorrencias.adicionar(new Ocorrencia("Festival do Vento", 26, 1, 6500, 1, 1));
        mOcorrencias.adicionar(new Ocorrencia("Festival da Água", 41, 7, 6501, 1, 1));
        mOcorrencias.adicionar(new Ocorrencia("Festival do Fogo", 15, 6, 6502, 1, 1));
        mOcorrencias.adicionar(new Ocorrencia("Festival da Terra", 5, 8, 6503, 1, 1));
        mOcorrencias.adicionar(new Ocorrencia("Festival Reciclum Tron", 50, 10, 1, 1, 1));

        mOcorrencias.adicionar(new Ocorrencia("Aposta do Trovão", 45, 2, 6507, 1, 3));

        mOcorrencias.adicionar(new Ocorrencia("Corrida dos Ventos", 26, 1, 6500, 5, 3));
        mOcorrencias.adicionar(new Ocorrencia("Olimpíadas de Trannor", 11, 3, 6444, 32, 4));
        mOcorrencias.adicionar(new Ocorrencia("Grande Competição de Izzator", 11, 4, 6500, 25, 5));

        mOcorrencias.adicionar(new Ocorrencia("Fuga do Labirinto", 1, 5, 5227, 20, 4));
        mOcorrencias.adicionar(new Ocorrencia("Cruzada das Águas", 41, 7, 6501, 5, 3));
        mOcorrencias.adicionar(new Ocorrencia("Emboscada da Terra", 5, 8, 6503, 10, 3));

        mOcorrencias.adicionar(new Ocorrencia("Jornada do Fogo", 15, 6, 6502, 5, 3));

        mOcorrencias.adicionar(new Ocorrencia("Festival da Colheita", 26, 10, 6200, 5, 1));

        mOcorrencias.adicionar(new Ocorrencia("Batalha dos Imperadores", 30, 1, 5382, 39, 5));
        mOcorrencias.adicionar(new Ocorrencia("Torneio de Hazzo", 11, 9, 5431, 10, 2));

        for (Ocorrencia ocorrencia_corrente : mOcorrencias) {

            if (ocorrencia_corrente.getPeriodo() == Modos.PERIODICO) {

                if (ocorrencia_corrente.getQuantidade() == 1) {

                    mPequenosEventos.adicionar(new AvisarPequenoEvento(ocorrencia_corrente.getNome(), ocorrencia_corrente.getHiperarko(),
                            ocorrencia_corrente.getSuperarko(), ocorrencia_corrente.getTronarkoInicio(), ocorrencia_corrente.getIntervaloTronarko(), 10, 10));

                } else if (ocorrencia_corrente.getQuantidade() > 1) {

                    mGrandesEventos.adicionar(new AvisarGrandeEvento(ocorrencia_corrente.getNome(), ocorrencia_corrente.getHiperarko(),
                            ocorrencia_corrente.getSuperarko(), ocorrencia_corrente.getHiperarko(), ocorrencia_corrente.getSuperarkoFim(),
                            ocorrencia_corrente.getIntervaloTronarko(), ocorrencia_corrente.getTronarkoInicio(), 100, 15));

                }

            }

            if (ocorrencia_corrente.getPeriodo() == Modos.CICLICO) {

                if (ocorrencia_corrente.getQuantidade() == 1) {


                    mPequenosEventos.adicionar(new AvisarPequenoEvento(ocorrencia_corrente.getNome(), ocorrencia_corrente.getHiperarko(),
                            ocorrencia_corrente.getSuperarko(), ocorrencia_corrente.getTronarkoInicio(), ocorrencia_corrente.getIntervaloTronarko(), 10, 10));


                } else if (ocorrencia_corrente.getQuantidade() > 1) {

                    mGrandesEventos.adicionar(new AvisarGrandeEvento(ocorrencia_corrente.getNome(), ocorrencia_corrente.getHiperarko(),
                            ocorrencia_corrente.getSuperarko(), ocorrencia_corrente.getHiperarko(), ocorrencia_corrente.getSuperarkoFim(),
                            ocorrencia_corrente.getIntervaloTronarko(), ocorrencia_corrente.getTronarkoInicio(), 100, 15));

                }

            }

        }

    }

    public Lista<AvisarPequenoEvento> getPequenosEventos(){
        return mPequenosEventos;
    }
    public Lista<AvisarGrandeEvento> getGrandesEventos(){
        return mGrandesEventos;
    }


    public Lista<Ocorrencia> getOcorrencias() {
        Lista<Ocorrencia> ret = new Lista<Ocorrencia>();

        for (AvisarPequenoEvento P : mPequenosEventos) {

            ret.adicionar(new Ocorrencia(P.getNome(), P.getSuperarko(), P.getHiperarko(), P.getTronarkoInicio(), 1, P.getIntervalo()));

        }

        for (AvisarGrandeEvento G : mGrandesEventos) {

            ret.adicionar(new Ocorrencia(G.getNome(), G.getSuperarkoInicio(), G.getHiperarkoInicio(), G.getInicio(),
                    G.getDuracao(), G.getIntervalo()));

        }

        return ret;
    }

    public void ordenarPequenosEventos(Lista<AvisarPequenoEvento> lista_pequenos_eventos) {
        Ordenador.ORDENAR_CRESCENTE_LISTA(lista_pequenos_eventos,AvisarPequenoEvento.ORDENAVEL());
    }

    public void ordenarGrandesEventos(Lista<AvisarGrandeEvento> Entrada) {
        Ordenador.ORDENAR_CRESCENTE_LISTA(Entrada,AvisarGrandeEvento.ORDENAVEL());
    }

    public void ordenarGrandesAvisos(Lista<Avisar> Entrada) {
        Ordenador.ORDENAR_CRESCENTE_LISTA(Entrada,Avisar.ORDENAVEL());
    }

    public Lista<AvisarPequenoEvento> getAvisosPequenosEventos() {

        Lista<AvisarPequenoEvento> mOrdenados = new Lista<AvisarPequenoEvento>();

        for (AvisarPequenoEvento P : mPequenosEventos) {
            mOrdenados.adicionar(P);
        }

        ordenarPequenosEventos(mOrdenados);

        return mOrdenados;
    }

    public Lista<AvisarGrandeEvento> getAvisosGrandesEventos() {

        Lista<AvisarGrandeEvento> mBaguncados = new Lista<AvisarGrandeEvento>();

        for (AvisarGrandeEvento P : mGrandesEventos) {
            mBaguncados.adicionar(P);
        }

        ordenarGrandesEventos(mBaguncados);

        return mBaguncados;
    }

    public Lista<String> eventosDe(int eTronarko) {

        Lista<String> ret = new Lista<String>();

        for (PequenoEvento P : proximosPequenosEventos(eTronarko)) {
            ret.adicionar(P.toString());

        }

        for (GrandeEvento P : proximosGrandesEventos(eTronarko)) {
            ret.adicionar(P.toString());
        }
        return ret;
    }

    public Lista<Evento> getProximosEventos(int eTronarko) {

        Lista<Evento> eventos = new Lista<Evento>();

        for (GrandeEvento ev : proximosGrandesEventos(eTronarko)) {
            Evento evt = new Evento(ev, ev.getNome(), ev.getInicio());
            eventos.adicionar(evt);
        }

        for (PequenoEvento ev : proximosPequenosEventos(eTronarko)) {
            Evento evt = new Evento(ev, ev.getNome(), ev.getTozte());
            eventos.adicionar(evt);
        }

        return eventos;
    }

    public Lista<PequenoEvento> proximosPequenosEventos(int eTronarko) {

        Lista<PequenoEvento> ret = new Lista<PequenoEvento>();

        Lista<AvisarPequenoEvento> mPequenosEventos = getAvisosPequenosEventos();

        ordenarPequenosEventos(mPequenosEventos);

        for (AvisarPequenoEvento TozteCorrente : mPequenosEventos) {

            int edicao = 1;

            int tronarko_inicio = TozteCorrente.getInicio();
            while (tronarko_inicio < eTronarko) {
                tronarko_inicio += TozteCorrente.getIntervalo();
                edicao += 1;
            }


            Tozte TozteC = new Tozte(TozteCorrente.getSuperarko(), TozteCorrente.getHiperarko(), tronarko_inicio);

            if (TozteC.getTronarko() == eTronarko) {
                ret.adicionar(new PequenoEvento(TozteCorrente.getNome(), TozteC));
            }

        }

        return ret;
    }

    public void listarPequenosEventosDe(String eGrandeEvento, int eAteTronarko) {

        int tamanho = mPequenosEventos.getQuantidade();
        int indice = 0;

        while (indice < tamanho) {
            AvisarPequenoEvento TozteCorrente = mPequenosEventos.get(indice);

            if (TozteCorrente.getNome().contentEquals(eGrandeEvento)) {

                int Edicao = 1;

                int TronarkoInicio = TozteCorrente.getInicio();
                while (TronarkoInicio < eAteTronarko) {

                    System.out.println(Edicao + " : " + eGrandeEvento + " - " + TronarkoInicio);

                    TronarkoInicio += TozteCorrente.getIntervalo();
                    Edicao += 1;
                }
            }
            indice += 1;
        }
    }

    public void listarGrandesEventos(String eGrandeEvento, int eAteTronarko) {

        int tamanho = mGrandesEventos.getQuantidade();
        int indice = 0;

        while (indice < tamanho) {
            AvisarGrandeEvento TozteCorrente = mGrandesEventos.get(indice);

            if (TozteCorrente.getNome().contentEquals(eGrandeEvento)) {

                int Edicao = 1;

                int TronarkoInicio = TozteCorrente.getInicio();
                while (TronarkoInicio < eAteTronarko) {

                    System.out.println(Edicao + " : " + eGrandeEvento + " - " + TronarkoInicio);

                    TronarkoInicio += TozteCorrente.getIntervalo();
                    Edicao += 1;
                }
            }
            indice += 1;
        }
    }

    public void listarEventos_Entre(int eDeTronarko, int eAteTronarko) {


        Lista<Avisar> Avisos = new Lista<Avisar>();

        for (AvisarPequenoEvento TozteCorrente : mPequenosEventos) {

            int Edicao = 1;

            int TronarkoInicio = TozteCorrente.getInicio();
            while (TronarkoInicio < eAteTronarko) {

                if (TronarkoInicio >= eDeTronarko) {
                    Avisos.adicionar(new Avisar(TozteCorrente.getNome(), TozteCorrente.getSuperarko(), TozteCorrente.getHiperarko(), TronarkoInicio, Edicao));
                }

                TronarkoInicio += TozteCorrente.getIntervalo();
                Edicao += 1;
            }

        }

        for (AvisarGrandeEvento TozteCorrente : mGrandesEventos) {

            int Edicao = 1;

            int TronarkoInicio = TozteCorrente.getInicio();
            while (TronarkoInicio < eAteTronarko) {

                if (TronarkoInicio >= eDeTronarko) {
                    Avisos.adicionar(new Avisar(TozteCorrente.getNome(), TozteCorrente.getSuperarkoInicio(), TozteCorrente.getHiperarkoInicio(), TronarkoInicio, Edicao));
                }

                TronarkoInicio += TozteCorrente.getIntervalo();
                Edicao += 1;
            }

        }

        ordenarGrandesAvisos(Avisos);


        for (Avisar AvisoC : Avisos) {
            System.out.println(AvisoC.toString());
        }
    }

    public void listarPequenosEventos_Entre(String eGrandeEvento, int eDeTronarko, int eAteTronarko) {

        int tamanho = mPequenosEventos.getQuantidade();
        int indice = 0;

        while (indice < tamanho) {
            AvisarPequenoEvento TozteCorrente = mPequenosEventos.get(indice);

            if (TozteCorrente.getNome().contentEquals(eGrandeEvento)) {

                int Edicao = 1;

                int TronarkoInicio = TozteCorrente.getInicio();
                while (TronarkoInicio < eAteTronarko) {

                    if (TronarkoInicio >= eDeTronarko) {

                        System.out.println(Edicao + " : " + eGrandeEvento + " - " + TronarkoInicio);

                    }

                    TronarkoInicio += TozteCorrente.getIntervalo();
                    Edicao += 1;
                }
            }
            indice += 1;
        }
    }

    public void listarGrandesEventos_Entre(String eGrandeEvento, int eDeTronarko, int eAteTronarko) {

        int tamanho = mGrandesEventos.getQuantidade();
        int indice = 0;

        while (indice < tamanho) {
            AvisarGrandeEvento TozteCorrente = mGrandesEventos.get(indice);

            if (TozteCorrente.getNome().contentEquals(eGrandeEvento)) {

                int Edicao = 1;

                int TronarkoInicio = TozteCorrente.getInicio();
                while (TronarkoInicio < eAteTronarko) {

                    if (TronarkoInicio >= eDeTronarko) {

                        System.out.println(Edicao + " : " + eGrandeEvento + " - " + TronarkoInicio);

                    }

                    TronarkoInicio += TozteCorrente.getIntervalo();
                    Edicao += 1;
                }
            }
            indice += 1;
        }
    }

    public Lista<GrandeEvento> proximosGrandesEventos(int eTronarko) {

        Lista<AvisarGrandeEvento> SAT = new Lista<AvisarGrandeEvento>();
        for (AvisarGrandeEvento TozteCorrente : mGrandesEventos) {

            int Edicao = 1;

            int TronarkoInicio = TozteCorrente.getInicio();
            while (TronarkoInicio < eTronarko) {
                TronarkoInicio += TozteCorrente.getIntervalo();
                Edicao += 1;
            }

            AvisarGrandeEvento SATC = new AvisarGrandeEvento(TozteCorrente.getNome(),
                    TozteCorrente.getHiperarkoInicio(), TozteCorrente.getSuperarkoInicio(),
                    TozteCorrente.getHiperarkoFim(), TozteCorrente.getSuperarkoFim(), TozteCorrente.getIntervalo(),
                    TozteCorrente.getInicio(), TozteCorrente.getAntes(), TozteCorrente.getDepois());

            SATC.setTronarko(TronarkoInicio);
            SATC.setEdicao(Edicao);

            SAT.adicionar(SATC);

        }

        ordenarGrandesEventos(SAT);

        Lista<GrandeEvento> mRetorno = new Lista<GrandeEvento>();

        for (AvisarGrandeEvento AvisoCorrente : SAT) {

            Tozte eInicio = new Tozte(AvisoCorrente.getSuperarkoInicio(), AvisoCorrente.getHiperarkoInicio(),
                    AvisoCorrente.getTronarko());
            Tozte eFim = new Tozte(AvisoCorrente.getSuperarkoFim(), AvisoCorrente.getHiperarkoFim(),
                    AvisoCorrente.getTronarko());

            if (eInicio.getTronarko() == eTronarko) {
                mRetorno.adicionar(new GrandeEvento(AvisoCorrente.getNome(), AvisoCorrente.getIntervalo(),
                        AvisoCorrente.getInicio(), eInicio, eFim, AvisoCorrente.getEdicao()));
            }

        }

        return mRetorno;
    }

    public Lista<String> eventosDoTozte(Tozte TozteC) {
        Lista<String> ret = new Lista<String>();

        for (AvisarPequenoEvento EventoCorrente : mPequenosEventos) {

            if (EventoCorrente.getSuperarko() == TozteC.getSuperarko()
                    && EventoCorrente.getHiperarko() == TozteC.getHiperarko()) {

                ret.adicionar(EventoCorrente.getNome() + "  ->  [ " + EventoCorrente.getSuperarko() + "/"
                        + EventoCorrente.getHiperarko() + "/" + TozteC.getTronarko() + " ]");

            }

        }

        return ret;
    }

    public Lista<String> grandesEventosDoTozte(Tozte TozteC, Lista<AvisarGrandeEvento> lsGrandesEventos) {
        Lista<String> ret = new Lista<String>();

        for (AvisarGrandeEvento GE : lsGrandesEventos) {

            if (GE.getSuperarkoInicio() == TozteC.getSuperarko() && GE.getHiperarkoInicio() == TozteC.getHiperarko()
                    && GE.getTronarko() == TozteC.getTronarko()) {

                ret.adicionar(GE.getNome() + "  ->  [ " + GE.getSuperarkoInicio() + "/" + GE.getHiperarkoInicio() + "/"
                        + GE.getTronarko() + " a " + GE.getSuperarkoFim() + "/" + GE.getHiperarkoFim() + "/"
                        + GE.getTronarko() + " ] a cada " + GE.getIntervalo() + " desde " + GE.getInicio()
                        + " Edicao : " + GE.getEdicao());

            }

        }

        return ret;
    }

    private Lista<AvisarGrandeEvento> grandesEventos_EntreTronarkos(int MinTronarko, int MaxTronarko) {

        int tamanho = mGrandesEventos.getQuantidade();
        int indice = 0;

        Lista<AvisarGrandeEvento> SAT = new Lista<AvisarGrandeEvento>();

        while (indice < tamanho) {
            AvisarGrandeEvento TozteCorrente = mGrandesEventos.get(indice);

            int Edicao = 1;

            int TronarkoInicio = TozteCorrente.getInicio();
            while (TronarkoInicio <= MaxTronarko) {

                if (TronarkoInicio >= MinTronarko && TronarkoInicio <= MaxTronarko) {

                    AvisarGrandeEvento SATC = new AvisarGrandeEvento(TozteCorrente.getNome(),
                            TozteCorrente.getHiperarkoInicio(), TozteCorrente.getSuperarkoInicio(),
                            TozteCorrente.getHiperarkoFim(), TozteCorrente.getSuperarkoFim(),
                            TozteCorrente.getIntervalo(), TozteCorrente.getInicio(), TozteCorrente.getAntes(),
                            TozteCorrente.getDepois());

                    SATC.setTronarko(TronarkoInicio);
                    SATC.setEdicao(Edicao);

                    SAT.adicionar(SATC);

                }

                TronarkoInicio += TozteCorrente.getIntervalo();
                Edicao += 1;
            }

            if (TronarkoInicio < MaxTronarko) {
                TronarkoInicio = TronarkoInicio + TozteCorrente.getIntervalo();
            }

            indice += 1;
        }

        return SAT;

    }

    public Lista<String> eventosDesde(Tozte TozteC, int eSuperarkos) {
        Lista<String> ret = new Lista<String>();

        Lista<AvisarGrandeEvento> GrandesEventosEntreTronarkos = grandesEventos_EntreTronarkos(TozteC.getTronarko(),
                TozteC.adicionar_Superarko(eSuperarkos).getTronarko());

        for (int i = 0; i < eSuperarkos; i++) {

            Tozte Novo = TozteC.adicionar_Superarko(i);

            for (String eS : eventosDoTozte(Novo)) {
                String NovoString = eS;
                if (!ret.existe(NovoString)) {
                    ret.adicionar(NovoString);
                }
            }

            for (String eS : grandesEventosDoTozte(Novo, GrandesEventosEntreTronarkos)) {
                String NovoString = eS;

                if (!ret.existe(NovoString)) {
                    ret.adicionar(NovoString);
                }
            }

        }

        return ret;

    }

    public Lista<EventoLegenda> getLegenda(Lista<EventoLegenda> ToztesComCor) {

        Lista<EventoLegenda> ToztesComCorUnico = new Lista<EventoLegenda>();
        for (EventoLegenda e : ToztesComCor) {
            boolean enc = false;

            e.getToztes().limpar();

            for (EventoLegenda u : ToztesComCorUnico) {
                if (u.getNome().contentEquals(e.getNome())) {
                    enc = true;
                    u.adicionar_Tozte(e.getTozte());
                    break;
                }
            }

            if (!enc) {
                e.adicionar_Tozte(e.getTozte());
                ToztesComCorUnico.adicionar(e);
            }
        }


        int n = ToztesComCorUnico.getQuantidade();
        EventoLegenda temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (ToztesComCorUnico.get(j - 1).getOrdem() >= (ToztesComCorUnico.get(j).getOrdem())) {
                    temp = ToztesComCorUnico.get(j - 1);
                    ToztesComCorUnico.set(j - 1, ToztesComCorUnico.get(j));
                    ToztesComCorUnico.set(j, temp);

                }

            }
        }


        return ToztesComCorUnico;
    }

    private Lista<EventoLegenda> removerMenorPorMaior(String eMaior, Lista<EventoLegenda> lsToztesComCor) {

        Lista<EventoLegenda> mRet = new Lista<EventoLegenda>();
        for (EventoLegenda eEventoLegenda : lsToztesComCor) {
            mRet.adicionar(eEventoLegenda);
        }

        boolean remover = false;
        String RemoverEsse = "";

        if (eMaior.contentEquals("Corrida dos Ventos")) {
            remover = true;
            RemoverEsse = "Festival do Vento";
        }

        if (eMaior.contentEquals("Cruzada das Águas")) {
            remover = true;
            RemoverEsse = "Festival da Água";
        }

        if (eMaior.contentEquals("Emboscada da Terra")) {
            remover = true;
            RemoverEsse = "Festival da Terra";
        }

        if (eMaior.contentEquals("Jornada do Fogo")) {
            remover = true;
            RemoverEsse = "Festival do Fogo";
        }

        if (remover) {

            for (EventoLegenda u : mRet) {
                if (u.getNome().contentEquals(RemoverEsse)) {
                    mRet.remover(u);
                    break;
                }
            }

        }

        return mRet;
    }

    public Lista<EventoLegenda> getToztesComCor(int eTronarko) {

        Lista<EventoLegenda> ToztesComCor = new Lista<EventoLegenda>();

        for (PequenoEvento e : proximosPequenosEventos(eTronarko)) {
            ToztesComCor.adicionar(new EventoLegenda(e.getNome(), e.getTozte(), getCor(e.getNome())));
        }

        for (GrandeEvento e : proximosGrandesEventos(eTronarko)) {

            ToztesComCor = removerMenorPorMaior(e.getNome(), ToztesComCor);

            for (Tozte T1 : e.getToztes()) {
                ToztesComCor.adicionar(new EventoLegenda(e.getNome(), T1.getCopia(), getCor(e.getNome())));
            }
        }


        return ToztesComCor;
    }


    public void alinharEventos(Lista<EventoLegenda> infos) {

        String mPassadoInfoNome = "";

        for (EventoLegenda info : infos) {

            String mAtualInfoNome = info.getNome();

            if (mPassadoInfoNome.contentEquals("Festival da Água") && mAtualInfoNome.contentEquals("Cruzada das Águas")) {
                info.setNome("Cruzada das Águas");
            }

            mPassadoInfoNome = info.getNome();
        }

    }

    public Lista<EventoLegenda> getToztesComCorHiperarko(int eHiperarko, int eTronarko) {

        Lista<EventoLegenda> ToztesComCor = new Lista<EventoLegenda>();

        for (PequenoEvento e : proximosPequenosEventos(eTronarko)) {

            ToztesComCor.adicionar(new EventoLegenda(e.getNome(), e.getTozte(), getCor(e.getNome())));

        }

        for (GrandeEvento e : proximosGrandesEventos(eTronarko)) {

            ToztesComCor = removerMenorPorMaior(e.getNome(), ToztesComCor);

            for (Tozte T1 : e.getToztes()) {
                ToztesComCor.adicionar(new EventoLegenda(e.getNome(), T1.getCopia(), getCor(e.getNome())));
            }
        }

        Lista<EventoLegenda> mFiltrando = new Lista<EventoLegenda>();
        for (EventoLegenda e : ToztesComCor) {

            if (e.getTozte().getHiperarko() == eHiperarko) {
                mFiltrando.adicionar(e);
            }

        }

        return mFiltrando;
    }

    public Lista<EventoLegenda> getToztesComCorEmIntervalo(Tozte eAntes, Tozte eDepois) {


        System.out.println("-->> ANTES : " + eAntes.getTexto());
        System.out.println("-->> DEPOIS : " + eDepois.getTexto());


        if (eAntes.isMaiorQue(eDepois)) {
            Tozte tmp = eAntes;
            eAntes = eDepois;
            eDepois = tmp;
        }


        Lista<EventoLegenda> ToztesComCor = new Lista<EventoLegenda>();

        if (eAntes.getTronarko() == eDepois.getTronarko()) {

            int eTronarko = eAntes.getTronarko();

            for (PequenoEvento e : proximosPequenosEventos(eTronarko)) {

                ToztesComCor.adicionar(new EventoLegenda(e.getNome(), e.getTozte(), getCor(e.getNome())));

            }

            for (GrandeEvento e : proximosGrandesEventos(eTronarko)) {

                ToztesComCor = removerMenorPorMaior(e.getNome(), ToztesComCor);

                for (Tozte T1 : e.getToztes()) {
                    ToztesComCor.adicionar(new EventoLegenda(e.getNome(), T1.getCopia(), getCor(e.getNome())));
                }
            }

        } else {

            int eTronarko1 = eAntes.getTronarko();
            int eTronarko2 = eDepois.getTronarko();


            for (int eTronarko = eTronarko1; eTronarko <= eTronarko2; eTronarko++) {

                for (PequenoEvento e : proximosPequenosEventos(eTronarko)) {

                    ToztesComCor.adicionar(new EventoLegenda(e.getNome(), e.getTozte(), getCor(e.getNome())));

                }

                for (GrandeEvento e : proximosGrandesEventos(eTronarko)) {

                    ToztesComCor = removerMenorPorMaior(e.getNome(), ToztesComCor);

                    for (Tozte T1 : e.getToztes()) {
                        ToztesComCor.adicionar(new EventoLegenda(e.getNome(), T1.getCopia(), getCor(e.getNome())));
                    }
                }

            }

        }

        Lista<EventoLegenda> mFiltrando = new Lista<EventoLegenda>();

        for (EventoLegenda e : ToztesComCor) {

            if (e.getTozte().isMaiorIgualQue(eAntes) && e.getTozte().isMenorIgualQue(eDepois)) {
                mFiltrando.adicionar(e);
            }

        }


        return mFiltrando;
    }

    public Lista<EventoLegenda> getToztesComCorHizarko(int eTronarko) {

        Lista<EventoLegenda> ToztesComCor = new Lista<EventoLegenda>();

        Tozte eTozte = new Tozte(1, 1, eTronarko);

        for (int n = 0; n < 500; n++) {

            ToztesComCor.adicionar(new EventoLegenda(eTozte.getHizarko().toString(), eTozte, getHizarkoCor(eTozte.getHizarko())));

            eTozte = eTozte.adicionar_Superarko(1);
        }


        return ToztesComCor;
    }


    public Cor getCor(String eNome) {

        Cor eCor = new Cor(170, 170, 170);

        if (eNome.contains("Reciclum")) {
            eCor = new Cor(140, 140, 140);
        }

        if (eNome.contains("Fogo")) {
            eCor = new Cor(255, 20, 60);
        }

        if (eNome.contains("Água")) {
            eCor = new Cor(92, 80, 200);
        }

        if (eNome.contains("Terra")) {
            eCor = new Cor(92, 140, 10);
        }

        if (eNome.contains("Trovão")) {
            eCor = new Cor(92, 140, 10);
        }


        if (eNome.contains("Vento")) {
            eCor = new Cor(92, 140, 208);
        }

        if (eNome.contains("Hazzo")) {
            eCor = new Cor(255, 165, 23);
        }

        if (eNome.contains("Colheita")) {
            eCor = new Cor(159, 95, 159);
        }

        if (eNome.contains("Trannor")) {
            eCor = new Cor(107, 142, 35);
        }

        if (eNome.contains("Izzator")) {
            eCor = new Cor(74, 118, 110);
        }

        if (eNome.contains("Labirinto")) {
            eCor = new Cor(154, 205, 50);
        }

        if (eNome.contains("Imperador")) {
            eCor = Cor.getHexCor("#fdd835");
        }


        return eCor;
    }




    public Cor getHizarkoCor(Hizarkos eHizarko) {

        Cor eCor = new Cor(0,0,0);

        if (eHizarko == Hizarkos.HARBARIUM) {
            eCor = new Cor(220, 220, 20);
        }

        if (eHizarko == Hizarkos.DEGGOVIUM) {
            eCor = new Cor(250, 20, 30);
        }

        if (eHizarko == Hizarkos.NUZTIUM) {
            eCor = new Cor(50, 60, 180);
        }

        if (eHizarko == Hizarkos.HITTARIUM) {
            eCor = new Cor(160, 160, 160);
        }


        return eCor;
    }

}
