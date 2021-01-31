package test.clyde.aoc.aoc2015;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day25 {
	
	private long test() {
		int desiredRow = 2981;
		int desiredCol = 3075;
		
		int nextRow = 0;
		int rowCounter = 0;
		int colCounter = 0;
		BigInteger current = BigInteger.valueOf(20151125);
		while (true) {
			colCounter += 1;
			rowCounter -= 1;
			if (rowCounter < 0) {
				colCounter = 0;
				rowCounter = ++nextRow;
			}
			
			current = current.multiply(BigInteger.valueOf(252533)).remainder(BigInteger.valueOf(33554393));
			if (rowCounter == desiredRow-1 && colCounter == desiredCol-1)
				break;
		}
		
		return current.longValue();
	}
	
	public static void main(String[] args) {
		System.out.println("A: " + new Day25().test());
	}
}
