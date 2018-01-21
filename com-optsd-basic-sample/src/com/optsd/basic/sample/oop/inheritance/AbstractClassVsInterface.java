package com.optsd.basic.sample.oop.inheritance;

import org.junit.Test;

public class AbstractClassVsInterface {

	abstract class Animal {
		String name;
		int lifeExpectancy;

		public Animal(String name, int lifeExpectancy) {
			this.name = name;
			this.lifeExpectancy = lifeExpectancy;
		}

		public abstract void remember();

		public abstract void protectOwner();

		public String toString() {
			return this.getClass().getSimpleName() + " : " + name + " : " + lifeExpectancy;
		}
	}
	
	class Dog extends Animal implements Learn
	{

		public Dog(String name, int age) {
			super(name, age);
		}

		@Override
		public void remember() {
			System.out.println(this.getClass().getSimpleName()+" can remember for 5 minutes");
		}

		@Override
		public void protectOwner() {
			System.out.println(this.getClass().getSimpleName()+ " will protect owner");
		}
		
		@Override
		public void learn() {
			System.out.println(this.getClass().getSimpleName() + " can learn");
		}
	}

	class Cat extends Animal implements Climb {
		
		public Cat(String name, int age) {
			super(name, age);
		}

		public void remember() {
			System.out.println(this.getClass().getSimpleName() + " can remember for 16 hours");
		}

		public void protectOwner() {
			System.out.println(this.getClass().getSimpleName() + " won't protect owner");
		}

		public void climb() {
			System.out.println(this.getClass().getSimpleName() + " can climb");
		}
	}
	
	interface Climb {
		void climb();
	}

	interface Think {
		void think();
	}

	interface Learn {
		void learn();
	}

	interface Apply {
		void apply();
	}
	
	class Man implements Think, Learn, Apply, Climb
	{
		String name;
		int age;

		public Man(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public void think() {
			System.out.println("I can think:" + this.getClass().getSimpleName());
		}

		public void learn() {
			System.out.println("I can learn:" + this.getClass().getSimpleName());
		}

		public void apply() {
			System.out.println("I can apply:" + this.getClass().getSimpleName());
		}

		public void climb() {
			System.out.println("I can climb:" + this.getClass().getSimpleName());
		}

		public String toString() {
			return "Man: " + name + " : Age: " + age;
		}
	}
	
	@Test
	public void usage() {
		Dog dog = new Dog("Jack", 16);
		Cat cat = new Cat("Joe", 20);
		
		System.out.println("Dog:" + dog);
		System.out.println("Cat:" + cat);
		
		dog.remember();
		dog.protectOwner();
		
		Learn dl = dog;
		dl.learn();
		
		cat.remember();
		cat.protectOwner();
		
		Climb c = cat;
		c.climb();
		
		Man man = new Man("Ravindra", 40);
		System.out.println(man);
		
		Climb cm = man;
		cm.climb();
		
		Think t = man;
		t.think();
		
		Learn l = man;
		l.learn();
		
		Apply a = man;
		a.apply();
	}
}
