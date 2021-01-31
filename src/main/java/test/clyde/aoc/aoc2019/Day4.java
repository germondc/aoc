package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.StringUtils;
import test.clyde.aoc.utils.Utils;

public class Day4 {
	private long problemA(String input) {
		String[] split = input.split("-");
		int value1 = Integer.valueOf(split[0]);
		int value2 = Integer.valueOf(split[1]);
		long count = 0;
		for (int i=value1; i<=value2; i++) {
			if (testPassword(Integer.toString(i)))
				count++;
		}
		return count;
	}
	
	private long problemB(String input) {
		String[] split = input.split("-");
		int value1 = Integer.valueOf(split[0]);
		int value2 = Integer.valueOf(split[1]);
		long count = 0;
		for (int i=value1; i<=value2; i++) {
			if (testPassword2(Integer.toString(i)))
				count++;
		}
		return count;
	}

	private boolean testPassword(String password) {
		boolean hasDouble = false;
		for (int i=0; i<password.length() -1; i++) {
			if (password.charAt(i)==password.charAt(i+1))
				hasDouble = true;
			if (password.charAt(i) > password.charAt(i+1))
				return false;
		}
		return hasDouble;
	}
	
	private boolean testPassword2(String password) {
		boolean hasDouble = false;
		for (int i=0; i<password.length() -1; i++) {
			if (password.charAt(i)==password.charAt(i+1) && StringUtils.charCount(password, password.charAt(i))==2)
				hasDouble = true;
			if (password.charAt(i) > password.charAt(i+1))
				return false;
		}
		return hasDouble;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day4().testPassword("111111"));
		System.out.println("TestA: " + new Day4().testPassword("223450"));
		System.out.println("TestA: " + new Day4().testPassword("123789"));
		System.out.println("A: " + new Day4().problemA("130254-678275"));
		System.out.println("TestB: " + new Day4().testPassword2("112233"));
		System.out.println("TestB: " + new Day4().testPassword2("123444"));
		System.out.println("TestB: " + new Day4().testPassword2("111122"));
		System.out.println("B: " + new Day4().problemB("130254-678275"));
	}

}
