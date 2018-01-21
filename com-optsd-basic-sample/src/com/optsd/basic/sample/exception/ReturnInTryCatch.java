package com.optsd.basic.sample.exception;

public class ReturnInTryCatch {

	// Bad practice. finally block associated with the try/catch block is executed 
	// before anything is returned. This method will always return 7
	@SuppressWarnings("finally")
	public static int returnTest(int number) {
		try {
			if (number % 2 == 0)
				throw new Exception("Exception thrown");
			else
				return number;
		} catch (Exception e) {
			return 3;
		} finally {
			return 7;
		}
	}
	
	
	public static class FinallyExample {
		public static void main(String[] args) {
			int n = returnTest(4);
			System.out.println(n);
		}

		/*
		 * If the catch block returns a primitive value and that primitive value 
		 * is subsequently changed in the finally block, the value returned in the catch block 
		 * will be returned and the changes from the finally block will be ignored
		 */
		public static int returnTest(int number) {
			int returnNumber = 0;
			try {
				if (number % 2 == 0)
					throw new Exception("Exception thrown");
				else
					return returnNumber;
			} catch (Exception e) {
				return returnNumber;
			} finally {
				returnNumber = 1;
			}
		}
	}
}
