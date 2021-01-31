package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class IntCode {
	private long index = 0;
	private long relIndex = 0;
	private Map<Long, Long> program;
	private Queue<Long> theInput;

	private IntCode() {

	}

	public IntCode copy() {
		IntCode copy = new IntCode();
		copy.program = new HashMap<>(this.program);
		copy.index = this.index;
		copy.relIndex = this.relIndex;
		return copy;
	}

	public IntCode(String line) {
		this.program = getProgram(line);
		this.theInput = new ArrayDeque<>();
	}

	public long getValueForInput(long input) {
		Queue<Long> queue = new ArrayDeque<>();
		queue.add(input);
		return getValueForInput(queue);
	}

	public List<Long> getValuesForInputs(Queue<Long> input) {
		return getValuesForInputs(input, false);
	}
	
	public void addToInput(List<Long> items) {
		theInput.addAll(items);
	}
	
	public List<Long> getOutputUntilInputEmpty() {
		List<Long> result = new ArrayList<>();
		while (program.get(index) != 99L) {
			long opcodeValue = program.get(index);
			int instruction = (int) opcodeValue % 10;
			int mode1 = ((int) opcodeValue / 100) % 10;
			int mode2 = ((int) opcodeValue / 1000) % 10;
			int mode3 = ((int) opcodeValue / 10000) % 10;
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
				if (theInput.size() == 0)
					return result;
				long inputValue = theInput.remove();
				program.put(address, inputValue);
				index += 2;
			} else if (instruction == 4) {
				long value = getItemValue(program, mode1, index + 1, relIndex, false);
				index += 2;
				result.add(value);
			} else if (instruction == 5) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 != 0)
					index = (int) value2;
				else
					index += 3;
			} else if (instruction == 6) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 == 0)
					index = (int) value2;
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

		return result;
	}
	
	public List<Long> getValuesForInputs(Queue<Long> input, boolean minusOneMissingInput) {
		List<Long> result = new ArrayList<>();
		while (program.get(index) != 99L) {
			long opcodeValue = program.get(index);
			int instruction = (int) opcodeValue % 10;
			int mode1 = ((int) opcodeValue / 100) % 10;
			int mode2 = ((int) opcodeValue / 1000) % 10;
			int mode3 = ((int) opcodeValue / 10000) % 10;
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
				if (input.size() == 0 && minusOneMissingInput)
					input.add(-1L);
				if (input.size() == 0)
					throw new RuntimeException("no more inputs");
				long inputValue = input.remove();
				program.put(address, inputValue);
				index += 2;
			} else if (instruction == 4) {
				long value = getItemValue(program, mode1, index + 1, relIndex, false);
				index += 2;
				result.add(value);
			} else if (instruction == 5) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 != 0)
					index = (int) value2;
				else
					index += 3;
			} else if (instruction == 6) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 == 0)
					index = (int) value2;
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

		return result;
	}

	public long getValueForInput(Queue<Long> input) {
		while (program.get(index) != 99L) {
			long opcodeValue = program.get(index);
			int instruction = (int) opcodeValue % 10;
			int mode1 = ((int) opcodeValue / 100) % 10;
			int mode2 = ((int) opcodeValue / 1000) % 10;
			int mode3 = ((int) opcodeValue / 10000) % 10;
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
				if (input.size() == 0)
					throw new RuntimeException("no more inputs");
				long inputValue = input.remove();
				program.put(address, inputValue);
				index += 2;
			} else if (instruction == 4) {
				long value = getItemValue(program, mode1, index + 1, relIndex, false);
				index += 2;
				return value;
			} else if (instruction == 5) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 != 0)
					index = (int) value2;
				else
					index += 3;
			} else if (instruction == 6) {
				long value1 = getItemValue(program, mode1, index + 1, relIndex, false);
				long value2 = getItemValue(program, mode2, index + 2, relIndex, false);
				if (value1 == 0)
					index = (int) value2;
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

		return -1L;
	}

	private long getItemValue(Map<Long, Long> program, int mode, long index, long relIndex, boolean isAddress) {
		program.putIfAbsent(index, 0L);
		long valueAtIndex = program.get(index);
		if (mode == 0) {
			program.putIfAbsent(valueAtIndex, 0L);
			if (isAddress)
				return valueAtIndex;
			return program.get(valueAtIndex);
		} else if (mode == 1) {
			return valueAtIndex;
		} else if (mode == 2) {
			long address = valueAtIndex + relIndex;
			program.putIfAbsent(address, 0L);
			if (isAddress)
				return address;
			return program.get(address);
		} else {
			throw new RuntimeException("no mode");
		}
	}

	private Map<Long, Long> getProgram(String line) {
		Map<Long, Long> result = new HashMap<>();
		String[] split = line.split(",");
		long index = 0;
		for (String s : split) {
			result.put(index, Long.valueOf(s));
			index++;
		}
		return result;
	}

	public void updateProgram(long address, long value) {
		program.put(address, value);
	}
}
