package br.edu.ifpb.git;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class repository init
 * @author wemerson.vital@gmail.com
 * @since 02 nov 2019
 */
public class Repository {

	static Logger logger = LoggerFactory.getLogger(Repository.class);
	private Repository(){}

	public static void init(Path dir) {
        try {
			Git.init().setDirectory(dir.toFile()).call();
			logger.info("Init git repository complete success.");
		} catch (GitAPIException e) {
			logger.error("Error to init repository: ", e);
		}
	}

	public static Git getRepository(String dir) {
		try {
			logger.info("Get git repository complete success.");
			return Git.open(new File(dir));
		} catch (Exception e) {
			logger.error("Error to get repository: ", e);
		}
		return null;
	}

}