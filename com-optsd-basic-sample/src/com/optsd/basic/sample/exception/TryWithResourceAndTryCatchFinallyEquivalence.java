package com.optsd.basic.sample.exception;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TryWithResourceAndTryCatchFinallyEquivalence {

	public void tryWithResource() throws FileNotFoundException {
		// 1. basic try-with-resource
		try (PrintStream stream = new PrintStream("hello.txt")) {
			stream.println("Hello world!");
		}

		// 2. enhanced form of try-with-resources
		try (PrintStream stream = new PrintStream("fileName")) {
			stream.println("Hello world!");
		} catch (NullPointerException ex) {
			System.err.println("Null filename");
		} finally {
			System.err.println("All done");
		}
	}

	public void tryCatchFinally() throws FileNotFoundException {
		// 1.
		// Note that the constructor is not part of the try-catch statement
		PrintStream stream = new PrintStream("hello.txt");
		// This variable is used to keep track of the primary exception thrown
		// in the try statement. If an exception is thrown in the try block,
		// any exception thrown by AutoCloseable.close() will be suppressed.
		Throwable primaryException = null;
		// The actual try block
		try {
			stream.println("Hello world!");
		} catch (Throwable t) {
			// If an exception is thrown, remember it for the finally block
			primaryException = t;
			throw t;
		} finally {
			if (primaryException == null) {
				// If no exception was thrown so far, exceptions thrown in close() will
				// not be caught and therefore be passed on to the enclosing code.
				stream.close();
			} else {
				// If an exception has already been thrown, any exception thrown in
				// close() will be suppressed as it is likely to be related to the
				// previous exception. The suppressed exception can be retrieved
				// using primaryException.getSuppressed().
				try {
					stream.close();
				} catch (Throwable suppressedException) {
					primaryException.addSuppressed(suppressedException);
				}
			}
		}

		// 2.
		try {
			try (PrintStream stream2 = new PrintStream("fileName")) {
				stream2.println("Hello world!");
			}
		} catch (NullPointerException ex) {
			System.err.println("Null filename");
		} finally {
			System.err.println("All done");
		}

	}
}
