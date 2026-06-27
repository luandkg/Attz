package apps.app_dajin;

import libs.fs.PastaFS;
import libs.luan.fmt;
import servicos.ASSETS;

public class AppDajin {


    public static void INICIAR(){


        fmt.println("---------------------- DAJIN -------------------------");

        PastaFS pasta_dajin = new PastaFS(ASSETS.GET_PASTA("dajin").getLocal());
        PastaFS pasta_build = new PastaFS(ASSETS.GET_PASTA("dajin_build").getLocal());

        Dajin dajin = new Dajin();
        dajin.compilar(pasta_dajin.getArquivo("01_ola_mundo.dajin"),pasta_build.getLocal());
      //  dajin.compilar(pasta_dajin.getArquivo("99_completo.dajin"),pasta_build.getLocal());


    }
}
