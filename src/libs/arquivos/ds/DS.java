package libs.arquivos.ds;

import libs.arquivos.TX;
import libs.arquivos.binario.Arquivador;
import libs.arquivos.binario.ByteChunk;
import libs.arquivos.binario.ByteChunker;
import libs.arquivos.binario.Inteiro;
import libs.entt.Entidade;
import libs.luan.Lista;
import libs.luan.Opcional;
import libs.luan.Strings;
import libs.luan.fmt;

import java.nio.charset.StandardCharsets;

public class DS {

    // AUTOR       : LUAN FREITAS
    // DATA        : 2023 04 17

    // ATUALIZACAO : 2024 08 28 - ITEM PRE ALOCADO
    // ATUALIZACAO : 2024 10 20 - ADICIONAR ARQUIVO

    public static void limpar(String eArquivo) {
        Arquivador.remover(eArquivo);
    }

    public static void adicionar(String eArquivo, String eNome, byte[] eDados) {


        Arquivador arquivar = new Arquivador(eArquivo);

        long t = arquivar.getLength();

        if (t == 0) {
            arquivar.setPonteiro(0);
            arquivar.set_u8((byte) 100);
            arquivar.set_u8((byte) 120);
        }

        arquivar.setPonteiro(arquivar.getLength());


        // adicionar novo item
        arquivar.set_u8((byte) 255);
        arquivar.set_u64((long) eDados.length);

        long antes = arquivar.getPonteiro();

        for (int z = 0; z < 1024; z++) {
            arquivar.set_u8((byte) 0);
        }

        long depois = arquivar.getPonteiro();

        arquivar.setPonteiro(antes);

        Lista<Byte> bytes_nome = TX.toListBytes(eNome);

        if (bytes_nome.getQuantidade() < 1024) {

            for (byte b : bytes_nome) {
                arquivar.set_u8(b);
            }

        }


        arquivar.setPonteiro(depois);

        int i = 0;
        int o = eDados.length;

        while (i < o) {
            arquivar.set_u8(eDados[i]);
            i += 1;
        }


        arquivar.encerrar();

    }

    public static void adicionar(String eArquivo, String eNome, Lista<Byte> eDados) {


        Arquivador arquivar = new Arquivador(eArquivo);

        long t = arquivar.getLength();

        if (t == 0) {
            arquivar.setPonteiro(0);
            arquivar.set_u8((byte) 100);
            arquivar.set_u8((byte) 120);
        }

        arquivar.setPonteiro(arquivar.getLength());


        // adicionar novo item
        arquivar.set_u8((byte) 255);
        arquivar.set_u64((long) eDados.getQuantidade());

        long antes = arquivar.getPonteiro();

        for (int z = 0; z < 1024; z++) {
            arquivar.set_u8((byte) 0);
        }

        long depois = arquivar.getPonteiro();

        arquivar.setPonteiro(antes);

        Lista<Byte> bytes_nome = TX.toListBytes(eNome);

        if (bytes_nome.getQuantidade() < 1024) {

            for (byte b : bytes_nome) {
                arquivar.set_u8(b);
            }

        }


        arquivar.setPonteiro(depois);

        int i = 0;
        int o = eDados.getQuantidade();

        while (i < o) {
            arquivar.set_u8(eDados.get(i));
            i += 1;
        }


        arquivar.encerrar();

    }

    public static void adicionar(String eArquivo, String eNome, String dados) {
        adicionar(eArquivo, eNome, dados.getBytes(StandardCharsets.UTF_8));
    }

    public static void adicionar(String eArquivo, String eNome, ByteChunker eDados) {


        Arquivador arquivar = new Arquivador(eArquivo);

        long t = arquivar.getLength();

        if (t == 0) {
            arquivar.setPonteiro(0);
            arquivar.set_u8((byte) 100);
            arquivar.set_u8((byte) 120);
        }

        arquivar.setPonteiro(arquivar.getLength());


        // adicionar novo item
        arquivar.set_u8((byte) 255);
        arquivar.set_u64((long) eDados.getTamanho());

        long antes = arquivar.getPonteiro();

        for (int z = 0; z < 1024; z++) {
            arquivar.set_u8((byte) 0);
        }

        long depois = arquivar.getPonteiro();

        arquivar.setPonteiro(antes);

        Lista<Byte> bytes_nome = TX.toListBytes(eNome);

        if (bytes_nome.getQuantidade() < 1024) {

            for (byte b : bytes_nome) {
                arquivar.set_u8(b);
            }

        }


        arquivar.setPonteiro(depois);

        long i = 0;
        long o = eDados.getTamanho();

        for(ByteChunk chunk : eDados.getChunks()){
            for(Byte b : chunk.getChunk()){
                if(i<o){
                    arquivar.set_u8(b);
                }
                i += 1;
            }
        }


        arquivar.encerrar();

    }


