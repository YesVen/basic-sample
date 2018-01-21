package com.optsd.basic.sample.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GenericMethod {

	// The type parameter T is scoped to the method
	// and is independent of type parameters of other methods.
	public <T> List<T> makeList(T t1, T t2) {
		List<T> result = new ArrayList<T>();
		result.add(t1);
		result.add(t2);
		return result;
	}
	
	public void usage() {
		List<String> listString = makeList("Jeff", "Atwood");
		List<Integer> listInteger = makeList(1, 2);
		
		 // it can be necessary to override this type inference with explicit type arguments
		// Java doesn't support omitting the class or object on which the method is called
		// when type arguments are explicitly provided.
		consumeObjects(this.<Object>makeList("Jeff", "Atwood").stream());
	}

	void consumeObjects(Stream<Object> stream) {
		// ...
	}
}
