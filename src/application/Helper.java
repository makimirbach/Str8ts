package application;

import java.util.ArrayList;

public class Helper {
	/*
	 * convert ArrayList of Integers to array of ints
	 */
	public static int[] listToArray(ArrayList<Integer> l) {
		int[] a = new int[l.size()];
		for (int i = 0; i < l.size(); i++) {
			a[i] = l.get(i);
		}
		return a;
	}
	
	/*
	 * get max elt from an array
	 */
	public static int getMax(int[] a) {
		int maxValue = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] > maxValue) {
				maxValue = a[i];
			}
		}
		return maxValue;
	}
	/*
	 * get min elt from an array
	 */
	public static int getMin(int[] a) {
		int minValue = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] < minValue) {
				minValue = a[i];
			}
		}
		return minValue;
	}
	
}
