package test.clyde.aoc.aoc2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day21 {
	private static List<String> LINES = Utils.readFile("2020/Day21.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/Day21a.txt");

	public static void main(String[] args) throws IOException {
		new Day21().solve(TEST_LINES);
		new Day21().solve(LINES);
	}

	private class Food {
		private List<String> ingredients = new ArrayList<>();
		private List<String> allergens = new ArrayList<>();
	}
	
	private List<Food> getFood(List<String> lines) {
		List<Food> allFood = new ArrayList<>();

		Pattern pat = Pattern.compile("([a-z]+)");
		for (String line : lines) {
			Food food = new Food();
			allFood.add(food);
			int index = line.indexOf("contains");
			Matcher m = pat.matcher(line);
			while (m.find()) {
				if (m.start() > index) {
					food.allergens.add(m.group(1));
				} else {
					if (!m.group(1).equals("contains"))
						food.ingredients.add(m.group(1));
				}
			}
		}
		return allFood;
	}

	public void solve(List<String> lines) {
		List<Food> allFood = getFood(lines);
		Map<String, String> result = new HashMap<>();
		
		while (allFood.stream().filter(f -> f.allergens.size() != 0).count() > 0) {
			for (Food food : allFood) {
				if (food.ingredients.size() == 1 && food.allergens.size() == 1) {
					String ingredient = food.ingredients.get(0);
					String aller = food.allergens.get(0);
					result.put(aller, ingredient);
					for (Food food1 : allFood) {
						food1.ingredients.remove(ingredient);
						food1.allergens.remove(aller);
					}
				}
			}

			main: for (Food food : allFood) {
				for (String allergen : food.allergens) {
					List<String> commonIng = getCommonIngre(getFoodsWithAllergen(allFood, allergen));
					if (commonIng.size() == 1) {
							result.put(allergen, commonIng.get(0));
							for (Food f : allFood) {
								f.ingredients.remove(commonIng.get(0));
								f.allergens.remove(allergen);
							}
							break main;
					}
				}
			}
		}
		
		Map<String, String> sortedMap = new TreeMap<String, String>(result);
		
		int appear = allFood.stream().map(f -> f.ingredients.size()).mapToInt(i -> i).sum();
		System.out.println("A: " + appear);
		
		String list = sortedMap.values().stream().collect(Collectors.joining(","));
		System.out.println("B: " + list);
	}

	private List<Food> getFoodsWithAllergen(List<Food> allFood, String allergen) {
		return allFood.stream().filter(f -> f.allergens.contains(allergen)).collect(Collectors.toList());
	}

	private List<String> getCommonIngre(List<Food> foods) {
		List<String> result = new ArrayList<>();
		
		for (Food food1 : foods) {
			for (String i : food1.ingredients) {
				boolean all = true;
				for (Food food2 : foods) {
					if (!food2.ingredients.contains(i)) {
						all = false;
						break;
					}
				}
				if (all && !result.contains(i)) {
					result.add(i);
				}
			}
		}
		return result;
	}
}
