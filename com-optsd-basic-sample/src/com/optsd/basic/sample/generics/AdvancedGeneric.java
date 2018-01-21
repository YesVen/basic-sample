package com.optsd.basic.sample.generics;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.Console;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Test;

public class AdvancedGeneric {

	public interface ReferringToDeclaredGenericTypeWithinitsOwnDeclaration
	{
		// T represents the data type the series holds, e.g. Double and DS the series itself
		public interface DataSeries<T, DS extends DataSeries<T, DS>> {
			
			DS add(DS values);
			List<T> data();
		}
		
		public interface DoubleSeries extends DataSeries<Double, DoubleSeries> {
			static DoubleSeries instance(Collection<Double> data) {
				return new DoubleSeriesImpl(data);
			}
		}

		class DoubleSeriesImpl implements DoubleSeries {
			private final List<Double> data;

			DoubleSeriesImpl(Collection<Double> data) {
				this.data = new ArrayList<>(data);
			}

			@Override
			public DoubleSeries add(DoubleSeries values) {
				List<Double> incoming = values != null ? values.data() : null;
				if (incoming == null || incoming.size() != data.size()) {
					throw new IllegalArgumentException("bad series");
				}
				List<Double> newdata = new ArrayList<>(data.size());
				for (int i = 0; i < data.size(); i++) {
					newdata.add(this.data.get(i) + incoming.get(i)); // beware autoboxing
				}
				return DoubleSeries.instance(newdata);
			}

			@Override
			public List<Double> data() {
				return Collections.unmodifiableList(data);
			}
		}
	}
	
	
	/*
	 * Generic parameters can also be bound to more than one type using the T extends Type1 & Type2 & ... syntax
	 * 
	 * You cannot bind the generic parameter to either of the type using OR (|) clause. Only the AND (&) clause is supported. 
	 * Generic type can "EXTENDS ONLY ONE CLASS AND MANY INTERFACES". Class must be placed at the beginning of the list.
	 */
	public interface BindingGenericParameterToMoreThanOneType
	{
		//  the ExampleClass only accepts as generic parameters, types which implement both Flushable and Closeable.
		public class ExampleClass<T extends Flushable & Closeable> {

			// The class methods can choose to infer generic type arguments as either Closeable or Flushable
			/* Assign it to a valid type as you want. */
			public void test(T param) {
				Flushable arg1 = param; // Works
				Closeable arg2 = param; // Works too.
			}

			/* You can even invoke the methods of any valid type directly. */
			public void test2(T param) throws IOException {
				param.flush(); // Method of Flushable called on T and works fine.
				param.close(); // Method of Closeable called on T and works fine too.
			}

		}
		
		
		public static void example()
		{
			ExampleClass<BufferedWriter> arg1; // Works because BufferedWriter implements both Flushable and Closeable
			ExampleClass<Console> arg4; // Does NOT work because Console only implements Flushable
			ExampleClass<ZipFile> arg5; // Does NOT work because ZipFile only implements Closeable
			ExampleClass<Flushable> arg2; // Does NOT work because Closeable bound is not satisfied.
			ExampleClass<Closeable> arg3; // Does NOT work because Flushable bound is not satisfied.
		}
	}
	
	
	public class UsingInstanceofWithGenerics
	{
		class Example<T> {
			public boolean isTypeAString(String s) {
				return s instanceof T; // Compilation error, cannot use T as class type here
			}
		}
		
		
		//  The same class after the compiler compiles (generics has stripped off by type erasure)
		class CompiledExample { // formal parameter is gone
			public boolean isTypeAString(String s) {
				return s instanceof Object; // Both <String> and <Number> are now Object
			}
		}
		
		
		// Solution:
		// always use unbounded wildcard (?) for specifying a type in the instanceof
		// unbounded wildcard is considered a reifiable type
		public boolean isAList(Object obj) {
			return obj instanceof List<?>;
		}
		
		
		// Using a generic instance with instanceof
		class InstanceExample<T> {
			public boolean isTypeAString(T t) {
				return t instanceof String; // No compilation error this time
			}
		}
		// after the type erasure the class will look like
		class AfterInstanceExample { // formal parameter is gone
			public boolean isTypeAString(Object t) {
				return t instanceof String; // No compilation error this time
			}
		}
		
		
	}
	@Test
	public void usage()
	{
		UsingInstanceofWithGenerics usingInstanceofWithGenerics = new UsingInstanceofWithGenerics();
		System.out.println(usingInstanceofWithGenerics.isAList("foo")); // prints false
		System.out.println(usingInstanceofWithGenerics.isAList(new ArrayList<String>())); // prints true
		System.out.println(usingInstanceofWithGenerics.isAList(new ArrayList<Float>())); // prints true
		
		// even if the type erasure happen anyway, now the JVM can distinguish among different types in memory, 
		// even if they use the same reference type (Object)
		Object obj1 = new String("foo"); // reference type Object, object type String
		Object obj2 = new Integer(11); // reference type Object, object type Integer
		System.out.println(obj1 instanceof String); // true
		System.out.println(obj2 instanceof String); // false, it's an Integer, not a String
	}

	
	public interface DifferentWaysToImplementGenericInterfaceOrExtendsGenericClass
	{
		// All the ways listed above are also allowed when using a generic class as a supertype instead of a generic interface.
		
