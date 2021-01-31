package test.clyde.aoc.aoc2017;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day11 {

	private static List<String> LINES = Utils.readFile("2017/Day11.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day10a.txt");

	private int most = 0;
	private int solveA(String directions) {
		Pattern pat = Pattern.compile("([a-z]+)");
		Matcher m = pat.matcher(directions);
		
		int nwse = 0;
		int nesw = 0;
		int ns = 0;
		while (m.find()) {
			String dir = m.group(1);
			if (dir.equals("n")) {
				ns -= 1;
			} else if (dir.equals("ne")) {
				nesw -= 1;
			} else if (dir.equals("nw")) {
				nwse -= 1;
			} else if (dir.equals("s")) {
				ns += 1;
			} else if (dir.equals("se")) {
				nwse += 1;
			} else if (dir.equals("sw")) {
				nesw += 1;
			}
			int stepCount = countSteps(nwse, nesw, ns);
			if (stepCount > most) {
				most = stepCount;
			}
		}
		
		return countSteps(nwse, nesw, ns);
	}
	
	private int countSteps(int nwse, int nesw, int ns) {
		while (nesw < 0 && ns > 0) {
			nesw++;
			ns--;
			nwse++;
		}
		while (nesw > 0 && ns < 0) {
			nesw--;
			ns++;
			nwse--;
		}
		while (nesw > 0 && nwse > 0) {
			nesw--;
			nwse--;
			ns++;
		}
		while (nesw < 0 && nwse < 0) {
			nesw++;
			nwse++;
			ns--;
		}
		while (nwse < 0 && ns > 0) {
			nwse++;
			ns--;
			nesw++;
		}
		while (nwse > 0 && ns < 0) {
			nwse--;
			ns++;
			nesw--;
		}
		return Math.abs(nesw)+Math.abs(nwse)+Math.abs(ns);
	}
	
	private long problemA(List<String> lines) {
		return solveA(lines.get(0));
	}	
		
	public static void main(String[] args) {
		System.out.println("Test: " + new Day11().solveA("ne,ne,ne"));
		System.out.println("Test: " + new Day11().solveA("ne,ne,sw,sw"));
		System.out.println("Test: " + new Day11().solveA("ne,ne,s,s"));
		System.out.println("Test: " + new Day11().solveA("se,sw,se,sw,sw"));
		Day11 d = new Day11();
		System.out.println("A: " + d.problemA(LINES));
		System.out.println("B: " + d.most);
	}

}
