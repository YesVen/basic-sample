package com.optsd.basic.sample.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class StreamToCollection {

	@Test
	public void collectStreamToCollection() {
		
		System.out.println(Arrays.asList("apple", "banana", "pear", "kiwi", "orange")
			  .stream()
			  .filter(s -> s.contains("a"))
			  .collect(Collectors.toList())
			  );
		// prints: [apple, banana, pear, orange]
		
		// int[] arrays can be converted to List<Integer> using streams
		int[] ints = {1,2,3};
		List<Integer> list = IntStream.of(ints).boxed().collect(Collectors.toList());
		
		// Explicit control over the implementation of List or Set
		List<String> strings = Arrays.asList("apple", "banana", "pear", "kiwi", "orange");
		
		// syntax with method reference
		System.out.println(strings
		.stream()
		.filter(s -> s != null && s.length() <= 4)
		.collect(Collectors.toCollection(ArrayList::new)));
		
		// syntax with lambda
		System.out.println(strings
				.stream()
				.filter(s -> s != null && s.length() <= 4)
				.collect(Collectors.toCollection(() -> new LinkedHashSet<>())));
		
		// Collecting Elements using toMap
		List<Student> students = new ArrayList<Student>();
		students.add(new Student(1, "test1"));
		students.add(new Student(2, "test2"));
		students.add(new Student(3, "test3"));

		Map<Integer, String> IdToName = students.stream().collect(Collectors.toMap(Student::getId, Student::getName));
		System.out.println(IdToName);

		// Collecting Elements to Map of Collections
		List<Student> list2 = new ArrayList<>();
		list2.add(new Student("Davis", SUBJECT.MATH, 35.0));
		list2.add(new Student("Davis", SUBJECT.SCIENCE, 12.9));
		list2.add(new Student("Davis", SUBJECT.GEOGRAPHY, 37.0));

		list2.add(new Student("Sascha", SUBJECT.ENGLISH, 85.0));
		list2.add(new Student("Sascha", SUBJECT.MATH, 80.0));
		list2.add(new Student("Sascha", SUBJECT.SCIENCE, 12.0));
		list2.add(new Student("Sascha", SUBJECT.LITERATURE, 50.0));

		list2.add(new Student("Robert", SUBJECT.LITERATURE, 12.0));
		
		Map<String, List<SUBJECT>> map = new HashMap<>();
		list2.stream().forEach(s -> {
			map.computeIfAbsent(s.getName(), x -> new ArrayList<>()).add(s.getSubject());
		});
		System.out.println(map);
		
		//  from ArrayList to Map<String, Map<>>
		List<Student> list3 = new ArrayList<>();
		list3.add(new Student("Davis", SUBJECT.MATH, 1, 35.0));
		list3.add(new Student("Davis", SUBJECT.SCIENCE, 2, 12.9));
		list3.add(new Student("Davis", SUBJECT.MATH, 3, 37.0));
		list3.add(new Student("Davis", SUBJECT.SCIENCE, 4, 37.0));
		
		list3.add(new Student("Sascha", SUBJECT.ENGLISH, 5, 85.0));
		list3.add(new Student("Sascha", SUBJECT.MATH, 1, 80.0));
		list3.add(new Student("Sascha", SUBJECT.ENGLISH, 6, 12.0));
		list3.add(new Student("Sascha", SUBJECT.MATH, 3, 50.0));
		
		list3.add(new Student("Robert", SUBJECT.ENGLISH, 5, 12.0));
		
		Map<String, Map<SUBJECT, List<Double>>> map2 = new HashMap<>();
		
		list3.stream().forEach(s -> {
			map2.computeIfAbsent(s.getName(), x -> new HashMap<>())
					.computeIfAbsent(s.getSubject(), x -> new ArrayList<>()).add(s.getGrade());
		});
		System.out.println(map2);
	}
	
	public class Student {
		int id;
		String name;
		SUBJECT subject;
		int mark;
		Double grade;

		public Student(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public Student(String name, SUBJECT subject, Double grade) {
			this.subject = subject;
			this.name = name;
			this.grade = grade;
		}
		
		public Student(String name, SUBJECT subject, int mark, Double grade) {
			this.subject = subject;
			this.name = name;
			this.mark = mark;
			this.grade = grade;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public SUBJECT getSubject() {
			return subject;
		}

		public Double getGrade() {
			return grade;
		}

		public int getMark() {
			return mark;
		}
	}
	
	enum SUBJECT {
		MATH, SCIENCE, GEOGRAPHY, ENGLISH, LITERATURE
	}

}
