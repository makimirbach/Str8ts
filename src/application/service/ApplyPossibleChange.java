package application.service;

import java.util.ArrayList;

public class ApplyPossibleChange {
	/*
	 * find isolated possibles that aren't relevant for street
	 */
	public static ArrayList<Integer> findIsolated(Street s) {
		ArrayList<Integer> isolated = new ArrayList<>();
		for (int p: s.getPossible()) {
			if (p > 1 && p < s.getN()) {
				if (!Helper.checkRelevantInStreet(s, p+1) && !Helper.checkRelevantInStreet(s, p-1)) {
					isolated.add(p);
				}
			} else if (p == 1) {
				if (!Helper.checkRelevantInStreet(s, p+1)) isolated.add(p);
			} else {
				if (!Helper.checkRelevantInStreet(s, p-1)) isolated.add(p);
			}
		}
		
		return isolated;
	}
	
	/*
	 * if #possible + #missing = #unentered, all are missing
	 */
	public static Street possibleChanged(Street s) {
	    if (s.getPossible().size() + s.getMissing().size() == s.getUnentered()) {
	    	s = ApplyMissingChange.addMissing(s, s.getPossible());
	    	s.setPossible(new ArrayList<Integer>());
	    }
		return s;
	}
	
	/*
	 * add possible entries
	 */
	public static Street addPossible(Street s, ArrayList<Integer> newPossible) {
		ArrayList<Integer> possible = s.getPossible();
		possible.addAll(newPossible);
		s.setPossible(Helper.deleteDuplicates(possible));
		s = possibleChanged(s);
		return s;
	}
	
	/*
	 * remove possible entry: might remove some more!
	 */
	public static Street removePossible(Street s, int toRemove) {
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
			possible.removeAll(findIsolated(s));
		}
		s.setPossible(possible);
		s = possibleChanged(s);
		return s;
	}
}
