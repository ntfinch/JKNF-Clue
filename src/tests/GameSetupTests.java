package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;

public class GameSetupTests {
	
	
	@Before
	public void init() {
		private static Board board;
		
		//initialize board
		// Board is singleton, get the only instance and initialize it
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ICJK_ClueLayout.csv", "ICJK_Legend.txt");
		board.initialize();
		
		//populate board
		
		//populate players
		
		//create deck
		
		//deal deck
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
