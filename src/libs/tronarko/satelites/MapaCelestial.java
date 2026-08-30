package libs.tronarko.satelites;

import java.awt.Color;
import java.util.ArrayList;

import libs.azzal.utilitarios.Cor;
import libs.tronarko.intervalos.Tozte_Intervalo;
import libs.tronarko.Tozte;
import libs.tronarko.utils.EventoLegenda;

public class MapaCelestial {

    // TRES
    public Iluminacao getIluminacao() {
        return new Iluminacao();
    }

    public Escuridao getEscuridao() {
        return new Escuridao();
    }

    // DUAS
    public Allett getAllett() {
        return new Allett();
    }

    public Unnall getUnnall() {
        return new Unnall();
    }

    public Ettun getEttun() {
        return new Ettun();
    }

    // UMA
    public Allizz getAllizz() {
        return new Allizz();
    }

    public Ettizz getEttizz() {
        return new Ettizz();
    }

    public Unnizz getUnnizz() {
        return new Unnizz();
    }


    public void mostrarOcorrencias(ArrayList<Tozte_Intervalo> ocorrencias) {

        for (Tozte_Intervalo ocorrencia : ocorrencias) {
            System.out.println(ocorrencia.getNome() + " : " + ocorrencia.getInicio().toString() + " a " + ocorrencia.getFim().getTexto() + " com : " + ocorrencia.getSuperarkos() + " Superarkos ");
        }

    }


    public ArrayList<EventoLegenda> getToztesComCor_Allux(int eTronarko) {

        ArrayList<EventoLegenda> ToztesComCor = new ArrayList<EventoLegenda>();

        Allux AlluxC = new Allux();

        for (int h = 1; h <= 10; h++) {

            for (int s = 1; s <= 50; s++) {

                Tozte TozteC = new Tozte(s, h, eTronarko);

                Fases FaseC = AlluxC.getFase(TozteC);

                switch (FaseC) {
                    case CHEIA:
                        ToztesComCor.add(new EventoLegenda("CHEIA", TozteC, new Cor(200,200,0)));
                        break;
                    case NOVA:
                        ToztesComCor.add(new EventoLegenda("NOVA", TozteC, new Cor(0,0,0)));
                        break;
                    case MINGUANTE:
                        ToztesComCor.add(new EventoLegenda("MINGUANTE", TozteC, new Cor(255,0,0)));
                        break;
                    case CRESCENTE:
                        ToztesComCor.add(new EventoLegenda("CRESCENTE", TozteC, new Cor(0,0,255)));
                        break;
                }

            }

        }

        return ToztesComCor;
    }

    public ArrayList<EventoLegenda> getToztesComCor_Ettos(int eTronarko) {

        ArrayList<EventoLegenda> ToztesComCor = new ArrayList<EventoLegenda>();

        Ettos EttosC = new Ettos();

        for (int h = 1; h <= 10; h++) {

            for (int s = 1; s <= 50; s++) {

                Tozte TozteC = new Tozte(s, h, eTronarko);

                Fases FaseC = EttosC.getFase(TozteC);

                switch (FaseC) {
                    case CHEIA:
                        ToztesComCor.add(new EventoLegenda("CHEIA", TozteC, Cor.getRGB(Color.YELLOW)));
                        break;
                    case NOVA:
                        ToztesComCor.add(new EventoLegenda("NOVA", TozteC, Cor.getRGB(Color.WHITE)));
                        break;
                    case MINGUANTE:
                        ToztesComCor.add(new EventoLegenda("MINGUANTE", TozteC, Cor.getRGB(Color.RED)));
                        break;
                    case CRESCENTE:
                        ToztesComCor.add(new EventoLegenda("CRESCENTE", TozteC, Cor.getRGB(Color.BLUE)));
                        break;
                }

            }

        }

        return ToztesComCor;
    }

