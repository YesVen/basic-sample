package com.optsd.basic.sample.generics;

public class GenericClassCreation {

	// T for "type", E for "element" and K/V for "key"/"value"
	public class Param<T> {
		private T value;

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}
	}
	
	
	// Multiple type parameters
	public class MultiGenericParam<T, S> {
		private T firstParam;
		private S secondParam;

		public MultiGenericParam(T firstParam, S secondParam) {
			this.firstParam = firstParam;
			this.secondParam = secondParam;
		}

		public T getFirstParam() {
			return firstParam;
		}

		public void setFirstParam(T firstParam) {
			this.firstParam = firstParam;
		}

		public S getSecondParam() {
			return secondParam;
		}

		public void setSecondParam(S secondParam) {
			this.secondParam = secondParam;
		}
	}
	
	
	public void usage()
	{
		// T is Integer
		Param<Integer> integerParam = new Param<Integer>();
		
		// Version >= Java SE 7
		integerParam = new Param<>();
		
		// The type argument can be any reference type, including arrays and other generic types
		Param<String[]> stringArrayParam;
		Param<int[][]> int2dArrayParam;
		Param<Param<Object>> objectNestedParam;
		
		MultiGenericParam<String, String> aParam = new MultiGenericParam<String, String>("value1", "value2");
		MultiGenericParam<Integer, Double> dayOfWeekDegrees = new MultiGenericParam<Integer, Double>(1, 2.6);
	}
	
	
	// Extending a generic class
	public abstract class AbstractParam<T> {
		private T value;

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}
	}
	
	// When extending this class, that type parameter can be replaced 
	// by a type argument written inside <>, or the type parameter can remain unchanged
	
	// String replace the type parameter
	public class Email extends AbstractParam<String> {
		// ...
	}

	// Integer replace the type parameter
	public class Age extends AbstractParam<Integer> {
		// ...
	}

	//  the type parameter remains unchanged
	public class Height<T> extends AbstractParam<T> {
		// ...
	}

	// doesn't use generics at all, so it's similar to if the class had an Object parameter
	// The compiler warn: "AbstractParam is a raw type", but it will compile the ObjectParam class
	public class ObjectParam extends AbstractParam {
		// ...
	}

	//  2 type parameters, choosing the second parameter as the type parameter passed to the superclass.
	public class MultiParam<T, E> extends AbstractParam<E> {
		// ...
	}
	
	
	public void usageExtends()
	{
		Email email = new Email();
		email.setValue("test@example.com");
		String retrievedEmail = email.getValue();
		
		Age age = new Age();
		age.setValue(25);
		Integer retrievedAge = age.getValue();
		int autounboxedAge = age.getValue();
		
		Height<Integer> heightInInt = new Height<>();
		heightInInt.setValue(125);
		Height<Float> heightInFloat = new Height<>();
		heightInFloat.setValue(120.3f);
		
		MultiParam<String, Double> multiParam = new MultiParam<>();
		multiParam.setValue(3.3);
		
		AbstractParam<Double> height = new AbstractParam<Double>() {};
		height.setValue(198.6);
		
		// Note: using the diamond with anonymous inner classes is not allowed
		// AbstractParam<Double> height = new AbstractParam<>() {};	-> compile error
	}
	
	// <>: the diamond. it is legal (though bad style) to have whitespace or comments between the two
}
