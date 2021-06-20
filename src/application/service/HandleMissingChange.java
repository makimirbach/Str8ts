package application.service;

import java.util.ArrayList;

public class HandleMissingChange {
	/*
	 * add missing entries
	 */
	public static ArrayList<Integer> addMissing(Street s, ArrayList<Integer> newMissing) {
		ArrayList<Integer> missing = s.getMissing();
		missing.addAll(newMissing);
		return Helper.deleteDuplicates(missing);
	}
	
	/*
	 * remove no more missing entry
	 */
	public static ArrayList<Integer> removeMissing(Street s, int toRemove) {
		ArrayList<Integer> missing = s.getMissing();
		if (missing.contains(toRemove)) {
			int index = missing.indexOf(toRemove);
			missing.remove(index);
		}
		return missing;
	}
}
