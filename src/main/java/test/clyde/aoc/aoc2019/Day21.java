package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import test.clyde.aoc.utils.Utils;

public class Day21 {
	private static List<String> INPUT = Utils.readFile("2019/Day21.txt");
	private List<String> input;

	public Day21(List<String> input) {
		this.input = input;
	}

	public long problemA() {
		IntCode ic = new IntCode(input.get(0));
		String[] commands = new String[] { "NOT A J", "NOT C T", "AND D T", "OR T J", "WALK" };
		Queue<Long> commandQueue = new ArrayDeque<>();
		for (String command : commands) {
			for (char c : command.toCharArray()) {
				commandQueue.add((long) c);
			}
			commandQueue.add(10L);
		}
		List<Long> outputs = ic.getValuesForInputs(commandQueue);
		long result = printOutput(outputs);
		return result;
	}

	private long printOutput(List<Long> output) {
		for (long l : output) {
			if (l > 255)
				return l;
			System.out.print((char) l);
		}
		return -1;
	}

	public long problemB() {
		IntCode ic = new IntCode(input.get(0));
		String[] commands = new String[] { "NOT A J", "NOT C T", "AND D T", "OR T J", "NOT B T", "AND C T", "AND H T",
				"OR T J", "RUN" };
		commands = new String[] { "NOT A J", "NOT C T", "AND D T", "AND E T", "OR T J", "NOT B T", "AND D T", "OR T J",
				"NOT C T", "AND D T", "AND H T", "OR T J", "RUN" };
		Queue<Long> commandQueue = new ArrayDeque<>();
		for (String command : commands) {
			for (char c : command.toCharArray()) {
				commandQueue.add((long) c);
			}
			commandQueue.add(10L);
		}
		List<Long> outputs = ic.getValuesForInputs(commandQueue);
		long result = printOutput(outputs);
		return result;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day21(INPUT).problemA());
		System.out.println("B: " + new Day21(INPUT).problemB());
	}
}