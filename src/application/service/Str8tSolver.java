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
					streets.add(new Street(Helper.cellListToArray(streetCells), this.n));
					streetCells.clear();
				}
			}
			if (streetCells.size() > 0) streets.add(new Street(Helper.cellListToArray(streetCells), this.n));
			streetCells.clear();
			
			// vertical
			for (int j = 0; j < this.n; j++) {
				Cell currentCell = this.state[j][i];
				if (currentCell.getCellType() == CellType.WHITE) {
					streetCells.add(currentCell);
				} else if (streetCells.size() > 0) {
					streets.add(new Street(Helper.cellListToArray(streetCells), this.n));
					streetCells.clear();
				}
			}
			if (streetCells.size() > 0) streets.add(new Street(Helper.cellListToArray(streetCells), this.n));
			streetCells.clear();
		}
		
		return streets;
	}
	
	/*
	 * get list of streets a cell is contained in
	 */
	public Street[] cellInStreets(Cell cell) {
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
		
		return Helper.streetListToArray(streets);
	}
	

	
}
