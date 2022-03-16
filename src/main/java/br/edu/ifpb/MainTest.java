package br.edu.ifpb;

import br.edu.ifpb.git.Branch;
import br.edu.ifpb.git.Commit;
import br.edu.ifpb.git.PushCommit;
import br.edu.ifpb.git.Repository;
import br.edu.ifpb.model.Author;
import br.edu.ifpb.util.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import org.eclipse.jgit.transport.SshSessionFactory;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {

        String repositorio = "/home/wemerson-porto/test-git";


        Author autor = new Author("WemersonThayne", "wemerson.thayne@gmail.com");
//        Author autor2 = new Author("Vital", "Vital@gmail.com");
        Repository.init(Path.of("/home/wemerson-porto/test-git"));



        try {
            Git local = Git.open(new File(repositorio));
            Branch.createBranch(local, "master");
            Branch.checkoutBranch(local, "master");

            FileUtil.create(Path.of(repositorio.concat("/a.txt")));
            FileUtil.create(Path.of(repositorio.concat("/b.txt")));

            Commit.gitAdd(local, List.of("a.txt", "b.txt"));
            Commit.gitCommit(local, "first commit test", autor);



//            // git remote add origin git@github.com:ralscha/test_repo.git
            RemoteAddCommand remoteAddCommand = local.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish("git@github.com:WemersonThayne/test-push-by-sh.git"));
            remoteAddCommand.call();

            PushCommand pushCommand = local.push();
//            pushCommand.setTransportConfigCallback(transport -> {
//                SshTransport sshTransport = (SshTransport) transport;
//                sshTransport.setSshSessionFactory(sshSessionFactory);
//            });
            pushCommand.add("master");
            pushCommand.setRemote("origin");
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("token", ""));

            pushCommand.call();

//            ArquivoUtil.criarArquivo(Path.of(repositorio.concat("/a.txt")));
//            ArquivoUtil.criarArquivo(Path.of(repositorio.concat("/b.txt")));
//
//            Commit.gitAdd(local, Arrays.asList("a.txt", "b.txt"));
//            Commit.gitCommit(local, "teste", autor);

//            ArquivoUtil.criarArquivo(Path.of(repositorio.concat("/e.txt")));

//            Commit.gitAdd(local, Arrays.asList("e.txt"));
//            Commit.gitCommit(local, "teste branch commit por outro autor", autor);

//            GitBranch.gitMerge(local,"teste", "master");

//            Path pathTeste  = Path.of(repositorio.concat("/d.txt"));
//            ArquivoUtil.writeFile(pathTeste,Arrays.asList("Linha 1", "Linha 2", "Linha 3"), StandardOpenOption.WRITE);
//            System.out.println(">>>>>>>>>>> Commit 1");
//            ArquivoUtil.getConteudoFile(pathTeste).forEach(l ->{
//                System.out.println(l);
//            });

//            Commit.gitAdd(local, Arrays.asList("d.txt"));
//            Commit.gitCommit(local, "Adicionando 3 linhas no arquivo", autor);

//            System.out.println(">>>>>>>>>>> Commit 2");
//            FileUtil.modificarArquivo(pathTeste,Arrays.asList("Nova Linha 4", "Linha 5","Linha 6"),6,9, TypeFileModification.ALTER);
//            FileUtil.getConteudoFile(pathTeste).forEach(l ->{
//                System.out.println(l);
//            });
//            Commit.gitAdd(local, Arrays.asList("d.txt"));
//            Commit.gitCommit(local, "Mais 3 linhas no arquivo", autor2);
//
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
