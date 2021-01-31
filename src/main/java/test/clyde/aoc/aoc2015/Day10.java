package test.clyde.aoc.aoc2015;

public class Day10 {

	public static void main(String[] args) {
		System.out.println("A: " + new Day10().getLength(40, "1321131112"));
		System.out.println("B: " + new Day10().getLength(50, "1321131112"));
	}
	
	private int getLength(int iteration, String init) {
		String currentString = init;
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< iteration; i++) {
			char[] chars = currentString.toCharArray();
			int currentCount = 1;
			for (int j=1; j<chars.length; j++) {
				char c = chars[j];
				if (c == chars[j-1]) {
					currentCount++;
				} else {
					sb.append(currentCount);
					sb.append(chars[j-1]);
					currentCount = 1;
				}
			}
			sb.append(currentCount);
			sb.append(chars[chars.length-1]);
			currentString = sb.toString();
			sb = new StringBuilder();
		}
		return currentString.length();
	}
}
