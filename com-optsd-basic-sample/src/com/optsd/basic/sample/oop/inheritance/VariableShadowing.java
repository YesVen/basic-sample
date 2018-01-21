package com.optsd.basic.sample.oop.inheritance;

import org.junit.Test;

public class VariableShadowing {

	class Car {
		public int gearRatio = 8;

		public String accelerate() {
			return "Accelerate : Car";
		}
	}

	class SportsCar extends Car {
		public int gearRatio = 9;

		public String accelerate() {
			return "Accelerate : SportsCar";
		}

		public void test() {
		}

	}
	
	
	@Test
	public void usage()
	{
		// Variables are SHADOWED and methods are OVERRIDDEN
		// Which variable will be used depends on the class that the variable is declared of. 
		// Which method will be used depends on the actual class of the object that is referenced by the variable.
		Car car = new SportsCar();
		System.out.println(car.gearRatio + " " + car.accelerate());
		// will print out 8 Accelerate : SportsCar
	}
	
}
