package apps.app_unbdoc.comandos;

import apps.app_unbdoc.UnBDoc;
import apps.app_unbdoc.UnBDocParser;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.RefInt;
import libs.luan.RefString;
import libs.luan.TextoDocumento;
import libs.luan.fmt;

public class ComandoConst {

    public static TextoDocumento init(UnBDoc doc,Entidade raiz, Entidade pai, RefString documento, RefInt index, RefInt tamanho, PastaFS pastaEntrada, PastaFS pastaSaida, RefInt capituloID) {

        TextoDocumento sairCom = new TextoDocumento();

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@CONST_USAR");

        String macronome = UnBDocParser.parser_entre_parenteses(documento, index, tamanho);
        index.somar(1);

        Entidade objusoe = ENTT.CRIAR_EM(obj.getEntidades(), "Nome", "@USAR");
        Entidade objusoe2 = ENTT.CRIAR_EM(objusoe.getEntidades(), "Nome", macronome + raiz.getEntidades().getQuantidade());

        for (Entidade macro : raiz.getEntidades()) {


            if (macro.is("Nome", "!CRIAR_CONST")) {

                for (Entidade macroDentro : macro.getEntidades()) {

                    if (macroDentro.is("Nome", macronome)) {

                        Entidade aq = ENTT.CRIAR_EM(obj.getEntidades(), "Nome", macro.at("Nome"));

                        //Entidade aqx = ENTT.CRIAR_EM(aq.getEntidades(), "Nome", macroDentro.at("Nome"));

                        Entidade objuso = ENTT.CRIAR_EM(obj.getEntidades(), "Nome", "@USANDO");
                        Entidade objuso2 = ENTT.CRIAR_EM(objuso.getEntidades(), "Nome", "Linha = " + index.get());

                        sairCom.adicionar(macro.at("Conteudo"));
                        break;

                    }


                }


            }

        }

        fmt.print("DEPOIS :: {}",sairCom.toDocumento());

        return sairCom;
    }
}
