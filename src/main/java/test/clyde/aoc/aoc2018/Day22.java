package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import test.clyde.aoc.utils.Directions;
import test.clyde.aoc.utils.Utils;

public class Day22 {
	private static List<String> LINES = Utils.readFile("2018/Day22.txt");

	private enum Gear {
		climbing(new int[] { 0, 1 }), torch(new int[] { 0, 2 }), none(new int[] { 1, 2 });

		private int[] poss;

		Gear(int[] poss) {
			this.poss = poss;
		}

		public boolean canMoveTo(int type) {
			return poss[0] == type || poss[1] == type;
		}
	}

	private class RoomWithGear {
		Point location;
		Gear gear;
		int bestTime = Integer.MAX_VALUE;
		RoomWithGear fromRoom;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((gear == null) ? 0 : gear.hashCode());
			result = prime * result + ((location == null) ? 0 : location.hashCode());
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
			RoomWithGear other = (RoomWithGear) obj;
			if (gear != other.gear)
				return false;
			if (location == null) {
				if (other.location != null)
					return false;
			} else if (!location.equals(other.location))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return String.format("RoomWithGear [(%s, %s) gear=%s time=%s]", location.x, location.y, gear, bestTime);
		}
	}

	private List<RoomWithGear> getPossibleMoves(Map<RoomWithGear, RoomWithGear> alreadyBeen, RoomWithGear current,
			int[][] map) {
		List<RoomWithGear> result = new ArrayList<>();

		for (Point p : Directions.DIRECTIONS_ARRAY) {
			int newX = current.location.x + p.x;
			int newY = current.location.y + p.y;
			if (newX < 0 || newY < 0 || newX >= map[0].length || newY >= map.length
					|| !current.gear.canMoveTo(map[newY][newX]))
				continue;

			RoomWithGear nextRoom = new RoomWithGear();
			nextRoom.location = new Point(newX, newY);
			nextRoom.gear = current.gear;
			nextRoom.bestTime = current.bestTime + 1;
			nextRoom.fromRoom = current;
			if (alreadyBeen.containsKey(nextRoom)) {
				RoomWithGear previous = alreadyBeen.get(nextRoom);
				if (nextRoom.bestTime < previous.bestTime) {
					previous.bestTime = nextRoom.bestTime;
					previous.fromRoom = current;
					result.add(nextRoom);
				}
			} else {
				result.add(nextRoom);
				alreadyBeen.put(nextRoom, nextRoom);
			}
		}

		for (Gear g : Gear.values()) {
			if (g == current.gear || !g.canMoveTo(map[current.location.y][current.location.x]))
				continue;
			RoomWithGear nextRoom = new RoomWithGear();
			nextRoom.location = new Point(current.location.x, current.location.y);
			nextRoom.gear = g;
			nextRoom.bestTime = current.bestTime + 7;
			nextRoom.fromRoom = current;
			if (alreadyBeen.containsKey(nextRoom)) {
				RoomWithGear previous = alreadyBeen.get(nextRoom);
				if (nextRoom.bestTime < previous.bestTime) {
					previous.bestTime = nextRoom.bestTime;
					previous.fromRoom = current;
					result.add(nextRoom);
				}
			} else {
				result.add(nextRoom);
				alreadyBeen.put(nextRoom, nextRoom);
			}
		}

		return result;
	}

	private long problemA(List<String> lines) throws Exception {
		String[] split = lines.get(0).split(" ");
		int depth = Integer.valueOf(split[1]);
		split = lines.get(1).split(" ")[1].split(",");
		Point target = new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1]));

		return solveA(target, depth);
	}

	private long problemB(List<String> lines) throws Exception {
		String[] split = lines.get(0).split(" ");
		int depth = Integer.valueOf(split[1]);
		split = lines.get(1).split(" ")[1].split(",");
		Point target = new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
		return solveB(target, depth);
	}

	private long solveB(Point target, int depth) {
		int[][] map = getMap(target, depth);

		int worst = (target.x + target.y) * 8;
		Queue<RoomWithGear> queue = new ArrayDeque<>();
		RoomWithGear start = new RoomWithGear();
		start.location = new Point(0, 0);
		start.gear = Gear.torch;
		start.bestTime = 0;
		queue.add(start);

		boolean printPath = false;
		int bestTime = worst;
		RoomWithGear bestFinish = null;
		Map<RoomWithGear, RoomWithGear> alreadyBeen = new HashMap<>();
		alreadyBeen.put(start, start);
		while (queue.size() > 0) {
			RoomWithGear current = queue.remove();
			if (current.bestTime > bestTime)
				continue;
			if (current.location.x == target.x && current.location.y == target.y && current.gear == Gear.torch) {
				bestTime = current.bestTime;
				bestFinish = current;
			}
			queue.addAll(getPossibleMoves(alreadyBeen, current, map));
		}
		if (printPath) {
			RoomWithGear current = bestFinish.fromRoom;
			while (!current.equals(start)) {
				System.out.println(current);
				current = current.fromRoom;
			}
			System.out.println(start);
		}
		
		return bestTime;
	}

	private int[][] getMap(Point target, int depth) {
		int targetX = target.x + 50;
		int targetY = target.y + 50;

		long[][] erosionLevels = new long[targetY + 1][targetX + 1];
		int[][] types = new int[targetY + 1][targetX + 1];
		for (int y = 0; y <= targetY; y++) {
			for (int x = 0; x <= targetX; x++) {
				long geoIndex = 0;
				if (x == 0 && y == 0) {
					geoIndex = 0;
				} else if (x == 0) {
					geoIndex = y * 48271;
				} else if (y == 0) {
					geoIndex = x * 16807;
				} else if (x == target.x && y == target.y) {
					geoIndex = 0;
				} else {
					geoIndex = erosionLevels[y - 1][x] * erosionLevels[y][x - 1];
				}
				erosionLevels[y][x] = (geoIndex + depth) % 20183;
				types[y][x] = (int) (erosionLevels[y][x] % 3);
			}
		}
		return types;
	}

	private long solveA(Point target, int depth) {
		int[][] types = getMap(target, depth);
		int risk = 0;
		for (int y = 0; y <= target.y; y++) {
			for (int x = 0; x <= target.x; x++) {

				risk += types[y][x];
			}
		}

		return risk;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("TestA: " + new Day22().solveA(new Point(10, 10), 510));
		System.out.println("A: " + new Day22().problemA(LINES));
		System.out.println("TestB: " + new Day22().solveB(new Point(10, 10), 510));
		System.out.println("B: " + new Day22().problemB(LINES));
	}

}
