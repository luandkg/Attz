package apps.app_dajin;

import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;
import servicos.AmbienteJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CodegenD {

    private Lista<Entidade> erros;
    private boolean compilou = true;

    public void iniciar(Lista<AST> programa, String local_build) {


        erros = new Lista<Entidade>();

        PastaFS pasta_build = new PastaFS(local_build);
        pasta_build.criarPasta("src");
        pasta_build.criarPasta("build");


        PastaFS pasta_build_src = new PastaFS(pasta_build.getPasta("src"));
        PastaFS pasta_build_build = new PastaFS(pasta_build.getPasta("build"));

        TextoDocumento texto_build = new TextoDocumento();

        texto_build.adicionarLinha("{");
        texto_build.adicionarLinha("\"name\": \"quo\",");
        texto_build.adicionarLinha("\"description\": \" Quo é um construtor de frases para commit semântico de forma brilhante e elegante.\",");
        texto_build.adicionarLinha("\"authors\": [\"Luan\"],");
        texto_build.adicionarLinha("\"license\": \"MIT\",");
        texto_build.adicionarLinha("\"targetType\": \"executable\",");
        texto_build.adicionarLinha("\"targetPath\": \"build\",");
        texto_build.adicionarLinha("\"sourcePaths\": [\"src\"]");
        texto_build.adicionarLinha("}");

        Texto.arquivo_escrever(pasta_build.getArquivo("dub.json"), texto_build.toString());

        TextoDocumento texto = new TextoDocumento();


        TextoDocumento importacoeRealizados = new TextoDocumento();


        for (AST item : programa) {

            if (item.is("Funcao")) {

                String funcao_nome = item.getValor();
                AST funcao_tipo = item.get("Tipo");
                AST parametros = item.get("Parametros");


                String real_nome = funcao_nome;

                if(funcao_nome.contentEquals("main")){
                    real_nome=funcao_nome;
                }else{
                    real_nome="funct_" + funcao_nome;
                }

                texto.adicionarLinha(espacamento(2) + " " + tipado(item) + " "+real_nome+ " ( " + parametrizar(parametros) + " ) {");

                escopo(2, texto, item.get("Corpo"));


                texto.adicionarLinha(espacamento(2) + "}");

            } else if (item.is("Tipo")) {

                String nome = item.getValor();

                String nome_tipo = "Tipo_" + item.getValor();
                importacoeRealizados.adicionarLinha("import "+nome_tipo +";");

                TextoDocumento texto_tipo = new TextoDocumento();

             //   texto_tipo.adicionarLinha("module Tipos;");
                texto_tipo.adicionarLinha("import std.stdio;");
                texto_tipo.adicionarLinha("class " + nome_tipo + "{");


                for (AST campo : item.get("Campos").getAST()) {

                    String sVisibilidade = "";
                    if (campo.get("Visibilidade").isValor("RESTRITO")) {
                        sVisibilidade = "";
                    }

                    texto_tipo.adicionarLinha(espacamento(2) + sVisibilidade + " " + tipado(campo) + " " + campo.getValor() + ";");

                }


                for (AST func_ou_acao : item.get("Corpo").getAST()) {

                    if (func_ou_acao.is("Init")) {

                        String funcao_nome = "this";
                        AST parametros = func_ou_acao.get("Parametros");

                        AST astVisibilidade = func_ou_acao.get("Visibilidade");

                        String visibilidade = "";

                        if (astVisibilidade.isValor("RESTRITO")) {
                            visibilidade = "";
                        }

                        texto_tipo.adicionarLinha(espacamento(2) + visibilidade + " " + "this" + " ( " + parametrizar(parametros) + " ) {");

                        escopo(2, texto_tipo, func_ou_acao.get("Corpo"));

                        texto_tipo.adicionarLinha(espacamento(2) + "}");
                    } else if (func_ou_acao.is("Funcao")) {

                        String funcao_nome = func_ou_acao.getValor();
                        AST funcao_tipo = func_ou_acao.get("Tipo");
                        AST parametros = func_ou_acao.get("Parametros");

                        AST astVisibilidade = func_ou_acao.get("Visibilidade");

                        String visibilidade = "";

                        if (astVisibilidade.isValor("RESTRITO")) {
                            visibilidade = "";
                        }

                        texto_tipo.adicionarLinha(espacamento(2) + visibilidade + " " + tipado(func_ou_acao) + " funct_" + funcao_nome + " ( " + parametrizar(parametros) + " ) {");

                        escopo(2, texto_tipo, func_ou_acao.get("Corpo"));


                        texto_tipo.adicionarLinha(espacamento(2) + "}");
                    } else if (func_ou_acao.is("Acao")) {

                        String funcao_nome = func_ou_acao.getValor();
                        AST parametros = func_ou_acao.get("Parametros");

                        AST astVisibilidade = func_ou_acao.get("Visibilidade");

                        String visibilidade = "";

                        if (astVisibilidade.isValor("RESTRITO")) {
                            visibilidade = "";
                        }

                        texto_tipo.adicionarLinha(espacamento(2) + visibilidade + " void funct_" + funcao_nome + " ( " + parametrizar(parametros) + " ) {");

                        escopo(2, texto_tipo, func_ou_acao.get("Corpo"));

                        texto_tipo.adicionarLinha(espacamento(2) + "}");

                    }
                }

                for (AST func_ou_acao : item.get("Operadores").getAST()) {

                    if (func_ou_acao.is("Operador")) {

                        String operador_simbolo = func_ou_acao.getValor();
                        String operador_nome = "";

                        if (Strings.isIgual(operador_simbolo, "+")) {
                            operador_nome = nome_tipo + "_soma";
                        }

                        AST parametros = func_ou_acao.get("Parametros");

                        AST astVisibilidade = func_ou_acao.get("Visibilidade");

                        String visibilidade = "";

                        if (astVisibilidade.isValor("RESTRITO")) {
                            visibilidade = "";
                        }

                        texto_tipo.adicionarLinha(espacamento(2) + visibilidade + " " + tipado(func_ou_acao) + " operator_" + operador_nome + " ( " + parametrizar(parametros) + " ) {");

                        escopo(2, texto_tipo, func_ou_acao.get("Corpo"));


                        texto_tipo.adicionarLinha(espacamento(2) + "}");

                    }
                }

                texto_tipo.adicionarLinha("}");


                fmt.println("----------------------------------------------------");
                fmt.print("{}", texto_tipo.toString());
                fmt.println("----------------------------------------------------");

                pasta_build_src.criarPasta("Tipos");
                PastaFS pasta_build_src_tipos = new PastaFS(new PastaFS(pasta_build.getPasta("src")).getPasta("Tipos"));

                Texto.arquivo_escrever(pasta_build_src_tipos.getArquivo(nome_tipo + ".d"), texto_tipo.toString());

            }


        }


        fmt.println("----------------------------------------------------");
        String principal_completo = importacoeRealizados.toString() + "\n" + texto.toString();
        fmt.println(principal_completo);
        fmt.println("----------------------------------------------------");

        pasta_build_src.criarPasta("Tipos");


        Texto.arquivo_escrever(pasta_build_src.getArquivo("Main_Main.d"), principal_completo);

        compilou = true;


        COMPILAR(pasta_build.getLocal(), pasta_build_build.getLocal(), true);

    }


    public void errar(Token tok, String mensagem) {
        Entidade e = new Entidade();
        e.at("Mensagem", mensagem);
        erros.adicionar(e);
    }

    public boolean temErros() {
        return erros.getQuantidade() > 0;
    }

    public void exibirErros() {
        ENTT.EXIBIR_TABELA_COM_TITULO(erros, "ERROS :: CODEGEN");
    }


    public void executar(String local_build) {

        PastaFS pasta_build = new PastaFS(local_build);

        if (compilou) {
            System.out.println(">> Executando :");
            //  AmbienteJava.JAVA_EXECUTAR_CLASS(pasta_build.getLocal(), pasta_build_bin.getLocal(), "Main_Main");
        }
    }


    public String espacamento(int prefixo) {
        String ret = "";
        for (int p = 0; p < prefixo; p++) {
            ret += "   ";
        }
        return ret;
    }

    public String tipado(AST tipo) {
        String s_tipado = "";

        if (tipo.get("Tipo").get("Vetor").isValor("SIM")) {
            s_tipado = tipo_qualificar(tipo.get("Tipo").getValor()) + " [" + expressao(tipo.get("Indice")) + "] ";
        } else {
            s_tipado = tipo_qualificar(tipo.get("Tipo").getValor()) + " ";
        }
        return s_tipado;
    }

    public String tipo_qualificar(String nome) {
        if (Strings.isIgual(nome, "u8")) {
            return "ubyte";
        } else if (Strings.isIgual(nome, "u16")) {
            return "short";
        } else if (Strings.isIgual(nome, "u32")) {
            return "int";
        } else if (Strings.isIgual(nome, "u64")) {
            return "ulong";

        } else if (Strings.isIgual(nome, "i8")) {
            return "int";

        } else if (Strings.isIgual(nome, "i32")) {
            return "int";


        } else if (Strings.isIgual(nome, "bool")) {
            return "boolean";
        } else {
            nome = "Tipo_" + nome;
        }
        return nome;
    }


    public String expressao(AST conteudo) {
        String string_conteudo = "";



        for (AST con : conteudo.getAST()) {

            System.out.println("@@ VALOR :: ");
            con.exibirAST();


            if (con.is("INTEIRO_LITERAL")) {
                string_conteudo += " " + con.getValor() + " ";
                break;
            } else if (con.is("TEXTO_LITERAL")) {


                string_conteudo += " cast(ubyte[])(\"" + con.getValor() + "\") ";

            } else if (con.is("DEFINIDO")) {

                if (con.existe("Indice")) {
                    string_conteudo += " " + con.getValor() + " [ " + expressao(con.get("Indice")) + " ] ";
                } else {

                    if (con.existe("Tipo") && con.get("Tipo").isValor("FuncaoChamada")) {

                        if (con.getValor().contentEquals("length_of")) {

                            int i = 1;
                            int o = con.get("Argumentos").getAST().getQuantidade();
                            String s_args = "";

                            for (AST arg : con.get("Argumentos").getAST()) {
                                if (i < o) {
                                    s_args += expressao(arg) + ",";
                                } else {
                                    s_args += expressao(arg);
                                }
                                i += 1;
                            }

                            string_conteudo += s_args + ".length ";

                        } else {

                            int i = 1;
                            int o = con.get("Argumentos").getAST().getQuantidade();
                            String s_args = "";

                            for (AST arg : con.get("Argumentos").getAST()) {
                                if (i < o) {
                                    s_args += expressao(arg) + ",";
                                } else {
                                    s_args += expressao(arg);
                                }
                                i += 1;
                            }

                            string_conteudo += " funct_" + con.getValor() + " ( " + s_args + " ) ";
                        }


                    } else if (con.existe("Tipo") && con.get("Tipo").isValor("TipoInvocacao")) {

                        String s_args = "";

                        System.out.println("@@@ AQU -- ");
                        con.exibirAST();


                        if (con.existe("Metodo") && con.get("Metodo").isValor("init")) {

                            System.out.println("@@@ init -- ");
                            con.exibirAST();

                            int i = 1;
                            int o = con.get("Argumentos").getAST().getQuantidade();

                            for (AST arg : con.get("Argumentos").getAST()) {

                                System.out.println("@@@ init item -- ");
                                arg.exibirAST();

                                if (i < o) {
                                    s_args += expressao(arg) + ",";
                                } else {
                                    s_args += expressao(arg);
                                }
                                i += 1;
                            }

                        }

                        string_conteudo += " new Tipo_" + con.getValor() + " (" + s_args + " ) ";

                    } else if (con.existe("Tipo") && con.get("Tipo").isValor("Internamente")) {

                        string_conteudo += " " + con.getValor() + "." + expressao(con.get("Internamente"));

                    } else {
                        string_conteudo += " " + con.getValor() + " ";
                    }

                }

                break;
            } else if (con.is("SLICE_MAKE")) {

                string_conteudo += " new " + tipadoExpressao(con);


                break;
            } else if (con.is("EXPRESSAO")) {

                String esquerda = expressao(con.get("ESQUERDA"));
                String operador = "";
                String direita = expressao(con.get("DIREITA"));


                fmt.println(">> TIPIFICADOR ESQ :: " + esquerda + " -->> " + Tipificador.tipar(con.get("ESQUERDA")));
                fmt.println(">> TIPIFICADOR DIR :: " + direita + " -->> " + Tipificador.tipar(con.get("DIREITA")));

                if (con.isValor("SOMA")) {
                    operador = "+";
                } else if (con.isValor("SUBTRACAO")) {
                    operador = "-";
                } else if (con.isValor("MULT")) {
                    operador = "*";
                } else if (con.isValor("DIV")) {
                    operador = "/";
                } else if (con.isValor("IGUALDADE")) {
                    operador = "==";
                } else if (con.isValor("MAIOR")) {
                    operador = ">";
                } else if (con.isValor("MENOR")) {
                    operador = "<";
                } else if (con.isValor("MAIOR_IGUAL")) {
                    operador = ">=";
                } else if (con.isValor("MENOR_IGUAL")) {
                    operador = "<=";
                }

                string_conteudo += " " + esquerda + " " + operador + " " + direita;
            }
        }
        return string_conteudo;
    }

    public String tipadoExpressao(AST tipo) {
        String s_tipado = "";

        if (tipo.get("Tipo").get("Vetor").isValor("SIM")) {
            s_tipado = tipo_qualificar(tipo.get("Tipo").getValor()) + " [" + expressao(tipo.get("Tipo").get("Indice")) + "] ";
        } else {
            s_tipado = tipo_qualificar(tipo.get("Tipo").getValor()) + " ";
        }
        return s_tipado;
    }

    public String parametrizar(AST pai) {

        String ret = "";

        int i = 1;
        int o = pai.getAST().getQuantidade();

        for (AST arg : pai.getAST()) {

            String s_tipo = tipo_qualificar(arg.get("Tipo").getValor());

            if (arg.get("Tipo").existe("Vetor")) {
                if (arg.get("Tipo").get("Vetor").isValor("SIM")) {
                    s_tipo += "[]";
                }
            }

            if (i < o) {
                ret += s_tipo + " " + arg.getValor() + ",";
            } else {
                ret += s_tipo + " " + arg.getValor() + " ";
            }

            i += 1;

        }


        return ret;
    }

    public void escopo(int pai_prefixo, TextoDocumento texto, AST escopo_itens) {


        for (AST item_corpo : escopo_itens.getAST()) {

            if (item_corpo.is("DEF")) {

                String variavel_nome = item_corpo.getValor();
                String variavel_tipo = item_corpo.get("Tipo").getValor();
                String variavel_slice = item_corpo.get("Tipo").get("Vetor").getValor();

                boolean is_slice = Strings.isIgual(variavel_slice, "SIM");

                AST conteudo = item_corpo.get("Conteudo");

                String mSlice = "";
                if (is_slice) {
                    mSlice = " []";
                }

                if (conteudo.isValor("SIM")) {

                    String string_conteudo = expressao(conteudo);

                    texto.adicionarLinha(espacamento(pai_prefixo + 2) + tipo_qualificar(variavel_tipo) + " " + mSlice + " " + variavel_nome + " = " + string_conteudo + ";");
                } else {
                    texto.adicionarLinha(espacamento(pai_prefixo + 2) + tipo_qualificar(variavel_tipo) + " " + mSlice + " " + variavel_nome + " " + ";");
                }

            } else if (item_corpo.is("ARROBA")) {

                AST rotina = item_corpo.get("Rotina");
                AST conteudo = item_corpo.get("Conteudo");

                if (item_corpo.isValor("@dajin")) {
                    if (rotina.isValor("print_u8")) {

                        String s_conteudo = expressao(conteudo);

                        texto.adicionarLinha(espacamento(pai_prefixo + 2) + "write(" + s_conteudo + ");");
                    } else if (rotina.isValor("println_u8")) {

                        String s_conteudo = expressao(conteudo);

                        texto.adicionarLinha(espacamento(pai_prefixo + 2) + "write(" + s_conteudo + ");");
                    } else if (rotina.isValor("println_u64")) {

                        String s_conteudo = expressao(conteudo);

                        texto.adicionarLinha(espacamento(pai_prefixo + 2) + "write(" + s_conteudo + ");");
                    } else if (rotina.isValor("println_slice_u8_as_string")) {

                        String s_conteudo = expressao(conteudo);

                        texto.adicionarLinha(espacamento(pai_prefixo + 2) + "write( (" + s_conteudo + "));");
                    } else {
                        errar(conteudo.getToken(), "Função desconhecida : " + rotina.getValor());
                    }
                }

            } else if (item_corpo.is("RETURN")) {

                AST conteudo = item_corpo.get("Conteudo");
                texto.adicionarLinha(espacamento(pai_prefixo + 2) + "return " + expressao(conteudo) + " ;");


            } else if (item_corpo.is("ATRIBUIR")) {


                String para = "";

                AST destino = item_corpo.get("DESTINO").get("ID");

                if (destino.existe("TemIndice") && destino.get("TemIndice").isValor("SIM")) {
                    para = destino.getValor() + " [ " + expressao(destino.get("Indice")) + " ] ";
                } else {
                    para = destino.getValor();
                }

                if (destino.get("Tipo").isValor("Internamente")) {
                    para += "." + destino.get("Internamente").get("DEFINIDO").getValor();
                }

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + para + " = " + expressao(item_corpo.get("Conteudo")) + ";");

            } else if (item_corpo.is("IF")) {

                String s_condicao = expressao(item_corpo.get("CONDICAO"));

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + " if ( " + s_condicao + " ) {");

                escopo(pai_prefixo + 2, texto, item_corpo.get("ENTAO"));


                for (AST outra_cond : item_corpo.get("SUB_CONDICOES").getAST()) {

                    String s_outra = expressao(outra_cond.get("CONDICAO"));

                    texto.adicionarLinha(espacamento(pai_prefixo + 2) + "} else if ( " + s_outra + " ) {");

                    escopo(pai_prefixo + 2, texto, outra_cond.get("ENTAO"));

                }


                texto.adicionarLinha(espacamento(pai_prefixo + 2) + "} else {");

                escopo(pai_prefixo + 2, texto, item_corpo.get("OUTRA_CONDICAO"));

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + "} ");

            } else if (item_corpo.is("WHILE")) {

                String s_condicao = expressao(item_corpo.get("CONDICAO"));

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + " while ( " + s_condicao + " ) {");

                escopo(pai_prefixo + 2, texto, item_corpo.get("ENTAO"));

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + "} ");

            } else if (item_corpo.is("LOOP")) {


                texto.adicionarLinha(espacamento(pai_prefixo + 2) + "while ( true ) {");

                escopo(pai_prefixo + 2, texto, item_corpo);

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + "} ");

            } else if (item_corpo.is("BREAK")) {
                texto.adicionarLinha(espacamento(pai_prefixo + 2) + " break; ");
            } else if (item_corpo.is("CONTINUE")) {
                texto.adicionarLinha(espacamento(pai_prefixo + 2) + " continue; ");

            } else if (item_corpo.is("ID")) {


                String para = "";

                AST destino = item_corpo;

                if (destino.existe("TemIndice") && destino.get("TemIndice").isValor("SIM")) {
                    para = destino.getValor() + " [ " + expressao(destino.get("Indice")) + " ] ";
                } else {
                    para = destino.getValor();
                }

                String mais = "";

                if (destino.get("Tipo").isValor("Internamente")) {
                    para += ".funct_" + destino.get("Internamente").get("DEFINIDO").getValor();

                    AST interno = destino.get("Internamente").get("DEFINIDO");


                    if (interno.existe("Tipo") && interno.get("Tipo").isValor("FuncaoChamada")) {

                        mais += "(";

                        int i = 1;
                        int o = interno.get("Argumentos").getAST().getQuantidade();
                        String s_args = "";

                        for (AST arg : interno.get("Argumentos").getAST()) {
                            if (i < o) {
                                s_args += expressao(arg) + ",";
                            } else {
                                s_args += expressao(arg);
                            }
                            i += 1;
                        }

                        mais += s_args;
                        mais += " )";

                    }
                }

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + para + mais + " ;");


            } else if (item_corpo.is("FUNCAO_CHAMAR")) {

                int i = 1;
                int o = item_corpo.get("Argumentos").getAST().getQuantidade();
                String s_args = "";

                for (AST arg : item_corpo.get("Argumentos").getAST()) {
                    if (i < o) {
                        s_args += expressao(arg) + ",";
                    } else {
                        s_args += expressao(arg);
                    }
                    i += 1;
                }

                texto.adicionarLinha(espacamento(pai_prefixo + 2) + " funct_" + item_corpo.getValor() + " ( " + s_args + " ) ;");

            }


        }


    }

    public static boolean COMPILAR(String local_projeto, String local_build_bin, boolean ocultar) {


        boolean sucesso = false;

        String comando = "";

        try {

            File workingFolder = new File(local_projeto);


            ProcessBuilder pb = new ProcessBuilder("dub", "run");
            // ProcessBuilder pb = new ProcessBuilder("pwd");

            pb.directory(workingFolder);


            for (String a : pb.command()) {
                comando += a + " ";
            }

            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));

            sucesso = true;

            String s = null;
            String t1 = "";
            while ((s = stdInput.readLine()) != null) {
                t1 += s + "\n";
            }

            if (!t1.isEmpty() && !ocultar) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t1);
                fmt.println(fmt.repetir("-", 40));
            }

            String s1 = null;
            String t2 = "";

            while ((s1 = stdError.readLine()) != null) {
                t2 += s1 + "\n";
                sucesso = false;
            }

            if (!t2.isEmpty()) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t2);
                fmt.println(fmt.repetir("-", 40));
            }


        } catch (Exception e) {
            fmt.print("COMANDO >> {}", comando);
            fmt.print("ERRO    >> {}", e.getMessage());
        }

        return sucesso;
    }

}
