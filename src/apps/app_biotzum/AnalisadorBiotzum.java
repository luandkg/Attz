package apps.app_biotzum;

import libs.arquivos.ds.DS;
import libs.arquivos.ds.DSItem;
import libs.entt.ENTT;
import libs.entt.Entidade;
import libs.luan.*;
import libs.tronarko.Tronarko;
import libs.tronarko.utils.StringTronarko;

public class AnalisadorBiotzum {

    public static void INICIAR() {

        String arquivo = "/home/luan/assets/orgs/organismo_0.org";

        Lista<Entidade> dados = ENTT.ABRIR(arquivo);

        //ENTT.EXIBIR_TABELA(dados);

        boolean comecou = false;

        int anterior_x = 0;
        int anterior_y = 0;
        String anterior_tron = "";

        Lista<Entidade> processado = new Lista<Entidade>();
        Lista<Entidade> resumoPassos = new Lista<Entidade>();
        Lista<Entidade> resumoBatimentos = new Lista<Entidade>();
        Lista<Entidade> treinos = new Lista<Entidade>();

        for (DSItem ref_item : DS.ler_todos(arquivo)) {

            Entidade item = ENTT.PARSER_ENTIDADE(ref_item.getTexto());

            if (ref_item.isNome("Organismo::Resumo(Passos)")) {
                resumoPassos.adicionar(item);
            } else if (ref_item.isNome("Organismo::Resumo(Batimentos)")) {
                resumoBatimentos.adicionar(item);
            }

            if (ref_item.isNome("Organismo::AtividadeFisica(Iniciar)")) {
                treinos.adicionar(item);
            } else if (ref_item.isNome("Organismo::AtividadeFisica(Terminar)")) {
                treinos.adicionar(item);
            }

            if (ref_item.isNome("Organismo::Informativo")) {

                if (comecou) {
                    int x = item.atInt("X");
                    int y = item.atInt("Y");
                    if (item.isDiferente("Tron", anterior_tron)) {


                        long tempo = Tronarko.TRON_DIFERENCA_VALOR(StringTronarko.PARSER_TRON(anterior_tron), StringTronarko.PARSER_TRON(item.at("Tron")));

                        double dx = Matematica.MODULO(x - anterior_x) / (double) tempo;
                        double dy = Matematica.MODULO(y - anterior_y) / (double) tempo;


                        item.at("Tempo", tempo);

                        anterior_tron = item.at("Tron");
                        anterior_x = item.atInt("X");
                        anterior_y = item.atInt("Y");


                        item.at("VelocidadeXY", "(" + fmt.doubleNumC2(dx) + "::" + fmt.doubleNumC2(dy) + ")");
                        item.at("Velocidade", fmt.doubleNumC2(Math.sqrt((dx * dx) + (dy * dy))));

                    }
                } else {
                    anterior_tron = item.at("Tron");
                    anterior_x = item.atInt("X");
                    anterior_y = item.atInt("Y");
                }


                comecou = true;
                processado.adicionar(item);
            }

        }

        //   ENTT.EXIBIR_TABELA(processado);

        ENTT.EXIBIR_TABELA(ENTT.GET_AMOSTRA_PEQUENA(dados));

        ENTT.EXIBIR_TABELA(resumoPassos);
        ENTT.EXIBIR_TABELA(resumoBatimentos);

        for (Entidade e : resumoBatimentos) {
            e.at("Passos", ENTT.GET_UM(resumoPassos, "Tron", e.at("Tron")).at("Passos"));
        }


        ENTT.ATRIBUTO_REMOVER(resumoBatimentos, "Resumo");
        ENTT.ATRIBUTO_REMOVER(resumoBatimentos, "Tipo");
        ENTT.ATRIBUTO_REMOVER(resumoBatimentos, "Amostras");

        ENTT.AT_ALTERAR_NOME(resumoBatimentos, "Media", "BPM(Media)");
        ENTT.AT_ALTERAR_NOME(resumoBatimentos, "Minimo", "BPM(Minimo)");
        ENTT.AT_ALTERAR_NOME(resumoBatimentos, "Maximo", "BPM(Maximo)");

        Opcional<Entidade> op_anterior = Opcional.CANCEL();

        for (Entidade e : resumoBatimentos) {
            int taxa = Aleatorio.aleatorio_entre(3, 6);
            e.at("Distancia", fmt.f2((e.atInt("Passos") * (2.0 / taxa))));

            if (op_anterior.isOK()) {
                long tempo = Tronarko.TRON_DIFERENCA_VALOR(StringTronarko.PARSER_TRON(op_anterior.get().at("Tron")), StringTronarko.PARSER_TRON(e.at("Tron")));
                e.at("Tempo", tempo);
                double velocidade = 0;
                if (tempo > 0) {
                    velocidade = e.atDouble("Distancia") / tempo;
                }
                e.at("Velocidade", fmt.f2(velocidade));

                int bpm_media = e.atInt("BPM(Media)");

                if (bpm_media >= 40) {
                    e.at("StatusBMP", "Normal");
                }

                if (bpm_media >= 60) {
                    e.at("StatusBMP", "Agitado");
                }

                if (velocidade >= 0.3) {
                    e.at("StatusVelocidade", "Caminhando");
                }

                if (velocidade >= 0.5) {
                    e.at("StatusVelocidade", "Correndo");
                }


            }

            op_anterior.set(e);
        }

        for (Entidade e : resumoBatimentos) {

            if (e.is("StatusVelocidade", "Caminhando") && e.is("StatusBMP", "Normal")) {
                e.at("Status", "Aquecimento");
            }
            if (e.is("StatusVelocidade", "Caminhando") && e.is("StatusBMP", "Agitado")) {
                e.at("Status", "Aquecimento");
            }
            if (e.is("StatusVelocidade", "Correndo") && e.is("StatusBMP", "Agitado")) {
                e.at("Status", "AtividadeFisica");
            }
        }

        ENTT.EXIBIR_TABELA(resumoBatimentos);
        ENTT.EXIBIR_TABELA(treinos);

    }
}

