package apps.app_dajin;

import libs.entt.Entidade;
import libs.luan.Lista;
import libs.luan.fmt;

public class Lexer {

    private static final String ALFABETO = "abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALFABETO_NUMERICO = "abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String DELIMITADORES = "()[]{};:,.";
    private static final String NUMEROS = "0123456789";
    private static final String MATEMATICOS = "+-*/<>=!";

    private String texto = "";
    private int i = 0;
    private int o = 0;

    private int coluna = 0;
    private int linha = 0;

    private Lista<Token> tokens;
    private Lista<Entidade> erros;

    public void tokenize(String eTexto) {

        tokens = new Lista<Token>();
        erros = new Lista<Entidade>();

        texto = eTexto;
        i = 0;
        o = texto.length();

        linha = 1;
        coluna = 0;

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));

            if (ALFABETO.contains(letra)) {
                parseIdentificador();
            } else if (DELIMITADORES.contains(letra)) {

                if (letra.contentEquals(":") && temProximaLetra()) {
                    String proxima = getProximaLetra();
                    if (proxima.contentEquals(":")) {
                        i += 1;
                        coluna += 1;
                        letra = letra + ":";
                    }
                }

                tokens.adicionar(new Token(linha, coluna, TokenTipo.DELIMITADOR, letra));
            } else if (NUMEROS.contains(letra)) {
                parseNumero();
            } else if (MATEMATICOS.contains(letra)) {

                String esses = "+-*/<>!=";
                if (esses.contains(letra) && temProximaLetra()) {
                    String proxima = getProximaLetra();
                    if (proxima.contentEquals("=")) {
                        i += 1;
                        coluna += 1;
                        letra = letra + "=";
                    }
                }


                tokens.adicionar(new Token(linha, coluna, TokenTipo.OPERADOR, letra));

            } else if (letra.contentEquals("\"")) {

                i += 1;
                coluna += 1;


                String textoEm = "";

                while (i < o) {
                    String l2 = String.valueOf(texto.charAt(i));

                    coluna += 1;

                    if (l2.contentEquals("\"")) {
                        break;
                    }else{
                        textoEm+=l2;
                    }

                    i += 1;
                }

                tokens.adicionar(new Token(linha, coluna, TokenTipo.TEXTO_LITERAL, textoEm));



            } else if (letra.contentEquals("@")) {
                i += 1;
                coluna += 1;
                parseArroba();

            } else if (letra.contentEquals("#")) {

                i += 1;
                coluna += 1;

                String p2 = String.valueOf(texto.charAt(i));
                String p3 = String.valueOf(texto.charAt(i + 1));

                if (p2.contentEquals("-") && p3.contentEquals("-")) {

                    String comentario = "";

                    i += 2;

                    while (i < o) {
                        String l2 = String.valueOf(texto.charAt(i));

                        String f1 = String.valueOf(texto.charAt(i + 1));
                        String f2 = String.valueOf(texto.charAt(i + 2));

                        coluna += 1;

                        if (l2.contentEquals("-") && f1.contentEquals("-") && f2.contentEquals("#")) {
                            i += 2;
                            coluna += 2;
                            break;
                        } else {

                            if (l2.contentEquals("\n")) {
                                linha += 1;
                            }

                            comentario += l2;
                        }
                        i += 1;
                    }

                    comentario = comentario.replace("\n", "<LINHA>");

                    tokens.adicionar(new Token(linha, coluna, TokenTipo.COMENTARIO_BLOCO, comentario));

                } else {

                    String comentario = "";

                    while (i < o) {
                        String l2 = String.valueOf(texto.charAt(i));

                        coluna += 1;

                        if (l2.contentEquals("\n")) {
                            break;
                        } else {
                            comentario += l2;
                        }
                        i += 1;
                    }

                    tokens.adicionar(new Token(linha, coluna, TokenTipo.COMENTARIO_SIMPLES, comentario));


                }


            } else if (letra.contentEquals(" ")) {
            } else if (letra.contentEquals("\t")) {
            } else if (letra.contentEquals("\n")) {
                linha += 1;
                coluna = 0;
            } else {
                fmt.println("ERRO :: " + letra);

                Entidade e = new Entidade();
                e.at("Linha", linha);
                e.at("Coluna", coluna);
                e.at("Valor", letra);
                e.at("Mensagem", "Caractere desconhecido");
                erros.adicionar(e);
            }

            i += 1;
            coluna += 1;
        }

    }

    public void parseIdentificador() {

        String palavra = "";

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));

            if (ALFABETO_NUMERICO.contains(letra)) {
                palavra += letra;
            } else {
                i -= 1;
                coluna -= 1;
                break;
            }

            i += 1;
            coluna += 1;
        }


        tokens.adicionar(new Token(linha, coluna, TokenTipo.IDENTIFICADOR, palavra));

    }

    public void parseNumero() {

        String palavra = "";
        boolean numeroReal = false;

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));

            if (NUMEROS.contains(letra)) {
                palavra += letra;
            } else if (letra.contentEquals(".")) {
                i += 1;
                coluna += 1;
                palavra += letra;
                numeroReal = true;
                break;
            } else {
                i -= 1;
                coluna -= 1;
                break;
            }

            i += 1;
            coluna += 1;
        }

        if (numeroReal) {
            while (i < o) {
                String letra = String.valueOf(texto.charAt(i));

                if (NUMEROS.contains(letra)) {
                    palavra += letra;
                } else {
                    i -= 1;
                    coluna -= 1;
                    break;
                }

                i += 1;
                coluna += 1;
            }
        }

        if (numeroReal) {
            tokens.adicionar(new Token(linha, coluna, TokenTipo.REAL_LITERAL, palavra));
        } else {
            tokens.adicionar(new Token(linha, coluna, TokenTipo.INTEIRO_LITERAL, palavra));
        }


    }

    public void parseMatematico() {

        String palavra = "";

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));

            if (MATEMATICOS.contains(letra)) {
                palavra += letra;
            } else {
                i -= 1;
                coluna -= 1;
                break;
            }

            i += 1;
            coluna += 1;
        }

        tokens.adicionar(new Token(linha, coluna, TokenTipo.OPERADOR, palavra));

    }

    public void parseArroba() {

        String palavra = "";

        int a = 0;

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));

            if (a == 0) {
                if (ALFABETO.contains(letra)) {
                    palavra += letra;
                } else {
                    i -= 1;
                    coluna -= 1;
                    break;
                }
                a += 1;
            } else {
                if (ALFABETO_NUMERICO.contains(letra)) {
                    palavra += letra;
                } else {
                    i -= 1;
                    coluna -= 1;
                    break;
                }
            }


            i += 1;
            coluna += 1;
        }


        tokens.adicionar(new Token(linha, coluna, TokenTipo.ARROBA, "@" + palavra));

    }

    public boolean temProximaLetra() {
        return (i + 1) < o;
    }

    public String getProximaLetra() {
        return String.valueOf(texto.charAt(i + 1));
    }

    public Lista<Token> getTokens() {
        return tokens;
    }

    public Lista<Entidade> getErros() {
        return erros;
    }

    public boolean estaOK() {
        return erros.getQuantidade() == 0;
    }


    public Lista<Entidade> getTokensEntidade() {
        Lista<Entidade> dados = new Lista<Entidade>();

        int i = 0;

        for (Token e : tokens) {
            Entidade entidade = new Entidade();
            entidade.at("ID", i);
            entidade.at("Tipo", e.getTipo().toString());
            entidade.at("Linha", e.getLinha());
            entidade.at("Coluna", e.getColuna());
            entidade.at("Valor", e.getValor());

            dados.adicionar(entidade);
            i += 1;
        }

        return dados;
    }

}
