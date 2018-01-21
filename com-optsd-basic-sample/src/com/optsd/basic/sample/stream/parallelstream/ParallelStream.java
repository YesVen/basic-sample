package com.optsd.basic.sample.stream.parallelstream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class ParallelStream {

	@Test
	public void parallelStream()
	{
		List<String> data = Arrays.asList("One", "Two", "Three", "Four", "Five");
		
		Stream<String> aParallelStream = data.parallelStream();		
		// Stream<String> aParallelStream = data.stream().parallel();		// the same
		
		aParallelStream.forEach(System.out::println);
	}
}
