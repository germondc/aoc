package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day20 {

	private static List<String> LINES = Utils.readFile("2020/Day20.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/Day20a.txt");

	public static void main(String[] args) {
//		System.out.println("TestA: " + new Day20().problemA(TEST_LINES));
		System.out.println("B: " + new Day20().problemA(LINES));
//		System.out.println("B: " + new Day20().problemB(LINES));
	}

	private class Edge implements Comparable<Edge> {
		int tileId;
		String pos;
		int hash;
		boolean flipped;
		int hashCount;

		public Edge(int tileId, String pos, int hash, boolean flipped) {
			super();
			this.tileId = tileId;
			this.pos = pos;
			this.hash = hash;
			this.flipped = flipped;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + hash;
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
			Edge other = (Edge) obj;

			if (hash != other.hash)
				return false;
			return true;
		}

		@Override
		public int compareTo(Edge o) {
			return hash - o.hash;
		}

		@Override
		public String toString() {
			return String.format("Edge [tileId=%s, pos=%s, hash=%s, flipped=%s, hashCount=%s]", tileId, pos, hash,
					flipped, hashCount);
		}

		public Edge copy() {
			Edge copy = new Edge(tileId, pos, hash, flipped);
			copy.hashCount = hashCount;
			return copy;
		}

	}

	private class Tile implements Cloneable {
		int id;
		boolean[][] pixels = new boolean[10][10];
		List<Edge> edges;

		int rowIndex = 0;
		int colIndex = 0;

		public void addPixel(boolean value) {
			pixels[rowIndex][colIndex] = value;
			colIndex++;
			if (colIndex >= 10) {
				colIndex = 0;
				rowIndex++;
			}
		}

		private boolean[] getTop() {
			boolean[] result = new boolean[10];
			for (int i = 0; i < 10; i++) {
				result[i] = pixels[0][i];
			}
			return result;
		}

		private boolean[] getBottom() {
			boolean[] result = new boolean[10];
			for (int i = 0; i < 10; i++) {
				result[i] = pixels[9][i];
			}
			return result;
		}

		private boolean[] getLeft() {
			boolean[] result = new boolean[10];
			for (int i = 0; i < 10; i++) {
				result[i] = pixels[i][0];
			}
			return result;
		}

		private boolean[] getRight() {
			boolean[] result = new boolean[10];
			for (int i = 0; i < 10; i++) {
				result[i] = pixels[i][9];
			}
			return result;
		}

		private int getHash(boolean[] edge, boolean flip) {
			int result = 0;
			if (flip) {
				for (int i = 0; i < 10; i++) {
					if (edge[i])
						result |= (1 << i);
				}
			} else {
				for (int i = 0; i < 10; i++) {
					if (edge[i])
						result |= (1 << (9 - i));
				}
			}

			return result;
		}

		public int getHash(String pos) {
			if (pos.equals("top")) {
				return getHash(getTop(), true);
			} else if (pos.equals("bottom")) {
				return getHash(getBottom(), true);
			} else if (pos.equals("left")) {
				return getHash(getLeft(), true);
			} else if (pos.equals("right")) {
				return getHash(getRight(), true);
			}
			return 0;
		}

		private void popE() {
			List<Edge> result = new ArrayList<>();
			result.add(new Edge(id, "top", getHash(getTop(), false), true));
			result.add(new Edge(id, "bottom", getHash(getBottom(), false), false));
			result.add(new Edge(id, "left", getHash(getLeft(), false), true));
			result.add(new Edge(id, "right", getHash(getRight(), false), true));
			result.add(new Edge(id, "top", getHash(getTop(), true), false));
			result.add(new Edge(id, "bottom", getHash(getBottom(), true), true));
			result.add(new Edge(id, "left", getHash(getLeft(), true), false));
			result.add(new Edge(id, "right", getHash(getRight(), true), false));
			edges = result;
		}

		public List<Edge> getEdges(boolean redo) {
			if (edges == null || redo)
				popE();
			return edges;
		}

		@Override
		public String toString() {
			return String.format("Tile [id=%s]", id);
		}

		public Tile copy() {
			Tile copy = new Tile();
			copy.id = id;
			copy.edges = new ArrayList<>();
			for (Edge e : edges) {
				copy.edges.add(e.copy());
			}

			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < 10; i++) {
					copy.pixels[j][i] = pixels[j][i];
				}
			}

			return copy;
		}
	}

	private long problemA(List<String> lines) {
		Tile[][] ta = getTA(lines);
		int size = ta.length;

		boolean[][] image = new boolean[size * 8][size * 8];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				Tile t = ta[row][col];
				for (int i = 1; i < 9; i++) {
					for (int j = 1; j < 9; j++) {
						image[row * 8 + (i - 1)][col * 8 + (j - 1)] = t.pixels[i][j];
					}
				}
			}
		}

		int countHash = 0;
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image.length; j++) {
				if (image[i][j])
					countHash++;
			}
		}

		boolean[][] newImage = image;
		newImage = rotateImage(newImage);
		newImage = flipImage(newImage, true);

		for (int i = 0; i < 2; i++) {
			newImage = image;
			if (i == 1)
				newImage = flipImage(newImage, true);
			if (i == 2)
				newImage = flipImage(newImage, false);
			for (int rotate = 0; rotate < 4; rotate++) {
				newImage = rotateImage(newImage);
				int monsters = searchMonster(newImage) * 15;
				if (monsters > 0)
					return countHash - monsters;
			}
		}

		return 0;
	}

	private boolean[][] rotateImage(boolean[][] image) {
		int size = image.length;
		boolean[][] newPixels = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newPixels[j][i] = image[size - i - 1][j];
			}
		}
		return newPixels;
	}

	private boolean[][] flipImage(boolean[][] image, boolean dir) {
		int size = image.length;
		boolean[][] newPixels = new boolean[size][size];
		if (dir) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					newPixels[j][i] = image[j][size - i - 1];
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					newPixels[j][i] = image[size - j - 1][i];
				}
			}
		}
		return newPixels;
	}

	private Tile[][] getTA(List<String> lines) {
		List<Tile> tilesOrig = new ArrayList<>();
		Tile currentTile = null;
		for (String line : lines) {
			if (line.contains("Tile")) {
				if (currentTile != null)
					tilesOrig.add(currentTile);
				String tileName = line.substring(line.indexOf(' ') + 1, line.length() - 1);
				currentTile = new Tile();
				currentTile.id = Integer.valueOf(tileName);
				continue;
			} else if (line.length() == 0) {
				continue;
			} else {
				for (char c : line.toCharArray()) {
					if (c == '.') {
						currentTile.addPixel(false);
					} else {
						currentTile.addPixel(true);
					}
				}

			}
		}
		tilesOrig.add(currentTile);

		List<Edge> allEdges = tilesOrig.stream().flatMap(t -> t.getEdges(false).stream()).sorted()
				.collect(Collectors.toList());
		Map<Integer, Integer> hashes = new HashMap<>();
		for (Edge edge : allEdges) {
			if (!hashes.containsKey(edge.hash)) {
				hashes.put(edge.hash, 0);
			}
			hashes.put(edge.hash, hashes.get(edge.hash) + 1);

		}

		allEdges.stream().forEach(e -> e.hashCount = hashes.get(e.hash));

		List<Tile> corners = tilesOrig.stream().filter(t -> t.edges.stream().filter(e -> e.hashCount == 1).count() == 4)
				.collect(Collectors.toList());

		long resultA = 1;

		for (Tile t : corners) {
			resultA *= t.id;
		}
		System.out.println("A: " + resultA);

		int size = (int) Math.sqrt(tilesOrig.size());
		int startId = corners.get(0).id;

		for (int j = 0; j < 3; j++) {
			List<Tile> tiles = new ArrayList<>();
			for (Tile t : tilesOrig) {
				tiles.add(t.copy());
			}
			Tile[][] tileArray = new Tile[size][size];
			Tile start = tiles.stream().filter(t -> t.id == startId).findFirst().get();

			if (j == 1)
				flip(start, "top");
			if (j == 2) {
				flip(start, "top");
				flip(start, "left");
			}
			if (!roStart(start))
				continue;

			try {

				tileArray[0][0] = start;
				Tile tileNext = start;
				for (int row = 0; row < size; row++) {
					for (int col = 0; col < size; col++) {
						if (row == 0 && col == 0)
							continue;
						Tile above;
						Tile left;
						if (row == 0)
							above = null;
						else
							above = tileArray[row - 1][col];
						if (col == 0)
							left = null;
						else
							left = tileArray[row][col - 1];

						tileNext = findNextTile(tiles, above, left);

						tileArray[row][col] = tileNext;
					}
				}
				return tileArray;
			} catch (Exception e) {
				System.out.println("dead");
			}
		}

		return null;
	}

	private boolean roStart(Tile start) {
		for (int i = 0; i < 4; i++) {
			boolean good = true;
			List<Edge> edges = start.edges.stream().filter(e -> e.hashCount == 1).collect(Collectors.toList());
			for (Edge edge : edges) {
				if (!edge.pos.equals("top") && !edge.pos.equals("left")) {
					good = false;
					break;
				}
			}
			if (good) {
				return true;
			}
			rotate(start);
		}
		return false;
	}

	private Tile findNextTile(List<Tile> tiles, Tile tileAbove, Tile tileLeft) throws Exception {
		int valueAbove = -1;
		if (tileAbove != null)
			valueAbove = tileAbove.getHash("bottom");
		int valueLeft = -1;
		if (tileLeft != null)
			valueLeft = tileLeft.getHash("right");

		final int valuea = valueLeft;
		final int valueb = valueAbove;
		for (Tile t : tiles) {
			if (t == tileLeft || t == tileAbove)
				continue;

			Edge edgea = t.edges.stream()
					.filter(e -> (valuea != -1 && e.hash == valuea) || (valuea == -1 && e.hashCount == 1)).findFirst()
					.orElse(null);
			Edge edgeb = t.edges.stream()
					.filter(e -> (valueb != -1 && e.hash == valueb) || (valueb == -1 && e.hashCount == 1)).findFirst()
					.orElse(null);

			if (edgea != null && edgeb != null) {
				adjust(t, valuea, valueb);
				return t;
			}

		}
		throw new Exception();
	}

	private void adjust(Tile tile, int left, int top) throws Exception {
		List<Integer> poss = tile.edges.stream().filter(e -> e.hashCount == 1).map(e -> e.hash)
				.collect(Collectors.toList());
		for (int i = 0; i < 3; i++) {
			if (i == 1)
				flip(tile, "top");
			if (i == 2) {
				flip(tile, "top");
				flip(tile, "left");
			}
			for (int rotate = 0; rotate < 4; rotate++) {
				rotate(tile);
				if ((top != -1 && tile.getHash("top") == top || top == -1 && poss.contains(tile.getHash("top")))
						&& (left != -1 && tile.getHash("left") == left
								|| left == -1 && poss.contains(tile.getHash("left"))))
					return;
			}
		}
		throw new Exception("none");
	}

	private void flip(Tile tile, String pos) {

		int size = tile.pixels.length;

		if (pos.equals("top") || pos.equals("bottom")) {
			boolean[][] newPixels = new boolean[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					newPixels[j][i] = tile.pixels[j][size - i - 1];
				}
			}
			tile.pixels = newPixels;
			for (Edge e : tile.edges) {
				if (e.pos.equals("left")) {
					e.pos = "right";
					e.flipped = !e.flipped;
				} else if (e.pos.equals("right")) {
					e.pos = "left";
					e.flipped = !e.flipped;
				}
			}
		} else {
			boolean[][] newPixels = new boolean[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					newPixels[j][i] = tile.pixels[size - j - 1][i];
				}
			}
			tile.pixels = newPixels;
			for (Edge e : tile.edges) {
				if (e.pos.equals("top")) {
					e.pos = "bottom";
					e.flipped = !e.flipped;
				} else if (e.pos.equals("bottom")) {
					e.pos = "top";
					e.flipped = !e.flipped;
				}
			}
		}
	}

	private void rotate(Tile tile) {
		int size = tile.pixels.length;
		boolean[][] newPixels = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newPixels[j][i] = tile.pixels[size - i - 1][j];
			}
		}
		tile.pixels = newPixels;
		for (Edge e : tile.edges) {
			if (e.pos.equals("left"))
				e.pos = "top";
			else if (e.pos.equals("top"))
				e.pos = "right";
			else if (e.pos.equals("right"))
				e.pos = "bottom";
			else if (e.pos.equals("bottom"))
				e.pos = "left";
		}
	}
	
	private int searchMonster(boolean[][] grid) {
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				try {
					if (grid[i][j + 18] && grid[i + 1][j] && grid[i + 1][j + 5] && grid[i + 1][j + 6]
							&& grid[i + 1][j + 11] && grid[i + 1][j + 12] && grid[i + 1][j + 17] && grid[i + 1][j + 18]
							&& grid[i + 1][j + 19] && grid[i + 2][j + 1] && grid[i + 2][j + 4] && grid[i + 2][j + 7]
							&& grid[i + 2][j + 10] && grid[i + 2][j + 13] && grid[i + 2][j + 16]) {
						count++;
					}
				} catch (Exception e) {

				}
			}
		}
		return count;
	}

}