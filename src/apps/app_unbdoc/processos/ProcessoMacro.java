package apps.app_unbdoc.processos;

import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.RefInt;
import libs.luan.RefString;

public class ProcessoMacro {
    public static void init(UnBDoc doc, Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        UnBDocParser.espera_isso(documento, index, tamanho, ":");
        index.somar(1);

        UnBDocParser.espera_isso(documento, index, tamanho, ":");
        index.somar(1);


        String texto = UnBDocParser.parser_entre_aspas(documento, index, tamanho);
        index.somar(1);

        UnBDocParser.espera_isso(documento, index, tamanho, "-");
        index.somar(1);

        UnBDocParser.espera_isso(documento, index, tamanho, ">");
        index.somar(1);

        String conteudo = UnBDocParser.parser_entre_parenteses(documento, index, tamanho);

        Entidade obj = ENTT.CRIAR_EM(raiz.getEntidades(), "Nome", "!CRIAR_MACRO");
        obj.at("Conteudo", conteudo);

        Entidade objNome = ENTT.CRIAR_EM(obj.getEntidades(), "Nome", texto);
        obj.at("Conteudo", conteudo);


        //   mErros.adicionar("Macro : " + texto + " :: " + ss);

    }

}
