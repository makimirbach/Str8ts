package application;


import java.util.ArrayList;

public class Str8t {
	private int n; // size
	private int[][] solution;
	private int[][] state;
	
	public Str8t(int n, int[][] solution, int[][] state) {
		this.n = n;
		this.solution = solution;
		this.state = state;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int[][] getSolution() {
		return solution;
	}
	public void setSolution(int[][] solution) {
		this.solution = solution;
	}
	public int[][] getState() {
		return state;
	}
	public void setState(int[][] state) {
		this.state = state;
	}
	
	
	/*
	 * check if str8t s is valid i.e. contains only consecutive numbers
	 */
	public boolean checkStr8t(int[] s) {
		int min = Helper.getMin(s);
		int max = Helper.getMax(s);
		
		// not all numbers entered yet
		if (min == 0) {
			return true;
		}
		
		if (s.length != max - min + 1) {
			return false;
		}
		
		boolean[] ordered = new boolean[max - min + 1]; 
		for (int i = 0; i < ordered.length; i++) {
			ordered[s[i]-min] = true;
		}
		for (int i = 0; i < ordered.length;i++) {
			if (!ordered[i]) {
				return false;
			}
		}
		return true;
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
	public boolean checkValidState() {
		/*
		 * check duplicates
		 */
		for (int i = 0; i < this.n; i++) {
			if (!checkDuplicatesRow(i)) {
				System.out.println("found duplicates in row " +i); 
				return false;
			}
			if (!checkDuplicatesColumn(i)) {
				System.out.println("found duplicates in column " +i);
				return false;
			}
		}
		/* 
		 * check str8ts row by row / column by column
		 */
		for (int i = 0; i < this.n; i++) {
			ArrayList<Integer> s = new ArrayList<Integer>();
			// check rows
			for (int j = 0; j < this.n; j++) {
				// black field
				if (this.solution[i][j] <= 0) {
					if (s.size()> 1) {
						if (!checkStr8t(Helper.listToArray(s))) {
							System.out.println("invalid str8 in row " +i);
							return false;
						}
					}
					s.clear();
				} else {
					// not zero here and white field
					s.add(this.state[i][j]);
				}
			}
			if (s.size()> 1) {
				if (!checkStr8t(Helper.listToArray(s))) {
					System.out.println("invalid s in row " +i);
					return false;
				}
			}
			s.clear();
			// check columns
			for (int j = 0; j < this.n; j++) {
				// nothing entered yet or black field
				if (this.state[j][i] == 0 || this.solution[j][i] < 0) {
					if (s.size()> 1) {
						if (!checkStr8t(Helper.listToArray(s))) {
							System.out.println("invalid in col " +i);
							return false;
						}
					}
					s.clear();
				} else {
					// not zero here and white field
					s.add(this.state[j][i]);
				}
			}
			if (s.size()> 1) {
				if (!checkStr8t(Helper.listToArray(s))) {
					System.out.println("invalid " +i);
					return false;
				}
			}
			s.clear();
		}
		return true;
	}
	public void print() {
		for (int i= 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				System.out.print(this.state[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void enterNumber(int r, int c, int x) {
		if (x > 0 && x <= this.n) {
			int[][] newState = this.state;
			newState[r][c] = x;
			setState(newState);
		}
	}
	
}
