package test.clyde.aoc.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PermutationsIterator<T> implements Iterator<List<T>> {

	private int i;
	private int n;
	private int[] indexes;
	private List<T> current;
	
	public PermutationsIterator(Permutations<T> permutations) {
		current = permutations.getCurrent();
		n = current.size();
		indexes = new int[n];
		for (int j = 0; j < n; j++) {
			indexes[j] = 0;
		}
		i=0;
	}
	
	@Override
	public boolean hasNext() {
		return current!=null;
	}

	@Override
	public List<T> next() {
		if (!hasNext())
			return null;
		List<T> result = new ArrayList<>(current);
		current = calcNext();
		return result;
	}
	
	private List<T> calcNext() {
		while (i < n) {
			if (indexes[i] < i) {
				List<T> l = swap(current, i % 2 == 0 ? 0 : indexes[i], i);
				indexes[i]++;
				i = 0;
				return l;
			} else {
				indexes[i] = 0;
				i++;
			}
		}
		return null;
	}

	private List<T> swap(List<T> elements, int a, int b) {
		List<T> result = new ArrayList<>(elements);
		result.set(a, elements.get(b));
		result.set(b, elements.get(a));
		return result;
	}

}
