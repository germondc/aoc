package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day8 {

	private static List<String> LINES = Utils.readFile("2016/Day8.txt");

	private int SCREEN_ROWS=6;
	private int SCREEN_COLS=50;
	
	private interface Instruction {
		public void operation(boolean[][] screen);
	}

	private class Rect implements Instruction {
		int a;
		int b;

		@Override
		public void operation(boolean[][] screen) {
			for (int row = 0; row < b; row++) {
				for (int col = 0; col < a; col++) {
					screen[row][col] = true;
				}
			}
		}
	}

	private class Rotate implements Instruction {
		boolean isRow;

		int a;
		int b;

		@Override
		public void operation(boolean[][] screen) {
			if (isRow) {
				boolean[] newRow = new boolean[SCREEN_COLS];
				for (int col=0; col<SCREEN_COLS; col++) {
					newRow[col] = screen[a][ (col < b ? SCREEN_COLS : 0) + (col - b)];
				}
				for (int col=0; col<SCREEN_COLS; col++) {
					screen[a][col] = newRow[col];
				}
			} else {
				boolean[] newCol = new boolean[SCREEN_ROWS];
				for (int row = 0; row < SCREEN_ROWS; row++) {
					newCol[row] = screen[ (row < b ? SCREEN_ROWS : 0) + (row - b)][a];
				}
				for (int row = 0; row < SCREEN_ROWS; row++) {
					screen[row][a] = newCol[row];
				}
			}
		}
	}

	private int problemA() {
		int count = 0;
		
		//rotate row y=0 by 3
		Pattern ROT_PATTERN = Pattern.compile("rotate (.*) .=([0-9]+) by ([0-9]+)");
		//rect 14x1
		Pattern REC_PATTERN = Pattern.compile("rect ([0-9]+)x([0-9]+)");

//		SCREEN_COLS = 7;
//		SCREEN_ROWS = 3;
//		LINES = Arrays.asList(new String[] { "rect 3x2", "rotate column x=1 by 1", "rotate row y=0 by 4", "rotate column x=1 by 1"   });
//		
		boolean[][] screen = new boolean[SCREEN_ROWS][SCREEN_COLS];
		
		List<Instruction> instructions = new ArrayList<>();
		for (String line : LINES) {
			if (line.startsWith("rotate")) {
				Matcher m = ROT_PATTERN.matcher(line);
				if (!m.find()) {
					System.out.println("failed to parse line: " + line);
					continue;
				}
				String rowCol = m.group(1);
				Rotate rotate = new Rotate();
				rotate.isRow = rowCol.equals("row");
				rotate.a = Integer.valueOf(m.group(2));
				rotate.b = Integer.valueOf(m.group(3));
				instructions.add(rotate);
			} else if (line.startsWith("rect")) {
				Matcher m = REC_PATTERN.matcher(line);
				if (!m.find()) {
					System.out.println("failed to parse line: " + line);
					continue;
				}
				Rect rect = new Rect();
				rect.a = Integer.valueOf(m.group(1));
				rect.b = Integer.valueOf(m.group(2));
				instructions.add(rect);
			} else {
				System.out.println("??, " + line);
			}
		}
		
		for (Instruction i : instructions) {
			i.operation(screen);
		}
		
		for (int row=0; row<SCREEN_ROWS; row++) {
			for (int col=0; col<SCREEN_COLS; col++) {
				if (screen[row][col]) {
					System.out.print("#");
					count++;
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();

		return count;
	}

	private int problemB() {

		int count = 0;

		return count;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		System.out.println("A: " + new Day8().problemA());
		System.out.println("B: " + "not coding this, read ascii above!");
	}
}
