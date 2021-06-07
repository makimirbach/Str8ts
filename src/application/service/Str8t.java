package application.service;


import java.util.ArrayList;
import java.util.Collections;

public class Str8t {
	private int n; // size
	private int[][] solution; // 0 on black cells, negative for white number on black field, positive for black numbers on white
	private int[][] state; // positive for already visible numbers (on black & white)
	private ArrayList<Integer>[][] notes;
	
	
	public Str8t(int n, int[][] solution, int[][] state) {
		this.n = n;
		this.solution = solution;
		this.state = state;
		this.notes = new ArrayList[this.n][this.n];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				notes[i][j] = new ArrayList<Integer>();
			}
		}
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
	public ArrayList<Integer>[][] getNotes() {
		return notes;
	}
	public void setNotes(ArrayList<Integer>[][] notes) {
		this.notes = notes;
	}
	
	
	
	public void updateNotes(int r, int c, String note) {
		ArrayList<Integer> noteList = new ArrayList<Integer>();
		for (char x: note.toCharArray()) {
			if (Character.isDigit(x)) {
				int t = Integer.parseInt(String.valueOf(x));
				if (t > 0 && t <= this.n && !noteList.contains(t))	noteList.add(t);
			}
		}
		Collections.sort(noteList);
		ArrayList<Integer>[][] newNotes = this.notes;
		newNotes[r][c] = noteList;
		setNotes(newNotes);
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
	
	public boolean enterNumber(int r, int c, int x) {
		int[][] newState = this.state;
		if (x > 0 && x <= this.n) {
			newState[r][c] = x;
			setState(newState);
			return true;
		}
		newState[r][c] = 0;
		setState(newState);
		return false;
	}
	
	/*
	 * concatenate notes(ints) to string
	 */
	public String getNotesString(int r, int c) {
		String s = "";
		for (int i: this.notes[r][c]) {
			s+= Integer.toString(i);
		}
		return s;
	}
	
	public boolean gameOver() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				if (this.solution[i][j] > 0 && this.state[i][j] != this.solution[i][j] ) return false;
			}
		}
		return true;
	}
	
	public boolean checkNumberCorrect(int r, int c) {
		if ((this.state[r][c] == this.solution[r][c] && this.state[r][c] != 0) || this.solution[r][c] < 0) return true;
		return false;
	}
	
	
	
}
