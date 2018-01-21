package com.optsd.basic.sample.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;

public class StreamConversion {

	@Test
	public void iteratorToStream()
	{
		Iterator<String> iterator = Arrays.asList("A", "B", "C").iterator();
		Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
		Stream<String> stream = StreamSupport.stream(spliterator, false);
	}
	
	
	@Test
	public void createMapByStream()
	{
		// Simple case without duplicate keys
		Stream<String> characters = Stream.of("A", "B", "C");
		Map<Integer, String> map = characters.collect(Collectors.toMap(element -> element.hashCode(), element -> element));
		// Map<Integer, String> map = characters.collect(Collectors.toMap(element -> element.hashCode(), Function.identity()));
		System.out.println(map); 		// map = {65=A, 66=B, 67=C}
		
		
		// Case where there might be duplicate keys. IllegalStateException is thrown
		// Use toMap(Function, Function, BinaryOperator) instead.
		Stream<String> duplicateCharacters = Stream.of("A", "B", "B", "C");
		Map<Integer, String> duplicateMap = duplicateCharacters.collect(Collectors.toMap(element -> element.hashCode(),
				Function.identity(), (existingVal, newVal) -> (existingVal + newVal)));
		System.out.println(duplicateMap);		// map = {65=A, 66=BB, 67=C}
		
		
		// Grouping by value
		class Person 
		{
			String firstName;
			String lastName;
			
			public Person(String firstName, String lastName) {
				this.firstName = firstName;
				this.lastName = lastName;
			}

		    String getFirstName() {
				return firstName;
			}

		    String getLastName() {
				return lastName;
			}
		}
		List<Person> people = Arrays.asList(
				new Person("Sam", "Rossi"),
				new Person("Sam", "Verdi"),
				new Person("John", "Bianchi"),
				new Person("John", "Rossi"),
				new Person("John", "Verdi")
				);
		
		Map<String, List<String>> groupingMap = people.stream().collect(Collectors.groupingBy(Person::getFirstName,
				Collectors.mapping(Person::getLastName, Collectors.toList())));
		System.out.println(groupingMap); 		// map = {John=[Bianchi, Rossi, Verdi], Sam=[Rossi, Verdi]}
		
		// FrequencyMap: count identity
		Stream.of("apple", "orange", "banana", "apple")
		  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
		  .entrySet()
		  .forEach(System.out::println);
	}
	
	
	public IntStream stringToIntStream(String in) {
		return in.codePoints();
	}
	
	public String intStreamToString(IntStream intStream) {
		return intStream.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
	
	
	@Test
	public void convertStringAndIntStream()
	{
		String in = "Opt SD";
		IntStream stringToIntStream = stringToIntStream(in);
		String intStreamToString = intStreamToString(stringToIntStream);
		System.out.println(intStreamToString);
		org.junit.Assert.assertEquals(in, intStreamToString);
	}
	
	
	@Test
	public void convertOptionalStreamToValueStream()
	{
		Optional<String> op1 = Optional.empty();
		Optional<String> op2 = Optional.of("Hello World");
		
		List<String> result = Stream.of(op1, op2)
										.filter(Optional::isPresent)
										.map(Optional::get)
										.collect(Collectors.toList());
		System.out.println(result); 	//[Hello World]
	}
	
	
	@Test
	public void joiningStreamToString()
	{
		Stream<String> fruitStream = Stream.of("apple", "banana", "pear", "kiwi", "orange");

		String result = fruitStream.filter(s -> s.contains("a"))
								   .map(String::toUpperCase)
								   .sorted()
								   .collect(Collectors.joining(", "));
		System.out.println(result);		// APPLE, BANANA, ORANGE, PEAR
		
		// pre- and postfixes
		String prePostfixResult = fruitStream.filter(s -> s.contains("e"))
								   .map(String::toUpperCase).sorted()
								   .collect(Collectors.joining(", ", "Fruits: ", "."));
		
		System.out.println(prePostfixResult);		// Fruits: APPLE, ORANGE, PEAR.
	}
	
	
	@Test
	public void collectStreamToArray()
	{
		List<String> fruits = Arrays.asList("apple", "banana", "pear", "kiwi", "orange");
		String[] filteredFruits = fruits.stream()
										.filter(s -> s.contains("a"))
										.toArray(String[]::new);
		System.out.println(Arrays.toString(filteredFruits));	// prints: [apple, banana, pear, orange]
	}
}
