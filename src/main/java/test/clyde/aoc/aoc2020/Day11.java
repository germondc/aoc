package test.clyde.aoc.aoc2020;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day11 {

	private static List<String> LINES = Utils.readFile("2020/Day11.txt");
	private static List<String> LINESA = Utils.readFile("2020/Day11a.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day11().problemA());
		System.out.println("B: " + new Day11().problemB());
	}
	
	private int[][] getSeating() {
		int sizeDown = LINES.size();
		int sizeAcross = LINES.get(0).length();

		int[][] seating = new int[sizeDown][sizeAcross];
		int index = 0;
		for (String line : LINES) {
			char[] c = line.toCharArray();
			for (int i = 0; i < c.length; i++) {

				int pos;
				if (c[i] == 'L')
					pos = 1;
				else if (c[i] == '.')
					pos = 0;
				else
					pos = 2;
				seating[index][i] = pos;
			}
			index++;
		}
		return seating;
	}

	private long problemA() {
		// LINES=LINESA;
		int[][] seating = getSeating();

		int counter = 0;
		int[][] newSeating = new int[seating.length][seating[0].length];
		boolean first = true;
		while (!testSeating(seating, newSeating)) {
			if (!first) {
				seating = newSeating;
			}
			newSeating = copySeat(seating);
			for (int down = 0; down < seating.length; down++) {
				for (int ac = 0; ac < seating[0].length; ac++) {
					if (seating[down][ac] == 1 && noAdjacentSeats(seating, down, ac)) {
						newSeating[down][ac] = 2;
					} else if (seating[down][ac] == 2 && noOccSeats(seating, down, ac) >= 4) {
						newSeating[down][ac] = 1;
					}
				}
			}
			first = false;
			counter++;
		}
		System.out.println("Steps: " + counter);
		return noOccSeats(seating);
	}

	private boolean testSeating(int[][] seating1, int[][] seating2) {
		for (int down = 0; down < seating1.length; down++) {
			for (int ac = 0; ac < seating1[0].length; ac++) {
				if (seating1[down][ac] != seating2[down][ac])
					return false;
			}
		}
		return true;
	}

	private boolean noAdjacentSeats(int[][] seating, int down, int across) {
		int minAcross = Math.max(0, across - 1);
		int minDown = Math.max(0, down - 1);
		int maxAcross = Math.min(seating[0].length - 1, across + 1);
		int maxDown = Math.min(seating.length - 1, down + 1);

		for (int d = minDown; d <= maxDown; d++) {
			for (int a = minAcross; a <= maxAcross; a++) {
				if (d == down && a == across)
					continue;
				if (seating[d][a] == 2)
					return false;
			}
		}
		return true;
	}
	
	private int seatInDir(int[][] seating, int y, int x, int deltaY, int deltaX) {
		int occSeats = 0;
		while ((y + deltaY) >= 0 && (x + deltaX) >= 0 && (y + deltaY) < seating.length && (x + deltaX) < seating[0].length) {
			if (seating[y + deltaY][x + deltaX] == 2) {
				occSeats++;
				break;
			} else if (seating[y + deltaY][x + deltaX] == 1) {
				break;
			}
			deltaX = adjustDelta(deltaX);
			deltaY = adjustDelta(deltaY);
		}
		return occSeats;
	}
	
	private int adjustDelta(int delta) {
		if (delta==0)
			return delta;
		if (delta<0)
			return delta-1;
		return delta+1;
	}

	private int noAdjacentSeatsB(int[][] seating, int down, int across) {

		int occSeats = seatInDir(seating, down, across, -1, 0);
		occSeats += seatInDir(seating, down, across, 0, -1);
		occSeats += seatInDir(seating, down, across, 1, 0);
		occSeats += seatInDir(seating, down, across, 0, 1);
		occSeats += seatInDir(seating, down, across, -1, -1);
		occSeats += seatInDir(seating, down, across, 1, -1);
		occSeats += seatInDir(seating, down, across, -1, 1);
		occSeats += seatInDir(seating, down, across, 1, 1);
		
		return occSeats;
	}

	private int noOccSeats(int[][] seating) {
		int occSeats = 0;
		for (int d = 0; d < seating.length; d++) {
			for (int a = 0; a < seating[0].length; a++) {
				if (seating[d][a] == 2)
					occSeats++;
			}
		}
		return occSeats;
	}

	private int noOccSeats(int[][] seating, int down, int across) {
		int minAcross = Math.max(0, across - 1);
		int minDown = Math.max(0, down - 1);
		int maxAcross = Math.min(seating[0].length - 1, across + 1);
		int maxDown = Math.min(seating.length - 1, down + 1);

		int occSeats = 0;
		for (int d = minDown; d <= maxDown; d++) {
			for (int a = minAcross; a <= maxAcross; a++) {
				if (d == down && a == across)
					continue;
				if (seating[d][a] == 2)
					occSeats++;
			}
		}
		return occSeats;
	}

	private int[][] copySeat(int[][] seating) {
		int[][] newSeating = new int[seating.length][seating[0].length];
		for (int down = 0; down < seating.length; down++) {
			for (int ac = 0; ac < seating[0].length; ac++) {
				newSeating[down][ac] = seating[down][ac];
			}
		}
		return newSeating;
	}

	private long problemB() {
		//LINES=LINESA;
		int[][] seating = getSeating();

		int counter = 0;
		int[][] newSeating = new int[seating.length][seating[0].length];
		boolean first = true;
		while (!testSeating(seating, newSeating)) {
			if (!first) {
				seating = newSeating;
			}
			newSeating = copySeat(seating);
			for (int down = 0; down < seating.length; down++) {
				for (int ac = 0; ac < seating[0].length; ac++) {
					if (seating[down][ac] == 1 && noAdjacentSeatsB(seating, down, ac)==0) {
						newSeating[down][ac] = 2;
					} else if (seating[down][ac] == 2 && noAdjacentSeatsB(seating, down, ac) >= 5) {
						newSeating[down][ac] = 1;
					}
				}
			}
			first = false;
			counter++;
			
		}
		System.out.println("Steps: " + counter);
		return noOccSeats(seating);
	}
}
