package apps.app_unbdoc;

import apps.app_unbdoc.comandos.*;
import apps.app_unbdoc.comandos.secao.ComandoSecao;
import apps.app_unbdoc.comandos.secao.ComandoSecaoTitulo;
import apps.app_unbdoc.comandos.tabela.*;
import apps.app_unbdoc.partes.*;
import apps.app_unbdoc.trechos.TrechoAnexo;
import apps.app_unbdoc.trechos.TrechoApendice;
import apps.app_unbdoc.trechos.TrechoCapitulo;
import apps.app_unbdoc.trechos.TrechoDocumento;
import apps.app_unbdoc.utils.ItemAspas;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;

public class BlocoDeProcessamento {

    public TextoDocumento parserConteudo(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {
        TextoDocumento txt = parserBlocoEmParsenteses(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
        txt.trim();
        return txt;
    }

    public TextoDocumento parserBlocoEmParsenteses(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        TextoDocumento docSaida = new TextoDocumento();

        while (index.get() < tamanho.get()) {
            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("@")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando @ : {} -------------------------------", tipo);

                if (tipo.contentEquals("DOCUMENTO")) {
                    docSaida.adicionarLinha(ComandoDocumento.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("IMPORTAR")) {
                    docSaida.adicionarLinha(ComandoImportar.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TEXTO")) {
                    docSaida.adicionarLinha(ComandoTexto.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DESTAQUE")) {
                    docSaida.adicionarLinha(ComandoDestaque.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("CITACAO")) {
                    docSaida.adicionarLinha(ComandoCitacao.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("REFERENCIA")) {
                    docSaida.adicionarLinha(ComandoReferencia.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TABELA")) {
                    docSaida.adicionarLinha(ComandoTabela.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("LEGENDA")) {
                    docSaida.adicionarLinha(ComandoLegenda.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TABELA_REFERENCIADA")) {
                    docSaida.adicionarLinha(ComandoTabelaReferenciada.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DADOS")) {
                    docSaida.adicionarLinha(ComandoDados.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DADOS_COLUNAS")) {
                    docSaida.adicionarLinha(ComandoDadosColunas.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TEOREMA")) {
                    docSaida.adicionarLinha(ComandoTeorema.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("AXIOMA_TITULO")) {
                    docSaida.adicionarLinha(ComandoAxiomaTitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("AXIOMA_REFERENCIA")) {
                    docSaida.adicionarLinha(ComandoAxiomaReferencia.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("AXIOMA_TERMINAR")) {
                    docSaida.adicionarLinha(ComandoAxiomaTerminar.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TEOREMA_TITULO")) {
                    docSaida.adicionarLinha(ComandoTeoremaTitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TEOREMA_AXIOMA")) {
                    docSaida.adicionarLinha(ComandoTeoremaAxioma.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("MATEMATICA")) {
                    docSaida.adicionarLinha(ComandoMatematica.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("EQUACAO_REFERENCIADA")) {
                    docSaida.adicionarLinha(ComandoEquacaoReferenciada.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("EQUACAO")) {
                    docSaida.adicionarLinha(ComandoEquacao.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("PROF")) {
                    docSaida.adicionarLinha(ComandoProfessor.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("PROFA")) {
                    docSaida.adicionarLinha(ComandoProfessora.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DRA")) {
                    docSaida.adicionarLinha(ComandoDoutora.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DR")) {
                    docSaida.adicionarLinha(ComandoDoutor.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("MACRO")) {
                    docSaida.adicionarLinha(ComandoMacro.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("MEMBRO_BANCA")) {
                    docSaida.adicionarLinha(ComandoMembroBanca.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DE")) {
                    docSaida.adicionarLinha(ComandoDe.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("AUTOR_NOME")) {
                    docSaida.adicionarLinha(ComandoAutorNome.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("AUTOR_SOBRENOME")) {
                    docSaida.adicionarLinha(ComandoAutorSobrenome.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("TITULO")) {
                    docSaida.adicionarLinha(ComandoTitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("PALAVRAS_CHAVES")) {
                    docSaida.adicionarLinha(ComandoPalavrasChaves.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("KEYWORDS")) {
                    docSaida.adicionarLinha(ComandoKeywords.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("TERMINAR")) {
                    docSaida.adicionarLinha(ComandoTerminar.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("CONST")) {

                    TextoDocumento conteudo = ComandoConst.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());

                } else if (tipo.contentEquals("RESUMO")) {

                    TextoDocumento conteudo = ParteResumo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());

                } else if (tipo.contentEquals("ABSTRACT")) {

                    TextoDocumento conteudo = ParteAbstract.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());
                } else if (tipo.contentEquals("DEDICATORIA")) {

                    TextoDocumento conteudo = ParteDedicatoria.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());

                } else if (tipo.contentEquals("SIGLAS")) {

                    TextoDocumento conteudo = ParteSiglas.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());

                } else if (tipo.contentEquals("AGRADECIMENTOS")) {

                    TextoDocumento conteudo = ParteAgradecimentos.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
                    docSaida.adicionar(conteudo.toDocumento());

                } else if (tipo.contentEquals("LINHA_INICIO")) {
                    docSaida.adicionarLinha(ComandoLinhaInicio.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("LINHA_MEIO")) {
                    docSaida.adicionarLinha(ComandoLinhaMeio.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("LINHA_ROPAPE")) {
                    docSaida.adicionarLinha(ComandoLinhaRodape.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("LINHA")) {
                    docSaida.adicionarLinha(ComandoLinha.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("CABECALHO")) {
                    docSaida.adicionarLinha(ComandoCabecalho.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());

                } else if (tipo.contentEquals("SECAO")) {
                    docSaida.adicionarLinha(ComandoSecao.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("SECAO_TITULO")) {
                    docSaida.adicionarLinha(ComandoSecaoTitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());

                } else {
                    doc.getErros().adicionar("Comando deconhecido interno : @" + tipo);
                }

                fmt.print("-------------------------------------------------");

            } else if (letra.contentEquals(")")) {
                fmt.print(">> Saindo de bloco");
                break;

            } else if (letra.contentEquals("!")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando @ : {} -------------------------------", tipo);

                if (tipo.contentEquals("CAPITULO")) {
                    docSaida.adicionarLinha(TrechoCapitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("APENDICE")) {
                    docSaida.adicionarLinha(TrechoApendice.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else if (tipo.contentEquals("ANEXO")) {
                    docSaida.adicionarLinha(TrechoAnexo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());
                } else {
                    doc.getErros().adicionar("Comando do tipo ! deconhecido :: " + tipo);
                }

            } else if (letra.contentEquals("#")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando # : {} -------------------------------", tipo);

                if (tipo.contentEquals("DOCUMENTO")) {

                    docSaida.adicionar(TrechoDocumento.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID).toDocumento());

                } else {

                    doc.getErros().adicionar("Falhou com : " + tipo);

                    UnBDocParser.espera_isso(documento, index, tamanho, "{");

                    index.somar(1);

                    String linha = "";

                    while (index.get() < tamanho.get()) {

                        String l = String.valueOf(documento.get().charAt(index.get()));

                        if (l.contentEquals("\n")) {
                            linha = linha.trim();

                            if (linha.contentEquals("}")) {
                                // index.somar(1);
                                break;
                            } else {
                                docSaida.adicionarLinha(linha);
                            }
                            fmt.print(">> {}", linha);
                            linha = "";
                        } else {
                            linha += l;
                        }


                        index.somar(1);
                    }

                    linha = linha.trim();

                    if (linha.contentEquals("}")) {
                        // index.somar(1);
                        // break;
                    } else {
                        docSaida.adicionarLinha(linha);
                    }

                    //    docSaida.adicionarLinha(linha);

                }


            } else if (letra.contentEquals("\"")) {

                docSaida.adicionar(ItemAspas.init(documento, index, tamanho, pai).toDocumento());

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


        return docSaida;
    }

    public TextoDocumento processarInternamente(UnBDoc doc, Entidade raiz, Entidade pai, String arquivoEntrada, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        TextoDocumento docSaida = new TextoDocumento();

        String sDocumento = Texto.arquivo_ler(arquivoEntrada);

        RefString documento = new RefString(sDocumento);
        RefInt index = new RefInt(0);
        RefInt tamanho = new RefInt(sDocumento.length());

        while (index.get() < tamanho.get()) {

            String letra = String.valueOf(documento.get().charAt(index.get()));

            if (letra.contentEquals("@")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando @ : {} -------------------------------", tipo);

                if (tipo.contentEquals("TEXTO")) {
                    docSaida.adicionarLinha(ComandoTexto.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DESTAQUE")) {
                    docSaida.adicionarLinha(ComandoDestaque.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("CITACAO")) {
                    docSaida.adicionarLinha(ComandoCitacao.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("REFERENCIA")) {
                    docSaida.adicionarLinha(ComandoReferencia.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TABELA")) {
                    docSaida.adicionarLinha(ComandoTabela.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("LEGENDA")) {
                    docSaida.adicionarLinha(ComandoLegenda.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TABELA_REFERENCIADA")) {
                    docSaida.adicionarLinha(ComandoTabelaReferenciada.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TEOREMA")) {
                    docSaida.adicionarLinha(ComandoTeorema.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TEOREMA_TITULO")) {
                    docSaida.adicionarLinha(ComandoTeoremaTitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("TEOREMA_AXIOMA")) {
                    docSaida.adicionarLinha(ComandoTeoremaAxioma.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("AXIOMA_TITULO")) {
                    docSaida.adicionarLinha(ComandoAxiomaTitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("AXIOMA_TERMINAR")) {
                    docSaida.adicionarLinha(ComandoAxiomaTerminar.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("PROF")) {
                    docSaida.adicionarLinha(ComandoProfessor.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("PROFA")) {
                    docSaida.adicionarLinha(ComandoProfessora.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DR")) {
                    docSaida.adicionarLinha(ComandoDoutor.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("DRA")) {
                    docSaida.adicionarLinha(ComandoDoutora.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());

                } else if (tipo.contentEquals("LINHA_INICIO")) {
                    docSaida.adicionarLinha(ComandoLinhaInicio.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());

                } else if (tipo.contentEquals("SECAO")) {
                    docSaida.adicionarLinha(ComandoSecao.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("SECAO_TITULO")) {
                    docSaida.adicionarLinha(ComandoSecaoTitulo.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());

                } else if (tipo.contentEquals("LINHA_ROPAPE")) {
                    docSaida.adicionarLinha(ComandoLinhaRodape.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else if (tipo.contentEquals("LINHA")) {
                    docSaida.adicionarLinha(ComandoLinha.init(doc, raiz, pai, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID).toDocumento());
                } else {
                    doc.getErros().adicionar("Comando deconhecido : @" + tipo);
                }

                fmt.print("-------------------------- -------------- -------------------------------", tipo);

            } else if (letra.contentEquals("#")) {

                index.somar(1);

                String tipo = UnBDocParser.parser_identificador(documento, index, tamanho).toUpperCase();

                fmt.print("-------------------------- Comando # : {} -------------------------------", tipo);

                // } else if (letra.contentEquals(")")) {

                doc.getErros().adicionar("Bloco com tipo não esperado : #" + tipo);


            } else {
                docSaida.adicionar(letra);
            }

            index.somar(1);
        }

        return docSaida;

    }


}
