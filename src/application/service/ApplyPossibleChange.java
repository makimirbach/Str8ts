package application.service;

import java.util.ArrayList;

public class ApplyPossibleChange {
	/*
	 * find isolated possibles that aren't relevant for street
	 */
	public static ArrayList<Integer> findIsolated(Street s) 
	{
		ArrayList<Integer> isolated = new ArrayList<>();
		for (int p: s.getPossible()) {
			if (p > 1 && p < s.getN()) {
				if (!Str8tsUtil.checkRelevantInStreet(s, p+1) && !Str8tsUtil.checkRelevantInStreet(s, p-1)) {
					isolated.add(p);
				}
			} else if (p == 1) {
				if (!Str8tsUtil.checkRelevantInStreet(s, p+1)) isolated.add(p);
			} else {
				if (!Str8tsUtil.checkRelevantInStreet(s, p-1)) isolated.add(p);
			}
		}
		
		return isolated;
	}
	
	/*
	 * if #possible + #missing = #unentered, all are missing
	 */
	public static Street possibleChanged(Str8tSolver str8t, Street s) 
	{
	    if (s.getPossible().size() + s.getMissing().size() == s.getUnentered()) {
	    	s = ApplyMissingChange.addMissing(str8t, s, s.getPossible());
	    	s.setPossible(new ArrayList<Integer>());
	    }
		return s;
	}
	
	/*
	 * add possible entries
	 */
	public static Street addPossible(Str8tSolver str8t, Street s, ArrayList<Integer> newPossible) 
	{
		ArrayList<Integer> possible = s.getPossible();
		possible.addAll(newPossible);
		s.setPossible(Str8tsUtil.deleteDuplicates(possible));
		s = possibleChanged(str8t, s);
		return s;
	}
	
	/*
	 * remove possible entry: might remove some more!
	 */
	public static Street removePossible(Str8tSolver str8t, Street s, int toRemove) 
	{
		ArrayList<Integer> possible = s.getPossible();
		if (possible.contains(toRemove)) {
			int index = possible.indexOf(toRemove);
			possible.remove(index);
			// also remove no longer possible 
			for (int i = 0; i < possible.size(); i++) {
				if (s.getMin()> 0) {
					if ((possible.get(i) < s.getMin() - s.getUnentered()) || (possible.get(i) > s.getMax() + s.getUnentered()) || (possible.get(i) <= s.getMax() - s.getLength()) || (possible.get(i) >= s.getMin() + s.getLength())
							) {
						possible.remove(i);
						i--;
					}
				}
			}
			if(s.getLength() > 1)  possible.removeAll(findIsolated(s));
			s.setPossible(possible);
			s = possibleChanged(str8t, s);
		}
		return s;
	}
}
