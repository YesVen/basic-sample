package com.optsd.basic.sample.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class CollectionInitialization {

	class ListInitialization {
		
		public void constructCollectionFromExistingData() {
			// Java Collections framework. 
			// 1.Use java.utils.Arrays method Arrays.asList
			List<String> data = Arrays.asList("ab", "bc", "cd", "ab", "bc", "cd");

			// 2. All standard collection implementations provide constructors that take
			// another collection as an argument adding all
			// elements to the new collection at the time of construction

			List<String> list = new ArrayList<>(data); // will add data as is
			Set<String> set1 = new HashSet<>(data); // will add data keeping only unique values
			SortedSet<String> set2 = new TreeSet<>(data); // will add data keeping unique values and sorting
			Set<String> set3 = new LinkedHashSet<>(data); // will add data keeping only unique values and preserving the
															// original order

			// Use Google Guava Collections framework
			List<String> list1 = Lists.newArrayList("ab", "bc", "cd");
			List<String> list2 = Lists.newArrayList(data);
			Set<String> set4 = Sets.newHashSet(data);
			SortedSet<String> set5 = Sets.newTreeSet(Arrays.asList("bc", "cd", "ab", "bc", "cd"));
			
			
			// Using Stream (Java SE 8)
			Stream.of("xyz", "abc").collect(Collectors.toList());
			Arrays.stream(new String[] {"xyz", "abc"}).collect(Collectors.toList());
		}
		
		public void declareArrayList()
		{
			List aListOfFruits = new ArrayList();
			// Version >= Java SE 5
			List<String> aListOfFruits5 = new ArrayList<String>();
			// Version >= Java SE 7
			List<String> aListOfFruits7 = new ArrayList<>();
			
			// String "Melon" at index 0 and the String "Strawberry" at index 1
			aListOfFruits.add("Melon");
			aListOfFruits.add("Strawberry");
			
			// add multiple elements with addAll(Collection<? extends E> c) method
			//  "Onion" is placed at 0 index in aListOfFruitsAndVeggies, 
			// "Melon" is at index 1 and "Strawberry" is at index 2
			List<String> aListOfFruitsAndVeggies = new ArrayList<String>();
			aListOfFruitsAndVeggies.add("Onion");
			aListOfFruitsAndVeggies.addAll(aListOfFruits);
		}
	}
	
	
	static class MapInitialization {
		
		// Java Collections framework
		static final Map<String, String> map;
	    static
	    {
	        map = new HashMap<String, String>();
	        map.put("a", "b");
	        map.put("c", "d");
	    }
		Map<String, Object> map1 = new HashMap<>(map);
		SortedMap<String, Object> map2 = new TreeMap<>(map);
		
		// Apache Commons Collections framework: ArrayUtils.toMap as well as MapUtils.toMap
		// Taken from org.apache.commons.lang.ArrayUtils#toMap JavaDoc -> Create a Map mapping colors.
		// 
		// Each element of the array must be either a Map.Entry or an Array, containing at least two elements, where the first
		// element is used as key and the second as value.
		Map colorMap = ArrayUtils.toMap(new String[][] {
		     {"RED", "#FF0000"},
		     {"GREEN", "#00FF00"},
		     {"BLUE", "#0000FF"}});
		
		
		<K, V> // Google Guava Collections framework
		void howToCreateMapsMethod(Function<? super K, V> valueFunction, Iterable<K> keys1, Set<K> keys2,
				SortedSet<K> keys3) {
			ImmutableMap<K, V> map1 = Maps.toMap(keys1, valueFunction); // Immutable copy
			Map<K, V> map2 = Maps.asMap(keys2, valueFunction); // Live Map view
			SortedMap<K, V> map3 = (SortedMap<K, V>) Maps.toMap(keys3, valueFunction); // Live Map view
		}
	}
}
