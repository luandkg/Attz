package apps.app_unbdoc.trechos;

import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.RefInt;
import libs.luan.RefString;
import libs.luan.TextoDocumento;
import libs.luan.fmt;

public class TrechoRaw {
    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        TextoDocumento docSaida = new TextoDocumento();


        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "#Raw");

        UnBDocParser.espera_isso(documento, index, tamanho, "{");
        index.somar(1);

        String bloco = UnBDocParser.parser_raw(documento, index, tamanho);

        //   docSaida.adicionar("Oieee :: " +index.get());
        docSaida.adicionarLinha(bloco);

        fmt.print("-------------------------- RAW -------------------------------");
        fmt.print("{}", bloco.length());
        fmt.print("-------------------------- --- -------------------------------");

        fmt.print("T-INDEX :: {}", index.get());

        return docSaida;
    }
}
