package servicos;

import libs.fs.PastaFS;
import libs.luan.Texto;
import libs.luan.fmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AmbienteJava {

    public static boolean JAVA_COMPILAR(String local_projeto, String pasta_destino, String a1) {

        boolean sucesso = false;

        String comando = "";

        try {

            File workingFolder = new File(local_projeto);


            ProcessBuilder pb = new ProcessBuilder("javac", "-d", pasta_destino, a1);
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
               t1+=s+"\n";
            }

            if(!t1.isEmpty()){
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t1);
                fmt.println(fmt.repetir("-", 40));
            }

            String s1 = null;
            String t2 = "";

            while ((s1 = stdError.readLine()) != null) {
               t2+=s1+"\n";
                sucesso = false;
            }

            if(!t2.isEmpty()){
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

    public static void JAVA_EXECUTAR_CLASS(String local_java_runtime, String pasta_nome, String a1) {


        String comando = "";

        try {

            File workingFolder = new File(local_java_runtime);


            ProcessBuilder pb = new ProcessBuilder("java", "-cp", pasta_nome, a1);
            // ProcessBuilder pb = new ProcessBuilder("pwd");

            pb.directory(workingFolder);


            for (String a : pb.command()) {
                comando += a + " ";
            }

            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));

            fmt.println(fmt.repetir("-", 40));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            fmt.println(fmt.repetir("-", 40));

            fmt.println(fmt.repetir("-", 40));
            String s1 = null;
            while ((s1 = stdError.readLine()) != null) {
                System.out.println(s1);
            }
            fmt.println(fmt.repetir("-", 40));


        } catch (Exception e) {
            fmt.print("COMANDO >> {}", comando);
            fmt.print("ERRO    >> {}", e.getMessage());
        }

    }

    public static boolean JAVA_PUBLICAR_JAR(String local_projeto,String local_build_bin, String nome_jar, String classe_principal,boolean ocultar) {

        PastaFS pasta_build_bin = new PastaFS(local_build_bin);
        Texto.arquivo_escrever(pasta_build_bin.getArquivo("MANIFEST.MF"),"Main-Class: "+classe_principal+"\n");

        boolean sucesso = false;

        String comando = "";

        try {

            File workingFolder = new File(local_projeto);


            ProcessBuilder pb = new ProcessBuilder("jar", "cvfm", nome_jar,pasta_build_bin.getArquivo("MANIFEST.MF"),"-C","bin",".");
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
                t1+=s+"\n";
            }

            if(!t1.isEmpty() && !ocultar){
                fmt.println(fmt.repetir("-", 40));
                fmt.println(t1);
                fmt.println(fmt.repetir("-", 40));
            }

            String s1 = null;
            String t2 = "";

            while ((s1 = stdError.readLine()) != null) {
                t2+=s1+"\n";
                sucesso = false;
            }

            if(!t2.isEmpty()){
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


    public static void JAVA_EXECUTAR_JAR(String local_java_runtime , String a1) {


        String comando = "";

        try {

            File workingFolder = new File(local_java_runtime);


            ProcessBuilder pb = new ProcessBuilder("java", "-jar", a1);
            // ProcessBuilder pb = new ProcessBuilder("pwd");

            pb.directory(workingFolder);


            for (String a : pb.command()) {
                comando += a + " ";
            }

            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));

            fmt.println(fmt.repetir("-", 40));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            fmt.println(fmt.repetir("-", 40));

            fmt.println(fmt.repetir("-", 40));
            String s1 = null;
            while ((s1 = stdError.readLine()) != null) {
                System.out.println(s1);
            }
            fmt.println(fmt.repetir("-", 40));


        } catch (Exception e) {
            fmt.print("COMANDO >> {}", comando);
            fmt.print("ERRO    >> {}", e.getMessage());
        }

    }

}
