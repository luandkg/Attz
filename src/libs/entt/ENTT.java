package libs.entt;

import libs.arquivos.ds.DS;
import libs.arquivos.ds.DSItem;
import libs.bibliotecas.ODS;
import libs.bibliotecas.xlsx.Planilha;
import libs.bibliotecas.xlsx.PlanilhaLinha;
import libs.bibliotecas.xlsx.XLSX;
import libs.dkg.DKG;
import libs.dkg.DKGAtributo;
import libs.dkg.DKGObjeto;
import libs.luan.*;

import java.nio.charset.StandardCharsets;

public class ENTT {

    public static Entidade CRIAR(String nome, String valor) {
        Entidade e = new Entidade();
        e.at(nome, valor);
        return e;
    }

    public static Entidade CRIAR(String nome, int valor) {
        Entidade e = new Entidade();
        e.at(nome, valor);
        return e;
    }

    public static Entidade CRIAR(String nome, long valor) {
        Entidade e = new Entidade();
        e.at(nome, valor);
        return e;
    }

    public static Entidade CRIAR(String nome1, String valor1, String nome2, String valor2) {
        Entidade e = new Entidade();
        e.at(nome1, valor1);
        e.at(nome2, valor2);
        return e;
    }

    public static Entidade CRIAR(String nome1, String valor1, String nome2, String valor2, String nome3, String valor3) {
        Entidade e = new Entidade();
        e.at(nome1, valor1);
        e.at(nome2, valor2);
        e.at(nome3, valor3);
        return e;
    }

    public static Entidade CRIAR(String nome1, int valor1, String nome2, int valor2, String nome3, String valor3) {
        Entidade e = new Entidade();
        e.at(nome1, valor1);
        e.at(nome2, valor2);
        e.at(nome3, valor3);
        return e;
    }

    public static Entidade CRIAR(String nome1, String valor1, String nome2, String valor2, String nome3, String valor3, String nome4, String valor4) {
        Entidade e = new Entidade();
        e.at(nome1, valor1);
        e.at(nome2, valor2);
        e.at(nome3, valor3);
        e.at(nome4, valor4);
        return e;
    }

    public static Entidade CRIAR(String nome1, int valor1, String nome2, String valor2) {
        Entidade e = new Entidade();
        e.at(nome1, valor1);
        e.at(nome2, valor2);
        return e;
    }

    public static Entidade CRIAR(String nome1, String valor1, String nome2, int valor2) {
        Entidade e = new Entidade();
        e.at(nome1, valor1);
        e.at(nome2, valor2);
        return e;
    }

    public static Entidade GET_UNICO(Lista<Entidade> mEntts, String eNome, String eValor) {
        Entidade ret = null;

        for (Entidade e : mEntts) {
            if (e.at(eNome).contentEquals(eValor)) {
                ret = e;
                break;
            }
        }

        return ret;
    }

    public static boolean EXISTE(Lista<Entidade> mEntts, String eNome, String eValor) {

        boolean existe = false;

        for (Entidade existente : mEntts) {
            String existente_chave = existente.at(eNome);
            if (existente_chave.contentEquals(eValor)) {
                existe = true;
                break;
            }
        }

        return existe;
    }

    public static Entidade GET_SEMPRE(Lista<Entidade> mEntts, String eNome, String eValor) {
        Entidade ret = null;
        boolean enc = false;

        for (Entidade e : mEntts) {
            if (e.at(eNome).contentEquals(eValor)) {
                ret = e;
                enc = true;
                break;
            }
        }

        if (!enc) {
            ret = new Entidade();
            ret.at(eNome, eValor);
            mEntts.adicionar(ret);
        }
        return ret;
    }

    public static Entidade GET_SEMPRE(Lista<Entidade> mEntts, String eNome, int eValor) {
        Entidade ret = null;
        boolean enc = false;

        for (Entidade e : mEntts) {
            if (e.atInt(eNome) == (eValor)) {
                ret = e;
                enc = true;
                break;
            }
        }

        if (!enc) {
            ret = new Entidade();
            ret.at(eNome, eValor);
            mEntts.adicionar(ret);
        }
        return ret;
    }

    public static Entidade GET_SEMPRE(Lista<Entidade> mEntts, String eNome1, int eValor1, String eNome2, int eValor2) {
        Entidade ret = null;
        boolean enc = false;

        for (Entidade e : mEntts) {
            if (e.atInt(eNome1) == (eValor1) && e.atInt(eNome2) == (eValor2)) {
                ret = e;
                enc = true;
                break;
            }
        }

        if (!enc) {
            ret = new Entidade();
            ret.at(eNome1, eValor1);
            ret.at(eNome2, eValor2);
            mEntts.adicionar(ret);
        }
        return ret;
    }

    public static Entidade GET_SEMPRE_TENTATIVA(Lista<Entidade> mEntts, String eNome, int eValor) {
        Entidade ret = null;
        boolean enc = false;

        for (Entidade e : mEntts) {
            if (e.atIntOuPadrao(eNome, 0) == (eValor)) {
                ret = e;
                enc = true;
                break;
            }
        }

        if (!enc) {
            ret = new Entidade();
            ret.at(eNome, eValor);
            mEntts.adicionar(ret);
        }
        return ret;
    }

    public static Opcional<Entidade> GET_OPCIONAL(Lista<Entidade> mEntts, String eNome, String eValor) {

        for (Entidade e : mEntts) {
            if (e.at(eNome).contentEquals(eValor)) {
                return Opcional.OK(e);
            }
        }

        return Opcional.CANCEL();

    }


    public static int GET_INTEIRO_MAIOR(Lista<Entidade> mEntts, String eNome) {
        int maior = 0;

        for (Entidade e : mEntts) {
            if (e.atIntOuPadrao(eNome, 0) > maior) {
                maior = e.atInt(eNome);
            }
        }

        return maior;
    }

    public static int GET_INTEIRO_MENOR(Lista<Entidade> mEntts, String eNome) {
        int menor = 0;

        boolean is_primeiro = true;

        for (Entidade e : mEntts) {
            if (is_primeiro) {
                menor = e.atInt(eNome);
                is_primeiro = false;
            } else {
                if (e.atIntOuPadrao(eNome, 0) < menor) {
                    menor = e.atInt(eNome);
                }
            }

        }

        return menor;
    }


    public static Entidade GET_ITEM_MAIOR(Lista<Entidade> mEntts, String eNome) {
        Entidade ret = GET_PRIMEIRO(mEntts);
        int maior = ret.atInt(eNome);

        for (Entidade e : mEntts) {
            if (e.atIntOuPadrao(eNome, 0) > maior) {
                maior = e.atInt(eNome);
                ret = e;
            }
        }

        return ret;
    }

    public static Entidade GET_ITEM_MENOR(Lista<Entidade> mEntts, String eNome) {
        Entidade ret = GET_PRIMEIRO(mEntts);
        int menor = ret.atInt(eNome);

        for (Entidade e : mEntts) {
            if (e.atIntOuPadrao(eNome, 0) < menor) {
                menor = e.atInt(eNome);
                ret = e;
            }
        }

        return ret;
    }


    public static Lista<Entidade> GET_ITEM_MAIOR_COM_REPETICAO(Lista<Entidade> mEntts, String eNome) {
        Entidade ret = GET_PRIMEIRO(mEntts);
        int maior = ret.atInt(eNome);

        Lista<Entidade> repeticao = new Lista<Entidade>();


        for (Entidade e : mEntts) {
            if (e.atIntOuPadrao(eNome, 0) > maior) {
                maior = e.atInt(eNome);
                ret = e;
                repeticao.limpar();
                repeticao.adicionar(e);
            } else if (e.atIntOuPadrao(eNome, 0) == maior) {
                repeticao.adicionar(e);
            }
        }

        return repeticao;
    }

    public static Lista<Entidade> GET_ITEM_MENOR_COM_REPETICAO(Lista<Entidade> mEntts, String eNome) {
        Entidade ret = GET_PRIMEIRO(mEntts);
        int menor = ret.atInt(eNome);

        Lista<Entidade> repeticao = new Lista<Entidade>();


        for (Entidade e : mEntts) {
            if (e.atIntOuPadrao(eNome, 0) < menor) {
                menor = e.atInt(eNome);
                ret = e;
                repeticao.limpar();
                repeticao.adicionar(e);
            } else if (e.atIntOuPadrao(eNome, 0) == menor) {
                repeticao.adicionar(e);
            }
        }

        return repeticao;
    }


    public static double GET_DOUBLE_MAIOR(Lista<Entidade> mEntts, String eNome) {
        double maior = 0;

        for (Entidade e : mEntts) {
            if (e.atDoubleOuPadrao(eNome, 0) > maior) {
                maior = e.atDouble(eNome);
            }
        }

        return maior;
    }

    public static double GET_DOUBLE_MENOR(Lista<Entidade> mEntts, String eNome) {
        double menor = 0;

        boolean is_primeiro = true;

        for (Entidade e : mEntts) {
            if (is_primeiro) {
                menor = e.atDouble(eNome);
                is_primeiro = false;
            } else {
                if (e.atDoubleOuPadrao(eNome, 0) < menor) {
                    menor = e.atDouble(eNome);
                }
            }

        }

        return menor;
    }


    public static Lista<Entidade> GET_ATRIBUTOS(Entidade entidade) {

        Unico<String> atributos = new Unico<String>(Strings.IGUALAVEL());

        for (Tag tag : entidade.tags()) {
            atributos.item(tag.getNome());
        }


        Lista<Entidade> e_atts = new Lista<Entidade>();
        for (String item : atributos) {
            e_atts.adicionar(ENTT.CRIAR("Nome", item));
        }

        return e_atts;
    }

    public static Lista<Entidade> GET_ATRIBUTOS(Lista<Entidade> entidades) {

        Unico<String> atributos = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade e : entidades) {
            for (Tag tag : e.tags()) {
                atributos.item(tag.getNome());
            }
        }

        Lista<Entidade> e_atts = new Lista<Entidade>();
        for (String item : atributos) {
            e_atts.adicionar(ENTT.CRIAR("Nome", item));
        }

