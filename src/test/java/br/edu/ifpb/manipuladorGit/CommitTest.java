package br.edu.ifpb.manipuladorGit;

import br.edu.ifpb.git.Commit;
import br.edu.ifpb.git.Repository;
import br.edu.ifpb.model.Author;
import br.edu.ifpb.util.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommitTest {

    private Git git;
    private final String DIR_REPOSITORY = "/tmp/test-git";

    @BeforeEach
    void init() throws IOException {
        Repository.init(Path.of(DIR_REPOSITORY));
        git = Git.open(new File(DIR_REPOSITORY));
    }

    @Test
    void gitCommitTest() throws GitAPIException {
        FileUtil.create(Path.of(DIR_REPOSITORY.concat("/a.txt")));
        Commit.gitAdd(git, List.of("a.txt"));
        Commit.gitCommit(git, "unit test commit", new Author("testAuthor","author@example.com"));
        List<RevCommit> commits = new ArrayList<>();
        git.log().call().forEach(commits::add);
        assertTrue(commits.stream().findFirst().filter(c -> c.getShortMessage().equalsIgnoreCase("unit test commit")).isPresent());
        assertTrue(commits.stream().findFirst().filter(c -> c.getAuthorIdent().getName().equalsIgnoreCase("testAuthor")).isPresent());
    }
}