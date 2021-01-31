package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day7 {

	private static List<String> LINES = Utils.readFile("2016/Day7.txt");

	private int problemA() {
		int count = 0;
		
//		LINES = Arrays.asList(new String[] {
//			"abba[mnop]qrst",
//			"abcd[bddb]xyyx",
//			"aaaa[qwer]tyui",
//			"ioxxoj[asdfgh]zxcvbn"	
//		});
		
		for (String line : LINES) {
			if (isValid(line)) {
				count++;
			}
		}
		

		return count;
	}
	
	private boolean isValid(String line) {
		
		boolean inSquare = false;
		boolean haveGood = false;
		char[] chars = line.toCharArray();
		for (int i=0; i<chars.length-3; i++) {
			if (chars[i] == '[') {
				inSquare = true;
				continue;
			} else if (chars[i] == ']') {
				inSquare = false;
				continue;
			}
			

			if (chars[i] != chars[i+1]) {
				if (chars[i] == chars[i+3] && chars[i+1] == chars[i+2]) {
					if (inSquare) {
						return false;
					} else {
						haveGood = true;
					}
				}
			}

		}
		return haveGood;
	}
	
	private boolean isValidB(String line) {
		

		
		
		boolean inSquare = false;
		
		List<String> abas = new ArrayList<>();
		List<String> babs = new ArrayList<>();
		char[] chars = line.toCharArray();
		for (int i=0; i<chars.length-2; i++) {
			if (chars[i] == '[') {
				inSquare = true;
				continue;
			} else if (chars[i] == ']') {
				inSquare = false;
				continue;
			}
			
			if (chars[i] != chars[i+1]) {
				if (chars[i] == chars[i+2] && chars[i+1] != '[' && chars[i+1] != ']' ) {
					if (inSquare) {
						babs.add("" + chars[i] + chars[i+1] + chars[i+2]);
					} else {
						abas.add("" + chars[i] + chars[i+1] + chars[i+2]);
					}
				}
			}

		}
		
		for (String aba : abas) {
			String lookup = "" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1);
			if (babs.contains(lookup))
				return true;
		}
		
		return false;
	}

	private int problemB() {
//		LINES = Arrays.asList(new String[] {
//		"aba[bab]xyz",
//		"xyx[xyx]xyx",
//		"aaa[kek]eke",
//		"zazbz[bzb]cdb"	
//	});
		
		int count = 0;
		for (String line : LINES) {
			if (isValidB(line)) {
				count++;
			}
		}

		return count;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		System.out.println("A: " + new Day7().problemA());
		System.out.println("B: " + new Day7().problemB());
	}
}
