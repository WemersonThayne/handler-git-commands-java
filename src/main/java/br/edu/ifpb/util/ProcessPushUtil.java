package br.edu.ifpb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessPushUtil {

    public static String getPathBash() {
        File file = new File("src/main/resources/git-commands.sh");
        return file.getAbsolutePath();
    }

    public static String builder(String pathRepositoryLocal, String remoteRepository, String branch, String originRemote, String useSshKey) {
        return getPathBash().concat(" ").concat(pathRepositoryLocal).concat(" ").concat(remoteRepository).concat(" ")
                .concat(branch).concat(" ").concat(originRemote).concat(" ").concat(useSshKey);
    }

    public static void execute(String pathRepositoryLocal, String remoteRepository, String branch, String originRemote, String useSshKey) {

        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        //String[] cmd = new String[]{"bash","-c",""};

        //  /home/wemerson-porto/test-git/
        //  https://github.com/WemersonThayne/teste-push-by-terminal.git
        //  master
        //  git@github.com:WemersonThayne/teste-push-by-terminal.git

        try {

            Process process = Runtime.getRuntime().exec(builder(pathRepositoryLocal,remoteRepository,branch,originRemote,useSshKey));

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(output);
                System.exit(0);
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

//
//        // -- Windows --
//
//        // Run a command
//        //processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");
//
//        // Run a bat file
//        //processBuilder.command("C:\\Users\\mkyong\\hello.bat");
//
//
    }
}
