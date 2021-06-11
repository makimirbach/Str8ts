package application.service;

import java.util.ArrayList;
import java.util.LinkedList;

import application.constants.CellType;

public class Str8tSolver {
	//private LinkedList<Entry> llstSolution = new LinkedList<Entry>();
	private Cell[][] state;
	private Cell[][] solution;
	private int n;
	private LinkedList<Street> streets;
	
	
	public Str8tSolver(Cell[][] state, Cell[][] solution, int n) {
		this.state = state;
		this.solution = solution;
		this.n = n;
		this.streets = getAllStreets();
		for (Cell[] cells: this.state) {
			for (Cell c: cells)	{
				c.setStreets(cellInStreets(c));
			}
		}
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
	public LinkedList<Street> getStreets() {
		return streets;
	}
	public void setStreets(LinkedList<Street> streets) {
		this.streets = streets;
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
	 * init: get all streets from initial state
	 */
	public LinkedList<Street> getAllStreets() {
		LinkedList<Street> streets = new LinkedList<Street>();
		LinkedList<Cell> streetCells = new LinkedList<Cell>();
		
		
		for (int i = 0; i < this.n; i++) {
			// horizontal
			for (int j = 0; j < this.n; j++) {
				Cell currentCell = this.state[i][j];
				if (currentCell.getCellType() == CellType.WHITE) {
					streetCells.add(currentCell);
				} else if (streetCells.size() > 0) {
					streets.add(new Street(Helper.cellListToArray(streetCells), this.n, true));
					streetCells.clear();
				}
			}
			if (streetCells.size() > 0) streets.add(new Street(Helper.cellListToArray(streetCells), this.n, true));
			streetCells.clear();
			
			// vertical
			for (int j = 0; j < this.n; j++) {
				Cell currentCell = this.state[j][i];
				if (currentCell.getCellType() == CellType.WHITE) {
					streetCells.add(currentCell);
				} else if (streetCells.size() > 0) {
					streets.add(new Street(Helper.cellListToArray(streetCells), this.n, false));
					streetCells.clear();
				}
			}
			if (streetCells.size() > 0) streets.add(new Street(Helper.cellListToArray(streetCells), this.n, false));
			streetCells.clear();
		}
		
		return streets;
	}
	
	/*
	 * get list of streets a cell is contained in
	 */
	public Street[] cellInStreets(Cell cell) {
		Street[] t = new Street[2];
		for (Street s: this.streets) {
			if (s.containsCell(cell)) {
				if (s.isHorizontal()) t[0] = s;
				else t[1] = s;
			}
		}
		return t;
		
	}
	
	
	/*
	 * which numbers are blocked for all entrys in steet s 
	 */
	public ArrayList<Integer> blockedInStreet(Street s) {
		int r = s.getState()[0].getX();
		int c = s.getState()[0].getY();
		ArrayList<Integer> blocked = new ArrayList<Integer>();
		if (s.isHorizontal()) {
			//horizontal street
			for (int i = 0; i < this.n; i++) {
				if (!s.containsCell(this.state[r][i])) {
					if (this.state[r][i].getEntry() != 0) blocked.add(this.state[r][i].getEntry());
					if (this.state[r][i].getCellType() == CellType.WHITE) {
						Street streets = cellInStreets(this.state[r][i])[0];
						ArrayList<Integer> liste = streets.getMissing();
						for (int b: liste) {
							if (!blocked.contains(b)) blocked.add(b);
						}
					}
				}
			} 
		} else {
			//vertical street
			for (int i = 0; i < this.n; i++) {
				if (!s.containsCell(this.state[i][c])) {
					if (this.state[i][c].getEntry() != 0) blocked.add(this.state[c][c].getEntry());
					if (this.state[i][c].getCellType() == CellType.WHITE) {
						Street streets = cellInStreets(this.state[i][c])[1];
						ArrayList<Integer> liste = streets.getMissing();
						for (int b: liste) {
							if (!blocked.contains(b)) blocked.add(b);
						}
					}
				}
			}
		}
		return blocked;
	}
}
