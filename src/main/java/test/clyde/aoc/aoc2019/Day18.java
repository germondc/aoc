package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day18 {
	private static List<String> INPUT = Utils.readFile("2019/Day18.txt");
	private List<String> input;

	public Day18(List<String> input) {
		this.input = input;
	}

	public long problemA() {
		TheMap map = new TheMap();
		int min = getShortestDistance(map, map.start, new HashSet<>(), new HashMap<>());
		return min;
	}

	public long problemB() {
		TheMap map = new TheMap();
		map.tweak();
		int min = getShortestDistance(map, map.start, new HashSet<>(), new HashMap<>());
		return min;
	}

	private class State {
		Set<Point2d> start;
		Set<Character> keys;

		public State(Set<Point2d> start, Set<Character> keys) {
			super();
			this.start = start;
			this.keys = keys;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((keys == null) ? 0 : keys.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
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
			State other = (State) obj;
			if (keys == null) {
				if (other.keys != null)
					return false;
			} else if (!keys.equals(other.keys))
				return false;
			if (start == null) {
				if (other.start != null)
					return false;
			} else if (!start.equals(other.start))
				return false;
			return true;
		}
	}

	private int getShortestDistance(TheMap map, Set<Point2d> starts, Set<Character> keys, Map<State, Integer> been) {
		List<Integer> distances = new ArrayList<>();
		State state = new State(starts, keys);
		if (been.containsKey(state)) {
			return been.get(state);
		}
		for (Point2d start : starts) {
			for (Map.Entry<Character, Integer> keyEntry : getPossibleKeys(map, start, keys).entrySet()) {
				char key = keyEntry.getKey();
				int distance = keyEntry.getValue();
				Set<Character> newKeys = new HashSet<>(keys);
				newKeys.add(key);
				Point2d nextStart = map.keysPoints.get(key);
				Set<Point2d> newStarts = new HashSet<>(starts);
				newStarts.add(nextStart);
				newStarts.remove(start);
				int totalDistance = distance + getShortestDistance(map, newStarts, newKeys, been);
				distances.add(totalDistance);
			}
		}
		int shortest = distances.stream().mapToInt(i -> i).min().orElseGet(() -> 0);
		been.put(state, shortest);
		return shortest;
	}

	private class TheMap {
		Set<Point2d> start = new HashSet<>();
		Map<Point2d, Character> keys = new HashMap<>();
		Map<Character, Point2d> keysPoints = new HashMap<>();
		Map<Point2d, Character> doors = new HashMap<>();
		Set<Point2d> path = new HashSet<>();

		public TheMap() {
			getMap();
		}

		private void getMap() {
			int y = 0;
			for (String line : input) {
				int x = 0;
				for (char c : line.toCharArray()) {
					Point2d point = new Point2d(x, y);
					if (c == '@')
						start.add(point);
					if (c >= 'a' && c <= 'z') {
						keys.put(point, c);
						keysPoints.put(c, point);
					} else if (c >= 'A' && c <= 'Z')
						doors.put(point, Character.toLowerCase(c));
					if (c != '#')
						path.add(point);
					x++;
				}
				y++;
			}
		}

		public void tweak() {
			Point2d s = start.iterator().next();
			start.clear();
			start.add(new Point2d(s.x - 1, s.y - 1));
			start.add(new Point2d(s.x + 1, s.y - 1));
			start.add(new Point2d(s.x - 1, s.y + 1));
			start.add(new Point2d(s.x + 1, s.y + 1));
			path.remove(s);
			path.removeAll(s.getNextDoor());
		}
	}

	private Map<Character, Integer> getPossibleKeys(TheMap map, Point2d start, Set<Character> keys) {
		Map<Character, Integer> result = new HashMap<>();
		Map<Point2d, Integer> distances = new HashMap<>();
		Queue<Point2d> moves = new ArrayDeque<>();
		moves.add(start);
		Set<Point2d> been = new HashSet<>();
		distances.put(start, 0);
		while (moves.size() > 0) {
			Point2d current = moves.remove();
			if (!been.contains(current)) {
				been.add(current);
				for (Point2d next : current.getNextDoor()) {
					if (distances.containsKey(next))
						continue;
					distances.put(next, distances.get(current) + 1);
					if (map.doors.containsKey(next) && !keys.contains(map.doors.get(next))) {
						continue;
					} else if (map.keys.containsKey(next)) {
						char key = map.keys.get(next);
						if (!keys.contains(key)) {
							result.put(map.keys.get(next), distances.get(next));
							continue;
						}
						moves.add(next);
					} else if (map.path.contains(next)) {
						moves.add(next);
					}
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day18(INPUT).problemA());
		System.out.println("B: " + new Day18(INPUT).problemB());
	}
}
