package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day5 {
	private static List<String> LINES = Utils.readFile("2019/Day5.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day5b.txt");

	private long problemA(List<String> lines) {
		return getValue(lines.get(0), 1);
	}

	private long getValue(String line, int input) {
		String[] split = line.split(",");

		int index = 0;
		List<Integer> outputs = new ArrayList<>();
		while (!split[index].equals("99")) {
			String opcode = split[index];
			if (opcode.length() == 5)
				System.out.println(opcode);
			int opcodeValue = Integer.valueOf(opcode);
			int instruction = opcodeValue % 10;
			int mode1 = (opcodeValue / 100) % 10;
			int mode2 = (opcodeValue / 1000) % 10;
			int mode3 = (opcodeValue / 10000) % 10;
			if (instruction == 1) {
				int value1 = getItemValue(split, mode1, index + 1);
				int value2 = getItemValue(split, mode2, index + 2);
				int address = getItemValue(split, 1, index + 3);
				split[address] = Long.toString(value1 + value2);
				index += 4;
			} else if (instruction == 2) {
				int value1 = getItemValue(split, mode1, index + 1);
				int value2 = getItemValue(split, mode2, index + 2);
				int address = getItemValue(split, 1, index + 3);
				split[address] = Long.toString(value1 * value2);
				index += 4;
			} else if (instruction == 3) {
				int address = getItemValue(split, 1, index + 1);
				split[address] = Long.toString(input);
				index += 2;
			} else if (instruction == 4) {
				int value = getItemValue(split, mode1, index + 1);
				outputs.add(value);
				index += 2;
			} else if (instruction == 5) {
				int value1 = getItemValue(split, mode1, index + 1);
				int value2 = getItemValue(split, mode2, index + 2);
				if (value1 != 0)
					index = value2;
				else
					index += 3;
			} else if (instruction == 6) {
				int value1 = getItemValue(split, mode1, index + 1);
				int value2 = getItemValue(split, mode2, index + 2);
				if (value1 == 0)
					index = value2;
				else
					index += 3;
			} else if (instruction == 7) {
				int value1 = getItemValue(split, mode1, index + 1);
				int value2 = getItemValue(split, mode2, index + 2);
				int address = getItemValue(split, 1, index + 3);
				if (value1 < value2)
					split[address] = "1";
				else
					split[address] = "0";
				index += 4;
			} else if (instruction == 8) {
				int value1 = getItemValue(split, mode1, index + 1);
				int value2 = getItemValue(split, mode2, index + 2);
				int address = getItemValue(split, 1, index + 3);
				if (value1 == value2)
					split[address] = "1";
				else
					split[address] = "0";
				index += 4;
			} else {
				System.out.println("need to cater for: " + split[index]);
			}

		}

		for (int i = 0; i < outputs.size() - 1; i++) {
			if (outputs.get(i) != 0L)
				System.out.println("there is a problem");
		}

		return outputs.get(outputs.size() - 1);
	}

	private int getItemValue(String[] items, int mode, int index) {
		int valueAtIndex = Integer.valueOf(items[index]);
		if (mode == 0) {
			return Integer.valueOf(items[valueAtIndex]);
		} else {
			return valueAtIndex;
		}
	}

	private long problemB(List<String> lines) {
		return getValue(lines.get(0), 5);
	}

	public static void main(String[] args) {
		//		System.out.println("A: " + new Day5().getValue("1002,4,3,4,33"));
		System.out.println("A: " + new Day5().problemA(LINES));
		System.out.println("TestB: " + new Day5().getValue("3,9,8,9,10,9,4,9,99,-1,8", 8));
		System.out.println("TestB: " + new Day5().getValue("3,9,8,9,10,9,4,9,99,-1,8", 9));
		System.out.println("TestB: " + new Day5().getValue("3,9,7,9,10,9,4,9,99,-1,8", 7));
		System.out.println("TestB: " + new Day5().getValue("3,9,7,9,10,9,4,9,99,-1,8", 8));
		System.out.println("TestB: " + new Day5().getValue("3,3,1108,-1,8,3,4,3,99", 8));
		System.out.println("TestB: " + new Day5().getValue("3,3,1108,-1,8,3,4,3,99", 9));
		System.out.println("TestB: " + new Day5().getValue("3,3,1107,-1,8,3,4,3,99", 3));
		System.out.println("TestB: " + new Day5().getValue("3,3,1107,-1,8,3,4,3,99", 8));
		System.out.println("TestB: " + new Day5().getValue("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 0));
		System.out.println("TestB: " + new Day5().getValue("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 4));
		System.out.println("TestB: " + new Day5().getValue("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", 0));
		System.out.println("TestB: " + new Day5().getValue("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", 4));
		System.out.println("TestB: " + new Day5().getValue(TESTB.get(0), 1));
		System.out.println("TestB: " + new Day5().getValue(TESTB.get(0), 8));
		System.out.println("TestB: " + new Day5().getValue(TESTB.get(0), 10));
		System.out.println("B: " + new Day5().problemB(LINES));
	}

}
