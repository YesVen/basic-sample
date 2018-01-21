package com.optsd.basic.sample.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamOperation {

	@Test
	public void createAStream()
	{
		Collection<String> stringList = new ArrayList<>();
		Stream<String> stringStream = stringList.parallelStream();
		
		String[] strValues = { "aaa", "bbbb", "ddd", "cccc" };
		Stream<String> anotherStringStream = Arrays.stream(strValues);
		Stream<String> stringStreamAlternative = Stream.of(strValues);
		
		// Stream.of() has a varargs parameter
		Stream<Integer> integerStream = Stream.of(1, 2, 3);
		
		// primitive Streams
		IntStream intStream = IntStream.of(1, 2, 3);
		DoubleStream doubleStream = DoubleStream.of(1.0, 2.0, 3.0);
		
		// These primitive streams can also be constructed using the Arrays.stream() method
		IntStream anotherIntStream = Arrays.stream(new int[]{ 1, 2, 3 });
		
		// create a Stream from an array with a specified range
		int[] intValues= new int[]{1, 2, 3, 4, 5};
		IntStream intStram = Arrays.stream(intValues, 1, 3);
		
		// any primitive stream can be converted to boxed type stream using the boxed method
		Stream<Integer> boxedIntegerStream = intStream.boxed();
	}
	
	
	@Test
	public void iterateAIntStream()
	{
		String[] names = { "Jon", "Darin", "Bauke", "Hans", "Marc" };
		
		IntStream.range(0, names.length).mapToObj(i -> String.format("#%d %s", i + 1, names[i]))
				.forEach(System.out::println);
		
		System.out.println("--------------------------------------");

		for (int i = 0; i < names.length; i++) {
			String newName = String.format("#%d %s", i + 1, names[i]);
			System.out.println(newName);
		}
	}
	
	
	@Test
	public void concatenateStream()
	{
		Collection<String> abc = Arrays.asList("a", "b", "c");
		Collection<String> digits = Arrays.asList("1", "2", "3");
		Collection<String> greekAbc = Arrays.asList("alpha", "beta", "gamma");
		
		// Example 1 - Concatenate two Streams
		final Stream<String> concat1 = Stream.concat(abc.stream(), digits.stream());
		concat1.forEach(System.out::print);			// prints: abc123
		
		System.out.println("\n--------------------------------------");
		
		// Example 2 - Concatenate more than two Streams
		final Stream<String> concat2 = Stream.concat(Stream.concat(abc.stream(), digits.stream()), greekAbc.stream());
		System.out.println(concat2.collect(Collectors.joining(", ")));
		// prints: a, b, c, 1, 2, 3, alpha, beta, gamma
		
		System.out.println("--------------------------------------");
		
		final Stream<String> concat3 = Stream.of(
				abc.stream(), digits.stream(), greekAbc.stream())
				.flatMap(s -> s);
				// or `.flatMap(Function.identity());` (java.util.function.Function)
		System.out.println(concat3.collect(Collectors.joining(", ")));
		// prints: a, b, c, 1, 2, 3, alpha, beta, gamma
	}
	
	@Test
	public void reduceStream()
	{
		// reduce method to implement the sum() method
		//Initialize istr
		IntStream istr = IntStream.of(1, 2, 3, 4, 5);
		OptionalInt optionalInt = istr.reduce((a,b)->a+b);
		System.out.println(optionalInt.getAsInt());
		
		//  combining a Stream<LinkedList<T>> into a single LinkedList<T>
		// Create a Stream<LinkedList<T>>
		Stream<LinkedList<String>> listStream = Stream.of(new LinkedList<>(Arrays.asList("a", "b", "c")),
				new LinkedList<>(Arrays.asList("g", "h", "j")));
		
		Optional<LinkedList<String>> bigList = listStream
				.reduce((LinkedList<String> list1, LinkedList<String> list2) -> {
					LinkedList<String> retList = new LinkedList<String>();
					retList.addAll(list1);
					retList.addAll(list2);
					return retList;
				});
		System.out.println(bigList.get());
		
		//  identity element: identity element for addition is 0, as x+0==x,  identity element is 1, as x*1==x
		// Create a Stream<LinkedList<T>>
		Stream<LinkedList<String>> listStream2 = Stream.of(new LinkedList<>(Arrays.asList("Tran", "Si", "Dai")),
				new LinkedList<>(Arrays.asList("Nguyen", "Thi", "My", "Trinh")));
		
		LinkedList<String> bigList2 = listStream2.reduce(new LinkedList<String>(),
				(LinkedList<String> list1, LinkedList<String> list2) -> {
					LinkedList<String> retList = new LinkedList<String>();
					retList.addAll(list1);
					retList.addAll(list2);
					return retList;
				});
		System.out.println(bigList2);
	}
	
	
	@Test
	public void searchInStream()
	{
		OptionalInt findFirst = IntStream.iterate(1, i -> i + 1) 	// Generate an infinite stream 1,2,3,4...
				.filter(i -> (i * i) > 50000) 						// Filter to find elements where the square is >50000
				.findFirst();										// Find the first filtered element
		System.out.println(findFirst.getAsInt());
	}
	
	
	@Test
	public void sliceStream()
	{
		Stream<Integer> collection = Stream.iterate(1, x -> x + 1);
		
		final long n = 20L; // the number of elements to skip
		final long maxSize = 30L; // the number of elements the stream should be limited to
		final Stream<Integer> slice = collection.skip(n).limit(maxSize);
		slice.forEach(System.out::println);
	}
	
	
	@Test
	public void sortUsingStream()
	{
		List<String> data = new ArrayList<>();
		data.add("Sydney");
		data.add("London");
		data.add("New York");
		data.add("Amsterdam");
		data.add("Mumbai");
		data.add("California");
		System.out.println(data);
		
		List<String> sortedData = data.stream().sorted().collect(Collectors.toList());
		System.out.println(sortedData);			// [Amsterdam, California, London, Mumbai, New York, Sydney]	

		List<String> sortedData2 = data.stream().sorted((s1, s2) -> s2.compareTo(s1))
												.collect(Collectors.toList());
		System.out.println(sortedData2);		// [Sydney, New York, Mumbai, London, California, Amsterdam]
		
		List<String> reverseSortedData = data.stream().sorted(Comparator.reverseOrder())
													  .collect(Collectors.toList());
		System.out.println(reverseSortedData);		// [Sydney, New York, Mumbai, London, California, Amsterdam]
	}
}
