//package test.clyde.aoc.utils;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.IntStream;
//
//public class CombinationsIterator<T> implements Iterator<List<T>> {
//
//	private int mindex;
//	private int mi;
//	private int size;
//	private List<T> current;
//	private List<T> last;
//
//	public CombinationsIterator(Combinations<T> combinations) {
//		current = combinations.getCurrent();
//		mi = 0;
//		mindex = 0;
//		size = combinations.getSize();
//		last = new ArrayList<>(size);
//		IntStream.range(0, size).forEach(i -> last.add(null));
//	}
//
//	@Override
//	public boolean hasNext() {
//		return mi < current.size();
//	}
//
//	@Override
//	public List<T> next() {
//		combinationUtil(mi, last);
//		return last;
//	}
//
//	private void combinationUtil(int i, List<T> result) {
//		if (mindex == size) {
//			mindex--;
//			if (mi==current.size()-1) {
//				mi -=2;
//				mindex--;
//			}
//			return;
//		}
//
//		if (i >= current.size())
//			return;
//
//		result.set(mindex++, current.get(i));
//		combinationUtil(i + 1, result);
//
//		mi++;
// 
//	//	combinationUtil(index, i + 1, result);
//	}
//}
