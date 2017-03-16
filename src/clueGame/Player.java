package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.List;


public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private List<Card> myCards;
	private List<Card> seenCards;

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

	public String getPlayerName() {
		return playerName;
	}

//	public void setPlayerName(String playerName) {
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
		return column;
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
