package test.clyde.aoc.utils;

import java.awt.Point;

public class Distances {
	public static int getManhattanDistance(Point p1, Point p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}
	
	public static long getManhattanDistance(Point3d p1, Point3d p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y) + Math.abs(p1.z - p2.z);
	}
}
