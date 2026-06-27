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

public class ComandoDocumento {
    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {
        TextoDocumento docSaida = new TextoDocumento();

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Documento");


        BlocoDeProcessamento bb = new BlocoDeProcessamento();

        boolean parenteses_aberto = UnBDocParser.espera_isso(documento, index, tamanho, "(");
        index.somar(1);

        if (!parenteses_aberto) {
            fmt.print("Erro : Era esperado abrir parenteses !");
        }

        fmt.print("ANTES :: {}", docSaida.toDocumento());
        TextoDocumento novo = bb.parserConteudo(doc, raiz, obj, documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID);

        docSaida.adicionar("\\begin{document}\n" + novo.toDocumento());
        docSaida.adicionarLinha("\n\\end{document}");

        return docSaida;
    }
}
