package test.clyde.aoc.aoc2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day2 {
	private static List<String> LINES = Utils.readFile("2016/Day2.txt");
	
	private int problemA() {
		
		Map<Integer, Map<Character, Integer>> moves = populate();
		int at = 5;
		String result = "";
		for (String line : LINES) {
			for (char c : line.toCharArray()) {
				at = moves.get(at).get(c);
			}
			result += at;
		}
		return Integer.valueOf(result);
	}
	
/**
 * @return
 */
private String problemB() {
		
		Map<Character, Map<Character, Character>> moves = populateB();
		char at = '5';
		String result = "";
		for (String line : LINES) {
			for (char c : line.toCharArray()) {
				at = moves.get(at).get(c);
			}
			result += at;
		}
		return result;
	}
	
	private Map<Integer, Map<Character, Integer>> populate() {
		Map<Integer, Map<Character, Integer>> result = new HashMap<>();
		Map<Character, Integer> moves = new HashMap<>();
		moves.put('U', 1);
		moves.put('D', 4);
		moves.put('L', 1);
		moves.put('R', 2);
		result.put(1, moves);
		
		moves = new HashMap<>();
		moves.put('U', 2);
		moves.put('D', 5);
		moves.put('L', 1);
		moves.put('R', 3);
		result.put(2, moves);
		
		moves = new HashMap<>();
		moves.put('U', 3);
		moves.put('D', 6);
		moves.put('L', 2);
		moves.put('R', 3);
		result.put(3, moves);
		
		moves = new HashMap<>();
		moves.put('U', 1);
		moves.put('D', 7);
		moves.put('L', 4);
		moves.put('R', 5);
		result.put(4, moves);
		
		moves = new HashMap<>();
		moves.put('U', 2);
		moves.put('D', 8);
		moves.put('L', 4);
		moves.put('R', 6);
		result.put(5, moves);
		
		moves = new HashMap<>();
		moves.put('U', 3);
		moves.put('D', 9);
		moves.put('L', 5);
		moves.put('R', 6);
		result.put(6, moves);
		
		moves = new HashMap<>();
		moves.put('U', 4);
		moves.put('D', 7);
		moves.put('L', 7);
		moves.put('R', 8);
		result.put(7, moves);
		
		moves = new HashMap<>();
		moves.put('U', 5);
		moves.put('D', 8);
		moves.put('L', 7);
		moves.put('R', 9);
		result.put(8, moves);
		
		moves = new HashMap<>();
		moves.put('U', 6);
		moves.put('D', 9);
		moves.put('L', 8);
		moves.put('R', 9);
		result.put(9, moves);
		
		return result;
	}
	
	private Map<Character, Map<Character, Character>> populateB() {
		Map<Character, Map<Character, Character>> result = new HashMap<>();
		Map<Character, Character> moves = new HashMap<>();
		moves.put('U', '5');
		moves.put('D', '5');
		moves.put('L', '5');
		moves.put('R', '6');
		result.put('5', moves);
		
		moves = new HashMap<>();
		moves.put('U', '2');
		moves.put('D', '6');
		moves.put('L', '2');
		moves.put('R', '3');
		result.put('2', moves);
		
		moves = new HashMap<>();
		moves.put('U', '2');
		moves.put('D', 'A');
		moves.put('L', '5');
		moves.put('R', '7');
		result.put('6', moves);
		
		moves = new HashMap<>();
		moves.put('U', '6');
		moves.put('D', 'A');
		moves.put('L', 'A');
		moves.put('R', 'B');
		result.put('A', moves);
		
		moves = new HashMap<>();
		moves.put('U', '1');
		moves.put('D', '3');
		moves.put('L', '1');
		moves.put('R', '1');
		result.put('1', moves);
		
		moves = new HashMap<>();
		moves.put('U', '1');
		moves.put('D', '7');
		moves.put('L', '2');
		moves.put('R', '4');
		result.put('3', moves);
		
		moves = new HashMap<>();
		moves.put('U', '3');
		moves.put('D', 'B');
		moves.put('L', '6');
		moves.put('R', '8');
		result.put('7', moves);
		
		moves = new HashMap<>();
		moves.put('U', '7');
		moves.put('D', 'D');
		moves.put('L', 'A');
		moves.put('R', 'C');
		result.put('B', moves);
		
		moves = new HashMap<>();
		moves.put('U', 'B');
		moves.put('D', 'D');
		moves.put('L', 'D');
		moves.put('R', 'D');
		result.put('D', moves);
		
		moves = new HashMap<>();
		moves.put('U', '4');
		moves.put('D', '8');
		moves.put('L', '3');
		moves.put('R', '4');
		result.put('4', moves);
		
		moves = new HashMap<>();
		moves.put('U', '4');
		moves.put('D', 'C');
		moves.put('L', '7');
		moves.put('R', '9');
		result.put('8', moves);
		
		moves = new HashMap<>();
		moves.put('U', '8');
		moves.put('D', 'C');
		moves.put('L', 'B');
		moves.put('R', 'C');
		result.put('C', moves);
		
		moves = new HashMap<>();
		moves.put('U', '9');
		moves.put('D', '9');
		moves.put('L', '8');
		moves.put('R', '9');
		result.put('9', moves);
		
		return result;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day2().problemA());
		System.out.println("B: " + new Day2().problemB());		
	}
}
