package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day20a {

	private static List<String> LINES = Utils.readFile("2020/day20.txt");
	private static List<String> TESTA = Utils.readFile("2020/day20a.txt");
	private static List<String> MONSTER = Utils.readFile("2020/day20_monster.txt");

	private long problemA(List<String> lines) {
		List<Tile> tiles = getTiles(lines);
		System.out.println(tiles.get(0).toString());
		return 0;
	}

	private long problemB(List<String> lines) {
		boolean[][] monster = getMonsterGrid();
		return 0;
	}

	private class Tile {
		int id;
		boolean[][] pixels = null;
		int currentLine = 0;

		public void addLine(String line) {
			if (pixels == null)
				pixels = new boolean[line.length()][line.length()];
			int x = 0;
			for (char c : line.toCharArray()) {
				pixels[currentLine][x] = c == '#';
				x++;
			}
			currentLine++;
		}

		@Override
		public String toString() {
			return String.format("%s: %s", id, Arrays.deepToString(pixels));
		}
		
		
	}

	private List<Tile> getTiles(List<String> lines) {
		List<Tile> result = new ArrayList<>();
		Tile tile = null;
		for (String line : lines) {
			if (line.startsWith("Tile")) {
				tile = new Tile();
				result.add(tile);
				tile.id = Integer.valueOf(line.split(" ")[1].replace(":", ""));
			} else if (line.length() == 0) {
				continue;
			} else {
				tile.addLine(line);
			}
		}
		return result;
	}

	private boolean[][] getMonsterGrid() {
		boolean[][] result = new boolean[MONSTER.size()][MONSTER.get(0).length()];
		int y = 0;
		for (String line : MONSTER) {
			int x = 0;
			for (char c : line.toCharArray()) {
				result[y][x] = c == '#';
				x++;
			}
			y++;
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day20a().problemA(TESTA));
		System.out.println("A: " + new Day20a().problemA(LINES));
		System.out.println("TestB: " + new Day20a().problemB(TESTA));
		System.out.println("B: " + new Day20a().problemB(LINES));
	}
}
