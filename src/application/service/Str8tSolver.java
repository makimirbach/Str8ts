package application.service;

import java.util.ArrayList;
import java.util.LinkedList;

import application.constants.CellType;
import application.constants.Solvability;

public class Str8tSolver {
	//private LinkedList<Entry> llstSolution = new LinkedList<Entry>();
	private Cell[][] state;
	private Cell[][] solution;
	private int n;
	
	
	public Str8tSolver(Cell[][] state, Cell[][] solution, int n) {
		this.state = state;
		this.solution = solution;
		this.n = n;
	}
	

	public Cell[][] getState() {
		return state;
	}


	public void setState(Cell[][] state) {
		this.state = state;
	}


	public Cell[][] getSolution() {
		return solution;
	}


	public void setSolution(Cell[][] solution) {
		this.solution = solution;
	}


	public int getN() {
		return n;
	}


	public void setN(int n) {
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
			t = this.state[r][0].getEntry();
			if (t != 0) {
				for (int j = i+1; j < this.n; j++) {
					if (t == this.state[r][j].getEntry()) {
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
			t = this.state[0][c].getEntry();
			if (t != 0) {
				for (int j = i+1; j < this.n; j++) {
					if (t == this.state[j][c].getEntry()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	
	
	/*
	 * get list of streets a cell is contained in
	 */
	public ArrayList<Street> cellInStreets(Cell cell) {
		ArrayList<Street> streets = new ArrayList<Street>();
		LinkedList<Cell> streetCells = new LinkedList<Cell>();
		
		// horizontal
		// forwards
		for (int i = 0; i < this.n; i++) {
			if (cell.getY() + i >= 0 && cell.getY() + i < this.n) {	
				Cell currentCell = this.state[cell.getX()][cell.getY() + i];
				if (currentCell.getCellType() == CellType.WHITE) {
					streetCells.add(currentCell);
				} else break;
			} else break;
		}
		// backwards
		for (int i = 1; i < this.n; i++) {
			if (cell.getY() - i >= 0 && cell.getY() - i < this.n) { 
				Cell currentCell = this.state[cell.getX()][cell.getY() - i];
				if (currentCell.getCellType() == CellType.WHITE) {
					streetCells.push(currentCell);
				} else break;
			} else break;
		}
		
		streets.add(new Street(Helper.cellListToArray(streetCells), this.n));
		streetCells.clear();
		// vertical
		
		// down
		for (int i = 0; i < this.n; i++) {
			if (cell.getX() + i >= 0 && cell.getX() + i < this.n) {
				Cell currentCell = this.state[cell.getX() + i][cell.getY()];
				if (currentCell.getCellType() == CellType.WHITE) {
					streetCells.add(currentCell);
				} else break;
			} else break;
			
		}
		// backwards
		for (int i = 1; i < this.n; i++) {
			if (cell.getX() - i >= 0 && cell.getX() - i < this.n) {
				Cell currentCell = this.state[cell.getX() - i][cell.getY()];
				if (currentCell.getCellType() == CellType.WHITE) {
					streetCells.push(currentCell);
				} else break;
			} else break;
		}
		
		streets.add(new Street(Helper.cellListToArray(streetCells), this.n));
		streetCells.clear();
		// vertical
		
		
		return streets;
		
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
		boolean unique = true;
		for (int i = 0; i < this.n; i++) {
			ArrayList<Integer> s = new ArrayList<Integer>();
			
			// check rows
			for (int j = 0; j < this.n; j++) {
				// black field
				if (this.solution[i][j].getEntry() <= 0) {
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
					s.add(this.state[i][j].getEntry()); // add last
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
				if (this.solution[j][i].getEntry() <= 0) {
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
					s.add(this.state[j][i].getEntry());
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
		return ((unique) ? Solvability.UNIQUELY_SOLVABLE: Solvability.SOLVABLE);
	}
}
