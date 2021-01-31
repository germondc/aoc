package test.clyde.aoc.aoc2019;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.clyde.aoc.utils.Directions;
import test.clyde.aoc.utils.Utils;

public class Day24 {
	private static List<String> INPUT = Utils.readFile("2019/Day24.txt");
	private static List<String> TESTA = Utils.readFile("2019/Day24a.txt");

	private List<String> input;

	public Day24(List<String> input) {
		this.input = input;
	}

	private Boolean[][] getMap() {
		Boolean[][] map = new Boolean[5][5];
		int y = 0;
		for (String line : input) {
			int x = 0;
			for (char c : line.toCharArray()) {
				map[y][x] = c == '#';
				x++;
			}
			y++;
		}
		return map;
	}

	private boolean getMapPoint(Boolean[][] map, int x, int y) {
		if (x < 0 || y < 0 || x >= 5 || y >= 5)
			return false;
		else
			return map[y][x];
	}

	private Boolean[][] generateEmptyLayer() {
		Boolean[][] result = new Boolean[5][5];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				result[y][x] = false;
			}
		}
		return result;
	}

	private int getCount(Boolean[][] grid) {
		int count = 0;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (grid[y][x])
					count++;
			}
		}
		return count;
	}

	private int getMapPoint(Map<Integer, Boolean[][]> initial, int refX, int refY, int x, int y, int z) {
		Boolean[][] initialGrid = initial.get(z);
		if (x < 0 || y < 0 || x >= 5 || y >= 5) {
			if (!initial.containsKey(z - 1))
				return 0;
			Boolean[][] outerGrid = initial.get(z - 1);
			if (x < 0 && outerGrid[2][1])
				return 1;
			else if (x >= 5 && outerGrid[2][3])
				return 1;
			else if (y < 0 && outerGrid[1][2])
				return 1;
			else if (y >= 5 && outerGrid[3][2])
				return 1;
			else
				return 0;
		} else if (x == 2 && y == 2) {
			if (!initial.containsKey(z + 1))
				return 0;
			Boolean[][] innerGrid = initial.get(z + 1);
			int count = 0;
			if (refX == 2 && refY == 1) {
				for (int i = 0; i < 5; i++) {
					if (innerGrid[0][i])
						count++;
				}
			} else if (refX == 2 && refY == 3) {
				for (int i = 0; i < 5; i++) {
					if (innerGrid[4][i])
						count++;
				}
			} else if (refX == 1 && refY == 2) {
				for (int i = 0; i < 5; i++) {
					if (innerGrid[i][0])
						count++;
				}
			} else if (refX == 3 && refY == 2) {
				for (int i = 0; i < 5; i++) {
					if (innerGrid[i][4])
						count++;
				}
			}
			return count;
		} else {
			if (initialGrid[y][x])
				return 1;
			else
				return 0;
		}
	}

	private Boolean[][] copyMap(Boolean[][] grid) {
		Boolean[][] result = new Boolean[grid.length][grid[0].length];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				result[y][x] = grid[y][x];
			}
		}

		return result;
	}

	private Map<Integer, Boolean[][]> copyMap(Map<Integer, Boolean[][]> grid) {
		Map<Integer, Boolean[][]> result = new HashMap<>();
		for (Map.Entry<Integer, Boolean[][]> entry : grid.entrySet()) {
			result.put(entry.getKey(), copyMap(entry.getValue()));
		}
		return result;
	}

	private long getHash(Boolean[][] map) {
		long result = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x])
					result += Math.pow(2, (y * map.length + x));
			}
		}
		return result;
	}

	public long problemA() {
		Boolean[][] map = getMap();
		Set<Long> states = new HashSet<>();
		while (true) {
			Boolean[][] current = copyMap(map);
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[0].length; x++) {
					int bugCount = 0;
					for (Point p : Directions.DIRECTIONS_ARRAY) {
						if (getMapPoint(map, p.x + x, p.y + y)) {
							bugCount++;
						}
					}
					if (getMapPoint(map, x, y)) {
						if (bugCount != 1) {
							current[y][x] = false;
						}
					} else {
						if (bugCount == 1 || bugCount == 2) {
							current[y][x] = true;
						}
					}
				}
			}
			map = current;
			long hash = getHash(map);
			if (states.contains(hash))
				break;
			states.add(hash);
		}

		return getHash(map);
	}

	public long problemB(int interations) {

		Map<Integer, Boolean[][]> map = new HashMap<>();
		map.put(0, getMap());

		for (int i = 0; i < interations; i++) {
			IntSummaryStatistics stats = map.keySet().stream().mapToInt(z -> z).summaryStatistics();
			if (getCount(map.get(stats.getMin())) > 0) {
				map.put(stats.getMin() - 1, generateEmptyLayer());
			}
			if (getCount(map.get(stats.getMax())) > 0) {
				map.put(stats.getMax() + 1, generateEmptyLayer());
			}

			Map<Integer, Boolean[][]> copyMap = copyMap(map);
			for (int z : copyMap.keySet()) {
				Boolean[][] current = copyMap.get(z);
				for (int y = 0; y < current.length; y++) {
					for (int x = 0; x < current[0].length; x++) {
						if (x == 2 && y == 2)
							continue;
						int bugCount = 0;
						for (Point p : Directions.DIRECTIONS_ARRAY) {
							bugCount += getMapPoint(map, x, y, x + p.x, y + p.y, z);
						}
						if (getMapPoint(map.get(z), x, y)) {
							if (bugCount != 1) {
								current[y][x] = false;
							}
						} else {
							if (bugCount == 1 || bugCount == 2) {
								current[y][x] = true;
							}
						}
					}
				}
			}
			map = copyMap;
		}

		long result = 0;
		for (Map.Entry<Integer, Boolean[][]> entry : map.entrySet()) {
			for (int y = 0; y < entry.getValue().length; y++) {
				for (int x = 0; x < entry.getValue()[0].length; x++) {
					if (entry.getValue()[y][x])
						result++;
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day24(TESTA).problemA());
		System.out.println("A: " + new Day24(INPUT).problemA());
		System.out.println("TestB: " + new Day24(TESTA).problemB(10));
		System.out.println("B: " + new Day24(INPUT).problemB(200));
	}
}