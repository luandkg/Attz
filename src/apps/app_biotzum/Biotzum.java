package apps.app_biotzum;

import apps.app_biotzum.acontecimentos.AcontecimentoMundano;
import apps.app_biotzum.acontecimentos.AcontecimentoSalvarLogs;
import libs.azzal.Renderizador;
import libs.luan.Lista;
import libs.tronarko.Tron;
import libs.tronarko.Tronarko;

public class Biotzum {

    private Tron mTron;

    private Lista<Organismo> mOrganismos;
    private Lista<Comida> mComidas;

    private Lista<AcontecimentoTemporal> mAcontecimentos;

    public Biotzum() {

        mOrganismos = new Lista<Organismo>();
        mComidas = new Lista<Comida>();

        mOrganismos.adicionar(new Organismo(10, 30));
        mOrganismos.adicionar(new Organismo(50, 30));
        mOrganismos.adicionar(new Organismo(80, 80));
        mOrganismos.adicionar(new Organismo(50, 30));
        mOrganismos.adicionar(new Organismo(40, 60));
        mOrganismos.adicionar(new Organismo(60, 90));
        mOrganismos.adicionar(new Organismo(70, 20));
        mOrganismos.adicionar(new Organismo(20, 20));
        mOrganismos.adicionar(new Organismo(60, 10));
        mOrganismos.adicionar(new Organismo(90, 40));

        mComidas.adicionar(new Comida(80, 5));
        mComidas.adicionar(new Comida(20, 80));
        mComidas.adicionar(new Comida(60, 40));
        mComidas.adicionar(new Comida(70, 5));
        mComidas.adicionar(new Comida(44, 80));
        mComidas.adicionar(new Comida(32, 40));

        mTron = new Tron(0,0,0,1,1,7000);

        mAcontecimentos = new Lista<AcontecimentoTemporal>();
        mAcontecimentos.adicionar(new AcontecimentoMundano(15,this, mOrganismos, mComidas));
        mAcontecimentos.adicionar(new AcontecimentoSalvarLogs(2000,this, mOrganismos));


    }

    public Tron getTron() {
        return mTron;
    }

    public void adicionarUzzon(int quantidade) {
        mTron = mTron.modificar_Uzzon(quantidade);
    }

    public Lista<Organismo> getOrganismos() {
        return mOrganismos;
    }

    public Lista<Comida> getComidas() {
        return mComidas;
    }

    public void update(double dt) {
        for (AcontecimentoTemporal acontecimento : mAcontecimentos) {
            acontecimento.atualiza();
        }
    }


    public void render(Renderizador g) {
        for (Organismo org : mOrganismos) {
            org.render(g);
        }

        for (Comida comida : mComidas) {
            comida.render(g);
        }
    }
}
