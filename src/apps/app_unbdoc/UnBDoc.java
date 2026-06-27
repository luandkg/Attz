package apps.app_unbdoc;

import apps.app_unbdoc.comandos.*;
import apps.app_unbdoc.processos.ProcessoConst;
import apps.app_unbdoc.processos.ProcessoMacro;
import apps.app_unbdoc.trechos.*;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;

import java.io.File;

public class UnBDoc {

    private Lista<String> mErros;

    private Lista<Entidade> mAST;

    private int mTeoremaContagem=0;

    private String mTeoremaID ="";

    public UnBDoc() {
        mErros = new Lista<String>();
        mAST = new Lista<Entidade>();
        mTeoremaContagem=0;
    }


    public Lista<String> getErros() {
        return mErros;
    }

    public void processar(String arquivoEntrada, String pastaSaida) {

        mErros.limpar();
        mTeoremaID="";
        mTeoremaContagem=0;

        RefInt capituloID = new RefInt(0);

        fmt.print("Entrada :: {}", arquivoEntrada);
        fmt.print("Saida   :: {}", pastaSaida);

        PastaFS pastaEntrada = new PastaFS(Strings.GET_REVERSO_DEPOIS_DE(arquivoEntrada, "/"));

        String sDocumento = Texto.arquivo_ler(arquivoEntrada);

        TextoDocumento docSaida = new TextoDocumento();

        Entidade documento = ENTT.CRIAR_EM(mAST, "Nome", "DocumentoUnB");
        Entidade raiz = ENTT.CRIAR_EM(documento.getEntidades(), "Nome", "Macros");

        parser(raiz, documento, sDocumento, pastaEntrada, new PastaFS(pastaSaida).getPastaFS("dist"), docSaida, capituloID);

        String conteudo = docSaida.toDocumento();

        Texto.arquivo_escrever(new PastaFS(pastaSaida).getArquivo("dist/" + new File(arquivoEntrada).getName().replace(".unb", "") + ".tex"), Strings.ELIMINAR_LINHAS_VAZIAS(conteudo));


        printAST();

    }

    public void printAST() {

        for (Entidade e : mAST) {
            fmt.print("Objeto {}", e.at("Nome"));
            printASTInternamente(e, 1);
        }


    }

    private void printASTInternamente(Entidade pai, int prefixo) {

        for (Entidade e : pai.getEntidades()) {
            fmt.print("{} ++ Objeto {}", fmt.repetir("\t", prefixo), e.at("Nome"));
            printASTInternamente(e, prefixo + 1);
        }

    }

    public void setTeoremaID(String t){
        mTeoremaID=t;
    }

    public String getTeoremaID(){
        return mTeoremaID;
    }

    public int getTeoremaContagem(){
        return mTeoremaContagem;
    }

    public void teoremaProximo(){
        mTeoremaContagem+=1;
    }

    public void processarInternamente(Entidade raiz, Entidade pai, String arquivoEntrada, PastaFS pastaEntrada, TextoDocumento docSaida, String pastaSaida, RefInt capituloID) {

        fmt.print("Entrada :: {}", arquivoEntrada);
        fmt.print("Saida   :: {}", pastaSaida);


        String sDocumento = Texto.arquivo_ler(arquivoEntrada);

        parser(raiz, pai, sDocumento, pastaEntrada, new PastaFS(pastaSaida), docSaida, capituloID);


    }

