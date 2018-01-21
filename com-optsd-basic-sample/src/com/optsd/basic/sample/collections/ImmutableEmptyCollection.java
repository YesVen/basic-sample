package com.optsd.basic.sample.collections;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * 
 * @author optsd
 * 
 *         The collections returned by these methods are immutable in that they
 *         will throw UnsupportedOperationException if you attempt to call
 *         methods which would change their contents (add, put, etc.). These
 *         collections are primarily useful as substitutes for empty method
 *         results or other default values, instead of using null or creating
 *         objects with new.
 *
 */
public class ImmutableEmptyCollection {

	@Test
	public void immutableEmptyCollection()
	{
		/*
		 * These methods are generic and will automatically convert the returned
		 * collection to the type it is assigned to. That is, an invocation of e.g.
		 * emptyList() can be assigned to any type of List and likewise for emptySet()
		 * and emptyMap()
		 */
		// Remember: it's immutable collection, cannot change
		List<String> anEmptyList = Collections.emptyList();
		Map<Integer, Date> anEmptyMap = Collections.emptyMap();
		Set<Number> anEmptySet = Collections.emptySet();
		
		System.out.println(anEmptyList == null);
		System.out.println(anEmptyList.isEmpty());
		
		// anEmptyList.add("123");	// Cause UnsupportedOperationException
	}
}
