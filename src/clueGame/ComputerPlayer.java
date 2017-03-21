package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, Color color, int row, int col) {
        super(name, color, row, col);
    }

    public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {}
	
	public Solution createSuggestion() {
		// Get room
		Board board = Board.getInstance();
		BoardCell cell = board.getCellAt(row, getColumn());
		String room = (board.getLegend()).get(cell.getInitial());
		
		// Sort cards
		List<Card> unseenCards = new ArrayList<Card>(myCards);
		unseenCards.removeAll(seenCards);
		List<Card> weapons = new ArrayList<Card>();
		List<Card> persons = new ArrayList<Card>();
		for (Card card : unseenCards) {
			CardType type = card.getType();
			if (type.equals(CardType.WEAPON)) {
				weapons.add(card);
			} else if (type.equals(CardType.PERSON)) {
				persons.add(card);
			}
		}
		
		// Get person
		String person = "";
		int personsSize = persons.size();
		if (personsSize == 1) {
			person = persons.get(0).getName();
		} else {
			person = persons.get(ThreadLocalRandom.current().nextInt(0, personsSize)).getName();
		}
		
		// Get weapon
		String weapon = "";
		int weaponsSize = weapons.size();
		if (weaponsSize == 1) {
			weapon = weapons.get(0).getName();
		} else {
			weapon = weapons.get(ThreadLocalRandom.current().nextInt(0, weaponsSize)).getName();
		}
		
		return new Solution(person, room, weapon);	
	}
}