		// Suppose the following generic interface has been declared
		public interface MyGenericInterface<T> {
			public void foo(T t);
		}
		
		// 1. Non-generic class implementation with a specific type
		// Choose a specific type to replace the formal type parameter <T> of MyGenericClass and implement it
		public class NonGenericClass implements MyGenericInterface<String> {
			public void foo(String t) 
			{
				// type T has been replaced by String	
			}

			public void testNonGenericClass()
			{
				NonGenericClass myClass = new NonGenericClass();
				myClass.foo("foo_string"); // OK, legal
				myClass.foo(11); // NOT OK, does not compile
				myClass.foo(new Object()); // NOT OK, does not compile
			}
		}
		
		
		// 2. Generic class implementation
		// Declare another generic interface with the formal type parameter <T> which implements MyGenericInterface
		public class MyGenericSubclass<T> implements MyGenericInterface<T> {
			public void foo(T t) {
				// type T is still the same
			}
			// other methods...
		}

		public class MyGenericSubclass2<U> implements MyGenericInterface<U> { // equivalent to the previous declaration
			public void foo(U t) {
			}
			// other methods...
		}
		
		
		// 3. Raw type class implementation
		//  not recommended, since it is not 100% safe at runtime
		public class MyGenericSubclassRawType implements MyGenericInterface {
			@Override
			public void foo(Object t) {
			}
		}
	}
	
	
	public class UsingGenericToAutocast
	{
		private Map<String, Object> foo;

		public <T> T get(String key) {
			return (T) foo.get(key);
		}
		
		
		@Test
		public void usage()
		{
			String expected = "Hallo";
			map.set(KEY1, expected);
			String value = map.get(KEY1); // Look Ma, no cast!
			Assert.assertEquals(expected, value);

			
			// Solution
			// The keys contains two pieces of information: The actual key (a string) 
			// and the type which the value of the key has.
			final TypedMapKey<String> KEY1 = new TypedMapKey<String>("key1");
			final TypedMapKey<List<String>> KEY2 = new TypedMapKey<List<String>>("key2");

			TypedMap map = new TypedMap();

			List<String> list = new ArrayList<String>();
			map.put(KEY2, list);
			List<String> valueList = map.get(KEY2); // Even with generics
			Assert.assertEquals(list, valueList);

		}
		

		public class TypedMapKey<T> {
		    private String name;
		     
		    public TypedMapKey(String name) {
		        this.name = name;
		    }
		     
		    public String name() {
		        return name;
		    }
		}
		
		
		public class TypedMap2 implements Map<TypedMapKey<?>, Object> {
		    private Map<TypedMapKey<?>, Object> delegate;
		     
		    public TypedMap2( Map<TypedMapKey<?>, Object> delegate ) {
		        this.delegate = delegate;
		    }
		 
		    public TypedMap2() {
		        this.delegate = new HashMap<TypedMapKey<?>, Object>();
		    }
		     
		    @SuppressWarnings( "unchecked" )
		    public <T> T get( TypedMapKey<T> key ) {
		        return (T) delegate.get( key.name() );
		    }
		    
			// ...
		}
		
		
		
		public class TypedMap implements Map<String, Object> {
			private Map<String, Object> delegate;

			public TypedMap(Map<String, Object> delegate) {
				this.delegate = delegate;
			}

			public TypedMap() {
				this.delegate = new HashMap<String, Object>();
			}

			@SuppressWarnings("unchecked")
			public <T> T get(TypedMapKey<T> key) {
				return (T) delegate.get(key.name());
			}

			@SuppressWarnings("unchecked")
			public <T> T remove(TypedMapKey<T> key) {
				return (T) delegate.remove(key.name());
			}

			public <T> void put(TypedMapKey<T> key, T value) {
				delegate.put(key.name(), value);
			}

			// --- Only calls to delegates below

			public void clear() {
				delegate.clear();
			}

			public boolean containsKey(Object key) {
				return delegate.containsKey(key);
			}

			public boolean containsValue(Object value) {
				return delegate.containsValue(value);
			}

			public Set<java.util.Map.Entry<String, Object>> entrySet() {
				return delegate.entrySet();
			}

			public boolean equals(Object o) {
				return delegate.equals(o);
			}

			public Object get(Object key) {
				return delegate.get(key);
			}

			public int hashCode() {
				return delegate.hashCode();
			}

			public boolean isEmpty() {
				return delegate.isEmpty();
			}

			public Set<String> keySet() {
				return delegate.keySet();
			}

			public Object put(String key, Object value) {
				return delegate.put(key, value);
			}

			public void putAll(Map<? extends String, ? extends Object> m) {
				delegate.putAll(m);
			}

			public Object remove(Object key) {
				return delegate.remove(key);
			}

			public int size() {
				return delegate.size();
			}

			public Collection<Object> values() {
				return delegate.values();
			}
		}
	}
}
