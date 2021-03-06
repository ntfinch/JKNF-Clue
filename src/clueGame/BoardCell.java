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
	private boolean validMove;

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
		if(this.validMove)
			g.setColor(Color.CYAN);
		else
			if (this.isWalkway)
			g.setColor(Color.GRAY);
		else
			g.setColor(blu);

		if (this.isDoorway) {
			drawDoorway(g);

		}

		/*// Highlights if valid move

		if (Board.getInstance().getTargets().contains(this) && Board.getInstance().isHumanPlayer()) {
			g.setColor(Color.RED);
		}
/
 *
 */
		// Draw individual cells
		g.fillRect(this.xPos, this.yPos, CELL_SIZE, CELL_SIZE);

		// Draw borders
		if (isWalkway()) {
			g.setColor(Color.BLACK);
			g.drawRect(this.xPos, this.yPos, CELL_SIZE, CELL_SIZE);
		}
		g.setColor(Color.BLUE);
		g.drawString("Master Bedroom", 3 * CELL_SIZE, 4 * CELL_SIZE);
		g.drawString("Office", 11 * CELL_SIZE, 2 * CELL_SIZE);
		g.drawString("Library", 16 * CELL_SIZE, 2 * CELL_SIZE);
		g.drawString("Game Room", 20 * CELL_SIZE, 2 * CELL_SIZE);
		g.drawString("Music Room", 3 * CELL_SIZE, 17 * CELL_SIZE);
		g.drawString("Dining Room", 20 * CELL_SIZE, 11 * CELL_SIZE);
		g.drawString("Kitchen", 20 * CELL_SIZE, 20 * CELL_SIZE);
		g.drawString("Guest Bedroom", 1 * CELL_SIZE, 23 * CELL_SIZE);
		g.drawString("Pantry", 11 * CELL_SIZE, 24 * CELL_SIZE);

	}

	public void drawDoorway(Graphics g) {
		g.setColor(Color.YELLOW);
		int sideSize = CELL_SIZE;
		if (this.doorDirection == DoorDirection.LEFT) {
			for (int i = 0; i < 4; i++) {
				g.drawLine(this.xPos + i, this.yPos, this.xPos + i, this.yPos + sideSize + sideSize);
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

	public boolean isWalkway() {
		return isWalkway;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumn() {
		return column;
	}

	public void setValidMove(boolean validMove) {
		this.validMove = validMove;
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
