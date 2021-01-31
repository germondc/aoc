package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class IntCode23 {
	private long index = 0;
	private long relIndex = 0;
	private Map<Long, Long> program;
	private Queue<Long> inputQueue;
	private Queue<Long> outputQueue;
	private int emptyReceivingCount = 0;

	private IntCode23() {

	}

	public IntCode23 copy() {
		IntCode23 copy = new IntCode23();
		copy.program = new HashMap<>(this.program);
		copy.index = this.index;
		copy.relIndex = this.relIndex;
		return copy;
	}

	public IntCode23(String line) {
		this.program = getProgram(line);
		this.inputQueue = new ArrayDeque<>();
		this.outputQueue = new ArrayDeque<>();
	}

	public void addToInput(long input) {
		inputQueue.add(input);
	}
	
	public boolean isOnlyReceiving() {
		return emptyReceivingCount > 2 && inputQueue.size() == 0;
	}

	public List<Long> getCurrentOutput(int size) {
		if (outputQueue.size() == size) {
			List<Long> result = new ArrayList<>();
			while (outputQueue.size() > 0)
				result.add(outputQueue.remove());
			return result;
		}
		return null;
	}

	public void process() {
		if (isOnlyReceiving())
			return;
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
			if (inputQueue.size() == 0) {
				emptyReceivingCount++;
				inputQueue.add(-1L);
			} else {
				emptyReceivingCount = 0;
			}
			long inputValue = inputQueue.remove();
			program.put(address, inputValue);
			index += 2;
		} else if (instruction == 4) {
			long value = getItemValue(program, mode1, index + 1, relIndex, false);
			index += 2;
			emptyReceivingCount = 0;
			outputQueue.add(value);
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
