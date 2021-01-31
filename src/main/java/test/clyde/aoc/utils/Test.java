package test.clyde.aoc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) {
		int arr[] = { 1, 2, 3, 4, 5 };
		List<Integer> in = Arrays.stream(arr).boxed().collect(Collectors.toList());
		int r = 3;
		int n = arr.length;
		for (List<Integer> item : getCombinations(in, r)) {
			System.out.println(item);
		}

//		List<Integer> items = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
//		for (List<Integer> combin : new Combinations<>(items, 3)) {
//			System.out.println(combin);
//		}
		
//		char[][] ca = new char[3][3];
//		ca[0][0] = 't';
//		ca[1][1] = 'h';
//		ca[2][2] = 'e';
//		
//		Set<char[][]> s = new HashSet<>();
//		s.add(ca);
//		
//		char[][] ca1 = new char[3][3];
//		ca1[0][0] = 't';
//		ca1[1][1] = 'h';
//		ca1[2][2] = 'e';
//		
//		System.out.println(s.contains(ca1));
		
	}

	private static void combinationUtil(int arr[], int data[], int start, int end, int index, int r, List<List<Integer>> result) {
		if (index == r) {
			result.add(Arrays.stream(data).boxed().collect(Collectors.toList()));
			return;
		}

		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			System.out.println(i + "  " + index);
			data[index] = arr[i];
			combinationUtil(arr, data, i + 1, end, index + 1, r, result);
		}
	}

	public static List<List<Integer>> getCombinations(List<Integer> input, int r) {
		List<List<Integer>> result = new ArrayList<>();
		int data[] = new int[r];
		combinationUtil(input.stream().mapToInt(i->i).toArray(), data, 0, input.size() - 1, 0, r, result);
		return result;
	}
}