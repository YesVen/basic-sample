package com.optsd.basic.sample.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class FlattenStream {
	
	@Test
	public void flattenStream()
	{
		// Array of List of Items can be converted into a single List
		List<String> list1 = Arrays.asList("one", "two");
		List<String> list2 = Arrays.asList("three","four","five");
		List<String> list3 = Arrays.asList("six");
		
		System.out.println(Stream.of(list1, list2, list3).collect(Collectors.toList()));
		// [[one, two], [three, four, five], [six]]

		List<String> finalList = Stream.of(list1, list2, list3).flatMap(Collection::stream).collect(Collectors.toList());
		System.out.println(finalList);		// [one, two, three, four, five, six]
		
		/*---------------------------------------------------------------*/
		
		// Map containing List of Items as values can be Flattened to a Combined List
		Map<String, List<Integer>> map = new LinkedHashMap<>();
		map.put("a", Arrays.asList(1, 2, 3));
		map.put("b", Arrays.asList(4, 5, 6));
		
		List<Integer> allValues = map.values()						// Collection<List<Integer>>				
								     .stream()						// Stream<List<Integer>>
								     .flatMap(List::stream)			// Stream<Integer>
								     .collect(Collectors.toList());
		System.out.println(allValues); 		// [1, 2, 3, 4, 5, 6]
		
		/*---------------------------------------------------------------*/
		
		// List of Map can be flattened into a single continuous Stream
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map1 = new HashMap<>();
		map1.put("1", "one");
		map1.put("2", "two");
		Map<String, String> map2 = new HashMap<>();
		map2.put("3", "three");
		map2.put("4", "four");
		list.add(map1);
		list.add(map2);
		
		Set<String> output = list.stream()							// Stream<Map<String, String>>
								 .map(Map::values)					// Stream<List<String>>
								 .flatMap(Collection::stream)		// Stream<String>
								 .collect(Collectors.toSet());		// Set<String>
		System.out.println(output); 								// [one, two, three,four]
	}

}
