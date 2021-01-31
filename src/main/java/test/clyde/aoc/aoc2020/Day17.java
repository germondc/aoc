package test.clyde.aoc.aoc2020;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day17 {

	private static List<String> LINES = Utils.readFile("2020/Day17.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/Day17a.txt");

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day17().problemA(TEST_LINES, 3));
		System.out.println("A: " + new Day17().problemA(LINES, 8));
		System.out.println("TestB: " + new Day17().problemB(TEST_LINES, 3));
		System.out.println("B: " + new Day17().problemB(LINES, 8));
	}

	private long problemA(List<String> lines, int size) {
		boolean[][][] grid = new boolean[size][size][size];
		int z = 1;
		int y = 0;
		for (String line : lines) {
			int x = 0;
			for (char c : line.toCharArray()) {
				if (c == '#')
					grid[z][y][x] = true;
				x++;
			}
			y++;
		}

		for (int i = 1; i < 7; i++) {
			boolean[][][] grid2 = growGrid(grid);
			boolean[][][] grid3 = copyGrid(grid2);
			for (int zz = 0; zz < grid2.length; zz++) {
				for (int yy = 0; yy < grid2.length; yy++) {
					for (int xx = 0; xx < grid2.length; xx++) {
						int count = count(grid3, xx, yy, zz);
						if (grid3[zz][yy][xx]) {
							if (count == 2 || count == 3) {
								grid2[zz][yy][xx] = true;
							} else {
								grid2[zz][yy][xx] = false;
							}
						} else {
							if (count == 3) {
								grid2[zz][yy][xx] = true;
							}
						}
					}
				}
			}
			grid = grid2;
		}

		return count(grid);
	}

	private int count(boolean[][][] grid) {
		int count = 0;
		for (int zz = 0; zz < grid.length; zz++) {
			for (int yy = 0; yy < grid.length; yy++) {
				for (int xx = 0; xx < grid.length; xx++) {
					if (grid[xx][yy][zz])
						count++;
				}
			}
		}
		return count;
	}

	private int countB(boolean[][][][] grid) {
		int count = 0;
		for (int zz = 0; zz < grid.length; zz++) {
			for (int yy = 0; yy < grid.length; yy++) {
				for (int xx = 0; xx < grid.length; xx++) {
					for (int ww = 0; ww < grid.length; ww++) {
						if (grid[xx][yy][zz][ww])
							count++;
					}
				}
			}
		}
		return count;
	}

	private boolean[][][][] copyGridB(boolean[][][][] grid) {
		int x = grid.length;
		int y = grid[0].length;
		int z = grid[0][0].length;

		boolean[][][][] result = new boolean[x][x][y][z];
		for (int ww = 0; ww < x; ww++) {
			for (int xx = 0; xx < x; xx++) {
				for (int yy = 0; yy < y; yy++) {
					for (int zz = 0; zz < z; zz++) {
						result[ww][xx][yy][zz] = grid[ww][xx][yy][zz];
					}
				}
			}
		}
		return result;
	}

	private boolean[][][] copyGrid(boolean[][][] grid) {
		int x = grid.length;
		int y = grid[0].length;
		int z = grid[0][0].length;

		boolean[][][] result = new boolean[x][y][z];
		for (int xx = 0; xx < x; xx++) {
			for (int yy = 0; yy < y; yy++) {
				for (int zz = 0; zz < z; zz++) {
					result[xx][yy][zz] = grid[xx][yy][zz];
				}
			}
		}
		return result;
	}

	private boolean[][][] growGrid(boolean[][][] grid) {
		int x = grid.length;
		int y = grid[0].length;
		int z = grid[0][0].length;

		boolean[][][] result = new boolean[x + 2][y + 2][z + 2];
		for (int xx = 0; xx < x + 2; xx++) {
			for (int yy = 0; yy < y + 2; yy++) {
				for (int zz = 0; zz < z + 2; zz++) {
					if (xx > 0 && xx <= x && yy > 0 && yy <= y && zz > 0 && zz <= z) {
						result[xx][yy][zz] = grid[xx - 1][yy - 1][zz - 1];
					}
				}
			}
		}
		return result;
	}

	private boolean[][][][] growGridB(boolean[][][][] grid) {
		int x = grid.length;
		int y = grid[0].length;
		int z = grid[0][0].length;

		boolean[][][][] result = new boolean[x + 2][x + 2][y + 2][z + 2];
		for (int ww = 0; ww < x + 2; ww++) {
			for (int xx = 0; xx < x + 2; xx++) {
				for (int yy = 0; yy < y + 2; yy++) {
					for (int zz = 0; zz < z + 2; zz++) {
						if (ww > 0 && ww <= x && xx > 0 && xx <= x && yy > 0 && yy <= y && zz > 0 && zz <= z ) {
							result[ww][xx][yy][zz] = grid[ww - 1][xx - 1][yy - 1][zz - 1];
						}
					}
				}
			}
		}
		return result;
	}

	private int count(boolean[][][] grid, int x, int y, int z) {
		int count = 0;
		for (int dz = -1; dz < 2; dz++) {
			for (int dy = -1; dy < 2; dy++) {
				for (int dx = -1; dx < 2; dx++) {
					if (dx == 0 && dy == 0 && dz == 0)
						continue;
					try {
						if (grid[dz + z][dy + y][dx + x]) {
							count++;
						}
					} catch (Throwable t) {
					}
				}
			}
		}
		return count;
	}

	private int countB(boolean[][][][] grid, int w, int x, int y, int z) {
		int count = 0;
		for (int dz = -1; dz < 2; dz++) {
			for (int dy = -1; dy < 2; dy++) {
				for (int dx = -1; dx < 2; dx++) {
					for (int dw = -1; dw < 2; dw++) {
						if (dx == 0 && dy == 0 && dz == 0 && dw == 0)
							continue;
						try {
							if (grid[dz + z][dy + y][dx + x][dw + w]) {
								count++;
							}
						} catch (Throwable t) {
						}
					}
				}
			}
		}
		return count;
	}

	private long problemB(List<String> lines, int size) {
		boolean[][][][] grid = new boolean[size][size][size][size];
		int z = 1;
		int y = 0;
		int w = 0;
		for (String line : lines) {
			int x = 0;
			for (char c : line.toCharArray()) {
				if (c == '#')
					grid[z][y][x][w] = true;
				x++;
			}
			y++;
		}

		for (int i = 1; i < 7; i++) {
			boolean[][][][] grid2 = growGridB(grid);
			boolean[][][][] grid3 = copyGridB(grid2);
			for (int zz = 0; zz < grid2.length; zz++) {
				for (int yy = 0; yy < grid2.length; yy++) {
					for (int xx = 0; xx < grid2.length; xx++) {
						for (int ww = 0; ww < grid2.length; ww++) {
							int count = countB(grid3, ww, xx, yy, zz);
							if (grid3[zz][yy][xx][ww]) {
								if (count == 2 || count == 3) {
									grid2[zz][yy][xx][ww] = true;
								} else {
									grid2[zz][yy][xx][ww] = false;
								}
							} else {
								if (count == 3) {
									grid2[zz][yy][xx][ww] = true;
								}
							}
						}
					}
				}
			}
			grid = grid2;
		}

		return countB(grid);
	}
}