    public static void adicionar_pre_alocado(String eArquivo, String eNome, int pre_alocado) {

        pre_alocado = pre_alocado + 9;

        Arquivador arquivar = new Arquivador(eArquivo);

        long t = arquivar.getLength();

        if (t == 0) {
            arquivar.setPonteiro(0);
            arquivar.set_u8((byte) 100);
            arquivar.set_u8((byte) 120);
        }

        arquivar.setPonteiro(arquivar.getLength());


        // adicionar novo item
        arquivar.set_u8((byte) 111);
        arquivar.set_u64((long) pre_alocado);

        long antes = arquivar.getPonteiro();

        for (int z = 0; z < 1024; z++) {
            arquivar.set_u8((byte) 0);
        }

        long depois = arquivar.getPonteiro();

        arquivar.setPonteiro(antes);

        Lista<Byte> bytes_nome = TX.toListBytes(eNome);

        if (bytes_nome.getQuantidade() < 1024) {

            for (byte b : bytes_nome) {
                arquivar.set_u8(b);
            }

        }


        arquivar.setPonteiro(depois);
        arquivar.set_u8((byte) 200);

        int i = 0;
        int o = pre_alocado - 1;

        while (i < o) {
            arquivar.set_u8((byte) 0);
            i += 1;
        }


        arquivar.encerrar();

    }


    public static Lista<DSItem> ler_todos(String eArquivo) {

        Lista<DSItem> itens = new Lista<DSItem>();

        Arquivador arquivar = new Arquivador(eArquivo);

        // System.out.println("Tamanho :: " + arquivar.getLength());

        arquivar.setPonteiro(0);

        int b1 = Inteiro.byteToInt(arquivar.get());
        int b2 = Inteiro.byteToInt(arquivar.get());

        //  System.out.println("B1 :: " + b1);
        // System.out.println("B2 :: " + b2);

        int status = Inteiro.byteToInt(arquivar.get());

        long t = arquivar.getLength();

        int index = 0;

        while ((status == 255 || status == 111) && arquivar.getPonteiro() < t) {

            long item_tamanho = arquivar.get_u64();


            long comecar_nome = arquivar.getPonteiro();

            TX eTX = new TX();
            String nome = eTX.lerFluxoLimitado(arquivar, 1024);

            arquivar.setPonteiro(comecar_nome + 1024);

            long antes = arquivar.getPonteiro();

            itens.adicionar(new DSItem(eArquivo, index, status, nome, item_tamanho, antes));

            arquivar.setPonteiro(arquivar.getPonteiro() + item_tamanho);
            status = Inteiro.byteToInt(arquivar.get());

            index += 1;

        }

        arquivar.encerrar();

        return itens;
    }

    public static Lista<DSItem> ler_alguns(String eArquivo, int eInicio, int eQuantidade) {

        Lista<DSItem> itens = new Lista<DSItem>();

        Arquivador arquivar = new Arquivador(eArquivo);

        // System.out.println("Tamanho :: " + arquivar.getLength());

        arquivar.setPonteiro(0);

        int b1 = Inteiro.byteToInt(arquivar.get());
        int b2 = Inteiro.byteToInt(arquivar.get());

        //  System.out.println("B1 :: " + b1);
        // System.out.println("B2 :: " + b2);

        int status = Inteiro.byteToInt(arquivar.get());

        long t = arquivar.getLength();

        int index = 0;
        int quantidade = 0;

        while ((status == 255 || status == 111) && arquivar.getPonteiro() < t) {

            long item_tamanho = arquivar.get_u64();


            long comecar_nome = arquivar.getPonteiro();

            TX eTX = new TX();
            String nome = eTX.lerFluxoLimitado(arquivar, 1024);

            arquivar.setPonteiro(comecar_nome + 1024);

            long antes = arquivar.getPonteiro();

            if (index >= eInicio && quantidade < eQuantidade) {
                itens.adicionar(new DSItem(eArquivo, index, status, nome, item_tamanho, antes));
                quantidade += 1;
            }

            arquivar.setPonteiro(arquivar.getPonteiro() + item_tamanho);
            status = Inteiro.byteToInt(arquivar.get());

            index += 1;

        }

        arquivar.encerrar();

        return itens;
    }


