package test.clyde.aoc.aoc2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day15 {
	private static List<String> LINES = Utils.readFile("2015/Day15.txt");

	Pattern PATTERN = Pattern.compile("(.*): capacity (.*), durability (.*), flavor (.*), texture (.*), calories (.*)");

	private class Ingredient {
		String name;
		int cap;
		int dur;
		int flav;
		int text;
		int cal;

	}

	private long test(boolean incCal) {
		int[][] matrix = new int[4][5];
		Map<String, Ingredient> ingredients = new HashMap<>();
		int index = 0;
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			Ingredient ing = new Ingredient();
			ing.name = m.group(1);
			ing.cap = Integer.valueOf(m.group(2));
			ing.dur = Integer.valueOf(m.group(3));
			ing.flav = Integer.valueOf(m.group(4));
			ing.text = Integer.valueOf(m.group(5));
			ing.cal = Integer.valueOf(m.group(6));
			ingredients.put(ing.name, ing);
//			matrix[index] = new int[5];
			matrix[index][0] = ing.cap;
			matrix[index][1] = ing.dur;
			matrix[index][2] = ing.flav;
			matrix[index][3] = ing.text;
			matrix[index][4] = ing.cal;
			index++;
		}

		long maxScore = 0;
		for (int sprinkles = 0; sprinkles <= 100; sprinkles++) {
			for (int butterscotch = 0; butterscotch <= 100; butterscotch++) {
				for (int chocolate = 0; chocolate <= 100; chocolate++) {
					for (int candy = 0; candy <= 100; candy++) {
						
						if (!(sprinkles+butterscotch+chocolate+candy==100))
							continue;

						int capTotal = sprinkles * matrix[0][0] + butterscotch * matrix[1][0] + chocolate * matrix[2][0]
								+ candy * matrix[3][0];
						if (capTotal <= 0)
							continue;
						int durTotal = sprinkles * matrix[0][1] + butterscotch * matrix[1][1] + chocolate * matrix[2][1]
								+ candy * matrix[3][1];
						if (durTotal <= 0)
							continue;
						int flavTotal = sprinkles * matrix[0][2] + butterscotch * matrix[1][2]
								+ chocolate * matrix[2][2] + candy * matrix[3][2];
						if (flavTotal <= 0)
							continue;
						int textTotal = sprinkles * matrix[0][3] + butterscotch * matrix[1][3]
								+ chocolate * matrix[2][3] + candy * matrix[3][3];
						if (textTotal <= 0)
							continue;
						
						int calTotal = sprinkles * matrix[0][4] + butterscotch * matrix[1][4]
								+ chocolate * matrix[2][4] + candy * matrix[3][4];
						if (incCal && calTotal != 500)
							continue;

						long score = capTotal * durTotal * flavTotal * textTotal;
						if (score > maxScore)
							maxScore = score;
					}

				}
			}

		}
		return maxScore;
	}

	public static void main(String[] argv) {
		System.out.println("A: " + new Day15().test(false));
		System.out.println("B: " + new Day15().test(true));
	}
}
