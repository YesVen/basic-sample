package com.optsd.basic.sample.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Predicates {

	public static class Person {
		private String name;
		private int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public int getAge() {
			return age;
		}

		public String getName() {
			return name;
		}
	}
	
	
	public static class LambdaExample {
		public static void main(String[] args) {
			List<Person> personList = new ArrayList<Person>();
			personList.add(new Person("Jeroen", 20));
			personList.add(new Person("Jack", 5));
			personList.add(new Person("Lisa", 19));
			print(personList, p -> p.getAge() >= 18);
		}

		private static void print(List<Person> personList, Predicate<Person> checker) {
			for (Person person : personList) {
				if (checker.test(person)) {
					System.out.println(person + " matches your expression.");
				} else {
					System.out.println(person + " doesn't match your expression.");
				}
			}
		}
	}
	
}