    public void parserBloco(Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, TextoDocumento docSaida, RefInt capituloID) {

        while (index.get() < tamanho.get()) {
            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("#")) {
                mErros.adicionar("Que isso #");
            } else if (letra.contentEquals("}")) {
                break;
            } else if (letra.contentEquals("!")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando @ : {} -------------------------------", tipo);

                if (tipo.contentEquals("CAPITULO")) {
                    docSaida.adicionar(TrechoCapitulo.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("APENDICE")) {
                    docSaida.adicionar(TrechoApendice.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("ANEXO")) {
                    docSaida.adicionar(TrechoAnexo.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else {
                    mErros.adicionar("Comando do tipo ! deconhecido :: " + tipo);
                }

            } else if (letra.contentEquals("@")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando @ : {} -------------------------------", tipo);


                if (tipo.contentEquals("RESUMO")) {



                } else if (tipo.contentEquals("ABSTRACT")) {


                } else if (tipo.contentEquals("AGRADECIMENTOS")) {



                } else if (tipo.contentEquals("DEDICATORIA")) {


                } else if (tipo.contentEquals("SIGLAS")) {


                }

            } else if (letra.contentEquals("/")) {

                if (String.valueOf(documento.get().charAt(index.get() + 1)).contentEquals("/")) {

                    index.somar(1);
                    index.somar(1);

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Comentario");

                    while (index.get() < tamanho.get()) {
                        String l = String.valueOf(documento.get().charAt(index.get()));
                        if (l.contentEquals("\n")) {
                            break;
                        }

                        index.somar(1);
                    }


                }
            }

            index.somar(1);
        }

    }


    public void parser(Entidade raiz, Entidade pai, String sDocumento, PastaFS pastaEntrada, PastaFS pastaSaida, TextoDocumento docSaida, RefInt capituloID) {

        RefString documento = new RefString(sDocumento);
        RefInt index = new RefInt(0);
        RefInt tamanho = new RefInt(sDocumento.length());


        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("#")) {
                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Tipo : {} -------------------------------", tipo);

                if (tipo.contentEquals("DOCUMENTO")) {

                    docSaida.adicionar(TrechoDocumento.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());

                } else if (tipo.contentEquals("RAW")) {

                    docSaida.adicionar(TrechoRaw.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());


                } else if (tipo.contentEquals("RAW_SOURCE")) {

                    docSaida.adicionar(TrechoRawSource.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());

                }


            } else if (letra.contentEquals("@")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando @ : {} -------------------------------", tipo);


                if (tipo.contentEquals("IMPORTAR")) {

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Importar");

                    String nomeArquivo = UnBDocParser.parser_entre_parenteses(documento, index, tamanho);

                    if (nomeArquivo.startsWith("\"") && nomeArquivo.endsWith("\"")) {
                        nomeArquivo =nomeArquivo.replace("\"","");
                    }

                    index.somar(1);

                    fmt.print("PASTA ENTRADA :: {}", pastaEntrada.getLocal());
                    fmt.print("ARQUIVO :: {}", nomeArquivo);

                    // PastaFS pastaEntrada2 = new PastaFS(Strings.GET_REVERSO_DEPOIS_DE(nomeArquivo,"/"));


                    processarInternamente(raiz, obj, pastaEntrada.getArquivo(nomeArquivo), pastaEntrada, docSaida, pastaSaida.getLocal(), capituloID);

                } else if (tipo.contentEquals("TEXTO")) {

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Texto");

                    docSaida.adicionarLinha("\\text{}");


                } else if (tipo.contentEquals("}}}")) {

                } else if (tipo.contentEquals("DE")) {

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@DE");

                    docSaida.adicionarLinha(ComandoDe.init(this, raiz, obj, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());

                } else if (tipo.contentEquals("ORIENTADOR")) {

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@ORIENTADOR");

                    index.somar(1);

                    BlocoDeProcessamento bb = new BlocoDeProcessamento();

                    TextoDocumento doc2 = bb.parserConteudo(this, raiz, obj, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);


                    docSaida.adicionarLinha("\\orientador{" + doc2.toDocumento() + "}");

                } else if (tipo.contentEquals("COORDENADORA")) {

                    index.somar(1);

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@COORDENADORA");

                    BlocoDeProcessamento bb = new BlocoDeProcessamento();

                    TextoDocumento doc2 = bb.parserConteudo(this, raiz, obj, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);


                    // index.somar(1);

                    docSaida.adicionarLinha("\\coordenador[a]{" + doc2.toDocumento() + "}");

                } else if (tipo.contentEquals("DATA")) {

                    index.somar(1);

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@DATA");

                    BlocoDeProcessamento bb = new BlocoDeProcessamento();

                    TextoDocumento doc2 = bb.parserConteudo(this, raiz, obj, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);

                    docSaida.adicionarLinha("\\diamesano" + doc2.toDocumento() + "");

                } else if (tipo.contentEquals("MEMBRO_BANCA")) {


                    docSaida.adicionarLinha(ComandoMembroBanca.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());

                } else if (tipo.contentEquals("AUTOR_NOME")) {

                    docSaida.adicionarLinha(ComandoAutorNome.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());

                } else if (tipo.contentEquals("AUTOR_SOBRENOME")) {

                    docSaida.adicionarLinha(ComandoAutorSobrenome.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());


                } else if (tipo.contentEquals("TITULO")) {

                    docSaida.adicionarLinha(ComandoTitulo.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());


                } else if (tipo.contentEquals("PALAVRAS_CHAVES")) {

                    docSaida.adicionarLinha(ComandoPalavrasChaves.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());


                } else if (tipo.contentEquals("KEYWORDS")) {

                    docSaida.adicionarLinha(ComandoKeywords.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());


                } else if (tipo.contentEquals("TERMINAR")) {

                    docSaida.adicionarLinha(ComandoTerminar.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());


                } else if (tipo.contentEquals("MACRO")) {

                    TextoDocumento conteudo = ComandoMacro.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());

                } else if (tipo.contentEquals("CONST")) {

                    TextoDocumento conteudo = ComandoConst.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());

                } else {
                    mErros.adicionar("Comando deconhecido na raiz : @" + tipo);
                }

            } else if (letra.contentEquals("!")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando @ : {} -------------------------------", tipo);

                if (tipo.contentEquals("MACRO")) {

                    ProcessoMacro.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);

                } else if (tipo.contentEquals("CONST")) {

                    ProcessoConst.init(this, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);

                }

            } else if (letra.contentEquals("/")) {

                if (String.valueOf(documento.get().charAt(index.get() + 1)).contentEquals("/")) {

                    index.somar(1);
                    index.somar(1);

                    Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Comentario");

                    while (index.get() < tamanho.get()) {
                        String l = String.valueOf(documento.get().charAt(index.get()));
                        if (l.contentEquals("\n")) {
                            break;
                        }

                        index.somar(1);
                    }


                }

            } else {
                docSaida.adicionar(letra);
            }

            index.somar(1);
        }


    }


}
