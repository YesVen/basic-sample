package com.optsd.basic.sample.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import org.junit.Test;

public class NumericalStreamStatistic {

	@Test
	public void numericalStreamStatistic()
	{
		List<Integer> naturalNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		IntSummaryStatistics stats = naturalNumbers.stream().mapToInt((x) -> x).summaryStatistics();
		
		System.out.println(stats);
	}
}
