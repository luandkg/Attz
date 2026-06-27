package libs.arquivos;

import apps.app_atzum.AtzumCreator;
import libs.dkg.DKG;

import java.io.File;

public class PreferenciasOrganizadas {

    private String mArquivo;
    private DKG eDKG;

    public PreferenciasOrganizadas(String eArquivo) {
        mArquivo = eArquivo;
        eDKG = new DKG();
    }

    public void abrir() {
        eDKG = new DKG();
        eDKG.abrir(mArquivo);
    }

    public boolean abrirSeExistir() {
        if (new File(mArquivo).exists()) {
            abrir();
            return true;
        }
        return false;
    }

    public void salvar() {
        eDKG.salvar(mArquivo);
    }


    public String getOpcao(String eSecao, String eAtributo) {
        String v = eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).getValor();
        return v;
    }

    public void setOpcao(String eSecao, String eAtributo, String eValor) {
        eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).setValor(eValor);
    }

    public boolean getLogico(String eSecao, String eAtributo) {

        String v = eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).getValor();

        if (v.contentEquals("SIM")) {
            return true;
        } else {
            return false;
        }
    }


    public void setLogico(String eSecao, String eAtributo, boolean eValor) {

        if (eValor) {
            eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).setValor("SIM");
        } else {
            eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).setValor("NAO");
        }

    }

    public void setInteiro(String eSecao, String eAtributo, int eValor) {
        eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).setInteiro(eValor);
    }
    public int getInteiro(String eSecao, String eAtributo) {
        int v = eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).getInteiro(0);
        return v;
    }

    public void setDouble(String eSecao, String eAtributo, double eValor) {
        eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).setDouble(eValor);
    }
    public double getDouble(String eSecao, String eAtributo) {
        double v = eDKG.unicoObjeto("Preferencias").unicoObjeto(eSecao).identifique(eAtributo).getDouble(0.0);
        return v;
    }


    public static String OBTER_DIRETO(String eArquivo,String eSecao,String eAtributo,String eAtributoValorPadrao){

        PreferenciasOrganizadas po = new PreferenciasOrganizadas(eArquivo);

        if (po.abrirSeExistir()) {
           return po.getOpcao(eSecao, eAtributo);
        } else {
           return  eAtributoValorPadrao;
        }

    }

    public static void ALTERAR_DIRETO(String eArquivo,String eSecao,String eAtributo,String eAtributoValor){

        PreferenciasOrganizadas po = new PreferenciasOrganizadas(eArquivo);
        po.abrirSeExistir();
        po.setOpcao(eSecao, eAtributo, eAtributoValor);
        po.salvar();

    }
}
