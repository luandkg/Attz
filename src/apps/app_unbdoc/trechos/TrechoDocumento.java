package apps.app_unbdoc.trechos;

import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.RefInt;
import libs.luan.RefString;
import libs.luan.TextoDocumento;

public class TrechoDocumento {

    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        TextoDocumento docSaida = new TextoDocumento();

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "#Documento");

        UnBDocParser.espera_isso(documento, index, tamanho, "{");
        index.somar(1);

        docSaida.adicionarLinha("\\begin{document}");

        doc.parserBloco(raiz, obj, documento, index, tamanho, pastaEntrada, pastaSaida, docSaida, capituloID);

        return docSaida;
    }
}
