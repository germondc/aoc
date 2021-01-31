package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day12 {

	private static List<String> LINES = Utils.readFile("2020/Day12.txt");
	private static List<String> LINESA = Utils.readFile("2020/Day12a.txt");

	private class Ins {
		Character operator;
		int amount;

		public Ins(Character operator, int amount) {
			super();
			this.operator = operator;
			this.amount = amount;
			if (operator == 'L') {
				this.operator = 'R';
				this.amount = -amount;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day12().problemA(LINESA));
		System.out.println("A: " + new Day12().problemA(LINES));
		System.out.println("TestB: " + new Day12().problemB(LINESA));
		System.out.println("B: " + new Day12().problemB(LINES));
	}

	private long problemA(List<String> lines) {
		List<Ins> inss = new ArrayList<>();
		for (String line : lines) {
			int amount = Integer.valueOf(line.substring(1));
			Ins i = new Ins(line.charAt(0), amount);
			inss.add(i);
		}

		int bearing = 90;
		int xDir = 1;
		int yDir = 0;
		int currentX = 0;
		int currentY = 0;
		for (Ins i : inss) {
			if (i.operator == 'R') {
				bearing += i.amount;
				if (bearing < 0)
					bearing += 360;
				else if (bearing >= 360)
					bearing -= 360;
	
				if (bearing == 0) {
					xDir = 0;
					yDir = -1;
				} else if (bearing == 90) {
					xDir = 1;
					yDir = 0;
				} else if (bearing == 180) {
					xDir = 0;
					yDir = 1;
				} else if (bearing == 270) {
					xDir = -1;
					yDir = 0;
				}
			} else if (i.operator == 'F') {
				currentX += xDir * i.amount;
				currentY += yDir * i.amount;
			} else if (i.operator == 'N') {
				currentY -= i.amount;
			} else if (i.operator == 'S') {
				currentY += i.amount;
			} else if (i.operator == 'E') {
				currentX += i.amount;
			} else if (i.operator == 'W') {
				currentX -= i.amount;
			}
		}

		return Math.abs(currentX) + Math.abs(currentY);
	}

	private long problemB(List<String> lines) {
		List<Ins> inss = new ArrayList<>();
		for (String line : lines) {
			int amount = Integer.valueOf(line.substring(1));
			Ins i = new Ins(line.charAt(0), amount);
			inss.add(i);
		}
		
		int waypointX = 10;
		int wayPointY = -1;
		int shipX = 0;
		int shipY = 0;
		
		for (Ins i : inss) {
			if (i.operator == 'R') {
				int bearing = i.amount;
				if (bearing < 0)
					bearing += 360;
				else if (bearing >= 360)
					bearing -= 360;
				
				int tempX = waypointX;
				int tempY = wayPointY;
				if (bearing == 90) {
					waypointX = -tempY;
					wayPointY = tempX;
				} else if (bearing == 180) {
					waypointX = -tempX;
					wayPointY = -tempY;
				} else if (bearing == 270) {
					waypointX = tempY;
					wayPointY = -tempX;
				}
			} else if (i.operator == 'F') {
				shipX += waypointX * i.amount;
				shipY += wayPointY * i.amount;
			} else if (i.operator == 'N') {
				wayPointY -= i.amount;
			} else if (i.operator == 'S') {
				wayPointY += i.amount;
			} else if (i.operator == 'E') {
				waypointX += i.amount;
			} else if (i.operator == 'W') {
				waypointX -= i.amount;
			}
		}
		
		return Math.abs(shipX) + Math.abs(shipY);
	}
}