    public static Opcional<DSItem> buscar_item(String eArquivo, String eNome) {

        Opcional<DSItem> ret = new Opcional<DSItem>();


        Arquivador arquivar = new Arquivador(eArquivo);

        //  System.out.println("Tamanho :: " + arquivar.getLength());

        arquivar.setPonteiro(0);

        int b1 = Inteiro.byteToInt(arquivar.get());
        int b2 = Inteiro.byteToInt(arquivar.get());

        //  System.out.println("B1 :: " + b1);
        //   System.out.println("B2 :: " + b2);

        int status = Inteiro.byteToInt(arquivar.get());

        long t = arquivar.getLength();

        int index = 0;

        while ((status == 255 || status == 111) && arquivar.getPonteiro() < t) {

            long item_tamanho = arquivar.get_u64();


            long comecar_nome = arquivar.getPonteiro();

            TX eTX = new TX();
            String nome = eTX.lerFluxoLimitado(arquivar, 1024);

            arquivar.setPonteiro(comecar_nome + 1024);

            long antes = arquivar.getPonteiro();


            if (nome.contentEquals(eNome)) {
                ret.set(new DSItem(eArquivo, index, status, nome, item_tamanho, antes));
                break;
            }

            arquivar.setPonteiro(arquivar.getPonteiro() + item_tamanho);
            status = Inteiro.byteToInt(arquivar.get());

            index += 1;

        }

        arquivar.encerrar();

        return ret;
    }


    public static int contar(String eArquivo) {

        int contando = 0;


        Arquivador arquivar = new Arquivador(eArquivo);

        //  System.out.println("Tamanho :: " + arquivar.getLength());

        arquivar.setPonteiro(0);

        int b1 = Inteiro.byteToInt(arquivar.get());
        int b2 = Inteiro.byteToInt(arquivar.get());

        //  System.out.println("B1 :: " + b1);
        //   System.out.println("B2 :: " + b2);

        int status = Inteiro.byteToInt(arquivar.get());

        long t = arquivar.getLength();

        int index = 0;

        while ((status == 255 || status == 111) && arquivar.getPonteiro() < t) {

            long item_tamanho = arquivar.get_u64();


            long comecar_nome = arquivar.getPonteiro();

            //  TX eTX = new TX();
            //  String nome = eTX.lerFluxoLimitado(arquivar, 1024);

            arquivar.setPonteiro(comecar_nome + 1024);

            // long antes = arquivar.getPonteiro();

            contando += 1;

            arquivar.setPonteiro(arquivar.getPonteiro() + item_tamanho);
            status = Inteiro.byteToInt(arquivar.get());

            index += 1;

        }

        arquivar.encerrar();

        return contando;
    }


    public static Opcional<DSItem> ler_item(String eArquivo, int chave) {

        Opcional<DSItem> ret = new Opcional<DSItem>();

        Lista<DSItem> itens = DS.ler_todos(eArquivo);
        for (DSItem item : itens) {
            if (item.getIndex() == chave) {
                ret.set(item);
                break;
            }
        }

        return ret;
    }


    public static Lista<DSItem> separar(String prefixo, Lista<DSItem> itens) {

        Lista<DSItem> guardando = new Lista<DSItem>();

        for (DSItem item : itens) {
            if (item.getNome().startsWith(prefixo)) {
                guardando.adicionar(item);
            }
        }

        return guardando;

    }


