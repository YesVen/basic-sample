package com.optsd.basic.sample.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class Usage {
	
	@Test
	public void usageStream()
	{
		Stream<String> fruitStream = Stream.of("apple", "banana", "pear", "kiwi", "orange");
		
		fruitStream.filter(s -> s.contains("a"))
				   .map(String::toUpperCase)
				   .sorted()
				   .forEach(System.out::println);

		IntStream.range(1, 10).filter(a -> a % 2 == 0).forEach(System.out::println);

		List<String> list = Arrays.asList("FOO", "BAR");
		Iterable<String> iterable = () -> list.stream().map(String::toLowerCase).iterator();

		for (String str : iterable) {
			System.out.println(str);
		}

		for (String str : iterable) {
			System.out.println(str);
		}

		//  Argument checks are always performed, even without a terminal operation:
		try {
			IntStream.range(1, 10).filter(null);
		} catch (NullPointerException e) {
			System.out.println("We got a NullPointerException as null was passed as an argument to filter()");
		}
	}
}
