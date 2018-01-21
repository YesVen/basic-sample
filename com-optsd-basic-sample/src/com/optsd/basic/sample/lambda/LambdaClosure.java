package com.optsd.basic.sample.lambda;

import java.util.function.IntUnaryOperator;

/*
 * A lambda closure is created when a lambda expression references the variables of an enclosing scope (global or local). 
 * The rules for doing this are the same as those for inline methods and anonymous classes.
 * 
 * Local variables from an enclosing scope that are used within a lambda have to be final. 
 * With Java 8 (the earliest version that supports lambdas), they don't need to be declared final 
 * in the outside context, but must be treated that way
 */
public class LambdaClosure {

	public void usage()
	{
		int n = 0; // With Java 8 there is no need to explicit final
		Runnable r = () -> { // Using lambda
		int i = n;
		// do something
		};
		
		
		int n2 = 0;
		Runnable r2 = () -> { // Using lambda
		int i = n2;
		// do something
		};
		n2++; // Will generate an error.
		
		
		// If it is necessary to use a changing variable within a lambda, the normal approach
		// is to declare a final copy of the variable and use the copy
		int n3 = 0;
		final int k = n3; // With Java 8 there is no need to explicit final
		Runnable r3 = () -> { // Using lambda
		int i = k;
		// do something
		};
		n3++; // Now will not generate an error
		r3.run(); // Will run with i = 0 because k was 0 when the lambda was created
	}
	
	
	//  Java lambda cannot be created in a way that allows it to see changes in the environment in which it was instantiated
	// Does not compile ...
	public IntUnaryOperator createAccumulator() {
		int value = 0;
		IntUnaryOperator accumulate = (x) -> {
			value += x;
			return value;
		};
		return accumulate;
	}
	
	/*
	 * Solution: simulate it using a regular class 
	 * Compiles, but is incorrect ...
	 * 
	 * The problem is that this breaks the design contract for the IntUnaryOperator
	 * interface which states that instances should be functional and stateless. If
	 * such a closure is passed to built-in functions that accept functional
	 * objects, it is liable to cause crashes or erroneous behavior
	 */
	public class AccumulatorGenerator {
		private int value = 0;

		public IntUnaryOperator createAccumulator() {
			IntUnaryOperator accumulate = (x) -> {
				value += x;
				return value;
			};
			return accumulate;
		}
	}
	
	
	// Correct ...
	// Closures that encapsulate mutable state should be implemented as regular classes
	public class Accumulator {
		private int value = 0;

		public int accumulate(int x) {
			value += x;
			return value;
		}
	}
}
