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
