package application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import application.constants.CellType;

public class Str8tsUtil {
	/*
	 * convert ArrayList of Integers to array of ints
	 */
	public static int[] listToArray(ArrayList<Integer> l) 
	{
		int[] a = new int[l.size()];
		for (int i = 0; i < l.size(); i++) {
			a[i] = l.get(i);
		}
		return a;
	}
	
	/*
	 * convert LinkedList of Cells to array of Cells
	 */
	public static Cell[] cellListToArray(LinkedList<Cell> l) 
	{
		Cell[] a = new Cell[l.size()];
		for (int i = 0; i < l.size(); i++) {
			a[i] = l.get(i);
		}
		return a;
	}
	
	/*
	 * convert ArrayList of Streets to array of Streets
	 */
	public static Street[] streetListToArray(ArrayList<Street> l) 
	{
		Street[] a = new Street[l.size()];
		for (int i = 0; i < l.size(); i++) {
			a[i] = l.get(i);
		}
		return a;
	}
	
	/*
	 * get array of entries from array of cells
	 */
	public static int[] getEntries(Cell[] cells) 
	{
		int[] entries = new int[cells.length];
		for (int i = 0; i < cells.length; i++) {
			entries[i] = cells[i].getEntry();
		}
		return entries;
	}
	
	/*
	 * cell-entries to string
	 */
	public static String getEntriesToString(Cell[] cells) 
	{
		int[] entries = getEntries(cells);
		String e = "";
		for (int i: entries) {
			e += Integer.toString(i);
		}
		return e;
	}
	
	/*
	 * how much space between blocked entries (sorted ascendingly)
	 */
	public static ArrayList<Integer> getLengthsBetweenBlocked(ArrayList<Integer> blocked, int n) 
	{
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for (int i = 0; i < blocked.size(); i++) {
			lengths.add(blocked.get(i) - ((i > 0)?(blocked.get(i-1)):0) - 1); // could be zero if consecutive numbers blocked
		}
		if (blocked.size() > 0) lengths.add(n - blocked.get(blocked.size()-1));
		return lengths;
	}
	
	/*
	 * delete duplicates from array list
	 */
	public static ArrayList<Integer> deleteDuplicates(ArrayList<Integer> list) 
	{
		HashSet<Integer> hashSet = new HashSet<Integer>();
		hashSet.addAll(list);
		list.clear();
		list.addAll(hashSet);
		return list;
	}
	
	/*
	 * check if int plays any role in street, i.e. is entered, possible or missing
	 */
	public static boolean checkRelevantInStreet(Street s, int i) 
	{
		if (i <= s.getMax() && i >= s.getMin()) return true;
		for (int p: s.getPossible()) {
			if (i == p) return true;
		}
		for (int e: s.getEntries()) {
			if (i == e) return true;
		}
		for (int m: s.getMissing()) {
			if (i == m) return true;
		}
		return false;
	}
	
	
	/*
	 * generate cell-2d-array from state and solution
	 */
	public static Cell[][] cellMatrixFromEntries(int[][] state, int[][] solution) 
	{
		Cell[][] cells = new Cell[state.length][state.length];
		
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				cells[i][j] = new Cell(i,j, new Street[0], state[i][j], (solution[i][j] > 0) ? CellType.WHITE: CellType.BLACK);
			}
		}
		
		return cells;
	}
}
