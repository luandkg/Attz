package apps.app_atzum.servicos;

import apps.app_attuz.Ferramentas.Espaco2D;
import apps.app_attuz.Ferramentas.GPS;
import apps.app_attuz.Sociedade.NomeadorBurgo;
import apps.app_atzum.Atzum;
import apps.app_atzum.AtzumCreator;
import apps.app_atzum.AtzumTerra;
import apps.app_atzum.utils.AtzumCriativoLog;
import apps.app_atzum.utils.AtzumPlacasTectonicas;
import apps.app_atzum.utils.AtzumPontosInteiro;
import apps.app_atzum.utils.Rasterizador;
import libs.arquivos.QTT;
import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.azzal.utilitarios.Cor;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.imagem.Imagem;
import libs.luan.*;
import libs.meta_functional.Acao;
import libs.meta_functional.FuncaoGama;

public class ServicoTectonico {

    public static void INIT() {
        AtzumCriativoLog.iniciar("ServicoTectonico.INIT");

        INICIAR_PLACAS();
        EXTRAIR_PLACAS_TECTONICAS_CONTORNOS();
        CRIAR_PLACAS_COM_LIMITES();

        GUARDAR_DADOS_PLACAS_TECTONICAS();

        DEFINIR_AREAS_DE_ATIVIDADE_SISMICA();
        VULCANIZAR();

        AtzumCriativoLog.terminar("ServicoTectonico.INIT");
        AtzumCriativoLog.exibir_item("ServicoTectonico.INIT");
    }


