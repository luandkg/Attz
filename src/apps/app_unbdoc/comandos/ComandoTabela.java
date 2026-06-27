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

public class ComandoTabela {

    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(),"Nome","@Tabela");

        BlocoDeProcessamento bb = new BlocoDeProcessamento();

        boolean parenteses_aberto = UnBDocParser.espera_isso(documento, index, tamanho, "(");
        index.somar(1);

        if (!parenteses_aberto) {
            fmt.print("Erro : Era esperado abrir parenteses !");
        }

        TextoDocumento docSaida = new TextoDocumento();

        TextoDocumento novo = bb.parserConteudo(doc,raiz,obj,documento, index, tamanho, pastaEntrada, pastaSaida, capituloID);
        novo.trim();


        docSaida.adicionar("\\begin{table}[h]");
        docSaida.adicionar("\\centering");
        docSaida.adicionar(novo.toDocumento());
        docSaida.adicionar("\\end{table}");

        fmt.print("DEPOIS :: {}", docSaida.toDocumento());


        return docSaida;
    }
}
