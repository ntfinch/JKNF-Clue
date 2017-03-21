package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSetupTests {
	private static Board board;

	@BeforeClass
	public static void initTheBoard() {
		// initialize board
		// Board is singleton, get the only instance and initialize it
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ICJK_ClueLayout.csv", "ICJK_Legend.txt", "TDNFTP_players.txt");

		// Loads the board with a fake random deck
		board.reset();
		try {
			board.loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}

		try {
			board.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}

		try {
			board.loadPlayerConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		board.loadDeck();
		// Distributes 1 weapon, room, person, repeatedly until it runs out and
		// then just distributes rooms
		int[] veryRandom = { 0, 6, 15, 1, 7, 16, 2, 8, 17, 3, 9, 18, 4, 10, 19, 5, 11, 20, 12, 13, 14 };
		board.dealDeck(new FakeRandom(veryRandom));
	}

	@Test
	public void loadThePeople() throws FileNotFoundException {
		List<Player> players = board.getPlayers();

		// Test to make sure first and last players are fully loaded correctly
		// Test that the other players are loaded in general

		// Test some names
		assertEquals("John Doe", players.get(0).getName());
		assertEquals("Jane Doe", players.get(1).getName());
		assertEquals("Brad Pitt", players.get(2).getName());

		// Test some colors
		assertEquals(Color.GREEN, players.get(0).getColor());
		assertEquals(Color.BLUE, players.get(2).getColor());

		// Test some positions
		assertEquals(0, players.get(0).getRow());
		assertEquals(1, players.get(0).getColumn());

		assertEquals(5, players.get(2).getRow());
		assertEquals(20, players.get(2).getColumn());

		// Test people are computers or people
		assertTrue(players.get(0) instanceof HumanPlayer);
		assertTrue(players.get(2) instanceof ComputerPlayer);
	}

	@Test
	public void loadTheDeck() {
		final List<Card> deck = board.getDeck();

		// Test that the deck has the right number of cards
		assertEquals(6 + 6 + 9, deck.size());

		// Test that the deck has the right number of each card type
		int weaponCount = 0;
		int roomCount = 0;
		int peopleCount = 0;
		for (Card card : deck) {
			if (card.getType() == CardType.WEAPON) {
				peopleCount++;
			} else if (card.getType() == CardType.ROOM) {
				roomCount++;
			} else if (card.getType() == CardType.PERSON) {
				weaponCount++;
			}
		}
		assertEquals(6, peopleCount);
		assertEquals(9, roomCount);
		assertEquals(6, weaponCount);

		// Test that the names were loaded right
		boolean foundGun = false;
		boolean foundPlatoon = false;
		boolean foundMasterBedroom = false;
		for (Card card : deck) {
			if (card.getType() == CardType.WEAPON && !foundGun && card.getName().equals("Gun")) {
				foundGun = true;
			} else if (card.getType() == CardType.ROOM && !foundMasterBedroom
					&& card.getName().equals("Master bedroom")) {
				foundMasterBedroom = true;
			} else if (card.getType() == CardType.PERSON && !foundPlatoon && card.getName().equals("Mr. Platoon")) {
				foundPlatoon = true;
			}
		}
		assertTrue(foundGun);
		assertTrue(foundPlatoon);
		assertTrue(foundMasterBedroom);
	}

	@Test
	public void dealTheCards() {
		final List<Player> players = board.getPlayers();
		final List<Card> deck = board.getDeck();

		// Test that all cards have been dealt
		for (Card card : deck) {
			assertTrue(card.hasBeenDealt());
		}

		// Test that all players have roughly the same amount of cards
		int cardsPerPlayer = (deck.size() - 3) / players.size();
		for (Player player : players) {
			assertTrue(player.getCards().size() >= cardsPerPlayer);
		}

		// Test that all players have different cards
		for (int i = 0; i < players.size(); i++) {
			List<Card> p1Cards = players.get(i).getCards();

			for (int j = i + 1; j < players.size(); j++) {
				List<Card> p2Cards = players.get(j).getCards();

				for (Card card : p1Cards) {
					assertFalse(p2Cards.contains(card));
				}
			}
		}
	}

	@Test

	public void testCheckAccusation() {
		Solution trueSoln = new Solution("Mr. Bob", "Library", "Bat");
		Solution falseSoln = new Solution("Mr. Ryan", "Master Bedroom", "Gun");

		assertTrue(board.checkAccusation(trueSoln));
		assertFalse(board.checkAccusation(falseSoln));
	}

	// Independent test, does not require the use of board
	@Test
	public void testDisproveSuggestion() {
		// Set up cards for the test
		Card person = new Card(CardType.PERSON, "Bob");
		Card badperson = new Card(CardType.PERSON, "Bill");
		Card room = new Card(CardType.ROOM, "Bedroom");
		Card badroom = new Card(CardType.ROOM, "Bathroom");
		Card weapon = new Card(CardType.WEAPON, "Gun");
		Card badweapon = new Card(CardType.WEAPON, "Knife");

		// Set up a computer player and give them three of the cards
		ComputerPlayer tester = new ComputerPlayer("Joe", Color.RED, 0, 0);
		tester.addCard(person);
		tester.addCard(room);
		tester.addCard(weapon);

		// Test with only one card correct
		assertEquals(weapon, tester.disproveSuggestion(
				new Solution(badperson.getName(), badroom.getName(), weapon.getName()), new Random()));
		assertEquals(person, tester.disproveSuggestion(
				new Solution(person.getName(), badroom.getName(), badweapon.getName()), new Random()));
		assertEquals(room, tester.disproveSuggestion(
				new Solution(badperson.getName(), room.getName(), badweapon.getName()), new Random()));

		// Tests with all three cards correct
		assertEquals(person, tester.disproveSuggestion(new Solution(person.getName(), room.getName(), weapon.getName()),
				new FakeRandom(0)));
		assertEquals(room, tester.disproveSuggestion(new Solution(person.getName(), room.getName(), weapon.getName()),
				new FakeRandom(1)));
		assertEquals(weapon, tester.disproveSuggestion(new Solution(person.getName(), room.getName(), weapon.getName()),
				new FakeRandom(2)));

		// Test with no cards correct
		assertEquals(null, tester.disproveSuggestion(
				new Solution(badperson.getName(), badroom.getName(), badweapon.getName()), new Random()));
	}

	@Test
	public void testHandleSuggestion() {
		List<Player> players = board.getPlayers();
		Player john = players.get(0);
		Player jane = players.get(1);
		Player brad = players.get(2);

		// Only case where no one can disprove is if it is the answer
		assertEquals(null, board.handleSuggestion(board.getAnswer(), john));
		assertEquals(null, board.handleSuggestion(board.getAnswer(), jane));
		assertEquals(null, board.handleSuggestion(board.getAnswer(), brad));

		
		// Only Brad can disprove the solution
		Solution test = new Solution(brad.getCards().get(5).getName(), board.getAnswer().getRoom(),
				board.getAnswer().getWeapon());

		// He is the accuser, returns null
		assertEquals(null, board.handleSuggestion(test, brad));

		// He is not the accuser, returns the person he has
		assertEquals(brad.getCards().get(5), board.handleSuggestion(test, jane));
		
		// Only John, a human can disprove the solution
		test = new Solution(john.getCards().get(2).getName(), board.getAnswer().getRoom(),
				board.getAnswer().getWeapon());

		// He is the accuser, returns null
		assertEquals(null, board.handleSuggestion(test, john));

		// He is not the accuser, returns the person he has
		assertEquals(john.getCards().get(2), board.handleSuggestion(test, jane));

		// All three can disprove it
		test = new Solution(brad.getCards().get(5).getName(), jane.getCards().get(1).getName(),
				john.getCards().get(0).getName());

		// Returns Jane's since she is the first after John
		assertEquals(jane.getCards().get(1), board.handleSuggestion(test, john));

		// Returns Brad's since he is the first after Jane
		// Jane is human, Brad is not, Brad goes first anyway because he is next
		assertEquals(brad.getCards().get(5), board.handleSuggestion(test, jane));

		// Returns John's since he is the first after Brad
		assertEquals(john.getCards().get(0), board.handleSuggestion(test, brad));
	}
}
