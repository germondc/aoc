package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day13 {

	private int input;
	
	private int testA() {
		input = 10;
		Coord goal = new Coord(7,  4);
		return doMove(goal);
	}
	
	private int problemA() {
		input = 1362;
		Coord goal = new Coord(31,  39);
		return doMove(goal);
	}

	private boolean isWall(int x, int y) {
		int value = x * x + 3 * x + 2 * x * y + y + y * y + input;
		String bin = Integer.toBinaryString(value);
		String zeros = bin.replace("1", "");
		return (bin.length() - zeros.length()) % 2 == 1;
	}

	private class Move {
		int count;
		Coord coord;

		public Move(int count, Coord coord) {
			this.count = count;
			this.coord = coord;
		}

		@Override
		public String toString() {
			return String.format("Move [count=%s, coord=%s]", count, coord);
		}
	}

	private class Coord {
		int x;
		int y;

		public Coord(int x, int y) {
			super();
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

	private static final int[] DELTAX = new int[] { 0, 0, -1, 1 };
	private static final int[] DELTAY = new int[] { -1, 1, 0, 0 };

	private Set<Coord> been = new HashSet<>();

	private List<Move> getPossibleMoves(Coord current, int moveCount) {
		List<Move> result = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int newX = current.x + DELTAX[i];
			int newY = current.y + DELTAY[i];
			if (newX < 0 || newY < 0 || isWall(newX, newY))
				continue;
			Coord c = new Coord(newX, newY);
			if (!been.contains(c)) {
				Move move = new Move(moveCount + 1, c);
				result.add(move);
				been.add(c);
			}

		}
		return result;
	}

	private int doMove(Coord goal) {
		Queue<Move> moveQueue = new ArrayDeque<>();
		Coord c = new Coord(1, 1);
		been.add(c);
		moveQueue.addAll(getPossibleMoves(c, 0));
		int moves = 0;
		while (moveQueue.size() > 0) {
			Move move = moveQueue.remove();
			if (move.count > moves) {
				moves = move.count;
			}
			if (move.coord.equals(goal)) {
				return moves;
			}
			moveQueue.addAll(getPossibleMoves(move.coord, move.count));
		}
		return -1;
	}
	
	private int doMoveB() {
		Queue<Move> moveQueue = new ArrayDeque<>();
		Coord c = new Coord(1, 1);
		been.add(c);
		moveQueue.addAll(getPossibleMoves(c, 0));
		int moves = 0;
		while (moveQueue.size() > 0) {
			Move move = moveQueue.remove();
			if (move.count > moves) {
				moves = move.count;
			}
			if (move.count < 50) {
				moveQueue.addAll(getPossibleMoves(move.coord, move.count));
			}
			
		}
		return been.size();
	}

	private int problemB() {
		input = 1362;
		return doMoveB();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println("Test A: " + new Day13().testA());
		System.out.println("A: " + new Day13().problemA());
		System.out.println("B: " + new Day13().problemB());
	}
}
