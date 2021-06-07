package application.service;

import java.util.ArrayList;
import java.util.LinkedList;

import application.constants.CellType;

public class Helper {
	/*
	 * convert ArrayList of Integers to array of ints
	 */
	public static int[] listToArray(ArrayList<Integer> l) {
		int[] a = new int[l.size()];
		for (int i = 0; i < l.size(); i++) {
			a[i] = l.get(i);
		}
		return a;
	}
	
	/*
	 * convert LinkedList of Cells to array of Cells
	 */
	public static Cell[] cellListToArray(LinkedList<Cell> l) {
		Cell[] a = new Cell[l.size()];
		for (int i = 0; i < l.size(); i++) {
			a[i] = l.get(i);
		}
		return a;
	}
	
	/*
	 * get array of entries from array of cells
	 */
	public static int[] getEntries(Cell[] cells) {
		int[] entries = new int[cells.length];
		for (int i = 0; i < cells.length; i++) {
			entries[i] = cells[i].getEntry();
		}
		return entries;
	}
	
	/*
	 * get max elt > 0 from an array
	 */
	public static int getMax(int[] a) {
		if (a.length == 0) return 0;
		int maxValue = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] > maxValue) {
				maxValue = a[i];
			}
		}
		return maxValue;
	}
	/*
	 * get min elt > 0 from an array
	 * or 0 if only zeros contained
	 */
	public static int getRealMin(int[] a) {
		
		int minValue = getMax(a);
		for (int i = 0; i < a.length; i++) {
			if (a[i] < minValue && a[i] > 0) {
				minValue = a[i];
			}
		}
		return minValue;
	}
	
	/*
	 * count numbers != 0 in array
	 */
	public static int getEntered(int[] a) {
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0) count++;
		}
		return count;
	}
	
	public static Cell[][] cellMatrixFromEntries(int[][] state, int[][] solution) {
		Cell[][] cells = new Cell[state.length][state.length];
		
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				cells[i][j] = new Cell(i,j, new Street[0], state[i][j], (solution[i][j] > 0) ? CellType.WHITE: CellType.BLACK);
			}
		}
		
		return cells;
	}
}
