package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import test.clyde.aoc.utils.Permutations;
import test.clyde.aoc.utils.Utils;

public class Day7 {
	private static List<String> LINES = Utils.readFile("2019/Day7.txt");
	private static List<String> TEST = Utils.readFile("2019/Day7a.txt");

	private long problemA(List<String> lines) {
		String line = lines.get(0);

		List<Integer> start = Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 });
		long max = 0;
		for (List<Integer> values : new Permutations<>(start)) {
			long current = getValueForPhase(line, values.toArray(new Integer[values.size()]));
			if (current > max)
				max = current;
		}

		return max;
	}

	private long getValueForPhase(String line, Integer[] phase) {
		long input = 0;
		for (int p : phase) {
			input = getValue(line, p, input);
		}
		return input;
	}
	
	private long problemB(List<String> lines) {
		String line = lines.get(0);

		List<Integer> start = Arrays.asList(new Integer[] { 5, 6, 7, 8, 9 });
		long max = 0;
		for (List<Integer> values : new Permutations<>(start)) {
			long current = getValueForPhaseB(line, values.toArray(new Integer[values.size()]));
			if (current > max)
				max = current;
		}

		return max;
	}

	private long getValueForPhaseB(String line, Integer[] phase) {
		List<Amp> amps = new ArrayList<>();
		IntStream.range(0, 5).forEach(i -> amps.add(new Amp(i, line.split(","), phase[i])));
		for (int i=0; i<4; i++) {
			amps.get(i).nextAmp = amps.get(i+1);
		}
		amps.get(4).nextAmp = amps.get(0);

		Amp current = amps.get(0);
		current.inputs.add(0L);
		while (!(current.isDone && current.id==4)) {
			processAmp(current);
			current = current.nextAmp;
		}
		return current.lastOutput;
	}

	private long getValue(String line, int input1, long input2) {
		String[] split = line.split(",");
		List<String[]> splits = new ArrayList<>();
		splits.add(split);
		return getValueForSplit(splits, 0, input1, input2);
	}
	
	private class Amp {
		int id;
		Amp nextAmp;
		long lastOutput;
		Queue<Long> inputs;
		String[] codes;
		int ip = 0;
		boolean isDone;
		
		public Amp(int id, String[] codes, long firstInput) {
			super();
			this.id = id;
			this.inputs = new ArrayDeque<>();
			this.inputs.add(firstInput);
			this.codes = codes;
		}

		@Override
		public String toString() {
			return String.format("Amp [id=%s]", id);
		}
	}
	
	private void processAmp(Amp amp) {
		String[] split = amp.codes;
		while (!split[amp.ip].equals("99")) {
			String opcode = split[amp.ip];
			int opcodeValue = Integer.valueOf(opcode);
			int instruction = opcodeValue % 10;
			int mode1 = (opcodeValue / 100) % 10;
			int mode2 = (opcodeValue / 1000) % 10;
			int mode3 = (opcodeValue / 10000) % 10;
			if (instruction == 1) {
				long value1 = getItemValue(split, mode1, amp.ip + 1);
				long value2 = getItemValue(split, mode2, amp.ip + 2);
				int address = (int) getItemValue(split, 1, amp.ip + 3);
				split[address] = Long.toString(value1 + value2);
				amp.ip += 4;
			} else if (instruction == 2) {
				long value1 = getItemValue(split, mode1, amp.ip + 1);
				long value2 = getItemValue(split, mode2, amp.ip + 2);
				int address = (int) getItemValue(split, 1, amp.ip + 3);
				split[address] = Long.toString(value1 * value2);
				amp.ip += 4;
			} else if (instruction == 3) {
				int address = (int) getItemValue(split, 1, amp.ip + 1);
				split[address] = Long.toString( amp.inputs.remove());
				amp.ip += 2;
			} else if (instruction == 4) {
				long value = getItemValue(split, mode1, amp.ip + 1);
				amp.ip += 2;
				amp.nextAmp.inputs.add(value);
				amp.lastOutput = value;
				return;
			} else if (instruction == 5) {
				long value1 = getItemValue(split, mode1, amp.ip + 1);
				long value2 = getItemValue(split, mode2, amp.ip + 2);
				if (value1 != 0)
					amp.ip = (int)value2;
				else
					amp.ip += 3;
			} else if (instruction == 6) {
				long value1 = getItemValue(split, mode1, amp.ip + 1);
				long value2 = getItemValue(split, mode2, amp.ip + 2);
				if (value1 == 0)
					amp.ip = (int)value2;
				else
					amp.ip += 3;
			} else if (instruction == 7) {
				long value1 = getItemValue(split, mode1, amp.ip + 1);
				long value2 = getItemValue(split, mode2, amp.ip + 2);
				int address = (int) getItemValue(split, 1, amp.ip + 3);
				if (value1 < value2)
					split[address] = "1";
				else
					split[address] = "0";
				amp.ip += 4;
			} else if (instruction == 8) {
				long value1 = getItemValue(split, mode1, amp.ip + 1);
				long value2 = getItemValue(split, mode2, amp.ip + 2);
				int address = (int) getItemValue(split, 1, amp.ip + 3);
				if (value1 == value2)
					split[address] = "1";
				else
					split[address] = "0";
				amp.ip += 4;
			} else {
				System.out.println("need to cater for: " + split[amp.ip]);
			}

		}

		amp.isDone = true;
		return;
	}
	
	private long getValueForSplit(List<String[]> splits, int splitIndex, long input1, long input2) {
		String[] split = splits.get(splitIndex);
		int index = 0;
		long input = input1;
		List<Long> outputs = new ArrayList<>();
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
				long value1 = getItemValue(split, mode1, index + 1);
				long value2 = getItemValue(split, mode2, index + 2);
				int address = (int) getItemValue(split, 1, index + 3);
				split[address] = Long.toString(value1 + value2);
				index += 4;
			} else if (instruction == 2) {
				long value1 = getItemValue(split, mode1, index + 1);
				long value2 = getItemValue(split, mode2, index + 2);
				int address = (int) getItemValue(split, 1, index + 3);
				split[address] = Long.toString(value1 * value2);
				index += 4;
			} else if (instruction == 3) {
				int address = (int) getItemValue(split, 1, index + 1);
				split[address] = Long.toString(input);
				index += 2;
				input = input2;
			} else if (instruction == 4) {
				long value = getItemValue(split, mode1, index + 1);
				outputs.add(value);
				index += 2;
			} else if (instruction == 5) {
				long value1 = getItemValue(split, mode1, index + 1);
				long value2 = getItemValue(split, mode2, index + 2);
				if (value1 != 0)
					index = (int)value2;
				else
					index += 3;
			} else if (instruction == 6) {
				long value1 = getItemValue(split, mode1, index + 1);
				long value2 = getItemValue(split, mode2, index + 2);
				if (value1 == 0)
					index = (int)value2;
				else
					index += 3;
			} else if (instruction == 7) {
				long value1 = getItemValue(split, mode1, index + 1);
				long value2 = getItemValue(split, mode2, index + 2);
				int address = (int) getItemValue(split, 1, index + 3);
				if (value1 < value2)
					split[address] = "1";
				else
					split[address] = "0";
				index += 4;
			} else if (instruction == 8) {
				long value1 = getItemValue(split, mode1, index + 1);
				long value2 = getItemValue(split, mode2, index + 2);
				int address = (int) getItemValue(split, 1, index + 3);
				if (value1 == value2)
					split[address] = "1";
				else
					split[address] = "0";
				index += 4;
			} else {
				System.out.println("need to cater for: " + split[index]);
			}

		}

		return outputs.get(outputs.size() - 1);
	}

	private long getItemValue(String[] items, int mode, int index) {
		long valueAtIndex = Long.valueOf(items[index]);
		if (mode == 0) {
			return Long.valueOf(items[(int)valueAtIndex]);
		} else {
			return valueAtIndex;
		}
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day7().getValueForPhase("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0",
				new Integer[] { 4, 3, 2, 1, 0 }));
		System.out.println("TestA: " + new Day7().getValueForPhase(
				"3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0",
				new Integer[] { 0, 1, 2, 3, 4 }));
		System.out.println("TestA: " + new Day7().getValueForPhase(
				"3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0",
				new Integer[] { 1, 0, 4, 3, 2 }));
		//		System.out.println("TestA: " + new Day7().problemA(TEST));
		System.out.println("A: " + new Day7().problemA(LINES));
		
		System.out.println("TestB: " + new Day7().getValueForPhaseB(
				"3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5",
				new Integer[] { 9, 8, 7, 6, 5 }));
		System.out.println("TestB: " + new Day7().getValueForPhaseB(
				"3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10",
				new Integer[] { 9, 7, 8, 5, 6 }));

		System.out.println("B: " + new Day7().problemB(LINES));
	}

}
