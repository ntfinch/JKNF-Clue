package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player {
	private BoardCell lastRoom;
	private boolean acc;
	private char lRoomChar;
	private Solution suggestion;

	public ComputerPlayer() {
		acc = false;
	}

	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return pickLocation(targets, new Random());
	}

	public BoardCell pickLocation(Set<BoardCell> targets, Random rand) {
		// Converts the targets set into an array list
		ArrayList<BoardCell> targetList = new ArrayList<BoardCell>(targets);
		Collections.sort(targetList);

		// Loops through each spot in the list returning if it is a valid room
		for (int i = 0; i < targetList.size(); i++) {
			if (targetList.get(i).isRoom()
					&& (lastRoom == null || targetList.get(i).getInitial() != lastRoom.getInitial())) {
				lastRoom = targetList.get(i);
				return targetList.get(i);
			}
		}

		// finds a random number between 0 and the target list size - 1
		int value = rand.nextInt(targetList.size());

		// if a valid room was not found, a random target is selected
		return targetList.get(value);
	}

	public void makeAccusation() {
		String guess = this.suggestion.getPerson() + " " + this.suggestion.getRoom() + " " + this.suggestion.getWeapon();

		boolean won = Board.getInstance().checkAccusation(this.suggestion);
		if (won) {
			JOptionPane.showMessageDialog(null, "The computer just won, answer is " + guess);

			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(null, "The computer made an incorrect guess of " + guess);
		}
	}

	public void createSuggestion(Board board, BoardCell cell) {
		suggestion = new Solution();
		// Get room
		System.out.println(cell.getInitial());
		String room = board.getRoomName(cell.getInitial());
		//String room = (board.getLegend()).get(cell.getInitial());
		this.suggestion.setRoom(room);
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
			Random r = new Random();
			System.out.println("persons size :: ");
			System.out.println(personsSize);
			person = persons.get(r.nextInt(personsSize)+1).getName();
		}
		this.suggestion.person = person;
		// Get weapon
		String weapon = "";
		int weaponsSize = weapons.size();
		if (weaponsSize == 1) {
			weapon = weapons.get(0).getName();
		} else {
			Random r = new Random();
			System.out.println("weapons size :: ");
			System.out.print(weaponsSize);
			weapon = weapons.get(r.nextInt(weaponsSize)+1).getName();
			
		}

		this.suggestion.weapon = weapon;
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

	public void setLastRoom(BoardCell room) {
		lastRoom = room;
	}

	@Override
	public void moveNeedsToBeMade(Board b) {
		if (this.acc)
			makeAccusation();
		else {
			Set<BoardCell> targets = b.getTargets();
			BoardCell loc = pickLocation(targets);
			setRow(loc.getColumn());
			setCol(loc.getRow());
			if (loc.isRoom()) {
				this.lRoomChar = loc.getInitial();
				createSuggestion(b, loc);
			}
		}
	}
}
