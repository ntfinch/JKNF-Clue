package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, Color color, int row, int col) {
        super(name, color, row, col);
    }

    public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {}
	
	public void createSuggestion() {}
}
