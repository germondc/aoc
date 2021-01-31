package test.clyde.aoc.aoc2015;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day2 {
	private static List<String> LINES = Utils.readFile("2015/Day2.txt");
	
	public static void main(String[] args) {
		System.out.println(new Day2().problemA());
		System.out.println(new Day2().problemB());
	}
	
	private long problemA() {
		long result = 0;
		for (String line : LINES) {
			String[] split = line.split("x");
			int l = Integer.valueOf(split[0]);
			int w = Integer.valueOf(split[1]);
			int h = Integer.valueOf(split[2]);
			int side1 = l*w;
			int side2 = w*h;
			int side3 = h*l;
			int paper = 2*(side1+side2+side3) + min(side1,side2,side3);
			result += paper;
		}
		return result;
	}
	
	private long problemB() {
		long result = 0;
		for (String line : LINES) {
			String[] split = line.split("x");
			int l = Integer.valueOf(split[0]);
			int w = Integer.valueOf(split[1]);
			int h = Integer.valueOf(split[2]);
			int ribbon = ((l+w+h - max(l,w,h)) * 2) + l*w*h;
			result += ribbon;
		}
		return result;
	}
	
	private int min(int v1, int v2, int v3) {
		if (v1 <= v2 && v1 <= v3)
			return v1;
		else if (v2 <= v1 && v2 <= v3) {
			return v2;
		} else {
			return v3;
		}
	}
	
	private int max(int v1, int v2, int v3) {
		if (v1 >= v2 && v1 >= v3)
			return v1;
		else if (v2 >= v1 && v2 >= v3) {
			return v2;
		} else {
			return v3;
		}
	}
}
