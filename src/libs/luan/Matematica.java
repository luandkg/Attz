package libs.luan;

import libs.meta_functional.AcaoAlfa;

public class Matematica {

    public static int maior(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    public static int menor(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    public static double normaliza(double a) {
        if (a < 0) {
            return a * (-1);
        } else {
            return a;
        }
    }

    public static int getProporcionadoInt(double taxa, double valor) {
        return (int) (taxa * valor);
    }


    public static double criarTaxa(int total, int por) {
        return (double) total / (double) por;
    }

    public static int aumentar_ate(int corrente, int taxa, int limite) {

        corrente += taxa;
        if (corrente >= limite) {
            corrente = limite;
        }

        return corrente;
    }

    public static int diminuir_ate(int corrente, int taxa, int limite) {

        corrente -= taxa;
        if (corrente <= limite) {
            corrente = limite;
        }

        return corrente;
    }

    public static boolean isPar(int numero) {
        return (numero % 2) == 0;
    }

    public static boolean isImpar(int numero) {
        return (numero % 2) == 1;
    }

    public static Ordenavel<Double> DOUBLE_COMPARADOR() {
        return new Ordenavel<Double>() {
            @Override
            public int emOrdem(Double a, Double b) {
                return Double.compare(a, b);
            }
        };
    }


    public static double NORMALIZAR(double valor, double min, double max) {

        if (valor < min) {
            valor = min;
        }

        if (valor > max) {
            valor = max;
        }

        return valor;
    }

    public static int NORMALIZAR(int valor, int min, int max) {

        if (valor < min) {
            valor = min;
        }

        if (valor > max) {
            valor = max;
        }

        return valor;
    }


    public static int ESCALA_NORMAL(double valor, double maximo) {
        return (int) (((double) valor / (double) maximo) * 100.0);
    }


    public static int KB(int eKB) {
        return eKB * 1024;
    }

    public static int MB(int eMB) {
        return eMB * (1024 * 1024);
    }

    public static int POSITIVO(int valor) {
        if (valor < 0) {
            return valor * (-1);
        } else {
            return valor;
        }
    }

    public static int NEGATIVO(int valor) {
        if (valor < 0) {
            return valor;
        } else {
            return valor * (-1);
        }
    }

    public static String CONDICIONAL(boolean condicao, String valido, String nao_valido) {
        if (condicao) {
            return valido;
        } else {
            return nao_valido;
        }
    }

    public static int CONDICIONAL(boolean condicao, int valido, int nao_valido) {
        if (condicao) {
            return valido;
        } else {
            return nao_valido;
        }
    }


    public static boolean isNumero(String s) {
        boolean ret = false;

        String digitos = "0123456789";

        int i = 0;
        int o = s.length();

        if (o > 0) {
            ret = true;

            boolean tem_decimal = false;

            String p = String.valueOf(0);

            if (p.contentEquals("+") || p.contentEquals("-")) {
                i += 1;
            }

            while (i < o) {

                String d = String.valueOf(s.charAt(i));

                if (d.contentEquals(".") || d.contentEquals(",")) {
                    tem_decimal = true;
                    i += 1;
                    break;
                } else {
                    if (!digitos.contains(d)) {
                        ret = false;
                        break;
                    }
                }


                i += 1;
            }

            if (tem_decimal) {
                while (i < o) {

                    String d = String.valueOf(s.charAt(i));

                    if (!digitos.contains(d)) {
                        ret = false;
                        break;
                    }

                    i += 1;
                }

            }
        }


        return ret;
    }

    public static boolean isNumeroInteiro(String s) {
        boolean ret = false;

        String digitos = "0123456789";

        int i = 0;
        int o = s.length();

        if (o > 0) {
            ret = true;

            String p = String.valueOf(0);

            if (p.contentEquals("+") || p.contentEquals("-")) {
                i += 1;
            }

            while (i < o) {

                String d = String.valueOf(s.charAt(i));

                if (!digitos.contains(d)) {
                    // fmt.print("OI : {}",d);
                    ret = false;
                    break;
                }


                i += 1;
            }

        }


        return ret;
    }


    public static int ate_zero(int valor) {
        if (valor < 0) {
            return 0;
        }
        return valor;
    }


    public static void PARA_CADA(int eInicio, int eFim, AcaoAlfa<Integer> eAcao) {
        for (int i = eInicio; i < eFim; i++) {
            eAcao.fazer(i);
        }
    }


    public static Igualavel<Long> LONG_IGUALAVEL() {
        return new Igualavel<Long>() {
            @Override
            public boolean is(Long a, Long b) {
                return a.longValue() == b.longValue();
            }

        };
    }


    public static int MODULO(int valor) {
        if (valor < 0) {
            return valor * (-1);
        } else {
            return valor;
        }
    }

    public static Ordenavel<Integer> INTEIRO_ORDENAVEL() {
        return new Ordenavel<Integer>() {
            @Override
            public int emOrdem(Integer a, Integer b) {
                return Integer.compare(a, b);
            }
        };
    }

    public static Igualavel<Integer> INTEIRO_IGUALAVEL() {
        return new Igualavel<Integer>() {
            @Override
            public boolean is(Integer a, Integer b) {
                return a.intValue() == b.intValue();
            }

        };
    }


    public static int POTENCIA(int base, int potencia) {

        int numero = base;

        while (potencia > 1) {
            numero = numero * base;
            potencia -= 1;
        }

        return numero;
    }

    public static int PERMUTACAO_QUANTIDADE(int valor, int repeticoes) {

        int numero = repeticoes;

        while (valor > 1) {
            numero = numero * repeticoes;
            valor -= 1;
        }

        return numero;
    }

    public static <T> Lista<T> PERMUTACOES(int operadores, Lista<T> opcoes, Concatenador<T> eConcatenador) {

        int operacoes = Matematica.PERMUTACAO_QUANTIDADE(operadores, opcoes.getQuantidade());

        Lista<T> operadores_valores = new Lista<T>();

        for (int a = 0; a < operacoes; a++) {
            operadores_valores.adicionar(null);
        }

        int indice_trocar_valor = operacoes / opcoes.getQuantidade();
        int montante_anterior = indice_trocar_valor;

        for (int operador_id = 0; operador_id < operadores; operador_id++) {

            // fmt.print("OP :: {} -->> {} :: {} == a {}", operador_id, divisor,indice_trocar_valor,montante_anterior);

            int i = 0;
            T OPERADOR_CORRENTE = opcoes.get(0);

            int op_indice = 0;
            int indice_valor_corrente = 0;

            for (T op : operadores_valores) {
                if (i == indice_trocar_valor) {

                    indice_valor_corrente += 1;
                    if (indice_valor_corrente == opcoes.getQuantidade()) {
                        indice_valor_corrente = 0;
                    }

                    OPERADOR_CORRENTE = opcoes.get(indice_valor_corrente);

                    i = 0;
                }

                if (operadores_valores.get(op_indice) == null) {
                    operadores_valores.set(op_indice, OPERADOR_CORRENTE);
                } else {
                    operadores_valores.set(op_indice, eConcatenador.concatenar(op, OPERADOR_CORRENTE));
                }

                i += 1;
                op_indice += 1;
            }

            indice_trocar_valor = montante_anterior / opcoes.getQuantidade();
            montante_anterior = indice_trocar_valor;

        }

        return operadores_valores;
    }

    public static <T> Vetor<T> PERMUTACOES_EM_VETOR(int operadores, Lista<T> opcoes, Concatenador<T> eConcatenador) {

        int operacoes = Matematica.PERMUTACAO_QUANTIDADE(operadores, opcoes.getQuantidade());

        Vetor<T> operadores_valores = new Vetor<T>(operacoes);

        for (int a = 0; a < operacoes; a++) {
            operadores_valores.set(a, null);
        }

        int indice_trocar_valor = operacoes / opcoes.getQuantidade();
        int montante_anterior = indice_trocar_valor;

        for (int operador_id = 0; operador_id < operadores; operador_id++) {

            // fmt.print("OP :: {} -->> {} :: {} == a {}", operador_id, divisor,indice_trocar_valor,montante_anterior);

            int i = 0;
            T OPERADOR_CORRENTE = opcoes.get(0);

            int op_indice = 0;
            int indice_valor_corrente = 0;

            for (T op : operadores_valores) {
                if (i == indice_trocar_valor) {

                    indice_valor_corrente += 1;
                    if (indice_valor_corrente == opcoes.getQuantidade()) {
                        indice_valor_corrente = 0;
                    }

                    OPERADOR_CORRENTE = opcoes.get(indice_valor_corrente);

                    i = 0;
                }

                if (operadores_valores.get(op_indice) == null) {
                    operadores_valores.set(op_indice, OPERADOR_CORRENTE);
                } else {
                    operadores_valores.set(op_indice, eConcatenador.concatenar(op, OPERADOR_CORRENTE));
                }

                i += 1;
                op_indice += 1;
            }

            indice_trocar_valor = montante_anterior / opcoes.getQuantidade();
            montante_anterior = indice_trocar_valor;

        }

        return operadores_valores;
    }


    public static int POSITIVO_OU_NEGATIVO(int valor) {
        if (Aleatorio.aleatorio(100) >= 50) {
            return valor;
        } else {
            return valor * (-1);
        }
    }

    public static <T> Lista<T> COMBINACOES(Lista<T> valores_a, Lista<T> valores_b, Concatenador<T> eConcatenador) {
        Lista<T> combinacoes = new Lista<T>();

        for (T a : valores_a) {
            for (T b : valores_b) {
                combinacoes.adicionar(eConcatenador.concatenar(a, b));
            }
        }

        return combinacoes;
    }

    public static <T> Lista<T> COMBINACOES(Lista<T> valores_a, Lista<T> valores_b, Lista<T> valores_c, Concatenador<T> eConcatenador) {
        Lista<T> combinacoes = new Lista<T>();

        for (T a : valores_a) {
            for (T b : valores_b) {
                for (T c : valores_c) {
                    T ab = eConcatenador.concatenar(a, b);
                    combinacoes.adicionar(eConcatenador.concatenar(ab, c));
                }
            }
        }

        return combinacoes;
    }


}