    public static void indexar(String eArquivoDados, String ePrefixo, String eArquivoIndex) {

        Arquivador.remover(eArquivoIndex);

        Lista<DSItem> guardando = new Lista<DSItem>();

        for (DSItem item : ler_todos(eArquivoDados)) {
            if (item.getNome().startsWith(ePrefixo)) {
                guardando.adicionar(item);
            }
        }


        int maior = 0;

        for (DSItem item : guardando) {

            String sID = item.getNome().replace(ePrefixo, "");
            if (!sID.isEmpty()) {
                int iID = Integer.parseInt(sID);

                if (iID > maior) {
                    maior = iID;
                }

            }
        }


        Arquivador arquivar = new Arquivador(eArquivoIndex);

        arquivar.setPonteiro(0);
        arquivar.set_u8((byte) 52);
        arquivar.set_u8((byte) 53);

        long antes = arquivar.getPonteiro();


        for (int i = 0; i < maior + 1; i++) {
            arquivar.set_u64(0);
            arquivar.set_u64(0);
        }


        for (DSItem item : guardando) {

            String sID = item.getNome().replace(ePrefixo, "");

            if (!sID.isEmpty()) {
                int iID = Integer.parseInt(sID);

                arquivar.setPonteiro(antes + (long) (iID * (16L)));
                arquivar.set_u64(item.getInicio());
                arquivar.set_u64(item.getTamanho());

            }
        }


        arquivar.encerrar();


    }


    public static void atualizar_pre_alocado(DSItem item_pre_alocado, Lista<Byte> bytes) {

        //    fmt.print("Iniciar Atualizacao Pre Alloc :: "+bytes.getQuantidade());

        long tamanho = item_pre_alocado.getTamanho() - 9;

        if (bytes.getQuantidade() < tamanho) {
            long pt_inicio_dados = item_pre_alocado.getInicio();

            Arquivador aquivador = new Arquivador(item_pre_alocado.getArquivo());
            aquivador.setPonteiro(pt_inicio_dados);

            int valor = aquivador.get_u8();

//fmt.print("Atualizar com 200");

            if (valor == 200) {
                aquivador.set_u64(bytes.getQuantidade());

                //  fmt.print("Atualizar com sv :: "+bytes.getQuantidade());

                for (Byte b : bytes) {
                    aquivador.set_u8(b);
                }
            }

            aquivador.encerrar();

            if (valor != 200) {
                throw new RuntimeException("Esse item não pôde ser atualizado porque não foi pré-alocado !");
            }

        } else {
            throw new RuntimeException("Tamanho superior ao item pre alocado !");
        }

    }


