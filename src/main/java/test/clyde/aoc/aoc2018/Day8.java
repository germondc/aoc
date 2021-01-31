package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day8 {
	private static List<String> LINES = Utils.readFile("2018/Day8.txt");
	private static List<String> TEST = Utils.readFile("2018/Day8a.txt");

	private class Node {
		int childrenNo;
		int metaNo;
		List<Node> children = new ArrayList<>();
		List<Integer> meta = new ArrayList<>();
		int endIndex;
	}

	private long problemA(List<String> lines) {
		String[] split = lines.get(0).split(" ");
		List<Integer> numbers = Arrays.stream(split).map(s -> Integer.valueOf(s)).collect(Collectors.toList());
		Node startingNode = getNode(numbers, 0);
		return sumMeta(startingNode);
	}

	private int sumMeta(Node node) {
		int sum = node.meta.stream().mapToInt(i -> i).sum();
		sum += node.children.stream().mapToInt(n -> sumMeta(n)).sum();
		return sum;
	}

	private long problemB(List<String> lines) {
		String[] split = lines.get(0).split(" ");
		List<Integer> numbers = Arrays.stream(split).map(s -> Integer.valueOf(s)).collect(Collectors.toList());
		Node startingNode = getNode(numbers, 0);
		return getNodeValue(startingNode);
	}

	private int getNodeValue(Node node) {
		if (node.childrenNo == 0) {
			return node.meta.stream().mapToInt(i -> i).sum();
		}
		int value = 0;
		for (int meta : node.meta) {
			int index = meta - 1;
			if (index < 0 || index >= node.children.size())
				continue;
			value += getNodeValue(node.children.get(index));
		}
		return value;
	}

	private Node getNode(List<Integer> numbers, int startIndex) {
		Node result = new Node();
		int index = startIndex;
		result.childrenNo = numbers.get(index++);
		result.metaNo = numbers.get(index++);
		for (int i = 0; i < result.childrenNo; i++) {
			Node child = getNode(numbers, index);
			index = child.endIndex;
			result.children.add(child);
		}
		for (int i = 0; i < result.metaNo; i++) {
			result.meta.add(numbers.get(index++));
		}
		result.endIndex = index;
		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day8().problemA(TEST));
		System.out.println("A: " + new Day8().problemA(LINES));
		System.out.println("TestB: " + new Day8().problemB(TEST));
		System.out.println("B: " + new Day8().problemB(LINES));
	}

}
