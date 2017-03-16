package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests {
    private static Board board;
    
    @BeforeClass
    public static void initTheBoard() {
        
        // initialize board
        // Board is singleton, get the only instance and initialize it
        board = Board.getInstance();
        // set the file names to use my config files
        board.setConfigFiles("ICJK_ClueLayout.csv", "ICJK_Legend.txt");
        board.initialize();
        
        // populate players
        
        // create deck
        
        // deal deck
    }
    
    @Test
    public void loadThePeople() throws FileNotFoundException {
        List<Player> players = board.loadPlayerConfig("TDNFTP_players.txt");
        
        //Test to make sure first and last players are fully loaded correctly
        //Test that the other players are loaded in general
        
        //Test some names
        assertEquals("John Doe", players.get(0).getName());
        assertEquals("Jane Doe", players.get(1).getName());
        assertEquals("Brad Pitt", players.get(2).getName());
        
        //Test some colors
        assertEquals(Color.GREEN, players.get(0).getColor());
        assertEquals(Color.BLUE, players.get(2).getColor());
        
        //Test some positions
        assertEquals(0, players.get(0).getRow());
        assertEquals(1, players.get(0).getColumn());
        
        assertEquals(5, players.get(2).getRow());
        assertEquals(20, players.get(2).getColumn());
        
        //Test people are computers or people
        assertTrue(players.get(0) instanceof HumanPlayer);
        assertTrue(players.get(2) instanceof ComputerPlayer);
    }
    
    @Test
    public void loadTheDeck() {
        fail("Not Yet Implemented");
    }
    
    @Test
    public void dealTheCards() {
        fail("Not Yet Implemented");
    }
    
}
