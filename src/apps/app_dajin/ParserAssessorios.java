package apps.app_dajin;

import libs.luan.Opcional;
import libs.luan.fmt;

public class ParserAssessorios {

    private Parser mParser;

    public ParserAssessorios(Parser parser) {
        mParser = parser;
    }


    public Opcional<String> esperado_identificador(String erro) {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return Opcional.CANCEL();
        }

        Token tk_nome = mParser.getTokenCorrente();
        String nome = tk_nome.getValor();
        if (!ParserChecador.IS_IDENTIFICADOR(tk_nome)) {
            mParser.errar(tk_nome, erro);
            return Opcional.CANCEL();
        }
        return Opcional.OK(nome);
    }

    public Opcional<String> esperado_identificador_mas_obtive(String erro) {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return Opcional.CANCEL();
        }

        Token tk_nome = mParser.getTokenCorrente();
        String nome = tk_nome.getValor();
        if (!ParserChecador.IS_IDENTIFICADOR(tk_nome)) {
            mParser.errar(tk_nome, erro + tk_nome.getValor());
            return Opcional.CANCEL();
        }
        return Opcional.OK(nome);
    }

    public Opcional<String> esperado_identificador_especifico_mas_obtive(String especifico, String erro) {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return Opcional.CANCEL();
        }

        Token tk_nome = mParser.getTokenCorrente();
        String nome = tk_nome.getValor();
        if (!ParserChecador.IS_IDENTIFICADOR(tk_nome)) {
            mParser.errar(tk_nome, erro + tk_nome.getValor());
            return Opcional.CANCEL();
        }

        if (ParserChecador.IS_IDENTIFICADOR(tk_nome) && ParserChecador.IS_PALAVRA_CHAVE(tk_nome, especifico)) {

        } else {
            mParser.errar(tk_nome, "Era esperado ( " + especifico + " ) mas obtive : " + tk_nome.getValor());
        }

        return Opcional.OK(nome);
    }

    public Opcional<String> esperado_operador(String erro) {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return Opcional.CANCEL();
        }

        Token tk_nome = mParser.getTokenCorrente();
        String nome = tk_nome.getValor();
        if (!ParserChecador.IS_OPERADOR(tk_nome)) {
            mParser.errar(tk_nome, erro);
            return Opcional.CANCEL();
        }
        return Opcional.OK(nome);
    }

    public boolean esperado_abrir_parenteses() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_PARENTESES_ABRIR(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado abrir paresenteses, mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }


    public boolean esperado_fechar_parenteses() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_PARENTESES_FECHAR(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado fechar paresenteses, mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }

    public boolean esperado_abrir_chaves() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_CHAVES_ABRIR(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado abrir chaves, mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }

    public boolean obrigacaoTerProximo() {
        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }
        return true;
    }


    public Opcional<String> esperado_delimitador(String delimitador, String erro) {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return Opcional.CANCEL();
        }

        Token tk_nome = mParser.getTokenCorrente();
        String nome = tk_nome.getValor();
        if (ParserChecador.IS_DELIMITADOR(tk_nome)) {
            if (tk_nome.isValorDiferente( delimitador)) {
                mParser.errar(tk_nome, erro);
                return Opcional.CANCEL();
            }
        } else {
            mParser.errar(tk_nome, erro);
            return Opcional.CANCEL();
        }
        return Opcional.OK(nome);
    }


    public boolean esperado_ponto_e_virgula() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_PONTO_E_VIRGULA(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado ponto e virgula, mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }


    public boolean esperado_menor() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_OPERADOR_MENOR(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado '<', mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }

    public boolean esperado_maior() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_OPERADOR_MAIOR(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado '>', mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }



    public boolean esperado_abrir_colchetes() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_COLCHETES_ABRIR(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado '[', mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }

    public boolean esperado_fechar_colchetes() {

        Token antes = mParser.getTokenCorrente();
        mParser.proximo();
        if (!mParser.temAgora()) {
            mParser.errar(antes, "Era esperado mais termos...");
            return false;
        }

        Token tk_abrir = mParser.getTokenCorrente();

        if (!ParserChecador.IS_COLCHETES_FECHAR(tk_abrir)) {
            mParser.errar(tk_abrir, "Era esperado ']', mas obtive " + tk_abrir.getValor());
            return false;
        }
        return true;
    }
}
