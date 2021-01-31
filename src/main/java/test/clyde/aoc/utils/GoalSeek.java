package test.clyde.aoc.utils;

public class GoalSeek {
	
	private boolean isLessThanEqual;
	private long lowerBound;
	private long upperBound;
	private GoalSeeker gs;
	private long result;
	
	private GoalSeek(GoalSeeker gs) {
		this.gs = gs;
		this.lowerBound = -1;
		this.upperBound = -1;
		this.isLessThanEqual = true;
		this.result = -1;
	}
	
	public static GoalSeek build(GoalSeeker gs) {
		return new GoalSeek(gs);
	}
	
	public GoalSeek setLowerBound(long lowerBound) {
		this.lowerBound = lowerBound;
		return this;
	}
	
	public GoalSeek setUpperBound(long upperBound) {
		this.upperBound = upperBound;
		return this;
	}
	
	public GoalSeek justGreaterOrEqual() {
		this.isLessThanEqual = false;
		return this;
	}
	
	public GoalSeek justLessOrEqual() {
		this.isLessThanEqual = true;
		return this;
	}
	
	public long seek(long goal) {
		if (upperBound == -1)
			getBounds(goal);
		if (result != -1)
			return result;
		if (lowerBound == -1)
			lowerBound = 1;
		
		long result;
		long lower = lowerBound;
		long upper = upperBound;
		while (true) {
			long mid = lower + (upper - lower) / 2;
			result = gs.executeForGoal(mid);
			if (upper == lower) {
				if (isLessThanEqual) {
					if (result > goal)
						return upper - 1;
					else
						return upper;
				} else {
					if (result > goal)
						return upper;
					else
						return upper + 1;
				}
			} else if (upper - lower == 1) {
				if (result > goal)
					upper = lower;
				else
					lower = upper;
			} else if (result == goal)
				return mid;
			else if (result > goal) {
				upper = mid;
			} else {
				lower = mid;
			}
		}
	}

	private void getBounds(long goal) {
		long index = 1;
		if (lowerBound != -1)
			index = lowerBound;
		long result;
		while (true) {
			result = gs.executeForGoal(index);
			if (result == goal) {
				result = index;
				break;
			} else if (result > goal) {
				break;
	}
			index *= 2;
		}
		upperBound=index;
		lowerBound=index/2;
	}
}
