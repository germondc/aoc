package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {
	private void problemA(String input) {
		List<String> items = new ArrayList<>();
		for (int i = 0; i < 128; i++) {
			String hash = getHash(input + "-" + i);
			String bin = binaryFromHex(hash);
			items.add(bin);
		}
		
		System.out.println("A: " + items.stream().map(s -> s.chars().filter(c -> c == '1').count()).mapToLong(l -> l).sum());

		int[][] indexes = new int[128][128];
		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < 128; j++) {
				indexes[i][j] = items.get(i).charAt(j) == '0' ? 0 : -1;
			}
		}

		int groupCount = 1;
		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < 128; j++) {
				if (indexes[i][j] == -1) {
					traverse(indexes, groupCount, i, j);
					groupCount++;
				}
			}
		}

		System.out.println("B: " + (groupCount-1));
	}

	private void traverse(int[][] indexes, int count, int row, int col) {
		indexes[row][col] = count;
		if (row > 0 && indexes[row - 1][col] == -1) {
			traverse(indexes, count, row - 1, col);
		}
		if (row < 127 && indexes[row + 1][col] == -1) {
			traverse(indexes, count, row + 1, col);
		}
		if (col > 0 && indexes[row][col - 1] == -1) {
			traverse(indexes, count, row, col - 1);
		}
		if (col < 127 && indexes[row][col + 1] == -1) {
			traverse(indexes, count, row, col + 1);
		}
	}

	private String binaryFromHex(String hex) {
		String result = "";
		for (int i = 0; i < 4; i++) {
			long value = Long.parseLong(hex.substring(i * 8, (i + 1) * 8), 16);
			String piece = Long.toBinaryString(value);
			while (piece.length() < 32) {
				piece = "0" + piece;
			}
			result += piece;
		}

		return result;
	}

	private String getHash(String line) {
		char[] ca = line.toCharArray();
		List<Integer> inputs = IntStream.range(0, ca.length).map(i -> (int) ca[i]).boxed().collect(Collectors.toList());
		inputs.add(17);
		inputs.add(31);
		inputs.add(73);
		inputs.add(47);
		inputs.add(23);
		int elementsSize = 256;

		List<Integer> elements = IntStream.range(0, elementsSize).boxed()
				.collect(Collectors.toCollection(LinkedList::new));
		int pos = 0;
		int skipSize = 0;
		for (int round = 0; round < 64; round++) {
			for (int input : inputs) {
				elements = reverseSection(elements, pos, input);
				pos += input + skipSize;
				skipSize++;
			}
		}

		List<Integer> denseHash = getDenseHash(elements);

		return getHexString(denseHash);
	}

	private List<Integer> getDenseHash(List<Integer> sparseHash) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			int value = sparseHash.get(16 * i);
			for (int j = 1; j < 16; j++) {
				value ^= sparseHash.get(16 * i + j);
			}
			result.add(value);
		}
		return result;
	}

	private List<Integer> reverseSection(List<Integer> list, int start, int size) {
		List<Integer> result = new ArrayList<>(list);

		for (int i = 0; i < size; i++) {
			int index1 = start + i;
			while (index1 >= list.size())
				index1 -= list.size();
			int index2 = start + size - i - 1;
			while (index2 >= list.size())
				index2 -= list.size();
			result.set(index1, list.get(index2));
		}

		return result;
	}

	private String getHexString(List<Integer> elements) {
		String result = "";
		for (int element : elements) {
			String hexElement = Integer.toHexString(element);
			if (hexElement.length() == 1)
				hexElement = "0" + hexElement;
			result += hexElement;
		}
		return result;
	}

	public static void main(String[] args) {
		new Day14().problemA("flqrgnkx");
		new Day14().problemA("stpzcrnm");
	}

}
