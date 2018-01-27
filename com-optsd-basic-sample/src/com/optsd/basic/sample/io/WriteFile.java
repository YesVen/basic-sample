package com.optsd.basic.sample.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class WriteFile {
	
	
	public void writeFileUsingPrintStream() {
		String destination = "file1.txt";
		try (PrintStream ps = new PrintStream(destination)) {
			ps.println("Stackoverflow documentation seems fun.");
			ps.println();
			ps.println("I love Java!");
			ps.printf("Today is: %1$tm/%1$td/%1$tY", LocalDate.now());
			ps.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void readWriteFileUsingFileInputOutputStream() {
		// Write to a file test.txt
		String filepath = "C:\\test.txt";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filepath);
			byte[] buffer = "This will be written in test.txt".getBytes();
			fos.write(buffer, 0, buffer.length);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException ignored) {
				}
		}

		// Read from file test.txt
		// String filepath = "C:\\test.txt";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			int length = (int) new File(filepath).length();
			byte[] buffer = new byte[length];
			fis.read(buffer, 0, length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException ignored) {
				}
		}

		// Java 1.7 the try-with-resources statement
		// Write to a file test.txt
		// String filepath = "C:\\test.txt";
		try (FileOutputStream fos2 = new FileOutputStream(filepath)) {
			byte[] buffer = "This will be written in test.txt".getBytes();
			fos2.write(buffer, 0, buffer.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read from file test.txt
		// String filepath ="C:\\test.txt";
		try (FileInputStream fis2 = new FileInputStream(filepath)) {
			int length = (int) new File(filepath).length();
			byte[] buffer = new byte[length];
			fis2.read(buffer, 0, length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readAllBytesToByteArray() {
		Path path = Paths.get("path/to/file");
		try {
			byte[] data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeByteArrayToFile() {
		// Most java.io file APIs accept both Strings and Files as arguments, so you
		// could as well use
		// File file = new File("Hello world.txt");
		// FileOutputStream stream = new FileOutputStream(file)

		byte[] bytes = { 0x48, 0x65, 0x6c, 0x6c, 0x6f };
		try (FileOutputStream stream = new FileOutputStream("Hello world.txt")) {
			stream.write(bytes);
		} catch (IOException ioe) {
			// Handle I/O Exception
			ioe.printStackTrace();
		}

		// Version < Java SE 7
		byte[] bytes2 = { 0x48, 0x65, 0x6c, 0x6c, 0x6f };
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream("Hello world.txt");
			stream.write(bytes2);
		} catch (IOException ioe) {
			// Handle I/O Exception
			ioe.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	public static class copyFile {

		// Using Channel to copy file content faster
		public static void copy(File sourceFile, File destFile) {
			if (!sourceFile.exists() || !destFile.exists()) {
				System.out.println("Source or destination file doesn't exist");
				return;
			}
			try (FileChannel srcChannel = new FileInputStream(sourceFile).getChannel();
					FileChannel sinkChanel = new FileOutputStream(destFile).getChannel()) {
				srcChannel.transferTo(0, srcChannel.size(), sinkChanel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Using InputStream and OutputStream
		public void copy(InputStream source, OutputStream destination) throws IOException {
			try {
				int c;
				while ((c = source.read()) != -1) {
					destination.write(c);
				}
			} finally {
				if (source != null) {
					source.close();
				}
				if (destination != null) {
					destination.close();
				}
			}
		}

		public static void main(String[] args) {
			File sourceFile = new File("hello.txt");
			File sinkFile = new File("hello2.txt");
			copy(sourceFile, sinkFile);
		}
	}
}
