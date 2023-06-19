package application.service;

import java.util.ArrayList;

public class ApplyBlockedChange {
	/*
	 * add blocked entries
	 */
	public static Street addBlocked(Str8tSolver str8t, Street s, ArrayList<Integer> newBlocked) {
		ArrayList<Integer> blocked = s.getBlocked();
		// remove duplicates
		blocked = Str8tsUtil.deleteDuplicates(blocked);
		blocked.addAll(newBlocked);
		s.setBlocked(Str8tsUtil.deleteDuplicates(blocked));
		// those are not longer (only) possible
		for (int i = 0; i < newBlocked.size(); i++) {
			int m = newBlocked.get(i);
			s = ApplyPossibleChange.removePossible(str8t, s,m);
		}
		return s;
	}
	
}
