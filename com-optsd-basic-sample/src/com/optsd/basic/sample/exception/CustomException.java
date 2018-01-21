package com.optsd.basic.sample.exception;

/**
 * 
 * @author optsd
 * 
 * 	 Checked exception (application exception): If a client can reasonably be expected to recover from an exception. 
 *   Unchecked exception (system exception): If a client cannot do anything to recover from the exception.
 *   Ex: NullPointerException, NumberFormatException, RuntimeException
 * 
 *         Cases where you do want to use a custom exception class:
 * 
 *         You are writing an API or library for use by others, and you want to
 *         allow users of your API to be able to specifically catch and handle
 *         exceptions from your API, and be able to differentiate those
 *         exceptions from other, more generic exceptions.
 * 
 *         You are throwing exceptions for a specific kind of error in one part
 *         of your program, which you want to catch and handle in another part
 *         of your program, and you want to be able to differentiate these
 *         errors from other, more generic errors.
 *
 */
public class CustomException {

	public class StringTooLongException extends RuntimeException {
		// Exceptions can have methods and fields like other classes
		// those can be useful to communicate information to pieces of code catching
		// such an exception
		public final String value;
		public final int maximumLength;

		public StringTooLongException(String value, int maximumLength) {
			super(String.format("String exceeds maximum Length of %s: %s", maximumLength, value));
			this.value = value;
			this.maximumLength = maximumLength;
		}
	}
	
	
	// Custom exception is used just as predefined exceptions
	void validateString(String value) {
		if (value.length() > 30) {
			throw new StringTooLongException(value, 30);
		}
	}
	
	// the fields can be used where the exception is caught and handled
	void anotherMethod(String value) {
		try {
			validateString(value);
		} catch (StringTooLongException e) {
			System.out.println("The string '" + e.value + "' was longer than the max of " + e.maximumLength);
		}
	}
}
