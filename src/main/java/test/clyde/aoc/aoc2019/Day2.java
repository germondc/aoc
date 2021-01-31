package test.clyde.aoc.aoc2019;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day2 {
	private static List<String> LINES = Utils.readFile("2019/Day2.txt");

	private long problemA(List<String> lines) {
		return getValue(lines.get(0), 12, 2);
	}
	
	private long getValue(String line, int noun, int verb) {
		String[] split = line.split(",");
		//split = "1,9,10,3,2,3,11,0,99,30,40,50".split(",");
//		split = "1,1,1,4,99,5,6,0,99".split(",");
//		split = "2,4,4,5,99,0".split(",");
		split[1] = Integer.toString(noun);
		split[2] = Integer.toString(verb);
		
		int index = 0;
		while (!split[index].equals("99")) {
			if (split[index].equals("1")) {
				long value1 = Long.valueOf(split[Integer.valueOf(split[index+1])]);
				long value2 = Long.valueOf(split[Integer.valueOf(split[index+2])]);
				int address = Integer.valueOf(split[index+3]);
				split[address] = Long.toString(value1+value2);
			} else if (split[index].equals("2")) {
				long value1 = Long.valueOf(split[Integer.valueOf(split[index+1])]);
				long value2 = Long.valueOf(split[Integer.valueOf(split[index+2])]);
				int address = Integer.valueOf(split[index+3]);
				split[address] = Long.toString(value1*value2);
			}
			index+=4;
		}
		return Long.valueOf(split[0]);
	}

	private long problemB(List<String> lines) {
		String line = lines.get(0);
		for (int i=0; i<=99; i++) {
			for (int j=0; j<=99; j++) {
				if (getValue(line, i, j)==19690720) {
					return 100*i+j;
				}
			}
		}
		
		return 0;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day2().problemA(LINES));
		System.out.println("B: " + new Day2().problemB(LINES));
	}

}
