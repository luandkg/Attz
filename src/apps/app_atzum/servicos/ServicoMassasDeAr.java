package apps.app_atzum.servicos;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_atzum.*;
import apps.app_atzum.utils.*;
import apps.app_campeonatum.VERIFICADOR;
import libs.arquivos.QTT;
import libs.arquivos.binario.Inteiro;
import libs.arquivos.video.Empilhador;
import libs.arquivos.video.VideoCodecador;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.azzal.utilitarios.HSV;
import libs.imagem.Efeitos;
import libs.imagem.Imagem;
import libs.luan.*;
import libs.meta_functional.Acao;

public class ServicoMassasDeAr {


    public static void INIT() {

        AtzumCriativoLog.iniciar("ServicoMassasDeAr.INIT");

        AtzumTerra planeta = new AtzumTerra();


        Lista<Par<String, String>> massas = new Lista<Par<String, String>>();

        massas.adicionar(new Par<String, String>("miz", "FRIO"));
        massas.adicionar(new Par<String, String>("mop", "FRIO"));
        massas.adicionar(new Par<String, String>("mut", "FRIO"));
        massas.adicionar(new Par<String, String>("mox", "FRIO"));

        massas.adicionar(new Par<String, String>("raf", "QUENTE"));
        massas.adicionar(new Par<String, String>("rez", "QUENTE"));
        massas.adicionar(new Par<String, String>("ruc", "QUENTE"));


        Cores mCores = new Cores();
        Atzum atzum = new Atzum();

        for (Par<String, String> massa_corrente : massas) {

            fmt.print("Massa : {}", massa_corrente.getChave());

            // ORGANIZAR PERCURSO
            Lista<Ponto> massa_percurso = GET_MASSA_PERCURSO(massa_corrente.getChave());

            fmt.print("\t + Percurso : {}", massa_percurso.getQuantidade());


            Renderizador render_percurso = Renderizador.CONSTRUIR(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());

            boolean ponto_anterior_existe = false;
            boolean ponto_inicial_existe = false;
            int ponto_indice = 0;
            Ponto pt_inicio = null;
            Ponto pt_anterior = null;

            for (Ponto pt : massa_percurso) {

                if (ponto_indice == 0) {
                    ponto_inicial_existe = true;
                    pt_inicio = pt;
                }

                if (ponto_anterior_existe) {
                    render_percurso.drawLinha(pt.getX(), pt.getY(), pt_anterior.getX(), pt_anterior.getY(), mCores.getVermelho());
                }

                ponto_anterior_existe = true;
                pt_anterior = pt;

                ponto_indice += 1;
                // render_percurso.drawCirculoCentralizado_Pintado(pt.getX(),pt.getY(),3,mCores.getAmarelo());

            }

            if (ponto_inicial_existe) {
                render_percurso.drawLinha(pt_inicio.getX(), pt_inicio.getY(), pt_anterior.getX(), pt_anterior.getY(), mCores.getVermelho());
            }

            FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar"));

            Imagem.exportar(render_percurso.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_percurso.png"));


            Lista<Ponto> percurso_completo = new Lista<Ponto>();


            for (int y = 0; y < planeta.getAltura(); y++) {
                for (int x = 0; x < planeta.getLargura(); x++) {

                    Cor cor = render_percurso.getPixel(x, y);

                    if (cor.igual(mCores.getVermelho())) {
                        percurso_completo.adicionar(new Ponto(x, y));
                    }
                }
            }

            fmt.print("\t + Percurso Completo : {}", percurso_completo.getQuantidade());

            VERIFICADOR.MINIMO(percurso_completo.getQuantidade(), 500, "Precisa no mínimo 500 pontos");

            double percurso_taxa = (double) percurso_completo.getQuantidade() / 500.0;
            fmt.print("\t + Percurso Taxa : {}", fmt.f2(percurso_taxa));


            Lista<Ponto> percurso_montado = new Lista<Ponto>();

            double percursando = 0.0;
            while (percurso_montado.getQuantidade() != 500) {
                int indice_percurso = (int) percursando;

                percurso_montado.adicionar(percurso_completo.get(indice_percurso));

                if (indice_percurso >= percurso_completo.getQuantidade()) {
                    indice_percurso = percurso_completo.getQuantidade() - 1;
                }
                percursando += percurso_taxa;
            }

            fmt.print("\t + Percurso Montado : {}", percurso_montado.getQuantidade());
            for (Ponto pt : percurso_montado) {
                render_percurso.setPixel(pt.getX(), pt.getY(), mCores.getAmarelo());
            }

            Imagem.exportar(render_percurso.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_percurso_v2.png"));
            AtzumPontos.SALVAR(percurso_montado, AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_percurso.dkg"));


            // ORGANIZAR MASSA
            Lista<Ponto> massa_dados = GET_MASSA_CONTORNO(massa_corrente.getChave());

            fmt.print("\t + Massa : {}", massa_dados.getQuantidade());

            MASSA_DE_AR_INICIAR(massa_dados);
            MASSA_DE_AR_CENTRALIZAR(massa_dados, planeta);

            Extremos<Integer> eixo_x = new Extremos<>(Inteiro.GET_ORDENAVEL());
            Extremos<Integer> eixo_y = new Extremos<>(Inteiro.GET_ORDENAVEL());

            for (Ponto pt : massa_dados) {

                eixo_x.set(pt.getX());
                eixo_y.set(pt.getY());

            }


            int centro_x = (eixo_x.getMaior() - eixo_x.getMenor()) / 2;
            int centro_y = (eixo_y.getMaior() - eixo_y.getMenor()) / 2;


            centro_x = eixo_x.getMenor() + centro_x;
            centro_y = eixo_y.getMenor() + centro_y;

            fmt.print("\t + Massa Centro : {}:{}", centro_x,centro_y);


            Renderizador render_preciptacao = AtzumCreator.GET_RENDER_FUNDO_PRETO();
            Renderizador render_preciptacao2 = AtzumCreator.GET_RENDER_FUNDO_PRETO();

            Cor massa_cor = mCores.getPreto();
            if (massa_corrente.getValor().contentEquals("FRIO")) {
                massa_cor = atzum.getMassaDeArFria();
            } else if (massa_corrente.getValor().contentEquals("QUENTE")) {
                massa_cor = atzum.getMassaDeArQuente();
            }

            boolean tem_anterior = false;
            Ponto massa_pt_anterior = null;
            for (Ponto pt : massa_dados) {

                if (tem_anterior) {
                    render_preciptacao.drawLinha(pt.getX(), pt.getY(), massa_pt_anterior.getX(), massa_pt_anterior.getY(), mCores.getAmarelo());
                }

                render_preciptacao.drawPixel(pt.getX(), pt.getY(), mCores.getAmarelo());
                tem_anterior = true;
                massa_pt_anterior = pt;
            }

            render_preciptacao.drawLinha(massa_dados.get(0).getX(), massa_dados.get(0).getY(), massa_pt_anterior.getX(), massa_pt_anterior.getY(), mCores.getAmarelo());


            //  render_preciptacao.drawCirculoCentralizado_Pintado(centro_x,centro_y,10,mCores.getVermelho());

            Imagem.exportar(render_preciptacao.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_contorno.png"));


            Rasterizador.RASTERIZAR_COM(render_preciptacao, centro_x, centro_y, mCores.getBranco(), mCores.getAmarelo(), Acao.ACAO_VAZIA(), Acao.ACAO_VAZIA());


            Imagem.exportar(render_preciptacao.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + ".png"));


            Lista<Ponto> massa_area = GET_MASSA_AREA(massa_corrente.getChave());


            // AREA EM ZONAS DE PROXIMIDADE

            Renderizador render_massa_limite = Renderizador.CONSTRUIR(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());


            for (Ponto ponto : massa_area) {
                render_massa_limite.setPixel(ponto.getX(), ponto.getY(), mCores.getAmarelo());
            }

            Imagem.exportar(render_massa_limite.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_limite.png"));


            Lista<Ponto> massa_limite = new Lista<Ponto>();
            for (Ponto ponto : massa_area) {
                if (Espaco2D.AO_REDOR_TEM(render_massa_limite, ponto.getX(), ponto.getY(), mCores.getPreto())) {
                    massa_limite.adicionar(ponto);
                }
            }

            Renderizador render_massa_solimite = Renderizador.CONSTRUIR(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());
            Renderizador render_massa_valor = Renderizador.CONSTRUIR(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());

            for (Ponto ponto : massa_limite) {
                render_massa_solimite.setPixel(ponto.getX(), ponto.getY(), mCores.getVermelho());
            }

            fmt.print("\t + Massa Area : {}", massa_area.getQuantidade());
            fmt.print("\t + Massa Limite : {}", massa_limite.getQuantidade());


            Imagem.exportar(render_massa_solimite.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_limite_v2.png"));

            Ponto massa_centro = new Ponto(centro_x,centro_y);

            for (Ponto ponto_area : massa_area) {

                Opcional<Ponto> limite_mais_proximo = Espaco2D.GET_MAIS_PROXIMO(ponto_area, massa_limite);
                if (limite_mais_proximo.isOK()) {

                    int distancia = Espaco2D.distancia_entre_pontos(ponto_area,limite_mais_proximo.get());
                    int distancia_centro = Espaco2D.distancia_entre_pontos(massa_centro,limite_mais_proximo.get());

                    double escala = (double)distancia/(double)distancia_centro;
                    //fmt.print("{} -> {}",ponto_area.toString(),fmt.f2(escala));

                    int i_escala = (int)(escala*100.0);

                    int e_escala = 0;

                    if(i_escala>=0 && i_escala<25) {
                        e_escala = 25;
                    }else if(i_escala>=25 && i_escala<50){
                        e_escala=50;
                    }else if(i_escala>=50 && i_escala<75){
                        e_escala=75;
                    }else{
                        e_escala=100;
                    }


                    HSV hsv = new HSV(230, HSV.MEDIANO, HSV.NORMAL(e_escala));
                    render_massa_valor.setPixel(ponto_area.getX(), ponto_area.getY(), hsv);

                }

            }

            Imagem.exportar(render_massa_valor.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_valor.png"));

            Lista<Par<Ponto, Integer>> massa_pontos_eixos = new Lista<Par<Ponto, Integer>>();

            for (int chave = 0; chave < 15; chave++) {
                massa_pontos_eixos.adicionar(new Par<Ponto, Integer>(Aleatorio.escolha_um(massa_area), Aleatorio.aleatorio(80)));
            }

            for (Par<Ponto, Integer> chave : massa_pontos_eixos) {
                render_preciptacao.drawCirculoCentralizado_Pintado(chave.getChave().getX(), chave.getChave().getY(), 10, mCores.getVermelho());
            }


            Imagem.exportar(render_preciptacao.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_v2.png"));


            for (Ponto ponto : massa_area) {

                int zona_valor = Espaco2D.GET_VALOR_DA_DISTANCIA_MAIS_PROXIMA(massa_pontos_eixos, ponto.getX(), ponto.getY());

                HSV hsv = new HSV(230, HSV.MEDIANO, HSV.NORMAL(zona_valor));
                render_preciptacao2.setPixel(ponto.getX(), ponto.getY(), hsv);

            }


            Imagem.exportar(render_preciptacao2.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_v3.png"));

            AtzumPontosInteiro.SALVAR(massa_pontos_eixos, AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + massa_corrente.getChave() + "_eixos.dkg"));


        }

        AtzumCriativoLog.terminar("ServicoMassasDeAr.INIT");

    }


    public static Lista<Ponto> GET_MASSA_AREA(String nome) {

        Cores mCores = new Cores();

        Lista<Ponto> massa = new Lista<Ponto>();

        Renderizador render_preciptacao = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/massas_de_ar/massa_" + nome + ".png"));

        AtzumTerra mapa_planeta = new AtzumTerra();


        for (int y = 0; y < mapa_planeta.getAltura(); y++) {
            for (int x = 0; x < mapa_planeta.getLargura(); x++) {
                if (render_preciptacao.getPixel(x, y).igual(mCores.getAmarelo())) {
                    massa.adicionar(new Ponto(x, y));
                }
            }
        }

        return massa;

    }

    public static Lista<Ponto> GET_MASSA_CONTORNO(String massa_nome) {
        return AtzumCidades.ABRIR_LOCAIS(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/massas_de_ar/" + massa_nome + "_massa.dkg"));
    }

    public static Lista<Ponto> GET_MASSA_PERCURSO(String massa_nome) {
        return AtzumCidades.ABRIR_LOCAIS(AtzumCreator.LOCAL_GET_ARQUIVO("parametros/massas_de_ar/" + massa_nome + "_percurso.dkg"));
    }

    public static Lista<Ponto> GET_MASSA_PERCURSO_CALCULADO(String massa_nome) {
        return AtzumPontos.ABRIR(AtzumCreator.LOCAL_GET_ARQUIVO("build/massas/massa_" + massa_nome + "_percurso.dkg"));
    }

    public static void PROCESSAR_TRONARKO() {

        AtzumCriativoLog.iniciar("ServicoMassasDeAr.PROCESSAR_TRONARKO");

        AtzumTerra mapa_planeta = new AtzumTerra();


        Lista<Par<String, String>> massas = new Lista<Par<String, String>>();

        massas.adicionar(new Par<String, String>("miz", "FRIO"));
        massas.adicionar(new Par<String, String>("mop", "FRIO"));
        massas.adicionar(new Par<String, String>("mut", "FRIO"));

        massas.adicionar(new Par<String, String>("raf", "QUENTE"));
        massas.adicionar(new Par<String, String>("rez", "QUENTE"));
        massas.adicionar(new Par<String, String>("ruc", "QUENTE"));


        Lista<Ponto> miz = GET_MASSA_CONTORNO("miz");
        Lista<Ponto> miz_percurso = GET_MASSA_PERCURSO("miz");

        Lista<Ponto> mop = GET_MASSA_CONTORNO("mop");
        Lista<Ponto> mop_percurso = GET_MASSA_PERCURSO("mop");

        Lista<Ponto> mut = GET_MASSA_CONTORNO("mut");
        Lista<Ponto> mut_percurso = GET_MASSA_PERCURSO("mut");

        Lista<Ponto> raf = GET_MASSA_CONTORNO("raf");
        Lista<Ponto> raf_percurso = GET_MASSA_PERCURSO("raf");

        Lista<Ponto> rez = GET_MASSA_CONTORNO("rez");
        Lista<Ponto> rez_percurso = GET_MASSA_PERCURSO("rez");

        Lista<Ponto> ruc = GET_MASSA_CONTORNO("ruc");
        Lista<Ponto> ruc_percurso = GET_MASSA_PERCURSO("ruc");

        miz = ServicoMassasDeAr.GET_MASSA_AREA("miz");
        mop = ServicoMassasDeAr.GET_MASSA_AREA("mop");
        mut = ServicoMassasDeAr.GET_MASSA_AREA("mut");
        raf = ServicoMassasDeAr.GET_MASSA_AREA("raf");
        rez = ServicoMassasDeAr.GET_MASSA_AREA("rez");
        ruc = ServicoMassasDeAr.GET_MASSA_AREA("ruc");

        //  fmt.print("AQM : {}",aqm.getQuantidade());


        Atzum atzum = new Atzum();
        Renderizador render_massa_de_ar_percurso = AtzumCreator.GET_RENDER_FUNDO_PRETO();

        for (Ponto percurso : miz_percurso) {
            render_massa_de_ar_percurso.drawCirculoCentralizado_Pintado(percurso.getX(), percurso.getY(), 5, atzum.getMassaDeArFria());
        }

        for (Ponto percurso : mop_percurso) {
            render_massa_de_ar_percurso.drawCirculoCentralizado_Pintado(percurso.getX(), percurso.getY(), 5, atzum.getMassaDeArFria());
        }
        for (Ponto percurso : mut_percurso) {
            render_massa_de_ar_percurso.drawCirculoCentralizado_Pintado(percurso.getX(), percurso.getY(), 5, atzum.getMassaDeArFria());
        }


        for (Ponto percurso : raf_percurso) {
            render_massa_de_ar_percurso.drawCirculoCentralizado_Pintado(percurso.getX(), percurso.getY(), 5, atzum.getMassaDeArQuente());
        }

        for (Ponto percurso : rez_percurso) {
            render_massa_de_ar_percurso.drawCirculoCentralizado_Pintado(percurso.getX(), percurso.getY(), 5, atzum.getMassaDeArQuente());
        }
        for (Ponto percurso : ruc_percurso) {
            render_massa_de_ar_percurso.drawCirculoCentralizado_Pintado(percurso.getX(), percurso.getY(), 5, atzum.getMassaDeArQuente());
        }


        Imagem.exportar(render_massa_de_ar_percurso.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/atzum_massa_de_ar_percurso.png"));


        Empilhador video_movimento = new VideoCodecador().criar(AtzumCreator.LOCAL_GET_ARQUIVO("videos/massas_de_ar.vi"), mapa_planeta.getLargura() / 2, mapa_planeta.getAltura() / 2);


        //BufferedImage img_prec = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/clima/atzum_umidade.png"));


        Lista<MassaDeAr> mMassasDeAr = new Lista<MassaDeAr>();



        mMassasDeAr.adicionar(new MassaDeAr("MIZ_A", "miz", "FRIO", 100, 1));
        mMassasDeAr.adicionar(new MassaDeAr("MOP_A", "mop", "FRIO", 100, 1));
        mMassasDeAr.adicionar(new MassaDeAr("MUT_A", "mut", "FRIO", 100, 1));

        mMassasDeAr.adicionar(new MassaDeAr("RAF_A", "raf", "QUENTE", 50, 1));
        mMassasDeAr.adicionar(new MassaDeAr("REZ_A", "rez", "QUENTE", 50, 1));
        mMassasDeAr.adicionar(new MassaDeAr("RUC_A", "ruc", "QUENTE", 50, 1));

        mMassasDeAr.adicionar(new MassaDeAr("REC_B", "rez", "QUENTE", 300, -1));
        mMassasDeAr.adicionar(new MassaDeAr("MUT_B", "mut", "FRIO", 300, -1));
        mMassasDeAr.adicionar(new MassaDeAr("RAF_B", "raf", "FRIO", 500, 1));


        mMassasDeAr.adicionar(new MassaDeAr("MUT_C", "mut", "FRIO", 100, -1));
        mMassasDeAr.adicionar(new MassaDeAr("REZ_C", "rez", "QUENTE", 400, -1));
        mMassasDeAr.adicionar(new MassaDeAr("RAF_C", "raf", "QUENTE", 400, -1));
        mMassasDeAr.adicionar(new MassaDeAr("RUC_C", "ruc", "QUENTE", 450, 1));


        for (MassaDeAr massa : mMassasDeAr) {
            ServicoMassasDeAr.MASSA_DE_AR_INICIAR(massa.getMassa());
        }


        //   DS.limpar(AtzumCreator.LOCAL_GET_ARQUIVO("build/clima/preciptacao.ds"));

        // MASSAS DE AR
        Cor AR_FRIO = atzum.getMassaDeArFria();
        Cor AR_QUENTE = atzum.getMassaDeArQuente();

        Cores mCores = new Cores();

        for (int s = 0; s < 500; s++) {

            fmt.print("Superarko ->> {}", s);

            Renderizador render_preciptacao = AtzumCreator.GET_RENDER_FUNDO_PRETO();
            //Renderizador render_massas_de_ar = AtzumCreator.GET_RENDER_FUNDO_PRETO();
            Renderizador render_massas_de_ar = AtzumCreator.GET_RENDER_FUNDO_PRETO_MARGEM_OCEANICA();


            // render_preciptacao.drawImagem(0, 0, img_prec);


            for (MassaDeAr massa : mMassasDeAr) {

                if (massa.isFrio()) {
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_FIXO(render_preciptacao, AR_FRIO, massa);
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_JUNTAS_FIXO(render_massas_de_ar, AR_FRIO, massa);

                } else if (massa.isQuente()) {
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_FIXO(render_preciptacao, AR_QUENTE, massa);
                    ServicoMassasDeAr.MASSA_DE_AR_MOVIMENTANDO_JUNTAS_FIXO(render_massas_de_ar, AR_QUENTE, massa);
                }


                massa.proximo();
            }

            // Imagem.exportar(render_massas_de_ar.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/clima/atzum_massas_de_ar.png"));

            // video_movimento.empurrarQuadro(Efeitos.reduzirMetade(render_preciptacao.toImagemSemAlfa()));
            video_movimento.empurrarQuadro(Efeitos.reduzirMetade(render_massas_de_ar.toImagemSemAlfa()));


            // DS.adicionar(AtzumCreator.LOCAL_GET_ARQUIVO("build/clima/preciptacao.ds"),String.valueOf(s), IM.salvar_to_bytes( Efeitos.reduzirMetade(render_preciptacao.toImagemSemAlfa()) ));

        }

        video_movimento.fechar();

        AtzumCriativoLog.terminar("ServicoMassasDeAr.PROCESSAR_TRONARKO");

    }



    public static void MASSA_DE_AR_INICIAR(Lista<Ponto> massa_de_ar) {

        Extremos<Integer> eixo_x = new Extremos<>(Inteiro.GET_ORDENAVEL());
        Extremos<Integer> eixo_y = new Extremos<>(Inteiro.GET_ORDENAVEL());

        for (Ponto pt : massa_de_ar) {

            eixo_x.set(pt.getX());
            eixo_y.set(pt.getY());

        }

      //  fmt.print("MASSA X ->> {}:{}", eixo_x.getMenor(), eixo_x.getMaior());
    //    fmt.print("MASSA Y ->> {}:{}", eixo_y.getMenor(), eixo_y.getMaior());

        int centro_x = (eixo_x.getMaior() - eixo_x.getMenor()) / 2;
        int centro_y = (eixo_y.getMaior() - eixo_y.getMenor()) / 2;

      //  fmt.print("MASSA :: {}:{}", maq1_x, maq1_y);


        for (Ponto pt : massa_de_ar) {
            pt.setX(pt.getX() - eixo_x.getMenor() - centro_x);
            pt.setY(pt.getY() - eixo_y.getMenor() - centro_y);
        }

        Extremos<Integer> eixo_x2 = new Extremos<>(Inteiro.GET_ORDENAVEL());
        Extremos<Integer> eixo_y2 = new Extremos<>(Inteiro.GET_ORDENAVEL());

        for (Ponto pt : massa_de_ar) {
            eixo_x2.set(pt.getX());
            eixo_y2.set(pt.getY());
        }

      //  fmt.print("MASSA X ->> {}:{}", eixo_x2.getMenor(), eixo_x2.getMaior());
      //  fmt.print("MASSA Y ->> {}:{}", eixo_y2.getMenor(), eixo_y2.getMaior());


    }


    public static void MASSA_DE_AR_MOVIMENTANDO(Renderizador render, Cor eCor, Lista<Ponto> massa_de_ar, Lista<Ponto> percurso, int processo) {


        double percurso_taxa = (double) percurso.getQuantidade() / 500.0;

        // fmt.print("Maq1 Quantidade :: {}",percurso.getQuantidade());
        //   fmt.print("Maq1 Taxa :: {}",percurso_taxa);


        Lista<Ponto> copia = new Lista<Ponto>();
        for (Ponto pt : massa_de_ar) {
            copia.adicionar(pt.getCopia());
        }


        for (Ponto pt : copia) {

            int indice = (int) (processo * percurso_taxa);
            if (indice >= percurso.getQuantidade()) {
                indice = percurso.getQuantidade() - 1;
            }

            pt.setX(pt.getX() + percurso.get(indice).getX());
            pt.setY(pt.getY() + percurso.get(indice).getY());
        }

        MARCAR_ZONA_DELIMITADA_SEM_LINHA(copia, eCor, render);


    }

    public static void MARCAR_ZONA_DELIMITADA_SEM_LINHA(Lista<Ponto> zona_delimitada, Cor zona_cor, Renderizador render) {


        boolean tem_anterior = false;
        Ponto pt_anterior = null;

        for (Ponto ponto : zona_delimitada) {

            int px = ponto.getX();
            int py = ponto.getY();

            if (px < 0) {
                px = px + render.getLargura();
            }
            if (px >= render.getLargura()) {
                px = px - render.getLargura();
            }


            //render.drawCirculoCentralizado_Pintado(px, py, 5, zona_cor);
            render.drawPixel(px, py, zona_cor);

            if (tem_anterior) {
                // render.drawLinha(ponto.getX(),ponto.getY(),pt_anterior.getX(),pt_anterior.getY(),zona_cor);
            }

            tem_anterior = true;
            pt_anterior = ponto;

        }

    }

    public static void MASSA_DE_AR_CENTRALIZAR(Lista<Ponto> massa_de_ar, AtzumTerra planeta) {

        for (Ponto pt : massa_de_ar) {
            pt.setX((planeta.getLargura() / 2) + pt.getX());
            pt.setY((planeta.getAltura() / 2) + pt.getY());
        }

    }

    public static void MASSA_DE_AR_MOVIMENTANDO_JUNTAS(Renderizador render, Cor zona_cor, Lista<Ponto> massa_de_ar, int valor, Lista<Ponto> percurso, int processo) {


        double percurso_taxa = (double) percurso.getQuantidade() / 500.0;

        Cores mCores = new Cores();
        Atzum atzum = new Atzum();

        for (Ponto ponto : massa_de_ar) {

            int px = ponto.getX();
            int py = ponto.getY();

            int indice = (int) (processo * percurso_taxa);
            if (indice >= percurso.getQuantidade()) {
                indice = percurso.getQuantidade() - 1;
            }

            px += percurso.get(indice).getX();
            py += percurso.get(indice).getY();


            if (px < 0) {
                px = px + render.getLargura();
            }
            if (px >= render.getLargura()) {
                px = px - render.getLargura();
            }

            if (render.getPixel(px, py).igual(mCores.getPreto())) {
                render.drawPixel(px, py, zona_cor);
            } else {

                if (render.getPixel(px, py).igual(atzum.getMassaDeArQuente()) && zona_cor.igual(atzum.getMassaDeArQuente())) {
                    render.drawPixel(px, py, atzum.getSuperMassaDeArQuente());
                } else if (render.getPixel(px, py).igual(atzum.getMassaDeArFria()) && zona_cor.igual(atzum.getMassaDeArFria())) {
                    render.drawPixel(px, py, atzum.getSuperMassaDeArFria());
                } else if (render.getPixel(px, py).igual(atzum.getMassaDeArFria()) && zona_cor.igual(atzum.getMassaDeArQuente())) {
                    render.drawPixel(px, py, atzum.getMassaDeArTempestade());
                } else if (render.getPixel(px, py).igual(atzum.getMassaDeArQuente()) && zona_cor.igual(atzum.getMassaDeArFria())) {
                    render.drawPixel(px, py, atzum.getMassaDeArTempestade());
                } else {
                    render.drawPixel(px, py, Cor.getHexCor("#F50057"));
                }

            }


        }

    }


    public static void ACUMULAR_MASSA_DE_AR(Renderizador render_entrada, Renderizador render_saida) {

        Cores mCores = new Cores();

        for (int y = 0; y < render_entrada.getAltura(); y++) {
            for (int x = 0; x < render_entrada.getLargura(); x++) {

                if (render_entrada.getPixel(x, y).isDiferente(mCores.getPreto())) {
                    render_saida.setPixel(x, y, render_entrada.getPixel(x, y));
                }

            }
        }


    }


    public static void MASSA_DE_AR_MOVIMENTANDO_FIXO(Renderizador render, Cor eCor, MassaDeAr eMassaDeAr) {


        // fmt.print("Maq1 Quantidade :: {}",percurso.getQuantidade());
        //   fmt.print("Maq1 Taxa :: {}",percurso_taxa);


        Lista<Ponto> copia = new Lista<Ponto>();
        for (Ponto pt : eMassaDeAr.getMassa()) {
            copia.adicionar(pt.getCopia());
        }


        for (Ponto pt : copia) {

            pt.setX(pt.getX() + eMassaDeAr.getCorrente().getX());
            pt.setY(pt.getY() + eMassaDeAr.getCorrente().getY());
        }

        MARCAR_ZONA_DELIMITADA_SEM_LINHA(copia, eCor, render);


    }

    public static void MASSA_DE_AR_MOVIMENTANDO_JUNTAS_FIXO(Renderizador render, Cor zona_cor, MassaDeAr massa) {


        Cores mCores = new Cores();
        Atzum atzum = new Atzum();

        for (Ponto ponto : massa.getMassa()) {

            int px = ponto.getX();
            int py = ponto.getY();


            px += massa.getCorrente().getX();
            py += massa.getCorrente().getY();


            if (px < 0) {
                px = px + render.getLargura();
            }
            if (px >= render.getLargura()) {
                px = px - render.getLargura();
            }

            if (render.getPixel(px, py).igual(mCores.getPreto())) {
                render.drawPixel(px, py, zona_cor);
            } else {

                if (render.getPixel(px, py).igual(atzum.getMassaDeArQuente()) && zona_cor.igual(atzum.getMassaDeArQuente())) {
                    render.drawPixel(px, py, atzum.getSuperMassaDeArQuente());
                } else if (render.getPixel(px, py).igual(atzum.getMassaDeArFria()) && zona_cor.igual(atzum.getMassaDeArFria())) {
                    render.drawPixel(px, py, atzum.getSuperMassaDeArFria());
                } else if (render.getPixel(px, py).igual(atzum.getMassaDeArFria()) && zona_cor.igual(atzum.getMassaDeArQuente())) {
                    render.drawPixel(px, py, atzum.getMassaDeArTempestade());
                } else if (render.getPixel(px, py).igual(atzum.getMassaDeArQuente()) && zona_cor.igual(atzum.getMassaDeArFria())) {
                    render.drawPixel(px, py, atzum.getMassaDeArTempestade());


                } else if (render.getPixel(px, py).igual(atzum.getSuperMassaDeArQuente()) && zona_cor.igual(atzum.getMassaDeArQuente())) {
                    render.drawPixel(px, py, atzum.getSuperMassaDeArQuente());
                } else if (render.getPixel(px, py).igual(atzum.getSuperMassaDeArFria()) && zona_cor.igual(atzum.getMassaDeArFria())) {
                    render.drawPixel(px, py, atzum.getSuperMassaDeArFria());


                } else {
                    render.drawPixel(px, py,atzum.getMassaDeArHiperTempestade());
                }

            }


        }

    }


    public static void TRANSPOR_MASSA_DE_AR(AtzumTerra planeta, Renderizador render_massas_de_ar, Renderizador movimentacao_temperatura_umidade, Renderizador render_preciptacao_valor, Renderizador render_chuva_tronarko) {

        Cores mCores = new Cores();


        for (int y = 0; y < render_massas_de_ar.getAltura(); y++) {
            for (int x = 0; x < render_massas_de_ar.getLargura(); x++) {

                if (render_massas_de_ar.getPixel(x, y).isDiferente(mCores.getPreto())) {
                    render_preciptacao_valor.setPixel(x, y, movimentacao_temperatura_umidade.getPixel(x, y));

                    if (planeta.isTerra(x, y)) {
                        render_chuva_tronarko.setPixel(x, y, mCores.getAzul());
                    }
                }

            }
        }


    }


    public static Renderizador PROCESSAR_FATORES_CLIMATICOS(AtzumTerra planeta, AreaDouble tronarko_temperatura, QTT umidade_dados, Renderizador render_massas_de_ar) {

        Cores mCores = new Cores();

        Atzum atzum = new Atzum();


        Renderizador render_chuva = Renderizador.construir(planeta.getLargura(), planeta.getAltura(), mCores.getPreto());

        int UMIDADE_MINIMA = 25;

        int TEMPERATURA_BAIXA = 5;
        int TEMPERATURA_ALTA = 36;

        int TEMPERATURA_ALTA_DE_VENTANIA = 35;

        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {

                if (planeta.isTerra(x, y)) {

                    int temperatura = (int) tronarko_temperatura.get(x, y);
                    int umidade = umidade_dados.getValor(x, y);


                    if (render_massas_de_ar.getPixel(x, y).isDiferente(mCores.getPreto())) {

                        String massa_de_ar = atzum.getMassaDeArTipo(render_massas_de_ar.getPixel(x, y));


                        if (umidade >= UMIDADE_MINIMA) {

                            if (massa_de_ar.contains("FRIO") && temperatura <= TEMPERATURA_BAIXA) {
                                render_chuva.setPixel(x, y, Atzum.COR_NEVE);
                            } else if (massa_de_ar.contains("FRIO") && temperatura > TEMPERATURA_BAIXA && temperatura < TEMPERATURA_ALTA) {
                                render_chuva.setPixel(x, y, Atzum.COR_CHUVA);
                            } else if (massa_de_ar.contains("FRIO") && temperatura >= TEMPERATURA_ALTA_DE_VENTANIA) {
                                render_chuva.setPixel(x, y, Atzum.COR_VENTANIA);
                            } else if (massa_de_ar.contains("QUENTE") && temperatura > TEMPERATURA_BAIXA && temperatura < TEMPERATURA_ALTA) {
                                render_chuva.setPixel(x, y, Atzum.COR_CHUVA);
                            } else if (massa_de_ar.contains("QUENTE") && temperatura >= TEMPERATURA_ALTA) {
                                render_chuva.setPixel(x, y, Atzum.COR_ONDA_DE_CALOR);
                            } else if (massa_de_ar.contains("TEMPESTADE") && temperatura > TEMPERATURA_BAIXA && temperatura < TEMPERATURA_ALTA) {
                                render_chuva.setPixel(x, y, Atzum.COR_TEMPESTADE_CHUVA);
                            } else if (massa_de_ar.contains("TEMPESTADE") && temperatura <= TEMPERATURA_BAIXA) {
                                render_chuva.setPixel(x, y, Atzum.COR_TEMPESTADE_NEVE);
                            }


                            if (massa_de_ar.contains("SUPERFRIO") && temperatura > TEMPERATURA_BAIXA && temperatura < TEMPERATURA_ALTA) {
                                render_chuva.setPixel(x, y, Atzum.COR_NEVE);
                            } else if (massa_de_ar.contains("SUPERQUENTE") && temperatura > TEMPERATURA_BAIXA && temperatura < TEMPERATURA_ALTA) {
                                render_chuva.setPixel(x, y, Atzum.COR_TEMPESTADE_CHUVA);
                            }

                        } else {

                            if (massa_de_ar.contains("FRIO") && temperatura <= TEMPERATURA_BAIXA) {
                                render_chuva.setPixel(x, y, Atzum.COR_SECA_EXTREMA);
                            } else if (massa_de_ar.contains("QUENTE") && temperatura >= TEMPERATURA_ALTA) {
                                render_chuva.setPixel(x, y, Atzum.COR_SECA_EXTREMA);
                            } else if ((massa_de_ar.contains("FRIO") || massa_de_ar.contains("QUENTE")) && (temperatura > TEMPERATURA_BAIXA && temperatura < TEMPERATURA_ALTA)) {
                                render_chuva.setPixel(x, y, Atzum.COR_SECA);
                            } else if (massa_de_ar.contains("TEMPESTADE")) {
                                render_chuva.setPixel(x, y, Atzum.COR_TEMPESTADE_VENTO);
                            }

                        }
                    }


                }


            }
        }

        return render_chuva;
    }

    public static void CALCULAR_UMIDADE(boolean ANALISAR_VARIACAO, AtzumTerra planeta, AreaDouble tronarko_umidade, Renderizador render_massas_de_ar, Opcional<QTT> dados_inferior, Opcional<QTT> dados_superior) {

        Atzum atzum = new Atzum();


        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {

                if (planeta.isTerra(x, y)) {

                    double umidade = tronarko_umidade.get(x, y);

                    if (ANALISAR_VARIACAO) {

                        String fator_climatico = atzum.getFatorClimatico(render_massas_de_ar.getPixel(x, y));


                        if (Strings.isIgual(fator_climatico, "CHUVA")) {
                            umidade += 1;
                        } else if (Strings.isIgual(fator_climatico, "NEVE")) {
                            umidade += 2;
                        } else if (Strings.isIgual(fator_climatico, "TEMPESTADE_CHUVA")) {
                            umidade += 3;
                        } else if (Strings.isIgual(fator_climatico, "TEMPESTADE")) {
                            umidade += 4;

                        } else if (Strings.isIgual(fator_climatico, "SECA")) {
                            umidade -= 1;
                        } else if (Strings.isIgual(fator_climatico, "SECA_EXTREMA")) {
                            umidade -= 2;
                        } else if (Strings.isIgual(fator_climatico, "ONDA_DE_CALOR")) {
                            umidade -= 3;

                        } else {

                            if (umidade > 0) {
                                umidade -= 0.5;
                            } else if (umidade < 0) {
                                umidade += 0.5;
                            }

                        }


                        int inferior = 0;
                        int superior = 0;

                        if (dados_inferior.isOK()) {
                            inferior = dados_inferior.get().getValor(x, y);
                        }

                        if (dados_superior.isOK()) {
                            superior = dados_superior.get().getValor(x, y);
                        }


                        int max_umidade = 10;
                        int min_umidade = 10;

                        if (superior >= 50) {
                            max_umidade = 40;
                        }

                        if (superior >= 80) {
                            max_umidade = 50;
                        }

                        if (inferior >= 50) {
                            min_umidade = 40;
                        }

                        if (inferior >= 80) {
                            min_umidade = 50;
                        }

                        min_umidade = min_umidade * (-1);


                        // APLICAR

                        if (umidade >= max_umidade) {
                            umidade = max_umidade;
                        }

                        if (umidade < min_umidade) {
                            umidade = min_umidade;
                        }

                    }


                    tronarko_umidade.set(x, y, umidade);

                }


            }
        }

    }

}
