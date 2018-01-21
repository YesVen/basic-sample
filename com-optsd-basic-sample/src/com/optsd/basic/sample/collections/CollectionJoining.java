package com.optsd.basic.sample.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.ListUtils;

public class CollectionJoining {

	public void usage()
	{
		List<String> listOne = new ArrayList<>();
		List<String> listTwo = new ArrayList<>();
		
		// First approach. Has more lines but easy to understand
		List<String> newList = new ArrayList<>();
		newList.addAll(listOne);
		newList.addAll(listTwo);
		
		// List<String> newList = new ArrayList<String>(listOne);
		newList.addAll(listTwo);
		List<String> newList2 = new ArrayList<String>(listOne);
		newList2.addAll(listTwo);
		
		// Third approach. Requires third party Apache commons-collections library.
		ListUtils.union(listOne, listTwo);

		// Version >= Java SE 8: Using Streams the same can be achieved by
		List<String> newList3 = Stream.concat(listOne.stream(), listTwo.stream())
									  .collect(Collectors.toList());
	}
}
