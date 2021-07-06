package application.service;

import java.util.ArrayList;

public class ApplyMissingChange {
	/*
	 * add missing entries
	 */
	public static Street addMissing(Str8tSolver str8t, Street s, ArrayList<Integer> newMissing) {
		ArrayList<Integer> missing = s.getMissing();
		missing.addAll(newMissing);
		s.setMissing(Helper.deleteDuplicates(missing));
		// those are not longer (only) possible here
		// and no longer possible in streets in line
		ArrayList<Street> streetsInLine = str8t.streetsInLine(s);
		for (int i = 0; i < newMissing.size(); i++) {
			int m = newMissing.get(i);
			s = ApplyPossibleChange.removePossible(str8t, s,m);
			for (Street sInLine: streetsInLine) {
				sInLine = ApplyPossibleChange.removePossible(str8t, sInLine, m);
			}
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