    public static boolean alterar_pre_alocado(String eArquivo, String eNome, String dados) {
        return alterar_pre_alocado(eArquivo, eNome, dados.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean alterar_pre_alocado(String eArquivo, String eNome, byte[] bytes) {


        Arquivador arquivar = new Arquivador(eArquivo);

        //  System.out.println("Tamanho :: " + arquivar.getLength());

        arquivar.setPonteiro(0);

        int b1 = Inteiro.byteToInt(arquivar.get());
        int b2 = Inteiro.byteToInt(arquivar.get());

        //  System.out.println("B1 :: " + b1);
        //   System.out.println("B2 :: " + b2);

        int status = Inteiro.byteToInt(arquivar.get());

        long t = arquivar.getLength();

        int index = 0;

        boolean pre_alocado_encontrado = false;
        long pre_alocado_ponteiro_dados = 0;
        long pre_alocado_ponteiro_tamanho = 0;


        while ((status == 255 || status == 111) && arquivar.getPonteiro() < t) {

            long item_tamanho = arquivar.get_u64();


            long comecar_nome = arquivar.getPonteiro();

            TX eTX = new TX();
            String nome = eTX.lerFluxoLimitado(arquivar, 1024);

            arquivar.setPonteiro(comecar_nome + 1024);

            long antes = arquivar.getPonteiro();


            if (status == 111 && nome.contentEquals(eNome)) {
                //    ret.set(new DSItem(eArquivo, index, status, nome, item_tamanho, antes));
                pre_alocado_encontrado = true;
                pre_alocado_ponteiro_dados = antes;
                pre_alocado_ponteiro_tamanho = item_tamanho;
                break;
            }

            arquivar.setPonteiro(arquivar.getPonteiro() + item_tamanho);
            status = Inteiro.byteToInt(arquivar.get());

            index += 1;

        }

        if (pre_alocado_encontrado) {

          //  fmt.print("Atualizar pre_alocado !");

            if ((bytes.length + 10) <= pre_alocado_ponteiro_tamanho) {

                arquivar.setPonteiro(pre_alocado_ponteiro_dados);
                arquivar.set_u64(bytes.length);
                arquivar.setPonteiro(pre_alocado_ponteiro_dados + 10);

                int i = 0;
                int o = bytes.length;

                while (i < o) {
                    arquivar.set_u8(bytes[i]);
                    i += 1;
                }

            //    fmt.print("DADOS EM {} -->> {} bytes ", pre_alocado_ponteiro_dados, o);

            }

        }


        arquivar.encerrar();

        return pre_alocado_encontrado;
    }

    public static byte[] obter_pre_alocado(String eArquivo, String eNome) {

        byte[] bytes = null;

        Arquivador arquivar = new Arquivador(eArquivo);

        //  System.out.println("Tamanho :: " + arquivar.getLength());

        arquivar.setPonteiro(0);

        int b1 = Inteiro.byteToInt(arquivar.get());
        int b2 = Inteiro.byteToInt(arquivar.get());

        //  System.out.println("B1 :: " + b1);
        //   System.out.println("B2 :: " + b2);

        int status = Inteiro.byteToInt(arquivar.get());

        long t = arquivar.getLength();

        int index = 0;

        boolean pre_alocado_encontrado = false;
        long pre_alocado_ponteiro_dados = 0;
        long pre_alocado_ponteiro_tamanho = 0;


        while ((status == 255 || status == 111) && arquivar.getPonteiro() < t) {

            long item_tamanho = arquivar.get_u64();


            long comecar_nome = arquivar.getPonteiro();

            TX eTX = new TX();
            String nome = eTX.lerFluxoLimitado(arquivar, 1024);

            arquivar.setPonteiro(comecar_nome + 1024);

            long antes = arquivar.getPonteiro();


            if (nome.contentEquals(eNome)) {
                //ret.set(new DSItem(eArquivo, index, status, nome, item_tamanho, antes));
                pre_alocado_encontrado = true;
                pre_alocado_ponteiro_dados = antes;
                pre_alocado_ponteiro_tamanho = item_tamanho;
                break;
            }

            arquivar.setPonteiro(arquivar.getPonteiro() + item_tamanho);
            status = Inteiro.byteToInt(arquivar.get());

            index += 1;

        }

        if (pre_alocado_encontrado) {


            arquivar.setPonteiro(pre_alocado_ponteiro_dados);

            long bloco_tamanho = arquivar.get_u64();


            arquivar.setPonteiro(pre_alocado_ponteiro_dados + 10);

            bytes = new byte[(int) bloco_tamanho];
            int i = 0;

            while (i < bloco_tamanho) {
                bytes[i] = arquivar.get();
                i += 1;
            }


        }


        arquivar.encerrar();

        return bytes;
    }

    public static String obter_pre_alocado_texto(String eArquivo, String eNome) {
        return new String(obter_pre_alocado(eArquivo, eNome), StandardCharsets.UTF_8);
    }

    public static long obter_tamanho_usado_pre_alocado(String eArquivo, String eNome) {

        long tamanho_usado = 0;

        Arquivador arquivar = new Arquivador(eArquivo);

        //  System.out.println("Tamanho :: " + arquivar.getLength());

        arquivar.setPonteiro(0);

        int b1 = Inteiro.byteToInt(arquivar.get());
        int b2 = Inteiro.byteToInt(arquivar.get());

        //  System.out.println("B1 :: " + b1);
        //   System.out.println("B2 :: " + b2);

        int status = Inteiro.byteToInt(arquivar.get());

        long t = arquivar.getLength();

        int index = 0;

        boolean pre_alocado_encontrado = false;
        long pre_alocado_ponteiro_dados = 0;
        long pre_alocado_ponteiro_tamanho = 0;


        while ((status == 255 || status == 111) && arquivar.getPonteiro() < t) {

            long item_tamanho = arquivar.get_u64();


            long comecar_nome = arquivar.getPonteiro();

            TX eTX = new TX();
            String nome = eTX.lerFluxoLimitado(arquivar, 1024);

            arquivar.setPonteiro(comecar_nome + 1024);

            long antes = arquivar.getPonteiro();


            if (nome.contentEquals(eNome)) {
                //ret.set(new DSItem(eArquivo, index, status, nome, item_tamanho, antes));
                pre_alocado_encontrado = true;
                pre_alocado_ponteiro_dados = antes;
                pre_alocado_ponteiro_tamanho = item_tamanho;
                break;
            }

            arquivar.setPonteiro(arquivar.getPonteiro() + item_tamanho);
            status = Inteiro.byteToInt(arquivar.get());

            index += 1;

        }

        if (pre_alocado_encontrado) {

            arquivar.setPonteiro(pre_alocado_ponteiro_dados);
            long bloco_tamanho = arquivar.get_u64();
            tamanho_usado=bloco_tamanho;
        }


        arquivar.encerrar();

        return tamanho_usado;
    }



    public static void adicionar_arquivo(String eArquivo, String eNome, String eArquivoAdicionar) {


        Arquivador dados_obter = new Arquivador(eArquivoAdicionar);
        dados_obter.setPonteiro(0);
        long dados_tamanho = dados_obter.getLength();


        Arquivador arquivar = new Arquivador(eArquivo);

        long t = arquivar.getLength();

        if (t == 0) {
            arquivar.setPonteiro(0);
            arquivar.set_u8((byte) 100);
            arquivar.set_u8((byte) 120);
        }

        arquivar.setPonteiro(arquivar.getLength());


        // adicionar novo item
        arquivar.set_u8((byte) 255);
        arquivar.set_u64((long) dados_tamanho);

        long antes = arquivar.getPonteiro();

        for (int z = 0; z < 1024; z++) {
            arquivar.set_u8((byte) 0);
        }

        long depois = arquivar.getPonteiro();

        arquivar.setPonteiro(antes);

        Lista<Byte> bytes_nome = TX.toListBytes(eNome);

        if (bytes_nome.getQuantidade() < 1024) {

            for (byte b : bytes_nome) {
                arquivar.set_u8(b);
            }

        }


        arquivar.setPonteiro(depois);

        long guardar = dados_obter.getLength();
        int TAMANHO_TRANSFERIR = 4*1024;

        while (guardar >= TAMANHO_TRANSFERIR) {

            byte[] bloco_transferindo = dados_obter.get_u8_array(TAMANHO_TRANSFERIR);
            arquivar.set_u8_array(bloco_transferindo);

            guardar -= TAMANHO_TRANSFERIR;
        }

        if (guardar > 0) {
            byte[] bloco_ultimo = dados_obter.get_u8_array((int) guardar);
            arquivar.set_u8_array(bloco_ultimo);
        }


        dados_obter.encerrar();

        arquivar.encerrar();

    }


    public static void DUMP_TABELA(String eArquivo) {


        Lista<DSItem> itens = DS.ler_todos(eArquivo);

        DUMP_ITENS(itens);


    }

    public static void DUMP_ITENS(Lista<DSItem> itens){

        int tamanho_nome = 0;

        for (DSItem item : itens) {
            if (item.getNome().length() > tamanho_nome) {
                tamanho_nome = item.getNome().length();
            }
        }

        tamanho_nome+=15;

        String cabecalho_fmt = "| {dir15} | {dir" + tamanho_nome + "} | {dir10} | {dir10} | {dir20} |{esq20} |";
        String linha_fmt = "| {dir15} | {dir" + tamanho_nome + "} | {dir10} | {dir10} | {esq20} |{esq20} |";

        fmt.print("{}",fmt.repetir("-",tamanho_nome+73+20));
        fmt.print(cabecalho_fmt,"Status","Nome","Início","Fim","Tamanho","Tamanho Usado");
        fmt.print("{}",fmt.repetir("-",tamanho_nome+73+20));

        for (DSItem item : itens) {
            if(item.getStatus()==111){
                fmt.print(linha_fmt, TIPO_ITEM(item.getStatus()),item.getNome(), item.getInicio(), item.getInicio() + item.getTamanho(), fmt.formatar_tamanho_precisao_dupla(item.getTamanho()),fmt.formatar_tamanho_precisao_dupla(item.getTamanhoUtilizadoPreAlocado()));
            }else{
                fmt.print(linha_fmt, TIPO_ITEM(item.getStatus()),item.getNome(), item.getInicio(), item.getInicio() + item.getTamanho(), fmt.formatar_tamanho_precisao_dupla(item.getTamanho()),fmt.formatar_tamanho_precisao_dupla(item.getTamanho()));
            }
        }

        fmt.print("{}",fmt.repetir("-",tamanho_nome+73+20));

    }


    public static String TIPO_ITEM(int valor){
        if(valor==111){
            return "PRE - ALOCADO";
        }else if(valor==255){
            return "ITEM";
        }else{
            return "DESCONHECIDO";
        }
    }


    public static void DUMP_TABELA_COM_CONTEUDO(String eArquivo) {


        Lista<DSItem> itens = DS.ler_todos(eArquivo);

        DUMP_ITENS_CONTEUDO(itens);


    }

    public static void DUMP_ITENS_CONTEUDO(Lista<DSItem> itens){

        int tamanho_nome = 0;

        for (DSItem item : itens) {
            if (item.getNome().length() > tamanho_nome) {
                tamanho_nome = item.getNome().length();
            }
        }

        tamanho_nome+=15;

        String cabecalho_fmt = "| {dir" + tamanho_nome + "} | {dir10} | {dir10} | {dir18} | {dir30}|";
        String linha_fmt = "| {dir" + tamanho_nome + "} | {dir10} | {dir10} | {esq18} | {dir30}|";

        fmt.print("{}",fmt.repetir("-",tamanho_nome+35+16+32));
        fmt.print(cabecalho_fmt,"Nome","Início","Fim","Tamanho","Conteudo");
        fmt.print("{}",fmt.repetir("-",tamanho_nome+35+16+32));

        for (DSItem item : itens) {
            fmt.print(linha_fmt, item.getNome(), item.getInicio(), item.getInicio() + item.getTamanho(), fmt.formatar_tamanho_precisao_dupla(item.getTamanho()),item.getTexto());
        }

        fmt.print("{}",fmt.repetir("-",tamanho_nome+35+16+32));

    }

    public static void DUMP_ITENS_CONTEUDO_LINEARIZADO(Lista<DSItem> itens){

        int tamanho_nome = 0;

        for (DSItem item : itens) {
            if (item.getNome().length() > tamanho_nome) {
                tamanho_nome = item.getNome().length();
            }
        }

        tamanho_nome+=15;

        String cabecalho_fmt = "| {dir" + tamanho_nome + "} | {dir10} | {dir10} | {dir18} | {dir30}|";
        String linha_fmt = "| {dir" + tamanho_nome + "} | {dir10} | {dir10} | {esq18} | {dir30}|";

        fmt.print("{}",fmt.repetir("-",tamanho_nome+35+16+32));
        fmt.print(cabecalho_fmt,"Nome","Início","Fim","Tamanho","Conteudo");
        fmt.print("{}",fmt.repetir("-",tamanho_nome+35+16+32));

        for (DSItem item : itens) {
            fmt.print(linha_fmt, item.getNome(), item.getInicio(), item.getInicio() + item.getTamanho(), fmt.formatar_tamanho_precisao_dupla(item.getTamanho()), Strings.LINEARIZAR(item.getTexto()));
        }

        fmt.print("{}",fmt.repetir("-",tamanho_nome+35+16+32));

    }


    public static void substituir_ultimo(String eArquivo, String eNome,String texto) {
        substituir_ultimo(eArquivo,eNome,texto.getBytes(StandardCharsets.UTF_8));
    }

        public static void substituir_ultimo(String eArquivo, String eNome, byte[] eDados) {



        Lista<DSItem> itens = ler_todos(eArquivo);

        long ponteiro = 0;

        if(itens.possuiObjetos()){
            ponteiro = itens.get(itens.getQuantidade()-1).getInicio() - (1024+8+1);
        }

        Arquivador arquivar = new Arquivador(eArquivo);

        long t = arquivar.getLength();

        if (t == 0) {
            arquivar.setPonteiro(0);
            arquivar.set_u8((byte) 100);
            arquivar.set_u8((byte) 120);
            ponteiro=arquivar.getPonteiro();
        }

        arquivar.setPonteiro(ponteiro);


        // adicionar novo item
        arquivar.set_u8((byte) 255);
        arquivar.set_u64((long) eDados.length);

        long antes = arquivar.getPonteiro();

        for (int z = 0; z < 1024; z++) {
            arquivar.set_u8((byte) 0);
        }

        long depois = arquivar.getPonteiro();

        arquivar.setPonteiro(antes);

        Lista<Byte> bytes_nome = TX.toListBytes(eNome);

        if (bytes_nome.getQuantidade() < 1024) {

            for (byte b : bytes_nome) {
                arquivar.set_u8(b);
            }

        }


        arquivar.setPonteiro(depois);

        int i = 0;
        int o = eDados.length;

        while (i < o) {
            arquivar.set_u8(eDados[i]);
            i += 1;
        }


        arquivar.encerrar();

    }



}
