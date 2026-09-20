package testes;

import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumTerra;
import libs.arquivos.QTT;
import libs.arquivos.QTTCacheVer;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.geometria.Retangulo;
import libs.azzal.utilitarios.Cor;
import libs.imagem.Imagem;
import libs.luan.Lista;
import libs.luan.Par;
import libs.luan.fmt;
import libs.tronarko.Tron;
import libs.tronarko.Tronarko;

public class TesteComQTTCacheVer {


    public static void init() {

        AtzumTerra planeta = new AtzumTerra();
        Cores mCores = new Cores();


        Tron t0_a = Tronarko.getTronAgora();
        QTTCacheVer dados_omega = new QTTCacheVer(AtzumCreator.DADOS_GET_ARQUIVO("regioes.qtt"), 500);
        Renderizador render_omega = Renderizador.construir(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());

        fmt.print("ZONAS = {}", dados_omega.getZonas().getQuantidade());

        for (Retangulo zona : dados_omega.getZonas()) {
            fmt.print("\t ++ ZONA :: {} - {} :: {} - {}", zona.getX(), zona.getY(), zona.getX2(), zona.getY2());


            Lista<Par<Ponto, Integer>> zona_dados = dados_omega.getZonaDados(zona);

            for (Par<Ponto, Integer> pt : zona_dados) {
                if (planeta.isTerra(pt.getChave().getX(), pt.getChave().getY())) {

                    int valor = pt.getValor();
                    if (valor > 0) {
                        valor = valor * 15;
                        render_omega.setPixel(pt.getChave().getX(), pt.getChave().getY(), new Cor(valor, valor, valor));
                    }


                }
            }

        }


        Imagem.exportar(render_omega.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("teste_omega.png"));
        Tron t0_b = Tronarko.getTronAgora();

        TEMPO("T0", t0_a, t0_b);


        Tron t1_a = Tronarko.getTronAgora();
        QTT dados_alfa = QTT.getTudo((AtzumCreator.DADOS_GET_ARQUIVO("regioes.qtt")));
        fmt.print("Dados : {} - {}", dados_alfa.getLargura(), dados_alfa.getAltura());


        Renderizador render_alfa = Renderizador.construir(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());

        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {
                if (planeta.isTerra(x, y)) {

                    int valor = dados_alfa.getValor(x, y);
                    if (valor > 0) {
                        valor = valor * 15;
                        render_alfa.setPixel(x, y, new Cor(valor, valor, valor));
                    }

                }
            }
        }


        Imagem.exportar(render_alfa.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("teste_alfa.png"));
        Tron t1_b = Tronarko.getTronAgora();

        TEMPO("T1", t1_a, t1_b);


        Tron t2_a = Tronarko.getTronAgora();

        QTTCacheVer dados_beta = new QTTCacheVer(AtzumCreator.DADOS_GET_ARQUIVO("regioes.qtt"), 500);
        Renderizador render_beta = Renderizador.construir(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());

        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {
                if (planeta.isTerra(x, y)) {

                    int valor = dados_beta.get(x, y);
                    if (valor > 0) {
                        valor = valor * 15;
                        render_beta.setPixel(x, y, new Cor(valor, valor, valor));
                    }


                }
            }
        }


        Imagem.exportar(render_beta.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("teste_beta.png"));
        Tron t2_b = Tronarko.getTronAgora();

        TEMPO("T2", t2_a, t2_b);

        Tron t3_a = Tronarko.getTronAgora();
        Renderizador render_gama = Renderizador.construir(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());
        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {
                if (planeta.isTerra(x, y)) {

                    int valor = QTT.pegar(AtzumCreator.DADOS_GET_ARQUIVO("regioes.qtt"), x, y);

                    if (valor > 0) {
                        valor = valor * 15;
                        render_gama.setPixel(x, y, new Cor(valor, valor, valor));
                    }


                }
            }
        }


        Imagem.exportar(render_gama.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("teste_gama.png"));
        Tron t3_b = Tronarko.getTronAgora();

        TEMPO("T3", t3_a, t3_b);
    }


    public static void TEMPO(String nome, Tron t1, Tron t2) {
        fmt.print("{} >>> + {}", nome, Tronarko.TRON_DIFERENCA(t1, t2));
    }

}
