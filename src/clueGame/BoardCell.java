package clueGame;

public class BoardCell implements Comparable<BoardCell>{
	private int row, column;
	private char initial = ' ';
	private boolean isDoorway = false;
	private boolean isWalkway = false;
	private boolean isRoom = false;
	private DoorDirection doorDirection = DoorDirection.NONE;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public BoardCell(int row, int column, char myChar) {
		super();
		this.row = row;
		this.column = column;
		this.initial = myChar;
		if (myChar == 'W'){
			isWalkway = true;
		}
		else if (myChar != 'X')
			isRoom = true;

	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public void setToDoorway(DoorDirection doorDirection) {
		if (!(doorDirection == DoorDirection.NONE)) {
			isDoorway = true;
			this.doorDirection = doorDirection;
		}
	}

	public boolean isWalkway() {
		return isWalkway;
	}

	public boolean isRoom() {
		return isRoom;
	}

	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}

	public char getInitial() {
		return initial;
	}

	public int compareTo(BoardCell other) {
		return (row * 100 + column) - (other.row * 100 + other.column);
	}

}
