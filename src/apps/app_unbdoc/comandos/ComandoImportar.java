package apps.app_unbdoc.comandos;

import apps.app_unbdoc.BlocoDeProcessamento;
import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.RefInt;
import libs.luan.RefString;
import libs.luan.TextoDocumento;
import libs.luan.fmt;

public class ComandoImportar {

    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        TextoDocumento docSaida = new TextoDocumento();

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Importar");

        String txt = UnBDocParser.parser_entre_parenteses(documento, index, tamanho);

        if (txt.startsWith("\"") && txt.endsWith("\"")) {
            txt =txt.replace("\"","");
        }


        String nomeArquivo = pastaEntrada.getArquivo(txt);
        index.somar(1);

        fmt.print("PASTA ENTRADA :: {}", pastaEntrada.getLocal());
        fmt.print("ARQUIVO :: {}", nomeArquivo);

        // PastaFS pastaEntrada2 = new PastaFS(Strings.GET_REVERSO_DEPOIS_DE(nomeArquivo,"/"));

        BlocoDeProcessamento bb = new BlocoDeProcessamento();

        docSaida.adicionar(bb.processarInternamente(doc, raiz, obj, nomeArquivo, pastaEntrada, pastaSaida, capituloID).toDocumento());

        index.somar(-1);

        fmt.print("Estou em " + String.valueOf(documento.get().charAt(index.get())));

        return docSaida;
    }

}
