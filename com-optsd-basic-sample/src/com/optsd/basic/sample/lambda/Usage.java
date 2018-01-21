package com.optsd.basic.sample.lambda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JButton;

import org.junit.Test;

public class Usage {
	
	interface TwoArgInterface {
		int operate(int a, int b);
	}
	
	@Test
	public void usage1()
	{
		TwoArgInterface plusOperation = (a, b) -> a + b;
		TwoArgInterface divideOperation = (a, b) -> {
			if (b == 0)
				throw new IllegalArgumentException("Divisor can not be 0");
			return a / b;
		};
		System.out.println("Plus operation of 3 and 5 is: " + plusOperation.operate(3, 5));
		System.out.println("Divide operation 50 by 25 is: " + divideOperation.operate(50, 25));
	}

	
	interface MathOperation {
		boolean unaryOperation(int num);
	}
	
	public static class LambdaTry {
		public static void main(String[] args) {
			// Traditional way
			MathOperation isEven = new MathOperation() {
				@Override
				public boolean unaryOperation(int num) {
					return num % 2 == 0;
				}
			};
			
			// Lambda style
			// 1. Remove class name and functional interface body
			isEven = (int num) -> {
				return num % 2 == 0;
			};
			
			
			// 2. Optional type declaration
			isEven = (num) -> {
				return num % 2 == 0;
			};
			
			// 3. Optional parenthesis around parameter, if it is single parameter
			isEven = num -> {
				return num % 2 == 0;
			};
			
			// 4. Optional curly braces, if there is only one line in function body
			// 5. Optional return keyword, if there is only one line in function body
			isEven = num -> num % 2 == 0;
			
			System.out.println(isEven.unaryOperation(25));
			System.out.println(isEven.unaryOperation(20));
		}
	}
	
	public void lamdbaNewThread() {
		// Old way
		new Thread(new Runnable() {
			public void run() {
				System.out.println("run logic...");
			}
		}).start();

		// lambdas, from Java 8
		new Thread(() -> System.out.println("run logic...")).start();
	}
	
	public void implementMultipleInterfaces()
	{
		// using intersection types (&)
		TreeSet<Long> ts = new TreeSet<>((Comparator<Long> & Serializable) (x, y) -> Long.compare(y, x));
	}
	
	// frequently using intersection types 
	// bring Comparator<Long> & Serializable to marker interface (empty interface) for reuse
	public interface SerializableComparator extends Comparator<Long>, Serializable	{};
	
	public class CustomTreeSet {
		public CustomTreeSet(SerializableComparator comparator) {};
	}
	
	
	public void listenerExample()
	{
		JButton btn = new JButton("My Button");
		
		// Anonymous class listener
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button was pressed");
			}
		});
		
		// Lambda listener
		btn.addActionListener(e -> {
			System.out.println("Button was pressed");
		});
	}

	
	// Collections.sort(...) only works on collections that are subtypes of List. The Set and Collection APIs
	// do not imply any ordering of the elements.
	@Test
	public void sortCollection()
	{
		// 1. Sorting lists
		// Version >= Java SE 1.2
		List<Person> people = new ArrayList<>();
		Collections.sort(people, new Comparator<Person>() {
			public int compare(Person p1, Person p2) {
				return p1.getFirstName().compareTo(p2.getFirstName());
			}
		});
		
		// Version >= Java SE 8
		Collections.sort(
				people, 
				(p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()));
		
		Collections.sort(
				people, 
				Comparator.comparing(Person::getFirstName));
		
		// Comparators built this way can also be chained together
		Collections.sort(
				people,
				Comparator.comparing(Person::getFirstName)
						  .thenComparing(Person::getLastName));
		
		
		// 2. Sorting maps
		// LinkedHashMap must be used as the target. The keys in an ordinary HashMap are unordered
		Map<String, Integer> map = new HashMap<>(); // ... or any other Map class
		map.put("D", 31);
		map.put("B", 22);
		map.put("C", 67);
		map.put("F", 31);
		// populate the map
		map = map.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue())
				.collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue(), (k, v) -> k, LinkedHashMap::new));
		System.out.println(map);		// {B=22, E=23, D=31, F=31, A=45, C=67}
	}
	
	
	class Person
	{
		String firstName;
		String lastName;

		public Person(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}
	}
}
