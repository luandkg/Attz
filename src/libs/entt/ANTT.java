package libs.entt;

import libs.luan.Lista;

public class ANTT {

    public static void CONTE(Lista<Entidade> dados, String nome, String valor){

        Entidade e = ENTT.GET_SEMPRE(dados,nome,valor);

        if(!e.existe("Quantidade")){
            e.at("Quantidade",1);
        }else{
            e.at("Quantidade",e.atInt("Quantidade")+1);
        }

    }

    public static void CONTE_E_MARQUE(Lista<Entidade> dados, String nome, String valor,String marcador){

        Entidade e = ENTT.GET_SEMPRE(dados,nome,valor);

        if(!e.existe("Quantidade")){
            e.at("Quantidade",1);
            e.at("Primeiro",marcador);
        }else{
            e.at("Quantidade",e.atInt("Quantidade")+1);
            e.at("Ultimo",marcador);
        }

    }
}
