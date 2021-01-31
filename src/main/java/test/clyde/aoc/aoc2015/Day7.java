package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day7 {

	private static List<String> LINES = Utils.readFile("2015/Day7.txt");

	// Pattern PATTERN = Pattern.compile("^(?:(.*) )?(.*) (.*) -> (.*)$");
	Pattern PATTERN = Pattern.compile("^(?:\\b(\\w+)\\b )?(?:\\b(\\w+)\\b )?\\b(\\w+)\\b -> \\b(\\w+)\\b$");

	public static void main(String[] args) {
		new Day7().getValueA();	
	}

	private class Operation {
		String left;
		String right;
		String operator;
		String result;
	}

	private void getValueA() {

		Map<String, Operation> operations = new HashMap<>();

		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			// System.out.println(m.groupCount());
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			Operation o = new Operation();
			o.left = m.group(1);
			o.operator = m.group(2);
			if (o.left != null && o.operator == null) {
				o.operator = o.left;
				o.left = null;
			}
			o.right = m.group(3);
			o.result = m.group(4);

			operations.put(o.result, o);

		}

		Operation a = operations.get("a");
		Map<String, Integer> values = new HashMap<>();
		int firstValue = getNext(values, operations, a);
		System.out.println("A: " + firstValue);
		Operation b = operations.get("b");
		b.right = Integer.toString(firstValue);
		values = new HashMap<>();
		int second = getNext(values, operations, a);
		System.out.println("B: " + second);
	}

	private Integer getNext(Map<String, Integer> values, Map<String, Operation> operations, Operation operation) {
		if (operation == null) {
			return null;
		}
		
		Integer left = null;
		if (operation.left == null) {
			left = null;
		} else {
			if (operations.containsKey(operation.left)) {
				if (values.containsKey(operation.left)) {
					left = values.get(operation.left);
				} else {
					left = getNext(values, operations, operations.get(operation.left));
					values.put(operation.left, left);
				}
			} else {
				left = Integer.valueOf(operation.left);
			}
		}
		Integer right = null;
		if (operation.right == null) {
			right = null;
		} else {
			if (operations.containsKey(operation.right)) {
				if (values.containsKey(operation.right)) {
					right = values.get(operation.right);
				} else {
					right = getNext(values, operations, operations.get(operation.right));
					values.put(operation.right, right);
				}
			} else {
				right = Integer.valueOf(operation.right);
			}
		}

		if (operation.operator == null) {
			return right;
		} else if (operation.operator.equals("NOT")) {
			return 65535 - right;
		} else if (operation.operator.equals("AND")) {
			return left & right;
		} else if (operation.operator.equals("OR")) {
			return left | right;
		} else if (operation.operator.equals("RSHIFT")) {
			return left >> right;
		} else if (operation.operator.equals("LSHIFT")) {
			return left << right;
		} else {
			throw new RuntimeException("missing operator: " + operation.operator);
		}
	}
	
	
}
