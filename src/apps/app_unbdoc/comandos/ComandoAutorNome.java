package apps.app_unbdoc.comandos;

import apps.app_unbdoc.BlocoDeProcessamento;
import apps.app_unbdoc.UnBDoc;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.RefInt;
import libs.luan.RefString;
import libs.luan.TextoDocumento;

public class ComandoAutorNome {
    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {
        TextoDocumento docSaida = new TextoDocumento();

        index.somar(1);

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@AUTOR_NOME");

        BlocoDeProcessamento bb = new BlocoDeProcessamento();

        TextoDocumento doc2 = bb.parserConteudo(doc,raiz, obj, documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);


        docSaida.adicionarLinha("\\autor{" + doc2.toDocumento().replace("(", "") + "}");

        return docSaida;

    }
}
