package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day6 {
	private static List<String> LINES = Utils.readFile("2015/Day6.txt");
	
	Pattern PATTERN = Pattern.compile("^(.*) (\\d*),(\\d*) through (\\d*),(\\d*)$");
	
	public static void main(String[] args) {
		System.out.println("A: " + new Day6().getOnLights());
		System.out.println("B: " + new Day6().getBrightness());
	}
	
	private class Instruction {
		int o;
		
		int startX;
		int startY;
		
		int endX;
		int endY;
	}
	
	private int getOnLights() {
		
		boolean[][] lights = new boolean[1000][1000];
		
		for (Instruction i : generateInstructions()) {
			for (int x=i.startX; x<=i.endX; x++) {
				for (int y=i.startY; y<=i.endY; y++) {
					if (i.o == 0)
						lights[x][y] = false;
					else if (i.o == 1)
						lights[x][y] = true;
					else if (i.o == 2)
						lights[x][y] = !lights[x][y];
				}
			}	
		}
		
		int count = 0;
		for (int x=0; x<1000; x++) {
			for (int y=0; y<1000; y++) {
				if (lights[x][y])
					count++;
			}
		}
		return count;
	}
	
private int getBrightness() {
		
		int[][] lights = new int[1000][1000];
		
		for (Instruction i : generateInstructions()) {
			for (int x=i.startX; x<=i.endX; x++) {
				for (int y=i.startY; y<=i.endY; y++) {
					if (i.o == 0) {
						lights[x][y] -= 1;
						if (lights[x][y] < 0)
							lights[x][y] = 0;
					}
					else if (i.o == 1)
						lights[x][y] += 1;
					else if (i.o == 2)
						lights[x][y] += 2;
				}
			}	
		}
		
		int count = 0;
		for (int x=0; x<1000; x++) {
			for (int y=0; y<1000; y++) {
				count += lights[x][y];
			}
		}
		return count;
	}
	
	private List<Instruction> generateInstructions() {
		List<Instruction> result = new ArrayList<>();
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			String command = m.group(1);
			int startX = Integer.valueOf(m.group(2));
			int startY = Integer.valueOf(m.group(3));
			int endX = Integer.valueOf(m.group(4));
			int endY = Integer.valueOf(m.group(5));
			Instruction i = new Instruction();
			if (command.equals("turn off")) {
				i.o = 0;
			} else if (command.equals("turn on")) {
				i.o = 1;
			} else if (command.equals("toggle")) {
				i.o = 2;
			}
			i.startX = startX;
			i.startY = startY;
			i.endX = endX;
			i.endY = endY;
			result.add(i);
		}
		return result;
	}
}
