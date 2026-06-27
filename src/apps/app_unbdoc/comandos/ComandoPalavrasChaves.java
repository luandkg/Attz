package apps.app_unbdoc.comandos;

import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.RefInt;
import libs.luan.RefString;
import libs.luan.TextoDocumento;

public class ComandoPalavrasChaves {
    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {
        TextoDocumento docSaida = new TextoDocumento();

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@PALAVRAS_CHAVES");

        String conteudo = UnBDocParser.parser_entre_parenteses(documento, index, tamanho);
        index.somar(1);

        docSaida.adicionarLinha("\\palavraschave{" + conteudo + "}");

        return docSaida;
    }

}
