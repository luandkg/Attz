package apps.app_atzum.renderizadores;

import apps.app_atzum.Atzum;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumTerra;
import apps.app_atzum.utils.IntervaloDeValorColorido;
import libs.arquivos.ds.DS;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.imagem.Imagem;
import libs.luan.FS;
import libs.luan.Lista;
import libs.luan.fmt;

public class TronarkoRenderizadorSensores {

    public static final String PASTA_TRONARKO_VALORES = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_valores/");


    public static String GET_ARQUIVO(int modelo){
       return PASTA_TRONARKO_VALORES + modelo + ".png";
    }

    public static void TRONARKO_VER_SENSORES_TEMPERATURA() {

        fmt.print(">> Ver dados sensores !");

        Lista<IntervaloDeValorColorido> FAIXAS_DE_TEMPERATURA = Atzum.GET_TEMPERATURA_INTERVALOS_COLORIDOS();


        String arquivo_sensores_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");

        DS.DUMP_TABELA(arquivo_sensores_por_superarko);

        fmt.print("Superarkos : {}", DS.contar(arquivo_sensores_por_superarko));

        AtzumTerra planeta = new AtzumTerra();

        int sensor_tamanho = 30;
        int sensor_tamanho_metade = sensor_tamanho / 2;


        Lista<Entidade> sensores = Atzum.GET_SENSORES();

        int superarko = 0;

        for (int modelo = 1; modelo <= 10; modelo++) {

            superarko += 50;
            int superarko_utilizar = superarko - 25;

            fmt.print("Modelo : {} -->> Superarko {}", modelo, superarko_utilizar);


            Lista<Entidade> dados = ENTT.PARSER(DS.buscar_item(arquivo_sensores_por_superarko, superarko_utilizar + ".entts").get().getTexto());

            ENTT.EXIBIR_TABELA(ENTT.SLICE_PRIMEIROS(dados, 5));

            Renderizador render = AtzumCreator.GET_RENDER_FUNDO_PRETO();

            Cores mCores = new Cores();


            for (Entidade sensor : dados) {

                int temperatura = (int) sensor.atDouble("T" + superarko_utilizar);

                // Cor cor = new HSV(350, HSV.MAXIMO, HSV.INVERSO((int) (temperatura * temp_taxa))).toRGB();

                Ponto sensor_normalizado = Atzum.SENSOR_NORMALIZAR(sensores, sensor);

                //render.drawRect_Pintado(sensor_px, sensor_py, 5, 5, cor);

                Cor cor = IntervaloDeValorColorido.GET_COR(FAIXAS_DE_TEMPERATURA, temperatura, mCores.getBranco());


                AtzumCreator.PREENCHER_TERRA(planeta, render, sensor_normalizado.getX() - sensor_tamanho_metade, sensor_normalizado.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, cor);

            }

            FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_valores"));

            Imagem.exportar(render.toImagemSemAlfa(), PASTA_TRONARKO_VALORES + modelo + ".png");

        }

    }

    public static void TRONARKO_VER_SENSORES_UMIDADE() {

        fmt.print(">> Ver dados sensores !");


        Lista<IntervaloDeValorColorido> FAIXAS_DE_UMIDADE = Atzum.GET_UMIDADE_INTERVALOS_COLORIDOS();


        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko"));

        String arquivo_sensores_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");

        DS.DUMP_TABELA(arquivo_sensores_por_superarko);

        fmt.print("Superarkos : {}", DS.contar(arquivo_sensores_por_superarko));

        AtzumTerra planeta = new AtzumTerra();

        int sensor_tamanho = 30;
        int sensor_tamanho_metade = sensor_tamanho / 2;


        Lista<Entidade> sensores = Atzum.GET_SENSORES();

        int superarko = 0;

        for (int modelo = 1; modelo <= 10; modelo++) {

            superarko += 50;
            int superarko_utilizar = superarko - 25;

            fmt.print("Modelo : {} -->> Superarko {}", modelo, superarko_utilizar);


            Lista<Entidade> dados = ENTT.PARSER(DS.buscar_item(arquivo_sensores_por_superarko, superarko_utilizar + ".entts").get().getTexto());

            ENTT.EXIBIR_TABELA(ENTT.SLICE_PRIMEIROS(dados, 5));

            Renderizador render = AtzumCreator.GET_RENDER_FUNDO_PRETO();

            Cores mCores = new Cores();


            for (Entidade sensor : dados) {

                int umidade = (int) sensor.atDouble("U" + superarko_utilizar);

                // Cor cor = new HSV(350, HSV.MAXIMO, HSV.INVERSO((int) (temperatura * temp_taxa))).toRGB();

                Ponto sensor_normalizado = Atzum.SENSOR_NORMALIZAR(sensores, sensor);


                //render.drawRect_Pintado(sensor_px, sensor_py, 5, 5, cor);

                Cor cor = IntervaloDeValorColorido.GET_COR(FAIXAS_DE_UMIDADE, umidade, mCores.getBranco());


                AtzumCreator.PREENCHER_TERRA(planeta, render, sensor_normalizado.getX() - sensor_tamanho_metade, sensor_normalizado.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, cor);

            }

            FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_valores"));

            Imagem.exportar(render.toImagemSemAlfa(), PASTA_TRONARKO_VALORES + modelo + ".png");

        }

    }

