package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.prism.paint.Color;

import clueGame.Board;
import clueGame.Player;

public class GameSetupTests {
    private static Board board;
    
    @BeforeClass
    public void initTheBoard() {
        
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
        
        //Test some names
        assertEquals(players.get(0).getPlayerName(), "John Doe");
        assertEquals(players.get(2).getPlayerName(), "Brad Pitt");
        
        //Test some colors
        assertEquals(players.get(0).getColor(), Color.GREEN);
        assertEquals(players.get(2).getColor(), Color.BLUE);
        
        //Test some positions
        assertEquals(players.get(0).getRow(), 0);
        assertEquals(players.get(0).getColumn(), 1);
        
        assertEquals(players.get(2).getRow(), 5);
        assertEquals(players.get(2).getColumn(), 20);
    }
    
    @Test
    public void loadTheDeck() {
    }
    
    @Test
    public void dealTheCards() {
    }
    
}
