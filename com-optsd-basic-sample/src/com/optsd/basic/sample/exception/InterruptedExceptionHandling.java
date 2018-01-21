package com.optsd.basic.sample.exception;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InterruptedExceptionHandling {

	public void usage()
	{
		// Bad. Don't do this.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// disregard
		}
		
		
		// When nothing will interrupt your code
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new AssertionError(e);
		}
		
		// Suppresses the exception but resets the interrupted state letting later code
		// detect the interrupt and handle it properly.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return; // return ... your expectations are still broken at this point - try not to
			// do more work.
		}
	}

	
	// Let the caller determine how to handle the interrupt if you're unsure
	public void myLongRunningMethod() throws InterruptedException {
	}
	
	
	// This declares a custom checked exception.
	public class MyException extends Exception {
	// constructors omitted.
	}

	// This declares a custom unchecked exception.
	public class MyException2 extends RuntimeException {
		// constructors omitted.
	}
	
	// INCORRECT
//	public void methodThrowingCheckedException(boolean flag) {
//		int i = 1 / 0; // Compiles OK, throws ArithmeticException
//		if (flag) {
//			throw new MyException(); // Compilation error
//		} else {
//			throw new MyException2(); // Compiles OK
//		}
//	}
	
	
	// CORRECTED
	public void methodThrowingCheckedException(boolean flag) throws MyException {
		int i = 1 / 0; // Compiles OK, throws ArithmeticException
		if (flag) {
			throw new MyException(); // Compilation error
		} else {
			throw new MyException2(); // Compiles OK
		}
	}
	
	// INCORRECT
//	public void methodWithPropagatedCheckedException() {
//	InputStream is = new FileInputStream("someFile.txt"); // Compilation error
//	// FileInputStream throws IOException or a subclass if the file cannot
//	// be opened. IOException is a checked exception.
//	}
	
	// CORRECTED (Version A)
	public void methodWithPropagatedCheckedExceptionA() throws IOException {
		InputStream is = new FileInputStream("someFile.txt");
	}
	
	
	// CORRECTED (Version B)
	public void methodWithPropagatedCheckedExceptionB() {
		try {
			InputStream is = new FileInputStream("someFile.txt");
		} catch (IOException ex) {
			System.out.println("Cannot open file: " + ex.getMessage());
		}
	}
	
	// deal with a checked exception in a static field initializer
	// INCORRECT
//	public class Test {
//		private static final InputStream is = new FileInputStream("someFile.txt"); // Compilation error
//	}

	// CORRECTED
	public static class Test {
		private static final InputStream is;
		static {
			InputStream tmp = null;
			try {
				tmp = new FileInputStream("someFile.txt");
			} catch (IOException ex) {
				System.out.println("Cannot open file: " + ex.getMessage());
			}
			is = tmp;
		}
	}
}
