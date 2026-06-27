package apps.app_letrum;

import libs.azzal.Renderizador;
import libs.azzal.utilitarios.Cor;

public interface Fonte {

    void setRenderizador(Renderizador eRenderizador);

    Renderizador getRenderizador();

    void escreva(int x, int y, String frase);
    void escreva(int x, int y, int frase);

    void escrevaComOutraCor(int x, int y, String frase, Cor eOutraCor);

    int getLarguraDe(String frase);

    int getAltura();

    void escreveLinha(int y, int x1, int x2, String eTexto1, String eTexto2);
    void escreveLinha(int y, int x1, int x2, String eTexto1, int eTexto2);

    void escrevaCentralizado(int x, int y, String frase);

    Cor getCor();

}
