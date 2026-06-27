package apps.app_unbdoc;

import libs.fs.PastaFS;
import libs.luan.FS;
import libs.luan.fmt;
import servicos.ASSETS;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AppUnBDoc {

    public static void INICIAR() {

        fmt.println("---------------------- UnB - Doc -------------------------");

        PastaFS pasta_latex = new PastaFS(ASSETS.GET_PASTA("latex_transpiler").getLocal());
        PastaFS pasta_saida = new PastaFS(ASSETS.GET_PASTA("latex_transpiler/dissertacao").getLocal());


        fmt.print("{}", pasta_latex.getLocal());

        COMANDO_RMDIR(pasta_saida.getLocal(), "build");
        COMANDO_RMDIR(pasta_saida.getLocal(), "dist");

        COMANDO_MKDIR(pasta_saida.getLocal(), "build");
        COMANDO_MKDIR(pasta_saida.getLocal(), "build/out");
        COMANDO_MKDIR(pasta_saida.getLocal(), "build/pdf");

        COMANDO_MKDIR(pasta_saida.getLocal(), "dist");


        UnBDoc doc = new UnBDoc();

        doc.processar(pasta_latex.getArquivo("dissertacao/src/dissertacao.unb"), pasta_saida.getLocal());

        if (doc.getErros().getQuantidade() > 0) {

            fmt.print("---------------------------------- ERROS ------------------------------------");
            for (String erro : doc.getErros()) {
                fmt.print("ERRO : {}", erro);
            }

            fmt.print("---------------------------------- ----- ------------------------------------");

        }

        //COMANDO_MAKE(pasta_saida.getLocal());

        //  COMANDO_MKDIR(pasta_saida.getLocal(),"dist");

    }

    public static boolean COMANDO_MKDIR(String local_projeto, String local) {

        boolean sucesso = false;

        String comando = "";

        try {

            File workingFolder = new File(local_projeto);


            ProcessBuilder pb = new ProcessBuilder("mkdir", "-p", local);
            // ProcessBuilder pb = new ProcessBuilder("pwd");

            pb.directory(workingFolder);


            for (String a : pb.command()) {
                comando += a + " ";
            }

            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));

            sucesso = true;

            String s = null;
            String t1 = "";
            while ((s = stdInput.readLine()) != null) {
                t1 += s + "\n";
            }

            if (!t1.isEmpty()) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t1);
                fmt.println(fmt.repetir("-", 40));
            }

            String s1 = null;
            String t2 = "";

            while ((s1 = stdError.readLine()) != null) {
                t2 += s1 + "\n";
                sucesso = false;
            }

            if (!t2.isEmpty()) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t2);
                fmt.println(fmt.repetir("-", 40));
            }


        } catch (Exception e) {
            fmt.print("COMANDO >> {}", comando);
            fmt.print("ERRO    >> {}", e.getMessage());
        }

        return sucesso;
    }

    public static boolean COMANDO_RMDIR(String local_projeto, String local) {

        boolean sucesso = false;

        String comando = "";

        try {

            File workingFolder = new File(local_projeto);


            ProcessBuilder pb = new ProcessBuilder("rm", "-rf", local);
            // ProcessBuilder pb = new ProcessBuilder("pwd");

            pb.directory(workingFolder);


            for (String a : pb.command()) {
                comando += a + " ";
            }

            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));

            sucesso = true;

            String s = null;
            String t1 = "";
            while ((s = stdInput.readLine()) != null) {
                t1 += s + "\n";
            }

            if (!t1.isEmpty()) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t1);
                fmt.println(fmt.repetir("-", 40));
            }

            String s1 = null;
            String t2 = "";

            while ((s1 = stdError.readLine()) != null) {
                t2 += s1 + "\n";
                sucesso = false;
            }

            if (!t2.isEmpty()) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t2);
                fmt.println(fmt.repetir("-", 40));
            }


        } catch (Exception e) {
            fmt.print("COMANDO >> {}", comando);
            fmt.print("ERRO    >> {}", e.getMessage());
        }

        return sucesso;
    }

    public static boolean COMANDO_MAKE(String local_projeto) {

        boolean sucesso = false;

        String comando = "";

        try {

            File workingFolder = new File(local_projeto);


            ProcessBuilder pb = new ProcessBuilder("make");
            // ProcessBuilder pb = new ProcessBuilder("pwd");

            pb.directory(workingFolder);


            for (String a : pb.command()) {
                comando += a + " ";
            }

            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));

            sucesso = true;

            String s = null;
            String t1 = "";
            while ((s = stdInput.readLine()) != null) {
                t1 += s + "\n";
            }

            if (!t1.isEmpty()) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t1);
                fmt.println(fmt.repetir("-", 40));
            }

            String s1 = null;
            String t2 = "";

            while ((s1 = stdError.readLine()) != null) {
                t2 += s1 + "\n";
                sucesso = false;
            }

            if (!t2.isEmpty()) {
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t2);
                fmt.println(fmt.repetir("-", 40));
            }


        } catch (Exception e) {
            fmt.print("COMANDO >> {}", comando);
            fmt.print("ERRO    >> {}", e.getMessage());
        }

        return sucesso;
    }

}