    public static void TRONARKO_VER_SENSORES_MASSAS_DE_AR() {

        fmt.print(">> Ver dados sensores !");


        String arquivo_sensores_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");

        DS.DUMP_TABELA(arquivo_sensores_por_superarko);

        fmt.print("Superarkos : {}", DS.contar(arquivo_sensores_por_superarko));

        AtzumTerra planeta = new AtzumTerra();

        int sensor_tamanho = 30;
        int sensor_tamanho_metade = sensor_tamanho / 2;


        Lista<Entidade> sensores = Atzum.GET_SENSORES();

        int superarko = 0;

        for (int modelo = 1; modelo <= 10; modelo++) {

            superarko += 50;
            int superarko_utilizar = superarko - 25;

            fmt.print("Modelo : {} -->> Superarko {}", modelo, superarko_utilizar);


            Lista<Entidade> dados = ENTT.PARSER(DS.buscar_item(arquivo_sensores_por_superarko, superarko_utilizar + ".entts").get().getTexto());

            ENTT.EXIBIR_TABELA(ENTT.SLICE_PRIMEIROS(dados, 5));

            Renderizador render = AtzumCreator.GET_RENDER_FUNDO_PRETO();

            Cores mCores = new Cores();

            Atzum atzum = new Atzum();


            for (Entidade sensor : dados) {

                String massa_de_ar = sensor.at("M" + superarko_utilizar);

                // Cor cor = new HSV(350, HSV.MAXIMO, HSV.INVERSO((int) (temperatura * temp_taxa))).toRGB();

                Ponto sensor_normalizado = Atzum.SENSOR_NORMALIZAR(sensores, sensor);


                //render.drawRect_Pintado(sensor_px, sensor_py, 5, 5, cor);

                if (!massa_de_ar.isEmpty()) {

                    Cor cor = atzum.GET_MASSA_DE_AR_COR(massa_de_ar);

                    AtzumCreator.PREENCHER_TERRA(planeta, render, sensor_normalizado.getX() - sensor_tamanho_metade, sensor_normalizado.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, cor);

                }


            }

            FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_valores"));

            Imagem.exportar(render.toImagemSemAlfa(), PASTA_TRONARKO_VALORES + modelo + ".png");

        }

    }

    public static void TRONARKO_VER_SENSORES_FATORES_CLIMATICOS() {

        fmt.print(">> Ver dados sensores !");


        String arquivo_sensores_por_superarko = AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_sensores_por_superarko.ds");

        DS.DUMP_TABELA(arquivo_sensores_por_superarko);

        fmt.print("Superarkos : {}", DS.contar(arquivo_sensores_por_superarko));

        AtzumTerra planeta = new AtzumTerra();

        int sensor_tamanho = 30;
        int sensor_tamanho_metade = sensor_tamanho / 2;


        Lista<Entidade> sensores = Atzum.GET_SENSORES();

        int superarko = 0;

        for (int modelo = 1; modelo <= 10; modelo++) {

            superarko += 50;
            int superarko_utilizar = superarko - 25;

            fmt.print("Modelo : {} -->> Superarko {}", modelo, superarko_utilizar);


            Lista<Entidade> dados = ENTT.PARSER(DS.buscar_item(arquivo_sensores_por_superarko, superarko_utilizar + ".entts").get().getTexto());

            ENTT.EXIBIR_TABELA(ENTT.SLICE_PRIMEIROS(dados, 5));

            Renderizador render = AtzumCreator.GET_RENDER_FUNDO_PRETO();


            Atzum atzum = new Atzum();


            for (Entidade sensor : dados) {

                String fator_climatico = sensor.at("FC" + superarko_utilizar);

                // Cor cor = new HSV(350, HSV.MAXIMO, HSV.INVERSO((int) (temperatura * temp_taxa))).toRGB();

                Ponto sensor_normalizado = Atzum.SENSOR_NORMALIZAR(sensores, sensor);


                //render.drawRect_Pintado(sensor_px, sensor_py, 5, 5, cor);

                if (!fator_climatico.isEmpty()) {

                    Cor cor = atzum.GET_FATOR_CLIMATICO_COR(fator_climatico);

                    AtzumCreator.PREENCHER_TERRA(planeta, render, sensor_normalizado.getX() - sensor_tamanho_metade, sensor_normalizado.getY() - sensor_tamanho_metade, sensor_tamanho, sensor_tamanho, cor);

                }


            }

            FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/tronarko_valores"));

            Imagem.exportar(render.toImagemSemAlfa(), PASTA_TRONARKO_VALORES + modelo + ".png");

        }

    }
}
