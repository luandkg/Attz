package libs.luan;

import libs.arquivos.binario.Arquivador;
import libs.fs.Arquivo;
import libs.fs.PastaFS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

public class FS {

    private ArrayList<String> mRemover;

    public FS() {
        mRemover = new ArrayList<String>();
    }

    public ArrayList<String> getRemover() {
        return mRemover;
    }

    public void limpar() {
        removerArquivos(getRemover());
    }

    public static void organizar_pasta(String pasta_caminho) {

        File pastinha = new File(pasta_caminho);
        if (!pastinha.exists()) {
            pastinha.mkdir();
        }

    }

    public static void removerArquivo(String arquivo) {
        if (new File(arquivo).exists()) {
            new File(arquivo).delete();
        }
    }

    public static void removerArquivos(ArrayList<String> arquivos) {
        for (String arquivo : arquivos) {

            if (new File(arquivo).exists()) {
                new File(arquivo).delete();
            }

        }

    }


    public static String GET_ARQUIVO(String pasta_caminho, String arquivo) {
        return pasta_caminho + arquivo;
    }

    public static String GET_NOME_DO_ARQUIVO(String arquivo_caminho) {
        return new File(arquivo_caminho).getName();
    }

    public static String GET_PASTA_DOCUMENTOS() {
        return System.getProperty("user.dir") + "/../";
    }

    public static String GET_PASTA_DOCUMENTOS(String sub_pasta) {
        String st = System.getProperty("user.dir") + "/../" + sub_pasta;
        if (!st.endsWith("/")) {
            st += "/";
        }
        return st;
    }

    public static boolean arquivo_existe(String arquivo) {
        return new File(arquivo).exists();
    }

    public static void criar_arquivo_se_nao_existir(String arquivo) {
        if (!arquivo_existe(arquivo)) {
            Texto.arquivo_escrever(arquivo, "");
        }
    }


    public static ArrayList<String> lista_de_arquivos_total(String pasta_local) {

        ArrayList<String> lista = new ArrayList<String>();

        for (File item : new File(pasta_local).listFiles()) {

            if (item.isDirectory()) {
                lista_de_arquivos_total(item.getAbsolutePath(), lista);
            } else if (item.isFile()) {
                lista.add(item.getAbsolutePath());
            }

        }

        return lista;
    }

    public static void lista_de_arquivos_total(String pasta_local, ArrayList<String> lista) {


        for (File item : new File(pasta_local).listFiles()) {

            if (item.isDirectory()) {
                lista_de_arquivos_total(item.getAbsolutePath(), lista);
            } else if (item.isFile()) {
                lista.add(item.getAbsolutePath());
            }

        }

    }


    public static void SNAP_SHOT(String arquivo_entrada, String arquivo_final) {
        FS.removerArquivo(arquivo_final);

        Arquivador entrada = new Arquivador(arquivo_entrada);
        Arquivador saida = new Arquivador(arquivo_final);

        while (entrada.getPonteiro() < entrada.getLength()) {
            saida.set_u8(entrada.get());
        }

        entrada.encerrar();
        saida.encerrar();

    }


    public static String GET_DATA_CRIACAO(String arquivo){
        String tempo = "";
        Path filePath = Paths.get(arquivo);

        try {
            FileTime creationTime =
                    (FileTime) Files.getAttribute(filePath, "creationTime");
            tempo = creationTime.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        return tempo;
    }

    public static String GET_DATA_MODIFICACAO(String arquivo){
        String tempo = "";
        Path filePath = Paths.get(arquivo);

        try {
            FileTime creationTime =
                    (FileTime) Files.getLastModifiedTime(filePath);
            tempo = creationTime.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempo;
    }


    public static void COPIAR(String origem,String destino){
        Arquivador.CONSTRUIR_ARQUIVO(destino,Arquivador.GET_BYTES(origem));
    }

    public static void REMOVER_TUDO_INTERNAMENTE(String local){

        for (Arquivo arquivo_item : new PastaFS(local).getArquivos()) {
            FS.removerArquivo(arquivo_item.getLocal());
        }

        for (PastaFS pasta_item : new PastaFS(local).getPastas()) {
            REMOVER_TUDO_INTERNAMENTE(pasta_item.getLocal());
            FS.removerArquivo(pasta_item.getLocal());
        }


    }
}
