package libs.luan;

import libs.arquivos.binario.Inteiro;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class Strings {

    public static String retirar_espaco_do_comeco(String texto) {

        int i = 0;
        int o = texto.length();

        String ret = "";
        boolean comecou = false;

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (comecou) {
                ret += c;
            } else {
                if (c.contentEquals(" ") || c.contentEquals("\t") || c.contentEquals("\n")) {

                } else {
                    comecou = true;
                    ret += c;
                }
            }

            i += 1;
        }

        return ret;
    }

    public static ArrayList<String> dividir_linhas(String texto) {
        ArrayList<String> linhas = new ArrayList<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals("\n")) {
                linhas.add(linha);
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (linha.length() > 0) {
            linhas.add(linha);
        }
        return linhas;
    }

    public static ArrayList<String> dividir_em_partes(String texto, int fixo) {
        ArrayList<String> linhas = new ArrayList<String>();

        int i = 0;
        int e = 0;

        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            linha += c;

            i += 1;
            e += 1;

            if (e >= fixo) {
                e = 0;
                linhas.add(linha);
                linha = "";
            }
        }
        if (linha.length() > 0) {
            linhas.add(linha);
        }
        return linhas;
    }

    public static ArrayList<String> ordenar(ArrayList<String> entradas) {

        int n = entradas.size();
        String temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entradas.get(j - 1).compareTo(entradas.get(j)) > 0) {
                    temp = entradas.get(j - 1);
                    entradas.set(j - 1, entradas.get(j));
                    entradas.set(j, temp);

                }

            }
        }
        return entradas;
    }

    public static String exibir_lista_em_linha(Lista<String> lista) {
        String ret = "";

        for (String item : lista) {
            ret += item + " ";
        }

        return ret;
    }

    public static String numero_zerado(int v) {
        String s = String.valueOf(v);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    public static String numero_zerado(String v) {
        String s = String.valueOf(v);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    public static String GET_REVERSO_ATE(String texto, String ate) {

        String ret = "";

        int o = texto.length() - 1;

        while (o >= 0) {
            String letra = String.valueOf(texto.charAt(o));
            if (letra.contentEquals(ate)) {
                break;
            }
            ret = letra + ret;
            o -= 1;
        }

        return ret;

    }

    public static String exibir_justificado(String texto, int tamanho) {

        String justificado = "";

        int i = 0;
        int o = texto.length();

        String palavra = "";
        String linha = "";

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));
            if (letra.contentEquals(" ")) {
                String linha_futura = "";

                if (linha.length() == 0) {
                    linha_futura = palavra;
                } else {
                    linha_futura = linha + " " + palavra;
                }

                if (linha_futura.length() >= tamanho) {
                    justificado += linha_futura + "\n";
                    linha = "";
                } else {
                    linha = linha_futura;
                }

                palavra = "";
            } else {
                palavra += letra;
            }
            i += 1;
        }

        if (linha.length() > 0 || palavra.length() > 0) {
            String linha_futura = linha + " " + palavra;
            justificado += linha_futura + "\n";
        }

        return justificado;

    }

    public static int indice(String conjunto, String letra) {

        int indice_retornar = 0;

        int o = conjunto.length();
        while (indice_retornar < o) {
            String proc_letra = String.valueOf(conjunto.charAt(indice_retornar));
            if (proc_letra.contentEquals(letra)) {
                break;
            }
            indice_retornar += 1;
        }

        return indice_retornar;

    }

    public static void println(Lista<String> ls) {
        for (String item : ls) {
            fmt.print("{}", item);
        }
    }

    public static int STRING_HASH_C10(String texto) {

        int limite = 1000;
        int valor = 0;

        int i = 0;
        byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
        int o = bytes.length;

        while (i < o) {
            int c = Inteiro.byteToInt(bytes[i]);
            valor += c;
            if (valor >= limite) {
                valor -= limite;
            }
            i += 1;
        }

        return valor;

    }

    public static Hasher<String> HASH() {
        return new Hasher<String>() {
            @Override
            public int hash(String eObjeto) {
                return STRING_HASH_C10(eObjeto);
            }
        };
    }

    public static Igualdade<String> IGUALDADE() {
        return new Igualdade<String>() {
            @Override
            public boolean isIgual(String v1, String v2) {
                return v1.contentEquals(v2);
            }
        };
    }

    public static Concatenador<String> CONCATENADOR() {
        return new Concatenador<String>() {
            @Override
            public String concatenar(String v1, String v2) {
                return v1 + v2;
            }
        };
    }

    public static Lista<String> ORDENAR(Lista<String> entradas) {

        int n = entradas.getQuantidade();
        String temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entradas.get(j - 1).compareTo(entradas.get(j)) > 0) {
                    temp = entradas.get(j - 1);
                    entradas.set(j - 1, entradas.get(j));
                    entradas.set(j, temp);

                }

            }
        }
        return entradas;
    }

    public static Lista<String> CRIAR_LISTA(String a) {
        Lista<String> lista = new Lista<String>();
        lista.adicionar(a);
        return lista;
    }

    public static Lista<String> CRIAR_LISTA(String a, String b) {
        Lista<String> lista = new Lista<String>();
        lista.adicionar(a);
        lista.adicionar(b);
        return lista;
    }

    public static Lista<String> CRIAR_LISTA(String a, String b, String c) {
        Lista<String> lista = new Lista<String>();
        lista.adicionar(a);
        lista.adicionar(b);
        lista.adicionar(c);
        return lista;
    }

    public static Lista<String> CRIAR_LISTA(String a, String b, String c, String d) {
        Lista<String> lista = new Lista<String>();
        lista.adicionar(a);
        lista.adicionar(b);
        lista.adicionar(c);
        lista.adicionar(d);
        return lista;
    }

    public static Lista<String> CRIAR_LISTA(String a, String b, String c, String d, String e) {
        Lista<String> lista = new Lista<String>();
        lista.adicionar(a);
        lista.adicionar(b);
        lista.adicionar(c);
        lista.adicionar(d);
        lista.adicionar(e);
        return lista;
    }

    public static Lista<String> CRIAR_LISTA(String a, String b, String c, String d, String e, String f) {
        Lista<String> lista = new Lista<String>();
        lista.adicionar(a);
        lista.adicionar(b);
        lista.adicionar(c);
        lista.adicionar(d);
        lista.adicionar(e);
        lista.adicionar(f);
        return lista;
    }

    public static String NORMALIZAR(String s) {
        String normalizada = s;
        normalizada = normalizada.replace(" ", "");
        normalizada = normalizada.replace("\t", "");
        normalizada = normalizada.replace("\n", "");
        return normalizada;
    }

    public static void UNICIDADE(ArrayList<String> ls, String s) {
        if (!ls.contains(s)) {
            ls.add(s);
        }
    }

    public static boolean isIgual(String s1, String s2) {
        return s1.contentEquals(s2);
    }

    public static boolean isVazio(String s) {
        return s.isEmpty();
    }

    public static String limpar_vazios(String s) {
        s = s.replace(" ", "");
        s = s.replace("\t", "");
        s = s.replace("\n", "");
        return s;
    }

    public static ArrayList<String> dividir_espacos(String texto) {
        ArrayList<String> linhas = new ArrayList<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(" ")) {
                if (linha.length() > 0) {
                    linhas.add(linha);
                }
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (linha.length() > 0) {
            linhas.add(linha);
        }
        return linhas;
    }

    public static Lista<String> dividir_por(String texto, String delimitador) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(delimitador)) {
                if (!linha.isEmpty()) {
                    linhas.adicionar(linha);
                }
                linha = c;
            } else {
                linha += c;
            }
            i += 1;
        }
        if (!linha.isEmpty()) {
            linhas.adicionar(linha);
        }
        return linhas;
    }


    public static Lista<String> dividir_linhas_normalizadas(String texto) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals("\n")) {
                if (linha.length() > 0) {
                    if (isNormalizadaValida(linha)) {
                        linhas.adicionar(linha);
                    }
                }
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (linha.length() > 0) {
            if (isNormalizadaValida(linha)) {
                linhas.adicionar(linha);
            }
        }
        return linhas;
    }

    public static boolean isNormalizadaValida(String s) {
        String normalizada = s;

        normalizada = normalizada.replace(" ", "");
        normalizada = normalizada.replace("\t", "");
        normalizada = normalizada.replace("\n", "");

        return normalizada.length() > 0;
    }

    public static boolean isNormalizadaValidaENaoNula(String s) {
        String normalizada = NORMALIZAR(s);
        boolean ret = false;

        if (!normalizada.contentEquals("null") && normalizada.length() > 0) {
            if (s.length() > 0) {
                ret = true;
            }
        }

        return normalizada.length() > 0;
    }

    public static String GET_INVERSO_HIFEN(String s, int n) {

        int i = s.length() - 1;
        String ret = "";
        int a = 0;

        while (i >= 0) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("-")) {
                if (a == n) {
                    break;
                }
                a += 1;
                ret = "";
            } else {
                ret = l + ret;
            }
            i -= 1;
        }

        // System.out.println(s + " -->> " + ret);

        return ret.trim();
    }

    public static String GET_INVERSO_HIFEN_A_PARTIR(String s, int n) {

        int i = s.length() - 1;
        String ret = "";
        int a = 0;

        while (i >= 0) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("-")) {
                if (a == n) {
                    break;
                }
                a += 1;
                ret = "";
            } else {
                ret = l + ret;
            }
            i -= 1;
        }

        while (i >= 0) {
            String l = String.valueOf(s.charAt(i));
            ret = l + ret;
            i -= 1;
        }

        // System.out.println(s + " -->> " + ret);

        return ret.trim();
    }

    public static int CONTAGEM(String texto, String esse) {

        int i = 0;
        int o = texto.length();
        int contar = 0;

        while (i < o) {
            String l = String.valueOf(texto.charAt(i));
            if (l.contentEquals(esse)) {
                contar += 1;
            }
            i += 1;
        }

        return contar;
    }

    public static int LISTA_CONTAGEM(ArrayList<String> lista, String proc_item) {

        int contar = 0;
        for (String cep_proc : lista) {
            if (cep_proc.contentEquals(proc_item)) {
                contar += 1;
            }
        }

        return contar;
    }

    public static ArrayList<String> LISTA_DEPOIS(ArrayList<String> lista, int n) {
        ArrayList<String> ret = new ArrayList<String>();

        int i = 0;
        for (String s : lista) {
            if (i >= n) {
                ret.add(s);
            }
            i += 1;
        }

        return ret;
    }

    public static String GET_ENTRE_ASPAS(String s, int vContagem) {

        String ret = "";

        int v = 0;

        int i = 0;
        int o = s.length();

        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("\"")) {
                v += 1;
            } else {
                if (v == vContagem) {
                    ret += l;
                }
            }

            i += 1;
        }

        return ret;
    }

    public static String GET_ENTRE_ESPACOS(String s, int vContagem) {

        String ret = "";

        int v = 0;

        int i = 0;
        int o = s.length();

        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals(" ")) {
                v += 1;
            } else {
                if (v == vContagem) {
                    ret += l;
                }
            }

            i += 1;
        }

        return ret;
    }

    public static String RETIRAR_ACENTOS(String s) {

        // MAIUSCULA
        s = s.replace("Á", "A");
        s = s.replace("À", "A");
        s = s.replace("Ã", "A");
        s = s.replace("Â", "A");
        s = s.replace("Ä", "A");
        s = s.replace("Á", "A");

        s = s.replace("É", "E");
        s = s.replace("È", "E");

        s = s.replace("Ê", "E");
        s = s.replace("Ë", "E");

        s = s.replace("Í", "I");
        s = s.replace("Ì", "I");

        s = s.replace("Î", "I");
        s = s.replace("Ï", "I");

        s = s.replace("Ó", "O");
        s = s.replace("Ò", "O");
        s = s.replace("Õ", "O");
        s = s.replace("Ô", "O");
        s = s.replace("Ö", "O");

        s = s.replace("Ú", "U");
        s = s.replace("Ù", "U");

        s = s.replace("Û", "U");
        s = s.replace("Ü", "U");

        s = s.replace("Ç", "C");

        // MINUSCULA
        s = s.replace("á", "a");
        s = s.replace("à", "a");
        s = s.replace("ã", "a");
        s = s.replace("â", "a");
        s = s.replace("ä", "a");

        s = s.replace("é", "e");
        s = s.replace("è", "e");

        s = s.replace("ê", "e");
        s = s.replace("ë", "e");

        s = s.replace("í", "i");
        s = s.replace("ì", "i");

        s = s.replace("î", "i");
        s = s.replace("ï", "i");

        s = s.replace("ó", "o");
        s = s.replace("ò", "o");
        s = s.replace("õ", "o");
        s = s.replace("ô", "o");
        s = s.replace("ö", "o");

        s = s.replace("ú", "u");
        s = s.replace("ù", "u");

        s = s.replace("û", "u");
        s = s.replace("ü", "u");

        s = s.replace("ç", "c");

        s = s.replace("\t", " ");
        s = s.replace("\n", " ");

        s = s.replace("Ñ", "n");
        s = s.replace("ñ", "n");

        s = s.replace("Å", "A");
        s = s.replace("Ė", "E");

        return s;
    }

    public static boolean isDiferente(String a, String b) {
        return !a.contentEquals(b);
    }

    public static boolean TEM_CARACTER_NAO_ALFABETICO(String texto) {

        boolean ret = false;

        String validos = "abcdefghijklmnopqrstuvwzyz ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int i = 0;
        int o = texto.length();
        while (i < o) {
            String l = String.valueOf(texto.charAt(i));
            if (validos.contains(l)) {

            } else {
                ret = true;
                break;
            }
            i += 1;
        }

        return ret;
    }

    public static boolean TEM_CARACTER_NAO_ALFABETICO_COM_VALIDOS(String texto, String mais_validos) {

        boolean ret = false;

        String validos = "abcdefghijklmnopqrstuvwzyz ABCDEFGHIJKLMNOPQRSTUVWXYZ" + mais_validos;

        int i = 0;
        int o = texto.length();
        while (i < o) {
            String l = String.valueOf(texto.charAt(i));
            if (validos.contains(l)) {

            } else {
                ret = true;
                break;
            }
            i += 1;
        }

        return ret;
    }

    public static boolean TEM_CARACTER_NAO_ALFABETICO_COM_VALIDOS_DEBUG(String texto, String mais_validos) {

        boolean ret = false;

        String validos = "abcdefghijklmnopqrstuvwzyz ABCDEFGHIJKLMNOPQRSTUVWXYZ" + mais_validos;

        int i = 0;
        int o = texto.length();
        while (i < o) {
            String l = String.valueOf(texto.charAt(i));
            if (validos.contains(l)) {
                System.out.println("Normal   :: " + l);
            } else {
                System.out.println("Estranho :: " + l);
                ret = true;
                break;
            }
            i += 1;
        }

        return ret;
    }

    public static String retirar(String s, String r) {
        return s.replace(r, "");
    }

    public static String ATE(String s, int t) {
        if (s.length() > t) {
            return s.substring(0, t);
        } else {
            String ss = "";
            while (s.length() + ss.length() < t) {
                ss += "0";
            }
            return s + ss;
        }
    }

    public static ArrayList<String> UNIR_LISTAS_UNICAMENTE(ArrayList<String> la, ArrayList<String> lb) {

        ArrayList<String> lc = new ArrayList<String>();

        for (String a : la) {
            if (!lc.contains(a)) {
                lc.add(a);
            }
        }

        for (String b : lb) {
            if (!lc.contains(b)) {
                lc.add(b);
            }
        }

        return lc;
    }

    public static String GET_REVERSO_ESPACO(String texto, int t) {

        int e = 0;
        int o = texto.length() - 1;
        String ret = "";

        while (o >= 0) {
            String s = String.valueOf(texto.charAt(o));
            if (s.contentEquals(" ")) {
                e += 1;
            }

            if (e == t) {
                break;
            } else {
                ret = s + ret;
            }
            o -= 1;
        }

        return ret;

    }

    public static String GET_REVERSO_DEPOIS(String texto, String ate) {

        int e = 0;
        int o = texto.length() - 1;
        String ret = "";

        while (o >= 0) {
            String s = String.valueOf(texto.charAt(o));

            if (s.contentEquals(ate)) {
                break;
            }

            o -= 1;
        }

        while (o >= 0) {
            String s = String.valueOf(texto.charAt(o));

            ret = s + ret;

            o -= 1;
        }

        return ret;

    }

    public static String GET_REVERSO_ESPACO_ENTRE(String texto, int t1, int t2) {

        int e = 0;
        int o = texto.length() - 1;
        String ret = "";

        while (o >= 0) {
            String s = String.valueOf(texto.charAt(o));
            if (s.contentEquals(" ")) {
                e += 1;
            }

            if (e >= t1 && e < t2) {
                ret = s + ret;
            }
            o -= 1;
        }

        return ret;

    }

    public static int GET_REVERSO_ESPACO_INDEX(String texto, int t) {

        int e = 0;
        int o = texto.length() - 1;
        String ret = "";

        while (o >= 0) {
            String s = String.valueOf(texto.charAt(o));
            if (s.contentEquals(" ")) {
                e += 1;
            }

            if (e == t) {
                break;
            } else {
                ret = s + ret;
            }
            o -= 1;
        }

        return o;

    }

    public static int GET_REVERSO_ESPACO_ENTRE_INDEX(String texto, int t1, int t2) {

        int e = 0;
        int o = texto.length() - 1;
        String ret = "";

        int index = 0;

        while (o >= 0) {
            String s = String.valueOf(texto.charAt(o));
            if (s.contentEquals(" ")) {
                e += 1;
            }

            if (e >= t1 && e < t2) {
                ret = s + ret;
                index = o;
            }
            o -= 1;
        }

        return index;

    }

    public static ArrayList<String> dividir_frases(String texto, int minimo) {

        ArrayList<String> frases = new ArrayList<String>();

        String linha = "";

        for (String palavra : Strings.dividir_espacos(texto)) {
            linha += palavra + " ";
            if (linha.length() >= minimo) {
                frases.add(linha);
                linha = "";
            }
        }

        if (linha.length() > 0) {
            frases.add(linha);
        }

        return frases;
    }

    public static ArrayList<String> dividir_blocos(String texto, int maximo) {

        ArrayList<String> linhas = new ArrayList<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (linha.length() >= maximo) {
                linhas.add(linha);
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (linha.length() > 0) {
            linhas.add(linha);
        }
        return linhas;
    }

    public static boolean CONTEM(ArrayList<String> lista, String item) {
        boolean enc = false;
        for (String r : lista) {
            if (r.contentEquals(item)) {
                enc = true;
                break;
            }
        }

        return enc;
    }

    public static int CONTAGEM_EM_LISTA(Lista<String> lista, String item) {
        int enc = 0;
        for (String r : lista) {
            if (r.contentEquals(item)) {
                enc += 1;
            }
        }

        return enc;
    }

    public static String numero_sinal(int num) {

        String s = "";
        if (num == 0) {
            s = String.valueOf(num);
        } else if (num > 0) {
            s = "+" + num;
        } else if (num < 0) {
            s = "-" + num;
        }

        return s;
    }

    public static String numero_positivo(int num) {

        String s = "";

        if (num == 0) {
            s = String.valueOf(num);
        } else {
            s = "+" + num;
        }

        return s;
    }

    public static String numero_negativo(int num) {

        String s = "";

        if (num == 0) {
            s = String.valueOf(num);
        } else {
            s = "-" + num;
        }

        return s;
    }

    public static String PRIMEIRO(ArrayList<String> itens) {
        return itens.get(0);
    }

    public static String ULTIMO(ArrayList<String> itens) {
        return itens.get(itens.size() - 1);
    }

    public static int GET_POSICAO(ArrayList<String> itens, String proc) {
        int pos = 0;

        for (String item : itens) {
            if (item.contentEquals(proc)) {
                break;
            }
            pos += 1;
        }
        return pos;
    }

    public static String GET_ATE_ESPACO(String texto) {

        int i = 0;
        int o = texto.length();

        String ret = "";

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));
            if (letra.contentEquals(" ") || letra.contentEquals("\t")) {
                break;
            } else {
                ret += letra;
            }
            i += 1;
        }

        return ret;
    }

    public static String exibir_lista_em_linhas(Lista<String> lista) {
        String ret = "";

        for (String item : lista) {
            System.out.println(item);
        }

        return ret;
    }

    public static void escrever(String eArquivo, String eConteudo) {

        BufferedWriter writer = null;
        try {
            File logFile = new File(eArquivo);

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(eConteudo);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }

    }

    public static String ler(String eArquivo) {

        String ret = "";

        try {
            FileReader arq = new FileReader(eArquivo);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            if (linha != null) {
                ret += linha;

                while (linha != null) {

                    linha = lerArq.readLine();
                    if (linha != null) {
                        ret += "\n" + linha;
                    }

                }
            }

            arq.close();
        } catch (IOException e) {

        }

        return ret;
    }

    public static String GET_ATE(String texto, String ate) {

        int i = 0;
        int o = texto.length();

        String ret = "";

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));
            if (letra.contentEquals(ate)) {
                break;
            } else {
                ret += letra;
            }
            i += 1;
        }

        return ret.trim();

    }

    public static String GET_ATE(String texto, String ate, int proc_ocorrencia) {

        int i = 0;
        int o = texto.length();
        int ocorrendo = 0;

        String ret = "";

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));
            if (letra.contentEquals(ate)) {
                if (ocorrendo == proc_ocorrencia) {
                    break;
                } else {
                    ocorrendo += 1;
                    ret = "";
                }
            } else {
                ret += letra;
            }
            i += 1;
        }

        return ret.trim();

    }

    public static String GET_DEPOIS(String texto, String de) {

        int i = 0;
        int o = texto.length();

        String ret = "";
        boolean valido = false;

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));
            if (valido) {
                ret += letra;
            } else {
                if (letra.contentEquals(de)) {
                    valido = true;
                }
            }

            i += 1;
        }

        return ret.trim();

    }

    public static String numero_ou_vazio(String v) {
        if (v.contentEquals("0")) {
            return "";
        } else {
            return v;
        }
    }

    public static String numero_ou_vazio_paresenteses(String v) {
        if (v.contentEquals("0")) {
            return "";
        } else {
            return "( " + v + " )";
        }
    }

    public static String NORMALIZAR_PATH(String path) {
        return path.replace("\\", "/");
    }

    public static ArrayList<ArrayList<String>> PAGINAR_LISTA(ArrayList<String> todas, int maximo) {

        ArrayList<ArrayList<String>> paginas = new ArrayList<ArrayList<String>>();

        ArrayList<String> sub_lista = new ArrayList<String>();

        for (String linha : todas) {

            sub_lista.add(linha);

            if (sub_lista.size() >= maximo) {
                ArrayList<String> sub_lista_inserir = new ArrayList<String>();
                for (String inserir : sub_lista) {
                    sub_lista_inserir.add(inserir);
                }
                paginas.add(sub_lista_inserir);

                sub_lista.clear();

            }
        }

        if (sub_lista.size() > 0) {

            ArrayList<String> sub_lista_inserir = new ArrayList<String>();
            for (String inserir : sub_lista) {
                sub_lista_inserir.add(inserir);
            }

            paginas.add(sub_lista_inserir);

        }

        return paginas;
    }

    public static String GET_REVERSO_DEPOIS_DE(String texto, String depois_de) {

        String ret = "";

        int o = texto.length() - 1;

        boolean valido = false;

        while (o >= 0) {
            String letra_proc = String.valueOf(texto.charAt(o));

            if (valido) {
                ret = letra_proc + ret;
            } else {
                if (letra_proc.contentEquals(depois_de)) {
                    valido = true;
                }
            }

            o -= 1;
        }

        return ret;
    }

    public static String LISTA_TO_TEXTO(ArrayList<String> ls) {
        String texto = "";

        for (String item : ls) {
            texto += item + "\n";
        }

        return texto;
    }


    public static String LISTA_TO_TEXTO_TAB(Lista<String> ls) {
        String texto = "";

        for (String item : ls) {
            texto += item + "\t";
        }

        return texto;
    }


    public static String LISTA_TO_TEXTO_FRASE(Lista<String> ls) {
        String texto = "";

        for (String item : ls) {
            texto += item + "\t";
        }

        return texto;
    }

    public static String LISTA_TO_TEXTO_LINHA(Lista<String> ls) {
        String texto = "";

        int index_ultimo = ls.getQuantidade() - 1;
        int index_corrente = 0;

        for (String item : ls) {
            if (index_ultimo == index_corrente) {
                texto += item;
            } else {
                texto += item + " | ";
            }

            index_corrente += 1;
        }

        return texto;
    }

    public static String RETIRAR_ESPACOS(String texto) {

        String ret = "";

        int i = 0;
        int o = texto.length();

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));
            if (letra.contentEquals(" ") || letra.contentEquals("\t") || letra.contentEquals("\n")) {

            } else {
                ret += letra;
            }
            i += 1;
        }
        return ret;
    }

    public static String RETIRAR_SO_ESPACOS(String texto) {
        while (texto.contains(" ")) {
            texto = texto.replace(" ", "");
        }
        return texto;
    }

    public static String REMOVER_ESPACOS(String s) {
        return s.replace("  ", " ").trim();
    }

    public static int GET_POSICAO_EM(String valor, ArrayList<String> lista) {
        int v = 0;
        for (String item : lista) {
            if (item.contentEquals(valor)) {
                break;
            }
            v += 1;
        }

        return v + 1;
    }

    public static String RETIRAR_PARENTESES(String s) {
        s = s.replace("(", "");
        s = s.replace(")", "");
        return s;
    }

    public static String CRIAR_SIGLA(String entrada) {

        entrada = entrada.replace("-", " ");
        entrada = entrada.replace("º", " ");

        entrada = entrada.replace("/", " ");
        entrada = entrada.replace("\\", " ");

        entrada = entrada.replace("(", " ");
        entrada = entrada.replace(")", " ");

        entrada = entrada.replace("  ", " ");

        String resultado_sigla = "";

        for (String parte : Strings.dividir_espacos(entrada)) {
            if (parte.length() > 0) {
                resultado_sigla += String.valueOf(parte.charAt(0));
            }
        }
        return resultado_sigla;

    }

    public static ArrayList<String> embaralhar(ArrayList<String> entradas) {

        int n = entradas.size();

        Random sorte = new Random();

        for (int i = 0; i < n; i++) {

            int j = sorte.nextInt(n);

            String a = entradas.get(i);
            String b = entradas.get(j);

            entradas.set(i, b);
            entradas.set(j, a);

        }

        return entradas;
    }

    public static int contar_tabs(String texto) {
        int contagem = 0;

        int i = 0;
        int o = texto.length();

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals("\t")) {
                contagem += 1;
            }
            i += 1;
        }

        return contagem;
    }

    public static boolean isDigito(String texto) {
        String numeros = "0123456789";
        return numeros.contains(texto);
    }

    public static boolean isLetra(String texto) {
        String alfa = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return alfa.contains(texto);
    }




    public static String GET_TABULADO(String s, int v) {

        String ret = "";

        int t = 0;
        int i = 0;
        int o = s.length();
        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("\t")) {
                if (t == v) {
                    break;
                }
                t += 1;
                ret = "";
            } else {
                ret += l;
            }
            i += 1;
        }

        return ret;
    }

    public static String GET_HIFEN(String s, int v) {

        String ret = "";

        int t = 0;
        int i = 0;
        int o = s.length();
        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("-")) {
                if (t == v) {
                    break;
                }
                t += 1;
                ret = "";
            } else {
                ret += l;
            }
            i += 1;
        }

        return ret;
    }

    public static String GET_HIFEN_A_PARTIR(String s, int v) {

        String ret = "";

        int t = 0;
        int i = 0;
        int o = s.length();
        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("-")) {
                if (t == v) {
                    break;
                }
                t += 1;
                ret = "";
            } else {
                ret += l;
            }
            i += 1;
        }

        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            ret += l;
            i += 1;
        }

        return ret;
    }

    public static String GET_TABULADO_TRIM(String s, int v) {

        String ret = "";

        int t = 0;
        int i = 0;
        int o = s.length();
        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("\t")) {
                if (t == v) {
                    break;
                }
                t += 1;
                ret = "";
            } else {
                ret += l;
            }
            i += 1;
        }

        return ret.trim();
    }

    public static String GET_ENTRE_VIRGULAS(String s, int vContagem) {

        String ret = "";

        int v = 0;

        int i = 0;
        int o = s.length();

        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals(",")) {
                v += 1;
            } else {
                if (v == vContagem) {
                    ret += l;
                }
            }
            i += 1;
        }

        return ret;
    }

    public static String GET_DEPOIS_VIRGULA(String s, int vContagem) {

        String ret = "";

        int v = 0;

        int i = 0;
        int o = s.length();

        while (i < o) {
            String l = String.valueOf(s.charAt(i));

            if (v >= vContagem) {
                ret += l;
            } else {
                if (l.contentEquals(",")) {
                    v += 1;
                }
            }

            i += 1;
        }

        return ret;
    }

    public static String GET_ENTRE_PARESENTESES(String s) {

        String ret = "";

        boolean valido = false;

        int i = 0;
        int o = s.length();
        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals("(")) {
                valido = true;
            } else if (l.contentEquals(")")) {
                valido = false;
            } else {
                if (valido) {
                    ret += l;
                }
            }
            i += 1;
        }

        return ret.trim();
    }

    public static String GET_ENTRE_ISSO(String s,String abrir,String fechar) {

        String ret = "";

        boolean valido = false;

        int i = 0;
        int o = s.length();
        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (l.contentEquals(abrir)) {
                valido = true;
            } else if (valido && l.contentEquals(fechar)) {
                valido = false;
                break;
            } else {
                if (valido) {
                    ret += l;
                }
            }
            i += 1;
        }

        return ret.trim();
    }

    public static String obter_entre_tabs(String texto, int caixa) {
        int contagem = 0;

        int i = 0;
        int o = texto.length();

        String ret = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals("\t")) {
                contagem += 1;
            } else {
                if (contagem == caixa) {
                    ret += c;
                }
            }
            i += 1;
        }

        return ret;
    }

    public static String obter_ate_espaco(String texto) {

        int i = 0;
        int o = texto.length();

        String ret = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals("\t")) {
                break;
            } else {
                ret += c;
            }
            i += 1;
        }

        return ret;

    }

    public static boolean isNumero(String texto) {

        boolean ret = true;

        int i = 0;
        int o = texto.length();
        if (o == 0) {
            ret = false;
        }
        String numeros = "0123456789";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));

            if (numeros.contains(c)) {

            } else {
                ret = false;
                break;
            }

            i += 1;
        }

        return ret;

    }

    public static int toInt(String valor) {

        valor = valor.replace(" ", "").replace("\t", "");
        int ret = 0;

        if (valor.length() > 0) {
            if (Strings.isNumero(valor)) {
                ret = Integer.parseInt(valor);
            }
        }
        return ret;

    }

    public static String RETIRAR_ESPACOS_DUPLOS(String texto) {

        while (texto.contains("  ")) {
            texto = texto.replace("  ", " ");
        }

        return texto;
    }

    public static String REESCREVER_FRASE_SEM_PALAVRAS_REPETIDAS(String texto) {

        String antiga = texto;
        String texto_novo = "";
        ArrayList<String> ja_tem = new ArrayList<String>();
        for (String palavra : Strings.dividir_espacos(antiga)) {
            if (!ja_tem.contains(palavra)) {
                ja_tem.add(palavra);
                texto_novo += palavra + " ";
            }
        }

        return texto_novo;
    }

    public static String GET_DIFERENCA(String frase_a, String frase_b) {
        String ret = "";

        int minimo = frase_a.length();
        if (frase_b.length() < minimo) {
            minimo = frase_b.length();
        }

        int i = 0;
        while (i < minimo) {
            String la = String.valueOf(frase_a.charAt(i));
            String lb = String.valueOf(frase_b.charAt(i));
            if (!la.contentEquals(lb)) {
                ret += i + "{" + la + "|" + lb + "} ";
            }
            i += 1;
        }

        return ret;
    }

    public static ArrayList<String> LISTA_GET_UNICOS(ArrayList<String> ls) {

        ArrayList<String> ret = new ArrayList<>();

        for (String item : ls) {
            if (!ret.contains(item)) {
                ret.add(item);
            }
        }

        return ret;
    }

    public static ArrayList<String> retirar_linhas_que_comecam(ArrayList<String> linhas, String eComeco) {

        ArrayList<String> linhas_escola = new ArrayList<String>();

        for (String linha : linhas) {
            if (linha.startsWith(eComeco)) {
            } else {
                linhas_escola.add(linha);
            }
        }

        return linhas_escola;
    }

    public static Igualavel<String> IGUALAVEL() {
        return new Igualavel<String>() {
            @Override
            public boolean is(String a, String b) {
                return a.contentEquals(b);
            }

        };
    }


    public static String ASPAS(String s) {
        return "\"" + s + "\"";
    }


    public static String getSlice(Vetor<Byte> bytes, int i, int o) {
        byte[] bytes_bruto = new byte[o - i];

        int c = 0;

        while (i < o) {
            bytes_bruto[c] = bytes.get(i);
            i += 1;
            c += 1;
        }

        return new String(bytes_bruto);
    }


    public static String parser_ate(String texto, String delimitador) {
        String ret = "";


        int i = 0;
        int o = texto.length();

        boolean juntar = false;

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(delimitador)) {
                break;
            } else {
                ret += c;
            }

            i += 1;
        }

        return ret;
    }

    public static String parser_depois_de(String texto, String delimitador) {
        String ret = "";


        int i = 0;
        int o = texto.length();

        boolean juntar = false;

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (juntar) {
                ret += c;
            } else {
                if (c.contentEquals(delimitador)) {
                    juntar = true;
                }
            }

            i += 1;
        }

        return ret;
    }


    public static String parser_entre_aspas(String texto) {
        String ret = "";


        int i = 0;
        int o = texto.length();

        boolean juntar = false;

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (juntar) {
                if (c.contentEquals("\"")) {
                    juntar = false;
                    break;
                } else {
                    ret += c;
                }
            } else {
                if (c.contentEquals("\"")) {
                    juntar = true;
                }
            }

            i += 1;
        }

        return ret;
    }

    public static String GET_SEQUENCIAL(String texto, int inicio, int quantidade) {

        String ret = "";
        int i = inicio;
        int o = texto.length();

        int ate = inicio + quantidade;

        while (i < ate) {
            ret += String.valueOf(texto.charAt(i));
            i += 1;
        }

        return ret;
    }


    public static String Capitalizar(String s) {
        return String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static boolean isValida(String s) {
        s = s.trim();
        return !s.isEmpty();
    }

    public static String CAPTALIZAR(String s) {

        if (!s.isEmpty()) {
            s = String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1).toLowerCase();
        }

        return s;
    }

    public static String CAPTALIZAR_FRASE(String s) {

        String ret = "";

        for (Indexado<String> palavra : Indexamento.indexe(DIVIDIR_POR(s, " "))) {
            if (palavra.isPrimeiroEUltimo() || palavra.isUltimo()) {
                ret += CAPTALIZAR(palavra.get().replace(" ", ""));
            } else {
                ret += CAPTALIZAR(palavra.get().replace(" ", "")) + " ";
            }
        }

        return ret;

    }

    public static Lista<String> DIVIDIR_POR(String texto, String por) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(por)) {
                if (!linha.isEmpty()) {
                    linhas.adicionar(linha);
                }
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (!linha.isEmpty()) {
            linhas.adicionar(linha);
        }
        return linhas;
    }

    public static Lista<String> DIVIDIR_POR_INCLUIDO(String texto, String por) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(por)) {
                if (!linha.isEmpty()) {
                    linhas.adicionar(linha);
                }
                linha = c;
            } else {
                linha += c;
            }
            i += 1;
        }
        if (!linha.isEmpty()) {
            linhas.adicionar(linha);
        }
        return linhas;
    }

    public static Lista<String> DIVIDIR_POR_SEM_DIVISOR(String texto, String por) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(por)) {
                if (!linha.isEmpty()) {
                    linhas.adicionar(linha);
                }
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (!linha.isEmpty()) {
            linhas.adicionar(linha);
        }
        return linhas;
    }

    public static Lista<String> DIVIDIR_POR_QUALQUER_UM_DESSES(String texto, String por) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (por.contains(c)) {
                if (!linha.isEmpty()) {
                    linhas.adicionar(linha);
                }
                linha = c;
            } else {
                linha += c;
            }
            i += 1;
        }
        if (!linha.isEmpty()) {
            linhas.adicionar(linha);
        }
        return linhas;
    }


    public static Lista<String> DIVIDIR_POR_OU_POR(String texto, String por1, String por2) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(por1) || c.contentEquals(por2)) {
                if (!linha.isEmpty()) {
                    linhas.adicionar(linha);
                }
                linha = c;
            } else {
                linha += c;
            }
            i += 1;
        }
        if (!linha.isEmpty()) {
            linhas.adicionar(linha);
        }
        return linhas;
    }

    public static Lista<String> DIVIDIR_LINHAS(String texto) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals("\n")) {
                if (linha.length() > 0) {
                    linhas.adicionar(linha);
                }
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (linha.length() > 0) {
            linhas.adicionar(linha);
        }
        return linhas;
    }

    public static Lista<String> GET_ENTRE_ASPAS_VARIOS(String txt) {
        int i = 0;
        int o = txt.length();

        boolean dentro = false;
        String aspas = "";

        Lista<String> ls = new Lista<String>();

        while (i < o) {
            String l = String.valueOf(txt.charAt(i));
            if (dentro) {
                if (l.contentEquals("\"")) {
                    if (aspas.length() > 0) {
                        ls.adicionar(aspas);
                    }
                    dentro = false;
                    aspas = "";
                } else {
                    aspas += l;
                }
            } else {
                if (l.contentEquals("\"")) {
                    dentro = true;
                    aspas = "";
                }
            }

            i += 1;
        }

        if (aspas.length() > 0) {
            ls.adicionar(aspas);
        }

        return ls;
    }

    public static boolean isTernario(boolean status, String s_inicio, String s_termina, String s) {

        if (Strings.isIgual(s, s_inicio)) {
            status = true;
        } else if (Strings.isIgual(s, s_termina)) {
            status = false;
        }

        return status;
    }

    public static String LETRAS_UNICAS(String s1) {

        int i = 0;
        int o = s1.length();

        String ret = "";

        while (i < o) {
            String l = String.valueOf(s1.charAt(i));
            if (!ret.contains(l)) {
                ret += l;
            }
            i += 1;
        }

        return ret;

    }


    public static int QUANTIDADE_LETRAS_IGUAIS(String s1, String s2) {

        s1 = LETRAS_UNICAS(s1);
        s2 = LETRAS_UNICAS(s2);

        String s_maior = s1;
        String s_menor = s2;

        if (s2.length() > s1.length()) {
            s_maior = s2;
            s_menor = s1;
        }

        int i = 0;
        int a1 = s_maior.length();

        int letras = 0;

        while (i < a1) {
            String l = String.valueOf(s_maior.charAt(i));

            int e = 0;
            int eo = s_menor.length();
            while (e < eo) {
                String l2 = String.valueOf(s_menor.charAt(e));
                if (l2.contentEquals(l)) {
                    letras += 1;
                }
                e += 1;
            }

            i += 1;
        }

        return letras;
    }

    public static String LINEARIZAR(String s) {
        return s.replace("\n", " ").trim();
    }

    public static String LISTA_EM_LINHA_COM(Lista<String> valores, String abrir, String fechar) {
        String ret = abrir + " ";
        for (String valor : valores) {
            ret += valor + " ";
        }
        ret += fechar;
        return ret;
    }

    public static String getParteTextual(String s) {
        String parte = "";

        String digitos = "0123456789";

        int i = 0;
        int o = s.length();

        while (i < o) {

            String d = String.valueOf(s.charAt(i));

            if (digitos.contains(d)) {
                break;
            } else {
                parte += d;
            }

            i += 1;
        }


        return parte;
    }

    public static int getParteNumericaAposTextual(String s) {
        String parte = "";

        String digitos = "0123456789";

        int i = 0;
        int o = s.length();

        while (i < o) {

            String d = String.valueOf(s.charAt(i));

            if (digitos.contains(d)) {
                break;
            }

            i += 1;
        }

        while (i < o) {

            String d = String.valueOf(s.charAt(i));

            if (digitos.contains(d)) {
                parte += d;
            } else {
                break;
            }

            i += 1;
        }


        return Integer.parseInt(parte);
    }

    public static String getParteNumerica(String s) {
        String parte = "";

        String digitos = "0123456789";

        int i = 0;
        int o = s.length();

        if (o > 0) {

            boolean tem_decimal = false;

            String p = String.valueOf(0);

            if (p.contentEquals("+") || p.contentEquals("-")) {
                parte += p;
                i += 1;
            }

            while (i < o) {

                String d = String.valueOf(s.charAt(i));

                if (d.contentEquals(".") || d.contentEquals(",")) {
                    parte += d;
                    tem_decimal = true;
                    i += 1;
                    break;
                } else {
                    if (digitos.contains(d)) {
                        parte += d;
                    } else {
                        break;
                    }
                }


                i += 1;
            }

            if (tem_decimal) {
                while (i < o) {

                    String d = String.valueOf(s.charAt(i));

                    if (digitos.contains(d)) {
                        parte += d;
                    } else {
                        break;
                    }

                    i += 1;
                }

            }
        }


        return parte;
    }

    public static String getParteDepoisDeNumerica(String s) {
        String parte = "";
        String segunda = "";

        String digitos = "0123456789";

        int i = 0;
        int o = s.length();

        if (o > 0) {

            boolean tem_decimal = false;

            String p = String.valueOf(0);

            if (p.contentEquals("+") || p.contentEquals("-")) {
                parte += p;
                i += 1;
            }

            while (i < o) {

                String d = String.valueOf(s.charAt(i));

                if (d.contentEquals(".") || d.contentEquals(",")) {
                    parte += p;
                    tem_decimal = true;
                    i += 1;
                    break;
                } else {
                    if (digitos.contains(d)) {
                        parte += p;
                    } else {
                        break;
                    }
                }


                i += 1;
            }

            if (tem_decimal) {
                while (i < o) {

                    String d = String.valueOf(s.charAt(i));

                    if (digitos.contains(d)) {
                        parte += p;
                    } else {
                        break;
                    }

                    i += 1;
                }

            }

            while (i < o) {
                String d = String.valueOf(s.charAt(i));
                segunda += d;
                i += 1;
            }

        }


        return segunda;
    }


    public static boolean temDigito(String texto) {

        String digitos = "0123456789";

        int i = 0;
        int o = texto.length();

        while (i < o) {
            String l = String.valueOf(texto.charAt(i));
            if (digitos.contains(l)) {
                return true;
            }
            i += 1;
        }

        return false;
    }

    public static void LISTA_EXIBIR(Lista<String> strings) {

        int i = 0;
        int o = strings.getQuantidade();

        while (i < o) {
            fmt.print("{}", strings.get(i));
            i += 1;
        }

    }

    public static Lista<String> LISTA_TRIM(Lista<String> strings) {

        int i = 0;
        int o = strings.getQuantidade();

        while (i < o) {
            strings.set(i, strings.get(i).trim());
            i += 1;
        }

        return strings;
    }

    public static Lista<String> LISTA_CAIXA_BAIXA(Lista<String> strings) {

        int i = 0;
        int o = strings.getQuantidade();

        while (i < o) {
            strings.set(i, strings.get(i).toLowerCase());
            i += 1;
        }

        return strings;
    }

    public static Lista<String> LISTA_CAIXA_ALTA(Lista<String> strings) {

        int i = 0;
        int o = strings.getQuantidade();

        while (i < o) {
            strings.set(i, strings.get(i).toUpperCase());
            i += 1;
        }

        return strings;
    }

    public static String GET_DIGITOS(String s) {

        int i = 0;
        int o = s.length();
        String ret = "";

        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (isDigito(l)) {
                ret += l;
            }
            i += 1;
        }

        return ret;
    }

    public static int CONTAGEM_PARTE(String texto, String divisor, String esse) {

        Lista<String> partes = DIVIDIR_POR(texto, divisor);
        int contar = 0;

        for (String parte : partes) {
            if (parte.trim().contentEquals(esse)) {
                contar += 1;
            }
        }

        return contar;
    }

    public static String GET_TEXTO_MAIOR(String a, String b) {
        if (a.length() > b.length()) {
            return a;
        } else {
            return b;
        }
    }


    public static String GET_STRING_VIEW(byte[] string_bytes) {
        return new String(string_bytes, StandardCharsets.UTF_8);
    }

    public static byte[] GET_STRING_VIEW_BYTES(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }


    public static String TRIM(String s) {
        return s.trim();
    }

    public static String CAIXA_ALTA(String s) {
        return s.toUpperCase();
    }

    public static String CAIXA_BAIXA(String s) {
        return s.toLowerCase();
    }

    public static String PURIFICAR(String s, String nao) {

        int i = 0;
        int o = s.length();
        String ret = "";

        while (i < o) {
            String l = String.valueOf(s.charAt(i));
            if (!nao.contains(l)) {
                ret += l;
            }
            i += 1;
        }

        return ret;
    }


    public static Lista<String> RETIRAR_ITEM_SE_COMECAR_COM(Lista<String> lista, String comeca_com) {

        Lista<String> ret = new Lista<String>();

        for (String item : lista) {
            if (!item.startsWith(comeca_com)) {
                ret.adicionar(item);
            }
        }

        return ret;
    }

    public static boolean LISTAS_IGUAIS(Lista<String> lista_a, Lista<String> lista_b) {


        if (lista_a.getQuantidade() == lista_b.getQuantidade()) {

            Unico<String> unico_a = new Unico<String>(Strings.IGUALAVEL());
            Unico<String> unico_b = new Unico<String>(Strings.IGUALAVEL());

            for (String item : lista_a) {
                unico_a.item(item);
            }

            for (String item : lista_b) {
                unico_b.item(item);
            }

            if (unico_a.getQuantidade() == unico_b.getQuantidade()) {
                return true;
            }

        }

        return false;
    }


    public static Lista<String> GET_LETRAS(String s) {
        int i = 0;
        int o = s.length();

        Lista<String> lista = new Lista<String>();

        while (i < o) {
            lista.adicionar(String.valueOf(s.charAt(i)));
            i += 1;
        }

        return lista;
    }

    public static String REVERSE(String s) {
        int i = s.length() - 1;

        String ret = "";

        while (i >= 0) {
            ret += String.valueOf(s.charAt(i));
            i -= 1;
        }
        return ret;
    }


    public static String LISTA_TO_TEXTO_LINHA_INTEIRO(Lista<Integer> ls) {
        String texto = "";

        int index_ultimo = ls.getQuantidade() - 1;
        int index_corrente = 0;

        for (Integer item : ls) {
            if (index_ultimo == index_corrente) {
                texto += item;
            } else {
                texto += item + " | ";
            }

            index_corrente += 1;
        }

        return texto;
    }

    public static Unico<String> CRIAR_UNICO() {
        return new Unico<String>(Strings.IGUALAVEL());
    }

    public static String LISTA_TO_TEXTO_LINHA_COM_SEPARADOR(Lista<String> ls, String separador) {
        String texto = "";

        int index_ultimo = ls.getQuantidade() - 1;
        int index_corrente = 0;

        for (String item : ls) {
            if (index_ultimo == index_corrente) {
                texto += item;
            } else {
                texto += item + separador;
            }

            index_corrente += 1;
        }

        return texto;
    }

    public static Lista<String> DIVIDIR_LINHAS_TRIM(String texto) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals("\n")) {
                if (!linha.isEmpty()) {
                    linhas.adicionar(linha.trim());
                }
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (!linha.isEmpty()) {
            linhas.adicionar(linha.trim());
        }
        return linhas;
    }

    public static Lista<String> DIVIDIR_ESPACOS(String texto) {
        Lista<String> linhas = new Lista<String>();

        int i = 0;
        int o = texto.length();

        String linha = "";

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(" ")) {
                if (linha.length() > 0) {
                    linhas.adicionar(linha.trim());
                }
                linha = "";
            } else {
                linha += c;
            }
            i += 1;
        }
        if (linha.length() > 0) {
            linhas.adicionar(linha.trim());
        }
        return linhas;
    }


    public static int contar(String texto, String s) {

        int i = 0;
        int o = texto.length();

        int contagem = 0;


        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(s)) {
                contagem += 1;
            }
            i += 1;
        }

        return contagem;
    }

    public static Lista<String> CARACTER_TO_LISTA(String texto) {

        Lista<String> ret = new Lista<String>();

        int i = 0;
        int o = texto.length();

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            ret.adicionar(c);
            i += 1;
        }
        return ret;
    }


    public static Ordenavel<String> GET_STRING_ORDENAVEL() {
        return new Ordenavel<String>() {
            @Override
            public int emOrdem(String a, String b) {
                return a.compareTo(b);
            }
        };
    }

    public static int STRING_COMPARAR(String a,String b){
        return a.compareTo(b);
    }

    public static String GET_DEPOIS_DE_CONTAGEM(String s, String de, int vContagem) {

        String ret = "";

        int v = 0;

        int i = 0;
        int o = s.length();

        while (i < o) {
            String l = String.valueOf(s.charAt(i));

            if (v >= vContagem) {
                ret += l;
            } else {
                if (l.contentEquals(de)) {
                    v += 1;
                }
            }

            i += 1;
        }


        return ret;
    }

    public static int contar_letra(String texto, String proc_letra) {
        int contagem = 0;

        int i = 0;
        int o = texto.length();

        while (i < o) {
            String c = String.valueOf(texto.charAt(i));
            if (c.contentEquals(proc_letra)) {
                contagem += 1;
            }
            i += 1;
        }

        return contagem;
    }


    public static String ELIMINAR_LINHAS_VAZIAS(String conteudo){

        Lista<String> linhas = Strings.DIVIDIR_LINHAS_TRIM(conteudo);

        String montagem = "";

        for (String linha : linhas) {
            if (!linha.isEmpty()) {
                montagem += linha + "\n";
            }
        }

        return montagem;
    }
}
