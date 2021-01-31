package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import test.clyde.aoc.utils.Utils;

public class Day20 {

	private static List<String> LINES = Utils.readFile("2017/Day20.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day20a.txt");

	private class Point3D {
		long x;
		long y;
		long z;

		public Point3D(long x, long y, long z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public long distanceFromOrigin() {
			return Math.abs(x) + Math.abs(y) + Math.abs(z);
		}

		public void add(Point3D anotherPoint) {
			this.x += anotherPoint.x;
			this.y += anotherPoint.y;
			this.z += anotherPoint.z;
		}

		@Override
		public String toString() {
			return String.format("(%s, %s, %s)", x, y, z);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (x ^ (x >>> 32));
			result = prime * result + (int) (y ^ (y >>> 32));
			result = prime * result + (int) (z ^ (z >>> 32));
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
			Point3D other = (Point3D) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}
	}

	private class Particle {
		Point3D position;
		Point3D velocity;
		Point3D acceleration;

		@Override
		public String toString() {
			return String.format("Particle [p=%s, v=%s, a=%s]", position, velocity, acceleration);
		}
	}

	private Map<Integer, Particle> getParticles(List<String> lines) {
		Pattern p = Pattern.compile(
				"p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>");
		// p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>
		List<Particle> particles = new ArrayList<>();
		for (String line : lines) {
			Matcher m = p.matcher(line);
			if (m.find()) {
				Particle part = new Particle();
				particles.add(part);
				part.position = new Point3D(Long.valueOf(m.group(1)), Long.valueOf(m.group(2)),
						Long.valueOf(m.group(3)));
				part.velocity = new Point3D(Long.valueOf(m.group(4)), Long.valueOf(m.group(5)),
						Long.valueOf(m.group(6)));
				part.acceleration = new Point3D(Long.valueOf(m.group(7)), Long.valueOf(m.group(8)),
						Long.valueOf(m.group(9)));

			}
		}
		return IntStream.range(0, particles.size()).boxed().collect(Collectors.toMap(i->i, i->particles.get(i)));
	}

	private int problemA(List<String> lines) {
		Map<Integer, Particle> particles = getParticles(lines);
		int j = 0;
		while (true) {
			int closestPart = 0;
			Long closestDist = Long.MAX_VALUE;
			int index = 0;
			for (Particle part : particles.values()) {
				part.velocity.add(part.acceleration);
				part.position.add(part.velocity);
				long dist = part.position.distanceFromOrigin();
				if (dist < closestDist) {
					closestDist = dist;
					closestPart = index;
				}
				index++;
			}
			j++;
			if (j == 500)
				return closestPart;
		}
	}

	private int problemB(List<String> lines) {
		Map<Integer, Particle> particles = getParticles(lines);
		int numberParticles = particles.size();
		int run = 0;
		while (true) {
			Map<Point3D, List<Integer>> distances = new HashMap<>();
			for (Map.Entry<Integer,Particle> partEntry : particles.entrySet()) {
				Particle part = partEntry.getValue();
				part.velocity.add(part.acceleration);
				part.position.add(part.velocity);
				//long dist = part.position.distanceFromOrigin();
				if (!distances.containsKey(part.position)) {
					distances.put(part.position, new ArrayList<>());
				}
				distances.get(part.position).add(partEntry.getKey());
				
			}
			distances.values().stream().filter(l -> l.size()>1).flatMap(l -> l.stream()).forEach(i -> particles.remove(i));
			if (particles.size() == numberParticles) {
				run++;
				if (run > 1000)
					return numberParticles;
			} else {
				run = 0;
				numberParticles = particles.size();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day20().problemA(TEST_LINES));
		System.out.println("A: " + new Day20().problemA(LINES));
		System.out.println("TestB: " + new Day20().problemB(TEST_LINES));
		System.out.println("B: " + new Day20().problemB(LINES));
	}

}
