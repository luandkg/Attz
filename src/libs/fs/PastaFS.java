package libs.fs;

import libs.luan.Lista;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PastaFS {

    public static final int PLATAFORMA_LINUX = 1;
    public static final int PLATAFORMA_WINDOWS = 2;

    public static final int PLATAFORMA_CORRENTE = PLATAFORMA_LINUX;


    private String mLocal;
    private String mSeparador;

    public static String PLATAFORMA_SEPARADOR() {

        if (PLATAFORMA_CORRENTE == PLATAFORMA_LINUX) {
            return "/";
        } else if (PLATAFORMA_CORRENTE == PLATAFORMA_WINDOWS) {
            return "\\";
        }

        return "/";
    }

    public static String PLATAFORMA_GET(String local) {

        if (PLATAFORMA_CORRENTE == PLATAFORMA_LINUX) {
            return local.replace("\\", "/");
        } else if (PLATAFORMA_CORRENTE == PLATAFORMA_WINDOWS) {
            return local.replace("/", "\\");
        }

        return local;
    }


    public PastaFS(String eLocal) {
        mSeparador = PLATAFORMA_SEPARADOR();
        mLocal = PLATAFORMA_GET(eLocal);
    }

    public String getArquivo(String arquivo) {
        String local_corrente = "";

        if (mLocal.endsWith(mSeparador)) {
            local_corrente = mLocal + PLATAFORMA_GET(arquivo);
        } else {
            local_corrente = mLocal + mSeparador + PLATAFORMA_GET(arquivo);
        }

        return local_corrente;

    }

    public String getPasta(String arquivo) {
        if (mLocal.endsWith(mSeparador)) {
            return mLocal + PLATAFORMA_GET(arquivo);
        } else {
            return mLocal + mSeparador + PLATAFORMA_GET(arquivo);
        }
    }

    public PastaFS getPastaFS(String arquivo) {
        return new PastaFS(getPasta(arquivo));
    }


        public String getLocal() {
        return mLocal;
    }

    public void verificar() {
        File pastinha = new File(mLocal);
        if (!pastinha.exists()) {
            pastinha.mkdir();
        }
    }


    public Lista<PastaFS> getPastas() {
        Lista<PastaFS> ret = new Lista<PastaFS>();

        File file = new File(mLocal);
        if (file.exists()) {
            for (File a : file.listFiles()) {
                if (a.isDirectory()) {
                    ret.adicionar(new PastaFS(a.getAbsolutePath()));
                }

            }
        }


        return ret;

    }


    public String getNome() {
        String ret = "";

        int o = mLocal.length() - 1;
        while (o > 0) {
            if (String.valueOf(mLocal.charAt(o)).contentEquals(mSeparador)) {
                break;
            }
            ret = String.valueOf(mLocal.charAt(o)) + ret;
            o -= 1;
        }

        return ret;
    }


    public Lista<Arquivo> getArquivos() {
        Lista<Arquivo> ret = new Lista<Arquivo>();

        File file = new File(mLocal);

        File[] files = file.listFiles();

        if (files != null) {
            for (File a : files) {
                if (a.isFile()) {
                    ret.adicionar(new Arquivo(a.getAbsolutePath()));
                }
            }
        }

        return ret;
    }

    public Lista<String> getArquivosCaminhos() {
        Lista<String> ret = new Lista<String>();

        File file = new File(mLocal);

        File[] files = file.listFiles();

        if (files != null) {
            for (File a : files) {
                if (a.isFile()) {
                    ret.adicionar(a.getAbsolutePath());
                }
            }
        }

        return ret;
    }


    public boolean existe() {
        return new File(mLocal).exists();
    }

    public void organizar() {
        if (!existe()) {
            new File(mLocal).mkdir();
        }
    }

    public boolean arquivo_existe(String arquivo) {
        String sa = "";

        if (mLocal.endsWith(mSeparador)) {
            sa = mLocal + PLATAFORMA_GET(arquivo);
        } else {
            sa = mLocal + mSeparador + PLATAFORMA_GET(arquivo);
        }

        return new File(sa).exists();
    }

    public boolean pasta_existe(String nome) {
        return new File(getArquivo(PLATAFORMA_GET(nome))).exists();
    }


    public Lista<String> getArquivosRecursivamente() {
        Lista<String> lista_de_arquivos = new Lista<String>();

        File file = new File(mLocal);

        File[] files = file.listFiles();

        if (files != null) {
            for (File a : files) {
                if (a.isFile()) {
                    lista_de_arquivos.adicionar(a.getAbsolutePath());
                } else if (a.isDirectory()) {
                    incluir_arquivos_internos(lista_de_arquivos, a.getAbsolutePath());
                }
            }
        }

        return lista_de_arquivos;
    }


    private void incluir_arquivos_internos(Lista<String> lista_de_arquivos, String pasta_caminho) {

        File file = new File(pasta_caminho);

        File[] files = file.listFiles();

        if (files != null) {
            for (File a : files) {
                if (a.isFile()) {
                    lista_de_arquivos.adicionar(a.getAbsolutePath());
                } else if (a.isDirectory()) {
                    incluir_arquivos_internos(lista_de_arquivos, a.getAbsolutePath());
                }
            }
        }

    }


    public Lista<Arquivo> getArquivosComExtensao(String eExtensao) {
        Lista<Arquivo> ret = new Lista<Arquivo>();

        File file = new File(mLocal);

        File[] files = file.listFiles();

        if (files != null) {
            for (File a : files) {
                if (a.isFile()) {
                    if (a.getName().endsWith(eExtensao)) {
                        ret.adicionar(new Arquivo(a.getAbsolutePath()));
                    }
                }
            }
        }

        return ret;
    }

    public Lista<String> getArquivosComExtensaoApenasNome(String eExtensao) {
        Lista<String> arquivos = new Lista<String>();
        for (Arquivo observado : getArquivosComExtensao(eExtensao)) {
            String nome = observado.getNome();
            arquivos.adicionar(nome);
        }
        return arquivos;
    }


    public void criarPasta(String nome){
        try {
            Files.createDirectories(Paths.get(this.getPasta(nome)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}