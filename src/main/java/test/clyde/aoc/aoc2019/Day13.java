package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day13 {
	private static List<String> INPUT = Utils.readFile("2019/Day13.txt");

	private long problemA(List<String> input) {
		Map<Long, Long> program = getProgram(input.get(0));
		List<Long> output = getValueForInput(program, 0L);
		long count = 0;
		for (int i = 0; i < output.size(); i += 3) {
			long tile = output.get(i + 2);
			if (tile == 2)
				count++;
		}
		return count;
	}

	private long problemB(List<String> input) {
		Map<Long, Long> program = getProgram(input.get(0));
		List<Long> scores = new ArrayList<>();
		program.put(0L, 2L);
		List<Long> output = getValueForInput(program, 0L);
		Map<Point2d, Long> tiles = getTiles(output, scores);
		drawTiles(tiles);

		while (true) {
			output = getValueForInput(program, -1L);
			tiles = getTiles(output, scores);
			long blockCount = tiles.values().stream().filter(l -> l==2).count();
			if (blockCount==0)
				return scores.get(scores.size()-1);
		}
	}
	
	private void drawTiles(Map<Point2d, Long> tiles) {
		long minX = tiles.keySet().stream().mapToLong(p -> p.x).min().getAsLong();
		long maxX = tiles.keySet().stream().mapToLong(p -> p.x).max().getAsLong();
		long minY = tiles.keySet().stream().mapToLong(p -> p.y).min().getAsLong();
		long maxY = tiles.keySet().stream().mapToLong(p -> p.y).max().getAsLong();
		
		for (long y=minY; y<=maxY; y++) {
			for (long x=minX; x<=maxX; x++) {
				Point2d p = new Point2d(x, y);
				if (!tiles.containsKey(p))
					System.out.print(' ');
				long l = tiles.get(p);
				if (l==0) {
					System.out.print(' ');
				} else if (l==1) {
					System.out.print('#');
				} else if (l==2) {
					System.out.print('.');
				} else if (l==3) {
					System.out.print('-');
				} else if (l==4) {
					System.out.print('o');
				}
			}
			System.out.println();
		}
	}

	private Map<Point2d, Long> getTiles(List<Long> output, List<Long> score) {
		Map<Point2d, Long> tiles = new HashMap<>();
		for (int i = 0; i < output.size(); i += 3) {
			long x = output.get(i + 0);
			long y = output.get(i + 1);
			long tile = output.get(i + 2);
			if (x==-1) {
				score.add(tile);
			} else {
				Point2d p = new Point2d(x, y);			
				tiles.put(p, tile);
			}
		}
		return tiles;
	}

	private List<Long> getValueForInput(Map<Long, Long> program, Long input) {

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

		return outputs;
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

	public static void main(String[] args) {
		//		System.out.println("TestA: " + new Day13().problemA(TESTA));
		System.out.println("A: " + new Day13().problemA(INPUT));
		//		System.out.println("TestB: " + new Day13().problemB(TESTA));
		System.out.println("B: " + new Day13().problemB(INPUT));
	}

}
