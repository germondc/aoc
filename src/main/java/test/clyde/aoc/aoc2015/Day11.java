package test.clyde.aoc.aoc2015;

public class Day11 {
	public static void main(String[] args) {
		//System.out.print(new Day11().findNextPassword("abcdefgh"));
		//System.out.print(new Day11().findNextPassword("vzbxkghb"));
		System.out.println(new Day11().findNextPassword("vzbxkghb"));
		System.out.println(new Day11().findNextPassword("vzbxxyzz"));
	}
	
	private String bumpByOne(String str) {
		char[] ca = str.toCharArray();

		int index = str.length()-1;
		while (index >= 0) {
			ca[index] += 1;
			if (ca[index] > 'z') {
				ca[index] = 'a';
			} else {
				break;
			}
			index--;
		}
		
		return new String(ca);
	}
	
	private String findNextPassword(String currentPassword) {
		String current = bumpByOne(currentPassword);
		while (!isValidPass(current)) {
			current = bumpByOne(current);
		}
		return current;
	}
	
	public static String toAlphabeticRadix(int num) {
	    char[] str = Integer.toString(num, 26).toCharArray();
	    for (int i = 0; i < str.length; i++) {
	        str[i] += str[i] > '9' ? 10 : 49;
	    }
	    return new String(str);
	}
	
	public static int fromAlphabeticRadix(String str) {
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			c[i] -= c[i] <= 'j' ? 49 : 10; 
		}
		return Integer.valueOf(new String(c), 26);
	}
	
	public boolean isValidPass(String pass) {
		char[] c = pass.toCharArray();
		boolean three = false;
		for (int i = 0; i < c.length - 2; i++) {
			if (c[i]+1 == c[i+1] && c[i]+2 == c[i+2]) {
				three = true;
				break;
			}
		}
		if (!three)
			return false;
		
		boolean badLetter = pass.contains("i") || pass.contains("o") || pass.contains("l");
		if (badLetter)
			return false;
		
		int pairs = 0;
		for (int i = 0; i < c.length - 1; i++) {
			if (c[i] == c[i+1]) {
				pairs++;
				i++;
			}
		}
		if (pairs < 2)
			return false;
		return true;
	}
}
