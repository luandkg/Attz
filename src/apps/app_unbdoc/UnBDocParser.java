package apps.app_unbdoc;

import libs.luan.RefInt;
import libs.luan.RefString;

public class UnBDocParser {

    public static boolean espera_isso(RefString documento, RefInt index, RefInt tamanho, String esperado) {

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals(" ") || letra.contentEquals("\t") || letra.contentEquals("\n")) {

            } else {
                if (letra.contentEquals(esperado)) {
                    return true;
                } else {
                    return false;
                }
            }

            index.somar(1);
        }


        return false;
    }

    public static String parser_identificador(RefString documento, RefInt index, RefInt tamanho) {
        String ret = "";

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals(" ") || letra.contentEquals("\t") || letra.contentEquals("\n") || letra.contentEquals("{") || letra.contentEquals("(")) {
                break;
            } else {
                ret += letra;
            }

            index.somar(1);
        }


        return ret;
    }

    public static String parser_raw(RefString documento, RefInt index, RefInt tamanho) {
        String ret = "";

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("}")) {
                break;

            } else if (letra.contentEquals("{")) {
                ret += parser_entre_chaves(documento, index, tamanho) + "}\n";
            } else if (letra.contentEquals("%")) {
                ret += parser_linha(documento, index, tamanho) + "\n";
            } else if (letra.contentEquals("\\")) {
                ret += parser_linha(documento, index, tamanho) + "\n";
            } else {
                ret += letra;
            }

            index.somar(1);
        }


        return ret;
    }

    public static String parser_linha(RefString documento, RefInt index, RefInt tamanho) {
        String ret = "";

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("\n")) {
                break;
            } else {
                ret += letra;
            }

            index.somar(1);
        }


        return ret;
    }

    public static String parser_entre_chaves(RefString documento, RefInt index, RefInt tamanho) {
        String ret = "";

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("}")) {
                break;
            } else {
                ret += letra;
            }

            index.somar(1);
        }


        return ret;
    }

    public static String parser_entre_aspas(RefString documento, RefInt index, RefInt tamanho) {
        String ret = "";

        espera_isso(documento, index, tamanho, "\"");
        index.somar(1);

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("\"")) {
                break;
            } else {
                ret += letra;
            }

            index.somar(1);
        }


        return ret;
    }

    public static String parser_entre_parenteses(RefString documento, RefInt index, RefInt tamanho) {
        String ret = "";

        espera_isso(documento, index, tamanho, "(");
        index.somar(1);

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals(")")) {
                break;
            } else {
                ret += letra;
            }

            index.somar(1);
        }


        return ret;
    }



    public static String parser_rawblock(RefString documento, RefInt index, RefInt tamanho) {
        String ret = "";

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("@")) {

                if (index.get() + 4 < tamanho.get()) {
                    String p1 = String.valueOf(documento.get().charAt(index.get() + 1));
                    String p2 = String.valueOf(documento.get().charAt(index.get() + 2));
                    String p3 = String.valueOf(documento.get().charAt(index.get() + 3));
                    String p4 = String.valueOf(documento.get().charAt(index.get() + 4));

                    String fechar = p1 + p2 + p3 + p4;

                    if (fechar.contentEquals("@}}}")) {
                        // fmt.print("Proximos = {}{}{}{}", p1, p2, p3, p4);
                        break;
                    } else {
                        ret += letra;
                    }
                } else {
                    ret += letra;
                }

                //break;
            } else {
                ret += letra;
            }

            index.somar(1);
        }


        return ret;
    }


}
