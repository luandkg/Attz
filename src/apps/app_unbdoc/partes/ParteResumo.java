package apps.app_unbdoc.partes;

import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;

public class ParteResumo {
    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {
        TextoDocumento docSaida = new TextoDocumento();


        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Resumo");

        String conteudo = UnBDocParser.parser_entre_parenteses(documento, index, tamanho);
        index.somar(1);

        String arquivoSaidaCriado = pastaSaida.getArquivo("ambiente/resumo.tex");

        String pastaPai = Strings.GET_REVERSO_DEPOIS_DE(arquivoSaidaCriado, "/");
        FS.organizar_pasta(pastaPai);


        Texto.arquivo_escrever(arquivoSaidaCriado, conteudo);

        fmt.print("PASTA ENTRADA :: {}", pastaEntrada.getLocal());
        fmt.print("ARQUIVO :: {}", arquivoSaidaCriado);

        return docSaida;
    }

}
