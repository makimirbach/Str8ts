package application.service;

import java.util.ArrayList;

public class ApplyCellEntryChange {
	
	
	/*
	 * which streets are affected by changing cell c in street s
	 */
	public static ArrayList<Street> getConcernedSteets(Str8tSolver str8t, Street s, Cell c) {
		ArrayList<Street> concernedStreets = new ArrayList<Street>();
		Street otherStreet = (s.isHorizontal() ? str8t.cellInStreets(c)[1] : str8t.cellInStreets(c)[0]);
		concernedStreets.add(otherStreet);
		concernedStreets.add(s);
		concernedStreets.addAll(str8t.streetsInLine(s));
		concernedStreets.addAll(str8t.streetsInLine(otherStreet));
		return concernedStreets;
	}
	
	
	public static Street enterEntry(Str8tSolver str8t, Street s, Cell c, int entry) {
		if (entry > 0 && entry <= s.getN())	{
			c.setEntry(entry);
			
			ArrayList<Street> concernedStreets = getConcernedSteets(str8t, s, c);
			for (Street cs: concernedStreets) {
				cs = ApplyMissingChange.removeMissing(cs, entry);
				cs = ApplyPossibleChange.removePossible(str8t, cs, entry);
			}
			
		
		}
		return s;
	}
}
