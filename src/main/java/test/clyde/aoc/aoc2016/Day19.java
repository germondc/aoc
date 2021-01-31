package test.clyde.aoc.aoc2016;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

public class Day19 {

	private long testA(int number) {
		Map<Integer, Integer> elves = new HashMap<>();
		List<Integer> index = new ArrayList<>();
		IntStream.range(0, number).forEach(i -> {
			elves.put(i, 1);
			index.add(i);
		});
		
		while (index.size() != 1) {
			Iterator<Integer> iterator = index.iterator();
			boolean removeFirst = false;
			while(iterator.hasNext()) {
				int i = iterator.next();
				int j;
				if (iterator.hasNext())
					j = iterator.next();
				else {
					j=index.get(0);
					removeFirst = true;
				}
				//elves.put(i, elves.get(i)+elves.get(j));
				if (!removeFirst)
					iterator.remove();
			}
			if (removeFirst)
				index.remove(0);
		}
		return index.get(0)+1;
	}
	
	private long testB(int number) {
//		List<Integer> index = new LinkedList<>();
//		IntStream.range(0, number).forEach(i -> index.add(i));
		int[] index = new int[number];
		for (int i=0; i<number; i++) {
			index[i] = i;
		}
		
		
//		List<Integer> test = new ArrayList<>();
		
		int i = 0;
		while (index.length > 1) {
			int adjust = index.length / 2;
			int remove = i+adjust;
			if (remove>=index.length) {
				remove-=index.length;
			}
			
			index = ArrayUtils.remove(index, remove);
//			test.add(index.remove(remove)+1);
			i++;
			if (i>=index.length) {
				i=0;
			}
		}
		
//		int i = 0;
//		int indexSize = number;
//		while (indexSize > 1) {
//			int adjust = indexSize / 2;
//			int remove = i+adjust;
//			if (remove>=indexSize) {
//				remove-=indexSize;
//			}
//			indexSize--;
//			index.add(remove);
//			i++;
//			if (i>=indexSize) {
//				i=0;
//			}
//		}
		
		return index[0]+1;
	}
	
	private int problemA(int size) {
		int index = 2;
		int number = 1;
		while (size / index >= 1) {
			if (size % index >= (index >> 1))
				number = number + index;
			index *= 2;
		}
		return number;
	}
	
	private int testC(int size) {
		Deque<Integer> left = new ArrayDeque<>();
		Deque<Integer> right = new ArrayDeque<>();
		
		for (int i=1; i<=size; i++) {
			if (i < size / 2 + 1) {
				left.addLast(i);
			} else {
				right.addFirst(i);
			}
		}
		
		while (left.size() > 0 && right.size() > 0) {
			if (left.size() > right.size()) {
				left.removeLast();
			} else {
				right.removeLast();
			}
			right.addFirst(left.removeFirst());
			left.addLast(right.removeLast());
		}
		if (left.size() > 0)
			return left.pop();
		else
			return right.pop();
	}
	
	

	public static void main(String[] args) {
		//System.out.println("Test: " + new Day19().testA(5));
		System.out.println("A: " + new Day19().problemA(3018458));
		
		System.out.println("Test: " + new Day19().testC(37));
		System.out.println("B: " + new Day19().testC(3018458));
	}
}
