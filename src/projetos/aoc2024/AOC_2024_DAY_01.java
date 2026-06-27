package projetos.aoc2024;

import libs.luan.*;

public class AOC_2024_DAY_01 extends  AOC_2024_DAY {

    public AOC_2024_DAY_01( ){
        super(1,"");
    }

    @Override
    public void parte_1() {
        AOC_2024.CABECALHO(getProblemaNumero(),AOC_2024.PARTE_1);

        fmt.print("------------- ENTRADA ----------------");

        String texto_dica = Texto.arquivo_ler(AOC_2024.GET_ARQUIVO("DAY_01.txt"));
        fmt.print("{}",texto_dica);

        Lista<Integer> esquerda = new Lista<Integer>();
        Lista<Integer> direita = new Lista<Integer>();

        int i =0;
        for(String linha : Strings.DIVIDIR_LINHAS(texto_dica)){

            int n1 = Integer.parseInt(Strings.GET_ATE(linha," "));
            int n2 = Integer.parseInt(Strings.GET_REVERSO_ATE(linha," "));

            fmt.print("\t ++ esquerda({}) = {} --- direita({}) = {}",i,n1,i,n2);

            esquerda.adicionar(n1);
            direita.adicionar(n2);

            i+=1;
        }

        fmt.print(">> Ordenando....");
        Ordenador.ORDENAR_CRESCENTE_LISTA(esquerda, Matematica.INTEIRO_ORDENAVEL());
        Ordenador.ORDENAR_CRESCENTE_LISTA(direita, Matematica.INTEIRO_ORDENAVEL());

        fmt.print(">> Exibindo");

        int distancia_total = 0;

        for(int a=0;a<esquerda.getQuantidade();a++){

            int esquerda_valor = esquerda.get(a);
            int direita_valor = direita.get(a);

            int distancia = Matematica.MODULO(esquerda_valor-direita_valor);

            fmt.print("\t ++ esquerda({}) = {} --- direita({}) = {} :: {}",a,esquerda_valor,a,direita_valor,distancia);

            distancia_total+=distancia;
        }

        fmt.print(">> Distancia total = {}",distancia_total);
    }

    @Override
    public void parte_2() {

        AOC_2024.CABECALHO(getProblemaNumero(),AOC_2024.PARTE_2);

        fmt.print("------------- ENTRADA ----------------");

        String texto_dica = Texto.arquivo_ler(AOC_2024.GET_ARQUIVO("DAY_01.txt"));
        fmt.print("{}",texto_dica);

        Lista<Integer> esquerda = new Lista<Integer>();
        Lista<Integer> direita = new Lista<Integer>();

        int i =0;
        for(String linha : Strings.DIVIDIR_LINHAS(texto_dica)){

            int n1 = Integer.parseInt(Strings.GET_ATE(linha," "));
            int n2 = Integer.parseInt(Strings.GET_REVERSO_ATE(linha," "));

            fmt.print("\t ++ esquerda({}) = {} --- direita({}) = {}",i,n1,i,n2);

            esquerda.adicionar(n1);
            direita.adicionar(n2);

            i+=1;
        }


        fmt.print(">> Exibindo");

        int similaridade_total = 0;

        int indice=0;
        for(Integer esquerda_valor : esquerda){

            int contagem_valor= direita.contar(Matematica.INTEIRO_IGUALAVEL(),esquerda_valor);

            int similaridade = esquerda_valor*contagem_valor;

            fmt.print("\t ++ esquerda({}) = {} -->> {} :: {}",indice,esquerda_valor,contagem_valor,similaridade);

            similaridade_total+=similaridade;
            indice+=1;
        }

        fmt.print(">> Similaridade = {}",similaridade_total);

    }
}
