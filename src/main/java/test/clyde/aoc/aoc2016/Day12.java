package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import test.clyde.aoc.utils.Utils;

public class Day12 {

	private static List<String> LINES = Utils.readFile("2016/Day12.txt");

	private class Instruction {
		String name;
		String par1;
		String par2;
	}
	
	private int problemA() {
		List<Instruction> is = new ArrayList<>();
		for (String line : LINES) {
			String[] split = line.split(" ");
			Instruction i = new Instruction();
			i.name = split[0];
			i.par1 = split[1];
			if (split.length > 2) {
				i.par2 = split[2];
			}
			is.add(i);
		}
		
		Map<String , Integer> registers  =new HashMap<>();
		registers.put("a", 0);
		registers.put("b", 0);
		registers.put("c", 0);
		registers.put("d", 0);
		for (int i=0; i<is.size(); i++) {
			Instruction ins = is.get(i);
			if (ins.name.equals("cpy")) {
				int value;
				if (registers.containsKey(ins.par1)) {
					value = registers.get(ins.par1);
				} else {
					value = Integer.valueOf(ins.par1);
				}
				registers.put(ins.par2, value);
			} else if (ins.name.equals("inc")) {
				registers.put(ins.par1, registers.get(ins.par1)+1);
			} else if (ins.name.equals("dec")) {
				registers.put(ins.par1, registers.get(ins.par1)-1);
			} else if (ins.name.equals("jnz")) {
				int value;
				if (registers.containsKey(ins.par1)) {
					value = registers.get(ins.par1);
				} else {
					value = Integer.valueOf(ins.par1);
				}
				if (value != 0) {
					i += Integer.valueOf(ins.par2) - 1;
				}
			}
		}
		
		return registers.get("a");
	}

	private int problemB() {
		List<Instruction> is = new ArrayList<>();
		for (String line : LINES) {
			String[] split = line.split(" ");
			Instruction i = new Instruction();
			i.name = split[0];
			i.par1 = split[1];
			if (split.length > 2) {
				i.par2 = split[2];
			}
			is.add(i);
		}
		
		Map<String , Integer> registers  =new HashMap<>();
		registers.put("a", 0);
		registers.put("b", 0);
		registers.put("c", 1);
		registers.put("d", 0);
		for (int i=0; i<is.size(); i++) {
			Instruction ins = is.get(i);
			if (ins.name.equals("cpy")) {
				int value;
				if (registers.containsKey(ins.par1)) {
					value = registers.get(ins.par1);
				} else {
					value = Integer.valueOf(ins.par1);
				}
				registers.put(ins.par2, value);
			} else if (ins.name.equals("inc")) {
				registers.put(ins.par1, registers.get(ins.par1)+1);
			} else if (ins.name.equals("dec")) {
				registers.put(ins.par1, registers.get(ins.par1)-1);
			} else if (ins.name.equals("jnz")) {
				int value;
				if (registers.containsKey(ins.par1)) {
					value = registers.get(ins.par1);
				} else {
					value = Integer.valueOf(ins.par1);
				}
				if (value != 0) {
					i += Integer.valueOf(ins.par2) - 1;
				}
			}
		}
		
		return registers.get("a");
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		System.out.println("A: " + new Day12().problemA());
		System.out.println("B: " + new Day12().problemB());
	}
}
