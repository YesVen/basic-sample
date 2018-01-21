package com.optsd.basic.sample.oop.inheritance;

import org.junit.jupiter.api.Test;

public class Inheritance {
	
	// Parent class
	public class BaseClass 
	{
		public int x;
		
		private int y = 5;
		
		public int getY() 
		{
			return y;
		}
		
		public void baseMethod()
		{
			System.out.println("Doing base class stuff");
		}
	}
	
	
	// child class
	public class SubClass extends BaseClass
	{
		public SubClass() 
		{
		}
		
		public SubClass(int x) 
		{
			this.x = x; 	// Can access fields
		}
		
		public void printY() 
		{
			// System.out.println(y); 		// Illegal, can't access private field x
			System.out.println(getY()); // Legal, prints 5
		}
		
		public void anotherMethod() 
		{
			System.out.println("Doing subclass stuff");
		}
	}
	
	
	@Test
	public void usage()
	{
		SubClass s = new SubClass();
		s.printY();
		s.baseMethod(); 		// Still valid , prints "Doing base class stuff"
		s.anotherMethod(); 	// Also valid, prints "Doing subclass stuff"
	}
}
