package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import test.clyde.aoc.utils.Permutations;
import test.clyde.aoc.utils.Utils;

public class Day8 {
	private static String INPUT = Utils.readFile("2019/Day8.txt").get(0);

	private List<List<List<Integer>>> getLayers(String line, int width, int height) {
		List<List<List<Integer>>> layers = new ArrayList<>();

		int index = 0;
		List<Integer> currentRow = null;
		List<List<Integer>> currentLayer = null;
		for (char c : line.toCharArray()) {
			if (index % (height * width) == 0) {
				currentLayer = new ArrayList<>();
				layers.add(currentLayer);
			}
			if (index % width == 0) {
				currentRow = new ArrayList<>();
				currentLayer.add(currentRow);
			}
			currentRow.add(c - '0');
			index++;
		}
		return layers;
	}

	private long problemA(String line, int width, int height) {
		List<List<List<Integer>>> layers = getLayers(line, width, height);
		int index = 0;
		long least = Long.MAX_VALUE;
		int bestIndex = 0;
		for (List<List<Integer>> layer : layers) {
			long count = layer.stream().mapToLong(l -> l.stream().filter(c -> c == 0).count()).sum();
			if (count < least) {
				bestIndex = index;
				least = count;
			}
			index++;
		}

		long count1 = layers.get(bestIndex).stream().mapToLong(l -> l.stream().filter(c -> c == 1).count()).sum();
		long count2 = layers.get(bestIndex).stream().mapToLong(l -> l.stream().filter(c -> c == 2).count()).sum();
		return count1 * count2;
	}

	private long problemB(String line, int width, int height) {
		List<List<List<Integer>>> layers = getLayers(line, width, height);
		
		List<List<Integer>> result = new ArrayList<>();
		for (int h=0; h<height; h++) {
			List<Integer> row  = new ArrayList<>();
			result.add(row);
			for (int w=0; w<width; w++) {
				row.add(2);
			}
		}
		
		for (List<List<Integer>> layer : layers) {
			for (int h=0; h<height; h++) {
				for (int w=0; w<width; w++) {
					if (result.get(h).get(w)==2) {
						result.get(h).set(w, layer.get(h).get(w));
					}
				}
			}
		}
		for (int h=0; h<height; h++) {
			for (int w=0; w<width; w++) {
				if (result.get(h).get(w) == 1)
					System.out.print('#');
				else
					System.out.print(' ');
			}
			System.out.println();
		}
		
		return 0;
	}

	public static void main(String[] args) {

		System.out.println("A: " + new Day8().problemA(INPUT, 25, 6));
		System.out.println("B: " + new Day8().problemB(INPUT, 25, 6));
	}

}
