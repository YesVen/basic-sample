package com.optsd.basic.sample.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectionRemoving {
	
	@Test
	public void removeItemsFromListWithinALoop()
	{
		List<String> fruits = initList();
		
		// INCORRECT 1: Removing in iteration of for statement Skips "Banana", only print Apple and Strawberry
		// Banana is skipped because it moves to index 0 once Apple is
		// deleted, but at the same time i gets incremented to 1
		for (int i = 0; i < fruits.size(); i++) {
			System.out.println(fruits.get(i));
			if ("Apple".equals(fruits.get(i))) {
				fruits.remove(i);
			}
		}
		
		// INCORRECT 2: Removing in the enhanced for statement Throws java.util.ConcurrentModificationException
		// Because a collection is modified while iterating over it using methods 
		// other than those provided by the iterator object
		fruits = initList();
		for (String fruit : fruits) {
			System.out.println(fruit);
			if ("Apple".equals(fruit)) {
				fruits.remove(fruit);
			}
		}
		
		/**
		 * CORRECT 1: Removing in while loop using an Iterator
		 * Throws: UnsupportedOperationException - if the remove operation is not supported by this iterator
		 * + Immutable collection
		 * + 3rd party library
		 * + Collections.unmodifiable...() 
		 */
		fruits = initList();
		Iterator<String> fruitIterator = fruits.iterator();
		
		while (fruitIterator.hasNext()) {
			String fruit = fruitIterator.next();
			System.out.println(fruit);
			if ("Apple".equals(fruit)) {
				fruitIterator.remove();
			}
		}
		
		
		// CORRECT 2: Iterating forward, adjusting the loop index
		fruits = initList();
		for (int i = 0; i < fruits.size(); i++) {
			System.out.println(fruits.get(i));
			if ("Apple".equals(fruits.get(i))) {
				fruits.remove(i);
				i--;
			}
		}
		
		
		// CORRECT 3: Iterating backwards does not skip anything but the output is reverse.
		fruits = initList();
		for (int i = (fruits.size() - 1); i >= 0; i--) {
			System.out.println(fruits.get(i));
			if ("Apple".equals(fruits.get(i))) {
				fruits.remove(i);
			}
		}
		
		
		// CORRECT 4: Using a "should-be-removed" list
		fruits = initList();
		List<String> shouldBeRemoved = new ArrayList<>();
		for (String fruit : fruits) {
			if ("Apple".equals(fruit)) {
				shouldBeRemoved.add(fruit);
			}
		}
		fruits.removeAll(shouldBeRemoved);
		
		
		/*
		 * In Java 8 the following alternatives are possible. These are cleaner and more
		 * straight forward if the removing does not have to happen in a loop.
		 */
		// CORRECT 1: Filtering a Stream
		//  produces a new List instance and keeps the original List unchanged.
		fruits = initList();
		List<String> filteredList = fruits.stream()
										  .filter(p -> !"Apple".equals(p))
										  .collect(Collectors.toList());
		
		// CORRECT 2: Using removeIf
		fruits = initList();
		fruits.removeIf(p -> !"Apple".equals(p));
		

		/*
		 * While using an Iterator a ConcurrentModificationException is thrown when the
		 * modCount of the List is changed from when the Iterator was created. This
		 * could have happened in the same thread or in a multi-threaded application
		 * sharing the same list.
		 * 
		 * A modCount is an int variable which counts the number of times this list has
		 * been structurally modified. A structural change essentially means an add() or
		 * remove() operation being invoked on Collection object (changes made by
		 * Iterator are not counted). When the Iterator is created, it stores this
		 * modCount and on every iteration of the List checks if the current modCount is
		 * same as and when the Iterator was created. If there is a change in the
		 * modCount value it throws a ConcurrentModificationException.
		 */
		// not throw any exception
		fruits = initList();
		Iterator<String> fruitIterator2 = fruits.iterator();
		fruits.set(0, "Watermelon");
		while (fruitIterator2.hasNext()) {
			System.out.println(fruitIterator2.next());
		}
		
		// adding a new element to the List after initializing an Iterator 
		// will throw a ConcurrentModificationException
		fruits = initList();
		Iterator<String> fruitIterator3 = fruits.iterator();
		fruits.add("Watermelon");
		while (fruitIterator3.hasNext()) {
			System.out.println(fruitIterator3.next()); // ConcurrentModificationException here
		}
		
	}

	private List<String> initList() {
		List<String> fruits = new ArrayList<String>();
		fruits.add("Apple");
		fruits.add("Banana");
		fruits.add("Strawberry");
		return fruits;
	}
}
