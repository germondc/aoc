package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Directions.Direction;
import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day11 {
	private static List<String> INPUT = Utils.readFile("2019/Day11.txt");

	private int getValueForInput(String line, Long input) {

		Map<Long, Long> program = getProgram(line);
		Map<Point2d, Boolean> painted = new HashMap<>();
		int countPainted = 0;
		long currentInput = input;
		Point2d currentPoint = new Point2d(0, 0);
		Direction currentDirection = Direction.up;

		//String[] split = splits.get(splitIndex);
		long index = 0;
		long relIndex = 0;
		List<Long> outputs = new ArrayList<>();
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
				program.put(address, currentInput);
				index += 2;
			} else if (instruction == 4) {
				long value = getItemValue(program, mode1, index + 1, relIndex, false);
				outputs.add(value);
				if (outputs.size()==2) {
					if (!painted.containsKey(currentPoint))
						countPainted++;
					boolean isWhite = outputs.get(0)==1;
					painted.put(currentPoint, isWhite);
					if (outputs.get(1)==0)
						currentDirection = currentDirection.turnLeft();
					else
						currentDirection = currentDirection.turnRight();
					currentPoint = currentPoint.move(currentDirection);
					if (painted.containsKey(currentPoint) && painted.get(currentPoint)) {
						currentInput = 1L;
					} else {
						currentInput = 0L;
					}
					outputs.clear();
				}
				index += 2;
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

		return countPainted;
	}
	
	private Map<Point2d, Boolean> getValueForInputB(String line, Long input) {
		Map<Long, Long> program = getProgram(line);
		Map<Point2d, Boolean> painted = new HashMap<>();
		int countPainted = 0;
		long currentInput = input;
		Point2d currentPoint = new Point2d(0, 0);
		Direction currentDirection = Direction.up;

		//String[] split = splits.get(splitIndex);
		long index = 0;
		long relIndex = 0;
		List<Long> outputs = new ArrayList<>();
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
				program.put(address, currentInput);
				index += 2;
			} else if (instruction == 4) {
				long value = getItemValue(program, mode1, index + 1, relIndex, false);
				outputs.add(value);
				if (outputs.size()==2) {
					if (!painted.containsKey(currentPoint))
						countPainted++;
					boolean isWhite = outputs.get(0)==1;
					painted.put(currentPoint, isWhite);
					if (outputs.get(1)==0)
						currentDirection = currentDirection.turnLeft();
					else
						currentDirection = currentDirection.turnRight();
					currentPoint = currentPoint.move(currentDirection);
					if (painted.containsKey(currentPoint) && painted.get(currentPoint)) {
						currentInput = 1L;
					} else {
						currentInput = 0L;
					}
					outputs.clear();
				}
				index += 2;
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

		return painted;
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

	private long problemA(List<String> input) {
		return getValueForInput(input.get(0), 0L);
	}

	private long problemB(List<String> input) {
		Map<Point2d, Boolean> painted = getValueForInputB(input.get(0), 1L);
		long minX = painted.keySet().stream().mapToLong(p -> p.x).min().getAsLong();
		long maxX = painted.keySet().stream().mapToLong(p -> p.x).max().getAsLong();
		long minY = painted.keySet().stream().mapToLong(p -> p.y).min().getAsLong();
		long maxY = painted.keySet().stream().mapToLong(p -> p.y).max().getAsLong();
		
		for (long y=minY; y<=maxY; y++) {
			for (long x=minX; x<=maxX; x++) {
				Point2d p = new Point2d(x,y);
				if (painted.containsKey(p) && painted.get(p)) {
					System.out.print('#');
				} else {
					System.out.print(' ');
				}
			}
			System.out.println();
		}
		
		return 0;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day11().problemA(INPUT));
		System.out.println("B: " + new Day11().problemB(INPUT));
	}

}
