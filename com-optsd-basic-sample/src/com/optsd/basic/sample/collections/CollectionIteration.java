package com.optsd.basic.sample.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class CollectionIteration {

	@Test
	public void iteratingOverList()
	{
		List<String> names = new ArrayList<>(Arrays.asList("Clementine", "Duran", "Mike"));
		
		// Version >= Java SE 8
		names.forEach(System.out::println);
		
		// parallelism use
		names.parallelStream().forEach(System.out::println);
		
		// Version >= Java SE 5
		for (String name : names) {
			System.out.println(name);
		}
		
		// Version < Java SE 5
		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}

		// Version >= Java SE 1.2
		// Creates ListIterator which supports both forward as well as backward traversal
		ListIterator<String> listIterator = names.listIterator();
		// Iterates list in forward direction
		while (listIterator.hasNext()) {
			System.out.println(listIterator.next());
		}
		
		// Iterates list in backward direction once reaches the last element from above
		// iterator in forward direction
		while (listIterator.hasPrevious()) {
			System.out.println(listIterator.previous());
		}
	}
	
	
	@Test
	public void iteratingOverSet()
	{
		Set<String> names = new HashSet<>(Arrays.asList("Clementine", "Duran", "Mike"));
		
		// Version >= Java SE 8
		names.forEach(System.out::println);
		
		// Version >= Java SE 5
		for (Iterator<String> iterator = names.iterator(); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
		
		for (String name : names) {
			System.out.println(name);
		}
		
		// Version < Java SE 5
		Iterator iterator = names.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	
	@Test
	public void iteratingOverMap()
	{
		Map<Integer, String> names = new HashMap<>();
		names.put(1, "Clementine");
		names.put(2, "Duran");
		names.put(3, "Mike");
		
		// Version >= Java SE 8
		names.forEach((key, value) -> System.out.println("Key: " + key + " Value: " + value));
		
		// Version >= Java SE 5
		for (Map.Entry<Integer, String> entry : names.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		
		// Iterating over only keys
		for (Integer key : names.keySet()) {
			System.out.println(key);
		}
		
		// Iterating over only values
		for (String value : names.values()) {
			System.out.println(value);
		}
		
		// Version < Java SE 5
		Iterator entries = names.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}
}
