package apps.app_unbdoc.utils;

import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.RefInt;
import libs.luan.RefString;
import libs.luan.TextoDocumento;

public class ItemAspas {

    public static TextoDocumento init(RefString documento, RefInt index, RefInt tamanho,Entidade pai){
        TextoDocumento docSaida = new TextoDocumento();

        index.somar(1);

        String linha = "";

        while (index.get() < tamanho.get()) {

            String l = String.valueOf(documento.get().charAt(index.get()));

            if (l.contentEquals("\"")) {
                //  index.somar(1);
                break;
            } else {
                linha += l;
            }
            index.somar(1);

        }

        Entidade obj = ENTT.CRIAR_EM(pai.getEntidades(), "Nome", "@Aspas");

        docSaida.adicionar(linha);

        return docSaida;
    }
}
