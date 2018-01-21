package com.optsd.basic.sample.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Unmodifiable Collection: "read-only" collections (cannot be altered)
 * An unmodifiable collection is often a copy of a modifiable collection 
 * Attempts to modify it will result in an UnsupportedOperationException exception
 * 
 * Notice: objects which are present inside the collection can still be ALTERED
 */
public class UnmodifiableCollection {

	 class MyPojoClass {
		private List<Integer> intList = new ArrayList<>();

		public void addValueToIntList(Integer value) {
			intList.add(value);
		}

		public List<Integer> getIntList() {
			return Collections.unmodifiableList(intList);
		}
	}
	
	public static void main(String[] args) {
		UnmodifiableCollection unmodifiableCollection = new UnmodifiableCollection();
		MyPojoClass pojo = unmodifiableCollection.new MyPojoClass();
		pojo.addValueToIntList(42);
		List<Integer> list = pojo.getIntList();
		list.add(69);		// java.lang.UnsupportedOperationException
	}
}
