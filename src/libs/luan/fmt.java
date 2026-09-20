package libs.luan;


public class fmt {

    // AUTOR : LUAN FREITAS
    // DATA : 2022 01 16

    private static String format_text(String texto, Lista<Object> objetos) {

        int i = 0;
        int o = texto.length();

        Lista<FatiaString> fatias = new Lista<FatiaString>();

        String juntando = "";
        boolean isDentro = false;

        while (i < o) {
            String letra = String.valueOf(texto.charAt(i));

            if (isDentro) {

                if (letra.contentEquals("}")) {

                    FatiaString fatia = new FatiaString();
                    fatia.setTipo(juntando);
                    fatias.adicionar(fatia);

                    isDentro = false;
                    juntando = "";

                } else {
                    juntando += letra;
                }

            } else {
                if (letra.contentEquals("{")) {
                    fatias.adicionar(new FatiaString(juntando));

                    isDentro = true;
                    juntando = "";
                } else {
                    juntando += letra;
                }

            }
            i += 1;
        }

        if (juntando.length() > 0) {
            fatias.adicionar(new FatiaString(juntando));
        }

        String retornar = "";
        int indice = 0;

        for (FatiaString fatia : fatias) {


            if (fatia.mesmoTipo("STRING")) {
                retornar += fatia.get();
            } else {

                if (fatia.getTipo().startsWith("dir")) {

                    String tam = fatia.getTipo().replace("dir", "");
                    int t = Integer.parseInt(tam);
                    String v = String.valueOf(objetos.get(indice));

                    while (v.length() < t) {
                        v = v + " ";
                    }

                    retornar += v;
                } else if (fatia.getTipo().startsWith("esq")) {


                    String tam = fatia.getTipo().replace("esq", "");
                    int t = Integer.parseInt(tam);
                    String v = String.valueOf(objetos.get(indice));

                    while (v.length() < t) {
                        v = " " + v;
                    }

                    retornar += v;

                } else if (fatia.getTipo().startsWith("f2")) {
                    String v = String.valueOf(objetos.get(indice));

                    if (v.endsWith(".0")) {
                        v = v + "0";
                    } else if (!v.contains(".")) {
                        v = v + ".00";
                    }

                    retornar += v;

                } else {
                    retornar += objetos.get(indice);
                }

                indice += 1;
            }

        }


        return retornar;
    }

    public static String format(String texto, Object ...args) {

        Lista<Object> objetos = new Lista<Object>();
        for(Object arg : args){
            objetos.adicionar(arg);
        }

        return format_text(texto, objetos);
    }


    public static String getN8(long e) {
        String v = String.valueOf(e);
        while (v.length() < 8) {
            v = "0" + v;
        }
        return v;
    }

    public static String getN3(long e) {
        String v = String.valueOf(e);
        while (v.length() < 3) {
            v = "0" + v;
        }
        return v;
    }

    public static String getN2(long e) {
        String v = String.valueOf(e);
        while (v.length() < 2) {
            v = "0" + v;
        }
        return v;
    }

    public static String getCasas(double e, int c) {

        String valor = String.valueOf(e);
        String mRet = "";

        int i = 0;
        int o = valor.length();

        boolean mPontuou = false;
        int dp = 0;

        while (i < o) {
            String l = String.valueOf(valor.charAt(i));
            if (mPontuou) {
                if (dp < c) {
                    mRet += l;
                } else {
                    break;
                }
                dp += 1;
            } else {
                if (l.contentEquals(".")) {
                    if (c > 0) {
                        mRet += l;
                    }
                    mPontuou = true;
                } else {
                    mRet += l;
                }
            }

            i += 1;
        }

        return mRet;

    }


    // PRINT

    public static void print() {
        System.out.println("");
    }

    public static void print(String texto) {
        System.out.println(texto);
    }

    public static void print(int inteiro) {
        System.out.println(inteiro);
    }

    public static void print(String texto, Object ...args) {
        System.out.println(format(texto, args));
    }

    public static void printSemLinha(String texto, Object ...args) {
        System.out.print(format(texto, args));
    }




    // ACESSORIOS

    public static String inFixo(String s, int t) {

        while (s.length() < t) {
            s = " " + s;
        }

        return s;
    }

    public static String inAposFixo(String s, int t) {

        while (s.length() < t) {
            s = s + " ";
        }

        return s;
    }

    public static String intNum(int i, int c) {
        String s = String.valueOf(i);
        while (s.length() < c) {
            s = "0" + s;
        }
        return s;
    }

    public static String longNum(long i, int c) {
        String s = String.valueOf(i);
        while (s.length() < c) {
            s = "0" + s;
        }
        return s;
    }


