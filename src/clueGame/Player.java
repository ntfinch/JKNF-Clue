package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.List;


public abstract class Player {
	private String name;
	private int row;
	private int col;
	private Color color;
	private List<Card> myCards;
	private List<Card> seenCards;
	
	public Player(String name, Color color, int row, int col){
	    this.name = name;
	    this.color = color;
	    this.row = row;
	    this.col = col;
	}

	public Card disproveSuggestion(Solution suggestion) {
		return null;
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

//	public void setName(String playerName) {
//		this.playerName = playerName;
//	}
//
	public int getRow() {
		return row;
	}
//
//	public void setRow(int row) {
//		this.row = row;
//	}
//
	public int getColumn() {
		return col;
	}
//
//	public void setColumn(int column) {
//		this.column = column;
//	}
//
	public Color getColor() {
		return color;
	}
//
//	public void setColor(Color color) {
//		this.color = color;
//	}
}
