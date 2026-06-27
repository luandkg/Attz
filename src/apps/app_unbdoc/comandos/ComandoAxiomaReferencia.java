package apps.app_unbdoc.comandos;

import apps.app_unbdoc.BlocoDeProcessamento;
import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;

public class ComandoAxiomaReferencia {

    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(),"Nome","@AxiomaReferencia");

        TextoDocumento docSaida = new TextoDocumento();

        BlocoDeProcessamento bb = new BlocoDeProcessamento();

        boolean parenteses_aberto = UnBDocParser.espera_isso(documento, index, tamanho, "(");
        index.somar(1);

        if (!parenteses_aberto) {
            fmt.print("Erro : Era esperado abrir parenteses !");
        }

        fmt.print("ANTES :: {}", docSaida.toDocumento());

        TextoDocumento novo = bb.parserConteudo(doc,raiz,obj,documento, index, tamanho, pastaEntrada, (pastaSaida), capituloID);

       // String sAntes = Strings.GET_ATE(novo.toDocumento(),":");
     //   String sDepois = Strings.GET_DEPOIS(novo.toDocumento(),":");


        docSaida.adicionar("\\label{"+doc.getTeoremaID() +":"+novo.toDocumento() +"}");

        fmt.print("DEPOIS :: {}", docSaida.toDocumento());


        return docSaida;
    }
}
