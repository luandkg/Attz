package apps.app_biotzum;

import libs.arquivos.ds.DS;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.fs.PastaFS;
import libs.luan.*;
import libs.tronarko.Tronarko;

public class Loggum {

    public static final String LOCAL = "/home/luan/assets/orgs/";

    public static void INICIAR(Lista<Organismo> eOrganismos) {

        PastaFS local_logs = new PastaFS(LOCAL);

        for (Organismo org : eOrganismos) {

            String arquivo = local_logs.getArquivo("organismo_" + org.getID() + ".org");

            String agora = Tronarko.getTronAgora().getTextoZerado();

            Entidade dados = new Entidade();
            dados.at("Tron", agora);
            dados.at("X", org.getX());
            dados.at("Y", org.getY());
            dados.at("Energia", org.getEnergia());
            dados.at("Cansaco", org.getCansaco());
            dados.at("Passos", org.getPassos());
            dados.at("Batimentos", org.getBatimentos());

            DS.limpar(arquivo);
            DS.adicionar(arquivo, "Organismo::Nascimento", ENTT.TO_DOCUMENTO(dados));

        }
    }

    public static void ATUALIZAR(Lista<Organismo> eOrganismos, String eAgora) {

        PastaFS local_logs = new PastaFS(LOCAL);

        for (Organismo org : eOrganismos) {

            Entidade dados = new Entidade();
            dados.at("Tron", eAgora);
            dados.at("X", org.getX());
            dados.at("Y", org.getY());
            dados.at("Energia", org.getEnergia());
            dados.at("Cansaco", org.getCansaco());
            dados.at("Passos", org.getPassos());
            dados.at("Batimentos", org.getBatimentos());

            if (org.getEstagio() == Organismo.ESTAGIO_DESCANSANDO) {
                dados.at("Descansando", "SIM");
            }

            org.zerarPassos();

            DS.adicionar(local_logs.getArquivo("organismo_" + org.getID() + ".org"), "Organismo::Informativo", ENTT.TO_DOCUMENTO(dados));
        }

    }

    public static void ATUALIZAR_RESUMO_PASSOS(TabelaHash<Integer, Integer> mResumoPassos, String eAgora) {

        PastaFS local_logs = new PastaFS(LOCAL);

        fmt.print("------------ ACUMULADO -------------");

        for (Par<Integer, Integer> org : mResumoPassos.toPares()) {

            Entidade dados = new Entidade();
            dados.at("Tron", eAgora);
            dados.at("Resumo", "Passos");
            dados.at("Tipo", "Acumulado");
            dados.at("Passos", org.getValor());

            fmt.print("Acumulado Passos :: " + org.getValor());

            DS.adicionar(local_logs.getArquivo("organismo_" + org.getChave() + ".org"), "Organismo::Resumo(Passos)", ENTT.TO_DOCUMENTO(dados));
        }

    }

    public static void ATUALIZAR_RESUMO_BATIMENTOS(int batimentos, TabelaHash<Integer, Integer> mResumoBatimentosMedia, TabelaHash<Integer, Integer> mResumoBatimentosMinimo, TabelaHash<Integer, Integer> mResumoBatimentosMaximo, String eAgora) {

        PastaFS local_logs = new PastaFS(LOCAL);

        fmt.print("------------ ACUMULADO -------------");

        for (Par<Integer, Integer> org : mResumoBatimentosMedia.toPares()) {

            Entidade dados = new Entidade();
            dados.at("Tron", eAgora);
            dados.at("Resumo", "Batimentos");
            dados.at("Tipo", "Amostral");
            dados.at("Amostras", batimentos);
            dados.at("Media", org.getValor() / batimentos);
            dados.at("Minimo", mResumoBatimentosMinimo.get(org.getChave()));
            dados.at("Maximo", mResumoBatimentosMaximo.get(org.getChave()));

            fmt.print("Media Batimentos :: " + org.getValor()/ batimentos);

            DS.adicionar(local_logs.getArquivo("organismo_" + org.getChave() + ".org"), "Organismo::Resumo(Batimentos)", ENTT.TO_DOCUMENTO(dados));
        }

    }

    public static void ORGANISMO_DORME_INICIAR(Organismo org,String eAgora) {

        PastaFS local_logs = new PastaFS(LOCAL);


        Entidade dados = new Entidade();
        dados.at("Tron", eAgora);

        DS.adicionar(local_logs.getArquivo("organismo_" + org.getID() + ".org"), "Organismo::Dormindo(Iniciar)", ENTT.TO_DOCUMENTO(dados));


    }

    public static void ORGANISMO_DORME_TERMINAR(Organismo org,String eAgora) {

        PastaFS local_logs = new PastaFS(LOCAL);


        Entidade dados = new Entidade();
        dados.at("Tron", eAgora);

        DS.adicionar(local_logs.getArquivo("organismo_" + org.getID() + ".org"), "Organismo::Dormindo(Terminar)", ENTT.TO_DOCUMENTO(dados));


    }



    public static void ORGANISMO_TREINO_INICIAR(Organismo org,String eAgora,int treinoID) {

        PastaFS local_logs = new PastaFS(LOCAL);


        Entidade dados = new Entidade();
        dados.at("Tron", eAgora);
        dados.at("AtividadeID", treinoID);
        dados.at("Status", "INICIADA");

        DS.adicionar(local_logs.getArquivo("organismo_" + org.getID() + ".org"), "Organismo::AtividadeFisica(Iniciar)", ENTT.TO_DOCUMENTO(dados));


    }

    public static void ORGANISMO_TREINO_TERMINAR(Organismo org,String eAgora,int treinoID) {

        PastaFS local_logs = new PastaFS(LOCAL);


        Entidade dados = new Entidade();
        dados.at("Tron", eAgora);
        dados.at("AtividadeID", treinoID);
        dados.at("Status", "FINALIZADA");

        DS.adicionar(local_logs.getArquivo("organismo_" + org.getID() + ".org"), "Organismo::AtividadeFisica(Terminar)", ENTT.TO_DOCUMENTO(dados));


    }
}