        return e_atts;
    }

    public static Lista<Entidade> GET_ATRIBUTOS_COM_TAMANHO(Lista<Entidade> entidades) {

        Lista<Entidade> e_atts = new Lista<Entidade>();

        for (Entidade e : entidades) {
            for (Tag tag : e.tags()) {
                Entidade a_att = ENTT.GET_SEMPRE(e_atts, "Nome", tag.getNome());
                if (tag.getValor().length() > a_att.atIntOuPadrao("Tamanho", 0)) {
                    a_att.at("Tamanho", tag.getValor().length());
                }
            }
        }


        return e_atts;
    }

    public static Lista<Entidade> GET_ATRIBUTOS_E_SEQUENCIAIS(Lista<Entidade> entidades) {

        Unico<String> atributos = new Unico<String>(Strings.IGUALAVEL());
        Unico<String> atributos_comuns = new Unico<String>(Strings.IGUALAVEL());
        Unico<String> sequenciais = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade e : entidades) {
            for (Tag tag : e.tags()) {
                atributos.item(tag.getNome());

                String nome_parte_textual = Strings.getParteTextual(tag.getNome());

                if (nome_parte_textual.length() == tag.getNome().length()) {
                    atributos_comuns.item(tag.getNome());
                } else {
                    sequenciais.item(nome_parte_textual);
                }

            }
        }

        Lista<Entidade> e_atts = new Lista<Entidade>();

        if (atributos.getQuantidade() == atributos_comuns.getQuantidade() || (atributos.getQuantidade() == (atributos_comuns.getQuantidade() + sequenciais.getQuantidade()))) {

            for (String item : atributos) {
                Entidade e = ENTT.CRIAR("Atributo", "Simples");
                e.at("Nome", item);
                e_atts.adicionar(e);
            }

        } else {

            for (String item : atributos_comuns) {
                Entidade e = ENTT.CRIAR("Atributo", "Simples");
                e.at("Nome", item);
                e_atts.adicionar(e);
            }

            for (String item : sequenciais) {

                Entidade e = ENTT.CRIAR("Atributo", "Sequencial");
                e.at("Nome", item);

                boolean primeiro = true;

                for (String att : atributos) {

                    String nome_parte_textual = Strings.getParteTextual(att);

                    if (Strings.isIgual(item, nome_parte_textual)) {

                        int sequencia = Strings.getParteNumericaAposTextual(att);

                        if (primeiro) {
                            primeiro = false;

                            e.at("Menor", sequencia);
                            e.at("Maior", sequencia);


                        } else {
                            if (sequencia < e.atInt("Menor")) {
                                e.at("Menor", sequencia);
                            }

                            if (sequencia > e.atInt("Maior")) {
                                e.at("Maior", sequencia);
                            }
                        }


                    }

                }

                e_atts.adicionar(e);
            }

        }


        return e_atts;
    }


    public static Lista<String> GET_ATRIBUTOS_NOMES(Lista<Entidade> entidades) {

        Unico<String> atributos = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade e : entidades) {
            for (Tag tag : e.tags()) {
                atributos.item(tag.getNome());
            }
        }


        return atributos.toLista();
    }

    public static Lista<String> GET_ATRIBUTOS_NOMES(Entidade entidades) {

        Unico<String> atributos = new Unico<String>(Strings.IGUALAVEL());

        for (Tag tag : entidades.tags()) {
            atributos.item(tag.getNome());
        }


        return atributos.toLista();
    }

    public static Lista<Entidade> GET_CAMPOS(Lista<Entidade> mEntts, Lista<String> campos) {
        Lista<Entidade> ret = new Lista<Entidade>();

        for (Entidade e : mEntts) {
            Entidade nova = new Entidade();
            for (String campo : campos) {
                nova.at(campo, e.at(campo));
            }
            ret.adicionar(nova);
        }

        return ret;
    }

    public static Entidade ATRIBUTO_INTEIRO_MENOR(Lista<Entidade> mEntts, String eNome) {
        Entidade ret = null;

        int menor = mEntts.get(0).atInt(eNome);

        for (Entidade e : mEntts) {
            if (e.atInt(eNome) < menor) {
                ret = e;
                menor = e.atInt(eNome);
            }
        }

        return ret;
    }

    public static Entidade ATRIBUTO_INTEIRO_MAIOR(Lista<Entidade> mEntts, String eNome) {
        Entidade ret = null;

        int maior = mEntts.get(0).atInt(eNome);

        for (Entidade e : mEntts) {
            if (e.atInt(eNome) > maior) {
                ret = e;
                maior = e.atInt(eNome);
            }
        }

        return ret;
    }


    public static void REMOVER_SE(Lista<Entidade> mEntts, String eNome, String eValor) {

        Lista<Entidade> remover = new Lista<Entidade>();

        for (Entidade e : mEntts) {
            if (e.at(eNome).contentEquals(eValor)) {
                remover.adicionar(e);
            }
        }

        for (Entidade e : remover) {
            mEntts.remover(e);
            // fmt.print("Removendo -->:> ");
        }

    }

    public static void REMOVER_SE(Lista<Entidade> mEntts, String eNome, int eValor) {

        Lista<Entidade> remover = new Lista<Entidade>();

        for (Entidade e : mEntts) {
            if (e.atInt(eNome) == (eValor)) {
                remover.adicionar(e);
            }
        }

        for (Entidade e : remover) {
            mEntts.remover(e);
            // fmt.print("Removendo -->:> ");
        }

    }

    public static void SEQUENCIAR(Lista<Entidade> mEntts, String eNome, int eInicio) {

        int valor = eInicio;

        for (Entidade e : mEntts) {
            e.at(eNome, valor);
            valor += 1;
        }


    }


    public static void ATRIBUTO_TORNAR_PRIMEIRO(Lista<Entidade> mEntts, String eNome) {

        for (Entidade e : mEntts) {
            e.tornar_primeiro(eNome);
        }

    }

    public static void ATRIBUTO_TORNAR_ULTIMO(Lista<Entidade> mEntts, String eNome) {

        for (Entidade e : mEntts) {
            e.tornar_ultimo(eNome);
        }

    }

    public static void ATRIBUTO_DEPOIS_DE(Lista<Entidade> mEntts, String eNomeAntes, String eNovoAtt) {

        for (Entidade e : mEntts) {
            e.atributo_depois(eNomeAntes, eNovoAtt);
        }

    }
    // FUNCOES ESPECIAIS

    public static int ATRIBUTO_SOMAR(Lista<Entidade> mEntts, String nome) {
        int somatorio = 0;

        for (Entidade e : mEntts) {
            somatorio += e.atInt(nome);
        }

        return somatorio;
    }

    public static int ATRIBUTO_SOMAR_E_AGREGAR(Lista<Entidade> mEntts, String a1, String a2) {
        int t1 = ATRIBUTO_SOMAR(mEntts, a1);
        int t2 = ATRIBUTO_SOMAR(mEntts, a2);

        return t1 + t2;
    }

    public static void EXPORTAR_DKG(Lista<Entidade> mEntts, String arquivo) {

        DKG documento = new DKG();
        DKGObjeto raiz = documento.unicoObjeto("ENTT");

        for (Entidade e : mEntts) {

            DKGObjeto objeto = raiz.criarObjeto("E");
            for (Tag tag : e.tags()) {
                objeto.identifique(tag.getNome(), tag.getValor());
            }

        }

        documento.salvar(arquivo);

    }

    public static Lista<Entidade> CRIAR_DE_XLSX(XLSX eXLSX) {

        Lista<Entidade> planilhas = new Lista<Entidade>();

        for (Planilha planilha : eXLSX.getPlanilhas()) {

            Entidade e_planilha = new Entidade();
            e_planilha.at("Titulo", planilha.getTitulo());
            e_planilha.atInt("Linhas", planilha.getLinhas().getQuantidade());
            e_planilha.atInt("Colunas", planilha.maxColunas());

            for (PlanilhaLinha linha : planilha.getLinhas()) {
                Entidade e_linha = new Entidade();

                int coluna_id = 0;

                for (String coluna_valor : linha.getColunas()) {
                    e_linha.at(String.valueOf(coluna_id), coluna_valor);
                    coluna_id += 1;
                }

                e_planilha.getEntidades().adicionar(e_linha);
            }

            planilhas.adicionar(e_planilha);

        }

        return planilhas;
    }

    public static Lista<Entidade> CRIAR_DE_ODS(ODS eODS) {

        Lista<Entidade> planilhas = new Lista<Entidade>();

        for (Planilha planilha : eODS.getPlanilhas()) {

            Entidade e_planilha = new Entidade();
            e_planilha.at("Titulo", planilha.getTitulo());
            e_planilha.atInt("Linhas", planilha.getLinhas().getQuantidade());
            e_planilha.atInt("Colunas", planilha.maxColunas());

            for (PlanilhaLinha linha : planilha.getLinhas()) {
                Entidade e_linha = new Entidade();

                int coluna_id = 0;

                for (String coluna_valor : linha.getColunas()) {
                    e_linha.at(String.valueOf(coluna_id), coluna_valor);
                    coluna_id += 1;
                }

                e_planilha.getEntidades().adicionar(e_linha);
            }

            planilhas.adicionar(e_planilha);

        }

        return planilhas;
    }


    public static Lista<Entidade> XLSX_FOLHA(Lista<Entidade> dados, String nome) {
        return ENTT.GET_SEMPRE(dados, "Titulo", nome).getEntidades();
    }

    public static void EXIBIR_TABELA(Lista<Entidade> objetos) {

        Lista<Entidade> colunas = new Lista<Entidade>();

        for (Entidade obj : objetos) {

            for (Tag att : obj.tags()) {
                int tamanho = att.getValor().length();
                Entidade obj_coluna = CRIAR_UNICO(colunas, "Nome", att.getNome());

                if (obj_coluna.atIntOuPadrao("Tamanho", 0) < tamanho) {
                    obj_coluna.at("Tamanho", tamanho);
                }

                if (obj_coluna.atIntOuPadrao("Tamanho", 0) < att.getNome().length()) {
                    obj_coluna.at("Tamanho", att.getNome().length());
                }

            }
        }

        int tracos = 0;
        for (Entidade obj : colunas) {
            int tt = obj.atInt("Tamanho") + 5 + 2;
            tracos += tt;
        }
        tracos += 2;

        fmt.print(fmt.repetir("-", tracos));

        String cabecalho = "";

        for (Indexado<Entidade> obj : Indexamento.indexe(colunas)) {
            int tt = obj.get().atIntOuPadrao("Tamanho", 0) + 5;
            if (obj.index() == 0) {
                cabecalho += "|" + fmt.espacar_depois(obj.get().at("Nome"), tt);
            } else {
                cabecalho += " |" + fmt.espacar_depois(obj.get().at("Nome"), tt);
            }
        }


        fmt.print("{}", cabecalho + "  |");
        fmt.print(fmt.repetir("-", tracos));

        for (Indexado<Entidade> obj : Indexamento.indexe(objetos)) {

            String linha = "";

            for (Indexado<Entidade> coluna : Indexamento.indexe(colunas)) {
                int tt = coluna.get().atInt("Tamanho") + 5;

                if (coluna.index() == 0) {
                    linha += "|" + fmt.espacar_depois(obj.get().at(coluna.get().at("Nome")), tt);
                } else {
                    linha += " |" + fmt.espacar_depois(obj.get().at(coluna.get().at("Nome")), tt);
                }

            }

            fmt.print("{}", linha + "  |");

        }
        fmt.print(fmt.repetir("-", tracos));

    }

    public static void EXIBIR_TABELA_PREFIXO(String prefixo, Lista<Entidade> objetos) {

        Lista<Entidade> colunas = new Lista<Entidade>();

        for (Entidade obj : objetos) {

            for (Tag att : obj.tags()) {
                int tamanho = att.getValor().length();
                Entidade obj_coluna = CRIAR_UNICO(colunas, "Nome", att.getNome());

                if (obj_coluna.atIntOuPadrao("Tamanho", 0) < tamanho) {
                    obj_coluna.at("Tamanho", tamanho);
                }

                if (obj_coluna.atIntOuPadrao("Tamanho", 0) < att.getNome().length()) {
                    obj_coluna.at("Tamanho", att.getNome().length());
                }

            }
        }

        int tracos = 0;
        for (Entidade obj : colunas) {
            int tt = obj.atInt("Tamanho") + 5 + 2;
            tracos += tt;
        }
        tracos += 2;

        fmt.print(prefixo + fmt.repetir("-", tracos));

        String cabecalho = "";

        for (Indexado<Entidade> obj : Indexamento.indexe(colunas)) {
            int tt = obj.get().atIntOuPadrao("Tamanho", 0) + 5;
            if (obj.index() == 0) {
                cabecalho += "|" + fmt.espacar_depois(obj.get().at("Nome"), tt);
            } else {
                cabecalho += " |" + fmt.espacar_depois(obj.get().at("Nome"), tt);
            }
        }


        fmt.print(prefixo + "{}", cabecalho + " |");
        fmt.print(prefixo + fmt.repetir("-", tracos));

        for (Indexado<Entidade> obj : Indexamento.indexe(objetos)) {

            String linha = "";

            for (Indexado<Entidade> coluna : Indexamento.indexe(colunas)) {
                int tt = coluna.get().atInt("Tamanho") + 5;

                if (coluna.index() == 0) {
                    linha += "|" + fmt.espacar_depois(obj.get().at(coluna.get().at("Nome")), tt);
                } else {
                    linha += " |" + fmt.espacar_depois(obj.get().at(coluna.get().at("Nome")), tt);
                }

            }

            fmt.print(prefixo + "{}", linha + " |");

        }
        fmt.print(prefixo + fmt.repetir("-", tracos));

    }

    public static void EXIBIR_TABELA_COM_TITULO(Lista<Entidade> objetos, String sTitulo) {

        Lista<Entidade> colunas = new Lista<Entidade>();

        for (Entidade obj : objetos) {

            for (Tag att : obj.tags()) {
                int tamanho = att.getValor().length();
                Entidade obj_coluna = CRIAR_UNICO(colunas, "Nome", att.getNome());

                if (obj_coluna.atIntOuPadrao("Tamanho", 0) < tamanho) {
                    obj_coluna.at("Tamanho", tamanho);
                }

                if (obj_coluna.atIntOuPadrao("Tamanho", 0) < att.getNome().length()) {
                    obj_coluna.at("Tamanho", att.getNome().length());
                }

            }
        }

        int tracos = 0;
        for (Entidade obj : colunas) {
            int tt = obj.atInt("Tamanho") + 5 + 2;
            tracos += tt;
        }
        tracos += 2;

        int tracos_metade = (tracos / 2) - (sTitulo.length() / 2);

        fmt.print(fmt.repetir("-", tracos));

        int contando_todos = tracos_metade + sTitulo.length() + tracos_metade;
        fmt.print("|{}{}{}|", fmt.repetir(" ", tracos_metade - 1), sTitulo, fmt.repetir(" ", tracos_metade - 1));


        fmt.print(fmt.repetir("-", tracos));


        String cabecalho = "";

        for (Indexado<Entidade> obj : Indexamento.indexe(colunas)) {
            int tt = obj.get().atIntOuPadrao("Tamanho", 0) + 5;
            if (obj.index() == 0) {
                cabecalho += "|" + fmt.espacar_depois(obj.get().at("Nome"), tt);
            } else {
                cabecalho += " |" + fmt.espacar_depois(obj.get().at("Nome"), tt);
            }
        }


        fmt.print("{}", cabecalho + " |");
        fmt.print(fmt.repetir("-", tracos));

        for (Indexado<Entidade> obj : Indexamento.indexe(objetos)) {

            String linha = "";

            for (Indexado<Entidade> coluna : Indexamento.indexe(colunas)) {
                int tt = coluna.get().atInt("Tamanho") + 5;

                if (coluna.index() == 0) {
                    linha += "|" + fmt.espacar_depois(obj.get().at(coluna.get().at("Nome")), tt);
                } else {
                    linha += " |" + fmt.espacar_depois(obj.get().at(coluna.get().at("Nome")), tt);
                }

            }

            fmt.print("{}", linha + " |");

        }
        fmt.print(fmt.repetir("-", tracos));

    }


    public static Entidade CRIAR_UNICO(Lista<Entidade> entts, String eID, String eValor) {

        Entidade ret = null;
        boolean existe = false;
        for (Entidade objeto_corrente : entts) {
            if (objeto_corrente.at(eID).contentEquals(eValor)) {
                existe = true;
                ret = objeto_corrente;
                break;
            }
        }

        if (!existe) {
            Entidade objeto_novo = new Entidade();
            objeto_novo.at(eID, eValor);
            entts.adicionar(objeto_novo);
            ret = objeto_novo;
        }

        return ret;
    }

    public static Lista<DKGObjeto> ENTIDADE_TO_OBJETO(Lista<Entidade> entts) {
        Lista<DKGObjeto> objetos = new Lista<DKGObjeto>();

        for (Entidade e : entts) {
            DKGObjeto obj = new DKGObjeto("Item");

            for (Tag tag : e.tags()) {
                obj.identifique(tag.getNome(), tag.getValor());
            }

            objetos.adicionar(obj);
        }

        return objetos;
    }


    public static Lista<String> FILTRAR_UNICOS(Lista<Entidade> entts, String campo) {

        Unico<String> valores = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade e : entts) {
            valores.item(e.at(campo));
        }

        return valores.toLista();
    }

    public static Lista<Entidade> FILTRAR_UNICOS_EM_ENTIDADES(Lista<Entidade> entts, String campo) {

        Unico<String> valores = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade e : entts) {
            valores.item(e.at(campo));
        }

        Lista<Entidade> unicos_entts = CRIAR_LISTA();
        for (String item : valores.toLista()) {
            Entidade novo = CRIAR_EM(unicos_entts);
            novo.at(campo, item);
        }

        return unicos_entts;
    }

    public static Lista<Entidade> COLETAR(Lista<Entidade> entts, String campo_nome, String campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.at(campo_nome).contentEquals(campo_valor)) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> COLETAR_E_COLETAR(Lista<Entidade> entts, String campo_nome1, String campo_valor1, String campo_nome2, String campo_valor2) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.at(campo_nome1).contentEquals(campo_valor1) && e.at(campo_nome2).contentEquals(campo_valor2)) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> COLETAR_VAZIO(Lista<Entidade> entts, String campo_nome) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.isVazio(campo_nome)) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }


    public static int CONTAGEM(Lista<Entidade> entts, String campo_nome, String campo_valor) {
        int contagem = 0;

        for (Entidade e : entts) {
            if (e.at(campo_nome).contentEquals(campo_valor)) {
                contagem += 1;
            }
        }


        return contagem;
    }

    public static int CONTAGEM(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        int contagem = 0;

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) == campo_valor) {
                contagem += 1;
            }
        }


        return contagem;
    }


    public static int CONTAGEM_UNICOS(Lista<Entidade> entts, String campo_nome) {
        return FILTRAR_UNICOS(entts, campo_nome).getQuantidade();
    }


    public static Lista<Entidade> COLETAR_DIFERENTE_DE(Lista<Entidade> entts, String campo_nome, String campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (!e.at(campo_nome).contentEquals(campo_valor)) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> COLETAR(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) == (campo_valor)) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> AGRUPAR(Lista<Entidade> entts, String campo) {

        Lista<String> grupos = FILTRAR_UNICOS(entts, campo);
        Lista<Entidade> entts_por_grupo = new Lista<Entidade>();

        for (String grupo : grupos) {
            Entidade e_grupo = new Entidade();
            e_grupo.at(campo, grupo);

            for (Entidade e : COLETAR(entts, campo, grupo)) {
                e_grupo.getEntidades().adicionar(e);
            }

            entts_por_grupo.adicionar(e_grupo);

        }

        return entts_por_grupo;
    }


    public static Lista<Entidade> AGRUPAR_E_ORDENAR(Lista<Entidade> entts, String campo) {
        return ENTT.ORDENAR_TEXTO(ENTT.AGRUPAR(entts, campo), campo);
    }


    public static Lista<Entidade> ORDENAR_TEXTO(Lista<Entidade> entts, String campo) {


        int n = entts.getQuantidade();
        Entidade temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entts.get(j - 1).at(campo).compareTo(entts.get(j).at(campo)) > 0) {
                    temp = entts.get(j - 1);
                    entts.set(j - 1, entts.get(j));
                    entts.set(j, temp);

                }

            }
        }

        return entts;
    }

    public static Lista<Entidade> ORDENAR_INTEIRO(Lista<Entidade> entts, String campo) {


        int n = entts.getQuantidade();
        Entidade temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entts.get(j - 1).atInt(campo) > (entts.get(j).atInt(campo))) {
                    temp = entts.get(j - 1);
                    entts.set(j - 1, entts.get(j));
                    entts.set(j, temp);

                }

            }
        }

        return entts;
    }

    public static Lista<Entidade> ORDENAR_COM_ORDENADOR(Lista<Entidade> entts, String campo,Ordenavel<String> ordenavel) {


        int n = entts.getQuantidade();
        Entidade temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if ( ordenavel.emOrdem(  entts.get(j - 1).at(campo) , entts.get(j).at(campo)) == Ordenavel.MAIOR) {
                    temp = entts.get(j - 1);
                    entts.set(j - 1, entts.get(j));
                    entts.set(j, temp);

                }

            }
        }

        return entts;
    }

    public static Lista<Entidade> ORDENAR_INTEIRO_DECRESCENTE(Lista<Entidade> entts, String campo) {


        int n = entts.getQuantidade();
        Entidade temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entts.get(j - 1).atInt(campo) < (entts.get(j).atInt(campo))) {
                    temp = entts.get(j - 1);
                    entts.set(j - 1, entts.get(j));
                    entts.set(j, temp);

                }

            }
        }

        return entts;
    }

    public static Lista<Entidade> ORDENAR_LONG(Lista<Entidade> entts, String campo) {


        int n = entts.getQuantidade();
        Entidade temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entts.get(j - 1).atLong(campo) > (entts.get(j).atLong(campo))) {
                    temp = entts.get(j - 1);
                    entts.set(j - 1, entts.get(j));
                    entts.set(j, temp);

                }

            }
        }

        return entts;
    }


    public static Lista<Entidade> ORDENAR_DOUBLE_DECRESCENTE(Lista<Entidade> entts, String campo) {


        int n = entts.getQuantidade();
        Entidade temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entts.get(j - 1).atDouble(campo) < (entts.get(j).atDouble(campo))) {
                    temp = entts.get(j - 1);
                    entts.set(j - 1, entts.get(j));
                    entts.set(j, temp);

                }

            }
        }

        return entts;
    }

    public static Lista<Entidade> ORDENAR_DOUBLE(Lista<Entidade> entts, String campo) {


        int n = entts.getQuantidade();
        Entidade temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (entts.get(j - 1).atDouble(campo) > (entts.get(j).atDouble(campo))) {
                    temp = entts.get(j - 1);
                    entts.set(j - 1, entts.get(j));
                    entts.set(j, temp);

                }

            }
        }

        return entts;
    }


    public static Entidade GET_UM(Lista<Entidade> entts, String campo_nome, String campo_valor) {

        Entidade buscado = null;

        for (Entidade e : entts) {
            if (e.at(campo_nome).contentEquals(campo_valor)) {
                buscado = e;
                break;
            }
        }

        return buscado;
    }

    public static Entidade GET_UM(Lista<Entidade> entts, String campo_nome, int campo_valor) {

        Entidade buscado = null;

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) == (campo_valor)) {
                buscado = e;
                break;
            }
        }

        return buscado;
    }


    public static void EXIBIR_DETALHADO(Entidade entidade) {

        int nome_tamanho = 0;

        for (Tag a : entidade.tags()) {
            if (a.getNome().length() > nome_tamanho) {
                nome_tamanho = a.getNome().length();
            }
        }

        fmt.print("");

        for (Tag a : entidade.tags()) {
            fmt.print("{} = {}", fmt.espacar_depois(a.getNome(), nome_tamanho + 5), a.getValor());
        }

        ENTT.EXIBIR_TABELA(entidade.getEntidades());

    }


    public static void GUARDAR(Lista<Entidade> mEntts, String arquivo) {

        DS.limpar(arquivo);

        for (Indexado<Entidade> e : Indexamento.indexe(mEntts)) {
            DS.adicionar(arquivo, String.valueOf(e.index()), TO_DOCUMENTO(e.get()));
        }

    }

    public static void GUARDAR_MAIS_UM(Entidade e, String arquivo) {

        int quantidade = DS.contar(arquivo);
        DS.adicionar(arquivo, String.valueOf(quantidade), TO_DOCUMENTO(e));

    }

    public static String GUARDAR_DOCUMENTO(Lista<Entidade> mEntts) {

        TextoDocumento texto = new TextoDocumento();

        for (Indexado<Entidade> e : Indexamento.indexe(mEntts)) {
            texto.adicionarLinha(TO_DOCUMENTO(e.get()));
        }

        return texto.toDocumento();
    }


    public static Lista<Entidade> ABRIR(String arquivo) {

        Lista<Entidade> lista = new Lista<Entidade>();

        for (DSItem item : DS.ler_todos(arquivo)) {
            lista.adicionar(PARSER_ENTIDADE(item.getTexto()));
        }


        return lista;
    }

    public static Entidade PARSER_ENTIDADE(String dados) {
        Entidade e = ENTTBinario.PARSER_TO_OBJETO(dados);
        return e;
    }

    public static String TO_DOCUMENTO(Entidade e) {
        return ENTTBinario.TO_DOCUMENTO(e);
    }

    public static String TO_DOCUMENTO(Lista<Entidade> lista) {

        DKGObjeto raiz = new DKGObjeto("ENTT");

        int pos = 0;

        for (Entidade e : lista) {
            DKGObjeto objeto = new DKGObjeto("Item");
            for (Tag att : e.tags()) {
                objeto.identifique(att.getNome(), att.getValor());
            }
            interno(e, objeto);
            raiz.adicionarObjeto(objeto);
            pos += 1;
        }

        return raiz.toDocumento();
    }


    public static byte[] TO_DOCUMENTO_BYTES(Lista<Entidade> lista) {
        return ENTT.TO_DOCUMENTO(lista).getBytes(StandardCharsets.UTF_8);
    }

    public static void interno(Entidade entidadePai, DKGObjeto objetoPai) {

        for (Entidade e : entidadePai.getEntidades()) {
            DKGObjeto objeto = objetoPai.criarObjeto("Item");
            for (Tag att : e.tags()) {
                objeto.identifique(att.getNome(), att.getValor());
            }
            interno(e, objeto);
        }

    }

    public static Lista<Entidade> GET_AMOSTRA_PEQUENA(Lista<Entidade> entts) {
        Lista<Entidade> amostra = new Lista<Entidade>();

        Lista<Entidade> entts_copia = entts.getCopia();

        for (int s = 0; s < 10; s++) {

            if (entts_copia.getQuantidade() > 1) {
                Entidade escolhida = ENTT.GET_ALEATORIO(entts_copia);
                amostra.adicionar(escolhida);
                entts_copia.remover(escolhida);
            }

        }

        return amostra;
    }

    public static Lista<Entidade> GET_AMOSTRA(Lista<Entidade> entts) {
        Lista<Entidade> amostra = new Lista<Entidade>();

        Lista<Entidade> entts_copia = entts.getCopia();

        for (int s = 0; s < 100; s++) {

            if (entts_copia.getQuantidade() > 1) {
                Entidade escolhida = ENTT.GET_ALEATORIO(entts_copia);
                amostra.adicionar(escolhida);
                entts_copia.remover(escolhida);
            }

        }

        return amostra;
    }

    public static Entidade GET_ALEATORIO(Lista<Entidade> entts) {

        return entts.get(Aleatorio.aleatorio(entts.getQuantidade()));

    }

    public static Lista<Entidade> GET_FATIA_INICIO(Lista<Entidade> entts) {
        Lista<Entidade> amostra = new Lista<Entidade>();

        Lista<Entidade> entts_copia = entts.getCopia();

        for (int s = 0; s < 100; s++) {

            if (entts_copia.getQuantidade() > 1) {
                Entidade escolhida = entts_copia.get(s);
                amostra.adicionar(escolhida);
                entts_copia.remover(escolhida);
            }

        }

        return amostra;
    }

    public static Lista<Entidade> GET_FATIA_INICIO_PEQUENO(Lista<Entidade> entts) {
        Lista<Entidade> amostra = new Lista<Entidade>();


        for (int s = 0; s < 10; s++) {
            Entidade escolhida = entts.get(s);
            amostra.adicionar(escolhida);
        }

        return amostra;
    }

    public static int CONTAGEM(Lista<Entidade> entts) {
        return entts.getQuantidade();
    }

    public static Lista<Entidade> CRIAR_LISTA() {
        return new Lista<Entidade>();
    }


    public static double ATRIBUTOS_VARIOS_ANALISE_DOUBLE_MENOR(Entidade e, String prefixo, int inicio, int fim) {

        double menor = e.atDouble(prefixo + inicio);
        boolean tem = true;

        for (int i = inicio; i <= fim; i++) {
            double valor = e.atDouble(prefixo + i);
            if (valor < menor) {
                menor = valor;
                tem = true;
            }
        }


        if (tem) {
            return menor;
        } else {
            throw new RuntimeException("ERRO :: ATRIBUTOS_VARIOS_ANALISE_DOUBLE_MENOR");
        }

    }

    public static double ATRIBUTOS_VARIOS_ANALISE_DOUBLE_MAIOR(Entidade e, String prefixo, int inicio, int fim) {

        double maior = e.atDouble(prefixo + inicio);
        boolean tem = true;

        for (int i = inicio; i <= fim; i++) {
            double valor = e.atDouble(prefixo + i);
            if (valor > maior) {
                maior = valor;
                tem = true;
            }
        }


        if (tem) {
            return maior;
        } else {
            throw new RuntimeException("ERRO :: ATRIBUTOS_VARIOS_ANALISE_DOUBLE_MAIOR");
        }

    }


    public static double ATRIBUTOS_VARIOS_ANALISE_DOUBLE_MEDIA(Entidade e, String prefixo, int inicio, int fim) {


        boolean tem = true;

        double somatorio = 0;
        int quantidade = 0;

        for (int i = inicio; i <= fim; i++) {
            double valor = e.atDouble(prefixo + i);
            somatorio += valor;
            quantidade += 1;
            tem = true;
        }


        if (tem) {
            return somatorio / quantidade;
        } else {
            throw new RuntimeException("ERRO :: ATRIBUTOS_VARIOS_ANALISE_DOUBLE_MAIOR");
        }

    }

    public static Entidade GET_PRIMEIRO(Lista<Entidade> entts) {
        return entts.get(0);
    }

    public static Entidade GET_ULTIMO(Lista<Entidade> entts) {
        return entts.get(entts.getQuantidade() - 1);
    }


    public static Lista<Entidade> DISPERSAO(Lista<Entidade> entts, String atributo) {

        Lista<Entidade> dispersao = CRIAR_LISTA();

        for (String valor : FILTRAR_UNICOS(entts, atributo)) {

            Entidade e = GET_SEMPRE(dispersao, atributo, valor);
            e.at("Quantidade", COLETAR(entts, atributo, valor).getQuantidade());
        }

        return dispersao;
    }

    public static Lista<Entidade> DISPERSAO_SOMATORIO(Lista<Entidade> entts, String atributo, String atributoSomatorio) {

        Lista<Entidade> dispersao = CRIAR_LISTA();

        for (String valor : FILTRAR_UNICOS(entts, atributo)) {

            Entidade e = GET_SEMPRE(dispersao, atributo, valor);
            Lista<Entidade> subdados = COLETAR(entts, atributo, valor);

            e.at("Quantidade", ATRIBUTO_SOMAR(subdados, atributoSomatorio));
        }

        return dispersao;
    }


    public static void DEFINIR(Lista<Entidade> entts, String att_nome, String att_valor) {
        for (Entidade e : entts) {
            e.at(att_nome, att_valor);
        }
    }

    public static void ADICIONAR_VARIOS(Lista<Entidade> entts, Lista<Entidade> adicionar) {
        for (Entidade e : adicionar) {
            entts.adicionar(e);
        }
    }


    public static Entidade CRIAR_EM_SEQUENCIALMENTE(Lista<Entidade> entts, String sequencia_nome) {
        Entidade novo = new Entidade();
        novo.at(sequencia_nome, ENTT.CONTAGEM(entts));
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM_SEQUENCIALMENTE(Lista<Entidade> entts, String sequencia_nome, int iniciar_em) {
        Entidade novo = new Entidade();
        novo.at(sequencia_nome, ENTT.CONTAGEM(entts) + iniciar_em);
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM(Lista<Entidade> entts) {
        Entidade novo = new Entidade();
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM(Lista<Entidade> entts, String att_nome, String att_valor) {
        Entidade novo = new Entidade();
        novo.at(att_nome, att_valor);
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM(Lista<Entidade> entts, String att_nome, int att_valor) {
        Entidade novo = new Entidade();
        novo.at(att_nome, att_valor);
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM(Lista<Entidade> entts, String att_nome, long att_valor) {
        Entidade novo = new Entidade();
        novo.at(att_nome, att_valor);
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM(Lista<Entidade> entts, String att_nome1, String att_valor1, String att_nome2, String att_valor2) {
        Entidade novo = new Entidade();
        novo.at(att_nome1, att_valor1);
        novo.at(att_nome2, att_valor2);
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM(Lista<Entidade> entts, String att_nome1, int att_valor1, String att_nome2, int att_valor2) {
        Entidade novo = new Entidade();
        novo.at(att_nome1, att_valor1);
        novo.at(att_nome2, att_valor2);
        entts.adicionar(novo);
        return novo;
    }

    public static Entidade CRIAR_EM(Lista<Entidade> entts, String att_nome1, double att_valor1, String att_nome2, double att_valor2) {
        Entidade novo = new Entidade();
        novo.at(att_nome1, att_valor1);
        novo.at(att_nome2, att_valor2);
        entts.adicionar(novo);
        return novo;
    }

    public static void SUBSTITUIR(Lista<Entidade> objetos, String proc_valor, String novo_valor) {


        for (Entidade obj : objetos) {
            for (Tag att : obj.tags()) {
                if (att.isValorIgual(proc_valor)) {
                    att.setValor(novo_valor);
                }
            }
        }

    }

    public static void SUBSTITUIR_INTEIRO_ABAIXO(Lista<Entidade> objetos, int proc_valor, int novo_valor) {

        for (Entidade obj : objetos) {
            for (Tag att : obj.tags()) {
                if (att.isInteiro()) {
                    if (att.asInt() <= proc_valor) {
                        att.setInteiro(novo_valor);
                    }
                }
            }
        }

    }

    public static Lista<Entidade> ABRIR_COM_LIMITE(String arquivo, int limite) {

        Lista<Entidade> lista = new Lista<Entidade>();

        for (DSItem item : DS.ler_todos(arquivo)) {
            if (lista.getQuantidade() < limite) {
                lista.adicionar(PARSER_ENTIDADE(item.getTexto()));
            } else {
                break;
            }
        }


        return lista;
    }

    public static void AT_ALTERAR_NOME(Lista<Entidade> entts, String atributo_antigo, String atributo_novo) {

        for (Entidade item : entts) {
            item.proc_at(atributo_antigo).setNome(atributo_novo);
        }

    }

    public static void AT_MUDAR_NOME(Lista<Entidade> entts, String atributo_antigo, String atributo_novo) {

        for (Entidade item : entts) {
            item.proc_at(atributo_antigo).setNome(atributo_novo);
        }

    }


    public static Lista<Entidade> GET_TOP10(Lista<Entidade> entts) {

        Lista<Entidade> top = new Lista<Entidade>();

        for (Entidade obj : entts) {
            if (CONTAGEM(top) < 10) {
                top.adicionar(obj);
            } else {
                break;
            }
        }

        return top;
    }


    public static Lista<Entidade> LISTA_TO_ENTIDADES(Lista<String> valores) {

        Lista<Entidade> entts = CRIAR_LISTA();

        for (String item : valores) {
            CRIAR_EM(entts, "Item", item);
        }

        return entts;

    }

    public static void EXIBIR_TABELA_COM_NOME(Lista<Entidade> entidades_lista, String tabela_nome) {


        String cabecalho = "";

        Lista<Entidade> cabs = new Lista<Entidade>();
        for (Entidade turma : entidades_lista) {
            for (Tag att : turma.tags()) {

                Entidade ent = null;
                String nome = att.getNome();
                int tam = nome.length();


                if (ENTT.EXISTE(cabs, "Nome", nome)) {
                    ent = ENTT.GET_SEMPRE(cabs, "Nome", nome);
                } else {
                    ent = new Entidade();
                    ent.at("Nome", nome);
                    ent.at("Tamanho", tam);
                    cabs.adicionar(ent);
                }

                int tt = ent.atInt("Tamanho");
                if (tam > tt) {
                    ent.at("Tamanho", tam);
                }
            }
        }

        for (Entidade turma : entidades_lista) {
            for (Entidade cab : cabs) {
                int valor = turma.at(cab.at("Nome")).length();
                if (valor > cab.atInt("Tamanho")) {
                    cab.at("Tamanho", valor);
                }
            }
        }


        int linha_tracos = 0;

        for (Entidade turma : cabs) {
            cabecalho += "| " + fmt.espacar_depois(turma.at("Nome"), turma.atInt("Tamanho") + 5);
            linha_tracos += turma.atInt("Tamanho") + 5 + 2;
        }

        linha_tracos += 1;

        fmt.print("{}", fmt.repetir("-", linha_tracos));

        int titulo_tracos_metade = (linha_tracos - 4 - tabela_nome.length()) / 2;

        int titulo_tracos_metade_esquerda = titulo_tracos_metade;
        int titulo_tracos_metade_direita = titulo_tracos_metade;


        String primeira = "";

        for (Entidade turma : entidades_lista) {
            primeira = "";
            for (Entidade cab : cabs) {
                primeira += "| " + fmt.espacar_depois(turma.at(cab.at("Nome")), cab.atInt("Tamanho") + 5);
            }
            primeira += "|";
            break;
        }

        int tt = (primeira.length() - tabela_nome.length()) - 4;
        int tt_metade = tt / 2;

        titulo_tracos_metade_esquerda = tt_metade;
        titulo_tracos_metade_direita = tt_metade;

        int falta = 0;
        int cabec = 2 + titulo_tracos_metade_esquerda + tabela_nome.length() + titulo_tracos_metade_direita + 2;

        if (cabec < linha_tracos) {
            falta = linha_tracos - cabec;
        }

        titulo_tracos_metade_direita += falta;

        String frase_cab = "| " + fmt.repetir(" ", titulo_tracos_metade_esquerda) + tabela_nome + fmt.repetir(" ", titulo_tracos_metade_direita) + " |";

        // fmt.print("Total = {}",linha_tracos);
        // fmt.print("Cabec = {}",frase_cab.length());
        // fmt.print("Falta = {}",falta);

        //  fmt.print(">> {} : {}",frase_cab.length(),primeira.length());

        fmt.print(frase_cab);
        fmt.print("{}", fmt.repetir("-", linha_tracos));
        fmt.print(cabecalho + "|");
        fmt.print("{}", fmt.repetir("-", linha_tracos));

        for (Entidade turma : entidades_lista) {
            String linha = "";
            for (Entidade cab : cabs) {
                linha += "| " + fmt.espacar_depois(turma.at(cab.at("Nome")), cab.atInt("Tamanho") + 5);
            }
            fmt.print("{}", linha + "|");
        }

        fmt.print("{}", fmt.repetir("-", linha_tracos));

    }


    public static void EXIBIR_TABELA_COM_NOME_CENTRALIZAR_DADOS(Lista<Entidade> entidades_lista, String tabela_nome, Lista<String> centralizar) {


        String cabecalho = "";

        Lista<Entidade> cabs = new Lista<Entidade>();
        for (Entidade turma : entidades_lista) {
            for (Tag att : turma.tags()) {

                Entidade ent = null;
                String nome = att.getNome();
                int tam = nome.length();


                if (ENTT.EXISTE(cabs, "Nome", nome)) {
                    ent = ENTT.GET_SEMPRE(cabs, "Nome", nome);
                } else {
                    ent = new Entidade();
                    ent.at("Nome", nome);
                    ent.at("Tamanho", tam);
                    cabs.adicionar(ent);
                }

                int tt = ent.atInt("Tamanho");
                if (tam > tt) {
                    ent.at("Tamanho", tam);
                }
            }
        }

        for (Entidade turma : entidades_lista) {
            for (Entidade cab : cabs) {
                int valor = turma.at(cab.at("Nome")).length();
                if (valor > cab.atInt("Tamanho")) {
                    cab.at("Tamanho", valor);
                }
            }
        }


        int linha_tracos = 0;

        for (Entidade turma : cabs) {

            int afastamento = 5;
            int tam = turma.atInt("Tamanho") + afastamento;
            if (tam % 2 == 0) {
                afastamento += 1;

            }
         //   fmt.print(">> {} - {}", turma.at("Nome"), tam);


            if (centralizar.existe(Strings.IGUALAVEL(), turma.at("Nome"))) {
                cabecalho += "| " + fmt.centralizar(turma.at("Nome"), turma.atInt("Tamanho") + afastamento);
            } else {
                cabecalho += "| " + fmt.espacar_depois(turma.at("Nome"), turma.atInt("Tamanho") + afastamento);
            }

            linha_tracos += turma.atInt("Tamanho") + afastamento + 2;
        }

        linha_tracos += 1;

        fmt.print("{}", fmt.repetir("-", linha_tracos));

        int titulo_tracos_metade = (linha_tracos - 4 - tabela_nome.length()) / 2;

        int titulo_tracos_metade_esquerda = titulo_tracos_metade;
        int titulo_tracos_metade_direita = titulo_tracos_metade;


        String primeira = "";

        for (Entidade turma : entidades_lista) {
            primeira = "";
            for (Entidade cab : cabs) {

                int afastamento = 5;
                int tam = cab.atInt("Tamanho") + afastamento;
                if (tam % 2 == 0) {
                    afastamento += 1;

                }

                primeira += "| " + fmt.espacar_depois(turma.at(cab.at("Nome")), cab.atInt("Tamanho") + afastamento);
            }
            primeira += "|";
            break;
        }

        int tt = (primeira.length() - tabela_nome.length()) - 4;
        int tt_metade = tt / 2;

        titulo_tracos_metade_esquerda = tt_metade;
        titulo_tracos_metade_direita = tt_metade;

        int falta = 0;
        int cabec = 2 + titulo_tracos_metade_esquerda + tabela_nome.length() + titulo_tracos_metade_direita + 2;

        if (cabec < linha_tracos) {
            falta = linha_tracos - cabec;
        }

        titulo_tracos_metade_direita += falta;

        String frase_cab = "| " + fmt.repetir(" ", titulo_tracos_metade_esquerda) + tabela_nome + fmt.repetir(" ", titulo_tracos_metade_direita) + " |";

        // fmt.print("Total = {}",linha_tracos);
        // fmt.print("Cabec = {}",frase_cab.length());
        // fmt.print("Falta = {}",falta);

        //  fmt.print(">> {} : {}",frase_cab.length(),primeira.length());

        fmt.print(frase_cab);
        fmt.print("{}", fmt.repetir("-", linha_tracos));
        fmt.print(cabecalho + "|");
        fmt.print("{}", fmt.repetir("-", linha_tracos));

        for (Entidade turma : entidades_lista) {
            String linha = "";
            for (Entidade cab : cabs) {

                int afastamento = 5;
                int tam = cab.atInt("Tamanho") + afastamento;
                if (tam % 2 == 0) {
                    afastamento += 1;
                }

                if (centralizar.existe(Strings.IGUALAVEL(), cab.at("Nome"))) {
                    linha += "| " + fmt.centralizar(turma.at(cab.at("Nome")), cab.atInt("Tamanho") + afastamento);
                } else {
                    linha += "| " + fmt.espacar_depois(turma.at(cab.at("Nome")), cab.atInt("Tamanho") + afastamento);
                }

            }
            fmt.print("{}", linha + "|");
        }

        fmt.print("{}", fmt.repetir("-", linha_tracos));

    }

    public static Lista<Entidade> SLICE(Lista<Entidade> entts, int inicio, int fim) {
        Lista<Entidade> ret = new Lista<Entidade>();

        int i = 0;
        for (Entidade e : entts) {
            if (i >= inicio && i <= fim) {
                ret.adicionar(e);
            }
            if (i > fim) {
                break;
            }
            i += 1;
        }
        return ret;
    }


    public static Lista<Entidade> SLICE_PRIMEIROS(Lista<Entidade> dados, int quantidade) {
        return ENTT.SLICE(dados, 0, quantidade);
    }


    public static Lista<Entidade> PARSER(String texto_dados) {

        DKG dkg = new DKG();
        dkg.parser(texto_dados);

        Lista<Entidade> varias = new Lista<Entidade>();

        for (DKGObjeto o : dkg.getObjetos()) {

            Entidade e1 = new Entidade();

            for (DKGAtributo oa : o.getAtributos()) {
                e1.at(oa.getNome(), oa.getValor());
            }

            parser_interno(e1, o);

            varias.adicionar(e1);

        }

        return varias.get(0).getEntidades();

    }

    private static void parser_interno(Entidade e_pai, DKGObjeto o_pai) {

        for (DKGObjeto o : o_pai.getObjetos()) {

            Entidade e1 = new Entidade();

            for (DKGAtributo oa : o.getAtributos()) {
                e1.at(oa.getNome(), oa.getValor());
            }

            parser_interno(e1, o);

            e_pai.getEntidades().adicionar(e1);
        }

    }


    public static void ATRIBUTO_ALTERAR(Lista<Entidade> entts, String att_nome, String att_valor) {
        for (Entidade e : entts) {
            e.at(att_nome, att_valor);
        }
    }

    public static boolean TEM(Lista<Entidade> entts) {
        return entts.getQuantidade() > 0;
    }


    public static Lista<Entidade> VALORES(String att_nome, Lista<String> valores) {
        Lista<Entidade> entts = CRIAR_LISTA();

        for (String item : valores) {
            Entidade e = new Entidade();
            e.at(att_nome, item);
            entts.adicionar(e);
        }

        return entts;
    }


    public static Lista<Entidade> VALORES_ENTRE(String att_nome, Lista<String> valores, int inicio, int fim) {
        Lista<Entidade> entts = CRIAR_LISTA();

        int i = 0;

        for (String item : valores) {

            if (i >= inicio && i <= fim) {
                Entidade e = new Entidade();
                e.at(att_nome, item);
                entts.adicionar(e);
            }


            i += 1;
        }

        return entts;
    }

    public static Lista<Entidade> VALORES_SEQUENCIADOS(String att_sequencia, String att_nome, Lista<String> valores) {
        Lista<Entidade> entts = CRIAR_LISTA();

        for (String item : valores) {
            Entidade e = new Entidade();
            e.at(att_sequencia, entts.getQuantidade());
            e.at(att_nome, item);
            entts.adicionar(e);
        }

        return entts;
    }


    public static Lista<Entidade> CRIAR_LISTA_COM(Entidade e) {
        Lista<Entidade> ls = new Lista<Entidade>();
        ls.adicionar(e);
        return ls;
    }

    public static Lista<Entidade> CRIAR_LISTA_COM(Entidade e1, Entidade e2) {
        Lista<Entidade> ls = new Lista<Entidade>();
        ls.adicionar(e1);
        ls.adicionar(e2);
        return ls;
    }


    public static Lista<Entidade> CRIAR_LISTA_VALORES_DE(Lista<Entidade> entradas, Lista<String> campos) {
        Lista<Entidade> ls = new Lista<Entidade>();

        for (Entidade entrada : entradas) {
            Entidade novo = CRIAR_EM(ls);

            for (String campo : campos) {
                novo.at(campo, entrada.at(campo));
            }


        }
        return ls;
    }

    public static void ATRIBUTO_TODOS(Lista<Entidade> entradas, String att_nome, String att_valor) {

        for (Entidade entidade : entradas) {
            entidade.at(att_nome, att_valor);
        }

    }


    public static void ATRIBUTO_TODOS(Lista<Entidade> entradas, String att_nome, int att_valor) {

        for (Entidade entidade : entradas) {
            entidade.at(att_nome, att_valor);
        }

    }


    public static Lista<Entidade> ABRIR_ZONAS_DE_CARREGAMENTO(String arquivo, int tamanho_zona) {

        Lista<Entidade> lista = new Lista<Entidade>();

        int quantidade = DS.ler_todos(arquivo).getQuantidade();

        int inicio = 0;

        while (quantidade >= tamanho_zona) {
            Entidade e_zona = CRIAR_EM(lista);
            e_zona.at("Inicio", inicio);
            e_zona.at("Quantidade", tamanho_zona);
            e_zona.at("Fim", inicio + tamanho_zona);

            inicio += tamanho_zona;
            quantidade -= tamanho_zona;
        }

        if (quantidade > 0) {
            Entidade e_zona = CRIAR_EM(lista);
            e_zona.at("Inicio", inicio);
            e_zona.at("Quantidade", quantidade);
            e_zona.at("Fim", inicio + quantidade);

        }

        return lista;
    }


    public static Entidade TRANSFORMAR_DE_OBJETO(DKGObjeto objeto) {
        Entidade e = new Entidade();

        for (DKGAtributo att : objeto.getAtributos()) {
            e.at(att.getNome(), att.getValor());
        }

        return e;
    }

    public static Lista<Entidade> TRANSFORMAR_DE_OBJETOS(Lista<DKGObjeto> objetos) {
        Lista<Entidade> ls = new Lista<Entidade>();

        for (DKGObjeto obj : objetos) {
            Entidade e = new Entidade();

            for (DKGAtributo att : obj.getAtributos()) {
                e.at(att.getNome(), att.getValor());
            }

            ls.adicionar(e);
        }

        return ls;
    }

    public static Entidade PARSER_RAW_TO_ENTIDADE(String dados) {
        DKG eDKG = new DKG();
        eDKG.parser(dados);

        Entidade e = new Entidade();

        if (eDKG.getObjetos().getQuantidade() > 0) {
            DKGObjeto raiz = eDKG.getObjetos().get(0);
            for (DKGAtributo a : raiz.getAtributos()) {
                e.at(a.getNome(), a.getValor());
            }
        }

        return e;
    }

    public static Entidade PARSER_ENTIDADE_QUALQUER(String texto_dados) {

        Entidade e = new Entidade();

        DKGObjeto obj = DKG.PARSER(texto_dados).getObjetos().get(0);

        for (DKGAtributo a : obj.getAtributos()) {
            e.at(a.getNome(), a.getValor());
        }

        for (DKGObjeto o : obj.getObjetos()) {

            Entidade e1 = new Entidade();

            for (DKGAtributo oa : o.getAtributos()) {
                e1.at(oa.getNome(), oa.getValor());
            }

            e.getEntidades().adicionar(e1);
        }


        return e;

    }


    public static Entidade ADICIONAR_EM(Lista<Entidade> entts, Entidade e) {
        entts.adicionar(e);
        return e;
    }


    public static Lista<Entidade> CRIAR_DE_STRINGS(Lista<String> strings) {

        Lista<Entidade> entts = CRIAR_LISTA();

        for (String item : strings) {
            CRIAR_EM(entts, "Item", item);
        }

        return entts;
    }


    public static Lista<Entidade> GET_DESCRITORES(Lista<Entidade> entidades) {

        Unico<String> atributos = new Unico<String>(Strings.IGUALAVEL());

        for (Entidade e : entidades) {
            for (Tag tag : e.tags()) {
                atributos.item(tag.getNome());
            }
        }


        Lista<Entidade> e_atts = new Lista<Entidade>();


        for (String att : atributos) {
            Entidade descritor = ENTT.CRIAR_EM_SEQUENCIALMENTE(e_atts, "ID");
            descritor.at("Nome", att);

            int itens = entidades.getQuantidade();

            int valores_quantidade = 0;
            int numeros_quantidade = 0;

            boolean isPrimeiro = true;
            boolean primeiro_tem_sufixo = false;
            String primeiro_sufixo = "";


            Lista<String> valores = new Lista<String>();


            for (Entidade e_item : entidades) {

                if (e_item.atributo_existe(att)) {

                    String att_valor = e_item.at(att);

                    if (isPrimeiro) {

                        String parte_numerica = Strings.getParteNumerica(att_valor);
                        String parte_sufixo = Strings.getParteDepoisDeNumerica(att_valor);

                        if (!parte_sufixo.contains(" ") && !parte_sufixo.contains("/") && !parte_sufixo.contains(":") && !parte_sufixo.contains("_") && !parte_sufixo.contains("-") && !Strings.temDigito(parte_sufixo)) {
                            primeiro_tem_sufixo = true;
                            primeiro_sufixo = parte_sufixo;
                        }

                    }

                    isPrimeiro = false;


                    if (Matematica.isNumero(att_valor)) {
                        numeros_quantidade += 1;

                        valores.adicionar(att_valor);

                    } else {
                        if (primeiro_tem_sufixo) {

                            String parte_sufixo = Strings.getParteNumerica(att_valor);
                            if (!parte_sufixo.isEmpty()) {
                                valores.adicionar(parte_sufixo);
                            }
                        }
                    }

                    valores_quantidade += 1;
                }


            }

            descritor.at("Quantidade", valores_quantidade);

            if (primeiro_tem_sufixo) {
                descritor.at("Numeros", valores.getQuantidade());
            } else {
                descritor.at("Numeros", numeros_quantidade);
            }


            if (descritor.atIntOuPadrao("Quantidade", 0) == descritor.atIntOuPadrao("Numeros", 0)) {

                if (primeiro_tem_sufixo) {
                    descritor.at("Primeiro.Sufixo", primeiro_sufixo);
                }

                descritor.at("Tipo", "Numero");

                int numero_inteiro = 0;
                int numero_real = 0;

                String numero_menor = valores.get(0);
                String numero_maior = valores.get(0);

                double valor_menor = Double.parseDouble(numero_menor);
                double valor_maior = Double.parseDouble(numero_maior);

                for (String numero : valores) {

                    if (Matematica.isNumeroInteiro(numero)) {
                        numero_inteiro += 1;
                    } else {
                        numero_real += 1;
                    }

                    double valor = Double.parseDouble(numero);

                    if (valor < valor_menor) {
                        valor_menor = valor;
                        numero_menor = numero;
                    }

                    if (valor > valor_maior) {
                        valor_maior = valor;
                        numero_maior = numero;
                    }

                }

                //  descritor.at("Numero.Inteiro", numero_inteiro);
                // descritor.at("Numero.Real", numero_real);


                if (numero_inteiro == valores.getQuantidade()) {
                    descritor.at("Numero.Tipo", "Inteiro");


                    Unico<String> numeros_unicos = new Unico<String>(Strings.IGUALAVEL());
                    for (String valor : valores) {
                        numeros_unicos.item(valor);
                    }


                    descritor.at("Numeros.Unicos", numeros_unicos.getQuantidade());
                    descritor.at("Numeros.Chave", Portugues.VALIDAR(itens == numeros_unicos.getQuantidade(), "Primaria", "NAO"));

                    if (numeros_unicos.getQuantidade() < itens / 4) {
                        descritor.at("Numeros.Chave", "Particao");
                    }

                } else if (numero_real == valores.getQuantidade()) {
                    descritor.at("Numero.Tipo", "Real");
                } else {
                    descritor.at("Numero.Tipo", "Misto");
                }


                descritor.at("Numero.Menor", numero_menor);
                descritor.at("Numero.Maior", numero_maior);


            } else {
                descritor.at("Tipo", "Texto");


                Lista<String> texto_valores = new Lista<String>();
                Unico<String> texto_unico = new Unico<String>(Strings.IGUALAVEL());

                for (Entidade e_item : entidades) {
                    if (e_item.atributo_existe(att)) {

                        String att_valor = e_item.at(att);

                        texto_unico.item(att_valor);
                        texto_valores.adicionar(att_valor);

                    }

                }

                descritor.at("Texto.Valores", texto_valores.getQuantidade());
                descritor.at("Texto.Unico", texto_unico.getQuantidade());

                if (texto_unico.getQuantidade() == texto_valores.getQuantidade()) {
                    descritor.at("Texto.Tipo", "Chave");


                } else {
                    descritor.at("Texto.Tipo", "Texto");

                    if (texto_unico.getQuantidade() == 1) {
                        descritor.at("Texto.Tipo", "Constante");
                    } else if (texto_unico.getQuantidade() < (texto_valores.getQuantidade() / 4)) {
                        descritor.at("Texto.Tipo", "Enum");
                    }


                }


            }


        }

        return e_atts;
    }

    public static void SUBSTITUIR_PARTE(Lista<Entidade> objetos, String att_nome, String proc_parte, String sub_parte) {

        for (Entidade e : objetos) {
            e.at(att_nome, e.at(att_nome).replace(proc_parte, sub_parte));
        }

    }

    public static Lista<Entidade> GET_ZONAS_PORCENTAGEM() {

        Lista<Entidade> pp_zonas = new Lista<Entidade>();
        ENTT.CRIAR_EM(pp_zonas, "Min", 0, "Max", 24);
        ENTT.CRIAR_EM(pp_zonas, "Min", 25, "Max", 49);
        ENTT.CRIAR_EM(pp_zonas, "Min", 50, "Max", 74);
        ENTT.CRIAR_EM(pp_zonas, "Min", 75, "Max", 100);


        SEQUENCIAR(pp_zonas, "ID", 1);
        ATRIBUTO_TORNAR_PRIMEIRO(pp_zonas, "ID");
        ATRIBUTO_TODOS(pp_zonas, "Quantidade", 0);

        return pp_zonas;
    }

    public static Lista<Entidade> GET_4ZONAS_DE(Lista<Entidade> dados, String att_nome) {

        int menor = ENTT.GET_INTEIRO_MENOR(dados, att_nome);
        int maior = ENTT.GET_INTEIRO_MAIOR(dados, att_nome);

        int parte = (maior - menor) / 4;

        Lista<Entidade> pp_zonas = new Lista<Entidade>();
        ENTT.CRIAR_EM(pp_zonas, "Min", menor, "Max", menor + (1 * parte) - 1);
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (1 * parte), "Max", menor + (2 * parte) - 1);
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (2 * parte), "Max", menor + (3 * parte) - 1);
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (3 * parte), "Max", maior);


        SEQUENCIAR(pp_zonas, "ID", 1);
        ATRIBUTO_TORNAR_PRIMEIRO(pp_zonas, "ID");
        ATRIBUTO_TODOS(pp_zonas, "Quantidade", 0);

        return pp_zonas;
    }

    public static Lista<Entidade> GET_2ZONAS_DE(Lista<Entidade> dados, String att_nome) {

        int menor = ENTT.GET_INTEIRO_MENOR(dados, att_nome);
        int maior = ENTT.GET_INTEIRO_MAIOR(dados, att_nome);

        int parte = (maior - menor) / 2;

        Lista<Entidade> pp_zonas = new Lista<Entidade>();
        ENTT.CRIAR_EM(pp_zonas, "Min", menor, "Max", menor + (1 * parte) - 1);
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (1 * parte), "Max", maior);


        SEQUENCIAR(pp_zonas, "ID", 1);
        ATRIBUTO_TORNAR_PRIMEIRO(pp_zonas, "ID");
        ATRIBUTO_TODOS(pp_zonas, "Quantidade", 0);

        return pp_zonas;
    }

    public static void ZONA_ANALISAR(Lista<Entidade> dados, Lista<Entidade> zonas, String att_valor, String att_agrupar) {


        for (Entidade zona : zonas) {

            int min = zona.atInt("Min");
            int max = zona.atInt("Max");

            for (Entidade item : dados) {

                int item_valor = item.atInt(att_valor);
                if (item_valor >= min && item_valor <= max) {
                    zona.at(att_agrupar, zona.atIntOuPadrao(att_agrupar, 0) + item.atInt(att_agrupar));
                }

            }


        }

    }


    public static Lista<Entidade> GET_4ZONAS_DOUBLE_DE(Lista<Entidade> dados, String att_nome) {

        double menor = ENTT.GET_DOUBLE_MENOR(dados, att_nome);
        double maior = ENTT.GET_DOUBLE_MAIOR(dados, att_nome);

        double parte = (maior - menor) / 4.0;

        Lista<Entidade> pp_zonas = new Lista<Entidade>();
        ENTT.CRIAR_EM(pp_zonas, "Min", menor, "Max", menor + (1 * parte));
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (1 * parte), "Max", menor + (2 * parte));
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (2 * parte), "Max", menor + (3 * parte));
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (3 * parte), "Max", maior + 0.1);


        SEQUENCIAR(pp_zonas, "ID", 1);
        ATRIBUTO_TORNAR_PRIMEIRO(pp_zonas, "ID");
        ATRIBUTO_TODOS(pp_zonas, "Quantidade", 0);

        return pp_zonas;
    }

    public static Lista<Entidade> GET_2ZONAS_DOUBLE_DE(Lista<Entidade> dados, String att_nome) {

        double menor = ENTT.GET_DOUBLE_MENOR(dados, att_nome);
        double maior = ENTT.GET_DOUBLE_MAIOR(dados, att_nome);

        double parte = (maior - menor) / 2.0;

        Lista<Entidade> pp_zonas = new Lista<Entidade>();
        ENTT.CRIAR_EM(pp_zonas, "Min", menor, "Max", menor + (parte));
        ENTT.CRIAR_EM(pp_zonas, "Min", menor + (parte), "Max", maior + 0.1);


        SEQUENCIAR(pp_zonas, "ID", 1);
        ATRIBUTO_TORNAR_PRIMEIRO(pp_zonas, "ID");
        ATRIBUTO_TODOS(pp_zonas, "Quantidade", 0);

        return pp_zonas;
    }


    public static void ZONA_ANALISAR_DOUBLE(Lista<Entidade> dados, Lista<Entidade> zonas, String att_valor, String att_agrupar) {


        for (Entidade zona : zonas) {

            double min = zona.atDouble("Min");
            double max = zona.atDouble("Max");

            for (Entidade item : dados) {

                double item_valor = item.atDouble(att_valor);
                if (item_valor >= min && item_valor < max) {
                    zona.at(att_agrupar, zona.atIntOuPadrao(att_agrupar, 0) + item.atInt(att_agrupar));
                }

            }


        }

    }


    public static long ATRIBUTO_LONG_SOMAR(Lista<Entidade> dados, String att_nome) {
        long somatorio = 0;
        for (Entidade e : dados) {
            somatorio += e.atLong(att_nome);
        }
        return somatorio;
    }

    public static void ATRIBUTO_REMOVER(Lista<Entidade> dados, String att_nome) {
        for (Entidade e : dados) {
            e.at_remover(att_nome);
        }
    }


    public static Lista<Entidade> ANALISAR_OBTER_NOVOS(Lista<Entidade> dados_primarios, Lista<Entidade> dados_secundarios, String att_primario, String att_secundario) {

        Lista<Entidade> novos = new Lista<Entidade>();

        for (Entidade primario : dados_primarios) {

            boolean existe = false;

            String primario_valor = primario.at(att_primario);

            for (Entidade secundario : dados_secundarios) {

                String secundario_valor = secundario.at(att_secundario);

                if (Strings.isIgual(primario_valor, secundario_valor)) {
                    existe = true;
                    break;
                }

            }

            if (!existe) {
                novos.adicionar(primario);
            }

        }

        return novos;
    }

    public static Lista<Entidade> ANALISAR_OBTER_EXCLUIDOS(Lista<Entidade> dados_primarios, Lista<Entidade> dados_secundarios, String att_primario, String att_secundario) {

        Lista<Entidade> excluidos = new Lista<Entidade>();

        for (Entidade secundario : dados_secundarios) {

            boolean existe = false;

            String secundario_valor = secundario.at(att_secundario);

            for (Entidade primario : dados_primarios) {

                String primario_valor = primario.at(att_primario);

                if (Strings.isIgual(primario_valor, secundario_valor)) {
                    existe = true;
                    break;
                }

            }

            if (!existe) {
                excluidos.adicionar(secundario);
            }

        }

        return excluidos;
    }


    public static Lista<Par<Entidade, Entidade>> ANALISAR_OBTER_EM_COMUM_PARES(Lista<Entidade> dados_primarios, Lista<Entidade> dados_secundarios, String att_primario, String att_secundario) {

        Lista<Par<Entidade, Entidade>> comum = new Lista<Par<Entidade, Entidade>>();

        for (Entidade primario : dados_primarios) {

            String primario_valor = primario.at(att_primario);

            for (Entidade secundario : dados_secundarios) {

                String secundario_valor = secundario.at(att_secundario);

                if (Strings.isIgual(primario_valor, secundario_valor)) {
                    comum.adicionar(new Par<Entidade, Entidade>(primario, secundario));
                    break;
                }

            }


        }

        return comum;
    }

    public static void ZONA_ANALISAR_EM_DISPERSAO(Lista<Entidade> dados, Lista<Entidade> zonas, String att_valor) {
        ENTT.ZONA_ANALISAR(ENTT.DISPERSAO(dados, att_valor), zonas, att_valor, "Quantidade");
    }

    public static Lista<Entidade> ZONA_ANALISAR_EM_DISPERSAO_4ZONAS(Lista<Entidade> dados, String att_valor) {

        Lista<Entidade> idade_zonas = ENTT.GET_4ZONAS_DE(dados, att_valor);

        ENTT.ZONA_ANALISAR_EM_DISPERSAO(dados, idade_zonas, att_valor);

        return idade_zonas;
    }


    public static void CALCULAR_PORCENTAGEM(Lista<Entidade> dados, String att_calcular, String att_porcentagem) {

        int total = ENTT.ATRIBUTO_SOMAR(dados, att_calcular);

        for (Entidade e : dados) {
            e.at(att_porcentagem, fmt.f2Porcentagem(e.atInt(att_calcular), total));
        }

    }


    public static Lista<String> COLETAR_ATRIBUTOS_NOME_QUANDO(Entidade e, String valor) {
        Lista<String> nomes = new Lista<String>();

        for (Tag tag : e.tags()) {
            if (tag.getValor().contentEquals(valor)) {
                nomes.adicionar(tag.getNome());
            }
        }

        return nomes;
    }


    public static Lista<Entidade> COLETAR_INTEIRO_MAIOR(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) > campo_valor) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> COLETAR_INTEIRO_MAIOR_OU_IGUAL(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) >= campo_valor) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> COLETAR_INTEIRO_MENOR(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) < campo_valor) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> COLETAR_INTEIRO_MENOR_OU_IGUAL(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) <= campo_valor) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }

    public static Lista<Entidade> COLETAR_INTEIRO_IGUAL(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) == campo_valor) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }


    public static Lista<Entidade> COLETAR_INTEIRO_DIFERENTE(Lista<Entidade> entts, String campo_nome, int campo_valor) {
        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.atInt(campo_nome) != campo_valor) {
                filtrados.adicionar(e);
            }
        }


        return filtrados;
    }


    public static Lista<Entidade> COPIAR(Lista<Entidade> entrada) {
        Lista<Entidade> ret = new Lista<Entidade>();

        for (Entidade e : entrada) {
            ret.adicionar(e.getCopia());
        }

        return ret;
    }

    public static Lista<Entidade> VALORES_TO_ENTIDADE_ORDENADO(Lista<String> lista, String att_nome_ordem, String att_nome) {

        Lista<Entidade> entts = CRIAR_LISTA();

        int ordem = 0;

        for (String item : lista) {
            Entidade e = new Entidade();
            e.at(att_nome_ordem, ordem);
            e.at(att_nome, item);

            entts.adicionar(e);
            ordem += 1;
        }

        return entts;
    }

    public static void REMOVER_SE_COMECAR_COM(Lista<Entidade> entidades_lista, String att_nome, String att_valor_comeca) {

        Lista<Entidade> validos = new Lista<Entidade>();

        for (Entidade item : entidades_lista) {
            if (!item.at(att_nome).startsWith(att_valor_comeca)) {
                validos.adicionar(item);
            }
        }

        entidades_lista.limpar();
        for (Entidade v : validos) {
            entidades_lista.adicionar(v);
        }

    }

    public static Opcional<Entidade> GET_PRIMEIRO_INICIA_COM(Lista<Entidade> ls, String atributo, String valor) {

        Opcional<Entidade> ret = Opcional.CANCEL();

        for (Entidade entt : ls) {
            if (entt.at(atributo).startsWith(valor)) {
                ret.set(entt);
                break;
            }
        }

        return ret;

    }

    public static Lista<Entidade> SLICE_REFERENCIADO(Lista<Entidade> entts, int onde, int antes_quantidade, int depois_quantidade) {
        Lista<Entidade> ret = new Lista<Entidade>();

        int inicio = onde - antes_quantidade;
        int fim = onde + depois_quantidade;

        int i = 0;
        for (Entidade e : entts) {
            if (i >= inicio && i <= fim) {
                ret.adicionar(e);
            }
            if (i > fim) {
                break;
            }
            i += 1;
        }
        return ret;
    }

    public static void TORNAR_PRIMEIRO(Lista<Entidade> entts, String att_nome) {
        for (Entidade e : entts) {
            e.tornar_primeiro(att_nome);
        }
    }

    public static void EXIBIR_BUSCA_REFERENCIADA_CONTEM(Lista<Entidade> entts, String att_ordem, String att_nome, String att_valor_contem) {

        for (Entidade e : entts) {
            if (e.at(att_nome).contains(att_valor_contem)) {

                Lista<Entidade> tokens_refs = ENTT.SLICE_REFERENCIADO(entts, e.atInt(att_ordem), 5, 5);
                ENTT.SEQUENCIAR(tokens_refs, att_ordem, -5);
                ENTT.TORNAR_PRIMEIRO(tokens_refs, att_ordem);
                ENTT.EXIBIR_TABELA(tokens_refs);


            }
        }

    }

    public static void EXIBIR_BUSCA_REFERENCIADA_CONTEM(Lista<Entidade> entts, String att_ordem, String att_nome, String att_valor_contem, int tamanho) {

        for (Entidade e : entts) {
            if (e.at(att_nome).contains(att_valor_contem)) {

                Lista<Entidade> tokens_refs = ENTT.SLICE_REFERENCIADO(entts, e.atInt(att_ordem), tamanho, tamanho);
                ENTT.SEQUENCIAR(tokens_refs, att_ordem, -5);
                ENTT.TORNAR_PRIMEIRO(tokens_refs, att_ordem);
                ENTT.EXIBIR_TABELA(tokens_refs);


            }
        }

    }


    public static Lista<Entidade> EXIBIR_BUSCA_REFERENCIADA_CONTEM_ABAIXO(Lista<Entidade> entts, String att_ordem, String att_nome, String att_valor_contem, int tamanho) {

        Lista<Entidade> refs = new Lista<Entidade>();

        for (Entidade e : entts) {
            if (e.at(att_nome).contains(att_valor_contem)) {

                Lista<Entidade> tokens_refs = ENTT.SLICE(entts, e.atInt(att_ordem), e.atInt(att_ordem) + tamanho);
                ENTT.SEQUENCIAR(tokens_refs, "Ref", 0);
                ENTT.TORNAR_PRIMEIRO(tokens_refs, att_ordem);
                ENTT.EXIBIR_TABELA(tokens_refs);

                refs.adicionar(e);
            }
        }

        return refs;
    }


    public static void EXIBIR_BUSCA_REFERENCIADA_INICIA(Lista<Entidade> entts, String att_ordem, String att_nome, String att_valor_contem) {

        for (Entidade e : entts) {
            if (e.at(att_nome).startsWith(att_valor_contem)) {

                Lista<Entidade> tokens_refs = ENTT.SLICE_REFERENCIADO(entts, e.atInt(att_ordem), 5, 5);
                ENTT.SEQUENCIAR(tokens_refs, att_ordem, -5);
                ENTT.TORNAR_PRIMEIRO(tokens_refs, att_ordem);
                ENTT.EXIBIR_TABELA(tokens_refs);


            }
        }

    }


    public static Lista<String> VALORES_DE(Lista<Entidade> entts, String att) {
        Lista<String> valores = new Lista<String>();
        for (Entidade e : entts) {
            valores.adicionar(e.at(att));
        }

        return valores;
    }

    public static Lista<Entidade> VALORES_PARA_ENTIDADES(Lista<String> valores, String att) {
        Lista<Entidade> lista = new Lista<Entidade>();
        for (String valor : valores) {
            Entidade item = ENTT.CRIAR_EM(lista);
            item.at(att, valor);
        }
        return lista;
    }

    public static Lista<Entidade> EMBARALHAR(Lista<Entidade> entts) {
        Embaralhar.emabaralhe(entts);
        return entts;
    }


    public static void ALTERAR_VALOR_SE(Lista<Entidade> entts, String att_nome, String att_valor_corrente, String att_valor_novo) {

        for (Entidade item : entts) {
            if (item.is(att_nome, att_valor_corrente)) {
                item.at(att_nome, att_valor_novo);
            }
        }

    }

    public static boolean COLETAR_EXISTE(Lista<Entidade> entts, String att_nome, String att_valor) {
        return COLETAR(entts, att_nome, att_valor).getQuantidade() > 0;
    }

    public static boolean TEM_OBJETOS(Lista<Entidade> entts) {
        return entts.getQuantidade() > 0;
    }

    public static boolean ZERO_OBJETOS(Lista<Entidade> entts) {
        return entts.getQuantidade() == 0;
    }

    public static Lista<Entidade> COLETAR_OU_COLETAR(Lista<Entidade> linhas, String chave, String coluna_valor, String chave2, String coluna_valor2) {

        Lista<Entidade> filtrados = new Lista<Entidade>();

        for (Entidade linha : linhas) {
            if (linha.isIgual(chave, coluna_valor) || linha.isIgual(chave2, coluna_valor2)) {
                filtrados.adicionar(linha);
            }
        }

        return filtrados;
    }

    public static int INTEIRO_MAIOR(Lista<Entidade> entts, String campo) {

        int maior = 0;

        for (Entidade assunto : entts) {
            int valor = assunto.atInt(campo);
            if (valor > maior) {
                maior = valor;
            }
        }

        return maior;
    }

    public static int INTEIRO_MENOR(Lista<Entidade> entts, String campo) {

        int menor = entts.get(0).atInt(campo);

        for (Entidade assunto : entts) {
            int valor = assunto.atInt(campo);
            if (valor < menor) {
                menor = valor;
            }
        }

        return menor;
    }


    public static Lista<String> LISTAR_VALORES(Lista<Entidade> entts, String att_nome) {
        Lista<String> valores = new Lista<String>();

        for (Entidade e : entts) {
            valores.adicionar(e.at(att_nome));
        }
        return valores;
    }

    public static void REMOVER_PRIMEIRA_LINHA(Lista<Entidade> dados) {
        dados.removerIndex(0);
    }

    public static void REMOVER_ULTIMA_LINHA(Lista<Entidade> dados) {
        dados.removerIndex(dados.getQuantidade() - 1);
    }

    public static Lista<Entidade> TITULARIZAR(Lista<Entidade> dados) {

        Lista<Entidade> copia = ENTT.COPIAR(dados);

        Entidade primeiro = GET_PRIMEIRO(copia);
        REMOVER_PRIMEIRA_LINHA(copia);

        for (Tag tag : primeiro.tags()) {
            tag.setValor(Strings.RETIRAR_ACENTOS(tag.getValor().toUpperCase()).replace(" ", "_"));
        }


        Lista<Entidade> novos = ENTT.CRIAR_LISTA();

        for (Entidade a : copia) {
            int ii = 0;
            for (Tag tag : a.tags()) {
                tag.setNome(primeiro.tags().get(ii).getValor());
                ii += 1;
            }
            novos.adicionar(a);
        }

        return novos;

    }

    public static Lista<Entidade> CRUZAMENTO_DISPERSO(Lista<Entidade> dados, String alfaChave, String betaChave, String gamaChave) {

        Lista<String> regionais = ENTT.FILTRAR_UNICOS(dados, alfaChave);
        Lista<Entidade> cruzamento = ENTT.CRIAR_LISTA();

        Lista<String> gamaTipos = ENTT.FILTRAR_UNICOS(dados, gamaChave);

        for (String tipoAlfa : regionais) {
            Lista<Entidade> alfaValores = ENTT.COLETAR(dados, alfaChave, tipoAlfa);

            Lista<String> turmas = ENTT.FILTRAR_UNICOS(alfaValores, betaChave);

            for (String turma : turmas) {

                Entidade p = ENTT.CRIAR_EM(cruzamento);
                p.at(alfaChave, tipoAlfa);
                p.at(betaChave, turma);

                Lista<Entidade> betaValores = ENTT.COLETAR(alfaValores, betaChave, turma);

                Lista<Entidade> disp = ENTT.DISPERSAO(betaValores, gamaChave);

                String frase = "";

                for (String gamaTipo : gamaTipos) {
                    String valor = ENTT.GET_SEMPRE(disp, gamaChave, gamaTipo).at("Quantidade");
                    if (valor.length() == 0) {
                        valor = "0";
                    }
                    frase += " [ " + gamaTipo + " = " + valor + " ] ";
                }

                p.at("Cruzamento", frase);

                //    ENTT.EXIBIR_TABELA(disp);
            }

        }

        return cruzamento;
    }

    public static Lista<Entidade> CRUZAMENTO_SOMATORIO(Lista<Entidade> dados, String alfaChave, String betaChave, String gamaChave, String deltaChave) {

        Lista<String> regionais = ENTT.FILTRAR_UNICOS(dados, alfaChave);
        Lista<Entidade> cruzamento = ENTT.CRIAR_LISTA();

        Lista<String> gamaTipos = ENTT.FILTRAR_UNICOS(dados, gamaChave);

        for (String tipoAlfa : regionais) {
            Lista<Entidade> alfaValores = ENTT.COLETAR(dados, alfaChave, tipoAlfa);

            Lista<String> turmas = ENTT.FILTRAR_UNICOS(alfaValores, betaChave);

            for (String turma : turmas) {

                Entidade p = ENTT.CRIAR_EM(cruzamento);
                p.at(alfaChave, tipoAlfa);
                p.at(betaChave, turma);

                Lista<Entidade> betaValores = ENTT.COLETAR(alfaValores, betaChave, turma);

                Lista<Entidade> disp = ENTT.DISPERSAO_SOMATORIO(betaValores, gamaChave, deltaChave);

                String frase = "";

                for (String gamaTipo : gamaTipos) {
                    String valor = ENTT.GET_SEMPRE(disp, gamaChave, gamaTipo).at("Quantidade");
                    if (valor.length() == 0) {
                        valor = "0";
                    }
                    frase += " [ " + gamaTipo + " = " + fmt.espacar_antes(valor, 5) + " ] ";
                }

                p.at("Cruzamento", frase);

                //   ENTT.EXIBIR_TABELA(disp);
            }

        }

        return cruzamento;
    }


    public static Lista<Entidade> DESCOLCHETEAR(Entidade item, String atributoNome, String nomeAlfa, String nomeBeta) {
        Lista<Entidade> dados = new Lista<Entidade>();

        String valor = item.at(atributoNome);

        for (String i : Strings.GET_ENTRE_COLCHETES_VARIOS(valor)) {
            Entidade a = new Entidade();
            a.at(nomeAlfa, Strings.GET_ATE(i, "=").trim());
            a.at(nomeBeta, Strings.GET_DEPOIS(i, "=").trim());
            dados.adicionar(a);
        }

        return dados;
    }

    public static Lista<Entidade> DESCOLCHETEAR_COMPLEMENTAR(Entidade item, String atributoNome, String nomeAlfa, String nomeBeta) {
        Lista<Entidade> dados = new Lista<Entidade>();

        String valor = item.at(atributoNome);

        for (String i : Strings.GET_ENTRE_COLCHETES_VARIOS(valor)) {
            Entidade a = new Entidade();

            for (Tag t : item.tags()) {
                if (!t.is_nome(atributoNome)) {
                    a.at(t.getNome(), t.getValor());
                }
            }

            a.at(nomeAlfa, Strings.GET_ATE(i, "=").trim());
            a.at(nomeBeta, Strings.GET_DEPOIS(i, "=").trim());
            dados.adicionar(a);
        }

        return dados;
    }

    public static Lista<Entidade> DESCOLCHETEAR_COMPLEMENTAR(Lista<Entidade> itens, String atributoNome, String nomeAlfa, String nomeBeta) {

        Lista<Entidade> dados = new Lista<Entidade>();

        for (Entidade item : itens) {
            dados.adicionar_varios(DESCOLCHETEAR_COMPLEMENTAR(item, atributoNome, nomeAlfa, nomeBeta));
        }

        return dados;
    }


    public static Lista<Entidade> INVERTER(Lista<Entidade> dados) {

        Lista<Entidade> saida = new Lista<Entidade>();

        while (dados.getQuantidade() > 0) {
            saida.adicionar(ENTT.GET_ULTIMO(dados));
            ENTT.REMOVER_ULTIMA_LINHA(dados);
        }

        return saida;
    }


}
