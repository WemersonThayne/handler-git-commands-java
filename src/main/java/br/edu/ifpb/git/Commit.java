package br.edu.ifpb.git;

import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ifpb.model.Author;

/**
 * Class responsible for manipulate commit in git repository
 * 
 * @author wemerson.thayne@gmail.com
 *
 */
public class Commit {

	static Logger logger = LoggerFactory.getLogger(Commit.class);

	private Commit(){}

	public static void gitAdd(Git git, List<String> filesNames) {
		filesNames.forEach(n -> {
			try {
				git.add().addFilepattern(n).call();
			} catch (NoFilepatternException e) {
				logger.error("Error to localize file: ", e);
			} catch (GitAPIException e) {
				logger.error("Error to execute git add: ", e);
			}
		});
		logger.info("Files adds success.");
	}

	public static void gitCommit(Git git, String message, Author author) {
		try {
			RevCommit commit = git.commit().setMessage(message).setAuthor(author.getName(), author.getEmail()).call();
			logger.info("Commit {} complete with success", commit.getName());
		} catch (GitAPIException e) {
			logger.error("Error to commit: ", e);
		}
	}

}