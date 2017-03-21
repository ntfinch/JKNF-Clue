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
		board.initialize();
		board.loadDeck();
		board.dealDeck();
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
		int cardsPerPlayer = deck.size() / players.size();
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
	
	@Test
	public void disproveSuggestions(){
		//Set up cards for the test
		Card person = new Card(CardType.PERSON, "Bob");
		Card badperson = new Card(CardType.PERSON, "Bill");
		Card room = new Card(CardType.ROOM, "Bedroom");
		Card badroom = new Card(CardType.ROOM, "Bathroom");
		Card weapon = new Card(CardType.WEAPON, "Gun");
		Card badweapon = new Card(CardType.WEAPON, "Knife");
		
		//Set up a computer player and give them three of the cards
		ComputerPlayer tester = new ComputerPlayer("Joe", Color.RED, 0, 0);
		tester.addCard(person);
		tester.addCard(room);
		tester.addCard(weapon);
		
		//Test with only one card correct
		assertEquals(weapon, tester.disproveSuggestion(new Solution(badperson.getName(), badroom.getName(), weapon.getName()), new Random()));
		assertEquals(person, tester.disproveSuggestion(new Solution(person.getName(), badroom.getName(), badweapon.getName()), new Random()));
		assertEquals(room, tester.disproveSuggestion(new Solution(badperson.getName(), room.getName(), badweapon.getName()), new Random()));
		
		//Tests with all three cards correct
		assertEquals(person, tester.disproveSuggestion(new Solution(person.getName(), room.getName(), weapon.getName()), new FakeRandom(0)));
		assertEquals(room, tester.disproveSuggestion(new Solution(person.getName(), room.getName(), weapon.getName()), new FakeRandom(1)));
		assertEquals(weapon, tester.disproveSuggestion(new Solution(person.getName(), room.getName(), weapon.getName()), new FakeRandom(2)));
		
		//Test with no cards correct
		assertEquals(null, tester.disproveSuggestion(new Solution(badperson.getName(), badroom.getName(), badweapon.getName()), new Random()));
	}
}
