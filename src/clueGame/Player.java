package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public abstract class Player {
	private String name;
	private int row;
	private int col;
	private Color color;
	protected List<Card> myCards;
	protected List<Card> seenCards;
	
	public Player(String name, Color color, int row, int col){
	    this.name = name;
	    this.color = color;
	    this.row = row;
	    this.col = col;
	    
	    myCards = new ArrayList<Card>();
//	    seenCards = new ArrayList<Card>();
	}

	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public void addCard(Card card) {
		myCards.add(card);
	}
	
	public Color convertColor(String strColor) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null;
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

	public Color getColor() {
		return color;
	}
	
	public List<Card> getCards() {
		return myCards;
	}
}
