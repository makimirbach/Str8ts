package application.service;

public class Entry {
	private int x;
	private int y;
	private int l; // labeled
	
	public Entry(int x, int y, int l) {
		super();
		setX(x);
		setY(y);
		setL(l);
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

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}
	
}
