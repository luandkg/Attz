package apps.app_unbdoc.trechos;

import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;

public class TrechoRawSource {

    public static TextoDocumento init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {
        TextoDocumento docSaida = new TextoDocumento();

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "#Raw_Source");

        UnBDocParser.espera_isso(documento, index, tamanho, ":");
        index.somar(1);

        UnBDocParser.espera_isso(documento, index, tamanho, ":");
        index.somar(1);

        String nomeArquivo =  UnBDocParser.parser_entre_aspas(documento, index, tamanho);
        index.somar(1);

        fmt.print("ARQUIVO :: {}", nomeArquivo);

        UnBDocParser.espera_isso(documento, index, tamanho, "{");
        index.somar(1);

        UnBDocParser.  espera_isso(documento, index, tamanho, "{");
        index.somar(1);


        UnBDocParser.  espera_isso(documento, index, tamanho, "{");
        index.somar(1);


        UnBDocParser.  espera_isso(documento, index, tamanho, "@");
        index.somar(1);


        UnBDocParser.  espera_isso(documento, index, tamanho, "@");
        index.somar(1);


        String bloco =  UnBDocParser.parser_rawblock(documento, index, tamanho);

        //   docSaida.adicionarLinha(bloco);

        fmt.print("-------------------------- RAW -------------------------------");
        fmt.print("{}", bloco.length());
        fmt.print("-------------------------- --- -------------------------------");

        //  fmt.print("A-INDEX :: {}", index.get());

        String arquivoSaidaCriado = pastaSaida.getArquivo(nomeArquivo);

        String pastaPai = Strings.GET_REVERSO_DEPOIS_DE(arquivoSaidaCriado, "/");
        FS.organizar_pasta(pastaPai);


        Texto.arquivo_escrever(arquivoSaidaCriado, bloco);

        return docSaida;
    }

}
