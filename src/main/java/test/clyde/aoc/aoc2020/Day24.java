package test.clyde.aoc.aoc2020;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day24 {

	private static List<String> LINES = Utils.readFile("2020/day24.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/day24a.txt");

	public static void main(String[] args) throws IOException {
		new Day24().solve(TEST_LINES);
		new Day24().solve(LINES);
		new Day24().solveB(TEST_LINES);
		long start = System.currentTimeMillis();
		new Day24().solveB(LINES);
		System.out.println(System.currentTimeMillis() - start);
	}

	private void solve(List<String> lines) {
		Map<Tile, Boolean> tiles = new HashMap<>();
		for (String line : lines) {
			Tile t = new Tile(line);
			if (!tiles.containsKey(t)) {
				tiles.put(t, true);
			}
			tiles.put(t, !tiles.get(t));
		}

		long count = tiles.values().stream().filter(b -> !b).count();

		System.out.println("A: " + count);
	}

	private void solveB(List<String> lines) {
		Map<Tile, Boolean> tiles = new HashMap<>();
		for (String line : lines) {
			Tile t = new Tile(line);
			if (!tiles.containsKey(t)) {
				tiles.put(t, true);
			}
			tiles.put(t, !tiles.get(t));
		}

		for (int day = 0; day < 100; day++) {
			addAdj(tiles);
			Map<Tile, Boolean> next = new HashMap<>();
			for (Map.Entry<Tile, Boolean> tileEntry : tiles.entrySet()) {
				Tile tile = tileEntry.getKey();
				int countBlack = 0;
				List<Tile> adjTiles = getAdj(tile);
				for (Tile adj : adjTiles) {
					if (tiles.containsKey(adj) && !tiles.get(adj)) {
						countBlack++;
					}
				}
				if (tileEntry.getValue() && countBlack == 2) {
					next.put(tile, false);
				} else if (!tileEntry.getValue() && (countBlack == 0 || countBlack>2)) {
					next.put(tile, true);
				} else {
					next.put(tile, tileEntry.getValue());
				}
			}
			tiles = next;
		}

		long count = tiles.values().stream().filter(b -> !b).count();
		System.out.println("B: " + count);
	}
	
	private void addAdj(Map<Tile, Boolean> adj) {
		List<Tile> addTiles = new ArrayList<>();
		for (Tile t : adj.keySet()) {
			List<Tile> toAdd = getAdj(t);
			addTiles.addAll(toAdd);
		}
		
		for (Tile adjTile : addTiles) {
			if (!adj.containsKey(adjTile)) {
				adj.put(adjTile, true);
			}
		}
	}

	private List<Tile> getAdj(Tile t) {
		List<Tile> result = new ArrayList<>();
		Tile copy = t.copy();
		copy.ew += 1;
		copy.adjCo();
		result.add(copy);
		copy = t.copy();
		copy.ew -= 1;
		copy.adjCo();
		result.add(copy);

		copy = t.copy();
		copy.nesw += 1;
		copy.adjCo();
		result.add(copy);
		copy = t.copy();
		copy.nesw -= 1;
		copy.adjCo();
		result.add(copy);

		copy = t.copy();
		copy.nwse += 1;
		copy.adjCo();
		result.add(copy);
		copy = t.copy();
		copy.nwse -= 1;
		copy.adjCo();
		result.add(copy);

		return result;
	}

	private class Tile {
		int nwse = 0;
		int nesw = 0;
		int ew = 0;

		public Tile copy() {
			Tile copy = new Tile();
			copy.nwse = this.nwse;
			copy.nesw = this.nesw;
			copy.ew = this.ew;
			return copy;
		}

		public Tile() {

		}

		@Override
		public String toString() {
			return String.format("Tile [nwse=%s, nesw=%s, ew=%s]", nwse, nesw, ew);
		}
		
		public void adjCo() {
			while (nwse < 0 && ew > 0) {
				nwse++;
				ew--;
				nesw--;
			}
			while (nwse > 0 && ew < 0) {
				nwse--;
				ew++;
				nesw++;
			}
			while (nwse < 0 && nesw > 0) {
				nwse++;
				nesw--;
				ew--;
			}
			while (nwse > 0 && nesw < 0) {
				nwse--;
				nesw++;
				ew++;
			}
			while (nesw < 0 && ew < 0) {
				nesw++;
				ew++;
				nwse--;
			}
			while (nesw > 0 && ew > 0) {
				nesw--;
				ew--;
				nwse++;
			}
		}

		public Tile(String direction) {
			for (int i = 0; i < direction.length(); i++) {
				String dir = "" + direction.charAt(i);
				if (dir.equals("n") || dir.equals("s")) {
					i++;
					dir += direction.charAt(i);
				}
				if (dir.equals("w")) {
					ew -= 1;
				} else if (dir.equals("ne")) {
					nesw -= 1;
				} else if (dir.equals("nw")) {
					nwse -= 1;
				} else if (dir.equals("e")) {
					ew += 1;
				} else if (dir.equals("se")) {
					nwse += 1;
				} else if (dir.equals("sw")) {
					nesw += 1;
				}
			}
			adjCo();
			
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ew;
			result = prime * result + nesw;
			result = prime * result + nwse;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tile other = (Tile) obj;

			if (ew != other.ew)
				return false;
			if (nesw != other.nesw)
				return false;
			if (nwse != other.nwse)
				return false;
			return true;
		}

	}
}
