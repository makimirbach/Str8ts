package application.service;

import java.util.ArrayList;

public class HandlePossibleChange {
	/*
	 * add possible entries
	 */
	public static ArrayList<Integer> addPossible(Street s, ArrayList<Integer> newPossible) {
		ArrayList<Integer> possible = s.getPossible();
		possible.addAll(newPossible);
		return Helper.deleteDuplicates(possible);
	}
	
	/*
	 * remove possible entry: might remove some more!
	 */
	public static ArrayList<Integer> removePossible(Street s, int toRemove) {
		ArrayList<Integer> possible = s.getPossible();
		if (possible.contains(toRemove)) {
			int index = possible.indexOf(toRemove);
			possible.remove(index);
			// also remove no longer possible 
			for (int i = 0; i < possible.size(); i++) {
				if ((s.getMin() > 0 && possible.get(i) < s.getMin() - s.getUnentered()) || (s.getMax() > 0 && possible.get(i) > s.getMax() + s.getUnentered())) {
					possible.remove(i);
					i--;
				}
			}
		}
		return possible;
	}
}
