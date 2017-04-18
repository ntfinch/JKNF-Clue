package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HumanPlayer extends Player {
	private boolean turnUnfinished;
	public HumanPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);

	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		// For the moment, uses the same random suggestion system as
		// computerPlayer
		Random random = new Random();
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

	public void moveNeedsToBeMade(Board b) {
		this.turnUnfinished = true;
		b.highlightCells(true);		
	}

	public void moveMade(BoardCell clickedCell) {
		this.turnUnfinished = false;

		setRow(clickedCell.getRow());
		setCol(clickedCell.getColumn());
		
	}
	public boolean isTurnUnfinished(){
		return this.turnUnfinished;
	}

}
