package com.optsd.basic.sample.exception;

/*
 * The throws clause forms part of a method's signature for the purpose of method overriding. An override method
 * can be declared with the same set of checked exceptions as thrown by the overridden method, or with a subset.
 * However the override method cannot add extra checked exceptions
 */
public class ThrowsAndMethodOverriding {

	public class OddNumberException extends Exception { // a checked exception
	}

	public void checkEven(int number) throws OddNumberException {
		if (number % 2 != 0) {
			throw new OddNumberException();
		}
	}

	// A throws clause can declare a list of types, and can include unchecked exceptions as well as checked exceptions
	public void checkEven(Double number) throws OddNumberException, ArithmeticException {
		if (!Double.isFinite(number)) {
			throw new ArithmeticException("INF or NaN");
		} else if (number % 2 != 0) {
			throw new OddNumberException();
		}
	}

	class PrimeNumberException extends OddNumberException {
	}

	class NonEvenNumberException extends OddNumberException {
	}
	
	interface Throws
	{
		public void checkEven(int number) throws OddNumberException;

		void checkEven(Double number) throws OddNumberException;
	}
	
	class SubThrows implements Throws
	{
		@Override
		public void checkEven(int number) throws NullPointerException // OK—NullPointerException is an unchecked exception
		{
		}
		@Override
		public void checkEven(Double number) throws OddNumberException // OK—identical to the superclass
		{
		}
	}
	
	class AnotherSubThrows implements Throws
	{
		@Override
		public void checkEven(int number) throws PrimeNumberException, NonEvenNumberException // OK—these are both subclasses
		{
		}
		
		@Override
		public void checkEven(Double number) throws IOExcepion // ERROR
		{
		}
	}
}