    public ArrayList<EventoLegenda> getToztesComCor_Todos(int eTronarko) {

        ArrayList<EventoLegenda> ToztesComCor = new ArrayList<EventoLegenda>();

        Allux FaseAllux = new Allux();
        Ettos FaseEttos = new Ettos();
        Unnos FaseUnnos = new Unnos();

        for (int h = 1; h <= 10; h++) {

            for (int s = 1; s <= 50; s++) {

                Tozte TozteC = new Tozte(s, h, eTronarko);

                Fases FaseAlluxC = FaseAllux.getFase(TozteC);
                Fases FaseEttosC = FaseEttos.getFase(TozteC);
                Fases FaseUnnosC = FaseUnnos.getFase(TozteC);


                // TRES
                if (FaseAlluxC == Fases.CHEIA && FaseEttosC == Fases.CHEIA && FaseUnnosC == Fases.CHEIA) {
                    ToztesComCor.add(new EventoLegenda("ILUMUNAÇAO", TozteC, Cor.getRGB(Color.GRAY)));
                }

                if (FaseAlluxC == Fases.NOVA && FaseEttosC == Fases.NOVA && FaseUnnosC == Fases.NOVA) {
                    ToztesComCor.add(new EventoLegenda("ESCURIDÃO", TozteC, Cor.getRGB(Color.BLACK)));
                }

                // DUAS
                if (FaseAlluxC == Fases.CHEIA && FaseEttosC == Fases.CHEIA && FaseUnnosC == Fases.NOVA) {
                    ToztesComCor.add(new EventoLegenda("ALLET", TozteC, Cor.getRGB(Color.ORANGE)));
                }

                if (FaseAlluxC == Fases.CHEIA && FaseEttosC == Fases.NOVA && FaseUnnosC == Fases.CHEIA) {
                    ToztesComCor.add(new EventoLegenda("UNNALL", TozteC, Cor.getRGB(Color.GREEN)));
                }
                if (FaseAlluxC == Fases.NOVA && FaseEttosC == Fases.CHEIA && FaseUnnosC == Fases.CHEIA) {
                    ToztesComCor.add(new EventoLegenda("ETTUN", TozteC, new Cor(135, 31, 120)));
                }

                // UM
                if (FaseAlluxC == Fases.CHEIA && FaseEttosC == Fases.NOVA && FaseUnnosC == Fases.NOVA) {
                    ToztesComCor.add(new EventoLegenda("ALLIZZ", TozteC, Cor.getRGB(Color.YELLOW)));
                }

                if (FaseAlluxC == Fases.NOVA && FaseEttosC == Fases.CHEIA && FaseUnnosC == Fases.NOVA) {
                    ToztesComCor.add(new EventoLegenda("ETTIZZ", TozteC, Cor.getRGB(Color.RED)));
                }
                if (FaseAlluxC == Fases.NOVA && FaseEttosC == Fases.NOVA && FaseUnnosC == Fases.CHEIA) {
                    ToztesComCor.add(new EventoLegenda("UNNIZZ", TozteC, Cor.getRGB(Color.BLUE)));
                }
            }

        }

        return ToztesComCor;
    }

    public ArrayList<EventoLegenda> getToztesComCor_Unnos(int eTronarko) {

        ArrayList<EventoLegenda> ToztesComCor = new ArrayList<EventoLegenda>();

        Unnos UnnosC = new Unnos();

        for (int h = 1; h <= 10; h++) {

            for (int s = 1; s <= 50; s++) {

                Tozte TozteC = new Tozte(s, h, eTronarko);

                Fases FaseC = UnnosC.getFase(TozteC);

                switch (FaseC) {
                    case CHEIA:
                        ToztesComCor.add(new EventoLegenda("CHEIA", TozteC, Cor.getRGB(Color.YELLOW)));
                        break;
                    case NOVA:
                        ToztesComCor.add(new EventoLegenda("NOVA", TozteC, Cor.getRGB(Color.WHITE)));
                        break;
                    case MINGUANTE:
                        ToztesComCor.add(new EventoLegenda("MINGUANTE", TozteC, Cor.getRGB(Color.RED)));
                        break;
                    case CRESCENTE:
                        ToztesComCor.add(new EventoLegenda("CRESCENTE", TozteC,Cor.getRGB( Color.BLUE)));
                        break;
                }

            }

        }

        return ToztesComCor;
    }

    public static class Allux extends Satelite {

        public Allux() {
            super("ALLUX", new Tozte(31, 6, 3856), 10, "AMARELO");
        }

    }

    public static class Ettos extends Satelite {

        public Ettos() {
            super("ETTOS", new Tozte(12, 8, 4727), 33, "VERMELHO");
        }

    }

    public static class Unnos extends Satelite {

        public Unnos() {
            super("UNNOS", new Tozte(18, 10, 4838), 22, "AZUL");
        }

    }


    public static class Iluminacao extends ObservadorLunar {

        public Iluminacao() {

            super("Iluminação", Fases.CHEIA, Fases.CHEIA, Fases.CHEIA);
        }

    }

    public static class Escuridao extends ObservadorLunar {

        public Escuridao() {

            super("Escuridão", Fases.NOVA, Fases.NOVA, Fases.NOVA);
        }

    }

    public static class Allett extends ObservadorLunar {

        public Allett() {

            super("Allett", Fases.CHEIA, Fases.CHEIA, Fases.NOVA);
        }

    }

    public static class Unnall extends ObservadorLunar {

        public Unnall() {

            super("Unnall", Fases.CHEIA, Fases.NOVA, Fases.CHEIA);
        }

    }

    public static class Ettun extends ObservadorLunar {

        public Ettun() {

            super("Ettun", Fases.NOVA, Fases.CHEIA, Fases.CHEIA);
        }

    }

    public static class Allizz extends ObservadorLunar {

        public Allizz() {

            super("Allizz", Fases.CHEIA, Fases.NOVA, Fases.NOVA);
        }

    }

    public static class Ettizz extends ObservadorLunar {

        public Ettizz() {

            super("Ettizz", Fases.NOVA, Fases.CHEIA, Fases.NOVA);
        }

    }

    public static class Unnizz extends ObservadorLunar {

        public Unnizz() {

            super("Unnizz", Fases.NOVA, Fases.NOVA, Fases.CHEIA);
        }

    }

}
