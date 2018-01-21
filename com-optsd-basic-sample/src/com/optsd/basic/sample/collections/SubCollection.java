package com.optsd.basic.sample.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

/**
 * 
 * @author optsd
 * 
 *         List subList(int fromIndex, int toIndex) 
 *         Here fromIndex is inclusive and toIndex is exclusive. 
 *         List list = new ArrayList(); 
 *         List list1 = list.subList(fromIndex,toIndex); 
 *         
 *         1. If the list doesn't exist in the give range, it throws IndexOutOfBoundException. 
 *         2. What ever changes made on the list1 will impact the same changes in the list.This is called backed collections. 
 *         3. If the fromnIndex is greater than the toIndex (fromIndex > toIndex) it throws IllegalArgumentException.
 *
 */
public class SubCollection {

	/*
	 * List subList(int fromIndex, int toIndex) Here fromIndex is inclusive and toIndex is exclusive. 
	 * List list = new ArrayList(); 
	 * List list1 = list.subList(fromIndex,toIndex);
	 * 
	 * 1. If the list doesn't exist in the give range, it throws IndexOutOfBoundException. 
	 * 2. What ever changes made on the list1 will impact the same changes in the list.This is called backed collections. 
	 * 3. If the fromnIndex is greater than the toIndex (fromIndex > toIndex) it throws IllegalArgumentException.
	 */
	@Test
	public void subList()
	{
		List<String> list = new ArrayList<String>();
		list.add("Hello1");
		list.add("Hello2");

		System.out.println("Before Sublist " + list);		// Before Sublist [Hello1, Hello2]
		List<String> list2 = list.subList(0, 1);

		list2.add("Hello3");
		System.out.println("After sublist changes " + list);	// After sublist changes [Hello1, Hello3, Hello2]
	}
	
	/*
	 * subSet(fromIndex,toIndex): fromIndex is inclusive and toIndex is exclusive
	 * 
	 * The returned set will throw an IllegalArgumentException on an attempt to
	 * insert an element outside its range.
	 */
	@Test
	public void subSet()
	{
		Set set = new TreeSet();
		// Set set1 = set.subSet(0, 1);   // ???
		
		TreeSet treeSet = new TreeSet();
		Set set2 = treeSet.subSet(0, 1);
	}
	
	/*
	 * subMap(fromKey,toKey): fromKey is inclusive and toKey is exclusive
	 * 
	 * If fromKey is greater than toKey or if this map itself has a restricted
	 * range, and fromKey or toKey lies outside the bounds of the range then it
	 * throws IllegalArgumentException.
	 * 
	 * All the collections support backed collections means changes made on the sub
	 * collection will have same change on the main collection.
	 */
	@Test
	public void subMap()
	{
		Map map = new TreeMap();
		// Map map1 = map.subMap(0, 1);	// ???
		
		TreeMap treeMap = new TreeMap();
		Map map2 = treeMap.subMap(0, 1);
	}
}
