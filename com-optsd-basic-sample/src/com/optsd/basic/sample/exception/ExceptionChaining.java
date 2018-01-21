package com.optsd.basic.sample.exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionChaining {
	
	/*
	 * Many standard exceptions have a constructor with a second cause argument in
	 * addition to the conventional message argument. The cause allows you to chain
	 * exceptions
	 */
	public class AppErrorException extends RuntimeException {
		public AppErrorException() {
			super();
		}

		public AppErrorException(String message) {
			super(message);
		}

		public AppErrorException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	public String readFirstLine(String file) throws AppErrorException {
		
		try (BufferedReader r = new BufferedReader(new FileReader(file))) {
			String line = r.readLine();
			if (line != null) {
				return line;
			} else {
				throw new AppErrorException("File is empty: " + file);
			}
		} catch (IOException ex) {
			throw new AppErrorException("Cannot read file: " + file, ex);
		}
	}
}