    public static String doubleNumC2(double numero) {
        String s = String.valueOf(numero);
        String f = "";

        int e = 0;
        int c = 2;

        boolean ja = false;

        int i = 0;
        int o = s.length();
        while (i < o) {
            String letra = String.valueOf(s.charAt(i));
            if (letra.contentEquals(".")) {
                ja = true;
                f += letra;
            } else {
                if (!ja) {
                    f += letra;
                } else {
                    if (e < c) {
                        f += letra;
                    }
                    e += 1;
                }
            }
            i += 1;
        }

        return f;
    }

    public static String doubleNumC3(double numero) {
        String s = String.valueOf(numero);
        String f = "";

        int e = 0;
        int c = 3;

        boolean ja = false;

        int i = 0;
        int o = s.length();
        while (i < o) {
            String letra = String.valueOf(s.charAt(i));
            if (letra.contentEquals(".")) {
                ja = true;
                f += letra;
            } else {
                if (!ja) {
                    f += letra;
                } else {
                    if (e < c) {
                        f += letra;
                    }
                    e += 1;
                }
            }
            i += 1;
        }

        return f;
    }

    public static String f2zerado(double numero) {
        String s = String.valueOf(numero);
        String f = "";

        int e = 0;
        int c = 2;

        boolean ja = false;

        int i = 0;
        int o = s.length();
        while (i < o) {
            String letra = String.valueOf(s.charAt(i));
            if (letra.contentEquals(".")) {
                ja = true;
                f += letra;
            } else {
                if (!ja) {
                    f += letra;
                } else {
                    if (e < c) {
                        f += letra;
                    }
                    e += 1;
                }
            }
            i += 1;
        }

        if (e < c) {
            f += "0";
        }
        return f;
    }


    public static String getTempoFormatado(long t) {

        if (t < 1000) {
            return t + " ms";
        } else {

            int s = 0;
            while (t >= 1000) {
                t -= 1000;
                s += 1;
            }

            if (s >= 60) {
                int min = 0;
                while (s >= 60) {
                    s -= 60;
                    min += 1;
                }

                return min + " min " + s + " s";

            } else {
                return s + " s";
            }

        }

    }


    public long getTempoTarefa(Tarefa eTarefa) {
        long eAntes = System.currentTimeMillis();

        eTarefa.executar();

        long eDepois = System.currentTimeMillis();
        return eDepois - eAntes;
    }

    public String getTempoFormatadoTarefa(Tarefa eTarefa) {
        long eAntes = System.currentTimeMillis();

        eTarefa.executar();

        long eDepois = System.currentTimeMillis();
        return getTempoFormatado(eDepois - eAntes);
    }

    public String getTempoEmSegundosFormatado(long s) {


        if (s >= 60) {
            int min = 0;
            while (s >= 60) {
                s -= 60;
                min += 1;
            }

            return min + " min " + s + " s";

        } else {
            return s + " s";
        }


    }

    public static String espacar_antes(String s, int t) {

        while (s.length() < t) {
            s = " " + s;
        }

        return s;
    }

    public static String espacar_antes(int n, int t) {

        String s = String.valueOf(n);

        while (s.length() < t) {
            s = " " + s;
        }

        return s;
    }

    public static String espacar_antes(long n, int t) {

        String s = String.valueOf(n);

        while (s.length() < t) {
            s = " " + s;
        }

        return s;
    }


    public static String espacar_depois(String s, int t) {

        while (s.length() < t) {
            s = s + " ";
        }

        return s;
    }

    public static String espacar_depois(int i, int t) {

        String s = String.valueOf(i);

        while (s.length() < t) {
            s = s + " ";
        }

        return s;
    }

    public static String espacar_depois(long i, int t) {

        String s = String.valueOf(i);

        while (s.length() < t) {
            s = s + " ";
        }

        return s;
    }

    public static String formatar_tamanho(long t) {

        String ret = "";

        int TAXA_BINARIA = 1024;

        if (t < TAXA_BINARIA) {
            ret = t + " bytes";
        } else {

            long kb = t / TAXA_BINARIA;

            if (kb < TAXA_BINARIA) {
                ret = kb + " Kb";
            } else {

                long mb = kb / TAXA_BINARIA;


                if (mb < TAXA_BINARIA) {
                    ret = mb + " Mb";
                } else {

                    long gb = mb / TAXA_BINARIA;

                    if (gb < TAXA_BINARIA) {
                        ret = gb + " Gb";
                    } else {
                        ret = gb + " Gb";
                    }

                }

            }

        }

        return ret;

    }

