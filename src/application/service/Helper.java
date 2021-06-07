package application.service;

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
	 * get max elt > 0 from an array
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
	 * get min elt > 0 from an array
	 * or 0 if only zeros contained
	 */
	public static int getRealMin(int[] a) {
		
		int minValue = getMax(a);
		for (int i = 0; i < a.length; i++) {
			if (a[i] < minValue && a[i] > 0) {
				minValue = a[i];
			}
		}
		return minValue;
	}
	
	/*
	 * count numbers != 0 in array
	 */
	public static int getEntered(int[] a) {
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0) count++;
		}
		return count;
	}
	
}
