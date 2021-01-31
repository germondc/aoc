package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day17 {

	private int problemA(int input) {
		List<Integer> buffer = new ArrayList<>();
		buffer.add(0);
		int pos=0;
		for (int i=1; i<=2017; i++) {
			pos += input;
			while (pos >= buffer.size()) {
				pos -= buffer.size();
			}
			pos++;
			buffer.add(pos, i);
		}
		
		return buffer.get(pos+1);
	}

	private int problemB(int input) {
		int pos=0;
		int value = 0;
		for (int i=1; i<=50000000; i++) {
			pos += input;
			while (pos >= i) {
				pos -= i;
			}
			if (pos == 0) {
				value = i;
			}
			pos++;
		}
		return value;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day17().problemA(3));
		System.out.println("A: " + new Day17().problemA(376));
//		System.out.println("TestB: " + new Day17().problemB(TEST_LINES.get(0), 5, 1000000000));
		System.out.println("B: " + new Day17().problemB(376));
	}

}
