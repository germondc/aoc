package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day15 {
	
	private static List<String> LINES = Utils.readFile("2016/Day15.txt");

	private class Disc {
		int number;
		int positions;
		int startPos;

		public Disc(int number, int positions, int startPos) {
			this.number = number;
			this.positions = positions;
			this.startPos = startPos;
		}
		
		public boolean isGood(int time) {
			return (time + number + startPos) % positions == 0;
		}

		@Override
		public String toString() {
			return String.format("Disc [number=%s, positions=%s, startPos=%s]", number, positions, startPos);
		}
	}

	private int problemA(List<String> lines) throws NoSuchAlgorithmException {
		List<Disc> discs = getInput(lines);
//		discs = new ArrayList<>();
//		discs.add(new Disc(1, 5, 4));
//		discs.add(new Disc(2, 2, 1));
//		
//		for (int i=0; i<6; i++) {
//			for (Disc disc : discs) {
//				System.out.println(i + " - disc: " + disc.number + " : " + disc.isGood(i));
//			}
//		}
		
		boolean allGood = false;
		int time = 0;
		while (!allGood) {
			allGood = true;
			for (Disc disc : discs) {
				if (!disc.isGood(time)) {
					allGood = false;
					break;
				}
			}
			time++;
		}
		
		return time-1;
	}

	private int problemB(List<String> lines) throws NoSuchAlgorithmException {
		List<Disc> discs = getInput(lines);
		discs.add(new Disc(discs.size()+1, 11, 0));

		boolean allGood = false;
		int time = 0;
		while (!allGood) {
			allGood = true;
			for (Disc disc : discs) {
				if (!disc.isGood(time)) {
					allGood = false;
					break;
				}
			}
			time++;
		}
		
		return time-1;
	}

	private List<Disc> getInput(List<String> lines) {
		Pattern pattern = Pattern
				.compile("Disc #([0-9]+) has ([0-9]+) positions; at time=0, it is at position ([0-9]+).");

		List<Disc> discs = new ArrayList<>();
		for (String line : lines) {
			Matcher m = pattern.matcher(line);
			if (m.find()) {
				discs.add(new Disc(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)),
						Integer.valueOf(m.group(3))));
			}
		}
		return discs;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println("A: " + new Day15().problemA(LINES));
		System.out.println("B: " + new Day15().problemB(LINES));
	}
}
