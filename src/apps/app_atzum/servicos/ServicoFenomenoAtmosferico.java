package apps.app_atzum.servicos;

import apps.app_attuz.Ferramentas.GPS;
import apps.app_atzum.Atzum;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumTerra;
import apps.app_campeonatum.VERIFICADOR;
import apps.app_letrum.Fonte;
import apps.app_letrum.Maker.FonteRunTime;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.imagem.Imagem;
import libs.luan.*;
import libs.tronarko.Hazde;
import libs.tronarko.Tron;
import libs.tronarko.utils.StringTronarko;

import java.awt.image.BufferedImage;


public class ServicoFenomenoAtmosferico {


    public static Renderizador MAPA_TEMPESTADE_INICIAR() {

        Cores mCores = new Cores();

        AtzumTerra mapa_planeta = new AtzumTerra();

        Renderizador render = Renderizador.construir(mapa_planeta.getLargura(), mapa_planeta.getAltura(), mCores.getPreto());

        return render;
    }

    public static void PROCESSAR_FURACAO(AtzumTerra planeta, Atzum atzum, Renderizador mapa, Renderizador render_massas_de_ar) {

        Cor COR_AZUL = Atzum.FENOMENO_COR_FURACAO;

        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {
                if (planeta.isOceano(x, y)) {

                    String massa_de_ar = atzum.getMassaDeArTipo(render_massas_de_ar.getPixel(x, y));

                    if (massa_de_ar.contains("TEMPESTADE")) {
                        mapa.drawPixel(x, y, COR_AZUL);
                    }

                }
            }
        }

    }

    public static void PROCESSAR_TORNADO(AtzumTerra planeta, Atzum atzum, Renderizador mapa, Renderizador render_massas_de_ar) {

        Cor COR_LARANJA = Atzum.FENOMENO_COR_TORNADO;

        for (int y = 0; y < planeta.getAltura(); y++) {
            for (int x = 0; x < planeta.getLargura(); x++) {
                if (planeta.isTerra(x, y)) {

                    String massa_de_ar = atzum.getMassaDeArTipo(render_massas_de_ar.getPixel(x, y));

                    if (massa_de_ar.contains("TEMPESTADE")) {
                        mapa.drawPixel(x, y, COR_LARANJA);
                    }

                }
            }
        }

    }


    public static String ARQUIVO_FENOMENOS_FURACOES() {
        return AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/fenomenos_furacoes.entts");
    }

    public static String ARQUIVO_FENOMENOS_TORNADOS() {
        return AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/fenomenos_tornados.entts");
    }

    public static String ARQUIVO_FENOMENOS_ATMOSFERICOS() {
        return AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/fenomenos_atmosfericos.entts");
    }


    public static void ZERAR() {

        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos"));

        Lista<Entidade> dados_furacoes = new Lista<Entidade>();


        for (int as = 0; as < 15; as++) {
            Entidade a = ENTT.CRIAR_EM_SEQUENCIALMENTE(dados_furacoes, "ID");
            a.at("Maximo", Aleatorio.aleatorio_entre(1000, 5000));
            a.at("Valor", Aleatorio.aleatorio(a.atInt("Maximo")));
        }

        ENTT.GUARDAR(dados_furacoes, ARQUIVO_FENOMENOS_FURACOES());

        Lista<Entidade> dados_tornados = new Lista<Entidade>();

        for (int as = 0; as < 15; as++) {
            Entidade a = ENTT.CRIAR_EM_SEQUENCIALMENTE(dados_tornados, "ID");
            a.at("Maximo", Aleatorio.aleatorio_entre(1000, 5000));
            a.at("Valor", Aleatorio.aleatorio(a.atInt("Maximo")));
        }

        ENTT.GUARDAR(dados_tornados, ARQUIVO_FENOMENOS_TORNADOS());

    }

    public static void PROCESSAR_TRONARKO(int tronarko) {

        BufferedImage zona_de_furacoes = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/tronarko_zona_de_furacoes.png"));
        BufferedImage zona_de_tornados = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/tronarko_zona_de_tornados.png"));


        Cor cor_furacao = Atzum.FENOMENO_COR_FURACAO;
        Cor cor_tornado = Atzum.FENOMENO_COR_TORNADO;


        Lista<Entidade> tronarko_atividade_atmosfericos = new Lista<Entidade>();

        Lista<Entidade> dados_furacoes = ENTT.ABRIR(ARQUIVO_FENOMENOS_FURACOES());
        Lista<Entidade> dados_tornados = ENTT.ABRIR(ARQUIVO_FENOMENOS_TORNADOS());

        ENTT.EXIBIR_TABELA_COM_TITULO(dados_furacoes, "@FURACOES");
        ENTT.EXIBIR_TABELA_COM_TITULO(dados_tornados, "@TORNADOS");


        for (int superarko = 1; superarko <= 500; superarko++) {

            tronarko_atividade_atmosfericos.adicionar_varios(processar_furacao(dados_furacoes, cor_furacao, zona_de_furacoes, tronarko, superarko));
            tronarko_atividade_atmosfericos.adicionar_varios(processar_tornado(dados_tornados, cor_tornado, zona_de_tornados, tronarko, superarko));

        }


        ENTT.GUARDAR(dados_furacoes, ARQUIVO_FENOMENOS_FURACOES());
        ENTT.GUARDAR(dados_tornados, ARQUIVO_FENOMENOS_TORNADOS());

        ENTT.GUARDAR(tronarko_atividade_atmosfericos, ARQUIVO_FENOMENOS_ATMOSFERICOS());

        RENDER(tronarko);

    }

    public static Lista<Entidade> processar_furacao(Lista<Entidade> dados_furacoes, Cor cor_furacao, BufferedImage mapa_zona_de_furacoes, int tronarko, int superarko) {

        Lista<Entidade> retornar_fenonemos = new Lista<Entidade>();

        for (Entidade a : dados_furacoes) {

            a.at("TemEvento", "NAO");

            a.at("Valor", a.atInt("Valor") + Aleatorio.aleatorio(5));


            if (a.atInt("Valor") >= a.atInt("Maximo")) {
                a.at("Valor", a.atInt("Valor") - a.atInt("Maximo"));
                a.at("Maximo", Aleatorio.aleatorio_entre(1000, 5000));

                if (Aleatorio.aleatorio(100) > 70) {
                    a.at("TemEvento", "SIM");
                }

            }

            if (a.is("TemEvento", "SIM")) {

                long tamanho_zona_de_furacoes = 0;
                for (int py = 0; py < mapa_zona_de_furacoes.getHeight(); py++) {
                    for (int px = 0; px < mapa_zona_de_furacoes.getWidth(); px++) {
                        Cor cor = Cor.getInt(mapa_zona_de_furacoes.getRGB(px, py));
                        if (cor.igual(cor_furacao)) {
                            tamanho_zona_de_furacoes += 1;
                        }
                    }
                }

                fmt.print("Total                    :: {}", (mapa_zona_de_furacoes.getWidth() * mapa_zona_de_furacoes.getHeight()));
                fmt.print("Tamanho Zona de Furacoes :: {}", tamanho_zona_de_furacoes);

                fmt.print("Fatores Atividade de Furacoes :: {}", dados_furacoes.getQuantidade());

                long furacoes_taxa = tamanho_zona_de_furacoes / dados_furacoes.getQuantidade();

                long contagem_furacoes_inicio = 0;
                long contagem_furacoes_final = furacoes_taxa;

                for (Entidade b : dados_furacoes) {

                    b.at("Inicio", contagem_furacoes_inicio);
                    b.at("Fim", contagem_furacoes_final);

                    contagem_furacoes_inicio += furacoes_taxa;
                    contagem_furacoes_final += furacoes_taxa;

                }

                boolean adicionar_evento = false;

                if (a.atInt("Fim") > a.atInt("Inicio")) {

                    int sorteado = Aleatorio.aleatorio_entre(a.atInt("Inicio"), a.atInt("Fim"));

                    long contagem = 0;
                    for (int py = 0; py < mapa_zona_de_furacoes.getHeight(); py++) {
                        for (int px = 0; px < mapa_zona_de_furacoes.getWidth(); px++) {
                            Cor cor = Cor.getInt(mapa_zona_de_furacoes.getRGB(px, py));
                            if (cor.igual(cor_furacao)) {
                                if (contagem == sorteado) {
                                    a.at("X", px);
                                    a.at("Y", py);

                                    //fmt.print("\t ++ Trocar {} para :: {} -- {} :: {}", a.at("ID"), area_sismica, px, py);
                                    adicionar_evento = true;
                                    break;
                                }
                                contagem += 1;
                            }
                        }
                        if (contagem == sorteado) {
                            break;
                        }
                    }

                }


                if (adicionar_evento) {
                    fmt.print("Superarko {} -- {} >> Furacao !!!", superarko, a.at("ID"));
                    ENTT.EXIBIR_TABELA_PREFIXO("\t", ENTT.CRIAR_LISTA_COM(a));

                    a.at("Superarko", superarko);
                    a.at("Tozte", StringTronarko.SUPERARKOS_DO_TRONARKO_PARA_TOZTE(superarko, tronarko));

                    a.at("Evento", "FURACAO");

                    Entidade e_furacao = a.getCopia();
                    e_furacao.at("Escala", Aleatorio.aleatorio_entre(1, 5));

                    ROTARIZADOR_DE_FENOMENO("Furacão", mapa_zona_de_furacoes, e_furacao);

                    retornar_fenonemos.adicionar(e_furacao);

                }


            }

        }

        return retornar_fenonemos;
    }

    public static Lista<Entidade> processar_tornado(Lista<Entidade> dados_tornados, Cor cor_tornado, BufferedImage mapa_zona_de_tornados, int tronarko, int superarko) {

        Lista<Entidade> retornar_fenonemos = new Lista<Entidade>();

        for (Entidade a : dados_tornados) {

            a.at("TemEvento", "NAO");

            a.at("Valor", a.atInt("Valor") + Aleatorio.aleatorio(5));


            if (a.atInt("Valor") >= a.atInt("Maximo")) {
                a.at("Valor", a.atInt("Valor") - a.atInt("Maximo"));
                a.at("Maximo", Aleatorio.aleatorio_entre(1000, 5000));

                if (Aleatorio.aleatorio(100) > 70) {
                    a.at("TemEvento", "SIM");
                    a.at("Escala", Aleatorio.aleatorio_entre(1, 10));
                }

            }

            if (a.is("TemEvento", "SIM")) {

                long tamanho_zona_de_tornados = 0;
                for (int py = 0; py < mapa_zona_de_tornados.getHeight(); py++) {
                    for (int px = 0; px < mapa_zona_de_tornados.getWidth(); px++) {
                        Cor cor = Cor.getInt(mapa_zona_de_tornados.getRGB(px, py));
                        if (cor.igual(cor_tornado)) {
                            tamanho_zona_de_tornados += 1;
                        }
                    }
                }

                fmt.print("Total                    :: {}", (mapa_zona_de_tornados.getWidth() * mapa_zona_de_tornados.getHeight()));
                fmt.print("Tamanho Zona de Tornados :: {}", tamanho_zona_de_tornados);

                fmt.print("Fatores Atividade de Tornados :: {}", dados_tornados.getQuantidade());

                long taxa_de_tornados = tamanho_zona_de_tornados / dados_tornados.getQuantidade();

                long contagem_tornados_inicio = 0;
                long contagem_tornados_final = taxa_de_tornados;

                for (Entidade b : dados_tornados) {

                    b.at("Inicio", contagem_tornados_inicio);
                    b.at("Fim", contagem_tornados_final);

                    contagem_tornados_inicio += taxa_de_tornados;
                    contagem_tornados_final += taxa_de_tornados;

                }

                boolean adicionar_evento = false;

                if (a.atInt("Fim") > a.atInt("Inicio")) {

                    int sorteado = Aleatorio.aleatorio_entre(a.atInt("Inicio"), a.atInt("Fim"));

                    long contagem = 0;
                    for (int py = 0; py < mapa_zona_de_tornados.getHeight(); py++) {
                        for (int px = 0; px < mapa_zona_de_tornados.getWidth(); px++) {
                            Cor cor = Cor.getInt(mapa_zona_de_tornados.getRGB(px, py));
                            if (cor.igual(cor_tornado)) {
                                if (contagem == sorteado) {
                                    a.at("X", px);
                                    a.at("Y", py);

                                    //fmt.print("\t ++ Trocar {} para :: {} -- {} :: {}", a.at("ID"), area_sismica, px, py);
                                    adicionar_evento = true;
                                    break;
                                }
                                contagem += 1;
                            }
                        }
                        if (contagem == sorteado) {
                            break;
                        }
                    }


                    fmt.print("Superarko {} -- {} >> Tornado !!!", superarko, a.at("ID"));
                    ENTT.EXIBIR_TABELA_PREFIXO("\t", ENTT.CRIAR_LISTA_COM(a));

                    a.at("Superarko", superarko);
                    a.at("Tozte", StringTronarko.SUPERARKOS_DO_TRONARKO_PARA_TOZTE(superarko, tronarko));


                }

                if (adicionar_evento) {
                    a.at("Evento", "TORNADO");

                    Entidade e_tornado = a.getCopia();
                    ROTARIZADOR_DE_FENOMENO("Tornado", mapa_zona_de_tornados, e_tornado);

                    retornar_fenonemos.adicionar(e_tornado);
                }

            }

        }

        return retornar_fenonemos;
    }


    public static void RENDER(int tronarko) {

        Lista<Entidade> tronarko_atividade_atmosfericos = ENTT.ABRIR(ARQUIVO_FENOMENOS_ATMOSFERICOS());

        Cores mCores = new Cores();

        ENTT.EXIBIR_TABELA_COM_TITULO(tronarko_atividade_atmosfericos, "FENOMENOS ATMOSFERICOS");

        BufferedImage mapa_atividade_atmosferico = Imagem.GET_IMAGEM_POR_PIXEL_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("atzum_regioes_contornos.png"));

        Renderizador render = new Renderizador(mapa_atividade_atmosferico);

        Fonte ESCRITOR_NORMAL_BRANCO = new FonteRunTime(mCores.getBranco(), 20);
        ESCRITOR_NORMAL_BRANCO.setRenderizador(render);

        for (Entidade fenomeno : tronarko_atividade_atmosfericos) {

            if (fenomeno.is("Evento", "FURACAO")) {
                Entidade e_furacao = fenomeno;
                render.drawCirculoCentralizado_Pintado(e_furacao.atInt("X"), e_furacao.atInt("Y"), 15, Cores.hexToCor("#B3E5FC"));
                render.drawCirculoCentralizado_Pintado(e_furacao.atInt("X"), e_furacao.atInt("Y"), 5, Cores.hexToCor("#43A047"));


                Entidade e_percurso_rota = ENTT.GET_SEMPRE(e_furacao.getEntidades(), "Nome", "Percurso");
                Entidade e_percurso_dias = ENTT.GET_SEMPRE(e_furacao.getEntidades(), "Nome", "Dias");

                for (Entidade rota : e_percurso_rota.getEntidades()) {
                    render.drawPixel(rota.atInt("X"), rota.atInt("Y"), Cores.hexToCor("#4FC3F7"));
                }

                for (Entidade rota : e_percurso_dias.getEntidades()) {
                    render.drawCirculoCentralizado_Pintado(rota.atInt("X1"), rota.atInt("Y1"), 5, Cores.hexToCor("#FDD835"));
                    ESCRITOR_NORMAL_BRANCO.escreva(rota.atInt("X1"), rota.atInt("Y1") + 20, rota.at("DiaID"));
                }

                ESCRITOR_NORMAL_BRANCO.escreva(e_furacao.atInt("X"), e_furacao.atInt("Y") + 20, "Furacão : " + e_furacao.at("Escala"));
                ESCRITOR_NORMAL_BRANCO.escreva(e_furacao.atInt("X"), e_furacao.atInt("Y") + 60, "Superarko :: " + StringTronarko.SUPERARKOS_DO_TRONARKO_PARA_TOZTE(e_furacao.atInt("Superarko"), tronarko));
            } else if (fenomeno.is("Evento", "TORNADO")) {
                Entidade tornado = fenomeno;
                render.drawCirculoCentralizado_Pintado(tornado.atInt("X"), tornado.atInt("Y"), 15, Cores.hexToCor("#FFEE58"));
                render.drawCirculoCentralizado_Pintado(tornado.atInt("X"), tornado.atInt("Y"), 5, Cores.hexToCor("#FFA000"));

                Entidade e_percurso_rota = ENTT.GET_SEMPRE(tornado.getEntidades(), "Nome", "Percurso");
                Entidade e_percurso_dias = ENTT.GET_SEMPRE(tornado.getEntidades(), "Nome", "Dias");

                for (Entidade rota : e_percurso_rota.getEntidades()) {
                    render.drawPixel(rota.atInt("X"), rota.atInt("Y"), Cores.hexToCor("#4FC3F7"));
                }

                for (Entidade rota : e_percurso_dias.getEntidades()) {
                    render.drawCirculoCentralizado_Pintado(rota.atInt("X1"), rota.atInt("Y1"), 5, Cores.hexToCor("#FDD835"));
                    ESCRITOR_NORMAL_BRANCO.escreva(rota.atInt("X1"), rota.atInt("Y1") + 20, rota.at("DiaID"));
                }

                ESCRITOR_NORMAL_BRANCO.escreva(tornado.atInt("X"), tornado.atInt("Y") + 20, "Tornado : " + tornado.at("Escala"));
                ESCRITOR_NORMAL_BRANCO.escreva(tornado.atInt("X"), tornado.atInt("Y") + 60, "Superarko :: " + StringTronarko.SUPERARKOS_DO_TRONARKO_PARA_TOZTE(tornado.atInt("Superarko"), tronarko));
            }

        }


        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/atzum_fenomenos_atmosfericos.png"));
        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tronarko/fenomenos/atzum_fenomenos_atmosfericos_tronarko_" + tronarko + ".png"));


    }


    public static void ROTARIZADOR_DE_FENOMENO(String s_fenomeno, BufferedImage mapa_fenomeno, Entidade e_fenomeno) {

        fmt.print("\t {} Percurso", s_fenomeno);

        int quantidade_de_eixos = Aleatorio.aleatorio_entre(3, 15);



        int x1 = e_fenomeno.atInt("X");
        int y1 = e_fenomeno.atInt("Y");

        Entidade e_percurso_eixos = ENTT.CRIAR_EM(e_fenomeno.getEntidades(), "Nome", "Eixos");
        Entidade e_percurso_segmento = ENTT.CRIAR_EM(e_fenomeno.getEntidades(), "Nome", "Segmentos");
        Entidade e_percurso_rota = ENTT.CRIAR_EM(e_fenomeno.getEntidades(), "Nome", "Percurso");


        for (int eixo = 1; eixo <= quantidade_de_eixos; eixo++) {
            Entidade e_percurso = ENTT.CRIAR_EM(e_percurso_eixos.getEntidades(), "EixoID", eixo);
            e_percurso.at("X1", x1);
            e_percurso.at("Y1", y1);

            int mais_x = Matematica.POSITIVO_OU_NEGATIVO(Aleatorio.aleatorio_entre(50, 100));
            int mais_y = Matematica.POSITIVO_OU_NEGATIVO(Aleatorio.aleatorio_entre(50, 100));

            int x2 = x1 + mais_x;
            int y2 = y1 + mais_y;

            int proximo_x2 = x2;

            if (x2 >= mapa_fenomeno.getWidth()) {
                x2 = mapa_fenomeno.getWidth();
                proximo_x2 = 0;
            }

            if (y2 >= mapa_fenomeno.getHeight()) {
                y2 -= Matematica.POSITIVO(mais_y);
            }

            if (x2 < 0) {
                x2 = 0;
                proximo_x2 = mapa_fenomeno.getWidth() - 1;
            }

            if (y2 < 0) {
                y2 += Matematica.POSITIVO(mais_y);
            }

            e_percurso.at("X2", x2);
            e_percurso.at("Y2", y2);

            int rota = 0;
            int tamanho_rota = 0;

            for (Ponto pt : GPS.criarRota(x1, y1, x2, y2)) {
                Entidade e_rota = ENTT.CRIAR_EM_SEQUENCIALMENTE(e_percurso_rota.getEntidades(), "RotaID", rota);
                e_rota.at("X", pt.getX());
                e_rota.at("Y", pt.getY());
                rota += 1;
                tamanho_rota += 1;
            }

            e_percurso.at("TamanhoRota", tamanho_rota);

            x1 = proximo_x2;
            y1 = y2;

        }


        ENTT.EXIBIR_TABELA_COM_TITULO(e_percurso_eixos.getEntidades(), "POR EIXO");

        int tamanho_rota_total = ENTT.ATRIBUTO_SOMAR(e_percurso_eixos.getEntidades(), "TamanhoRota");

        fmt.print("Tamanho Rota Total : {}", tamanho_rota_total);

        int tamanho_por_segmento = 450 + Aleatorio.aleatorio_entre(20, 30);

        int tamanho_calculando = 0;
        int tempo_para_reducao = 200;

        while (tamanho_calculando < (tamanho_rota_total - 1)) {

            Entidade e_Segmento = ENTT.CRIAR_EM_SEQUENCIALMENTE(e_percurso_segmento.getEntidades(), "SegmentoID", 0);
            e_Segmento.at("IniciarRotaID", tamanho_calculando);

            tamanho_calculando += tamanho_por_segmento;

            if (tamanho_calculando >= tamanho_rota_total) {
                tamanho_calculando = tamanho_rota_total - 1;
            }

            e_Segmento.at("TerminarRotaID", tamanho_calculando);
            e_Segmento.at("Tamanho", e_Segmento.atInt("TerminarRotaID") - e_Segmento.atInt("IniciarRotaID"));


            e_Segmento.at("Tempo", tempo_para_reducao);
            e_Segmento.at("Velocidade", fmt.f2((double) e_Segmento.atInt("Tamanho") / (double) e_Segmento.atInt("Tempo")));


            Lista<Entidade> rota_local = ENTT.SLICE(e_percurso_rota.getEntidades(), e_Segmento.atInt("IniciarRotaID"), e_Segmento.atInt("TerminarRotaID"));

            ENTT.ATRIBUTO_TODOS(rota_local, "SegmentoID", e_Segmento.at("SegmentoID"));

            tamanho_por_segmento -= Aleatorio.aleatorio_entre(20, 30);
            tempo_para_reducao += Aleatorio.aleatorio_entre(20, 30);


            VERIFICADOR.DEVE_SER_VERDADEIRO(tamanho_por_segmento > 10, "PROBLEMA COM A REDUÇÃO : " + tamanho_por_segmento);
        }


        ENTT.EXIBIR_TABELA_COM_TITULO(e_percurso_segmento.getEntidades(), "POR SEGMENTOS");


        int maior_segmento = ENTT.GET_INTEIRO_MAIOR(e_percurso_segmento.getEntidades(), "SegmentoID");

        fmt.print(">> Maior SegmentoID : {}", maior_segmento);

        ENTT.REMOVER_SE(e_percurso_segmento.getEntidades(), "SegmentoID", maior_segmento);
        ENTT.REMOVER_SE(e_percurso_rota.getEntidades(), "SegmentoID", maior_segmento);

        ENTT.EXIBIR_TABELA_COM_TITULO(e_percurso_segmento.getEntidades(), "POR SEGMENTOS");

        int indice = 0;

        int arko = Aleatorio.aleatorio_entre(0, 9);
        int ittas = Aleatorio.aleatorio_entre(0, 99);

        Tron tron = new Tron(new Hazde(arko,ittas,0),StringTronarko.PARSER_TOZTE(e_fenomeno.at("Tozte")));

        int arko_padrao = Aleatorio.aleatorio_entre(7,9);


        for (Entidade posicoes_do_dia : ENTT.AGRUPAR(e_percurso_rota.getEntidades(), "SegmentoID")) {

            fmt.print(">> SegmentoID {} com {} posicoes", posicoes_do_dia.at("SegmentoID"), ENTT.CONTAGEM(posicoes_do_dia.getEntidades()));

            int quantidade = ENTT.CONTAGEM(posicoes_do_dia.getEntidades());
            double taxa = 0;

            long total = ((long)arko_padrao * 100);
            taxa = (double) total / (double) quantidade;

            double iniciando = 0;

            fmt.print("\t Total : {} -->> {}", total, taxa);

            Tron tron_local = tron.getCopia();

            Tron tron_ultimo = tron.getCopia();

            for (Entidade passo : posicoes_do_dia.getEntidades()) {
                passo.at("Momento", (int) iniciando);

                Tron tron_copia = tron_local.getCopia();

                tron_copia=  tron_copia.modificar_Itta((int)iniciando);
                tron_ultimo=tron_copia;

                passo.at("Tozte", tron_copia.getTozte().getTextoZerado());
                passo.at("Hazde", tron_copia.getHazde().getTextoSemUzzonZerado());
                passo.at("Tron",tron_copia.getTextoSemUzzonZerado());
                iniciando += taxa;
            }

            tron=tron_ultimo.getCopia();

            //   ENTT.EXIBIR_TABELA_COM_TITULO(posicoes_do_dia.getEntidades(), "Momentos OUTROS");


            Entidade e_dia = ENTT.GET_SEMPRE(e_percurso_segmento.getEntidades(), "SegmentoID", indice);
            e_dia.at("Inicio", ENTT.GET_PRIMEIRO(posicoes_do_dia.getEntidades()).at("Tron"));
            e_dia.at("Fim", ENTT.GET_ULTIMO(posicoes_do_dia.getEntidades()).at("Tron"));

            e_dia.at("PercursoTamanho", quantidade);
            e_dia.at("Taxa", fmt.f4(taxa));

            indice += 1;
        }


        ENTT.EXIBIR_TABELA_COM_TITULO(e_percurso_segmento.getEntidades(), s_fenomeno.toUpperCase() + " PERCURSO");
         //  ENTT.EXIBIR_TABELA_COM_TITULO(e_percurso_rota.getEntidades(), s_fenomeno.toUpperCase() + " PERCURSO::ROTA");

        e_fenomeno.at("Inicio",   ENTT.GET_PRIMEIRO(e_percurso_rota.getEntidades()).at("Tron"));
        e_fenomeno.at("Fim",  ENTT.GET_ULTIMO(e_percurso_rota.getEntidades()).at("Tron"));
        e_fenomeno.at("Duracao", ENTT.FILTRAR_UNICOS(e_percurso_rota.getEntidades(),"Tozte").getQuantidade());

        e_fenomeno.at("PercursoTamanho", ENTT.CONTAGEM(e_percurso_rota.getEntidades()));

      //  e_fenomeno.at_remover("Evento");
        e_fenomeno.at_remover("Valor");
        e_fenomeno.at_remover("Maximo");

    }
}
