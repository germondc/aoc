package test.clyde.aoc.aoc2016;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day22 {

	private static List<String> LINES = Utils.readFile("2016/Day22.txt");

	private class Node extends Point {
		int size;
		int used;
		int avail;

		public Node(int x, int y, int size, int used, int avail) {
			super(x, y);
			this.size = size;
			this.used = used;
			this.avail = avail;
		}
	}

	private class Pair {
		Node node1;
		Node node2;

		public Pair(Node node1, Node node2) {
			this.node1 = node1;
			this.node2 = node2;
		}

		public boolean isValid() {
			if (node1.used == 0)
				return false;
			if (node1 == node2)
				return false;
			if (node1.used < node2.avail)
				return true;
			return false;
		}
	}

	private List<Node> getNodes(List<String> lines) {
		Pattern rule1_pat = Pattern.compile(".*x([0-9]+)-y([0-9]+) * ([0-9]+)T.* ([0-9]+)T.* ([0-9]+)T.*%");
		List<Node> nodes = new ArrayList<>();
		for (String line : lines) {
			if (!line.startsWith("/dev")) {
				continue;
			}
			Matcher m = rule1_pat.matcher(line);
			while (m.find()) {
				int x = Integer.valueOf(m.group(1));
				int y = Integer.valueOf(m.group(2));
				int size = Integer.valueOf(m.group(3));
				int used = Integer.valueOf(m.group(4));
				int avail = Integer.valueOf(m.group(5));
				Node node = new Node(x, y, size, used, avail);
				nodes.add(node);
			}
		}
		return nodes;
	}

	private long problemA(List<String> lines) {
		List<Node> nodes = getNodes(lines);
		List<Pair> pairs = new ArrayList<>();
		for (Node n1 : nodes) {
			for (Node n2 : nodes) {
				Pair p = new Pair(n1, n2);
				if (p.isValid())
					pairs.add(p);
			}
		}

		return pairs.size();
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day22().problemA(LINES));
		System.out.println("B: " + new Day22().problemB(LINES));
	}

	private long problemB(List<String> lines) {
		List<Node> nodes = getNodes(lines);
		int maxX = nodes.stream().max(Comparator.comparing(n -> n.x)).get().x;
		int maxY = nodes.stream().max(Comparator.comparing(n -> n.y)).get().y;
		Node wall = null;
		Node empty = null;
		Node[][] nodeArray = new Node[maxX + 1][maxY + 1];
		nodes.forEach(n -> nodeArray[n.x][n.y] = n);
		for (int y = 0; y < nodeArray[0].length; y++) {
			for (int x = 0; x < nodeArray.length; x++) {
				Node n = nodeArray[x][y];
				if (x == 0 && y == 0)
					System.out.print("S");
				else if (x == maxX && y == 0)
					System.out.print("G");
				else if (n.used == 0) {
					empty = n;
					System.out.print("_");
				} else if (n.size > 250) {
					if (wall == null)
						wall = nodeArray[x - 1][y];
					System.out.print("#");
				} else
					System.out.print(".");
			}
			System.out.println();
		}
		
		int emptyToGoal = empty.y + empty.x - (2 * wall.x) + maxX;
		int goalAndEmptyToStart = 5 * (maxX - 1);
		return emptyToGoal + goalAndEmptyToStart;
	}
}
