package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day9 {
	private static List<String> INPUT = Utils.readFile("2019/Day9.txt");

	private List<Long> problemA(List<String> input) {
		return getValueForInput(input.get(0), 1L);
	}

	private List<Long> problemB(List<String> input) {
		return getValueForInput(input.get(0), 2L);
	}
	
	private List<Long> getValueForInput(String line, Long input) {
		
		Map<Long, Long> program = getProgram(line);
		
		//String[] split = splits.get(splitIndex);
		long index = 0;
		long relIndex = 0;
		List<Long> outputs = new ArrayList<>();
		while (program.get(index) != 99L) {
			long opcodeValue = program.get(index);
			int instruction = (int)opcodeValue % 10;
			int mode1 = ((int)opcodeValue / 100) % 10;
			int mode2 = ((int)opcodeValue / 1000) % 10;
			int mode3 = ((int)opcodeValue / 10000) % 10;
			if (instruction == 1) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				long address = getItemValue(program, mode3, index + 3, relIndex, true);
				program.put(address, value1 + value2);
				index += 4;
			} else if (instruction == 2) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				long address = getItemValue(program, mode3, index + 3, relIndex, true);
				program.put(address, value1 * value2);
				index += 4;
			} else if (instruction == 3) {
				long address = getItemValue(program, mode1, index + 1, relIndex, true);
				program.put(address, input);
				index += 2;
			} else if (instruction == 4) {
				long value = getItemValue(program, mode1, index + 1, relIndex, false);
				outputs.add(value);
				index += 2;
			} else if (instruction == 5) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 != 0)
					index = (int)value2;
				else
					index += 3;
			} else if (instruction == 6) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 == 0)
					index = (int)value2;
				else
					index += 3;
			} else if (instruction == 7) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				long address = getItemValue(program, mode3, index + 3, relIndex, true);
				if (value1 < value2)
					program.put(address, 1L);
				else
					program.put(address, 0L);
				index += 4;
			} else if (instruction == 8) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				long address = getItemValue(program, mode3, index + 3, relIndex, true);
				if (value1 == value2)
					program.put(address, 1L);
				else
					program.put(address, 0L);
				index += 4;
			} else if (instruction == 9) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				relIndex += value1;
				index += 2;
			} else {
				System.out.println("need to cater for: " + program.get(index));
			}

		}

		return outputs;
	}
	
	private Map<Long, Long> getProgram(String line) {
		Map<Long, Long> result = new HashMap<>();
		String[] split = line.split(",");
		long index = 0;
		for (String s : split) {
			result.put(index,  Long.valueOf(s));
			index++;
		}
		return result;
	}
	
	private long getItemValue(Map<Long, Long> program, int mode, long index, long relIndex, boolean isAddress) {
		program.putIfAbsent(index, 0L);
		long valueAtIndex = program.get(index);
		if (mode == 0) {
			program.putIfAbsent(valueAtIndex, 0L);
			if (isAddress)
				return valueAtIndex;
			return program.get(valueAtIndex);
		} else if (mode==1) {
			return valueAtIndex;
		} else if (mode==2) {
			long address = valueAtIndex + relIndex;
			program.putIfAbsent(address, 0L);
			if (isAddress)
				return address;
			return program.get(address);
		} else {
			throw new RuntimeException("no mode");
		}
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day9().getValueForInput("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99", null));
		System.out.println("TestA: " + new Day9().getValueForInput("1102,34915192,34915192,7,4,7,99,0", null));
		System.out.println("TestA: " + new Day9().getValueForInput("104,1125899906842624,99", null));
		System.out.println("A: " + new Day9().problemA(INPUT));
		System.out.println("B: " + new Day9().problemB(INPUT));
	}

}
