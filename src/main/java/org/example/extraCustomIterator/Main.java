package org.example.extraCustomIterator;

import java.util.List;

import org.example.utils.LoggerUtils;

public class Main {
	public static void main(String[] args) {
		List<Integer> testCase1 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
		List<Integer> testCase2 = List.of(2, 4, 6, 8);
		List<Integer> testCase3 = List.of(1, 3, 5);
		List<Integer> testCase4 = List.of();
		List<Integer> testCase5 = List.of(2, 4, 7);

		runTestCase("testCase1", testCase1); // testCase1 - odd numbers = 5
		runTestCase("testCase2", testCase2); // testCase2 - odd numbers = 0
		runTestCase("testCase3", testCase3); // testCase3 - odd numbers = 3
		runTestCase("testCase4", testCase4); // testCase4 - odd numbers = 0
		runTestCase("testCase5", testCase5); // testCase5 - odd numbers = 1
	}

	private static void runTestCase(final String caseName, final List<Integer> list) {
		CustomOddIterator iterator = new CustomOddIterator(list);
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		LoggerUtils.info("CustomOddIterator", "main", caseName + " - odd numbers = " + count);
	}
}
