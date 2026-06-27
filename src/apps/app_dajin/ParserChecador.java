package apps.app_dajin;

public class ParserChecador {

    public static boolean IS_PONTO_E_VIRGULA(Token tk){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor(";");
    }
    public static boolean IS_IDENTIFICADOR(Token tk){
        return tk.getTipo()==TokenTipo.IDENTIFICADOR ;
    }
    public static boolean IS_PALAVRA_CHAVE(Token tk,String palavra){
        return tk.getTipo()==TokenTipo.IDENTIFICADOR && tk.isValor(palavra);
    }
    public static boolean IS_DELIMITADOR(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR ;
    }
    public static boolean IS_OPERADOR(Token tk){
        return tk.getTipo()==TokenTipo.OPERADOR;
    }
    public static boolean IS_INTEIRO_LITERAL(Token tk){
        return tk.getTipo()==TokenTipo.INTEIRO_LITERAL;
    }
    public static boolean IS_TEXTO_LITERAL(Token tk){
        return tk.getTipo()==TokenTipo.TEXTO_LITERAL;
    }
    public static boolean IS_PARENTESES_ABRIR(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor("(");
    }
    public static boolean IS_PARENTESES_FECHAR(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor(")");
    }
    public static boolean IS_VIRGULA(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor(",");
    }
    public static boolean IS_CHAVES_ABRIR(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor("{");
    }
    public static boolean IS_CHAVES_FECHAR(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor("}");
    }
    public static boolean IS_OPERADOR_IGUAL(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("=");
    }
    public static boolean IS_ARROBA(Token tk ){
        return tk.getTipo()==TokenTipo.ARROBA ;
    }

    public static boolean IS_OPERADOR_SOMA(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("+");
    }
    public static boolean IS_OPERADOR_SUBTRACAO(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("-");
    }
    public static boolean IS_OPERADOR_MULT(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("*");
    }
    public static boolean IS_OPERADOR_DIV(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("/");
    }

    public static boolean IS_OPERADOR_MENOR(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("<");
    }
    public static boolean IS_OPERADOR_MAIOR(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor(">");
    }
    public static boolean IS_OPERADOR_IGUALDADE(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("==");
    }
    public static boolean IS_OPERADOR_DIFERENCIADO(Token tk ){
        return tk.getTipo()==TokenTipo.OPERADOR && tk.isValor("!=");
    }

    public static boolean IS_COLCHETES_ABRIR(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor("[");
    }
    public static boolean IS_COLCHETES_FECHAR(Token tk ){
        return tk.getTipo()==TokenTipo.DELIMITADOR && tk.isValor("]");
    }
}
