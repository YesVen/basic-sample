package com.optsd.basic.sample.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Stacktraces {

	/*
	 * Notes:
	 * 
	 * - The stacktrace does not include the details of the exception itself. You
	 * can use the toString() method to get those details
	 * 
	 * - Stacktrace printing should be used sparingly. It is often better to use a logging framework,
	 * and pass the exception object to be logged.
	 * 
	 * The class and method names in the stack frames are the internal names for the classes and methods. 
	 * You will need to recognize the following:
	 * 
	 * + A nested or inner class will look like "OuterClass$InnerClass".
	 * + An anonymous inner class will look like "OuterClass$1", "OuterClass$2", etcetera.
	 * + When code in a constructor, instance field initializer or an instance initializer block is being executed, the
	 *   method name will be "".
	 * + When code in a static field initializer or static initializer block is being executed, the method name will be ""
	 * 
	 */
	public void printAStacktrace() {
		try {
			int a = 0;
			int b = 0;
			int c = a / b;
		} catch (ArithmeticException ex) {
			// Print exception and stacktrace to standard output
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	
	
	/*
	 * General approach for doing this is to create a temporary OutputStream or Writer 
	 * that writes to an inmemory buffer and pass that to the printStackTrace(...)
	 * 
	 * The Apache Commons and Guava libraries provide utility methods for capturing a stacktrace as a String:
	 * - org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(Throwable)
	 * - com.google.common.base.Throwables.getStackTraceAsString(Throwable
	 */
	public static class captureStackTraceAsString
	{
		/**
		* Returns the string representation of the stack trace.
		* 
		* Usage: when we cannot use third party libraries 
		*
		* @param throwable the throwable
		* @return the string.
		*/
		public static String stackTraceToString(Throwable throwable) {
			StringWriter stringWriter = new StringWriter();
			throwable.printStackTrace(new PrintWriter(stringWriter));
			return stringWriter.toString();
		}
	}
}
