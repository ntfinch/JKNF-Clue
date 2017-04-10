package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BoardCell implements Comparable<BoardCell> {
	private int row, column;
	private char initial = ' ';
	private boolean isDoorway = false, isWalkway = false, isRoom = false, showName = false;
	private DoorDirection doorDirection = DoorDirection.NONE;
	Color blu = new Color(155, 194, 230);
	public static int CELL_SIZE = 34;
	protected int xPos, yPos;

	public BoardCell(int row, int column, char myChar) {
		super();
		this.row = row;
		this.column = column;
		this.initial = myChar;
		if (myChar == 'W') {
			isWalkway = true;
		} else if (myChar != 'X')
			isRoom = true;
		xPos = row * CELL_SIZE;
		yPos = column * CELL_SIZE;

	}

	public void drawBoardCells(Graphics g) {
		if (this.isWalkway)
			g.setColor(Color.GRAY);
		else
			g.setColor(blu);

		

		// Draw individual cells
		g.fillRect(this.xPos, this.yPos, CELL_SIZE, CELL_SIZE);
		
		
		if (this.isDoorway){
			drawDoorway(g);

		}
		// Draw borders
		if (isWalkway()) {
			g.setColor(Color.BLACK);
			g.drawRect(this.xPos, this.yPos, CELL_SIZE, CELL_SIZE);
		}
		if (this.showName) {
			g.setColor(Color.GREEN);
			g.drawString(Board.getInstance().getRoomName(this.initial).toUpperCase(), this.xPos, this.yPos);
		}
	}

	public void drawDoorway(Graphics g) {
		g.setColor(Color.BLUE);
		int sideSize = CELL_SIZE;
		if (this.doorDirection == DoorDirection.LEFT) {
			for (int i = 0; i < 4; i++) {
				g.drawLine(this.xPos + i, this.yPos, this.xPos + i, this.yPos + sideSize);
			}
		} else if (this.doorDirection == DoorDirection.RIGHT) {
			for (int i = 0; i < 4; i++) {
				g.drawLine(this.xPos + sideSize - i, this.yPos, this.xPos + sideSize - i, this.yPos + sideSize);
			}
		} else if (this.doorDirection == DoorDirection.UP) {
			for (int i = 0; i < 4; i++) {
				g.drawLine(this.xPos, this.yPos + i, this.xPos + sideSize, this.yPos + i);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				g.drawLine(this.xPos, this.yPos + sideSize - i, this.xPos + sideSize, this.yPos + sideSize - i);
			}
		}
	}

	// Getters, setters, boolean testers
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

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
