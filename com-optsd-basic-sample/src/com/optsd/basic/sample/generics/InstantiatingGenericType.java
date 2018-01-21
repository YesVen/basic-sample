package com.optsd.basic.sample.generics;

import java.util.function.Supplier;

import org.junit.Test;

public class InstantiatingGenericType {

	// The type T is erased. Since, at runtime, the JVM does not know what T originally was, 
	// it does not know which constructor to call.
	public <T> void genericMethod() {
		T t = new T(); // Can not instantiate the type T.
	}
	
	
	// 1. Passing T's class when calling genericMethod
	public <T> void genericMethod(Class<T> cls) {
		try {
			T t = cls.newInstance();
			System.out.println("Value of t: " + t);
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Could not instantiate: " + cls.getName());
		}
	}
	
	
	// 2. Passing a reference to T's constructor
	// Version >= Java SE 8
	public <T> void genericMethod(Supplier<T> cons) {
		T t = cons.get();
		System.out.println("Value of t: " + t);
	}
	
	
	@Test
	public void test()
	{
		// 1. Passing T's class when calling genericMethod
		genericMethod(String.class);
		
		// 2. Passing a reference to T's constructor
		genericMethod(String::new);
	}
}
