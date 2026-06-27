package apps.app_unbdoc.trechos;

import apps.app_unbdoc.BlocoDeProcessamento;
import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;

public class TrechoAnexo {
    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        TextoDocumento docSaida = new TextoDocumento();

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "!Anexo");

        UnBDocParser.espera_isso(documento, index, tamanho, ":");
        index.somar(1);
        UnBDocParser.espera_isso(documento, index, tamanho, ":");
        index.somar(1);


        String nomeCapitulo = UnBDocParser.parser_entre_aspas(documento, index, tamanho);
        index.somar(1);

        UnBDocParser.espera_isso(documento, index, tamanho, "-");
        index.somar(1);
        UnBDocParser.espera_isso(documento, index, tamanho, ">");
        index.somar(1);

        UnBDocParser.espera_isso(documento, index, tamanho, "(");
        index.somar(1);

        fmt.print(">> Apendice :: {} ", nomeCapitulo);

        BlocoDeProcessamento bb = new BlocoDeProcessamento();

        TextoDocumento doc2 = bb.parserConteudo(doc, raiz, obj, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);

        FS.organizar_pasta(pastaSaida.getPasta("outros"));
        FS.organizar_pasta(pastaSaida.getPasta("outros/anexos"));

        String conteudo = doc2.toString();

        conteudo = conteudo.replace("\t", " ");
        //conteudo = conteudo.replace("  ", " ");

        while (conteudo.contains("  ")) {
            conteudo = conteudo.replace("  ", " ");
        }

        conteudo = conteudo.trim();

        conteudo = conteudo.replace("∣", " $\\lvert$");
        conteudo = conteudo.replace("Σ", "\\Sigma");
        conteudo = conteudo.replace("γ", "\\gamma");
        conteudo = conteudo.replace("⋅", "\\cdot");

        Texto.arquivo_escrever(pastaSaida.getArquivo("outros/anexos/anexo_" + capituloID.get() + ".tex"), Strings.ELIMINAR_LINHAS_VAZIAS(conteudo));

        //doc2.salvar();

        fmt.println(">> Salvando " + capituloID.get() + " com " + doc2.toDocumento().length());


        docSaida.adicionarLinha("\\anexo{outros/anexos/anexo_" + capituloID.get() + "}{" + nomeCapitulo + "}");
        capituloID.somar(1);

        return docSaida;

    }

}
