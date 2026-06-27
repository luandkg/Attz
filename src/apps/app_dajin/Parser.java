package apps.app_dajin;

import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.luan.Opcional;
import libs.luan.Strings;
import libs.luan.fmt;

public class Parser {

    private Lista<Token> tokens;
    private Lista<Entidade> erros;

    private int i = 0;
    private int o = 0;

    private Lista<AST> mAST;

    private ParserAssessorios pa;

    public void parse(Lista<Token> eTokens) {
        tokens = eTokens;
        erros = new Lista<Entidade>();
        mAST = new Lista<AST>();

        tokens= removerComentarios(tokens);

        i = 0;
        o = tokens.getQuantidade();


        pa = new ParserAssessorios(this);

        while (i < o) {
            Token tk_raiz = getTokenCorrente();

            if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "funct")) {

                parserFuncao("PUBLICO", mAST);

            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "act")) {

                parserAcao("PUBLICO", mAST);

            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "type")) {

                parserTipo();

            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "impl")) {

                parserImplementacao();

            } else {
                errar(tk_raiz, "Era esperado ( funct | act | type | impl ) mas obtive : " + tk_raiz.getValor());
            }

            i += 1;
        }


    }

    public Lista<Token> removerComentarios(Lista<Token> entrada){
        Lista<Token> saida = new Lista<Token>();

        for(Token e : tokens){
            if(e.getTipo()!=TokenTipo.COMENTARIO_SIMPLES && e.getTipo()!=TokenTipo.COMENTARIO_BLOCO){
                saida.adicionar(e);
            }
        }
        return saida;
    }

    public void parserFuncao(String visibilidade, Lista<AST> pai) {
        Opcional<String> funcao_nome = pa.esperado_identificador("Era esperado o nome do funct !");
        if (funcao_nome.isVazio()) {
            return;
        }

        AST funcao = new AST("Funcao");
        funcao.setValor(funcao_nome.get());

        AST astVisibilidade = funcao.criarAST("Visibilidade");
        astVisibilidade.setValor(visibilidade);

        pai.adicionar(funcao);

        AST argumentos = funcao.criarAST("Parametros");

        parserArgumentos(argumentos);

        pa.esperado_identificador_especifico_mas_obtive("with", "Era esperado ( WITH ) mas obtive : ");


        tipar(funcao);

        AST corpo = funcao.criarAST("Corpo");

        exibirAST();
        parserEscopo(corpo);
    }

    public void parserAcao(String visibilidade, Lista<AST> pai) {
        Opcional<String> funcao_nome = pa.esperado_identificador("Era esperado o nome da act !");
        if (funcao_nome.isVazio()) {
            return;
        }

        AST acao = new AST("Acao");
        acao.setValor(funcao_nome.get());

        AST astVisibilidade = acao.criarAST("Visibilidade");
        astVisibilidade.setValor(visibilidade);


        pai.adicionar(acao);

        AST argumentos = acao.criarAST("Parametros");

        parserArgumentos(argumentos);

        AST corpo = acao.criarAST("Corpo");

        exibirAST();
        parserEscopo(corpo);
    }

    public void parserInit(String visibilidade, Lista<AST> pai) {
        Opcional<String> funcao_nome = pa.esperado_identificador("Era esperado o nome do init !");
        if (funcao_nome.isVazio()) {
            return;
        }

        AST init = new AST("Init");
        init.setValor(funcao_nome.get());

        AST astVisibilidade = init.criarAST("Visibilidade");
        astVisibilidade.setValor(visibilidade);


        pai.adicionar(init);

        AST argumentos = init.criarAST("Parametros");

        parserArgumentos(argumentos);

        AST corpo = init.criarAST("Corpo");

        exibirAST();
        parserEscopo(corpo);
    }


    public void parserOperador(String visibilidade, Lista<AST> pai) {
        Opcional<String> funcao_nome = pa.esperado_operador("Era esperado o tipo do operador !");
        if (funcao_nome.isVazio()) {
            return;
        }

        AST funcao = new AST("Operador");
        funcao.setValor(funcao_nome.get());

        AST astVisibilidade = funcao.criarAST("Visibilidade");
        astVisibilidade.setValor(visibilidade);

        pai.adicionar(funcao);

        AST argumentos = funcao.criarAST("Parametros");

        parserArgumentos(argumentos);

        pa.esperado_identificador_especifico_mas_obtive("with", "Era esperado ( WITH ) mas obtive : ");


        tipar(funcao);

        AST corpo = funcao.criarAST("Corpo");

        exibirAST();
        parserEscopo(corpo);
    }

    public void parserTipo() {
        Opcional<String> funcao_nome = pa.esperado_identificador("Era esperado o nome do Tipo !");
        if (funcao_nome.isVazio()) {
            return;
        }

        AST tipo = criarAST("Tipo");
        tipo.setValor(funcao_nome.get());

        AST campos = tipo.criarAST("Campos");

        parserTipoCampos(campos);
    }

    public void parserArgumentos(AST pai) {

        if (!pa.esperado_abrir_parenteses()) {
            return;
        }

        proximo();

        boolean mais = true;

        while (i < o) {
            Token tk_raiz = getTokenCorrente();

            if (ParserChecador.IS_PARENTESES_FECHAR(tk_raiz)) {
                break;
            } else if (ParserChecador.IS_VIRGULA(tk_raiz)) {
                mais = true;
            } else {
                parseArgumento(pai);
                mais = false;
            }
            proximo();
        }

    }

    public void parseArgumento(AST pai) {

        voltar();

        AST arg = pai.criarAST("Parametro");
        Opcional<String> arg_def = pa.esperado_identificador_especifico_mas_obtive("def", "Era esperado : ");
        Opcional<String> arg_nome = pa.esperado_identificador("Era esperado o nome do parametro");

        if (arg_nome.isOK()) {
            arg.setValor(arg_nome.get());
        }

        Opcional<String> arg_with = pa.esperado_identificador_especifico_mas_obtive("with", "Era esperado : ");

        tipar(arg);


    }

    public void parserArgumentosValores(AST pai) {

        if (!pa.esperado_abrir_parenteses()) {
            return;
        }

        proximo();

        boolean mais = true;


        while (i < o) {
            Token tk_raiz = getTokenCorrente();

            if (ParserChecador.IS_PARENTESES_FECHAR(tk_raiz)) {
                break;
            } else if (ParserChecador.IS_VIRGULA(tk_raiz)) {
                mais = true;
            } else {
                AST valor = pai.criarAST("Valor");
                parserValor(valor);
                mais = false;
            }
            proximo();
        }

    }

    public void parserEscopo(AST pai) {

        if (!pa.esperado_abrir_chaves()) {
            return;
        }

        Token escopo_aberto = getTokenCorrente();
        proximo();

        boolean finalizado = false;

        while (i < o) {
            Token tk_raiz = getTokenCorrente();


            if (ParserChecador.IS_CHAVES_FECHAR(tk_raiz)) {
                finalizado = true;
                break;
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "def")) {
                parserDef(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "return")) {
                parserReturn(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "if")) {
                parserIf(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "while")) {
                parserWhile(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "loop")) {
                parseLoop(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "break")) {
                parseBreak(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "continue")) {
                parseContinue(pai);
            } else if (ParserChecador.IS_ARROBA(tk_raiz)) {
                parserArroba(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz)) {
                parserID(pai);
            } else {
                errar(tk_raiz, "Termo desconhecido : " + tk_raiz.getValor());
            }
            proximo();


        }

        if (!finalizado) {
            errar(escopo_aberto, "Escopo nao finalizado !");
        }
    }

    public boolean parserValor(AST pai) {


        boolean finalizado = false;

        while (i < o) {
            Token tk_raiz = getTokenCorrente();

            if (ParserChecador.IS_PONTO_E_VIRGULA(tk_raiz)) {
                finalizado = true;
                break;
            } else if (ParserChecador.IS_INTEIRO_LITERAL(tk_raiz)) {
                parserTermo(pai);
            } else if (ParserChecador.IS_TEXTO_LITERAL(tk_raiz)) {
                System.out.println("Texto :: "+getTokenCorrente().getValor());
                parserTermo(pai);
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz)) {
                parserTermo(pai);
            } else {
                // errar(tk_raiz, "Termo desconhecido : " + tk_raiz.getValor());
                voltar();
                break;
            }

            proximo();
        }

        return finalizado;
    }

    public boolean parserTermo(AST pai) {

        AST noAST = new AST("");

        boolean finalizado = false;

        while (i < o) {
            Token tk_raiz = getTokenCorrente();

            if (ParserChecador.IS_INTEIRO_LITERAL(tk_raiz)) {

                AST variavel_conteudo = new AST("INTEIRO_LITERAL");
                variavel_conteudo.setValor(tk_raiz.getValor());

                noAST = variavel_conteudo;

            } else if (ParserChecador.IS_TEXTO_LITERAL(tk_raiz)) {

                AST variavel_conteudo = new AST("TEXTO_LITERAL");
                variavel_conteudo.setValor(tk_raiz.getValor());

                noAST = variavel_conteudo;

            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz)) {

                if (Strings.isIgual(tk_raiz.getValor(), "slice_make")) {

                    AST variavel_conteudo = new AST("SLICE_MAKE");

                    pa.esperado_menor();
                    tipar(variavel_conteudo);
                    pa.esperado_maior();

                    variavel_conteudo.get("Tipo").get("Vetor").setValor("SIM");
                    indice(variavel_conteudo.get("Tipo"));

                    noAST = variavel_conteudo;

                } else {
                    noAST = parserEspecifico(tk_raiz);
                }


            } else if (ParserChecador.IS_OPERADOR_SOMA(tk_raiz)) {

                proximo();
                AST variavel_conteudo = new AST("EXPRESSAO");
                variavel_conteudo.setValor("SOMA");
                variavel_conteudo.criarAST("ESQUERDA").adicionar(noAST);
                AST noDireito = variavel_conteudo.criarAST("DIREITA");
                parserTermo(noDireito);

                noAST = variavel_conteudo;

            } else if (ParserChecador.IS_OPERADOR_SUBTRACAO(tk_raiz)) {

                proximo();
                AST variavel_conteudo = new AST("EXPRESSAO");
                variavel_conteudo.setValor("SUBTRACAO");
                variavel_conteudo.criarAST("ESQUERDA").adicionar(noAST);
                AST noDireito = variavel_conteudo.criarAST("DIREITA");
                parserTermo(noDireito);

                noAST = variavel_conteudo;
            } else if (ParserChecador.IS_OPERADOR_MULT(tk_raiz)) {

                proximo();
                AST variavel_conteudo = new AST("EXPRESSAO");
                variavel_conteudo.setValor("MULT");
                variavel_conteudo.criarAST("ESQUERDA").adicionar(noAST);
                AST noDireito = variavel_conteudo.criarAST("DIREITA");
                parserTermo(noDireito);

                noAST = variavel_conteudo;
            } else if (ParserChecador.IS_OPERADOR_DIV(tk_raiz)) {

                proximo();
                AST variavel_conteudo = new AST("EXPRESSAO");
                variavel_conteudo.setValor("DIV");
                variavel_conteudo.criarAST("ESQUERDA").adicionar(noAST);
                AST noDireito = variavel_conteudo.criarAST("DIREITA");
                parserTermo(noDireito);

                noAST = variavel_conteudo;

            } else if (ParserChecador.IS_OPERADOR_IGUALDADE(tk_raiz)) {

                proximo();
                AST variavel_conteudo = new AST("EXPRESSAO");
                variavel_conteudo.setValor("IGUALDADE");
                variavel_conteudo.criarAST("ESQUERDA").adicionar(noAST);
                AST noDireito = variavel_conteudo.criarAST("DIREITA");
                parserTermo(noDireito);

                noAST = variavel_conteudo;

            } else if (ParserChecador.IS_OPERADOR_MAIOR(tk_raiz)) {

                proximo();
                AST variavel_conteudo = new AST("EXPRESSAO");
                variavel_conteudo.setValor("MAIOR");
                variavel_conteudo.criarAST("ESQUERDA").adicionar(noAST);
                AST noDireito = variavel_conteudo.criarAST("DIREITA");
                parserTermo(noDireito);

                noAST = variavel_conteudo;

            } else if (ParserChecador.IS_OPERADOR_MENOR(tk_raiz)) {

                proximo();
                AST variavel_conteudo = new AST("EXPRESSAO");
                variavel_conteudo.setValor("MENOR");
                variavel_conteudo.criarAST("ESQUERDA").adicionar(noAST);
                AST noDireito = variavel_conteudo.criarAST("DIREITA");
                parserTermo(noDireito);

                noAST = variavel_conteudo;

            } else {
                voltar();
                break;
            }

            proximo();
        }

        pai.adicionar(noAST);


        return finalizado;
    }

    public AST parserEspecifico(Token tk_raiz) {

        AST variavel_conteudo = new AST("DEFINIDO");
        variavel_conteudo.setValor(tk_raiz.getValor());


        proximo();
        Token agora = getTokenCorrente();

        boolean teveIndice = false;

        if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor("[")) {
            teveIndice = true;
            voltar();
            indice_apenas(variavel_conteudo);

        } else if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor("(")) {
            voltar();

            variavel_conteudo.criarAST("Tipo").setValor("FuncaoChamada");
            AST args = variavel_conteudo.criarAST("Argumentos");

            parserArgumentosValores(args);

            proximo();
            Token agora2 = getTokenCorrente();
            if (agora2.getTipo() == TokenTipo.DELIMITADOR && agora2.isValor("[")) {
                voltar();
                indice_apenas(variavel_conteudo.criarAST("ACESSO"));
            } else {
                voltar();
            }

        } else if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor("::")) {

            voltar();
            //  errar(getTokenCorrente(), tk_raiz.getValor());
            //   debug();

            variavel_conteudo.criarAST("Tipo").setValor("TipoInvocacao");
            AST invocacao = variavel_conteudo.criarAST("Invocacao");
            AST metodo = variavel_conteudo.criarAST("Metodo");
            invocacao.setValor(tk_raiz.getValor());

            proximo();
            proximo();

            Token a = getTokenCorrente();
            metodo.setValor(a.getValor());

            AST args = variavel_conteudo.criarAST("Argumentos");

            parserArgumentosValores(args);

        } else if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor(".")) {

            voltar();
            //  errar(getTokenCorrente(), tk_raiz.getValor());
            //   debug();

            variavel_conteudo.criarAST("Tipo").setValor("Internamente");
            AST metodo = variavel_conteudo.criarAST("Internamente");
            metodo.setValor("");

            proximo();
            proximo();

            Token a = getTokenCorrente();
            //  metodo.setValor(a.getValor());

            AST novo = parserEspecifico(a);
            metodo.adicionar(novo);
        } else {
            voltar();
        }


        return variavel_conteudo;
    }

    public void parserDef(AST pai) {
        AST variavel = pai.criarAST("DEF");

        Opcional<String> variavel_nome = pa.esperado_identificador("Era esperado o nome da variável !");
        if (variavel_nome.isVazio()) {
            return;
        }

        variavel.setValor(variavel_nome.get());

        pa.esperado_identificador_especifico_mas_obtive("with", "Era esperado ( WITH ) mas obtive : ");


        AST variavel_forma = variavel.criarAST("Forma");
        variavel_forma.setValor("PRIMITIVA");


        tipar(variavel);


        if (!pa.obrigacaoTerProximo()) {
            return;
        }

        boolean var_finalizado = false;

        Token prox = getTokenCorrente();

        if (ParserChecador.IS_OPERADOR_IGUAL(prox)) {

            AST variavel_conteudo = variavel.criarAST("Conteudo");
            variavel_conteudo.setValor("SIM");

            proximo();
            parserValor(variavel_conteudo);

            prox = getTokenCorrente();

            if (ParserChecador.IS_PONTO_E_VIRGULA(prox)) {
                var_finalizado = true;
            }

        } else if (ParserChecador.IS_PONTO_E_VIRGULA(prox)) {
            var_finalizado = true;
        }

        if (!var_finalizado) {
            errar(prox, "Era esperado ponto e virgula !");
        }

        System.out.println("DEBUG :: "+getTokenCorrente().getValor());

    }

    public void parserID(AST pai) {
        AST variavel = pai.criarAST("ID");

        Token corrente = getTokenCorrente();

        variavel.setValor(corrente.getValor());

        proximo();
        Token agora = getTokenCorrente();

        boolean teveIndice = false;

        if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor("[")) {
            teveIndice = true;
            voltar();
            indice_apenas(variavel);
            proximo();
        }

        agora = getTokenCorrente();


        if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor("(")) {

            variavel.setTipo("FUNCAO_CHAMAR");

            voltar();
            parserArgumentosValores(variavel.criarAST("Argumentos"));
            proximo();
            agora = getTokenCorrente();
        }


        if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor(".")) {

            voltar();
            //  errar(getTokenCorrente(), tk_raiz.getValor());
            //   debug();

            variavel.criarAST("Tipo").setValor("Internamente");
            AST metodo = variavel.criarAST("Internamente");
            metodo.setValor("");

            proximo();
            proximo();

            Token a = getTokenCorrente();
            //  metodo.setValor(a.getValor());

            AST novo = parserEspecifico(a);
            metodo.adicionar(novo);

            proximo();
            agora = getTokenCorrente();

            if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor(";")) {
                return;
            }

        }

        if (agora.getTipo() == TokenTipo.OPERADOR && agora.isValor("=")) {

            //AST atribuir_local = new AST("ATRIBUIR");
            //atribuir_local.setValor(corrente.getValor());


            if (teveIndice) {
                AST ast = variavel.criarAST("TemIndice", "SIM");
                variavel.adicionar(variavel.get("Indice"));
                variavel.remover("Indice");
            } else {
                variavel.criarAST("TemIndice", "NAO");
            }

            AST atribuir_local = new AST("ATRIBUIR");
            AST destino = atribuir_local.criarAST("DESTINO");
            destino.adicionar(variavel.getClone());

         //   variavel.setValor("");
         //   variavel.setTipo("ATRIBUIR");


            proximo();

            AST conteudo = atribuir_local.criarAST("Conteudo");
            conteudo.setValor("SIM");
            parserValor(conteudo);

            pai.adicionar(atribuir_local);
            pai.remover(variavel);
        }


    }

    public void parserArroba(AST pai) {

        Token arroba_nome = getTokenCorrente();
        AST variavel = pai.criarAST("ARROBA");

        variavel.setValor(arroba_nome.getValor());

        Opcional<String> delimitador = pa.esperado_delimitador("::", "Era esperado o delimitador ::");
        if (delimitador.isVazio()) {
            return;
        }

        Opcional<String> variavel_nome = pa.esperado_identificador("Era esperado o nome da rotina !");
        if (variavel_nome.isVazio()) {
            return;
        }

        AST rotina = variavel.criarAST("Rotina");
        AST conteudo = variavel.criarAST("Conteudo");

        rotina.setValor(variavel_nome.get());

        pa.esperado_abrir_parenteses();

        proximo();

        parserValor(conteudo);

        pa.esperado_fechar_parenteses();

        pa.esperado_ponto_e_virgula();

    }

    public void parserValores(AST pai) {

        if (!pa.esperado_abrir_parenteses()) {
            return;
        }

        proximo();

        while (i < o) {
            Token tk_raiz = getTokenCorrente();

            if (ParserChecador.IS_PARENTESES_FECHAR(tk_raiz)) {
                break;
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz)) {

                AST variavel_conteudo = pai.criarAST("VARIAVEL");
                variavel_conteudo.setValor(tk_raiz.getValor());

            } else if (ParserChecador.IS_INTEIRO_LITERAL(tk_raiz)) {

                AST variavel_conteudo = pai.criarAST("INTEIRO_LITERAL");
                variavel_conteudo.setValor(tk_raiz.getValor());


            } else {
                errar(tk_raiz, "Termo desconhecido : " + tk_raiz.getValor());
            }
            proximo();
        }

    }


    public void parserReturn(AST pai) {
        AST variavel = pai.criarAST("RETURN");

        AST variavel_conteudo = variavel.criarAST("Conteudo");

        proximo();
        if (!parserValor(variavel_conteudo)) {
            pa.esperado_ponto_e_virgula();
        }

    }

    public void parserIf(AST pai) {
        AST variavel = pai.criarAST("IF");
        AST condicao = variavel.criarAST("CONDICAO");
        AST entao = variavel.criarAST("ENTAO");
        AST sub_condicao = variavel.criarAST("SUB_CONDICOES");
        AST outra = variavel.criarAST("OUTRA_CONDICAO");

        // proximo();

        pa.esperado_abrir_parenteses();

        proximo();

        parserValor(condicao);

        pa.esperado_fechar_parenteses();

        parserEscopo(entao);

        proximo();

        boolean continuarIF = true;

        while (continuarIF) {
            Token corrente = getTokenCorrente();


            int a = i;
            proximo();

            if (i >= o) {
                break;
            }

            Token proximo = getTokenCorrente();


            if ((ParserChecador.IS_IDENTIFICADOR(corrente) && corrente.isValor("else")) && !(ParserChecador.IS_IDENTIFICADOR(proximo) && proximo.isValor("if"))) {
                //  proximo();

                i = a;
                parserEscopo(outra);
                continuarIF = false;
                break;


            } else if (ParserChecador.IS_IDENTIFICADOR(corrente) && corrente.isValor("else") && (ParserChecador.IS_IDENTIFICADOR(proximo) && proximo.isValor("if"))) {


                pa.esperado_abrir_parenteses();

                proximo();

                AST outra_condicao = sub_condicao.criarAST("OUTRA_CONDICAO");
                AST o_condicao = outra_condicao.criarAST("CONDICAO");
                AST o_entao = outra_condicao.criarAST("ENTAO");

                parserValor(o_condicao);

                pa.esperado_fechar_parenteses();

                parserEscopo(o_entao);

                proximo();

            } else {
                i = a;
                voltar();
                break;
            }

        }


    }

    public void parserWhile(AST pai) {

        AST variavel = pai.criarAST("WHILE");
        AST condicao = variavel.criarAST("CONDICAO");
        AST entao = variavel.criarAST("ENTAO");


        pa.esperado_abrir_parenteses();

        proximo();

        parserValor(condicao);

        pa.esperado_fechar_parenteses();

        parserEscopo(entao);

        //  proximo();
    }

    public void parseLoop(AST pai) {
        AST variavel = pai.criarAST("LOOP");

        parserEscopo(variavel);
    }

    public void parseBreak(AST pai) {
        AST variavel = pai.criarAST("BREAK");
        pa.esperado_ponto_e_virgula();
    }

    public void parseContinue(AST pai) {
        AST variavel = pai.criarAST("CONTINUE");
        pa.esperado_ponto_e_virgula();
    }


    public void parserImplementacao() {

        Opcional<String> nome_geral = pa.esperado_identificador("Era esperado o nome do Tipo para implementar !");
        if (nome_geral.isVazio()) {
            return;
        }

        proximo();

        Token agora = getTokenCorrente();

        //pa.esperado_delimitador("::", "Era esperado delimitador ::");

        String visibilidade = "PUBLICO";
        String implementacao = "FUNCOES_ACOES";


        if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor("::")) {

            Opcional<String> definicao_visibilidade = pa.esperado_identificador("Era esperado um mecanismo de definição ou visibilidade !");

            if (definicao_visibilidade.isOK()) {
                if (definicao_visibilidade.get().contentEquals("public")) {
                    visibilidade = "PUBLICO";
                } else if (definicao_visibilidade.get().contentEquals("restrict")) {
                    visibilidade = "RESTRITO";
                } else if (definicao_visibilidade.get().contentEquals("operator")) {
                    implementacao = "OPERADORES";
                } else {
                    errar(getTokenCorrente(), "Visibilidade desconhecida !");
                }
            }
        } else if (agora.getTipo() == TokenTipo.DELIMITADOR && agora.isValor("{")) {
            voltar();
        }


        boolean enc = false;
        AST tipoAST = null;

        for (AST proc : mAST) {
            if (proc.is("Tipo") && proc.isValor(nome_geral.get())) {
                enc = true;
                tipoAST = proc;
                break;
            }
        }


        if (!tipoAST.existe("Corpo")) {
            tipoAST.criarAST("Corpo");
        }

        if (!tipoAST.existe("Operadores")) {
            tipoAST.criarAST("Operadores");
        }


        if (!enc) {
            errar(getTokenCorrente(), "Tipo nao encontrado : " + nome_geral.get());
        }


        if (!pa.esperado_abrir_chaves()) {
            return;
        }

        Token escopo_aberto = getTokenCorrente();
        proximo();

        boolean finalizado = false;

        if (implementacao.contentEquals("FUNCOES_ACOES")) {

            while (i < o) {
                Token tk_raiz = getTokenCorrente();

                if (ParserChecador.IS_CHAVES_FECHAR(tk_raiz)) {
                    finalizado = true;
                    break;
                } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "funct")) {
                    parserFuncao(visibilidade, tipoAST.get("Corpo").getAST());
                } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "act")) {
                    parserAcao(visibilidade, tipoAST.get("Corpo").getAST());
                } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "init")) {
                    parserInit(visibilidade, tipoAST.get("Corpo").getAST());
                } else {
                    errar(tk_raiz, "Termo desconhecido : " + tk_raiz.getValor());
                }
                proximo();
            }
        } else if (implementacao.contentEquals("OPERADORES")) {

            while (i < o) {
                Token tk_raiz = getTokenCorrente();

                if (ParserChecador.IS_CHAVES_FECHAR(tk_raiz)) {
                    finalizado = true;
                    break;
                } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "operator")) {
                    parserOperador(visibilidade, tipoAST.get("Operadores").getAST());
                } else {
                    errar(tk_raiz, "Termo desconhecido : " + tk_raiz.getValor());
                }
                proximo();
            }

        }


        if (!finalizado) {
            errar(escopo_aberto, "Escopo nao finalizado !");
        }


    }


    public void debug() {
        errar(getTokenCorrente(), "?? " + getTokenCorrente().getValor());
    }

    public void tipar(AST noPai) {

        AST tipado = noPai.criarAST("Tipo");

        AST tipado_slice = tipado.criarAST("Vetor");
        tipado_slice.setValor("NAO");

        Opcional<String> tk_tipo = pa.esperado_identificador_mas_obtive("Era esperado ( TIPO ) mas obtive : ");
        if (tk_tipo.isVazio()) {
            return;
        }

        if (Strings.isIgual(tk_tipo.get(), "slice_of")) {

            tipado_slice.setValor("SIM");


            if (!pa.esperado_menor()) {
                return;
            }

            Opcional<String> tk_tipo_slice = pa.esperado_identificador_mas_obtive("Era esperado ( TIPO ) mas obtive : ");
            if (tk_tipo_slice.isVazio()) {
                return;
            }

            if (!pa.esperado_maior()) {
                return;
            }

            tipado.setValor(tk_tipo_slice.get());

        } else {
            tipado.setValor(tk_tipo.get());
        }


    }

    public void indice(AST noPai) {

        AST tipado = noPai.criarAST("Indice");

        boolean esp = pa.esperado_abrir_colchetes();
        if (!esp) {
            return;
        }
        proximo();
        parserValor(tipado);
        esp = pa.esperado_fechar_colchetes();

        if (!esp) {
            return;
        }
        esp = pa.esperado_abrir_parenteses();

        if (!esp) {
            return;
        }
        esp = pa.esperado_fechar_parenteses();

        if (!esp) {
            return;
        }
    }

    public void indice_apenas(AST noPai) {

        AST tipado = noPai.criarAST("Indice");

        boolean ret = pa.esperado_abrir_colchetes();

        if (!ret) {
            return;
        }
        proximo();
        parserValor(tipado);
        ret = pa.esperado_fechar_colchetes();


    }


    public void parserTipoCampos(AST pai) {

        if (!pa.esperado_abrir_chaves()) {
            return;
        }

        Token escopo_aberto = getTokenCorrente();
        proximo();

        boolean finalizado = false;

        while (i < o) {
            Token tk_raiz = getTokenCorrente();

            if (ParserChecador.IS_CHAVES_FECHAR(tk_raiz)) {
                finalizado = true;
                break;
            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "public")) {

                AST campo = pai.criarAST("Campo");

                Opcional<String> nome = pa.esperado_identificador("Era esperado o nome de um campo");

                if (!nome.isOK()) {
                    return;
                }

                campo.setValor(nome.get());
                campo.criarAST("Visibilidade").setValor("PUBLICO");

                pa.esperado_identificador_especifico_mas_obtive("with", "Era esperado : ");

                tipar(campo);

                pa.esperado_ponto_e_virgula();

            } else if (ParserChecador.IS_IDENTIFICADOR(tk_raiz) && ParserChecador.IS_PALAVRA_CHAVE(tk_raiz, "restrict")) {

                AST campo = pai.criarAST("Campo");

                Opcional<String> nome = pa.esperado_identificador("Era esperado o nome de um campo");

                if (!nome.isOK()) {
                    return;
                }

                campo.setValor(nome.get());
                campo.criarAST("Visibilidade").setValor("RESTRITO");

                pa.esperado_identificador_especifico_mas_obtive("with", "Era esperado : ");

                tipar(campo);

                pa.esperado_ponto_e_virgula();
            } else {
                errar(tk_raiz, "Termo desconhecido : " + tk_raiz.getValor());
            }
            proximo();
        }

        if (!finalizado) {
            errar(escopo_aberto, "Escopo nao finalizado !");
        }
    }


    public void exibirAST() {

        int prefixo = 0;

        for (AST e : mAST) {
            System.out.println(getPrefixo(prefixo) + "++ " + e.getTipo() + " :: " + e.getValor());
            exibirASTInterno(prefixo + 1, e);
        }

    }

    public void exibirASTInterno(int prefixo, AST pai) {


        for (AST e : pai.getAST()) {
            System.out.println(getPrefixo(prefixo) + "++ " + e.getTipo() + " :: " + e.getValor());
            exibirASTInterno(prefixo + 1, e);
        }

    }


    public String getPrefixo(int prefixo) {
        String ret = "";
        for (int p = 0; p < prefixo; p++) {
            ret += "   ";
        }
        return ret;
    }

    public void errar(Token token_ref, String mensagem) {
        Entidade erro = new Entidade();
        erro.at("Linha", token_ref.getLinha());
        erro.at("Coluna", token_ref.getColuna());
        erro.at("Mensagem", mensagem);
        erros.adicionar(erro);
    }

    public boolean temAgora() {
        return (i) < o;
    }

    public void proximo() {
        i += 1;
    }

    public void voltar() {
        i -= 1;
    }

    public boolean temProximo() {
        return (i + 1) < o;
    }

    public Token getTokenCorrente() {
        return tokens.get(i);
    }


    public Lista<Entidade> getErros() {
        return erros;
    }

    public boolean estaOK() {
        return erros.getQuantidade() == 0;
    }

    public Lista<AST> getAST() {
        return mAST;
    }

    public AST criarAST(String tipo) {
        AST novo = new AST(tipo);
        mAST.adicionar(novo);
        return novo;
    }
}
