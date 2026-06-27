package libs.luan;

public class TextoDocumento {

    private String mTexto;

    public TextoDocumento() {
        mTexto = "";
    }

    public void adicionarLinha() {
        mTexto += "\n";
    }


    public void adicionarLinha(String eLinha) {
        mTexto += eLinha + "\n";
    }

    public void Adicionar(String eMais) {
        mTexto += eMais;
    }

    public void adicionar(String eMais) {
        mTexto += eMais;
    }

    public String toString() {
        return mTexto;
    }

    public String toDocumento() {
        return mTexto;
    }


    public void adicionar_com_tab_final(String eMais) {
        mTexto += eMais + "\t";
    }

    public void adicionar_com_tab_final(int eMais) {
        mTexto += eMais + "\t";
    }

    public void adicionar_com_tab_final(double eMais) {
        mTexto += eMais + "\t";
    }

    public void adicionar_linha_duplo_com_tab_final(String e1, String e2) {
        String linha = "";
        linha += e1 + "\t";
        linha += e2 + "\t";
        adicionarLinha(linha);
    }

    public void adicionar_linha_duplo_com_tab_final(int e1, int e2) {
        String linha = "";
        linha += e1 + "\t";
        linha += e2 + "\t";
        adicionarLinha(linha);
    }

    public void adicionar_linha_duplo_com_tab_final(String e1, int e2) {
        String linha = "";
        linha += e1 + "\t";
        linha += e2 + "\t";
        adicionarLinha(linha);
    }

    public void adicionar_linha_duplo_com_tab_final(int e1, String e2) {
        String linha = "";
        linha += e1 + "\t";
        linha += e2 + "\t";
        adicionarLinha(linha);
    }

    public void salvar(String arquivo) {
        Texto.arquivo_escrever(arquivo, this.toDocumento());
    }

    public void trim(){
     mTexto=mTexto.trim();
    }
}
