package application.service;

import java.util.ArrayList;

import application.constants.Solvability;

public class Street {
	
	private int n;
	private int length;
	private boolean horizontal;
	
	private Cell[] state;
	
	private ArrayList<Integer> missing; // for sure
	private ArrayList<Integer> possible; 
	private ArrayList<Integer> blocked; 
	
	public Street(Cell[] state, int n, boolean horizontal) {
		setState(state);
		setN(n);
		setHorizontal(horizontal);
		setLength(state.length);
		setMissing(new ArrayList<Integer>());
		setPossible(new ArrayList<Integer>());
	}
	/*
	 * getter & setter
	 */
	public int getMin() {
		int minValue = getMax();
		for (int i = 0; i < this.length; i++) {
			if (this.state[i].getEntry() < minValue && this.state[i].getEntry() > 0) {
				minValue = this.state[i].getEntry();
			}
		}
		return minValue;
	}
	
	public int getMax() {
		if (this.state.length == 0) return 0;
		int maxValue = this.state[0].getEntry();
		for (int i = 1; i < this.length; i++) {
			if (this.state[i].getEntry() > maxValue) {
				maxValue = this.state[i].getEntry();
			}
		}
		return maxValue;
	}
	
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Cell[] getState() {
		return state;
	}
	public void setState(Cell[] state) {
		this.state = state;
	}
	public int getEntered() {
		int count = 0;
		for (int i = 0; i < this.length; i++) {
			if (this.state[i].getEntry() != 0) count++;
		}
		return count;
	}
	
	public int getUnentered() {
		return this.length - getEntered();
	}
	
	
	
	
	public ArrayList<Integer> getEntries() {
		ArrayList<Integer> entries = new ArrayList<Integer>();
		for (Cell c: this.state) {
			if (c.getEntry()!= 0) entries.add(c.getEntry());
		}
		return entries;
	}
	
	public boolean isHorizontal() {
		return horizontal;
	}
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	public ArrayList<Integer> getMissing() {
		return missing;
	}
	public void setMissing(ArrayList<Integer> missing) {
		this.missing = missing;
	}
	
	public ArrayList<Integer> getBlocked() {
		return blocked;
	}
	public void setBlocked(ArrayList<Integer> blocked) {
		this.blocked = blocked;
	}
	public ArrayList<Integer> getPossible() {
		return possible;
	}
	public void setPossible(ArrayList<Integer> possible) {
		this.possible = possible;
	}
	/*
	 * check if str8t s is (uniquely) solvable i.e. contains only consecutive numbers
	 */
	public Solvability checkStr8t() {
		int min = getMin(); // smallest number > 0 
		int max = getMax(); 
		
		if (max == 0) return Solvability.SOLVABLE; // nothing entered yet
		
		if (max-min+1 > this.length) {
			return Solvability.UNSOLVABLE;
		} else {
			int entered = this.getEntered(); // > 0
			int unentered = this.length - entered; //  number of zeros in s
			// uniquely when all entered or only one missing and only one choice left
			if (unentered == 0 || (unentered == 1 && (min == 1 || max == this.n || max-min+1 == this.length))) return Solvability.UNIQUELY_SOLVABLE;
			else return Solvability.SOLVABLE;
		}
	}
	
	/*
	 * which numbers are missing in this street FOR SURE?
	 */
	public void findMissing() {
		ArrayList<Integer> missing = new ArrayList<Integer>();
		if (this.getMax() != 0) {
			// something known yet
			if (this.getMax() - this.getMin() + 1 == this.length || this.getMin() == 1 || this.getMax() == this.n || this.length == this.n) {
				// exakt set of all numbers know
				int min = getMin();
				if (this.getMax() == n) min = (min > 0 && min  == this.getMax() - this.length + 1) ? min : this.getMax() - this.length + 1;
				if (this.length == this.n) min = 1;
				boolean[] enteredNumbers = new boolean[this.length];
				for (Cell c: this.state) {
					if (c.getEntry() != 0) enteredNumbers[c.getEntry() - min] = true;
				}
				
				for (int i = 0; i < this.length; i++) {
					if (!enteredNumbers[i]) missing.add(i + min);
				}
			} else if (this.getMin() != 0) {
				// don't know exakt range
				boolean[] enteredNumbers = new boolean[this.getMax() - this.getMin() + 1];
				for (Cell c: this.state) {
					if (c.getEntry() != 0) enteredNumbers[c.getEntry() - this.getMin()] = true;
				}
				
				for (int i = 0; i < this.getMax() - this.getMin() + 1; i++) {
					if (!enteredNumbers[i]) missing.add(i + this.getMin());
				}
			}
		}
		setMissing(missing);
	}
	
	/*
	 * if only one missing and one unentered, enter it
	 */
	public void checkCanEnterMissing() {
		if (this.missing.size() == 1 && this.getUnentered() == 1) {
			for (Cell c: this.state) {
				if (c.getEntry() == 0) {
					c.setEntry(this.missing.get(0));
					this.missing.remove(0);
				}
			}
		}
	}
	
	/*
	 * check if given cell is contained in this street
	 */
	public boolean containsCell(Cell cell) {
		for (Cell c: this.state) {
			if (c == cell) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "\nStreet: " + Helper.getEntriesToString(this.state) +  ", starting in (" + this.getState()[0].getX() + "," + this.getState()[0].getY() + ") blocked: " + this.getBlocked() + " missing " + this.getMissing();
	}
	
	
}
