package com.optsd.basic.sample.exception;

import java.util.Scanner;

public class ExceptionHandling {
	
//	public static void main(String[] args) {
//		int a, b, result;
//		Scanner input = new Scanner(System.in);
//		System.out.println("Input two integers");
//		
//		a = input.nextInt();
//		b = input.nextInt();
//		
//		result = a / b;
//		System.out.println("Result = " + result);
//		input.close();
//	}
	
	
	// typical solution to this problem may be to first check if b == 0
//	public static void main(String[] args) {
//		int a, b, result;
//		Scanner input = new Scanner(System.in);
//		System.out.println("Input two integers");
//		a = input.nextInt();
//		b = input.nextInt();
//
//		if (b == 0) {
//			System.out.println("You cannot divide by zero.");
//			input.close();
//			return;
//		}
//
//		result = a / b;
//		System.out.println("Result = " + result);
//		input.close();
//	}
	
	
	// replace the if flow control with a try-catch block
	public static void main(String[] args) {
		int a, b, result;
		Scanner input = new Scanner(System.in);
		System.out.println("Input two integers");
		a = input.nextInt();
		b = input.nextInt();

		try {
			result = a / b;
		} catch (ArithmeticException e) {
			System.out.println("An ArithmeticException occurred. Perhaps you tried to divide by zero.");
			input.close();
			return;
		}
		System.out.println("Result = " + result);
		input.close();
	}
}
