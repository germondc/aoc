package test.clyde.aoc.aoc2016;

public class Day16 {
	
	private static String INPUT = "10111100110001111";
	
	private String problemB(int diskSize) {
		String a = INPUT;
		while (a.length() < diskSize) {
			StringBuilder sb = new StringBuilder(a);
			sb.append('0');
			for (int i=a.length()-1; i>=0; i--) {
				if (a.charAt(i)=='0')
					sb.append('1');
				else
					sb.append('0');
			}
			a = sb.toString();
		}
		
		a = a.substring(0, diskSize);
		String checkSum = getChkSum(a);
		return checkSum;
	}
	
	private String getChkSum(String value) {
		String result = value;
		while (result.length() % 2 == 0) {
			result = calcChkSum(result);
		}
		return result;
	}
	
	private String calcChkSum(String value) {
		StringBuilder result = new StringBuilder();
		for (int i=0; i<value.length(); i+=2) {
			if (value.charAt(i) == value.charAt(i+1)) {
				result.append('1');
			} else
				result.append('0');
		}
		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day16().problemB(272));
		System.out.println("B: " + new Day16().problemB(35651584));
	}
}
