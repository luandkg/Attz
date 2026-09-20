package libs.tronarko.utils;

import libs.luan.Lista;
import libs.luan.Ordenavel;
import libs.tronarko.agenda.Lembrete;
import libs.tronarko.Hazde;
import libs.tronarko.Tozte;
import libs.tronarko.Tron;

import java.util.ArrayList;

public class Ordenador {

    public static void OrdenarToztes(ArrayList<Tozte> Entrada) {

        int n = Entrada.size();
        Tozte temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (Entrada.get(j - 1).isMaiorIgualQue(Entrada.get(j))) {
                    temp = Entrada.get(j - 1);
                    Entrada.set(j - 1, Entrada.get(j));
                    Entrada.set(j, temp);

                }

            }
        }

    }

    public static void OrdenarHazdes(ArrayList<Hazde> Entrada) {

        int n = Entrada.size();
        Hazde temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (Entrada.get(j - 1).isMaiorIgual(Entrada.get(j))) {
                    temp = Entrada.get(j - 1);
                    Entrada.set(j - 1, Entrada.get(j));
                    Entrada.set(j, temp);

                }

            }
        }

    }

    public static void OrdenarTrons(ArrayList<Tron> Entrada) {

        int n = Entrada.size();
        Tron temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (Entrada.get(j - 1).isMaiorIgualQue(Entrada.get(j))) {
                    temp = Entrada.get(j - 1);
                    Entrada.set(j - 1, Entrada.get(j));
                    Entrada.set(j, temp);

                }

            }
        }

    }

    public static Lista<Lembrete> OrdenarLembretes(Lista<Lembrete> entradas) {

        int n = entradas.getQuantidade();
        Lembrete temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entradas.get(j - 1).getTozte().isMaiorIgualQue((entradas.get(j)).getTozte())) {
                    if (entradas.get(j - 1).getTozte().isIgual((entradas.get(j)).getTozte())) {

                        if (entradas.get(j - 1).getHazde().isMaior((entradas.get(j)).getHazde())) {
                            temp = entradas.get(j - 1);
                            entradas.set(j - 1, entradas.get(j));
                            entradas.set(j, temp);
                        }

                    } else {
                        temp = entradas.get(j - 1);
                        entradas.set(j - 1, entradas.get(j));
                        entradas.set(j, temp);
                    }
                }

            }
        }
        return entradas;
    }


    public static Ordenavel<Tron> TRON_ORDENADOR() {
        return new Ordenavel<Tron>() {
            @Override
            public int emOrdem(Tron a, Tron b) {
                int resp = Ordenavel.IGUAL;

                if (a.isMaiorQue(b)) {
                    resp = Ordenavel.MAIOR;
                } else if (a.isMenorQue(b)) {
                    resp = Ordenavel.MENOR;
                }
                return resp;
            }
        };
    }

    public static Ordenavel<String> TRON_ORDENADOR_STRING() {
        return new Ordenavel<String>() {
            @Override
            public int emOrdem(String sa, String sb) {

                Tron a = StringTronarko.PARSER_TRON(sa);
                Tron b = StringTronarko.PARSER_TRON(sb);

                int resp = Ordenavel.IGUAL;

                if (a.isMaiorQue(b)) {
                    resp = Ordenavel.MAIOR;
                } else if (a.isMenorQue(b)) {
                    resp = Ordenavel.MENOR;
                }
                return resp;
            }
        };
    }

}
