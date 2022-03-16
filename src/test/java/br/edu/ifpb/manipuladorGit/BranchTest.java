package br.edu.ifpb.manipuladorGit;

import br.edu.ifpb.git.Branch;
import br.edu.ifpb.git.Commit;
import br.edu.ifpb.git.Repository;
import br.edu.ifpb.model.Author;
import br.edu.ifpb.util.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BranchTest {

    private static Git git;

    @BeforeAll
     static void init() throws IOException {
        String DIR_REPOSITORY = "/tmp/test-git";
        Repository.init(Path.of(DIR_REPOSITORY));
        git = Git.open(new File(DIR_REPOSITORY));
        FileUtil.create(Path.of(DIR_REPOSITORY.concat("/a.txt")));
        Commit.gitAdd(git, List.of("a.txt"));
        Commit.gitCommit(git, "test commit", new Author("testAuthor","author@example.com"));
    }

    @Test
    void testCreateBranchSuccess(){
        Branch.createBranch(git, "test");
        assertTrue(new ArrayList<>(Branch.branch(git)).contains("test"));
    }

    @Test
    void testCheckoutBranchSuccess() throws IOException {
        Branch.checkoutBranch(git, "test");
        assertTrue(git.getRepository().getFullBranch().contains("test"));
    }

    @Test
    void testCheckoutBranchFailed() throws IOException {
        Branch.checkoutBranch(git, "master");
        assertFalse(git.getRepository().getFullBranch().contains("test"));
    }

    @Test
    void testListBranchSuccess() {
        List<String> branch = Branch.branch(git);
        assertTrue(branch.contains("test"));
    }

    @Test
    void testListBranchFailed() {
        List<String> branch = Branch.branch(git);
        assertFalse(branch.contains("XPT"));
    }

    @Test
    void testMerge() throws GitAPIException {
        Branch.checkoutBranch(git, "test");
        AtomicReference<RevCommit> commitBranchTest = new AtomicReference<>();
        List<RevCommit> commitBranchMaster = new ArrayList<>();
        git.log().call().forEach(commitBranchTest::set);
        Branch.merge(git,"test","master");
        Branch.checkoutBranch(git, "master");
        git.log().call().forEach(commitBranchMaster::add);
        assertTrue(commitBranchMaster.contains(commitBranchTest.get()));
    }

}