    public static String formatar_tamanho_precisao_dupla(long t) {

        String ret = "";

        int TAXA_BINARIA = 1024;

        if (t < TAXA_BINARIA) {
            if(t==1){
                ret = t + " byte";
            }else{
                ret = t + " bytes";
            }
        } else {

            long valor_antes =t;
            long kb = t / TAXA_BINARIA;

            if (kb < TAXA_BINARIA) {

                long bytes_em_kb = kb * TAXA_BINARIA;
                long sobras = valor_antes - bytes_em_kb;

                if(sobras>0){
                    ret = kb + " Kb e " + sobras + " bytes";
                }else{
                    ret = kb + " Kb";
                }

            } else {

                long mb = kb / TAXA_BINARIA;
                long sobras = kb - (mb * 1024);


                if (mb < TAXA_BINARIA) {
                    if(sobras==0){
                        ret = mb + " Mb";
                    }else{
                        ret = mb + " Mb e " + sobras + " Kb";
                    }
                } else {

                    long gb = mb / TAXA_BINARIA;

                    if (gb < TAXA_BINARIA) {
                        ret = gb + " Gb";
                    } else {
                        ret = gb + " Gb";
                    }

                }

            }

        }

        return ret;

    }

    public static String repetir(String rep, int quantidade) {
        String s = "";

        for (int v = 0; v < quantidade; v++) {
            s += rep;
        }
        return s;
    }

    public static String zerado(int v, int quantidade) {
        String s = String.valueOf(v);
        while (s.length() < quantidade) {
            s = "0" + s;
        }
        return s;
    }


    public static String centralizar(String nome, int tracos) {
        int metade = (tracos / 2);
        metade -= nome.length() / 2;

        int completa = 0;

        int completando = metade + nome.length() + 1;
        while (completando <= tracos) {
            completando += 1;
            completa += 1;
        }

        return fmt.format("{}{}{}", fmt.repetir(" ", metade), nome, fmt.repetir(" ", completa));


    }

    public static String centralizar(int valor, int tracos) {
        return centralizar(String.valueOf(valor), tracos);
    }


    // FEATURE 2024 01 02
    public static void exibir_lado_a_lado(Lista<String> lado_a, String entre, Lista<String> lado_b) {
        for (int index = 0; index < lado_a.getQuantidade(); index++) {
            fmt.print("{}{}{}", lado_a.get(index), entre, lado_b.get(index));
        }
    }

    public static void listar_strings(String prefixo, Lista<String> lista) {

        for (String aa : lista) {
            System.out.println(prefixo + aa);
        }

    }

    public static void listar_strings_ordenada(Lista<String> lista) {

        int i = 1;

        for (String aa : lista) {
            System.out.println(i + " - " + aa);
            i += 1;
        }

    }


    public static String double_to_virgular(double d) {
        String s = String.valueOf(d);
        s = s.replace(".", ",");

        return s;
    }


    public static String metros_to_kilometros(double d) {

        if (d >= 1000) {
            int min = 0;
            while (d >= 1000) {
                d -= 1000;
                min += 1;
            }

            return min + " km " + d + " m";

        } else {
            return d + " m";
        }


    }

    public static String metros_to_kilometros_v2(double d) {

        if (d >= 1000) {
            int min = 0;
            while (d >= 1000) {
                d -= 1000;
                min += 1;
            }

            return min + "." + ((int) d) + " km ";

        } else {
            return d + " m";
        }


    }

    public static String format_strings(Lista<String> ls) {

        String ret = "";

        for (String s : ls) {
            ret += s + "\n";
        }

        return ret;
    }

    public static String verticalmente(Lista<String> s_linhas) {

        Lista<String> horizontal = new Lista<String>();

        int maximo = 0;

        for (String linha : s_linhas) {
            if (linha.length() > maximo) {
                maximo = linha.length();
            }
        }

        for (int a = 0; a < maximo; a++) {
            horizontal.adicionar("");
        }


        int ii = 0;

        while (ii < maximo) {

            for (String linha : s_linhas) {
                int o = linha.length();
                if (ii < o) {
                    horizontal.set(ii, horizontal.get(ii) + String.valueOf(linha.charAt(ii)));
                } else {
                    horizontal.set(ii, horizontal.get(ii) + " ");
                }
            }

            ii += 1;
        }


        int e = horizontal.getQuantidade() - 1;
        String ss = "";

        while (e > 0) {
            ss += horizontal.get(e) + "\n";
            e -= 1;
        }

        return ss;
    }

    public static String numero_zerado_c2(String v) {
        String sValor = String.valueOf(v);
        if (sValor.length() == 1) {
            sValor = "0" + sValor;
        }
        return sValor;
    }


    public static String numero_zerado_c2(int v) {
        String sValor = String.valueOf(v);
        if (sValor.length() == 1) {
            sValor = "0" + sValor;
        }
        return sValor;
    }

    public static String numero_zerado_c2(RefInt v) {
        String sValor = String.valueOf(v.get());
        if (sValor.length() == 1) {
            sValor = "0" + sValor;
        }
        return sValor;
    }

