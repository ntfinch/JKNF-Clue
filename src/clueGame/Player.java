package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public abstract class Player {
	private String name;
	protected int row;
	protected int col;
	private Color color;
	protected List<Card> myCards;
	protected List<Card> seenCards;
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setRow(int row) {
		this.row = row;
	}
	  public Player() {}
	public Player(String name, Color color, int row, int col){
	    this.name = name;
	    this.color = color;
	    this.row = row;
	    this.col = col;
	    
	    myCards = new ArrayList<Card>();
	    seenCards = new ArrayList<Card>();
	}

	public abstract Card disproveSuggestion(Solution suggestion);
	
	public void addCard(Card card) {
		myCards.add(card);
	}
	
	public Color convertColor(String strColor) {
		Color convColor;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			convColor = (Color)field.get(null);
		} catch (Exception e) {
			convColor = null;
		}
		return color;
	}

	public String getName() {
		return name;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return col;
	}
	
	public void setRoom(int r, int c) {
		row = r;
		col = c;
	}

	public Color getColor() {
		return color;
	}
	
	public List<Card> getCards() {
		return myCards;
	}
	
	public void setCards(List<Card> cards) {
		myCards = cards;
	}

	public List<Card> getSeenCards() {
		return seenCards;
	}
	
	@Override
	public String toString(){
		return this.getClass() + ": " + name + ". Hand: " + myCards.toString();
	}
	

	
	public void playerMove(BoardCell nextMove) {
		this.row = nextMove.getRow();
		this.col = nextMove.getColumn();
	}
	
	//GuI
	public abstract void moveNeedsToBeMade(Board b);
	  public void drawPlayer(Graphics g, Board board)
	  {
	    int playerSize = BoardCell.CELL_SIZE;
	    int x = this.col * playerSize;
	    int y = this.row * playerSize;
	 
	    g.setColor(this.color);
	    g.fillOval(x, y, playerSize, playerSize);
	    g.setColor(Color.BLACK);
	    g.drawOval(x, y, playerSize, playerSize);
	  }


}
