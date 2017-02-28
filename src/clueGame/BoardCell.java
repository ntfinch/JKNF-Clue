package clueGame;

public class BoardCell {
	private int row, column;
	private char initial;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	public boolean isDoorway(){
		return false;
	}
	public boolean isWalkway(){
		return false;
	}
	public boolean isRoom(){
		return false;
	}
	public DoorDirection getDoorDirection(){
		return null;
	}
	public char getInitial(){
		return initial;
	}
	
}
