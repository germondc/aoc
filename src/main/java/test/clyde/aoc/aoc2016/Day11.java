package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day11 {

	//private static List<String> LINES = Utils.readFile("2016/Day11a.txt");
	private static List<String> LINES = Utils.readFile("2016/Day11.txt");

	private String[] ELEMENTS;

	private enum Type {
		generator, microchip
	}

	private class Item {
		Type type;
		String element;

		public Item(Type type, String element) {
			this.type = type;
			this.element = element;
		}

		@Override
		public String toString() {
			return String.format("<:%s, %s>", type, element);
		}
	}

	private class Pair implements Comparable<Pair> {
		Integer one;
		Integer two;

		public Pair() {

		}

		public void add(int i) {
			if (one == null)
				one = i;
			else
				two = i;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + one;
			result = prime * result + two;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (one != other.one)
				return false;
			if (two != other.two)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", one, two);
		}

		@Override
		public int compareTo(Pair o) {
			if (o == null)
				return 1;
			if (this.one < o.one)
				return -1;
			else if (this.one > o.one)
				return 1;
			else if (this.two < o.two)
				return -1;
			else if (this.two > o.two)
				return 1;
			return 0;
		}
	}

	private class Move {
		int level;
		int count;
		List<List<Item>> floors;
		
		public Move(int level, int count, List<List<Item>> floors) {
			this.level = level;
			this.count = count;
			this.floors = floors;
		}
	}

	private class HashKey {
		int level;
		List<Pair> groups;

		public HashKey(int level, List<Pair> groups) {
			this.level = level;
			this.groups = groups;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + level;
			result = prime * result + ((groups == null) ? 0 : groups.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HashKey other = (HashKey) obj;
			if (level != other.level)
				return false;
			if (groups == null) {
				if (other.groups != null)
					return false;
			} else if (!groups.equals(other.groups))
				return false;
			return true;
		}
	}

	private Set<HashKey> keys = new HashSet<>();

	private int problemA() {
		List<List<Item>> floors = getFloors();
		ELEMENTS = floors.stream().flatMap(l -> l.stream()).map(i -> i.element).distinct().toArray(String[]::new);
		int count = (int) floors.stream().flatMap(l -> l.stream()).count();
		int value = search(floors, count);
		return value;
	}

	private List<Pair> getPairs(List<List<Item>> floors) {
		Map<String, Pair> elementFloors = new HashMap<>();
		for (String element : ELEMENTS)
			elementFloors.put(element, new Pair());
		int i = 0;
		for (List<Item> floor : floors) {
			for (Item item : floor) {
				elementFloors.get(item.element).add(i);
			}
			i++;
		}
		return elementFloors.values().stream().sorted().collect(Collectors.toList());
	}

	private boolean isInvalid(List<List<Item>> floors) {
		for (List<Item> floor : floors) {
			List<String> gens = floor.stream().filter(i -> i.type == Type.generator).map(i -> i.element)
					.collect(Collectors.toList());
			if (gens.size() == 0)
				continue;
			List<String> chips = floor.stream().filter(i -> i.type == Type.microchip).map(i -> i.element)
					.collect(Collectors.toList());
			if (!gens.containsAll(chips))
				return true;
		}
		return false;
	}

	private int[] DELTAS = new int[] { 1, -1 };

	private List<Move> getNextMoves(int currentFloor, int count, List<List<Item>> floors) {
		List<Move> moves = new ArrayList<>();
		List<Item> items = floors.get(currentFloor);
		List<List<Item>> combos = Utils.getCombinations(items, 2);
		combos.addAll(Utils.getCombinations(items, 1));

		for (int delta : DELTAS) {
			int newFloor = currentFloor + delta;
			if (newFloor < 0 || newFloor > 3)
				continue;

//			if (delta == -1)
//				Collections.sort(combos, (o1, o2) -> o2.size() - o1.size());

			for (List<Item> combo : combos) {
				List<List<Item>> nextFloors = copyFloors(floors);
				nextFloors.get(currentFloor).removeAll(combo);
				nextFloors.get(newFloor).addAll(combo);
				if (isInvalid(nextFloors))
					continue;

				HashKey key = new HashKey(newFloor, getPairs(nextFloors));
				if (!keys.contains(key)) {
					Move move = new Move(newFloor, count + 1, nextFloors);
					moves.add(move);
					keys.add(key);
				}
			}
		}

		return moves;
	}

	private int search(List<List<Item>> floors, int goal) {
		Queue<Move> nextMovesQueue = new ArrayDeque<>();
		nextMovesQueue.addAll(getNextMoves(0, 0, floors));
		int nodeCount = 0;

		HashKey key = new HashKey(0, getPairs(floors));
		keys.add(key);

		int moveCount = 0;
		while (nextMovesQueue.size() > 0) {
			Move nextMove = nextMovesQueue.remove();
			nodeCount += 1;
			int floor = nextMove.level;
			int dist = nextMove.count;
			List<List<Item>> items = nextMove.floors;
			if (dist > moveCount) {
				//System.out.println(dist + " moves, queue has " + nextMovesQueue.size());
				moveCount = dist;
			}

			if (items.get(3).size() == goal) {
				//System.out.println("considered " + nodeCount + " nodes");
				return dist;
			}
			nextMovesQueue.addAll(getNextMoves(floor, dist, items));
		}
		return -1;
	}

	private int problemB() {
		List<List<Item>> floors = getFloors();
		floors.get(0).add(new Item(Type.generator, "elerium"));
		floors.get(0).add(new Item(Type.microchip, "elerium"));
		floors.get(0).add(new Item(Type.generator, "dilithium"));
		floors.get(0).add(new Item(Type.microchip, "dilithium"));
		ELEMENTS = floors.stream().flatMap(l -> l.stream()).map(i -> i.element).distinct().toArray(String[]::new);
		int count = (int) floors.stream().flatMap(l -> l.stream()).count();
		int value = search(floors, count);
		return value;
	}

	private List<List<Item>> copyFloors(List<List<Item>> source) {
		List<List<Item>> result = new ArrayList<>();
		for (List<Item> item : source) {
			result.add(new ArrayList<>(item));
		}
		return result;
	}

	private List<List<Item>> getFloors() {
		List<List<Item>> floors = new ArrayList<>();
		Pattern gen_pat = Pattern.compile(" ([^ ]+) generator");
		Pattern chip_pat = Pattern.compile(" ([^ ]+)-compatible microchip");
		for (String line : LINES) {
			List<Item> floor = new ArrayList<>();
			Matcher m = gen_pat.matcher(line);
			while (m.find()) {
				floor.add(new Item(Type.generator, m.group(1)));
			}
			m = chip_pat.matcher(line);
			while (m.find()) {
				floor.add(new Item(Type.microchip, m.group(1)));
			}
			floors.add(floor);
		}
		return floors;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		System.out.println("A: " + new Day11().problemA());
		System.out.println("B: " + new Day11().problemB());
	}

}
