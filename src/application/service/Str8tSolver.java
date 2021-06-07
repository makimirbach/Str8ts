package application.service;

import java.util.ArrayList;
import java.util.LinkedList;

import application.constants.Solvability;

public class Str8tSolver {
	private LinkedList<Entry> llstSolution = new LinkedList<Entry>();
	private int[][] state;
	private int[][] solution;
	private int n;
	
	
	public Str8tSolver(int[][] state, int[][] solution, int n) {
		this.state = state;
		this.solution = solution;
		this.n = n;
	}

	/*
	 * check if str8t s is (uniquely) solvable i.e. contains only consecutive numbers
	 */
	public Solvability checkStr8t(int[] s) {
		int min = Helper.getRealMin(s); // smallest number > 0 
		int max = Helper.getMax(s); 
		
		if (max == 0) return Solvability.SOLVABLE; // nothing entered yet
		
		if (max-min+1 > s.length) {
			return Solvability.UNSOLVABLE;
		} else {
			int entered = Helper.getEntered(s); // > 0
			int unentered = s.length - entered; //  number of zeros in s
			// uniquely when all entered or only one missing and only one choice left
			if (unentered == 0 || (unentered == 1 && (min == 1 || max == this.n || max-min+1 == s.length))) return Solvability.UNIQUELY_SOLVABLE;
			else return Solvability.SOLVABLE;
		}
	}
	
	/*
	 * check if row does not contain duplicates
	 */
	public boolean checkDuplicatesRow(int r) {
		int t = 0;
		for (int i = 0; i < this.n; i++) {
			t = this.state[r][0];
			if (t != 0) {
				for (int j = i+1; j < this.n; j++) {
					if (t == this.state[r][j]) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * check if column does not contain duplicates
	 */
	public boolean checkDuplicatesColumn(int c) {
		int t = 0;
		for (int i = 0; i < this.n; i++) {
			t = this.state[0][c];
			if (t != 0) {
				for (int j = i+1; j < this.n; j++) {
					if (t == this.state[j][c]) {
						return false;
					}
				}
			}
		}
		return true;
	}
	/*
	 * check state-matrix: is it (uniquely) solvable? 
	 */
	public Solvability checkValidState() {
		/*
		 * check duplicates
		 */
		for (int i = 0; i < this.n; i++) {
			if (!checkDuplicatesRow(i)) {
				System.out.println("found duplicates in row " +i); 
				return Solvability.UNSOLVABLE;
			}
			if (!checkDuplicatesColumn(i)) {
				System.out.println("found duplicates in column " +i);
				return Solvability.UNSOLVABLE;
			}
		}
		/* 
		 * check str8ts row by row / column by column
		 */
		
		for (int i = 0; i < this.n; i++) {
			ArrayList<Integer> s = new ArrayList<Integer>();
			boolean unique = true;
			// check rows
			for (int j = 0; j < this.n; j++) {
				// black field
				if (this.solution[i][j] <= 0) {
					if (s.size()> 1) {
						Solvability solvable = checkStr8t(Helper.listToArray(s));
						if (solvable == Solvability.UNSOLVABLE) {
							System.out.println("invalid str8 in row " +i);
							return Solvability.UNSOLVABLE;
						} else if(solvable == Solvability.SOLVABLE) unique = false;
					}
					s.clear();
				} else {
					// not zero here and white cell
					s.add(this.state[i][j]);
				}
			}
			if (s.size()> 1) {
				Solvability solvable = checkStr8t(Helper.listToArray(s));
				if (solvable == Solvability.UNSOLVABLE) {
					System.out.println("invalid s in row " +i);
					return Solvability.UNSOLVABLE;
				} else if(solvable == Solvability.SOLVABLE) unique = false;
			}
			s.clear();
			// check columns
			for (int j = 0; j < this.n; j++) {
				// black cell
				if (this.solution[j][i] <= 0) {
					if (s.size()> 1) {
						Solvability solvable = checkStr8t(Helper.listToArray(s));
						if (solvable == Solvability.UNSOLVABLE) {
							System.out.println("invalid in col " +i);
							return Solvability.UNSOLVABLE;
						} else if(solvable == Solvability.SOLVABLE) unique = false;
					}
					s.clear();
				} else {
					// not zero here and white cell
					s.add(this.state[j][i]);
				}
			}
			if (s.size()> 1) {
				Solvability solvable = checkStr8t(Helper.listToArray(s));
				if (solvable == Solvability.UNSOLVABLE) {
					System.out.println("invalid " +i);
					return Solvability.UNSOLVABLE;
				} else if(solvable == Solvability.SOLVABLE) unique = false;
			}
			s.clear();
		}
		return Solvability.SOLVABLE;
	}
}
