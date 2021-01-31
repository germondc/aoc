package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day3 {
	private static List<String> LINES = Utils.readFile("2019/Day3.txt");
	private static List<String> TEST = Utils.readFile("2019/Day3a.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day3b.txt");
	private static List<String> TESTC = Utils.readFile("2019/Day3c.txt");

	private long problemA(List<String> lines) {
		Map<Integer, Set<Point2d>> wires = new HashMap<>();
		int index=0;
		for (String line : lines) {
			String[] split = line.split(",");
			Point2d current = new Point2d(0, 0);
			wires.putIfAbsent(index, new HashSet<>());
			for (String pathItem : split) {
				String dir = pathItem.substring(0, 1);
				int amount = Integer.valueOf(pathItem.substring(1));
				for (int i=0; i<amount; i ++) {
					current = current.move(dir);
					wires.get(index).add(current);
				}
			}
			index++;
		}
		
		List<Point2d> collisions = new ArrayList<>();
		for (int i=0; i<wires.size(); i++) {
			for (int j=i+1; j<wires.size(); j++) {
				List<Point2d> copy = new ArrayList<>(wires.get(i));
				copy.retainAll(wires.get(j));
				collisions.addAll(copy);
			}
		}
		
		long best = collisions.stream().mapToLong(p -> p.getManhattanDistance(Point2d.ORIGIN)).min().getAsLong();
		return best;
	}
	

	private long problemB(List<String> lines) {
		Map<Integer, Map<Point2d, Integer>> wires = new HashMap<>();
		int index=0;
		for (String line : lines) {
			String[] split = line.split(",");
			Point2d current = new Point2d(0, 0);
			wires.putIfAbsent(index, new HashMap<>());
			int steps = 1;
			for (String pathItem : split) {
				String dir = pathItem.substring(0, 1);
				int amount = Integer.valueOf(pathItem.substring(1));
				for (int i=0; i<amount; i ++) {
					current = current.move(dir);
					if (!wires.get(index).containsKey(current))
						wires.get(index).put(current, steps);
					steps++;
				}
			}
			index++;
		}
		
		Map<Point2d, Integer> collisions = new HashMap<>();
		for (int i=0; i<wires.size(); i++) {
			for (int j=i+1; j<wires.size(); j++) {
				for (Map.Entry<Point2d, Integer> entry : wires.get(i).entrySet()) {
					if (wires.get(j).containsKey(entry.getKey()) && !collisions.containsKey(entry.getKey())) {
						collisions.put(entry.getKey(), entry.getValue() + wires.get(j).get(entry.getKey()));
					}
				}
			}
		}
		
		long best = collisions.values().stream().mapToInt(i -> i).min().getAsInt();
		return best;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day3().problemA(TEST));
		System.out.println("TestA: " + new Day3().problemA(TESTB));
		System.out.println("TestA: " + new Day3().problemA(TESTC));
		System.out.println("A: " + new Day3().problemA(LINES));
		System.out.println("TestB: " + new Day3().problemB(TEST));
		System.out.println("TestB: " + new Day3().problemB(TESTB));
		System.out.println("TestB: " + new Day3().problemB(TESTC));
		System.out.println("B: " + new Day3().problemB(LINES));
	}

}
