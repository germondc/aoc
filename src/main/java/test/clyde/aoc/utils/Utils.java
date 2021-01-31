package test.clyde.aoc.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.core.io.ClassPathResource;

public class Utils {
	public static List<String> readFile(String name) {
		try {
			InputStream is = new ClassPathResource(name).getInputStream();

			List<String> result = new ArrayList<String>();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				String line;
				while ((line = reader.readLine()) != null) {
					result.add(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("failed read file", e);
		}
	}

	public static List<Character> readChars(String name) {
		try {
			InputStream is = new ClassPathResource(name).getInputStream();
			List<Character> chars = new ArrayList<>();

			int b;
			while ((b = is.read()) != -1) {
				chars.add((char) (b & 0xFF));
			}
			return chars;
		} catch (Exception e) {
			throw new RuntimeException("failed read file", e);
		}
	}

	public static <T> List<List<T>> getAllIterative(List<T> elements) {
		List<List<T>> result = new ArrayList<>();
		List<T> current = elements;
		result.add(current);

		int n = elements.size();
		int[] indexes = new int[n];
		for (int i = 0; i < n; i++) {
			indexes[i] = 0;
		}

		int i = 0;
		while (i < n) {
			if (indexes[i] < i) {
				current = swap(current, i % 2 == 0 ? 0 : indexes[i], i);
				result.add(current);
				indexes[i]++;
				i = 0;
			} else {
				indexes[i] = 0;
				i++;
			}
		}
		return result;
	}

	private static <T> List<T> swap(List<T> elements, int a, int b) {
		List<T> result = new ArrayList<>(elements);
		result.set(a, elements.get(b));
		result.set(b, elements.get(a));
		return result;
	}

	public static List<Integer> getFactors(int number) {
		List<Integer> result = new ArrayList<>();
		if (number == 0)
			return result;
		result.add(1);
		if (number == 1)
			return result;
		for (int i = 2; i <= number / 2; ++i) {
			if (number % i == 0) {
				result.add(i);
			}
		}
		result.add(number);
		return result;
	}

	public static int divSum(int n) {
		if (n == 1)
			return 1;

		int result = 0;

		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {

				if (i == (n / i))
					result += i;
				else
					result += (i + n / i);
			}
		}

		return (result + n + 1);
	}

	private static <T> void combinationUtil(List<T> arr, List<T> data, int start, int end, int index, int r,
			List<List<T>> result) {
		if (index == r) {
			result.add(new ArrayList<>(data));
			return;
		}

		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			data.set(index, arr.get(i));
			combinationUtil(arr, data, i + 1, end, index + 1, r, result);
		}
	}

	public static <T> List<List<T>> getCombinations(List<T> input, int r) {
		List<List<T>> result = new ArrayList<>();
		List<T> data = new ArrayList<>();
		IntStream.range(0, r).forEach(i -> data.add(null));
		combinationUtil(input, data, 0, input.size() - 1, 0, r, result);
		return result;
	}

	public static boolean isPrime(long value) {
		long n = value < 0 ? value * -1 : value;
		if (n < 2)
			return false;
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		long sqrtN = (long) Math.sqrt(n) + 1;
		for (long i = 6L; i <= sqrtN; i += 6) {
			if (n % (i - 1) == 0 || n % (i + 1) == 0)
				return false;
		}
		return true;
	}
}
