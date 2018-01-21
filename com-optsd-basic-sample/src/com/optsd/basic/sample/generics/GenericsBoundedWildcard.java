package com.optsd.basic.sample.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author optsd
 * 
 *         * The syntax for Java generics bounded wildcards, representing the
 *         unknown type by ? is: 
 *         
 *           + ? extends T: represents an upper bounded wildcard. The unknown type represents a type 
 *             that must be a subtype of T, or type T itself.
 *            
 *           + ? super T: represents a lower bounded wildcard. The unknown type represents a type 
 *             that must be a supertype of T, or type T itself
 *           
 *         * As a rule of thumb, you should use:
 *         
 *           + ? extends T if you only need "read" access ("input")
 *         
 *           + ? super T if you need "write" access ("output")
 *         
 *           + T if you need both ("modify")
 *           
 *           Using extends or super is usually better because it makes your code more flexible 
 *
 */
public class GenericsBoundedWildcard {
	
	class Shoe {}
	
	class IPhone {}
	
	interface Fruit {}
	
	class Apple implements Fruit {}
	
	class Banana implements Fruit {}
	
	class GrannySmith extends Apple {}
	
	public class FruitHelper {
		
		public void eatAll(Collection<? extends Fruit> fruits) {}

		public void addApple(Collection<? super Apple> apples) {}
	}

	
	// extendsMultipleUpperBounds
	// Since multiple inheritance is not allowed, you can use at most one class as a bound and it must be the first listed.
	// For example, <T extends Comparable<T> & Number> is not allowed because Comparable is an interface, and not a class.
	public <T extends Number & Comparable<T>> void sortNumbers(List<T> n) {
		Collections.sort(n);
	}
	

	public class GenericsTest {

		@Test
		public void test() {
			
			FruitHelper fruitHelper = new FruitHelper();
			
			List<Fruit> fruits = new ArrayList<Fruit>();
			fruits.add(new Apple()); // Allowed, as Apple is a Fruit
			fruits.add(new Banana()); // Allowed, as Banana is a Fruit
			fruitHelper.addApple(fruits); // Allowed, as "Fruit super Apple"
			fruitHelper.eatAll(fruits); // Allowed
			
			Collection<Banana> bananas = new ArrayList<>();
			bananas.add(new Banana()); // Allowed
			// fruitHelper.addApple(bananas); // Compile error: may only contain Bananas!
			fruitHelper.eatAll(bananas); // Allowed, as all Bananas are Fruits
			
			Collection<Apple> apples = new ArrayList<>();
			fruitHelper.addApple(apples); // Allowed
			apples.add(new GrannySmith()); // Allowed, as this is an Apple
			fruitHelper.eatAll(apples); // Allowed, as all Apples are Fruits.
			
			Collection<GrannySmith> grannySmithApples = new ArrayList<>();
			// fruitHelper.addApple(grannySmithApples); // Compile error. GrannySmith is not a supertype of Apple
			apples.add(new GrannySmith()); // Still allowed, GrannySmith is an Apple
			fruitHelper.eatAll(grannySmithApples);// Still allowed, GrannySmith is a Fruit
			
			Collection<Object> objects = new ArrayList<>();
			fruitHelper.addApple(objects); // Allowed, as Object super Apple
			objects.add(new Shoe()); // Not a fruit
			objects.add(new IPhone()); // Not a fruit
			// fruitHelper.eatAll(objects); // Compile error: may contain a Shoe, too!
		}
	}
	
	
	public class creatingBoundedGenericClass {
		
		public abstract class Animal {
			public abstract String getSound();
		}

		public class Cat extends Animal {
			public String getSound() {
				return "Meow";
			}
		}

		public class Dog extends Animal {
			@Override
			public String getSound() {
				return "Woof";
			}
		}

		// Without bounded generics, we cannot make a container class that is both generic 
		// and knows that each element is an animal
		public class AnimalContainer<T> {
			private Collection<T> col;

			public AnimalContainer() {
				col = new ArrayList<T>();
			}

			public void add(T t) {
				col.add(t);
			}

			public void printAllSounds() {
				for (T t : col) {
					// Illegal, type T doesn't have makeSound()
					// it is used as an java.lang.Object here
					System.out.println(t.getSound());
				}
			}
		}
		
		// With generic bound in class definition, this is now possible.
		public class BoundedAnimalContainer<T extends Animal> { // Note bound here.
			private Collection<T> col;

			public BoundedAnimalContainer() {
				col = new ArrayList<T>();
			}

			public void add(T t) {
				col.add(t);
			}

			public void printAllSounds() {
				for (T t : col) {
					// Now works because T is extending Animal
					System.out.println(t.getSound());
				}
			}
		}
		
		
		// restricts the valid instantiations of the generic type
		public void usage()
		{
			// Legal
			AnimalContainer<Cat> a = new AnimalContainer<Cat>();
			// Legal
			AnimalContainer<String> b = new AnimalContainer<String>();
			
			// Legal because Cat extends Animal
			BoundedAnimalContainer<Cat> c = new BoundedAnimalContainer<Cat>();
			// Illegal because String doesn't extends Animal
			BoundedAnimalContainer<String> d = new BoundedAnimalContainer<String>();
		}
	}
}
