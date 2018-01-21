package com.optsd.basic.sample.stream;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamSupplier {

	@Test
	public void streamSupplier()
	{
		// Reusing intermediate operations of a stream chain
		Supplier<Stream<String>> streamSupplier = () -> 
		Stream.of("apple", "banana", "orange", "grapes", "melon", "blueberry", "blackberry")
			  .map(String::toUpperCase)
			  .sorted();
		
		streamSupplier.get().filter(s -> s.startsWith("A")).forEach(System.out::println);
		// APPLE
		streamSupplier.get().filter(s -> s.startsWith("B")).forEach(System.out::println);
		// BANANA
		// BLACKBERRY
		// BLUEBERRY
	}
}
