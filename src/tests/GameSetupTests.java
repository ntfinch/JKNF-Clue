package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class GameSetupTests {
	private static Board board;
	
	@BeforeClass
	public void initTheBoard() {
		
		//initialize board
		// Board is singleton, get the only instance and initialize it
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ICJK_ClueLayout.csv", "ICJK_Legend.txt");
		board.initialize();
		
		//populate players
		
		//create deck
		
		//deal deck
	}

	@Test
	public void loadThePeople() throws FileNotFoundException {
		FileReader reader = new FileReader("TDNFTP_players.txt");
		Scanner in = new Scanner(reader);
		String firstLine = "";
		String lastLine = "";
		while (in.hasNextLine()) {
			if (firstLine.isEmpty()) {
				firstLine = in.nextLine();
			} else {
				lastLine = in.nextLine();
			}
		}
		in.close();

		assertEquals("John Doe,Green,0,1", firstLine);
		assertEquals("Brad Pitt,Blue,5,20", lastLine);
	}
	
	@Test
	public void loadTheDeck() {}
	
	@Test
	public void dealTheCards() {}

}
