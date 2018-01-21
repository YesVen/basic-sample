package com.optsd.basic.sample.oop.inheritance;

public class FinalClass {

	// This declares a final class
	final class MyFinalClass {
		/* some code */
	}

	// Compilation error: cannot inherit from final MyFinalClass
	class MySubClass extends MyFinalClass {
		/* more code */
	}
	
	// usage: utility class, immutable class
	public final static class UtilityClass {
		
		// Private constructor to replace the default visible constructor
		private UtilityClass() {
		}

		// Static members can still be used as usual
		public static int doSomethingCool() {
			return 123;
		}
	}
	
	
	public class MyClassWithFinalMethod {
		public final void someMethod() {
		}
	}

	public class MySubClassWithFinalMethod extends MyClassWithFinalMethod {
		@Override
		public void someMethod() { // Compiler error (overridden method is final)
		}
	}
}
