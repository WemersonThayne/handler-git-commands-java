package br.edu.ifpb.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ifpb.enums.TypeFileModification;


public class FileUtil {
	static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private FileUtil(){}

	public static void create(Path path)  {
		try {
			if (!path.toFile().exists()) {
				Files.createFile(path);
				logger.info("File create with success.");
			}
		} catch (IOException e) {
			logger.error("Error to create file: ", e);
		}
	}


	public static void modifyFile(Path path, List<String> newContent, int initLine, int finalLine,
			TypeFileModification typeFileModification) throws IOException {
		if (isFileExists(path)) {
			List<String> contentActual = contentFile(path);
			switch (typeFileModification){
				case ADD:
					writeFile(path, newContent, StandardOpenOption.APPEND);
					break;
				case ALTER:
					alterContentFile(path, contentActual, newContent, initLine, finalLine);
					break;
				case DELETE:
					removeLineInFile(path, contentActual, initLine, finalLine);
					break;
			}
		} else {
			logger.error("File not found");
		}
	}

	public static void deleteFile(String path){
		File myObj = new File(path);
		if (myObj.delete()) {
			logger.info("Deleted the file: {}", myObj.getName());
		} else {
			logger.error("Failed to delete the file.");
		}
	}

	private static void removeLineInFile(Path path, List<String> contentActual, int initLine, int finalLine) {
		List<String> mergeContent = new ArrayList<>();
		mergeContent.addAll(contentActual);
		int tam = finalLine > contentActual.size() ? contentActual.size() : finalLine;

		if (initLine <= contentActual.size() ) {
			mergeContent.subList(initLine - 1, tam).clear();
		}
		writeFile(path, mergeContent, StandardOpenOption.WRITE);
	}

	private static void alterContentFile(Path path, List<String> contentActual, List<String> newContent,
										 int initLine, int finalLine) {

		List<String> mergeContent = new ArrayList<>();
		List<String> partitionInitialContent = new ArrayList<>();
		List<String> partitionFinalContent = new ArrayList<>();

		if (initLine - 1 <= contentActual.size()) {
			partitionInitialContent.addAll(contentActual.subList(0, (initLine - 1)));
			mergeContent.addAll(partitionInitialContent);
			mergeContent.addAll(newContent);
		}

		if(finalLine - 1 <= contentActual.size()){
			partitionFinalContent.addAll(contentActual.subList(initLine - 1, finalLine - 1));
			mergeContent.addAll(partitionFinalContent);
			mergeContent.addAll(contentActual.subList(finalLine, contentActual.size()));
		}

		writeFile(path, mergeContent,StandardOpenOption.WRITE);
	}

	public static List<String> contentFile(Path path) throws IOException {
		List<String> content = new ArrayList<>();
		if (isFileExists(path)) {
			content = Files.readAllLines(path);
			logger.info("Content file: {}", content);
		}
		return content;
	}

	private static void writeFile(Path path, List<String> contentFile, StandardOpenOption option) {
		if (isFileExists(path)) {
			try (BufferedWriter bw = Files.newBufferedWriter(path, option)) {
				contentFile.forEach(c -> {
					try {
						bw.write(c);
						bw.newLine();
					} catch (IOException e) {
						logger.error("Error to write in file: ", e);
					}
				});
			} catch (IOException e) {
				logger.error("Error to read Buffered: ", e);
			}
			logger.info("Write file success");
		} else {
			logger.error("File not found");
		}
	}

	private static boolean isFileExists(Path path) {
		return path.toFile().exists();
	}

}