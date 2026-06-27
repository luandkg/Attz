package apps.app_dajin;

import libs.luan.Strings;
import libs.luan.fmt;

public class Tipificador {


    public static String tipar(AST expressao) {
        String ret = "";

        for (AST con : expressao.getAST()) {
            if (con.is("INTEIRO_LITERAL")) {
                ret = "INTEIRO_LITERAL";
                break;
            } else if (con.is("EXPRESSAO")) {

                String esquerda = tipar(con.get("ESQUERDA"));
                String operador = "";
                String direita = tipar(con.get("DIREITA"));

                if (con.isValor("SOMA")) {
                    if (Strings.isIgual(esquerda, "INTEIRO_LITERAL") && Strings.isIgual(direita, "INTEIRO_LITERAL")) {
                        ret = "INTEIRO_LITERAL";
                    }
                }

                break;
            } else if (con.is("DEFINIDO")) {

                if (con.existe("Indice")) {
                } else {

                    if (con.existe("Tipo") && con.get("Tipo").isValor("FuncaoChamada")) {
                    } else if (con.existe("Tipo") && con.get("Tipo").isValor("TipoInvocacao")) {
                    } else if (con.existe("Tipo") && con.get("Tipo").isValor("Internamente")) {
                    } else {
                        ret = procurarTipoDefinidoAnteriormente(expressao, con.getValor());
                    }


                }


            }
        }

        return ret;
    }

    public static String procurarTipoDefinidoAnteriormente(AST pai, String nome) {
        String ret = "";

        if(pai==null){
            return "";
        }

        boolean enc = false;

        for (AST aqui : pai.getAST()) {
            if (aqui.is("DEF")) {
                fmt.println("Procurando achei :: " + aqui.getValor());
                if (aqui.isValor(nome)) {
                    ret = aqui.get("Tipo").getValor();
                    enc = true;
                }
            }
        }

        if (!enc) {
            ret = procurarTipoDefinidoAnteriormente(pai.getPai(), nome);
        }


        return ret;
    }
}
