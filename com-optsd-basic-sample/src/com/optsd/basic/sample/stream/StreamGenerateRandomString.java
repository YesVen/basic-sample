package com.optsd.basic.sample.stream;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamGenerateRandomString {

	// returns true for all chars in 0-9, a-z and A-Z
	boolean useThisCharacter(char c) {
		// check for range to avoid using all unicode Letter (e.g. some chinese symbols)
		return c >= '0' && c <= 'z' && Character.isLetterOrDigit(c);
	}

	public String generateRandomString(long length) throws NoSuchAlgorithmException {
		// 20 Bytes as a seed is rather arbitrary, it is the number used in the JavaDoc example
		final SecureRandom rng = new SecureRandom(SecureRandom.getInstance("SHA1PRNG").generateSeed(20));

		// Since there is no native CharStream, we use an IntStream instead
		// and convert it to a Stream<Character> using mapToObj.
		// We need to specify the boundaries for the int values to ensure they can
		// safely be cast to char
		Stream<Character> randomCharStream = rng.ints(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
				.mapToObj(i -> (char) i).filter(this::useThisCharacter).limit(length);
		// now we can use this Stream to build a String utilizing the collect method.
		String randomString = randomCharStream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
		return randomString;
	}
	
	@Test
	public void generateRandomString()
	{
		try {
			System.out.println(generateRandomString(20L));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}
