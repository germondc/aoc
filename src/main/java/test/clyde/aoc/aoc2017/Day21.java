package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day21 {

	private static List<String> LINES = Utils.readFile("2017/Day21.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day21a.txt");

	private class Rule {
		private boolean[][] in;
		private boolean[][] out;

		public Rule(boolean[][] in, boolean[][] out) {
			super();
			this.in = in;
			this.out = out;
		}

		private Rule() {

		}

		public long getHash() {
			long value = 0;
			int index = 1;
			if (in.length==2)
				value=1;
			for (int i = 0; i < in.length; i++) {
				for (int j = 0; j < in[i].length; j++) {
					if (in[j][i]) {
						value += Math.pow(2, index);
					}
					index++;
				}
			}
			return value;
		}

		private Rule copy() {
			Rule rule = new Rule();
			rule.in = new boolean[in.length][in.length];
			for (int i = 0; i < in.length; i++) {
				for (int j = 0; j < in[i].length; j++) {
					rule.in[i][j] = this.in[i][j];
				}
			}
			rule.out = out;
			return rule;
		}

		public List<Rule> getCombinations() {
			List<Rule> rules = new ArrayList<>();
			rules.add(this);
			Rule current = this;
			for (int rot = 0; rot < 7; rot++) {
				if (rot == 3) {
					current = this;
					Rule copy = current.copy();
					rules.add(copy);
					for (int i = 0; i < current.in.length; i++) {
						for (int j = 0; j < current.in[i].length; j++) {
							copy.in[i][j] = current.in[in.length - i - 1][j];
						}
					}
					current = copy;
				} else {
					Rule copy = current.copy();
					rules.add(copy);
					for (int i = 0; i < current.in.length; i++) {
						for (int j = 0; j < current.in[i].length; j++) {
							copy.in[i][j] = current.in[in.length - j - 1][i];
						}
					}
					current = copy;
				}

			}
			return rules;
		}
	}

	private List<Rule> getRules(List<String> lines) {
		List<Rule> result = new ArrayList<>();
		for (String line : lines) {
			String in = line.substring(0, line.indexOf("=>") - 1);
			String out = line.substring(line.indexOf("=>") + 3);
			boolean[][] ins = new boolean[in.indexOf("/")][in.indexOf("/")];
			boolean[][] outs = new boolean[out.indexOf("/")][out.indexOf("/")];
			int row = 0;
			for (String s : in.split("/")) {
				int col = 0;
				for (char c : s.toCharArray()) {
					ins[row][col] = (c == '#');
					col++;
				}
				row++;
			}
			row = 0;
			for (String s : out.split("/")) {
				int col = 0;
				for (char c : s.toCharArray()) {
					outs[row][col] = (c == '#');
					col++;
				}
				row++;
			}
			result.addAll((new Rule(ins, outs)).getCombinations());
		}
		return result;
	}

	private long problemA(List<String> lines, int iterations) {
		// List<Rule> rules = getRules(lines);
		Map<Long, Rule> rules = new HashMap<>();
		for (Rule r : getRules(lines)) {
			long hash = r.getHash();
			if (!rules.containsKey(hash)) {
				rules.put(hash, r);
			}
		}
		boolean[][] grid = new boolean[3][3];
		grid[0][1] = true;
		grid[1][2] = true;
		grid[2][0] = true;
		grid[2][1] = true;
		grid[2][2] = true;

		for (int i = 0; i < iterations; i++) {
			int size = (grid.length % 2 == 0) ? 2 : 3;
			int newDim = grid.length / size * (size + 1);
			boolean[][] newGrid = new boolean[newDim][newDim];

			for (int row = 0; row < grid.length; row += size) {
				for (int col = 0; col < grid.length; col += size) {
					long hash = getHashForSection(grid, row, col, size);
					Rule r = rules.get(hash);
					if (r == null)
						System.out.println("norule");
					boolean[][] newPart = r.out;
					for (int rowNew = 0; rowNew < newPart.length; rowNew++) {
						for (int colNew = 0; colNew < newPart.length; colNew++) {
							newGrid[(row / size * (size + 1))
									+ rowNew][(col / size * (size + 1)) + colNew] = newPart[rowNew][colNew];
						}
					}
				}
			}
			grid = newGrid;
		}

		return countOn(grid);
	}
	
	private long countOn(boolean[][] grid) {
		long count = 0;
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				if (grid[row][col])
					count++;
			}
		}
		return count;
	}
	
	private String printGrid(boolean[][] grid) {
		String result = "";
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				if (grid[row][col])
					result += "#";
				else
					result += ".";
			}
			result+=System.lineSeparator();
		}
		return result;
	}

	private long getHashForSection(boolean[][] grid, int row, int col, int size) {
		long value = 0;
		int index = 1;
		if (size==2)
			value=1;
		for (int i = col; i < col + size; i++) {
			for (int j = row; j < row + size; j++) {
				if (grid[j][i]) {
					value += Math.pow(2, index);
				}
				index++;
			}
		}
		return value;
	}

	private int problemB(List<String> lines) {
		return 0;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day21().problemA(TEST_LINES, 2));
		System.out.println("A: " + new Day21().problemA(LINES, 5));
		System.out.println("TestB: " + new Day21().problemB(TEST_LINES));
		System.out.println("B: " + new Day21().problemA(LINES, 18));
	}

}
