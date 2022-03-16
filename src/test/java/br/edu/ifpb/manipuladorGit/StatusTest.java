package br.edu.ifpb.manipuladorGit;

import br.edu.ifpb.git.Repository;
import br.edu.ifpb.git.Status;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StatusTest {

    private Git git;

    @BeforeEach
    void init() throws IOException {
        String DIR_REPOSITORY = "/tmp/test-git";
        Repository.init(Path.of(DIR_REPOSITORY));
        git = Git.open(new File(DIR_REPOSITORY));
    }

    @Test
    void statusTest() {
        Status.status(git);
        assertTrue(true);
    }
}