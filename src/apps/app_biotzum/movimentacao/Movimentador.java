package apps.app_biotzum.movimentacao;

import apps.app_biotzum.Organismo;
import libs.luan.Lista;

public interface Movimentador {
    Movimento andar(Lista<Organismo> outros);
}
