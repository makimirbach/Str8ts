package application.service;

import java.util.ArrayList;
import java.util.Collections;

import application.constants.CellType;

public class Cell {
	private int x;
	private int y;
	private Street[] streets;
	private int entry;
	private CellType cellType;
	private ArrayList<Integer> notes;
	
	
	public Cell(int x, int y, Street[] streets, int entry, CellType cellType) {
		setX(x);
		setY(y);
		setStreets(streets);
		setEntry(entry);
		setCellType(cellType);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Street[] getStreets() {
		return streets;
	}
	public void setStreets(Street[] streets) {
		this.streets = streets;
	}
	public int getEntry() {
		return entry;
	}
	public void setEntry(int entry) {
		this.entry = entry;
	}
	public CellType getCellType() {
		return cellType;
	}
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	public ArrayList<Integer> getNotes() {
		return notes;
	}
	public void setNotes(ArrayList<Integer> notes) {
		this.notes = notes;
	}
	
	public void updateNotes(String note, int n) {
		ArrayList<Integer> noteList = new ArrayList<Integer>();
		for (char x: note.toCharArray()) {
			if (Character.isDigit(x)) {
				int t = Integer.parseInt(String.valueOf(x));
				if (t > 0 && t <= n && !noteList.contains(t))	noteList.add(t);
			}
		}
		Collections.sort(noteList);
		ArrayList<Integer> newNotes = this.notes;
		newNotes = noteList;
		setNotes(newNotes);
	}
	
	/*
	 * concatenate notes(ints) to string
	 */
	public String getNotesString() {
		String s = "";
		for (int i: this.notes) {
			s+= Integer.toString(i);
		}
		return s;
	}
	

	/*
	 * try to enter number x here
	 */
	public boolean enterNumber(int x, int n) {
		int newState = this.entry;
		if (x > 0 && x <= n) {
			newState = x;
			setEntry(newState);
			return true;
		}
		newState = 0;
		setEntry(newState);
		return false;
	}
	
	@Override
	public String toString() {
		return "Cell: entry: " + this.entry + ", position " + this.x + ", " +this.y + ", type: " + this.cellType;
	}
	
	
	
}
