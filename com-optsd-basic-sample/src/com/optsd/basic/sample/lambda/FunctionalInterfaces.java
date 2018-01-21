package com.optsd.basic.sample.lambda;

/*
 * Lambdas can only operate on a functional interface, which is an interface with just one abstract method. 
 * Functional interfaces can have any number of default or static methods. 
 * (For this reason, they are sometimes referred to as Single Abstract Method Interfaces, or SAM Interfaces).
 */
public class FunctionalInterfaces {
	// example of functional interfaces
	interface Foo1 {
		void bar();
	}

	interface Foo2 {
		int bar(boolean baz);
	}

	interface Foo3 {
		String bar(Object baz, int mink);
	}

	interface Foo4 {
		default String bar() { // default so not counted
			return "baz";
		}

		void quux();
	}
	
	/*
	 * When declaring a functional interface, the @FunctionalInterface annotation can be added. 
	 * This has no special effect, but a compiler error will be generated if this annotation is applied 
	 * to an interface which is not functional, thus acting as a reminder that the interface should not be changed.
	 */
	@FunctionalInterface
	interface Foo5 {
		void bar();
	}

	@FunctionalInterface
	interface BlankFoo1 extends Foo3 { // inherits abstract method from Foo3
	}

	@FunctionalInterface
	interface Foo6 {
		void bar();

		boolean equals(Object obj); // overrides one of Object's method so not counted
	}
	
	
	// not a functional interface: more than one abstract method
	interface BadFoo {
		void bar();

		void quux(); // <-- Second method prevents lambda: which one should be considered as lambda?
	}
	
	// not a functional interface: do not have any methods
	interface BlankFoo2 { }	
	
	
	interface Parent {
		public int parentMethod();
	}

	// Child cannot be a functional interface since it has two specified methods
	interface Child extends Parent {
		public int ChildMethod();
	}
}