    public static String numero_zerado_c2(RefLong v) {
        String sValor = String.valueOf(v.get());
        if (sValor.length() == 1) {
            sValor = "0" + sValor;
        }
        return sValor;
    }

    public static void println() {
        System.out.println();
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void cabecalho(String s, int t) {
        String ss = s + "-";
        while (ss.length() < t) {
            ss += "-";
        }
        fmt.println("--------------------- " + ss);
        fmt.println("");
    }

    public static void sessao(String s, int t) {
        String ss = s + "-";
        while (ss.length() < t) {
            ss += "-";
        }
        fmt.println("--------------------- " + ss);
    }

    public static void sessao_dados(String s, String v, int t) {
        fmt.println("\t >> " + fmt.espacar_depois(s, t) + " = " + v);
    }

    public static void sessao_dados(String s, int v, int t) {
        fmt.println("\t >> " + fmt.espacar_depois(s, t) + " = " + v);
    }

    public static String coluna_tab(String s, int t) {
        return s + "\t";
    }

    public static String coluna_tab(int i, int t) {
        return String.valueOf(i) + "\t";
    }


    public static String formatar_tempo_ate_minutos(long segundos) {
        String tempo = "";

        if (segundos >= 60) {

            int min = 0;

            while (segundos >= 60) {
                segundos -= 60;
                min += 1;
            }

            if (segundos == 0) {
                tempo = min + " min";
            } else {
                tempo = min + " min " + segundos + " s";
            }

        } else {
            tempo = segundos + " s";
        }

        return tempo;
    }

    public static String formatar_tempo(long segundos) {
        String tempo = "";

        if (segundos >= 60) {

            int min = 0;

            while (segundos >= 60) {
                segundos -= 60;
                min += 1;
            }

            if (min > 60) {

                int horas = 0;
                while (min >= 60) {
                    min -= 60;
                    horas += 1;
                }

                if (min == 0 && segundos == 0) {
                    tempo = horas + " h";
                } else if (min > 0 && segundos == 0) {
                    tempo = horas + " h " + min + " min";
                } else {
                    tempo = horas + " h " + min + " min " + segundos + " s";
                }

            } else {
                if (segundos == 0) {
                    tempo = min + " min";
                } else {
                    tempo = min + " min " + segundos + " s";
                }
            }


        } else {
            tempo = segundos + " s";
        }

        return tempo;
    }

    public static String f2(double numero) {
        String s = String.valueOf(numero);
        String f = "";

        int e = 0;
        int c = 2;

        boolean ja = false;

        int i = 0;
        int o = s.length();
        while (i < o) {
            String letra = String.valueOf(s.charAt(i));
            if (letra.contentEquals(".")) {
                ja = true;
                f += letra;
            } else {
                if (!ja) {
                    f += letra;
                } else {
                    if (e < c) {
                        f += letra;
                    }
                    e += 1;
                }
            }
            i += 1;
        }

        if (ja && e < c) {
            f = f + "0";
        }

        return f;
    }

    public static String f4(double numero) {
        String s = String.valueOf(numero);
        String f = "";

        int e = 0;
        int c = 4;

        boolean ja = false;

        int i = 0;
        int o = s.length();
        while (i < o) {
            String letra = String.valueOf(s.charAt(i));
            if (letra.contentEquals(".")) {
                ja = true;
                f += letra;
            } else {
                if (!ja) {
                    f += letra;
                } else {
                    if (e < c) {
                        f += letra;
                    }
                    e += 1;
                }
            }
            i += 1;
        }

        if (ja && e < c) {
            f = f + "0";
        }

        return f;
    }


    public static String f2Porcentagem(double numero) {
        return f2(numero) + " %";
    }


    public static String getTempoEmMilissegundosFormatado(long ms) {

        int s = 0;

        while (ms >= 1000) {
            ms -= 1000;
            s += 1;
        }

        if (s >= 60) {
            int min = 0;
            while (s >= 60) {
                s -= 60;
                min += 1;
            }

            return min + " min " + s + " s";

        } else {
            return s + " s";
        }


    }


    public static void print_titulo_central(String s) {

        print("{}", repetir("-", 100 + s.length()));
        print("{}", espacar_antes(s, 50));
        print("{}", repetir("-", 100 + s.length()));

    }


    public static String f2Porcentagem(int valor, int total) {
        return f2(((double) valor / (double) total) * 100.0) + " %";
    }

    public static String f3(int i){
       String n = String.valueOf(i);
        while(n.length()<3){
            n="0"+n;
        }
        return n;
    }

    public static String f3(String n){
        while(n.length()<3){
            n="0"+n;
        }
        return n;
    }
}
