package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import test.clyde.aoc.utils.Directions;
import test.clyde.aoc.utils.Utils;

public class Day15 {

	private static List<String> LINES = Utils.readFile("2018/Day15.txt");
	//private static List<String> MOVE = Utils.readFile("2018/Day15_move.txt");
	private static List<String> COMBAT = Utils.readFile("2018/Day15_combat.txt");
	private static List<String> COMBAT2 = Utils.readFile("2018/Day15_combat2.txt");
	private static List<String> COMBAT3 = Utils.readFile("2018/Day15_combat3.txt");
	private static List<String> COMBAT4 = Utils.readFile("2018/Day15_combat4.txt");
	private static List<String> COMBAT5 = Utils.readFile("2018/Day15_combat5.txt");
	private static List<String> COMBAT6 = Utils.readFile("2018/Day15_combat6.txt");

	private class Soldier implements Comparable<Soldier> {
		Point location;
		char type;
		int hp = 200;
		int attack = 3;

		@Override
		public int compareTo(Soldier o) {
			if (this.location.y < o.location.y)
				return -1;
			if (this.location.y > o.location.y)
				return 1;
			return this.location.x - o.location.x;
		}

		@Override
		public String toString() {
			return String.format("Soldier@(%s,%s)", location.x, location.y);
		}
	}

	private class PointDistance implements Comparable<PointDistance> {
		int x;
		int y;
		int distance;

		public PointDistance(int x, int y, int distance) {
			super();
			this.x = x;
			this.y = y;
			this.distance = distance;
		}

		@Override
		public int compareTo(PointDistance o) {
			return distance - o.distance;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
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
			PointDistance other = (PointDistance) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}

	private boolean isMovable(char[][] grid, int x, int y, Soldier currentSoldier) {
		if (x == currentSoldier.location.x && y == currentSoldier.location.y)
			return true;
		char c = grid[y][x];
		if (c == '.')
			return true;
		else
			return false;
	}

	private Map<Soldier, Map<Point, Integer[][]>> getAllDistances(List<Soldier> soldiers, Soldier currentSoldier,
			char[][] currentGrid) {
		Map<Soldier, Map<Point, Integer[][]>> result = new HashMap<>();
		for (Soldier soldier : soldiers) {
			if (soldier.hp <= 0 || soldier.type == currentSoldier.type)
				continue;
			Map<Point, Integer[][]> nextDoorDistances = new LinkedHashMap<>();
			for (Point adjDirection : Directions.DIRECTIONS_ARRAY_RO) {
				int adjX = soldier.location.x + adjDirection.x;
				int adjY = soldier.location.y + adjDirection.y;
				if (isMovable(currentGrid, adjX, adjY, currentSoldier)) {
					Integer[][] distances = new Integer[currentGrid.length][currentGrid[0].length];

					Queue<PointDistance> queue = new PriorityQueue<>();
					queue.add(new PointDistance(adjX, adjY, 0));
					while (queue.size() > 0) {
						PointDistance p = queue.remove();
						distances[p.y][p.x] = p.distance;
						for (Point direction : Directions.DIRECTIONS_ARRAY_RO) {
							int newX = p.x + direction.x;
							int newY = p.y + direction.y;
							if (isMovable(currentGrid, newX, newY, currentSoldier)) {
								if (distances[newY][newX] == null) {
									PointDistance pd = new PointDistance(newX, newY, p.distance + 1);
									if (!queue.contains(pd))
										queue.add(pd);
								}
							} else {
								if (newX == adjX && newY == adjY)
									continue;
								distances[newY][newX] = -1;
							}
						}
					}
					nextDoorDistances.put(
							new Point(soldier.location.x + adjDirection.x, soldier.location.y + adjDirection.y),
							distances);
				}
			}
			if (nextDoorDistances.size() > 0)
				result.put(soldier, nextDoorDistances);
		}
		return result;
	}

	private long problemA(List<String> lines) {
		return simulate(lines, 3, false);
	}
	
	private long problemB(List<String> lines) {
		long result = -1;
		int elfAttack = 4;
		while (result == -1) {
			result = simulate(lines, elfAttack++, true);
		}
		System.out.println("elf attack: " + elfAttack);
		return result;
	}
	
