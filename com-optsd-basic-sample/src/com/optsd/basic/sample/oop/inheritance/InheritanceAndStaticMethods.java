package com.optsd.basic.sample.oop.inheritance;

public class InheritanceAndStaticMethods {

	static class Parent {
		public static void staticMethod() {
			System.out.println("Inside Parent");
		}
	}
	
	static class Child extends Parent {
		public static void staticMethod() {
			System.out.println("Inside Child");
		}
	}
	
	
	static class StaticMethodTest {
		// In Java, parent and child class both can have static methods with the same name. 
		// But in such cases implementation of static method in child is hiding parent class' implementation, 
		// it's not method overriding. 
		public static void main(String[] args) {
			Parent p = new Child();
			p.staticMethod(); // prints Inside Parent
			((Child) p).staticMethod(); // prints Inside Child
		}
	}
}
