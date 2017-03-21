package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}

	public void makeAccusation() {
	}

	public void createSuggestion() {
	}

	// Used for testing with a fake random
	public Card disproveSuggestion(Solution suggestion, Random random) {
		List<Integer> matchingIndices = new ArrayList<Integer>();
		List<Card> hand = this.getCards();
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getName().equals(suggestion.getPerson())
					|| hand.get(i).getName().equals(suggestion.getRoom())
					|| hand.get(i).getName().equals(suggestion.getWeapon())) {
				matchingIndices.add(i);
			}
		}

		if (matchingIndices.size() == 0) {
			return null;
		} else {
			return hand.get(matchingIndices.get(random.nextInt(matchingIndices.size())));
		}
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		return disproveSuggestion(suggestion, new Random());
	}
}
