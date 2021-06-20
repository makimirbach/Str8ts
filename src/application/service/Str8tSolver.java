package application.service;

import java.util.ArrayList;
import java.util.LinkedList;

import application.constants.CellType;

public class Str8tSolver {
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
	 * get streets in same row (horizontal) or column respectively
	 */
	public ArrayList<Street> streetsInLine(Street s) {
		int r = s.getState()[0].getX();
		int c = s.getState()[0].getY();
		ArrayList<Street> streets = new ArrayList<Street>();
		if (s.isHorizontal()) {
			//horizontal street
			for (int i = 0; i < this.n; i++) {
				if (!s.containsCell(this.state[r][i])) {
					if (this.state[r][i].getCellType() == CellType.WHITE) {
						Street street = cellInStreets(this.state[r][i])[0];
						streets.add(street);
						i += street.getLength();
					}
				}
			} 
		} else {
			//vertical street
			for (int i = 0; i < this.n; i++) {
				if (!s.containsCell(this.state[i][c])) {
					if (this.state[i][c].getCellType() == CellType.WHITE) {
						Street street = cellInStreets(this.state[i][c])[1];
						streets.add(street);
						i += street.getLength();
					}
				}
			}
		}
		return streets;
	}
	
	/*
	 * which numbers are blocked for all entrys in steet s 
	 */
	public void blockedInStreet(Street s) {
		int r = s.getState()[0].getX();
		int c = s.getState()[0].getY();
		ArrayList<Integer> blocked = new ArrayList<Integer>();
		if (s.isHorizontal()) {
			//horizontal street
			for (Street blockedStreet: streetsInLine(s)) {
				blocked.addAll(blockedStreet.getMissing());
				blocked.addAll(blockedStreet.getEntries());
			}
			for (int i = 0; i < this.n; i++) {
				if (!s.containsCell(this.state[r][i]) && this.state[r][i].getCellType() == CellType.BLACK && this.state[r][i].getEntry() > 0) {
					blocked.add(this.state[r][i].getEntry());
				}
			} 
		} else {
			//vertical street
			for (Street blockedStreet: streetsInLine(s)) {
				blocked.addAll(blockedStreet.getMissing());
				blocked.addAll(blockedStreet.getEntries());
			}
			for (int i = 0; i < this.n; i++) {
				if (!s.containsCell(this.state[i][c]) && this.state[i][c].getCellType() == CellType.BLACK && this.state[i][c].getEntry() > 0) {
					blocked.add(this.state[i][c].getEntry());
				}
			} 
			
		}
		// remove duplicates
		blocked = Helper.deleteDuplicates(blocked);
		s.setBlocked(blocked);
	}
	
	/*
	 * considering already blocked entries, what is max range for this street
	 */
	public ArrayList<Integer> unblockedRange(Street s) {
		ArrayList<Integer> unblocked = new ArrayList<Integer>();
		ArrayList<Integer> blocked = s.getBlocked(); // sorted from min to max
		
		if (s.getMax() > 0) {
			// know some entries
			int min = 0;
			int max = s.getN()+1; // exklusive
			if (s.getMin() > 0) {
				for (int b: blocked) {
					if (b > min && b < s.getMin()) min = b;
				}
			}
			for (int b: blocked) {
				if (b < max && b > s.getMax()) max = b;
			}
			// combine unblocked
			for (int i = min + 1; i < max; i++) {
				unblocked.add(i);
			}
		} else {
			// consider length
			ArrayList<Integer> lengths = Helper.getLengthsBetweenBlocked(blocked, s.getN());
			for (int i = 0; i < lengths.size(); i++) {
				if (lengths.get(i) >= s.getLength()) {
					if (i == 0) {
						for (int u = 1; u < blocked.get(i); u++) {
							unblocked.add(u);
						}
					} else if (i == lengths.size() - 1){
						for (int u = blocked.get(i-1) +1; u <= s.getN(); u++) {
							unblocked.add(u);
						}
					} else {
						for (int u = blocked.get(i-1) + 1; u < blocked.get(i); u++) {
							unblocked.add(u);
						}
					}
				}
			}
		}
		
		return unblocked;
		
	}
	
	/*
	 * set all possible numbers in a streets
	 */
	public void possibleInStreet(Street s) {
	    int r = s.getState()[0].getX();
	    int c = s.getState()[0].getY();
	    ArrayList<Integer> possible = new ArrayList<Integer>();
	    
	    if (s.getMax() > 0 && s.getUnentered() == 1) {
    	
    		if (s.getLength() > s.getMax() - s.getMin() + 1) {
    			if (!s.getBlocked().contains(s.getMin() - 1) && s.getMin() - 1 > 0) {
    				possible.add(s.getMin() - 1);
    			}
    			if (!s.getBlocked().contains(s.getMax() +1) && s.getMax() + 1 <= s.getN()) {
    				possible.add(s.getMax()+1);	
    			}
    			
    			
    		} else {
    			System.out.println("enter unique missing entry");
    		}
	    } 
		ArrayList<Integer> unblocked = unblockedRange(s);
		// consider length of street
		for (int i = 0; i < unblocked.size();i++) {
			if ((s.getMin() > 0 && unblocked.get(i) < s.getMin() - s.getUnentered()) || (s.getMax() > 0 && unblocked.get(i) > s.getMax() + s.getUnentered())) {
				unblocked.remove(i);
				i--;
			}
		}
		for (int u: unblocked) {
			if (!s.getMissing().contains(u) && !s.getEntries().contains(u) ) possible.add(u);
		}
    	
		// remove duplicates
		possible = Helper.deleteDuplicates(possible);
	    s.setPossible(possible);
	    // if #possible + #missing = #unentered, all are missing
	    if (s.getPossible().size() + s.getMissing().size() == s.getUnentered()) {
	    	ArrayList<Integer> missing = s.getMissing();
	    	missing.addAll(possible);
	    	s.setMissing(missing);
	    	possible.clear();
	    	s.setPossible(possible);
	    }
	}
	
	public void printState() {
		for (int i= 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				System.out.print((this.solution[i][j].getEntry()!= 0)?this.state[i][j].getEntry():"x");
			}
			System.out.println();
		}
		System.out.println();
	}
}
