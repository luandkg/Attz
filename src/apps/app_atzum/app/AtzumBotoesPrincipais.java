package apps.app_atzum.app;

import apps.app_atzum.Atzum;
import libs.arquivos.dsvideo.DSVideo;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.imagem.Efeitos;
import libs.luan.*;
import libs.mockui.Interface.Acao;

import java.awt.image.BufferedImage;

public class AtzumBotoesPrincipais {

    public static void criar_camadas(GrupoDeBotoesGrandes grupo, GrupoDeBotoesGrandes mSubCamadas, AppAtzum app) {

        Cores mCores = new Cores();


        grupo.criarCamada("Cidades", mCores.getLaranja()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumGeral.GET_MAPA_DE_CONTORNO()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("Cidades");
                mSubCamadas.zerar();

            }
        });

        grupo.criarCamada("Regiões", mCores.getLaranja()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumGeral.GET_MAPA_DE_REGIOES()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("Regiões");
                mSubCamadas.zerar();

            }
        });

        grupo.criarCamada("PlacasTectonicas", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumGeral.GET_MAPA_DE_PLACAS_TECTONICAS()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("PlacasTectonicas");
                mSubCamadas.zerar();

            }
        });

        grupo.criarCamada("PlacasLimites", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {


                BufferedImage placas_limites = app.mArquivoAtzumGeral.GET_MAPA_DE_PLACAS_TECTONICAS_LIMITES();
                BufferedImage placas_regioes = app.mArquivoAtzumGeral.GET_MAPA_DE_CONTORNO();

                Renderizador render = new Renderizador(placas_limites);
                Renderizador render_regioes = new Renderizador(placas_regioes);

                for(int y=0;y<render.getAltura();y++){
                    for(int x=0;x<render.getLargura();x++){
                        if(render.getPixel(x,y).igual(mCores.getVermelho())){
                            render.setPixel(x,y,mCores.getAmarelo());
                        }
                    }
                }

                for(int y=0;y<render_regioes.getAltura();y++){
                    for(int x=0;x<render_regioes.getLargura();x++){
                        if(render_regioes.getPixel(x,y).igual(mCores.getVermelho())){
                            render.setPixel(x,y,mCores.getVermelho());
                        }
                    }
                }


                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(render.toImagemSemAlfa()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("PlacasLimites");
                mSubCamadas.zerar();

            }
        });

        grupo.criarCamada("Relevo", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumGeral.GET_MAPA_DE_RELEVO()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("Relevo");
                mSubCamadas.zerar();

            }
        });


        grupo.criarCamada("Oceanos", mCores.getAzul()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumGeral.GET_MAPA_DE_OCEANOS()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("Oceanos");
                mSubCamadas.zerar();


            }
        });

        grupo.criarCamada("Modelo Climático", mCores.getLaranja()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumTronarko.GET_MODELO_CLIMATICO()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("Modelo Climático");
                mSubCamadas.zerar();


            }
        });

        grupo.criarCamada("Modelo Vegetação", mCores.getRosa()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumTronarko.GET_MODELO_VEGETACAO()));

                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("Modelo Vegetação");
                mSubCamadas.zerar();

            }
        });


        grupo.criarCamada("Umidade", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mVideoEmExecucao.parar();

                criar_subcamadas(mSubCamadas, app, "umidade");
                grupo.setSelecionado("Umidade");

                mSubCamadas.criarCamadaComNome("VU", "VU", mCores.getVermelho()).setAcao(new Acao() {
                    @Override
                    public void onClique() {

                        DSVideo video = app.mArquivoAtzumTronarko.GET_VIDEO("@animacao/umidade.vi");
                        app.mVideoEmExecucao.reproduzirVideo(video);
                        mSubCamadas.setSelecionado("VU");

                    }
                });

            }
        });

        grupo.criarCamada("Temperatura", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mVideoEmExecucao.parar();

                criar_subcamadas(mSubCamadas, app, "temperatura");
                grupo.setSelecionado("Temperatura");

                mSubCamadas.criarCamadaComNome("VT", "VT", mCores.getVermelho()).setAcao(new Acao() {
                    @Override
                    public void onClique() {

                        DSVideo video = app.mArquivoAtzumTronarko.GET_VIDEO("@animacao/temperatura.vi");
                        app.mVideoEmExecucao.reproduzirVideo(video);
                        mSubCamadas.setSelecionado("VT");

                    }
                });


            }
        });

        grupo.criarCamada("MassaDeAr", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mVideoEmExecucao.parar();

                criar_subcamadas(mSubCamadas, app, "massa_de_ar");
                grupo.setSelecionado("MassaDeAr");

                mSubCamadas.criarCamadaComNome("MA", "MA", mCores.getVermelho()).setAcao(new Acao() {
                    @Override
                    public void onClique() {

                        DSVideo video = app.mArquivoAtzumTronarko.GET_VIDEO("@animacao/massa_de_ar.vi");
                        app.mVideoEmExecucao.reproduzirVideo(video);
                        mSubCamadas.setSelecionado("MA");

                    }
                });

            }
        });

        grupo.criarCamada("FatorClimatico", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mVideoEmExecucao.parar();
                criar_subcamadas(mSubCamadas, app, "fator_climatico");

                mSubCamadas.criarCamadaComNome("FC", "FC", mCores.getVermelho()).setAcao(new Acao() {
                    @Override
                    public void onClique() {


                        DSVideo video = app.mArquivoAtzumTronarko.GET_VIDEO("@animacao/fatores_climaticos.vi");

                        app.mVideoEmExecucao.reproduzirVideo(video);

                        mSubCamadas.setSelecionado("FC");
                        // app.mVideoEmExecucao.parar();

                    }
                });

                grupo.setSelecionado("FatorClimatico");

            }
        });

        grupo.criarCamada("MapaTectonico", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mFenomenosTectonicos = new Lista<Entidade>();

                Renderizador render = new Renderizador(app.mArquivoAtzumGeral.GET_MAPA_DE_CONTORNO());

                for (Entidade atividade : app.mArquivoAtzumTronarko.getFenomenosTectonicos()) {
                    if (atividade.is("Evento", "TERREMOTO")) {
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 10, mCores.getLaranja());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 20, mCores.getLaranja());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 30, mCores.getLaranja());
                    }
                }

                for (Entidade atividade : app.mArquivoAtzumTronarko.getFenomenosTectonicos()) {
                    if (atividade.is("Evento", "VULCANISMO")) {
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 10, mCores.getVermelho());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 20, mCores.getVermelho());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 30, mCores.getVermelho());

                        app.mFenomenosTectonicos.adicionar(atividade);
                    }
                }


                for (Entidade atividade : app.mArquivoAtzumTronarko.getFenomenosTectonicos()) {
                    if (atividade.is("Evento", "TERREMOTO")) {

                        boolean existe = false;

                        for(Entidade vulcao : app.mFenomenosTectonicos){
                            if(vulcao.atInt("X") == atividade.atInt("X") && vulcao.atInt("Y") == atividade.atInt("Y")){
                                existe=true;
                                break;
                            }
                        }

                        if(!existe){
                            app.mFenomenosTectonicos.adicionar(atividade);
                        }

                    }
                }


                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(render.toImagemSemAlfa()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("MapaTectonico");
                mSubCamadas.zerar();


            }
        });


        grupo.criarCamada("MapaAtmosferico", mCores.getAzul()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mFenomenosAtmosfericos = app.mArquivoAtzumTronarko.getFenomenosAtmosfericos();

                Renderizador render = new Renderizador(app.mArquivoAtzumGeral.GET_MAPA_DE_CONTORNO());

                for (Entidade atividade : app.mArquivoAtzumTronarko.getFenomenosAtmosfericos()) {
                    if (atividade.is("Evento", "FURACAO")) {
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 10, mCores.getAzul());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 20, mCores.getAzul());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 30, mCores.getAzul());
                    }
                }

                for (Entidade atividade : app.mArquivoAtzumTronarko.getFenomenosAtmosfericos()) {
                    if (atividade.is("Evento", "TORNADO")) {
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 10, mCores.getLaranja());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 20, mCores.getLaranja());
                        render.drawCirculoCentralizado(atividade.atInt("X"), atividade.atInt("Y"), 30, mCores.getLaranja());
                    }
                }

                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(render.toImagemSemAlfa()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("MapaAtmosferico");
                mSubCamadas.zerar();


            }
        });

        grupo.criarCamada("Sociedades", mCores.getAzul()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mFenomenosAtmosfericos = app.mArquivoAtzumTronarko.getFenomenosAtmosfericos();

                Renderizador render = new Renderizador(app.mArquivoAtzumGeral.GET_MAPA_DE_CONTORNO());

                Lista<Entidade> sociedades = Atzum.GET_SOCIEDADES();

                for(Entidade so : sociedades){
                    Cor cor = Cor.getHexCor(so.at("Cor"));

                    Lista<Ponto> locais = new Lista<Ponto>();
                    Lista<Ponto> locais1 = new Lista<Ponto>();
                    Lista<Ponto> locais2 = new Lista<Ponto>();

                    for(String local : Strings.DIVIDIR_ESPACOS(so.at("Local"))){
                        int px = Integer.parseInt(Strings.GET_ATE(local,":"));
                        int py = Integer.parseInt(Strings.GET_DEPOIS(local,":"));

                        render.drawCirculoCentralizado_Pintado(px,py,20,cor);

                        fmt.print("{}--{}",px,py);
                        locais.adicionar(new Ponto(px,py));
                    }

                    for(String local : Strings.DIVIDIR_ESPACOS(so.at("Local1"))){
                        int px = Integer.parseInt(Strings.GET_ATE(local,":"));
                        int py = Integer.parseInt(Strings.GET_DEPOIS(local,":"));

                        render.drawCirculoCentralizado_Pintado(px,py,20,cor);

                        fmt.print("{}--{}",px,py);
                        locais1.adicionar(new Ponto(px,py));
                    }

                    for(String local : Strings.DIVIDIR_ESPACOS(so.at("Local2"))){
                        int px = Integer.parseInt(Strings.GET_ATE(local,":"));
                        int py = Integer.parseInt(Strings.GET_DEPOIS(local,":"));

                        render.drawCirculoCentralizado_Pintado(px,py,20,cor);

                        fmt.print("{}--{}",px,py);
                        locais2.adicionar(new Ponto(px,py));
                    }

                    for(Ponto a : locais){
                        for(Ponto b : locais){
                            render.drawLinha(a.getX(),a.getY(),b.getX(), b.getY(),cor);
                        }
                    }

                    for(Ponto a : locais1){
                        for(Ponto b : locais1){
                            render.drawLinha(a.getX(),a.getY(),b.getX(), b.getY(),cor);
                        }
                    }

                    for(Ponto a : locais2){
                        for(Ponto b : locais2){
                            render.drawLinha(a.getX(),a.getY(),b.getX(), b.getY(),cor);
                        }
                    }


                }


                app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(render.toImagemSemAlfa()));
                app.mVideoEmExecucao.parar();

                grupo.setSelecionado("Sociedades");
                mSubCamadas.zerar();


            }
        });
    }


    public static void criar_subcamadas(GrupoDeBotoesGrandes mSubCamadas, AppAtzum app, String tipo) {

        Cores mCores = new Cores();

        mSubCamadas.zerar();

        int o_superarko_valor = 25;

        for (int modelo = 1; modelo <= 10; modelo++) {

            RefInt ref_modelo = new RefInt(modelo);

            mSubCamadas.criarCamadaComNome(String.valueOf(ref_modelo.get()), String.valueOf(o_superarko_valor), mCores.getLaranja()).setAcao(new Acao() {
                @Override
                public void onClique() {

                    app.mWidgetMapaVisualizador.setMapaPequeno(Efeitos.reduzirMetade(app.mArquivoAtzumTronarko.GET_MAPA_MODELO(tipo, String.valueOf(ref_modelo.get()))));

                    mSubCamadas.setSelecionado(String.valueOf(ref_modelo.get()));
                    fmt.print("SubCamada :: {} !", tipo);
                    app.mVideoEmExecucao.parar();

                }
            });

            o_superarko_valor += 50;

        }

        if (mSubCamadas.getQuantidade() > 0) {
            mSubCamadas.aplicarCamada("1");
        }

    }

    public static void criar_camadas_zoom(GrupoDeBotoesGrandes mCamadasZoom, AppAtzum app) {

        mCamadasZoom.zerar();

        Cores mCores = new Cores();

        mCamadasZoom.criarCamada("Cidades", mCores.getLaranja()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaGrande(app.mArquivoAtzumGeral.GET_MAPA_DE_CONTORNO());

                app.mVideoEmExecucao.parar();

                mCamadasZoom.setSelecionado("Cidades");
                app.mMapaZoom.update(true);


            }
        });

        mCamadasZoom.criarCamada("Regiões", mCores.getLaranja()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaGrande(app.mArquivoAtzumGeral.GET_MAPA_DE_REGIOES());

                mCamadasZoom.setSelecionado("Regiões");
                app.mMapaZoom.update(true);


            }
        });

        mCamadasZoom.criarCamada("PlacasTectonicas", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaGrande(app.mArquivoAtzumGeral.GET_MAPA_DE_PLACAS_TECTONICAS());
                app.mVideoEmExecucao.parar();

                mCamadasZoom.setSelecionado("PlacasTectonicas");
                app.mMapaZoom.update(true);

            }
        });

        mCamadasZoom.criarCamada("PlacasLimites", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {


                BufferedImage placas_limites = app.mArquivoAtzumGeral.GET_MAPA_DE_PLACAS_TECTONICAS_LIMITES();
                BufferedImage placas_regioes = app.mArquivoAtzumGeral.GET_MAPA_DE_CONTORNO();

                Renderizador render = new Renderizador(placas_limites);
                Renderizador render_regioes = new Renderizador(placas_regioes);

                for(int y=0;y<render.getAltura();y++){
                    for(int x=0;x<render.getLargura();x++){
                        if(render.getPixel(x,y).igual(mCores.getVermelho())){
                            render.setPixel(x,y,mCores.getAmarelo());
                        }
                    }
                }

                for(int y=0;y<render_regioes.getAltura();y++){
                    for(int x=0;x<render_regioes.getLargura();x++){
                        if(render_regioes.getPixel(x,y).igual(mCores.getVermelho())){
                            render.setPixel(x,y,mCores.getVermelho());
                        }
                    }
                }


                app.mWidgetMapaVisualizador.setMapaGrande(render.toImagemSemAlfa());
                app.mVideoEmExecucao.parar();

                mCamadasZoom.setSelecionado("PlacasLimites");
                app.mMapaZoom.update(true);

            }
        });

        mCamadasZoom.criarCamada("Relevo", mCores.getVermelho()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaGrande(app.mArquivoAtzumGeral.GET_MAPA_DE_RELEVO());

                mCamadasZoom.setSelecionado("Relevo");
                app.mMapaZoom.update(true);

            }
        });


        mCamadasZoom.criarCamada("Oceanos", mCores.getAzul()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaGrande(app.mArquivoAtzumGeral.GET_MAPA_DE_OCEANOS());

                mCamadasZoom.setSelecionado("Oceanos");
                app.mMapaZoom.update(true);

            }
        });

        mCamadasZoom.criarCamada("Modelo Climático", mCores.getLaranja()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaGrande(app.mArquivoAtzumTronarko.GET_MODELO_CLIMATICO());

                mCamadasZoom.setSelecionado("Modelo Climático");
                app.mMapaZoom.update(true);

            }
        });

        mCamadasZoom.criarCamada("Modelo Vegetação", mCores.getRosa()).setAcao(new Acao() {
            @Override
            public void onClique() {

                app.mWidgetMapaVisualizador.setMapaGrande(app.mArquivoAtzumTronarko.GET_MODELO_VEGETACAO());

                mCamadasZoom.setSelecionado("Modelo Vegetação");
                app.mMapaZoom.update(true);

            }
        });



    }

}
