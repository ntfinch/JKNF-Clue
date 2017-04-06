package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BoardCell implements Comparable<BoardCell>{
	private int row, column;
	private char initial = ' ';
	private boolean isDoorway = false;
	private boolean isWalkway = false;
	private boolean isRoom = false;
	private DoorDirection doorDirection = DoorDirection.NONE;
	Color blu = new Color(155, 194, 230);
	public static int CELL_SIZE = 34;
	protected int xPos, yPos;

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

	public void drawBoardCells(Graphics g){
		if(this.isWalkway)
			g.setColor(Color.GRAY);
		else
			g.setColor(blu);
		if(this.isDoorway)
			drawDoorway(g);
		 g.fillRect(this.row, this.column, PIECE_SIZE, PIECE_SIZE);
		    if ((isWalkway()) || (this.highlighted))
		    {
		      g.setColor(Color.black);
		      g.drawRect(this.x, this.y, PIECE_SIZE, PIECE_SIZE);
		    }
	}
	
	public void drawDoorway(Graphics g)
	  {
		
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

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
