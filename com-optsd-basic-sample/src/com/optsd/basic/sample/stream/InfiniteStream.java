package com.optsd.basic.sample.stream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class InfiniteStream {

	@Test
	public void limitItems() {
		// Generate infinite stream - 1, 2, 3, 4, 5, 6, 7, ...
		IntStream naturalNumbers = IntStream.iterate(1, x -> x + 1);
		
		// Print out only the first 5 terms
		naturalNumbers.limit(5).forEach(System.out::println);
		
		
		// Generate an infinite stream of random numbers
		Stream<Double> infiniteRandomNumbers = Stream.generate(Math::random);
		
		// Print out only the first 10 random numbers
		infiniteRandomNumbers.limit(10).forEach(System.out::println);
	}
}
