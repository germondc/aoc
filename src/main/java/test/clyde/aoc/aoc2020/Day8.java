package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day8 {

	private static List<String> LINES = Utils.readFile("2020/Day8.txt");

	private class Instruction {
		String ins;
		int val;

		public Instruction(String ins, int val) {
			super();
			this.ins = ins;
			this.val = val;
		}

		public Instruction copy() {
			Instruction copy = new Instruction(this.ins, this.val);
			return copy;
		}
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day8().problemA());
		System.out.println("B: " + new Day8().problemB());
	}

	private int problemA() {

		List<Instruction> instructions = new ArrayList<>();

		for (String line : LINES) {
			String[] split = line.split(" ");
			String instruction = split[0];
			int by = Integer.valueOf(split[1]);
			Instruction i = new Instruction(instruction, by);
			instructions.add(i);
		}

		int acc = 0;

		List<Integer> values = new ArrayList<>();
		int i = 0;
		while (true) {
			if (values.contains(i))
				break;
			values.add(i);
			Instruction instruction = instructions.get(i);
			if (instruction.ins.equals("nop")) {
				i++;
				continue;
			} else if (instruction.ins.equals("acc")) {
				acc += instruction.val;
				i++;
			} else if (instruction.ins.equals("jmp")) {
				i += instruction.val;
			}
		}

		return acc;
	}

	private int run(List<Instruction> instructions) {
		int acc = 0;

		List<Integer> values = new ArrayList<>();
		int i = 0;
		while (i < instructions.size()) {
			if (values.contains(i))
				break;
			values.add(i);
			Instruction instruction = instructions.get(i);
			if (instruction.ins.equals("nop")) {
				i++;
				continue;
			} else if (instruction.ins.equals("acc")) {
				acc += instruction.val;
				i++;
			} else if (instruction.ins.equals("jmp")) {
				i += instruction.val;
			}
		}

		if (values.contains(instructions.size() - 1))
			return acc;

		return -1;
	}

	private List<Instruction> copy(List<Instruction> source) {
		List<Instruction> result = new ArrayList<>();
		for (Instruction instruction : source) {
			result.add(instruction.copy());
		}
		return result;
	}

	private int problemB() {
		List<Instruction> instructions = new ArrayList<>();

		for (String line : LINES) {
			String[] split = line.split(" ");
			String instruction = split[0];
			int by = Integer.valueOf(split[1]);
			Instruction i = new Instruction(instruction, by);
			instructions.add(i);
		}

		for (int i = 0; i < instructions.size(); i++) {
			Instruction instruction = instructions.get(i);
			List<Instruction> newInstructions = copy(instructions);

			if (instruction.ins.equals("nop")) {
				newInstructions.get(i).ins = "jmp";
			} else if (instruction.ins.equals("jmp")) {
				newInstructions.get(i).ins = "nop";
			}
			int value = run(newInstructions);
			if (value != -1)
				return value;
		}

		return 0;
	}
}
