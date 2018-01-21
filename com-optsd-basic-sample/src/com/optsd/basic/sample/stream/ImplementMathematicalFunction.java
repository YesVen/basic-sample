package com.optsd.basic.sample.stream;

import java.util.stream.IntStream;

import org.junit.Test;

public class ImplementMathematicalFunction {

	@Test
	public void madhavasApproximationOfPi()
	{
		double pi = Math.sqrt(12)
				* IntStream.rangeClosed(0, 100).mapToDouble(k -> (Math.pow(-3, -k) / (2 * k + 1))).sum();
		
		System.out.println(pi);
	}

	
	@Test
	public void streamsOfPrimitives() 
	{
		IntStream is = IntStream.of(10, 20, 30);
		double average = is.average().getAsDouble();
		System.out.println(average);		// average is 20.0
	}
}
