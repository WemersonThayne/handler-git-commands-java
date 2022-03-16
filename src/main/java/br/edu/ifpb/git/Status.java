package br.edu.ifpb.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible to manipulate git status
 * @author wemerson.thayne@gmail.com
 *
 */
public class Status {

	static Logger logger = LoggerFactory.getLogger(Status.class);
	private Status(){}

	public static void status(Git git) {
		try {
			org.eclipse.jgit.api.Status status = git.status().call();
			showStatus(status);
		} catch (GitAPIException e) {
			logger.error("Error execute git status: ", e);
		}
	}

	private static void showStatus(org.eclipse.jgit.api.Status status) {
		logger.info("Added              :{} ", status.getAdded());
		logger.info("Changed            :{}", status.getChanged());
		logger.info("Removed            :{}", status.getRemoved());
		logger.info("Uncommitted Changes:{}", status.getUncommittedChanges());
		logger.info("Untracked          :{}", status.getUntracked());
		logger.info("Untracked Folders  :{}", status.getUntrackedFolders());
		logger.info("Ignored Not Index  :{}", status.getIgnoredNotInIndex());
		logger.info("Conflicting        :{}", status.getConflicting());
		logger.info("Missing            :{}", status.getMissing());
	}

}