package test.clyde.aoc.aoc2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Hex;

public class Day17 {

	private static String INPUT = "rrrbmfta";

	private enum Direction {
		U, D, L, R
	}

	private class Move {
		int count;
		List<Direction> previousMoves;
		Coord coord;

		public Move(int count, List<Direction> previousMoves, Coord coord) {
			this.count = count;
			this.previousMoves = previousMoves;
			this.coord = coord;
		}

		@Override
		public String toString() {
			return String.format("Move [count=%s, previousMoves=%s, coord=%s]", count,
					previousMoves.stream().map(d -> d.name()).collect(Collectors.joining()), coord);
		}
	}

	private class Coord {
		int x;
		int y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
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
			Coord other = (Coord) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", x, y);
		}
	}

	private static MessageDigest MD;
	static {
		try {
			MD = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private List<Direction> getShortPath() {
		// INPUT = "ulqzkmiv";
		Coord start = new Coord(0, 0);
		Coord goal = new Coord(3, 3);

		Queue<Move> nextMovesQueue = new ArrayDeque<>();
		nextMovesQueue.addAll(getNextMoves(0, new ArrayList<>(), start));

		while (nextMovesQueue.size() > 0) {
			Move currentMove = nextMovesQueue.remove();

			if (currentMove.coord.equals(goal)) {
				return currentMove.previousMoves;
			}
			nextMovesQueue.addAll(getNextMoves(currentMove.count, currentMove.previousMoves, currentMove.coord));
		}

		return null;
	}

	private int getLongPath() {
		//INPUT = "ulqzkmiv";
		Coord start = new Coord(0, 0);
		Coord goal = new Coord(3, 3);

		Queue<Move> nextMovesQueue = new ArrayDeque<>();
		nextMovesQueue.addAll(getNextMoves(0, new ArrayList<>(), start));
		int longest = 0;

		while (nextMovesQueue.size() > 0) {
			Move currentMove = nextMovesQueue.remove();

			if (currentMove.coord.equals(goal)) {
				if (currentMove.previousMoves.size() > longest) {
					longest = currentMove.previousMoves.size();
				}
			} else {
				nextMovesQueue.addAll(getNextMoves(currentMove.count, currentMove.previousMoves, currentMove.coord));
			}
		}

		return longest;
	}

	private String problemA() {
		List<Direction> path = getShortPath();
		return path.stream().map(d -> d.name()).collect(Collectors.joining());
	}

	private int problemB() {
		return getLongPath();
	}

	private String getStartOfMd5(String current) {
		MD.update(current.getBytes());
		String hash = Hex.encodeHexString(MD.digest());
		return hash.substring(0, 4);
	}

	private List<Move> getNextMoves(int count, List<Direction> previousMoves, Coord coord) {
		List<Move> result = new ArrayList<>();
		List<Direction> dirs = getPossible(previousMoves, coord);
		for (Direction dir : dirs) {
			Coord newCoord = getCoordForDirection(dir, coord);
			// if (!been.contains(newCoord)) {
			// been.add(newCoord);
			List<Direction> updatedMoves = new ArrayList<>(previousMoves);
			updatedMoves.add(dir);
			Move move = new Move(count + 1, updatedMoves, newCoord);
			result.add(move);
			// }
		}
		return result;
	}

	private List<Direction> getPossible(List<Direction> moves, Coord coord) {
		List<Direction> result = new ArrayList<>();
		String dirs = moves.stream().map(d -> d.name()).collect(Collectors.joining());
		String md5Start = getStartOfMd5(INPUT + dirs);
		if (isOpen(md5Start.charAt(0)) && coord.y > 0)
			result.add(Direction.U);
		if (isOpen(md5Start.charAt(1)) && coord.y < 3)
			result.add(Direction.D);
		if (isOpen(md5Start.charAt(2)) && coord.x > 0)
			result.add(Direction.L);
		if (isOpen(md5Start.charAt(3)) && coord.x < 3)
			result.add(Direction.R);
		return result;
	}

	private boolean isOpen(char dir) {
		if (dir == 'B' || dir == 'C' || dir == 'D' || dir == 'E' || dir == 'F')
			return true;
		return false;
	}

	private Coord getCoordForDirection(Direction direction, Coord start) {
		int x = start.x;
		int y = start.y;
		if (direction == Direction.U)
			y -= 1;
		else if (direction == Direction.D)
			y += 1;
		else if (direction == Direction.L)
			x -= 1;
		else if (direction == Direction.R)
			x += 1;
		return new Coord(x, y);
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day17().problemA());
		System.out.println("B: " + new Day17().problemB());
	}
}
