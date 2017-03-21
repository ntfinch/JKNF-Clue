package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	private boolean dealt;
	
	public Card(CardType type, String cardName) {
		this.type = type;
		this.cardName = cardName;
		dealt = false;
	}
	
	@Override
	public boolean equals(Object object) {
		return
				object instanceof Card &&
				((Card) object).getName() == cardName &&
				((Card) object).getType() == type &&
				((Card) object).hasBeenDealt() == dealt;
	}
	
	public CardType getType() {
		return type;
	}
	
	public String getName() {
		return cardName;
	}
	
	public boolean hasBeenDealt() {
		return dealt;
	}
	
	public void dealToPlayer(Player player) {
		player.addCard(this);
		dealt = true;
	}
}
