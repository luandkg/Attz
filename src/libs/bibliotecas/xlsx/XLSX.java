package libs.bibliotecas.xlsx;

import libs.arquivos.DocumentoTexto;
import libs.arquivos.Zipper;
import libs.luan.*;
import libs.bibliotecas.xml.XML;
import libs.bibliotecas.xml.XMLObjeto;

import java.util.ArrayList;


public class XLSX {

    private Lista<String> mSharedStrings;
    private Lista<Planilha> mPlanilhas;


    public XLSX(String eArquivo) {

        mSharedStrings = new Lista<String>();
        mPlanilhas = new Lista<Planilha>();

        Lista<DocumentoTexto> documentos = new Lista<DocumentoTexto>();
        ArrayList<DocumentoTexto> planilhas_titulos = new ArrayList<DocumentoTexto>();

        documentos = Zipper.EXTRAIR_DOCUMENTOS_COM_EXTENSAO(eArquivo, ".xml");

        for (DocumentoTexto doc : documentos) {
//            System.out.println(doc.getNome() + " :: " + doc.getConteudo().length());
        }

        Procurador<DocumentoTexto> documentos_procurador = new Procurador<DocumentoTexto>(new IgualdadeTiposDiferentes<String, DocumentoTexto>() {
            @Override
            public boolean isIgual(String chave, DocumentoTexto valor) {
                return valor.getNome().contentEquals(chave);
            }
        });

        Opcional<DocumentoTexto> doc_sharedStrings = documentos_procurador.procure("xl/sharedStrings.xml", documentos);
        Opcional<DocumentoTexto> doc_workbook = documentos_procurador.procure("xl/workbook.xml", documentos);

        if (doc_sharedStrings.isOK()) {

            //  System.out.println("SHARED STRINGS");
            // System.out.println(doc.getConteudo());

            XML xml = XML.PARSER_XML(doc_sharedStrings.get().getConteudo());
            // xml.exibir();

            for (XMLObjeto obj : xml.getObjeto("sst").getObjetos()) {
                mSharedStrings.adicionar(obj.getObjeto("t").getConteudo());
            }

        }

        if (doc_workbook.isOK()) {

            //   System.out.println("WORK BOOK");
            //  System.out.println(doc.getConteudo());

            XML xml = XML.PARSER_XML(doc_workbook.get().getConteudo());
            //      xml.exibir();

            int id = 0;

            for (XMLObjeto obj : xml.getObjeto("workbook").getObjeto("sheets").getObjetos()) {
                planilhas_titulos.add(new DocumentoTexto(String.valueOf(id), obj.atributo("name").getValor()));
                id += 1;
            }

        }


        int planilha_id = 0;


        for (DocumentoTexto doc : documentos) {
            if (doc.getNome().startsWith("xl/worksheets/sheet")) {

                XML xml = XML.PARSER_XML(doc.getConteudo());
                //  xml.exibir();

                Planilha planilha_corrente = new Planilha();

                String sID = String.valueOf(planilha_id);

                for (DocumentoTexto pt : planilhas_titulos) {
                    if (pt.getNome().contentEquals(sID)) {
                        planilha_corrente.setTitulo(pt.getConteudo());
                        break;
                    }
                }


                planilha_id += 1;

                int linha_corrente = 1;
                for (XMLObjeto obj : xml.getObjeto("worksheet").getObjeto("sheetData").getObjetos()) {

                    PlanilhaLinha linha = new PlanilhaLinha();

                   // fmt.print(">> nova linha ");
                  //  obj.exibir();

                    boolean primeiro = true;

                    for (XMLObjeto coluna : obj.getObjetos()) {
                       // fmt.print("\t ++ ");
                      //  coluna.exibir();

                        if (primeiro) {

                            String valor_r = coluna.atributo("r").getValor();
                            if (valor_r.length() > 0) {
                                String primeira = String.valueOf(valor_r.charAt(0)).toUpperCase();
                               // fmt.print("\t ++ PRIMEIRA :: {}",primeira);

                                if(Strings.isDiferente(primeira,"A")){

                                    int posicao = Strings.GET_POSICAO("ABCDEFGHIJKLMNOPQRSTUVWXYZ",primeira);
                                    int pi = 0;
                                    while(pi<posicao){
                                        linha.adicionar("");
                                        pi+=1;
                                    }

                                }

                            }

                            primeiro = false;
                        }


                        String tipo = coluna.atributo("t").getValor();
                        String valor = coluna.getObjeto("v").getConteudo();

                        if (tipo.contentEquals("s")) {
                            linha.adicionar(mSharedStrings.get(Integer.parseInt(valor)));
                        } else {
                            linha.adicionar(valor);
                        }


                    }

                    planilha_corrente.adicionar(linha);

                    linha_corrente += 1;
                }

                mPlanilhas.adicionar(planilha_corrente);
            }
        }


    }

    public void exibir_ss() {
        int si = 0;
        for (String ss : mSharedStrings) {
            System.out.println("\t -->> SS(" + si + ") = " + ss);
            si += 1;
        }
    }

    public void exibirTudo() {

        for (Planilha planilha : mPlanilhas) {

            fmt.print("$$ Planilha :: " + planilha.getTitulo() + " ( " + planilha.maxLinhas() + " :: " + planilha.maxColunas() + " )");

            for (PlanilhaLinha linha : planilha.getLinhas()) {
                fmt.print(linha.getString());
            }

        }

    }

    public void exibirNomes() {

        for (Planilha planilha : mPlanilhas) {
            fmt.print("$$ Planilha :: " + planilha.getTitulo() + " ( " + planilha.maxLinhas() + " :: " + planilha.maxColunas() + " ) -- " + planilha.getLinhas().getQuantidade());
        }

    }

    public Lista<Planilha> getPlanilhas() {
        return mPlanilhas;
    }

}
