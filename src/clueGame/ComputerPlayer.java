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
	
	public void createSuggestion() {
//		BoardCell room = 
		
		
//		List<Card> unseenCards = new ArrayList<Card>(myCards);
//		unseenCards.removeAll(unseenCards);
//		
//		int rand = ThreadLocalRandom.current().nextInt(0, unseenCards.size() + 1);
//		
//		Solution solution = new Solution();
//		
	}
}
