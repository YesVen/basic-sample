package com.optsd.basic.sample.exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class TryWithResources {
	
	@Test
	public void usage() throws FileNotFoundException
	{
		/*
		 * The basic try-with-resource statement
		 * 
		 * Once the resource variables have been initialized, the try block is executed.
		 * When that completes, stream.close will be called automatically to ensure that
		 * the resource does not leak. Note that the close() call happens no matter how
		 * the block completes.
		 */
		try (PrintStream stream = new PrintStream("hello.txt")) {
			stream.println("Hello world!");
		}

		
		/*
		 * The enhanced try-with-resource statements
		 * 
		 * Notes:
		 * The resource variable is out of scope in the catch and finally blocks.
		 * The resource cleanup will happen before the statement tries to match the catch block.
		 * If the automatic resource cleanup threw an exception, then that could be caught in one of the catch blocks.
		 */
		try (PrintStream stream = new PrintStream("hello.txt")) {
			stream.println("Hello world!");
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot open the file");
		} finally {
			System.err.println("All done");
		}
		
		
		/*
		 * Managing multiple resources
		 * 
		 * Notes:
		 * The initializations occur in the code order, and later resource variable initializers can use of the values of the earlier ones.
		 * All resource variables that were successfully initialized will be cleaned up.
		 * Resource variables are cleaned up in reverse order of their declarations.
		 */
		try (InputStream is = new FileInputStream("file1"); 
		     OutputStream os = new FileOutputStream("file2")) {
			// is is initialized before os and cleaned up after it, and is will be cleaned up if there is an
			// exception while initializing os.
			
			// Copy 'is' to 'os'
		} catch (IOException e) {
			System.err.println("IOException happens");
		}
	}
	
	
}
