package test.clyde.aoc.utils;

import java.util.Iterator;
import java.util.List;

public class Permutations<T> implements Iterable<List<T>> {
	
	private List<T> current;
	
	public Permutations(List<T> collection) {
		
		this.current = collection;
	}

	public List<T> getCurrent() {
		return current;
	}

	@Override
	public Iterator<List<T>> iterator() {
		return new PermutationsIterator<T>(this);
	}

}
