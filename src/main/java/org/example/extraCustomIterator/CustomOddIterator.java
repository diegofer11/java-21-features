package org.example.extraCustomIterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CustomOddIterator implements Iterator<Integer> {
	private int currentIndex;
	private final List<Integer> list;
	private Integer nextOdd;

	public CustomOddIterator(final List<Integer> list) {
		this.list = list;
		this.currentIndex = 0;
		this.nextOdd = null;
	}

	@Override
	public boolean hasNext() {
		if (nextOdd != null) {
			return true;
		}

		while (currentIndex < list.size()) {
			final Integer currentNumber = list.get(currentIndex);
			if (isOdd(currentNumber)) {
				nextOdd = currentNumber;
				currentIndex++;
				return true;
			} else {
				currentIndex++;
			}
		}
		return false;
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		final Integer result = nextOdd;
		nextOdd = null;
		return result;
	}

	private boolean isOdd(final Integer currentNumber) {
		return currentNumber != null && currentNumber % 2 != 0;
	}
}
