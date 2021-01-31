package test.clyde.aoc.aoc2018;

import java.util.HashMap;
import java.util.Map;

public class Day9 {
	
	private class Marble {
		int number;
		Marble cw;
		Marble ccw;
		public Marble(int number) {
			super();
			this.number = number;
		}
		@Override
		public String toString() {
			return String.format("Marble [number=%s]", number);
		}
	}
	
	private long problemA(int playersNo, int last) {
		Map<Integer, Long> players = new HashMap<>();
		for (int i=0; i<playersNo; i++) {
			players.put(i, 0L);
		}
		int player = 0;
		Marble firstMarble = new Marble(0);
		firstMarble.cw = firstMarble;
		firstMarble.ccw = firstMarble;
		Marble currentMarble = firstMarble;
		for (int i=1; i<last; i++) {
			if (i%23==0) {
				long currentScore = players.get(player);
				currentScore += i;
				Marble temp = currentMarble;
				for (int j=0; j<7; j++) {
					temp = temp.ccw;
				}
				currentScore += temp.number;
				players.put(player, currentScore);
				temp.ccw.cw = temp.cw;
				temp.cw.ccw = temp.ccw;
				currentMarble = temp.cw;
			} else {
				Marble newMarble = new Marble(i);
				Marble cw1 = currentMarble.cw;
				Marble cw2 = currentMarble.cw.cw;
				newMarble.ccw = cw1;
				newMarble.cw = cw2;
				cw1.cw = newMarble;
				cw2.ccw = newMarble;
				currentMarble = newMarble;
			}
			player++;
			if (player==playersNo) {
				player=0;
			}
		}
		return players.values().stream().mapToLong(l -> l).max().getAsLong();
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day9().problemA(9, 25));
		System.out.println("TestA: " + new Day9().problemA(10, 1618));
		System.out.println("TestA: " + new Day9().problemA(13, 7999));
		System.out.println("TestA: " + new Day9().problemA(17, 1104));
		System.out.println("TestA: " + new Day9().problemA(21, 6111));
		System.out.println("TestA: " + new Day9().problemA(30, 5807));
		System.out.println("A: " + new Day9().problemA(448, 71628));
//		System.out.println("TestB: " + new Day9().problemB(TEST));
		System.out.println("B: " + new Day9().problemA(448, 7162800));
	}

}
