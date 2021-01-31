package test.clyde.aoc.aoc2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day6 {
	private static List<String> LINES = Utils.readFile("2019/Day6.txt");
	private static List<String> TEST = Utils.readFile("2019/Day6a.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day6b.txt");

	private class OrbitObject {
		String name;
		String orbitsString;
		OrbitObject orbits;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
			OrbitObject other = (OrbitObject) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return String.format("OrbitObject [name=%s]", name);
		}
	}

	private long problemA(List<String> lines) {
		Map<String, OrbitObject> orbits = new HashMap<>();
		for (String line : lines) {
			String[] split = line.split("\\)");
			OrbitObject o = new OrbitObject();
			o.name=split[1];
			o.orbitsString=split[0];
			orbits.put(o.name, o);
		}
		
		for (OrbitObject o : orbits.values()) {
			if (o.orbitsString.equals("COM"))
				continue;
			o.orbits = orbits.get(o.orbitsString);
		}
		
		int count = 0;
		for (OrbitObject o : orbits.values()) {
			OrbitObject current = o;
			count++;
			while (current.orbits != null) {
				current = current.orbits;
				count++;
			}
		}
		
		return count;
	}

	private long problemB(List<String> lines) {
		Map<String, OrbitObject> orbits = new HashMap<>();
		for (String line : lines) {
			String[] split = line.split("\\)");
			OrbitObject o = new OrbitObject();
			o.name=split[1];
			o.orbitsString=split[0];
			orbits.put(o.name, o);
		}
		
		for (OrbitObject o : orbits.values()) {
			if (o.orbitsString.equals("COM"))
				continue;
			o.orbits = orbits.get(o.orbitsString);
		}
		
		int destSteps =0;
		OrbitObject dest = orbits.get("SAN");
		Map<OrbitObject, Integer> destPath = new HashMap<>();
		while (!dest.orbitsString.equals("COM")) {
			dest = dest.orbits;
			destPath.put(dest, destSteps++);
		}
		
		int count = 0;
		
		OrbitObject current = orbits.get("YOU");
		while (!destPath.containsKey(current)) {
			current = current.orbits;
			count++;
		}
		int santaPath = destPath.get(current);
		
		return count-1+santaPath;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day6().problemA(TEST));
		System.out.println("A: " + new Day6().problemA(LINES));
		System.out.println("TestB: " + new Day6().problemB(TESTB));
		System.out.println("B: " + new Day6().problemB(LINES));
	}

}
