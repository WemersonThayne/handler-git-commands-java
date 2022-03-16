package br.edu.ifpb.git;

import java.io.IOException;
import java.time.Instant;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible manipulate git log
 * 
 * @author wemerson.thayne@gmail.com
 *
 */
public class Log {
	static Logger logger = LoggerFactory.getLogger(Log.class);

	private Log(){}

	public static void logAll(Git git) {
		try {
			Iterable<RevCommit> logs = git.log().all().call();
			showLog(logs);
		} catch (GitAPIException | IOException e) {
			logger.error("Error to execute git log: ", e);
		}
	}

	public static void logFile(Git git, String path) {
		try {
			Iterable<RevCommit> logs = git.log().addPath(path).call();
			showLog(logs);
		} catch (GitAPIException e) {
			logger.error("Error to execute git log: ", e);
		}
	}

	private static void showLog(Iterable<RevCommit> logs) {
		logs.forEach(rev -> {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("\n-------------------------");
			stringBuilder.append(Instant.ofEpochSecond(rev.getCommitTime()) + " ");
			stringBuilder.append("Commit: ");
			stringBuilder.append(rev.getFullMessage());
			stringBuilder.append("\n");
			stringBuilder.append("Author: " + rev.getId().getName());
			stringBuilder.append(" " + rev.getAuthorIdent().getName());
			stringBuilder.append(" " + rev.getAuthorIdent().getEmailAddress());
			stringBuilder.append("\n-------------------------");
			logger.info(stringBuilder.toString());
		});
	}
}