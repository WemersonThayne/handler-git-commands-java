package br.edu.ifpb.git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for handling branches
 * 
 * @author wemerson.thayne@gmail.com
 *
 */
public class Branch {

	static Logger logger = LoggerFactory.getLogger(Branch.class);
	private Branch(){}

	public static void createBranch(Git git, String nameBranch) {
		try {
			if(validBranch(git,nameBranch)){
				git.branchCreate().setName(nameBranch).call();
			} else {
				logger.info("branch already exists");
			}
		} catch (GitAPIException e) {
			logger.error("Error to create branch: ", e);
		}
	}

	public static void checkoutBranch(Git git, String nameBranch) {
		try {
			git.checkout().setName(nameBranch).call();
		} catch (GitAPIException e) {
			logger.error("Error when checking out the branch: ", e);
		}
	}

	public static List<String> branch(Git git) {
		List<String> branchNames = new ArrayList<>();
		try {
			showAllBranch(git);
			List<Ref> branches = git.branchList().setListMode(ListMode.ALL).call();
			branches.forEach(b ->
					branchNames.add(b.getName().substring(b.getName().lastIndexOf("/")+1)));
		} catch (GitAPIException e) {
			logger.error("Error when git branch: ", e);
		}
		return branchNames;
	}

	public static void merge(Git git, String branchOrigin, String branchDestiny) {
		checkoutBranch(git, branchDestiny);
		try {
			ObjectId branchObjectId = git.getRepository().resolve(branchOrigin);
			MergeResult mergeResult = git.merge().include(branchObjectId).call();
			logger.info("Merge: {}", mergeResult);
			if (mergeResult.getConflicts() != null) {
				for (Entry<String, int[][]> entry : mergeResult.getConflicts().entrySet()) {
					logger.info("key: {}", entry.getKey());
					for (int[] arr : entry.getValue()) {
						logger.info("value: ", arr);
					}
				}
			}
		} catch (RevisionSyntaxException | GitAPIException | IOException e) {
			logger.error("Error when git merge:", e);
		}
	}

	private static boolean validBranch(Git git, String nameBranch){
		return branch(git).stream().filter(b -> b.equalsIgnoreCase(nameBranch)).collect(Collectors.toList()).isEmpty();
	}

	private static void showAllBranch(Git git) {
		try {
			List<Ref> branches = git.branchList().setListMode(ListMode.ALL).call();
			branches.forEach(b -> logger.info(b.getName()));
		} catch (GitAPIException e) {
			logger.error("Error when git branch: ", e);
		}
	}

}