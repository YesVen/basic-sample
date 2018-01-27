package com.optsd.basic.sample.io;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class IterateFile {

	public void iterateAndFilter() throws IOException {
		Path dir = Paths.get("C:/foo/bar");
		
		PathMatcher imageFileMatcher = FileSystems.getDefault()
				.getPathMatcher("regex:.*(?i:jpg|jpeg|png|gif|bmp|jpe|jfif)");
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, entry -> imageFileMatcher.matches(entry.getFileName()))) {
			for (Path path : stream) {
				System.out.println(path.getFileName());
			}
		}
	}
}
