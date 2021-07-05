package application.service;

import java.util.ArrayList;

public class ApplyMissingChange {
	/*
	 * add missing entries
	 */
	public static Street addMissing(Street s, ArrayList<Integer> newMissing) {
		ArrayList<Integer> missing = s.getMissing();
		missing.addAll(newMissing);
		s.setMissing(Helper.deleteDuplicates(missing));
		// those are not longer (only) possible
		for (int i = 0; i < newMissing.size(); i++) {
			int m = newMissing.get(i);
			s = ApplyPossibleChange.removePossible(s,m);
		}
		
		return s;
	}
	
	/*
	 * remove no more missing entry
	 */
	public static Street removeMissing(Street s, int toRemove) {
		ArrayList<Integer> missing = s.getMissing();
		if (missing.contains(toRemove)) {
			int index = missing.indexOf(toRemove);
			missing.remove(index);
		}
		s.setMissing(missing);
		return s;
	}
}
