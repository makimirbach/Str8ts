package application.service;

import application.constants.Solvability;

public class Street {
	private int min;
	private int max;
	private int n;
	
	private Cell[] state;
	private int entered;
	private int unentered;
	public Street(Cell[] state, int n) {
		setState(state);
		setMin(Helper.getRealMin(Helper.getEntries(state)));
		setMax(Helper.getMax(Helper.getEntries(state)));
		setEntered(Helper.getEntered(Helper.getEntries(state))); // > 0
		setUnentered(state.length - this.entered); //  number of zeros in s
		setN(n);
	}
	/*
	 * getter & setter
	 */
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public Cell[] getState() {
		return state;
	}
	public void setState(Cell[] state) {
		this.state = state;
	}
	public int getEntered() {
		return entered;
	}
	public void setEntered(int entered) {
		this.entered = entered;
	}
	public int getUnentered() {
		return unentered;
	}
	public void setUnentered(int unentered) {
		this.unentered = unentered;
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
	
	@Override
	public String toString() {
		return "Street: entered:" + this.entered + ", unentered:" + this.unentered + ", min: " + this.min + ", max: " + this.max;
	}
	
}
