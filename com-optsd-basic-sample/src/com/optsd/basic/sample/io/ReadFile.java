package com.optsd.basic.sample.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadFile {
	
	public void readFileLineByLine() throws IOException {
		// Read from baseReader, one line at a time
		BufferedReader reader = new BufferedReader(new FileReader("aa"));
		String line;
		while ((line = reader.readLine()) != null) {
			// Remember: System.out is a stream, not a writer!
			System.out.println(line);
		}
	}
	
	
	public void readFileWithScanner() {
		
		//  line by line
		try {
			Scanner scanner = new Scanner(new File("example.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// do stuff
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		// word by word
		try {
			Scanner scanner = new Scanner(new File("example.txt"));
			while (scanner.hasNext()) {
				String line = scanner.next();
				// do stuff
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		// the whole file
		// \Z is the EOF (End of File)
		File f = new File("example.txt");
		try {
			String content = new Scanner(f).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void readFileWithChannel() {
		File inputFile = new File("hello.txt");
		if (!inputFile.exists()) {
			System.out.println("The input file doesn't exit.");
			return;
		}
		try {
			FileInputStream fis = new FileInputStream(inputFile);
			FileChannel fileChannel = fis.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (fileChannel.read(buffer) > 0) {
				buffer.flip();
				while (buffer.hasRemaining()) {
					byte b = buffer.get();
					System.out.print((char) b);
				}
				buffer.clear();
			}
			fileChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void readFileUsingBufferedInputStream() {
		// Reading file using a BufferedInputStream generally faster than FileInputStream because it maintains an internal
		// buffer to store bytes read from the underlying input stream
		String source = "hello.txt";
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source))) {
			byte data;
			while ((data = (byte) bis.read()) != -1) {
				System.out.println((char) data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void readBinaryFile() throws IOException {
		// Version >= Java SE 1.4
		File file = new File("path_to_the_file");
		byte[] data = new byte[(int) file.length()];
		DataInputStream stream = new DataInputStream(new FileInputStream(file));
		stream.readFully(data);
		stream.close();
		// If you are using Java 7 or later, there is a simpler way using the nio API:
		// Version >= Java SE 7
		Path path = Paths.get("path_to_the_file");
		byte [] data2 = Files.readAllBytes(path);
	}
	
	
	public void readZipFile()
	{
		Path pathToZip = Paths.get("path/to/file.zip");
		try (FileSystem zipFs = FileSystems.newFileSystem(pathToZip, null)) {
			Path root = zipFs.getPath("/");
			// ... //access the content of the zip file same as ordinary files
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void createZipFile()
	{
		Map<String, String> env = new HashMap<>();
		env.put("create", "true"); // required for creating a new zip file
		env.put("encoding", "UTF-8"); // optional: default is UTF-8

		URI uri = URI.create("jar:file:/path/to/file.zip");
		try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
			Path newFile = zipfs.getPath("/newFile.txt");
			// writing to file
			Files.write(newFile, "Hello world".getBytes());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}