    public static void INICIAR_PLACAS() {
        AtzumCriativoLog.iniciar("ServicoTectonico.INICIAR_PLACAS");

        fmt.print("Tectonismo");

        Cores mCores = new Cores();

        Renderizador render_tronarko = new Renderizador(AtzumCreator.GET_MAPA_PRETO_E_BRANCO());
        Renderizador render_tronarko_placas_tectonicas_limites = Renderizador.CONSTRUIR(render_tronarko.getLargura(), render_tronarko.getAltura(), mCores.getPreto());


        AtzumTerra planeta = new AtzumTerra();

        String ARQUIVO_NOME = AtzumCreator.LOCAL_GET_ARQUIVO("parametros/PLACAS_TECTONICAS.dkg") ;

        Unico<Par<Ponto, Integer>> eixos = new Unico<Par<Ponto, Integer>>(AtzumPontosInteiro.PAR_PONTO_INTEGER_IGUALAVEL());
        for (Par<Ponto, Integer> p : AtzumPontosInteiro.ABRIR(ARQUIVO_NOME)) {
            eixos.item(p);
        }

        Lista<Ponto> pontos = new Lista<Ponto>();

        for (Par<Ponto, Integer> eixo : eixos) {
            pontos.adicionar(eixo.getChave());
        }

        fmt.print("Quantidade : {}", pontos.getQuantidade());

        fmt.print(">> Criar placas tectônicas !");

        for (Ponto pt_eixo : pontos) {

            render_tronarko.drawCirculoCentralizado_Pintado(pt_eixo, 10, mCores.getAmarelo());


            Opcional<Ponto> proximo = Espaco2D.GET_MAIS_PROXIMO(pt_eixo, pontos);

            if (proximo.isOK()) {
                double dist = Espaco2D.distancia_entre_pontos(pt_eixo, proximo.get());
                if (dist < 200) {
                    render_tronarko.drawLinha(pt_eixo, proximo.get(), mCores.getVerde());

                    for (Ponto o : GPS.criarRota(pt_eixo, proximo.get())) {
                        render_tronarko_placas_tectonicas_limites.drawRect_Pintado(o.getX(), o.getY(), 20, 20, mCores.getVermelho());
                    }
                }
            }

            Opcional<Ponto> proximo2 = Espaco2D.GET_SEGUNDO_PROXIMO(pt_eixo, pontos);

            if (proximo2.isOK()) {
                double dist = Espaco2D.distancia_entre_pontos(pt_eixo, proximo2.get());
                if (dist < 200) {
                    render_tronarko.drawLinha(pt_eixo, proximo2.get(), mCores.getVermelho());

                    for (Ponto o : GPS.criarRota(pt_eixo, proximo2.get())) {
                        render_tronarko_placas_tectonicas_limites.drawRect_Pintado(o.getX(), o.getY(), 20, 20, mCores.getVermelho());
                    }
                }
            }


        }

        FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico"));

        Imagem.exportar(render_tronarko.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_eixos.png"));


        render_tronarko_placas_tectonicas_limites.drawRect_Pintado(0, 420, 50, 50, mCores.getVermelho());
        render_tronarko_placas_tectonicas_limites.drawRect_Pintado(0, 1050, 50, 50, mCores.getVermelho());

        fmt.print(">> Renderizar placas tectônicas !");

        Lista<Par<Ponto, Cor>> placas_tectonicas = AtzumPlacasTectonicas.GET_PLACAS_TECTONICAS();

        for (Par<Ponto, Cor> placa_tectonica : placas_tectonicas) {
            //  render_tronarko_placas_tectonicas_limites.drawRect_Pintado(placa_tectonica.getChave().getX(),placa_tectonica.getChave().getY(),50,50,placa_tectonica.getValor());

            fmt.print("\t ++ {}",placa_tectonica.getValor().toString());

            RefInt processante = new RefInt(0);

            Acao durante_mudanca = new Acao() {
                @Override
                public void fazer() {
                }
            };

            Acao a_cada_100 = new Acao() {
                @Override
                public void fazer() {

                    // Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.PROCESSANDO_GET_ARQUIVO("processando_" + fmt.zerado(processante.get(), 4) + ".png"));
                    processante.set(processante.get() + 1);
                    Imagem.exportar(render_tronarko_placas_tectonicas_limites.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/processando.png"));

                }
            };


            Rasterizador.RASTERIZAR_COM(render_tronarko_placas_tectonicas_limites, placa_tectonica.getChave().getX(), placa_tectonica.getChave().getY(), mCores.getPreto(), placa_tectonica.getValor(), durante_mudanca, a_cada_100);

            FS.organizar_pasta(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico"));

            Imagem.exportar(render_tronarko_placas_tectonicas_limites.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas_v0.png"));

        }


        Rasterizador.trocar_cores(render_tronarko_placas_tectonicas_limites, mCores.getPreto(), mCores.getVermelho());

        Imagem.exportar(render_tronarko_placas_tectonicas_limites.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas_v0.png"));

        fmt.print(">> Expandir limites !");

        // EXPANDIR LIMITES
        for (int y = 0; y < render_tronarko_placas_tectonicas_limites.getAltura(); y++) {
            for (int x = 0; x < render_tronarko_placas_tectonicas_limites.getLargura(); x++) {
                if (render_tronarko_placas_tectonicas_limites.getPixel(x, y).igual(mCores.getVermelho())) {

                    Cor mais_proxima = Rasterizador.GET_COR_DO_QUADRANTE_DIFERENTE(render_tronarko_placas_tectonicas_limites, x, y, 50, mCores.getVermelho(), mCores.getPreto());
                    render_tronarko_placas_tectonicas_limites.setPixel(x, y, mais_proxima);

                }
            }
        }

        Imagem.exportar(render_tronarko_placas_tectonicas_limites.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas.png"));


        AtzumCriativoLog.terminar("ServicoTectonico.INICIAR_PLACAS");
        AtzumCriativoLog.exibir_item("ServicoTectonico.INICIAR_PLACAS");
    }


    public static void EXTRAIR_PLACAS_TECTONICAS_CONTORNOS() {

        AtzumCriativoLog.iniciar("ServicoTectonico.EXTRAIR_CONTORNOS");

        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas.png"));

        Cores mCores = new Cores();


        Renderizador render_salvar = Renderizador.CONSTRUIR(render.getLargura(), render.getAltura(), mCores.getPreto());

        fmt.print(">> Extrair contornos das placas tectônicas !");

        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {

                Cor ponto_a = render.getPixel(x, y);

                if (render.isPontoValido(x + 1, y)) {

                    Cor ponto_b = render.getPixel(x + 1, y);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x - 1, y)) {

                    Cor ponto_b = render.getPixel(x - 1, y);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x, y + 1)) {

                    Cor ponto_b = render.getPixel(x, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }
                if (render.isPontoValido(x, y - 1)) {

                    Cor ponto_b = render.getPixel(x, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                // DIAGONAL
                if (render.isPontoValido(x + 1, y + 1)) {

                    Cor ponto_b = render.getPixel(x + 1, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x + 1, y - 1)) {

                    Cor ponto_b = render.getPixel(x + 1, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x - 1, y + 1)) {

                    Cor ponto_b = render.getPixel(x - 1, y + 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

                if (render.isPontoValido(x - 1, -+1)) {

                    Cor ponto_b = render.getPixel(x - 1, y - 1);

                    if (ponto_a.isDiferente(ponto_b)) {
                        render_salvar.setPixel(x, y, mCores.getVermelho());
                    }
                }

            }
        }

        Imagem.exportar(render_salvar.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_limites.png"));

        AtzumCriativoLog.terminar("ServicoTectonico.EXTRAIR_CONTORNOS");
        AtzumCriativoLog.exibir_item("ServicoTectonico.EXTRAIR_CONTORNOS");

    }

    public static void CRIAR_PLACAS_COM_LIMITES() {

        AtzumCriativoLog.iniciar("ServicoTectonico.CRIAR_PLACAS_COM_LIMITES");

        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_limites.png"));

        Cores mCores = new Cores();

        fmt.print(">> Criar placas tectônicas !");

        Lista<Par<Ponto, Cor>> placas_tectonicas = AtzumPlacasTectonicas.GET_PLACAS_TECTONICAS();

        for (Par<Ponto, Cor> placa_tectonica : placas_tectonicas) {
            //  render_tronarko_placas_tectonicas_limites.drawRect_Pintado(placa_tectonica.getChave().getX(),placa_tectonica.getChave().getY(),50,50,placa_tectonica.getValor());

            RefInt processante = new RefInt(0);

            Acao durante_mudanca = new Acao() {
                @Override
                public void fazer() {
                    Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/processando.png"));
                }
            };

            Acao a_cada_100 = new Acao() {
                @Override
                public void fazer() {

                    // Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.PROCESSANDO_GET_ARQUIVO("processando_" + fmt.zerado(processante.get(), 4) + ".png"));
                    processante.set(processante.get() + 1);

                }
            };


            Rasterizador.RASTERIZAR_COM(render, placa_tectonica.getChave().getX(), placa_tectonica.getChave().getY(), mCores.getPreto(), placa_tectonica.getValor(), durante_mudanca, a_cada_100);

            Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas_com_limites.png"));

        }

        Imagem.exportar(render.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas_com_limites.png"));

        AtzumCriativoLog.terminar("ServicoTectonico.CRIAR_PLACAS_COM_LIMITES");
        AtzumCriativoLog.exibir_item("ServicoTectonico.CRIAR_PLACAS_COM_LIMITES");
    }


    public static void DEFINIR_AREAS_DE_ATIVIDADE_SISMICA() {

        AtzumCriativoLog.iniciar("ServicoTectonico.DEFINIR_AREAS_DE_ATIVIDADE_SISMICA");

        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_limites.png"));
        Renderizador render_atividade_sismica = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_limites.png"));

        fmt.print(">> Definir areas com atividade sismica !");

        Cores mCores = new Cores();

        int x_mudanca = Aleatorio.aleatorio_entre(50, 200);
        int x_contagem = 0;
        int x_aumentar = Aleatorio.aleatorio_entre(5, 20);

        for (int y = 0; y < render.getAltura(); y++) {
            x_contagem += 1;
            if (x_contagem == x_mudanca) {
                x_contagem = 0;
                x_mudanca = Aleatorio.aleatorio_entre(50, 200);
                x_aumentar = Aleatorio.aleatorio_entre(5, 20);
            }
            for (int x = 0; x < render.getLargura(); x++) {
                if (render.getPixel(x, y).igual(mCores.getVermelho())) {

                    for (int a = (x - 30 - x_aumentar); a < (x + 30 + x_aumentar); a++) {
                        render_atividade_sismica.setPixel(a, y, mCores.getAmarelo());
                    }

                }

            }
        }

        int y_mudanca = Aleatorio.aleatorio_entre(50, 200);
        int y_contagem = 0;
        int y_aumentar = Aleatorio.aleatorio_entre(5, 20);


        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {

                y_contagem += 1;
                if (y_contagem == y_mudanca) {
                    y_contagem = 0;
                    y_mudanca = Aleatorio.aleatorio_entre(50, 200);
                    y_aumentar = Aleatorio.aleatorio_entre(5, 20);
                }
                if (render.getPixel(x, y).igual(mCores.getVermelho())) {

                    for (int a = (y - 30 - y_aumentar); a < (y + 30 + y_aumentar); a++) {
                        render_atividade_sismica.setPixel(x, a, mCores.getAmarelo());
                    }

                }

            }
        }

        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {
                if (render.getPixel(x, y).igual(mCores.getVermelho())) {
                    render_atividade_sismica.setPixel(x, y, mCores.getAzul());
                }
            }
        }

        Imagem.exportar(render_atividade_sismica.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_atividade_sismica.png"));

        AtzumCriativoLog.terminar("ServicoTectonico.DEFINIR_AREAS_DE_ATIVIDADE_SISMICA");
        AtzumCriativoLog.exibir_item("ServicoTectonico.DEFINIR_AREAS_DE_ATIVIDADE_SISMICA");
    }


    public static void VULCANIZAR() {

        AtzumCriativoLog.iniciar("ServicoTectonico.VULCANIZAR");

        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_atividade_sismica.png"));
        Renderizador render_vulcanismo = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_atividade_sismica.png"));
        Renderizador render_vulcoes = new Renderizador(AtzumCreator.GET_MAPA_PRETO_E_BRANCO());
        Renderizador terra_ou_agua = new Renderizador(AtzumCreator.GET_MAPA_PRETO_E_BRANCO());

        Cores mCores = new Cores();

        fmt.print(">> Definir areas com atividade vulcânica !");

        Lista<Entidade> vulcoes = ENTT.CRIAR_LISTA();


        int vulcanizando_contagem = 0;
        int vulcanizando_mudanca = Aleatorio.aleatorio_entre(100, 300);


        for (int y = 0; y < render.getAltura(); y++) {
            for (int x = 0; x < render.getLargura(); x++) {
                if (render.getPixel(x, y).igual(mCores.getAzul())) {

                    vulcanizando_contagem += 1;
                    if (vulcanizando_contagem == vulcanizando_mudanca) {
                        vulcanizando_contagem = 0;
                        vulcanizando_mudanca = Aleatorio.aleatorio_entre(100, 300);

                        int vulcao_x = x - Aleatorio.aleatorio_entre_positivo_ou_negativo(10, 30);
                        int vulcao_y = y + Aleatorio.aleatorio_entre_positivo_ou_negativo(10, 30);

                        if (render.getPixel(vulcao_x, vulcao_y).igual(mCores.getAmarelo()) || render.getPixel(vulcao_x, vulcao_y).igual(mCores.getAzul())) {
                            render_vulcanismo.drawCirculoCentralizado_Pintado(vulcao_x, vulcao_y, 10, mCores.getVermelho());

                            Entidade e_vulcao = ENTT.CRIAR_EM_SEQUENCIALMENTE(vulcoes, "VID");
                            e_vulcao.at("X", vulcao_x);
                            e_vulcao.at("Y", vulcao_y);
                            e_vulcao.at("Sismicidade", Aleatorio.aleatorio_entre(500, 6000));
                            e_vulcao.at("Dormencia", e_vulcao.atInt("Sismicidade") + Aleatorio.aleatorio_entre(50, 100));
                            e_vulcao.at("Escala", Aleatorio.aleatorio_entre(1, 12));


                            if (terra_ou_agua.getPixel(vulcao_x, vulcao_y).igual(mCores.getPreto())) {

                                e_vulcao.at("Tipo", "Aquatico");
                                render_vulcoes.drawCirculoCentralizado_Pintado(vulcao_x, vulcao_y, 10, mCores.getAzul());

                            } else {

                                e_vulcao.at("Tipo", "Terrestre");
                                render_vulcoes.drawCirculoCentralizado_Pintado(vulcao_x, vulcao_y, 10, mCores.getVermelho());

                            }


                        }


                    }

                }
            }
        }


        Imagem.exportar(render_vulcanismo.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_vulcanismo.png"));
        Imagem.exportar(render_vulcoes.toImagemSemAlfa(), AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_vulcoes.png"));

        ENTT.GUARDAR(vulcoes, AtzumCreator.LOCAL_GET_ARQUIVO("dados/vulcanismo.entts"));


        ENTT.EXIBIR_TABELA_COM_TITULO(vulcoes, "VULCÕES");

        AtzumCriativoLog.terminar("ServicoTectonico.VULCANIZAR");
        AtzumCriativoLog.exibir_item("ServicoTectonico.VULCANIZAR");
    }


    public static void GUARDAR_DADOS_PLACAS_TECTONICAS() {

        AtzumCriativoLog.iniciar("ServicoTectonico.GUARDAR_DADOS_PLACAS_TECTONICAS");

        Renderizador render = Renderizador.ABRIR_DE_ARQUIVO_RGB(AtzumCreator.LOCAL_GET_ARQUIVO("build/tectonico/atzum_tectonismo_placas.png"));

        fmt.print(">> Guardar dados tectônicos !");

        AtzumTerra terra = new AtzumTerra();

        QTT.alocar(AtzumCreator.DADOS_GET_ARQUIVO("placas_tectonicas.qtt"), terra.getLargura(), terra.getAltura());


        Lista<Cor> placas_tectonicas = AtzumPlacasTectonicas.GET_PLACAS_TECTONICAS_CORES();

        Lista<Entidade> dados_placas_tectonicas = ENTT.CRIAR_LISTA();


        for (Cor placa_tectonica : placas_tectonicas) {

            Entidade e_placa = ENTT.CRIAR_EM_SEQUENCIALMENTE(dados_placas_tectonicas, "PlacaID", 1);
            e_placa.at("Cor", placa_tectonica.toString());

            int placa_id = e_placa.atInt("PlacaID");

            long tamanho = 0;

            for (int y = 0; y < render.getAltura(); y++) {
                for (int x = 0; x < render.getLargura(); x++) {
                    if (render.getPixel(x, y).igual(placa_tectonica)) {
                        QTT.alterar(AtzumCreator.DADOS_GET_ARQUIVO("placas_tectonicas.qtt"), x, y, placa_id);
                        tamanho += 1;
                    }
                }
            }

            e_placa.at("Tamanho", tamanho);

        }

        ENTT.GUARDAR(dados_placas_tectonicas, AtzumCreator.DADOS_GET_ARQUIVO("placas_tectonicas.entts"));

        AtzumCriativoLog.terminar("ServicoTectonico.GUARDAR_DADOS_PLACAS_TECTONICAS");
        AtzumCriativoLog.exibir_item("ServicoTectonico.GUARDAR_DADOS_PLACAS_TECTONICAS");

    }


    public static void VULCOES_NOMEAR() {

        AtzumCriativoLog.iniciar("ServicoTectonico.VULCOES_NOMEAR");

        // DEFINIR PLACAS EM VULCOES
        QTT placas_tectonicas = QTT.getTudo(AtzumCreator.DADOS_GET_ARQUIVO("placas_tectonicas.qtt"));

        Lista<Entidade> vulcoes = ENTT.ABRIR(AtzumCreator.DADOS_GET_ARQUIVO("vulcanismo.entts"));

        ENTT.EXIBIR_TABELA(vulcoes);

        Atzum atzum = new Atzum();
        AtzumTerra atzum_planeta = new AtzumTerra();


        Lista<String> nomes_unicos = new Lista<String>();

        nomes_unicos = ENTT.FILTRAR_UNICOS(Atzum.GET_CIDADES_NOMES(), "Nome");

        //Strings.exibir_lista_em_linhas(nomes_unicos);


        FuncaoGama<String, QTT, Integer, Integer> buscar_placa = new FuncaoGama<String, QTT, Integer, Integer>() {
            @Override
            public String fazer(QTT qtt, Integer x, Integer y) {

                if (x < 0) {
                    x = atzum_planeta.getLargura() + x;
                } else if (x >= atzum_planeta.getLargura()) {
                    x = x - atzum_planeta.getLargura();
                }

                if (y < 0) {
                    y = 0;
                } else if (y >= atzum_planeta.getAltura()) {
                    y = atzum_planeta.getAltura() - 1;
                }

                if (x >= 0 && y >= 0 && x < atzum_planeta.getLargura() && y < atzum_planeta.getAltura()) {
                    return atzum.GET_PLACA_TECTONICA(qtt.getValor(x, y));
                }
                return "";
            }
        };


        for (Entidade e : vulcoes) {

            e.at("PlacaTectonica", buscar_placa.fazer(placas_tectonicas, e.atInt("X"), e.atInt("Y")));

            String h1 = buscar_placa.fazer(placas_tectonicas, e.atInt("X") - 200, e.atInt("Y"));
            String h2 = buscar_placa.fazer(placas_tectonicas, e.atInt("X") + 200, e.atInt("Y"));

            String v1 = buscar_placa.fazer(placas_tectonicas, e.atInt("X"), e.atInt("Y") - 200);
            String v2 = buscar_placa.fazer(placas_tectonicas, e.atInt("X"), e.atInt("Y") + 200);

            e.at("PlacaTectonica_Norte", v1);
            e.at("PlacaTectonica_Sul", v2);

            e.at("PlacaTectonica_Oeste", h1);
            e.at("PlacaTectonica_Leste", h2);

            Unico<String> placas = Strings.CRIAR_UNICO();

            Unico<String> placas_horizontalmente = Strings.CRIAR_UNICO();
            Unico<String> placas_verticalmente = Strings.CRIAR_UNICO();


            if (Strings.isValida(h1)) {
                placas_horizontalmente.item(h1);
                placas.item(h1);
            }

            if (Strings.isValida(h2)) {
                placas_horizontalmente.item(h2);
                placas.item(h2);
            }

            if (Strings.isValida(v1)) {
                placas_verticalmente.item(v1);
                placas.item(v1);
            }

            if (Strings.isValida(v2)) {
                placas_verticalmente.item(v2);
                placas.item(v2);
            }


            String horizontalidade = Strings.LISTA_TO_TEXTO_LINHA(placas_horizontalmente.toLista());
            String verticalidade = Strings.LISTA_TO_TEXTO_LINHA(placas_verticalmente.toLista());


            e.at("Horizontalidade", horizontalidade);
            e.at("Verticalidade", verticalidade);

            e.at("Localidade", "");
            e.at("LocalidadeModo", "");

            String localidade_modo = "";

            if (placas_horizontalmente.getQuantidade() == 0) {

                if (placas_verticalmente.getQuantidade() == 1) {
                    localidade_modo = "Central";
                } else if (placas_verticalmente.getQuantidade() == 2) {
                    localidade_modo = "Verticalmente";
                }

            } else if (placas_verticalmente.getQuantidade() == 0) {

                if (placas_horizontalmente.getQuantidade() == 1) {
                    localidade_modo = "Central";
                } else if (placas_horizontalmente.getQuantidade() == 2) {
                    localidade_modo = "Horizontalmente";
                }

            }

            if (placas_horizontalmente.getQuantidade() == 1 && placas_verticalmente.getQuantidade() == 1) {
                if (placas.getQuantidade() == 1) {
                    localidade_modo = "Central";
                } else if (placas.getQuantidade() == 2) {
                    localidade_modo = "Diagonalmente";
                }
            } else if (placas_horizontalmente.getQuantidade() == 1 && placas_verticalmente.getQuantidade() == 2) {
                if(placas_verticalmente.existe(placas_horizontalmente.toLista().get(0))){
                    localidade_modo = "Diagonal";
                }else{
                    localidade_modo = "Zona";
                }
            } else if (placas_horizontalmente.getQuantidade() == 2 && placas_verticalmente.getQuantidade() == 1) {
                if(placas_horizontalmente.existe(placas_verticalmente.toLista().get(0))){
                    localidade_modo = "Diagonal";
                }else{
                    localidade_modo = "Zona";
                }
            } else {
                if (placas.getQuantidade() == 1) {
                    localidade_modo = "Central";
                } else if (placas.getQuantidade() > 1) {
                    localidade_modo = "Zona";
                }
            }


            e.at("LocalidadeModo", localidade_modo);


            if (placas.getQuantidade() > 1) {
                e.at("Localidade", Strings.LISTA_TO_TEXTO_LINHA(placas.toLista()));
            }

            String nome = NomeadorBurgo.getSimplesUnico(nomes_unicos);

            e.at("Nome", nome);
            nomes_unicos.adicionar(nome);
        }


        ENTT.ATRIBUTO_REMOVER(vulcoes, "Sismicidade");
        ENTT.ATRIBUTO_REMOVER(vulcoes, "Dormencia");

        ENTT.EXIBIR_TABELA(vulcoes);

        ENTT.GUARDAR(vulcoes, AtzumCreator.DADOS_GET_ARQUIVO("tectonico_vulcoes.entts"));


        AtzumCriativoLog.terminar("ServicoTectonico.VULCOES_NOMEAR");
        AtzumCriativoLog.exibir_item("ServicoTectonico.VULCOES_NOMEAR");

    }

}
