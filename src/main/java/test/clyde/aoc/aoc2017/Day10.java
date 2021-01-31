package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import test.clyde.aoc.utils.Utils;

public class Day10 {

	private static List<String> LINES = Utils.readFile("2017/Day10.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day10a.txt");

	private long problemA(List<String> lines, int elementsSize) {
		Pattern pat = Pattern.compile("(\\d+)");
		Matcher m = pat.matcher(lines.get(0));
		List<Integer> inputs = new ArrayList<>();
		while (m.find()) {
			inputs.add(Integer.valueOf(m.group(1)));
		}
		
		List<Integer> elements = IntStream.range(0, elementsSize).boxed().collect(Collectors.toList());
		int pos = 0;
		int skipSize = 0;
		for (int input : inputs) {
			List<Integer> current = rotateList(elements, -pos);
			current = reverse(current, input);
			elements = rotateList(current, pos);
			pos += input + skipSize;
			skipSize++;
		}
		
		return elements.get(0) * elements.get(1);
	}
	
	private List<Integer> reverse(List<Integer> list, int size) {
		List<Integer> result = new ArrayList<>(list.size());
		
		for (int i=0; i<size; i++) {
			result.add(i, list.get(size-i-1));
		}
		for (int i=size; i<list.size(); i++) {
			result.add(i, list.get(i));
		}
		
		return result;
	}
	
	private List<Integer> rotateList(List<Integer> list, int rotateAmount) {
		if (rotateAmount == 0)
			return list;
		
		List<Integer> result = new ArrayList<>(list.size());
		
		for (int i=0; i<list.size(); i++) {
			int index = i-rotateAmount;
			while (index >= list.size())
				index -= list.size();
			while (index < 0)
				index += list.size();
			result.add(i, list.get(index));
		}
		
		return result;
	}
	
	private String solveB(String line) {
		char[] ca = line.toCharArray();
		List<Integer> inputs = IntStream.range(0, ca.length).map(i -> (int)ca[i]).boxed().collect(Collectors.toList());
		inputs.add(17);
		inputs.add(31);
		inputs.add(73);
		inputs.add(47);
		inputs.add(23);
		int elementsSize = 256;
		
		List<Integer> elements = IntStream.range(0, elementsSize).boxed().collect(Collectors.toList());
		int pos = 0;
		int skipSize = 0;
		for (int round=0; round<64; round++) {
			for (int input : inputs) {
				List<Integer> current = rotateList(elements, -pos);
				current = reverse(current, input);
				elements = rotateList(current, pos);
				pos += input + skipSize;
				skipSize++;
			}
		}
		
		List<Integer> denseHash = getDenseHash(elements);
		
		return getHexString(denseHash);
	}
	
	private List<Integer> getDenseHash(List<Integer> sparseHash) {
		List<Integer> result = new ArrayList<>();
		for (int i=0; i<16; i++) {
			int value = sparseHash.get(16*i);
			for (int j=1; j<16; j++) {
				value ^= sparseHash.get(16*i+j);
			}
			result.add(value);
		}
		return result;
	}
	
	private String getHexString(List<Integer> elements) {
		String result = "";
		for (int element:elements) {
			result += Integer.toHexString(element);
		}
		return result;
	}

	private String problemB(List<String> lines) {
		return solveB(lines.get(0));
	}

	public static void main(String[] args) {
		System.out.println("Test: " + new Day10().problemA(TEST_LINES, 5));
		System.out.println("A: " + new Day10().problemA(LINES, 256));
		System.out.println("TestB: " + new Day10().solveB(""));
		System.out.println("TestB: " + new Day10().solveB("AoC 2017"));
		System.out.println("TestB: " + new Day10().solveB("1,2,3"));
		System.out.println("TestB: " + new Day10().solveB("1,2,4"));
		System.out.println("B: " + new Day10().problemB(LINES));
	}

}
