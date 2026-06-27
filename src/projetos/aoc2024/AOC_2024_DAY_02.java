package projetos.aoc2024;

import libs.luan.*;

public class AOC_2024_DAY_02 extends AOC_2024_DAY {

    public AOC_2024_DAY_02() {
        super(2,"");
    }

    @Override
    public void parte_1() {

        AOC_2024.CABECALHO(getProblemaNumero(), AOC_2024.PARTE_1);

        fmt.print("------------- ENTRADA ----------------");
        String texto_entrada = Texto.arquivo_ler(AOC_2024.GET_ARQUIVO("DAY_02.txt"));
        fmt.print("{}", texto_entrada);

        fmt.print(">> Processando");

        int contagem_safe = 0;

        for (String linha : Strings.DIVIDIR_LINHAS(texto_entrada)) {

            Resultado<Boolean,String> nivel = validar_nivel(Strings.DIVIDIR_POR_SEM_DIVISOR(linha, " "));

            if (nivel.isOK()) {
                contagem_safe += 1;
                fmt.print("\t ++ {} :: {}", linha, Portugues.VALIDAR(nivel.isOK(), "Safe", "Unsafe"));
            } else {
                fmt.print("\t ++ {} :: {} -->> {}", linha, Portugues.VALIDAR(nivel.isOK(), "Safe", "Unsafe"), nivel.getErro());
            }
        }

        fmt.print(">> Resultado : {}", contagem_safe);

    }

    @Override
    public void parte_2() {

        AOC_2024.CABECALHO(getProblemaNumero(), AOC_2024.PARTE_2);

        fmt.print("------------- ENTRADA ----------------");
        String texto_entrada = Texto.arquivo_ler(AOC_2024.GET_ARQUIVO("DAY_02.txt"));
        fmt.print("{}", texto_entrada);

        fmt.print(">> Processando");

        int contagem_safe = 0;

        for (String linha : Strings.DIVIDIR_LINHAS(texto_entrada)) {


            Resultado<Boolean,String> nivel = validar_nivel(Strings.DIVIDIR_POR_SEM_DIVISOR(linha, " "));

            boolean problema_dampener_detectado=false;
            String dampener_nivel_organizado = "";

            if (nivel.isErro()) {

                Lista<String> nivel_valores = Strings.DIVIDIR_POR_SEM_DIVISOR(linha, " ");

                fmt.print("\t Construido :: {}",Strings.LISTA_TO_TEXTO_LINHA(nivel_valores));

                for(int remove_unico_item=0;remove_unico_item<nivel_valores.getQuantidade();remove_unico_item++){
                    Lista<String> nivel_construido = nivel_valores.getCopia();
                    nivel_construido.remover_indice(remove_unico_item);

                    int i =0;
                    dampener_nivel_organizado="";
                    for(String item : nivel_valores){
                        if(i==remove_unico_item){
                            dampener_nivel_organizado+="<"+item+">" +" ";
                        }else{
                            dampener_nivel_organizado+=item +" ";
                        }
                        i+=1;
                    }

                    Resultado<Boolean,String> nivel_construido_validado = validar_nivel(nivel_construido);

                    if(nivel_construido_validado.isOK()){
                        problema_dampener_detectado=true;
                        break;
                    }

                 //   fmt.print("\t\t Construido :: {} -->> {}",Strings.LISTA_TO_TEXTO_LINHA(nivel_construido),nivel_construido_validado.isOK());
                }


            }

            if (nivel.isOK()) {
                contagem_safe += 1;
                fmt.print("\t ++ {} :: {}", linha, Portugues.VALIDAR(nivel.isOK(), "Safe", "Unsafe"));
            }else if (problema_dampener_detectado){
                contagem_safe += 1;
                fmt.print("\t ++ {} -->> Dampener {} :: {}", linha,dampener_nivel_organizado, Portugues.VALIDAR(problema_dampener_detectado, "Safe", "Unsafe"));

            } else {
                fmt.print("\t ++ {} :: {} -->> {}", linha, Portugues.VALIDAR(nivel.isOK(), "Safe", "Unsafe"), nivel.getErro());
            }
        }

        fmt.print(">> Resultado : {}", contagem_safe);

    }

    public static Resultado<Boolean,String> validar_nivel(Lista<String> nivel){

        boolean safe = true;
        String erro = "";

        boolean iniciado = false;
        int valor_anterior = 0;

        boolean regra_definida = false;
        final int REGRA_AUMENTANDO = +1;
        final int REGRA_DIMINUINDO = -1;
        int regra = 0;


        for (String s_valor_corrente : nivel) {
            int valor_corrente = Integer.parseInt(s_valor_corrente);
            if (!iniciado) {
                iniciado = true;
            } else {
                int diferenca = Matematica.MODULO(valor_anterior - valor_corrente);
                if (diferenca >= 1 && diferenca <= 3) {
                    if (!regra_definida) {
                        regra_definida = true;
                        if (valor_anterior < valor_corrente) {
                            regra = REGRA_AUMENTANDO;
                        } else {
                            regra = REGRA_DIMINUINDO;
                        }
                    } else {
                        int regra_corrente = 0;
                        if (valor_anterior < valor_corrente) {
                            regra_corrente = REGRA_AUMENTANDO;
                        } else {
                            regra_corrente = REGRA_DIMINUINDO;
                        }
                        if (regra != regra_corrente) {
                            erro = valor_anterior + " " + valor_corrente + " -->> Mudanca de Regra !";
                            safe = false;
                            break;
                        }
                    }
                } else {
                    erro = valor_anterior + " " + valor_corrente + " -->> " + diferenca;
                    safe = false;
                    break;
                }
            }

            valor_anterior = valor_corrente;

        }

        if(safe){
            return Resultado.OK(true);
        }else{
            return Resultado.FALHAR(erro);
        }

    }
}
