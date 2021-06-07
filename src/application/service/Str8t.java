package application.service;




public class Str8t {
	private int n; // size
	private Cell[][] solution; // 0 on black cells, negative for white number on black field, positive for black numbers on white
	private Cell[][] state; // positive for already visible numbers (on black & white)
	
	
	public Str8t(int n, Cell[][] solution, Cell[][] state) {
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
	public Cell[][] getSolution() {
		return solution;
	}
	public void setSolution(Cell[][] solution) {
		this.solution = solution;
	}
	public Cell[][] getState() {
		return state;
	}
	public void setState(Cell[][] state) {
		this.state = state;
	}
	
	
	
	public void print() {
		for (int i= 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				System.out.print(this.state[i][j].getEntry());
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	public boolean gameOver() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				if (this.solution[i][j].getEntry() > 0 && this.state[i][j] != this.solution[i][j] ) return false;
			}
		}
		return true;
	}
	
	// TODO
	public boolean checkNumberCorrect(int r, int c) {
		if ((this.state[r][c].getEntry() == this.solution[r][c].getEntry() && this.state[r][c].getEntry() != 0) || this.solution[r][c].getEntry() < 0) return true;
		return false;
	}
	
	
	
}
