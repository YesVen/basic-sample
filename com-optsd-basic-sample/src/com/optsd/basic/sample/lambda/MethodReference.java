package com.optsd.basic.sample.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author optsd
 *
 *         Cheat-Sheet 
 *         Method Reference Format 				Code 					Equivalent Lambda 
 *         Static method 						TypeName::method 		(args) -> TypeName.method(args) 
 *         Non-static method (on instance*) 	instance::method 		(args) -> instance.method(args) 
 *         Non-static method (no instance) 		TypeName::method 		(instance, args) -> instance.method(args)
 *         Constructor** 						TypeName::new 			(args) -> new TypeName(args) 
 *         Array constructor 					TypeName[]::new 		(int size) -> new TypeName[size]
 *         
 *         * instance can be any expression that evaluates to a reference to an instance, e.g. getInstance()::method, this::method
 *		   * If TypeName is a non-static inner class, constructor reference is only valid within the scope of an outer class instance
 */
public class MethodReference {

	class Person {
		
		private final String name;
		private final String surname;

		public Person(String name, String surname) {
			this.name = name;
			this.surname = surname;
		}

		public String getName() {
			return name;
		}

		public String getSurname() {
			return surname;
		}
	}
	
	@Test
	public void usage()
	{
		List<Person> people = getSomePeople();
		
		// Instance method reference (to an arbitrary instance)
		people.stream().map(Person::getName);
		// equivalent lambda
		people.stream().map(person -> person.getName());

		
		// Instance method reference (to a specific instance)
		people.forEach(System.out::println);
		// equivalent lambda
		people.forEach(person -> System.out.println(person));
		
		
		// Static method reference
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		numbers.stream().map(String::valueOf);
		// equivalent lambda
		numbers.stream().map(num -> String.valueOf(num));
		
		
		// Reference to a constructor
		List<String> strings = Arrays.asList("1", "2", "3");
		strings.stream().map(Integer::new);
		// equivalent lambda
		strings.stream().map(s -> new Integer(s));
		
	}
	

	private List<Person> getSomePeople() {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("Opt", "SD"));
		persons.add(new Person("Trinh", "Nguyen"));
		persons.add(new Person("Quanh", "Van"));
		persons.add(new Person("Hieu", "Nguyrn"));
		persons.add(new Person("Huyen", "Vo"));
		return persons;
	}
}
