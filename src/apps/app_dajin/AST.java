package apps.app_dajin;

import libs.luan.Lista;
import libs.luan.Strings;

public class AST {

    private String mTipo;
    private String mValor;

    private Lista<AST> mAST;

    private AST pai;
    private Token mToken;

    public AST(String tipo) {
        mTipo = tipo;
        mValor = "";
        mAST = new Lista<AST>();
        pai=null;
    }

    public AST(String tipo, String valor) {
        mTipo = tipo;
        mValor = valor;
        mAST = new Lista<AST>();
        pai=null;
    }


    public String getTipo() {
        return mTipo;
    }

    public String getValor() {
        return mValor;
    }

    public void setValor(String valor) {
        mValor = valor;
    }

    public AST criarAST(String tipo) {
        AST novo = new AST(tipo);
        novo.pai=this;
        mAST.adicionar(novo);
        return novo;
    }

    public AST criarAST(String tipo, String valor) {
        AST novo = new AST(tipo, valor);
        novo.pai=this;
        mAST.adicionar(novo);
        return novo;
    }

    public AST getPai(){
        return pai;
    }

    public Lista<AST> getAST() {
        return mAST;
    }

    public boolean is(String tipo) {
        return Strings.isIgual(mTipo, tipo);
    }

    public boolean isValor(String valor) {
        return Strings.isIgual(mValor, valor);
    }

    public void setTipo(String tipo) {
        mTipo = tipo;
    }

    public AST get(String nome) {
        for (AST a : mAST) {
            if (a.is(nome)) {
                return a;
            }
        }
        return new AST("");
    }


    public boolean existe(String tipo) {
        boolean ret = false;
        for (AST a : mAST) {
            if (a.is(tipo)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public void adicionar(AST no) {
        mAST.adicionar(no);
    }

    public boolean remover(String tipo) {
        boolean ret = false;
        for (AST a : mAST) {
            if (a.is(tipo)) {
                mAST.remover(a);
                break;
            }
        }
        return ret;
    }


    public boolean remover(AST tipo) {
        boolean ret = false;
        for (AST a : mAST) {
            if (a==tipo) {
                mAST.remover(a);
                break;
            }
        }
        return ret;
    }

    public AST getClone(){
        AST clone = new AST(mTipo,mValor);
        for(AST a : mAST){
            clone.adicionar(a.getClone());
        }
        return clone;
    }

    public String getPrefixo(int prefixo) {
        String ret = "";
        for (int p = 0; p < prefixo; p++) {
            ret += "   ";
        }
        return ret;
    }

    public void exibirAST() {

        int prefixo = 0;

        for (AST e : mAST) {
            System.out.println(getPrefixo(prefixo) + "++ " + e.getTipo() + " :: " + e.getValor());
            exibirASTInterno(prefixo + 1, e);
        }

    }

    public void exibirASTInterno(int prefixo, AST pai) {


        for (AST e : pai.getAST()) {
            System.out.println(getPrefixo(prefixo) + "++ " + e.getTipo() + " :: " + e.getValor());
            exibirASTInterno(prefixo + 1, e);
        }

    }

    public Token getToken(){
        return mToken;
    }

    public void setToken(Token a){
        mToken=a;
    }

}
