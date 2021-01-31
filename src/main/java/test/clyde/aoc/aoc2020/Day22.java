package test.clyde.aoc.aoc2020;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import test.clyde.aoc.utils.Utils;

public class Day22 {
	private static List<String> LINES = Utils.readFile("2020/Day22.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/Day22a.txt");

	public static void main(String[] args) throws IOException {
		new Day22().solve(TEST_LINES);
		new Day22().solve(LINES);
		new Day22().solveB(TEST_LINES);
		new Day22().solveB(LINES);
	}

	public void solve(List<String> lines) {

		Queue<Integer> player1 = new ArrayDeque<>();
		Queue<Integer> player2 = new ArrayDeque<>();

		boolean isPlayer1 = true;
		for (String line : lines) {
			if (line.contains("Player 2")) {
				isPlayer1 = false;
				continue;
			} else if (line.length() == 0 || line.contains("Player")) {
				continue;
			}
			if (isPlayer1) {
				player1.add(Integer.valueOf(line));
			} else {
				player2.add(Integer.valueOf(line));
			}
		}

		while (player1.size() > 0 && player2.size() > 0) {
			int card1 = player1.remove();
			int card2 = player2.remove();
			if (card1 > card2) {
				player1.add(card1);
				player1.add(card2);
			} else {
				player2.add(card2);
				player2.add(card1);
			}
		}

		Queue<Integer> winningQueue = player1.size() == 0 ? player2 : player1;
		long score = 0;
		int size = winningQueue.size();
		for (int i = 0; i < size; i++) {
			score += winningQueue.remove() * (size - i);
		}
		System.out.println("A: " + score);
	}
	
	private List<Integer> combinedList(Queue<Integer> p1, Queue<Integer> p2) {
		List<Integer> result = new ArrayList<>(p1);
		result.add(Integer.MAX_VALUE);
		result.addAll(p2);
		return result;
	}

	private int playSubGame(Queue<Integer> p1, Queue<Integer> p2, int c1, int c2) {
		List<List<Integer>> moves = new ArrayList<>();
		Queue<Integer> player1 = new ArrayDeque<>(p1);
		Queue<Integer> player2 = new ArrayDeque<>(p2);
		while (player1.size() > c1) {
			((ArrayDeque<Integer>) player1).removeLast();
		}
		while (player2.size() > c2) {
			((ArrayDeque<Integer>) player2).removeLast();
		}

		while (player1.size() > 0 && player2.size() > 0) {
			List<Integer> combined = combinedList(player1, player2);
			if (moves.contains(combined))
				return 1;
			moves.add(combined);
			int card1 = player1.remove();
			int card2 = player2.remove();
			if (card1 > card2) {
				player1.add(card1);
				player1.add(card2);
			} else {
				player2.add(card2);
				player2.add(card1);
			}
		}
		return player1.size() == 0 ? 2 : 1;
	}

	public void solveB(List<String> lines) {

		Queue<Integer> player1 = new ArrayDeque<>();
		Queue<Integer> player2 = new ArrayDeque<>();

		boolean isPlayer1 = true;
		for (String line : lines) {
			if (line.contains("Player 2")) {
				isPlayer1 = false;
				continue;
			} else if (line.length() == 0 || line.contains("Player")) {
				continue;
			}
			if (isPlayer1) {
				player1.add(Integer.valueOf(line));
			} else {
				player2.add(Integer.valueOf(line));
			}
		}

		while (player1.size() > 0 && player2.size() > 0) {
			int card1 = player1.remove();
			int card2 = player2.remove();
			boolean player1Okay = (card1 <= player1.size());
			boolean player2Okay = (card2 <= player2.size());

			if (player1Okay && player2Okay) {
				if (playSubGame(player1, player2, card1, card2) == 1) {
					player1.add(card1);
					player1.add(card2);
				} else {
					player2.add(card2);
					player2.add(card1);
				}
			} else {
				if (card1 > card2) {
					player1.add(card1);
					player1.add(card2);
				} else {
					player2.add(card2);
					player2.add(card1);
				}
			}
		}

		Queue<Integer> winningQueue = player1.size() == 0 ? player2 : player1;
		long score = 0;
		int size = winningQueue.size();
		for (int i = 0; i < size; i++) {
			score += winningQueue.remove() * (size - i);
		}
		System.out.println("B: " + score);
	}

}
