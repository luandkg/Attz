package apps.app_dajin;


import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.FS;
import libs.luan.Lista;
import libs.luan.Texto;
import libs.luan.fmt;

public class Dajin {


    public void compilar(String arquivo, String local_build) {


        FS.REMOVER_TUDO_INTERNAMENTE(local_build);

        String texto = Texto.arquivo_ler(arquivo);

        fmt.println("-------------------------------------------------");
        fmt.print("{}", texto);
        fmt.println("-------------------------------------------------");

        Lexer l = new Lexer();

        l.tokenize(texto);

        if (l.estaOK()) {
            ENTT.EXIBIR_TABELA(l.getTokensEntidade());


            Parser parser = new Parser();
            parser.parse(l.getTokens());

            fmt.println("-----------------------");
            parser.exibirAST();
            fmt.println("-----------------------");

            if (parser.estaOK()) {

                CodegenD cg = new CodegenD();
                cg.iniciar(parser.getAST(),local_build);

                if(!cg.temErros()){
                  //  cg.executar(local_build);
                }else{
                    cg.exibirErros();
                }

            }else{
                ENTT.EXIBIR_TABELA_COM_TITULO(parser.getErros(), "ERROS");
            }


        } else {
            ENTT.EXIBIR_TABELA_COM_TITULO(l.getErros(), "ERROS");
        }

    }


}
