package com.optsd.basic.sample.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class MigratingFileJavaIOToJava7NIO {

	// A file system read/write operation using a java.io.File instance method, 
	// as a static method within java.nio.file.Files
	public void usage() throws IOException
	{
		// Point to a path	
		// -> IO
		File file = new File("io.txt");
		// -> NIO
		Path path = Paths.get("nio.txt");
		
		
		// Paths relative to another path
		// Forward slashes can be used in place of backslashes even on a Windows operating system
		// -> IO
		File folder = new File("C:/");
		File fileInFolder = new File(folder, "io.txt");
		// -> NIO
		Path directory = Paths.get("C:/");
		Path pathInDirectory = directory.resolve("nio.txt");
		
		
		// Converting File from/to Path for use with libraries
		// -> IO to NIO
		Path pathFromFile = new File("io.txt").toPath();
		// -> NIO to IO
		File fileFromPath = Paths.get("nio.txt").toFile();
		
		
		// Check if the file exists and delete it if it does
		// -> IO
		if (file.exists()) {
			boolean deleted = file.delete();
			if (!deleted) {
				throw new IOException("Unable to delete file");
			}
		}
		// -> NIO
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		 * Write to a file via an OutputStream
		 * 
		 * There are several ways to write and read from a file using NIO for different
		 * performance and memory constraints, readability and use cases, such as
		 * FileChannel, Files.write(Path path, byte\[\] bytes, OpenOption... options)...
		 * 
		 * Only OutputStream is covered
		 */
		List<String> lines = Arrays.asList(String.valueOf(Calendar.getInstance().getTimeInMillis()), "line one",
				"line two");
		// -> IO
		if (file.exists()) {
			// Note: Not atomic
			throw new IOException("File already exists");
		}
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			for (String line : lines) {
				outputStream.write((line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
			}
		}
		// -> NIO
		try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW)) {
			for (String line : lines) {
				outputStream.write((line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
			}
		}
		
		
		
		// Iterating on each file within a folder
		// -> IO
		for (File selectedFile : folder.listFiles()) {
			// Note: Depending on the number of files in the directory folder.listFiles()
			// may take a long time to return
			System.out.println((selectedFile.isDirectory() ? "d" : "f") + " " + selectedFile.getAbsolutePath());
		}
		// -> NIO
		Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), 1, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path selectedPath, BasicFileAttributes attrs) throws IOException {
				System.out.println("d " + selectedPath.toAbsolutePath());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path selectedPath, BasicFileAttributes attrs) throws IOException {
				System.out.println("f " + selectedPath.toAbsolutePath());
				return FileVisitResult.CONTINUE;
			}
		});
		
		
		// Recursive folder iteration
		// -> IO
		recurseFolder(folder);
		
		// -> NIO
		// Note: Symbolic links are NOT followed unless explicitly passed as an argument to Files.walkFileTree
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println("d " + dir.toAbsolutePath());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path selectedPath, BasicFileAttributes attrs) throws IOException {
				System.out.println("f " + selectedPath.toAbsolutePath());
				return FileVisitResult.CONTINUE;
			}
		});
		
	}

	private static void recurseFolder(File folder) {
		for (File selectedFile : folder.listFiles()) {
			System.out.println((selectedFile.isDirectory() ? "d" : "f") + " " + selectedFile.getAbsolutePath());
			if (selectedFile.isDirectory()) {
				// Note: Symbolic links are followed
				recurseFolder(selectedFile);
			}
		}
	}
	
	public static void iterateOverDirectoryPrintingSubdirectories() throws IOException {
		final DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("dirPath"));
		for (final Path path : paths) {
			if (Files.isDirectory(path)) {
				System.out.println(path.getFileName());
			}
		}
	}
}