	private long simulate(List<String> lines, int elfAttack, boolean exitOnElfDying) {
		List<Soldier> soldiers = new ArrayList<>();
		char[][] grid = getGrid(lines, soldiers, elfAttack);
		Map<Soldier, Map<Point, Integer[][]>> distances;
		int rounds = 0;
		main: while (true) {
//			ArrayUtils.printGrid(grid);
			Collections.sort(soldiers);
			for (Soldier soldier1 : soldiers) {
				if (soldier1.hp <= 0)
					continue;
				if (soldiers.stream().filter(s -> s.hp > 0 && s.type != soldier1.type).count() == 0)
					break main;
				distances = getAllDistances(soldiers, soldier1, grid);
				int nearest = Integer.MAX_VALUE;
				int lowestHp = Integer.MAX_VALUE;
				Point closestPoint = null;
				Soldier nearestSoldier = null;
				for (Soldier soldier2 : soldiers) {
					if (soldier1 == soldier2 || soldier1.type == soldier2.type)
						continue;
					if (distances.get(soldier2) == null)
						continue;
					for (Map.Entry<Point, Integer[][]> entry : distances.get(soldier2).entrySet()) {
						Integer soldierDistance = entry.getValue()[soldier1.location.y][soldier1.location.x];
						if (soldierDistance != null && (soldierDistance < nearest
								|| soldierDistance == nearest && soldier2.hp < lowestHp)) {
							nearest = soldierDistance;
							closestPoint = entry.getKey();
							nearestSoldier = soldier2;
							lowestHp = soldier2.hp;
						}
					}
				}
				if (nearestSoldier == null) {
					// no possible moves
					continue;
				}

				if (!(closestPoint.x == soldier1.location.x && closestPoint.y == soldier1.location.y)) {
					// move
					Integer[][] pathDistances = distances.get(nearestSoldier).get(closestPoint);
					nearest = Integer.MAX_VALUE;
					int bestX = 0;
					int bestY = 0;
					for (Point direction : Directions.DIRECTIONS_ARRAY_RO) {
						int adjX = soldier1.location.x + direction.x;
						int adjY = soldier1.location.y + direction.y;
						if (grid[adjY][adjX] == '.' && pathDistances[adjY][adjX] < nearest) {
							nearest = pathDistances[adjY][adjX];
							bestX = adjX;
							bestY = adjY;
						}
					}
					grid[soldier1.location.y][soldier1.location.x] = '.';
					soldier1.location.x = bestX;
					soldier1.location.y = bestY;
					grid[bestY][bestX] = soldier1.type;
				}
				
				if (closestPoint.x == soldier1.location.x && closestPoint.y == soldier1.location.y) {
					// attack
					nearestSoldier.hp -= soldier1.attack;
					if (nearestSoldier.hp <= 0) {
						if (exitOnElfDying && nearestSoldier.type == 'E')
							return -1;
						grid[nearestSoldier.location.y][nearestSoldier.location.x] = '.';
					}
				}
			}
			rounds++;
		}

		int remainingHp = soldiers.stream().filter(s -> s.hp > 0).mapToInt(s -> s.hp).sum();
		long outcome = rounds * remainingHp;
		return outcome;
	}

	private char[][] getGrid(List<String> lines, List<Soldier> soldiers, int elfAttack) {
		char[][] result = new char[lines.size()][lines.get(0).length()];
		int y = 0;
		for (String line : lines) {
			int x = 0;
			for (char c : line.toCharArray()) {
				if (c == 'G' || c == 'E') {
					Soldier s = new Soldier();
					s.type = c;
					if (c=='E')
						s.attack=elfAttack;
					s.location = new Point(x, y);
					soldiers.add(s);
				}
				result[y][x] = c;
				x++;
			}
			y++;
		}
		return result;
	}

	public static void main(String[] args) {
		// System.out.println("TestA: " + new Day15().problemA(MOVE));
		System.out.println("Test Combat: " + new Day15().problemA(COMBAT));
		System.out.println("Test Combat2: " + new Day15().problemA(COMBAT2));
		System.out.println("Test Combat3: " + new Day15().problemA(COMBAT3));
		System.out.println("Test Combat4: " + new Day15().problemA(COMBAT4));
		System.out.println("Test Combat5: " + new Day15().problemA(COMBAT5));
		System.out.println("Test Combat6: " + new Day15().problemA(COMBAT6));
		System.out.println("A: " + new Day15().problemA(LINES));
		
		System.out.println("B: " + new Day15().problemB(LINES));
	}

}
