package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	Card(CardType type, String cardName) {
		this.type = type;
		this.cardName = cardName;
	}
	
	public boolean equals() {
		return false;
	}
	
	public CardType getType() {
		return type;
	}
	
	public String getName() {
		return cardName;
	}
}
