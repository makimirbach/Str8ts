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
	 * which numbers are missing in this street FOR SURE?
	 */
	public void findMissing(Street s) {
		if (s.getMissing().size() < s.getUnentered()) {
			ArrayList<Integer> missing = new ArrayList<Integer>();
			if (s.getMax() != 0) {
				// something known yet
				if (s.getMax() - s.getMin() + 1 == s.getLength() || s.getMin() == 1 || s.getMax() == s.getN() || s.getLength() == s.getN()) {
					// exakt set of all numbers know
					int min = s.getMin();
					if (s.getMax() == n) min = (min > 0 && min  == s.getMax() - s.getLength() + 1) ? min : s.getMax() - s.getLength() + 1;
					if (s.getLength() == s.getN()) min = 1;
					boolean[] enteredNumbers = new boolean[s.getLength()];
					for (Cell c: s.getState()) {
						if (c.getEntry() != 0) enteredNumbers[c.getEntry() - min] = true;
					}
					
					for (int i = 0; i < s.getLength(); i++) {
						if (!enteredNumbers[i]) missing.add(i + min);
					}
				} else if (s.getLength() > this.n / 2) {
					int min = s.getMin();
					min = (min > 0)? Integer.min(min, this.n - s.getLength() + 1) : s.getN() - s.getLength() + 1;
					int max = s.getMax();
					max = Integer.max(max, s.getLength()) + 1;
					boolean[] enteredNumbers = new boolean[max-min+1];
					for (Cell c: s.getState()) {
						if (c.getEntry() != 0) enteredNumbers[c.getEntry() - min] = true;
					}
					for (int i = min; i < max; i++) {
						if (!enteredNumbers[i-min]) missing.add(i);
					}
				} else if (s.getMin() != 0) {
					// don't know exakt range
					boolean[] enteredNumbers = new boolean[s.getMax() - s.getMin() + 1];
					for (Cell c: s.getState()) {
						if (c.getEntry() != 0) enteredNumbers[c.getEntry() - s.getMin()] = true;
					}
					
					for (int i = 0; i < s.getMax() - s.getMin() + 1; i++) {
						if (!enteredNumbers[i]) missing.add(i + s.getMin());
					}
				}
			}
			s.setMissing(missing);
		}
	}
	/*
	 * which streets are affected by changing cell c in street s
	 */
	public ArrayList<Street> getConcernedSteets(Street s, Cell c) {
		ArrayList<Street> concernedStreets = new ArrayList<Street>();
		Street otherStreet = (s.isHorizontal() ? cellInStreets(c)[1] : cellInStreets(c)[0]);
		concernedStreets.add(otherStreet);
		concernedStreets.addAll(streetsInLine(s));
		concernedStreets.addAll(streetsInLine(otherStreet));
		return concernedStreets;
	}
	
	/*
	 * if only one missing and one unentered, enter it. remove it from missing (and possible of other street(s))
	 */
	public void checkCanEnterMissing(Street s) {
		if (s.getMissing().size() == 1 && s.getUnentered() == 1) {
			for (Cell c: s.getState()) {
				if (c.getEntry() == 0) {
					int entry = s.getMissing().get(0);
					c.setEntry(entry);
					ArrayList<Street> concernedStreets = getConcernedSteets(s, c);
					for (Street cs: concernedStreets) {
						int indexOtherMissing = cs.getMissing().indexOf(entry);
						if (indexOtherMissing>=0) {
							ArrayList<Integer> missing = cs.getMissing();
							missing.remove(indexOtherMissing);
							cs.setMissing(missing);
						}
						HandlePossibleChange.removePossible(cs, entry);
					}
					s.getMissing().remove(0);
				}
			}
		}
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
		if (s.getMissing().size() < s.getUnentered()) {
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
	    	
		    s.setPossible(HandlePossibleChange.addPossible(s, possible));
		    // if #possible + #missing = #unentered, all are missing
		    if (s.getPossible().size() + s.getMissing().size() == s.getUnentered()) {
		    	s.setMissing(HandleMissingChange.addMissing(s, s.getPossible()));
		    	possible.clear();
		    	s.setPossible(possible);
		    }
		}
	}
	
	/*
	 * check if missing numbers position is determined by orthogonal streets
	 */
	public void checkMissingOrthogonally(Street s) {
		for (int j = 0; j < s.getMissing().size(); j++) {
			int m = s.getMissing().get(j);
			int counter = 0;
			ArrayList<Boolean> possible = new ArrayList<Boolean>();
			for (int i = 0; i < s.getState().length; i++) {
				Cell c = s.getState()[i];
				Street otherStreet = (s.isHorizontal() ? cellInStreets(c)[1] : cellInStreets(c)[0]);
				if (otherStreet.getPossible().contains(m) || otherStreet.getMissing().contains(m)) {
					possible.add(true);
					counter++;
				} else {
					possible.add(false);
				}
			}
			if (counter == 1) {
				s.getState()[possible.indexOf(true)].setEntry(m);
				s.setMissing(HandleMissingChange.removeMissing(s, m));
				j--;
			}
			possible.clear();
		}
	}
	
	/*
	 * check if possible numbers can be excluded by checking orthogonal streets
	 */
	public void checkPossibleOrthogonally(Street s) {
		for (int j = 0; j < s.getPossible().size(); j++) {
			int m = s.getPossible().get(j);
			int counter = 0;
			for (int i = 0; i < s.getState().length; i++) {
				Cell c = s.getState()[i];
				Street otherStreet = (s.isHorizontal() ? cellInStreets(c)[1] : cellInStreets(c)[0]);
				if (otherStreet.getPossible().contains(m) || otherStreet.getMissing().contains(m)) {
					counter++;
				} 
			}
			if (counter == 0) {
				s.setPossible(HandlePossibleChange.removePossible(s, m));
				j--;
			}
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
