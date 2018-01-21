package com.optsd.basic.sample.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 
 * @author optsd
 *	Method references make excellent self-documenting code, and using method references with Streams makes
 *  complicated processes simple to read and understand
 */
public class SelfDocumentingUsingStreamAndMethodReference {

	public interface Ordered {
		default int getOrder() {
			return 0;
		}
	}

	public interface Valued<V extends Ordered> {
		boolean hasPropertyTwo();

		V getValue();
	}

	public interface Thing<V extends Ordered> {
		boolean hasPropertyOne();

		Valued<V> getValuedProperty();
	}

	
	public <V extends Ordered> List<V> myMethod(List<Thing<V>> things) {
		List<V> results = new ArrayList<V>();
		for (Thing<V> thing : things) {
			if (thing.hasPropertyOne()) {
				Valued<V> valued = thing.getValuedProperty();
				if (valued != null && valued.hasPropertyTwo()) {
					V value = valued.getValue();
					if (value != null) {
						results.add(value);
					}
				}
			}
		}
		results.sort((a, b) -> {
			return Integer.compare(a.getOrder(), b.getOrder());
		});
		return results;
	}
	
	// rewritten using Streams and method references is much more legible and each step of the process
	// is quickly and easily understood - it's not just shorter, it also shows at a glance which interfaces 
    // and classes are responsible for the code in each step:
	public <V extends Ordered> List<V> rewrittenMyMethod(List<Thing<V>> things) {
		return things.stream().filter(Thing::hasPropertyOne)
							  .map(Thing::getValuedProperty)
							  .filter(Objects::nonNull)
							  .filter(Valued::hasPropertyTwo)
							  .map(Valued::getValue)
							  .filter(Objects::nonNull)
							  .sorted(Comparator.comparing(Ordered::getOrder))
							  .collect(Collectors.toList());
	}
}
