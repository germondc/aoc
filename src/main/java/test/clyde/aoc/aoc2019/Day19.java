package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import jdk.javadoc.internal.doclets.toolkit.util.DocFinder.Input;
import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day19 {
	private static List<String> INPUT = Utils.readFile("2019/Day19.txt");
	private List<String> input;

	public Day19(List<String> input) {
		this.input = input;
	}

	public long problemA() {
		int count = 0;
		IntCode ic = new IntCode(input.get(0));
		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				IntCode current = ic.copy();
				Queue<Long> inputs = new ArrayDeque<>();
				inputs.add((long) x);
				inputs.add((long) y);
				List<Long> output = current.getValuesForInputs(inputs);
				if (output.get(0) == 1) {
					count++;
				}
			}
		}
		return count;
	}

	public long problemB() {
		List<List<Boolean>> grid = new ArrayList<>();

		IntCode ic = new IntCode(input.get(0));
		int startx = 0;
		int endx = 0;
		for (int y = 0; y < 1250; y++) {
			List<Boolean> row = new ArrayList<>();
			grid.add(row);
			boolean isFirst = true;
			for (int x = 0; x < 1250; x++) {
				if (x<startx || x>endx+5) {
					row.add(false);
					continue;
				}
				IntCode current = ic.copy();
				Queue<Long> inputs = new ArrayDeque<>();
				inputs.add((long) x);
				inputs.add((long) y);
				List<Long> output = current.getValuesForInputs(inputs);
				boolean b = output.get(0) == 1;
				if (b && isFirst) {
					startx = x;
					isFirst = false;
					while (x<endx-1) {
						row.add(true);
						x++;
					}
				
				}
				if (!b && !isFirst) {
					endx = x;
					isFirst = true;
				}
				row.add(b);
			}
		}
		
		int x = 0;
		int y = 0;
		main: for (y = 100; y < grid.size(); y++) {
			for (x = grid.get(y).size() - 1; x >= 99; x--) {
				if (!grid.get(y).get(x))
					continue;
				if (grid.get(y).get(x - 99) && grid.get(y + 99).get(x - 99)) {
					break main;
				}
			}
		}

		return (x - 99) * 10000 + y;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day19(INPUT).problemA());
		System.out.println("B: " + new Day19(INPUT).problemB());
	}
}
